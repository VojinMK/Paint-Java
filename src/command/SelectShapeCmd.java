package command;

import java.util.ArrayList;
import java.util.Stack;

import geometry.Shape;
import mvc.DrawingModel;

public class SelectShapeCmd implements Command {
	// private Shape shape;
	private ArrayList<Shape> selectedShapes;
	private Stack<Shape> tempSelectShapes = new Stack<Shape>();

	public SelectShapeCmd(ArrayList<Shape> selectedShapes) {
		this.selectedShapes = selectedShapes;
	}

	@Override
	public void execute() {
		// potencijalno problme sa indexom, zato onda treba uzeti index u unexecute i
		// upotrebiti ga ovde
		tempSelectShapes.peek().setSelected(true);
		selectedShapes.add(tempSelectShapes.pop());
	}

	@Override
	public void unexecute() {

		selectedShapes.get(selectedShapes.size() - 1).setSelected(false);
		tempSelectShapes.add(selectedShapes.get(selectedShapes.size() - 1));
		selectedShapes.remove(selectedShapes.size() - 1);

	}

}
