package interfaces;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.json.JSONException;

import basic.FreqGraph;
import basic.RegIN;
import basic.Utils;
import webservice.WebService;

public class JIFRelMes extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private Calendar cal;
	private JPanel contentPane;
	JList<RegIN> listStatusIN;
	JScrollPane scrollPane;
	DefaultListModel<RegIN> registroLM;
	private JButton btnSair;
	ArrayList<RegIN> regs;
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
					JIFRelMes frame = new JIFRelMes();
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
	public JIFRelMes() {
		setTitle("Relatório Por Mês");	
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
		scrollPane.setBounds(60, 75, 300, 310);
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
		btnGraph.setIcon(new ImageIcon(JIFRelatorioHSM.class.getResource("/drawables/graph.png")));
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
		
		listStatusIN = new JList<RegIN>(registroLM);
		scrollPane.setViewportView(listStatusIN);
	}

}
