package de.feu.propra18.q9512268.app.main.algorithm.hull;

import java.util.Iterator;
import java.util.TreeSet;

import de.feu.propra18.q9512268.app.main.container.MainFacade;
import de.feu.propra18.q9512268.app.main.objects.Point;
import de.feu.propra18.q9512268.app.main.objects.SimplePoint;
import javafx.collections.transformation.SortedList;

/**
 * This abstract class provides the main template for calculating the convex
 * outline independently for the left and the right outline.
 * 
 * @author Christian Luetticke
 * @version 2.0
 * @see ConvexOutlineLeftCalculator
 * @see ConvexOutlineRightCalculator
 */
abstract class ConvexOutlineTemplate {

	/**
	 * Reference to {@link MainFacade#get_PointsSorted()}
	 */
	protected SortedList<Point> currentPoints;

	/**
	 * Abstract method implemented in subclasses.
	 * 
	 * @return TreeSet A set containing all points belonging to the outline.
	 */
	protected abstract TreeSet<SimplePoint> createOutline();

	/**
	 * Main method which runs the convex hull calculation. Step 1: create outline
	 * (contour polygon) Step 2: filter this outline (remove all concave or
	 * collinear constellations)
	 * 
	 * @return TreeSet A set of all convex hull points for either left or right
	 *         outline.
	 * @throws NullPointerException
	 *             If no points are available.
	 */
	public TreeSet<SimplePoint> getConvexOutline() {

		this.currentPoints = MainFacade.get_Instance().get_PointsSorted();

		if (!currentPoints.isEmpty()) {
			TreeSet<SimplePoint> outline = createOutline();

			if (outline.size() > 2) {
				filterOutline(outline);
			}
			return outline;
		}

		throw new NullPointerException("no points available");

	}

	/**
	 * Filters the already calculated outline.
	 * <P>
	 * All {@link SimplePoint} instances which returned {@link Curve#COLLINEAR} or
	 * {@link Curve#CONCAVE} during the
	 * {@link LeftRightTester#getResult(SimplePoint, SimplePoint, SimplePoint)} call
	 * are removed from the outline.
	 * <P>
	 * If a collinear of concave constellation is found, reset the iterator and
	 * start testing all points again from the beginning. This way all convex
	 * constellations will be preserved as the result.
	 * 
	 * @param outline
	 *            The outline to process.
	 */
	private void filterOutline(TreeSet<SimplePoint> outline) {

		for (Iterator<SimplePoint> iterator = outline.iterator(); iterator.hasNext();) {

			SimplePoint A = (SimplePoint) iterator.next();
			SimplePoint B = outline.higher(A);
			SimplePoint C = outline.higher(B);

			if (!(A == null || B == null || C == null)) {

				Curve result = LeftRightTester.getResult(A, B, C);

				switch (result) {
				case CONVEX:
					continue;
				default:
					outline.remove(B);
					iterator = outline.iterator();
					continue;
				}
			}

			break;

		}

	}
}
