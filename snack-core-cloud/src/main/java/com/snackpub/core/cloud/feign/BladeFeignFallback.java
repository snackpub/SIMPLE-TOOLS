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
package com.snackpub.core.cloud.feign;

import com.fasterxml.jackson.databind.JsonNode;
import com.snackpub.core.tools.api.R;
import com.snackpub.core.tools.api.ResultCode;
import com.snackpub.core.tools.jackson.JsonUtil;
import com.snackpub.core.tools.utils.ObjectUtil;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * blade fallBack  代理处理
 *
 * @author L.cm
 */
@Slf4j
@AllArgsConstructor
public class BladeFeignFallback<T> implements MethodInterceptor {
    private final Class<T> targetType;
    private final String targetName;
    private final Throwable cause;
    private final String code = "code";

    @Nullable
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        String errorMessage = cause.getMessage();
        log.error("SnackPubFeignFallback:[{}.{}] serviceId:[{}] message:[{}]", targetType.getName(), method.getName(), targetName, errorMessage);
        Class<?> returnType = method.getReturnType();
        // 暂时不支持 flux,rx, 异步等，返回值不是R,直接返回null
        if (R.class != returnType) {
            return null;
        }
        // 非 FeignException
        if (!(cause instanceof FeignException)) {
            return R.fail(ResultCode.INTERNAL_SERVER_ERROR, errorMessage);
        }
        FeignException exception = (FeignException) cause;
        byte[] content = exception.content();
        // 如果返回的数据为空
        if (ObjectUtil.isEmpty(content)) {
            return R.fail(ResultCode.INTERNAL_SERVER_ERROR, errorMessage);
        }
        // 转换成jsonNode 读取，因为直接转换，可能对方放回的不是R格式
        JsonNode resultNode = JsonUtil.readTree(content);
        // 判断是否是R格式返回体
        if (resultNode.has(code)) {
            return JsonUtil.getInstance().convertValue(resultNode, R.class);
        }
        return R.fail(resultNode.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BladeFeignFallback<?> that = (BladeFeignFallback<?>) o;
        return targetType.equals(that.targetType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetType);
    }
}
