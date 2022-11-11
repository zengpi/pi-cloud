/*
 * Copyright 2022 ZnPi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.cloud.pi.auth.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class SwaggerAutoConfiguration {

	@Bean
	public OpenAPI customOpenAPI() {
		List<Server> serverList = new ArrayList<>();
		serverList.add(new Server().url("http://localhost:9731/admin"));

		return new OpenAPI()
				.schemaRequirement(HttpHeaders.AUTHORIZATION, new SecurityScheme()
								.type(SecurityScheme.Type.OAUTH2)
								.flows(new OAuthFlows()
										.password(new OAuthFlow()
												.tokenUrl("http://localhost:9731/auth/oauth2/token")
												.scopes(new Scopes()
														.addString("server", "server scope"))))
						)
				.info(new Info()
						.title("PI-AUTH")
						.version("1.0.0-SNAPSHOT")
						.license(new License().name("Apache 2.0")))
				.servers(serverList);
	}
}