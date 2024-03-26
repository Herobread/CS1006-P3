package rgou.view;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;

public class MainFrame extends JFrame {
	public MainFrame() {
		setTitle("Royal game of Ur");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(504, 358);
		centerFrame();
		setVisible(true);
	}

	private void centerFrame() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - getWidth()) / 2;
		int y = (screenSize.height - getHeight()) / 2;
		setLocation(x, y);
	}
}
