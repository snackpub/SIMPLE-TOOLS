/**
 * Copyright (c) 2018-2028, DreamLu (qq596392912@gmail.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.snackpub.core.cloud.hystrix;


import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;

/**
 * Blade 用户信息获取器，用于请求头传递
 *
 * @author L.cm
 */
public interface BladeHystrixAccountGetter {

    /**
     * 账户信息获取器
     *
     * @param request HttpServletRequest
     * @return account 信息
     */
    @Nullable
    String get(HttpServletRequest request);
}
