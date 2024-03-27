package rgou.view.components;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

import rgou.view.assets.FontLoader;

public class LabelBox extends JLabel {
	private static Font font = FontLoader.loadFont("assets/fonts/pixelify-sans.ttf");

	public LabelBox(String text) {
		super(text);

		setFont(font);
	}

	public static void setFontSize(int size) {
		if (font != null) {
			font = font.deriveFont((float) size);
		}
	}
}
