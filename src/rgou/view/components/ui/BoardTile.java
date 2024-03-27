package rgou.view.components.ui;

import javax.swing.JPanel;

import rgou.view.components.primitives.ImageBox;
import rgou.view.components.primitives.RenderScaleContext;

public class BoardTile extends JPanel {
	public BoardTile(RenderScaleContext renderScaleContext, String texture, boolean isHighlighted) {
		setLayout(null);
		setOpaque(false);
		setPreferredSize(renderScaleContext.scaleDimension(36, 36));

		ImageBox tile = new ImageBox(texture);
		tile.setBounds(renderScaleContext.scaleRectangle(2, 2, 32, 32));
		add(tile);

		if (isHighlighted) {
			ImageBox ring = new ImageBox("pawns/pawn-target.png");
			ring.setBounds(renderScaleContext.scaleRectangle(0, 0, 36, 36));
			add(ring);
		}
	}
}
