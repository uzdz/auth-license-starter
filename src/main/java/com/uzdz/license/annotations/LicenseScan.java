package com.uzdz.license.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 证书检查
 * @author uzdz
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Documented
public @interface LicenseScan {
}
