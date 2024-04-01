package rgou.view.components.ui;

import javax.swing.JComponent;

import rgou.model.Tile;
import rgou.view.components.primitives.ImageBox;
import rgou.view.components.primitives.RenderScaleContext;

import java.awt.Graphics;

public class TilePanel extends JComponent {
	private final int TILE_BOUNDS_SIZE = 36;
	private final int TILE_SIZE = 32;
	private final int PADDING = 4;

	private Tile tile;
	private RenderScaleContext renderScaleContext;

	public TilePanel(RenderScaleContext renderScaleContext, Tile tile) {
		this.tile = tile;
		this.renderScaleContext = renderScaleContext;
	}

	@Override
	protected void paintComponent(Graphics g) {
		setPreferredSize(renderScaleContext.scaleDimension(TILE_BOUNDS_SIZE, TILE_BOUNDS_SIZE));

		ImageBox tileImageBox = new ImageBox(tile.getTextureName());
		tileImageBox.setBounds(renderScaleContext.scaleRectangle(PADDING, PADDING, TILE_SIZE, TILE_SIZE));
		add(tileImageBox);
	}
}
