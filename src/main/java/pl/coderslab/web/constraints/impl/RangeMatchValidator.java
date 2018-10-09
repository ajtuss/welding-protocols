package pl.coderslab.web.constraints.impl;

import org.apache.commons.beanutils.PropertyUtils;
import pl.coderslab.web.constraints.RangeMatch;
import pl.coderslab.service.dto.RangeDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class RangeMatchValidator implements ConstraintValidator<RangeMatch, RangeDTO> {

    private String minFieldName;
    private String maxFieldName;

    @Override
    public void initialize(RangeMatch constraintAnnotation) {
        minFieldName = constraintAnnotation.min();
        maxFieldName = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(RangeDTO value, ConstraintValidatorContext context) {
        int matches = -1;
        try {
            BigDecimal minValue = (BigDecimal) PropertyUtils.getProperty(value, minFieldName);
            BigDecimal maxValue = (BigDecimal) PropertyUtils.getProperty(value, maxFieldName);
            matches = maxValue.compareTo(minValue);
        } catch (Exception ignore) {
        }

        return matches == 1;
    }
}
