package cn.edu.hit.ftcl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

public class Main extends Activity {
    /** Called when the activity is first created. */
	private static final int REFRESH = 0x000001;
	private JigsawView m = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.m = new JigsawView(this);
        setContentView(m);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
    	if(keyCode == KeyEvent.KEYCODE_BACK)
    	{
			Intent intent = new Intent();
			intent.setClass(Main.this, Jigsaw.class);
			startActivity(intent);
			Main.this.finish();
    	}
    	if(m.on < 0 || m.on > 6) return false;
    	switch(keyCode)
    	{
    	case KeyEvent.KEYCODE_DPAD_UP:
    	case KeyEvent.KEYCODE_2:
    		m.y[m.on]--;
    		break;
    	case KeyEvent.KEYCODE_DPAD_DOWN:
    	case KeyEvent.KEYCODE_8:
    		m.y[m.on]++;
    		break;
    	case KeyEvent.KEYCODE_DPAD_LEFT:
    	case KeyEvent.KEYCODE_4:
    		m.x[m.on]--;
    		break;
    	case KeyEvent.KEYCODE_DPAD_RIGHT:
    	case KeyEvent.KEYCODE_6:
    		m.x[m.on]++;
    		break;
    	case KeyEvent.KEYCODE_3:
    		m.degree[m.on]++;
    		break;
    	case KeyEvent.KEYCODE_1:
    		m.degree[m.on]--;
    		break;
    	}
    	return false;
    }
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
    	switch(keyCode)
    	{
    	case KeyEvent.KEYCODE_5:
    	case KeyEvent.KEYCODE_DPAD_CENTER:
    		if(m.on <= 6 && m.on >= 0)
    		{
    			m.on++;
    		}
    		else
    		{
    			m.on = 0;
    		}
    		break;
    	}
    	return true;    	
    }
}