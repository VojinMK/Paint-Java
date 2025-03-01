package strategy;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import mvc.DrawingFrame;
import mvc.DrawingModel;

public class SaveLoadTxt implements SaveStrategy {
	private DrawingFrame frame;

	public SaveLoadTxt(DrawingFrame frame) {
		this.frame = frame;
	}

	@Override
	public void saveFile(String filePath) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			String textToSave = frame.getTxtCommands().getText();
			writer.write(textToSave);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String[] loadFile(String filePath, DrawingModel model) {
		File file = new File(filePath);
		String[] shape = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			int numberOfLines = countLines(file);
			shape = new String[numberOfLines];
			int i = 0;
			while ((line = reader.readLine()) != null) {
				shape[i] = line;
				i++;
			}
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return shape;
	}

	private int countLines(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int lines = 0;
		while (reader.readLine() != null)
			lines++;
		reader.close();
		return lines;
	}

}