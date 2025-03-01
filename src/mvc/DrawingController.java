package mvc;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import command.AddShapeCmd;
import command.BringToBackCmd;
import command.BringToFrontCmd;
import command.Command;
import command.DeselectShapeCmd;
import command.RemoveShapeCmd;
import command.SelectShapeCmd;
import command.ToBackCmd;
import command.ToFrontCmd;
import command.UpdateCircleCmd;
import command.UpdateDonutCmd;
import command.UpdateHexagonCmd;
import command.UpdateLineCmd;
import command.UpdatePointCmd;
import command.UpdateRectangleCmd;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import adapter.HexagonAdapter;
import gui.DlgCircle;
import gui.DlgDonut;
import gui.DlgHexagon;
import gui.DlgLine;
import gui.DlgPoint;
import gui.DlgRectangle;
import hexagon.Hexagon;
import observer.ButtonAvailability;
import observer.ButtonUpdate;
import strategy.SaveLoadBin;
import strategy.SaveLoadTxt;
import strategy.SaveManager;

public class DrawingController {

	DrawingModel model;
	DrawingFrame frame;

	private Shape selectedShape;
	Shape shape1 = null;
	Shape shape2 = null;
	Shape shape3 = null;
	public Point startPoint;
	public ArrayList<String> commands = new ArrayList<String>();
	public ArrayList<String> tempCommands = new ArrayList<String>();
	public ArrayList<Shape> tempShapes = new ArrayList<Shape>();
	public ArrayList<Shape> removedShapes = new ArrayList<Shape>();
	public ArrayList<Shape> tempRemovedShapes = new ArrayList<Shape>();
	public ArrayList<Integer> index = new ArrayList<Integer>();
	public Stack<Shape> modifiedShapes = new Stack<Shape>();
	public ArrayList<Shape> selectedShapes = new ArrayList<Shape>();
	public Stack<Shape> tempSelectShapes = new Stack<Shape>();
	public ArrayList<Shape> tempDeselectedShapes = new ArrayList<Shape>();
	public Stack<Shape> tempModifiedShapes = new Stack<Shape>();
	public Stack<Command> updateCmdStack = new Stack<Command>(); // pokusaj
	public Stack<Command> tempUpdateCmdStack = new Stack<Command>();
	private SelectShapeCmd selectShapesCmd = new SelectShapeCmd(selectedShapes);
	private DeselectShapeCmd deselectShapesCmd = new DeselectShapeCmd(selectedShapes);

	public SaveManager saveLoadTxt;
	public SaveManager saveLoadBin;
	public SaveManager saveLoadBinLoad;
	public SaveManager saveLoadTxtLoad;

	public Stack<Command> undoRedoBackFront = new Stack<Command>();
	public Stack<Command> tempUndoRedoBackFront = new Stack<Command>();

