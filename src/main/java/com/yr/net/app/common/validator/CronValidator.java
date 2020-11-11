package com.yr.net.app.common.validator;

import com.yr.net.app.common.annotation.IsCron;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验是否为合法的 Cron表达式
 *
 * @author dengbp
 */
public class CronValidator implements ConstraintValidator<IsCron, String> {

    @Override
    public void initialize(IsCron isCron) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
