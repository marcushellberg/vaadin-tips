package org.vaadin.marcus.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for easily collecting View meta info. Less typos, more awesome.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ViewConfig {
    enum CreationMode {ALWAYS_NEW, LAZY, EAGER}

    String uri();
    String displayName();
    CreationMode create() default CreationMode.ALWAYS_NEW;
}
