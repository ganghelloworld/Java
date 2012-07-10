package cn.edu.hit.ftcl.maze;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.*;

public class MazeActivity extends Activity {
    /** Called when the activity is first created. */
	private FrameLayout content;
	ImageButton upButton, rightButton, downButton, leftButton, backButton;
	Maze maze;
	private int modeLevel = Maze.LevelMiddle;
	private AlertDialog.Builder config;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        String appName = "迷宫";
        setTitle(appName);
        LinearLayout mainWindow = new LinearLayout(this);
        mainWindow.setOrientation(LinearLayout.VERTICAL);
        mainWindow.setBackgroundResource(R.raw.bg_main);
        
        LinearLayout menu = createMenu();
        LinearLayout operator = createOperator();
       
        
        TextView title = new TextView(this);
        title.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 70));
        content = new FrameLayout(this);

        getMaze(modeLevel);
        
        content.addView(maze);
        config = new AlertDialog.Builder(this)
        	.setTitle("难度选择")
        	.setIcon(R.raw.config_icon)
        	.setNegativeButton("返回", null);
        
        TextView blankTitle = new TextView(this);
        blankTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 10));
        TextView blankOperator = new TextView(this);
        blankOperator.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 15));
        
        mainWindow.addView(title);
        mainWindow.addView(menu);
        mainWindow.addView(blankTitle);
        mainWindow.addView(content);
        mainWindow.addView(blankOperator);
        mainWindow.addView(operator);
        
        //TextView debug = new TextView(this);
        //debug.setText(Integer.toString(width));
        //mainWindow.addView(debug);
        setContentView(mainWindow);
    }
    private void getMaze(int level)
    {
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
    	maze = new Maze(this, dm.widthPixels, level);
    }
    private LinearLayout createOperator()
    {
    	LinearLayout operator = new LinearLayout(this);
    	operator.setOrientation(LinearLayout.VERTICAL);
    	LinearLayout up = new LinearLayout(this);
        up.setGravity(Gravity.CENTER);
        LinearLayout middle = new LinearLayout(this);
        middle.setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayout down = new LinearLayout(this);
        down.setGravity(Gravity.CENTER);
        upButton = new ImageButton(this);
        leftButton = new ImageButton(this);
        backButton = new ImageButton(this);
        rightButton = new ImageButton(this);
        downButton = new ImageButton(this);
        
        upButton.setBackgroundResource(R.raw.arrow_up);
        leftButton.setBackgroundResource(R.raw.arrow_left);
        rightButton.setBackgroundResource(R.raw.arrow_right);
        downButton.setBackgroundResource(R.raw.arrow_down);
        backButton.setBackgroundResource(R.raw.arrow_back);
        
        upButton.setLayoutParams(new LayoutParams(40, 40));
        leftButton.setLayoutParams(new LayoutParams(40, 40));
        backButton.setLayoutParams(new LayoutParams(40, 40));
        rightButton.setLayoutParams(new LayoutParams(40, 40));
        downButton.setLayoutParams(new LayoutParams(40, 40));
        
        upButton.setOnClickListener(new Move());
        leftButton.setOnClickListener(new Move());
        downButton.setOnClickListener(new Move());
        rightButton.setOnClickListener(new Move());
        backButton.setOnClickListener(new Move());
        
        TextView blank_left_middle = new TextView(this);
        blank_left_middle.setLayoutParams(new LayoutParams(5,40));
        TextView blank_right_middle = new TextView(this);
        blank_right_middle.setLayoutParams(new LayoutParams(5,40));
        
        up.addView(upButton);
        middle.addView(leftButton);
        middle.addView(blank_left_middle);
        middle.addView(backButton);
        middle.addView(blank_right_middle);
        middle.addView(rightButton);
        down.addView(downButton);
        
        TextView blankUpMiddle = new TextView(this);
        blankUpMiddle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 5));
        TextView blankDownMiddle = new TextView(this);
        blankDownMiddle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 5));
        
        operator.addView(up);
        operator.addView(blankUpMiddle);
        operator.addView(middle);
        operator.addView(blankDownMiddle);
        operator.addView(down);
        return operator;
    }
    private LinearLayout createMenu()
    {
    	LinearLayout menu = new LinearLayout(this);
		menu.setOrientation(LinearLayout.HORIZONTAL);
		menu.setGravity(Gravity.LEFT);
		menu.setPadding(0, 0, 0, 0);
		
		ImageButton resetButton = new ImageButton(this);
		resetButton.setBackgroundResource(R.raw.button_reset);
		resetButton.setPadding(0, 0, 0, 0);
		resetButton.setOnClickListener(new Reset());
		resetButton.setLayoutParams(new LayoutParams(90, 30));
		
		ImageButton giveupButton = new ImageButton(this);
		giveupButton.setBackgroundResource(R.raw.button_giveup);
		giveupButton.setPadding(0, 0, 0, 0);
		giveupButton.setOnClickListener(new Giveup());
		giveupButton.setLayoutParams(new LayoutParams(90, 30));
		
		ImageButton newButton = new ImageButton(this);
		newButton.setBackgroundResource(R.raw.button_change);
		newButton.setPadding(0, 0, 0, 0);
		newButton.setOnClickListener(new ChangeMap());
		newButton.setLayoutParams(new LayoutParams(90, 30));
		
		ImageButton setButton = new ImageButton(this);
		setButton.setBackgroundResource(R.raw.button_config);
		setButton.setPadding(0, 0, 0, 0);
		setButton.setOnClickListener(new Config());
		setButton.setLayoutParams(new LayoutParams(90, 30));
		
        ImageButton helpButton = new ImageButton(this);
        helpButton.setBackgroundResource(R.raw.button_help);
        helpButton.setLayoutParams(new LayoutParams(90, 30));
        helpButton.setOnClickListener(new Help());
        
        TextView blank1 = new TextView(this);
        blank1.setLayoutParams(new LayoutParams(5, LayoutParams.MATCH_PARENT));
        TextView blank2 = new TextView(this);
        blank2.setLayoutParams(new LayoutParams(5, LayoutParams.MATCH_PARENT));
        TextView blank3 = new TextView(this);
        blank3.setLayoutParams(new LayoutParams(5, LayoutParams.MATCH_PARENT));
        TextView blank4 = new TextView(this);
        blank4.setLayoutParams(new LayoutParams(5, LayoutParams.MATCH_PARENT));
        TextView blank5 = new TextView(this);
        blank5.setLayoutParams(new LayoutParams(5, LayoutParams.MATCH_PARENT));
        
        menu.addView(blank1);
		menu.addView(resetButton);
        menu.addView(blank2);
		menu.addView(giveupButton);
        menu.addView(blank3);
		menu.addView(newButton);
        menu.addView(blank4);
		menu.addView(setButton);
        menu.addView(blank5);
		menu.addView(helpButton);
    	return menu;
    }
    private class Help implements OnClickListener
    {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
    		Intent intent = new Intent();
    		intent.setClass(MazeActivity.this, HelpActivity.class);
    		startActivity(intent);
		}
    	
    }
    private class Giveup implements OnClickListener
    {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			maze.giveup();
		}
    }
    private class Reset implements OnClickListener
    {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			maze.clearPath();
		}
    	
    }
    private class Move implements OnClickListener
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.equals(upButton)) maze.move(0);
			else if(v.equals(rightButton)) maze.move(1);
			else if(v.equals(downButton)) maze.move(2);
			else if(v.equals(leftButton)) maze.move(3);
			else if(v.equals(backButton)) maze.move(4);
		}
    	
    }
    private class Config implements OnClickListener
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			config.setSingleChoiceItems(new String[]{"简单", "中等", "困难", "变态"}, (modeLevel-10)/10, 
        			new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							if(which == 0) modeLevel = Maze.LevelEasy;
							else if(which == 1) modeLevel = Maze.LevelMiddle;
							else if(which == 2) modeLevel = Maze.LevelHard;
							else if(which == 3) modeLevel = Maze.LevelCrazy;
							//dialog.dismiss();
						}
					})
			.show();
		}
    	
    }
    private class ChangeMap implements OnClickListener
    {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			getMaze(modeLevel);
			content.addView(maze);
		}
    }
}