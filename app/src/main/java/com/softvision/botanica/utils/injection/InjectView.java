package com.softvision.botanica.utils.injection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by codetwister on 1/23/14.
 * used to annotate methods that handle a specific type of event
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectView {
    int value();
}
