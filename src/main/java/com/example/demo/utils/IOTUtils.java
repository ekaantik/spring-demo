package com.example.demo.utils;

import java.time.ZonedDateTime;

public class IOTUtils {

    private IOTUtils(){}
    
    /**
     * Returns One Day Before the given date.
     * @param dateTime : date
     * @return One Day Before the given date.
     */
    public static ZonedDateTime getOneDayBefore(ZonedDateTime dateTime) {
        return dateTime.minusDays(1)
                    .withHour(23)
                    .withMinute(59)
                    .withSecond(59)
                    .withNano(0);
    }
    
    /**
     * Returns the end date after adding the specified duration (in years) to the given ZonedDateTime.
     * 
     * @param dateTime The base date and time
     * @param duration The number of years to add to the base date
     * @return The ZonedDateTime after adding the specified duration
     */
    public static ZonedDateTime getLongEndDate(ZonedDateTime dateTime, int duration) {
        return dateTime.plusYears(duration);
    }

    /**
     * Returns the current date and time as a ZonedDateTime object.
     * 
     * @return The current ZonedDateTime
     */
    public static ZonedDateTime getCurrentTime() {
        return ZonedDateTime.now();
    }

}
