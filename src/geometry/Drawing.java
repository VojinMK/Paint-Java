package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Drawing extends JPanel {

	public static void main(String agrs[]) {
		JFrame frame = new JFrame("Drawing");
		frame.setSize(800, 600);
		Drawing panelDrawing = new Drawing();
		frame.getContentPane().add(panelDrawing);
		frame.setVisible(true);
	}

	public void paint(Graphics g) {
		Point p = new Point(200, 300);
		// p.draw(g);
		g.setColor(Color.red);
		Point startPoint = new Point(300, 400);
		Line line = new Line(startPoint, new Point(800, 900));
		// line.draw(g);
		g.setColor(Color.black);
		Donut donut = new Donut(new Point(350, 450), 50, 25, true);
		// donut.draw(g);

		// doamci
		Rectangle k1 = new Rectangle(p, 200, 200, true);
		Point c = new Point(p.getX() + (k1.getWidth() / 2), p.getY() + (k1.getHeight() / 2));
		c.draw(g);
		k1.draw(g);
		double inner = 100 * Math.sqrt(2);
		Donut d1 = new Donut(c, (int) inner, 80, true);
		d1.draw(g);

		ArrayList<Shape> shapes = new ArrayList<Shape>();
		Point p1 = new Point(10, 20);
		Point p2 = new Point(20, 30);
		Line l1 = new Line(p1, p2);
		Circle c1 = new Circle(p1, 10);
		Rectangle r1 = new Rectangle(p1, 10, 20);
		Donut don1 = new Donut(p1, 15, 10, true);
		// zadatak 1
		shapes.add(p1);
		shapes.add(l1);
		shapes.add(r1);
		shapes.add(c1);
		shapes.add(don1);
		Iterator<Shape> it = shapes.iterator();
		while (it.hasNext()) {
			Shape sh = it.next();
			sh.moveBy(10, 0);
			System.out.println(sh);

		}

		// zadatak2
		shapes.get(3).draw(g);
		shapes.get(shapes.size() - 1).draw(g);
		shapes.remove(1);
		shapes.get(1).draw(g);
		shapes.get(3).draw(g);
		shapes.add(3, l1);

		// e
		try {
			c1.setRadius(-10);
			System.out.println("try");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		it = shapes.iterator();
		while (it.hasNext()) {
			Shape sh = it.next();
			sh.moveBy(10, 0);
			sh.setSelected(true);
			sh.draw(g);
		}

	}

}
