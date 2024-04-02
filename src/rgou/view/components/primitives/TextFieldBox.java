package rgou.view.components.primitives;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import rgou.view.assetLoaders.FontLoader;

public class TextFieldBox extends JTextField {
	private static Font font = null;

	/**
	 * Static initializer to load the custom font.
	 */
	static {
		loadCustomFont();
	}

	/**
	 * Constructs a new {@code TextFieldBox} instance and sets the value and custom
	 * font if available.
	 */
	public TextFieldBox(String defaultValue) {
		super(defaultValue); // Call the superclass constructor with the default value
		if (font != null) {
			this.setFont(font);
		}
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
			// Update font for existing instances if needed
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

	/**
	 * Adds a listener that will be notified whenever the text changes.
	 * 
	 * @param listener A functional interface that accepts a String parameter.
	 */
	public void addTextChangeListener(TextChangeListener listener) {
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listener.textChanged(TextFieldBox.this.getText());
			}
		});
	}

	/**
	 * Functional interface for text change events.
	 */
	@FunctionalInterface
	public interface TextChangeListener {
		void textChanged(String newText);
	}
}
