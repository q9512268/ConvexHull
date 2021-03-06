/**
 * Contains data structures in form of singleton containers holding the main
 * objects of the application.
 * <P>
 * {@link de.feu.propra18.q9512268.app.main.container.PointsContainer} stores
 * points.
 * <P>
 * {@link de.feu.propra18.q9512268.app.main.container.CircleContainer} stores
 * the largest included circle.
 * <P>
 * {@link de.feu.propra18.q9512268.app.main.container.HullContainer} stores the
 * convex hull.
 * <P>
 * * {@link de.feu.propra18.q9512268.app.main.container.PointsContainerHistory}
 * keeps track of
 * {@link de.feu.propra18.q9512268.app.main.container.PointsContainer} history
 * (necessary for undo/redo-functionality).
 * <p>
 * {@link de.feu.propra18.q9512268.app.main.container.MainFacade} acts as a main
 * access point to the containers and provides main functionality for
 * calculation.
 * 
 * @author Christian Luetticke
 * @version 2.0
 */
package de.feu.propra18.q9512268.app.main.container;