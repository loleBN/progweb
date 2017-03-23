package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JDesktopPane;
import java.awt.event.ActionListener;
import java.security.Principal;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

public class TelaPrincipal extends JFrame {

	private JPanel contentPane;
	JDesktopPane jdpPrincipal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnRelatorio = new JMenu("relatorio");
		menuBar.add(mnRelatorio);
		
		JMenu mnSobre = new JMenu("sobre");
		menuBar.add(mnSobre);
		
		JMenuItem mntmAtual = new JMenuItem("atual");
		mntmAtual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jifrelatorio obj = new jifrelatorio();
				jdpPrincipal.add(obj);
				obj.setVisible(true);
			}
		});
		mnRelatorio.add(mntmAtual);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		jdpPrincipal = new JDesktopPane();
		jdpPrincipal.setSize(new Dimension(990, 595));
		jdpPrincipal.setMinimumSize(new Dimension(400, 400));
		jdpPrincipal.setBackground(Color.LIGHT_GRAY);
		jdpPrincipal.setBounds(0, 0, 990, 590);
		contentPane.add(jdpPrincipal);
		jdpPrincipal.setLayout(null);
	}
}
