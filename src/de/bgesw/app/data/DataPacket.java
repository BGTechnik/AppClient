package de.bgesw.app.data;

public abstract class DataPacket
{
	
	public abstract String toString(); //Methode um einen versendbaren String aus den Daten zu generieren
	public abstract int getType(); //Datatype
	
}