package de.feu.propra18.q9512268.test;

import java.io.File;
import java.util.List;

import de.feu.propra18.q9512268.app.main.container.MainFacade;
import de.feu.propra18.q9512268.app.main.objects.Point;
import de.feu.propra18.q9512268.app.main.objects.SimpleCircle;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

/**
 * Class for additional tests while developing.
 * <P>
 * Can be instantiated independently of main program.
 * 
 * @author Christian Luetticke
 * @version 2.0
 */
public class Test {

	/**
	 * A reference to {@link MainFacade}
	 */
	private static MainFacade FACADE;

	/**
	 * Main method calling each test and printing result.
	 * <P>
	 * After each test {@link MainFacade#clear_Container()} is called to remove all
	 * points and convex hull coordinates.
	 * 
	 * @param args
	 *            void
	 */
	public static void main(String[] args) {

		FACADE = MainFacade.get_Instance();
		System.out.println(test_add_Point() + ": test_add_Point");
		FACADE.clear_Container();
		System.out.println(test_add_PointsFromArray() + ": test_add_PointsFromArray");
		FACADE.clear_Container();
		System.out.println(test_remove_Point() + ": test_remove_Point");
		FACADE.clear_Container();
		System.out.println(
				test_remove_Point_calculate_PointsConvexHull() + ": test_remove_Point_calculate_PointsConvexHull");
		FACADE.clear_Container();
		System.out.println(
				test_remove_Point_calculate_PointsConvexHull2() + ": test_remove_Point_calculate_PointsConvexHull2");
		FACADE.clear_Container();
		System.out.println(test_add_PointsFromFile() + ": test_add_PointsFromFile");
		FACADE.clear_Container();
		System.out.println(test_clear() + ": test_clear");
		FACADE.clear_Container();
		System.out.println(test_save_reload_Points() + ": test_save_reload_Points");
		FACADE.clear_Container();
		System.out.println(test_fileChooser() + ": test_fileChooser");
		FACADE.clear_Container();
		System.out.println(test_add_PointsRandom() + ": test_add_PointsRandom");
		FACADE.clear_Container();
		System.out.println(test_add_PointsRandom2() + ": test_add_PointsRandom2");
		FACADE.clear_Container();
		System.out.println(
				add_PointsRandom_calculate_PointsConvexHull() + ": add_PointsRandom_calculate_PointsConvexHull");
		FACADE.clear_Container();
		System.out.println(test_calculate_PointsConvexHull() + ": test_calculate_PointsConvexHull");
		FACADE.clear_Container();
		System.out.println(test_calculate_GEK_sechseck1() + ": test_calculate_GEK_sechseck1");
		FACADE.clear_Container();
		System.out.println(test_calculate_GEK_sechseck1_remove() + ": test_calculate_GEK_sechseck1_remove");
		FACADE.clear_Container();
		System.out.println(
				test_calculate_get_PointsConvexHull_asArray() + ": test_calculate_get_PointsConvexHull_asArray");
		FACADE.clear_Container();
		System.out.println(longChallengeTest() + ": longChallengeTest");
		FACADE.clear_Container();
		System.out.println(linie2Test() + ": linie2Test");
		FACADE.clear_Container();

	}

	/**
	 * Test to see if adding a point works.
	 * 
	 * @return boolean Test Result.
	 */
	private static boolean test_add_Point() {

		long startTime = System.nanoTime();

		FACADE.add_Point(new Point(3, 3));
		long endTime1 = System.nanoTime();
		System.out.print("(" + (endTime1 - startTime) / 1000000.0 + " ms) ");
		if (!(FACADE.get_PointsAll().size() == 1)) {
			return false;
		}
		long endTime = System.nanoTime();
		System.out.print("(" + (endTime - startTime) / 1000000.0 + " ms) ");
		return true;

	}

