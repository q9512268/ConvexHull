package de.feu.propra18.q9512268.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.feu.propra18.q9512268.gui.util.Properties;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;

/**
 * Controller class for Manual.fxml view.
 * 
 * @author Christian Luetticke
 * @version 2.0
 */
public class ManualController implements Initializable {

	/**
	 * WebView for displaying the manual.html file.
	 */
	@FXML
	private WebView user_manual_webview;

	/**
	 * Loads the manual.html document into the
	 * {@link ManualController#user_manual_webview} WebView.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		final URL url = getClass().getResource(Properties.getString("MANUAL_HTML"));
		user_manual_webview.getEngine().load(url.toExternalForm());
	}

}
