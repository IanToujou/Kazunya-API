package net.toujoustudios.kazunyaapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValueValidator.class)
@Documented
public @interface ValidEnum {

    Class<? extends Enum<?>> enumClass();
    String message() default "Invalid value. Must be one of: {values}";
    boolean ignoreCase() default true;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}