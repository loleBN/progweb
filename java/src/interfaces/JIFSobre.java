package interfaces;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

public class JIFSobre extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JIFSobre frame = new JIFSobre();
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
	public JIFSobre() {
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 450, 400);
		getContentPane().setLayout(null);
		
		JLabel lblLicon = new JLabel("");
		lblLicon.setBounds(90, 5, 260, 200);		
		lblLicon.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/drawables/ICON.png")));
		getContentPane().add(lblLicon);
		
		JLabel lblInfosoft = new JLabel("Software de relatório de acessos.");
		lblInfosoft.setBounds(42, 250, 363, 15);
		getContentPane().add(lblInfosoft);
		
		JLabel lblInfosoft2 = new JLabel("Desenvolvido por Lois Lene Braga Nascimento.");
		lblInfosoft2.setBounds(42, 270, 363, 15);
		getContentPane().add(lblInfosoft2);
		
		JLabel lblInfosoft3 = new JLabel("Email: loislene.of.b@gmail.com");
		lblInfosoft3.setBounds(42, 290, 363, 15);
		getContentPane().add(lblInfosoft3);

	}
}
