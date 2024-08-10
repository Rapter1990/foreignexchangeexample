package com.example.demo.model.dto.request.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation for validating currency codes.
 * This annotation is used to mark fields or parameters that should be validated
 * to ensure they contain a valid currency code. The validation logic is provided
 * by the {@link CurrencyValidatorConstraint} class.
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CurrencyValidatorConstraint.class)
public @interface CurrencyValidator {

    /**
     * The error message that will be used when the currency code is invalid.
     * This message can be customized to provide more specific information about
     * the validation error.
     *
     * @return the error message
     */
    String message() default "Invalid currency code";

    /**
     * Groups used for grouping constraints. This can be used to apply the constraint
     * only in specific contexts or validation groups.
     * This can be left empty if not needed.
     *
     * @return the groups associated with the constraint
     */
    Class<?>[] groups() default {};

    /**
     * Payload for carrying additional data with the constraint annotation.
     * This can be used by clients of the validation API to associate metadata
     * with the constraint.
     *
     * @return the payload associated with the constraint
     */
    Class<? extends Payload>[] payload() default {};

}
