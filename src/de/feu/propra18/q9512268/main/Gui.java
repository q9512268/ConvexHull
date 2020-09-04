package de.feu.propra18.q9512268.main;

import de.feu.propra18.q9512268.gui.controller.MainController;
import de.feu.propra18.q9512268.gui.util.Properties;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class representing a JavaFX {@link Application}.
 * @author Christian Luetticke
 * @version 2.0
 */
public class Gui extends Application {

	/**
	 * Starts the application.
	 * <P>Sets Main.fxml as the {@link Scene}.
	 * <P>Connects Main.fxml with its {@link MainController}.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {

			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Properties.getString("MAIN_FXML")));
			fxmlLoader.setController(new MainController());

			final Parent root = fxmlLoader.load();

			Scene scene = new Scene(root, 600, 700);

			primaryStage.setScene(scene);
			primaryStage.setTitle(Properties.getString("MAIN_TITLE"));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
