package rgou.view.components;

import javax.swing.*;

import rgou.view.utils.TextureLoader;

import java.awt.Image;
import java.io.IOException;

/**
 * A custom component to draw an image.
 */
public class ImageBox extends JComponent {
	public ImageBox(String imagePath) throws IOException {
		Image icon = TextureLoader.loadImage(imagePath);
	}
}
