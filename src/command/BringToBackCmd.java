package command;

import java.util.Collections;

import geometry.Shape;
import mvc.DrawingModel;

public class BringToBackCmd implements Command {
	
	DrawingModel model;
	Shape shape;
	int indexOfShape;
	
	public BringToBackCmd(DrawingModel model, Shape shape) {
		this.model=model;
		this.shape=shape;
	}

	@Override
	public void execute() {
		indexOfShape=model.getShapes().lastIndexOf(shape);
		
		for(int i=indexOfShape;i>0;i--) {
			Collections.swap(model.getShapes(), i, i-1);
		}
		
	}

	@Override
	public void unexecute() {
		
		for(int a =indexOfShape , i = 0;a > 0;a--, i++) {
			Collections.swap(model.getShapes(), i, i+1);
		}
	}
    
}
