package son.funkydj3.smartemeter.etc;


public class Accumulator {
	private static int off_day1 = 0;
	private static int off_month1 = 0;
	private static int off_year1 = 0;
	public static void getANDaccmulatePower(){
		Constant.wattPerSecond = Class_Data.Data_POWER/1000;
		Constant.KwattPerSecond = Constant.wattPerSecond/1000;
		Constant.accumulatedWatt += Constant.wattPerSecond;
		Constant.accumulatedkWh = Constant.accumulatedWatt/1000;
	}
	
	// * exist "some error"
	public static void accumulateStart(){
		// * for DAY
		for(int i = 1 ; i< 25 ; i++){
			if(Class_Time.getCurHour() == i){
				Constant.today_kWh[i] += Constant.KwattPerSecond;
				if(Class_Time.getCurHour() == 1 && off_day1 == 0){
					Constant.sum_today_kWh = 0;
					off_day1 = 1;
				}else{
					if(Class_Time.getCurHour() == 2) off_day1 = 0;
					Constant.sum_today_kWh += Constant.KwattPerSecond;
				}
			}else if(Class_Time.getCurHour() == 0){
				Constant.today_kWh[24] += Constant.KwattPerSecond;
			}
		}
		// * for MONTH
		for(int i = 1 ; i< 32 ; i++){
			if(Class_Time.getCurDay() == i){
				Constant.this_month_kWh[i] += Constant.KwattPerSecond;
				if(Class_Time.getCurDay() == 1 && off_month1 == 0){
					Constant.sum_this_month_kWh = 0;
					off_month1 = 1;
				}else{
					if(Class_Time.getCurDay() == 2) off_month1 = 0;
					Constant.sum_this_month_kWh += Constant.KwattPerSecond;
				}
			}
		}
		
		// * for YEAR
		for(int i = 1 ; i< 13 ; i++){
			if(Class_Time.getCurMonth() == i){
				Constant.this_year_kWh[i] += Constant.KwattPerSecond;
				if(Class_Time.getCurMonth() == 1 && off_year1 == 0){
					Constant.sum_this_year_kWh = 0;
					off_year1 = 1;
				}else{
					if(Class_Time.getCurMonth() == 2) off_year1 = 0;
					Constant.sum_this_year_kWh += Constant.KwattPerSecond;
				}
			}
		}
	}
}
