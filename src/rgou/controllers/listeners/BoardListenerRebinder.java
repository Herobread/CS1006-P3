package rgou.controllers.listeners;

import java.util.List;

import javax.swing.event.ChangeListener;

import rgou.model.Board;

public class BoardListenerRebinder {
	public static Board rebind(Board oldBoard, Board newBoard) {
		List<ChangeListener> listeners = oldBoard.getListeners();
		newBoard.setListeners(listeners);

		List<ChangeListener> eventListeners = oldBoard.getListeners();
		newBoard.setEventListeners(eventListeners);

		return newBoard;
	}
}
