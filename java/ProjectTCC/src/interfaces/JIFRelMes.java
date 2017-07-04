package interfaces;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import basic.Tag;
import basic.Utils;
import webservice.WebService;

public class JIFRelMes extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private Calendar cal;
	private JPanel contentPane;
	JList<Tag> jListTags;
	JScrollPane scrollPane;
	DefaultListModel<Tag> tagLM;
	private JButton btnSair;
	ArrayList<Tag> tags;
	private JComboBox<String> comboBox;
	String[] mesesDoAno = new String[] {"Janeiro", "Fevereiro",
            "Março", "Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};
	
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
		setBounds(50, 50, 434, 330);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tagLM = new DefaultListModel<Tag>();
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(60, 75, 300, 150);
		contentPane.add(scrollPane);
		jListTags = new JList<Tag>(tagLM);
		scrollPane.setViewportView(jListTags);
		
		JButton btnSalvar = new JButton("SALVAR");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Utils.saveCHM(tags);
			}
		});
		btnSalvar.setBounds(60, 250, 110, 25);
		contentPane.add(btnSalvar);
		
		btnSair = new JButton("SAIR");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSair.setBounds(245, 250, 110, 25);
		contentPane.add(btnSair);
		
		JButton btnGraph = new JButton("");
		btnGraph.setIcon(new ImageIcon(JIFRelatorioPorTag.class.getResource("/drawables/graph.png")));
		btnGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
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
						tags = WebService.getFHByMonth("0"+selectedIndex+"/"+cal.get(Calendar.YEAR));
					else
						tags = WebService.getFHByMonth(selectedIndex+"/"+cal.get(Calendar.YEAR));
					tagLM.removeAllElements();
					for (Tag r : tags) {
						tagLM.addElement(r);
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
				tags = WebService.getFHByMonth("0"+index+"/"+cal.get(Calendar.YEAR));
			else
				tags = WebService.getFHByMonth(index+"/"+cal.get(Calendar.YEAR));
			tagLM.removeAllElements();
			for (Tag r : tags) {
				tagLM.addElement(r);
			}
			if(tags.size() == 0) {
				System.out.println("tags size is null");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		jListTags = new JList<Tag>(tagLM);
		scrollPane.setViewportView(jListTags);
		
		jListTags.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        @SuppressWarnings("unchecked")
				JList<Tag> list = (JList<Tag>) evt.getSource();
		        if (evt.getClickCount() == 2) {
		        	 Rectangle r = list.getCellBounds(0, list.getLastVisibleIndex()); 
		        	 if (r != null && r.contains(evt.getPoint())) { 
		        		 // Double-click detected
		        		 int index = list.locationToIndex(evt.getPoint());
		        		 
		        		 if(tags.get(index).getFrequencia_mensal()>0){
		        			 JIFRelatorioPorTag obj = new JIFRelatorioPorTag(tags.get(index).getRegistros());
		        			 getDesktopPane().add(obj);
		        		 }
		        		  
		        	 }
		        } 
		    	
		    }
		});
	}

}
