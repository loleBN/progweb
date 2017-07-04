package interfaces;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import basic.RegIN;
import basic.Utils;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JIFRelatorioPorTag extends JInternalFrame {

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
					JIFRelatorioPorTag frame = new JIFRelatorioPorTag();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public JIFRelatorioPorTag() {
		setIconifiable(true);
		setClosable(true);
		setBounds(150, 150, 434, 483);
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
	public JIFRelatorioPorTag(ArrayList<RegIN> regsByTAG) {
	
		setIconifiable(true);
		setClosable(true);
		setBounds(50, 50, 500, 330);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		registroLM = new DefaultListModel<RegIN>();
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 25, 400, 225);
		contentPane.add(scrollPane);
		listStatusIN = new JList<RegIN>(registroLM);
		scrollPane.setViewportView(listStatusIN);
		
		
		JButton btnSalvar = new JButton("SALVAR");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Utils.save(regs); 
			}
		});
		btnSalvar.setBounds(100, 260, 110, 25);
		contentPane.add(btnSalvar);
		
		btnSair = new JButton("SAIR");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSair.setBounds(300, 260, 110, 25);
		contentPane.add(btnSair);
		
	
			setTitle(regsByTAG.get(0).getNome());	
			try {
				regs = regsByTAG;
				registroLM.removeAllElements();
				for (RegIN r : regs) {
					registroLM.addElement(r);
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
