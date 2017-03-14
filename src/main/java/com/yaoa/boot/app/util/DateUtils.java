package com.yaoa.boot.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	/**
	 * 格式化日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date,String pattern){
		return new SimpleDateFormat(pattern).format(date);
	}
	
	public static Date getDate(String pattern,String dateString){
		Date date = null;
		try {
			date = new SimpleDateFormat(pattern).parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 获取今天是星期几。中国风格，从星期一开始
	 * @return
	 */
	public static int getDayOfWeek(){
		Calendar now = Calendar.getInstance();
		//一周第一天是否为星期天
		boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
		//获取周几
		int weekDay = now.get(Calendar.DAY_OF_WEEK);
		//若一周第一天为星期天，则-1
		if(isFirstSunday){
		  weekDay = weekDay - 1;
		  if(weekDay == 0){
		    weekDay = 7;
		  }
		}
		return weekDay;
	}
}
