package rgou.view.components;

import javax.swing.JButton;

import rgou.view.utils.TextureLoader;
import rgou.view.utils.TextureProcessor;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Cursor;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * A custom JButton component with image support.
 */
public class ImageButton extends JButton {
	private Image originalImage;

	/**
	 * Constructs an ImageButton with the specified image path.
	 *
	 * Note: base path already included
	 * 
	 * @param imagePath The path to the image file.
	 */
	public ImageButton(String imagePath) {
		this.originalImage = TextureLoader.loadImage(imagePath);
		setIcon(new ImageIcon(originalImage));

		setBorder(null);
		setBorderPainted(false);
		setContentAreaFilled(false);

		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		if (originalImage != null) {
			// rescale image, so that it's not blurry
			setIcon(new ImageIcon(
					TextureProcessor.getScaledImage(
							originalImage,
							width,
							height,
							RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR,
							false)));
		}

		super.setBounds(x, y, width, height);
	}

	public void setOpacity(float opacity) {
		BufferedImage imageWithOpactiy = TextureProcessor.setOpacity(originalImage, opacity);

		setIcon(new ImageIcon(imageWithOpactiy));
	}
}
