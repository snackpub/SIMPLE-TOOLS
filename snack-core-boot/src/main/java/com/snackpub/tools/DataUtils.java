package com.snackpub.tools;

import java.util.Calendar;
import java.util.Date;

/**
 * @author api
 */
public class DataUtils {

    public static Date addTime(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

}
