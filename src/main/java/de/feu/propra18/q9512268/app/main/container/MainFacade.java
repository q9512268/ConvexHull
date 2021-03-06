package de.feu.propra18.q9512268.app.main.container;

import java.util.List;
import java.util.Set;

import de.feu.propra18.q9512268.app.io.IOFacade;
import de.feu.propra18.q9512268.app.main.algorithm.circle.CircleAlgorithm;
import de.feu.propra18.q9512268.app.main.algorithm.hull.HullAlgorithm;
import de.feu.propra18.q9512268.app.main.objects.Point;
import de.feu.propra18.q9512268.app.main.objects.PointType;
import de.feu.propra18.q9512268.app.main.objects.SimpleCircle;
import de.feu.propra18.q9512268.app.main.objects.SimplePoint;
import de.feu.propra18.q9512268.gui.controller.MainController;
import de.feu.propra18.q9512268.gui.controller.MenuController;
import de.feu.propra18.q9512268.gui.controller.ToolBarController;
import de.feu.propra18.q9512268.main.HullCalculatorImpl;
import de.feu.propra18.q9512268.test.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.stage.FileChooser;

/**
 * This class, realized as a singleton instance, provides access to the main
 * functions of the application.
 * <P>
 * It encapsulates access to {@link IOFacade}, {@link PointsContainer},
 * {@link PointsContainerHistory}, {@link CircleContainer},
 * {@link HullContainer}.
 * 
 * @author Christian Luetticke
 * @version 2.0
 */
public class MainFacade {

	/**
	 * Final singleton instance of the {@link MainFacade} class.
	 * <P>
	 * Gets created when accessed the first time.
	 */
	private static final MainFacade INSTANCE = new MainFacade();

	/**
	 * Reference to the {@link IOFacade} singleton instance.
	 */
	private IOFacade IOFACADE;

	/**
	 * Reference to the {@link PointsContainer} singleton instance.
	 */
	private PointsContainer CONTAINER;

	/**
	 * Reference to the {@link PointsContainerHistory} singleton instance.
	 */
	private PointsContainerHistory CONTAINER_HISTORY;

	/**
	 * Reference to the {@link CircleContainer} singleton instance.
	 */
	private CircleContainer CONTAINER_CIRCLE;

	/**
	 * Reference to the {@link HullContainer} singleton instance.
	 */
	private HullContainer CONTAINER_HULL;

	/**
	 * Private constructor which can never be called directly.
	 * <P>
	 * Only instantiated when accessing {@link MainFacade#INSTANCE} for the first
	 * time.
	 * <P>
	 * Gets a reference to {@link IOFacade}, {@link PointsContainer},
	 * {@link PointsContainerHistory}, {@link CircleContainer},
	 * {@link HullContainer} singleton instances.
	 */
	private MainFacade() {
		this.IOFACADE = IOFacade.getInstance();
		this.CONTAINER = PointsContainer.get_Instance();
		this.CONTAINER_HISTORY = PointsContainerHistory.get_Instance();
		this.CONTAINER_CIRCLE = CircleContainer.get_Instance();
		this.CONTAINER_HULL = HullContainer.get_Instance();
	}

	/**
	 * Get access to the {@link MainFacade#INSTANCE}.
	 * 
	 * @return The MainFacade instance.
	 */
	public static MainFacade get_Instance() {
		return INSTANCE;
	}

	/**
	 * Add a new {@link Point} to {@link PointsContainer}.
	 * <P>
	 * Delegates request to {@link PointsContainer#add_Point(Point)}
	 * 
	 * @param p
	 *            The Point to add.
	 * @see PointsContainer#get_PointsAll()
	 * @see PointsContainer#get_PointsAllOuter()
	 * @see PointsContainer#get_PointsSorted()
	 */
	public void add_Point(Point p) {
		CONTAINER.add_Point(p);
	}

	/**
	 * Can be called when random {@link Point} instances should be added to the
	 * visible canvas. Delegates request to
	 * {@link MainFacade#pointsRandom(int, double, double)}
	 * 
	 * @param amount
	 *            How many points should be added.
	 * @param width
	 *            Width of the area in which random points should be generated.
	 * @param height
	 *            Height of the area in which random points should be generated.
	 * @see ToolBarController
	 */
	public void add_PointsRandom(int amount, double width, double height) {
		pointsRandom(amount, width, height);
		snapshot_AllPoints();
	}

	/**
	 * Delegates request to add {@link Point} instances from a 2D array to
	 * {@link IOFacade#add_PointsFromArray(int[][])}.
	 * 
	 * @param pointArray
	 *            2D array of x/y coordinates.
	 */
	public void add_PointsFromArray(int[][] pointArray) {
		IOFACADE.add_PointsFromArray(pointArray);
		snapshot_AllPoints();
	}

	/**
	 * Takes as input a fileName pointing to a file containing point coordinates.
	 * <P>
	 * Delegates the request to {@link IOFacade#add_PointsFromFile(String)}.
	 * 
	 * @param fileName
	 *            The name of the file to be read. void
	 */
	public void add_PointsFromFile(String fileName) {
		IOFACADE.add_PointsFromFile(fileName);
		snapshot_AllPoints();
	}