	/**
	 * Test to see if adding points from a 2D array works.
	 * 
	 * @return boolean Test Result.
	 */
	public static boolean test_add_PointsFromArray() {

		long startTime = System.nanoTime();

		Point p1 = new Point(3, 5);
		Point p2 = new Point(40, 20);
		Point p3 = new Point(60, 80);
		Point p4 = new Point(55, 66);

		int[][] array = { { 3, 5 }, { 40, 20 }, { 60, 80 }, { 55, 66 } };
		FACADE.add_PointsFromArray(array);

		if (!(FACADE.get_PointsAll().size() == 4)) {
			return false;
		}
		if (!(FACADE.contains_Point(p1))) {
			return false;
		}
		if (!(FACADE.contains_Point(p2))) {
			return false;
		}
		if (!(FACADE.contains_Point(p3))) {
			return false;
		}
		if (!(FACADE.contains_Point(p4))) {
			return false;
		}

		long endTime = System.nanoTime();
		System.out.print("(" + (endTime - startTime) / 1000000.0 + " ms) ");
		return true;

	}

	/**
	 * Test to see if adding points from a file works.
	 * 
	 * @return boolean Test Result.
	 */
	private static boolean test_add_PointsFromFile() {

		long startTime = System.nanoTime();

		Point p1 = new Point(200, 100);
		Point p2 = new Point(230, 160);
		Point p3 = new Point(300, 175);

		FACADE.add_PointsFromFile("../Tester/data/menge-drei-punkt.points");

		if (!(FACADE.get_PointsAll().size() == 3)) {
			return false;
		}
		if (!(FACADE.contains_Point(p1))) {
			return false;
		}
		if (!(FACADE.contains_Point(p2))) {
			return false;
		}
		if (!(FACADE.contains_Point(p3))) {
			return false;
		}

		long endTime = System.nanoTime();
		System.out.print("(" + (endTime - startTime) / 1000000.0 + " ms) ");
		return true;

	}

	/**
	 * Test to see if removing a point works.
	 * 
	 * @return boolean Test Result.
	 */
	private static boolean test_remove_Point() {

		long startTime = System.nanoTime();

		Point p1 = new Point(200, 100);
		Point p2 = new Point(230, 160);
		Point p3 = new Point(300, 175);

		FACADE.add_PointsFromFile("../Tester/data/menge-drei-punkt.points");

		if (!(FACADE.get_PointsAll().size() == 3)) {
			return false;
		}
		if (!(FACADE.contains_Point(p1))) {
			return false;
		}
		if (!(FACADE.contains_Point(p2))) {
			return false;
		}
		if (!(FACADE.contains_Point(p3))) {
			return false;
		}

		FACADE.remove_Point(p1);
		if (!(FACADE.get_PointsAll().size() == 2)) {
			return false;
		}
		if (FACADE.contains_Point(p1)) {
			return false;
		}

		long endTime = System.nanoTime();
		System.out.print("(" + (endTime - startTime) / 1000000.0 + " ms) ");
		return true;

	}

	/**
	 * Test to see if convex hull is correctly calculated after removing a point.
	 * 
	 * @return boolean Test Result.
	 */
	private static boolean test_remove_Point_calculate_PointsConvexHull() {

		long startTime = System.nanoTime();

		Point p1 = new Point(200, 100);
		Point p2 = new Point(230, 160);
		Point p3 = new Point(300, 175);
		Point p4 = new Point(400, 800);

		FACADE.add_Point(new Point(p1.getX(), p1.getY()));
		FACADE.add_Point(new Point(p2.getX(), p2.getY()));
		FACADE.add_Point(new Point(p3.getX(), p3.getY()));
		FACADE.add_Point(new Point(p4.getX(), p4.getY()));

		if (!(FACADE.get_PointsAll().size() == 4)) {
			return false;
		}
		if (!(FACADE.contains_Point(p1))) {
			return false;
		}
		if (!(FACADE.contains_Point(p2))) {
			return false;
		}
		if (!(FACADE.contains_Point(p3))) {
			return false;
		}
		if (!(FACADE.contains_Point(p4))) {
			return false;
		}
		FACADE.remove_Point(p1);
		FACADE.clear_ConvexHullPoints();
		FACADE.calculate_PointsConvexHull();
		if (!(FACADE.get_PointsAll().size() == 3)) {
			return false;
		}
		if (FACADE.contains_Point(p1)) {
			return false;
		}
		if (!(FACADE.get_PointsConvexHull().size() == 6)) {
			return false;
		}

		long endTime = System.nanoTime();
		System.out.print("(" + (endTime - startTime) / 1000000.0 + " ms) ");
		return true;

	}

