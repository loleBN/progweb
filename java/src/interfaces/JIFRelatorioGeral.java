package interfaces;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import com.toedter.calendar.JCalendar;

import basic.RegIN;
import basic.Utils;
import webservice.WebService;

import java.util.ArrayList;

//////////////////////////////////////////////////////////////////////////////////////////////////////
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

import javax.swing.JList;

//////////////////////////////////////////////////////////////////////////////////////////////////////

public class JIFRelatorioGeral extends JInternalFrame {

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
					JIFRelatorioGeral frame = new JIFRelatorioGeral();
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
	public JIFRelatorioGeral() {
		setIconifiable(true);
		setClosable(true);
		setTitle("Relatorio de Acessos");
		setBounds(50, 50, 750, 450);
		getContentPane().setLayout(null);
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(475, 193, 200, 225);
		
				contentPane.add(scrollPane);
		listRegByDate = new JList<RegIN>(model);
		scrollPane.setViewportView(listRegByDate);
		registroLM = new DefaultListModel<RegIN>();
		
		scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(29, 54, 392, 352);
		contentPane.add(scrollPane2);
		listStatusIN = new JList<RegIN>(registroLM);
		scrollPane2.setViewportView(listStatusIN);
		
		try {
			ArrayList<RegIN> regsNow = WebService.getRegsNow();
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
					System.out.println(evt.getPropertyName()+" - "+ Utils.convertDateToString(g.getTime()));
					try {
						ArrayList<RegIN> regs = WebService.getRegsByDate(Utils.convertDateToString(g.getTime()));
						model.removeAllElements();
						for (RegIN r : regs) {
							model.addElement(r);
						}
						if(regs.size() == 0) {
							model.addElement(new RegIN(-1,"","", "", -1));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		
		contentPane.add(calendar);
		
		try {
			System.out.println("try:"+Utils.convertDateToString(new Date()));
			ArrayList<RegIN> regs = WebService.getRegsByDate(Utils.convertDateToString(new Date()));
			model.removeAllElements();
			for (RegIN r : regs) {
				model.addElement(r);
				System.out.println("r: "+r);
			}
			if(regs.size() == 0) {
				model.addElement(new RegIN(-1,"", "","", -1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
}