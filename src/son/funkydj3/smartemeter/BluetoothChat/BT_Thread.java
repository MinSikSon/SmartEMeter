package son.funkydj3.smartemeter.BluetoothChat;

import android.os.Handler;
import android.os.Message;

public class BT_Thread extends Thread {
	Handler mHandler;
	

	public BT_Thread() {

	}

	public BT_Thread(Handler mHandler) {
		this.mHandler = mHandler;
	}

	@Override
	public void run() {
		while (true) {
			Message msg = Message.obtain();

			
			mHandler.sendMessage(msg);
			// bluetooth ����� �ȵ��� �ؾ���.
			
			try {
				Thread.sleep(1000); // Thread�� 1�� �������� ����
			} catch (InterruptedException e) {
				;
			}
		}
	}
}
