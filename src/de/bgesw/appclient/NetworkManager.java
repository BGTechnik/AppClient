package de.bgesw.appclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import de.bgesw.app.data.Chat;
import de.bgesw.app.data.GameData;
import de.bgesw.app.data.Profile;

public class NetworkManager {
	
	private static String sessionid = "null"; //SessionID mit der man sich am Server authentifiziert
	
	public static String query(NetworkMethod method,String data) //Wrapper für query(int,String)
	{
		return query(method.getID(),data);
	}
	
	public static String query(int method,String data) //Anfrage an den Server
	{
		String str = null;
		String result = null;
		try
		{
			Socket s = new Socket("192.168.1.82",4455); //Verbindung aufbauen
			OutputStream out = s.getOutputStream();
			PrintWriter writer = new PrintWriter(out);
			InputStream in = s.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			writer.write(sessionid+"//"+method+"//"+data+"\n"); //Anfrage senden
			writer.flush();
			while((str=reader.readLine())!=null) //Antwort/en lesen
			{
				int status = Integer.parseInt(str.split("//")[0]); //Statuscode parsen
				if(status==1) //Wenn positiv
				{
					result="";
					if(str.split("//").length>1)
					{
						result=str.split("//")[1]; //Daten parsen
					}
				}
				break; //Maximal eine Antwort lesen
			}
			reader.close();
			writer.close();
			s.close(); //Verbindung beenden
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public static boolean authentificate(String username,String password) //Am Server authentifizieren
	{
		String result = query(0,username+";"+password);
		if(result!=null){sessionid=result;return true;}
		return false;
	}
	
	public static void newGame(UUID against) //Neues Spiel anfordern
	{
		String result = query(NetworkMethod.NEWGAME,against.toString());
	}
	
	public static Profile getProfile(UUID uuid) //Profil abfragen
	{
		String result = query(NetworkMethod.GETPROFILE,uuid.toString());
		if(result!=null)
		{
			return new Profile(result);
		}
		return null;
	}
	
	public static Profile getOwnProfile() //Eigenes Profil abfragen
	{
		String result = query(NetworkMethod.GETPROFILE,"null");
		if(result!=null)
		{
			return new Profile(result);
		}
		return null;
	}
	
	public static ArrayList<Integer> getGameList(GameListType type) //Spielliste des jeweiligen Typs abfragen
	{
		String result = query(NetworkMethod.GAMELIST,type.toString());
		if(result!=null)
		{
			ArrayList<Integer> list = new ArrayList<Integer>();
			for(String s : result.split(","))if(s.length()>0)list.add(Integer.parseInt(s));
			return list;
		}
		return null;
	}
	
	public static ArrayList<UUID> getFriendList() //Freundesliste abfragen
	{
		String result = query(NetworkMethod.FRIENDLIST,"null");
		if(result!=null)
		{
			ArrayList<UUID> list = new ArrayList<UUID>();
			for(String s : result.split(","))if(s.length()>0)list.add(UUID.fromString(s));
			return list;
		}
		return null;
	}
	
	public static GameData getGameData(int gameid) //Spieldaten abfragen
	{
		String result = query(NetworkMethod.GETGAMEDATA,""+gameid);
		if(result!=null)
		{
			return new GameData(gameid,result);
		}
		return null;
	}
	
	public static Chat getChat(int chatid) //Chat abfragen
	{
		String result = query(NetworkMethod.GETCHAT,""+chatid);
		if(result!=null)
		{
			return new Chat(result);
		}
		return null;
	}
	
	public static void updateGameData(GameData d) //Veränderte Spieldaten zum Server senden
	{
		query(NetworkMethod.UPDATEGAMEDATA,""+d.getID()+"|"+d.toString());
	}
}
