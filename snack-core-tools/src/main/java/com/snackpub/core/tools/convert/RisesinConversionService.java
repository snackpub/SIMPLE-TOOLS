package com.snackpub.core.tools.convert;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.lang.Nullable;
import org.springframework.util.StringValueResolver;

/**
 * 类型 转换 服务，添加了 IEnum 转换
 */
public class RisesinConversionService extends ApplicationConversionService {
    @Nullable
    private static volatile RisesinConversionService SHARED_INSTANCE;

    public RisesinConversionService() {
        this(null);
    }

    public RisesinConversionService(@Nullable StringValueResolver embeddedValueResolver) {
        super(embeddedValueResolver);
        super.addConverter(new EnumToStringConverter());
        super.addConverter(new StringToEnumConverter());
    }

    /**
     * Return a shared default application {@code ConversionService} instance, lazily
     * building it once needed.
     * <p>
     * Note: This method actually returns an {@link RisesinConversionService}
     * instance. However, the {@code ConversionService} signature has been preserved for
     * binary compatibility.
     *
     * @return the shared {@code BladeConversionService} instance (never{@code null})
     */
    public static GenericConversionService getInstance() {
        RisesinConversionService sharedInstance = RisesinConversionService.SHARED_INSTANCE;
        if (sharedInstance == null) {
            synchronized (RisesinConversionService.class) {
                sharedInstance = RisesinConversionService.SHARED_INSTANCE;
                if (sharedInstance == null) {
                    sharedInstance = new RisesinConversionService();
                    RisesinConversionService.SHARED_INSTANCE = sharedInstance;
                }
            }
        }
        return sharedInstance;
    }
}
