package interfaces;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import basic.RegIN;
import basic.Utils;
import webservice.WebService;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JIFRelatorioHSM extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
					JIFRelatorioHSM frame = new JIFRelatorioHSM();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public JIFRelatorioHSM() {
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
		scrollPane.setBounds(50, 10, 300, 350);
		contentPane.add(scrollPane);
		listStatusIN = new JList<RegIN>(registroLM);
		scrollPane.setViewportView(listStatusIN);
		
		JButton btnSalvar = new JButton("SALVAR");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Utils.save(regs);
			}
		});
		btnSalvar.setBounds(63, 381, 117, 25);
		contentPane.add(btnSalvar);
		
		btnSair = new JButton("SAIR");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSair.setBounds(245, 381, 117, 25);
		contentPane.add(btnSair);
		
	}

	/**
	 * Create the frame.
	 */
	public JIFRelatorioHSM(int id) {
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
		scrollPane.setBounds(50, 10, 300, 350);
		contentPane.add(scrollPane);
		listStatusIN = new JList<RegIN>(registroLM);
		scrollPane.setViewportView(listStatusIN);
		
		
		JButton btnSalvar = new JButton("SALVAR");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Utils.save(regs);
			}
		});
		btnSalvar.setBounds(63, 381, 117, 25);
		contentPane.add(btnSalvar);
		
		btnSair = new JButton("SAIR");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSair.setBounds(245, 381, 117, 25);
		contentPane.add(btnSair);
		
		if(id==1){
			setTitle("Relatório Hoje");	
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
			setTitle("Relatório Semanal");	
			try {
				regs = WebService.getRegsByWeek();
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
			setTitle("Relatório Mensal");	
			try {
				regs = WebService.getRegsByMonth(Utils.getSoMes(new Date()));
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
