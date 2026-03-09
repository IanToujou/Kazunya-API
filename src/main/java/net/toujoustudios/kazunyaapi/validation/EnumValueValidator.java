package net.toujoustudios.kazunyaapi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValueValidator implements ConstraintValidator<ValidEnum, String> {

    private Class<? extends Enum<?>> enumClass;
    private boolean ignoreCase;

    @Override
    public void initialize(ValidEnum annotation) {
        this.enumClass = annotation.enumClass();
        this.ignoreCase = annotation.ignoreCase();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return true;
        Enum<?>[] enumConstants = enumClass.getEnumConstants();
        return Arrays.stream(enumConstants)
                .anyMatch(e -> ignoreCase
                        ? e.name().equalsIgnoreCase(value)
                        : e.name().equals(value));
    }

}