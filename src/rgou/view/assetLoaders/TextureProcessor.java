package rgou.view.assetLoaders;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * Provides methods for processing textures.
 */
public class TextureProcessor {

	/**
	 * Scales an image to the specified width and height.
	 *
	 * @param srcImg        the source image to scale
	 * @param width         the width of the scaled image
	 * @param height        the height of the scaled image
	 * @param hint          the rendering hint
	 * @param higherQuality whether to use higher quality scaling
	 * @return the scaled image
	 */
	public static Image getScaledImage(Image srcImg, int width, int height, Object hint, boolean higherQuality) {
		BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
		g2.drawImage(srcImg, 0, 0, width, height, null);
		g2.dispose();
		return resizedImg;
	}

	/**
	 * Sets the opacity of an image.
	 *
	 * @param sourceImage the source image
	 * @param opacity     the opacity value (between 0.0 and 1.0)
	 * @return the image with the specified opacity
	 * @throws IllegalArgumentException if the opacity value is out of range
	 */
	public static BufferedImage setOpacity(Image sourceImage, float opacity) {
		if (opacity >= 0.0f && opacity <= 1.0f) {
			BufferedImage bufferedImage = new BufferedImage(sourceImage.getWidth(null), sourceImage.getHeight(null),
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics = bufferedImage.createGraphics();
			graphics.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, opacity));
			graphics.drawImage(sourceImage, 0, 0, null);
			graphics.dispose();
			return bufferedImage;
		} else {
			throw new IllegalArgumentException("Opacity value must be between 0.0 and 1.0");
		}
	}
}
