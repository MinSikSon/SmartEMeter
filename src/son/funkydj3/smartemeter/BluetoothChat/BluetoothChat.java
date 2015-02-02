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

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import son.funkydj3.smartemeter.MainActivity;
import son.funkydj3.smartemeter.R;
import son.funkydj3.smartemeter.etc.Class_Data;
import son.funkydj3.smartemeter.etc.Class_Time;
import son.funkydj3.smartemeter.etc.Constant;
import son.funkydj3.smartemeter.etc.SampleDataTable;
import son.funkydj3.smartemeter.handler.BackPressCloseHandler;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
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
import android.widget.LinearLayout;
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
	// private BluetoothAdapter mBluetoothAdapter = null;
	public static BluetoothAdapter mBluetoothAdapter = null;
	// Member object for the chat services
	// private BluetoothChatService mChatService = null;
	public static BluetoothChatService mChatService = null;

	// *point*
	public static int init_data_get_count = 0;
	public static int data_get_count = 0; // *point* ó���� ������ �߸��� Stream
											// ���͸� ���� ���Ǵ�
	// count

	public static String VOLTAGE_String;
	public static String CURRENT_String;
	private static double VOLTAGE;
	private static double CURRENT;
	public static int RECEIVE_DATA_OK = 0;

	public static int BLUETOOTH_STATE_SON = 0;

	// *point* layout
	public static TextView tv_BluetoothChat_charge_now = null;
	public static TextView tv_BluetoothChat_charge_endofmonth = null;
	public static TextView tv_time;
	public static int BluetoothChat_charge_now = 0;
	public static int BluetoothChat_charge_endofmonth = 0;

	public static Button btn_BluetoothChat_IntentToMain = null;
	public static ImageView img_bluetoothimage = null;

	// *point* thread
	public static BT_Thread ST = null;
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
		initLayout_BluetoothChat();

		// *charylayout
		initGaugeLayout_BluetoothChat1();
		initChart_BluetoothChat1();
		initGaugeLayout_BluetoothChat2();
		initChart_BluetoothChat2();

		// Get local Bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		// Start thread
		if (ST == null) {
			ST = new BT_Thread(mHandler2);
			ST.setDaemon(true);
			ST.start();
		}
		if (STT == null) {
			STT = new BT_Thread_Timer();
			STT.setDaemon(true);
			STT.start();
		}

		// If the adapter is null, then Bluetooth is not supported
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Bluetooth is not available",
					Toast.LENGTH_LONG).show();
			finish();
			return;
		}

		backPressCloseHandler = new BackPressCloseHandler(this);
	}

	void initLayout_BluetoothChat() {
		tv_BluetoothChat_charge_now = (TextView) findViewById(R.id.tv_BluetoothChat_charge_now);
		tv_BluetoothChat_charge_endofmonth = (TextView) findViewById(R.id.tv_BluetoothChat_charge_endofmonth);
		tv_time = (TextView) findViewById(R.id.tv_time);

		img_bluetoothimage = (ImageView) findViewById(R.id.img_bluetooth);

		// *
		btn_BluetoothChat_IntentToMain = (Button) findViewById(R.id.btn_BluetoothChat_IntentToMain);
		btn_BluetoothChat_IntentToMain
				.setText("if you want to connect \nwith board\nClick [Menu Button]");
		btn_BluetoothChat_IntentToMain
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent i = new Intent(BluetoothChat.this,
								MainActivity.class);
						startActivity(i);
					}
				});
	}

	// * gaugeView1
		public static GraphicalView mChart_BluetoothChat1;
		public static XYSeries mCurrentSeries_BluetoothChat1;
		public static XYMultipleSeriesDataset mDataset_BluetoothChat1 = new XYMultipleSeriesDataset();
		public static SimpleSeriesRenderer mCurrentRenderer_BluetoothChat1;
		public static XYMultipleSeriesRenderer mRenderer_BluetoothChat1 = new XYMultipleSeriesRenderer();
		public static LinearLayout layout_BluetoothChat1;

		void initGaugeLayout_BluetoothChat1() {
			layout_BluetoothChat1 = (LinearLayout) findViewById(R.id.ll_BluetoothChat_chart1);
			if (mChart_BluetoothChat1 == null)
				initChart_BluetoothChat1();
			mChart_BluetoothChat1 = ChartFactory
					.getBarChartView(this, mDataset_BluetoothChat1,
							mRenderer_BluetoothChat1, Type.STACKED); // * modified
			layout_BluetoothChat1.addView(mChart_BluetoothChat1);
		}

		void initChart_BluetoothChat1() {
			mCurrentSeries_BluetoothChat1 = new XYSeries("");
			mDataset_BluetoothChat1.addSeries(mCurrentSeries_BluetoothChat1);

			mCurrentRenderer_BluetoothChat1 = new XYSeriesRenderer();
			mCurrentRenderer_BluetoothChat1.setColor(Color.BLACK);
			mCurrentRenderer_BluetoothChat1.setDisplayChartValues(true);
			mCurrentRenderer_BluetoothChat1.setChartValuesTextAlign(Align.CENTER);
			if (Constant.widthPixels <= 480) {
				mCurrentRenderer_BluetoothChat1.setChartValuesTextSize(15);
			} else if (Constant.widthPixels > 480 && Constant.widthPixels <= 720) {
				mCurrentRenderer_BluetoothChat1.setChartValuesTextSize(20);
			} else if (Constant.widthPixels >= 1080) {
				mCurrentRenderer_BluetoothChat1.setChartValuesTextSize(25);
			}
			mRenderer_BluetoothChat1.setLabelsColor(Color.rgb(255, 255, 255)); // *
																				// "title + label"'s
																				// color
			if (Constant.widthPixels <= 480) {
				mRenderer_BluetoothChat1.setLabelsTextSize(20);
			} else if (Constant.widthPixels > 480 && Constant.widthPixels <= 720) {
				mRenderer_BluetoothChat1.setLabelsTextSize(30);
			} else if (Constant.widthPixels >= 1080) {
				mRenderer_BluetoothChat1.setLabelsTextSize(40);
			}
			int[] margins_BluetoothChat1 = new int[] { 0, 0, 0, 0 }; // {top, left,
																		// bottom,
																		// right}
			if (Constant.widthPixels <= 480) {
				margins_BluetoothChat1 = new int[] { 40, 90, -15, 50 };
			} else if (Constant.widthPixels > 480 && Constant.widthPixels <= 720) {
				margins_BluetoothChat1 = new int[] { 50, 120, -25, 60 };
			} else if (Constant.widthPixels == 1080) { // 1080*1920
				margins_BluetoothChat1 = new int[] { 60, 150, -35, 70 };
			}
			mRenderer_BluetoothChat1.setMargins(margins_BluetoothChat1);
			mRenderer_BluetoothChat1.setApplyBackgroundColor(true);
			mRenderer_BluetoothChat1.setPanEnabled(false, false); // * fix graph
			mRenderer_BluetoothChat1.setZoomEnabled(false, false); // * enable zoom
			mRenderer_BluetoothChat1.setYLabelsAlign(Align.RIGHT);
			mRenderer_BluetoothChat1.setXTitle(""); // another one's name is "THE
													// END OF THIS MONTH
			mRenderer_BluetoothChat1.setYTitle("");
			if (Constant.widthPixels <= 480) { // 480*720
				mRenderer_BluetoothChat1.setAxisTitleTextSize(24);
			} else if (Constant.widthPixels > 480 && Constant.widthPixels <= 720) { // 720*1080
				mRenderer_BluetoothChat1.setAxisTitleTextSize(32);
			} else if (Constant.widthPixels >= 1080) { // 1080*1920
				mRenderer_BluetoothChat1.setAxisTitleTextSize(40);
			}
			mRenderer_BluetoothChat1.setShowGridX(true);
			mRenderer_BluetoothChat1.setGridColor(Color.rgb(255, 255, 255));
			mRenderer_BluetoothChat1.setXLabelsColor(Color.WHITE);
			mRenderer_BluetoothChat1.setXLabels(0); // sets the number of integer
													// labels to appear
			mRenderer_BluetoothChat1.addXTextLabel(1, ""); // set X Text
			mRenderer_BluetoothChat1.setShowGridY(true);
			mRenderer_BluetoothChat1.setYLabelsColor(0, Color.WHITE); // y축 값 색깔
			mRenderer_BluetoothChat1.setYLabelsAngle(0);
			mRenderer_BluetoothChat1.setBarSpacing(0);
			mRenderer_BluetoothChat1.setXAxisMin(0);
			mRenderer_BluetoothChat1.setXAxisMax(2);
			mRenderer_BluetoothChat1.setYAxisMin(0);
			mRenderer_BluetoothChat1.setYAxisMax(25000);
			mRenderer_BluetoothChat1
					.addSeriesRenderer(mCurrentRenderer_BluetoothChat1);
		}

	// * gaugeView2
	public static GraphicalView mChart_BluetoothChat2;
	public static XYSeries mCurrentSeries_BluetoothChat2;
	public static XYMultipleSeriesDataset mDataset_BluetoothChat2 = new XYMultipleSeriesDataset();
	public static SimpleSeriesRenderer mCurrentRenderer_BluetoothChat2;
	public static XYMultipleSeriesRenderer mRenderer_BluetoothChat2 = new XYMultipleSeriesRenderer();
	public static LinearLayout layout_BluetoothChat2;

	void initGaugeLayout_BluetoothChat2() {
		layout_BluetoothChat2 = (LinearLayout) findViewById(R.id.ll_BluetoothChat_chart2);
		if (mChart_BluetoothChat2 == null)
			initChart_BluetoothChat2();
		mChart_BluetoothChat2 = ChartFactory
				.getBarChartView(this, mDataset_BluetoothChat2,
						mRenderer_BluetoothChat2, Type.STACKED); // * modified
		layout_BluetoothChat2.addView(mChart_BluetoothChat2);
	}

	void initChart_BluetoothChat2() {
		mCurrentSeries_BluetoothChat2 = new XYSeries("");
		mDataset_BluetoothChat2.addSeries(mCurrentSeries_BluetoothChat2);

		mCurrentRenderer_BluetoothChat2 = new XYSeriesRenderer();
		mCurrentRenderer_BluetoothChat2.setColor(Color.BLACK);
		mCurrentRenderer_BluetoothChat2.setDisplayChartValues(true);
		mCurrentRenderer_BluetoothChat2.setChartValuesTextAlign(Align.CENTER);
		if (Constant.widthPixels <= 480) {
			mCurrentRenderer_BluetoothChat2.setChartValuesTextSize(15);
		} else if (Constant.widthPixels > 480 && Constant.widthPixels <= 720) {
			mCurrentRenderer_BluetoothChat2.setChartValuesTextSize(20);
		} else if (Constant.widthPixels >= 1080) {
			mCurrentRenderer_BluetoothChat2.setChartValuesTextSize(25);
		}
		mRenderer_BluetoothChat2.setLabelsColor(Color.rgb(255, 255, 255)); // *
																			// "title + label"'s
																			// color
		if (Constant.widthPixels <= 480) {
			mRenderer_BluetoothChat2.setLabelsTextSize(20);
		} else if (Constant.widthPixels > 480 && Constant.widthPixels <= 720) {
			mRenderer_BluetoothChat2.setLabelsTextSize(30);
		} else if (Constant.widthPixels >= 1080) {
			mRenderer_BluetoothChat2.setLabelsTextSize(40);
		}
		int[] margins_BluetoothChat2 = new int[] { 0, 0, 0, 0 }; // {top, left,
																	// bottom,
																	// right}
		if (Constant.widthPixels <= 480) {
			margins_BluetoothChat2 = new int[] { 40, 90, -15, 50 };
		} else if (Constant.widthPixels > 480 && Constant.widthPixels <= 720) {
			margins_BluetoothChat2 = new int[] { 50, 120, -25, 60 };
		} else if (Constant.widthPixels == 1080) { // 1080*1920
			margins_BluetoothChat2 = new int[] { 60, 150, -35, 70 };
		}
		mRenderer_BluetoothChat2.setMargins(margins_BluetoothChat2);
		mRenderer_BluetoothChat2.setApplyBackgroundColor(true);
		mRenderer_BluetoothChat2.setPanEnabled(false, false); // * fix graph
		mRenderer_BluetoothChat2.setZoomEnabled(false, false); // * enable zoom
		mRenderer_BluetoothChat2.setYLabelsAlign(Align.RIGHT);
		mRenderer_BluetoothChat2.setXTitle(""); // another one's name is "THE
												// END OF THIS MONTH
		mRenderer_BluetoothChat2.setYTitle("");
		if (Constant.widthPixels <= 480) { // 480*720
			mRenderer_BluetoothChat2.setAxisTitleTextSize(24);
		} else if (Constant.widthPixels > 480 && Constant.widthPixels <= 720) { // 720*1080
			mRenderer_BluetoothChat2.setAxisTitleTextSize(32);
		} else if (Constant.widthPixels >= 1080) { // 1080*1920
			mRenderer_BluetoothChat2.setAxisTitleTextSize(40);
		}
		mRenderer_BluetoothChat2.setShowGridX(true);
		mRenderer_BluetoothChat2.setGridColor(Color.rgb(255, 255, 255));
		mRenderer_BluetoothChat2.setXLabelsColor(Color.WHITE);
		mRenderer_BluetoothChat2.setXLabels(0); // sets the number of integer
												// labels to appear
		mRenderer_BluetoothChat2.addXTextLabel(1, ""); // set X Text
		mRenderer_BluetoothChat2.setShowGridY(true);
		mRenderer_BluetoothChat2.setYLabelsColor(0, Color.WHITE); // y축 값 색깔
		mRenderer_BluetoothChat2.setYLabelsAngle(0);
		mRenderer_BluetoothChat2.setBarSpacing(0);
		mRenderer_BluetoothChat2.setXAxisMin(0);
		mRenderer_BluetoothChat2.setXAxisMax(2);
		mRenderer_BluetoothChat2.setYAxisMin(0);
		mRenderer_BluetoothChat2.setYAxisMax(25000);
		mRenderer_BluetoothChat2
				.addSeriesRenderer(mCurrentRenderer_BluetoothChat2);
	}

	@Override
	public void onStart() {
		super.onStart();
		if (D)
			Log.e(TAG, "++ ON START ++");
		if (D_SON)
			Log.d("SON", "BluetoothChat / onStart()");

		// If BT is not on, request that it be enabled.
		// setupChat() will then be called during onActivityResult
		// *point* ó�� ����ǰ� �ߴ�, Bluetooth Ȱ��ȭ ����â
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			// Otherwise, setup the chat session
		} else {
			if (mChatService == null)
				setupChat();
		}

		if (D_SON)
			Log.d("SON", "auto pairing test");
		// *auto pairing test*
		/*
		 * if (mBluetoothAdapter.isEnabled()){ BluetoothDevice device =
		 * mBluetoothAdapter.getRemoteDevice(BLUETOOTH_SERIAL);
		 * mChatService.connect(device, false); BLUETOOTH_ON = 1; }
		 */
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
		if (D_SON)
			Log.d(TAG, "setupChat()");

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
				// byte[] readBuf = new byte[1024];
				// readBuf = (byte[]) msg.obj;

				for (int i = 0; i < 8; i++)
					if (D_SON)
						Log.d("SON", "readBuf[] : " + readBuf[i]);
				if (D_SON)
					Log.d("SON",
							"---------------------------------------------------");

				String readBufToStr = BT_TypeCasting.byteToHex(readBuf); // *point*
																			// byteToHex
				if (D_SON)
					Log.d("SON", "readBufToStr : " + readBufToStr);

				BT_StringCutter ssc = new BT_StringCutter(readBufToStr);
				ssc.Cutting();
				String OK_ACK = ssc.get_ACK();

				String OK_CURRENT1 = ssc.get_CURRENT1();
				String OK_VOLTAGE1 = ssc.get_VOLTAGE1();

				CURRENT_String = ssc.get_CURRENT();
				VOLTAGE_String = ssc.get_VOLTAGE();
				// Log.d("SON", "CUR & VOL String : " + CURRENT_String + " & "+
				// VOLTAGE_String);
				if (D_SON)
					Log.d("SON", "CALIB : " + CURRENT_String + " & "
							+ VOLTAGE_String);
				if (D_SON)
					Log.d("SON", "CALIB : " + CURRENT_String + " & "
							+ VOLTAGE_String);

				{
					// ************
					CURRENT = (Math.round(BT_TypeCasting
							._8HexToDec(CURRENT_String)));
					// Log.d("SON", "CURRENT : " + CURRENT);
					CURRENT /= 10000;
					// Log.d("SON", "CURRENT/10000 : " + CURRENT);
				}
				VOLTAGE = BT_TypeCasting.V_Gain(0.1 * BT_TypeCasting
						._4HexToDec(VOLTAGE_String));
				// VOLTAGE = Son_TypeCasting.V_Gain(VOLTAGE);
				if (D_SON)
					Log.d("SON", "this");
				// *point* �� ���� �����ǿ� Data�� display�ȴ�
				if (OK_ACK.equals("06")) {
					if (VOLTAGE >= 210 && VOLTAGE <= 230) {
						RECEIVE_DATA_OK = 1;
					} else if (VOLTAGE >= 135 && VOLTAGE <= 145) {
						VOLTAGE = Math.round(VOLTAGE * 1.56029 * 10000) / 10000;
						RECEIVE_DATA_OK = 1;
					} else {
						RECEIVE_DATA_OK = 0;
					}
				} else
					RECEIVE_DATA_OK = 0;

				String readMessage = new String(readBuf, 0, msg.arg1);
				// mConversationArrayAdapter.add(mConnectedDeviceName+":  " +
				// readMessage);
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
			if (STT.get_global_time() % 4 == 0)
				btn_BluetoothChat_IntentToMain.setTextColor(Color.BLACK);
			else
				btn_BluetoothChat_IntentToMain.setTextColor(Color.WHITE);
			// mOutEditText.setText(mOutStringBuffer);

			// else if(mBluetoothAdapter.disable()) BLUETOOTH_ON = 0;

			// *test end*

			// *
			if (BLUETOOTH_STATE_SON == 1) {
				if (img_bluetoothimage != null)
					img_bluetoothimage
							.setBackgroundResource(R.drawable.bluetooth_connect);
				if (btn_BluetoothChat_IntentToMain != null)
					btn_BluetoothChat_IntentToMain
							.setText("If you want to see more information,\n click this button");
				if (RECEIVE_DATA_OK == 1) {

					// *chart
					if (tv_BluetoothChat_charge_now != null
							&& tv_BluetoothChat_charge_endofmonth != null) {
						BluetoothChat_charge_now = Constant.this_year_charge[Class_Time
								.getCurMonth()];
						if (SampleDataTable.addOn == 1) {
							BluetoothChat_charge_endofmonth = BluetoothChat_charge_now;
						} else {
							BluetoothChat_charge_endofmonth = 460 + (((BluetoothChat_charge_now - 460) / Class_Time
									.getCurDay()) * 30);
						}
						tv_BluetoothChat_charge_now
								.setText(BluetoothChat_charge_now + " WON");
						tv_BluetoothChat_charge_endofmonth
								.setText(BluetoothChat_charge_endofmonth
										+ " WON");
						Log.d("SON", "BT...." + BluetoothChat_charge_now
								+ "   " + BluetoothChat_charge_endofmonth);
					}

					// *
					if (Constant.powerSettingDeactivated == true) {
						Class_Data.Data_CURRENT_mA = CURRENT; // mA
						Class_Data.Data_CURRENT_A = Class_Data.Data_CURRENT_mA / 1000; // A
						Class_Data.Data_VOLTAGE = VOLTAGE; // V
						Class_Data.Data_POWER = Class_Data.Data_VOLTAGE
								* Class_Data.Data_CURRENT_A * Constant.speedUp; // W
																				// =
																				// VI
					} else if (Constant.powerSettingDeactivated == false) {
						Class_Data.Data_CURRENT_mA = 1000 * Constant.powerSetting / 220;
						Class_Data.Data_CURRENT_A = Class_Data.Data_CURRENT_mA / 1000;
						Class_Data.Data_VOLTAGE = 220.0;
						Class_Data.Data_POWER = Constant.powerSetting
								* Constant.speedUp;
					}
				}
				if (STT.get_grid_time_hour() > 0) {
					tv_time.setText(STT.get_grid_time_hour() + " Hour "
							+ STT.get_grid_time_minute() + " Min "
							+ STT.get_grid_time_second() + " Sec");
				} else if (STT.get_grid_time_minute() > 0) {
					tv_time.setText(STT.get_grid_time_minute() + " Min "
							+ STT.get_grid_time_second() + " Sec");
				} else {
					tv_time.setText(STT.get_grid_time_second() + " Sec");
				}
				// *point* ��������� �ٸ� �� �ֱ� ������, �� �κ��� �����ϰ�
				// �ٲ������.
				if (CURRENT >= 0.0048) {
					STT.up_grid_time();
				} else {
				}

			} else {
				if (img_bluetoothimage != null) {
					if (STT.get_global_time() % 4 == 0)
						img_bluetoothimage
								.setBackgroundResource(R.drawable.bluetooth_disconnect1);
					else if (STT.get_global_time() % 4 == 2)
						img_bluetoothimage
								.setBackgroundResource(R.drawable.bluetooth_disconnect2);
				}
				if (btn_BluetoothChat_IntentToMain != null)
					btn_BluetoothChat_IntentToMain
							.setText("if you want to connect \nwith board\nClick [Menu Button]");
				if (Constant.powerSettingDeactivated == true) {
					Class_Data.Data_CURRENT_mA = 0;
					Class_Data.Data_CURRENT_A = Class_Data.Data_CURRENT_mA / 1000;
					Class_Data.Data_VOLTAGE = 0;
					Class_Data.Data_POWER = 0 * Constant.speedUp;
				} else if (Constant.powerSettingDeactivated == false) {
					Class_Data.Data_CURRENT_mA = 1000 * Constant.powerSetting / 220;
					Class_Data.Data_CURRENT_A = Class_Data.Data_CURRENT_mA / 1000;
					Class_Data.Data_VOLTAGE = 220.0;
					Class_Data.Data_POWER = Constant.powerSetting
							* Constant.speedUp;
				}
				// *test*
				// tv_BluetoothChat_charge_now.setText("0 WON");
				// tv_BluetoothChat_charge_endofmonth.setText("0 WON");

				// *chart
				if (tv_BluetoothChat_charge_now != null
						&& tv_BluetoothChat_charge_endofmonth != null) {
					BluetoothChat_charge_now = Constant.this_year_charge[Class_Time
							.getCurMonth()];
					if (SampleDataTable.addOn == 1) {
						BluetoothChat_charge_endofmonth = BluetoothChat_charge_now;
					} else {
						BluetoothChat_charge_endofmonth = 460 + (((BluetoothChat_charge_now - 460) / Class_Time
								.getCurDay()) * 30);
					}
					tv_BluetoothChat_charge_now
							.setText(BluetoothChat_charge_now + " WON");
					tv_BluetoothChat_charge_endofmonth
							.setText(BluetoothChat_charge_endofmonth + " WON");
					Log.d("SON", "BT...." + BluetoothChat_charge_now + "   "
							+ BluetoothChat_charge_endofmonth);
				}
			}

			// *chart
			if (mChart_BluetoothChat1 != null)
				mChart_BluetoothChat1.repaint();
			if (mCurrentSeries_BluetoothChat1 != null
					&& Constant.update_RealTime_START == 1) {
				int tmp = Math.round(Constant.this_year_charge[Class_Time.getCurMonth()]);
				mCurrentSeries_BluetoothChat1.add(1, tmp);
			}
			if (mChart_BluetoothChat2 != null)
				mChart_BluetoothChat2.repaint();
			if (mCurrentSeries_BluetoothChat2 != null
					&& Constant.update_RealTime_START == 1) {
				mCurrentSeries_BluetoothChat2.add(1,
						BluetoothChat_charge_endofmonth);
			}
		}
	};

	// **********************************************************************************************************
	// **********************************************************************************************************

	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		// backPressCloseHandler.onBackPressed();
	}
}
