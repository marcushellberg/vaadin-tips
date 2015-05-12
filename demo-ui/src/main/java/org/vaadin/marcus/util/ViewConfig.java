package org.vaadin.marcus.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for easily collecting View meta info. Less typos, more awesome.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewConfig {
    enum CreateMode {ALWAYS_NEW, LAZY_INIT, EAGER_INIT}

    String uri();
    String displayName();
    CreateMode createMode() default CreateMode.ALWAYS_NEW;
}
