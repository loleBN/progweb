

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JComboBox;
import com.toedter.calendar.JCalendar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import basic.Evento;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class MainScreen extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private URL url, urlPhoto;
	private JPanel contentPane;
	private JCalendar calendar;
	JLabel lblDiaDoEvento;
	JList<Evento> listPendingEvents;
	JList<Evento> listEventos;
	ArrayList<Evento> pendingEvents = new ArrayList<Evento>();
	JScrollPane scrollPane2;
	DefaultListModel<Evento> e;
	DefaultListModel<Evento> model;
	public static JComboBox<?> cBoxStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen frame = new MainScreen();
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
	public MainScreen() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 600);
		setTitle("Sercretária - Módulo PC");
		this.setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblStatus = new JLabel("Status: ");
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblStatus.setBounds(29, 477, 43, 20);
		contentPane.add(lblStatus);

		JLabel lblBemVindoAo = new JLabel("Bem Vindo ao Sistema de Secretária");
		lblBemVindoAo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblBemVindoAo.setBounds(29, 11, 392, 43);
		contentPane.add(lblBemVindoAo);		

		JLabel lblSolicitaesPendentes = new JLabel("Solicitações Pendentes:");
		lblSolicitaesPendentes.setFont(new Font("Tahoma", Font.ITALIC, 20));
		lblSolicitaesPendentes.setBounds(218, 80, 219, 33);
		contentPane.add(lblSolicitaesPendentes);

		JLabel lblVisitante = new JLabel("Visitante:");
		lblVisitante.setFont(new Font("Tahoma", Font.ITALIC, 20));
		lblVisitante.setBounds(65, 145, 113, 33);
		contentPane.add(lblVisitante);

		JLabel lblFotoDo = new JLabel("Foto do");
		lblFotoDo.setFont(new Font("Tahoma", Font.ITALIC, 20));
		lblFotoDo.setBounds(74, 114, 81, 33);
		contentPane.add(lblFotoDo);
		
		JLabel lblEventosDoDia = new JLabel("Eventos da data:");
		lblEventosDoDia.setFont(new Font("Tahoma", Font.ITALIC, 20));
		lblEventosDoDia.setBounds(489, 189, 165, 33);
		contentPane.add(lblEventosDoDia);
		
		lblDiaDoEvento = new JLabel(" ");
		lblDiaDoEvento.setFont(new Font("Tahoma", Font.ITALIC, 20));
		lblDiaDoEvento.setBounds(499, 227, 165, 33);
		lblDiaDoEvento.setText(convertDateToStringBR(Calendar.getInstance().getTime()));
		contentPane.add(lblDiaDoEvento);


		String[] items = new String[] {"Livre","Ocupado"};
		cBoxStatus = new JComboBox<Object>(items);
		cBoxStatus.setSelectedIndex(1);
		cBoxStatus.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
