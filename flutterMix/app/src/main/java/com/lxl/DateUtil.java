package com.lxl;

import java.lang.Exception;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


/**
 * @author xinyh
 * @version1.0
 * @creat date 2012-3-27
 */
public class DateUtil {
    public static final String TIMEZONE_CN = "Asia/Shanghai";

    /**
     * ********************SIMPLEFORMATTYPE对应的字串*********************
     */
    /**
     * SIMPLEFORMATTYPE1 对应类型：yyyyMMddHHmmss
     */
    public final static String SIMPLEFORMATTYPESTRING1 = "yyyyMMddHHmmss";

    /**
     * SIMPLEFORMATTYPE2 对应的类型：yyyy-MM-dd HH:mm:ss
     */
    public final static String SIMPLEFORMATTYPESTRING2 = "yyyy-MM-dd HH:mm:ss";

    /**
     * SIMPLEFORMATTYPE3 对应的类型：yyyy-M-d HH:mm:ss
     */
    public final static String SIMPLEFORMATTYPESTRING3 = "yyyy-M-d HH:mm:ss";

    /**
     * SIMPLEFORMATTYPE4对应的类型：yyyy-MM-dd HH:mm
     */
    public final static String SIMPLEFORMATTYPESTRING4 = "yyyy-MM-dd HH:mm";

    /**
     * SIMPLEFORMATTYPE5 对应的类型：yyyy-M-d HH:mm
     */
    public final static String SIMPLEFORMATTYPESTRING5 = "yyyy-M-d HH:mm";

    /**
     * SIMPLEFORMATTYPE6对应的类型：yyyyMMdd
     */
    public final static String SIMPLEFORMATTYPESTRING6 = "yyyyMMdd";

    /**
     * SIMPLEFORMATTYPE7对应的类型：yyyy-MM-dd
     */
    public final static String SIMPLEFORMATTYPESTRING7 = "yyyy-MM-dd";

    /**
     * SIMPLEFORMATTYPE8对应的类型： yyyy-M-d
     */
    public final static String SIMPLEFORMATTYPESTRING8 = "yyyy-M-d";

    /**
     * SIMPLEFORMATTYPE9对应的类型：yyyy年MM月dd日
     */
    public final static String SIMPLEFORMATTYPESTRING9 = "yyyy年MM月dd日";

    /**
     * SIMPLEFORMATTYPE10对应的类型：yyyy年M月d日
     */
    public final static String SIMPLEFORMATTYPESTRING10 = "yyyy年M月d日";

    /**
     * nn'd对应的类型：M月d日
     */
    public final static String SIMPLEFORMATTYPESTRING11 = "M月d日";

    /**
     * SIMPLEFORMATTYPE12对应的类型：HH:mm:ss
     */
    public final static String SIMPLEFORMATTYPESTRING12 = "HH:mm:ss";

    /**
     * SIMPLEFORMATTYPE13对应的类型：HH:mm
     */
    public final static String SIMPLEFORMATTYPESTRING13 = "HH:mm";
    /**
     * SIMPLEFORMATTYPE7对应的类型：yyyy-MM-dd
     */
    public final static String SIMPLEFORMATTYPESTRING14 = "yyyy/MM/dd";

    /**
     * SIMPLEFORMATTYPE15对应的类型：MM月dd日
     */
    public final static String SIMPLEFORMATTYPESTRING15 = "MM月dd日";

    /**
     * SIMPLEFORMATTYPE16对应的类型：yyyy/MM/dd HH:mm:ss
     */
    public final static String SIMPLEFORMATTYPESTRING16 = "yyyy/MM/dd HH:mm:ss";

    /**
     * SIMPLEFORMATTYPE17对应的类型：MM-dd
     */
    public final static String SIMPLEFORMATTYPESTRING17 = "MM-dd";

    public final static String SIMPLEFORMATTYPESTRING18 = "yyyy-MM";

    public final static String SIMPLEFORMATTYPESTRING19 = "yy/MM/dd";

    public final static String SIMPLEFORMATTYPESTRING20 = "yyyy";
    public final static String SIMPLEFORMATTYPESTRING21 = "MM/YY";


    // =====================================End===================================

    /**
     * 获取当前日期
     *
     * @return Calendar
     */
    public static Calendar getCurrentCalendar() {
        Calendar currentCalendar = Calendar.getInstance();
        return currentCalendar;
    }

