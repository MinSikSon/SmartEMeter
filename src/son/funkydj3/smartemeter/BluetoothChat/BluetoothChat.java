/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package son.funkydj3.smartemeter.BluetoothChat;

import son.funkydj3.smartemeter.MainActivity;
import son.funkydj3.smartemeter.R;
import son.funkydj3.smartemeter.etc.Class_Data;
import son.funkydj3.smartemeter.etc.Constant;
import son.funkydj3.smartemeter.handler.BackPressCloseHandler;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This is the main Activity that displays the current chat session.
 */
public class BluetoothChat extends Activity {
	// Debugging
	private static final String TAG = "BluetoothChat";
	private static final boolean D = false;
	private static final boolean D_SON = false;
	

	// Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;

	// Key names received from the BluetoothChatService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";

	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
	private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
	private static final int REQUEST_ENABLE_BT = 3;

	// Layout Views
	// private ListView mConversationView;
	// private EditText mOutEditText;
	// private Button mSendButton;

	public static String BLUETOOTH_SERIAL = "00:01:95:17:AD:08";
    public static int BLUETOOTH_ON = 0;
	
	// Name of the connected device
	private String mConnectedDeviceName = null;
	// Array adapter for the conversation thread
	private ArrayAdapter<String> mConversationArrayAdapter;
	// String buffer for outgoing messages
	public static StringBuffer mOutStringBuffer;
	// Local Bluetooth adapter
	//private BluetoothAdapter mBluetoothAdapter = null;
	public static BluetoothAdapter mBluetoothAdapter = null;
	// Member object for the chat services
	//private BluetoothChatService mChatService = null;
	public static BluetoothChatService mChatService = null;

	// *point*
	public static int init_data_get_count = 0;
	public static int data_get_count = 0; // *point* ó���� ������ �߸��� Stream ���͸� ���� ���Ǵ�
									// count

	public static String VOLTAGE_String;
	public static String CURRENT_String;
	private static double VOLTAGE;
	private static double CURRENT;
	public static int RECEIVE_DATA_OK = 0;
	
	public static int BLUETOOTH_STATE_SON = 0;

	// *point* layout
	public static TextView tv_current;
	public static TextView tv_voltage;
	public static TextView tv_time;
	
	public static Button btn_Guage;
	public static ImageView img_bluetoothimage;
	
	//*point* thread
	public static BT_Thread ST= null; 
	public static BT_Thread_Timer STT = null;

	// *
	private BackPressCloseHandler backPressCloseHandler;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (D)
			Log.e(TAG, "+++ ON CREATE +++");

		// Set up the window layout
		setContentView(R.layout.bt_main);
		tv_current = (TextView)findViewById(R.id.tv_current);
		tv_voltage = (TextView)findViewById(R.id.tv_voltage);
		tv_time = (TextView)findViewById(R.id.tv_time);
		
		img_bluetoothimage = (ImageView)findViewById(R.id.img_bluetooth);
		
