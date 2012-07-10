package cn.edu.hit.ftcl;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;


public class Help extends Activity{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.help);
        TextView help = (TextView)findViewById(R.id.helpString);
        help.setTextColor(Color.rgb(0, 0, 0));
    }
}