package de.bgesw.appclient;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
	JLabel lbl_status; //Status Label für Fehlerausgabe
	
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
		((AppClient)parent).setDefaultButton(btn_login); //Default Button wird gedrückt, wenn Enter gedrückt wird
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
	public void paintComponent(Graphics g) //Zeichenmethod, wird derzeit nicht verwendet
	{
		super.paintComponent(g);
	}
	
	class BListener implements ActionListener //Listener für den Login Button
	{
		public void actionPerformed(ActionEvent e)
		{
			if(NetworkManager.authentificate(tf_name.getText(), tf_password.getText())) //Einloggen
			{
				//Login Erfolgreich
				AppClient.ownprofile=NetworkManager.getOwnProfile(); //Eigenes Profil vom Server laden
				AppClient.instance.remove(AppClient.view); //LoginView entfernen
				AppClient.view=new MenuView(AppClient.instance); //Neuen MenuView setzen
				AppClient.instance.add(AppClient.view); //View zum Fenster hinzufügen
				AppClient.instance.repaint(); //Fenster neuzeichnen
			}else{
				lbl_status.setText("Fehler beim Login!"); //Fehler ausgeben, bei nicht erfolgreichem Login
			}
		}
	}
	
}
