package son.funkydj3.smartemeter.etc;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Class_Time {
	private static String strCurDate;
	private static String strCurTime;
	private static String strCurYear;
	private static String strCurMonth;
	private static String strCurDay;
	private static String strCurHour;
	private static String strCurMinute;
	private static String strCurSecond;

	public static void update_RealTime() {
		long now = System.currentTimeMillis();
		Date date = new Date(now);
		SimpleDateFormat CurDateFormat = new SimpleDateFormat("dd MM yyyy");
		SimpleDateFormat CurTimeFormat = new SimpleDateFormat("HH:mm");
		SimpleDateFormat CurYearFormat = new SimpleDateFormat("yyyy");
		SimpleDateFormat CurMonthFormat = new SimpleDateFormat("MM");
		SimpleDateFormat CurDayFormat = new SimpleDateFormat("dd");
		SimpleDateFormat CurHourFormat = new SimpleDateFormat("HH");
		SimpleDateFormat CurMinuteFormat = new SimpleDateFormat("mm");
		SimpleDateFormat CurSecondFormat = new SimpleDateFormat("ss");

		strCurDate = CurDateFormat.format(date);
		strCurTime = CurTimeFormat.format(date);
		strCurYear = CurYearFormat.format(date);
		strCurMonth = CurMonthFormat.format(date);
		strCurDay = CurDayFormat.format(date);
		strCurHour = CurHourFormat.format(date);
		strCurMinute = CurMinuteFormat.format(date);
		strCurSecond = CurSecondFormat.format(date);
	}

	public static String modifiedMonth(String month) {
		String returnMonth;
		if (month.equalsIgnoreCase("01")){
			returnMonth = "Jan";
		}else if (month.equalsIgnoreCase("02")){
			returnMonth = "Feb";
		}else if (month.equalsIgnoreCase("03")){
			returnMonth = "Mar";
		}else if (month.equalsIgnoreCase("04")){
			returnMonth = "Apr";
		}else if (month.equalsIgnoreCase("05")){
			returnMonth = "May";
		}else if (month.equalsIgnoreCase("06")){
			returnMonth = "Jun";
		}else if (month.equalsIgnoreCase("07")){
			returnMonth = "Jul";
		}else if (month.equalsIgnoreCase("08")){
			returnMonth = "Aug";
		}else if (month.equalsIgnoreCase("09")){
			returnMonth = "Sep";
		}else if (month.equalsIgnoreCase("10")){
			returnMonth = "Oct";
		}else if (month.equalsIgnoreCase("11")){
			returnMonth = "Nov";
		}else{
			returnMonth = "Dec";
		}
		return returnMonth;
	}
	
	public static int getCurDate() {
		int a = Integer.parseInt(strCurDate);
		return a;
	}

	public static String getCurDateToString() {
		String dmy = strCurDay + " " + modifiedMonth(strCurMonth) + " " + strCurYear;
		return dmy;
	}

	public static int getCurTime() {
		int a = Integer.parseInt(strCurTime);
		return a;
	}

	public static String getCurTimeToString() {
		String tms = strCurTime + ":" + strCurSecond;
		return tms;
	}

	public static int getCurYear() {
		int a = Integer.parseInt(strCurYear);
		// if(Constant.PACE == 2)a = Integer.parseInt(strCurYear); // ** this is
		// key point
		return a;
	}

	public static int getCurMonth() {
		int a = Integer.parseInt(strCurMonth);
		return a;
	}

	public static int getCurDay() {
		int a = Integer.parseInt(strCurDay);
		return a;
	}

	public static int getCurHour() {
		int a = Integer.parseInt(strCurHour);
		return a;
	}

	public static int getCurMinute() {
		int a = Integer.parseInt(strCurMinute);
		return a;
	}

	public static int getCurSecond() {
		int a = Integer.parseInt(strCurSecond);
		return a;
	}

	public static void update_FastTime() {

	}

}