	/**
	 * Remove an existing {@link Point} from {@link PointsContainer}.
	 * <P>
	 * Delegates request to {@link PointsContainer#remove_Point(Point)}
	 * 
	 * @param p
	 *            The Point to be removed.
	 * @see PointsContainer#get_PointsAll()
	 * @see PointsContainer#get_PointsAllOuter()
	 * @see PointsContainer#get_PointsSorted()
	 */
	public void remove_Point(Point p) {
		CONTAINER.remove_Point(p);
		snapshot_AllPoints();
	}

	/**
	 * Method to verify a {@link Point} instance already exists.
	 * <P>
	 * Delegates request to {@link PointsContainer#contains_Point(Point)}
	 * 
	 * @param p
	 *            The point to search for.
	 * @return boolean
	 */
	public boolean contains_Point(Point p) {
		return CONTAINER.contains_Point(p);
	}

	/**
	 * To call when all current {@link Point} instances that belong to the convex
	 * hull should be removed.
	 * <P>
	 * Delegates call to {@link HullContainer#remove_AllConvexHullPoints()}.
	 */
	public void clear_ConvexHullPoints() {
		CONTAINER_HULL.remove_AllConvexHullPoints();
	}

	/**
	 * To call when all current {@link Point} instances (also those which belong to
	 * the convex hull) should be removed.
	 * <P>
	 * Also removes the current largest included circle (GEK) and clears the
	 * undo/redo-history.
	 * 
	 * @see PointsContainer#remove_AllPoints()
	 * @see HullContainer#remove_AllConvexHullPoints()
	 * @see CircleContainer#remove_GEK()
	 * @see PointsContainerHistory#clear_History()
	 */
	public void clear_Container() {
		CONTAINER.remove_AllPoints();
		CONTAINER_HULL.remove_AllConvexHullPoints();
		CONTAINER_CIRCLE.remove_GEK();
		CONTAINER_HISTORY.clear_History();
	}

	/**
	 * Returns all current {@link Point} instances of type {@link PointType#INNER}.
	 * 
	 * @return ObservableList The observable list containing the current points of
	 *         type {@link PointType#INNER}.
	 * @see PointsContainer#get_PointsAll()
	 */
	public ObservableList<Point> get_PointsAll() {
		return CONTAINER.get_PointsAll();
	}

	/**
	 * Returns all current {@link Point} instances of type {@link PointType#OUTER}.
	 * 
	 * @return ObservableList The observable list containing the current points of
	 *         type {@link PointType#OUTER}.
	 * @see PointsContainer#get_PointsAllOuter()
	 */
	public ObservableList<Point> get_PointsAllOuter() {
		return CONTAINER.get_PointsAllOuter();
	}

	/**
	 * Returns all current {@link Point} instances of Type {@link PointType#INNER}
	 * sorted depending on their x coordinates.
	 * 
	 * @return SortedList The ObservableList containing the current points sorted.
	 * @see PointsContainer#get_PointsSorted()
	 */
	public SortedList<Point> get_PointsSorted() {
		return CONTAINER.get_PointsSorted();
	}

	/**
	 * Returns all current coordinates that belong to the convex hull.
	 * <P>
	 * Coordinates are represented as Double values to bind them to the polygon
	 * created in {@link MenuController}.
	 * 
	 * @return ObservableList The observable list containing the current convex hull
	 *         points represented by Double values.
	 * @see HullContainer#get_PointsConvexHull()
	 */
	public ObservableList<Double> get_PointsConvexHull() {
		return CONTAINER_HULL.get_PointsConvexHull();
	}

	/**
	 * Get access to the largest included circle which is represented by an
	 * {@link SimpleCircle} instance inside the {@link CircleContainer}.
	 * 
	 * @return SimpleCircle The current largest included circle.
	 * @see HullCalculatorImpl
	 * @see Test
	 * @see MainController#initCircle()
	 */
	public SimpleCircle get_GEK() {
		return CONTAINER_CIRCLE.get_GEK();
	}

	/**
	 * Returns all current {@link Point} instances that belong to the convex hull in
	 * form of a 2D int array. Delegates request to
	 * {@link HullContainer#get_PointsConvexHull_asArray()}
	 * 
	 * @return int[][] The convex hull points represented as an 2D int array.
	 */
	public int[][] get_PointsConvexHull_asArray() {
		return CONTAINER_HULL.get_PointsConvexHull_asArray();
	}

	/**
	 * Delegates the request to export points to a file to
	 * {@link IOFacade#save_PointsToFile(String, java.util.List)}.
	 * <P>
	 * Passes current {@link Point} instances from Type {@link PointType#INNER} in
	 * form of an unmodifiableObservableList to {@link IOFacade}.
	 * 
	 * @param fileName
	 *            Name of file to export points to.
	 */
	public void save_PointsToFile(String fileName) {
		IOFACADE.save_PointsToFile(fileName, FXCollections.unmodifiableObservableList(get_PointsAll()));
	}

