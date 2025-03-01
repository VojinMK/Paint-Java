package strategy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import geometry.Shape;
import mvc.DrawingModel;

public class SaveLoadBin implements SaveStrategy {

	DrawingModel model;

	public SaveLoadBin(DrawingModel model) {
		this.model = model;
	}

	@Override
	public void saveFile(String filePath) {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
			List<Shape> shapes = model.getShapes();
			outputStream.writeObject(shapes);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String[] loadFile(String filePath, DrawingModel model) {
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
			@SuppressWarnings("unchecked")
			List<Shape> shapes = (List<Shape>) inputStream.readObject();
			model.getShapes().clear();
			for (Shape shape : shapes) {
				shape.setSelected(false);
				model.add(shape);
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}