package command;

import geometry.Point;
import geometry.Line;

public class UpdateLineCmd implements Command {

	Line line;
	Line newState;
	Line original = new Line(new Point(), new Point());
	public String toString;

	public UpdateLineCmd(Line line, Line newState) {
		this.line = line;
		this.newState = newState;
		toString=line.toString()+":to"+newState.toString();
	}

	@Override
	public void execute() {
		
		line.clone(original);

		
		newState.clone(line);
	}

	@Override
	public void unexecute() {

		original.clone(line);
	}

	public String getToString() {
		return toString;
	}

}
