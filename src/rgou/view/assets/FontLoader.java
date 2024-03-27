package rgou.view.assets;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FontLoader {
	public static Font loadFont(String fontFilePath) {
		Font customFont = null;
		int fontSize = 20;

		try {
			File fontFile = new File(fontFilePath);
			FileInputStream fontStream = new FileInputStream(fontFile);

			Font baseFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);

			customFont = baseFont.deriveFont((float) fontSize);

			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(customFont);

			fontStream.close();
		} catch (IOException | FontFormatException e) {
			// loading failed, load system font instead
			System.err.println("Error loading font: " + e.getMessage());
			customFont = new Font(Font.SANS_SERIF, Font.PLAIN, fontSize);
		}

		return customFont;
	}
}
