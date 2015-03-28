package com.softvision.botanica.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: lorand.krucz
 * Date: 5/10/13
 * Time: 3:16 PM
 * Date Time util
 */
public class DateTimeUtils {
    private static final String DATE_TIMEZONE_FORMAT = "d-MMM-yyyy hh:mm aa z";
    private static final String DATE_TIME_FORMAT = "M/d/yyyy h:mm:ss aa";
    private static final String DATE_FORMAT = "MM/dd/yyyy";

    public static Date getDateTimeFromDateTimeString(String dateString) {
        if (dateString == null) return null;
        DateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Date getDateFromDateString(String dateString) {
        if (dateString == null) return null;
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Date getDateFromDateTimeZoneString(String dateString) {
        if (dateString == null) return null;
        DateFormat formatter;
        // Android 2.3 does not recognize UTC time zone
        if (dateString.contains("UTC")) {
            dateString = dateString.replace("UTC", "GMT");
        }
        formatter = new SimpleDateFormat(DATE_TIMEZONE_FORMAT, Locale.US);
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Long getTimestampFromDateString(String dateString) {
        Date date = getDateFromDateString(dateString);
        return (date == null) ? null : date.getTime();
    }

    public static Long getTimestampFromDateTimeString(String dateTimeString) {
        Date date = getDateTimeFromDateTimeString(dateTimeString);
        return (date == null) ? null : date.getTime();
    }

    public static String getDateTimeStringFromTimestamp(Long timestamp) {
        if (timestamp == null) return null;
        DateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
        return formatter.format(new Date(timestamp));
    }

    public static String getDateStringFromTimestamp(Long timestamp) {
        if (timestamp == null) return null;
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.format(new Date(timestamp));
    }

    public static int getUtcOffsetMinutes() {
        TimeZone tz = TimeZone.getDefault();
        Date now = new Date();
        return tz.getOffset(now.getTime()) / 60000;
    }

    public static boolean isDaylightSavingsTime() {
        return TimeZone.getDefault().inDaylightTime(new Date());
    }

    public static String validateStringBirthDate(String date) {
        if (date == null) {
            return null;
        }
        // Direct use of Pattern:
        Pattern p1 = Pattern.compile("(\\d{2})/(\\d{2})/(\\d{4})");
        Pattern p2 = Pattern.compile("(\\d{2})(\\d{2})(\\d{4})");
        Matcher m1 = p1.matcher(date);
        Matcher m2 = p2.matcher(date);
        Matcher mm;
        int m = 0, d = 0, y = 0;
        if (m1.matches()) {
            mm = m1;
        } else if (m2.matches()) {
            mm = m2;
        } else {
            return null;
        }

        try {
            m = Integer.parseInt(mm.group(1));
            d = Integer.parseInt(mm.group(2));
            y = Integer.parseInt(mm.group(3));
        } catch (NumberFormatException nfe) {
            return null;
        }

        if ((m > 0) && (m < 13) && (d > 0) && (d < 32) && (y > 1000) && (y < 3000)) {
            return String.format("%02d/%02d/%04d", m, d, y);
        } else {
            return null;
        }
    }
}
