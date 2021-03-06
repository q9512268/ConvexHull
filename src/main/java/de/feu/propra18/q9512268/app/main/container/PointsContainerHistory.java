package de.feu.propra18.q9512268.app.main.container;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import de.feu.propra18.q9512268.app.main.objects.Point;

/**
 * This class which is realized as a singleton provides a history for the
 * {@link PointsContainer} instance.
 * <P>
 * It provides functionality to undo and redo actions and manages two stacks
 * to realize the history.
 * <P>
 * The functions provided by this class are called from {@link MainFacade}.
 * 
 * @author Christian Luetticke
 * @version 2.0
 * @see MainFacade
 * @see PointsContainer
 */
class PointsContainerHistory {

	/**
	 * Final singleton instance of the {@link PointsContainerHistory} class.
	 * <P>
	 * Gets created when accessed the first time.
	 */
	private static final PointsContainerHistory INSTANCE = new PointsContainerHistory();

	/**
	 * stack_visible represents the current state which is visible to the user.
	 */
	private Stack<byte[]> stack_visible;
	/**
	 * stack_backup represents a auxiliary structure for stack_visible to realize undo and redo
	 * functionality.
	 */
	private Stack<byte[]> stack_backup;

	/**
	 * Private constructor which can never be called directly.
	 * <P>
	 * Only instantiated when accessing {@link PointsContainerHistory#INSTANCE} for
	 * the first time.
	 * <P>
	 * Constructs the two different stacks to manage the history.
	 */
	private PointsContainerHistory() {
		stack_visible = new Stack<byte[]>();
		stack_backup = new Stack<byte[]>();
	}

	/**
	 * Get access to the {@link PointsContainerHistory#INSTANCE}.
	 * 
	 * @return The PointsContainerHistory instance.
	 */
	static PointsContainerHistory get_Instance() {
		return INSTANCE;
	}

	/**
	 * If this function is called, the passed points are converted into an byte
	 * array and added to {@link PointsContainerHistory#stack_visible}.
	 * <P>
	 * Auxiliary structure {@link PointsContainerHistory#stack_backup} gets cleared.
	 * 
	 * @see MainFacade#snapshot_AllPoints()
	 */
	void serialize_and_store_allPoints() {
		try {
			final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(new ArrayList<Point>(PointsContainer.get_Instance().get_PointsAll()));
			byte[] array = byteArrayOutputStream.toByteArray();

			stack_backup.clear();

			stack_visible.push(array);

			objectOutputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Called by {@link MainFacade#undo()} to undo a current action.
	 * <P>
	 * This means the top element of {@link PointsContainerHistory#stack_visible} is
	 * pushed onto auxiliary structure {@link PointsContainerHistory#stack_backup}.
	 * 
	 * @return List All points after undo function completed.
	 * @see MainFacade#undo()
	 */
	List<Point> undo() {
		if (stack_visible.size() > 0) {
			stack_backup.push(stack_visible.pop());
			return restorePoints();
		}
		return null;
	}

	/**
	 * Called by {@link MainFacade#undo()} to redo an undone action.
	 * <P>
	 * This means the top element of {@link PointsContainerHistory#stack_backup} is
	 * pushed back onto {@link PointsContainerHistory#stack_visible}.
	 * 
	 * @return List All points after redo function completed.
	 * @see MainFacade#redo()
	 */
	List<Point> redo() {
		if (stack_backup.size() > 0) {
			stack_visible.push(stack_backup.pop());
			return restorePoints();
		}
		return null;
	}

	/**
	 * Restores points from the peek element of
	 * {@link PointsContainerHistory#stack_visible}.
	 * 
	 * @return List The restored points.
	 */
	private List<Point> restorePoints() {
		List<Point> restored = new ArrayList<Point>();
		try {
			final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(stack_visible.peek());
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			restored = (List<Point>) objectInputStream.readObject();
			objectInputStream.close();
		} catch (Exception e) {
		}
		return restored;
	}

	/**
	 * Completely resets the current history.
	 * 
	 * @see MainFacade#clear_Container()
	 */
	public void clear_History() {
		stack_visible.clear();
		stack_backup.clear();
	}

}
