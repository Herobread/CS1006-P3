package rgou.view.scenes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;

import rgou.view.GameSceneController;
import rgou.view.GameScenes;
import rgou.view.components.ImageButton;
import rgou.view.sceneTemplates.GameSceneBase;

public class GameplayScene extends GameSceneBase {
	public GameplayScene(GameSceneController gameSceneController) {
		super(gameSceneController);
	}

	public void run() {
		double scale = gameSceneController.getSceneScale();
		System.out.println(scale);
		System.out.println("rerender");

		ImageButton goBackButton = new ImageButton("assets/textures/pawns/pawn-black.png");
		goBackButton.setBounds((int) (248 * scale), (int) (226 * scale), (int) (40 * scale), (int) (40 * scale));

		goBackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameSceneController.setActiveScene(GameScenes.MAIN_MENU);
			}
		});

		add(goBackButton);

		setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

		setPreferredSize(new Dimension((int) (300 * scale), 100));

		repaint();
	}
}
