package son.funkydj3.smartemeter.BluetoothChat;

import java.io.IOException;

public class Son_Thread_Timer extends Thread {

	private int GLOBAL_TIME = 0;
	private int GRID_TIME_SECOND = 0;
	private int GRID_TIME_MINUTE = 0;
	private int GRID_TIME_HOUR = 0;
	
	
	public int get_global_time(){
		return GLOBAL_TIME;
	}
	
	public int get_grid_time_second(){
		return GRID_TIME_SECOND;
	}
	public int get_grid_time_minute(){
		return GRID_TIME_MINUTE;
	}
	public int get_grid_time_hour(){
		return GRID_TIME_HOUR;
	}
	
	public void up_grid_time(){
		GRID_TIME_SECOND++;
	}
	
	@Override
	public void run(){
		while(true){
			
			GLOBAL_TIME++;
			if(GRID_TIME_SECOND == 59){
				GRID_TIME_SECOND = 0;
				GRID_TIME_SECOND--;
				GRID_TIME_MINUTE++;
			}
			if(GRID_TIME_MINUTE == 60){
				GRID_TIME_MINUTE = 0;
				GRID_TIME_HOUR++;
			}
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){
				;
			}
		}
	}
}
