package interfaces;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import basic.Tag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class jifrelatorio extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					jifrelatorio frame = new jifrelatorio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public jifrelatorio() {
		setClosable(true);
		setMaximizable(true);
		setTitle("Relatorio de Acessos");
		setBounds(50, 50, 750, 450);
		getContentPane().setLayout(null);
		
		JLabel lblTxturl = new JLabel("txtURL");
		lblTxturl.setBounds(62, 52, 398, 139);
		getContentPane().add(lblTxturl);
		
		URL url;
		try {
			ArrayList<Tag> tags = new ArrayList<Tag>();
			url = new URL("http://ufam-automation.net/loislene/getTags.php");
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String result= in.readLine();
			System.out.println(result);
			
			JSONArray tagsL = new JSONArray(result);
			JSONObject tag;

			for (int i = 0; i < tagsL.length(); i++) {
				tag = new JSONObject(tagsL.getString(i));
				tags.add(new Tag(tag.getString("tag_rfid"), tag.getString("nome"), tag.getInt("status")));			
			}

			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}