	private UpdatePointCmd updatePointCmd;
	private UpdateLineCmd updateLineCmd;
	private UpdateCircleCmd updateCircleCmd;
	private UpdateDonutCmd updateDonutCmd;
	private UpdateRectangleCmd updateRectangleCmd;
	private UpdateHexagonCmd updateHexagonCmd;
	private String lastCommand;
	boolean check = false;
	private ButtonAvailability buttonAvailability = new ButtonAvailability();
	ButtonUpdate buttonUpdate;

	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		this.buttonUpdate = new ButtonUpdate(frame);
		buttonAvailability.addListener(buttonUpdate);
	}

	public void selectButtonAvailability() {
		if (model.getShapes().size() > 0) {
			buttonAvailability.setSelectButton(true);
		} else {
			buttonAvailability.setSelectButton(false);
			frame.getTglbtnSelect().setSelected(false);
		}
	}

	public void modifyButtonAvailability() {
		if (selectedShapes.size() == 1) {
			buttonAvailability.setModifyButton(true);
		} else {
			buttonAvailability.setModifyButton(false);
		}
	}

	public void deleteButtonAvailability() {
		if (selectedShapes.size() > 0) {
			buttonAvailability.setDeleteButton(true);
		} else {
			buttonAvailability.setDeleteButton(false);
		}
	}

	public void undoButtonAvailability() {
		if (commands.size() > 0) {
			buttonAvailability.setUndoButton(true);
		} else {
			buttonAvailability.setUndoButton(false);
		}
	}

	public void redoButtonAvailability() {
		if (tempCommands.size() > 0) {
			buttonAvailability.setRedoButton(true);
		} else {
			buttonAvailability.setRedoButton(false);
		}
	}

	public void backButtonsAvailability() {
		if (selectedShapes.size() == 1 && model.getShapes().lastIndexOf(getSelectedShape()) != 0) {
			buttonAvailability.setBackButtons(true);
		} else {
			buttonAvailability.setBackButtons(false);
		}
	}

	public void frontButtonsAvailability() {
		if (selectedShapes.size() == 1
				&& model.getShapes().lastIndexOf(getSelectedShape()) != model.getShapes().size() - 1) {
			buttonAvailability.setFrontButtons(true);
		} else {
			buttonAvailability.setFrontButtons(false);
		}
	}

	protected void delete() {

		ArrayList<Shape> tempSelectedShapes = new ArrayList<Shape>();
		for (int i = 0; i <= selectedShapes.size() - 1; i++) {
			tempSelectedShapes.add(selectedShapes.get(i));
		}
		selectedShapes.clear();
		if (tempSelectedShapes.size() != 0) {
			int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item?",
					"Warning message", JOptionPane.YES_NO_OPTION);
			if (selectedOption == JOptionPane.YES_OPTION) {
				for (int i = tempSelectedShapes.size() - 1; i >= 0; i--) {
					selectedShape = tempSelectedShapes.get(i);
					index.add(model.getShapes().indexOf(selectedShape));
					model.getShapes().remove(selectedShape);
					removedShapes.add(selectedShape);
					commands.add("Deleted" + selectedShape.toString());
					lastCommand = "Deleted" + selectedShape.toString();
					frame.getTxtCommands().append("Deleted" + selectedShape.toString() + '\n');
					tempSelectedShapes.remove(selectedShape);

				}
				disableRedo();
			} else if (selectedOption == JOptionPane.NO_OPTION) {

			}

		} else {
			JOptionPane.showMessageDialog(null, "Please, select item you want to delete.", "Message",
					JOptionPane.WARNING_MESSAGE);
		}
		checkButtonsAvailability();
		frame.view.repaint();
	}

	// modifikacija
	protected void modify() {
		int count = 0;
		Shape selectedShape = null;

		for (int i = 0; i < selectedShapes.size(); i++) {
			selectedShape = selectedShapes.get(i);
			count++;
		}

		if (count == 1 && selectedShape != null) {

			if (selectedShape instanceof Point) {

				Point p = (Point) selectedShape;
				Point newPoint = new Point();
				DlgPoint dialog = new DlgPoint();

				dialog.getTxtX().setText("" + Integer.toString(p.getX()));
				dialog.getTxtY().setText("" + Integer.toString(p.getY()));
				dialog.getBtnColorPoint().setBackground(p.getColor());
				dialog.setVisible(true);

				if (dialog.isConfirm()) {
					newPoint.setX(Integer.parseInt(dialog.getTxtX().getText()));
					newPoint.setY(Integer.parseInt(dialog.getTxtY().getText()));
					newPoint.setColor(dialog.getBtnColorPoint().getBackground());

					updatePointCmd = new UpdatePointCmd(p, newPoint);
					updateCmdStack.push(updatePointCmd);
					updatePointCmd.execute();
					modifiedShapes.push(p);
					commands.add("Modified" + updatePointCmd.toString());
					lastCommand = "Modified" + updatePointCmd.toString();
					disableRedo();
					checkButtonsAvailability();
					frame.getTxtCommands().append("Modified" + updatePointCmd.toString() + '\n');
					frame.repaint();
				} else {

					frame.repaint();
				}

			} else if (selectedShape instanceof Line) {

				Line line = (Line) selectedShape;

				Line newLine = new Line(new Point(), new Point());
				DlgLine dialog = new DlgLine();

				dialog.getTxtXSP().setText("" + Integer.toString(line.getStartPoint().getX()));
				dialog.getTxtYSP().setText("" + Integer.toString(line.getStartPoint().getY()));
				dialog.getTxtXEP().setText("" + Integer.toString(line.getEndPoint().getX()));
				dialog.getTxtYEP().setText("" + Integer.toString(line.getEndPoint().getY()));
				dialog.getBtnColorLine().setBackground(line.getColor());

				dialog.setVisible(true);

				if (dialog.isConfirm()) {
					newLine.getStartPoint().setX(Integer.parseInt(dialog.getTxtXSP().getText()));
					newLine.getStartPoint().setY(Integer.parseInt(dialog.getTxtYSP().getText()));
					newLine.getEndPoint().setX(Integer.parseInt(dialog.getTxtXEP().getText()));
					newLine.getEndPoint().setY(Integer.parseInt(dialog.getTxtYEP().getText()));
					newLine.setColor(dialog.getBtnColorLine().getBackground());
					updateLineCmd = new UpdateLineCmd(line, newLine);
					updateCmdStack.push(updateLineCmd);
					updateLineCmd.execute();
					modifiedShapes.push(line);
					commands.add("Modified" + updateLineCmd.getToString());
					lastCommand = "Modified" + updateLineCmd.getToString();
					disableRedo();
					checkButtonsAvailability();
					frame.getTxtCommands().append("Modified" + updateLineCmd.getToString() + '\n');
					frame.repaint();
				} else {

					frame.repaint();
				}

			} else if (selectedShape instanceof Donut) {

				Donut donut = (Donut) selectedShape;
				Donut newDonut = new Donut(new Point(), 0, 0);
				DlgDonut dialog = new DlgDonut();

				dialog.getTxtX().setText("" + Integer.toString(donut.getCenter().getX()));
				dialog.getTxtY().setText("" + Integer.toString(donut.getCenter().getY()));
				dialog.getTxtR().setText("" + Integer.toString(donut.getRadius()));
				dialog.getTxtInnerR().setText("" + Integer.toString(donut.getInnerRadius()));
				dialog.getBtnInnerColorDonut().setBackground(donut.getInnerColor());
				dialog.getBtnOutlineColorDonut().setBackground(donut.getColor());
				dialog.setModal(true);
				dialog.setVisible(true);

				if (dialog.isConfirm()) {
					newDonut.getCenter().setX(Integer.parseInt(dialog.getTxtX().getText()));
					newDonut.getCenter().setY(Integer.parseInt(dialog.getTxtY().getText()));
					newDonut.setInnerRadius(Integer.parseInt(dialog.getTxtInnerR().getText()));
					try {
						newDonut.setRadius(Integer.parseInt(dialog.getTxtR().getText()));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					newDonut.setInnerColor(dialog.getBtnInnerColorDonut().getBackground());
					newDonut.setColor(dialog.getBtnOutlineColorDonut().getBackground());
					updateDonutCmd = new UpdateDonutCmd(donut, newDonut);
					updateCmdStack.push(updateDonutCmd);
					updateDonutCmd.execute();
					modifiedShapes.push(donut);
					commands.add("Modified" + updateDonutCmd.getToString());
					lastCommand = "Modified" + updateDonutCmd.getToString();
					disableRedo();
					checkButtonsAvailability();
					frame.getTxtCommands().append("Modified" + updateDonutCmd.getToString() + '\n');
					frame.repaint();
				} else {

					frame.repaint();
				}
			} else if (selectedShape instanceof Circle && (selectedShape instanceof Donut) == false) {

				Circle circle = (Circle) selectedShape;
				Circle newCircle = new Circle(new Point(), 0);
				DlgCircle dialog = new DlgCircle();

				dialog.getTxtX().setText("" + Integer.toString(circle.getCenter().getX()));
				dialog.getTxtY().setText("" + Integer.toString(circle.getCenter().getY()));
				dialog.getTxtR().setText("" + Integer.toString(circle.getRadius()));
				dialog.getBtnInnerColorCircle().setBackground(circle.getInnerColor());
				dialog.getBtnOutlineColorCircle().setBackground(circle.getColor());

				dialog.setVisible(true);
				dialog.setModal(true);

				if (dialog.isConfirm()) {
					newCircle.getCenter().setX(Integer.parseInt(dialog.getTxtX().getText()));
					newCircle.getCenter().setY(Integer.parseInt(dialog.getTxtY().getText()));
					try {
						newCircle.setRadius(Integer.parseInt(dialog.getTxtR().getText()));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					newCircle.setInnerColor(dialog.getBtnInnerColorCircle().getBackground());
					newCircle.setColor(dialog.getBtnOutlineColorCircle().getBackground());
					updateCircleCmd = new UpdateCircleCmd(circle, newCircle);
					updateCmdStack.push(updateCircleCmd);
					updateCircleCmd.execute();
					modifiedShapes.push(circle);
					commands.add("Modified" + updateCircleCmd.getToString());
					lastCommand = "Modified" + updateCircleCmd.getToString();
					disableRedo();
					checkButtonsAvailability();
					frame.getTxtCommands().append("Modified" + updateCircleCmd.getToString() + '\n');
					frame.repaint();
				} else {

					frame.repaint();
				}

			} else if (selectedShape instanceof Rectangle) {

				Rectangle rect = (Rectangle) selectedShape;
				Rectangle newRect = new Rectangle(new Point(), 0, 0);
				DlgRectangle dialog = new DlgRectangle();

				dialog.getTxtX().setText("" + Integer.toString(rect.getUpperLeftPoint().getX()));
				dialog.getTxtY().setText("" + Integer.toString(rect.getUpperLeftPoint().getY()));
				dialog.getTxtHeight().setText("" + Integer.toString(rect.getHeight()));
				dialog.getTxtWidth().setText("" + Integer.toString(rect.getWidth()));
				dialog.getBtnInnerColorRect().setBackground(rect.getInnerColor());
				dialog.getBtnOutlineColorRect().setBackground(rect.getColor());
				dialog.setModal(true);
				dialog.setVisible(true);

				if (dialog.isConfirm()) {
					newRect.getUpperLeftPoint().setX(Integer.parseInt(dialog.getTxtX().getText()));
					newRect.getUpperLeftPoint().setY(Integer.parseInt(dialog.getTxtY().getText()));
					newRect.setHeight(Integer.parseInt(dialog.getTxtHeight().getText()));
					newRect.setWidth(Integer.parseInt(dialog.getTxtWidth().getText()));
					newRect.setInnerColor(dialog.getBtnInnerColorRect().getBackground());
					newRect.setColor(dialog.getBtnOutlineColorRect().getBackground());
					updateRectangleCmd = new UpdateRectangleCmd(rect, newRect);
					updateCmdStack.push(updateRectangleCmd);
					updateRectangleCmd.execute();
					modifiedShapes.push(rect);
					commands.add("Modified" + updateRectangleCmd.getToString());
					lastCommand = "Modified" + updateRectangleCmd.getToString();
					disableRedo();
					checkButtonsAvailability();
					frame.getTxtCommands().append("Modified" + updateRectangleCmd.getToString() + '\n');
					frame.repaint();
				} else {

					frame.repaint();
				}
			} else if (selectedShape instanceof HexagonAdapter) {
				HexagonAdapter hexagon = (HexagonAdapter) selectedShape;
				HexagonAdapter newHexagon = new HexagonAdapter(new Hexagon(0, 0, 0));
				DlgHexagon dialog = new DlgHexagon();

				dialog.getTxtX().setText("" + Integer.toString(hexagon.getCenterX()));
				dialog.getTxtY().setText("" + Integer.toString(hexagon.getCenterY()));
				dialog.getTxtR().setText("" + Integer.toString(hexagon.getRadius()));
				dialog.getBtnInnerColorHexagon().setBackground(hexagon.getInnerColor());
				dialog.getBtnOutlineColorHexagon().setBackground(hexagon.getColor());

				dialog.setVisible(true);
				dialog.setModal(true);

				if (dialog.isConfirm()) {
					newHexagon.setCenterX(Integer.parseInt(dialog.getTxtX().getText()));
					newHexagon.setCenterY(Integer.parseInt(dialog.getTxtY().getText()));
					try {
						newHexagon.setRadius(Integer.parseInt(dialog.getTxtR().getText()));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					newHexagon.setInnerColor(dialog.getBtnInnerColorHexagon().getBackground());
					newHexagon.setColor(dialog.getBtnOutlineColorHexagon().getBackground());
					updateHexagonCmd = new UpdateHexagonCmd(hexagon, newHexagon);
					updateCmdStack.push(updateHexagonCmd);
					updateHexagonCmd.execute();
					modifiedShapes.push(hexagon);
					commands.add("Modified" + updateHexagonCmd.getToString());
					lastCommand = "Modified" + updateHexagonCmd.getToString();
					disableRedo();
					checkButtonsAvailability();
					frame.getTxtCommands().append("Modified" + updateHexagonCmd.getToString() + '\n');
					frame.repaint();
				} else {

					frame.repaint();
				}
			}

		} else if (count > 1) {
			JOptionPane.showMessageDialog(null, "Please, select only one shape!", "Message",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Please, select what you want to modify!", "Message",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// dodavanje elemenata

	protected void drawElement(int x, int y) {
		Shape newShape = null;
		Point click = new Point(x, y);

		if (frame.getTglbtnPoint().isSelected()) {
			newShape = new Point(click.getX(), click.getY(), false, frame.getBtnOutlineColor().getBackground());
			model.shapes.add(newShape);
			commands.add("Added" + newShape.toString());
			lastCommand = "Added" + newShape.toString();
			frame.getTxtCommands().append("Added" + newShape.toString() + '\n');
			startPoint = null;
			disableRedo();
			checkButtonsAvailability();
		} else if (frame.getTglbtnLine().isSelected()) {

			if (startPoint == null) {
				startPoint = new Point(x, y);

			} else {
				newShape = new Line(startPoint, new Point(x, y), false, frame.getBtnOutlineColor().getBackground());
				model.shapes.add(newShape);
				commands.add("Added" + newShape.toString());
				lastCommand = "Added" + newShape.toString();
				frame.getTxtCommands().append("Added" + newShape.toString() + '\n');
				startPoint = null;
				disableRedo();
				checkButtonsAvailability();
			}

		} else if (frame.getTglbtnRectangle().isSelected()) {

			DlgRectangle dialog = new DlgRectangle();
			dialog.setModal(true);
			dialog.getTxtX().setText("" + Integer.toString(x));
			dialog.getTxtX().setEditable(false);
			dialog.getTxtY().setText("" + Integer.toString(y));
			dialog.getTxtY().setEditable(false);
			dialog.getBtnOutlineColorRect().setBackground(frame.getBtnOutlineColor().getBackground());
			dialog.getBtnInnerColorRect().setBackground(frame.getBtnInnerColor().getBackground());
			dialog.setVisible(true);

			if (dialog.isConfirm()) {
				newShape = dialog.getRect();
				model.shapes.add(newShape);
				commands.add("Added" + newShape.toString());
				lastCommand = "Added" + newShape.toString();
				frame.getTxtCommands().append("Added" + newShape.toString() + '\n');
				startPoint = null;
				disableRedo();
				checkButtonsAvailability();

			}

		} else if (frame.getTglbtnCircle().isSelected()) {
			DlgCircle dialog = new DlgCircle();

			dialog.getTxtX().setText(Integer.toString(click.getX()));
			dialog.getTxtX().setEditable(false);
			dialog.getTxtY().setText(Integer.toString(click.getY()));
			dialog.getTxtY().setEditable(false);
			dialog.getBtnOutlineColorCircle().setBackground(frame.getBtnOutlineColor().getBackground());
			dialog.getBtnInnerColorCircle().setBackground(frame.getBtnInnerColor().getBackground());
			dialog.setVisible(true);

			if (dialog.isConfirm()) {
				newShape = dialog.getCircle();
				model.shapes.add(newShape);
				commands.add("Added" + newShape.toString());
				lastCommand = "Added" + newShape.toString();
				frame.getTxtCommands().append("Added" + newShape.toString() + '\n');
				startPoint = null;
				disableRedo();
				checkButtonsAvailability();
			}
		} else if (frame.getTglbtnDonut().isSelected()) {
			DlgDonut dialog = new DlgDonut();

			dialog.getTxtX().setText(Integer.toString(click.getX()));
			dialog.getTxtX().setEditable(false);
			dialog.getTxtY().setText(Integer.toString(click.getY()));
			dialog.getTxtY().setEditable(false);
			dialog.getBtnOutlineColorDonut().setBackground(frame.getBtnOutlineColor().getBackground());
			dialog.getBtnInnerColorDonut().setBackground(frame.getBtnInnerColor().getBackground());
			dialog.setVisible(true);

			if (dialog.isConfirm()) {
				newShape = dialog.getDonut();
				model.shapes.add(newShape);
				commands.add("Added" + newShape.toString());
				lastCommand = "Added" + newShape.toString();
				frame.getTxtCommands().append("Added" + newShape.toString() + '\n');
				startPoint = null;
				disableRedo();
				checkButtonsAvailability();
			}

		} else if (frame.getTglbtnHexagon().isSelected()) {
			DlgHexagon dialog = new DlgHexagon();

			dialog.getTxtX().setText(Integer.toString(click.getX()));
			dialog.getTxtX().setEditable(false);
			dialog.getTxtY().setText(Integer.toString(click.getY()));
			dialog.getTxtY().setEditable(false);
			dialog.getBtnOutlineColorHexagon().setBackground(frame.getBtnOutlineColor().getBackground());
			dialog.getBtnInnerColorHexagon().setBackground(frame.getBtnInnerColor().getBackground());
			dialog.setVisible(true);

			if (dialog.isConfirm()) {
				newShape = dialog.getHexagon();
				model.shapes.add(newShape);
				commands.add("Added" + newShape.toString());
				lastCommand = "Added" + newShape.toString();
				frame.getTxtCommands().append("Added" + newShape.toString() + '\n');
				startPoint = null;
				disableRedo();
				checkButtonsAvailability();
			}
		}

		frame.repaint();
	}

	// selektovanje
	public void select(int x, int y) {
		Iterator<Shape> iterator = model.getShapes().iterator();
		int count = 0;
		for (int i = 0; i < model.getShapes().size(); i++) {
			if (model.getShapes().get(i).contains(x, y)) {
				count = count + 1;
			}
		}

		while (iterator.hasNext()) {
			Shape shape = iterator.next();
			// shape.setSelected(false);
			if (shape.contains(x, y) && count == 1) {
				if (shape.isSelected() == true && count == 1) {
					deselectShapesCmd.execute1(shape);
					tempDeselectedShapes.add(shape);
					frame.getTxtCommands().append("Deselected" + shape.toString() + "\n");
					commands.add("Deselected" + shape.toString());
					lastCommand = "Deselected" + shape.toString();
					count = 0;
				} else if (shape.isSelected() == false && count == 1) {
					selectedShapes.add(shape);
					shape.setSelected(true);
					frame.getTxtCommands().append("Selected" + shape.toString() + "\n");
					commands.add("Selected" + shape.toString());
					lastCommand = "Selected" + shape.toString();
					count = 0;
				}

			}

			if (shape.contains(x, y) && count == 2) { // && check == true
				for (int i = 0; i < model.shapes.size(); i++) {
					for (int n = i + 1; n < model.shapes.size(); n++) {

						if (model.shapes.get(i).isSelected() == true && model.shapes.get(n).isSelected() == true
								&& model.shapes.get(i).contains(x, y) && model.shapes.get(n).contains(x, y)) {
							model.shapes.get(n).setSelected(false);
							shape2 = model.shapes.get(n);
						}

						else if (model.shapes.get(i).contains(x, y) && model.shapes.get(n).contains(x, y)
								&& model.shapes.get(i).isSelected() == false
								&& model.shapes.get(n).isSelected() == true) {
							model.shapes.get(n).setSelected(false);
							shape2 = model.shapes.get(n);
						} else if (model.shapes.get(i).contains(x, y) && model.shapes.get(n).contains(x, y)
								&& model.shapes.get(i).isSelected() == false
								&& model.shapes.get(n).isSelected() == false) {
							model.shapes.get(n).setSelected(true);
							shape1 = model.shapes.get(n);
							model.shapes.get(i).setSelected(false);
						} else if (model.shapes.get(i).contains(x, y) && model.shapes.get(n).contains(x, y)
								&& model.shapes.get(i).isSelected() == true
								&& model.shapes.get(n).isSelected() == false) {
							model.shapes.get(n).setSelected(true);
							shape1 = model.shapes.get(n);
						}
					}
				}

				check = false;
				count = 0;
			}
			if (shape.contains(x, y) && count >= 3) {
				if (model.getShapes().get(model.getShapes().size() - 1).isSelected() == false) {
					model.getShapes().get(model.getShapes().size() - 1).setSelected(true);
					shape3 = model.getShapes().get(model.getShapes().size() - 1);
				} else {
					model.getShapes().get(model.getShapes().size() - 1).setSelected(false);
					shape2 = model.getShapes().get(model.getShapes().size() - 1);
				}
			}
			// System.out.println(shape.isSelected());
			if (shape1 != null) {
				frame.getTxtCommands().append("Selected" + shape1.toString() + "\n");
				commands.add("Selected" + shape1.toString());
				lastCommand = "Selected" + shape1.toString();
				selectedShapes.add(shape1);
				shape1 = null;
				count = 0;
			}
			if (shape2 != null) {
				frame.getTxtCommands().append("Deselected" + shape2.toString() + "\n");
				commands.add("Deselected" + shape2.toString());
				lastCommand = "Deselected" + shape2.toString();
				deselectShapesCmd.execute1(shape2);
				// selectedShapes.remove(shape2);
				shape2 = null;
				count = 0;
			}
			if (shape3 != null) {
				frame.getTxtCommands().append("Selected" + shape3.toString() + "\n");
				commands.add("Selected" + shape3.toString());
				lastCommand = "Selected" + shape3.toString();
				selectedShapes.add(shape3);
				shape3 = null;
				count = 0;
			}
			checkButtonsAvailability();
		}
	}

	public Shape getSelectedShape() {
		Shape selectedShape1 = null;
		for (int i = 0; i < selectedShapes.size(); i++) {
			if (selectedShapes.get(i).isSelected() == true) {
				selectedShape1 = selectedShapes.get(i);
			}
		}

		return selectedShape1;
	}

	// brint to back and other

	public void bringToBack() {

		Shape shape = selectedShapes.get(0);
		BringToBackCmd bringToBackCmd = new BringToBackCmd(model, shape);
		bringToBackCmd.execute();
		undoRedoBackFront.add(bringToBackCmd);
		frame.getTxtCommands().append("BringToBack:" + shape.toString() + "\n");
		commands.add("BringToBack:" + shape.toString());
		lastCommand = "BringToBack:" + shape.toString();
		frame.repaint();
		disableRedo();
		checkButtonsAvailability();

	}

	public void bringToFront() {

		Shape shape = selectedShapes.get(0);
		BringToFrontCmd bringToFrontCmd = new BringToFrontCmd(model, shape);
		bringToFrontCmd.execute();
		undoRedoBackFront.add(bringToFrontCmd);
		frame.getTxtCommands().append("BringToFront:" + shape.toString() + "\n");
		commands.add("BringToFront:" + shape.toString());
		lastCommand = "BringToFront:" + shape.toString();
		frame.repaint();
		disableRedo();
		checkButtonsAvailability();

	}

	public void toBack() {

		Shape shape = selectedShapes.get(0);
		ToBackCmd toBackCmd = new ToBackCmd(model, shape);
		toBackCmd.execute();
		undoRedoBackFront.add(toBackCmd);
		frame.getTxtCommands().append("ToBack:" + shape.toString() + "\n");
		commands.add("ToBack:" + shape.toString());
		lastCommand = "ToBack:" + shape.toString();
		frame.repaint();
		disableRedo();
		checkButtonsAvailability();

	}

	public void toFront() {

		Shape shape = selectedShapes.get(0);
		ToFrontCmd toFrontCmd = new ToFrontCmd(model, shape);
		toFrontCmd.execute();
		undoRedoBackFront.add(toFrontCmd);
		frame.getTxtCommands().append("ToFront:" + shape.toString() + "\n");
		commands.add("ToFront:" + shape.toString());
		lastCommand = "ToFront:" + shape.toString();
		frame.repaint();
		disableRedo();
		checkButtonsAvailability();

	}

	// Undo
	public void undo() {
		if (commands.size() != 0) {
			if (commands.get(commands.size() - 1).contains("Added")) {
				tempCommands.add(commands.get(commands.size() - 1));
				frame.getTxtCommands().append("Undo:" + commands.get(commands.size() - 1) + "\n");
				lastCommand = "Undo:" + commands.get(commands.size() - 1);
				commands.remove(commands.size() - 1);
				AddShapeCmd addShapeCmd = new AddShapeCmd(model.get(model.getShapes().size() - 1), model);
				tempShapes.add(model.get(model.getShapes().size() - 1));
				addShapeCmd.unexecute();
				frame.repaint();

			} else if (commands.get(commands.size() - 1).contains("Deleted")) {
				// EVENTUALNO COMMANDS SREDITI RADI ISPISA, NE PISU SE UNDO I REDO U COMMANDS
				tempCommands.add(commands.get(commands.size() - 1));
				frame.getTxtCommands().append("Undo:" + commands.get(commands.size() - 1) + "\n");
				lastCommand = "Undo:" + commands.get(commands.size() - 1);
				commands.remove(commands.size() - 1);

				RemoveShapeCmd removeShapeCmd = new RemoveShapeCmd(removedShapes.get(removedShapes.size() - 1), model,
						index.get(index.size() - 1));
				tempRemovedShapes.add(removedShapes.get(removedShapes.size() - 1));
				selectedShapes.add(removedShapes.get(removedShapes.size() - 1));// pokusaj
				removedShapes.remove(removedShapes.size() - 1);

				removeShapeCmd.unexecute();
				index.remove(index.size() - 1);
				frame.repaint();

			} else if (commands.get(commands.size() - 1).contains("Modified")) {
				frame.getTxtCommands().append("Undo:" + commands.get(commands.size() - 1) + "\n");
				tempCommands.add(commands.get(commands.size() - 1));
				tempModifiedShapes.push(modifiedShapes.pop()); // temp sluzi za redo
				updateCmdStack.peek().unexecute();
				tempUpdateCmdStack.push(updateCmdStack.pop());
				frame.repaint();

				lastCommand = "Undo:" + commands.get(commands.size() - 1);
				commands.remove(commands.size() - 1);
			} else if (commands.get(commands.size() - 1).contains("Selected")) {
				frame.getTxtCommands().append("Undo:" + commands.get(commands.size() - 1) + "\n");
				tempCommands.add(commands.get(commands.size() - 1));
				selectShapesCmd.unexecute();
				frame.repaint();
				lastCommand = "Undo:" + commands.get(commands.size() - 1);
				commands.remove(commands.size() - 1);
			} else if (commands.get(commands.size() - 1).contains("Deselected")) {
				frame.getTxtCommands().append("Undo:" + commands.get(commands.size() - 1) + "\n");
				tempCommands.add(commands.get(commands.size() - 1));
				deselectShapesCmd.unexecute();
				tempDeselectedShapes.add(deselectShapesCmd.getShapeToAddInDeselectUndo());
				frame.repaint();
				lastCommand = "Undo:" + commands.get(commands.size() - 1);
				commands.remove(commands.size() - 1);
			} else if ((commands.get(commands.size() - 1).contains("Back")
					|| commands.get(commands.size() - 1).contains("Front"))
					&& !commands.get(commands.size() - 1).contains("Undo")) {
				frame.getTxtCommands().append("Undo:" + commands.get(commands.size() - 1) + "\n");
				tempCommands.add(commands.get(commands.size() - 1));
				undoRedoBackFront.peek().unexecute();
				tempUndoRedoBackFront.push(undoRedoBackFront.pop());
				frame.repaint();
				lastCommand = "Undo:" + commands.get(commands.size() - 1);
				commands.remove(commands.size() - 1);
			}
			checkButtonsAvailability();

		} else {
			JOptionPane.showMessageDialog(null, "\r\n" + "There are no commands!", "Message",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void redo() {
		if (tempCommands.size() > 0 && lastCommand.contains("Undo")) {

			if (tempCommands.get(tempCommands.size() - 1).contains("Added")) {
				commands.add(tempCommands.get(tempCommands.size() - 1));
				frame.getTxtCommands().append("Redo:" + tempCommands.get(tempCommands.size() - 1) + "\n");
				AddShapeCmd addShapeCmd = new AddShapeCmd(tempShapes.get(tempShapes.size() - 1), model);
				addShapeCmd.execute();
				frame.repaint();
				tempCommands.remove(tempCommands.size() - 1);
				tempShapes.remove(tempShapes.size() - 1);
			} else if (tempCommands.get(tempCommands.size() - 1).contains("Deleted")) {
				commands.add(tempCommands.get(tempCommands.size() - 1));
				frame.getTxtCommands().append("Redo:" + tempCommands.get(tempCommands.size() - 1) + "\n");
				tempCommands.remove(tempCommands.size() - 1);
				index.add(model.getShapes().indexOf(tempRemovedShapes.get(tempRemovedShapes.size() - 1)));
				RemoveShapeCmd removeShapeCmd = new RemoveShapeCmd(tempRemovedShapes.get(tempRemovedShapes.size() - 1),
						model, 0);
				removedShapes.add(tempRemovedShapes.get(tempRemovedShapes.size() - 1));
				selectedShapes.remove(tempRemovedShapes.get(tempRemovedShapes.size() - 1));// pokusaj
				tempRemovedShapes.remove(tempRemovedShapes.get(tempRemovedShapes.size() - 1));
				removeShapeCmd.execute(); // remove

				frame.repaint();
			} else if (tempCommands.get(tempCommands.size() - 1).contains("Modified")) {
				commands.add(tempCommands.get(tempCommands.size() - 1));
				frame.getTxtCommands().append("Redo:" + tempCommands.get(tempCommands.size() - 1) + "\n");
				tempCommands.remove(tempCommands.size() - 1);
				modifiedShapes.push(tempModifiedShapes.pop());
				tempUpdateCmdStack.peek().execute();
				updateCmdStack.push(tempUpdateCmdStack.pop());
				frame.repaint();
			} else if (tempCommands.get(tempCommands.size() - 1).contains("Selected")) {
				commands.add(tempCommands.get(tempCommands.size() - 1));
				frame.getTxtCommands().append("Redo:" + tempCommands.get(tempCommands.size() - 1) + "\n");
				tempCommands.remove(tempCommands.size() - 1);
				selectShapesCmd.execute();
				frame.repaint();
			} else if (tempCommands.get(tempCommands.size() - 1).contains("Deselected")) {
				commands.add(tempCommands.get(tempCommands.size() - 1));
				frame.getTxtCommands().append("Redo:" + tempCommands.get(tempCommands.size() - 1) + "\n");
				tempCommands.remove(tempCommands.size() - 1);
				deselectShapesCmd.execute1(tempDeselectedShapes.get(tempDeselectedShapes.size() - 1));
				tempDeselectedShapes.remove(tempDeselectedShapes.size() - 1);
				frame.repaint();
			} else if ((tempCommands.get(tempCommands.size() - 1).contains("Back")
					|| tempCommands.get(tempCommands.size() - 1).contains("Front"))) {
				commands.add(tempCommands.get(tempCommands.size() - 1));
				frame.getTxtCommands().append("Redo:" + tempCommands.get(tempCommands.size() - 1) + "\n");
				tempCommands.remove(tempCommands.size() - 1);
				tempUndoRedoBackFront.peek().execute();
				undoRedoBackFront.push(tempUndoRedoBackFront.pop());
				frame.repaint();
			}
			checkButtonsAvailability();
		} else {
			JOptionPane.showMessageDialog(null, "\r\n" + "\r\n" + "Can't redo now!", "Message",
					JOptionPane.INFORMATION_MESSAGE);
			disableRedo(); // ovaj deo ne treba {ELSE}
		}
	}

	public void disableRedo() {
		tempShapes.clear();
		tempCommands.clear();
		tempRemovedShapes.clear();
		tempModifiedShapes.clear();
		tempDeselectedShapes.clear();
		tempUndoRedoBackFront.clear();
	}

	// *************************************************************************
	public void saveClicked() {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "/Desktop");
		fileChooser.setDialogTitle("Save as");
		fileChooser.setVisible(true);

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
		fileChooser.setFileFilter(filter);

		int userSelection = fileChooser.showSaveDialog(frame);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			String savePath = fileToSave.getAbsolutePath();

			saveLoadTxt = new SaveManager(new SaveLoadTxt(frame));
			saveLoadTxt.save(savePath + ".txt");

			saveLoadBin = new SaveManager(new SaveLoadBin(model));
			saveLoadBin.save(savePath + ".bin");
		}
	};

	public void loadBinFile() {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "/Desktop");
		fileChooser.setDialogTitle("Open");
		fileChooser.setVisible(true);

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Binary Files", "bin");
		fileChooser.setFileFilter(filter);

		int userSelection = fileChooser.showOpenDialog(frame);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToOpen = fileChooser.getSelectedFile();
			String filePath = fileToOpen.getAbsolutePath();
			clearBeforeLoading();
			saveLoadBinLoad = new SaveManager(new SaveLoadBin(model));
			saveLoadBinLoad.load(filePath, model);
			frame.repaint();

		}
		checkButtonsAvailability();

	}

	public void clearBeforeLoading() {
		model.getShapes().clear();
		selectedShapes.clear();
		tempCommands.clear();
		commands.clear();
		index.clear();
		tempRemovedShapes.clear();
		tempModifiedShapes.clear();
		tempDeselectedShapes.clear();
		tempUndoRedoBackFront.clear();
		frame.getTxtCommands().setText("");
	}

	public void loadTxtFile() {
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "/Desktop");
		fileChooser.setDialogTitle("Open");
		fileChooser.setVisible(true);

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
		fileChooser.setFileFilter(filter);

		int userSelection = fileChooser.showOpenDialog(frame);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToOpen = fileChooser.getSelectedFile();
			String filePath = fileToOpen.getAbsolutePath();
			saveLoadTxtLoad = new SaveManager(new SaveLoadTxt(frame));
			String[] lines = saveLoadTxtLoad.load(filePath, model);
			if (lines == null) {
				System.out.println("null");
			} else {
				txtFileReader(lines);
			}

		}
		selectButtonAvailability();
	}

	public void txtFileReader(String[] string) {
		clearBeforeLoading();
		frame.repaint();
		String commandToDo[];
		int continueDrawing = 0;
		int i = 0;
		while (i < string.length && continueDrawing == 0) {
			String line = string[i];
			if (line.startsWith("Added")) {
				commandToDo = line.split(":");
				if (commandToDo[1].equals("Point")) {
					int option = JOptionPane.showConfirmDialog(null,
							"Do you want to add Point(" + commandToDo[3] + "," + commandToDo[5] + ", RGB ("
									+ commandToDo[7] + "," + commandToDo[8] + "," + commandToDo[9] + "))?");
					if (option == JOptionPane.YES_OPTION) {
						int x = Integer.parseInt(commandToDo[3]);
						int y = Integer.parseInt(commandToDo[5]);
						Color color = new Color(Integer.parseInt(commandToDo[7]), Integer.parseInt(commandToDo[8]),
								Integer.parseInt(commandToDo[9]));
						Point p = new Point(x, y, false, color);
						model.shapes.add(p);
						commands.add("Added" + p.toString());
						frame.getTxtCommands().append("Added" + p.toString() + '\n');
					} else {
						continueDrawing = option;
					}
				} else if (commandToDo[1].equals("Line")) {
					int option = JOptionPane.showConfirmDialog(null,
							"Do you want to add Line((" + commandToDo[3] + "," + commandToDo[4] + "),(" + commandToDo[6]
									+ "," + commandToDo[7] + "), RGB (" + commandToDo[9] + "," + commandToDo[10] + ","
									+ commandToDo[11] + "))?");
					if (option == JOptionPane.YES_OPTION) {
						int spX = Integer.parseInt(commandToDo[3]);
						int spY = Integer.parseInt(commandToDo[4]);
						int epX = Integer.parseInt(commandToDo[6]);
						int epY = Integer.parseInt(commandToDo[7]);
						Color color = new Color(Integer.parseInt(commandToDo[9]), Integer.parseInt(commandToDo[10]),
								Integer.parseInt(commandToDo[11]));
						Point sp = new Point(spX, spY);
						Point ep = new Point(epX, epY);
						Line l = new Line(sp, ep, false, color);
						model.shapes.add(l);
						commands.add("Added" + l.toString());
						frame.getTxtCommands().append("Added" + l.toString() + '\n');
					} else {
						continueDrawing = option;
					}
				} else if (commandToDo[1].equals("Rectangle")) {
					int option = JOptionPane.showConfirmDialog(null,
							"Do you want to add Rectangle((" + commandToDo[3] + "," + commandToDo[4] + "),"
									+ commandToDo[6] + "," + commandToDo[8] + ", RGB (" + commandToDo[10] + ","
									+ commandToDo[11] + "," + commandToDo[12] + "), RGB (" + commandToDo[14] + ","
									+ commandToDo[15] + "," + commandToDo[16] + ")?");
					if (option == JOptionPane.YES_OPTION) {
						int x = Integer.parseInt(commandToDo[3]);
						int y = Integer.parseInt(commandToDo[4]);
						Point ulp = new Point(x, y);
						int width = Integer.parseInt(commandToDo[6]);
						int height = Integer.parseInt(commandToDo[8]);
						Color color = new Color(Integer.parseInt(commandToDo[10]), Integer.parseInt(commandToDo[11]),
								Integer.parseInt(commandToDo[12]));
						Color innerColor = new Color(Integer.parseInt(commandToDo[14]),
								Integer.parseInt(commandToDo[15]), Integer.parseInt(commandToDo[16]));
						Rectangle r = new Rectangle(ulp, width, height, false, color, innerColor);
						model.shapes.add(r);
						commands.add("Added" + r.toString());
						frame.getTxtCommands().append("Added" + r.toString() + '\n');
					} else {
						continueDrawing = option;
					}
				} else if (commandToDo[1].equals("Circle")) {
					int option = JOptionPane.showConfirmDialog(null,
							"Do you want to add Circle((" + commandToDo[3] + "," + commandToDo[4] + "),"
									+ commandToDo[6] + ", RGB (" + commandToDo[8] + "," + commandToDo[9] + ","
									+ commandToDo[10] + "), RGB (" + commandToDo[12] + "," + commandToDo[13] + ","
									+ commandToDo[14] + ")?");
					if (option == JOptionPane.YES_OPTION) {
						int x = Integer.parseInt(commandToDo[3]);
						int y = Integer.parseInt(commandToDo[4]);
						Point center = new Point(x, y);
						int radius = Integer.parseInt(commandToDo[6]);
						Color color = new Color(Integer.parseInt(commandToDo[8]), Integer.parseInt(commandToDo[9]),
								Integer.parseInt(commandToDo[10]));
						Color innerColor = new Color(Integer.parseInt(commandToDo[12]),
								Integer.parseInt(commandToDo[13]), Integer.parseInt(commandToDo[14]));
						Circle c = new Circle(center, radius, false, color, innerColor);
						model.shapes.add(c);
						commands.add("Added" + c.toString());
						frame.getTxtCommands().append("Added" + c.toString() + '\n');
					} else {
						continueDrawing = option;
					}
				} else if (commandToDo[1].equals("Donut")) {
					int option = JOptionPane.showConfirmDialog(null,
							"Do you want to add Donut((" + commandToDo[3] + "," + commandToDo[4] + ")," + commandToDo[6]
									+ "," + commandToDo[8] + ", RGB (" + commandToDo[10] + "," + commandToDo[11] + ","
									+ commandToDo[12] + "), RGB (" + commandToDo[14] + "," + commandToDo[15] + ","
									+ commandToDo[16] + ")?");
					if (option == JOptionPane.YES_OPTION) {
						int x = Integer.parseInt(commandToDo[3]);
						int y = Integer.parseInt(commandToDo[4]);
						Point center = new Point(x, y);
						int radius = Integer.parseInt(commandToDo[6]);
						int innerRadius = Integer.parseInt(commandToDo[8]);
						Color color = new Color(Integer.parseInt(commandToDo[10]), Integer.parseInt(commandToDo[11]),
								Integer.parseInt(commandToDo[12]));
						Color innerColor = new Color(Integer.parseInt(commandToDo[14]),
								Integer.parseInt(commandToDo[15]), Integer.parseInt(commandToDo[16]));
						Donut d = new Donut(center, radius, innerRadius, false, color, innerColor);
						model.shapes.add(d);
						commands.add("Added" + d.toString());
						frame.getTxtCommands().append("Added" + d.toString() + '\n');
					} else {
						continueDrawing = option;
					}
				} else if (commandToDo[1].equals("Hexagon")) {
					int option = JOptionPane.showConfirmDialog(null,
							"Do you want to add Hexagon((" + commandToDo[3] + "," + commandToDo[4] + "),"
									+ commandToDo[6] + ", RGB (" + commandToDo[8] + "," + commandToDo[9] + ","
									+ commandToDo[10] + "), RGB (" + commandToDo[12] + "," + commandToDo[13] + ","
									+ commandToDo[14] + ")?");
					if (option == JOptionPane.YES_OPTION) {
						int x = Integer.parseInt(commandToDo[3]);
						int y = Integer.parseInt(commandToDo[4]);
						int radius = Integer.parseInt(commandToDo[6]);
						Color color = new Color(Integer.parseInt(commandToDo[8]), Integer.parseInt(commandToDo[9]),
								Integer.parseInt(commandToDo[10]));
						Color innerColor = new Color(Integer.parseInt(commandToDo[12]),
								Integer.parseInt(commandToDo[13]), Integer.parseInt(commandToDo[14]));
						HexagonAdapter h = new HexagonAdapter(new Hexagon(x, y, radius));
						h.setColor(color);
						h.setInnerColor(innerColor);
						model.shapes.add(h);
						commands.add("Added" + h.toString());
						frame.getTxtCommands().append("Added" + h.toString() + '\n');
					} else {
						continueDrawing = option;
					}
				}

			} else if (line.startsWith("Selected")) {
				String line1 = extractCommand(line, "Selected:");
				int option = JOptionPane.showConfirmDialog(null, "Do you want to select " + line1);
				if (option == JOptionPane.YES_OPTION) {
					for (Shape shape : model.getShapes()) {
						if (shape.toString().equals(":" + line1)) {
							selectedShapes.add(shape);
							shape.setSelected(true);
							frame.getTxtCommands().append("Selected" + shape.toString() + "\n");
							commands.add("Selected" + shape.toString());
							break;
						}
					}
				} else {
					continueDrawing = option;
				}

			} else if (line.startsWith("Deselected")) {
				String line1 = extractCommand(line, "Deselected:");
				int option = JOptionPane.showConfirmDialog(null, "Do you want to deselected " + line1);
				if (option == JOptionPane.YES_OPTION) {
					for (Shape shape : model.getShapes()) {
						if (shape.toString().equals(":" + line1)) {
							deselectShapesCmd.execute1(shape);
							tempDeselectedShapes.add(shape);
							frame.getTxtCommands().append("Deselected" + shape.toString() + "\n");
							commands.add("Deselected" + shape.toString());
							break;
						}
					}
				} else {
					continueDrawing = option;
				}

			} else if (line.startsWith("Deleted")) {
				String line1 = extractCommand(line, "Deleted:");
				int option = JOptionPane.showConfirmDialog(null, "Do you want to delete " + line1);
				if (option == JOptionPane.YES_OPTION) {
					for (Shape shape : model.getShapes()) {
						if (shape.toString().equals(":" + line1)) {
							index.add(model.getShapes().indexOf(shape));
							model.getShapes().remove(shape);
							selectedShapes.remove(shape);
							removedShapes.add(shape);
							commands.add("Deleted" + shape.toString());
							frame.getTxtCommands().append("Deleted" + shape.toString() + '\n');
							// tempSelectedShapes.remove(shape);
							break;
						}
					}
				} else {
					continueDrawing = option;
				}
			} else if (line.startsWith("ToBack:")) {
				String line1 = extractCommand(line, "ToBack:");
				int option = JOptionPane.showConfirmDialog(null, "Do you want to move to back " + line1);
				if (option == JOptionPane.YES_OPTION) {
					for (Shape shape : model.getShapes()) {
						if (shape.toString().equals(line1)) {
							ToBackCmd toBackCmd = new ToBackCmd(model, shape);
							toBackCmd.execute();
							undoRedoBackFront.add(toBackCmd);
							frame.getTxtCommands().append("ToBack:" + shape.toString() + "\n");
							commands.add("ToBack:" + shape.toString());
							break;
						}
					}
				} else {
					continueDrawing = option;
				}
			} else if (line.startsWith("ToFront:")) {
				String line1 = extractCommand(line, "ToFront:");
				int option = JOptionPane.showConfirmDialog(null, "Do you want to move to front " + line1);
				if (option == JOptionPane.YES_OPTION) {
					for (Shape shape : model.getShapes()) {
						if (shape.toString().equals(line1)) {
							ToFrontCmd toFrontCmd = new ToFrontCmd(model, shape);
							toFrontCmd.execute();
							undoRedoBackFront.add(toFrontCmd);
							frame.getTxtCommands().append("ToFront:" + shape.toString() + "\n");
							commands.add("ToFront:" + shape.toString());
							break;
						}
					}
				} else {
					continueDrawing = option;
				}
			} else if (line.startsWith("BringToBack:")) {
				String line1 = extractCommand(line, "BringToBack:");
				int option = JOptionPane.showConfirmDialog(null, "Do you want to bring to back " + line1);
				if (option == JOptionPane.YES_OPTION) {
					for (Shape shape : model.getShapes()) {
						if (shape.toString().equals(line1)) {
							BringToBackCmd bringToBackCmd = new BringToBackCmd(model, shape);
							bringToBackCmd.execute();
							undoRedoBackFront.add(bringToBackCmd);
							frame.getTxtCommands().append("BringToBack:" + shape.toString() + "\n");
							commands.add("BringToBack:" + shape.toString());
							break;
						}
					}
				} else {
					continueDrawing = option;
				}
			} else if (line.startsWith("BringToFront:")) {
				String line1 = extractCommand(line, "BringToFront:");
				int option = JOptionPane.showConfirmDialog(null, "Do you want to bring to front " + line1);
				if (option == JOptionPane.YES_OPTION) {
					for (Shape shape : model.getShapes()) {
						if (shape.toString().equals(line1)) {
							BringToFrontCmd bringToFrontCmd = new BringToFrontCmd(model, shape);
							bringToFrontCmd.execute();
							undoRedoBackFront.add(bringToFrontCmd);
							frame.getTxtCommands().append("BringToFront:" + shape.toString() + "\n");
							commands.add("BringToFront:" + shape.toString());
							break;
						}
					}
				} else {
					continueDrawing = option;
				}
			} else if (line.startsWith("Undo:")) {
				String line1 = extractCommand(line, "Undo:");
				int option = JOptionPane.showConfirmDialog(null, "Do you want to undo " + line1);
				if (option == JOptionPane.YES_OPTION) {
					undo();
				} else {
					continueDrawing = option;
				}
			} else if (line.startsWith("Redo:")) {
				String line1 = extractCommand(line, "Redo:");
				int option = JOptionPane.showConfirmDialog(null, "Do you want to redo " + line1);
				if (option == JOptionPane.YES_OPTION) {
					redo();
				} else {
					continueDrawing = option;
				}
			} else if (line.startsWith("Modified")) {
				commandToDo = line.split(":");
				if (commandToDo[1].equals("Point")) {
					int option = JOptionPane.showConfirmDialog(null,
							"Do you want to modify Point(" + commandToDo[3] + "," + commandToDo[5] + ", RGB ("
									+ commandToDo[7] + "," + commandToDo[8] + "," + commandToDo[9] + "))?");
					if (option == JOptionPane.YES_OPTION) {
						int x1 = Integer.parseInt(commandToDo[3]);
						int y1 = Integer.parseInt(commandToDo[5]);
						Color color1 = new Color(Integer.parseInt(commandToDo[7]), Integer.parseInt(commandToDo[8]),
								Integer.parseInt(commandToDo[9]));
						Point p1 = new Point(x1, y1, false, color1);
						int x2 = Integer.parseInt(commandToDo[13]);
						int y2 = Integer.parseInt(commandToDo[15]);
						Color color2 = new Color(Integer.parseInt(commandToDo[17]), Integer.parseInt(commandToDo[18]),
								Integer.parseInt(commandToDo[19]));
						Point p2 = new Point(x2, y2, false, color2);
						for (Shape shape : model.getShapes()) {
							if (shape.toString().equals(p1.toString())) {
								updatePointCmd = new UpdatePointCmd((Point) shape, p2);
								updateCmdStack.push(updatePointCmd);
								updatePointCmd.execute();
								modifiedShapes.push((Point) shape);
								commands.add("Modified" + updatePointCmd.toString());
								lastCommand = "Modified" + updatePointCmd.toString();
								frame.getTxtCommands().append("Modified" + updatePointCmd.toString() + '\n');
								break;
							}
						}
					} else {
						continueDrawing = option;
					}
				} else if (commandToDo[1].equals("Line")) {
					int option = JOptionPane.showConfirmDialog(null,
							"Do you want to modify Line((" + commandToDo[3] + "," + commandToDo[4] + "),("
									+ commandToDo[6] + "," + commandToDo[7] + "), RGB (" + commandToDo[9] + ","
									+ commandToDo[10] + "," + commandToDo[11] + "))?");
					if (option == JOptionPane.YES_OPTION) {

						int l1spX = Integer.parseInt(commandToDo[3]);
						int l1spY = Integer.parseInt(commandToDo[4]);
						int l1epX = Integer.parseInt(commandToDo[6]);
						int l1epY = Integer.parseInt(commandToDo[7]);
						Color color1 = new Color(Integer.parseInt(commandToDo[9]), Integer.parseInt(commandToDo[10]),
								Integer.parseInt(commandToDo[11]));
						Point sp1 = new Point(l1spX, l1spY);
						Point ep1 = new Point(l1epX, l1epY);
						Line l1 = new Line(sp1, ep1, false, color1);
						// System.out.println(l);
						int l2spX = Integer.parseInt(commandToDo[15]);
						int l2spY = Integer.parseInt(commandToDo[16]);
						int l2epX = Integer.parseInt(commandToDo[18]);
						int l2epY = Integer.parseInt(commandToDo[19]);
						Color color2 = new Color(Integer.parseInt(commandToDo[21]), Integer.parseInt(commandToDo[22]),
								Integer.parseInt(commandToDo[23]));
						Point sp2 = new Point(l2spX, l2spY);
						Point ep2 = new Point(l2epX, l2epY);
						Line l2 = new Line(sp2, ep2, false, color2);
						for (Shape shape : model.getShapes()) {
							if (shape.toString().equals(l1.toString())) {
								updateLineCmd = new UpdateLineCmd((Line) shape, l2);
								updateCmdStack.push(updateLineCmd);
								updateLineCmd.execute();
								modifiedShapes.push((Line) shape);
								commands.add("Modified" + updateLineCmd.getToString());
								lastCommand = "Modified" + updateLineCmd.getToString();
								frame.getTxtCommands().append("Modified" + updateLineCmd.getToString() + '\n');
								break;
							}
						}
					}
				} else if (commandToDo[1].equals("Rectangle")) {
					int option = JOptionPane.showConfirmDialog(null,
							"Do you want to modify Rectangle((" + commandToDo[3] + "," + commandToDo[4] + "),"
									+ commandToDo[6] + "," + commandToDo[8] + ", RGB (" + commandToDo[10] + ","
									+ commandToDo[11] + "," + commandToDo[12] + "), RGB (" + commandToDo[14] + ","
									+ commandToDo[15] + "," + commandToDo[16] + ")?");
					if (option == JOptionPane.YES_OPTION) {

						int ulp1X = Integer.parseInt(commandToDo[3]);
						int ulp1Y = Integer.parseInt(commandToDo[4]);
						Point ulp1 = new Point(ulp1X, ulp1Y);
						int w1 = Integer.parseInt(commandToDo[6]);
						int h1 = Integer.parseInt(commandToDo[8]);
						Color color1 = new Color(Integer.parseInt(commandToDo[10]), Integer.parseInt(commandToDo[11]),
								Integer.parseInt(commandToDo[12]));
						Color innerColor1 = new Color(Integer.parseInt(commandToDo[14]),
								Integer.parseInt(commandToDo[15]), Integer.parseInt(commandToDo[16]));
						Rectangle r1 = new Rectangle(ulp1, w1, h1, false, color1, innerColor1);

						int ulp2X = Integer.parseInt(commandToDo[20]);
						int ulp2Y = Integer.parseInt(commandToDo[21]);
						Point ulp2 = new Point(ulp2X, ulp2Y);
						int w2 = Integer.parseInt(commandToDo[23]);
						int h2 = Integer.parseInt(commandToDo[25]);
						Color color2 = new Color(Integer.parseInt(commandToDo[27]), Integer.parseInt(commandToDo[28]),
								Integer.parseInt(commandToDo[29]));
						Color innerColor2 = new Color(Integer.parseInt(commandToDo[31]),
								Integer.parseInt(commandToDo[32]), Integer.parseInt(commandToDo[33]));
						Rectangle r2 = new Rectangle(ulp2, w2, h2, false, color2, innerColor2);

						for (Shape shape : model.getShapes()) {
							if (shape.toString().equals(r1.toString())) {
								updateRectangleCmd = new UpdateRectangleCmd((Rectangle) shape, r2);
								updateCmdStack.push(updateRectangleCmd);
								updateRectangleCmd.execute();
								modifiedShapes.push((Rectangle) shape);
								commands.add("Modified" + updateRectangleCmd.getToString());
								lastCommand = "Modified" + updateRectangleCmd.getToString();
								frame.getTxtCommands().append("Modified" + updateRectangleCmd.getToString() + '\n');
								break;
							}
						}
					} else {
						continueDrawing = option;
					}
				} else if (commandToDo[1].equals("Circle")) {
					int option = JOptionPane.showConfirmDialog(null,
							"Do you want to modify Circle((" + commandToDo[3] + "," + commandToDo[4] + "),"
									+ commandToDo[6] + ", RGB (" + commandToDo[8] + "," + commandToDo[9] + ","
									+ commandToDo[10] + "), RGB (" + commandToDo[12] + "," + commandToDo[13] + ","
									+ commandToDo[14] + ")?");
					if (option == JOptionPane.YES_OPTION) {

						int c1X = Integer.parseInt(commandToDo[3]);
						int c1Y = Integer.parseInt(commandToDo[4]);
						Point center1 = new Point(c1X, c1Y);
						int r1 = Integer.parseInt(commandToDo[6]);
						Color color1 = new Color(Integer.parseInt(commandToDo[8]), Integer.parseInt(commandToDo[9]),
								Integer.parseInt(commandToDo[10]));
						Color innerColor1 = new Color(Integer.parseInt(commandToDo[12]),
								Integer.parseInt(commandToDo[13]), Integer.parseInt(commandToDo[14]));
						Circle c1 = new Circle(center1, r1, false, color1, innerColor1);

						int c2X = Integer.parseInt(commandToDo[18]);
						int c2Y = Integer.parseInt(commandToDo[19]);
						Point center2 = new Point(c2X, c2Y);
						int r2 = Integer.parseInt(commandToDo[21]);
						Color color2 = new Color(Integer.parseInt(commandToDo[23]), Integer.parseInt(commandToDo[24]),
								Integer.parseInt(commandToDo[25]));
						Color innerColor2 = new Color(Integer.parseInt(commandToDo[27]),
								Integer.parseInt(commandToDo[28]), Integer.parseInt(commandToDo[29]));
						Circle c2 = new Circle(center2, r2, false, color2, innerColor2);
						for (Shape shape : model.getShapes()) {
							if (shape.toString().equals(c1.toString())) {
								updateCircleCmd = new UpdateCircleCmd((Circle) shape, c2);
								updateCmdStack.push(updateCircleCmd);
								updateCircleCmd.execute();
								modifiedShapes.push((Circle) shape);
								commands.add("Modified" + updateCircleCmd.getToString());
								lastCommand = "Modified" + updateCircleCmd.getToString();
								frame.getTxtCommands().append("Modified" + updateCircleCmd.getToString() + '\n');
								break;
							}
						}
					} else {
						continueDrawing = option;
					}
				} else if (commandToDo[1].equals("Donut")) {
					int option = JOptionPane.showConfirmDialog(null,
							"Do you want to modify Donut((" + commandToDo[3] + "," + commandToDo[4] + "),"
									+ commandToDo[6] + "," + commandToDo[8] + ", RGB (" + commandToDo[10] + ","
									+ commandToDo[11] + "," + commandToDo[12] + "), RGB (" + commandToDo[14] + ","
									+ commandToDo[15] + "," + commandToDo[16] + ")?");
					if (option == JOptionPane.YES_OPTION) {
						int d1X = Integer.parseInt(commandToDo[3]);
						int d1Y = Integer.parseInt(commandToDo[4]);
						Point c1 = new Point(d1X, d1Y);
						int r1 = Integer.parseInt(commandToDo[6]);
						int innerR1 = Integer.parseInt(commandToDo[8]);
						Color color1 = new Color(Integer.parseInt(commandToDo[10]), Integer.parseInt(commandToDo[11]),
								Integer.parseInt(commandToDo[12]));
						Color innerColor1 = new Color(Integer.parseInt(commandToDo[14]),
								Integer.parseInt(commandToDo[15]), Integer.parseInt(commandToDo[16]));
						Donut d1 = new Donut(c1, r1, innerR1, false, color1, innerColor1);

						int d2X = Integer.parseInt(commandToDo[20]);
						int d2Y = Integer.parseInt(commandToDo[21]);
						Point c2 = new Point(d2X, d2Y);
						int r2 = Integer.parseInt(commandToDo[23]);
						int innerR2 = Integer.parseInt(commandToDo[25]);
						Color color2 = new Color(Integer.parseInt(commandToDo[27]), Integer.parseInt(commandToDo[28]),
								Integer.parseInt(commandToDo[29]));
						Color innerColor2 = new Color(Integer.parseInt(commandToDo[31]),
								Integer.parseInt(commandToDo[32]), Integer.parseInt(commandToDo[33]));
						Donut d2 = new Donut(c2, r2, innerR2, false, color2, innerColor2);
						for (Shape shape : model.getShapes()) {
							if (shape.toString().equals(d1.toString())) {
								updateDonutCmd = new UpdateDonutCmd((Donut) shape, d2);
								updateCmdStack.push(updateDonutCmd);
								updateDonutCmd.execute();
								modifiedShapes.push((Donut) shape);
								commands.add("Modified" + updateDonutCmd.getToString());
								lastCommand = "Modified" + updateDonutCmd.getToString();
								frame.getTxtCommands().append("Modified" + updateDonutCmd.getToString() + '\n');
								break;
							}
						}
					} else {
						continueDrawing = option;
					}
				} else if (commandToDo[1].equals("Hexagon")) {
					int option = JOptionPane.showConfirmDialog(null,
							"Do you want to modify Hexagon((" + commandToDo[3] + "," + commandToDo[4] + "),"
									+ commandToDo[6] + ", RGB (" + commandToDo[8] + "," + commandToDo[9] + ","
									+ commandToDo[10] + "), RGB (" + commandToDo[12] + "," + commandToDo[13] + ","
									+ commandToDo[14] + ")?");
					if (option == JOptionPane.YES_OPTION) {

						int h1X = Integer.parseInt(commandToDo[3]);
						int h1Y = Integer.parseInt(commandToDo[4]);
						int r1 = Integer.parseInt(commandToDo[6]);
						Color color1 = new Color(Integer.parseInt(commandToDo[8]), Integer.parseInt(commandToDo[9]),
								Integer.parseInt(commandToDo[10]));
						Color innerColor1 = new Color(Integer.parseInt(commandToDo[12]),
								Integer.parseInt(commandToDo[13]), Integer.parseInt(commandToDo[14]));
						HexagonAdapter h1 = new HexagonAdapter(new Hexagon(h1X, h1Y, r1));
						h1.setColor(color1);
						h1.setInnerColor(innerColor1);

						int h2X = Integer.parseInt(commandToDo[18]);
						int h2Y = Integer.parseInt(commandToDo[19]);
						int r2 = Integer.parseInt(commandToDo[21]);
						Color color2 = new Color(Integer.parseInt(commandToDo[23]), Integer.parseInt(commandToDo[24]),
								Integer.parseInt(commandToDo[25]));
						Color innerColor2 = new Color(Integer.parseInt(commandToDo[27]),
								Integer.parseInt(commandToDo[28]), Integer.parseInt(commandToDo[29]));
						HexagonAdapter h2 = new HexagonAdapter(new Hexagon(h2X, h2Y, r2));
						h2.setColor(color2);
						h2.setInnerColor(innerColor2);

						for (Shape shape : model.getShapes()) {
							if (shape.toString().equals(h1.toString())) {

								updateHexagonCmd = new UpdateHexagonCmd((HexagonAdapter) shape, h2);
								updateCmdStack.push(updateHexagonCmd);
								updateHexagonCmd.execute();
								modifiedShapes.push((HexagonAdapter) shape);
								commands.add("Modified" + updateHexagonCmd.getToString());
								lastCommand = "Modified" + updateHexagonCmd.getToString();
								frame.getTxtCommands().append("Modified" + updateHexagonCmd.getToString() + '\n');
								break;
							}
						}
					} else {
						continueDrawing = option;
					}
				}

			}
			frame.repaint();
			checkButtonsAvailability();
			i++;
		}
		if (continueDrawing == 0) {
			JOptionPane.showMessageDialog(null, "The TXT file has been successfully loaded.", "Loading a txt file",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Loading of the TXT file was canceled.", "Load a txt file",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void checkButtonsAvailability() {
		selectButtonAvailability();
		modifyButtonAvailability();
		deleteButtonAvailability();
		undoButtonAvailability();
		redoButtonAvailability();
		backButtonsAvailability();
		frontButtonsAvailability();
	}

	public String extractCommand(String line, String cut) {
		String result = line.replace(cut, "");
		return result.trim();
	}

	public void setSelectedShape(Shape selectedShape) {
		this.selectedShape = selectedShape;
	}

};