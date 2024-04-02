package rgou.view.components.primitives;

import java.awt.Font;
import javax.swing.JTextField;

import rgou.view.assetLoaders.FontLoader;

public class TextFieldBox extends JTextField {
	private static Font font = null;

	static {
		loadCustomFont(); // Load the font when the class is loaded.
	}

	public TextFieldBox() {
		super();
		if (font != null) {
			this.setFont(font); // Apply the loaded font to the TextFieldBox instance.
		}
	}

	public static void setFontSize(int size) {
		if (font != null) {
			font = font.deriveFont((float) size);
			// Update the font size for newly created instances.
			// Note: This won't update existing instances. You'd need to manually update
			// them or implement a listener mechanism.
		}
	}

	private static void loadCustomFont() {
		if (font == null) { // Load the font only if it has not been loaded.
			font = FontLoader.loadFont("assets/fonts/pixelify-sans.ttf");
		}
	}
}
