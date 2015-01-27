package son.funkydj3.smartemeter.saving;

import android.os.Handler;
import android.os.Message;

public class Thread_Saving extends Thread {
	Handler mHandler;

	public Thread_Saving(Handler mHandler) {
		this.mHandler = mHandler;
	}

	@Override
	public void run() {
		while (true) {
			Message msg = Message.obtain();
			msg.arg1 = 1;
			mHandler.sendMessage(msg);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				;
			}
		}
	}
}
