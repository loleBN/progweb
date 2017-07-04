package interfaces;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import org.json.JSONException;

import com.toedter.calendar.JCalendar;

import basic.FreqGraph;
import basic.RegIN;
import basic.Tag;
import basic.Utils;
import webservice.WebService;
import javax.swing.JLabel;

public class JIFRelSemana extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JCalendar calendar;
	private JPanel contentPane;
	JList<Tag> listTags;
	JScrollPane scrollPane;
	DefaultListModel<RegIN> registroLM;
	DefaultListModel<Tag> tagLM;
	private JButton btnSair;
	ArrayList<RegIN> regs;
	ArrayList<Tag> tags;
	private ArrayList<FreqGraph> lfreq;
	private Date dateCalendar;
	private JLabel lblIntervalo;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JIFRelSemana frame = new JIFRelSemana();
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
	public JIFRelSemana() {
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

		tagLM = new DefaultListModel<Tag>();
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 75, 200, 150);
		contentPane.add(scrollPane);
		
		listTags = new JList<Tag>(tagLM);
		listTags.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(listTags);
		
		lblIntervalo = new JLabel("intervalo");
		lblIntervalo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblIntervalo.setBounds(100, 30, 419, 25);
		contentPane.add(lblIntervalo);		
		
		JButton btnSalvar = new JButton("SALVAR");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Utils.saveCHS(tags);
			}
		});
		btnSalvar.setBounds(35, 250, 120, 25);
		contentPane.add(btnSalvar);
		
		btnSair = new JButton("SAIR");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSair.setBounds(325, 250, 120, 25);
		contentPane.add(btnSair);
		
		JButton btnGraph = new JButton("VISUALIZAR");
		//btnGraph.setIcon(new ImageIcon(JIFRelatorioSemana.class.getResource("/drawables/graph.png")));
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
		btnGraph.setBounds(180, 250, 120, 25);
		contentPane.add(btnGraph);
		
			calendar = new JCalendar();
			calendar.setBounds(235, 75, 240, 153);
			calendar.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					if ("calendar".equals(evt.getPropertyName())){
						GregorianCalendar g = (GregorianCalendar)evt.getNewValue();
						
						dateCalendar=g.getTime();
						 	
							try {
								tags = WebService.getFHByWeek(g.getTime());    
								
								Calendar cal = Calendar.getInstance();
								cal.setTime(g.getTime());  
								int day = cal.get(Calendar.DAY_OF_WEEK);
								cal.add(Calendar.DATE, -day);
								
								cal.add(Calendar.DATE, +1);
								String d1=Utils.convertDateToString(cal.getTime());
								cal.add(Calendar.DATE, +6);
								String d7 =Utils.convertDateToString(cal.getTime());
								
								lblIntervalo.setText(d1+" - "+d7);
								
								tagLM.removeAllElements();
								for (Tag r : tags) {
									tagLM.addElement(r);
								}
								if(tags.size() == 0) {
									System.out.println("ta nulo!!! :x");
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						
						
					}
				}
			});
			contentPane.add(calendar);
 
			setTitle("Relatório Por Semana");	
			try {
				Date date =new Date();
				dateCalendar=date;
				tags = WebService.getFHByWeek(date);
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);  
				int day = cal.get(Calendar.DAY_OF_WEEK);
				cal.add(Calendar.DATE, -day);
				
				cal.add(Calendar.DATE, +1);
				String d1=Utils.convertDateToString(cal.getTime());
				cal.add(Calendar.DATE, +6);
				String d7 =Utils.convertDateToString(cal.getTime());
				
				lblIntervalo.setText(d1+" - "+d7);
				
				tagLM.removeAllElements();
				for (Tag r : tags) {
					tagLM.addElement(r);
				}
				if(tags.size() == 0) {
					//tagLM.addElement(new Tag("",""));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
		listTags = new JList<Tag>(tagLM);
		scrollPane.setViewportView(listTags);
		
		
		listTags.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        @SuppressWarnings("unchecked")
				JList<Tag> list = (JList<Tag>) evt.getSource();
		        if (evt.getClickCount() == 2) {
		        	 Rectangle r = list.getCellBounds(0, list.getLastVisibleIndex()); 
		        	 if (r != null && r.contains(evt.getPoint())) { 
		        		 // Double-click detected
		        		 int index = list.locationToIndex(evt.getPoint());
		        		 if(tags.get(index).getFrequencia_semanal()>0){
		        			 JIFRelatorioPorTag obj = new JIFRelatorioPorTag(tags.get(index).getRegistros());
		        			 getDesktopPane().add(obj);
		        		 }
		        		  
		        	 }
		        } 
		    	
		    }
		});
				
	}
}
