package observer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import mvc.DrawingFrame;

public class ButtonUpdate implements PropertyChangeListener {

	DrawingFrame frame;
	private boolean select;
	private boolean modify;
	private boolean delete;
	private boolean undo;
	private boolean redo;
	private boolean backButtons;
	private boolean frontButtons;

	public ButtonUpdate(DrawingFrame frame) {
		this.frame = frame;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("select")) {
			this.select = (boolean) evt.getNewValue();
			selectButtonSwitcher(select);
		} else if (evt.getPropertyName().equals("modify")) {
			this.modify = (boolean) evt.getNewValue();
			modifyButtonSwitcher(modify);
		} else if (evt.getPropertyName().equals("delete")) {
			this.delete = (boolean) evt.getNewValue();
			deleteButtonSwitcher(delete);
		} else if (evt.getPropertyName().equals("undo")) {
			this.undo = (boolean) evt.getNewValue();
			undoButtonSwitcher(undo);
		} else if (evt.getPropertyName().equals("redo")) {
			this.redo = (boolean) evt.getNewValue();
			redoButtonSwitcher(redo);
		} else if (evt.getPropertyName().equals("backButtons")) {
			this.backButtons = (boolean) evt.getNewValue();
			backButtonsSwitcher(backButtons);
		} else if (evt.getPropertyName().equals("frontButtons")) {
			this.frontButtons = (boolean) evt.getNewValue();
			frontButtonsSwitcher(frontButtons);
		}
	}

	public void selectButtonSwitcher(boolean value) {
		frame.getTglbtnSelect().setEnabled(value);
	}

	public void modifyButtonSwitcher(boolean value) {
		frame.getBtnModify().setEnabled(value);
	}

	public void deleteButtonSwitcher(boolean value) {
		frame.getBtnDelete().setEnabled(value);
	}

	public void undoButtonSwitcher(boolean value) {
		frame.getBtnUndo().setEnabled(value);
	}

	public void redoButtonSwitcher(boolean value) {
		frame.getBtnRedo().setEnabled(value);
	}

	public void backButtonsSwitcher(boolean value) {
		frame.getBtnToBack().setEnabled(value);
		frame.getBtnBringToBack().setEnabled(value);
	}

	public void frontButtonsSwitcher(boolean value) {
		frame.getBtnToFront().setEnabled(value);
		frame.getBtnBringToFront().setEnabled(value);
	}

}
