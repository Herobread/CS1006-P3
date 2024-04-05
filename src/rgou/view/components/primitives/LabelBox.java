package rgou.view.components.primitives;

import java.awt.Font;
import javax.swing.JLabel;
import rgou.view.assetLoaders.FontLoader;

/**
 * A custom JLabel component with support for custom fonts.
 */
public class LabelBox extends JLabel {
	private static Font font;

	/**
	 * Constructs a LabelBox with an empty text.
	 */
	public LabelBox() {
		this("");
	}

	/**
	 * Constructs a LabelBox with the specified text.
	 *
	 * @param text The text to be displayed on the label.
	 */
	public LabelBox(String text) {
		super(text);
		setFont(font);
	}

	/**
	 * Sets the font size for all LabelBox instances.
	 *
	 * @param size The font size to set.
	 */
	public static void setFontSize(int size) {
		if (font != null) {
			font = font.deriveFont((float) size);
		}
	}

	/**
	 * Loads a custom font for all LabelBox instances.
	 */
	public static void loadCustomFont() {
		font = FontLoader.loadFont("assets/fonts/pixelify-sans.ttf");
	}
}
