package ru.netology.TransferMoneyApp.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ValidDataCheckerImpl implements ConstraintValidator<ValidDataChecker, String> {
    @Override
    public void initialize(ValidDataChecker constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.length() < 4) {
            return false;
        }
        return doValidDataCheck(value);
    }

    boolean doValidDataCheck(String value) {
        Calendar calendar = new GregorianCalendar();
        String[] valueArr = value.split("/");
        int year = Integer.parseInt("20" + valueArr[1]);
        return Integer.parseInt(valueArr[0]) >= 1 && Integer.parseInt(valueArr[0]) <= 12 && year >= calendar.get(Calendar.YEAR);
    }
}