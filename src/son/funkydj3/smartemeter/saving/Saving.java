// set Target Charge

package son.funkydj3.smartemeter.saving;

import son.funkydj3.smartemeter.R;
import son.funkydj3.smartemeter.etc.Class_Time;
import son.funkydj3.smartemeter.etc.Constant;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Saving extends Activity implements OnClickListener {
	private Button btn_saving_15000, btn_saving_20000, btn_saving_25000,
			btn_saving_30000;
	private TextView txt_targetcharge_selected;
	private ImageView img_saving_currentphase;
	private TextView txt_saving_currentphase;

	Thread_Saving thread_saving;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saving);
		initLayout();
		thread_saving = new Thread_Saving(mHandler_saving);
		thread_saving.setDaemon(true);
		thread_saving.start();
	}
	private Handler mHandler_saving = new Handler() {
		public void handleMessage(Message msg) {
			if(((Constant.targetCharge) < Constant.this_year_charge[Class_Time.getCurMonth()])){
				img_saving_currentphase.setBackgroundResource(R.drawable.currentphase5);
				txt_saving_currentphase.setText("DANGER");
				txt_saving_currentphase.setTextColor(Color.WHITE);
			}
			else if(((Constant.targetCharge * 1) >= Constant.this_year_charge[Class_Time.getCurMonth()])
					&& ((Constant.targetCharge * 0.75) < Constant.this_year_charge[Class_Time.getCurMonth()])){
				img_saving_currentphase.setBackgroundResource(R.drawable.currentphase4);
				txt_saving_currentphase.setText("EXTREME");
				txt_saving_currentphase.setTextColor(Color.rgb(233, 40, 58));
			}else if(((Constant.targetCharge * 0.75) >= Constant.this_year_charge[Class_Time.getCurMonth()])
					&& ((Constant.targetCharge * 0.6) < Constant.this_year_charge[Class_Time.getCurMonth()])){
				img_saving_currentphase.setBackgroundResource(R.drawable.currentphase3);
				txt_saving_currentphase.setText("HIGH");
				txt_saving_currentphase.setTextColor(Color.rgb(242, 163, 50));
			}else if(((Constant.targetCharge * 0.6) >= Constant.this_year_charge[Class_Time.getCurMonth()])
					&& ((Constant.targetCharge * 0.35) < Constant.this_year_charge[Class_Time.getCurMonth()])){
				img_saving_currentphase.setBackgroundResource(R.drawable.currentphase2);
				txt_saving_currentphase.setText("MODERATE");
				txt_saving_currentphase.setTextColor(Color.rgb(252, 250, 43));
			}else if(((Constant.targetCharge * 0.35) >= Constant.this_year_charge[Class_Time.getCurMonth()])){
				img_saving_currentphase.setBackgroundResource(R.drawable.currentphase1);
				txt_saving_currentphase.setText("LOW");
				txt_saving_currentphase.setTextColor(Color.rgb(1, 196, 231));
			}
			txt_targetcharge_selected.setText(Constant.targetCharge+" WON");
		}
	};

	void initLayout() {
		txt_targetcharge_selected = (TextView)findViewById(R.id.txt_targetcharge_selected);
		btn_saving_15000 = (Button) findViewById(R.id.btn_saving_15000);
		btn_saving_20000 = (Button) findViewById(R.id.btn_saving_20000);
		btn_saving_25000 = (Button) findViewById(R.id.btn_saving_25000);
		btn_saving_30000 = (Button) findViewById(R.id.btn_saving_30000);
		btn_saving_15000.setOnClickListener(this);
		btn_saving_20000.setOnClickListener(this);
		btn_saving_25000.setOnClickListener(this);
		btn_saving_30000.setOnClickListener(this);
		img_saving_currentphase = (ImageView) findViewById(R.id.img_saving_currentphase);
		txt_saving_currentphase = (TextView) findViewById(R.id.txt_saving_currentphase);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_saving_15000:
			Constant.targetCharge = 15000;
			break;
		case R.id.btn_saving_20000:
			Constant.targetCharge = 20000;
			break;
		case R.id.btn_saving_25000:
			Constant.targetCharge = 25000;
			break;
		case R.id.btn_saving_30000:
			Constant.targetCharge = 30000;
			break;
		default:
			break;
		}
	}
}
