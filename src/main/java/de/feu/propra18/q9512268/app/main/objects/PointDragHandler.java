package de.feu.propra18.q9512268.app.main.objects;

import de.feu.propra18.q9512268.app.main.container.MainFacade;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Private class that creates a drag event handler for a {@link Point} instance.
 * 
 * @author Christian Luetticke
 * @version 2.0
 * @see Point#Point(double, double)
 */
class PointDragHandler implements EventHandler<MouseEvent> {

	/**
	 * The minimal value that is accepted as the x coordinate of a {@link Point}
	 * instance while dragging.
	 */
	private double min_x = 0;
	/**
	 * The minimal value that is accepted as the y coordinate of a {@link Point}
	 * instance while dragging.
	 */
	private double min_y = 0;
	/**
	 * The maximal value that is accepted as the x coordinate of a {@link Point}
	 * instance while dragging.
	 * <P>
	 * Depends on the size of the {@link Pane} in which the points are displayed.
	 */
	private double max_x;
	/**
	 * The maximal value that is accepted as the y coordinate of a {@link Point}
	 * instance while dragging.
	 * <P>
	 * Depends on the size of the {@link Pane} in which the points are displayed.
	 */
	private double max_y;

	/**
	 * A reference to the {@link MainFacade} singleton instance.
	 */
	private MainFacade FACADE;
	/**
	 * A reference to {@link Point} instance on which an event is called.
	 */
	private Point p;

	/**
	 * Overrides eventHandler.
	 * <P>
	 * Sets the {@link PointDragHandler#max_x} to the current width of the pane
	 * containing the points.
	 * <P>
	 * Sets the {@link PointDragHandler#max_y} to the current height of the pane
	 * containing the points.
	 * <P>
	 * While dragging a point on the front-end side, the x and y coordinates of the
	 * point and its sibling are tested. If the x and y value of the event are
	 * within the boundaries defined by {@link PointDragHandler#min_x} and
	 * {@link PointDragHandler#max_x} and {@link PointDragHandler#min_y} and
	 * {@link PointDragHandler#max_y} the corresponding methods eg.
	 * {@link PointDragHandler#setCenterX_setCenterXSibling(double)} get called.
	 * <P>
	 * After every drag event the convex cull is recalculated.
	 *
	 * @param event
	 *            the event which occurred
	 * @see MainFacade#clear_ConvexHullPoints()
	 * @see MainFacade#calculate_PointsConvexHull()
	 */
	@Override
	public void handle(MouseEvent event) {

		this.FACADE = MainFacade.get_Instance();
		this.p = (Point) event.getSource();
		final Group group = (Group) p.getParent();
		final Pane pane_points = (Pane) group.getParent();

		max_x = pane_points.getWidth();
		max_y = pane_points.getHeight();

		/**
		 * x and y event coordinates are tested if they lie within defined boundaries.
		 */
		if (event.getX() < min_x) { // if the x coordinate of the event is less than min_x (0)
			setCenterX_setCenterXSibling(min_x); // then keep centerX coordinates of point and sibling at min_x
			testThenSetCenterY(event); // and proceed with setting the centerY coordinates depending on the event
		} else if (event.getX() > max_x) {
			setCenterX_setCenterXSibling(max_x);
			testThenSetCenterY(event);
		} else if (event.getY() < min_y) {
			setCenterY_setCenterYSibling(min_y);
			testThenSetCenterX(event);
		} else if (event.getY() > max_y) {
			setCenterY_setCenterYSibling(max_y);
			testThenSetCenterX(event);

		} else {
			setCenterX_setCenterXSibling(event.getX());
			setCenterY_setCenterYSibling(event.getY());
		}

		FACADE.clear_ConvexHullPoints();
		FACADE.calculate_PointsConvexHull();
		FACADE.calculate_GEK();

	}

	/**
	 * Sets the new centerX coordinate of a point and its sibling.
	 * 
	 * @param d
	 *            The new x coordinate depending on the x coordinate of the event.
	 */
	private void setCenterX_setCenterXSibling(double d) {
		p.setCenterX(d);
		p.getSibling().setCenterX(d);
	}

	/**
	 * Sets the new centerY coordinate of a point and its sibling.
	 * 
	 * @param d
	 *            The new y coordinate depending on the x coordinate of the event.
	 */
	private void setCenterY_setCenterYSibling(double d) {
		p.setCenterY(d);
		p.getSibling().setCenterY(d);
	}

	/**
	 * Further testing of the x values depending on the event.
	 * <P>
	 * Keeps centerX of point and sibling at max_x if x of event is larger than
	 * max_x.
	 * <P>
	 * Keeps centerX of point and sibling at min_x if x of event is less than min_x.
	 * <P>
	 * Otherwise sets centerX of point and sibling to x of the event.
	 * 
	 * @param event
	 *            The current MouseEvent.
	 */
	private void testThenSetCenterX(MouseEvent event) {
		if (event.getX() > max_x) {
			setCenterX_setCenterXSibling(max_x);
		} else if (event.getX() < min_x) {
			setCenterX_setCenterXSibling(min_x);
		} else {
			p.setCenterX(event.getX());
			p.getSibling().setCenterX(event.getX());
		}
	}

	/**
	 * Further testing of the y values depending on the event.
	 * <P>
	 * Keeps centerY of point and sibling at max_y if y of event is larger than
	 * max_y.
	 * <P>
	 * Keeps centerY of point and sibling at min_y if y of event is less than min_y.
	 * <P>
	 * Otherwise sets centerY of point and sibling to y of the event.
	 * 
	 * @param event
	 *            The current MouseEvent.
	 */
	private void testThenSetCenterY(MouseEvent event) {
		if (event.getY() > max_y) {
			setCenterY_setCenterYSibling(max_y);
		} else if (event.getY() < min_y) {
			setCenterY_setCenterYSibling(min_y);
		} else {
			p.setCenterY(event.getY());
			p.getSibling().setCenterY(event.getY());
		}
	}

}
