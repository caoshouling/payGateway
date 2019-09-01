package ins.platform.kit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.UserException;


public class DateKit {

	public final static String YEAR_TO_DAY = "yyyy-MM-dd";
	public final static String YEAR_TO_SECOND = "yyyy-MM-dd HH:mm:ss";
	public final static String HOUR_TO_SECOND = "HH:mm:ss";

	public final static int YEAR = 0;// 年
	public final static int MONTH = 1;// 月
	public final static int DAY_OF_MONTH = 2;// 日
	public final static int HOUR_OF_DAY = 3;// 时
	public final static int MINUTE = 4;// 分
	public final static int SECOND = 5;// 秒

	
	/**
	 *  获取月份差，不满一个月也才算一月 
	 * @param startDate
	 * @param intStartHour
	 * @param endDate
	 * @param intEndHour
	 * @return
	 * @throws Exception
	 */
	public static int getMonthMinus(Date startDate, int intStartHour,Date endDate, int intEndHour) throws Exception {
		int intMonth = 0;
		
		LocalDate localDate_startDate = 	DateKit.DateToLocalDate(startDate);
		LocalDate localDate_endDate = 	DateKit.DateToLocalDate(endDate);
		
		intMonth = (localDate_endDate.getYear() - localDate_startDate.getYear()) * 12 + 
				(localDate_endDate.getMonthValue() - localDate_startDate.getMonthValue());
		

		if(localDate_endDate.getDayOfMonth() - localDate_startDate.getDayOfMonth() > 0 
			|| (localDate_endDate.getDayOfMonth() == localDate_startDate.getDayOfMonth()
			 && intEndHour > intStartHour)) {
			intMonth++;
		}
		return intMonth;
	}
	/**
	 * @desc 比较日期是否相等
	 * @param String
	 *            strFirstDate 比较的第一个日期 格式为yyyy-MM-DD或者yyyy/MM/DD
	 * @param String
	 *            strSecondDate 比较的第二个日期 格式为yyyy-MM-DD或者yyyy/MM/DD
	 * @return int intReturn 1 （First）大于（second）、0 相等、-1 （First）小于（second）
	 * @throws ParseException
	 */
	public static int compareDate(String strFirstDate, String strSecondDate) throws ParseException {
		java.util.Date firstDate = null;
		java.util.Date secondDate = null;
		int intReturn = 0;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		StringUtils.replace(strFirstDate, "/", "-"); // 将“/”的分隔符替换为“-”
		StringUtils.replace(strSecondDate, "/", "-");
		firstDate = format.parse(strFirstDate);
		secondDate = format.parse(strSecondDate);
		if (firstDate.after(secondDate)) {
			intReturn = 1;
		} else if (firstDate.before(secondDate)) {
			intReturn = -1;
		} else if (firstDate.equals(secondDate)) {
			intReturn = 0;
		}
		return intReturn;

	}

	/**
	 * 比较日期是否相等 （转换成日期yyyy-MM-dd来比较）
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return
	 * @throws Exception
	 */
	public static int compareDate(Date firstDate, Date secondDate) {

		int intReturn = 0;

		LocalDate firstLocalDate = DateToLocalDate(firstDate);
		LocalDate secondLocalDate = DateToLocalDate(secondDate);

		if (firstLocalDate.isAfter(secondLocalDate)) {
			intReturn = 1;
		} else if (firstLocalDate.isBefore(secondLocalDate)) {
			intReturn = -1;
		} else if (firstLocalDate.isEqual(secondLocalDate)) {
			intReturn = 0;
		}
		return intReturn;
	}
	/**
	 * 转换成yyyy-MM-dd HH-mm-ss 格式
	 * @param date
	 * @param datetime
	 * @return
	 */
	public static String transDateTime(Date date,String datetime) {
		if(date == null) {
			return "";
		}else {
			String dateDay = new SimpleDateFormat("yyyy-MM-dd").format(date);
			if(StringUtils.isEmpty(datetime)) {
				dateDay = dateDay + " 00:00:00";
			}else {
				dateDay= dateDay + " " + datetime;
			}
			return dateDay;
		}
	}
	/**
	 * 转换成yyyy-MM-dd HH-mm-ss 格式
	 * @param date
	 * @param dateHour
	 * @param dateMinute
	 * @return
	 */
	public static String transDateTime2(Date date,Double dateHour,Double dateMinute) {
		if (date == null) {
			return "";
		}else {
			String dateTime = "";
			dateTime = new SimpleDateFormat("yyyy-MM-dd").format(date);
			String hour = String.valueOf((int)ChgDataKit.nullToZero(dateHour));
			String minute = String.valueOf((int)ChgDataKit.nullToZero(dateMinute));
			if (hour == null) {
				hour = "00";
			}
			else if (hour.length() == 1) {
				hour = " 0" +hour;
			}
			
			if (StringUtils.isEmpty(minute)) {
				minute = "00";
			}else if(minute.length() == 1) {
				minute = "0" + minute;
			}
			return dateTime + " " + hour + ":" + minute + ":" + "00";
		}
	}
	
