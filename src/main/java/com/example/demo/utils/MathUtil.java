package com.example.demo.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@UtilityClass
public class MathUtil {

    public static final int BIG_DECIMAL_SCALE = 7;

    public static final int QUANTITY_SCALE = 4;

    public static final int RATE_SCALE = 2;

    private Random random = new Random();
    /**
     * To set the precision and scale in the database using "columnDefinition" parameter in @Column
     * annotation. DECIMAL(x, y) : x = precision and y = scale.
     */
    public static final String DB_DECIMAL_FORMAT = "DECIMAL(19,7)";

    private static final BigDecimal HUNDRED = new BigDecimal(100);

    public static BigDecimal percentage(BigDecimal value, BigDecimal percentage) {
        return divide(value.multiply(percentage), HUNDRED);
    }

    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
        return dividend.divide(divisor, BIG_DECIMAL_SCALE, RoundingMode.HALF_UP);
    }

    public static String getRandomNumberString() {
        
        // It will generate 6 digit random Number.
        // from 0 to 999999
        int number = random.nextInt(99999999);

        // this will convert any number sequence into 6 character.
        return String.format("%08d", number);
    }

}
