package com.anizzzz.product.sssweaterhouse.validation;

import com.anizzzz.product.sssweaterhouse.model.Users;
import org.springframework.lang.Nullable;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
* Creating validator to be used with @InitBinder
*/
public class UserValidator implements Validator {
    private Pattern pattern;
    private Matcher matcher;
    private static final String PASSWORD_PATTER="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,}";
    @Override
    public boolean supports(Class<?> aClass) {
        return Users.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(@Nullable Object o, Errors errors) {
        Users users =(Users)o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"username","","Username cannot be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password","","Password cannot be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"firstname","","Firstname cannot be empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"lastname","","Lastname cannot be empty");

        pattern=Pattern.compile(PASSWORD_PATTER);
        matcher=pattern.matcher(users.getPassword());
        if(!matcher.matches()){
            errors.rejectValue("password","","Password must contain atleast one uppercase, " +
                    "one number and length must be greater or equal to 6");
        }
    }
}
