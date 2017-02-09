package de.bgesw.appclient;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import de.bgesw.app.data.GameData;



public abstract class Game {
	
	public static int id = 0;
	private GameType type;
	private int score = 0;
	private Color[][] pixels;
	private Color bg_color = Color.WHITE;
	private Color draw_color = Color.BLACK;
	private boolean paused = false;
	private int height = 0;
	private int width = 0;
	public int ups = 2;
	public int gameover = 0;
	private GameData data;
	
	public abstract void onUpdate();
	public abstract void onClick(Point p);
	public abstract void onKeyPress(int keycode);
	public abstract void onKeyRelease(int keycode);
	
	public GameType getType()
	{
		return type;
	}
	
	public Game(GameData d)
	{
		this.data=d;
		this.type=d.getGame(d.getRound());
	}
	
	public void setUPS(int ups)
	{
		this.ups = ups;
		
	}
	
	public void updater()
	{
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
			public void run()
			{
				onUpdate();
			}
		}, 1000/ups, 1000/ups);
		/*Thread t = new Thread(new Runnable(){
			public void run()
			{
				while(true)
				{
					
					try {
						Thread.sleep();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();*/
	}
	
	public void setSize(int width,int height)
	{
		this.height=height;
		this.width=width;
		pixels = new Color[width][height];
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public void setBackgroundColor(Color c)
	{
		this.bg_color=c;
	}
	
	public Color getBackgroundColor()
	{
		return bg_color;
	}
	
	public Color[][] getImageAsArray()
	{
		Color[][] r = new Color[pixels.length][pixels[0].length];
		for(int x=0;x<pixels.length;x++)
			for(int y=0;y<pixels[x].length;y++)
			{
				if(pixels[x][y]!=null)
				{
					r[x][y]=pixels[x][y];
				}else{
					r[x][y]=bg_color;
				}
			}
		return r;
	}
	
	public Image getImage()
	{
		BufferedImage img = new BufferedImage(pixels.length, pixels[0].length, BufferedImage.TYPE_INT_RGB);
		for(int x=0;x<pixels.length;x++)
			for(int y=0;y<pixels[x].length;y++)
			{
				if(pixels[x][y]!=null)
				{
					img.setRGB(x, y, pixels[x][y].getRGB());
				}else{
					img.setRGB(x, y, bg_color.getRGB());
				}
			}
		return img;
	}
	
	public void clear()
	{
		pixels=new Color[width][height];
	}
	
	public void setColor(Color c)
	{
		draw_color=c;
	}
	
	public void drawPixel(int x,int y)
	{
		if(x>-1)if(y>-1)if(x<pixels.length)if(y<pixels[0].length)pixels[x][y]=draw_color;
	}
	
	public void drawRectangle(int px,int py,int w,int h)
	{
		for(int x=px;x<=w+px;x++)
		{
			drawPixel(x,py);
			drawPixel(x,py+h);
		}
		for(int y=py;y<=h+py;y++)
		{
			drawPixel(px,y);
			drawPixel(px+w,y);
		}
	}
	
	public void drawFilledRectangle(int px,int py,int w,int h)
	{
		for(int x=px;x<=px+w;x++)
			for(int y=py;y<=py+h;y++)
			{
				drawPixel(x,y);
			}
	}
	
	public boolean isPaused()
	{
		return paused;
	}
	
	public void setPaused(boolean pause)
	{
		paused=pause;
	}
	
	public void gameover()
	{	
		gameover = 1;
		data.put(data.getRound(), AppClient.ownprofile.getUUID(), score);
		setPaused(true);
		setBackgroundColor(AppClient.COLOR_BG);
		clear();
		//setBackground
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void setScore(int s)
	{
		score=s;
	}
	
}
