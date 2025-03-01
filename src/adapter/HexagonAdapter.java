package adapter;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import geometry.Point;
import geometry.Shape;
import hexagon.Hexagon;

public class HexagonAdapter extends Shape implements Serializable {

	private Hexagon hexagon;

	public HexagonAdapter() {

	}

	public HexagonAdapter(Hexagon hexagon) {
		this.hexagon = hexagon;
	}

	public Color getColor() {
		return hexagon.getBorderColor();
	}

	public void setColor(Color color) {
		this.hexagon.setBorderColor(color);
	}

	public Color getInnerColor() {
		return hexagon.getAreaColor();
	}

	public void setInnerColor(Color color) {
		this.hexagon.setAreaColor(color);
	}

	public int getCenterX() {
		return hexagon.getX();
	}

	public void setCenterX(int x) {
		this.hexagon.setX(x);
	}

	public int getCenterY() {
		return hexagon.getY();
	}

	public void setCenterY(int y) {
		this.hexagon.setY(y);
	}

	public int getRadius() {
		return hexagon.getR();
	}

	public void setRadius(int r) {
		this.hexagon.setR(r);
	}

	@Override
	public void moveTo(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveBy(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		return hexagon.doesContain(x, y);
	}

	@Override
	public void draw(Graphics g) {

		hexagon.paint(g);

		if (this.isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.getCenterX() - 2, this.getCenterY() - 2, 4, 4);
			g.drawRect(this.getCenterX() - this.getRadius() - 2, this.getCenterY() - 2, 4, 4);
			g.drawRect(this.getCenterX() + this.getRadius() - 2, this.getCenterY() - 2, 4, 4);
			g.drawRect(this.getCenterX() - 2, (int) (this.getCenterY() - (this.getRadius() / 2 * Math.sqrt(3)) - 2), 4,
					4);
			g.drawRect(this.getCenterX() - 2, (int) (this.getCenterY() + (this.getRadius() / 2 * Math.sqrt(3)) - 2), 4,
					4);
			g.setColor(Color.BLACK);
		}

	}

	public String toString() {
		Color color = getColor();
		Color innerColor = getInnerColor();
		return ":Hexagon:Center:" + getCenterX() + ":" + getCenterY() + ":Radius:" + getRadius() + ":RGB:"
				+ color.getRed() + ":" + color.getGreen() + ":" + color.getBlue() + ":RGB:" + innerColor.getRed() + ":"
				+ innerColor.getGreen() + ":" + innerColor.getBlue();

	}

	public HexagonAdapter clone(HexagonAdapter hexagon) {
		hexagon.setCenterX(this.getCenterX());
		hexagon.setCenterY(this.getCenterY());
		hexagon.setRadius(this.getRadius());
		hexagon.setColor(this.getColor());
		hexagon.setInnerColor(this.getInnerColor());
		return hexagon;

	}

}