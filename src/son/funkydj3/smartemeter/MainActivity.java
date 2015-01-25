package son.funkydj3.smartemeter;

import son.funkydj3.smartemeter.achartengine.Chart;
import son.funkydj3.smartemeter.etc.Constant;
import son.funkydj3.smartemeter.gauge.GaugeActivity;
import son.funkydj3.smartemeter.option.Option;
import son.funkydj3.smartemeter.saving.Saving;
import son.funkydj3.smartemeter.thread.Thread_Second_Timer;
import android.app.Activity;
import android.content.Intent;
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
	
	public static ImageView img_Main1;
	
	public static Thread_Second_Timer TT = null;
	
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

		
		if(Constant.GET_TST_STATE.equalsIgnoreCase("RUNNABLE")){
			Constant.BREAK_TST = 1;
		}
		//if(TT == null)
		//{	
			TT = new Thread_Second_Timer(mHandler);
			TT.setDaemon(true);
			TT.start();
			Log.d("SON", "MainActivity - TT.start()");
		//}
		Log.d("SON", "TIME : " + System.currentTimeMillis());
	}
	public static Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			// * width 4inch, dp 150
			if(Constant.PUBLIC_TIME % 101 == 1){
				img_Main1.setImageResource(R.drawable.img_main_spark);
			}else if(Constant.PUBLIC_TIME % 101 == 7){
				img_Main1.setImageResource(R.drawable.img_main_stair);
			}else if(Constant.PUBLIC_TIME % 101 == 13){
				img_Main1.setImageResource(R.drawable.img_main_chicago);
			}else if(Constant.PUBLIC_TIME % 101 == 19){
				img_Main1.setImageResource(R.drawable.img_main_city);
			}else if(Constant.PUBLIC_TIME % 101 == 25){
				img_Main1.setImageResource(R.drawable.img_main_dog3);
			}else if(Constant.PUBLIC_TIME % 101 == 31){
				img_Main1.setImageResource(R.drawable.img_main_goplay);
			}else if(Constant.PUBLIC_TIME % 101 == 37){
				img_Main1.setImageResource(R.drawable.img_main_donga);
			}else if(Constant.PUBLIC_TIME % 101 == 49){
				img_Main1.setImageResource(R.drawable.img_main_radiotower);
			}else if(Constant.PUBLIC_TIME % 101 == 55){
				img_Main1.setImageResource(R.drawable.img_main_spark);
			}else if(Constant.PUBLIC_TIME % 101 == 61){
				img_Main1.setImageResource(R.drawable.img_main_dog2);
			}else if(Constant.PUBLIC_TIME % 101 == 67){
				img_Main1.setImageResource(R.drawable.img_main_busan);
			}else if(Constant.PUBLIC_TIME % 101 == 73){
				img_Main1.setImageResource(R.drawable.img_main_temple);
			}else if(Constant.PUBLIC_TIME % 101 == 79){
				img_Main1.setImageResource(R.drawable.img_main_donga);
			}else if(Constant.PUBLIC_TIME % 101 == 91){
				img_Main1.setImageResource(R.drawable.img_main_berlin);
			}else if(Constant.PUBLIC_TIME % 101 == 97){
				img_Main1.setImageResource(R.drawable.img_main_dog);
			}
		}
	};

	private void init() {
		btn_Main1 = (Button) findViewById(R.id.btn_Main1);
		btn_Main1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, GaugeActivity.class);
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
				Intent iii = new Intent(MainActivity.this, Saving.class);
				startActivity(iii);
			}
		});
		btn_Main6 = (Button) findViewById(R.id.btn_Main6);
		btn_Main6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent iii = new Intent(MainActivity.this, Option.class);
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
