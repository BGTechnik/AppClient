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
	
	private Game game; //Game Objekt, stellt das anzuzeigende Spiel dar
	private static final int WIDTH = 800; //Bildhöhe
	private static final int HEIGHT = 420; //Bildbreite
	private static final int CONTROL_HEIGHT = 30; //Höhe des Balkens für die Steuerelemente (Abbrechen, Pause)
	private BufferedImage img_exit; //Exit Icon
	private BufferedImage img_pause; //Pause Icon
	
	public GameView(Component parent,Game game)
	{
		game.setSize(WIDTH, HEIGHT); //Setzt die Höhe und Breite des Spielbilds
		this.game=game;
		try { //Laden der Icons aus den Ressourcen
			img_exit=ImageIO.read(this.getClass().getResource("exit.png"));
			img_pause=ImageIO.read(this.getClass().getResource("pause.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setBackground(AppClient.COLOR_BG); //Hintergrundfarbe des Panels setzen
		this.setBounds(0, 0, parent.getWidth(), parent.getHeight()); //Bounds des Panels setzen | !ACHTUNG! Muss gemacht werden bevor etwas gemalt wird!
		this.addMouseListener(new GameMouseListener()); //MouseListener registrieren
		this.addKeyListener(new GameKeyListener()); //KeyListener registrieren
		this.setFocusable(true); //Wichtig, damit KeyListener funktioniert
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D gr = (Graphics2D)g; //Graphics2D dient als 2. Dimension (Cache) um flackern zu verhindern
		gr.setColor(new Color(0x58,0x58,0x58)); //Farbe des Balkens
		gr.fillRect(0, 0, getWidth(), CONTROL_HEIGHT);
		gr.setColor(Color.WHITE); //Schriftfarbe
		gr.drawString(game.getType().toString(), 5, 12); //Spielname zeichnen
		gr.drawString("Score: " + game.getScore(), getWidth()-70, 12); //Aktuellen Score zeichnen
		gr.drawImage(img_exit, (getWidth()/2)-33, 2, new Color(0x58,0x58,0x58), null); //Exit Button zeichnen
		gr.drawImage(img_pause, (getWidth()/2)+11, 2, new Color(0x58,0x58,0x58), null); //Pause Button zeichnen
		gr.drawImage(game.getImage(), 0, CONTROL_HEIGHT, game.getBackgroundColor(), null); //Spielbild von Game zeichnen
	}
	
	class GameMouseListener implements MouseListener //Eigener Maus-Listener
	{
		public void mouseClicked(MouseEvent e) {
			
			if(e.getY()>CONTROL_HEIGHT) //Klick im eigentlichen Spiel
			{
				int x = e.getX();
				int y = e.getY()-CONTROL_HEIGHT;
				game.onClick(new Point(x,y));
			}else{ //Klick auf ein Controlelement
				if(e.getX()>=(getWidth()/2)+11&&e.getX()<=(getWidth()/2)+33) //Pause Button
				{
					game.setPaused(!game.isPaused()); //Toggle Pause
				}
				if(e.getX()>=(getWidth()/2)-33&&e.getX()<=(getWidth()/2)-11) //Abbrechen Button
				{	
					AppClient.instance.remove(AppClient.view);
					AppClient.view= new MenuView(AppClient.view);
					AppClient.instance.add(AppClient.view);
					AppClient.instance.repaint();
					AppClient.view.requestFocusInWindow();//Zurück zu Menu
				}
			}
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
	
	class GameKeyListener implements KeyListener //Eigener Keylistener
	{
		public void keyPressed(KeyEvent e) {
			game.onKeyPress(e.getKeyCode()); //Key Press Callback aufrufen
		}
		public void keyReleased(KeyEvent e) {
			game.onKeyRelease(e.getKeyCode()); //Key Release Callback aufrufen
		}
		public void keyTyped(KeyEvent e) {}
	}
	
}
