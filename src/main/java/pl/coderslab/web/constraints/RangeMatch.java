package pl.coderslab.web.constraints;


import pl.coderslab.web.constraints.impl.RangeMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = RangeMatchValidator.class)
@Documented
public @interface RangeMatch {

    String message() default "{Maximum value must by higher than minimum}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String min();

    String max();

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List
    {
        RangeMatch[] value();
    }
}
