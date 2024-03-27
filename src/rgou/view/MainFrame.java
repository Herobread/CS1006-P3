package rgou.view;

import java.awt.Color;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	public MainFrame() {
		setTitle("Royal game of Ur");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1280, 720);

		setBackground(Color.BLACK);

		// center window itself
		setLocationRelativeTo(null);

		// disable layout manager and handle everything manually:
		setLayout(null);

		setVisible(true);
	}

}
