package rgou.view.components.ui;

import javax.swing.JPanel;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import rgou.controllers.GameStateController;
import rgou.model.Board;
import rgou.model.Tile;
import rgou.view.components.primitives.ImageBox;
import rgou.view.components.primitives.RenderScaleContext;

public class BoardPanel extends JPanel {
	private Point highlightedTilePoint = null;
	private RenderScaleContext renderScaleContext;
	private ImageBox outline;

	private final int TILE_SIZE = 36;
	private final int TILE_PADDING = 4;
	private final int PAWN_SIZE = 18;
	private final int PAWN_MARGIN = 11;
	private final int OUTLINE_PADDING = 2;

	public BoardPanel(RenderScaleContext renderScaleContext, GameStateController gameStateController) {
		setLayout(null);
		setOpaque(false);

		Board board = gameStateController.getBoard();
		this.renderScaleContext = renderScaleContext;
		this.outline = new ImageBox("pawns/pawn-target.png");
		this.outline.setVisible(false);
		add(this.outline);

		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 3; x++) {
				Tile currentTile = board.getTile(x, y);
				Point currentTilePoint = new Point(x, y);

				if (currentTile.getPawn() != null) {
					ImageBox pawnImageBox = new ImageBox("pawns/pawn-" + currentTile.getPawn() + ".png");
					pawnImageBox.setBounds(renderScaleContext.scaleRectangle(
							PAWN_MARGIN + x * TILE_SIZE,
							PAWN_MARGIN + y * TILE_SIZE,
							PAWN_SIZE,
							PAWN_SIZE));
					add(pawnImageBox);
				}

				if (!currentTile.isVisible()) {
					continue;
				}

				ImageBox boardTilePanel = new ImageBox(currentTile.getTextureName());
				boardTilePanel.setBounds(renderScaleContext.scaleRectangle(
						TILE_PADDING + x * TILE_SIZE,
						TILE_PADDING + y * TILE_SIZE,
						TILE_SIZE - TILE_PADDING,
						TILE_SIZE - TILE_PADDING));

				boolean isInputRequired = gameStateController.getPlayerAgent(board.getActivePlayer()).isInputRequired();
				if (isInputRequired) {

					boardTilePanel.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseEntered(MouseEvent e) {
							if (board.isRollAvailable()) {
								return;
							}
							highlightedTilePoint = board.getPossibleMove(currentTilePoint);
							updateHighlight();
						}

						@Override
						public void mouseExited(MouseEvent e) {
							if (board.isRollAvailable()) {
								return;
							}
							highlightedTilePoint = null;
							updateHighlight();
						}

						@Override
						public void mouseReleased(MouseEvent e) {
							board.makeMove(currentTilePoint);
						}
					});
				}

				add(boardTilePanel);

			}
		}

		ImageBox boardBg = new ImageBox("tiles/board-bg.png");
		boardBg.setBounds(renderScaleContext.scaleRectangle(0, 0, 112, 292));
		add(boardBg);
	}

	private void updateHighlight() {
		if (highlightedTilePoint != null) {
			outline.setBounds(renderScaleContext.scaleRectangle(
					OUTLINE_PADDING + highlightedTilePoint.x * TILE_SIZE,
					OUTLINE_PADDING + highlightedTilePoint.y * TILE_SIZE,
					TILE_SIZE,
					TILE_SIZE));
			outline.setVisible(true);
		} else {
			outline.setVisible(false);
		}
		repaint();
		revalidate();
	}
}
