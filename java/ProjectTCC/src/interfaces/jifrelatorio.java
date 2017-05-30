package interfaces;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.toedter.calendar.JCalendar;

import basic.Evento;
import basic.RegIN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

//////////////////////////////////////////////////////////////////////////////////////////////////////
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JComboBox;

import javax.swing.JList;

//////////////////////////////////////////////////////////////////////////////////////////////////////

public class jifrelatorio extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JCalendar calendar;
	
	private JPanel contentPane;
	JList<RegIN> listStatusIN;
	JList<RegIN> listRegByDate;
	JScrollPane scrollPane2;
	DefaultListModel<RegIN> registroLM;
	DefaultListModel<RegIN> model;

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
		
		
		// get tags cadastradas
		/*try {
			ArrayList<Tag> tags = new ArrayList<Tag>();
			URL url = new URL("http://ufam-automation.net/loislene/getTags.php");
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
		*/
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Relatório Geral");
		this.setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblRoom = new JLabel("Lab Ambientes Inteligentes");
		lblRoom.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblRoom.setBounds(29, 11, 432, 43);
		contentPane.add(lblRoom);

		model = new DefaultListModel<RegIN>();
		listRegByDate = new JList<RegIN>(model);
		listRegByDate.setBounds(479, 190, 188, 216);
		contentPane.add(listRegByDate);	
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(479, 187, 191, 219);

		contentPane.add(scrollPane);
		registroLM = new DefaultListModel<RegIN>();
		
		scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(29, 54, 392, 352);
		contentPane.add(scrollPane2);
		listStatusIN = new JList<RegIN>(registroLM);
		scrollPane2.setViewportView(listStatusIN);
		
		try {
			ArrayList<RegIN> regsNow = getRegsNow();
			if(regsNow.size() == 0) {
				registroLM.removeAllElements();
			}							
			System.out.println("Got Event");
			registroLM.removeAllElements();
			for (RegIN rg : regsNow) {
				System.out.println("Registro: " + rg);
				registroLM.addElement(rg);
			}
							
		} catch (Exception e1) {
			registroLM.removeAllElements();
			e1.printStackTrace();
		}			
		
		calendar = new JCalendar();
		calendar.setBounds(479, 25, 191, 153);
		calendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if ("calendar".equals(evt.getPropertyName())){
					GregorianCalendar g = (GregorianCalendar)evt.getNewValue();
					System.out.println(evt.getPropertyName()+" - "+ convertDateToString(g.getTime()));
					try {
						ArrayList<RegIN> regs = getRegsByDate(convertDateToString(g.getTime()));
						model.removeAllElements();
						for (RegIN r : regs) {
							model.addElement(r);
						}
						if(regs.size() == 0) {
							model.addElement(new RegIN(-1,"", "", -1));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		
		contentPane.add(calendar);
		
		try {
			System.out.println("try:"+convertDateToString(new Date()));
			ArrayList<RegIN> regs = getRegsByDate(convertDateToString(new Date()));
			model.removeAllElements();
			for (RegIN r : regs) {
				model.addElement(r);
				System.out.println("r: "+r);
			}
			if(regs.size() == 0) {
				model.addElement(new RegIN(-1,"", "", -1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private ArrayList<RegIN> getRegsNow() throws IOException, JSONException {
		ArrayList<RegIN> regs = new ArrayList<RegIN>();
		URL urlEventos = new URL("http://ufam-automation.net/loislene/getStatusIN.php");
		BufferedReader in = new BufferedReader(new InputStreamReader(urlEventos.openStream()));
		String inputLine;
		inputLine = in.readLine();

		JSONArray regsL = new JSONArray(inputLine);
		JSONObject r;
		System.out.println("RegsNOW: "+inputLine);
		for (int i = 0; i < regsL.length(); i++) {
			r = new JSONObject(regsL.getString(i));
			regs.add(new RegIN(i,r.getString("tag_rfid"), r.getString("nome")));			
		}
		
		return regs;
	}
	
	public ArrayList<RegIN> getRegsByDate(String date) throws IOException, JSONException {
		ArrayList<RegIN> regs = new ArrayList<RegIN>();
		URL urlRegs = new URL("http://ufam-automation.net/loislene/getRegistroByData.php?date=" + date);
		BufferedReader in = new BufferedReader(new InputStreamReader(urlRegs.openStream()));
		String inputLine;
		inputLine = in.readLine();

		if(!inputLine.equals("-1")){
			JSONArray regsL = new JSONArray(inputLine);
			JSONObject r;
	
			for (int i = 0; i < regsL.length(); i++) {
				r = new JSONObject(regsL.getString(i));
				regs.add(new RegIN(i,r.getString("tag_rfid"), r.getString("date_time"), r.getInt("status")));			
			}
		} else{
				regs.add(new RegIN(-1,"", "", -1));		
		}
		
		return regs;
	}
	
	public String convertDateToString(Date dateInString){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String date = null;
        try {
            date = format.format(dateInString);
        } catch (Exception e) {
        	System.out.println("Erro = "+e.getMessage());
        }
        return date;
    }
	
	public String convertDateToStringBR(Date dateInString){
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date = null;
        try {
            date = format.format(dateInString);
        } catch (Exception e) {
        	System.out.println("Erro = "+e.getMessage());
        }
        return date;
    }

	private Date createDate(Date date) {
		Calendar c = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c2.get(Calendar.DAY_OF_MONTH));
		c.set(Calendar.MONTH, c2.get(Calendar.MONTH));
		c.set(Calendar.YEAR, c2.get(Calendar.YEAR));
		return (c.getTime());
	}

	
}