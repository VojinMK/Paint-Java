package strategy;

import mvc.DrawingModel;

public interface SaveStrategy {
	void saveFile(String filePath);

	String[] loadFile(String filePath, DrawingModel model);
}