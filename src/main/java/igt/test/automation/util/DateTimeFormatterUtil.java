

package igt.test.automation.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

/**
 * <p>
 * this utility class is based on {@code JODA} which gives the formatted DateTime.
 * <p>
 * <p>
 * Methods to return date and Time in specific formats.
 * <p>
 *
 * @author 
 *
 */
public final class DateTimeFormatterUtil {

    // Hide the constructor
    private DateTimeFormatterUtil(){

    }

    // Widths of the date fields
    /** Year field width. */
    private static final int YEAR_WIDTH = 4;
    /** Month field width. */
    private static final int MONTH_WIDTH = 2;
    /** Day field width. */
    private static final int DAY_WIDTH = 2;
    /** Hour field width. */
    private static final int HOUR_WIDTH = 2;
    /** Minute field width. */
    private static final int MINUTE_WIDTH = 2;
    /** Second field width. */
    private static final int SECOND_WIDTH = 2;
    /** Number thousand for covert to Milli seconds to seconds. */
    private static final int NO_THOUSAND_MILISECOND_TO_SECONDS = 1000;
    /** covert seconds to minutes. */
    private static final int NO_SIXTY_SECOND_TO_MINUTES = 60;

    /**
     * Format a date and time as YYYY-MM-DDTHH:MI.
     */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
        .appendFixedDecimal(DateTimeFieldType.year(), YEAR_WIDTH).appendLiteral("-")
        .appendMonthOfYear(MONTH_WIDTH).appendLiteral("-").appendDayOfMonth(DAY_WIDTH)
        .appendLiteral("T").appendHourOfDay(HOUR_WIDTH).appendLiteral(":")
        .appendMinuteOfHour(MINUTE_WIDTH).appendLiteral(":").appendSecondOfMinute(SECOND_WIDTH)
        .toFormatter();

    /**
     * Format a date as DD-MM-YYYY.
     */
    private static final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
        .appendDayOfMonth(DAY_WIDTH)
        .appendLiteral("-")
        .appendMonthOfYear(MONTH_WIDTH)
        .appendLiteral("-")
        .appendFixedDecimal(DateTimeFieldType.year(), YEAR_WIDTH)
        .toFormatter();


    /**
     * Format a date as DD/MM/YYYY.
     */
    private static final DateTimeFormatter DATE_FORMATTERDDMMYYYY = new DateTimeFormatterBuilder()
        .appendDayOfMonth(DAY_WIDTH)
        .appendLiteral("/")
        .appendMonthOfYear(MONTH_WIDTH)
        .appendLiteral("/")
        .appendFixedDecimal(DateTimeFieldType.year(), YEAR_WIDTH)
        .toFormatter();

    /**
     * Format a date as YYYY-MM-DD.
     */
    private static final DateTimeFormatter DATE_FORMATTER_YYYYMMDD = new DateTimeFormatterBuilder()
        .appendFixedDecimal(DateTimeFieldType.year(), YEAR_WIDTH)
        .appendLiteral("-")
        .appendMonthOfYear(MONTH_WIDTH)
        .appendLiteral("-")
        .appendDayOfMonth(DAY_WIDTH)
        .toFormatter();



    /**
     * Format a date as YYYYMMDD.
     */
    private static final DateTimeFormatter DATE_FORMATTER_YYYYMMDD_NODELIMITER = new DateTimeFormatterBuilder()
        .appendFixedDecimal(DateTimeFieldType.year(), YEAR_WIDTH)
        .appendMonthOfYear(MONTH_WIDTH)
        .appendDayOfMonth(DAY_WIDTH)
        .toFormatter();

    /**
     * Format a date as DDMMYY.
     */
    private static final DateTimeFormatter DATE_FORMATTER_DDMMYY_NODELIMITER = new DateTimeFormatterBuilder()
        .appendDayOfMonth(DAY_WIDTH)
        .appendMonthOfYear(MONTH_WIDTH)
        //.appendFixedDecimal(DateTimeFieldType.year(), SHORT_YEAR_WIDTH)
        .appendTwoDigitYear(yearAsInt(DateTimeFieldType.year()), true)
        .toFormatter();

    /**
     * Format a date as DDMMYY.
     */
    private static final DateTimeFormatter DATE_FORMATTER_YYMMDD_NODELIMITER = new DateTimeFormatterBuilder()
        .appendTwoDigitYear(yearAsInt(DateTimeFieldType.year()), true)
        .appendMonthOfYear(MONTH_WIDTH)
        .appendDayOfMonth(DAY_WIDTH)
        .toFormatter();


    /**
     * Format a time as HH:MM.
     */
    private static final DateTimeFormatter TIME_FORMATTER_HHMM = new DateTimeFormatterBuilder()
        .appendHourOfDay(HOUR_WIDTH).appendLiteral(":").appendMinuteOfHour(MINUTE_WIDTH)
        .toFormatter();

