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
	
	private static String sessionid = "null";
	
	public static String query(NetworkMethod method,String data)
	{
		return query(method.getID(),data);
	}
	
	public static String query(int method,String data)
	{
		String str = null;
		String result = null;
		try
		{
			Socket s = new Socket("192.168.1.82",4455);
			OutputStream out = s.getOutputStream();
			PrintWriter writer = new PrintWriter(out);
			InputStream in = s.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			writer.write(sessionid+"//"+method+"//"+data+"\n");
			writer.flush();
			while((str=reader.readLine())!=null)
			{
				int status = Integer.parseInt(str.split("//")[0]);
				if(status==1)
				{
					result="";
					if(str.split("//").length>1)
					{
						result=str.split("//")[1];
					}
				}
				break;
			}
			reader.close();
			writer.close();
			s.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public static boolean authentificate(String username,String password)
	{
		String result = query(0,username+";"+password);
		if(result!=null){sessionid=result;return true;}
		return false;
	}
	
	public static void newGame(UUID against)
	{
		String result = query(NetworkMethod.NEWGAME,against.toString());
	}
	
	public static Profile getProfile(UUID uuid)
	{
		String result = query(NetworkMethod.GETPROFILE,uuid.toString());
		if(result!=null)
		{
			return new Profile(result);
		}
		return null;
	}
	
	public static Profile getOwnProfile()
	{
		String result = query(NetworkMethod.GETPROFILE,"null");
		if(result!=null)
		{
			return new Profile(result);
		}
		return null;
	}
	
	public static ArrayList<Integer> getGameList(GameListType type)
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
	
	public static ArrayList<UUID> getFriendList()
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
	
	public static GameData getGameData(int gameid)
	{
		String result = query(NetworkMethod.GETGAMEDATA,""+gameid);
		if(result!=null)
		{
			return new GameData(gameid,result);
		}
		return null;
	}
	
	public static Chat getChat(int chatid)
	{
		String result = query(NetworkMethod.GETCHAT,""+chatid);
		if(result!=null)
		{
			return new Chat(result);
		}
		return null;
	}
	
	public static void updateGameData(GameData d)
	{
		query(NetworkMethod.UPDATEGAMEDATA,""+d.getID()+"|"+d.toString());
	}
}
