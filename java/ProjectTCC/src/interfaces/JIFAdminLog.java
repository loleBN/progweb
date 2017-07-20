package interfaces;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Rectangle;

import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import org.json.JSONException;

import basic.Tag;
import webservice.WebService;

public class JIFAdminLog extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField tfTAG;
	private JTextField tfNOME;
	DefaultListModel<Tag> tagLM;
	JList<Tag> jListTags;
	JScrollPane scrollPane;
	ArrayList<Tag> tags;
	int indexEDIT=-1;
	int indexREM=-1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JIFAdminLog frame = new JIFAdminLog();
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
	public JIFAdminLog() {
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 519, 338);
		getContentPane().setLayout(null);
		
		tagLM = new DefaultListModel<Tag>();
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(44, 27, 416, 158);
		getContentPane().add(scrollPane);
		jListTags = new JList<Tag>(tagLM);
		scrollPane.setViewportView(jListTags);
		
		tfTAG = new JTextField();
		tfTAG.setBounds(44, 197, 208, 33);
		getContentPane().add(tfTAG);
		tfTAG.setColumns(10);
		
		tfNOME = new JTextField();
		tfNOME.setColumns(10);
		tfNOME.setBounds(264, 197, 208, 33);
		getContentPane().add(tfNOME);
		
		try {
			Tag tagPen = WebService.getTagsPendentes();
			if(!tagPen.getTag_rfid().equals("")){
				tfTAG.setText(tagPen.getTag_rfid());
			}else{
				tfTAG.setText("no tag");
				tfNOME.setText("no tag");
			}
				
			tags = WebService.getTags();
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
		        if (evt.getClickCount() == 1) {
		        	 Rectangle r = list.getCellBounds(0, list.getLastVisibleIndex()); 
		        	 if (r != null && r.contains(evt.getPoint())) { 
		        		 // Double-click detected
		        		 int index = list.locationToIndex(evt.getPoint());
		        		 tfTAG.setText(tags.get(index).getTag_rfid());
		        		 tfNOME.setText(tags.get(index).getNome());
		        		  indexEDIT=index;
		        		  System.out.println("ready to edit");
		        	 }
		        }else if (evt.getClickCount() == 2) {
		        	 Rectangle r = list.getCellBounds(0, list.getLastVisibleIndex()); 
		        	 if (r != null && r.contains(evt.getPoint())) { 
		        		 // triple-click detected
		        		 int index = list.locationToIndex(evt.getPoint());
		        		 tfTAG.setText(tags.get(index).getTag_rfid());
		        		 tfNOME.setText(tags.get(index).getNome());
		        		 indexREM= index;
		        		 System.out.println("ready to remove");
		        	 }
		        } 
		    	
		    }
		});
		
		JButton btnListarTags = new JButton("EDITAR");
		btnListarTags.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(indexEDIT!=-1){
					System.out.println("editar tag "+tags.get(indexEDIT).getTag_rfid());
					if(!tfNOME.equals("")){
						System.out.println("editando tag "+tags.get(indexEDIT).getTag_rfid());
						//update BD
						try {
							tags.get(indexEDIT).setNome(tfNOME.getText().toString());
							WebService.upTag(tags.get(indexEDIT));
							JOptionPane.showMessageDialog(new Frame(),
							        "tag "+tags.get(indexEDIT).getTag_rfid()+" alterada!");
							tagLM.removeAllElements();
							for (Tag r : tags) {
								tagLM.addElement(r);
							}
							jListTags = new JList<Tag>(tagLM);
							scrollPane.setViewportView(jListTags);
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
						tfTAG.setText("");
						tfNOME.setText("");
						indexEDIT=-1;
					}else{
						JOptionPane.showMessageDialog(new Frame(),
						        "Adcione um nome!");
					}
				}
			}
		});
		btnListarTags.setBounds(189, 242, 142, 25);
		getContentPane().add(btnListarTags);
		
		JButton btnAdcionarTag = new JButton("ADCIONAR");
		btnAdcionarTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!tfTAG.equals("no tag")&&(!tfNOME.equals("") || !tfNOME.equals("no tag"))){
					Tag t=new Tag(tfTAG.getText().toString(), tfNOME.getText().toString(),-1);
					//add ao BD
					try {
						WebService.addTag(t);
						//add tag to list
						tagLM.addElement(t);
						jListTags = new JList<Tag>(tagLM);
						scrollPane.setViewportView(jListTags);
						
						JOptionPane.showMessageDialog(new Frame(),
						        "tag "+tfTAG.getText()+" adicionada!");
						tfTAG.setText("");
						tfNOME.setText("");
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnAdcionarTag.setBounds(35, 242, 142, 25);
		getContentPane().add(btnAdcionarTag);
		
		JButton btnExcluir = new JButton("EXCLUIR");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(indexREM!=-1){
					try {
						WebService.rmTag(tags.get(indexREM));
						tagLM.remove(indexREM);
						JOptionPane.showMessageDialog(new Frame(),
								"tag "+tags.get(indexREM).getTag_rfid()+" foi removida");
						indexREM=-1;
						tfTAG.setText("");
						tfNOME.setText("");
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnExcluir.setBounds(343, 242, 117, 25);
		getContentPane().add(btnExcluir);
		
	
	}
}
