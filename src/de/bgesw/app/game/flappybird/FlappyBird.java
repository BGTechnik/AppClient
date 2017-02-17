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
	int jump = 0;		//Jumpticks
	
	public FlappyBird(GameData d) {
		super(d);
		setUPS(30);
		updater();
		setBackgroundColor(Color.CYAN);
	}

	public void onUpdate() {
		if(isPaused())return;
		if(r1==-1000)r1=getWidth()-50;
		if(r2==-1000)r2=(2*getWidth())-(getWidth()/2);
		if(jump==0){
			bh=bh+(int)8;		
		}else{
			int sub = (int)Math.pow(jump*3, 2)/50;
			if(sub<2)sub=2;
			bh=bh-sub;
			jump--;
		}														//Gameovers
		if((r1>=350&&r1<=420)&&(bh<=h1||bh>=h1+120))gameover();
		if((r2>=350&&r2<=420)&&(bh<=h2||bh>=h2+120))gameover();
																//Scores setzen
		if((r1>=350&&r1<=352)&&(bh>=h1||bh<h1+120)){
			setScore(getScore() +100);
		}
		if((r2>=350&&r2<=352)&&(bh>=h2||bh<h2+120)){
			setScore(getScore() +100);
		}
		
		
		if(bh>=getHeight()-20)gameover();
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
		drawFilledRectangle(r1,h1+120,50,getHeight()-h1+120);	//draw pipe1
		
		drawFilledRectangle(r2,0,50,h2);						//draw pipe2
		drawFilledRectangle(r2,h2+120,50,getHeight()-h2+120);	//draw pipe2
		
		setColor(Color.YELLOW);									//draw flappy dot
		drawFilledRectangle(getWidth()/2,bh,20,20);				//draw flappy dot
		
	}
	
	private static int getRandInt(int min,int max)
	{
		Random r = new Random();
		return r.nextInt(max-min)+min;
	}

	public void onClick(Point p) {
		
	}
	
	public void onKeyPress(int keycode) {
		if(keycode==32){jump=10;		}
		if(keycode==69)setUPS(1);
		
	}

	public void onKeyRelease(int keycode) {
		
	}
	
}
