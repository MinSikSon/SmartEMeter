// Language : Korean , English
// Charge : won, dollar 
// speed up : x2, x3, x4... 

package son.funkydj3.smartemeter.option;

import son.funkydj3.smartemeter.R;
import son.funkydj3.smartemeter.etc.Constant;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Option extends Activity implements OnClickListener {
	private Button btn_option_speed_x1, btn_option_speed_x2,
			btn_option_speed_x3, btn_option_speed_x4, btn_option_speed_x8,
			btn_option_speed_x16;
	private Button btn_option_powerset1, btn_option_powerset2,
			btn_option_powerset3, btn_option_powerset4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("SON", "Option - start");
		setContentView(R.layout.activity_option);
		Log.d("SON", "Option - made");
		initBtn();
		Log.d("SON", "Option - made");
	}

	void initBtn() {
		btn_option_speed_x1 = (Button) findViewById(R.id.btn_option_speed_x1);
		btn_option_speed_x2 = (Button) findViewById(R.id.btn_option_speed_x2);
		btn_option_speed_x3 = (Button) findViewById(R.id.btn_option_speed_x3);
		btn_option_speed_x4 = (Button) findViewById(R.id.btn_option_speed_x4);
		btn_option_speed_x8 = (Button) findViewById(R.id.btn_option_speed_x8);
		btn_option_speed_x16 = (Button) findViewById(R.id.btn_option_speed_x16);

		btn_option_powerset1 = (Button) findViewById(R.id.btn_option_powerset1);
		btn_option_powerset2 = (Button) findViewById(R.id.btn_option_powerset2);
		btn_option_powerset3 = (Button) findViewById(R.id.btn_option_powerset3);
		btn_option_powerset4 = (Button) findViewById(R.id.btn_option_powerset4);

		btn_option_speed_x1.setOnClickListener(this);
		btn_option_speed_x2.setOnClickListener(this);
		btn_option_speed_x3.setOnClickListener(this);
		btn_option_speed_x4.setOnClickListener(this);
		btn_option_speed_x8.setOnClickListener(this);
		btn_option_speed_x16.setOnClickListener(this);

		btn_option_powerset1.setOnClickListener(this);
		btn_option_powerset2.setOnClickListener(this);
		btn_option_powerset3.setOnClickListener(this);
		btn_option_powerset4.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_option_speed_x1:
			Constant.speedUp = 1;
			break;
		case R.id.btn_option_speed_x2:
			Constant.speedUp = 2;
			break;
		case R.id.btn_option_speed_x3:
			Constant.speedUp = 3;
			break;
		case R.id.btn_option_speed_x4:
			Constant.speedUp = 4;
			break;
		case R.id.btn_option_speed_x8:
			Constant.speedUp = 8;
			break;
		case R.id.btn_option_speed_x16:
			Constant.speedUp = 16;
			break;
		case R.id.btn_option_powerset1:
			Constant.powerSettingDeactivated = true;
			break;
		case R.id.btn_option_powerset2:
			Constant.powerSettingDeactivated = false;
			Constant.powerSetting = 0.01;
			break;
		case R.id.btn_option_powerset3:
			Constant.powerSettingDeactivated = false;
			Constant.powerSetting = 0.1;
			break;
		case R.id.btn_option_powerset4:
			Constant.powerSettingDeactivated = false;
			Constant.powerSetting = 0.5;
			break;
		default:
			break;
		}
	}
}
