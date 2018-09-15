package pl.coderslab.domain.constraints.impl;

import org.apache.commons.beanutils.PropertyUtils;
import pl.coderslab.domain.constraints.RangeMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RangeMatchValidator implements ConstraintValidator<RangeMatch, Object> {

    private String minFieldName;
    private String maxFieldName;

    @Override
    public void initialize(RangeMatch constraintAnnotation) {
        minFieldName = constraintAnnotation.min();
        maxFieldName = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean matches = false;
        try {
            Double minValue = (Double) PropertyUtils.getProperty(value, minFieldName);
            Double maxValue = (Double) PropertyUtils.getProperty(value, maxFieldName);
            matches = maxValue > minValue;
        } catch (Exception ignore) {

        }

        return matches;
    }
}
