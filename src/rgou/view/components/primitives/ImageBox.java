package rgou.view.components.primitives;

import javax.swing.JComponent;
import rgou.view.assetLoaders.TextureLoader;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 * Represents a component for displaying an image.
 */
public class ImageBox extends JComponent {
	private Image image;

	/**
	 * Constructs an ImageBox with the specified image path.
	 *
	 * @param imagePath the path to the image
	 */
	public ImageBox(String imagePath) {
		setImagePath(imagePath);
	}

	/**
	 * Sets the image path and loads the image.
	 *
	 * @param imagePath the path to the image
	 */
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