	public static Date transStr1(Date date,String datetime) throws ParseException {
		String dateStr = DateKit.transDateTime(date, datetime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date2 = sdf.parse(dateStr);
		return date2;
	}
	public static Date transStr2(Date date,Double dateHour,Double dateMinute) throws ParseException {
		String dateStr = DateKit.transDateTime2(date, dateHour,dateMinute);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date2 = sdf.parse(dateStr);
		return date2;
	}
	

	/**
	 * 转换成yyyyMMddHHmm格式
	 * 
	 * @param dateString
	 * @return
	 */
	public static String transDate12(Date date, String hourString, String minuteString) {
		if (date == null) {
			return "";
		} else {
			String dateDay = new SimpleDateFormat("yyyyMMdd").format(date);
			if (StringUtils.isEmpty(hourString)) {
				hourString = "00";
			}else if(hourString.indexOf(".") > -1){
				hourString = hourString.substring(0,hourString.indexOf("."));
			}
			if (StringUtils.isEmpty(minuteString)) {
				minuteString = "00";
			}
			if (hourString.length() == 1) {
				hourString = "0" + hourString;
			}
			if (minuteString.length() == 1) {
				minuteString = "0" + minuteString;
			}
			return dateDay += hourString + minuteString;
		}
	}
	
	public static String transDate12_2359(Date date, String hourString, String minuteString) {
		if (date == null) {
			return "";
		} else {
			String dateDay = new SimpleDateFormat("yyyyMMdd").format(date);
			if (StringUtils.isEmpty(hourString)) {
				hourString = "00";
			}else if(hourString.indexOf(".") > -1){
				hourString = hourString.substring(0,hourString.indexOf("."));
			}
			if (StringUtils.isEmpty(minuteString)) {
				minuteString = "00";
			}
			if (hourString.length() == 1) {
				hourString = "0" + hourString;
			}
			if (minuteString.length() == 1) {
				minuteString = "0" + minuteString;
			}
			if(hourString.equals("24")) {
				hourString = "23";
				minuteString = "59";
			}
			return dateDay += hourString + minuteString;
		}
	}
 
    /**
     * 转换成yyyyMMddHH格式
     * @param date
     * @param hourString
     * @return
     */
	public static String transDate10(Date date, String hourString) {
		if (date == null) {
			return "";
		} else {
			String dateDay = new SimpleDateFormat("yyyyMMdd").format(date);
			if (StringUtils.isEmpty(hourString)) {
				hourString = "00";
			}

			if (hourString.length() == 1) {
				hourString = "0" + hourString;
			}
			return dateDay += hourString;
		}
	}

	/**
	 * 转换成yyyyMMddHH格式
	 * 
	 * @param date
	 * @return
	 */
	public static String transDate10(Date date) {
		if (date == null) {
			return "";
		} else {
			return new SimpleDateFormat("yyyyMMddHH").format(date);
		}
	}

	/**
	 * 转换成yyyyMMdd格式
	 * 
	 * @param dateString
	 * @return
	 */
	public static String transDate8(Date date) {
		if (date == null) {
			return "";
		} else {
			return new SimpleDateFormat("yyyyMMdd").format(date);
		}
	}

	/**
	 * 获取年
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
	    if(date != null){
	    	return DateToLocalDate(date).getYear();
	    }
	    return 0;
	}

	/**
	 * 获取月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		if(date!=null){
			return DateToLocalDate(date).getMonthValue();
		}
		return 0;
	}

	/**
	 * 获取日
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfMonth(Date date) {
		if(date!=null){
			return DateToLocalDate(date).getDayOfMonth();
		}
		return 0;

	}
	/**
	 * 获取小时
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
	    return DateToLocalDateTime(date).getHour();
	}
	
	/**
	 * java.util.Date --> java.time.LocalDateTime 包含日期和时间
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDateTime DateToLocalDateTime(Date date) {
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		return localDateTime;
	}

	/**
	 * 获取只包含年月日的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getYearToDate(Date date) {
		LocalDate localdate = DateToLocalDate(date);
		return LocalDateToDate(localdate);
	}
	/**
	 * 获取只包含年月日的日期
	 * @param date
	 * @return
	 */
	public static Date nowDate(){
		return LocalDateToDate(LocalDate.now());
	}
	
	/**
	 * 传送年、月、日 获取日期Date实例
	 * 
	 * @param year
	 *            4位年
	 * @param month
	 *            1-12
	 * @param dayOfMonth
	 *            1-31
	 * @return
	 */
	public static Date getDate(int year, int month, int dayOfMonth) {

		LocalDate localdate = LocalDate.of(year, month, dayOfMonth);
		return LocalDateToDate(localdate);
	}
	/**
	 * 传送日期，获取年、月、日、时、分、秒的Date
	 * 
	 * @param year
	 *            4位年
	 * @param month
	 *            1-12
	 * @param dayOfMonth
	 *            1-31
	 * @return
	 */
	public static Date getDate_YearToSeconds(Date date) {
		
		LocalDateTime localDateTime = DateToLocalDateTime(date);
	
		LocalDateTime localDateTime_result = LocalDateTime.of(
				localDateTime.getYear(), 
				localDateTime.getMonthValue()
				,localDateTime.getDayOfMonth(),
				localDateTime.getHour(),
				localDateTime.getMinute(),
				localDateTime.getSecond());
		
		return DateKit.LocalDateTimeToDate(localDateTime_result);
	}
	/**
	 * 传送年、月、日 、时、分、秒获取日期Date实例
	 * 
	 * @param year
	 *            4位年
	 * @param month
	 *            1-12
	 * @param dayOfMonth
	 *            1-31
	 * @return
	 */
	public static Date getDateTime(int year, int month, int dayOfMonth, int hour, int minute, int second) {

		LocalDateTime localDateTime_result = LocalDateTime.of(year, month, dayOfMonth, hour, minute, second);

		return DateKit.LocalDateTimeToDate(localDateTime_result);
	}
	/**
	 * 时间加法 : 计算完后，不影响originDate值
	 * 
	 * @param originDate
	 * @param count
	 * @param type
	 * @return
	 */
	public static Date plusDateTime(Date originDate, int count, int type) {

		LocalDateTime localdateTime = DateToLocalDateTime(originDate);

		LocalDateTime localdateTime_result = DateToLocalDateTime(originDate);

		if (type == YEAR) {
			localdateTime_result = localdateTime.plusYears(count);

		} else if (type == MONTH) {
			localdateTime_result = localdateTime.plusMonths(count);

		} else if (type == DAY_OF_MONTH) {
			localdateTime_result = localdateTime.plusDays(count);

		} else if (type == HOUR_OF_DAY) {
			localdateTime_result = localdateTime.plusHours(count);

		} else if (type == MINUTE) {
			localdateTime_result = localdateTime.plusMinutes(count);

		} else if (type == SECOND) {

			localdateTime_result = localdateTime.plusSeconds(count);
		}

		return LocalDateTimeToDate(localdateTime_result);
	}

	/**
	 * 时间减法 : 计算完后，不影响originDate值
	 * 
	 * @param originDate
	 * @param count
	 * @param type
	 * @return
	 */
	public static Date minusDateTime(Date originDate, int count, int type) {

		LocalDateTime localdateTime = DateToLocalDateTime(originDate);

		LocalDateTime localdateTime_result = DateToLocalDateTime(originDate);

		if (type == YEAR) {
			localdateTime_result = localdateTime.minusYears(count);

		} else if (type == MONTH) {
			localdateTime_result = localdateTime.minusMonths(count);

		} else if (type == DAY_OF_MONTH) {
			localdateTime_result = localdateTime.minusDays(count);

		} else if (type == HOUR_OF_DAY) {
			localdateTime_result = localdateTime.minusHours(count);

		} else if (type == MINUTE) {
			localdateTime_result = localdateTime.minusMinutes(count);

		} else if (type == SECOND) {

			localdateTime_result = localdateTime.minusSeconds(count);
		}

		return LocalDateTimeToDate(localdateTime_result);
	}

	/**
	 * java.util.Date --> java.time.LocalDate 只包含日期
	 * 
	 * @param date
	 */
	public static LocalDate DateToLocalDate(Date date) {
		if (!ChgDataKit.isEmpty(date)) {
			Instant instant = date.toInstant();
			ZoneId zone = ZoneId.systemDefault();
			LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
			return localDateTime.toLocalDate();
		}
		return null;
	}

	/**
	 * java.util.Date --> java.time.LocalTime 只包含时间
	 * 
	 * @param date
	 * @return
	 */
	public static LocalTime DateToLocalTime(Date date) {
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		return localDateTime.toLocalTime();
	}

	/**
	 * java.time.LocalDateTime --> java.util.Date
	 * 
	 * @param localDateTime
	 * @return
	 */
	public static Date LocalDateTimeToDate(LocalDateTime localDateTime) {
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zone).toInstant();
		return Date.from(instant);
	}