	/**
	 * Test to see if convex hull is correctly calculated after removing a point.
	 * 
	 * @return boolean Test Result.
	 */
	private static boolean test_remove_Point_calculate_PointsConvexHull2() {

		long startTime = System.nanoTime();

		Point p1 = new Point(200, 100);
		Point p2 = new Point(230, 160);
		Point p3 = new Point(300, 175);
		Point p4 = new Point(400, 800);

		FACADE.add_Point(p1);
		FACADE.add_Point(p2);
		FACADE.add_Point(p3);
		FACADE.add_Point(p4);

		if (!(FACADE.get_PointsAll().size() == 4)) {
			return false;
		}
		if (!(FACADE.contains_Point(p1))) {
			return false;
		}
		if (!(FACADE.contains_Point(p2))) {
			return false;
		}
		if (!(FACADE.contains_Point(p3))) {
			return false;
		}
		if (!(FACADE.contains_Point(p4))) {
			return false;
		}

		FACADE.remove_Point(p1);
		FACADE.remove_Point(p2);
		FACADE.remove_Point(p3);
		FACADE.remove_Point(p4);
		FACADE.clear_ConvexHullPoints();
		FACADE.calculate_PointsConvexHull();
		FACADE.clear_Container();
		if (!(FACADE.get_PointsAll().size() == 0)) {
			return false;
		}
		if (FACADE.contains_Point(p1)) {
			return false;
		}
		if (!(FACADE.get_PointsConvexHull().size() == 0)) {
			return false;
		}

		long endTime = System.nanoTime();
		System.out.print("(" + (endTime - startTime) / 1000000.0 + " ms) ");
		return true;

	}

	/**
	 * Test of {@link MainFacade#clear_Container()}
	 * 
	 * @return boolean Test Result.
	 */
	private static boolean test_clear() {

		long startTime = System.nanoTime();

		FACADE.add_Point(new Point(3, 3));
		FACADE.add_Point(new Point(4, 4));
		if (!(FACADE.get_PointsAll().size() == 2)) {
			return false;
		}
		FACADE.clear_Container();
		if (!(FACADE.get_PointsAll().size() == 0)) {
			return false;
		}
		long endTime = System.nanoTime();
		System.out.print("(" + (endTime - startTime) / 1000000.0 + " ms) ");
		return true;
	}

	/**
	 * Test if saving points to file and reloading them works.
	 * 
	 * @return boolean Test Result.
	 */
	private static boolean test_save_reload_Points() {

		long startTime = System.nanoTime();

		Point p1 = new Point(3, 5);
		Point p2 = new Point(40, 20);
		Point p3 = new Point(60, 80);
		Point p4 = new Point(55, 66);

		int[][] array = { { 3, 5 }, { 40, 20 }, { 60, 80 }, { 55, 66 } };
		FACADE.add_PointsFromArray(array);

		if (!(FACADE.get_PointsAll().size() == 4)) {
			return false;
		}
		if (!(FACADE.contains_Point(p1))) {
			return false;
		}
		if (!(FACADE.contains_Point(p2))) {
			return false;
		}
		if (!(FACADE.contains_Point(p3))) {
			return false;
		}
		if (!(FACADE.contains_Point(p4))) {
			return false;
		}
		File file = new File("save_reload_Points.txt");
		FACADE.save_PointsToFile(file.getAbsolutePath());

		FACADE.clear_Container();
		if (!(FACADE.get_PointsAll().size() == 0)) {
			return false;
		}

		FACADE.add_PointsFromFile(file.getAbsolutePath());
		if (!(FACADE.get_PointsAll().size() == 4)) {
			return false;
		}

		file.delete();

		long endTime = System.nanoTime();
		System.out.print("(" + (endTime - startTime) / 1000000.0 + " ms) ");
		return true;

	}

