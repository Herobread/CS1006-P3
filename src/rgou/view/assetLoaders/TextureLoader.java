package rgou.view.assetLoaders;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class TextureLoader {
	private static String BASE_TEXTURE_PATH = "assets/textures/";
	private static String TEXTURE_NOT_FOUND = "not-found.png";

	private static HashMap<String, Image> textureCache = new HashMap<>();

	public static Image loadImage(String textureName) {
		// Check if the texture is already loaded in the cache
		if (textureCache.containsKey(textureName)) {
			return textureCache.get(textureName);
		}

		File imageFile = new File(BASE_TEXTURE_PATH + textureName);

		if (imageFile.exists()) {
			try {
				Image textureImage = ImageIO.read(imageFile);

				// cache
				textureCache.put(textureName, textureImage);

				return textureImage;
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

	public static void preLoadToCache(String textureName) {
		File imageFile = new File(BASE_TEXTURE_PATH + textureName);

		if (!imageFile.exists()) {
			throw new RuntimeException(
					"Failed to preload image: image " + BASE_TEXTURE_PATH + textureName +
							"doesn't exist.");
		}

		try {
			textureCache.put(textureName, ImageIO.read(imageFile));
		} catch (IOException e) {
			System.err.println("Failed to preload image." + BASE_TEXTURE_PATH +
					textureName);
		}
	}
}
