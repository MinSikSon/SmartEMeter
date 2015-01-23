package son.funkydj3.smartemeter;

import son.funkydj3.smartemeter.BluetoothChat.BluetoothChat;
import son.funkydj3.smartemeter.achartengine.Chart;
import son.funkydj3.smartemeter.etc.Class_Color;
import son.funkydj3.smartemeter.etc.Constant;
import son.funkydj3.smartemeter.thread.Thread_Second_Timer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private Button btn_Main1;
	private Button btn_Main2;
	//private Button btn_Main3;
	//private Button btn_Main4;
	private Button btn_Main5;
	private Button btn_Main6;
	
	private ImageView img_Main1;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("SON", "MainActivity - onCreate()");
		init();
		InfoDeviceDisplay();
		if (Constant.initConstantCheck == 0) {
			Constant.initConstant();
			Constant.initConstantCheck = 1;
		}

		Thread_Second_Timer TT = new Thread_Second_Timer(mHandler);
		TT.start();
		Log.d("SON", "TIME : " + System.currentTimeMillis());
	}
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if(Constant.PUBLIC_TIME % 10 == 0){
				img_Main1.setImageResource(R.drawable.img_main1);
			}else if(Constant.PUBLIC_TIME % 10 == 5){
				img_Main1.setImageResource(R.drawable.img_main2);
			}else if(Constant.PUBLIC_TIME % 10 == 9){
				img_Main1.setImageResource(R.drawable.img_main3);
			}
		}
	};

	private void init() {
		btn_Main1 = (Button) findViewById(R.id.btn_Main1);
		btn_Main1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, BluetoothChat.class);
				startActivity(i);
			}
		});
		btn_Main2 = (Button) findViewById(R.id.btn_Main2);
		btn_Main2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent ii = new Intent(MainActivity.this, Chart.class);
				startActivity(ii);
			}
		});
		img_Main1 = (ImageView)findViewById(R.id.img_Main1);
		/*btn_Main3 = (Button) findViewById(R.id.btn_Main3);
		btn_Main3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent iii = new Intent(MainActivity.this, Chart.class);
				startActivity(iii);
			}
		});
		btn_Main4 = (Button) findViewById(R.id.btn_Main3);
		btn_Main4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent iii = new Intent(MainActivity.this, Chart.class);
				startActivity(iii);
			}
		});*/
		btn_Main5 = (Button) findViewById(R.id.btn_Main5);
		btn_Main5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent iii = new Intent(MainActivity.this, Chart.class);
				startActivity(iii);
			}
		});
		btn_Main6 = (Button) findViewById(R.id.btn_Main6);
		btn_Main6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent iii = new Intent(MainActivity.this, Chart.class);
				startActivity(iii);
			}
		});
	}

	// *
	private void InfoDeviceDisplay() {
		int screenLayout = getResources().getConfiguration().screenLayout;
		// Get the metrics
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Constant.widthPixels = metrics.widthPixels;
		Constant.heightPixels = metrics.heightPixels;
		Constant.densityDpi = metrics.densityDpi;
		Constant.density = metrics.density;
		Constant.scaledDensity = metrics.scaledDensity;
		Constant.xdpi = metrics.xdpi;
		Constant.ydpi = metrics.ydpi;
		Log.d("SON", "Screen W x H pixels: " + Constant.widthPixels + " x "
				+ Constant.heightPixels);
		Log.d("SON", "Screen X x Y dpi: " + Constant.xdpi + " x "
				+ Constant.ydpi);
		Log.d("SON", "density = " + Constant.density + "  scaledDensity = "
				+ Constant.scaledDensity + "  densityDpi = "
				+ Constant.densityDpi);
	}
}