	/**
	 * Test to see if FileChooser instance works.
	 * 
	 * @return boolean Test Result.
	 */
	private static boolean test_fileChooser() {

		long startTime = System.nanoTime();

		FileChooser fileChooser = FACADE.get_FileChooser();

		if (!(fileChooser.getInitialDirectory().equals(new File("../Tester/data")))) {
			return false;

		}
		long endTime = System.nanoTime();
		System.out.print("(" + (endTime - startTime) / 1000000.0 + " ms) ");
		return true;

	}

	/**
	 * Test if adding random points works.
	 * 
	 * @return boolean Test Result.
	 */
	private static boolean test_add_PointsRandom() {

		long startTime = System.nanoTime();

		FACADE.add_PointsRandom(1000, 800, 800);
		if (!(FACADE.get_PointsAll().size() == 1000)) {
			return false;
		}
		FACADE.add_PointsRandom(500, 800, 800);
		if (!(FACADE.get_PointsAll().size() == 1500)) {
			return false;
		}
		FACADE.add_PointsRandom(50, 800, 800);
		if (!(FACADE.get_PointsAll().size() == 1550)) {
			return false;
		}
		FACADE.add_PointsRandom(100, 800, 800);
		if (!(FACADE.get_PointsAll().size() == 1650)) {
			return false;
		}
		FACADE.add_PointsRandom(10, 800, 800);
		if (!(FACADE.get_PointsAll().size() == 1660)) {
			return false;
		}

		FACADE.add_Point(new Point(10, 2));
		if (!(FACADE.get_PointsAll().size() == 1661)) {
			return false;
		}

		long endTime = System.nanoTime();
		System.out.print("(" + (endTime - startTime) / 1000000.0 + " ms) ");
		return true;
	}

	/**
	 * Test if adding random points works.
	 * 
	 * @return boolean Test Result.
	 */
	private static boolean test_add_PointsRandom2() {

		long startTime = System.nanoTime();

		FACADE.add_PointsRandom(3000, 800, 800);
		if (!(FACADE.get_PointsAll().size() == 3000)) {
			return false;
		}

		long endTime = System.nanoTime();
		System.out.print("(" + (endTime - startTime) / 1000000.0 + " ms) ");
		return true;
	}

	/**
	 * Test if adding random points works and convex hull is calculated.
	 * 
	 * @return boolean Test Result.
	 */
	private static boolean add_PointsRandom_calculate_PointsConvexHull() {

		long startTime = System.nanoTime();

		FACADE.add_PointsRandom(3000, 800, 800);
		if (!(FACADE.get_PointsAll().size() == 3000)) {
			return false;
		}
		ObservableList<Double> pointsConvexHull = FACADE.get_PointsConvexHull();
		if (!(pointsConvexHull.isEmpty())) {
			return false;
		}

		FACADE.add_PointsRandom(3220, 800, 800);
		if (!(FACADE.get_PointsAll().size() == 6220)) {
			return false;
		}

		long endTime = System.nanoTime();
		System.out.print("(" + (endTime - startTime) / 1000000.0 + " ms) ");
		return true;
	}

