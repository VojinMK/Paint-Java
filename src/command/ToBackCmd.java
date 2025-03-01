package command;

import java.util.Collections;

import geometry.Shape;
import mvc.DrawingModel;

public class ToBackCmd implements Command{
	
	DrawingModel model;
	Shape shape;
	
	public ToBackCmd(DrawingModel model,Shape shape) {
		this.model=model;
		this.shape=shape;
	}

	@Override
	public void execute() {
		int i = model.getShapes().lastIndexOf(shape);
		Collections.swap(model.getShapes(), i-1, i);
		
	}

	@Override
	public void unexecute() {
		int i = model.getShapes().lastIndexOf(shape);
		Collections.swap(model.getShapes(), i+1, i);
		
	}

}
