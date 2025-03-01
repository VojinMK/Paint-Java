package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Rectangle extends SurfaceShape implements Serializable {

	private Point upperLeftPoint=new Point();
	private int width;
	private int height;

	public Rectangle() {

	}

	public Rectangle(Point upperLeftPoint, int width, int height) {
		this.upperLeftPoint = upperLeftPoint;
		this.width = width;
		this.height = height;
	}

	public Rectangle(Point upperLeftPoint, int width, int height, boolean selected) {

		this(upperLeftPoint, width, height);
		setSelected(selected);
	}

	public Rectangle(Point upperLeftPoint, int width, int height, boolean selected, Color color) {
		this(upperLeftPoint, width, height, selected);
		setColor(color);
	}

	public Rectangle(Point upperLeftPoint, int width, int height, boolean selected, Color color, Color innerColor) {
		this(upperLeftPoint, width, height, selected, color);
		setInnerColor(innerColor);
	}

	public boolean equals(Object obj) {
		if (obj instanceof Rectangle) {
			Rectangle pomocni = (Rectangle) obj;
			if (this.upperLeftPoint.equals(pomocni.upperLeftPoint) && this.width == pomocni.width
					&& this.height == pomocni.height) {
				return true;
			} else
				return false;
		} else
			return false;
	}

	public boolean contains(int x, int y) {
		return x >= upperLeftPoint.getX() && x <= upperLeftPoint.getX() + width && y >= upperLeftPoint.getY()
				&& y <= upperLeftPoint.getY() + height;
	}

	public boolean contains(Point clickPoint) {
		return clickPoint.getX() >= upperLeftPoint.getX() && clickPoint.getX() <= upperLeftPoint.getX() + width
				&& clickPoint.getY() >= upperLeftPoint.getY() && clickPoint.getY() <= upperLeftPoint.getY() + height;
	}

	public double area() {
		return width * height;
	}

	public int circumference() {
		return 2 * (width + height);
	}

	@Override
	public void fill(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(getInnerColor());
		g.fillRect(upperLeftPoint.getX() + 1, upperLeftPoint.getY() + 1, width - 1, height - 1);
	}

	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawRect(upperLeftPoint.getX(), upperLeftPoint.getY(), width, height);
		this.fill(g);
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(upperLeftPoint.getX() - 2, upperLeftPoint.getY() - 2, 4, 4);
			g.drawRect(upperLeftPoint.getX() + width - 2, upperLeftPoint.getY() - 2, 4, 4);
			g.drawRect(upperLeftPoint.getX() - 2, upperLeftPoint.getY() + height - 2, 4, 4);
			g.drawRect(upperLeftPoint.getX() + width - 2, upperLeftPoint.getY() + height - 2, 4, 4);
			g.setColor(Color.black);

		}

	}

	@Override
	public void moveTo(int x, int y) {
		upperLeftPoint.moveTo(x, y);

	}

	@Override
	public void moveBy(int x, int y) {
		upperLeftPoint.moveBy(x, y);

	}

	@Override
	public int compareTo(Object obj) {
		if (obj instanceof Rectangle) {
			Rectangle shapeToCompare = (Rectangle) obj;
			return (int) (this.area() - shapeToCompare.area());
		}
		return 0;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public Point getUpperLeftPoint() {
		return upperLeftPoint;
	}

	public void setUpperLeftPoint(Point upperLeftPoint) {
		this.upperLeftPoint = upperLeftPoint;
	}


	public String toString() {
	    Color color = getColor();
	    Color innerColor = getInnerColor();
	    return ":Rectangle:UpperLeftPoint:" + upperLeftPoint.getX() +":"+upperLeftPoint.getY()+":"+"Width:" + width + ":Height:" + height +
	           ":RGB:" + color.getRed() + ":" + color.getGreen() + ":" + color.getBlue() +
	           ":RGB:" + innerColor.getRed() + ":" + innerColor.getGreen() + ":" + innerColor.getBlue();
	}
	public Rectangle clone(Rectangle rectangle) {
		rectangle.getUpperLeftPoint().setX(this.getUpperLeftPoint().getX());
		rectangle.getUpperLeftPoint().setY(this.getUpperLeftPoint().getY());
		rectangle.setHeight(this.getHeight());
		rectangle.setWidth(this.getWidth());
		rectangle.setColor(this.getColor());
		rectangle.setInnerColor(this.getInnerColor());
		return rectangle;

	}

}
