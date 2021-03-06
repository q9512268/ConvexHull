package de.feu.propra18.q9512268.app.main.algorithm.hull;

import java.util.ArrayList;
import java.util.List;

import de.feu.propra18.q9512268.app.main.objects.SimplePoint;

/**
 * This final class provides functionality to run the left-right-test on three
 * points. <P>
 * Is only called via the {@link ConvexOutlineTemplate}.
 * 
 * @author Christian Luetticke
 * @version 2.0
 */
final class LeftRightTester {

	/**
	 * Private constructor to make sure class is only accessed in a static way.
	 */
	private LeftRightTester() {

	}

	/**
	 * Runs the left-right-test on three points in order to decide on the corresponding
	 * determinant. <P>
	 * Catches a long overflow and requests a different calculation strategy
	 * {@link LeftRightTester#newCalc(long[])}.
	 * 
	 * @param A First point
	 * @param B Second point
	 * @param C Third point
	 * @return Curve The result of the calculation as a {@link Curve} value.
	 */
	public static Curve getResult(SimplePoint A, SimplePoint B, SimplePoint C) {

		final long a_x = (long) A.getX();
		final long a_y = (long) A.getY();
		final long b_x = (long) B.getX();
		final long b_y = (long) B.getY();
		final long c_x = (long) C.getX();
		final long c_y = (long) C.getY();

		final long part1 = a_x * (b_y - c_y);
		final long part2 = b_x * (c_y - a_y);
		final long part3 = c_x * (a_y - b_y);

		long determinant = 0;
		try {
			determinant = Math.addExact(Math.addExact(part1, part2), part3);
		} catch (ArithmeticException e) {
			long[] parts = { part1, part2, part3 };
			determinant = newCalc(parts);
		}

		if (determinant > 0) {
			return Curve.CONCAVE;
		} else if (determinant < 0) {
			return Curve.CONVEX;
		} else {
			return Curve.COLLINEAR;
		}

	}

	/**
	 * Alternative calculation of the determinant. <P>
	 * This method creates two different lists depending on the signum and delegates
	 * the determinant calculation to
	 * {@link LeftRightTester#test_parts(List, List, int)}
	 * 
	 * @param parts
	 *            The 3 different interim calculations which have already been done
	 *            and which caused an {@link ArithmeticException}.
	 * 
	 * @return long Result of determinant calculation.
	 */
	private static long newCalc(long[] parts) {
		List<Long> pos = new ArrayList<Long>();
		List<Long> neg = new ArrayList<Long>();

		for (int i = 0; i < parts.length; i++) {
			long part = parts[i];
			long sig = Long.signum(part);
			if (sig == 1) {
				pos.add(part);
			} else {
				neg.add(part);
			}
		}

		return test_parts(pos, neg, 3);

	}

	/**
	 * This method uses two different lists and runs the sum calculation step by
	 * step regarding the signum of the current interim result. <P>
	 * Depending on the distribution of values in the lists the determinant can be
	 * guessed.
	 * 
	 * @param pos
	 *            List of positive interim results.
	 * @param neg
	 *            List of negative interim results.
	 * @param size
	 *            The size to test each list for.
	 * @return long
	 */
	private static long test_parts(List<Long> pos, List<Long> neg, int size) {
		if (pos.size() == size) {
			return 1;
		} else if (neg.size() == size) {
			return -1;
		} else {
			long sum = Math.addExact(pos.get(0), neg.get(0));
			pos.remove(0);
			neg.remove(0);
			long sumsig = Long.signum(sum);
			if (sumsig == 1) {
				pos.add(sum);
			} else {
				neg.add(sum);
			}

			return test_parts(pos, neg, size - 1);
		}
	}

}
