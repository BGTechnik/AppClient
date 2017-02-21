package de.bgesw.app.game.tetris;

import java.awt.Color;
import java.awt.Point;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.TitlePaneLayout;

import de.bgesw.app.data.GameData;
import de.bgesw.appclient.Game;
import de.bgesw.appclient.GameType;


public class Tetris extends Game {
	
	
	
	private Integer[][] tiles = new Integer[10][20];
	private int cx = 3;
	private int cy = 0;
	private boolean[][] current = new boolean[4][4];
	private int rotation = 0;
	private TileType ctype;
	
	public Tetris(GameData d)
	{
		super(d);
		ctype = TileType.random();
		drawTetris(ctype);
		updater();
	}

	@Override
	public void onUpdate() {
		if(isPaused())return;
		cy++;
		boolean coll = false;
		for(int x=0;x<4;x++)
			for(int y=0;y<4;y++)
			{
				if(current[x][y])
				{
					if(cy+y==20)
					{
						coll=true;
					}else{
						if(tiles[cx+x][cy+y]!=null)if(tiles[cx+x][cy+y]!=-1)coll=true;
					}
				}
			}
		if(coll)
		{
			for(int x=0;x<4;x++)
				for(int y=0;y<4;y++)
				{
					if(current[x][y])
					{
						setTile(cx+x,cy+y-1,0);
					}
				}
			cy=0;
			cx=3;
			rotation=0;
			ctype = TileType.random();
			//ctype = TileType.TypeI;
			drawTetris(ctype);
			//setUPS(10);
		}
		int lines = 0;
		for(int r=tiles[0].length-1;r>0;r--)
		{
			boolean done = false;
			while(!done)
			{
				boolean full = true;
				for(int x=0;x<tiles.length;x++)
				{
					if(tiles[x][r]==null){
						full=false;
					}else{
						if(tiles[x][r]==-1)full=false;
					}
				}
				if(full)
				{
					lines++;
					for(int x=0;x<tiles.length;x++)setTile(x,r,-1);
					for(int m=r;m>1;m--)for(int x=0;x<tiles.length;x++)
					{
						if(tiles[x][m-1]!=null)
						{
							setTile(x,m,tiles[x][m-1]);
						}else{
							setTile(x,m,-1);
						}
					}
				}else{
					done=true;
				}
			}
		}
		if(lines > 0) {
			setScore(getScore()+(50 << lines));	
		}
		
		
		//Malen
		this.clear();
		this.drawRectangle(10, 20, 20, 10);
		setColor(Color.BLACK);
		this.drawFilledRectangle(0, 0, 160, 320);
		for(int x=0;x<tiles.length;x++)
			for(int y=0;y<tiles[0].length;y++)
			{
				if(tiles[x][y]!=null)
				{
					if(tiles[x][y]!=-1)
					{
						if(tiles[x][y]==0)
						{
							drawTile((x)*16,(y)*16,Color.RED,new Color(0xF7,0x81,0x81),new Color(0x61,0x0B,0x0B));
						}
					}
				}
			}
		for(int x=0;x<4;x++)
			for(int y=0;y<4;y++)
			{
				if(current[x][y])
				{
					drawTile((cx+x)*16,(cy+y)*16,Color.RED,new Color(0xF7,0x81,0x81),new Color(0x61,0x0B,0x0B));
				}
			}
	}

	@Override
	public void onClick(Point p) {
		
	}
	public void drawTetris(TileType tile) 
	{
		for(int x=0;x<4;x++)
			for(int y=0;y<4;y++)
				current[x][y]=tile.tiles[rotation][x][y];
	}
	@Override
	public void onKeyPress(int keycode) {
		System.out.println("press:"+keycode);
		if(keycode==39)
		{
			int sub=0;
			for(int x=0;x<4;x++)
				for(int y=0;y<4;y++)
				{
					if(current[x][y]){sub=x;break;}
				}
			int cxd = cx;
			cxd++;
			if(cxd>9-sub)cxd=9-sub;
			cx=cxd;
		}
		if(keycode==37)
		{
			int sub=0;
			for(int x=0;x<4;x++)
			{
				boolean isEmpty = true;
				for(int y=0;y<4;y++)
				{
					if(current[x][y]){isEmpty=false;break;}
				}
				if(isEmpty)
				{
					sub++;
					break;
				}
			}
			int cxd = cx;
			cxd--;
			if(cxd<0-sub)cxd=0-sub;
			cx=cxd;
		}
		if(keycode==81)
		{
			//left
			if(rotation!=0)
			{
				rotation--;
			}else
			{
				rotation=3;
			}
			drawTetris(ctype);
		}
		
		if(keycode==69)
		{
			//right
			if(rotation!=3)
			{
				rotation++;
			}else
			{
				rotation=0;
			}
			drawTetris(ctype);
		}
		
		if(keycode==32)
		{
			setUPS(10);
			System.out.print("schnell");
		}
	}
	
	@Override
	public void onKeyRelease(int keycode) 
	{	System.out.println("release:"+keycode);
		if(keycode==32)
		{	
			setUPS(2);
			System.out.print("normal");
		}
	}
	public void setTile(int x,int y,int type)
	{
		tiles[x][y]=type;
	}
	
	public void drawTile(int sx,int sy,Color base,Color light,Color dark)
	{
		setColor(base);
		drawFilledRectangle(sx+2,sy+2,12,12);
		for(int x = sx;x<sx+16;x++)
		{
			setColor(light);
			drawPixel(x,sy);
			drawPixel(x,sy+1);
		}
		for(int x = sx;x<sx+16;x++)
		{
			setColor(dark);
			drawPixel(x,sy+15);
			drawPixel(x,sy+14);
		}
		for(int y = sy;y<sy+16;y++)
		{
			setColor(light);
			drawPixel(sx,y);
			drawPixel(sx+1,y);
		}
		for(int y = sy;y<sy+16;y++)
		{
			setColor(dark);
			drawPixel(sx+15,y);
			drawPixel(sx+14,y);
		}
		drawPixel(sx+1,sy+15);
		setColor(light);
		drawPixel(sx+14,sy);
		
	}
	
}
