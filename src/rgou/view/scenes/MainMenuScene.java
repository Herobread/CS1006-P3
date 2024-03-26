package rgou.view.scenes;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuScene extends JPanel implements Runnable {
	public MainMenuScene() {
		createUI();
	}

	private void createUI() {
		JButton button = new JButton("Click Me");
		button.addActionListener(new ButtonClickListener());

		add(button);
	}

	public void run() {
	}

	private class ButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Button clicked!");
		}
	}
}
