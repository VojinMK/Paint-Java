package gui;

import java.awt.BorderLayout;
import geometry.Rectangle;
import geometry.Point;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgRectangle extends JDialog {
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtHeight;
	private JTextField txtWidth;
	private boolean confirm;
	private JButton btnInnerColorRect;
	private JButton btnOutlineColorRect;
	private Rectangle rect;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgRectangle dialog = new DlgRectangle();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgRectangle() {
		setTitle("Add/Modify Rectangle");
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());

		{
			JPanel pnlNorth = new JPanel();
			pnlNorth.setBackground(new Color(255, 0, 255));
			getContentPane().add(pnlNorth, BorderLayout.NORTH);
			GridBagLayout gbl_pnlNorth = new GridBagLayout();
			gbl_pnlNorth.columnWidths = new int[] { 220, 91, 0 };
			gbl_pnlNorth.rowHeights = new int[] { 26, 0 };
			gbl_pnlNorth.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
			gbl_pnlNorth.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
			pnlNorth.setLayout(gbl_pnlNorth);
			{
				JLabel lblUpperLeftPoint = new JLabel("  Upper Left Point");
				lblUpperLeftPoint.setForeground(new Color(255, 255, 255));
				lblUpperLeftPoint.setVerticalAlignment(SwingConstants.TOP);
				lblUpperLeftPoint.setFont(new Font("Tahoma", Font.BOLD, 15));
				GridBagConstraints gbc_lblUpperLeftPoint = new GridBagConstraints();
				gbc_lblUpperLeftPoint.gridwidth = 2;
				gbc_lblUpperLeftPoint.anchor = GridBagConstraints.WEST;
				gbc_lblUpperLeftPoint.gridx = 0;
				gbc_lblUpperLeftPoint.gridy = 0;
				pnlNorth.add(lblUpperLeftPoint, gbc_lblUpperLeftPoint);
			}
		}
		{
			JPanel pnlCenter = new JPanel();
			pnlCenter.setBackground(SystemColor.textHighlight);
			getContentPane().add(pnlCenter, BorderLayout.CENTER);
			GridBagLayout gbl_pnlCenter = new GridBagLayout();
			gbl_pnlCenter.columnWidths = new int[] { 100, 0, 0 };
			gbl_pnlCenter.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			gbl_pnlCenter.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
			gbl_pnlCenter.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
			pnlCenter.setLayout(gbl_pnlCenter);
			{
				Component horizontalStrut = Box.createHorizontalStrut(20);
				GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
				gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
				gbc_horizontalStrut.gridx = 0;
				gbc_horizontalStrut.gridy = 1;
				pnlCenter.add(horizontalStrut, gbc_horizontalStrut);
			}
			{
				JLabel lblXCoordinate = new JLabel("X coordinate");
				lblXCoordinate.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
				lblXCoordinate.setForeground(new Color(255, 255, 255));
				GridBagConstraints gbc_lblXCoordinate = new GridBagConstraints();
				gbc_lblXCoordinate.insets = new Insets(0, 0, 5, 5);
				gbc_lblXCoordinate.gridx = 0;
				gbc_lblXCoordinate.gridy = 2;
				pnlCenter.add(lblXCoordinate, gbc_lblXCoordinate);
			}
			{
				txtX = new JTextField();
				GridBagConstraints gbc_txtX = new GridBagConstraints();
				gbc_txtX.insets = new Insets(0, 0, 5, 0);
				gbc_txtX.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtX.gridx = 1;
				gbc_txtX.gridy = 2;
				pnlCenter.add(txtX, gbc_txtX);
				txtX.setColumns(10);
			}
			{
				JLabel lblYCoordinate = new JLabel("Y coordinate");
				lblYCoordinate.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
				lblYCoordinate.setForeground(new Color(255, 255, 255));
				GridBagConstraints gbc_lblYCoordinate = new GridBagConstraints();
				gbc_lblYCoordinate.insets = new Insets(0, 0, 5, 5);
				gbc_lblYCoordinate.gridx = 0;
				gbc_lblYCoordinate.gridy = 3;
				pnlCenter.add(lblYCoordinate, gbc_lblYCoordinate);
			}
			{
				txtY = new JTextField();
				GridBagConstraints gbc_txtY = new GridBagConstraints();
				gbc_txtY.insets = new Insets(0, 0, 5, 0);
				gbc_txtY.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtY.gridx = 1;
				gbc_txtY.gridy = 3;
				pnlCenter.add(txtY, gbc_txtY);
				txtY.setColumns(10);
			}
			{
				JLabel lblHeight = new JLabel("  Height");
				lblHeight.setForeground(new Color(255, 255, 255));
				lblHeight.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
				GridBagConstraints gbc_lblHeight = new GridBagConstraints();
				gbc_lblHeight.anchor = GridBagConstraints.WEST;
				gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
				gbc_lblHeight.gridx = 0;
				gbc_lblHeight.gridy = 4;
				pnlCenter.add(lblHeight, gbc_lblHeight);
			}
			{
				txtHeight = new JTextField();
				GridBagConstraints gbc_txtHeight = new GridBagConstraints();
				gbc_txtHeight.insets = new Insets(0, 0, 5, 0);
				gbc_txtHeight.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtHeight.gridx = 1;
				gbc_txtHeight.gridy = 4;
				pnlCenter.add(txtHeight, gbc_txtHeight);
				txtHeight.setColumns(10);
			}
			{
				JLabel lblWidth = new JLabel("  Width");
				lblWidth.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
				lblWidth.setForeground(new Color(255, 255, 255));
				GridBagConstraints gbc_lblWidth = new GridBagConstraints();
				gbc_lblWidth.anchor = GridBagConstraints.WEST;
				gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
				gbc_lblWidth.gridx = 0;
				gbc_lblWidth.gridy = 5;
				pnlCenter.add(lblWidth, gbc_lblWidth);
			}
			{
				txtWidth = new JTextField();
				GridBagConstraints gbc_txtWidth = new GridBagConstraints();
				gbc_txtWidth.insets = new Insets(0, 0, 5, 0);
				gbc_txtWidth.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtWidth.gridx = 1;
				gbc_txtWidth.gridy = 5;
				pnlCenter.add(txtWidth, gbc_txtWidth);
				txtWidth.setColumns(10);
			}
			{
				btnInnerColorRect = new JButton("Inner Color");
				btnInnerColorRect.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Color innerColor = JColorChooser.showDialog(null, "Choose the inner color of the rectangle.",
								btnInnerColorRect.getBackground());
						if (innerColor != null) {
							btnInnerColorRect.setBackground(innerColor);
						}
					}
				});
				GridBagConstraints gbc_btnInnerColor = new GridBagConstraints();
				gbc_btnInnerColor.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnInnerColor.insets = new Insets(0, 0, 5, 0);
				gbc_btnInnerColor.gridx = 1;
				gbc_btnInnerColor.gridy = 6;
				pnlCenter.add(btnInnerColorRect, gbc_btnInnerColor);
			}
			{
				btnOutlineColorRect = new JButton("Outline Color");
				btnOutlineColorRect.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Color outlineColor = JColorChooser.showDialog(null,
								"Choose the otuline color of the rectangle.", btnOutlineColorRect.getBackground());
						if (outlineColor != null) {
							btnOutlineColorRect.setBackground(outlineColor);
						}
					}
				});
				GridBagConstraints gbc_btnOutlineColor = new GridBagConstraints();
				gbc_btnOutlineColor.insets = new Insets(0, 0, 5, 0);
				gbc_btnOutlineColor.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnOutlineColor.gridx = 1;
				gbc_btnOutlineColor.gridy = 7;
				pnlCenter.add(btnOutlineColorRect, gbc_btnOutlineColor);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(255, 0, 255));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						try {
							if (txtX.getText().isEmpty() || txtY.getText().isEmpty() || txtWidth.getText().isEmpty()
									|| txtHeight.getText().isEmpty()) {
								JOptionPane.showMessageDialog(null, "Please, fill in all fields.", "Message",
										JOptionPane.ERROR_MESSAGE);
								setConfirm(false);
							} else {
								if (Integer.parseInt(txtX.getText()) < 0 || Integer.parseInt(txtY.getText()) < 0
										|| Integer.parseInt(txtWidth.getText()) <= 0
										|| Integer.parseInt(txtHeight.getText()) <= 0) {

									JOptionPane.showMessageDialog(null, "Values must be greater than 0!", "Message",
											JOptionPane.ERROR_MESSAGE);

								} else {
									rect = new Rectangle(
											new Point(Integer.parseInt(getTxtX().getText().toString()),
													Integer.parseInt(getTxtY().getText().toString())),
											Integer.parseInt(getTxtWidth().getText().toString()),
											Integer.parseInt(getTxtHeight().getText().toString()), false,
											btnOutlineColorRect.getBackground(), btnInnerColorRect.getBackground());
									setConfirm(true);
									setVisible(false);
								}
							}

						} catch (Exception NumberFormatException) {
							JOptionPane.showMessageDialog(null, "Enter only integers!", "Message",
									JOptionPane.ERROR_MESSAGE);
						}

					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

	}

	public JTextField getTxtX() {
		return txtX;
	}

	public void setTxtX(JTextField txtX) {
		this.txtX = txtX;
	}

	public JTextField getTxtY() {
		return txtY;
	}

	public void setTxtY(JTextField txtY) {
		this.txtY = txtY;
	}

	public JTextField getTxtHeight() {
		return txtHeight;
	}

	public void setTxtHeight(JTextField txtHeight) {
		this.txtHeight = txtHeight;
	}

	public JTextField getTxtWidth() {
		return txtWidth;
	}

	public void setTxtWidth(JTextField txtWidth) {
		this.txtWidth = txtWidth;
	}

	public boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}

	public JButton getBtnInnerColorRect() {
		return btnInnerColorRect;
	}

	public void setBtnInnerColorRect(JButton btnInnerColorRect) {
		this.btnInnerColorRect = btnInnerColorRect;
	}

	public JButton getBtnOutlineColorRect() {
		return btnOutlineColorRect;
	}

	public void setBtnOutlineColorRect(JButton btnOutlineColorRect) {
		this.btnOutlineColorRect = btnOutlineColorRect;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

}
