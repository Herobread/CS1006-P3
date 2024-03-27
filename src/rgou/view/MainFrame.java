package rgou.view;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	public MainFrame() {
		setTitle("Royal game of Ur");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1280, 720);

		// center window itself
		setLocationRelativeTo(null);

		// disable layout manager and handle everything manually:
		setLayout(null);

		setVisible(true);
	}
}
