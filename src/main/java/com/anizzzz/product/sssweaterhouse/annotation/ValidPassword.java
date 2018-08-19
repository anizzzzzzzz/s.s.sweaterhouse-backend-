package com.anizzzz.product.sssweaterhouse.annotation;

import com.anizzzz.product.sssweaterhouse.validation.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidPassword {
    String message() default "This password cannot be set.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
