package com.anizzzz.product.sssweaterhouse.validation;

import com.anizzzz.product.sssweaterhouse.annotation.ValidPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
* Creating Annotation validator that is used in entity (model) ie; @Password
*/
public class PasswordValidator implements ConstraintValidator<ValidPassword,String> {
    private Pattern pattern;
    private Matcher matcher;
    private static final String PASSWORD_PATTER="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return validatePassword(password);
    }

    private boolean validatePassword(String password){
        pattern=Pattern.compile(PASSWORD_PATTER);
        matcher=pattern.matcher(password);
        return matcher.matches();
    }
}
