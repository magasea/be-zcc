package com.wensheng.zcc.common.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author chenwei on 1/10/19
 * @project zcc-backend
 */
@Documented
@Constraint(validatedBy = SQLInjectionSafeConstraintValidator.class)
@Target( { ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLInjectionSafe {

  String message() default "{SQLInjectionSafe}";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}