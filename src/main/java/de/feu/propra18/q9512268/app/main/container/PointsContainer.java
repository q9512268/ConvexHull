package de.feu.propra18.q9512268.app.main.container;

import java.util.Comparator;

import de.feu.propra18.q9512268.app.main.objects.Point;
import de.feu.propra18.q9512268.app.main.objects.PointType;
import de.feu.propra18.q9512268.gui.controller.MainController;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.util.Callback;

/**
 * This class, which is realized as a singleton data structures containers which
 * hold points.
 * <P>
 * The contents of these main data containers are bound to points on the
 * front-end side.
 * <P>
 * The functions provided by this class can be accessed via the
 * {@link MainFacade}.
 * 
 * @author Christian Luetticke
 * @version 2.0
 * @see MainController
 */
class PointsContainer {

	/**
	 * Final singleton instance of the {@link PointsContainer} class.
	 * <P>
	 * Gets created when accessed the first time.
	 */
	private static final PointsContainer INSTANCE = new PointsContainer();

	/**
	 * Holds all current {@link Point} of type {@link PointType#INNER} instances
	 * sorted depending on their x coordinates.
	 */
	private SortedList<Point> sortedPoints;
	/**
	 * Holds all current {@link Point} instances of type {@link PointType#INNER}.
	 */
	private ObservableList<Point> allPoints;
	/**
	 * Holds all current {@link Point} instances of type {@link PointType#OUTER}
	 * which are siblings of the points in {@link PointsContainer#allPoints}.
	 */
	private ObservableList<Point> allPointsOuter;

	/**
	 * Private constructor which can never be called directly.
	 * <P>
	 * Only instantiated when accessing {@link PointsContainer#INSTANCE} for the
	 * first time.
	 * <P>
	 * Constructs the different point containers as {@link ObservableList}
	 * instances.
	 */
	private PointsContainer() {

		allPoints = createObservablePointArrayList();
		allPointsOuter = createObservablePointArrayList();

		Comparator<Point> xComparator = (p1, p2) -> {
			int result = Double.compare(p1.getCenterX(), p2.getCenterX());
			if (result == 0) {
				return Double.compare(p1.getCenterY(), p2.getCenterY());
			}
			return result;
		};

		sortedPoints = allPoints.sorted(xComparator);
	}

	/**
	 * Creates an {@link ObservableList} which holds {@link Point} instances.
	 * <P>
	 * Uses a {@link Callback} to make sure observers are notified when
	 * {@link Point#centerXProperty()} or {@link Point#centerYProperty()} changes.
	 * 
	 * @return ObservableList An observable list for {@link Point} instances.
	 */
	private ObservableList<Point> createObservablePointArrayList() {
		return FXCollections.observableArrayList(new Callback<Point, Observable[]>() {
			@Override
			public Observable[] call(Point param) {
				return new Observable[] { param.centerXProperty(), param.centerYProperty() };
			}
		});
	}

	/**
	 * Get access to the {@link PointsContainer#INSTANCE}.
	 * 
	 * @return The PointsContainer instance.
	 */
	static PointsContainer get_Instance() {
		return INSTANCE;
	}

	/**
	 * Get access to the ObservableList containing all {@link Point} instances of
	 * type {@link PointType#INNER}.
	 * 
	 * @return ObservableList The observable list containing all INNER points.
	 * @see MainFacade#get_PointsAll()
	 */
	ObservableList<Point> get_PointsAll() {
		return allPoints;
	}

	/**
	 * Get access to the ObservableList containing all {@link Point} instances of
	 * type {@link PointType#OUTER}.
	 * 
	 * @return ObservableList The observable list containing all OUTER points.
	 * @see MainFacade#get_PointsAllOuter()
	 */
	ObservableList<Point> get_PointsAllOuter() {
		return allPointsOuter;
	}

	/**
	 * Get access to the SortedList containing all {@link Point} instances sorting
	 * regarding their x coordinate.
	 * 
	 * @return SortedList The SortedList of points.
	 * @see MainFacade#get_PointsSorted()
	 */
	SortedList<Point> get_PointsSorted() {
		return sortedPoints;
	}

	/**
	 * Adds a new {@link Point} instance to the corresponding container depending on
	 * the {@link PointType}. Also adds its sibling.
	 * 
	 * @param p
	 *            The Point to add.
	 * @see MainFacade#add_Point(Point)
	 */
	void add_Point(Point p) {

		PointType type = p.getType();
		switch (type) {
		case INNER:
			allPoints.add(p);
			allPointsOuter.add(p.getSibling());
			break;
		case OUTER:
			allPoints.add(p.getSibling());
			allPointsOuter.add(p);
			break;
		}
	}

	/**
	 * Returns an info if a {@link Point} instance is already present in
	 * {@link PointsContainer#allPoints}.
	 * 
	 * @param p
	 *            The point to run the contains-request on.
	 * @return boolean Info if {@link PointsContainer#allPoints} contains the
	 *         requested point.
	 */
	boolean contains_Point(Point p) {
		return allPoints.contains(p);
	}

	/**
	 * Removes a {@link Point} instance to the corresponding container depending on
	 * the {@link PointType}. Also removes also its sibling.
	 * 
	 * @param p
	 *            The Point to add.
	 * @see MainFacade#remove_Point(Point)
	 */
	void remove_Point(Point p) {

		PointType type = p.getType();
		switch (type) {
		case INNER:
			allPoints.remove(p);
			allPointsOuter.remove(p.getSibling());
			break;
		case OUTER:
			allPointsOuter.remove(p);
			allPoints.remove(p.getSibling());
			break;
		}

	}

	/**
	 * Clears the observable lists {@link PointsContainer#allPoints} and
	 * {@link PointsContainer#allPointsOuter}.
	 * 
	 * @see MainFacade#clear_Container()
	 */
	void remove_AllPoints() {
		allPoints.clear();
		allPointsOuter.clear();
	}

}
