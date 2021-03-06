package de.feu.propra18.q9512268.app.main.container;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class, which is realized as a singleton acts as a container for the
 * convex hull (GEK). Information about the
 * {@link HullContainer#convexHullPoints} are bound the convex hull polygon
 * shape on the front-end side.
 * <P>
 * The functions provided by this class can be accessed via the
 * {@link MainFacade}.
 * 
 * @author Christian Luetticke
 * @version 2.0
 * @see MainFacade
 *
 */
class HullContainer {

	/**
	 * Final singleton instance of the {@link HullContainer} class.
	 * <P>
	 * Gets created when accessed the first time.
	 */
	private static final HullContainer INSTANCE = new HullContainer();

	/**
	 * Holds all coordinates as Double values which belong to the convex hull.
	 */
	private ObservableList<Double> convexHullPoints;

	/**
	 * Private constructor which can never be called directly.
	 * <P>
	 * Only instantiated when accessing {@link HullContainer#INSTANCE} for the first
	 * time.
	 * <P>
	 * Constructs a {@link ObservableList} instance to hold the convex hull points.
	 */
	private HullContainer() {
		convexHullPoints = FXCollections.observableArrayList();
	}

	/**
	 * Get access to the {@link HullContainer#INSTANCE}.
	 * 
	 * @return HullContainer The HullContainer instance.
	 * @see MainFacade
	 */
	static HullContainer get_Instance() {
		return INSTANCE;
	}

	/**
	 * Get access to the ObservabelList containing all convex hull coordinates.
	 * 
	 * @return ObservableList The observable list containing all convex hull
	 *         coordinates.
	 * @see MainFacade#get_PointsConvexHull()
	 */
	ObservableList<Double> get_PointsConvexHull() {
		return convexHullPoints;
	}

	/**
	 * Returns a 2D int array which contains the convex hull coordinates.
	 * 
	 * @return int[][] 2D array of convex hull values.
	 * @see MainFacade#get_PointsConvexHull_asArray()
	 */
	int[][] get_PointsConvexHull_asArray() {

		int[][] convexHull = new int[convexHullPoints.size() / 2][2];
		int i = 0;
		int j = 0;
		while (i < convexHullPoints.size() - 1) {
			int[] row = convexHull[j];
			row[0] = convexHullPoints.get(i).intValue();
			row[1] = convexHullPoints.get(i + 1).intValue();
			i = i + 2;
			j++;
		}

		return convexHull;

	}

	/**
	 * Adds a Double value to {@link HullContainer#convexHullPoints}.
	 * 
	 * @param d
	 *            The Double value which should be added.
	 * @see MainFacade#calculate_PointsConvexHull()
	 */
	void add_PointConvexHull(Double d) {
		convexHullPoints.add(d);
	}

	/**
	 * Clears the observable list {@link HullContainer#convexHullPoints}.
	 * 
	 * @see MainFacade#clear_ConvexHullPoints()
	 */
	void remove_AllConvexHullPoints() {
		convexHullPoints.clear();
	}

}
