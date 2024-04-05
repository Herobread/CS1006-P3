package rgou.controllers.listeners;

import java.util.List;

import javax.swing.event.ChangeListener;

import rgou.model.Board;

/**
 * Utility class for rebinding listeners from an old board to a new board.
 */
public class BoardListenerRebinder {
	/**
	 * Rebinds listeners from the old board to the new board.
	 * 
	 * @param oldBoard The old board.
	 * @param newBoard The new board.
	 * @return The new board with listeners rebound.
	 */
	public static Board rebind(Board oldBoard, Board newBoard) {
		List<ChangeListener> listeners = oldBoard.getListeners();
		newBoard.setListeners(listeners);

		List<ChangeListener> eventListeners = oldBoard.getEventListeners();
		newBoard.setEventListeners(eventListeners);

		return newBoard;
	}
}
