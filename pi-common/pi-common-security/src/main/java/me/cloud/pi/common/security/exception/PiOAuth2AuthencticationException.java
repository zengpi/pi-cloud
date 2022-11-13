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

package me.cloud.pi.common.security.exception;

import lombok.Getter;
import me.cloud.pi.common.web.enums.ResponseStatus;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * @author ZnPi
 * @date 2022-10-21
 */
@Getter
public class PiOAuth2AuthencticationException extends OAuth2AuthenticationException {
    private final String code;

    public PiOAuth2AuthencticationException(ResponseStatus status) {
        super(new OAuth2Error(status.getMsg()), status.getMsg());
        this.code = status.getCode();
    }

    public PiOAuth2AuthencticationException(ResponseStatus status, String msg) {
        super(new OAuth2Error(status.getMsg()), msg);
        this.code = status.getCode();
    }
}