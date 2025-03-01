package mvc;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Application {

	public static void main(String[] args) {

		// realizacija mvc arhitekture
		DrawingModel model = new DrawingModel();
		DrawingFrame frame = new DrawingFrame();
		frame.getTglbtnPoint().setSize(133, 25);
		frame.getTglbtnPoint().setLocation(0, 5);
		frame.getView().setBounds(5, 30, 818, 534);

		JScrollPane scrollPane = new JScrollPane(frame.getTxtCommands());
		scrollPane.setSize(335, 558);
		scrollPane.setLocation(957, 6);

		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		frame.getTxtCommands().setSize(345, 558);
		frame.getTxtCommands().setLocation(760, 6);
		frame.getContentPane().add(scrollPane);
		frame.getView().setModel(model);
		DrawingController controller = new DrawingController(model, frame);
		frame.setController(controller);

		// frame
		frame.setSize(1310, 670);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
}
