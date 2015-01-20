package son.funkydj3.smartemeter.thread;

import android.os.Handler;
import android.os.Message;

public class Thread_Timer extends Thread {

	
	@Override
	public void run() {
		while (true) {
			Message msg = Message.obtain();
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
