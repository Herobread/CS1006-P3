package rgou.view.scenes.remote;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import rgou.controllers.GameSceneController;
import rgou.controllers.GameStateController;
import rgou.model.remote.RemoteConfig;
import rgou.view.GameScenes;
import rgou.view.components.primitives.ImageButton;
import rgou.view.components.primitives.LabelBox;
import rgou.view.components.primitives.RenderScaleContext;
import rgou.view.components.primitives.TextFieldBox;
import rgou.view.sceneTemplates.GameSceneBase;

public class JoinScene extends GameSceneBase {
	GameStateController gameStateController;

	public JoinScene(GameSceneController gameSceneController, GameStateController gameStateController) {
		super(gameSceneController);
		this.gameStateController = gameStateController;
	}

	public void run() {
		RemoteConfig remoteConfig = gameStateController.getRemoteConfig();
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

		LabelBox hostnameLabel = new LabelBox("Hostname:");
		hostnameLabel.setBounds(renderScaleContext.scaleRectangle(221, 115, 200, 19));
		add(hostnameLabel);

		TextFieldBox hostTextFieldBox = new TextFieldBox(remoteConfig.getHostname());
		hostTextFieldBox.setBounds(renderScaleContext.scaleRectangle(221, 140, 200, 23));
		hostTextFieldBox.addTextChangeListener(newText -> {
			remoteConfig.setHostname(newText);
		});
		add(hostTextFieldBox);

		LabelBox portLabel = new LabelBox("Port:");
		portLabel.setBounds(renderScaleContext.scaleRectangle(221, 169, 200, 19));
		add(portLabel);

		TextFieldBox portTextFieldBox = new TextFieldBox(remoteConfig.getPort());
		portTextFieldBox.setBounds(renderScaleContext.scaleRectangle(221, 194, 200, 23));
		portTextFieldBox.addTextChangeListener(newText -> {
			remoteConfig.setPort(newText);
		});
		add(portTextFieldBox);

		LabelBox text = new LabelBox("Join a game");
		text.setBounds(renderScaleContext.scaleRectangle(221, 10, 200, 19));
		text.setHorizontalAlignment(SwingConstants.CENTER);
		add(text);

		ImageButton hostButton = new ImageButton("buttons/join.png");
		hostButton.setBounds(renderScaleContext.scaleRectangle(297, 223, 42, 20));
		hostButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameSceneController.setActiveScene(GameScenes.SELECT_REMOTE_LOADING);
			}
		});
		add(hostButton);
	}
}
