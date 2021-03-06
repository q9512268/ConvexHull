package de.feu.propra18.q9512268.app.main.algorithm.circle;

import java.util.ArrayList;
import java.util.List;

import de.feu.propra18.q9512268.app.main.objects.Edge;

/**
 * This final class provides functionality to calculate the inner circle based
 * on an edge, its previous and next edges.
 * 
 * @author Christian Luetticke
 * @version 2.0
 */
public final class InnerCircleCalculator {

	/**
	 * Private constructor to make sure class is only accessed in a static way.
	 */
	private InnerCircleCalculator() {
	}

	/**
	 * Algorithm to calculate the inner circle.
	 * <P>
	 * Triggered when previous or next edge of an edge is updated.
	 * <P>
	 * Uses an edge as input and calculates inner circle based on this edge, its
	 * previous and next edges.
	 * 
	 * @param current
	 *            An edge on which the calculation of inner circle should be run.
	 * @return List List representing x,y,radius information of the inner
	 *         circle.
	 * @see Edge
	 */
	public static List<Double> calculate_innerCircle(Edge current) {

		List<Double> circleInfo = new ArrayList<Double>(3);

		Edge previous = current.getPrevious();
		Edge next = current.getNext();

		if (previous != null && next != null) {

			double A_x = previous.getStartX();
			double A_y = previous.getStartY();

			double B_x = previous.getEndX();
			double B_y = previous.getEndY();

			double C_x = current.getStartX();
			double C_y = current.getStartY();

			double D_x = current.getEndX();
			double D_y = current.getEndY();

			double E_x = next.getStartX();
			double E_y = next.getStartY();

			double F_x = next.getEndX();
			double F_y = next.getEndY();

			double dAB = Math.sqrt(Math.pow((A_x - B_x), 2) + Math.pow((A_y - B_y), 2));
			double dCD = Math.sqrt(Math.pow((C_x - D_x), 2) + Math.pow((C_y - D_y), 2));
			double dEF = Math.sqrt(Math.pow((E_x - F_x), 2) + Math.pow((E_y - F_y), 2));

			double a_1 = (dAB * (D_y - C_y)) - (dCD * (B_y - A_y));
			double a_2 = (dCD * (F_y - E_y)) - (dEF * (D_y - C_y));

			double b_1 = (dCD * (B_x - A_x)) - (dAB * (D_x - C_x));
			double b_2 = (dEF * (D_x - C_x)) - (dCD * (F_x - E_x));

			double y_1 = dAB * ((C_x * (D_y - C_y)) - (C_y * (D_x - C_x)))
					+ dCD * ((A_y * (B_x - A_x)) - (A_x * (B_y - A_y)));
			double y_2 = dCD * ((E_x * (F_y - E_y)) - (E_y * (F_x - E_x)))
					+ dEF * ((C_y * (D_x - C_x)) - (C_x * (D_y - C_y)));

			double dH = (a_1 * b_2) - (a_2 * b_1);
			double dU = (y_1 * b_2) - (y_2 * b_1);
			double dV = (a_1 * y_2) - (a_2 * y_1);

			double Mx = dU / dH;
			double My = dV / dH;

			circleInfo.add(Mx);
			circleInfo.add(My);

			double r = (((A_x - Mx) * (B_y - A_y)) + ((A_y - My) * (A_x - B_x))) / dAB;

			/*
			 * Edges input was clockwise. Radius size sign has to be changed.
			 */
			circleInfo.add(r * -1); 
		}

		return circleInfo;

	}
}
