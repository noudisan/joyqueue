/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jd.joyqueue.handler.routing.validate;

import com.jd.joyqueue.handler.error.ConfigException;
import com.jd.joyqueue.handler.error.ErrorCode;
import com.jd.joyqueue.model.domain.Application;
import com.jd.joyqueue.model.domain.ApplicationUser;
import com.jd.joyqueue.service.UserService;
import com.jd.laf.binding.annotation.Value;
import com.jd.laf.web.vertx.parameter.Parameters.RequestParameter;
import io.vertx.ext.web.RoutingContext;

import javax.validation.constraints.NotNull;

import static com.jd.joyqueue.handler.Constants.APPLICATION;
import static com.jd.joyqueue.handler.Constants.APP_USER_ID;
import static com.jd.joyqueue.handler.Constants.APP_USER;
import static com.jd.joyqueue.handler.Constants.USER_ID;

/**
 * 验证是指定应用的成员
 */
public class ValidateAppUserOfApplicationHandler extends ValidateHandler {
    @Value
    @NotNull
    protected UserService userService;

    @Override
    protected void validate(final RoutingContext context, final RequestParameter parameter) {
        Application application = context.get(APPLICATION);
        //appUserId参数
        Long appUserId = parameter.query().getLong(APP_USER_ID);
        //userId参数
        Long userId = parameter.query().getLong(USER_ID);
        ApplicationUser user = appUserId != null ? userService.findAppUserById(appUserId) :
                (userId != null && application != null ? userService.findAppUserByAppIdAndUserId(application.getId(), userId) : null);
        if (application == null) {
            throw new ConfigException(ErrorCode.ApplicationNotExists);
        } else if (user == null) {
            throw new ConfigException(ErrorCode.AppUserNotExists);
        } else if (user.getApplication().getId() != application.getId()) {
            throw new ConfigException(ErrorCode.NoPrivilege);
        }
        context.put(APP_USER, user);

    }

    @Override
    public String type() {
        return "validateAppUserOfApplication";
    }
}