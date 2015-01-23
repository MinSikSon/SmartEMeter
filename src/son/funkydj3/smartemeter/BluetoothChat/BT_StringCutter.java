package son.funkydj3.smartemeter.BluetoothChat;

import java.util.StringTokenizer;

public class BT_StringCutter {

	private String str;
	private int Cutting_Count = 0;
	private StringTokenizer st;
	private String ACK;
	private String NUMBEROFBYTE;
	private String CURRENT1, CURRENT2, CURRENT3, CURRENT4;
	private String VOLTAGE1, VOLTAGE2;
	private String BIT1, BIT2, BIT3, BIT4, BIT5, BIT6, BIT7, BIT8;

	public BT_StringCutter(String str) {
		this.str = str;
	}

	public void Cutting() {
		st = new StringTokenizer(this.str, " ");
		String particle_String;
		while (st.hasMoreTokens()) {
			particle_String = st.nextToken();
			if (Cutting_Count == 0) {
				ACK = particle_String;
				BIT1 = particle_String;
				Cutting_Count++;
			} else if (Cutting_Count == 1) {
				NUMBEROFBYTE = particle_String;
				BIT2 = particle_String;
				Cutting_Count++;
			} else if (Cutting_Count == 2) {
				CURRENT4 = particle_String;
				BIT3 = particle_String;
				Cutting_Count++;
			} else if (Cutting_Count == 3) {
				CURRENT3 = particle_String;
				BIT4 = particle_String;
				Cutting_Count++;
			} else if (Cutting_Count == 4) {
				CURRENT2 = particle_String;
				BIT5 = particle_String;
				Cutting_Count++;
			} else if (Cutting_Count == 5) {
				CURRENT1 = particle_String;
				BIT6 = particle_String;
				Cutting_Count++;
			} else if (Cutting_Count == 6) {
				VOLTAGE2 = particle_String;
				BIT7 = particle_String;
				Cutting_Count++;
			} else if (Cutting_Count == 7) {
				VOLTAGE1 = particle_String;
				BIT8 = particle_String;
				Cutting_Count++;
			}
		}
	}

	String get_ACK() {
		return ACK;
	}

	String get_NUMBEROFBYTE() {
		return NUMBEROFBYTE;
	}

	String get_CURRENT1() {
		return CURRENT1;
	}

	String get_CURRENT2() {
		return CURRENT2;
	}

	String get_CURRENT() {
		String TOTALCURRENT = CURRENT1 + CURRENT2 + CURRENT3 + CURRENT4;
		return TOTALCURRENT;
	}

	String get_VOLTAGE1(){
		return VOLTAGE1;
	}
	String get_VOLTAGE() {
		String TOTALVOLTAGE = VOLTAGE1 + VOLTAGE2;
		return TOTALVOLTAGE;
	}
	
	/*double get_CURRENT_Double(){
		double mCurDec = 0;
		mCurDec = 0.001 * Son_TypeCasting.HexToDec(CURRENT3) + 0.0001 * Son_TypeCasting.HexToDec(CURRENT4);
		return mCurDec;
	}
	
	double get_VOLTAGE_Double(){
		double mVotDec = 0;
		mVotDec = 10 * Son_TypeCasting.HexToDec(VOLTAGE1) + 0.1 * Son_TypeCasting.HexToDec(VOLTAGE2);
		return mVotDec;
	}*/
	
	String get_BIT1(){
		return BIT1;
	}
	
	String get_BIT2(){
		return BIT2;
	}
	
	String get_BIT3(){
		return BIT3;
	}
	
	String get_BIT4(){
		return BIT4;
	}
	
	String get_BIT5(){
		return BIT5;
	}
	
	String get_BIT6(){
		return BIT6;
	}
	
	String get_BIT7(){
		return BIT7;
	}
	
	String get_BIT8(){
		return BIT8;
	}
	
	
}
	
