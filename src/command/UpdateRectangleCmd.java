package command;

import geometry.Point;
import geometry.Rectangle;

public class UpdateRectangleCmd implements Command {

	Rectangle rectangle;
	Rectangle newState;
	Rectangle original = new Rectangle(new Point(), 0, 0);
	public String toString;

	public UpdateRectangleCmd(Rectangle rectangle, Rectangle newState) {
		this.rectangle = rectangle;
		this.newState = newState;
		toString = rectangle.toString() + ":to" + newState.toString();
	}

	@Override
	public void execute() {

		rectangle.clone(original);

		newState.clone(rectangle);
	}

	@Override
	public void unexecute() {

		original.clone(rectangle);
	}

	public String getToString() {
		return toString;
	}

}