		//*
		btn_Guage = (Button)findViewById(R.id.test);
		btn_Guage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(BluetoothChat.this, MainActivity.class);
				startActivity(i);
			}
		});
		
		// Get local Bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		// Start thread
		if(ST == null){
			ST = new BT_Thread(mHandler2);
			ST.setDaemon(true);
			ST.start();
		}
		if(STT == null){
			STT = new BT_Thread_Timer();
			STT.setDaemon(true);
			STT.start();
		}

		// If the adapter is null, then Bluetooth is not supported
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
			finish();
			return;
		}
		
		backPressCloseHandler = new BackPressCloseHandler(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		if (D)
			Log.e(TAG, "++ ON START ++");
		if(D_SON) Log.d("SON", "BluetoothChat / onStart()");

		// If BT is not on, request that it be enabled.
		// setupChat() will then be called during onActivityResult
		// *point* ó�� ����ǰ� �ߴ�, Bluetooth Ȱ��ȭ ����â
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			// Otherwise, setup the chat session
		} else {
			if (mChatService == null)
				setupChat();
		}

		if(D_SON) Log.d("SON", "auto pairing test");
		// *auto pairing test*
		/*if (mBluetoothAdapter.isEnabled()){
			BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(BLUETOOTH_SERIAL);
			mChatService.connect(device, false);
			BLUETOOTH_ON = 1;
		}*/
		// *test end*
					
		
	}

	@Override
	public synchronized void onResume() {
		super.onResume();
		if (D)
			Log.e(TAG, "+ ON RESUME +");
		// Log.d("SON", "BluetoothChat / onResume()");
		// Performing this check in onResume() covers the case in which BT was
		// not enabled during onStart(), so we were paused to enable it...
		// onResume() will be called when ACTION_REQUEST_ENABLE activity
		// returns.
		if (mChatService != null) {
			// Only if the state is STATE_NONE, do we know that we haven't
			// started already
			if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
				// Start the Bluetooth chat services
				mChatService.start();
			}
		}

		
	}

	private void setupChat() {
		if(D_SON)Log.d(TAG, "setupChat()");

		// Initialize the array adapter for the conversation thread
		mConversationArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.bt_message);
		// mConversationView = (ListView) findViewById(R.id.in);
		// mConversationView.setAdapter(mConversationArrayAdapter);

		// Initialize the compose field with a listener for the return key
		// mOutEditText = (EditText) findViewById(R.id.edit_text_out);
		// mOutEditText.setOnEditorActionListener(mWriteListener);

		// Initialize the send button with a listener that for click events
		// mSendButton = (Button) findViewById(R.id.button_send);
		/*
		 * mSendButton.setOnClickListener(new OnClickListener() { public void
		 * onClick(View v) { // Send a message using content of the edit text
		 * widget TextView view = (TextView) findViewById(R.id.edit_text_out);
		 * String message = view.getText().toString(); sendMessage(message); }
		 * });
		 */

		// Initialize the BluetoothChatService to perform bluetooth connections

		// *point* Handler�� �ٿ���
		mChatService = new BluetoothChatService(this, mHandler);

		// Initialize the buffer for outgoing messages
		mOutStringBuffer = new StringBuffer("");
	}

	@Override
	public synchronized void onPause() {
		super.onPause();
		if (D)
			Log.e(TAG, "- ON PAUSE -");
	}

	@Override
	public void onStop() {
		super.onStop();
		if (D)
			Log.e(TAG, "-- ON STOP --");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Stop the Bluetooth chat services
		if (mChatService != null)
			mChatService.stop();
		if (D)
			Log.e(TAG, "--- ON DESTROY ---");
	}

	private void ensureDiscoverable() {
		if (D)
			Log.d(TAG, "ensure discoverable");
		if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Intent discoverableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(
					BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivity(discoverableIntent);
		}
	}

	/**
	 * Sends a message.
	 * 
	 * @param message
	 *            A string of text to send.
	 */
	// *point*
	private void sendMessage(String message) {
		// Check that we're actually connected before trying anything
		if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
			Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT)
					.show();
			return;
		}

		// Check that there's actually something to send
		if (message.length() > 0) {
			// Get the message bytes and tell the BluetoothChatService to write
			byte[] send = message.getBytes();
			mChatService.write(send);

			// Reset out string buffer to zero and clear the edit text field
			mOutStringBuffer.setLength(0);
			// mOutEditText.setText(mOutStringBuffer);
		}
	}

	// The action listener for the EditText widget, to listen for the return key
	private TextView.OnEditorActionListener mWriteListener = new TextView.OnEditorActionListener() {
		public boolean onEditorAction(TextView view, int actionId,
				KeyEvent event) {
			// If the action is a key-up event on the return key, send the
			// message
			if (actionId == EditorInfo.IME_NULL
					&& event.getAction() == KeyEvent.ACTION_UP) {
				String message = view.getText().toString();
				sendMessage(message);
			}
			if (D)
				Log.i(TAG, "END onEditorAction");
			return true;
		}
	};

	/*
	 * private final void setStatus(int resId) { final ActionBar actionBar =
	 * getActionBar(); actionBar.setSubtitle(resId); }
	 * 
	 * private final void setStatus(CharSequence subTitle) { final ActionBar
	 * actionBar = getActionBar();
	 * 
	 * actionBar.setSubtitle(subTitle); }
	 */

	// The Handler that gets information back from the BluetoothChatService
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_STATE_CHANGE:
				if (D)
					Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
				switch (msg.arg1) {
				case BluetoothChatService.STATE_CONNECTED:
					// setStatus(getString(R.string.title_connected_to,
					// mConnectedDeviceName));
					mConversationArrayAdapter.clear();
					break;
				case BluetoothChatService.STATE_CONNECTING:
					// setStatus(R.string.title_connecting);
					break;
				case BluetoothChatService.STATE_LISTEN:
				case BluetoothChatService.STATE_NONE:
					// setStatus(R.string.title_not_connected);
					break;
				}
				break;
			case MESSAGE_WRITE:
				byte[] writeBuf = (byte[]) msg.obj;
				// construct a string from the buffer
				String writeMessage = new String(writeBuf);
				// mConversationArrayAdapter.add("Me:  " + writeMessage);
				break;
			case MESSAGE_READ:
				byte[] readBuf = (byte[]) msg.obj;
				//byte[] readBuf = new byte[1024];
				//readBuf = (byte[]) msg.obj;
				
				for(int i = 0 ; i<8 ; i++) if(D_SON) Log.d("SON", "readBuf[] : " + readBuf[i]);
				if(D_SON) Log.d("SON","---------------------------------------------------");

				String readBufToStr = BT_TypeCasting.byteToHex(readBuf); // *point* byteToHex
				if(D_SON) Log.d("SON", "readBufToStr : " + readBufToStr);
				
				BT_StringCutter ssc = new BT_StringCutter(readBufToStr);
				ssc.Cutting();
				String OK_ACK = ssc.get_ACK();

				String OK_CURRENT1 = ssc.get_CURRENT1();
				String OK_VOLTAGE1 = ssc.get_VOLTAGE1();
				
				CURRENT_String = ssc.get_CURRENT();
				VOLTAGE_String = ssc.get_VOLTAGE();
				// Log.d("SON", "CUR & VOL String : " + CURRENT_String + " & "+ VOLTAGE_String);
				if(D_SON)Log.d("SON", "CALIB : " + CURRENT_String + " & " + VOLTAGE_String);
				if(D_SON) Log.d("SON", "CALIB : " + CURRENT_String + " & " + VOLTAGE_String);
				
				{
					//************
					CURRENT = (Math.round(BT_TypeCasting._8HexToDec(CURRENT_String)));
					//Log.d("SON", "CURRENT : " + CURRENT);
					CURRENT /= 10000;
					//Log.d("SON", "CURRENT/10000 : " + CURRENT);
				}
				VOLTAGE = BT_TypeCasting.V_Gain(0.1 * BT_TypeCasting._4HexToDec(VOLTAGE_String));
				//VOLTAGE = Son_TypeCasting.V_Gain(VOLTAGE);
				if(D_SON) Log.d("SON", "this");
				//*point* �� ���� �����ǿ� Data�� display�ȴ�
				if(OK_ACK.equals("06")) {
					if(VOLTAGE >= 210 && VOLTAGE <= 230){
						RECEIVE_DATA_OK = 1;
					}else if(VOLTAGE >= 135 && VOLTAGE <= 145){
						VOLTAGE = Math.round(VOLTAGE * 1.56029 * 10000) / 10000;
						RECEIVE_DATA_OK = 1;
					}else{
						RECEIVE_DATA_OK = 0;
					}
				}
				else RECEIVE_DATA_OK = 0;
					

				String readMessage = new String(readBuf, 0, msg.arg1);
				// mConversationArrayAdapter.add(mConnectedDeviceName+":  " + readMessage);
				// mConversationArrayAdapter.add(readMessage);
				readMessage = "0"; // *point* String �ʱ�ȭ

				break;
			case MESSAGE_DEVICE_NAME:
				// save the connected device's name
				mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
				Toast.makeText(getApplicationContext(),
						"Connected to " + mConnectedDeviceName,
						Toast.LENGTH_SHORT).show();
				break;
			case MESSAGE_TOAST:
				Toast.makeText(getApplicationContext(),
						msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}
	};

	

	

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (D)
			Log.d(TAG, "onActivityResult " + resultCode);
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE_SECURE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				connectDevice(data, true);
			}
			break;
		case REQUEST_CONNECT_DEVICE_INSECURE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				connectDevice(data, false);
			}
			break;
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				// Bluetooth is now enabled, so set up a chat session
				setupChat();
			} else {
				// User did not enable Bluetooth or an error occurred
				Log.d(TAG, "BT not enabled");
				Toast.makeText(this, R.string.bt_not_enabled_leaving,
						Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}

	private void connectDevice(Intent data, boolean secure) {
		// Get the device MAC address
		String address = data.getExtras().getString(
				DeviceListActivity.EXTRA_DEVICE_ADDRESS);
		// Get the BluetoothDevice object
		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
		// Attempt to connect to the device
		mChatService.connect(device, secure);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.option_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent serverIntent = null;
		switch (item.getItemId()) {
		case R.id.secure_connect_scan:
			// Launch the DeviceListActivity to see devices and do scan
			serverIntent = new Intent(this, DeviceListActivity.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
			return true;
		case R.id.insecure_connect_scan:
			// Launch the DeviceListActivity to see devices and do scan
			serverIntent = new Intent(this, DeviceListActivity.class);
			startActivityForResult(serverIntent,
					REQUEST_CONNECT_DEVICE_INSECURE);
			return true;
		case R.id.discoverable:
			/*
			 * // Ensure this device is discoverable by others
			 * ensureDiscoverable(); return true;
			 */
			// Launch the DeviceListActivity to see devices and do scan
			serverIntent = new Intent(this, DeviceListActivity.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
			return true;
		}
		return false;
	}
	
	
	// **********************************************************************************************************	
	// **********************************************************************************************************
	// *point*
	public static Handler mHandler2 = new Handler() {
		public void handleMessage(Message msg) {
			byte[] send = { 1, 1 };
			mChatService.write(send);
			mOutStringBuffer.setLength(0);
			
			// *btn animation
			if(STT.get_global_time()%4 == 0) btn_Guage.setTextColor(Color.BLACK);
			else btn_Guage.setTextColor(Color.WHITE);
			// mOutEditText.setText(mOutStringBuffer);
				
			//else if(mBluetoothAdapter.disable()) BLUETOOTH_ON = 0;
			
			// *test end*
			
			if (BLUETOOTH_STATE_SON == 1) {
				if(img_bluetoothimage != null) img_bluetoothimage.setBackgroundResource(R.drawable.bluetooth_connect);
				if(RECEIVE_DATA_OK == 1){
					tv_current.setText(CURRENT + " mA");
					tv_voltage.setText(VOLTAGE + " V");
					// *
					if(Constant.powerSettingDeactivated == true){
						Class_Data.Data_CURRENT_mA = CURRENT; // mA
						Class_Data.Data_CURRENT_A = Class_Data.Data_CURRENT_mA/1000; // A
						Class_Data.Data_VOLTAGE = VOLTAGE; // V
						Class_Data.Data_POWER = Class_Data.Data_VOLTAGE * Class_Data.Data_CURRENT_A * Constant.speedUp; // W = VI
					}else if(Constant.powerSettingDeactivated == false){
						Class_Data.Data_CURRENT_mA = 1000 * Constant.powerSetting / 220 ;
						Class_Data.Data_CURRENT_A = Class_Data.Data_CURRENT_mA/1000;
						Class_Data.Data_VOLTAGE = 220.0;
						Class_Data.Data_POWER = Constant.powerSetting * Constant.speedUp;
					}
				}
				if(STT.get_grid_time_hour() > 0 ){
					tv_time.setText(STT.get_grid_time_hour() + " Hour " + STT.get_grid_time_minute() + " Min "+STT.get_grid_time_second()+" Sec");
				}
				else if(STT.get_grid_time_minute() > 0){
					tv_time.setText(STT.get_grid_time_minute() + " Min "+STT.get_grid_time_second()+" Sec");
				}else{
					tv_time.setText(STT.get_grid_time_second() + " Sec");
				}
				//*point* ��������� �ٸ� �� �ֱ� ������, �� �κ��� �����ϰ� �ٲ������.
				if(CURRENT > 0.0048){
					STT.up_grid_time();
				}
				else{
				}
				
			} else {
				if(img_bluetoothimage != null) {
					if(STT.get_global_time() % 4 == 0)img_bluetoothimage.setBackgroundResource(R.drawable.bluetooth_disconnect1);
					else if(STT.get_global_time() % 4 == 2)img_bluetoothimage.setBackgroundResource(R.drawable.bluetooth_disconnect2);
				}
				if(Constant.powerSettingDeactivated == true){
					Class_Data.Data_CURRENT_mA = 0;
					Class_Data.Data_CURRENT_A = Class_Data.Data_CURRENT_mA/1000;
					Class_Data.Data_VOLTAGE = 0;
					Class_Data.Data_POWER = 0 * Constant.speedUp;
				}else if(Constant.powerSettingDeactivated == false){
					Class_Data.Data_CURRENT_mA = 1000 * Constant.powerSetting / 220 ;
					Class_Data.Data_CURRENT_A = Class_Data.Data_CURRENT_mA/1000;
					Class_Data.Data_VOLTAGE = 220.0;
					Class_Data.Data_POWER = Constant.powerSetting * Constant.speedUp;
				}
				// *test*
				tv_current.setText("0 mA");
				tv_voltage.setText("0 V");
			}
		}
	};
	// **********************************************************************************************************
	// **********************************************************************************************************
	
	
	@Override
	public void onBackPressed() {
		//super.onBackPressed();
		//backPressCloseHandler.onBackPressed();
	}
}
