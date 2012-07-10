package cn.edu.hit.ftcl.maze;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Html;
import android.util.Log;
import android.view.*;
import android.view.ViewGroup.LayoutParams;

public class Maze extends View
{
	public static final int LevelEasy = 10;
	public static final int LevelMiddle = 20;
	public static final int LevelHard = 30;
	public static final int LevelCrazy = 40;
	private int screenSize;
	private int level;
	private boolean[][] visited;
	private ArrayList<Cell> path;
	private ArrayList<Cell> successPath;
	private boolean[][] volumns;
	private boolean[][] rows;
	private Cell current;
	private int tag;
	private Paint pathPaint, successPathPaint;
	private int padding, cellSize;
	private int lastValidMove = -1;
	private long startTime, endTime;
	private int backNum;
	private AlertDialog.Builder resultDialog;
	private class Cell
	{
		public int x;
		public int y;
		public Cell(int xa, int ya)
		{
			x = xa;
			y = ya;
		}
	}
	public Maze(Context context, int size, int l) {
		super(context);
		// TODO Auto-generated constructor stub
		screenSize = size;
		level = l;
		tag = 1;
		pathPaint = new Paint();
		pathPaint.setColor(Color.RED);
		pathPaint.setAntiAlias(true);
		successPathPaint = new Paint();
		successPathPaint.setColor(Color.BLUE);
		successPathPaint.setAntiAlias(true);
		
		setLayoutParams(new LayoutParams(screenSize, screenSize));
		setBackgroundColor(Color.WHITE);
		setBackgroundResource(R.raw.bg_board);
		
		visited = new boolean[level][level];
		path = new ArrayList<Cell>();
		volumns = new boolean[level][level-1];
		rows = new boolean[level-1][level];
		current = new Cell((int)(Math.random() * level), (int)(Math.random() * level));
		
		resultDialog = new AlertDialog.Builder(context)
			.setTitle("恭喜你，成功到达出口！")
			.setIcon(R.raw.config_icon)
			.setNegativeButton("返回", null);
		
		init();
		Cell next;
		startTime = System.currentTimeMillis();
		backNum = 0;
		while(true)
		{
			next = carveWall();
			if(next.x == current.x && next.y == current.y)
			{
				break;
			}
			else
			{
				//path.add(current);
				current = new Cell(next.x, next.y);
			}
		}
		path.clear();
		current.x = level - 1;
		current.y = 0;
		path.add(new Cell(current.x, current.y));
	}
	@Override
	public void onDraw(Canvas canvas)
	{
		Paint p=new Paint(5);
		padding = 5;
		while((screenSize - padding * 2) % level != 0)
		{
			padding += 5;
		}
		cellSize = (screenSize - padding * 2) / level;
		canvas.drawLine(padding, padding, screenSize-padding, padding,  p);
		canvas.drawLine(screenSize-padding, padding, screenSize-padding, screenSize-padding-cellSize, p);
		canvas.drawLine(screenSize-padding, screenSize-padding, padding, screenSize-padding, p);
		canvas.drawLine(padding, screenSize-padding, padding, padding + cellSize, p);

		for(int i = 0; i < level; i++)
		{
			int y = screenSize - padding - i * cellSize;
			for(int j = 0; j < level-1; j++)
			{
				if(volumns[i][j] == true)
				{
					int x = padding + (j + 1) * cellSize;
					canvas.drawLine(x, y, x, y - cellSize, p);
				}
			}
		}
		for(int i = 0; i < level - 1; i++)
		{
			int y = screenSize - padding - (i + 1) * cellSize;
			for(int j = 0; j < level; j++)
			{
				if(rows[i][j] == true)
				{
					int x = padding + j * cellSize;
					canvas.drawLine(x, y, x + cellSize, y, p);
				}
			}
		}
		//Log.v("debug", Integer.toString(current.x) + " " + Integer.toString(current.y));
		//canvas.drawCircle(current.x * cellSize + cellSize/2, current.y * cellSize + cellSize/2, cellSize/2, p);
		//canvas.drawCircle(padding + cellSize / 2, padding + cellSize / 2, 3 , pathPaint);
		Log.v("debug", Integer.toString(padding) + " " + Integer.toString(cellSize));
		Bitmap bmp;
		if(cellSize > 30)
		{
			bmp = BitmapFactory.decodeResource(getResources(), R.raw.arrow_30);
		}
		else if(cellSize > 20)
		{
			bmp = BitmapFactory.decodeResource(getResources(), R.raw.arrow_20);
		}
		else if(cellSize > 10)
		{
			bmp = BitmapFactory.decodeResource(getResources(), R.raw.arrow_10);
		}
		else
		{
			bmp = BitmapFactory.decodeResource(getResources(), R.raw.arrow_7);
		}
		canvas.drawBitmap(bmp, padding, padding + (cellSize-bmp.getHeight())/2, null);
		//canvas.drawBitmap(bmp, screenSize-50, screenSize-50, null);
		canvas.drawBitmap(bmp, screenSize-padding-bmp.getWidth(), screenSize-padding-bmp.getHeight(), null);
		if(path.size() > 1) drawPath(canvas, path, pathPaint);
		if(successPath != null && successPath.size() > 1) drawPath(canvas, successPath, successPathPaint);
		//canvas.drawLine(padding + cellSize / 2, padding + cellSize / 2 , padding + cellSize / 2 + cellSize, padding + cellSize / 2, pathCell);
		canvas.save();
		if(path.size() > 1 && path.get(path.size()-1).x == 0 && path.get(path.size()-1).y == level-1)
		{
			endTime = System.currentTimeMillis();
			int full = path.size() / 2;
			int res = (int)(endTime-startTime)/1000;
			res += backNum;
			res = full * 100 / res;
			res = (res > 100) ? 100 : res;
			res = (res < 0) ? 0 : res;
			String html = "用时：" + Long.toString((endTime-startTime)/1000) + "秒<br/>" +
						  "回退数：" + Integer.toString(backNum) + "次<br/>" +
						  "成绩：" + Integer.toString(res) + "分<br/>";
			CharSequence charSequence=Html.fromHtml(html);
			resultDialog.setMessage(charSequence);
			resultDialog.show();
			path.clear();
			
		}
	}
	public void drawPath(Canvas canvas, ArrayList<Cell> p, Paint paint)
	{
		Cell start = new Cell(p.get(0).x, p.get(0).y);
		//Log.v("debug path.size()", Integer.toString(p.size()));
		for(int i = 1; i < p.size(); i++)
		{
			Cell end = p.get(i);
			//Log.v("debug start", Integer.toString(start.x) + " " + Integer.toString(start.y));
			//Log.v("debug end", Integer.toString(end.x) + " " + Integer.toString(end.y));
			canvas.drawLine(start.y * cellSize + padding + cellSize / 2, 
					(level - start.x - 1) * cellSize + padding + cellSize / 2,
					end.y * cellSize + padding + cellSize / 2, 
					(level - end.x - 1) * cellSize + padding + cellSize / 2,
					paint);
			start.x = end.x;
			start.y = end.y;
		}
	}
	public boolean move(int forward)
	{
		//Log.v("current", Integer.toString(current.x) + " " + Integer.toString(current.y));
		boolean res = false;
		boolean back = false;
		switch(forward)
		{
			case 0:
				//Log.v("move", "up");
				if(lastValidMove == 2 && path.size() > 1)
				{
					back = true;
				}
				else if(current.x < level - 1 && rows[current.x][current.y] == false)
				{
					//canvas.drawLine(startX, startY, startX, startY - cellSize, pathCell);
					current.x = current.x + 1;
					res = true;
				}
				break;
			case 1:
				//Log.v("move", "right");
				if(lastValidMove == 3 && path.size() > 1)
				{
					back = true;
				}
				else if(current.y < level - 1 && volumns[current.x][current.y] == false)
				{
					//canvas.drawLine(startX, startY, startX + cellSize, startY, pathCell);
					current.y = current.y + 1;
					res = true;
				}
				break;
			case 2:
				//Log.v("move", "down");
				if(lastValidMove == 0 && path.size() > 1)
				{
					back = true;
				}
				else if(current.x > 0 && rows[current.x - 1][current.y] == false)
				{
					//canvas.drawLine(startX, startY, startX, startY + cellSize, pathCell);
					current.x = current.x - 1;
					res = true;
				}
				break;
			case 3:
				//Log.v("move", "left");
				if(lastValidMove == 1 && path.size() > 1)
				{
					back = true;
				}
				else if(current.y > 0 && volumns[current.x][current.y - 1] == false)
				{
					//canvas.drawLine(startX, startY, startX - cellSize, startY, pathCell);
					current.y = current.y - 1;
					res = true;
				}
				break;
			case 4:
				if(path.size() > 1)
				{
					back = true;
				}
				break;
		}
		if(back)
		{
			path.remove(path.size() - 1);
			current.x = path.get(path.size() - 1).x;
			current.y = path.get(path.size() - 1).y;
			lastValidMove = getLastValidMove();
			invalidate();
			backNum++;
			return true;
		}
		if(res)
		{
			lastValidMove = forward;
			path.add(new Cell(current.x, current.y));
			invalidate();
		}
		return res;
	}
	private int getLastValidMove()
	{
		if(path.size() < 2) return -1;
		Cell start = path.get(path.size() - 2);
		Cell end = path.get(path.size() - 1);
		if(start.x == end.x)
		{
			if(start.y < end.y)
				return 1;
			else
				return 3;
		}
		else if(start.y == end.y)
		{
			if(start.x < end.x)
				return 0;
			else
				return 2;
		}
		return -1;
	}
	public void clearPath()
	{
		path.clear();
		current.x = level - 1;
		current.y = 0;
		path.add(new Cell(current.x, current.y));
		if(successPath != null) successPath.clear();
		invalidate();
	}
	public void giveup()
	{
		successPath = new ArrayList<Cell>();
		Cell curr = new Cell(level-1, 0);
		for(int i = 0; i < level; i ++)
		{
			for(int j = 0; j < level; j++)
			{
				visited[i][j] = false;
			}
		}
		while(true)
		{
			findRoute(curr);
			//Log.v("route", Integer.toString(curr.x) + " " + Integer.toString(curr.y));
			if(curr.x == 0 && curr.y == level-1)
			{
				successPath.add(new Cell(curr.x, curr.y));
				break;
			}
		}
		invalidate();
	}
	private void findRoute(Cell curr) //shoud modified curr, change curr to next point
	{
		visited[curr.x][curr.y] = true;
		int num = 0;
		boolean[] adjacent= {false, false, false, false};
		
		if(curr.x + 1 < level && !visited[curr.x+1][curr.y] && !rows[curr.x][curr.y])
		{
			num++;
			adjacent[0] = true;
		}
		if(curr.x - 1 >= 0 && !visited[curr.x-1][curr.y] && !rows[curr.x-1][curr.y])
		{
			num++;
			adjacent[2] = true;
		}
		if(curr.y + 1 < level && !visited[curr.x][curr.y+1] && !volumns[curr.x][curr.y])
		{
			num++;
			adjacent[1] = true;
		}
		if(curr.y - 1 >= 0 && !visited[curr.x][curr.y-1] && !volumns[curr.x][curr.y-1])
		{
			num++;
			adjacent[3] = true;
		}
		if(num == 0)
		{
			//Log.v("path.size", Integer.toString(path.size()));
			if(successPath.size() > 0)
			{
				curr.x = successPath.get(successPath.size()-1).x;
				curr.y = successPath.get(successPath.size()-1).y;
				successPath.remove(successPath.size()-1);
			}
			else
			{
				curr.x = level - 1;
				curr.y = 0;
			}
		}
		else
		{
			int i = 0;
			for(i = 0; i < 4; i++)
			{
				if(adjacent[i]) break;
			}
			successPath.add(new Cell(curr.x, curr.y));
			switch(i)
			{
				case 0:
					curr.x = curr.x+1;
					curr.y = curr.y;
					break;
				case 1:
					curr.x = curr.x;
					curr.y = curr.y+1;
					break;
				case 2:
					curr.x = curr.x-1;
					curr.y = curr.y;
					break;
				case 3:
					curr.x = curr.x;
					curr.y = curr.y-1;
					break;
				default:
					break;
			}
		}
	}
	private Cell carveWall()
	{
		Cell next = new Cell(0, 0);
		visited[current.x][current.y] = true;
		int num = 0;
		boolean[] adjacent = {false, false, false, false};
		if(current.x + 1 < level && !visited[current.x+1][current.y])
		{
			num++;
			adjacent[0] = true;
		}
		if(current.x - 1 >= 0 && !visited[current.x-1][current.y])
		{
			num++;
			adjacent[2] = true;
		}
		if(current.y + 1 < level && !visited[current.x][current.y+1])
		{
			num++;
			adjacent[1] = true;
		}
		if(current.y - 1 >= 0 && !visited[current.x][current.y-1])
		{
			num++;
			adjacent[3] = true;
		}
		if(num == 0)
		{
			//Log.v("path.size", Integer.toString(path.size()));
			if(path.size() > 0)
			{
				next.x = path.get(path.size()-1).x;
				next.y = path.get(path.size()-1).y;
				path.remove(path.size()-1);
			}
			else
			{
				next.x = current.x;
				next.y = current.y;
			}
		}
		else
		{
			int use = (int)(Math.random() * num);
			int j = -1;
			int i = 0;
			for(i = 0; i < 4; i++)
			{
				if(adjacent[i]) j++;
				if(j == use) break;
			}
			//Log.v("current cell", Integer.toString(current.x) + " " + Integer.toString(current.y));
			//Log.v("next cell", Integer.toString(i));
			switch(i)
			{
				case 0:
					next.x = current.x+1;
					next.y = current.y;
					rows[current.x][current.y] = false;
					break;
				case 1:
					next.x = current.x;
					next.y = current.y+1;
					volumns[current.x][current.y] = false;
					break;
				case 2:
					next.x = current.x-1;
					next.y = current.y;
					rows[current.x-1][current.y] = false;
					break;
				case 3:
					next.x = current.x;
					next.y = current.y-1;
					volumns[current.x][current.y-1] = false;
					break;
				default:
					break;
			}
			path.add(current);
		}
		return next;
	}
	private void init()
	{
		for(int i = 0; i < level; i++)
		{
			for(int j = 0; j < level; j++)
			{
				visited[i][j] = false;
			}
		}
		for(int i = 0; i < level; i++)
		{
			for(int j = 0; j < level-1;j ++)
			{
				volumns[i][j] = true;
				rows[j][i] = true;
			}
		}
	}
}
