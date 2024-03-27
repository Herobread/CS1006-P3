package rgou.view.components.primitives;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * A utility class for managing rendering context properties, such as scale.
 * These properties are commonly shared among different components.
 */
public class RenderScaleContext {

	/** The scale factor applied to rendering dimensions. */
	private double scale;

	/**
	 * Constructs a RenderContext object with the given scale.
	 *
	 * @param scale The scale factor to be applied to rendering dimensions.
	 */
	public RenderScaleContext(double scale) {
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
		return new Rectangle(scaleValue(x), scaleValue(y), scaleValue(width), scaleValue(height));
	}

	/**
	 * Scales the provided dimensions by the current scale factor.
	 *
	 * @param width  The width to be scaled.
	 * @param height The height to be scaled.
	 * @return A new Dimension object with scaled width and height.
	 */
	public Dimension scaleDimension(int width, int height) {
		return new Dimension(scaleValue(width), scaleValue(height));
	}

	/**
	 * Scale point
	 * 
	 * @param x
	 * @param y
	 * @return Point
	 */
	public Point scalePoint(int x, int y) {
		return new Point(scaleValue(x), scaleValue(y));
	}

	/**
	 * Scales 1 value by the current scale factor
	 * 
	 * @param value original value
	 * @return scaled value
	 */
	public int scaleValue(int value) {
		return (int) (value * scale);
	}

	public int scaleFont() {
		return scaleFont(16);
	}

	public int scaleFont(int font) {
		return (int) (font * scale);
	}
}
