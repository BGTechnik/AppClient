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
	private GameType type; //Spiel -> Tetris,Flappybird,...
	private int score = 0; //Aktueller Score
	private Color[][] pixels; //Bild
	private Color bg_color = Color.WHITE; //Hintergrundfarbe
	private Color draw_color = Color.BLACK; //Aktuelle Zeichenfarbe
	private boolean paused = false; //Pausiert?
	private int height = 0; //Höhe des Bilds
	private int width = 0; //Breite des Bilds
	public int ups = 2; //Tickrate [Updates per Second]
	private GameData data; //Spieldaten über das aktuelle Spiel
	
	public abstract void onUpdate(); //Wird beim Tick gecallt
	public abstract void onClick(Point p); //Wird beim Klicken im Fenster gecallt
	public abstract void onKeyPress(int keycode); //Wird beim Drücken einer Taste im Fenster gecallt
	public abstract void onKeyRelease(int keycode); //Wird beim Loslassen einer Taste im Fenster gecallt
	
	public GameType getType() //Gibt das Spiel zurück
	{
		return type;
	}
	
	public Game(GameData d)
	{
		this.data=d;
		this.type=d.getGame(d.getRound()); //Finde Spiel anhand der aktuellen Runde und deren Spiel in den Spieldaten
	}
	
	public void setUPS(int ups) //Setze Tickrate
	{
		this.ups = ups;
		
	}
	
	public void updater() //Tick, muss einmalig gecallt werden und callt dann onUpdate() alle 1/UPS Sekunden
	{
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
			public void run()
			{
				onUpdate();
			}
		}, 1000/ups, 1000/ups);
	}
	
	public void setSize(int width,int height) //Setzt die Größe des Bilds. Muss vor dem ersten zeichnen gecallt werden.
	{
		this.height=height;
		this.width=width;
		pixels = new Color[width][height];
	}
	
	public int getHeight() //Gibt die Höhe des Bilds zurück
	{
		return height;
	}
	
	public int getWidth() //Gibt die Höhe des Bilds zurück
	{
		return width;
	}
	
	public void setBackgroundColor(Color c) //Setzt die Hintergrundfarbe des Bilds
	{
		this.bg_color=c;
	}
	
	public Color getBackgroundColor() //Gibts die Hintergrundfarbe des Bilds zurück
	{
		return bg_color;
	}
	
	public Color[][] getImageAsArray() //Gibt das Bild als 2D Color Array zurück [1.Dim: X,2. Dim: Y]
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
	
	public Image getImage() //Gibt das Bild als Image zurück
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
	
	public void clear() //Cleart das Bild, sollte vorm neumalen ausgeführt werden
	{
		pixels=new Color[width][height];
	}
	
	public void setColor(Color c) //Setzt die Zeichenfarbe
	{
		draw_color=c;
	}
	
	public void drawPixel(int x,int y) //Zeichnet den Pixel an der gegebenen XY-Position mit der Zeichenfarbe
	{
		if(x>-1)if(y>-1)if(x<pixels.length)if(y<pixels[0].length)pixels[x][y]=draw_color;
	}
	
	public void drawRectangle(int px,int py,int w,int h) //Zeichnet ein 1 Pixel breites Rechteck an Position px,py mit der Breite w und der Höhe h
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
	
	public void drawFilledRectangle(int px,int py,int w,int h) //Malt ein Rechteck an Position px,py mit der Breite w und der Höhe h
	{
		for(int x=px;x<=px+w;x++)
			for(int y=py;y<=py+h;y++)
			{
				drawPixel(x,y);
			}
	}
	
	public boolean isPaused() //Gibt zurück ob das Spiel pausiert ist
	{
		return paused;
	}
	
	public void setPaused(boolean pause) //Setzt den Pause Status [true=Pause]
	{
		paused=pause;
	}
	
	public void gameover() //Beendet das Spiel und speichert den Score
	{	
		data.put(data.getRound(), AppClient.ownprofile.getUUID(), score); //Score in Spieldaten setzen
		setPaused(true);
		setBackgroundColor(AppClient.COLOR_BG);
		clear();
		//setBackground
	}
	
	public int getScore() //Gibt den aktuellen Score zurück
	{
		return score;
	}
	
	public void setScore(int s) //Setzt den aktuellen Score
	{
		score=s;
	}
	
}
