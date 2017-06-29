package interfaces;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.DefaultListModel;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.json.JSONException;

import com.toedter.calendar.JCalendar;

import basic.FreqGraph;
import basic.RegIN;
import basic.Utils;
import webservice.WebService;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;

public class JIFRelatorioMes extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Calendar cal;
	private JCalendar calendar;
	private JPanel contentPane;
	JList<RegIN> listStatusIN;
	JScrollPane scrollPane;
	DefaultListModel<RegIN> registroLM;
	private JButton btnSair;
	ArrayList<RegIN> regs;
	private int id_;
	private JComboBox<String> comboBox;
	String[] mesesDoAno = new String[] {"Janeiro", "Fevereiro",
            "Março", "Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};
	private ArrayList<FreqGraph> lfreq;
	private Date dateCalendar;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JIFRelatorioMes frame = new JIFRelatorioMes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public JIFRelatorioMes() {
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
		
	}

	/**
	 * Create the frame.
	 */
	public JIFRelatorioMes(int id) {
		
		id_ = id;
		
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
		scrollPane.setBounds(55, 150, 175, 250);
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
		
		JButton btnGraph = new JButton("");
		btnGraph.setIcon(new ImageIcon(JIFRelatorioMes.class.getResource("/drawables/graph.png")));
		btnGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					lfreq = WebService.getFreqByWeek(dateCalendar);
					GraphDrawTeste obj = new GraphDrawTeste(lfreq);
					contentPane.getParent().add(obj);
					obj.setVisible(true);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnGraph.setBounds(190, 410, 35, 35);
		contentPane.add(btnGraph);
		
		if(id_ == 1 || id_==2){
			calendar = new JCalendar();
			calendar.setBounds(110, 12, 191, 153);
			calendar.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					if ("calendar".equals(evt.getPropertyName())){
						GregorianCalendar g = (GregorianCalendar)evt.getNewValue();
						System.out.println(evt.getPropertyName()+" - "+ Utils.convertDateToString(g.getTime()));
						dateCalendar=g.getTime();
						if(id_== 1){
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
	
						}else if(id_== 2){ 	
							try {
								regs = WebService.getRegsByWeek(g.getTime());                 
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
				}
			});
			contentPane.add(calendar);
		}
		
		if(id==1){
			setTitle("Relatório Por Data");	
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

		}else if(id==2){ 
			setTitle("Relatório Por Semana");	
			try {
				Date date =new Date();
				dateCalendar=date;
				regs = WebService.getRegsByWeek(date);
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
		}else if(id==3){
			
			scrollPane.setBounds(60, 75, 300, 310);
			comboBox = new JComboBox<String>(mesesDoAno);
			comboBox.setBounds(60, 30, 300, 25);
			contentPane.add(comboBox);
			
			comboBox.addActionListener(new ActionListener() {
				 
			    public void actionPerformed(ActionEvent event) {			        			 
			        try {
			        	int selectedIndex = comboBox.getSelectedIndex()+1;
						if(selectedIndex<10)
							regs = WebService.getRegsByMonth("0"+selectedIndex+"/"+cal.get(Calendar.YEAR));
						else
							regs = WebService.getRegsByMonth(selectedIndex+"/"+cal.get(Calendar.YEAR));
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
			});
			
			cal = Calendar.getInstance();
			cal.setTime(new Date());
			  			
			comboBox.setSelectedIndex(cal.get(Calendar.MONTH));
			
			setTitle("Relatório Por Mês");	
			try {
				int index = comboBox.getSelectedIndex()+1;
				if(index<10)
					regs = WebService.getRegsByMonth("0"+index+"/"+cal.get(Calendar.YEAR));
				else
					regs = WebService.getRegsByMonth(index+"/"+cal.get(Calendar.YEAR));
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
		
		listStatusIN = new JList<RegIN>(registroLM);
		scrollPane.setViewportView(listStatusIN);
		
	}
}
