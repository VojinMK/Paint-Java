package mvc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import geometry.Line;
import geometry.Point;
import geometry.Shape;
import gui.DlgCircle;
import gui.DlgDonut;
import gui.DlgRectangle;

public class DrawingView extends JPanel {

	DrawingModel model=new DrawingModel();
	
	public void setModel(DrawingModel model) {
		this.model=model;
	}
	

	public void paint(Graphics g) {
		super.paint(g);
		Iterator<Shape> iterator = model.shapes.iterator();
		while (iterator.hasNext()) {
			iterator.next().draw(g);
		}
	}

	



}
