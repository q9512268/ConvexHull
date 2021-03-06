package de.feu.propra18.q9512268.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.feu.propra18.q9512268.app.main.container.MainFacade;
import de.feu.propra18.q9512268.app.main.objects.Point;
import de.feu.propra18.q9512268.app.main.objects.PointType;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Scale;

/**
 * Controller class for Main.fxml view.
 * 
 * @author Christian Luetticke
 * @version 2.0
 */
public class MainController implements Initializable {

	/**
	 * Reference to the {@link MainFacade} and its functionality.
	 */
	private MainFacade FACADE;

	/**
	 * Reference to {@link MenuController}.
	 */
	@FXML
	private MenuController menuController;
	/**
	 * Reference to {@link ToolBarController}.
	 */
	@FXML
	private ToolBarController toolBarController;

	/**
	 * The centered pane containing points and polygon.
	 */
	@FXML
	private Pane pane;

	/**
	 * A pane holding all points.
	 */
	@FXML
	private Pane pane_points;

	/**
	 * A ScrollPane which wraps {@link MainController#pane_points} to optimize
	 * resizing of the main window.
	 */
	@FXML
	private ScrollPane scrollpane_points;
	/**
	 * Group of all {@link Point} instances of type {@link PointType#INNER}.
	 */
	@FXML
	private Group group_points;
	/**
	 * Group of all {@link Point} instances of type {@link PointType#INNER}.
	 */
	@FXML
	private Group group_points_outer;
	/**
	 * A ScrollPane which wraps {@link MainController#pane_polygon} to optimize
	 * resizing of the main window.
	 */
	@FXML
	private ScrollPane scrollpane_polygon;
	/**
	 * A pane holding the polygon which represents the convex hull.
	 */
	@FXML
	private Pane pane_polygon;

	/**
	 * A ScrollPane which wraps {@link MainController#pane_circle} to optimize
	 * resizing of the main window.
	 */
	@FXML
	private ScrollPane scrollpane_circle;
	/**
	 * A pane holding the circle which represents the largest included circle.
	 */
	@FXML
	private Pane pane_circle;

	/**
	 * Stores information about current drag event.
	 * <P>
	 * Drag event should only be active directly on {@link Point} instances.
	 */
	private boolean dragging = false;

	/**
	 * The polygon created from the current convex hull points.
	 */
	private Polygon polygon;

	/**
	 * The circle represented the largest included circle.
	 */
	private Circle gek;

	/**
	 * Initializes the components which are present in the markup of Main.fxml.
	 * <P>
	 * Initializes the centered {@link Pane} which holds all points and the polygon
	 * representing the convex hull.
	 * <P>
	 * Passes the {@link Pane} instance to the {@link ToolBarController} and this
	 * {@link MainController} instance to {@link MenuController} for further
	 * processing.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		FACADE = MainFacade.get_Instance();

		initPane();
		initPolygon();
		initCircle();
		initPoints();

		toolBarController.setPane(pane);
		menuController.setMainController(this);
	}

	/**
	 * Initializes the {@link MainController#pane} instance.
	 * <P>
	 * Sets a new scale to move the origin to the left bottom.
	 * <P>
	 * Adds event handler mainly to add new {@link Point} instances when then user
	 * clicks in the pane area.
	 */
	private void initPane() {
		Scale scale = new Scale();
		scale.setX(1);
		scale.setY(-1);
		scale.pivotYProperty().bind(pane.heightProperty().divide(2));
		pane.getTransforms().add(scale);

		pane.addEventHandler(MouseEvent.ANY, (event) -> {

			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				dragging = false;
			} else if (event.getEventType() == MouseEvent.DRAG_DETECTED) {
				dragging = true;
			} else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				dragging = true;
			} else if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {

				if (!dragging) {
					Point newPoint = new Point(event.getX(), event.getY());
					if (!FACADE.contains_Point(newPoint)) {
						FACADE.add_Point(newPoint);
						FACADE.snapshot_AllPoints();
						FACADE.clear_ConvexHullPoints();
						FACADE.calculate_PointsConvexHull();
						FACADE.calculate_GEK();
					}
				}
			}

		});
	}

	/**
	 * Creates a polygon.
	 * <P>
	 * The points of this polygon are bound to the current convex hull points.
	 * <P>
	 * The polygon is placed inside a {@link ScrollPane} to optimize resizing of the
	 * main {@link Scene}.
	 * 
	 * @see MainFacade#get_PointsConvexHull()
	 */
	public void initPolygon() {
		polygon = new Polygon();
		polygon.setId("polygon");
		Bindings.bindContent(polygon.getPoints(), FACADE.get_PointsConvexHull());
		pane_polygon.getChildren().add(polygon);
		pane_polygon.prefHeightProperty().bind(pane.heightProperty());
		pane_polygon.toBack();
		scrollpane_polygon.toBack();
	}

	/**
	 * Creates a circle.
	 * <P>
	 * The x, y and radius property of this circle are bound to the current information
	 * of the largest included circle (GEK).
	 * <P>
	 * The circle is placed inside a {@link ScrollPane} to optimize resizing of the
	 * main {@link Scene}.
	 * 
	 * @see MainFacade#get_GEK()
	 */
	public void initCircle() {
		gek = new Circle();
		gek.setId("gek");
		gek.centerXProperty().bind(FACADE.get_GEK().getX());
		gek.centerYProperty().bind(FACADE.get_GEK().getY());
		gek.radiusProperty().bind(FACADE.get_GEK().getRadius());
		pane_circle.getChildren().add(gek);
		pane_circle.prefHeightProperty().bind(pane.heightProperty());
		pane_circle.toBack();
		scrollpane_circle.toBack();
	}

	/**
	 * Removes all children of the polygon.
	 * <P>
	 * Called from inside of {@link MenuController}.
	 */
	public void clearPolygon() {
		pane_polygon.getChildren().clear();
	}

	/**
	 * Removes all children of the circle plane.
	 * <P>
	 * Called from inside of {@link MenuController}.
	 */
	public void clearCircle() {
		pane_circle.getChildren().clear();
	}

	/**
	 * Initializes the pane holding all points.
	 * <P>
	 * The {@link MainController#pane_points} Pane consists of two groups holding
	 * the {@link PointType#INNER} and {@link PointType#OUTER} points instances.
	 * <P>
	 * The children of each group are bound to the corresponding observable lists
	 * inside the PointsContainer instance.
	 */
	private void initPoints() {

		pane_points.prefHeightProperty().bind(pane.heightProperty());
		pane_points.prefWidthProperty().bind(pane.widthProperty());

		Bindings.bindContent(group_points.getChildren(), FACADE.get_PointsAll());
		Bindings.bindContent(group_points_outer.getChildren(), FACADE.get_PointsAllOuter());

		group_points.toFront();
	}

}
