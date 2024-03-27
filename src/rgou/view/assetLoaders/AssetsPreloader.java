package rgou.view.assetLoaders;

import rgou.view.components.LabelBox;

public class AssetsPreloader {
	public static void load() {
		LabelBox.loadCustomFont();

		String[] textures = {
				"buttons/host.png",
				"buttons/join.png",
				"buttons/ok.png",
				"buttons/play-ai.png",
				"buttons/play-local.png",
				"buttons/play-remote.png",
				"buttons/roll.png",
				"dice/numbers/0.png",
				"dice/numbers/1.png",
				"dice/numbers/2.png",
				"dice/numbers/3.png",
				"dice/numbers/4.png",
				"dice/variants/dice-notop-left-up.png",
				"dice/variants/dice-notop-right-left.png",
				"dice/variants/dice-notop-up-right.png",
				"dice/variants/dice-top-left.png",
				"dice/variants/dice-top-right.png",
				"dice/variants/dice-top-up.png",
				"pawns/pawn-black.png",
				"pawns/pawn-white.png",
				"pawns/pawn-target.png",
				"tiles/board-bg.png",
				"tiles/rosette.png",
				"tiles/tile-1.png",
				"tiles/tile-2.png",
				"tiles/tile-3.png",
				"tiles/tile-4.png",
				"ui/background.png",
				"ui/logo.png",
				"not-found.png",
		};

		for (String texture : textures) {
			TextureLoader.preLoadToCache(texture);
		}
	}
}
