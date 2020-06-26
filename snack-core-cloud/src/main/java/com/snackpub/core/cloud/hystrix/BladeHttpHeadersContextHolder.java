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

import com.snackpub.core.cloud.props.BladeHystrixHeadersProperties;
import com.snackpub.core.tools.utils.StringUtil;
import com.snackpub.core.tools.utils.WebUtil;
import org.springframework.core.NamedThreadLocal;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * HttpHeadersContext
 *
 * @author L.cm
 */
public class BladeHttpHeadersContextHolder {
    private static final ThreadLocal<HttpHeaders> HTTP_HEADERS_HOLDER = new NamedThreadLocal<>("Blade hystrix HttpHeaders");

    /**
     * 请求和转发的ip
     */
    private static final String[] ALLOW_HEADS = new String[]{
            "X-Real-IP", "x-forwarded-for", "authorization", "Authorization",
    };

    static void set(HttpHeaders httpHeaders) {
        HTTP_HEADERS_HOLDER.set(httpHeaders);
    }

    @Nullable
    public static HttpHeaders get() {
        return HTTP_HEADERS_HOLDER.get();
    }

    static void remove() {
        HTTP_HEADERS_HOLDER.remove();
    }

    @Nullable
    public static HttpHeaders toHeaders(
            @Nullable BladeHystrixAccountGetter accountGetter,
            BladeHystrixHeadersProperties properties) {
        HttpServletRequest request = WebUtil.getRequest();
        if (request == null) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        String accountHeaderName = properties.getAccount();
        // account
        if (accountGetter != null) {
            String xAccountHeader = accountGetter.get(request);
            if (StringUtil.isNotBlank(xAccountHeader)) {
                headers.add(accountHeaderName, xAccountHeader);
            }
        }
        List<String> allowHeadsList = new ArrayList<>(Arrays.asList(ALLOW_HEADS));
        // account header
        allowHeadsList.add(accountHeaderName);
        //
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            List<String> allowed = properties.getAllowed();
            String pattern = properties.getPattern();
            while (headerNames.hasMoreElements()) {
                String key = headerNames.nextElement();
                // header
                if (allowHeadsList.contains(key) || allowed.contains(key) || PatternMatchUtils.simpleMatch(pattern, key)) {
                    String values = request.getHeader(key);
                    // header value
                    if (StringUtil.isNotBlank(values)) {
                        headers.add(key, values);
                    }
                }

            }
        }
        return headers.isEmpty() ? null : headers;
    }
}
