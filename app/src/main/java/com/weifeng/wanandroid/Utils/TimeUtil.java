package com.weifeng.wanandroid.Utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @anthor weifeng
 * @time 2018/9/25 下午5:38
 */
public class TimeUtil {

    public static String getDate(long time) {
        Date commentDate = new Date(time);
        Calendar c = Calendar.getInstance();
        c.setTime(commentDate);
        StringBuilder sb = new StringBuilder();
        int year = c.get(Calendar.YEAR);
        Date today = new Date();
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(today);
        if (year != todayCalendar.get((Calendar.YEAR))) {
            sb.append(c.get(Calendar.YEAR)).append("年");
        }
        int month = c.get(Calendar.MONTH) + 1;
        sb.append(month).append("月");

        int day = c.get(Calendar.DAY_OF_MONTH);
        sb.append(day).append("日 ");

        int hour = c.get(Calendar.HOUR_OF_DAY);
        if (hour < 10) {
            sb.append(0).append(hour).append(":");
        } else {
            sb.append(hour).append(":");
        }

        int minute = c.get(Calendar.MINUTE);
        if (minute < 10) {
            sb.append(0).append(minute);
        } else {
            sb.append(minute);
        }
        return sb.toString();
    }
}
