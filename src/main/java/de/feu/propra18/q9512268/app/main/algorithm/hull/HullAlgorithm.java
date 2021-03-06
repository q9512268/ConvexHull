package de.feu.propra18.q9512268.app.main.algorithm.hull;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;

import de.feu.propra18.q9512268.app.main.container.MainFacade;
import de.feu.propra18.q9512268.app.main.objects.SimplePoint;

/**
 * This final class controls the calculation of the convex hull points. <P>
 * Is only called via the {@link MainFacade}.
 * 
 * @author Christian Luetticke
 * @version 2.0
 */
public final class HullAlgorithm {

	/**
	 * Private constructor to make sure class is only accessed in a static way.
	 */
	private HullAlgorithm() {
	}

	/**
	 * Uses {@link CompletableFuture} to calculate left and right outline
	 * simultaneous. <P>
	 * Once both calculations are finished the returned {@link SimplePoint}
	 * instances are added to a {@link LinkedHashSet} to eliminate duplicates but
	 * keep the insertion order.
	 * 
	 * @return Set A set containing all points belonging to the current convex hull.
	 */
	public static Set<SimplePoint> getConvexHull() {

		CompletableFuture<TreeSet<SimplePoint>> task1 = CompletableFuture
				.supplyAsync(() -> new ConvexOutlineLeftCalculator().getConvexOutline());
		CompletableFuture<TreeSet<SimplePoint>> task2 = CompletableFuture
				.supplyAsync(() -> new ConvexOutlineRightCalculator().getConvexOutline());

		CompletableFuture<Set<SimplePoint>> result = task1.thenCombine(task2, (left, right) -> {
			Set<SimplePoint> set = new LinkedHashSet<SimplePoint>();
			set.addAll(left);
			set.addAll(right);
			return set;
		});

		return result.join();

	}

}