/**
 * Copyright (c) 2018-2028, lengleng (wangiegie@gmail.com).
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
package com.snackpub.transaction.annotation;

import com.alibaba.cloud.seata.feign.hystrix.SeataHystrixAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.lang.annotation.*;

/**
 * Seata启动注解配置
 *
 * <p>
 *     Field existingConcurrencyStrategy in com.snackpub.core.cloud.hystrix.BladeHystrixAutoConfiguration required a single bean, but 2 were found:
 *     seataHystrixConcurrencyStrategy.class
 *     sleuthHystrixConcurrencyStrategy.class
 *     使用 seata 集成 sleuth 时报这个问题，排除其中一个bean即可
 * </p>
 *
 * @author Qiuhaijun
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableDiscoveryClient
@EnableCircuitBreaker
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,SeataHystrixAutoConfiguration.class})
public @interface SeataCloudApplication {

}