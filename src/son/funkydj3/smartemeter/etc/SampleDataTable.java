package son.funkydj3.smartemeter.etc;

import android.util.Log;

public class SampleDataTable {
	public static int addOn = 0;
	// 13
	public static double[] buf_year_kWh = new double[] { 0, 150, 170, 175, 165,
			180, 220, 230, 260, 220, 165, 160, 165 };
	public static int[] buf_year_charge = new int[] { 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0 };
	// 32
	public static double[] buf_month_kWh = new double[] { 0, 
		5, 6, 5, 6, 7, 7, 6, 6, 4, 5, 
		6, 5, 7, 6, 7, 6, 4, 4, 3, 4, 
		4, 4, 6, 4, 6, 7, 4, 4, 4, 5, 4 };
	public static double[] buf_month_kWh_10 = new double[]{
		57, 52, 51
	};
	public static int[] buf_month_charge_10 = new int[]{
		0,0,0
	};
	
	public static int[] buf_month_charge = new int[] { 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	// 25
	public static double[] buf_day_kWh = new double[] { 0, 
		0.3, 0.27, 0.23, 0.22, 0.25, 0.24, 0.26, 0.29, 0.3, 0.3, 
		0.3, 0.25, 0.26, 0.25, 0.25, 0.25, 0.25, 0.3, 0.31, 0.32, 
		0.4, 0.45, 0.45, 0.3 };
	public static double[] buf_day_charge = new double[] { 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
		0, 0, 0, 0 };
	
	public static void calculateSampleData(){
		if (SampleDataTable.addOn == 0) {
			// * year
			for (int i = 1; i < 13; i++) {
				Constant.this_year_kWh[i] += SampleDataTable.buf_year_kWh[i];
				SampleDataTable.buf_year_charge[i] = (int) ChargeTable_WON.ChargeCalculate(Constant.this_year_kWh[i]);
				Constant.this_year_charge[i] = SampleDataTable.buf_year_charge[i];
			}

			Log.d("SON", "be - month");
			// * month			
			for(int i = 1; i < 32 ; i++){
				Constant.this_month_kWh[i] += SampleDataTable.buf_month_kWh[i];
				Constant.this_month_charge[i] = (int) ((Constant.this_year_charge[Class_Time.getCurMonth()])*(Constant.this_month_kWh[i]/Constant.this_year_kWh[Class_Time.getCurMonth()]));
				if((i >= 1) && (i < 11)) SampleDataTable.buf_month_charge_10[0] += Constant.this_month_charge[i];
				else if((i >= 11) && (i < 21)) SampleDataTable.buf_month_charge_10[1] += Constant.this_month_charge[i];
				else if((i >= 21) && (i < 32)) SampleDataTable.buf_month_charge_10[2] += Constant.this_month_charge[i];
			}
			Log.d("SON", "af - month");
			
			
			//* day
			for(int i = 1; i< 25 ; i++){
				Constant.today_kWh[i] += SampleDataTable.buf_day_kWh[i];
				if(Constant.D) Log.d("SON", "SampleDataTable / day["+i+"]");
				Constant.today_charge[i] = (int) ((Constant.this_month_charge[Class_Time.getCurDay()])*(Constant.today_kWh[i]/Constant.this_month_kWh[Class_Time.getCurDay()]));
			}
			if(Constant.D) Log.d("SON", "SampleDataTable / day");			
			
			
			SampleDataTable.addOn = 1;
			//Calculator.sumThisYearCharge();
		} else {
			for (int i = 1; i < 13; i++) {
				//SampleDataTable.buf_year_charge[i] = (int) ChargeTable_WON.ChargeCalculate(SampleDataTable.buf_year_kWh[i]);
				Constant.this_year_kWh[i] -= SampleDataTable.buf_year_kWh[i];
				SampleDataTable.buf_year_charge[i] = (int) ChargeTable_WON.ChargeCalculate(Constant.this_year_kWh[i]);
				Constant.this_year_charge[i] = SampleDataTable.buf_year_charge[i];
			}
			
			buf_month_charge_10[0] = 0;
			buf_month_charge_10[1] = 0;
			buf_month_charge_10[2] = 0;
			for(int i = 1; i < 32 ; i++){
				Constant.this_month_kWh[i] -= SampleDataTable.buf_month_kWh[i];
				Constant.this_month_charge[i] = (int) ((Constant.this_year_charge[Class_Time.getCurMonth()])*(Constant.this_month_kWh[i]/Constant.this_year_kWh[Class_Time.getCurMonth()]));
				if((i >= 1) && (i < 11)) SampleDataTable.buf_month_charge_10[0] += Constant.this_month_charge[i];
				else if((i >= 11) && (i < 21)) SampleDataTable.buf_month_charge_10[1] += Constant.this_month_charge[i];
				else if((i >= 21) && (i < 32)) SampleDataTable.buf_month_charge_10[2] += Constant.this_month_charge[i];
			}			
			
			//* day
			for(int i = 1; i< 25 ; i++){
				Constant.today_kWh[i] -= SampleDataTable.buf_day_kWh[i];
				Constant.today_charge[i] = (int) ((Constant.this_month_charge[Class_Time.getCurDay()])*(Constant.today_kWh[i]/Constant.this_month_kWh[Class_Time.getCurDay()]));
			}
			
			SampleDataTable.addOn = 0;
			//Calculator.sumThisYearCharge();
			double tempppppp = 0;
			for(int i = 1 ; i < 25 ; i++){
				tempppppp += buf_day_kWh[i];
			}
			Log.d("SON", "SUM - buf_day_kWh : " + tempppppp);
		}
	}
}
