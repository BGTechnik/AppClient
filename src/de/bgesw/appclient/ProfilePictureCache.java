package de.bgesw.appclient;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

import javax.imageio.ImageIO;

public class ProfilePictureCache {
	
	private static HashMap<UUID,BufferedImage> images = new HashMap<UUID,BufferedImage>();
	private static final String url = "http://192.168.1.82/app/profileimages/%s.png";
	private static BufferedImage def = null;
	
	public static BufferedImage getImage(UUID uuid)
	{
		if(def==null)
		{
			try {
				def=ImageIO.read(AppClient.instance.getClass().getResource("defaultprofile.png"));
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
		if(!images.containsKey(uuid))
		{
			BufferedImage img = getImage(url.replace("%s", uuid.toString()));
			if(img!=null)images.put(uuid, img);
		}
		if(!images.containsKey(uuid))
		{
			images.put(uuid, def);
		}
		return images.get(uuid);
	}
	
	public static BufferedImage getImage(String url)
	{
		try {
			BufferedImage img = ImageIO.read(new URL(url));
			return img;
		} catch (IOException e) {
			//e.printStackTrace();
		}
		return null;
	}
	
}
