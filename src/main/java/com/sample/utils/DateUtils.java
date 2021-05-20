package com.sample.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtils {
	public static final long DAY = 24 * 60 * 60 * 1000L;

	private static final Map<String, DateFormat> DFS = new HashMap<>();

	private static final Log log = LogFactory.getLog(DateUtils.class);

	private DateUtils() {
	}

	public static DateFormat getFormat(String pattern) {
		DateFormat format = DFS.get(pattern);
		if (format == null) {
			format = new SimpleDateFormat(pattern);
			DFS.put(pattern, format);
		}
		return format;
	}

	public static Date parse(String source, String pattern) {
		if (source == null) {
			return null;
		}
		Date date;
		try {
			date = getFormat(pattern).parse(source);
		} catch (ParseException e) {
			if (log.isDebugEnabled()) {
				log.debug(source + " doesn't match " + pattern);
			}
			return null;
		}
		return date;
	}
    
	public static String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		return getFormat(pattern).format(date);
	}
    
	public static boolean isValid(int year, int month, int day) {
		if (month > 0 && month < 13 && day > 0 && day < 32) {
			// month of calendar is 0-based
			int mon = month - 1;
			Calendar calendar = new GregorianCalendar(year, mon, day);
			if (calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH) == mon
					&& calendar.get(Calendar.DAY_OF_MONTH) == day) {
				return true;
			}
		}
		return false;
	}

	private static Calendar convert(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar;
	}
    
	public static Date yearOffset(Date date, int offset) {
		return offsetDate(date, Calendar.YEAR, offset);
	}
    
	public static Date monthOffset(Date date, int offset) {
		return offsetDate(date, Calendar.MONTH, offset);
	}
    
	public static Date dayOffset(Date date, int offset) {
		return offsetDate(date, Calendar.DATE, offset);
	}
    
	public static Date offsetDate(Date date, int field, int offset) {
		Calendar calendar = convert(date);
		calendar.add(field, offset);
		return calendar.getTime();
	}
    
	public static Date firstDay(Date date) {
		Calendar calendar = convert(date);
		calendar.set(Calendar.DATE, 1);
		return calendar.getTime();
	}
    
	public static Date lastDay(Date date) {
		Calendar calendar = convert(date);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		return calendar.getTime();
	}

	public static int dayDiff(Date date1, Date date2) {
		long diff = date1.getTime() - date2.getTime();
		return (int) (diff / DAY);
	}

	public static Date gethms(Date tt) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = sdf.format(tt) + " 00:00:00";
		try {
			Date date = sdf2.parse(s);
			return date;
		} catch (ParseException e) {
			return null;
		}
	}
    
	public static String timeStamp2Date(Long seconds, String format) {
		if (seconds == null ) {
			return "";
		}
		if (format == null || format.isEmpty()) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		seconds = seconds * 1000;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(seconds));
	}
    
	public static String date2TimeStamp(String date_str, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return String.valueOf(sdf.parse(date_str).getTime() / 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static Long timeStamp() {
		long time = System.currentTimeMillis();
		Long t =time / 1000;
		return t;
	}

	public static Timestamp currentTimeStamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static Timestamp defaultTimeStamp() {
		LocalDate defaultDate = LocalDate.of(1900,1,1);
		return Timestamp.valueOf(defaultDate.atStartOfDay());
	}

	public static Timestamp toTimeStamp(Date val) {
		return new Timestamp(val.getTime());
	}

	public static java.sql.Date toSqlDate(Timestamp ts) {
		return new java.sql.Date(ts.getTime());
	}

	public static java.util.Date toUtilDate(Timestamp ts) {
		return new java.util.Date(ts.getTime());
	}

	public static LocalDateTime toLocalDateTime(Date dateToConvert) {
		return Instant.ofEpochMilli(dateToConvert.getTime())
			.atZone(ZoneId.systemDefault())
			.toLocalDateTime();
	}

	public static LocalDateTime toLocalDateTime(Timestamp ts) {
		return LocalDateTime.ofInstant(ts.toInstant(), ZoneId.systemDefault());
	}
    
	public static Date string2date(String date, String format) {
		SimpleDateFormat sdf =   new SimpleDateFormat( format );
		Date dater = null;
		try {
			dater = sdf.parse(date);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return dater;
	}
	
	public static String getCurrentDateString(String format){
		return getString(new Date(), format);
	}
	
	public static String getString(Date date, String format){
		if(format == null) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		 SimpleDateFormat formatter = new SimpleDateFormat(format);
		 String dateString = formatter.format(date);
		 return dateString;
	}

	public static Timestamp addMinutes(Timestamp ts, int minutes) {
		ts.setTime(ts.getTime() + TimeUnit.MINUTES.toMillis(minutes));
		return ts;
	}

}
