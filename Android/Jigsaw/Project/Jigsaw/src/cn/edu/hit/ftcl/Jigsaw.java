package cn.edu.hit.ftcl;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Jigsaw extends Activity{
    /** Called when the activity is first created. */
	public static int screenWidth;
	public static int screenHeight;
	TextView start, help;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
		
        setContentView(R.layout.jigsaw);
        start = (TextView)findViewById(R.id.start);
        help = (TextView)findViewById(R.id.help);
        start.setTextColor(Color.rgb(24, 152, 227));
        help.setTextColor(Color.rgb(24, 152, 227));
        
        start.setPadding((screenWidth / 2) - 30, (screenHeight / 2) - 100, 0, 50);
        help.setPadding((screenWidth / 2) - 27, 0, 0, 0);
        
        start.setOnClickListener(new OnClickListener(){
        	public void onClick(View a)
        	{
        		Intent intent = new Intent();
        		intent.setClass(Jigsaw.this, Main.class);
        		startActivity(intent);
        		Jigsaw.this.finish();
        	}
        });

        help.setOnClickListener(new OnClickListener(){
        	public void onClick(View a)
        	{
        		Intent intent = new Intent();
        		intent.setClass(Jigsaw.this, Help.class);
        		startActivity(intent);
        	}
        });
    }
}