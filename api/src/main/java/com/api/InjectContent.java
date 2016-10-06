package com.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;

@Target(value = FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface InjectContent {
    String key();
}
