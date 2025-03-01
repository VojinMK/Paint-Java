package mvc;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DrawingFrame extends JFrame {

	DrawingView view = new DrawingView();
	DrawingController controller;

	private JPanel contentPane = new JPanel();
	private String element;
	private JToggleButton tglbtnPoint = new JToggleButton("Point");
	private JToggleButton tglbtnLine = new JToggleButton("Line");
	private JToggleButton tglbtnRectangle = new JToggleButton("Rectangle");
	private JToggleButton tglbtnCircle = new JToggleButton("Circle");
	private JToggleButton tglbtnDonut = new JToggleButton("Donut");

	public JToggleButton tglbtnSelect = new JToggleButton("Select");
	private final JButton btnUndo = new JButton("Undo");
	private final JButton btnRedo = new JButton("Redo");
	private JTextArea txtCommands;
	private final JToggleButton tglbtnHexagon = new JToggleButton("Hexagon");
	private JButton btnOutlineColor;
	private JButton btnInnerColor;
	JButton btnModify;
	JButton btnDelete;
	JButton btnBringToBack;
	JButton btnBringToFront;
	JButton btnToBack;
	JButton btnToFront;

	public Color outlineColor;
	public Color innerColor;

	public DrawingFrame() {
		setTitle("Cetkovic Vojin IT 21/2021");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1033, 717);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.textHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		view.setBounds(5, 30, 741, 534);
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!tglbtnSelect.isSelected()) {
					controller.drawElement(e.getX(), e.getY());

				} else {
					controller.select(e.getX(), e.getY());
					repaint();
				}

			}
		});
		contentPane.setLayout(null);

		view.setBackground(Color.WHITE);
		contentPane.add(view, BorderLayout.CENTER);

		JPanel pnlNorth = new JPanel();
		pnlNorth.setBounds(12, 5, 741, 25);
		pnlNorth.setBackground(SystemColor.textHighlight);
		contentPane.add(pnlNorth);
		GridBagLayout gbl_pnlNorth = new GridBagLayout();
		gbl_pnlNorth.columnWidths = new int[] { 126, 126, 126, 126, 126, 0, 0, 0, 0 };
		gbl_pnlNorth.rowHeights = new int[] { 25, 0 };
		gbl_pnlNorth.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_pnlNorth.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		pnlNorth.setLayout(gbl_pnlNorth);

		GridBagConstraints gbc_tglbtnPoint = new GridBagConstraints();
		gbc_tglbtnPoint.fill = GridBagConstraints.BOTH;
		gbc_tglbtnPoint.insets = new Insets(0, 0, 0, 5);
		gbc_tglbtnPoint.gridx = 0;
		gbc_tglbtnPoint.gridy = 0;
		pnlNorth.add(tglbtnPoint, gbc_tglbtnPoint);

		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(tglbtnPoint);
		GridBagConstraints gbc_tglbtnLine = new GridBagConstraints();
		gbc_tglbtnLine.fill = GridBagConstraints.BOTH;
		gbc_tglbtnLine.insets = new Insets(0, 0, 0, 5);
		gbc_tglbtnLine.gridx = 1;
		gbc_tglbtnLine.gridy = 0;
		pnlNorth.add(tglbtnLine, gbc_tglbtnLine);
		btnGroup.add(tglbtnLine);
		GridBagConstraints gbc_tglbtnCircle = new GridBagConstraints();
		gbc_tglbtnCircle.fill = GridBagConstraints.BOTH;
		gbc_tglbtnCircle.insets = new Insets(0, 0, 0, 5);
		gbc_tglbtnCircle.gridx = 2;
		gbc_tglbtnCircle.gridy = 0;
		pnlNorth.add(tglbtnCircle, gbc_tglbtnCircle);
		btnGroup.add(tglbtnCircle);
		GridBagConstraints gbc_tglbtnDonut = new GridBagConstraints();
		gbc_tglbtnDonut.fill = GridBagConstraints.BOTH;
		gbc_tglbtnDonut.insets = new Insets(0, 0, 0, 5);
		gbc_tglbtnDonut.gridx = 3;
		gbc_tglbtnDonut.gridy = 0;
		pnlNorth.add(tglbtnDonut, gbc_tglbtnDonut);
		btnGroup.add(tglbtnDonut);

		GridBagConstraints gbc_tglbtnRectangle = new GridBagConstraints();
		gbc_tglbtnRectangle.insets = new Insets(0, 0, 0, 5);
		gbc_tglbtnRectangle.fill = GridBagConstraints.BOTH;
		gbc_tglbtnRectangle.gridx = 4;
		gbc_tglbtnRectangle.gridy = 0;
		pnlNorth.add(tglbtnRectangle, gbc_tglbtnRectangle);
		btnGroup.add(tglbtnRectangle);

		GridBagConstraints gbc_tglbtnHexagon = new GridBagConstraints();
		gbc_tglbtnHexagon.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnHexagon.gridwidth = 3;
		gbc_tglbtnHexagon.insets = new Insets(0, 0, 0, 5);
		gbc_tglbtnHexagon.gridx = 5;
		gbc_tglbtnHexagon.gridy = 0;
		pnlNorth.add(tglbtnHexagon, gbc_tglbtnHexagon);
		btnGroup.add(tglbtnHexagon);

		JPanel pnlSouth = new JPanel();
		pnlSouth.setBounds(5, 564, 741, 25);
		contentPane.add(pnlSouth);
		pnlSouth.setBackground(SystemColor.textHighlight);
		pnlSouth.setLayout(new BoxLayout(pnlSouth, BoxLayout.X_AXIS));
		tglbtnSelect.setEnabled(false);
		pnlSouth.add(tglbtnSelect);

		btnModify = new JButton("Modify");
		btnModify.setEnabled(false);
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				controller.modify();
				
			}
		});
		pnlSouth.add(btnModify);
		btnGroup.add(btnModify);

		btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.delete();
				tglbtnSelect.setSelected(false);
			}
		});
		pnlSouth.add(btnDelete);
		btnGroup.add(btnDelete);
		btnUndo.setEnabled(false);
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.undo();
			}
		});
		btnUndo.setHorizontalAlignment(SwingConstants.RIGHT);

		pnlSouth.add(btnUndo);
		btnRedo.setEnabled(false);
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.redo();
			}
		});

		pnlSouth.add(btnRedo);

		btnBringToBack = new JButton("Bring to back");
		btnBringToBack.setEnabled(false);
		btnBringToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.bringToBack();
			}
		});
		pnlSouth.add(btnBringToBack);

		btnBringToFront = new JButton("Bring to front");
		btnBringToFront.setEnabled(false);
		btnBringToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.bringToFront();
			}
		});
		pnlSouth.add(btnBringToFront);

		btnToBack = new JButton("To back");
		btnToBack.setEnabled(false);
		btnToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.toBack();
			}
		});
		pnlSouth.add(btnToBack);

		btnToFront = new JButton("To front");
		btnToFront.setEnabled(false);
		btnToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.toFront();
			}
		});
		pnlSouth.add(btnToFront);

		view.repaint();
		getContentPane().add(view, BorderLayout.CENTER);

		txtCommands = new JTextArea();
		txtCommands.setEditable(false);
		txtCommands.setBounds(843, 5, 169, 559);
		txtCommands.setColumns(100);
		JScrollPane scroll = new JScrollPane();
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(txtCommands);

		btnOutlineColor = new JButton("Outline Color");
		btnOutlineColor.setBackground(Color.black);
		btnOutlineColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				outlineColor = JColorChooser.showDialog(null, "Select color", Color.black);
				btnOutlineColor.setBackground(outlineColor);
			}
		});
		btnOutlineColor.setBounds(796, 564, 107, 25);
		contentPane.add(btnOutlineColor);

		btnInnerColor = new JButton("Inner Color");
		btnInnerColor.setBackground(Color.white);
		btnInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				innerColor = JColorChooser.showDialog(null, "Select color", Color.WHITE);
				btnInnerColor.setBackground(innerColor);
			}
		});
		btnInnerColor.setBounds(915, 564, 97, 25);
		contentPane.add(btnInnerColor);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		fileMenu.setFont(new Font("Tahoma", Font.PLAIN, 15));
		menuBar.add(fileMenu);

		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                 controller.saveClicked();
			}
		});
		saveMenuItem.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fileMenu.add(saveMenuItem);

		JMenuItem loadBinMenuItem = new JMenuItem("Load bin file");
		loadBinMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.loadBinFile();
			}
		});
		loadBinMenuItem.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fileMenu.add(loadBinMenuItem);

		JMenuItem loadTxtMenuItem = new JMenuItem("Load txt file");
		loadTxtMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.loadTxtFile();
			}
		});
		loadTxtMenuItem.setFont(new Font("Tahoma", Font.PLAIN, 15));
		loadBinMenuItem.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fileMenu.add(loadTxtMenuItem);

	}

	public DrawingView getView() {
		return view;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}

	public JToggleButton getTglbtnPoint() {
		return tglbtnPoint;
	}

	public void setTglbtnPoint(JToggleButton tglbtnPoint) {
		this.tglbtnPoint = tglbtnPoint;
	}

	public Color getOutlineColor() {
		return outlineColor;
	}

	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}

	public Color getInnerColor() {
		return innerColor;
	}

	public void setInnerColor(Color innerColor) {
		this.innerColor = innerColor;
	}

	public JToggleButton getTglbtnLine() {
		return tglbtnLine;
	}

	public void setTglbtnLine(JToggleButton tglbtnLine) {
		this.tglbtnLine = tglbtnLine;
	}

	public JToggleButton getTglbtnRectangle() {
		return tglbtnRectangle;
	}

	public void setTglbtnRectangle(JToggleButton tglbtnRectangle) {
		this.tglbtnRectangle = tglbtnRectangle;
	}

	public JToggleButton getTglbtnCircle() {
		return tglbtnCircle;
	}

	public void setTglbtnCircle(JToggleButton tglbtnCircle) {
		this.tglbtnCircle = tglbtnCircle;
	}

	public JToggleButton getTglbtnDonut() {
		return tglbtnDonut;
	}

	public void setTglbtnDonut(JToggleButton tglbtnDonut) {
		this.tglbtnDonut = tglbtnDonut;
	}

	public JToggleButton getTglbtnSelect() {
		return tglbtnSelect;
	}

	public void setTglbtnSelect(JToggleButton tglbtnSelect) {
		this.tglbtnSelect = tglbtnSelect;
	}

	public JTextArea getTxtCommands() {
		return txtCommands;
	}

	public JButton getBtnOutlineColor() {
		return btnOutlineColor;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public JToggleButton getTglbtnHexagon() {
		return tglbtnHexagon;
	}

	public JButton getBtnUndo() {
		return btnUndo;
	}

	public JButton getBtnRedo() {
		return btnRedo;
	}

	public JButton getBtnModify() {
		return btnModify;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public JButton getBtnBringToBack() {
		return btnBringToBack;
	}

	public JButton getBtnBringToFront() {
		return btnBringToFront;
	}

	public JButton getBtnToBack() {
		return btnToBack;
	}

	public JButton getBtnToFront() {
		return btnToFront;
	}

}
