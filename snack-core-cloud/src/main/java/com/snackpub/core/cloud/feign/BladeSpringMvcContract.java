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

import com.alibaba.csp.sentinel.util.StringUtil;
import com.snackpub.core.cloud.annotation.ApiVersion;
import com.snackpub.core.cloud.annotation.UrlVersion;
import com.snackpub.core.cloud.version.BladeMediaType;
import feign.MethodMetadata;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * blade-boot 的版本处理
 *
 * @see com.snackpub.core.cloud.annotation.UrlVersion
 * @see com.snackpub.core.cloud.annotation.ApiVersion
 * @author L.cm
 */
public class BladeSpringMvcContract extends SpringMvcContract {

	public BladeSpringMvcContract(List<AnnotatedParameterProcessor> annotatedParameterProcessors, ConversionService conversionService) {
		super(annotatedParameterProcessors, conversionService);
	}

	@Override
	protected void processAnnotationOnMethod(MethodMetadata data, Annotation methodAnnotation, Method method) {
		if (RequestMapping.class.isInstance(methodAnnotation) || methodAnnotation.annotationType().isAnnotationPresent(RequestMapping.class)) {
			Class<?> targetType = method.getDeclaringClass();
			// url 
			UrlVersion urlVersion = AnnotatedElementUtils.findMergedAnnotation(method, UrlVersion.class);
			// 
			if (urlVersion == null || StringUtil.isBlank(urlVersion.value())) {
				urlVersion = AnnotatedElementUtils.findMergedAnnotation(targetType, UrlVersion.class);
			}
			if (urlVersion != null && StringUtil.isNotBlank(urlVersion.value())) {
				String versionUrl = "/" + urlVersion.value();
				data.template().uri(versionUrl);
			}

			// urlMedia Types 
			super.processAnnotationOnMethod(data, methodAnnotation, method);

			// Media Types 
			ApiVersion apiVersion = AnnotatedElementUtils.findMergedAnnotation(method, ApiVersion.class);
			// 
			if (apiVersion == null || StringUtil.isBlank(apiVersion.value())) {
				apiVersion = AnnotatedElementUtils.findMergedAnnotation(targetType, ApiVersion.class);
			}
			if (apiVersion != null && StringUtil.isNotBlank(apiVersion.value())) {
				BladeMediaType BladeMediaType = new BladeMediaType(apiVersion.value());
				data.template().header(HttpHeaders.ACCEPT, BladeMediaType.toString());
			}
		}
	}

	/**
	 * https://gist.github.com/rmfish/0ed59a9af6c05157be2a60c9acea2a10
	 * @param annotations 
	 * @param paramIndex 
	 * @return http 
	 */
	@Override
	protected boolean processAnnotationsOnParameter(MethodMetadata data, Annotation[] annotations, int paramIndex) {
		boolean httpAnnotation = super.processAnnotationsOnParameter(data, annotations, paramIndex);
		// springMvc Get Param
		if (!httpAnnotation && StringPool.GET.equals(data.template().method().toUpperCase())) {
			for (Annotation parameterAnnotation : annotations) {
				if (!(parameterAnnotation instanceof RequestBody)) {
					return false;
				}
			}
			data.queryMapIndex(paramIndex);
			return true;
		}
		return httpAnnotation;
	}
}
