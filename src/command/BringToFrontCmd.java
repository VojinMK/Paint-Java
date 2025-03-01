package command;

import java.util.Collections;

import geometry.Shape;
import mvc.DrawingModel;

public class BringToFrontCmd implements Command {
	
    DrawingModel model;
    Shape shape;
    int index;
    int steps;
    
    public BringToFrontCmd(DrawingModel model, Shape shape) {
    	this.model=model;
    	this.shape=shape;
    }
	
	@Override
	public void execute() {
		index=model.getShapes().lastIndexOf(shape);
		steps=model.getShapes().size()-1 - index;
		for(int i = steps , j = model.getShapes().lastIndexOf(shape);i>0;i--, j++) {
			Collections.swap(model.getShapes(), j, j+1);
		}
		
	}

	@Override
	public void unexecute() {
		for(int a = steps, j = model.getShapes().size()-1;a>0;a--, j--) {
			Collections.swap(model.getShapes(), j, j-1);
		}
	}

}
