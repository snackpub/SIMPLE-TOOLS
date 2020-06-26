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
package com.snackpub.core.cloud.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * Hystrix Headers  配置
 *
 * @author L.cm
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties("snackpub.hystrix.headers")
public class BladeHystrixHeadersProperties {

    /**
     * 用于聚合层向调用层传递用户信息的请求头，默认：x-snackpub-account
     */
    private String account = "X-SnackPub-Account";

    /**
     * RestTemplate 和 Fegin 传递到下层 Headers 的名称表达式
     */
    @Nullable
    private String pattern = "SnackPub*";

    /**
     * RestTemplate Fegin  传递到下层 Headers 的名称列表
     */
    private List<String> allowed = Arrays.asList("X-Real-IP", "x-forwarded-for", "authorization", "Authorization");

}
