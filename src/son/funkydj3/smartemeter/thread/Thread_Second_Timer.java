package son.funkydj3.smartemeter.thread;

import son.funkydj3.smartemeter.etc.Accumulator;
import son.funkydj3.smartemeter.etc.Class_Time;
import son.funkydj3.smartemeter.etc.Constant;
import android.os.Message;

public class Thread_Second_Timer extends Thread {

	
	@Override
	public void run() {
		while (true) {
			Constant.PUBLIC_TIME++; // * basic time
			Message msg = Message.obtain();
			// *calculate* 
			Accumulator.accmulatePower();
			Class_Time.update_RealTime();
			Accumulator.accumulateStart();
			
			
			Constant.COUNT += 100;
			Constant.COUNT2 += 1;
			Constant.COUNT3 += 1;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				;
			}
		}
	}
}