	/**
	 * Get access to the customized file chooser.
	 * <P>
	 * Delegates request to {@link IOFacade#get_FileChooser()}.
	 * 
	 * @return The FileChooser instance.
	 */
	public FileChooser get_FileChooser() {
		return IOFACADE.get_FileChooser();
	}

	/**
	 * Is called when the convex hull should be calculated.
	 * <P>
	 * This method makes a call to {@link HullAlgorithm#getConvexHull()}, loops
	 * through the results in form of {@link SimplePoint} instances and adds the
	 * corresponding x and y coordinates to the convex hull.
	 * 
	 * @see HullContainer#add_PointConvexHull(Double)
	 */
	public void calculate_PointsConvexHull() {

		if (!get_PointsAll().isEmpty()) {
			final Set<SimplePoint> convexHullNodes = HullAlgorithm.getConvexHull();

			for (SimplePoint node : convexHullNodes) {
				CONTAINER_HULL.add_PointConvexHull((double) node.getX());
				CONTAINER_HULL.add_PointConvexHull((double) node.getY());
			}
		}

	}

	/**
	 * Is called when the largest included circle (GEK) should be calculated.
	 * <P>
	 * This method makes a call to {@link CircleAlgorithm#getGEKInfo()}, takes
	 * results to update the {@link SimpleCircle} inside the {@link CircleContainer}
	 * singleton which represents the largest included circle (GEK).
	 * 
	 * @see ToolBarController
	 * @see MenuController
	 * @see HullCalculatorImpl
	 * @see Test
	 */
	public void calculate_GEK() {
		if (!get_PointsAll().isEmpty()) {
			final List<Double> circleInfo = CircleAlgorithm.getGEKInfo();
			CONTAINER_CIRCLE.setGEK_X(circleInfo.get(0));
			CONTAINER_CIRCLE.setGEK_Y(circleInfo.get(1));
			CONTAINER_CIRCLE.setGEK_Radius(circleInfo.get(2));
		}
	}

	/**
	 * Called from {@link MainFacade#add_PointsRandom(int, double, double)} when
	 * random {@link Point} instances should be added to the visible canvas.
	 * 
	 * @param amount
	 *            How many points should be added.
	 * @param width
	 *            Width of the area in which random points should be generated.
	 * @param height
	 *            Height of the area in which random points should be generated.
	 * @see PointsContainer#add_Point(Point)
	 * @see ToolBarController
	 */
	void pointsRandom(int amount, double width, double height) {

		final int max_width = (int) width;
		final int max_height = (int) height;

		int i = 0;
		while (i < amount) {

			int x = (int) (Math.random() * max_width);
			int y = (int) (Math.random() * max_height);

			Point p = new Point(x, y);
			if (!CONTAINER.contains_Point(p)) {
				CONTAINER.add_Point(p);
				i++;
			}
		}
	}

	/**
	 * Creates a 'snapshot' of the current points.
	 * <P>
	 * Necessary for undo/redo-functionality.
	 * <P>
	 * Called when points are added, removed or updated (via drag and drop).
	 * 
	 * @see MainFacade#add_PointsFromArray(int[][])
	 * @see MainFacade#add_PointsFromFile(String)
	 * @see MainFacade#add_PointsRandom(int, double, double)
	 * @see Point
	 * @see MainController
	 */
	public void snapshot_AllPoints() {
		CONTAINER_HISTORY.serialize_and_store_allPoints();
	}

	/**
	 * To call when last action should be undone.
	 * <P>
	 * Calls {@link PointsContainerHistory#undo()}, retrieves a list of points which
	 * represent the previous state and reconstructs the visible frontend shapes
	 * from this list by calling {@link MainFacade#restore(List)}
	 */
	public void undo() {
		List<Point> restored = CONTAINER_HISTORY.undo();
		restore(restored);
	}

	/**
	 * To call when action should be redone.
	 * <P>
	 * Calls {@link PointsContainerHistory#redo()}, retrieves a list of points which
	 * represent the next state and reconstructs the visible frontend shapes from
	 * this list by calling {@link MainFacade#restore(List)}
	 */
	public void redo() {
		List<Point> restored = CONTAINER_HISTORY.redo();
		restore(restored);
	}

	/**
	 * Restores shapes for frontend by clearing all points, all convex hull points
	 * and the largest included circle (GEK).
	 * <P>
	 * Adds restored points.
	 * <P>
	 * Triggers recalculation of the convex hull and the largest included circle.
	 * 
	 * @param restored
	 *            A list of points which should be restored. Serves as new basis for
	 *            recalculation of shapes.
	 */
	private void restore(List<Point> restored) {
		if (restored != null) {
			CONTAINER.remove_AllPoints();
			CONTAINER_HULL.remove_AllConvexHullPoints();
			CONTAINER_CIRCLE.remove_GEK();
			for (int i = 0; i < restored.size(); i++) {
				Point p = restored.get(i);
				add_Point(new Point(p.getCenterX(), p.getCenterY()));
			}
			calculate_PointsConvexHull();
			calculate_GEK();
		}
	}

}
