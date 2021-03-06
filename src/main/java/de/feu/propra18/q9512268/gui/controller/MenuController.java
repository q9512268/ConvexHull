package de.feu.propra18.q9512268.gui.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.feu.propra18.q9512268.app.main.container.MainFacade;
import de.feu.propra18.q9512268.gui.util.Properties;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller class for MenuBar.fxml view.
 * 
 * @author Christian Luetticke
 * @version 2.0
 */
public class MenuController implements Initializable {

	/**
	 * Reference to the {@link MainFacade} and its functionality.
	 */
	private MainFacade FACADE;

	/**
	 * Reference to the {@link MainController} and its functionality.
	 */
	private MainController mainController;

	/**
	 * The MenuBar.
	 */
	@FXML
	private MenuBar menu_bar;
	/**
	 * The MenuItem for 'Open' action.
	 */
	@FXML
	private MenuItem menu_file_open;
	/**
	 * The MenuItem for 'Save' action.
	 */
	@FXML
	private MenuItem menu_file_save;
	/**
	 * The MenuItem for 'Save As' action.
	 */
	@FXML
	private MenuItem menu_file_save_as;
	/**
	 * The MenuItem for 'New' action.
	 */
	@FXML
	private MenuItem menu_file_new;
	/**
	 * The MenuItem for 'Help' action.
	 */
	@FXML
	private MenuItem menu_help_manual;

	/**
	 * String representing the selected path from the last save operation.
	 */
	private static String savedPath;

	/**
	 * Reference to the {@link FileChooser} and its functionality.
	 */
	private FileChooser fileChooser;

	/**
	 * Initializes functionality of menu bar items.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		FACADE = MainFacade.get_Instance();
		fileChooser = FACADE.get_FileChooser();

		initNew();
		initOpen();
		initSave();
		initSaveAs();
		initManual();
	}

	/**
	 * If user clicks on 'New' Menu Item all points are removed by calling
	 * {@link MainFacade#clear_Container()}.
	 * <P>
	 * To make sure the polygon is removed correctly
	 * {@link MainController#clearPolygon()} is called and the polygon is newly
	 * created by {@link MainController#initPolygon()}.
	 * <P>
	 * To make sure the circle is removed correctly
	 * {@link MainController#clearCircle()} is called and the circle is newly
	 * created by {@link MainController#initCircle()}.
	 * 
	 */
	private void initNew() {
		menu_file_new.setOnAction((e) -> {
			menu_file_save.setDisable(true);
			savedPath = null;
			mainController.clearPolygon();
			mainController.initPolygon();
			mainController.clearCircle();
			mainController.initCircle();
			FACADE.clear_Container();

		});
	}

	/**
	 * If user clicks on 'Open' the {@link FileChooser} instance is displayed.
	 * <P>
	 * If the user selects a file to import, the current points are removed, all
	 * points are then imported by calling
	 * {@link MainFacade#add_PointsFromFile(String)}.
	 * <P>
	 * The convex hull and the largest included Circle (GEK) are calculated.
	 */
	private void initOpen() {
		menu_file_open.setOnAction((e) -> {
			File file = fileChooser.showOpenDialog(menu_bar.getScene().getWindow());
			if (file != null) {
				FACADE.clear_Container();
				FACADE.add_PointsFromFile(file.getAbsolutePath());
				FACADE.calculate_PointsConvexHull();
				FACADE.calculate_GEK();
				menu_file_save.setDisable(false);
				savedPath = file.getAbsolutePath();
			}
		});
	}

	/**
	 * Initializes 'Save' Action.
	 * <P>
	 * If the current points haven't been saved before this menu item is disabled.
	 * 
	 */
	private void initSave() {
		menu_file_save.setDisable(true);
		menu_file_save.setOnAction((e) -> {
			FACADE.save_PointsToFile(savedPath);
		});
	}

	/**
	 * Initializes functionality of the 'Save As' menu item.
	 * <P>
	 * The {@link MenuController#fileChooser} instance is displayed to the user and
	 * the current points can be exported be passing the select file path to
	 * {@link MainFacade#save_PointsToFile(String)}.
	 * 
	 */
	private void initSaveAs() {
		menu_file_save_as.setOnAction((e) -> {
			fileChooser.setTitle(Properties.getString("SAVE_POINTS"));
			fileChooser.setInitialFileName(Properties.getString("INITIAL_POINTS_FILE_NAME"));
			File file = fileChooser.showSaveDialog(menu_bar.getScene().getWindow());
			if (file != null) {
				FACADE.save_PointsToFile(file.getAbsolutePath());
				menu_file_save.setDisable(false);
				savedPath = file.getAbsolutePath();

			}
		});
	}

	/**
	 * Initializes functionality that is triggered once the user clicks on the
	 * 'Manual' menu item.
	 * <P>
	 * To make sure the window can only be opened once its modality is set.
	 * <P>
	 * A new windows is displayed by creating a new {@link Scene} based on
	 * Manual.fxml and its corresponding {@link ManualController}.
	 */
	private void initManual() {
		menu_help_manual.setOnAction((e) -> {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Properties.getString("MANUAL_FXML")));
				fxmlLoader.setController(new ManualController());

				Stage stage = new Stage();
				Scene scene = new Scene(fxmlLoader.load(), 600, 700);
				stage.setScene(scene);
				stage.setTitle(Properties.getString("MANUAL"));
				stage.initModality(Modality.APPLICATION_MODAL); 
				stage.show();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
	}

	/**
	 * Method to initialize the reference to the {@link MainController} instance.
	 * 
	 * @param mainController
	 *            Reference to MainController instance.
	 */
	public void setMainController(MainController mainController) {
		this.mainController = mainController;

	}
}
