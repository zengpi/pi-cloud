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

package me.cloud.pi.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * SpringDoc 分组设置
 * @author ZnPi
 * @date 2022-11-11
 */
@Component
@RequiredArgsConstructor
public class SpringDocConfiguration {

	private final SwaggerUiConfigParameters swaggerUiConfigParameters;

	@PostConstruct
	public void init() {
		swaggerUiConfigParameters.addGroup("admin");
		swaggerUiConfigParameters.addGroup("auth");
	}
}