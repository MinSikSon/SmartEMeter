package son.funkydj3.smartemeter.etc;

public class ChargeTable_WON {
	static double totalCharge;
	private static double[] kWh_basecharge = { 410, 910, 1600, 3850, 7300,
			12940 };
	private static double[] kWh_powercharge = { 60.7, 125.9, 187.9, 280.6,
			417.7, 709.5 };

	public static double ChargeCalculate(double kWh) {
		double[] buf = { 0, 0, 0, 0, 0, 0};
		if (kWh <= 100) {
			for (int i = 0; i < 1; i++) {
				buf[0] += kWh_basecharge[i];
				buf[1] += kWh_powercharge[i] * kWh;
			}
			buf[2] = buf[0] + buf[1];
			buf[3] = Math.round(buf[2]*0.1);
			buf[4] = Math.floor(buf[2]*0.037);
			buf[5] = buf[2]+buf[3]+buf[4];
			return Math.floor(buf[5]*0.1d)/0.1d;
		} else if (kWh <= 200 && kWh > 100) {
			for (int i = 0; i < 2; i++) {
				buf[0] += kWh_basecharge[i];
				if (kWh >= 100) {
					buf[1] += kWh_powercharge[i] * 100;
					kWh -= 100;
				} else
					buf[1] += kWh_powercharge[i] * kWh;
			}
			buf[2] = buf[0] + buf[1];
			buf[3] = Math.round(buf[2]*0.1);
			buf[4] = Math.floor(buf[2]*0.037);
			buf[5] = buf[2]+buf[3]+buf[4];
			return Math.floor(buf[5]*0.1d)/0.1d;
		} else if (kWh <= 300 && kWh > 200) {
			for (int i = 0; i < 3; i++) {
				buf[0] += kWh_basecharge[i];
				if (kWh >= 100) {
					buf[1] += kWh_powercharge[i] * 100;
					kWh -= 100;
				} else
					buf[1] += kWh_powercharge[i] * kWh;
			}
			buf[2] = buf[0] + buf[1];
			buf[3] = Math.round(buf[2]*0.1);
			buf[4] = Math.floor(buf[2]*0.037);
			buf[5] = buf[2]+buf[3]+buf[4];
			return Math.floor(buf[5]*0.1d)/0.1d;
		} else if (kWh <= 400 && kWh > 300) {
			for (int i = 0; i < 4; i++) {
				buf[0] += kWh_basecharge[i];
				if (kWh >= 100) {
					buf[1] += kWh_powercharge[i] * 100;
					kWh -= 100;
				} else
					buf[1] += kWh_powercharge[i] * kWh;
			}
			buf[2] = buf[0] + buf[1];
			buf[3] = Math.round(buf[2]*0.1);
			buf[4] = Math.floor(buf[2]*0.037);
			buf[5] = buf[2]+buf[3]+buf[4];
			return Math.floor(buf[5]*0.1d)/0.1d;
		} else if (kWh <= 500 && kWh > 400) {
			for (int i = 0; i < 5; i++) {
				buf[0] += kWh_basecharge[i];
				if (kWh >= 100) {
					buf[1] += kWh_powercharge[i] * 100;
					kWh -= 100;
				} else
					buf[1] += kWh_powercharge[i] * kWh;
			}
			buf[2] = buf[0] + buf[1];
			buf[3] = Math.round(buf[2]*0.1);
			buf[4] = Math.floor(buf[2]*0.037);
			buf[5] = buf[2]+buf[3]+buf[4];
			return Math.floor(buf[5]*0.1d)/0.1d;
		} else if (kWh > 500) {
			for (int i = 0; i < 6; i++) {
				buf[0] += kWh_basecharge[i];
				if (kWh >= 100) {
					buf[1] += kWh_powercharge[i] * 100;
					kWh -= 100;
				} else
					buf[1] += kWh_powercharge[i] * kWh;
			}
			buf[2] = buf[0] + buf[1];
			buf[3] = Math.round(buf[2]*0.1);
			buf[4] = Math.floor(buf[2]*0.037);
			buf[5] = buf[2]+buf[3]+buf[4];
			return Math.floor(buf[5]*0.1d)/0.1d;
		}
		else
			return 0;
	}
}
