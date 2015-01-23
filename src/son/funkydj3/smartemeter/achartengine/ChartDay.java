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
import son.funkydj3.smartemeter.thread.Thread3;
import android.R.color;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChartDay extends Fragment {
	public GraphicalView mChart3_1; // * Study about "GraphicalView"
	private XYSeries mCurrentSeries3_1; // * series : set of numbers
	private XYMultipleSeriesDataset mDataset3_1 = new XYMultipleSeriesDataset();
	private SimpleSeriesRenderer mCurrentRenderer3_1;
	private XYMultipleSeriesRenderer mRenderer3_1 = new XYMultipleSeriesRenderer();
	
	public GraphicalView mChart3_2; // * Study about "GraphicalView"
	private XYSeries mCurrentSeries3_2; // * series : set of numbers
	private XYMultipleSeriesDataset mDataset3_2 = new XYMultipleSeriesDataset();
	private SimpleSeriesRenderer mCurrentRenderer3_2;
	private XYMultipleSeriesRenderer mRenderer3_2 = new XYMultipleSeriesRenderer();
	
	public static ChartDay newInstance(String title) {
		ChartDay pageFragment = new ChartDay();
		Bundle bundle = new Bundle();
		bundle.putString("title", title);
		pageFragment.setArguments(bundle);
		return pageFragment;
	}

	Thread3 t3;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		t3 = new Thread3(mHandler3);
		t3.start();
	}
	private Handler mHandler3 = new Handler() {
		public void handleMessage(Message msg) {
			if(Constant.sum_today_kWh >= 8){
				//mCurrentRenderer.setColor(Color.rgb(255, 0, 0)); // * RED
				mCurrentRenderer3_1.setColor(Class_Color.RED()); // * RED
				mCurrentRenderer3_2.setColor(Class_Color.RED()); // * RED
			}
			double tmp1, tmp2;
			for(int i = 1 ; i < 13 ; i++){
				tmp1 = Constant.today_kWh[i];
				tmp1 = Math.round(tmp1*1000d)/1000d; // * get point 3
				tmp2 = Constant.today_kWh[i+12];
				tmp2 = Math.round(tmp2*1000d)/1000d; // * get point 3
				mCurrentSeries3_1.add(i, tmp1);
				mCurrentSeries3_2.add(i+12, tmp2);
			}
			if(mChart3_1 != null) mChart3_1.repaint();
			if(mChart3_2 != null) mChart3_2.repaint();
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_chart_day, container, false);
		TextView textView = (TextView) view.findViewById(R.id.textView3);
		textView.setText(getArguments().getString("title"));

		LinearLayout layout3 = (LinearLayout)view.findViewById(R.id.chart_day_1);
		if(mChart3_1 == null) initChart3_1();
		mChart3_1 = ChartFactory.getBarChartView(view.getContext(), mDataset3_1, mRenderer3_1, Type.DEFAULT);
		layout3.addView(mChart3_1);
			
		LinearLayout layout3_2 = (LinearLayout)view.findViewById(R.id.chart_day_2);
		if(mChart3_2 == null) initChart3_2();
		mChart3_2 = ChartFactory.getBarChartView(view.getContext(), mDataset3_2, mRenderer3_2, Type.DEFAULT);
		layout3_2.addView(mChart3_2);
		
		return view;
	}
	
	private void initChart3_1(){
		mCurrentSeries3_1 = new XYSeries("Wh");
		mDataset3_1.addSeries(mCurrentSeries3_1);
		
		mCurrentRenderer3_1 = new XYSeriesRenderer();
		mCurrentRenderer3_1.setColor(Color.WHITE);
		//mCurrentRenderer.setColor(Class_Color.GREEN()); // * GREEN
		mCurrentRenderer3_1.setDisplayChartValues(true);
		mCurrentRenderer3_1.setChartValuesTextAlign(Align.CENTER);
		if(Constant.widthPixels <= 480){
			mCurrentRenderer3_1.setChartValuesTextSize(20);
		}else if(Constant.widthPixels > 480 && Constant.widthPixels <= 720){
			mCurrentRenderer3_1.setChartValuesTextSize(20);
		}else if(Constant.widthPixels >= 1080){
			mCurrentRenderer3_1.setChartValuesTextSize(30);
		}
		
	    //mRenderer.setClickEnabled(false);

		// double[] zoomlimits = new double[] {0,20,0,40}; // {zoomMinimumX, zoomMaximumX, zoomMinimumY, zoomMaximumY}
		// mRenderer.setZoomLimits(zoomlimits);
		mRenderer3_1.setBackgroundColor(Color.TRANSPARENT);
		/*mRenderer3_1.setChartTitle("Day - sunrise");
		if(Constant.widthPixels <= 480){
			mRenderer3_1.setChartTitleTextSize(20);
		}else if(Constant.widthPixels > 480 && Constant.widthPixels <= 720){
			mRenderer3_1.setChartTitleTextSize(30);
		}else if(Constant.widthPixels >= 1080){
			mRenderer3_1.setChartTitleTextSize(40);
		}*/
		mRenderer3_1.setLabelsColor(Color.rgb(38, 43, 49)); // * "title + label"'s color
		if(Constant.widthPixels <= 480){
			mRenderer3_1.setLabelsTextSize(18);
		}else if(Constant.widthPixels > 480 && Constant.widthPixels <= 720){
			mRenderer3_1.setLabelsTextSize(22);
		}else if(Constant.widthPixels >= 1080){
			mRenderer3_1.setLabelsTextSize(32);
		}
		
		int[] margins3 = new int[] {0,0,0,0}; // {top, left, bottom, right}
		if(Constant.widthPixels <= 480){
			margins3 = new int[] {10,35,0,10};
		}else if(Constant.widthPixels > 480 && Constant.widthPixels <= 720){
			margins3 = new int[] {20,60,0,20};
		}else if(Constant.widthPixels == 1080){ // 1080*1920
			margins3 = new int[] {30,80,0,30};
		}
		mRenderer3_1.setMargins(margins3);
		mRenderer3_1.setMarginsColor(Color.rgb(241, 233, 211));
		
		
		mRenderer3_1.setApplyBackgroundColor(true);
		//mRenderer.setBackgroundColor(Color.rgb(255, 228, 0));
		
		
		mRenderer3_1.setPanEnabled(false, true); // * fix graph
		double[] panLimits3_1 = new double[] {0.5,12.8,0,40};
		mRenderer3_1.setPanLimits(panLimits3_1);
		mRenderer3_1.setZoomEnabled(false, false); // * enable zoom
		double[] range3_1 = new double[] {0.5,12.8,0,40};
		mRenderer3_1.setInitialRange(range3_1);
		mRenderer3_1.setYLabelsAlign(Align.RIGHT);
		mRenderer3_1.setXTitle("Hours");
		mRenderer3_1.setYTitle("kWh");
		if(Constant.widthPixels <= 720){ // 720*1080
			mRenderer3_1.setAxisTitleTextSize(18);
		}else if(Constant.widthPixels == 1080){ // 1080*1920
			mRenderer3_1.setAxisTitleTextSize(36);
		}

		
		mRenderer3_1.setShowGridX(true);
		//mRenderer3_1.setGridColor(Color.rgb(93, 93, 93));
		mRenderer3_1.setGridColor(Color.BLACK);
		mRenderer3_1.setXLabelsColor(Color.BLACK);
		mRenderer3_1.setXLabelsAngle(330);
		mRenderer3_1.setXLabels(12); // sets the number of integer labels to appear
		for(int i = 1 ; i < 13 ; i++){
			mRenderer3_1.addXTextLabel(i, Integer.toString(i));
		}
		
		
		mRenderer3_1.setShowGridY(true);
		//mRenderer3_1.setYLabels(2);
		mRenderer3_1.setYLabelsColor(0, Color.BLACK);
		mRenderer3_1.setYLabelsAngle(0);
		for(int i = 0 ; i < 20 ; i++)
			mRenderer3_1.addYTextLabel(i*2, Integer.toString(i*2));
		
		
		mRenderer3_1.setBarSpacing(0.6);
		mRenderer3_1.setXAxisMin(0.5);
		mRenderer3_1.setXAxisMax(12.8);
		mRenderer3_1.setYAxisMin(0);
		mRenderer3_1.setYAxisMax(8);
		
		mRenderer3_1.setPointSize(1.5f);
	    mRenderer3_1.addSeriesRenderer(mCurrentRenderer3_1);
	}
	
	private void initChart3_2(){
		mCurrentSeries3_2 = new XYSeries("Wh");
		mDataset3_2.addSeries(mCurrentSeries3_2);
		
		mCurrentRenderer3_2 = new XYSeriesRenderer();
		mCurrentRenderer3_2.setColor(Color.WHITE);
		//mCurrentRenderer.setColor(Class_Color.GREEN()); // * GREEN
		mCurrentRenderer3_2.setDisplayChartValues(true);
		mCurrentRenderer3_2.setChartValuesTextAlign(Align.CENTER);
		if(Constant.widthPixels <= 480){
			mCurrentRenderer3_2.setChartValuesTextSize(20);
		}else if(Constant.widthPixels > 480 && Constant.widthPixels <= 720){
			mCurrentRenderer3_2.setChartValuesTextSize(20);
		}else if(Constant.widthPixels >= 1080){
			mCurrentRenderer3_2.setChartValuesTextSize(30);
		}
		
	    //mRenderer.setClickEnabled(false);

		// double[] zoomlimits = new double[] {0,20,0,40}; // {zoomMinimumX, zoomMaximumX, zoomMinimumY, zoomMaximumY}
		// mRenderer.setZoomLimits(zoomlimits);
		mRenderer3_2.setBackgroundColor(Color.TRANSPARENT);
		/*mRenderer3_2.setChartTitle("Day - sunset");
		if(Constant.widthPixels <= 480){
			mRenderer3_2.setChartTitleTextSize(20);
		}else if(Constant.widthPixels > 480 && Constant.widthPixels <= 720){
			mRenderer3_2.setChartTitleTextSize(30);
		}else if(Constant.widthPixels >= 1080){
			mRenderer3_2.setChartTitleTextSize(40);
		}*/
		mRenderer3_2.setLabelsColor(Color.rgb(38, 43, 49)); // * "title + label"'s color
		if(Constant.widthPixels <= 480){
			mRenderer3_2.setLabelsTextSize(18);
		}else if(Constant.widthPixels > 480 && Constant.widthPixels <= 720){
			mRenderer3_2.setLabelsTextSize(22);
		}else if(Constant.widthPixels >= 1080){
			mRenderer3_2.setLabelsTextSize(32);
		}
		
		int[] margins3 = new int[] {0,0,0,0}; // {top, left, bottom, right}
		if(Constant.widthPixels <= 480){
			margins3 = new int[] {10,35,0,10};
		}else if(Constant.widthPixels > 480 && Constant.widthPixels <= 720){
			margins3 = new int[] {20,60,0,20};
		}else if(Constant.widthPixels == 1080){ // 1080*1920
			margins3 = new int[] {30,80,0,30};
		}
		mRenderer3_2.setMargins(margins3);
		mRenderer3_2.setMarginsColor(Color.rgb(241, 233, 211));
		
		mRenderer3_2.setApplyBackgroundColor(true);
		//mRenderer.setBackgroundColor(Color.rgb(255, 228, 0));
		
		
		mRenderer3_2.setPanEnabled(false, true); // * fix graph
		double[] panLimits3_2 = new double[] {12.5,24.8,0,40};
		mRenderer3_2.setPanLimits(panLimits3_2);
		mRenderer3_2.setZoomEnabled(false, false); // * enable zoom
		double[] range3_2 = new double[] {12.5,24.8,0,40};
		mRenderer3_2.setInitialRange(range3_2);
		mRenderer3_2.setYLabelsAlign(Align.RIGHT);
		mRenderer3_2.setXTitle("Hours");
		mRenderer3_2.setYTitle("kWh");
		if(Constant.widthPixels <= 720){ // 720*1080
			mRenderer3_2.setAxisTitleTextSize(18);
		}else if(Constant.widthPixels == 1080){ // 1080*1920
			mRenderer3_2.setAxisTitleTextSize(36);
		}

		
		mRenderer3_2.setShowGridX(true);
		//mRenderer3_2.setGridColor(Color.rgb(93, 93, 93));
		mRenderer3_2.setGridColor(Color.BLACK);
		mRenderer3_2.setXLabelsColor(Color.BLACK);
		mRenderer3_2.setXLabelsAngle(330);
		mRenderer3_2.setXLabels(12); // sets the number of integer labels to appear
		for(int i = 1 ; i < 13 ; i++){
			mRenderer3_2.addXTextLabel(i+12, Integer.toString(i+12));
		}
		
		
		mRenderer3_2.setShowGridY(true);
		//mRenderer3_2.setYLabels(2);
		mRenderer3_2.setYLabelsColor(0, Color.BLACK);
		mRenderer3_2.setYLabelsAngle(0);
		for(int i = 0 ; i < 20 ; i++)
			mRenderer3_2.addYTextLabel(i*2, Integer.toString(i*2));
		
		
		mRenderer3_2.setBarSpacing(0.6);
		mRenderer3_2.setXAxisMin(12.5);
		mRenderer3_2.setXAxisMax(24.8);
		mRenderer3_2.setYAxisMin(0);
		mRenderer3_2.setYAxisMax(8);
		
		//mRenderer2.setPointSize(1.0f);
	    mRenderer3_2.addSeriesRenderer(mCurrentRenderer3_2);
	}
}
