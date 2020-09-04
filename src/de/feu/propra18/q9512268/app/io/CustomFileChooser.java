package de.feu.propra18.q9512268.app.io;

import java.io.File;

import de.feu.propra18.q9512268.gui.util.Properties;
import javafx.stage.FileChooser;

/**
 * Final singleton class that provides a customized FileChooser instance.
 * <P>
 * Can only be accessed via {@link IOFacade}.
 * 
 * @author Christian Luetticke
 * @version 2.0
 * 
 */
final class CustomFileChooser {

	/**
	 * Final singleton instance of the {@link CustomFileChooser} class.
	 * <P>
	 * Gets created when accessed for the first time.
	 */
	private static final CustomFileChooser INSTANCE = new CustomFileChooser();
	/**
	 * Reference to FileChooser.
	 * 
	 * @see CustomFileChooser#get_fc
	 */
	private FileChooser fc;

	/**
	 * Private constructor which cannot be called directly.
	 * <P>
	 * Only instantiated when accessing {@link CustomFileChooser#INSTANCE} for the
	 * first time.
	 * <P>
	 * Creates a FileChooser and sets its initial directory.
	 * 
	 * @see de.feu.propra18.q9512268.gui.util.Properties
	 */
	private CustomFileChooser() {
		fc = new FileChooser();
		File dir = new File(Properties.getString("TESTER_DATA"));
		if (dir.exists()) {
			fc.setInitialDirectory(dir);
		}
	}

	/**
	 * Get access to the {@link CustomFileChooser#fc}.
	 * 
	 * @return The FileChooser instance.
	 * @see IOFacade#get_FileChooser()
	 */
	protected FileChooser get_fc() {
		return fc;
	}

	/**
	 * Get access to the {@link CustomFileChooser#INSTANCE}.
	 * 
	 * @return The CustomFileChooser instance.
	 */
	protected static CustomFileChooser getInstance() {
		return INSTANCE;
	}

}
