package de.feu.propra18.q9512268.app.main.objects;

import java.util.List;
import java.util.Objects;

import de.feu.propra18.q9512268.app.main.algorithm.circle.InnerCircleCalculator;

/**
 * Class representing an edge object.
 * <P>
 * An edge represents part of the convex hull outline.
 * 
 * @author Christian Luetticke
 * @version 2.0
 */
public class Edge {

	/**
	 * The previous edge in the outline which is connected to this current edge.
	 */
	private Edge previous;
	/**
	 * The next edge in the outline which is connected to this current edge.
	 */
	private Edge next;
	/**
	 * The x coordinate of the starting point of the edge.
	 */
	private double startX;
	/**
	 * The y coordinate of the starting point of the edge.
	 */
	private double startY;
	/**
	 * The x coordinate of the end point of the edge.
	 */
	private double endX;
	/**
	 * The y coordinate of the end point of the edge.
	 */
	private double endY;
	/**
	 * The inner circle of the three edges: previous, this and next.
	 */
	private SimpleCircle innerCircle;

	/**
	 * Constructor for an edge.
	 * <P>
	 * Sets position attributes and created a new {@link SimpleCircle} object which
	 * represents the inner circle.
	 * 
	 * @param startX
	 *            The start x position.
	 * @param startY
	 *            The start y position.
	 * @param endX
	 *            The end x position.
	 * @param endY
	 *            The end y position.
	 */
	public Edge(double startX, double startY, double endX, double endY) {

		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;

		innerCircle = new SimpleCircle();
	}

	/**
	 * Get the previous edge.
	 * 
	 * @return Edge The previous neighbor of this edge.
	 */
	public Edge getPrevious() {
		return previous;
	}

	/**
	 * Set a new previous edge.
	 * <P>
	 * (Re)calculates the {@link Edge#innerCircle}.
	 * 
	 * @param previous
	 *            The new previous edge.
	 */
	public void setPrevious(Edge previous) {
		this.previous = previous;
		update_innerCircle();
	}

	/**
	 * Get the next edge.
	 * 
	 * @return Edge The next neighbor of this edge.
	 */
	public Edge getNext() {
		return next;
	}

	/**
	 * Set a new next edge.
	 * <P>
	 * (Re)calculates the {@link Edge#innerCircle}.
	 * 
	 * @param next
	 *            The new next edge.
	 */
	public void setNext(Edge next) {
		this.next = next;
		update_innerCircle();
	}

	/**
	 * Returns the inner circle of the three edges: previous, this edge, next.
	 * 
	 * @return SimpleCircle A {@link SimpleCircle} object representing the inner circle.
	 */
	public SimpleCircle getInnerCircle() {
		return innerCircle;
	}

	/**
	 * Called whenever the previous or next edge changes. Uses
	 * {@link InnerCircleCalculator#calculate_innerCircle(Edge)} to calculate the inner circle and update the
	 * {@link Edge#innerCircle} coordinates.
	 */
	private void update_innerCircle() {
		List<Double> circleInfo = InnerCircleCalculator.calculate_innerCircle(this);
		if (!circleInfo.isEmpty()) {
			this.innerCircle.setX(circleInfo.get(0));
			this.innerCircle.setY(circleInfo.get(1));
			this.innerCircle.setRadius(circleInfo.get(2));
		}
	}

	/**
	 * Get access to the start x position.
	 * 
	 * @return double The x coordinate of the edge starting position.
	 */
	public double getStartX() {
		return startX;
	}

	/**
	 * Set the start x position
	 * 
	 * @param startX
	 *            The new x coordinate of the edge starting position.
	 */
	public void setStartX(double startX) {
		this.startX = startX;
	}

	/**
	 * Get access to the start y position.
	 * 
	 * @return double The y coordinate of the edge starting position.
	 */
	public double getStartY() {
		return startY;
	}

	/**
	 * Set the start y position
	 * 
	 * @param startY
	 *            The new y coordinate of the edge starting position.
	 */
	public void setStartY(double startY) {
		this.startY = startY;
	}

	/**
	 * Get access to the end x position.
	 * 
	 * @return double The x coordinate of the edge end position.
	 */
	public double getEndX() {
		return endX;
	}

	/**
	 * Set the end x position
	 * 
	 * @param endX
	 *            The new x coordinate of the edge end position.
	 */
	public void setEndX(double endX) {
		this.endX = endX;
	}

	/**
	 * Get access to the end y position.
	 * 
	 * @return double The y coordinate of the edge end position.
	 */
	public double getEndY() {
		return endY;
	}

	/**
	 * Set the end y position
	 * 
	 * @param endY
	 *            The new y coordinate of the edge end position.
	 */
	public void setEndY(double endY) {
		this.endY = endY;
	}

	/**
	 * Override hashCode method to create hash for {@link Edge} instance only based
	 * on its startX, startY, endX and endY coordinates.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(startX, startY, endX, endY);
	}

	/**
	 * Override equals method to compare to {@link Edge} instance only based on its
	 * startX, startY, endX and endY coordinates.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (getStartX() != other.getStartX())
			return false;
		if (getStartY() != other.getStartY())
			return false;
		if (getEndX() != other.getEndX())
			return false;
		if (getEndY() != other.getEndY())
			return false;
		return true;
	}

}
