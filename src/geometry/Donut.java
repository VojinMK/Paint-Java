package geometry;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

public class Donut extends Circle implements Serializable {

	public int innerRadius;

	public Donut() {

	}

	public Donut(Point center, int radius, int innerRadius) {
		super(center, radius);
		this.innerRadius = innerRadius;
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		super(center, radius, selected);
		this.innerRadius = innerRadius;
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color) {
		this(center, radius, innerRadius, selected);
		setColor(color);
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color, Color innerColor) {
		this(center, radius, innerRadius, selected, color);
		setInnerColor(innerColor);

	}

	public boolean equals(Object obj) {
		if (obj instanceof Donut) {
			Donut pomocni = (Donut) obj;
			if (super.equals(pomocni) && innerRadius == pomocni.innerRadius) {
				return true;
			} else {
				return false;

			}

		} else {
			return false;
		}
	}

	public boolean contains(Point clickPoint) {
		return super.contains(clickPoint)
				&& this.getCenter().distance(clickPoint.getX(), clickPoint.getY()) >= innerRadius;
	}

	public double area() {
		return super.area() - innerRadius * innerRadius * Math.PI;
	}

	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		super.fill(g);
		g.setColor(Color.WHITE);
		g.fillOval(getCenter().getX() - innerRadius, getCenter().getY() - innerRadius, innerRadius * 2,
				this.innerRadius * 2);
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setComposite(AlphaComposite.SrcOver.derive(1.0f));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Ellipse2D outerCircle = new Ellipse2D.Double(getCenter().getX() - radius, getCenter().getY() - radius,
				2 * radius, 2 * radius);
		Ellipse2D innerCircle = new Ellipse2D.Double(getCenter().getX() - innerRadius, getCenter().getY() - innerRadius,
				2 * innerRadius, 2 * innerRadius);

		Area outerArea = new Area(outerCircle);
		Area innerArea = new Area(innerCircle);

		outerArea.subtract(innerArea);

		g2d.setColor(getColor());
		g2d.drawOval(getCenter().getX() - radius, getCenter().getY() - radius, 2 * radius, 2 * radius);
		g2d.drawOval(getCenter().getX() - innerRadius, getCenter().getY() - innerRadius, 2 * innerRadius,
				2 * innerRadius);
		g2d.setColor(getInnerColor());
		g2d.fill(outerArea);

		g2d.dispose();

		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(getCenter().getX() - getRadius() - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() + getRadius() - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() - getRadius() - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() + getRadius() - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() - innerRadius - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() + innerRadius - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() - innerRadius - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() + innerRadius - 2, 4, 4);
			g.setColor(Color.black);
		}

	}

	public int compareTo(Object obj) {
		if (obj instanceof Donut) {
			Donut shapeToCompare = (Donut) obj;
			return (int) (this.area() - shapeToCompare.area());
		}
		return 0;
	}

	public int getInnerRadius() {
		return innerRadius;
	}

	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
	}


	public String toString() {
	    Color color = getColor();
	    Color innerColor = getInnerColor();
	    return ":Donut:Center:" + getCenter().getX() +":"+getCenter().getY()+":Radius:" + radius +":Inner Radius:"+innerRadius+ ":RGB:" + color.getRed() + ":" + color.getGreen() + ":" + color.getBlue() + ":RGB:" + innerColor.getRed() + ":" + innerColor.getGreen() + ":" + innerColor.getBlue();
	}
	
	public Donut clone(Donut donut) {
		donut.getCenter().setX(this.getCenter().getX());
		donut.getCenter().setY(this.getCenter().getY());
		donut.setInnerRadius(this.getInnerRadius());
		try {
			donut.setRadius(this.getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		donut.setColor(this.getColor());
		donut.setInnerColor(this.getInnerColor());
		return donut;

	}
}
