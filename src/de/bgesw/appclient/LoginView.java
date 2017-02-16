package de.bgesw.appclient;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;



public class LoginView extends JPanel {
	

	Font font = new Font("sans-serif", 0, 20); //Schrift
	JTextField tf_name; //Eingabefeld Name
	JPasswordField tf_password; //Eingabefeld Passwort
	JButton btn_login; //Login Button
	JLabel lbl_status; //Status Label f�r Fehlerausgabe
	
	public LoginView(Component parent)
	{
		this.setBackground(AppClient.COLOR_BG);
		this.setLayout(null);
		tf_name = new JTextField();
		tf_name.setBounds((parent.getWidth()/2)-75, (parent.getHeight()/2)-40, 150, 30);
		tf_password = new JPasswordField();
		tf_password.setBounds((parent.getWidth()/2)-75, (parent.getHeight()/2)+10, 150, 30);
		btn_login = new JButton("Einloggen");
		btn_login.setBounds((parent.getWidth()/2)-75, (parent.getHeight()/2)+50, 150, 30);
		btn_login.addActionListener(new BListener());
		((AppClient)parent).setDefaultButton(btn_login); //Default Button wird gedr�ckt, wenn Enter gedr�ckt wird
		lbl_status = new JLabel("");
		lbl_status.setBounds((parent.getWidth()/2)-75, (parent.getHeight()/2)+90, 150, 30);
		lbl_status.setForeground(Color.RED); //Schriftfarbe
		
		this.add(tf_name);
		this.add(tf_password);
		this.add(btn_login);
		this.add(lbl_status);
		this.setBounds(0, 0, parent.getWidth(),parent.getHeight()); //Bounds setzen. Achtung muss gemacht werden, wegen entferntem LayoutManager
		this.setFocusable(true);
	}
	
	@Override
	public void paintComponent(Graphics g) //Zeichenmethod, wird derzeit nicht verwendet //added Title mehr oder weniger
	{	
		Font LARGE_FONT = new Font("Tahoma", Font.BOLD, 13);
		super.paintComponent(g);
		InputStream is = LoginView.class.getResourceAsStream("/de/bgesw/appclient/Mario.ttf");
		Font input = null;
		try {
			input = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			System.out.println("Fuck");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Fuck2");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Font Mario = input.deriveFont(40f);	
		g.setFont(Mario);
		g.drawString("GameDuell",260 ,100 );
	}
	
	class BListener implements ActionListener //Listener f�r den Login Button
	{
		public void actionPerformed(ActionEvent e)
		{
			if(NetworkManager.authentificate(tf_name.getText(), tf_password.getText())) //Einloggen
			{
				//Login Erfolgreich
				AppClient.ownprofile=NetworkManager.getOwnProfile(); //Eigenes Profil vom Server laden
				AppClient.instance.remove(AppClient.view); //LoginView entfernen
				AppClient.view=new MenuView(AppClient.instance); //Neuen MenuView setzen
				AppClient.instance.add(AppClient.view); //View zum Fenster hinzuf�gen
				AppClient.instance.repaint(); //Fenster neuzeichnen
			}else{
				lbl_status.setText("Fehler beim Login!"); //Fehler ausgeben, bei nicht erfolgreichem Login
			}
		}
	}
	
}
