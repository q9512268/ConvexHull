package de.feu.propra18.q9512268.app.main.objects;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Class representing a simple circle.
 * <P>
 * This circle only consists of DoubleProperty values for x center, y center coordinates and its radius.
 * 
 * @author Christian Luetticke
 * @version 2.0
 */
public class SimpleCircle {

	/**
	 * The x coordinate of the center of the circle.
	 */
	private DoubleProperty x;
	/**
	 * The y coordinate of the center of the circle.
	 */
	private DoubleProperty y;
	/**
	 * The radius of the circle.
	 */
	private DoubleProperty radius;

	/**
	 * SimpleCircle constructor. 
	 * <P>
	 * Initializes x,y and radius properties of type SimpleDoubleProperty.
	 */
	public SimpleCircle() {
		x = new SimpleDoubleProperty();
		y = new SimpleDoubleProperty();
		radius = new SimpleDoubleProperty();
	}

	/**
	 * Get the center x property of the circle.
	 * @return
	 * DoubleProperty Center X DoubleProperty.
	 */
	public DoubleProperty getX() {
		return x;
	}

	/**
	 * Sets the center x property of the circle.
	 * @param x A new Double value for the center x property.
	 */
	public void setX(Double x) {
		this.x.set(x);
	}

	/**
	 * Get the center y property of the circle.
	 * @return
	 * DoubleProperty Center Y DoubleProperty.
	 */
	public DoubleProperty getY() {
		return y;
	}

	/**
	 * Sets the center y property of the circle.
	 * @param y A new Double value for the center y property.
	 */
	public void setY(Double y) {
		this.y.set(y);
	}

	/**
	 * Get the radius property of the circle.
	 * @return
	 * DoubleProperty Radius size DoubleProperty.
	 */
	public DoubleProperty getRadius() {
		return radius;
	}

	/**
	 * Sets the radius property of the circle.
	 * @param radius A new Double value for the radius property.
	 */
	public void setRadius(Double radius) {
		this.radius.set(radius);
	}

	/**
	 * Clears the circle. Sets x,y and radius properties to zero.
	 */
	public void clear() {
		this.x.set(0);
		this.y.set(0);
		this.radius.set(0);
	}

}
