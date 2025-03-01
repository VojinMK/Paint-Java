package command;

import geometry.Circle;
import geometry.Point;

public class UpdateCircleCmd implements Command{
	
	Circle newState;
	Circle circle;
	Circle original=new Circle(new Point(),0);
	public String toString;

	public UpdateCircleCmd(Circle circle, Circle newState) {
		this.circle=circle;
		this.newState=newState;
		toString=circle.toString()+":to"+newState.toString();
	}
	
	@Override
	public void execute() {

        circle.clone(original);	

		newState.clone(circle);
	}

	@Override
	public void unexecute() {

		original.clone(circle);
	}

	public String getToString() {
		return toString;
	}

}