//				System.out.println("mudou = "+cBoxStatus.getSelectedIndex());
				//Envia status para o web service
				try {
					//TODO
					//tvz precise mudar aqui!!!!
//					if(cBoxStatus.getSelectedIndex() == 1) {
					url = new URL("http://tccmari.esy.es/setStatus.php?Status=" + cBoxStatus.getSelectedIndex());
					new BufferedReader(new InputStreamReader(url.openStream()));
//					}
				} catch (Exception e) {
					System.out.println("Erro Combo Box - "+e.getLocalizedMessage());
				}
			}
		});
				
		// chamando o webservice e dizer que tá ocupado!!
		try {
			url = new URL("http://tccmari.esy.es/setStatus.php?Status=1");
			new BufferedReader(new InputStreamReader(url.openStream()));
		} catch (Exception e) {
			System.out.println("Erro - "+e.getLocalizedMessage());
		}
		
		cBoxStatus.setBounds(82, 477, 100, 20);
		contentPane.add(cBoxStatus);

		final JLabel JLabelImage = new JLabel();
		JLabelImage.setIcon(new ImageIcon("C:\\Users\\Toshiba\\workspace\\SecretariaPC\\drawable\\sem_visitantes.png"));
		JLabelImage.setFont(new Font("Tahoma", Font.PLAIN, 15));
		JLabelImage.setForeground(Color.RED);
		JLabelImage.setBounds(29, 189, 175, 175);
		contentPane.add(JLabelImage);

		model = new DefaultListModel<Evento>();
		listEventos = new JList<Evento>(model);
		JScrollPane scrollPane = new JScrollPane(listEventos);
		scrollPane.setBounds(479, 271, 191, 274);

		contentPane.add(scrollPane);
		e = new DefaultListModel<Evento>();
		listPendingEvents = new JList<Evento>(e);
		scrollPane2 = new JScrollPane(listPendingEvents);
		scrollPane2.setBounds(230, 145, 191, 400);
		listPendingEvents.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList<?> list = (JList<?>)evt.getSource();
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					if (index >= 0) {
						Evento o = (Evento) list.getModel().getElementAt(index);
						//new SendEmailScreen(o).setVisible(true);						
					}
				} 
			}
		});		
		contentPane.add(scrollPane2);
		
		//Verify in web service if list changed
		ScheduledExecutorService exec2 = Executors.newSingleThreadScheduledExecutor();
		exec2.scheduleAtFixedRate(new Runnable() {
	
			public void run() {
				try {
					ArrayList<Evento> newPendingEvents = getPendingEvents();
					if(newPendingEvents.size() == 0) {
						e.removeAllElements();
						e.addElement(new Evento(-2, "", "", "", "", ""));
					}
					if (!compareArrays(newPendingEvents, pendingEvents)){											
						System.out.println("Got Event");
						e.removeAllElements();
						for (Evento ev : newPendingEvents) {
							System.out.println("Evento " + e);
							e.addElement(ev);
						}	
						pendingEvents = newPendingEvents;
					}				
				} catch (Exception e1) {
					e.removeAllElements();
					e.addElement(new Evento(-3, "", "", "", "", ""));
					e1.printStackTrace();
				}
			}

			private boolean compareArrays(ArrayList<Evento> newPendingEvents, ArrayList<Evento> pendingEvents) {
				if (newPendingEvents.size() != pendingEvents.size()){
					return false;
				}
				for(int i = 0;i<newPendingEvents.size();i++) {					
					if (newPendingEvents.get(i).getEventoId() != pendingEvents.get(i).getEventoId()){
						return false;
					}
				}
				return true;
			}
		}, 0, 5, TimeUnit.SECONDS);	

		try {
			ArrayList<Evento> eventos = getEventsByDate(convertDateToString(new Date()));
			model.removeAllElements();
			for (Evento ev : eventos) {
				model.addElement(ev);
			}
			if(eventos.size() == 0) {
				model.addElement(new Evento(-1, "", "", "", "", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	

		calendar = new JCalendar();
		calendar.setBounds(479, 25, 191, 153);
		calendar.addPropertyChangeListener(new PropertyChangeListener() {
		
			public void propertyChange(PropertyChangeEvent evt) {
				if ("calendar".equals(evt.getPropertyName())){
					GregorianCalendar g = (GregorianCalendar)evt.getNewValue();
					System.out.println(evt.getPropertyName()+" - "+ convertDateToString(g.getTime()));
					lblDiaDoEvento.setText(convertDateToStringBR(g.getTime()));
					try {
						ArrayList<Evento> eventos = getEventsByDate(convertDateToString(g.getTime()));
						model.removeAllElements();
						for (Evento ev : eventos) {
							model.addElement(ev);
						}
						if(eventos.size() == 0) {
							model.addElement(new Evento(-1, "", "", "", "", ""));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		contentPane.add(calendar);
		
		
		
		final JButton btnAccept = new JButton("");
		btnAccept.setEnabled(false);
		btnAccept.setIcon(new ImageIcon("C:\\Users\\Toshiba\\workspace\\SecretariaPC\\drawable\\accept_menor.png"));
		btnAccept.setBounds(39, 375, 50, 50);
		contentPane.add(btnAccept);
		
		final JButton btnNotAccept = new JButton("");
		btnNotAccept.setEnabled(false);
		btnNotAccept.setIcon(new ImageIcon("C:\\Users\\Toshiba\\workspace\\SecretariaPC\\drawable\\not accept_menor.png"));
		btnNotAccept.setBounds(132, 375, 50, 50);
		contentPane.add(btnNotAccept);
		
		JButton btnRefresh = new JButton("");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Calendar cal = Calendar.getInstance();
					ArrayList<Evento> eventos = getEventsByDate(convertDateToString(cal.getTime()));
					model.removeAllElements();
					for (Evento ev : eventos) {
						model.addElement(ev);
					}
					if(eventos.size() == 0) {
						model.addElement(new Evento(-1, "", "", "", "", ""));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}
		});
		btnRefresh.setIcon(new ImageIcon("C:\\Users\\Toshiba\\workspace\\SecretariaPC\\drawable\\rsz_refresh.jpg"));
		btnRefresh.setBounds(640, 238, 30, 30);
		contentPane.add(btnRefresh);
		
		JButton btnRefreshPending = new JButton("");
		btnRefreshPending.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ArrayList<Evento> newPendingEvents;
					newPendingEvents = getPendingEvents();
					if(newPendingEvents.size() == 0) {
						e.removeAllElements();
						e.addElement(new Evento(-2, "", "", "", "", ""));
					} else {											
						System.out.println("Got Event");
						e.removeAllElements();
						for (Evento ev : newPendingEvents) {
							System.out.println("Evento " + e);
							e.addElement(ev);
						}	
						pendingEvents = newPendingEvents;
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnRefreshPending.setIcon(new ImageIcon("C:\\Users\\Toshiba\\workspace\\SecretariaPC\\drawable\\rsz_refresh.jpg"));
		btnRefreshPending.setBounds(391, 110, 30, 30);
		contentPane.add(btnRefreshPending);
		
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					url = new URL("http://tccmari.esy.es/setResposta.php?Resposta=1");
					new BufferedReader(new InputStreamReader(url.openStream()));
					JLabelImage.setIcon(new ImageIcon("C:\\Users\\Toshiba\\workspace\\SecretariaPC\\drawable\\sem_visitantes.png"));
					btnAccept.setEnabled(false);
					btnNotAccept.setEnabled(false);
				} catch (Exception e) {
					System.out.println("Erro - "+e.getLocalizedMessage());
				}
			}
		});
		
		
		btnNotAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					url = new URL("http://tccmari.esy.es/setResposta.php?Resposta=0");
					new BufferedReader(new InputStreamReader(url.openStream()));
					JLabelImage.setIcon(new ImageIcon("C:\\Users\\Toshiba\\workspace\\SecretariaPC\\drawable\\sem_visitantes.png"));
					btnAccept.setEnabled(false);
					btnNotAccept.setEnabled(false);
				} catch (Exception e1) {
					System.out.println("Erro - "+e1.getLocalizedMessage());
				}
			}
		});
		

		//Verify in web service if photo changed
		ScheduledExecutorService exec = Executors
				.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {
			
			public void run() {
				try {
					url = new URL("http://tccmari.esy.es/serverPC.php");
					BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
					String response = in.readLine();
					if (response.equals("1\t\t\t\t")) {
						System.out.println("Troca imagem!!");
						setImage(JLabelImage);
						btnAccept.setEnabled(true);
						btnNotAccept.setEnabled(true);
					} 
				} catch (Exception e) {
					System.out.println("Erro - "+e.getMessage());
				}
			}
		}, 0, 1, TimeUnit.SECONDS);
	}

	private ArrayList<Evento> getPendingEvents() throws IOException, JSONException {
		ArrayList<Evento> eventos = new ArrayList<Evento>();
		URL urlEventos = new URL("http://tccmari.esy.es/getEventsTemp.php");
		BufferedReader in = new BufferedReader(new InputStreamReader(urlEventos.openStream()));
		String inputLine;
		inputLine = in.readLine();

		JSONArray eventosL = new JSONArray(inputLine);
		JSONObject evento;

		for (int i = 0; i < eventosL.length(); i++) {
			evento = new JSONObject(eventosL.getString(i));
			eventos.add(new Evento(evento.getInt("Evento_id"), evento.getString("Titulo"), evento.getString("Email"), evento.getString("Nome"), evento.getString("Data_inicio"), evento.getString("Data_fim")));			
		}

		//Ordenar por data
		Collections.sort(eventos, new Comparator<Evento>() {
		
			public int compare(Evento o1, Evento o2) {
				return o1.convertStringToDate(o1.getDataInicio()).compareTo(o2.convertStringToDate(o2.getDataInicio()));
			}
		});

		return eventos;
	}
	
	public ArrayList<Evento> getEventsByDate(String date) throws IOException, JSONException {
		ArrayList<Evento> eventos = new ArrayList<Evento>();
		URL urlEventos = new URL("http://tccmari.esy.es/selectbyDate.php?Data=" + date);
		BufferedReader in = new BufferedReader(new InputStreamReader(urlEventos.openStream()));
		String inputLine;
		inputLine = in.readLine();

		JSONArray eventosL = new JSONArray(inputLine);
		JSONObject evento;

		for (int i = 0; i < eventosL.length(); i++) {
			evento = new JSONObject(eventosL.getString(i));
			eventos.add(new Evento(evento.getInt("Evento_id"), evento.getString("Titulo"), evento.getString("Email"), evento.getString("Nome"), evento.getString("Data_inicio"), evento.getString("Data_fim")));			
		}

		//Ordenar por data
		Collections.sort(eventos, new Comparator<Evento>() {
			
			public int compare(Evento o1, Evento o2) {
				return o1.getDataInicio().compareTo(o2.getDataInicio());
			}
		});
		return eventos;
	}

	public void setImage(JLabel JLabelImage) throws Exception {
		String path = "http://tccmari.esy.es/uploads/photo.jpg";
		System.out.println("Get Image from " + path);
		urlPhoto = new URL(path);
		BufferedImage image = resize(urlPhoto, new Dimension(175,175));
		System.out.println("Load image into frame...");
		ImageIcon photo = new ImageIcon(image);
		JLabelImage.setIcon(photo);
		System.out.println("Image filled...");
	}

	public BufferedImage resize(final URL url, final Dimension size) throws IOException{
		final BufferedImage image = ImageIO.read(url);
		final BufferedImage resized = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g = resized.createGraphics();
		g.drawImage(image, 0, 0, size.width, size.height, null);
		g.dispose();
		return resized;
	}
	
	public String convertDateToString(Date dateInString){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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
}
