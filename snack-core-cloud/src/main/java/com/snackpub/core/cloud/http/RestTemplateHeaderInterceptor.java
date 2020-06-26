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
package com.snackpub.core.cloud.http;

import com.snackpub.core.cloud.hystrix.BladeHttpHeadersContextHolder;
import com.snackpub.core.cloud.hystrix.BladeHystrixAccountGetter;
import com.snackpub.core.cloud.props.BladeHystrixHeadersProperties;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;

import java.io.IOException;

/**
 * RestTemplateHeaderInterceptor 传递Request header
 *
 * @author L.cm
 */
@AllArgsConstructor
public class RestTemplateHeaderInterceptor implements ClientHttpRequestInterceptor {
    @Nullable
    private final BladeHystrixAccountGetter accountGetter;
    private final BladeHystrixHeadersProperties properties;

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request, byte[] bytes,
            ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = BladeHttpHeadersContextHolder.get();
        // 1.考虑2种情况1. RestTemplate 不是用 hystrix 2. 使用 hystrix
        if (headers == null) {
            headers = BladeHttpHeadersContextHolder.toHeaders(accountGetter, properties);
        }
        if (headers != null && !headers.isEmpty()) {
            HttpHeaders httpHeaders = request.getHeaders();
            headers.forEach((key, values) -> {
                values.forEach(value -> httpHeaders.add(key, value));
            });
        }
        return execution.execute(request, bytes);
    }
}
