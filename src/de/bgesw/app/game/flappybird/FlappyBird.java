package de.bgesw.app.game.flappybird;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

import de.bgesw.app.data.GameData;
import de.bgesw.appclient.Game;
import de.bgesw.appclient.GameType;

public class FlappyBird extends Game {
	
	private static int ID = 2;
	int r1 = -1000;		//X Position of the first tube
	int r2 = -1000;		//X Position of the second tube
	int h1 = getRandInt(10,300); //Height of the first tube (Inverted)
	int h2 = getRandInt(10,300); //Height of the second tube (Inverted)
	int bh = 10;		//Bird Height (Inverted)
	int jump = 0;		//zu jumpende höhe?
	double jumpx = 0; 	//position der Flugkurve
	
	public FlappyBird(GameData d) {
		super(d);
		setUPS(10);
		updater();
		setBackgroundColor(Color.CYAN);
	}

	public void onUpdate() {
		if(isPaused())return;
		if(r1==-1000)r1=getWidth()-50;
		if(r2==-1000)r2=(2*getWidth())-(getWidth()/2);
		
		/*if(jump>0)												//der jump part 	 
		{
			
			if(bh>=4)
			{
											//Flugkurve mit 0.1 statt -0.1 hier einfügen
				int t=0;
				
				jumpx++;
				t=(int)jumpx; 
				bh=bh+(int) (0.1*(3*t*0.7-(1.5/2*Math.pow(t,2))));						
			}else{							
				jumpx=0;						
			}								
			jump--;
		}else{
			int t=0;
			jumpx++;
			t=(int)jumpx; 
			bh=bh+(int) (-0.1*(3*t*0.7-(1.5/2*Math.pow(t,2))));
			//jumpx++;
			//double j = Math.pow(jumpx, 2)/10;
			//bh+=j; 
		}*/
		if(jump==0){
			
			jumpx++;
			int t=(int)jumpx; 
			bh=bh-(int) (0.1*(3*t*0.7-(1.5/2*Math.pow(t,2))));		
		}else{
			int t=0;
			jumpx++;
			t=(int)jumpx; 
			bh=bh+(int) (0.1*(3*t*0.7-(1.5/2*Math.pow(t,2))));
			jump--;
		}
		//if(jump==1)jumpx=0;
		//System.out.println(" r1:"+ r1+" r2:"+r2+" h1:"+h1+" h2:"+h2+" bh:"+bh+" jump:"+jump);	//height=420 width=800
		
		if((r1>=350&&r1<=420)&&(bh<=h1||bh>=h1+80))gameover();
		if((r2>=350&&r2<=420)&&(bh<=h2||bh>=h2+80))gameover();
				
		r1-=3;													//move pipe1 left
		r2-=3;													//move pipe2 left
		if(r1<-100)												//set pipe1 height?
		{
			r1=getWidth();
			h1=getRandInt(10,300);
		}
		if(r2<-100)												//set pipe2 height?
		{
			r2=getWidth();
			h2=getRandInt(10,300);
		}
		clear();
		setColor(Color.GREEN);									//draw pipes
		drawFilledRectangle(r1,0,50,h1);						//draw pipe1
		drawFilledRectangle(r1,h1+100,50,getHeight()-h1+100);	//draw pipe1
		
		drawFilledRectangle(r2,0,50,h2);						//draw pipe2
		drawFilledRectangle(r2,h2+100,50,getHeight()-h2+100);	//draw pipe2
		
		setColor(Color.YELLOW);									//draw flappy dot
		drawFilledRectangle(getWidth()/2,bh,20,20);				//draw flappy dot
		
		
		if(gameover==1);
	}
	
	private static int getRandInt(int min,int max)
	{
		Random r = new Random();
		return r.nextInt(max-min)+min;
	}

	public void onClick(Point p) {
		
	}
	
	public void onKeyPress(int keycode) {
		if(keycode==32){jump=3;		}
		if(keycode==69)setUPS(1);
		
	}

	public void onKeyRelease(int keycode) {
		
	}
	
}
