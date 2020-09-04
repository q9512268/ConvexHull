package de.feu.propra18.q9512268.app.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.feu.propra18.q9512268.app.main.container.MainFacade;
import de.feu.propra18.q9512268.app.main.objects.Point;
import de.feu.propra18.q9512268.main.HullCalculatorImpl;

/**
 * Final class that provides function to read points from a file and add new
 * {@link Point} instances to the main data containers of the application.
 * <P>
 * Can only be accessed in a static way via the {@link IOFacade} class.
 * 
 * @author Christian Luetticke
 * @version 2.0
 */
final class FileInput {

	/**
	 * Private constructor to make sure class is only accessed in a static way.
	 */
	private FileInput() {

	}

	/**
	 * Takes as input a fileName pointing to a file containing point coordinates.
	 * <P>
	 * Reads each line, passes created points into a set to eliminate duplicates
	 * and adds all points to the PointsContainer by calling {@link MainFacade#add_Point(Point)}.
	 * <P>
	 * Is used by {@link HullCalculatorImpl#addPointsFromFile(String)}.
	 * 
	 * @param fileName
	 *            The name of the file to be read.
	 * @see IOFacade#add_PointsFromFile(String)
	 */
	protected static void add_PointsFromFile(String fileName) {

		try (final BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

			HashSet<Point> set = new HashSet<>();

			for (String line; (line = reader.readLine()) != null;) {
				Point p = createPointIfLineMatches(line);
				if (p != null) {
					set.add(p);
				}
			}
			
			for (Point p : set) {
				MainFacade.get_Instance().add_Point(p);
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Takes a line which was read from a file and tries to match it to a pattern
	 * (e.g. 100 200).
	 * <P>
	 * If the line matches, a new {@link Point} is created from matching x/y
	 * coordinates.
	 * 
	 * @param line Read line from a file.
	 * @return Point A new {@link Point} instance.
	 */	
	private static Point createPointIfLineMatches(String line) {

		final Pattern pattern = Pattern.compile("^-?(\\d+)\\s-?(\\d+)");
		final Matcher matcher = pattern.matcher(line);
		final boolean result = matcher.find();

		if (result) {
			final String[] coordinates = line.split(" ");

			final int x = Integer.parseInt(coordinates[0]);
			final int y = Integer.parseInt(coordinates[1]);

			return new Point(x, y);
		}

		return null;
	}

}
