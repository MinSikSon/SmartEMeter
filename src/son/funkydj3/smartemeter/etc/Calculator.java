package son.funkydj3.smartemeter.etc;

public class Calculator {
	public static void sumLastYearCharge(){
		Constant.sum_last_year_charge = 0;
		for(int i = 0 ; i<13 ; i++){
			Constant.sum_last_year_charge += Constant.last_year_charge[i];
		}
	}
	public static void sumThisYearCharge(){
		Constant.sum_this_year_charge = 0;
		for(int i = 1 ; i<13 ; i++){
			Constant.sum_this_year_charge += Constant.this_year_charge[i];
		}
	}
}
