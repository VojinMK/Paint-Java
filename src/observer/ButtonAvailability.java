package observer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ButtonAvailability {
	
	private boolean select;
	private boolean modify;
	private boolean delete;
	private boolean undo;
	private boolean redo;
	private boolean backButtons;
	private boolean frontButtons;

	private PropertyChangeSupport propertyChangeSupport;
	
	public ButtonAvailability() {
		propertyChangeSupport = new PropertyChangeSupport(this);
	}
	
	public void setSelectButton(boolean select) {
		propertyChangeSupport.firePropertyChange("select", this.select, select);
		this.select = select;
	}
	
	public void setModifyButton(boolean modify) {
		propertyChangeSupport.firePropertyChange("modify", this.modify, modify);
		this.modify = modify;
	}
	
	public void setDeleteButton(boolean delete) {
		propertyChangeSupport.firePropertyChange("delete", this.delete, delete);
		this.delete = delete;
	}
	
	public void setUndoButton(boolean undo) {
		propertyChangeSupport.firePropertyChange("undo", this.undo, undo);
		this.undo = undo;
	}
	
	public void setRedoButton(boolean redo) {
		propertyChangeSupport.firePropertyChange("redo", this.redo, redo);
		this.redo = redo;
	}
	
	public void setBackButtons(boolean backButtons) {
		propertyChangeSupport.firePropertyChange("backButtons", this.backButtons, backButtons);
		this.backButtons = backButtons;
	}
	
	public void setFrontButtons(boolean frontButtons) {
		propertyChangeSupport.firePropertyChange("frontButtons", this.frontButtons, frontButtons);
		this.frontButtons = frontButtons;
	}
	
	public void addListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}
	public void removeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
	
}
