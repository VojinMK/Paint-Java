package command;

import geometry.Donut;
import geometry.Point;

public class UpdateDonutCmd implements Command {

	Donut donut;
	Donut newState;
	Donut original = new Donut(new Point(), 0, 0);
	public String toString;

	public UpdateDonutCmd(Donut donut, Donut newState) {
		this.donut = donut;
		this.newState = newState;
		toString = donut.toString() + ":to" + newState.toString();
	}

	@Override
	public void execute() {

		donut.clone(original);

		newState.clone(donut);
	}

	@Override
	public void unexecute() {

		original.clone(donut);
	}

	public String getToString() {
		return toString;
	}

}
