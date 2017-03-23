package interfaces;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

public class jifrelatorio extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					jifrelatorio frame = new jifrelatorio();
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
	public jifrelatorio() {
		setClosable(true);
		setMaximizable(true);
		setTitle("Relatorio de Acessos");
		setBounds(50, 50, 750, 450);

	}

}
