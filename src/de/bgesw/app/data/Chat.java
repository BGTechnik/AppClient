package de.bgesw.app.data;

import java.util.ArrayList;
import java.util.UUID;

public class Chat extends DataPacket 
{
	private UUID player1;
	private UUID player2;
	private ArrayList<Tuple<UUID,String>> messages;

	public Chat(UUID player1,UUID player2,ArrayList<Tuple<UUID,String>> messages)
	{
		this.player1=player1;
		this.player2=player2;
		this.messages=messages;
	}

	public Chat(String string)
	{
		String [] parts = string.split(";");
		this.player1 = UUID.fromString(parts[0]);
		this.player2 = UUID.fromString(parts[1]);
		this.messages = new ArrayList<Tuple<UUID,String>>();
		for(int i=0;i<5;i++)
		{
			UUID a = UUID.fromString(parts[2+i].split("|")[0]);
			String b = parts[2+i].split("|")[1];
			messages.add(new Tuple<UUID,String>(a,b));
		}
		
	}	
	
	
	public UUID getOther(UUID uuid)
	{
		if(player1.equals(uuid))return player2;
		if(player2.equals(uuid))return player1;
		return null;
	}
	
	public UUID getPlayer1()
	{
		return player1;
	}
	
	public UUID getPlayer2()
	{
		return player2;
	}
	
	public ArrayList<Tuple<UUID,String>> getMessages()
	{
		return messages;
	}
	
	
	public void put(UUID sender, String message)
	{
		Tuple<UUID,String> msg = new Tuple<UUID,String>(sender,message);
		messages.add(msg);
	}
	
	public String toString()
	{
		String string = player1.toString()+";"+player2.toString();
		for(Tuple<UUID,String> t : messages)
		{
			string+=";"+t.a.toString()+"|"+t.b;
		}
		return string;
	}
	
	public int getType()
	{
		return 0;
	}
		
}
