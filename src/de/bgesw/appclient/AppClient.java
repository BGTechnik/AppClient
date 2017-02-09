package de.bgesw.appclient;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;

import de.bgesw.app.data.GameData;
import de.bgesw.app.data.Profile;
import de.bgesw.app.data.encryption.Encryption;
import de.bgesw.app.data.encryption.NoEncryption;

public class AppClient extends JFrame
{
	
	public static Encryption encryption;
	
	public static AppClient instance;
	
	public static JPanel view;
	public static ArrayList<GameData> gamecache = new ArrayList<GameData>();
	public static ArrayList<Profile> friendcache = new ArrayList<Profile>();
	public static ArrayList<Profile> profilecache = new ArrayList<Profile>();
	public static Profile ownprofile = null;
	
	public static final Color COLOR_BG = new Color(0x58,0xD3,0xF7);
	
	public AppClient()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("BG Spiel");
		this.setSize(800, 450);
		view = new LoginView(this);
		this.setResizable(false);
		this.setVisible(true);
		this.add(view);
		this.setLayout(null);
		try {
			this.setIconImage(ImageIO.read(getClass().getResource("icon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frameUpdater(60);
	}
	
	public void setDefaultButton(JButton btn)
	{
		this.getRootPane().setDefaultButton(btn);
	}
	
	public void frameUpdater(int fps)
	{
		Thread t = new Thread(new Runnable(){
			public void run()
			{
				while(true)
				{
					view.repaint();
					try {
						Thread.sleep(1000/fps);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}
	
	public static void main(String [] args)
	{
		instance=new AppClient();
		encryption = new NoEncryption();
	}
	
	public static int randInt(int min,int max)
	{
		Random r = new Random();
		return r.nextInt(max-min)+min;
	}
	
	public static void refreshGameCache()
	{
		gamecache.clear();
		for(Integer i : NetworkManager.getGameList(GameListType.ALL))
		{
			gamecache.add(NetworkManager.getGameData(i));
		}
	}
	
	public static void refreshFriendCache()
	{
		friendcache.clear();
		for(UUID i : NetworkManager.getFriendList())
		{
			friendcache.add(NetworkManager.getProfile(i));
		}
	}
	
	public static Profile getProfile(UUID uuid,boolean forcereload)
	{
		Profile p = null;
		for(Profile pr : profilecache)if(pr.getUUID().equals(uuid))p=pr;
		if(p!=null)
		{
			if(!forcereload)
			{
				return p;
			}else{
				profilecache.remove(p);
			}
		}
		p=NetworkManager.getProfile(uuid);
		if(p!=null)profilecache.add(p);
		return p;
	}
	
}
