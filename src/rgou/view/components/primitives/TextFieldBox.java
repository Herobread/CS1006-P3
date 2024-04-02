package rgou.view.components.primitives;

import java.awt.Font;
import javax.swing.JTextField;
import rgou.view.assetLoaders.FontLoader;

/**
 * The {@code TextFieldBox} class extends {@link JTextField} to support custom
 * fonts.
 * It allows for the usage of a custom font file for all instances of text
 * fields within the application.
 * The class automatically loads a specified font upon initialization and
 * applies it to each instance.
 */
public class TextFieldBox extends JTextField {
	private static Font font = null;

	/**
	 * Static initializer to load the custom font.
	 */
	static {
		loadCustomFont();
	}

	/**
	 * Constructs a new {@code TextFieldBox} instance and sets the custom font if
	 * available.
	 */
	public TextFieldBox() {
		super();
		if (font != null) {
			this.setFont(font);
		}
	}

	/**
	 * Sets the font size for the custom font. This size will be applied to all new
	 * instances
	 * of {@code TextFieldBox}. Existing instances will need to be manually updated
	 * if necessary.
	 * 
	 * @param size the size of the font
	 */
	public static void setFontSize(int size) {
		if (font != null) {
			font = font.deriveFont((float) size);
		}
	}

	/**
	 * Loads the custom font from a specified path. This method checks if the font
	 * has already been loaded
	 * to avoid redundant loading. The font is loaded using the {@link FontLoader}
	 * class.
	 */
	private static void loadCustomFont() {
		if (font == null) {
			font = FontLoader.loadFont("assets/fonts/pixelify-sans.ttf");
		}
	}
}
