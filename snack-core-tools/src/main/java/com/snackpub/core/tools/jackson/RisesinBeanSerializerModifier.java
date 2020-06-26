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
package com.snackpub.core.tools.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.snackpub.core.tools.utils.StringPool;
import com.snackpub.core.tools.utils.StringUtil;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * jackson默认值为null时的处理
 * <p>
 * 主要是为了避免app端出现null导致闪退
 * <p>
 * 规则
 * <p>
 * number -1
 * string ""
 * date ""
 * boolean false
 * array []
 * Object {}
 *
 * @author L.cm
 */
public class RisesinBeanSerializerModifier extends BeanSerializerModifier {
    @Override
    public List<BeanPropertyWriter> changeProperties(
            SerializationConfig config, BeanDescription beanDesc,
            List<BeanPropertyWriter> beanProperties) {
        //
        beanProperties.forEach(writer -> {
            // null = xxx)
            if (writer.hasNullSerializer()) {
                return;
            }
            JavaType type = writer.getType();
            Class<?> clazz = type.getRawClass();
            if (type.isTypeOrSubTypeOf(Number.class)) {
                writer.assignNullSerializer(NullJsonSerializers.NUMBER_JSON_SERIALIZER);
            } else if (type.isTypeOrSubTypeOf(Boolean.class)) {
                writer.assignNullSerializer(NullJsonSerializers.BOOLEAN_JSON_SERIALIZER);
            } else if (type.isTypeOrSubTypeOf(Character.class)) {
                writer.assignNullSerializer(NullJsonSerializers.STRING_JSON_SERIALIZER);
            } else if (type.isTypeOrSubTypeOf(String.class)) {
                writer.assignNullSerializer(NullJsonSerializers.STRING_JSON_SERIALIZER);
            } else if (type.isArrayType() || clazz.isArray() || type.isTypeOrSubTypeOf(Collection.class)) {
                writer.assignNullSerializer(NullJsonSerializers.ARRAY_JSON_SERIALIZER);
            } else if (type.isTypeOrSubTypeOf(OffsetDateTime.class)) {
                writer.assignNullSerializer(NullJsonSerializers.STRING_JSON_SERIALIZER);
            } else if (type.isTypeOrSubTypeOf(Date.class) || type.isTypeOrSubTypeOf(TemporalAccessor.class)) {
                writer.assignNullSerializer(NullJsonSerializers.STRING_JSON_SERIALIZER);
            } else {
                writer.assignNullSerializer(NullJsonSerializers.OBJECT_JSON_SERIALIZER);
            }
        });
        return super.changeProperties(config, beanDesc, beanProperties);
    }

    public interface NullJsonSerializers {

        JsonSerializer<Object> STRING_JSON_SERIALIZER = new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString(StringPool.EMPTY);
            }
        };

        JsonSerializer<Object> NUMBER_JSON_SERIALIZER = new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeNumber(StringUtil.INDEX_NOT_FOUND);
            }
        };

        JsonSerializer<Object> BOOLEAN_JSON_SERIALIZER = new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeObject(Boolean.FALSE);
            }
        };

        JsonSerializer<Object> ARRAY_JSON_SERIALIZER = new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeStartArray();
                gen.writeEndArray();
            }
        };

        JsonSerializer<Object> OBJECT_JSON_SERIALIZER = new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeStartObject();
                gen.writeEndObject();
            }
        };

    }
}
