package rgou.view.scenes.remote.loading;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import rgou.controllers.GameSceneController;
import rgou.controllers.GameStateController;
import rgou.controllers.scenes.RemoteConnector;
import rgou.model.remote.RemoteStatus;
import rgou.view.GameScenes;
import rgou.view.components.primitives.ImageButton;
import rgou.view.components.primitives.LabelBox;
import rgou.view.components.primitives.RenderScaleContext;
import rgou.view.sceneTemplates.GameSceneBase;

public class LoadingRemoteScene extends GameSceneBase {
	private GameStateController gameStateController;
	private LabelBox statusBox;

	public LoadingRemoteScene(GameSceneController gameSceneController, GameStateController gameStateController) {
		super(gameSceneController);
		this.gameStateController = gameStateController;
	}

	public void run() {
		RenderScaleContext renderScaleContext = new RenderScaleContext(gameSceneController.getSceneScale());
		LabelBox.setFontSize(renderScaleContext.scaleFont(16));

		statusBox = new LabelBox("-");
		statusBox.setBounds(renderScaleContext.scaleRectangle(68, 170, 500, 19));
		statusBox.setHorizontalAlignment(SwingConstants.CENTER);
		add(statusBox);

		RemoteStatus remoteStatus = gameStateController.getRemoteStatus();
		remoteStatus.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				RemoteStatus remoteStatus = (RemoteStatus) e.getSource();
				System.out.println(remoteStatus.getStatus());
				statusBox.setText(remoteStatus.getStatus());
			}
		});

		ImageButton exit = new ImageButton("buttons/cross.png");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameSceneController.setActiveScene(GameScenes.MAIN_MENU);
			}
		});
		exit.setBounds(renderScaleContext.scaleRectangle(596, 10, 20, 20));
		add(exit);

		LabelBox text = new LabelBox("Host a game");
		text.setBounds(renderScaleContext.scaleRectangle(221, 10, 200, 19));
		text.setHorizontalAlignment(SwingConstants.CENTER);
		add(text);

		////////////////////////// connect in parallel:

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					RemoteConnector.createConnection(gameSceneController, gameStateController);
				} catch (Exception e) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							JOptionPane.showMessageDialog(null, e.getMessage(), "Connection error",
									JOptionPane.ERROR_MESSAGE);
							gameSceneController.setActiveScene(GameScenes.MAIN_MENU);
						}
					});
				}
			}
		}).start();

	}
}
