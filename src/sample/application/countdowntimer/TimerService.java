package sample.application.countdowntimer;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;

public class TimerService extends Service {

	public Context mContext;
	public int counter;
	public Timer timer;
	public PowerManager.WakeLock w1;
	
	@Override
	public void onStart(Intent intent, int startId){
		super.onStart(intent, startId);
		
		mContext = this;
		counter = intent.getIntExtra("counter",0);
		if(counter != 0){
			PowerManager pm = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
			w1 = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK + PowerManager.ON_AFTER_RELEASE, "My Tag");
			w1.acquire();
			startTimer();
		}
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		timer.cancel();
		if(w1.isHeld()){
			w1.release();
		}
	}
	
	public void showAlarm(){
		
	}

	public void startTimer(){
		
		if(timer != null){
			timer.cancel();
		}
		
		timer = new Timer();
		final android.os.Handler handler = new android.os.Handler();
		
		timer.schedule(
			new TimerTask(){
			@Override
			public void run(){
				handler.post(new Runnable(){
					public void run(){
						if(counter == -1){
							timer.cancel();
							if(w1.isHeld()){
								w1.release();
							}
							showAlarm();
						}else{
							CountdownTimerActivity.countdown(counter);
							counter = counter -1;
						}
					}
				});
			}
		}, 0, 1000);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
