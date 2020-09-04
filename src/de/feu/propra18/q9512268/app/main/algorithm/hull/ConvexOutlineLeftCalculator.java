package de.feu.propra18.q9512268.app.main.algorithm.hull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

import de.feu.propra18.q9512268.app.main.objects.Point;
import de.feu.propra18.q9512268.app.main.objects.SimplePoint;

/**
 * Class which runs the calculation of the left outline. <P>
 * Extends abstract class {@link ConvexOutlineTemplate} which provides the main process template for
 * calculating a convex hull outline.
 * 
 * @author Christian Luetticke
 * @version 2.0
 */
final class ConvexOutlineLeftCalculator extends ConvexOutlineTemplate {

	/**
	 * Calculates the contour polygon for the left side of the current point set.
	 * <P>
	 * Sweeps over each point from left to right (points are present sorted by
	 * their x coordinates). <P>
	 * Adds all matching points to a {@link TreeSet} which holds {@link SimplePoint}
	 * instances sorted ascending by their y coordinate.
	 */
	@Override
	protected TreeSet<SimplePoint> createOutline() {

		Comparator<SimplePoint> yAscending = (p1, p2) -> Integer.compare(p1.getY(), p2.getY());

		TreeSet<SimplePoint> outline = new TreeSet<SimplePoint>(yAscending);

		Point min_y_so_far = currentPoints.get(0);
		Point max_y_so_far = currentPoints.get(0);
		outline.add(new SimplePoint(min_y_so_far.getX(), min_y_so_far.getY()));
		for (Iterator<Point> iterator = currentPoints.iterator(); iterator.hasNext();) {
			Point point = (Point) iterator.next();
			if (point.getCenterY() < min_y_so_far.getCenterY()) {
				outline.add(new SimplePoint(point.getX(), point.getY()));
				min_y_so_far = point;
			}
			if (point.getCenterY() > max_y_so_far.getCenterY()) {
				outline.add(new SimplePoint(point.getX(), point.getY()));
				max_y_so_far = point;
			}
		}

		return outline;

	}

}
