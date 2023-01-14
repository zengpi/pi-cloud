package me.pi.cloud.monitor.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

/**
 * @author ZnPi
 * @date 2023-01-14
 */
public class CustomCsrfFilter extends OncePerRequestFilter {

	public static final String CSRF_COOKIE_NAME = "XSRF-TOKEN";

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									@NonNull HttpServletResponse response,
									@NonNull FilterChain filterChain)
			throws ServletException, IOException {

		CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

		if (csrf != null) {

			Cookie cookie = WebUtils.getCookie(request, CSRF_COOKIE_NAME);
			String token = csrf.getToken();

			if (cookie == null || token != null && !token.equals(cookie.getValue())) {
				cookie = new Cookie(CSRF_COOKIE_NAME, token);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}

		filterChain.doFilter(request, response);
	}

}