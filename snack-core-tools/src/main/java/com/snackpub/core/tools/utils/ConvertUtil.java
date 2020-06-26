//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.snackpub.core.tools.utils;

import com.snackpub.core.tools.convert.RisesinConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.lang.Nullable;

public final class ConvertUtil {
    @Nullable
    public static <T> T convert(@Nullable Object source, Class<T> targetType) {
        if (source == null) {
            return null;
        } else if (ClassUtil.isAssignableValue(targetType, source)) {
            return (T) source;
        } else {
            GenericConversionService conversionService = RisesinConversionService.getInstance();
            return conversionService.convert(source, targetType);
        }
    }

    @Nullable
    public static <T> T convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return null;
        } else {
            GenericConversionService conversionService = RisesinConversionService.getInstance();
            return (T) conversionService.convert(source, sourceType, targetType);
        }
    }

    @Nullable
    public static <T> T convert(@Nullable Object source, TypeDescriptor targetType) {
        if (source == null) {
            return null;
        } else {
            GenericConversionService conversionService = RisesinConversionService.getInstance();
            return (T) conversionService.convert(source, targetType);
        }
    }

    private ConvertUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
