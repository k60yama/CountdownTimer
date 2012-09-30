package sample.application.countdowntimer;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Preferences extends PreferenceActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		//ActivityクラスのonCreateを実行
		super.onCreate(savedInstanceState);
		
		//レイアウト設定ファイルの指定
		setContentView(R.layout.preferences_screen);
		
		this.getPreferenceManager().setSharedPreferencesName("CountdownTimePrefs");
		this.addPreferencesFromResource(R.xml.preference);
	}

}
