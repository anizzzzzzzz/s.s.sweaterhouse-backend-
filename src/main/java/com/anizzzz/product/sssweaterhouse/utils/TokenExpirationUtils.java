package com.anizzzz.product.sssweaterhouse.utils;

import java.util.Calendar;
import java.util.Date;

public class TokenExpirationUtils {
    public static Date setTokenExpirationDate(int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public static boolean isVerificationTokenExpired(Date expiryTime){
        Calendar cal=Calendar.getInstance();
        return (expiryTime.getTime() - cal.getTime().getTime()) <= 0;
    }

}
