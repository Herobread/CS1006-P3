package rgou.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.BorderLayout;

import rgou.view.scenes.Scenes;

public class MainFrame extends JFrame {
	private GameSceneController gameSceneController;
	private JPanel activeScenePanel;

	public MainFrame() {
		setTitle("Royal game of Ur");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(504, 358);
		centerFrame();
	}

	private void centerFrame() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - getWidth()) / 2;
		int y = (screenSize.height - getHeight()) / 2;
		setLocation(x, y);
	}
}
