package cn.edu.hit.ftcl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class JigsawView extends SurfaceView implements SurfaceHolder.Callback, Runnable
{
	boolean mbLoop = false;
	Bitmap bg = null;
	SurfaceHolder mSurfaceHolder = null;
	Path[] Board = new Path[7];
	int[] x = new int[7];
	int[] y = new int[7];
	float[] degree = new float[7];
	Paint mPaint = new Paint();
	int on = 7;
	public JigsawView(Context context)
	{
		super(context);
		mSurfaceHolder = this.getHolder();
		mSurfaceHolder.addCallback(this);
		this.setFocusable(true);
		mbLoop = true;
		bg = ((BitmapDrawable) getResources().getDrawable(R.raw.bg)).getBitmap();
		for(int i = 0; i < 7; i++)
		{
			x[i] = 10;
			y[i] = 10;
			degree[i] = 0.0f;
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
		// TODO Auto-generated method stub	
	}

	public void surfaceCreated(SurfaceHolder holder)
	{	
		new Thread(this).start();
	}

	public void surfaceDestroyed(SurfaceHolder holder)
	{
		mbLoop = false;
	}

	public void run()
	{
		while(mbLoop)
		{
			try
			{
				Thread.sleep(5);
			}
			catch(Exception e)
			{
				
			}
			synchronized(mSurfaceHolder)
			{
				Draw();
			}
		}
	}
	public void Draw()
	{
		Canvas canvas = mSurfaceHolder.lockCanvas();
		if(mSurfaceHolder == null || canvas == null)
		{
			return;
		}
		mPaint.setAntiAlias(true);
		canvas.drawBitmap(bg, 0, 0, mPaint);
		
		Board[0] = new Path();
		mPaint.setColor(Color.rgb(236, 41, 45));
		getTriangle(new Point(0, 0), new Point(40, 40), new Point(0, 80), 0);
		canvas.drawPath(Board[0], mPaint);
		
		Board[1] = new Path();
		mPaint.setColor(Color.rgb(24, 152, 227));
		getTriangle(new Point(40, 40), new Point(80, 80), new Point(0, 80), 1);
		canvas.drawPath(Board[1], mPaint);
		
		Board[2] = new Path();
		mPaint.setColor(Color.rgb(251, 90, 154));
		getTriangle(new Point(40, 40), new Point(60, 60), new Point(60, 20), 2);
		canvas.drawPath(Board[2], mPaint);
				
		Board[3] = new Path();
		mPaint.setColor(Color.rgb(252, 230, 48));
		getTriangle(new Point(80, 40), new Point(40, 0), new Point(80, 0), 3);
		canvas.drawPath(Board[3], mPaint);
		
		Board[4] = new Path();
		mPaint.setColor(Color.rgb(255, 159, 46));
		getTriangle(new Point(0, 0), new Point(40, 0), new Point(20, 20), 4);
		canvas.drawPath(Board[4], mPaint);
		
		Board[5] = new Path();
		mPaint.setColor(Color.rgb(12, 148, 68));
		getquadrangle(new Point(60, 20), new Point(80, 40), new Point(80, 80), new Point(60, 60), 5);
		canvas.drawPath(Board[5], mPaint);
		
		Board[6] = new Path();
		mPaint.setColor(Color.rgb(37, 83, 255));
		getquadrangle(new Point(20, 20), new Point(40, 0), new Point(60, 20), new Point(40, 40), 6);
		canvas.drawPath(Board[6], mPaint);
		
		mSurfaceHolder.unlockCanvasAndPost(canvas);
	}
	private void getquadrangle(Point a, Point b, Point c, Point d, int i)
	{
		float cx, cy;
		cx = (a.x + b.x + c.x + d.x) / 4 + x[i];
		cy = (a.y + b.y + c.y + d.y) / 4 + y[i];
		mPaint.setStyle(Paint.Style.FILL);
		if(on == i)
		{
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setStrokeWidth(1);
		}
		
		Matrix mMatrix = new Matrix();
		Board[i].moveTo(a.x+x[i], a.y+y[i]);
		Board[i].lineTo(b.x+x[i], b.y+y[i]);
		Board[i].lineTo(c.x+x[i], c.y+y[i]);
		Board[i].lineTo(d.x+x[i], d.y+y[i]);
		Board[i].close();
		mMatrix.reset();
		mMatrix.setRotate(degree[i], cx, cy);
		Board[i].transform(mMatrix);
	}

	private void getTriangle(Point a, Point b, Point c, int i)
	{
		float cx, cy;
		
		cx = ((a.x + x[i] + b.x + x[i]) / 2 + c.x + x[i]) / 2;
		cy = ((a.y + y[i] + b.y + y[i]) / 2 + c.y + y[i]) / 2;
		mPaint.setStyle(Paint.Style.FILL);
		if(on == i)
		{
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setStrokeWidth(1);
		}
		
		Matrix mMatrix = new Matrix();
		Board[i].moveTo(a.x+x[i], a.y+y[i]);
		Board[i].lineTo(b.x+x[i], b.y+y[i]);
		Board[i].lineTo(c.x+x[i], c.y+y[i]);
		Board[i].close();
		mMatrix.reset();
		mMatrix.setRotate(degree[i], cx, cy);
		Board[i].transform(mMatrix);		
	}
	class Point
	{
		int x;
		int y;
		public Point(int a, int b)
		{
			x = a;
			y = b;
		}
	}
}