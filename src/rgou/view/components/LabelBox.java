package rgou.view.components;

import java.awt.Font;

import javax.swing.JLabel;

import rgou.view.assets.FontLoader;

public class LabelBox extends JLabel {
	private static Font font;

	public LabelBox(String text) {
		super(text);

		setFont(font);
	}

	public static void setFontSize(int size) {
		if (font != null) {
			font = font.deriveFont((float) size);
		}
	}

	public static void loadCustomFont() {
		font = FontLoader.loadFont("assets/fonts/pixelify-sans.ttf");
	}
}
