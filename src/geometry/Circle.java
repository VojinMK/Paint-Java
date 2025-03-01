package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Circle extends SurfaceShape implements Serializable {

	private Point center=new Point();
	protected int radius;

	public Circle() {

	}

	public Circle(Point center, int radius) {
		this.center = center;
		this.radius = radius;
	}

	public Circle(Point center, int radius, boolean selected) {
		this(center, radius);
		setSelected(selected);
	}

	public Circle(Point center, int radius, boolean selected, Color color) {
		this(center, radius, selected);
		setColor(color);
	}

	public Circle(Point center, int radius, boolean selected, Color color, Color innerColor) {
		this(center, radius, selected, color);
		setInnerColor(innerColor);
	}

	public boolean equals(Object obj) {
		if (obj instanceof Circle) {
			Circle pomocni = (Circle) obj;
			if (this.center.equals(pomocni.center) && this.radius == pomocni.radius) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean contains(int x, int y) {
		return this.center.distance(x, y) <= radius;
	}

	public boolean contains(Point clickPoint) {
		return this.center.distance(clickPoint.getX(), clickPoint.getY()) <= radius;
	}

	public double area() {
		return radius * radius * Math.PI;
	}

	public double circumfence() {
		return 2 * radius * Math.PI;
	}

	@Override
	public void fill(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(getInnerColor());
		g.fillOval(getCenter().getX() - radius+1, getCenter().getY()+1 - radius, radius * 2-2, radius * 2-2);
	}

	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawOval(center.getX() - radius, center.getY() - radius, 2 * radius, 2 * radius);
		this.fill(g);
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(center.getX() - 2, center.getY() - 2, 4, 4);
			g.drawRect(center.getX() - radius - 2, center.getY() - 2, 4, 4);
			g.drawRect(center.getX() + radius - 2, center.getY() - 2, 4, 4);
			g.drawRect(center.getX() - 2, center.getY() - radius - 2, 4, 4);
			g.drawRect(center.getX() - 2, center.getY() + radius - 2, 4, 4);
			g.setColor(Color.black);

		}

	}

	@Override
	public void moveTo(int x, int y) {
		center.moveTo(x, y);

	}

	@Override
	public void moveBy(int x, int y) {
		center.moveBy(x, y);

	}

	@Override
	public int compareTo(Object obj) {
		if (obj instanceof Circle) {
			Circle shapeToCompare = (Circle) obj;
			return (int) (this.area() - shapeToCompare.area());
		}
		return 0;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public Point getCenter() {
		return center;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) throws Exception {
		if (radius < 0) {
			throw new Exception("Radius ne sme biti manji od 0.");
		} else {
			this.radius = radius;
		}

	}


	public String toString() {
	    Color color = getColor();
	    Color innerColor = getInnerColor();
	    return ":Circle:Center:" + center.getX() +":"+center.getY()+":Radius:" + radius + ":RGB:" + color.getRed() + ":" + color.getGreen() + ":" + color.getBlue() + ":RGB:" + innerColor.getRed() + ":" + innerColor.getGreen() + ":" + innerColor.getBlue();
	}
	
	public Circle clone(Circle circle) {
		circle.getCenter().setX(this.getCenter().getX());
		circle.getCenter().setY(this.getCenter().getY());
		try {
			circle.setRadius(this.getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		circle.setColor(this.getColor());
		circle.setInnerColor(this.getInnerColor());
		return circle;

	}

}
