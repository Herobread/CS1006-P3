package rgou.view.assets;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TextureLoader {
	private static String BASE_TEXTURE_PATH = "assets/textures/";
	private static String TEXTURE_NOT_FOUND = "not-found.png";

	public static Image loadImage(String textureName) {
		File imageFile = new File(BASE_TEXTURE_PATH + textureName);

		if (imageFile.exists()) {
			try {
				return ImageIO.read(imageFile);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Failed to load image: " + e.getMessage());
			}
		} else {
			System.err.println("Target file not found: " + BASE_TEXTURE_PATH + textureName);
			System.err.println("Loading placeholder image.");

			File notFoundImageFile = new File(BASE_TEXTURE_PATH + TEXTURE_NOT_FOUND);
			if (!notFoundImageFile.exists()) {
				throw new RuntimeException("Failed to load assets.");
			}

			try {
				return ImageIO.read(notFoundImageFile);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Failed to load placeholder image: " + e.getMessage());
			}
		}
	}
}
