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
        String html = "<font color='white'><big>��Ϸ���</big></font><br/>" +
        		"<font color='white'>�Թ���һ����ʷ�ƾõ���������Ϸ������Ϸͨ�����������������������������ص�С·�����ߣ�" +
        		"���ҵ���·�����ࡣͨ������Ϸ������ѵ��һ���˵Ĺ۲죬���䣬�����������Ǵٽ���ͯ����������˼ά������һ������Ȥ����Ч�ķ�ʽ��</font><br/><br/>" +
        		"<font color='white'><big>����˵��</big></font><br/>" +
        		"<font color='white'>������<i>����Թ������߹���·������ͷ���¿�ʼ</i></font><br/>" +
        		"<font color='white'>���䣺<i>ϵͳ�����ҵ���·�����Թ��������߱���</i></font><br/>" +
        		"<font color='white'>��ͼ��<i>�����µ��Թ���ͼ</i></font><br/>" +
        		"<font color='white'>���ã�<i>�����Թ����Ѷȵȼ�</i></font><br/>" +
        		"<font color='white'>������<i>�鿴�˰�������</i></font><br/>" +
        		"<font color='white'>�ϡ��¡����ҷ������<i>���Թ����ƶ���·���ú�ɫ���߱���</i></font><br/>" +
        		"<font color='white'>Back����<i>���˵���һ��</i></font><br/><br/>" +
        		"<font color='white'><big>�Ѷ�˵��</big></font><br/>" +
        		"<font color='white'>�򵥣�<i>��10*10�ķ������</i></font><br/>" +
        		"<font color='white'>�еȣ�<i>��20*20�ķ������</i></font><br/>" +
        		"<font color='white'>���ѣ�<i>��30*30�ķ������</i></font><br/>" +
        		"<font color='white'>��̬��<i>��40*40�ķ������</i></font><br/><br/>" +
        		"<font color='white'><big>�÷ֹ���</big></font><br/>" +
        		"<font color='white'>���һ����Ϸʤ����ϵͳ��Ϊ���ڱ�����Ϸ�ı��ִ�֣��÷�ͨ����ȷ·���ĳ��ȣ�����ʤ���õ�ʱ���Լ�������ʱ�Ļ����������ּ���ó�������Ϊ100�֡�</font><br/>";
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
