package de.bgesw.appclient;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


import de.bgesw.app.data.Profile;
import de.bgesw.app.game.flappybird.FlappyBird;
import de.bgesw.appclient.MenuView;
import de.bgesw.appclient.Game;

public class GameView extends JPanel {
	
	private Game game;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 420;
	private static final int CONTROL_HEIGHT = 30;
	private BufferedImage img_exit;
	private BufferedImage img_pause;
	
	public GameView(Component parent,Game game)
	{
		game.setSize(WIDTH, HEIGHT);
		this.game=game;
		try {
			img_exit=ImageIO.read(this.getClass().getResource("exit.png"));
			img_pause=ImageIO.read(this.getClass().getResource("pause.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setBackground(AppClient.COLOR_BG);
		this.setBounds(0, 0, parent.getWidth(), parent.getHeight());
		this.addMouseListener(new GameMouseListener());
		this.addKeyListener(new GameKeyListener());
		this.setFocusable(true);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D gr = (Graphics2D)g;
		gr.setColor(new Color(0x58,0x58,0x58));
		gr.fillRect(0, 0, getWidth(), CONTROL_HEIGHT);
		gr.setColor(Color.WHITE);
		gr.drawString(game.getType().toString(), 5, 12);
		gr.drawString("Score: " + game.getScore(), getWidth()-70, 12);
		gr.drawImage(img_exit, (getWidth()/2)-33, 2, new Color(0x58,0x58,0x58), null);
		gr.drawImage(img_pause, (getWidth()/2)+11, 2, new Color(0x58,0x58,0x58), null);
		gr.drawImage(game.getImage(), 0, CONTROL_HEIGHT, game.getBackgroundColor(), null);
	}
	
	
	
	class GameMouseListener implements MouseListener
	{
		public void mouseClicked(MouseEvent e) {
			
			if(e.getY()>CONTROL_HEIGHT)
			{
				int x = e.getX();
				int y = e.getY()-CONTROL_HEIGHT;
				game.onClick(new Point(x,y));
			}else{
				if(e.getX()>=(getWidth()/2)+11&&e.getX()<=(getWidth()/2)+33)
				{
					game.setUPS(10);
					//game.setPaused(!game.isPaused());							//Pause
				}
				if(e.getX()>=(getWidth()/2)-33&&e.getX()<=(getWidth()/2)-11)
				{	
					AppClient.instance.remove(AppClient.view);
					AppClient.view= new MenuView(AppClient.view);
					AppClient.instance.add(AppClient.view);
					AppClient.instance.repaint();
					AppClient.view.requestFocusInWindow();						//Zurück zu Menu
				}
			}
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
	
	class GameKeyListener implements KeyListener
	{
		public void keyPressed(KeyEvent e) {
			game.onKeyPress(e.getKeyCode());
		}
		public void keyReleased(KeyEvent e) {
			game.onKeyRelease(e.getKeyCode());
		}
		public void keyTyped(KeyEvent e) {}
	}
	
}