	/**
	 * Test to see if convex hull is calculated correctly.
	 * <P>
	 * Uses file ../Tester/data/menge-drei-punkt.points.
	 * 
	 * @return boolean Test Result.
	 */
	private static boolean test_calculate_PointsConvexHull() {

		long startTime = System.nanoTime();

		Point p1 = new Point(200, 100);
		Point p2 = new Point(230, 160);
		Point p3 = new Point(300, 175);

		FACADE.add_PointsFromFile("../Tester/data/menge-drei-punkt.points");

		if (!(FACADE.get_PointsAll().size() == 3)) {
			return false;
		}
		if (!(FACADE.contains_Point(p1))) {
			return false;
		}
		if (!(FACADE.contains_Point(p2))) {
			return false;
		}
		if (!(FACADE.contains_Point(p3))) {
			return false;
		}

		FACADE.calculate_PointsConvexHull();
		ObservableList<Double> pointsConvexHull = FACADE.get_PointsConvexHull();
		if (!(pointsConvexHull.size() == 6)) {
			return false;
		}
		if (!(pointsConvexHull.get(0).intValue() == p1.getX())) {
			return false;
		}
		if (!(pointsConvexHull.get(1).intValue() == p1.getY())) {
			return false;
		}

		long endTime = System.nanoTime();
		System.out.print("(" + (endTime - startTime) / 1000000.0 + " ms) ");
		return true;

	}

	/**
	 * Test to see if largest included circle is calculated correctly.
	 * 
	 * @return boolean Test Result.
	 */
	private static boolean test_calculate_GEK_sechseck1() {

		long startTime = System.nanoTime();

		FACADE.add_PointsFromFile("../Tester/data/sechseck1.points");

		FACADE.calculate_PointsConvexHull();
		FACADE.calculate_GEK();
		SimpleCircle gek = FACADE.get_GEK();

		if (!(Double.compare(gek.getX().doubleValue(), new Double(199.99999999999997)) == 0)) {
			return false;
		}
		if (!(Double.compare(gek.getY().doubleValue(), new Double(199.99999999999997)) == 0)) {
			return false;
		}
		if (!(Double.compare(gek.getRadius().doubleValue(), new Double(89.44271909999155)) == 0)) {
			return false;
		}

		long endTime = System.nanoTime();
		System.out.print("(" + (endTime - startTime) / 1000000.0 + " ms) ");
		return true;

	}

	/**
	 * Test to see if largest included circle is calculated correctly.
	 * 
	 * @return boolean Test Result.
	 */
	private static boolean test_calculate_GEK_sechseck1_remove() {

		long startTime = System.nanoTime();

		Point p1 = new Point(100, 200);
		Point p2 = new Point(150, 100);
		Point p3 = new Point(250, 100);
		Point p4 = new Point(300, 200);
		Point p5 = new Point(150, 300);
		
		FACADE.add_Point(p1);
		FACADE.add_Point(p2);
		FACADE.add_Point(p3);
		FACADE.add_Point(p4);
		FACADE.add_Point(p5);

		if (!(FACADE.get_PointsAll().size() == 5)) {
			return false;
		}
		if (!(FACADE.contains_Point(p1))) {
			return false;
		}
		if (!(FACADE.contains_Point(p2))) {
			return false;
		}
		if (!(FACADE.contains_Point(p3))) {
			return false;
		}
		if (!(FACADE.contains_Point(p4))) {
			return false;
		}
		if (!(FACADE.contains_Point(p5))) {
			return false;
		}
		FACADE.calculate_PointsConvexHull();
		FACADE.calculate_GEK();
		SimpleCircle gek = FACADE.get_GEK();

		if (!(Double.compare(gek.getX().doubleValue(), new Double(197.28875265268505)) == 0)) {
			return false;
		}
		if (!(Double.compare(gek.getY().doubleValue(), new Double(176.51480907763113)) == 0)) {
			return false;
		}
		if (!(Double.compare(gek.getRadius().doubleValue(), new Double(76.51480907763113)) == 0)) {
			return false;
		}
		
		//REMOVE 1 POINT
		FACADE.remove_Point(p3);
		FACADE.clear_ConvexHullPoints();
		FACADE.calculate_PointsConvexHull();
		FACADE.calculate_GEK();
		gek = FACADE.get_GEK();
		
		if (!(Double.compare(gek.getX().doubleValue(), new Double(176.55644370746373)) == 0)) {
			return false;
		}
		if (!(Double.compare(gek.getY().doubleValue(), new Double(200.0)) == 0)) {
			return false;
		}
		if (!(Double.compare(gek.getRadius().doubleValue(), new Double(68.47416489820999)) == 0)) {
			return false;
		}
		
		//REMOVE 2 POINTS - ONLY 2 POINTS LEFT
		FACADE.remove_Point(p1);
		FACADE.remove_Point(p2);		
		FACADE.clear_ConvexHullPoints();
		FACADE.calculate_PointsConvexHull();
		FACADE.calculate_GEK();
		gek = FACADE.get_GEK();
		
		if (!(Double.compare(gek.getRadius().doubleValue(), new Double(0)) == 0)) {
			return false;
		}
		
		long endTime = System.nanoTime();
		System.out.print("(" + (endTime - startTime) / 1000000.0 + " ms) ");
		return true;

	}

