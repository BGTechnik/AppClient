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
	
	public static Encryption encryption; //Verwendete Verschl�sselung
	
	public static AppClient instance; //Instanz des Clients
	
	public static JPanel view; //Anzeigepanel, wird je nach Ansicht gesetzt
	public static ArrayList<GameData> gamecache = new ArrayList<GameData>(); //Game Cache
	public static ArrayList<Profile> friendcache = new ArrayList<Profile>(); //Friendlist Cache
	public static ArrayList<Profile> profilecache = new ArrayList<Profile>(); //Profil Cache
	public static Profile ownprofile = null; //Eigenes Profil
	
	public static final Color COLOR_BG = new Color(0x58,0xD3,0xF7); //Hintergrundfarbe
	
	public AppClient()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //Beende beim Schlie�en des Fensters
		this.setTitle("BG Spiel"); //Fenstertitel
		this.setSize(800, 450); //Fenstergr��e
		view = new LoginView(this); //View auf einen neuen LoginView setzen
		this.setResizable(false); //Nicht skalierbar
		this.setVisible(true); //Fenster �ffnen
		this.add(view); //View hinzuf�gen
		this.setLayout(null); //Entfernen des Layout Managers um die Objekte selbt anordnen zu k�nnen -> Alle Child-Elemente m�ssen ihre Bounds selbst definieren
		try {
			this.setIconImage(ImageIO.read(getClass().getResource("icon.png"))); //Fenstericon
		} catch (IOException e) {
			e.printStackTrace();
		}
		frameUpdater(60); //Bildberechnung FPS: 60
	}
	
	public void setDefaultButton(JButton btn)
	{
		this.getRootPane().setDefaultButton(btn); //Default Button = Button der betätigt wird wenn Enter gedrückt wird
	}
	
	public void frameUpdater(int fps)
	{
		Thread t = new Thread(new Runnable(){ //Paraleller Thread wegen Sleep
			public void run()
			{
				while(true)
				{
					view.repaint();
					try {
						Thread.sleep(1000/fps); //Warte 1/FPS Sekunden
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
		encryption = new NoEncryption(); // NoEncryption ver/entschlüsselt die Strings nicht
	}
	
	public static int randInt(int min,int max) //Zuf�lliger Integer in: min<=x<=max
	{
		Random r = new Random();
		return r.nextInt(max-min)+min;
	}
	
	public static void refreshGameCache() //Aktualisiere Spieldaten vom Server
	{
		gamecache.clear();
		for(Integer i : NetworkManager.getGameList(GameListType.ALL))
		{
			gamecache.add(NetworkManager.getGameData(i));
		}
	}
	
	public static void refreshFriendCache() //Aktualisiere Freundesliste vom Server
	{
		friendcache.clear();
		for(UUID i : NetworkManager.getFriendList())
		{
			friendcache.add(NetworkManager.getProfile(i));
		}
	}
	
	public static Profile getProfile(UUID uuid,boolean forcereload) //Hole Profil aus dem Cache, wenn nicht vorhanden, vom Server [forcereload=true sorgt f�r ein gezwungenes neuladen vom Server]
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
