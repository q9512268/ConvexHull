package de.feu.propra18.q9512268.app.main.algorithm.hull;

import java.util.Comparator;
import java.util.TreeSet;

import de.feu.propra18.q9512268.app.main.objects.Point;
import de.feu.propra18.q9512268.app.main.objects.SimplePoint;

/**
 * Class which runs the calculation of the right outline. <P>
 * Extends abstract class {@link ConvexOutlineTemplate} which provides the main process template for
 * calculating a convex hull outline.
 * 
 * @author Christian Luetticke
 * @version 2.0
 */
final class ConvexOutlineRightCalculator extends ConvexOutlineTemplate {

	/**
	 * Calculates the contour polygon for the right side of the current point set.
	 * <P>Sweeps over each point from right to left (points are present sorted by their x coordinates).
	 * <P>Adds all matching points to a {@link TreeSet} which holds {@link SimplePoint} instances sorted descending
	 * by their y coordinate. 
	 */
	@Override
	protected TreeSet<SimplePoint> createOutline() {

		Comparator<SimplePoint> yDescending = (p1, p2) -> Integer.compare(p2.getY(), p1.getY());

		TreeSet<SimplePoint> outline = new TreeSet<SimplePoint>(yDescending);

		Point min_y_so_far = currentPoints.get(currentPoints.size() - 1);
		Point max_y_so_far = currentPoints.get(currentPoints.size() - 1);
		outline.add(new SimplePoint(min_y_so_far.getX(), min_y_so_far.getY()));

		for (int i = currentPoints.size() - 1; i >= 0; i--) {
			Point point = currentPoints.get(i);
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
