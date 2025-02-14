package rgou.controllers.scenes;

import rgou.controllers.GameStateController;
import rgou.model.remote.RemoteConfig;
import rgou.model.remote.RemoteTypes;

public class RemoteOptionsController {
	public static void onRemoteJoin(GameStateController gameStateController) {
		RemoteConfig remoteConfig = new RemoteConfig(RemoteTypes.CLIENT);

		gameStateController.setRemoteConfig(remoteConfig);
	}

	public static void onRemoteHost(GameStateController gameStateController) {
		RemoteConfig remoteConfig = new RemoteConfig(RemoteTypes.SERVER);

		gameStateController.setRemoteConfig(remoteConfig);
	}
}
