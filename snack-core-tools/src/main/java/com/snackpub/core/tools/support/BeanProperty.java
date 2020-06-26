package com.snackpub.core.tools.support;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Bean 属性
 */
@Getter
@AllArgsConstructor
public class BeanProperty {
    private final String name;
    private final Class<?> type;
}
