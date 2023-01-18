
package me.pi.cloud.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.pi.cloud.gateway.enums.ResponseStatusEnum;
import me.pi.cloud.gateway.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.HttpStatusHolder;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.setResponseStatus;

/**
 * User Request Rate Limiter filter. See <a href="https://stripe.com/blog/rate-limiters">rate-limiters</a> and
 * <a href="https://gist.github.com/ptarjan/e38f45f2dfe601419ca3af937fff574d#file-1-check_request_rate_limiter-rb-L11-L34">
 *     check_request_rate_limiter
 * </a>.
 * @author ZnPi
 * @date 2023/01/18
 */
@Slf4j
@Component
public class PiRequestRateLimiterGatewayFilterFactory
		extends RequestRateLimiterGatewayFilterFactory {

	private static final String EMPTY_KEY = "____EMPTY_KEY__";

	private final ObjectMapper objectMapper;

	@Autowired
	public PiRequestRateLimiterGatewayFilterFactory(RateLimiter defaultRateLimiter,
													KeyResolver defaultKeyResolver,
													ObjectMapper objectMapper) {
		super(defaultRateLimiter, defaultKeyResolver);
		this.objectMapper = objectMapper;
	}

	@Override
	public GatewayFilter apply(RequestRateLimiterGatewayFilterFactory.Config config) {
		KeyResolver resolver = getOrDefault(config.getKeyResolver(), super.getDefaultKeyResolver());
		@SuppressWarnings("unchecked")
		RateLimiter<Object> limiter = getOrDefault(config.getRateLimiter(), super.getDefaultRateLimiter());
		boolean denyEmpty = getOrDefault(config.getDenyEmptyKey(), super.isDenyEmptyKey());
		HttpStatusHolder emptyKeyStatus = HttpStatusHolder
				.parse(getOrDefault(config.getEmptyKeyStatus(), super.getEmptyKeyStatusCode()));

		return (exchange, chain) -> resolver.resolve(exchange).defaultIfEmpty(EMPTY_KEY).flatMap(key -> {
			if (EMPTY_KEY.equals(key)) {
				if (denyEmpty) {
					setResponseStatus(exchange, emptyKeyStatus);
					return exchange.getResponse().setComplete();
				}
				return chain.filter(exchange);
			}
			String routeId = config.getRouteId();
			if (routeId == null) {
				Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
				assert route != null;
				routeId = route.getId();
			}
			return limiter.isAllowed(routeId, key).flatMap(response -> {

				for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
					exchange.getResponse().getHeaders().add(header.getKey(), header.getValue());
				}

				if (response.isAllowed()) {
					return chain.filter(exchange);
				}

				ServerHttpResponse serverHttpResponse = exchange.getResponse();
				boolean rst = serverHttpResponse.setStatusCode(config.getStatusCode());
				if (!rst && log.isWarnEnabled()) {
					log.warn("Unable to set status code to " + rst + ". Response already committed.");
				}
				serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);

				return serverHttpResponse.writeWith(Mono.create(monoSink -> {
					try {
						byte[] bytes = objectMapper.writeValueAsBytes(
								ResponseData.error(ResponseStatusEnum.REQUEST_RATE_LIMIT));
						DataBuffer dataBuffer = serverHttpResponse.bufferFactory().wrap(bytes);
						monoSink.success(dataBuffer);
					} catch (JsonProcessingException e) {
						log.error(e.getMessage());
						monoSink.error(e);
					}
				}));
			});
		});
	}

	private <T> T getOrDefault(T configValue, T defaultValue) {
		return (configValue != null) ? configValue : defaultValue;
	}
}