    /**
     * This returns String representation for UCP specific date/time.
     *<p>
     *<p>
     *eg. 14APR2015
     *<p>
     * @author 
     */
    private static final DateTimeFormatter DATE_UCP_FORMATTER = new DateTimeFormatterBuilder()
        .appendDayOfMonth(DAY_WIDTH)
        .appendMonthOfYearShortText()
        .appendFixedDecimal(DateTimeFieldType.year(), YEAR_WIDTH)
        .toFormatter();

    /**
     * convertStringDateToAltFormat converts a String written date between formats.
     * @param dateToConvert the string written date
     * @param fromFormat the original date format
     * @param toFormat the new date format
     * @return the converted String in the new format.
     * @throws ParseException an exception when parse has gone wrong
     */
    public static String convertStringDateToAltFormat(final String dateToConvert, final String fromFormat, final String toFormat) throws ParseException {
        SimpleDateFormat fromXml = new SimpleDateFormat(fromFormat);
        SimpleDateFormat newFormat = new SimpleDateFormat(toFormat);
        return newFormat.format(fromXml.parse(dateToConvert));
    }

    private static int yearAsInt(final DateTimeFieldType year) {
        return 0;
    }

    /**
     * This method adds the number of days you specified to the current system date and
     * returns you the String representation of modified date.
     *
     * For eg. If the current system date is 01-04-2014 then {@code addDaysToTheLocalDay(3)} returns you {@code 04-04-2014}
     *
     * @param days number of days to add to current date
     * @return string representing the date
     */
    public static String addDaysToTheLocalDay(final int days){
        //Returns a copy of this date plus the specified number of days.
        return DATE_FORMATTER.print(new LocalDate().plusDays(days));
        
    }

    /**
     * This method adds the number of days you specified to the current system date and
     * returns you the String representation of modified date.
     *
     * For eg. If the current system date is YYYY-MM-DD then {@code addDaysToTheLocalDay(3)} returns you {@code 04-04-2014}
     *
     * @param days number of days to add to current date
     * @return string representing the date
     */
    public static String addDaysToTheYYYYMMDD(final int days){
        //Returns a copy of this date plus the specified number of days.
        return DATE_FORMATTER_YYYYMMDD.print(new LocalDate().plusDays(days));
    }

    /**
     * This method adds the number of days you specified to the current system date and
     * returns you the String representation of modified date.
     *
     * For eg. If the current system date is 01-04-2014 then {@code addDaysToTheLocalDay(3)} returns you {@code 04-04-2014}
     *
     * @param days number of days to add to current date
     * @return string representing the date
     */
    public static String minusDaysFromTheLocalDay(final int days){
        //Returns a copy of this date plus the specified number of days.
        return DATE_FORMATTER.print(new LocalDate().minusDays(days));
    }

    /**
     *<p>
     *this method returns you the String representation of current date and Time in {@code YYYY-DD-MMTHH:MM:SSZ} format
     *<p>
     *<p>
     *eg. {@code 2014-03-27T16:12:01Z} <br>T - refers to time component. Z - refers to time zone.
     *<p>
     * @author 
     * @param days
     * @return String
     */
    public static String getCurrentDateTime(){
        return DATE_TIME_FORMATTER.print(new LocalDateTime());
    }

    /**
     *<p>
     *this method returns you the String representation of current date in MM-DD-YYYY format
     *<p>
     *<p>
     *eg. 30-03-2014
     *<p>
     * @author 
     * @param days
     * @return String
     */
    public static String getCurrentDate(){
        return DATE_FORMATTER.print(new LocalDate());
    }


    /**
     *<p>
     *this method returns you the String representation of current date in MM-DD-YYYY format
     *<p>
     *<p>
     *eg. 30/03/2014
     *<p>
     * @author sandhya.hooda
     * @param days
     * @return String
     */
    public static String getCurrentDateDDMMYYYY(){
        return DATE_FORMATTERDDMMYYYY.print(new LocalDate());
    }
    /**
     *<p>
     *this method returns you the String representation of current date in MM-DD-YYYY format
     *<p>
     *<p>
     *eg. 30-03-2014
     *<p>
     * @author 
     * @param days
     * @return String
     */
    public static String getCurrentDateinFormatYYYYMMDD(){
        return DATE_FORMATTER_YYYYMMDD.print(new LocalDate());
    }

    /**
     *<p>
     *This method returns you the String representation of current date in YYYYMMDD format
     *<p>
     *<p>
     *eg. YYYYMMDD
     *<p>
     * @param days
     * @return String
     */
    public static String getCurrentDateinFormatYYYYMMDDNoDelimiter(){
        return DATE_FORMATTER_YYYYMMDD_NODELIMITER.print(new LocalDate());
    }

