package strategy;

import mvc.DrawingModel;

public class SaveManager {
	private SaveStrategy saveStrategy;

	public SaveManager(SaveStrategy saveStrategy) {
		this.saveStrategy = saveStrategy;
	}

	public void save(String filePath) {
		saveStrategy.saveFile(filePath);
	}

	public String[] load(String filePath, DrawingModel model) {
		return saveStrategy.loadFile(filePath, model);

	}
}