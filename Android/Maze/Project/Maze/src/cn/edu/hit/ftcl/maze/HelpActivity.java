package cn.edu.hit.ftcl.maze;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class HelpActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        LinearLayout mainWindow = new LinearLayout(this);
        mainWindow.setOrientation(LinearLayout.VERTICAL);
        mainWindow.setBackgroundResource(R.raw.bg_help);
        
        TextView blankTitle = new TextView(this);
        blankTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 60));
        
        TextView content = new TextView(this);
        LinearLayout buttonP = new LinearLayout(this);
        buttonP.setOrientation(LinearLayout.HORIZONTAL);
        buttonP.setGravity(Gravity.CENTER_HORIZONTAL);
        ImageButton back = new ImageButton(this);
        
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        content.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        String html = "<font color='white'><big>游戏简介</big></font><br/>" +
        		"<font color='white'>迷宫是一款历史悠久的益智类游戏。此游戏通过吸引人们在弯弯曲曲，困难重重的小路上行走，" +
        		"以找到出路和真相。通过此游戏，可以训练一个人的观察，记忆，推理能力，是促进儿童智力发育和思维能力的一个既有趣又有效的方式。</font><br/><br/>" +
        		"<font color='white'><big>操作说明</big></font><br/>" +
        		"<font color='white'>重来：<i>清除迷宫上已走过的路径，从头重新开始</i></font><br/>" +
        		"<font color='white'>认输：<i>系统帮你找到出路，在迷宫上用蓝线标明</i></font><br/>" +
        		"<font color='white'>换图：<i>生成新的迷宫地图</i></font><br/>" +
        		"<font color='white'>设置：<i>设置迷宫的难度等级</i></font><br/>" +
        		"<font color='white'>帮助：<i>查看此帮助界面</i></font><br/>" +
        		"<font color='white'>上、下、左、右方向键：<i>在迷宫中移动，路径用红色的线标明</i></font><br/>" +
        		"<font color='white'>Back键：<i>回退到上一步</i></font><br/><br/>" +
        		"<font color='white'><big>难度说明</big></font><br/>" +
        		"<font color='white'>简单：<i>由10*10的方格组成</i></font><br/>" +
        		"<font color='white'>中等：<i>由20*20的方格组成</i></font><br/>" +
        		"<font color='white'>困难：<i>由30*30的方格组成</i></font><br/>" +
        		"<font color='white'>变态：<i>由40*40的方格组成</i></font><br/><br/>" +
        		"<font color='white'><big>得分规则</big></font><br/>" +
        		"<font color='white'>获得一局游戏胜利后，系统会为你在本局游戏的表现打分，得分通过正确路径的长度，你获得胜利用的时间以及你行走时的回退数三部分计算得出，满分为100分。</font><br/>";
        CharSequence charSequence=Html.fromHtml(html);
        content.setText(charSequence);
        content.setPadding(0, 0, 10, 10);
        
        back.setLayoutParams(new LayoutParams(90, 30));
        back.setBackgroundResource(R.raw.button_back);
        back.setOnClickListener(new OnClickListener(){
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
        		Intent intent = new Intent();
        		intent.setClass(HelpActivity.this,  MazeActivity.class);
        		startActivity(intent);
    		}
        });
        buttonP.addView(back);
        
        ScrollView contentP = new ScrollView(this);
        contentP.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, dm.heightPixels-60-30-15-10-50));
        contentP.setScrollbarFadingEnabled(false);
        contentP.addView(content);

        
        TextView blankContentBack = new TextView(this);
        blankContentBack.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 15));
        TextView blankBottom = new TextView(this);
        blankBottom.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 10));
        
        
        
        mainWindow.addView(blankTitle);
        mainWindow.addView(contentP);
        mainWindow.addView(blankContentBack);
        mainWindow.addView(buttonP);
        setContentView(mainWindow);
    }
}
