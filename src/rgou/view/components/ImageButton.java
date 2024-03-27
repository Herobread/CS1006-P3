package rgou.view.components;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * A custom JButton component with image support.
 */
public class ImageButton extends JButton {
	private Image originalImage;
	private String BASE_TEXTURE_PATH = "assets/textures/";
	private String TEXTURE_NOT_FOUND = "not-found.png";

	/**
	 * Constructs an ImageButton with the specified image path.
	 *
	 * Note: base path already included
	 * 
	 * @param imagePath The path to the image file.
	 */
	public ImageButton(String imagePath) {
		// check if image exists
		File image = new File(BASE_TEXTURE_PATH + imagePath);
		if (!image.exists()) {
			imagePath = TEXTURE_NOT_FOUND;
		}

		// check if texture not found image exists
		File imageNotFound = new File(BASE_TEXTURE_PATH + TEXTURE_NOT_FOUND);
		if (!imageNotFound.exists()) {
			throw new RuntimeException("Could not find placeholder asset.");
		}

		ImageIcon icon = new ImageIcon(BASE_TEXTURE_PATH + imagePath);
		originalImage = icon.getImage();
		setIcon(icon);

		setBorder(null);
		setBorderPainted(false);
		setContentAreaFilled(false);

		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		if (originalImage != null) {
			Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
			scaledImage = getScaledImage(originalImage, width, height,
					RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR, false);
			setIcon(new ImageIcon(scaledImage));
		}
		super.setBounds(x, y, width, height);
	}

	private Image getScaledImage(Image srcImg, int width, int height, Object hint, boolean higherQuality) {
		BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
		g2.drawImage(srcImg, 0, 0, width, height, null);
		g2.dispose();
		return resizedImg;
	}

	/**
	 * Sets the opacity of the button's image.
	 *
	 * @param opacity The opacity value, between 0.0 and 1.0.
	 * @throws IllegalArgumentException if the opacity value is not within the range
	 *                                  [0.0f, 1.0f].
	 */
	public void setOpacity(float opacity) {
		if (opacity >= 0.0f && opacity <= 1.0f) {
			ImageIcon icon = (ImageIcon) getIcon();
			if (icon != null && icon.getImage() != null) {
				BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),
						BufferedImage.TYPE_INT_ARGB);
				Graphics2D graphics = bufferedImage.createGraphics();
				graphics.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, opacity));
				graphics.drawImage(icon.getImage(), 0, 0, null);
				graphics.dispose();
				setIcon(new ImageIcon(bufferedImage));
			}
		} else {
			throw new IllegalArgumentException("Opacity value must be between 0.0 and 1.0");
		}
	}
}
