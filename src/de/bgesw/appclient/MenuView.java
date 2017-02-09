
package de.bgesw.appclient;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.bgesw.app.data.GameData;
import de.bgesw.app.data.Profile;
import de.bgesw.app.game.flappybird.FlappyBird;
import de.bgesw.app.game.tetris.Tetris;

public class MenuView extends JPanel {
	
	JLabel lbl_name; //Namesanzeige
	JLabel lbl_xp; //XP Anzeige
	JButton btn_start;
	GameListPanel gamelist; //Anzeige für die Spielliste
	FriendListPanel friendlist; //Anzeige für die Freundesliste
	public static BufferedImage img_defaultprofile; //Standard Profilbild
	
	public MenuView(Component parent)
	{
		try {
			img_defaultprofile=ImageIO.read(this.getClass().getResource("defaultprofile.png")); //Standard Profilbild aus den Ressourcen laden
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setBackground(AppClient.COLOR_BG); //Hintergrundfarbe setzen
		this.setBounds(0, 0, parent.getWidth(), parent.getHeight()); //Bounds setzen, WICHTIG!
		AppClient.ownprofile = NetworkManager.getOwnProfile(); //Profildaten erneut laden, um aktuelle XP zu haben
		lbl_name = new JLabel(AppClient.ownprofile.getName());
		lbl_name.setForeground(Color.WHITE); //Textfarbe
		lbl_name.setBounds(parent.getWidth()-165, 2, 100, 20);
		lbl_xp = new JLabel("XP: "+AppClient.ownprofile.getXP());
		lbl_xp.setForeground(Color.WHITE);
		lbl_xp.setBounds(parent.getWidth()-165, 18, 100, 20);
		btn_start = new JButton("Start");
		btn_start.setBounds((parent.getWidth()/2)-50, 100, 100, 20);
		btn_start.setActionCommand("start"); //Action Command setzen
		btn_start.addActionListener(new ButtonListener()); //Button Listener registrieren
		gamelist = new GameListPanel();
		gamelist.setBounds(10, 10, 200, 120);
		friendlist = new FriendListPanel();
		friendlist.setBounds(parent.getWidth()-200, 40, 200, parent.getHeight()-40);
		friendlist.setBackground(new Color(0,0,0,0));
		this.add(lbl_name);
		this.add(lbl_xp);
		this.add(btn_start);
		this.add(gamelist);
		this.add(friendlist);
		//Daten laden
		AppClient.refreshGameCache();
		AppClient.refreshFriendCache();
	}
	
	public void openGame(GameData g)
	{
		AppClient.instance.remove(AppClient.view);
		AppClient.view=new GameView(AppClient.instance,getNewGameInstance(g));
		AppClient.instance.add(AppClient.view);
		AppClient.instance.repaint();
		AppClient.view.requestFocusInWindow();
	}
	
	public Game getNewGameInstance(GameData d) //Erstelle ein Game Objekt für die gegebenen Spieldaten
	{
		switch(d.getGame(d.getRound()))
		{
		case TETRIS: return new Tetris(d);
		case FLAPPYBIRD: return new FlappyBird(d);
		}
		return null;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		int width = 200;
		int height = 40;
		Graphics2D gr = (Graphics2D)g;
		gr.setColor(new Color(0x58,0x58,0x58));
		gr.fillRect(this.getParent().getWidth()-width, 0, width, height);
		gr.setColor(new Color(0x84,0x84,0x84));
		gr.fillRect(this.getParent().getWidth()-width, height, width, this.getParent().getHeight()-height);
		gr.drawImage(ProfilePictureCache.getImage(AppClient.ownprofile.getUUID()), this.getParent().getWidth()-width+8,8, new Color(0x58,0x58,0x58,0x00),null);
	}
	
	static class GameListPanel extends JPanel //Panel für die Spielliste
	{
		GameListPanel()
		{
			this.addMouseListener(new GameListMouseListener());
			this.setFocusable(true);
			this.setBackground(new Color(0,0,0,0));
		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			int width = 200;
			int height = 120;
			Graphics2D gr = (Graphics2D)g;
			int dr = 0;
			for(int i=0;i<AppClient.gamecache.size()&&dr<5;i++){ //Alle laufenden Spiele zeichnen, aber maximal 5
				if(!AppClient.gamecache.get(i).isFinished())
				{
					GameData d = AppClient.gamecache.get(i);
					Profile against = AppClient.getProfile(d.getOther(AppClient.ownprofile.getUUID()), false);
					String state = "Warte auf Spieler";
					if(d.isCurrentPlayer(AppClient.ownprofile.getUUID()))state="Du bist dran";
					gr.drawString("Gegen: "+against.getName()+" | "+state, 5, 30+(i*30));
					dr++;
				}
			}
		}
		
		class GameListMouseListener implements MouseListener //TODO Dynamische Elementerkennung
		{
			public void mouseClicked(MouseEvent e) {
				ArrayList<GameData> dg = new ArrayList<GameData>();
				int dr = 0;
				for(int i=0;i<AppClient.gamecache.size()&&dr<5;i++){
					if(!AppClient.gamecache.get(i).isFinished())
					{
						dg.add(AppClient.gamecache.get(i));
					}
				}
				if(e.getY()>=0 && e.getY()<30) //Klick auf Spiel1
				{
					GameData d = dg.get(0);
					if(d.isCurrentPlayer(AppClient.ownprofile.getUUID())) //Überprüfen ob der Spieler an der Reihe ist
					{
						((MenuView)AppClient.view).openGame(d); //Spiel starten
					}
				}
				if(e.getY()>=30 && e.getY()<60) //Klick auf Spiel2
				{
					GameData d = dg.get(1);
					if(d.isCurrentPlayer(AppClient.ownprofile.getUUID()))
					{
						((MenuView)AppClient.view).openGame(d);
					}
				}
				if(e.getY()>=60 && e.getY()<90) //Klick auf Spiel3
				{
					GameData d = dg.get(2);
					if(d.isCurrentPlayer(AppClient.ownprofile.getUUID()))
					{
						((MenuView)AppClient.view).openGame(d);
					}
				}
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		}
	}
	
	static class FriendListPanel extends JPanel //Panel für die Freundesliste
	{
		FriendListPanel()
		{
			this.addMouseListener(new FriendListMouseListener());
			this.setFocusable(true);
		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			int width = 200;
			int height = 120;
			Graphics2D gr = (Graphics2D)g;
			for(int i=0;i<AppClient.friendcache.size()&&i<3;i++) //Alle Freunde zeichnen, aber maximal 3
			{
				gr.setColor(Color.white);
				gr.drawImage(ProfilePictureCache.getImage(AppClient.friendcache.get(i).getUUID()), 5,15+(i*30), new Color(0x58,0x58,0x58,0x00), null);
				gr.drawString(AppClient.friendcache.get(i).getName().toString(), 35, 30+(i*30));
			}
		}
		class FriendListMouseListener implements MouseListener //TODO Dynamische Elementerkennung
		{
			public void mouseClicked(MouseEvent e) {
				if(e.getY()>=0 && e.getY()<30)
				{
					System.out.println("Friend1");
				}
				if(e.getY()>=30 && e.getY()<60)
				{
					System.out.println("Friend2");
				}
				if(e.getY()>=60 && e.getY()<90)
				{
					System.out.println("Friend3");
				}
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		}
	}
	
	class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("start"))
			{	
				/*Random randomGenerator = new Random();
				int id = randomGenerator.nextInt(2)+1;
			
				if(id == 1)
				{
				AppClient.instance.remove(AppClient.view);
				AppClient.view=new GameView(AppClient.instance,new Tetris());
				AppClient.instance.add(AppClient.view);
				AppClient.instance.repaint();
				AppClient.view.requestFocusInWindow();
				}else{
					if(id == 2)
					{
					AppClient.instance.remove(AppClient.view);
					AppClient.view=new GameView(AppClient.instance,new FlappyBird());
					AppClient.instance.add(AppClient.view);
					AppClient.instance.repaint();
					AppClient.view.requestFocusInWindow();
					}else
					{
						System.out.println("Scheiße"+id+"Scheiße");
					}
					
				}
				System.out.println(id);*/
				
				NetworkManager.newGame(UUID.randomUUID()); //Neues Spiel gegen Random UUID !ACHTUNG! Nicht mehr benutzen!!! Das Profil einer Random UUID wird nicht gefunden == CRASH!
				for(Integer i : NetworkManager.getGameList(GameListType.ALL))
				{
					System.out.println(""+i);
				}
			}
		}
	}
	
}
