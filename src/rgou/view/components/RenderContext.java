package rgou.view.components;

import java.awt.Dimension;
import java.awt.Rectangle;

/**
 * A utility class for managing rendering context properties, such as scale.
 * These properties are commonly shared among different components.
 */
public class RenderContext {

	/** The scale factor applied to rendering dimensions. */
	private double scale;

	/**
	 * Constructs a RenderContext object with the given scale.
	 *
	 * @param scale The scale factor to be applied to rendering dimensions.
	 */
	public RenderContext(double scale) {
		this.scale = scale;
	}

	/**
	 * Gets the current scale factor.
	 *
	 * @return The scale factor.
	 */
	public double getScale() {
		return scale;
	}

	/**
	 * Sets the scale factor.
	 *
	 * @param scale The new scale factor to be applied.
	 */
	public void setScale(double scale) {
		this.scale = scale;
	}

	/**
	 * Scales the provided rectangle dimensions by the current scale factor.
	 *
	 * @param x      The X-coordinate of the rectangle.
	 * @param y      The Y-coordinate of the rectangle.
	 * @param width  The width of the rectangle.
	 * @param height The height of the rectangle.
	 * @return A new Rectangle with scaled dimensions.
	 */
	public Rectangle scaleRectangle(int x, int y, int width, int height) {
		return new Rectangle((int) (x * scale), (int) (y * scale), (int) (width * scale), (int) (height * scale));
	}

	/**
	 * Scales the provided dimensions by the current scale factor.
	 *
	 * @param width  The width to be scaled.
	 * @param height The height to be scaled.
	 * @return A new Dimension object with scaled width and height.
	 */
	public Dimension scaleDimension(int width, int height) {
		return new Dimension((int) (width * scale), (int) (height * scale));
	}
}
