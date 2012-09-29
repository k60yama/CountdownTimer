package sample.application.countdowntimer;

import java.text.SimpleDateFormat;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class CountdownTimerActivity extends Activity {

	private static TextView tv;
	private static SeekBar sb;
	private static Context mContext;
	private	static int timeLeft = 0;
	private static Button btnStart;
	private static Button btnStop;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	//ActivityクラスのonCreateを実行する
        super.onCreate(savedInstanceState);
        
        //レイアウト設定ファイルの指定
        setContentView(R.layout.countdowntimer);
        
        //インスタンスを取得
        mContext = this;
        
        //TextViewオブジェクトを取得
        tv = (TextView)this.findViewById(R.id.textview1);
        
        //Buttonオブジェクトを取得
        btnStart = (Button)this.findViewById(R.id.buttonStart);
        btnStop = (Button)this.findViewById(R.id.buttonStop);
        
        //SeekBarオブジェクトを取得
        sb = (SeekBar)this.findViewById(R.id.seekBar1);
        sb.setBackgroundDrawable(drawScale());		//背景画像の変更

        this.setListeners();
    }

    //目盛表示メソッド
    public BitmapDrawable drawScale(){
    	Paint paint;
    	Path path;
    	Canvas canvas;
    	Bitmap bitmap;
    	
    	//Paintクラスのインスタンス生成
    	paint = new Paint();
    	paint.setStrokeWidth(0);
    	paint.setStyle(Paint.Style.STROKE);
    	
    	//目盛
    	bitmap = Bitmap.createBitmap(241, 30, Bitmap.Config.ARGB_8888);
    	
    	//Pathクラスのインスタンス生成
    	path = new Path();
    	
    	//Canvasクラスのインスタンス生成
    	canvas = new Canvas(bitmap);
    	
    	//全目盛分繰り返す
    	for(int i=0; i<17; i++){
    		//位置情報のリセット
    		path.reset();
    		
    		//目盛が5,10,15のときだけ白にする(それ以外は灰色)
    		if(i==5 || i==10 || i==15){
    			paint.setColor(Color.WHITE);
    		}else{
    			paint.setColor(Color.GRAY);
    		}
    		
    		//開始位置設定
    		path.moveTo(i*16, 5);
    		path.quadTo(i*16, 5, i*16, 15);
    		
    		canvas.drawPath(path,paint);
    	}
    	BitmapDrawable bd = new BitmapDrawable(bitmap);
    	return bd;
    }
    
    
    public void setListeners(){
    	
    	sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
    		@Override
    		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
    			
    			//左方向に移動する度、1分間ずつ進める
    			timeLeft = progress * 60;
    			
    			if(fromUser){
    				showTime(progress * 60);
    			}
   
    			//スタートボタンの有効化・無効化切り替え
    			if(fromUser && (progress > 0)){
    				btnStart.setEnabled(true);	//有効
    			}else{
    				btnStart.setEnabled(false);	//無効
    			}
    			
    			//ストップボタンの無効化
    			if(progress == 0){
    				btnStop.setEnabled(false);
    			}
    		}
    		
    		@Override
    		public void onStartTrackingTouch(SeekBar seekBar){
    		}
    		@Override
    		public void onStopTrackingTouch(SeekBar seekBar){
    		}
    	});
    	
    	btnStart.setOnClickListener(new OnClickListener(){
    		@Override
    		public void onClick(View v){
    			Intent intent = new Intent(mContext, TimerService.class);
    			intent.putExtra("counter", timeLeft);
    			startService(intent);
    			btnStart.setEnabled(false);
    			btnStop.setEnabled(true);
    			sb.setEnabled(false);
    		}
    	});
    		
    	btnStop.setOnClickListener(new OnClickListener(){
    		@Override
    		public void onClick(View v){
    			Intent i = new Intent(mContext, TimerService.class);
    			mContext.stopService(i);
    			btnStart.setEnabled(true);
    			btnStop.setEnabled(false);
    			sb.setEnabled(true);
    		}
    	});
    	
    }
    
    public static void showTime(int timeSeconds){
    	
    	//SimpleDateFormat クラスのインスタンス生成
    	SimpleDateFormat form = new SimpleDateFormat("mm:ss");
    	tv.setText(form.format(timeSeconds*1000));
    }
    
    public static void countdown(int counter){
    	showTime(counter);
    	timeLeft = counter;
    	
    	if(counter % 60 == 0){
    		sb.setProgress(counter/60);
    	}else{
    		sb.setProgress(counter/60+1);
    	}
    	
    	if(counter != 0){
    		btnStop.setEnabled(true);
    		btnStart.setEnabled(false);
    		sb.setEnabled(false);
    	}else{
    		btnStop.setEnabled(false);
    		btnStart.setEnabled(false);
    		sb.setEnabled(true);
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample, menu);
        return true;
    }

    
}
