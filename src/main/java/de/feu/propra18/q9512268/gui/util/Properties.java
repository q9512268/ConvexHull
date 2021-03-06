package de.feu.propra18.q9512268.gui.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Final class providing access to properties.
 * @author Christian Luetticke
 * @version 2.0
 */
public final class Properties {
	/**
	 * Destination of properties.
	 */
	private static final String BUNDLE_NAME = "de.feu.propra18.q9512268.gui.util.messages"; 

	/**
	 * The ResourceBundle - depending on the BUNDLE_NAME.
	 */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	/**
	 * Private constructor to make sure class is only accessed in a static way.
	 */
	private Properties() {
	}

	/**
	 * Returns a matching string depending on the key.
	 * @param key The key to look for.
	 * @return
	 * String
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
