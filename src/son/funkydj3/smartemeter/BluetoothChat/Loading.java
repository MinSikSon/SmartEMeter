package son.funkydj3.smartemeter.BluetoothChat;

import son.funkydj3.smartemeter.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;



public class Loading extends Activity {
	public static final int REQUEST_CODE_MAIN = 1001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bt_activity_loading);
		int threadOut = 1;

		if (threadOut == 1) {
			threadOut++;
			new Thread(new Runnable() {

				@Override
				public void run() {

					try {
						Thread.sleep(2000);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// Intent intent = new Intent(getBaseContext(),
					// Login_login.class);
					Intent intent = new Intent(getBaseContext(),
							BluetoothChat.class);
					startActivityForResult(intent, REQUEST_CODE_MAIN);

					finish();
				}

			}).start();
		} else {
			Intent intent = new Intent(getBaseContext(), BluetoothChat.class);
			startActivityForResult(intent, REQUEST_CODE_MAIN);
		}
	}
}
