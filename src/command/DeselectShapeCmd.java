package command;

import java.util.ArrayList;
import java.util.Stack;

import geometry.Shape;

public class DeselectShapeCmd implements Command {

	private ArrayList<Shape> selectedShapes;
	private Stack<Shape> tempDeselectedShapes = new Stack<Shape>();
	private Stack<Integer> index = new Stack<Integer>();
	public Shape shapeToAddInDeselectUndo;

	public DeselectShapeCmd(ArrayList<Shape> selectedShapes) {
		this.selectedShapes = selectedShapes;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	public void execute1(Shape shape) {

		shape.setSelected(false);
		index.add(selectedShapes.indexOf(shape));
		tempDeselectedShapes.add(shape);
		selectedShapes.remove(shape);
	}

	@Override
	public void unexecute() {
		tempDeselectedShapes.peek().setSelected(true);
		shapeToAddInDeselectUndo = tempDeselectedShapes.peek();
		selectedShapes.add(index.pop(), tempDeselectedShapes.pop());
	}

	public Shape getShapeToAddInDeselectUndo() {
		return shapeToAddInDeselectUndo;
	}

}
