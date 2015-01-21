package son.funkydj3.smartemeter.achartengine;

import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import son.funkydj3.smartemeter.R;
import son.funkydj3.smartemeter.etc.Calculator;
import son.funkydj3.smartemeter.etc.Class_Color;
import son.funkydj3.smartemeter.etc.Constant;
import son.funkydj3.smartemeter.thread.Thread1;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;



public class ChartYear extends Fragment {
	public GraphicalView mChart; // * Study about "GraphicalView"
	private XYSeries mCurrentSeries; // * series : set of numbers
	private XYSeries mCurrentSeries1_2; // *** 
	private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
	private SimpleSeriesRenderer mCurrentRenderer; // ***
	private SimpleSeriesRenderer mCurrentRenderer1_2; // ***
	private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
	
	private Button btn_display;
	private TextView tv_sum_last_year_charge;
	
	public static ChartYear newInstance(String title) {
		ChartYear pageFragment = new ChartYear();
		Bundle bundle = new Bundle();
		bundle.putString("title", title);
		pageFragment.setArguments(bundle);
		return pageFragment;
	}

	Thread1 t1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		t1 = new Thread1(mHandler);
		t1.start();
	}
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if(Constant.sum_this_year_kWh >= 3168){
				//mCurrentRenderer.setColor(Color.rgb(255, 0, 0)); // * RED
				mCurrentRenderer.setColor(Class_Color.RED()); // * RED
			}
			// *modify* this_year_charge will be modified with data from db
			if(mDataset.getSeriesCount() == 2){
				// *modify* last_year_charge will be modified with data from db
				for(int i = 1 ; i< 13 ; i++){
					mCurrentSeries1_2.add(i,Constant.last_year_charge[i-1]); // ***
				}
			}
			
			double tmp1;
			for(int i = 1 ; i < 13 ; i++){
				tmp1 = Constant.this_year_kWh[i];
				tmp1 = Math.round(tmp1*1000d)/1000d; // * get point 3
				Constant.this_year_charge[i] = (int)(tmp1 * 300);
				mCurrentSeries.add(i, Constant.this_year_charge[i]);
			}
			if(mChart != null) mChart.repaint();
			
			// *tv
		}
	};

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_chart_year, container, false);
		TextView textView = (TextView) view.findViewById(R.id.textView3);
		// setTextView from Main message
		textView.setText(getArguments().getString("title"));
		
		LinearLayout layout = (LinearLayout)view.findViewById(R.id.chart_year);
		if (mChart == null) initChart();
		mChart = ChartFactory.getBarChartView(view.getContext(), mDataset, mRenderer, Type.DEFAULT);
		//mChart = ChartFactory.getLineChartView(view.getContext(), mDataset, mRenderer);
		layout.addView(mChart);
		
		
		Log.d("SON", "this");
		btn_display = (Button)view.findViewById(R.id.btn_display_add);
		btn_display.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mDataset.getSeriesCount() == 2){
					mDataset.removeSeries(mDataset.getSeriesCount()-1); // ***
					mRenderer.removeSeriesRenderer(mCurrentRenderer1_2); // ***
				}else{
					Random random = new Random();
					Constant.last_year_charge = new int[]{
							(130+random.nextInt(150))*100, (130+random.nextInt(150))*100,
							(130+random.nextInt(150))*100, (130+random.nextInt(150))*100,
							(130+random.nextInt(150))*100, (130+random.nextInt(150))*100,
							(130+random.nextInt(150))*100, (130+random.nextInt(150))*100,
							(130+random.nextInt(150))*100, (130+random.nextInt(150))*100,
							(130+random.nextInt(150))*100, (130+random.nextInt(150))*100
					};
					Calculator.sumLastYearCharge();
					tv_sum_last_year_charge.setText("Last Year Charge : "+Math.round(Constant.sum_last_year_charge) +" WON");
					mDataset.addSeries(mCurrentSeries1_2); // ***
					mRenderer.addSeriesRenderer(mCurrentRenderer1_2); // ***
				}
			}
		});
		tv_sum_last_year_charge = (TextView)view.findViewById(R.id.tv_sum_last_year_charge);
		
		return view;
	}
	
	
	
	private void initChart(){
		mCurrentSeries = new XYSeries("this year");
		mDataset.addSeries(mCurrentSeries);
		mCurrentSeries1_2 = new XYSeries("last year"); // ***
		//mDataset.addSeries(mCurrentSeries2); // ***
		
		mCurrentRenderer = new XYSeriesRenderer();
		mCurrentRenderer.setColor(Color.rgb(18, 105, 120));
		//mCurrentRenderer.setColor(Class_Color.GREEN()); // * GREEN
		mCurrentRenderer.setDisplayChartValues(true);
		mCurrentRenderer.setChartValuesTextAlign(Align.CENTER);
		if(Constant.widthPixels <= 480){
			mCurrentRenderer.setChartValuesTextSize(15);
		}else if(Constant.widthPixels > 480 && Constant.widthPixels <= 720){
			mCurrentRenderer.setChartValuesTextSize(20);
		}else if(Constant.widthPixels >= 1080){
			mCurrentRenderer.setChartValuesTextSize(25);
		}
		
		// ***
		mCurrentRenderer1_2 = new XYSeriesRenderer();
		mCurrentRenderer1_2.setColor(Color.rgb(165, 102, 255));
		//mCurrentRenderer.setColor(Class_Color.GREEN()); // * GREEN
		mCurrentRenderer1_2.setDisplayChartValues(true);
		mCurrentRenderer1_2.setChartValuesTextAlign(Align.CENTER);
		if(Constant.widthPixels <= 480){
			mCurrentRenderer1_2.setChartValuesTextSize(15);
		}else if(Constant.widthPixels > 480 && Constant.widthPixels <= 720){
			mCurrentRenderer1_2.setChartValuesTextSize(20);
		}else if(Constant.widthPixels >= 1080){
			mCurrentRenderer1_2.setChartValuesTextSize(25);
		}
		// ***
		
	    //mRenderer.setClickEnabled(false);

		// double[] zoomlimits = new double[] {0,20,0,40}; // {zoomMinimumX, zoomMaximumX, zoomMinimumY, zoomMaximumY}
		// mRenderer.setZoomLimits(zoomlimits);
		mRenderer.setChartTitle("Year Electric Charge");
		if(Constant.widthPixels <= 480){
			mRenderer.setChartTitleTextSize(40);
		}else if(Constant.widthPixels > 480 && Constant.widthPixels <= 720){
			mRenderer.setChartTitleTextSize(60);
		}else if(Constant.widthPixels >= 1080){
			mRenderer.setChartTitleTextSize(80);
		}
		mRenderer.setLabelsColor(Color.BLACK); // * "title + label"'s color
		if(Constant.widthPixels <= 480){
			mRenderer.setLabelsTextSize(18);
		}else if(Constant.widthPixels > 480 && Constant.widthPixels <= 720){
			mRenderer.setLabelsTextSize(27);
		}else if(Constant.widthPixels >= 1080){
			mRenderer.setLabelsTextSize(36);
		}
		
		int[] margins = new int[] {0,0,0,0}; // {top, left, bottom, right}
		if(Constant.widthPixels <= 480){
			margins = new int[] {70,50,0,0};
		}else if(Constant.widthPixels > 480 && Constant.widthPixels <= 720){
			margins = new int[] {105,65,0,0};
		}else if(Constant.widthPixels == 1080){ // 1080*1920
			margins = new int[] {140,80,0,0};
		}
		mRenderer.setMargins(margins);
		mRenderer.setMarginsColor(Color.WHITE);
		
		mRenderer.setApplyBackgroundColor(true);
		//mRenderer.setBackgroundColor(Color.rgb(255, 228, 0));
		
		
		mRenderer.setPanEnabled(false, true); // * fix graph
		double[] panLimits = new double[] {0,0,0,50000};
		mRenderer.setPanLimits(panLimits);
		mRenderer.setZoomEnabled(false, false); // * enable zoom
		double[] range = new double[] {0,12,0,40000};
		mRenderer.setInitialRange(range);
		mRenderer.setYLabelsAlign(Align.RIGHT);
		mRenderer.setXTitle("Months");
		mRenderer.setYTitle("WON");
		if(Constant.widthPixels <= 480){ // 480*720
			mRenderer.setAxisTitleTextSize(16);
		}else if(Constant.widthPixels > 480 && Constant.widthPixels <= 720){ // 720*1080
			mRenderer.setAxisTitleTextSize(24);
		}else if(Constant.widthPixels >= 1080){ // 1080*1920
			mRenderer.setAxisTitleTextSize(32);
		}

		
		mRenderer.setShowGridX(true);
		mRenderer.setGridColor(Color.rgb(93, 93, 93));
		mRenderer.setXLabelsColor(Class_Color.BLACK());
		mRenderer.setXLabels(0); // sets the number of integer labels to appear
		for(int i = 1 ; i<13 ; i++){
			mRenderer.addXTextLabel(i, Integer.toString(i));
		}
		
		
		mRenderer.setShowGridY(true);
		mRenderer.setYLabels(5);
		mRenderer.setYLabelsColor(0, Class_Color.BLACK());
		mRenderer.setYLabelsAngle(310);
		int num_buf = 2;
		String num_buf2 = "500";
		for(int i = 1 ; i < 22 ; i++){
			String value = Integer.toString(num_buf) + "," + num_buf2;
			Log.d("SON", value);
			mRenderer.addYTextLabel(i*2500, value);
			if(i%2 == 1) {
				num_buf += 3;
				num_buf2 = "000";
			}
			else {
				num_buf += 2;
				num_buf2 = "500";
			}
		}
		
		mRenderer.setBarSpacing(0.7);
		mRenderer.setXAxisMin(0.5);
		mRenderer.setXAxisMax(12.8);
		mRenderer.setYAxisMin(0);
		mRenderer.setYAxisMax(30000);
		
		//mRenderer.setPointSize(1.0f);
	    mRenderer.addSeriesRenderer(mCurrentRenderer);
	    //mRenderer.addSeriesRenderer(mCurrentRenderer2);
	}
	
	
}
