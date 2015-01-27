package son.funkydj3.smartemeter.etc;

public class Constant {
	// *Saving*
	public static int targetCharge = 15000;
	
	// *Option*
	public static int speedUp = 1; // speedup
	public static double powerSetting = 0; // powersetting
	public static boolean powerSettingDeactivated = true; 
	
	// *log*
	public static boolean D = false;
	
	// *count*
	public static int PUBLIC_TIME = 0;
	public static int PACE = 1;

	public static int COUNT = 0;
	public static int COUNT2 = 0;
	public static int COUNT3 = 0;

	public static String GET_TST_STATE = "";
	public static int BREAK_TST = 0;

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
	public static int[] this_year_charge = new int[] { 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0 };
	public static int[] last_year_charge = new int[] { 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	public static double[] this_year_kWh = new double[] { 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0 };
	public static double[] last_year_kWh = new double[] { 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0 };
	public static int sum_this_year_charge = 1;
	public static int sum_last_year_charge = 1;
	public static double sum_this_year_kWh = 1;
	public static double sum_last_year_kWh = 1;

	public static int[] this_month_charge = new int[] { 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0 };
	public static double[] this_month_kWh = new double[] { 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0 };
	public static double[] last_month_kWh = new double[] { 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0 };
	public static double sum_this_month_kWh = 1;
	public static double sum_last_month_kWh = 1;

	public static double[] today_kWh = new double[] { 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0 };
	public static int[] today_charge = new int[] { 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0 };
	public static double[] yesterday_kWh = new double[] { 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	public static double sum_today_kWh = 0;
	public static double sum_yesterday_kWh = 0;

	// * use this method "only first start"
}
