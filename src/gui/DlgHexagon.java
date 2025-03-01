package gui;

import java.awt.BorderLayout;
import geometry.Point;
import hexagon.Hexagon;

import java.awt.FlowLayout;
import adapter.HexagonAdapter;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgHexagon extends JDialog {
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtR;
	private boolean confirm;
	private HexagonAdapter hexagon;
	private JButton btnInnerColorHexagon;
	private JButton btnOutlineColorHexagon;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgHexagon dialog = new DlgHexagon();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgHexagon() {
		setResizable(false);
		setModal(true);
		setTitle("Add/Modify Hexagon");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(255, 0, 255));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (txtX.getText().isEmpty() || txtY.getText().isEmpty() || txtR.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Please, fill in all fields.", "Message",
									JOptionPane.ERROR_MESSAGE);
						} else {
							try {
								if (Integer.parseInt(txtX.getText()) < 0 || Integer.parseInt(txtY.getText()) < 0
										|| Integer.parseInt(txtR.getText()) <= 0) {
									JOptionPane.showMessageDialog(null, "Values must be greater than 0!", "Message",
											JOptionPane.ERROR_MESSAGE);
								} else {
									hexagon=new HexagonAdapter(new Hexagon(Integer.parseInt(txtX.getText()),Integer.parseInt(txtY.getText()),Integer.parseInt(txtR.getText())));
									hexagon.setColor(btnOutlineColorHexagon.getBackground());
                                    hexagon.setInnerColor(btnInnerColorHexagon.getBackground());

									setConfirm(true);
									setVisible(false);

								}

							} catch (Exception NumberFormatException) {
								JOptionPane.showMessageDialog(null, "Enter only integers!", "Message",
										JOptionPane.ERROR_MESSAGE);
							}

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
		{
			JPanel pnlNorth = new JPanel();
			pnlNorth.setBackground(new Color(255, 0, 255));
			getContentPane().add(pnlNorth, BorderLayout.NORTH);
			GridBagLayout gbl_pnlNorth = new GridBagLayout();
			gbl_pnlNorth.columnWidths = new int[] { 190, 51, 0 };
			gbl_pnlNorth.rowHeights = new int[] { 26, 0 };
			gbl_pnlNorth.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
			gbl_pnlNorth.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
			pnlNorth.setLayout(gbl_pnlNorth);
			{
				JLabel lblCenter = new JLabel("  Center");
				lblCenter.setForeground(new Color(255, 255, 255));
				lblCenter.setFont(new Font("Tahoma", Font.BOLD, 15));
				GridBagConstraints gbc_lblCenter = new GridBagConstraints();
				gbc_lblCenter.gridwidth = 2;
				gbc_lblCenter.fill = GridBagConstraints.HORIZONTAL;
				gbc_lblCenter.gridx = 0;
				gbc_lblCenter.gridy = 0;
				pnlNorth.add(lblCenter, gbc_lblCenter);
			}
		}
		{
			JPanel pnlCenter = new JPanel();
			pnlCenter.setBackground(SystemColor.textHighlight);
			getContentPane().add(pnlCenter, BorderLayout.CENTER);
			GridBagLayout gbl_pnlCenter = new GridBagLayout();
			gbl_pnlCenter.columnWidths = new int[] { 100, 0, 0 };
			gbl_pnlCenter.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
			gbl_pnlCenter.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
			gbl_pnlCenter.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
			pnlCenter.setLayout(gbl_pnlCenter);
			{
				Component horizontalStrut = Box.createHorizontalStrut(20);
				GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
				gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
				gbc_horizontalStrut.gridx = 0;
				gbc_horizontalStrut.gridy = 0;
				pnlCenter.add(horizontalStrut, gbc_horizontalStrut);
			}
			{
				JLabel lblXCoordinate = new JLabel("X coordinate");
				lblXCoordinate.setForeground(new Color(255, 255, 255));
				lblXCoordinate.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
				GridBagConstraints gbc_lblXCoordinate = new GridBagConstraints();
				gbc_lblXCoordinate.insets = new Insets(0, 0, 5, 5);
				gbc_lblXCoordinate.gridx = 0;
				gbc_lblXCoordinate.gridy = 1;
				pnlCenter.add(lblXCoordinate, gbc_lblXCoordinate);
			}
			{
				txtX = new JTextField();
				GridBagConstraints gbc_txtX = new GridBagConstraints();
				gbc_txtX.insets = new Insets(0, 0, 5, 0);
				gbc_txtX.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtX.gridx = 1;
				gbc_txtX.gridy = 1;
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
				gbc_lblYCoordinate.gridy = 2;
				pnlCenter.add(lblYCoordinate, gbc_lblYCoordinate);
			}
			{
				txtY = new JTextField();
				GridBagConstraints gbc_txtY = new GridBagConstraints();
				gbc_txtY.insets = new Insets(0, 0, 5, 0);
				gbc_txtY.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtY.gridx = 1;
				gbc_txtY.gridy = 2;
				pnlCenter.add(txtY, gbc_txtY);
				txtY.setColumns(10);
			}
			{
				JLabel lblRadius = new JLabel("  Radius");
				lblRadius.setForeground(new Color(255, 255, 255));
				lblRadius.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
				GridBagConstraints gbc_lblRadius = new GridBagConstraints();
				gbc_lblRadius.anchor = GridBagConstraints.WEST;
				gbc_lblRadius.insets = new Insets(0, 0, 5, 5);
				gbc_lblRadius.gridx = 0;
				gbc_lblRadius.gridy = 3;
				pnlCenter.add(lblRadius, gbc_lblRadius);
			}
			{
				txtR = new JTextField();
				GridBagConstraints gbc_txtR = new GridBagConstraints();
				gbc_txtR.insets = new Insets(0, 0, 5, 0);
				gbc_txtR.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtR.gridx = 1;
				gbc_txtR.gridy = 3;
				pnlCenter.add(txtR, gbc_txtR);
				txtR.setColumns(10);
			}
			{
				btnInnerColorHexagon = new JButton("Inner Color");
				btnInnerColorHexagon.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Color innerColor = JColorChooser.showDialog(null, "Choose the inner color of the hexagon.",
								btnInnerColorHexagon.getBackground());
						if (innerColor != null) {
							btnInnerColorHexagon.setBackground(innerColor);
						}
					}
				});
				GridBagConstraints gbc_btnInnerColor = new GridBagConstraints();
				gbc_btnInnerColor.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnInnerColor.insets = new Insets(0, 0, 5, 0);
				gbc_btnInnerColor.gridx = 1;
				gbc_btnInnerColor.gridy = 5;
				pnlCenter.add(btnInnerColorHexagon, gbc_btnInnerColor);
			}
			{
				btnOutlineColorHexagon = new JButton("Outline Hexagon");
				btnOutlineColorHexagon.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Color outlineColor = JColorChooser.showDialog(null, "Choose the otuline color of the hexagon.",
								btnOutlineColorHexagon.getBackground());
						if (outlineColor != null) {
							btnOutlineColorHexagon.setBackground(outlineColor);
						}
					}
				});
				GridBagConstraints gbc_btnOutlineColor = new GridBagConstraints();
				gbc_btnOutlineColor.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnOutlineColor.gridx = 1;
				gbc_btnOutlineColor.gridy = 6;
				pnlCenter.add(btnOutlineColorHexagon, gbc_btnOutlineColor);
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

	public JTextField getTxtR() {
		return txtR;
	}

	public void setTxtR(JTextField txtR) {
		this.txtR = txtR;
	}

	public boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}

	public HexagonAdapter getHexagon() {
		return hexagon;
	}

	public void setHexagon(HexagonAdapter hexagon) {
		this.hexagon = hexagon;
	}

	public JButton getBtnInnerColorHexagon() {
		return btnInnerColorHexagon;
	}

	public void setBtnInnerColorHexagon(JButton btnInnerColorHexagon) {
		this.btnInnerColorHexagon = btnInnerColorHexagon;
	}

	public JButton getBtnOutlineColorHexagon() {
		return btnOutlineColorHexagon;
	}

	public void setBtnOutlineColorHexagon(JButton btnOutlineColorHexagon) {
		this.btnOutlineColorHexagon = btnOutlineColorHexagon;
	}

}
