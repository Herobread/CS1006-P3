package rgou.view.components.ui;

import javax.swing.JPanel;

import rgou.view.components.primitives.ImageBox;
import rgou.view.components.primitives.RenderScaleContext;

public class BoardPanel extends JPanel {
	public BoardPanel(RenderScaleContext renderScaleContext) {
		setLayout(null);
		setOpaque(false);

		final int TILE_SIZE = 36;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 3; j++) {
				BoardTile tile = new BoardTile(renderScaleContext, "tiles/rosette.png", i == 1 && j == 2);
				tile.setBounds(renderScaleContext.scaleRectangle(
						2 + j * TILE_SIZE,
						2 + i * TILE_SIZE,
						TILE_SIZE,
						TILE_SIZE));
				add(tile);
				add(tile);
			}
		}

		ImageBox boardBg = new ImageBox("tiles/board-bg.png");
		boardBg.setBounds(renderScaleContext.scaleRectangle(0, 0, 112, 292));
		add(boardBg);
	}
}
