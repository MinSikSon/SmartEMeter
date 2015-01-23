package son.funkydj3.smartemeter.achartengine;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import son.funkydj3.smartemeter.R;
import son.funkydj3.smartemeter.etc.Class_Color;
import son.funkydj3.smartemeter.etc.Constant;
import son.funkydj3.smartemeter.thread.Thread2;
import son.funkydj3.smartemeter.thread.Thread3;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChartMonth extends Fragment {
	public GraphicalView mChart2; // * Study about "GraphicalView"
	private XYSeries mCurrentSeries2; // * series : set of numbers
	private XYMultipleSeriesDataset mDataset2 = new XYMultipleSeriesDataset();
	private SimpleSeriesRenderer mCurrentRenderer2;
	private XYMultipleSeriesRenderer mRenderer2 = new XYMultipleSeriesRenderer();
	
	public static ChartMonth newInstance(String title) {
		ChartMonth pageFragment = new ChartMonth();
		Bundle bundle = new Bundle();
		bundle.putString("title", title);
		pageFragment.setArguments(bundle);
		return pageFragment;
	}

	Thread2 t2;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		t2 = new Thread2(mHandler2);
		t2.start();
	}
	private Handler mHandler2 = new Handler() {
		public void handleMessage(Message msg) {
			if(Constant.sum_this_month_kWh >= 264){
				//mCurrentRenderer.setColor(Color.rgb(255, 0, 0)); // * RED
				mCurrentRenderer2.setColor(Class_Color.RED()); // * RED
			}
			double tmp1;
			for(int i = 1 ; i < 32 ; i++){
				tmp1 = Constant.this_month_kWh[i];
				tmp1 = Math.round(tmp1*1000d)/1000d; // * get point 3
				mCurrentSeries2.add(i, tmp1);
			}
			if(mChart2 != null) mChart2.repaint();
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_chart_month, container, false);
		TextView textView = (TextView) view.findViewById(R.id.textView2);
		textView.setText(getArguments().getString("title"));

		LinearLayout layout2 = (LinearLayout)view.findViewById(R.id.chart_month);
		//if(mChart == null){
			if (mChart2 == null) initChart2();
			// * Study about "ChartFactory"
			mChart2 = ChartFactory.getBarChartView(view.getContext(), mDataset2, mRenderer2, Type.DEFAULT);
			layout2.addView(mChart2);
		//} else{
			//mChart.repaint();
		//}
		return view;
	}
	
	private void initChart2(){
		mCurrentSeries2 = new XYSeries("kWh");
		mDataset2.addSeries(mCurrentSeries2);
		
		mCurrentRenderer2 = new XYSeriesRenderer();
		mCurrentRenderer2.setColor(Color.rgb(18, 105, 120));
		//mCurrentRenderer.setColor(Class_Color.GREEN()); // * GREEN
		mCurrentRenderer2.setDisplayChartValues(true);
		mCurrentRenderer2.setChartValuesTextAlign(Align.CENTER);
		if(Constant.widthPixels <= 480){
			mCurrentRenderer2.setChartValuesTextSize(8);
		}else if(Constant.widthPixels > 480 && Constant.widthPixels <= 720){
			mCurrentRenderer2.setChartValuesTextSize(13);
		}else if(Constant.widthPixels >= 1080){
			mCurrentRenderer2.setChartValuesTextSize(18);
		}
		
	    //mRenderer.setClickEnabled(false);

		// double[] zoomlimits = new double[] {0,20,0,40}; // {zoomMinimumX, zoomMaximumX, zoomMinimumY, zoomMaximumY}
		// mRenderer.setZoomLimits(zoomlimits);
		/*mRenderer2.setChartTitle("Month Electric Charge");
		if(Constant.widthPixels <= 480){
			mRenderer2.setChartTitleTextSize(40);
		}else if(Constant.widthPixels > 480 && Constant.widthPixels <= 720){
			mRenderer2.setChartTitleTextSize(60);
		}else if(Constant.widthPixels >= 1080){
			mRenderer2.setChartTitleTextSize(80);
		}*/
		mRenderer2.setLabelsColor(Color.rgb(38, 43, 49)); // * "title + label"'s color
		if(Constant.widthPixels <= 480){
			mRenderer2.setLabelsTextSize(12);
		}else if(Constant.widthPixels > 480 && Constant.widthPixels <= 720){
			mRenderer2.setLabelsTextSize(18);
		}else if(Constant.widthPixels >= 1080){
			mRenderer2.setLabelsTextSize(24);
		}
		
		int[] margins2 = new int[] {0,0,0,0}; // {top, left, bottom, right}
		if(Constant.widthPixels <= 480){
			margins2 = new int[] {10,40,0,10};
		}else if(Constant.widthPixels > 480 && Constant.widthPixels <= 720){
			margins2 = new int[] {20,60,0,20};
		}else if(Constant.widthPixels == 1080){ // 1080*1920
			margins2 = new int[] {30,80,0,30};
		}
		mRenderer2.setMargins(margins2);
		mRenderer2.setMarginsColor(Color.rgb(241, 233, 211));
		
		mRenderer2.setApplyBackgroundColor(true);
		//mRenderer.setBackgroundColor(Color.rgb(255, 228, 0));
		
		
		mRenderer2.setPanEnabled(false, true); // * fix graph
		double[] panLimits2 = new double[] {0,31.5,0,100};
		mRenderer2.setPanLimits(panLimits2);
		mRenderer2.setZoomEnabled(false, false); // * enable zoom
		double[] range2 = new double[] {0,31.5,0,50};
		mRenderer2.setInitialRange(range2);
		mRenderer2.setYLabelsAlign(Align.RIGHT);
		mRenderer2.setXTitle("Days");
		mRenderer2.setYTitle("kWh");
		if(Constant.widthPixels <= 480){ // 480*720
			mRenderer2.setAxisTitleTextSize(16);
		}else if(Constant.widthPixels > 480 && Constant.widthPixels <= 720){ // 720*1080
			mRenderer2.setAxisTitleTextSize(24);
		}else if(Constant.widthPixels >= 1080){ // 1080*1920
			mRenderer2.setAxisTitleTextSize(32);
		}

		
		mRenderer2.setShowGridX(true);
		mRenderer2.setGridColor(Color.rgb(93, 93, 93));
		mRenderer2.setXLabelsColor(Color.BLACK);
		mRenderer2.setXLabelsAngle(320);
		mRenderer2.setXLabels(30); // sets the number of integer labels to appear
		for(int i = 1 ; i < 31 ; i++){
			mRenderer2.addXTextLabel(i, Integer.toString(i));
		}
		
		
		mRenderer2.setShowGridY(true);
		//mRenderer2.setYLabels(5);
		mRenderer2.setYLabelsColor(0, Color.BLACK);
		mRenderer2.setYLabelsAngle(0);
		for(int i = 0 ; i < 21 ; i++)
		mRenderer2.addYTextLabel(i*5, Integer.toString(i*5));
		
		
		mRenderer2.setBarSpacing(0.6);
		mRenderer2.setXAxisMin(0.5);
		mRenderer2.setXAxisMax(31.5);
		mRenderer2.setYAxisMin(0);
		mRenderer2.setYAxisMax(25);
		
		//mRenderer2.setPointSize(1.0f);
	    mRenderer2.addSeriesRenderer(mCurrentRenderer2);
	}
}
