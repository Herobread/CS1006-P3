package rgou.view.scenes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import rgou.controllers.GameSceneController;
import rgou.controllers.GameStateController;
import rgou.controllers.scenes.RemoteOptionsController;
import rgou.view.GameScenes;
import rgou.view.components.primitives.ImageButton;
import rgou.view.components.primitives.LabelBox;
import rgou.view.components.primitives.RenderScaleContext;
import rgou.view.sceneTemplates.GameSceneBase;

public class RemoteOptionsScene extends GameSceneBase {
	private GameStateController gameStateController;

	public RemoteOptionsScene(GameSceneController gameSceneController, GameStateController gameStateController) {
		super(gameSceneController);
		this.gameStateController = gameStateController;
	}

	public void run() {
		RenderScaleContext renderScaleContext = new RenderScaleContext(gameSceneController.getSceneScale());
		LabelBox.setFontSize(renderScaleContext.scaleFont(16));

		ImageButton exit = new ImageButton("buttons/cross.png");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameSceneController.setActiveScene(GameScenes.MAIN_MENU);
			}
		});
		exit.setBounds(renderScaleContext.scaleRectangle(596, 10, 20, 20));
		add(exit);

		LabelBox text = new LabelBox("Play remote:");
		text.setBounds(renderScaleContext.scaleRectangle(258, 155, 120, 19));
		text.setHorizontalAlignment(SwingConstants.CENTER);
		add(text);

		ImageButton join = new ImageButton("buttons/join.png");
		join.setBounds(renderScaleContext.scaleRectangle(323, 183, 42, 20));
		join.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RemoteOptionsController.onRemoteJoin(gameStateController);
				System.out.println("join");
				gameSceneController.setActiveScene(GameScenes.SELECT_REMOTE_JOIN);
			}
		});
		add(join);

		ImageButton host = new ImageButton("buttons/host.png");
		host.setBounds(renderScaleContext.scaleRectangle(271, 183, 42, 20));
		host.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RemoteOptionsController.onRemoteHost(gameStateController);
				System.out.println("host");
				gameSceneController.setActiveScene(GameScenes.SELECT_REMOTE_HOST);
			}
		});
		add(host);
	}
}
