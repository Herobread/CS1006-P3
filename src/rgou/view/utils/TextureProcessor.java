package rgou.view.utils;

import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class TextureProcessor {

	public static Image getScaledImage(Image srcImg, int width, int height, Object hint, boolean higherQuality) {
		BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
		g2.drawImage(srcImg, 0, 0, width, height, null);
		g2.dispose();
		return resizedImg;
	}

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
