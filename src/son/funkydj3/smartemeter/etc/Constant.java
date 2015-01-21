package son.funkydj3.smartemeter.etc;

public class Constant {
	public static int PUBLIC_TIME = 0;
	public static int PACE = 1;
	
	public static int COUNT = 0;
	public static int COUNT2 = 0;
	public static int COUNT3 = 0;
	
	// *accumulate*
	public static double wattPerSecond = 1;
	public static double KwattPerSecond = 0.001;
	public static double accumulatedWatt = 0;
	public static double accumulatedkWh = 0;
	
	
	// * InfoDeviceDisplay()
	public static int widthPixels;
	public static int heightPixels;
	public static int densityDpi;
	public static float density;
	public static float scaledDensity;
	public static float xdpi;
	public static float ydpi;
	//
	
	// * not used "address value 0"
	public static int[] this_year_charge = new int[13];
	public static int[] last_year_charge = new int[13];
	public static double[] this_year_kWh = new double[13];
	public static double[] last_year_kWh = new double[13];
	public static double sum_this_year_charge;
	public static double sum_last_year_charge;
	public static double sum_this_year_kWh;
	public static double sum_last_year_kWh;
	
	
	public static double[] this_month_kWh = new double[32];
	public static double[] last_month_kWh = new double[32];
	public static double sum_this_month_kWh;
	public static double sum_last_month_kWh;
	
	public static double[] today_kWh = new double[25];
	public static double[] yesterday_kWh = new double[25];
	public static double sum_today_kWh;
	public static double sum_yesterday_kWh;
	
	// * use this method "only first start"
	public static int initConstantCheck = 0;
	public static void initConstant(){
		for(int i = 0 ; i<13 ; i++){
			this_year_charge[i] = 0;
			last_year_charge[i] = 0;
		}
		for(int i = 0 ; i<32 ; i++){
			this_month_kWh[i] = 0;
			last_month_kWh[i] = 0;
		}
		for(int i = 0 ; i<25 ; i++){
			today_kWh[i] = 0;
			yesterday_kWh[i] = 0;
		}
	}
	
}
