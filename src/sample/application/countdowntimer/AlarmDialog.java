package sample.application.countdowntimer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;

public class AlarmDialog extends Activity {

	Ringtone rt;
	Vibrator vib;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		//ActivityクラスのonCreateを実行する
		super.onCreate(savedInstanceState);
		
		//レイアウト設定ファイルを指定
		this.setContentView(R.layout.alarmdialog);
		
		vib = (Vibrator)this.getSystemService(VIBRATOR_SERVICE);
		
		this.findViewById(R.id.button1).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				if(rt != null){
					rt.stop();
				}
				vib.cancel();
				finish();
			}
		});		
	}
	
	@Override
	public void onResume(){
		
		//ActivityクラスのonResume()を実行
		super.onResume();
		
		//プリファレンス取得
		SharedPreferences prefs;
		prefs = this.getSharedPreferences("CountdownTimerPrefs", 0);
		
		String fn = prefs.getString("alarm", "");
		if(fn != ""){
			rt = RingtoneManager.getRingtone(this, Uri.parse(fn));
			if((rt != null) && !(rt.isPlaying())){
				rt.play();
			}
		}
		
		if(prefs.getBoolean("vibrator", true)){
			vib.vibrate(new long[]{0,1000,500,1000,500,1000}, -1);
		}
	}
}
