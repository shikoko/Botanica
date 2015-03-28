package com.softvision.botanica.common.pojo.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: lorand.krucz
 * Date: 2/8/13
 * Time: 5:50 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Compound {
    Class type() default Object.class;
}
