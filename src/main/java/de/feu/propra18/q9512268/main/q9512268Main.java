package de.feu.propra18.q9512268.main;

import de.feu.propra18.interfaces.IHullCalculator;
import de.feu.propra18.tester.Tester;
import javafx.application.Application;

/**
 * Main access point of the application. Either runs the {@link Tester} or
 * launches the {@link Application}.
 * 
 * @author Christian Luetticke
 * @version 2.0
 */
public class q9512268Main {

	/**
	 * Depending on program parameters either all tests are run or the {@link Gui} is
	 * launched.
	 * 
	 * @param args
	 *            void
	 */
	public static void main(String[] args) {

		if (args.length > 0 && "-t".equals(args[0])) {
			IHullCalculator calculator = new HullCalculatorImpl();
			Tester tester = new Tester(args, calculator);
			System.out.println(tester.test());
		} else {
			javafx.application.Application.launch(Gui.class);
		}

	}

}