	/**
	 * Test to see if convex hull is calculated and returned correctly as array.
	 * <P>
	 * Uses file ../Tester/data/menge-drei-punkt.points.
	 * 
	 * @return boolean Test Result.
	 */
	private static boolean test_calculate_get_PointsConvexHull_asArray() {

		long startTime = System.nanoTime();

		Point p1 = new Point(200, 100);
		Point p2 = new Point(230, 160);
		Point p3 = new Point(300, 175);

		FACADE.add_PointsFromFile("../Tester/data/menge-drei-punkt.points");

		if (!(FACADE.get_PointsAll().size() == 3)) {
			return false;
		}
		if (!(FACADE.contains_Point(p1))) {
			return false;
		}
		if (!(FACADE.contains_Point(p2))) {
			return false;
		}
		if (!(FACADE.contains_Point(p3))) {
			return false;
		}

		FACADE.calculate_PointsConvexHull();
		int[][] convexHull = FACADE.get_PointsConvexHull_asArray();
		if (!(convexHull.length == 3)) {
			return false;
		}
		if (!(convexHull[0][0] == p1.getX())) {
			return false;
		}
		if (!(convexHull[0][1] == p1.getY())) {
			return false;
		}

		long endTime = System.nanoTime();
		System.out.print("(" + (endTime - startTime) / 1000000.0 + " ms) ");
		return true;

	}

	/**
	 * Test to see if convex hull is calculated correctly.
	 * <P>
	 * Uses file ../Tester/data/longchallenge.points.
	 * 
	 * @return boolean Test Result.
	 */
	private static boolean longChallengeTest() {

		long startTime = System.nanoTime();

		FACADE.add_PointsFromFile("../Tester/data/longchallenge.points");

		if (!(FACADE.get_PointsAll().size() == 4)) {
			return false;
		}

		FACADE.calculate_PointsConvexHull();
		int[][] convexHull = FACADE.get_PointsConvexHull_asArray();
		if (!(convexHull.length == 4)) {
			return false;
		}

		long endTime = System.nanoTime();
		System.out.print("(" + (endTime - startTime) / 1000000.0 + " ms) ");
		return true;

	}

	/**
	 * Test to see if convex hull is calculated correctly.
	 * <P>
	 * Uses file ../Tester/data/linie2.points.
	 * 
	 * @return boolean Test Result.
	 */
	private static boolean linie2Test() {

		long startTime = System.nanoTime();

		FACADE.add_PointsFromFile("../Tester/data/linie2.points");

		if (!(FACADE.get_PointsAll().size() == 5)) {
			return false;
		}

		FACADE.calculate_PointsConvexHull();
		int[][] convexHull = FACADE.get_PointsConvexHull_asArray();
		if (!(convexHull.length == 2)) {
			return false;
		}

		long endTime = System.nanoTime();
		System.out.print("(" + (endTime - startTime) / 1000000.0 + " ms) ");
		return true;

	}

}
