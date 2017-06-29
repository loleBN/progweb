package interfaces;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import basic.RegIN;
import basic.Utils;
import webservice.WebService;

public class JIFRelData extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JCalendar calendar;
	private JPanel contentPane;
	JList<RegIN> listStatusIN;
	JScrollPane scrollPane;
	DefaultListModel<RegIN> registroLM;
	private JButton btnSair;
	ArrayList<RegIN> regs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JIFRelData frame = new JIFRelData();
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
	public JIFRelData() {
		setTitle("Relatório Por Data");	
		setIconifiable(true);
		setClosable(true);
		setBounds(50, 50, 434, 483);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		registroLM = new DefaultListModel<RegIN>();
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(55, 160, 300, 240);
		contentPane.add(scrollPane);
		listStatusIN = new JList<RegIN>(registroLM);
		scrollPane.setViewportView(listStatusIN);
		
		
		JButton btnSalvar = new JButton("SALVAR");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Utils.save(regs);
			}
		});
		btnSalvar.setBounds(60, 410, 110, 25);
		contentPane.add(btnSalvar);
		
		btnSair = new JButton("SAIR");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSair.setBounds(245, 410, 110, 25);
		contentPane.add(btnSair);
				
		calendar = new JCalendar();
		calendar.setBounds(110, 12, 191, 153);
		calendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if ("calendar".equals(evt.getPropertyName())){
					GregorianCalendar g = (GregorianCalendar)evt.getNewValue();
					try {
						regs = WebService.getRegsByDate(Utils.convertDateToString(g.getTime()),0);
						registroLM.removeAllElements();
						for (RegIN r : regs) {
							registroLM.addElement(r);
								System.out.println("r: "+r);
							}
							if(regs.size() == 0) {
								registroLM.addElement(new RegIN(-1,"", "","", -1));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
			contentPane.add(calendar);
					
			try {
				regs = WebService.getRegsByDate(Utils.convertDateToString(new Date()),2);
				registroLM.removeAllElements();
				for (RegIN r : regs) {
					registroLM.addElement(r);
					System.out.println("r: "+r);
				}
				if(regs.size() == 0) {
					registroLM.addElement(new RegIN(-1,"", "","", -1));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			listStatusIN = new JList<RegIN>(registroLM);
			scrollPane.setViewportView(listStatusIN);

	}

}
