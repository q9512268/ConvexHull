package de.feu.propra18.q9512268.app.io;

import java.util.HashSet;
import java.util.List;

import de.feu.propra18.q9512268.app.main.container.MainFacade;
import de.feu.propra18.q9512268.app.main.objects.Point;
import javafx.stage.FileChooser;

/**
 * This class acts as the main access point to IO Utilities in the
 * {@link de.feu.propra18.q9512268.app.io} package.
 * 
 * @author Christian Luetticke
 * @version 2.0
 */
public class IOFacade {

	/**
	 * Final singleton instance of the {@link IOFacade}. <P>
	 * Gets created when accessed the first time.
	 */
	private static final IOFacade INSTANCE = new IOFacade();
	/**
	 * {@link IOFacade} holds a reference to the {@link CustomFileChooser} singleton
	 * instance.
	 */
	private CustomFileChooser cfc;

	/**
	 * Private constructor which can never be called directly.
	 * <P>
	 * Only instantiated when accessing {@link IOFacade#INSTANCE} for the first
	 * time.
	 * <P>
	 * Calls {@link CustomFileChooser#getInstance()} to get a reference to the
	 * {@link CustomFileChooser} singleton.
	 */
	private IOFacade() {
		this.cfc = CustomFileChooser.getInstance();
	}

	/**
	 * Public method which delegates a 'save' or 'save as' request to the
	 * {@link FileOutput#save_PointsToFile(String, List)} method.
	 * 
	 * @param fileName
	 *            Name of file to export points to.
	 * @param points
	 *            Current list of points.
	 * @see MainFacade#save_PointsToFile(String)
	 */
	public void save_PointsToFile(String fileName, List<Point> points) {
		FileOutput.save_PointsToFile(fileName, points);
	}

	/**
	 * Public method which delegates the request to add {@link Point} instances to
	 * the {@link FileInput#add_PointsFromFile(String)} method.
	 * 
	 * @param fileName
	 *            Name of file to read point coordinates from.
	 * @see MainFacade#add_PointsFromFile(String)
	 */
	public void add_PointsFromFile(String fileName) {
		FileInput.add_PointsFromFile(fileName);
	}

	/**
	 * Loops through a 2D int array of point coordinates.
	 * <P>
	 * Reads array, passes created points into a set to eliminate duplicates
	 * and adds all points to the PointsContainer by calling {@link MainFacade#add_Point(Point)}.
	 * 
	 * @param pointArray
	 *            2D array of x/y coordinates.
	 * @see IOFacade#add_PointsFromArray(int[][])
	 * @see MainFacade#add_Point(Point)
	 */
	public void add_PointsFromArray(int[][] pointArray) {

		HashSet<Point> set = new HashSet<>();

		for (int i = 0; i < pointArray.length; i++) {

			int[] row = pointArray[i];
			Point p = new Point(row[0], row[1]);
			if (p != null) {
				set.add(p);
			}
		}

		for (Point p : set) {
			MainFacade.get_Instance().add_Point(p);
		}

	}

	/**
	 * Get access to the {@link CustomFileChooser#fc}.
	 * 
	 * @return The FileChooser instance.
	 */
	public FileChooser get_FileChooser() {
		return cfc.get_fc();
	}

	/**
	 * Get access to the {@link IOFacade#INSTANCE}.
	 * 
	 * @return The IOFacade instance.
	 * @see MainFacade#get_Instance()
	 */
	public static IOFacade getInstance() {
		return INSTANCE;
	}

}
