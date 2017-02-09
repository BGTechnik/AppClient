
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
	
	JLabel lbl_name;
	JLabel lbl_xp;
	JButton btn_start;
	GameListPanel gamelist;
	FriendListPanel friendlist;
	public static BufferedImage img_defaultprofile;
	
	public MenuView(Component parent)
	{
		try {
			img_defaultprofile=ImageIO.read(this.getClass().getResource("defaultprofile.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setBackground(AppClient.COLOR_BG);
		this.setBounds(0, 0, parent.getWidth(), parent.getHeight());
		Profile p = NetworkManager.getOwnProfile();
		lbl_name = new JLabel(p.getName());
		lbl_name.setForeground(Color.WHITE);
		lbl_name.setBounds(parent.getWidth()-165, 2, 100, 20);
		lbl_xp = new JLabel("XP: "+p.getXP());
		lbl_xp.setForeground(Color.WHITE);
		lbl_xp.setBounds(parent.getWidth()-165, 18, 100, 20);
		btn_start = new JButton("Start");
		btn_start.setBounds((parent.getWidth()/2)-50, 100, 100, 20);
		btn_start.setActionCommand("start");
		btn_start.addActionListener(new ButtonListener());
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
	
	public Game getNewGameInstance(GameData d)
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
	
	static class GameListPanel extends JPanel
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
			for(int i=0;i<AppClient.gamecache.size()&&dr<5;i++){
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
		class GameListMouseListener implements MouseListener
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
				if(e.getY()>=0 && e.getY()<30)
				{
					GameData d = dg.get(0);
					if(d.isCurrentPlayer(AppClient.ownprofile.getUUID()))
					{
						((MenuView)AppClient.view).openGame(d);
					}
				}
				if(e.getY()>=30 && e.getY()<60)
				{
					GameData d = dg.get(1);
					if(d.isCurrentPlayer(AppClient.ownprofile.getUUID()))
					{
						((MenuView)AppClient.view).openGame(d);
					}
				}
				if(e.getY()>=60 && e.getY()<90)
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
	
	static class FriendListPanel extends JPanel
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
			for(int i=0;i<AppClient.friendcache.size()&&i<3;i++)
			{
				gr.setColor(Color.white);
				gr.drawImage(ProfilePictureCache.getImage(AppClient.friendcache.get(i).getUUID()), 5,15+(i*30), new Color(0x58,0x58,0x58,0x00), null);
				gr.drawString(AppClient.friendcache.get(i).getName().toString(), 35, 30+(i*30));
			}
		}
		class FriendListMouseListener implements MouseListener
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
				
				NetworkManager.newGame(UUID.randomUUID());
				for(Integer i : NetworkManager.getGameList(GameListType.ALL))
				{
					System.out.println(""+i);
				}
			}
		}
	}
	
}
