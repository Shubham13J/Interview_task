package com.innoventes.test.app.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

//Task 6: Create a Field Validation Annotation:
//        Create and field validation annotation named @EvenNumberOrZero
//- The Integer or Long type field annotated with @EvenNumberOrZero annotation can take
//        only even numbers or zero as values (Even numbers are numbers that are divisible by 2)
//        - Use the created annotation to annotate the field strength


@Documented
@Constraint(validatedBy = EvenNumberOrZeroValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EvenNumberOrZero {

    String message() default "Only even numbers or zero are allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
