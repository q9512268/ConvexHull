package de.feu.propra18.q9512268.app.main.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

import de.feu.propra18.q9512268.app.main.container.MainFacade;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Class representing a point object.
 * <P>
 * Extending {@link Circle}.
 * 
 * @author Christian Luetticke
 * @version 2.0
 */
public class Point extends Circle implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Every point instance has a sibling in form of an {@link PointType#INNER} or
	 * {@link PointType#OUTER} type.
	 * <P>
	 * The siblings are both displayed on the canvas even though OUTER instances are
	 * not visible.
	 */
	private Point sibling;
	/**
	 * The {@link PointType} of the point instance.
	 */
	private PointType type;
	/**
	 * The initial x coordinate at which the point instance was created via click on
	 * the front-end side.
	 * <P>
	 * Necessary to create a constant {@link Point#hashCode()} of the point
	 * instance.
	 */
	private final double initial_x;
	/**
	 * The initial y coordinate at which the point instance was created via click on
	 * the front-end side.
	 * <P>
	 * Necessary to create a constant {@link Point#hashCode()} of the point
	 * instance.
	 */
	private final double initial_y;
	/**
	 * A reference to the {@link MainFacade} singleton instance.
	 */
	private MainFacade FACADE;

	/**
	 * The radius of points of type {@link PointType#INNER}.
	 */
	private final static int INNER_RADIUS = 6;
	/**
	 * The color of points of type {@link PointType#INNER}.
	 */
	private final static Color INNER_COLOR = Color.BLACK;
	/**
	 * The radius of points of type {@link PointType#OUTER}.
	 */
	private final static int OUTER_RADIUS = 35;
	/**
	 * The color of points of type {@link PointType#OUTER}.
	 * <P>
	 * Set to transparent because OUTER points only serve as a additional construct
	 * to catch mouse events close to INNER points.
	 */
	private final static Color OUTER_COLOR = Color.TRANSPARENT;
	/**
	 * The color which is shown as soon as a point is selected via a mouse event.
	 */
	private final static Color ACTIVE_COLOR = Color.web("#99cc8c");

	private boolean dragOccured = false;

	/**
	 * Constructor which creates a new point instance of type
	 * {@link PointType#INNER}.
	 * <P>
	 * Calls {@link Point#initPoint(Point)} to add eventHandler to the newly created
	 * point.
	 * <P>
	 * Also calls {@link Point#Point(Point)} to add a sibling of type
	 * {@link PointType#OUTER}.
	 * 
	 * @param centerX
	 *            The x coordinate of the point.
	 * @param centerY
	 *            The y coordinate of the point.
	 */
	public Point(double centerX, double centerY) {
		super(centerX, centerY, INNER_RADIUS, INNER_COLOR);
		this.type = PointType.INNER;
		this.FACADE = MainFacade.get_Instance();

		this.initial_x = centerX;
		this.initial_y = centerY;
		initPoint(this);
		this.sibling = new Point(this);
	}

	/**
	 * Private constructor to create a sibling point of type
	 * {@link PointType#OUTER}.
	 * <P>
	 * Calls {@link Point#initPoint(Point)} to add eventHandler to this newly
	 * created sibling point.
	 * 
	 * @param p
	 *            The sibling point of type {@link PointType#INNER}.
	 */
	private Point(Point p) {
		super(p.getCenterX(), p.getCenterY(), OUTER_RADIUS, OUTER_COLOR);
		this.sibling = p;
		this.type = PointType.OUTER;
		this.FACADE = MainFacade.get_Instance();

		this.initial_x = p.getCenterX();
		this.initial_y = p.getCenterY();
		initPoint(this.sibling);
	}

	/**
	 * Adding eventHandler to a created point.
	 * <P>
	 * These eventHandler control the drag-drop event and clicks on secondary mouse
	 * button to remove a point.
	 * <P>
	 * After a point is removed, the convex hull is calculated again.
	 * 
	 * @param p
	 *            The point instance to add the EventHandler to.
	 * @see PointDragHandler
	 * @see MainFacade#remove_Point(Point)
	 * @see MainFacade#clear_ConvexHullPoints()
	 * @see MainFacade#calculate_PointsConvexHull()
	 */
	private void initPoint(Point p) {
		this.addEventHandler(MouseEvent.MOUSE_ENTERED, (event) -> p.setFill(ACTIVE_COLOR));
		this.addEventHandler(MouseEvent.MOUSE_EXITED, (event) -> p.setFill(INNER_COLOR));
		this.addEventHandler(MouseEvent.MOUSE_RELEASED, (event) -> {
			p.setFill(INNER_COLOR);			
			if (dragOccured) {
				FACADE.snapshot_AllPoints();
			}
			dragOccured = false;
		});
		this.addEventHandler(MouseEvent.MOUSE_DRAGGED, (event) -> dragOccured = true);
		this.addEventHandler(MouseEvent.MOUSE_DRAGGED, new PointDragHandler());
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			if (event.getButton().equals(MouseButton.SECONDARY)) {
				FACADE.remove_Point(this);
				FACADE.clear_ConvexHullPoints();
				FACADE.calculate_PointsConvexHull();
				FACADE.calculate_GEK();
				event.consume();
			}
		});
	}

	/**
	 * Get access to the {@link Point#sibling}.
	 * 
	 * @return The sibling point instance.
	 */
	public Point getSibling() {
		return sibling;
	}

	/**
	 * Get access to the x coordinate of the point cast to an int value.
	 * 
	 * @return int The x coordinate.
	 * @see Circle#getCenterX()
	 */
	public int getX() {
		return (int) getCenterX();
	}

	/**
	 * Get access to the y coordinate of the point cast to an int value.
	 * 
	 * @return int The y coordinate.
	 * @see Circle#getCenterY()
	 */
	public int getY() {
		return (int) getCenterY();
	}

	/**
	 * Get access to the {@link PointType} value of the current point.
	 * 
	 * @return PointType The type of point (OUTER or INNER).
	 */
	public PointType getType() {
		return type;
	}

	/**
	 * Override hashCode method to create hash for {@link Point} instance only based
	 * on its initial x and y coordinates. Necessary to keep a constant hashCode
	 * while dragging a point on the canvas.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(initial_x, initial_y);
	}

	/**
	 * Override equals method to compare to {@link Point} instance only based on its
	 * x and y coordinates.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (getX() != other.getX())
			return false;
		if (getY() != other.getY())
			return false;
		return true;
	}

	/**
	 * Override toString method to display a point instance as a string only based
	 * on its x and y coordinates.
	 */
	@Override
	public String toString() {
		return getX() + " " + getY();
	}

	/**
	 * Serializes a point instance. Adds x and y coordinates to {@link ObjectOutputStream}.
	 * 
	 * @param outStream  The ObjectOutputStream to write points to.
	 * @throws IOException Exception while IO.
	 * void
	 */
	private void writeObject(final ObjectOutputStream outStream) throws IOException {
		outStream.writeDouble(getCenterX());
		outStream.writeDouble(getCenterY());
	}

	/**
	 * Reconstructs a point instance from {@link ObjectInputStream}.
	 * @param inStream The ObjectInputStream to read points from.
	 * @throws IOException Exception while IO.
	 * void
	 */
	private void readObject(final ObjectInputStream inStream) throws IOException {
		setCenterX(inStream.readDouble());
		setCenterY(inStream.readDouble());
	}

}