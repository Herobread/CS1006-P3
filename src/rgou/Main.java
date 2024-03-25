package rgou;

import javax.swing.SwingUtilities;

import rgou.view.MainFrame;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			MainFrame frame = new MainFrame();
			frame.setVisible(true);
		});
	}
}