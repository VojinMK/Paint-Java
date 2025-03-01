package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Line extends Shape implements Serializable{
	private Point startPoint=new Point();
	private Point endPoint=new Point();

	public Line() {

	}

	public Line(Point startPoint, Point endPoint) {

		this.startPoint = startPoint;
		this.endPoint = endPoint;

	}

	public Line(Point startPoint, Point endPoint, boolean selected) {

		super(selected);
		this.startPoint = startPoint;
		this.endPoint = endPoint;

	}

	public Line(Point startPoint, Point endPoint, boolean selected, Color color) {
		this(startPoint, endPoint, selected);
		setColor(color);
	}

	public boolean equals(Object obj) {
		if (obj instanceof Line) {
			Line pomocna = (Line) obj;
			if (this.startPoint.equals(pomocna.startPoint) && this.endPoint.equals(pomocna.endPoint)) {
				return true;
			} else {
				return false;
			}

		} else
			return false;
	}

	public boolean contains(int x, int y) {
		return this.startPoint.distance(x, y) + this.endPoint.distance(x, y) - length() <= 2;
	}

	public boolean contains(Point clickPoint) {
		return this.startPoint.distance(clickPoint.getX(), clickPoint.getY())
				+ this.endPoint.distance(clickPoint.getX(), clickPoint.getY()) - length() <= 2;
	}

	public double length() {
		return this.startPoint.distance(this.endPoint.getX(), this.getEndPoint().getY());
	}

	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawLine(this.startPoint.getX(), this.startPoint.getY(), endPoint.getX(), endPoint.getY());

		if (isSelected()) {
			g.setColor(Color.blue);
			g.drawRect(startPoint.getX() - 2, startPoint.getY() - 2, 4, 4);
			g.drawRect(endPoint.getX() - 2, endPoint.getY() - 2, 4, 4);
			g.setColor(Color.black);

		}

	}

	@Override
	public void moveTo(int x, int y) {
		startPoint.moveTo(x, y);
		endPoint.moveTo(x, y);

	}

	@Override
	public void moveBy(int x, int y) {
		startPoint.moveBy(x, y);
		endPoint.moveBy(x, y);

	}

	@Override
	public int compareTo(Object obj) {
		if (obj instanceof Line) {
			Line shapeToCompare = (Line) obj;
			return (int) (this.length() - shapeToCompare.length());
		}
		return 0;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public Point getStartPoint() {

		return this.startPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	public String toString() {
	    Color color = getColor();
	    return ":Line:startPoint:" +startPoint.getX()+":"+startPoint.getY()+""+ ":endPoint:" +endPoint.getX()+":"+endPoint.getY()+":"+"RGB:" + color.getRed() + ":" + color.getGreen() + ":" + color.getBlue();
	}
	
	public Line clone(Line line) {
		line.getStartPoint().setX(this.getStartPoint().getX());
		line.getStartPoint().setY(this.getStartPoint().getY());
		
		line.getEndPoint().setX(this.getEndPoint().getX());
		line.getEndPoint().setY(this.getEndPoint().getY());
		line.setColor(this.getColor());
		
		return line;

	}

}
