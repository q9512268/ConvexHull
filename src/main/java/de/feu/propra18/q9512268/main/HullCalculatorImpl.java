package de.feu.propra18.q9512268.main;

import java.io.IOException;

import de.feu.propra18.interfaces.IHullCalculator;
import de.feu.propra18.q9512268.app.main.container.MainFacade;
import de.feu.propra18.q9512268.app.main.objects.Point;
import de.feu.propra18.q9512268.gui.util.Properties;
import javafx.collections.ObservableList;

/**
 * Implementation of {@link IHullCalculator}. Mainly delegates method calls to
 * corresponding methods in the {@link MainFacade}.
 * 
 * @author Christian Luetticke
 * @version 2.0
 */
public class HullCalculatorImpl implements IHullCalculator {

	/**
	 * List which holds the initially added points.
	 * <P>
	 * Necessary to avoid recalculation of convex hull and largest included circle
	 * if points haven't changed.
	 */
	private ObservableList<Point> initial_added_points;
	/**
	 * Tracks if the hull calculation has been called for the first time.
	 */
	private static boolean first_calc_hull = true;
	/**
	 * Tracks if the circle calculation has been called for the first time.
	 */
	private static boolean first_calc_gek = true;

	@Override
	public void addPoint(int x, int y) {
		MainFacade.get_Instance().add_Point(new Point(x, y));
	}

	@Override
	public void addPointsFromArray(int[][] pointArray) {
		MainFacade.get_Instance().add_PointsFromArray(pointArray);
	}

	@Override
	public void addPointsFromFile(String fileName) throws IOException {
		MainFacade.get_Instance().add_PointsFromFile(fileName);
		initial_added_points = MainFacade.get_Instance().get_PointsAll();
	}

	@Override
	public void clear() {
		MainFacade.get_Instance().clear_Container();
		first_calc_hull = true;
		first_calc_gek = true;
	}

	@Override
	public int[][] getConvexHull() {

		if (first_calc_hull) {
			MainFacade.get_Instance().calculate_PointsConvexHull();
		} else {
			/*
			 * If hull calculation is called again, make sure to only recalculate if point
			 * input has changed.
			 */
			if (initial_added_points != MainFacade.get_Instance().get_PointsAll()) {
				MainFacade.get_Instance().calculate_PointsConvexHull();
			}
		}
		first_calc_hull = false;

		return MainFacade.get_Instance().get_PointsConvexHull_asArray();
	}

	@Override
	public String getEmail() {
		return Properties.getString("EMAIL");
	}

	@Override
	public String getMatrNr() {
		return Properties.getString("MATR_NR");
	}

	@Override
	public String getName() {
		return Properties.getString("NAME");
	}

	@Override
	public double getGEKCenterX() {

		if (first_calc_gek) {
			MainFacade.get_Instance().calculate_GEK();
		} else {
			/*
			 * If circle calculation is called again, make sure to only recalculate if point
			 * input has changed.
			 */
			if (initial_added_points != MainFacade.get_Instance().get_PointsAll()) {
				MainFacade.get_Instance().calculate_GEK();
			}
		}
		first_calc_gek = false;

		return MainFacade.get_Instance().get_GEK().getX().doubleValue();
	}

	@Override
	public double getGEKCenterY() {
		return MainFacade.get_Instance().get_GEK().getY().doubleValue();
	}

	@Override
	public double getGEKRadius() {
		return MainFacade.get_Instance().get_GEK().getRadius().doubleValue();
	}

}
