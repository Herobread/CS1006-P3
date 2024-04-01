package rgou.view.components.primitives;

import javax.swing.*;
import rgou.view.assetLoaders.TextureLoader;
import java.awt.*;

public class ImageBox extends JComponent {
	private Image image;

	public ImageBox(String imagePath) {
		setImagePath(imagePath);
	}

	public void setImagePath(String imagePath) {
		this.image = TextureLoader.loadImage(imagePath);
		invalidate();
		revalidate();
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			Graphics2D g2d = (Graphics2D) g.create();
			int width = getWidth();
			int height = getHeight();
			g2d.drawImage(image, 0, 0, width, height, null);
			g2d.dispose();
		}
	}

	@Override
	public Dimension getPreferredSize() {
		if (image != null) {
			return new Dimension(image.getWidth(null), image.getHeight(null));
		}
		return super.getPreferredSize();
	}
}
