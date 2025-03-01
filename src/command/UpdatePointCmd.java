package command;

import geometry.Point;

public class UpdatePointCmd implements Command {

	private Point point;
	private Point newState;
	private Point original=new Point();
	public String toString;
	
	public UpdatePointCmd(Point point, Point newState) {
		this.point=point;
		this.newState=newState;
	    toString=point.toString()+":to"+newState.toString();
	}
	
	@Override
	public void execute() {

		point.clone(original);

		
		newState.clone(point);
	}

	@Override
	public void unexecute() {

		original.clone(point);
	}

	public String toString() {
		return toString;
	}
	

}
