package son.funkydj3.smartemeter.gauge;

import son.funkydj3.smartemeter.R;
import son.funkydj3.smartemeter.etc.Class_Data;
import son.funkydj3.smartemeter.etc.Constant;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class GaugeActivity extends Activity {
	public static TextView tv_gauge_power;
	public static TextView tv_gauge_voltage;
	public static TextView tv_gauge_current;
	public static Thread_Gauge TG;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gauge);

		tv_gauge_power = (TextView) findViewById(R.id.tv_gauge_power);
		tv_gauge_voltage = (TextView) findViewById(R.id.tv_gauge_voltage);
		tv_gauge_current = (TextView) findViewById(R.id.tv_gauge_current);

		TG = new Thread_Gauge(mHandler_Gauge);
		TG.setDaemon(true);
		TG.start();
	}

	public static Handler mHandler_Gauge = new Handler() {
		public void handleMessage(Message msg) {
			tv_gauge_power.setText(Double.toString((Math.round(Class_Data.Data_POWER*1000d))/1000d));
			tv_gauge_voltage.setText(Double.toString((Math.round(Class_Data.Data_VOLTAGE*1000d))/1000d));
			tv_gauge_current.setText(Double.toString((Math.round(Class_Data.Data_CURRENT*1000d))/1000d));
		}
	};

}
