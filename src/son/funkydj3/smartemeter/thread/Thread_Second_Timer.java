package son.funkydj3.smartemeter.thread;

import son.funkydj3.smartemeter.etc.Accumulator;
import son.funkydj3.smartemeter.etc.Class_Time;
import son.funkydj3.smartemeter.etc.Constant;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Thread_Second_Timer extends Thread {
	Handler mHandler;
	
	public Thread_Second_Timer(Handler mHandler){
		this.mHandler = mHandler;
	}
	
	@Override
	public void run() {
		while (true) {
			Constant.PUBLIC_TIME++; // * basic time
			Message msg = Message.obtain();
			mHandler.sendMessage(msg);
			// *calculate* 
			Accumulator.accmulatePower();
			Class_Time.update_RealTime();
			Accumulator.accumulateStart();
			Constant.COUNT += 100;
			Constant.COUNT2 += 1;
			Constant.COUNT3 += 1;
			
			Constant.GET_TST_STATE = "" + Thread.currentThread().getState();
			Log.d("SON", ""+Thread.currentThread().getState());
			if(Constant.BREAK_TST == 1){
				Constant.BREAK_TST = 0;
				Log.d("SON","BREAK_TST");
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				;
			}
		}
	}
}