    /**
     * getCurrentDateinFormatDDMMYYNoDelimiter.
     * @return String.
     */
    public static String getCurrentDateinFormatDDMMYYNoDelimiter(){
        return DATE_FORMATTER_DDMMYY_NODELIMITER.print(new LocalDate());
    }

    /**
     * getCurrentDateinFormatYYMMDDNoDelimiter.
     * @return String.
     */
    public static String getCurrentDateinFormatYYMMDDNoDelimiter(){
        return DATE_FORMATTER_YYMMDD_NODELIMITER.print(new LocalDate());
    }

    /**
     * This method returns String representation of modified hours for current dateTime
     * in 2014-03-27<b>T</b>16:12:01 format
     *<p>
     *<p>
     *eg. 30-03-2014
     *<p>
     * @author 
     * @param hours the hours to append
     * @return string representing the modified datetime
     */
    public static String appendHoursToCurrentDateTime(final int hours){
        return DATE_TIME_FORMATTER.print(new LocalDateTime().plusHours(hours));
    }

    /**
     * This method returns String representation of modified minutes for current dateTime.
     * @param minutes the minutes to append
     * @return string representing the modified datetime
     */
    public static String appendMinutesToCurrentDateTime(final int minutes){
        return DATE_TIME_FORMATTER.print(new LocalDateTime().plusMinutes(minutes));
    }

    /**
     * This method returns String representation of modified hours for current dateTime
     * in 2014-03-27<b>T</b>16:12:01 format
     *<p>
     *<p>
     *eg. 30-03-2014
     *<p>
     * @param hours the hours to append
     * @return string representing the modified datetime
     */
    public static String appendHoursToCurrentDate(final int hours){
        return DATE_FORMATTER.print(new LocalDateTime().plusHours(hours));
    }

    /**
     *<p>
     *this method returns String representation for UCP specific date / time
     *<p>
     *<p>
     *eg. 14APR2015
     *<p>
     * @author 
     * @return String
     */

    public static String getCurrentDateTimeForUCP() {
        return DATE_UCP_FORMATTER.print(new LocalDateTime());
    }

    /**
     * This method returns String modified time stamp for specific time stamp
     * in 16:47 format.
     * @param specificTime  String
     * @param minute  integer
     * @throws ParseException  Exception
     * @return string representing the modified datetime
     */
    public static String appendMinuteToSpecificTime(final String specificTime, final int minute)throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date d = null;
        d = df.parse(specificTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, minute);
        return df.format(cal.getTime());
    }

    /**
     *<p>
     *this method returns you the String representation of current time in {@code HH:MM} format
     *<p>
     *<p>
     *eg. {@code 16:12}
     *<p>
     * @author bhargu.singh
     * @return String
     */
    public static String getCurrentTime(){
        return TIME_FORMATTER_HHMM.print(new LocalDateTime());
    }

    /**
     * This method adds the number of days you specified to the current system date and
     * returns you the String representation of modified date.
     *
     * For eg. If the current system date is 14APR2015 then {@code addDaysToTheLocalDay(3)} returns you {@code 17APR2015}
     *
     * @param days number of days to add to current date
     * @return string representing the date
     */
    public static String addDaysToTheLocalDayForUCP(final int days){
        //Returns a copy of this date plus the specified number of days.
        return DATE_UCP_FORMATTER.print(new LocalDate().plusDays(days));
    }

    /**
     * This method minus the minutes in given dataTime.
     * @param dateTime  String
     * @param minute  integer
     * @throws ParseException  Exception
     * @return string representing the modified datetime
     */
    public static String minusMinuteToGivenDateTime(final String dateTime, final int minute)throws ParseException{
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date =  df.parse(dateTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, -minute);
        return df.format(cal.getTime());
    }

    /**
     * Method is used to find the difference in minutes between two datetime.
     * @param eventDateTime eventDateTime
     * @param displayedEventTime displayedEventTime
     * @param diffrenceRange diffrenceRange
     * @return boolean, difference acceptable or not.
     * @throws ParseException ParseException
     * @author rchoudhary
     */

    public static boolean isDiffrenceNearByInMunites(final String eventDateTime, final String displayedEventTime, final long diffrenceRange) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date1 =  df.parse(eventDateTime);
        Date date2 =  df.parse(displayedEventTime);

        long diffMs = date2.getTime() - date1.getTime();
        long diffSec = diffMs / NO_THOUSAND_MILISECOND_TO_SECONDS;
        long minutes = diffSec / NO_SIXTY_SECOND_TO_MINUTES;
        return minutes <= diffrenceRange && minutes >= -diffrenceRange;
    }
}
