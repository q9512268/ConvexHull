package de.feu.propra18.q9512268.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.feu.propra18.q9512268.app.main.container.MainFacade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;

/**
 * Controller class for ToolBar.xml view.
 * 
 * @author Christian Luetticke
 * @version 2.0
 */
public class ToolBarController implements Initializable {

	/**
	 * Reference to the {@link MainFacade} and its functionality.
	 */
	private MainFacade FACADE;

	/**
	 * Reference to the {@link MainController} pane and its functionality.
	 */
	private Pane pane;

	/**
	 * The ToolBar.
	 */
	@FXML
	private ToolBar tool_bar;

	/**
	 * Button to undo actions.
	 */
	@FXML
	private Button undo;

	/**
	 * Button to redo actions.
	 */
	@FXML
	private Button redo;

	/**
	 * Button to create 10 random points.
	 */
	@FXML
	private Button create_points_10;
	/**
	 * Button to create 50 random points.
	 */
	@FXML
	private Button create_points_50;
	/**
	 * Button to create 100 random points.
	 */
	@FXML
	private Button create_points_100;
	/**
	 * Button to create 500 random points.
	 */
	@FXML
	private Button create_points_500;
	/**
	 * Button to create 1000 random points.
	 */
	@FXML
	private Button create_points_1000;

	/**
	 * Initializes undo and redo button actions and functionality for different
	 * 'random points' buttons.
	 * <P>
	 * Once the user clicks on one of these buttons random points are created and
	 * displayed within the visible pane.
	 * 
	 * @see MainFacade#undo()
	 * @see MainFacade#redo()
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		FACADE = MainFacade.get_Instance();

		undo.setOnAction((e) -> FACADE.undo());
		redo.setOnAction((e) -> FACADE.redo());

		create_points_10.setOnAction((e) -> createRandomButtonAction(10));
		create_points_100.setOnAction((e) -> createRandomButtonAction(100));
		create_points_1000.setOnAction((e) -> createRandomButtonAction(1000));
		create_points_50.setOnAction((e) -> createRandomButtonAction(50));
		create_points_500.setOnAction((e) -> createRandomButtonAction(500));

	}

	/**
	 * Initializes the action which occurs once a 'random points' button is clicked.
	 * <P>
	 * {@link MainFacade#add_PointsRandom(int, double, double)} is called, all
	 * convex hull points are removed and the convex hull is recalculated.
	 * 
	 * @param amount
	 *            How many random points should be created. void
	 */
	private void createRandomButtonAction(int amount) {
		FACADE.add_PointsRandom(amount, pane.getWidth(), pane.getHeight());
		FACADE.clear_ConvexHullPoints();
		FACADE.calculate_PointsConvexHull();
		FACADE.calculate_GEK();
	}

	/**
	 * Sets the reference to the main pane.
	 * 
	 * @param pane
	 *            Reference to the main pane.
	 */
	public void setPane(Pane pane) {
		this.pane = pane;
	}

}
