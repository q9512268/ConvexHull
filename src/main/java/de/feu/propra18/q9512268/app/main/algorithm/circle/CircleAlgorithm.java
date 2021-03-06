package de.feu.propra18.q9512268.app.main.algorithm.circle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import de.feu.propra18.q9512268.app.main.container.MainFacade;
import de.feu.propra18.q9512268.app.main.objects.Edge;
import javafx.collections.ObservableList;

/**
 * This final class controls the calculation of the largest included circle
 * (GEK).
 * <P>
 * Is only called via the {@link MainFacade}.
 * 
 * @author Christian Luetticke
 * @version 2.0
 */
public final class CircleAlgorithm {

	/**
	 * Private constructor to make sure class is only accessed in a static way.
	 */
	private CircleAlgorithm() {
	}

	/**
	 * Takes convex hull points, creates and connects edges between these points.
	 * <P>
	 * Iterates over edges, removes their corresponding inner circles until the edge
	 * combination with the largest included circle (GEK) is left.
	 * 
	 * @return List A list containing the circle information of the GEK (x
	 *         center position, y center position and radius size).
	 * @see MainFacade#calculate_GEK()
	 */
	public static List<Double> getGEKInfo() {

		List<Double> circleInformation = new ArrayList<Double>(3);

		ObservableList<Double> convexNodes = MainFacade.get_Instance().get_PointsConvexHull();

		/*
		 * convexNodes.size() == 2 --> only one edge: circle center is this point,
		 * radius == 0;
		 * 
		 * convesNodes.size() == 4 --> two edges: circle center is one of the two
		 * points, radius == 0
		 */
		if (convexNodes.size() == 2 || convexNodes.size() == 4) {

			double startX = convexNodes.get(0);
			double startY = convexNodes.get(1);
			circleInformation.add(startX);
			circleInformation.add(startY);
			circleInformation.add(0.0);
			return circleInformation;

		} else {

			List<Edge> edges = createEdges(convexNodes); // create edges from convex hull
			if (!edges.isEmpty()) {
				connectEdges(edges); // connect edges: set previous and next for each edge
			} else {
				throw new NullPointerException("no edges available");
			}

			/*
			 * Create set holding edges sorted by their inner circle radius. first() method
			 * of this set will always return the edge with corresponding smallest inner
			 * circle. TreeSet is used to provide O(log n) performance.
			 */
			Comparator<Edge> inCircleRadiusAscending = (p1, p2) -> Double.compare(
					p1.getInnerCircle().getRadius().doubleValue(), p2.getInnerCircle().getRadius().doubleValue());
			TreeSet<Edge> edgesRadiusSorted = new TreeSet<Edge>(inCircleRadiusAscending);
			edgesRadiusSorted.addAll(edges);
			filterEdges(edgesRadiusSorted); // filter edges by their inner circle radius until only largest included
											// circle left

			if (!convexNodes.isEmpty()) {
				circleInformation.add(edgesRadiusSorted.first().getInnerCircle().getX().doubleValue());
				circleInformation.add(edgesRadiusSorted.first().getInnerCircle().getY().doubleValue());
				circleInformation.add(edgesRadiusSorted.first().getInnerCircle().getRadius().doubleValue());
				return circleInformation;
			}
		}

		throw new NullPointerException("no points available");

	}

	/**
	 * Takes convex hull double values and creates edges.
	 * 
	 * @param convexNodes
	 *            The convex hull represented by double values.
	 * @return List A List of edges which constitute the convex hull.
	 */
	private static List<Edge> createEdges(ObservableList<Double> convexNodes) {

		List<Edge> list = new LinkedList<Edge>();

		int i = 0;
		while (i < convexNodes.size() - 1) {
			double startX;
			double startY;
			double endX;
			double endY;

			// look at 4 double values as coordinates for an edge
			startX = convexNodes.get(i);
			startY = convexNodes.get(i + 1);
			// if last edge should be created
			if (i == convexNodes.size() - 2) {
				endX = convexNodes.get(0);
				endY = convexNodes.get(0 + 1);
			} else {
				endX = convexNodes.get(i + 2);
				endY = convexNodes.get(i + 3);
			}

			Edge edge = new Edge(startX, startY, endX, endY);
			list.add(edge);

			i = i + 2;
		}

		return list;
	}

	/**
	 * Connect edges. Set previous and next edge.
	 * 
	 * @param edges
	 *            List of edges which should be connected.
	 */
	private static void connectEdges(List<Edge> edges) {

		// connect first and last edges
		edges.get(0).setPrevious(edges.get(edges.size() - 1));
		edges.get(edges.size() - 1).setNext(edges.get(0));

		// connect remaining
		for (int i = 0; i < edges.size(); i++) {
			Edge edge = edges.get(i);

			if (!(i - 1 < 0)) {
				edge.setPrevious(edges.get(i - 1));
			}
			if (!(i + 1 > edges.size() - 1)) {
				edge.setNext(edges.get(i + 1));
			}
		}

	}

	/**
	 * Filter edges until edge with corresponding largest inner circle remains. This
	 * inner circle represents the largest included circle (GEK).
	 * 
	 * @param edgesRadiusSorted
	 *            A set containing all connected edges sorted by the radius of their
	 *            corresponding inner circle.
	 */
	private static void filterEdges(TreeSet<Edge> edgesRadiusSorted) {
		while (edgesRadiusSorted.size() > 3) {
			Edge min = edgesRadiusSorted.first(); // get edge with smallest inner circle
			Edge min_previous = min.getPrevious(); // get previous edge
			Edge min_next = min.getNext(); // get next edge

			edgesRadiusSorted.remove(min); // remove edge from radius structure
			edgesRadiusSorted.remove(min_previous); // remove previous edge
			edgesRadiusSorted.remove(min_next); // remove next edge

			/*
			 * Reconnect previous and next edge with each other. Reconnection triggers
			 * recalculation of inner circle due to new edge combination.
			 */
			min_previous.setNext(min_next);
			min_next.setPrevious(min_previous);

			// re-add previous and next edges to radius structure
			edgesRadiusSorted.add(min_previous);
			edgesRadiusSorted.add(min_next);

		}
	}

}
