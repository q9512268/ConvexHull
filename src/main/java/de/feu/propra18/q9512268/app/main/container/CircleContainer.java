package de.feu.propra18.q9512268.app.main.container;

import de.feu.propra18.q9512268.app.main.objects.SimpleCircle;

/**
 * This class, which is realized as a singleton acts as a container for the
 * current largest included circle (GEK). Information about the
 * {@link CircleContainer#gek} are bound the circle shape on the front-end side.
 * <P>
 * The functions provided by this class can be accessed via the
 * {@link MainFacade}.
 * 
 * @author Christian Luetticke
 * @version 2.0
 * @see MainFacade
 */
class CircleContainer {

	/**
	 * Final singleton instance of the {@link CircleContainer} class.
	 * <P>
	 * Gets created when accessed the first time.
	 */
	private static final CircleContainer INSTANCE = new CircleContainer();

	/**
	 * A {@link SimpleCircle} instance representing the largest included circle
	 * (GEK).
	 */
	private SimpleCircle gek;

	/**
	 * Private constructor which can never be called directly.
	 * <P>
	 * Only instantiated when accessing {@link CircleContainer#INSTANCE} for the
	 * first time.
	 * <P>
	 * Constructs a {@link SimpleCircle} instance.
	 */
	private CircleContainer() {
		gek = new SimpleCircle();
	}

	/**
	 * Get access to the {@link CircleContainer#INSTANCE}.
	 * 
	 * @return CircleContainer The CircleContainer instance.
	 * @see MainFacade
	 */
	static CircleContainer get_Instance() {
		return INSTANCE;
	}

	/**
	 * Get access to the largest included circle (GEK).
	 * 
	 * @return SimpleCircle The GEK.
	 * @see MainFacade#get_GEK()
	 */
	SimpleCircle get_GEK() {
		return gek;
	}

	/**
	 * Sets the center x coordinate of the largest included circle (GEK).
	 * 
	 * @param d
	 *            A new value for center x.
	 * @see MainFacade#calculate_GEK()
	 */
	void setGEK_X(Double d) {
		gek.setX(d);
	}

	/**
	 * Sets the center y coordinate of the largest included circle (GEK).
	 * 
	 * @param d
	 *            A new value for center y.
	 * @see MainFacade#calculate_GEK()
	 */
	void setGEK_Y(Double d) {
		gek.setY(d);
	}

	/**
	 * Sets the radius size of the largest included circle (GEK).
	 * 
	 * @param d
	 *            A new value for the radius.
	 * @see MainFacade#calculate_GEK()
	 */
	void setGEK_Radius(Double d) {
		gek.setRadius(d);
	}

	/**
	 * To call when x,y,radius information of the largest included circle (GEK)
	 * should all be set to 0.
	 * 
	 * @see MainFacade#clear_Container()
	 * @see SimpleCircle#clear()
	 */
	public void remove_GEK() {
		gek.clear();
	}

}