	/**
	 * java.time.LocalDate --> java.util.Date
	 * 
	 * @param localDate
	 * @return
	 */
	public static Date LocalDateToDate(LocalDate localDate) {
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
		return Date.from(instant);

	}

	/**
	 *  java.time.LocalDate localDate + java.time.LocalTime --> java.util.Date
	 * java.time.LocalTime --> java.util.Date
	 * 
	 * @return
	 */
	public static Date LocalTimeToDate(LocalDate localDate, LocalTime localTime) {
		LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zone).toInstant();
		return Date.from(instant);
	}
	
	/**
	 *  获取日期加小时之后的时间
	 * @param date  yyyy-mm-dd
	 * @param hour int
	 * @return
	 */
	public static Date getDate(Date date,int hour,int minute,int second) {
		LocalDate localDate = DateKit.DateToLocalDate(date);
		if(hour == 24){
			localDate = localDate.plusDays(1);
			hour = 0;
		}
		LocalTime localTime = LocalTime.of(hour, minute, second);
	    return LocalTimeToDate(localDate,localTime);
	}
	/** 

	/**
	 * 获取yyyy-MM-dd HH:mm:ss格式的时间字符串
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getFormatedDateStr(Date dateTime, String formatParteen) {
		if (dateTime != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(formatParteen);
			String dateString = formatter.format(dateTime);
			return dateString;
		}
		return "";
	}

	/**
	 * 获取yyyy-MM-dd HH:mm:ss
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateTimeStr(Date dateTime) {
		return getFormatedDateStr(dateTime, YEAR_TO_SECOND);
	}

	/**
	 * 获取yyyy-MM-dd格式的时间字符串
	 * 
	 * @return返回字符串格式 yyyy-MM-dd
	 */
	public static String getDateStr(Date date) {
		if (!ChgDataKit.isEmpty(date)) {
			return getFormatedDateStr(date, YEAR_TO_DAY);
		}
		return "";

	}

	/**
	 * 使用参数Format将字符串转为Date
	 * 
	 * @throws ParseException
	 */
	public static Date parse(String strDate, String pattern) throws ParseException {
		if(StringUtils.isBlank(strDate)){
			return null;
		}
		if(strDate.length() > pattern.length()){
			strDate = strDate.substring(0, pattern.length());
		}
		return  new SimpleDateFormat(pattern).parse(strDate);
	}

	/**
	 * 获取HH:mm:ss格式的时间字符串
	 * 
	 * @param date
	 * @returnHH:mm:ss格式的时间字符串
	 */
	public static String getTimeStr(Date date) {
		return getFormatedDateStr(date, HOUR_TO_SECOND);
	}

	/**
	 * 根据两个日期,获取其相差的月份(不满一月不算)
	 * 
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	public static int getDiffFullMonth(Date startDay, Date endDay) {
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		startCalendar.setTime(startDay);
		endCalendar.setTime(endDay);

		int startYear = startCalendar.get(Calendar.YEAR);
		int startMonth = startCalendar.get(Calendar.MONTH);
		int startDate = startCalendar.get(Calendar.DAY_OF_MONTH);

		int endYear = endCalendar.get(Calendar.YEAR);
		int endMonth = endCalendar.get(Calendar.MONTH);
		int endDate = endCalendar.get(Calendar.DAY_OF_MONTH);

		if (endDate - startDate > 0) {
			return (endYear - startYear) * 12 + (endMonth - startMonth);
		} else {
			return (endYear - startYear) * 12 + (endMonth - startMonth) - 1;
		}
	}

	/**
	 * 根据两个日期,获取其相差的月份(超过一天就算一月)
	 * 
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	public static int getDiffFullMonthUpper(Date startDay, Date endDay) {
		if (!ChgDataKit.isEmpty(startDay) && !ChgDataKit.isEmpty(endDay)) {
			Calendar startCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			startCalendar.setTime(startDay);
			endCalendar.setTime(endDay);

			int startYear = startCalendar.get(Calendar.YEAR);
			int startMonth = startCalendar.get(Calendar.MONTH);
			int startDate = startCalendar.get(Calendar.DAY_OF_MONTH);

			int endYear = endCalendar.get(Calendar.YEAR);
			int endMonth = endCalendar.get(Calendar.MONTH);
			int endDate = endCalendar.get(Calendar.DAY_OF_MONTH);

			if (endDate - startDate > 0) {
				return (endYear - startYear) * 12 + (endMonth - startMonth) + 1;
			} else {
				return (endYear - startYear) * 12 + (endMonth - startMonth);
			}
		}
		return 0;

	}

	public static String fmtChg(String input, String format) {
		SimpleDateFormat formatInstance = new SimpleDateFormat(format);
		try {
			formatInstance.parse(input);
			return input;
		} catch (Exception e) {
		}
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(input);
			return formatInstance.format(date);
		} catch (Exception e) {
		}
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(input);
			return formatInstance.format(date);
		} catch (Exception e) {
		}
		try {
			Date date = new SimpleDateFormat("yyyyMMddHH").parse(input);
			return formatInstance.format(date);
		} catch (Exception e) {
		}
		try {
			Date date = new SimpleDateFormat("yyyyMMdd").parse(input);
			return formatInstance.format(date);
		} catch (Exception e) {
		}
		return input;
	}

	public static Date parse(String dateStr) {
		if (ChgDataKit.isEmptyStr(dateStr)) {
			return null;
		} else {
			dateStr = dateStr.replace("-", "").replace("/", "").replace(" ", "").replace(":", "");
		}
		SimpleDateFormat sdf = null;
		Date date = null;
		try {
			if (dateStr.length() >= 14) {
				sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				date = sdf.parse(dateStr);
				return new Date(date.getTime());
			}

		} catch (Exception e) {
		}

		try {
			if (dateStr.length() >= 12) {
				sdf = new SimpleDateFormat("yyyyMMddHHmm");
				date = sdf.parse(dateStr);
				return new Date(date.getTime());
			}

		} catch (Exception e) {
		}
		try {
			if (dateStr.length() >= 10) {
				sdf = new SimpleDateFormat("yyyyMMddHH");
				date = sdf.parse(dateStr);
				return new Date(date.getTime());
			}

		} catch (Exception e) {
		}
		try {
			if (dateStr.length() >= 8) {
				sdf = new SimpleDateFormat("yyyyMMdd");
				date = sdf.parse(dateStr);
				return new Date(date.getTime());
			}

		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 获取去掉时分秒的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getSimpleDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s = sdf.format(date);
		Date simpleDate = new Date();
		try {
			simpleDate = sdf.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return simpleDate;
	}

	/**
	 * 计算两个日期之间的天数差
	 * 
	 * @param iStartDate
	 *            起始日期
	 * @param iStartHour
	 *            起始小时
	 * @param iEndDate
	 *            终止日期
	 * @param iEndHour
	 *            终止小时
	 */
	public static int getDiffDay(Date startDate, int startHour, Date endDate, int endHour) {
		startDate = getSimpleDate(startDate); // 不计时分秒
		endDate = getSimpleDate(endDate);
		int intDiffDay = (int) ((endDate.getTime() - startDate.getTime()) / 86400000l) + 1;

		if (endHour <= startHour) {
			if (startHour == 24 && endHour == 0)
				intDiffDay = intDiffDay - 2;
			else
				intDiffDay = intDiffDay - 1;
		}
		return intDiffDay;
	}

	/**
	 * 计算两个日期之间的天数差
	 * 
	 * @param iStartDate
	 *            起始日期
	 * @param iEndDate
	 *            终止日期
	 */
	public static int getDiffDay(Date startDate, Date endDate) {
		startDate = getSimpleDate(startDate); // 不计时分秒
		endDate = getSimpleDate(endDate);
		int intDiffDay = (int) ((endDate.getTime() - startDate.getTime()) / 86400000l) + 1;
		return intDiffDay;
	}

	/**
	 * 得到年份加1的日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getNextYear(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);

		Date nextDate = cal.getTime();
		return nextDate;
	}

	/**
	 * 得到下一年的日期的前一天
	 *   如2017年1月5日 得到2018年1月4日
	 * @param date
	 * @return
	 */
	public static Date getNextYearBeforeOneDay(Date date) {
		
		return getNextDateFullDate(getNextYear(date),-1);
	}
	
	// 得到下n个天
	public static Date getNextDateFullDate(Date date, int intCount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + intCount);
		Date nextDate = cal.getTime();
		return nextDate;
	}

	// 得到下n个月
	public static Date getNextMonthFullDate(Date date, int intCount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + intCount);
		Date nextDate = cal.getTime();
		return nextDate;
	}
	
	public static void main(String[] args) throws ParseException {
		Date source = new Date();
		System.out.println(source.toLocaleString());
	    Date date = DateKit.minusDateTime(source, 1, DateKit.DAY_OF_MONTH);
		System.out.println(source.toLocaleString());
	    System.out.println(date.toLocaleString());
	}

	/**
	 * 获取两个日期的天数差（精确到小时）：不足一天不算一天
	 * 
	 * @param startDate
	 * @param intStartHour
	 * @param endDate
	 * @param intEndHour
	 * @return
	 */
	public static int getDayMinus(Date startDate, int intStartHour, Date endDate, int intEndHour) {
		LocalDate startDate_local = DateToLocalDate(startDate);
		LocalDate endDate_local = DateToLocalDate(endDate);

		startDate = LocalDateToDate(startDate_local);
		endDate = LocalDateToDate(endDate_local);

		int intDay = 0;

		intDay = (int) ((endDate.getTime() - startDate.getTime()) / 86400000l);
		if (intStartHour == 0 && intEndHour == 24) {
			intDay = intDay + 1;
		} else if (intStartHour == 24 && intEndHour == 0) {
			intDay = intDay - 1;
		} else {
			if (intStartHour > intEndHour) {
				intDay = intDay - 1;
			}
		}

		return intDay;

	}
	/**
	 * 
	 * @param startDate
	 * @param startHour
	 * @param endDate
	 * @param endHour
	 * @return
	 * @throws Exception
	 */
	public static int getDayMinusByTime(java.util.Date startDate, int startHour,java.util.Date endDate, int endHour) throws Exception {
		
		java.util.Date startDate_DateTime = DateKit.getDate(startDate, startHour, 0, 0);
		
		java.util.Date endDate_DateTime = DateKit.getDate(endDate, endHour, 0, 0);
		
		int intDay = (int) ((endDate_DateTime.getTime() - startDate_DateTime.getTime()) / 86400000l);
		
		return intDay;
	}
	/**
	 * 转换成yyyyMMddHHmm格式
	 * 
	 * @param dateString
	 * @return
	 */
	public static String transDate13(Date date, String hourStr, String minuteStr) {
		if (!ChgDataKit.isEmpty(date)) {
			String dateStr = getFormatedDateStr(date, "yyyyMMdd");

			if (StrKit.isEmpty(hourStr)) {
				hourStr = "00";
			} else if (hourStr.length() == 1) {
				hourStr = "0" + hourStr;
			}

			if (StrKit.isEmpty(minuteStr)) {
				minuteStr = "00";
			} else if (minuteStr.length() == 1) {
				minuteStr = "0" + minuteStr;
			}

			return dateStr + hourStr + minuteStr;
		} else {
			return null;
		}
	}

	public static boolean isInRange(Date start, Date end, Date value) {
		return value.compareTo(start) >= 0 && value.compareTo(end) <= 0;
	}

	/**
	 * 本月的最后一天的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDate_DateTime(Date date) {
		// 获取Calendar
		Calendar calendar = Calendar.getInstance();
		// 设置时间,当前时间不用设置
		calendar.setTime(date);
		// 设置日期为本月最大日期
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

		return calendar.getTime();
	}

	public static boolean isValidDate(String str, String pattern) {
		boolean convertSuccess = true;
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			convertSuccess = false;
		}
		return convertSuccess;
	}
	
	/**
	 * YYYYMMDD -> YYYY-MM-DD
	 * @param dateString 8个字符的日期
	 * @return YYYY-MM-DD形式的日期
	 */
	public static String correctDate(String dateString) {
		if (StrKit.isEmpty(dateString)) {
			return "";
		}
		if (dateString.length() < 8) {
			throw new IllegalArgumentException(dateString
					+ "的日期格式不对，必须为大于8位的纯数字的字符串");
		}
		String result = dateString.substring(0, 4) + "-"
				+ dateString.substring(4, 6) + "-" + dateString.substring(6, 8);
		return result;
	}
	
	/**
	 * 纠正日期时间格式
	 * @param dateString
	 * @return 修正后的日期时间 YYYY-MM-DD HH:mm:ss
	 */
	public static String correctDateTime(String dateString) {
		if (StrKit.isEmpty(dateString)) {
			return "";
		}
		String result = correctDate(dateString);
		if (dateString.length() >= 10) 
		{
			result += " " + dateString.substring(8, 10);
		}
		if (dateString.length() >= 12) 
		{
			result += ":" + dateString.substring(10, 12);
		}
		if (dateString.length() >= 14) 
		{
			result += ":" + dateString.substring(12, 14);
		}
		return result;
	}
	
	/**
	 * 转换成yyyy-MM-dd格式
	 * @param dateString
	 * @return
	 */
	public static String transDate10(String dateString) {
		String result = "";
		if(dateString != null && !"".equals(dateString.trim())) {
			if(dateString.length() >= 8) {
				result = dateString.substring(0, 4) + "-" + dateString.substring(4, 6) + "-" + dateString.substring(6,8);
			}
		}
		return result;
	}
	
	/**
	 * 日期小时转换 00 => 24
	 * 例:2018-08-08 00 => 2018-08-07 24
	 * @param dateStr
	 * @return
	 */
	public static String convertHourBetween0And24(String dateStr){
		if(!StrKit.isEmpty(dateStr)){
			Date date = DateKit.parse(dateStr);
			String formatDateStr = DateKit.getDateTimeStr(date); 
			if(!StrKit.isEmpty(formatDateStr) && formatDateStr.length()>=13){
				String hour = formatDateStr.substring(11,13);
				if("00".equals(hour)){
					Date newDate = DateKit.minusDateTime(date, 1,2); //减一天
					String resultStr = DateKit.getDateStr(newDate) + " 24";
					return resultStr;
				}
			}
		}
		return dateStr;
		
	}
	
	
	/**
	 * 日期小时转换 
	 * 例:2018-08-08 24 => 2018-08-09 
	 * @param dateStr
	 * @return
	 */
	public static Date convertHourBetween0And24(Date date, Double endHour) {
		if(date!=null){
			if(endHour!=null && endHour==24){
				Date newDate = DateKit.plusDateTime(date, 1, 2);
				return newDate;
			}
		}
		return date;
	}
	/**
	 * 日期小时转换 
	 * 例:2018-08-07 24:00:00 => 2018-08-07 
	 * @param dateStr
	 * @return
	 */
	public static Date ignore24(Date date,Double endHour){
		if(date!=null){
			if(endHour == 24){
				Date newDate = DateKit.minusDateTime(date, 1, 2);
				return newDate;
			}
		}
		return null;
	}


	public static Date addTime(Date date, int year, int month, int day, int hour) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, year);
		c.add(Calendar.MONTH, month);
		c.add(Calendar.DATE, day);
		c.add(Calendar.HOUR_OF_DAY, hour);
		return c.getTime();
	}
	
	/**
	 * 15位
	 * @param date
	 * @return
	 */
	public static String getYYMMDDHHMMSSSSS(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
		return sdf.format(date);
	}
	/**
	 * 17位	
	 * @param date
	 * @return
	 */
	public static String getYYYYMMDDHHMMSSSSS(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(date);
	}
	public static String getYYYYMMDDHH(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		return sdf.format(date);
	}

	public static String getYYYYMMDD(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}

	public static Date getNextDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, 1);
		return c.getTime();
	}

	
	public static String getMinutes(Date date) {
		if (date.getMinutes() < 10) {
			return "0" + date.getMinutes();
		}
		return String.valueOf(date.getMinutes());
	}

	public static String getH(Date date) {
		return date.getHours() + "";
	}

	public static Date getPureDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date fromYYYYMMDDHH(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		Date date = sdf.parse(dateStr);
		return date;
	}

	public static Date fromYYYYMMDD(String dateStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(dateStr);
		} catch (Exception e) {
			return sdf1.parse(dateStr);
		}
	}



	public static boolean isYYYYMMDDHH(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		try {
			Date date = sdf.parse(dateStr);
		} catch (ParseException e) {
			return false;
		}

		return true;
	}

	public static String getYYYY_MM_DD(String YYYYMMDDHHStr) {
		if (!StrKit.isEmpty(YYYYMMDDHHStr) && YYYYMMDDHHStr.length() >= 8) {
			return YYYYMMDDHHStr.substring(0, 4) + "-" + YYYYMMDDHHStr.substring(4, 6) + "-"
					+ YYYYMMDDHHStr.substring(6, 8);
		} else {
			return YYYYMMDDHHStr;
		}

	}

	public static String formatChange(String input, String format) {
		SimpleDateFormat formatInstance = new SimpleDateFormat(format);
		try {
			formatInstance.parse(input);
			return input;
		} catch (Exception e) {
		}
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(input);
			return formatInstance.format(date);
		} catch (Exception e) {
		}
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(input);
			return formatInstance.format(date);
		} catch (Exception e) {
		}
		try {
			Date date = new SimpleDateFormat("yyyyMMddHH").parse(input);
			return formatInstance.format(date);
		} catch (Exception e) {
		}
		try {
			Date date = new SimpleDateFormat("yyyyMMdd").parse(input);
			return formatInstance.format(date);
		} catch (Exception e) {
		}
		return input;
	}

	public static String getHH(String YYYYMMDDHHStr) {
		if (!StrKit.isEmpty(YYYYMMDDHHStr) && YYYYMMDDHHStr.length() >= 10) {
			return YYYYMMDDHHStr.substring(8, 10);
		} else {
			return YYYYMMDDHHStr;
		}
	}

	public static String getYYYYMMDDNow() {
		return new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
	}

	public static java.sql.Date parseDate(String dateStr) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date date = sdf.parse(dateStr.replace("-", "").replace("/", ""));
			return new java.sql.Date(date.getTime());
		} catch (Exception e) {
		}

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
			Date date = sdf.parse(dateStr.replace("-", "").replace("/", ""));
			return new java.sql.Date(date.getTime());
		} catch (Exception e) {
		}

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = sdf.parse(dateStr.replace("-", "").replace("/", ""));
			return new java.sql.Date(date.getTime());
		} catch (Exception e) {
		}

		return new java.sql.Date(Calendar.getInstance().getTimeInMillis());
	}

	public static int getAge(String date) {
		try {
			return getAge(fromYYYYMMDD(date.replace("-", "").substring(0, 8)));
		} catch (Exception e) {
			return 0;
		}
	}

	public static int getAge(Date dateOfBirth) {
		int age = 0;
		Calendar born = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		if (dateOfBirth != null) {
			now.setTime(new Date());
			born.setTime(dateOfBirth);
			if (born.after(now)) {
				throw new IllegalArgumentException("Can't be born in the future");
			}
			age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
			if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
				age -= 1;
			}
		}
		return age;
	}
	
	/**
     * 上海只有起保两天内的保单才能进行全额退保和全额退保（扣手续费）
     * 两个日期中间的工作日天数(包括起期和止期当天)   caoshouling 20140520
     * 注：如果数据表中工作日和休息日不能都插入同一天 
     * @param strStartDate
     * @param strEndDate
     * @return
     * @throws Exception 
     * @throws UserException 
     */
	public static int getDutyDays(Date startDate,Date endDate){
    	int days = 0;
    	if(startDate.compareTo(endDate) > 0){
    		return days;
    	}else if(startDate.compareTo(endDate) == 0){
    		return days + 1;
    	}else {
    		return -1;
    	}
	
	}
	
	/**
     * 两个日期中间的工作日天数(包括起期和止期当天)
     * @param strStartDate
     * @param strEndDate
     * @param ExtractDutyDateList 额外添加的工作日 （可能包括周六周日）
     * @param ExtractRestDateList 额外添加的休息日 （主要是节假日，可能包括周六周日）
     * @return 
     */
	public static int getDutyDays(Date startDate,Date endDate,String[] ExtractDutyDateList,String[] ExtractRestDateList ) {
    	
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	Date startDateOrgin = startDate;
    	Date ExtractDate = null;

    	int result = 0;
    	if(startDate.compareTo(endDate) >= 0){
    		return result;
    	}
    	while (startDate.compareTo(endDate) <= 0) {
        	if (startDate.getDay() != 6 && startDate.getDay() != 0)
        	    result++;
        	    startDate.setDate(startDate.getDate() + 1);
    	}
    	//额外添加的工作日
    	if(ExtractDutyDateList != null){
        	for(String tempDate: ExtractDutyDateList){
        		if(tempDate != null && !"".equals(tempDate)){
        			try {
						ExtractDate = df.parse(tempDate);
						if(startDateOrgin.compareTo(ExtractDate) <= 0 && endDate.compareTo(ExtractDate) >= 0  ){
							if (ExtractDate.getDay() == 6 || ExtractDate.getDay() == 0){//因为非周末已经添加了，所以周末才加
								result ++;
							}
						}
					} catch (ParseException e) {
						System.out.println("”额外添加的工作日“日期格式非法,无法进行转换");
						e.printStackTrace();
					}
        		}
        	}
    	}

    	//额外添加的休息日
    	if(ExtractRestDateList != null){
        	for(String tempDate: ExtractRestDateList){
        		if(tempDate != null && !"".equals(tempDate)){
        			try {
						ExtractDate = df.parse(tempDate);
						if(startDateOrgin.compareTo(ExtractDate) <= 0 && endDate.compareTo(ExtractDate) >= 0  && result >=1){
							if (ExtractDate.getDay() != 6 && ExtractDate.getDay() != 0){//因为前面排除了周末，所以非周末才减
								result --;
							}
						}
					} catch (ParseException e) {
						System.out.println("”额外添加的休息日“日期格式非法,无法进行转换");
						e.printStackTrace();
					}
        		}
        	}
    	}
    	return result;
    } 
	
	/**
	 * 转换成yyyyMMdd格式
	 * 
	 * @param dateString
	 * @return
	 */
	public static String transDate8(String date) {
		if (StrKit.isEmpty(date)) {
			return "";
		} else {
			return date.replace("-", "");
		}
	}
	/**
	 * 日期1是否在日期2之后
	 * @param strData1
	 * @param strDate2
	 * @return
	 */
	public static boolean after(String strData1,String strDate2 ){
		
	    	boolean  validFlag= false;
			if (new ins.framework.common.DateTime(strData1, ins.framework.common.DateTime.YEAR_TO_SECOND)
									.getTime() > new ins.framework.common.DateTime(strDate2, ins.framework.common.DateTime.YEAR_TO_SECOND).getTime()) {
				validFlag = true;
			}
			return validFlag;
	}
	/**
	 * 日期1是否在日期2之后
	 * @param strData
	 * @param date
	 * @return
	 */
	public static  boolean after(String strData,Date date ){
		
    	boolean  validFlag= false;
		if (new ins.framework.common.DateTime(strData, ins.framework.common.DateTime.YEAR_TO_SECOND)
								.getTime() > date.getTime()) {
			validFlag = true;
		}
		return validFlag;
    }
	/**
	 * 日期1是否在日期2之后
	 * @param strData
	 * @param date
	 * @return
	 */
	public static  boolean after(Date date,String strData ){
		
    	boolean  validFlag= false;
		if (date.getTime() > new ins.framework.common.DateTime(strData, ins.framework.common.DateTime.YEAR_TO_SECOND)
								.getTime()  ) {
			validFlag = true;
		}
		return validFlag;
    }
}
