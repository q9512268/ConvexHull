package de.feu.propra18.q9512268.app.main.objects;

import java.util.Objects;

import de.feu.propra18.q9512268.app.main.container.MainFacade;

/**
 * A simple point class consisting of only two int coordinates. Used during
 * calculation of convex hull.
 * 
 * @author Christian Luetticke
 * @version 2.0
 * @see MainFacade#get_PointsConvexHull()
 */
public class SimplePoint {

	/**
	 * The x coordinate of a {@link SimplePoint}.
	 */
	private int x;
	/**
	 * The y coordinate of a {@link SimplePoint}.
	 */
	private int y;

	/**
	 * Create a {@link SimplePoint} instance from two int coordinates.
	 * 
	 * @param x
	 *            The x coordinate.
	 * @param y
	 *            The y coordinate.
	 */
	public SimplePoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Get access to the x coordinate.
	 * 
	 * @return x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Set x coordinate.
	 * 
	 * @param x
	 *            New x coordinate.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Get access to the y coordinate.
	 * 
	 * @return y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Set y coordinate.
	 * 
	 * @param y
	 *            New y coordinate.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Override hashCode method to create hash for {@link SimplePoint} instance only
	 * based on its x and y coordinates.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	/**
	 * Override equals method to compare to {@link SimplePoint} instance only based
	 * on its x and y coordinates.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimplePoint other = (SimplePoint) obj;
		if (getX() != other.getX())
			return false;
		if (getY() != other.getY())
			return false;
		return true;
	}

}