    /**
     * 获取手机当前日期
     *
     * @return Calendar
     */
    public static Calendar getLocalCalendar() {
        Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE_CN));
        return localCalendar;
    }

    //    SIMPLEFORMATTYPESTRING13
    public static String calendar2Time(long time, String type) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(time);
        return calendar2Time(instance, type);
    }

    //    SIMPLEFORMATTYPESTRING13
    public static String calendar2Time(Calendar calendar, String type) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(type);
        TimeZone timeZone = TimeZone.getTimeZone(TIMEZONE_CN);
        dateFormat.setTimeZone(timeZone);
        String str = dateFormat.format(calendar.getTime());
        return str;
    }


    /**
     * 将日期字串转为 Calendar ,dateStr需超过8位且不能为空,否则返回null
     *
     * @param dateStr
     * @return Calendar
     */
    public static Calendar getCalendarByDateStr(String dateStr) {
        if (StringUtil.emptyOrNull(dateStr) || dateStr.length() < 8) {
            return null;
        }
        Calendar calendar = getLocalCalendar();
        try {
            int year = Integer.valueOf(dateStr.substring(0, 4));
            int month = Integer.valueOf(dateStr.substring(4, 6));
            int day = Integer.valueOf(dateStr.substring(6, 8));
            calendar.set(year, month - 1, day, 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        } catch (Exception e) {
        }
        return calendar;
    }

    /**
     * 将时间字串转为 Calendar dateStr小于8时返回null，不足14位补0
     *
     * @param dateStr
     * @return
     */
    public static Calendar getCalendarByDateTimeStr(String dateStr) {
        if (StringUtil.emptyOrNull(dateStr) || dateStr.length() < 8) {
            return null;
        }
        while (dateStr.length() < 14) {
            dateStr += "0";
        }
        Calendar calendar = getLocalCalendar();
        int year = StringUtil.toInt(dateStr.substring(0, 4));
        int month = StringUtil.toInt(dateStr.substring(4, 6));
        int day = StringUtil.toInt(dateStr.substring(6, 8));
        int hour = StringUtil.toInt(dateStr.substring(8, 10));
        int min = StringUtil.toInt(dateStr.substring(10, 12));
        int second = 0;
        if (dateStr.length() >= 14) {
            second = StringUtil.toInt(dateStr.substring(12, 14));
        }
        calendar.set(year, month - 1, day, hour, min, second);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }


    /**
     * 时间字符串格式转换 20101001082000 -> 2010-10-01 08:20
     *
     * @param timeStr
     * @return
     */
    public static String formatDateTime2String(String timeStr) {
        Calendar calendar = YYYYMMDD2calendar(timeStr);
        String s = calendar2YYYY_MM_DD(calendar);
        return s;
    }

    public static Calendar dateStr2calendar(String dateStr,String type) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(type);
        TimeZone timeZone = TimeZone.getTimeZone(TIMEZONE_CN);
        dateFormat.setTimeZone(timeZone);
        try {
            Date parse = dateFormat.parse(dateStr);
            Calendar instance = Calendar.getInstance();
            instance.setTime(parse);
            return instance;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Calendar.getInstance();
    }


    public static Calendar YYYYMMDD2calendar(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(SIMPLEFORMATTYPESTRING6);
        TimeZone timeZone = TimeZone.getTimeZone(TIMEZONE_CN);
        dateFormat.setTimeZone(timeZone);
        try {
            Date parse = dateFormat.parse(dateStr);
            Calendar instance = Calendar.getInstance();
            instance.setTime(parse);
            return instance;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Calendar.getInstance();
    }


    public static String calendar2YYYY_MM_DD(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(SIMPLEFORMATTYPESTRING9);
        TimeZone timeZone = TimeZone.getTimeZone(TIMEZONE_CN);
        dateFormat.setTimeZone(timeZone);
        String str = dateFormat.format(calendar.getTime());
        return str;
    }

    /**
     * 获取当前日期 8位
     *
     * @return String
     */
    public static String getCurrentDate() {
        Calendar currentCalendar = getCurrentCalendar();
        SimpleDateFormat dateFormat = new SimpleDateFormat(SIMPLEFORMATTYPESTRING6);
        TimeZone timeZone = TimeZone.getTimeZone(TIMEZONE_CN);
        dateFormat.setTimeZone(timeZone);
        String str = dateFormat.format(currentCalendar.getTime());
        return str;
    }

    /**
     * 获取当前日期 8位
     *
     * @return String
     */
    public static String getCurrentTime(String format) {
        Calendar currentCalendar = getCurrentCalendar();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        TimeZone timeZone = TimeZone.getTimeZone(TIMEZONE_CN);
        dateFormat.setTimeZone(timeZone);
        String str = dateFormat.format(currentCalendar.getTime());
        return str;
    }

    /**
     * 获取当前日期 8位
     *
     * @return String
     */
    public static String getCurrentTime() {
        return getCurrentTime(SIMPLEFORMATTYPESTRING3);
    }

}
