package gui;

import java.awt.BorderLayout;
import geometry.Line;
import geometry.Point;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgLine extends JDialog {
	private JTextField txtXSP;
	private JTextField txtYSP;
	private JTextField txtXEP;
	private JTextField txtYEP;
	private boolean confirm;
	private Line line;
	private Color colorLine = Color.black;
	private JButton btnColorLine;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgLine dialog = new DlgLine();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgLine() {
		setTitle("Add/Modify Line");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());

		{
			JPanel pnlCenter = new JPanel();
			pnlCenter.setBackground(SystemColor.textHighlight);
			getContentPane().add(pnlCenter, BorderLayout.CENTER);
			GridBagLayout gbl_pnlCenter = new GridBagLayout();
			gbl_pnlCenter.columnWidths = new int[] { 102, 0, 0, 29, 105, 0, 0 };
			gbl_pnlCenter.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			gbl_pnlCenter.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
			gbl_pnlCenter.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
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
				JLabel lblXSP = new JLabel("X coordinate");
				lblXSP.setForeground(Color.WHITE);
				lblXSP.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
				GridBagConstraints gbc_lblXSP = new GridBagConstraints();
				gbc_lblXSP.insets = new Insets(0, 0, 5, 5);
				gbc_lblXSP.gridx = 0;
				gbc_lblXSP.gridy = 1;
				pnlCenter.add(lblXSP, gbc_lblXSP);
			}
			{
				txtXSP = new JTextField();
				GridBagConstraints gbc_txtXSP = new GridBagConstraints();
				gbc_txtXSP.insets = new Insets(0, 0, 5, 5);
				gbc_txtXSP.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtXSP.gridx = 1;
				gbc_txtXSP.gridy = 1;
				pnlCenter.add(txtXSP, gbc_txtXSP);
				txtXSP.setColumns(10);
			}
			{
				JLabel lblXEP = new JLabel("X coordinate");
				lblXEP.setForeground(new Color(255, 255, 255));
				lblXEP.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
				GridBagConstraints gbc_lblXEP = new GridBagConstraints();
				gbc_lblXEP.insets = new Insets(0, 0, 5, 5);
				gbc_lblXEP.gridx = 4;
				gbc_lblXEP.gridy = 1;
				pnlCenter.add(lblXEP, gbc_lblXEP);
			}
			{
				txtXEP = new JTextField();
				GridBagConstraints gbc_txtXEP = new GridBagConstraints();
				gbc_txtXEP.insets = new Insets(0, 0, 5, 0);
				gbc_txtXEP.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtXEP.gridx = 5;
				gbc_txtXEP.gridy = 1;
				pnlCenter.add(txtXEP, gbc_txtXEP);
				txtXEP.setColumns(10);
			}
			{
				JLabel lblYSP = new JLabel("Y coordinate");
				lblYSP.setForeground(new Color(255, 255, 255));
				lblYSP.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
				GridBagConstraints gbc_lblYSP = new GridBagConstraints();
				gbc_lblYSP.insets = new Insets(0, 0, 5, 5);
				gbc_lblYSP.gridx = 0;
				gbc_lblYSP.gridy = 2;
				pnlCenter.add(lblYSP, gbc_lblYSP);
			}
			{
				txtYSP = new JTextField();
				GridBagConstraints gbc_txtYSP = new GridBagConstraints();
				gbc_txtYSP.insets = new Insets(0, 0, 5, 5);
				gbc_txtYSP.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtYSP.gridx = 1;
				gbc_txtYSP.gridy = 2;
				pnlCenter.add(txtYSP, gbc_txtYSP);
				txtYSP.setColumns(10);
			}
			{
				JLabel lblYEP = new JLabel("Y coordinate");
				lblYEP.setForeground(new Color(255, 255, 255));
				lblYEP.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
				GridBagConstraints gbc_lblYEP = new GridBagConstraints();
				gbc_lblYEP.insets = new Insets(0, 0, 5, 5);
				gbc_lblYEP.gridx = 4;
				gbc_lblYEP.gridy = 2;
				pnlCenter.add(lblYEP, gbc_lblYEP);
			}
			{
				txtYEP = new JTextField();
				GridBagConstraints gbc_txtYEP = new GridBagConstraints();
				gbc_txtYEP.insets = new Insets(0, 0, 5, 0);
				gbc_txtYEP.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtYEP.gridx = 5;
				gbc_txtYEP.gridy = 2;
				pnlCenter.add(txtYEP, gbc_txtYEP);
				txtYEP.setColumns(10);
			}
			{
				btnColorLine = new JButton("Color");
				btnColorLine.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						 colorLine = JColorChooser.showDialog(null, "Choose line color",
								btnColorLine.getBackground());
						if (colorLine != null)
							btnColorLine.setBackground(colorLine);

					}

				});
				GridBagConstraints gbc_btnColorLine = new GridBagConstraints();
				gbc_btnColorLine.gridwidth = 2;
				gbc_btnColorLine.fill = GridBagConstraints.BOTH;
				gbc_btnColorLine.gridx = 4;
				gbc_btnColorLine.gridy = 7;
				pnlCenter.add(btnColorLine, gbc_btnColorLine);
			}
		}

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(255, 0, 255));
			buttonPane.setForeground(new Color(0, 0, 0));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (txtXSP.getText().isEmpty() || txtYSP.getText().isEmpty() || txtXEP.getText().isEmpty()
								|| txtYEP.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Please, fill in all fields.", "message",
									JOptionPane.ERROR_MESSAGE);

						} else {
							try {
								if (Integer.parseInt(txtXSP.getText()) < 0 || Integer.parseInt(txtYSP.getText()) < 0
										|| Integer.parseInt(txtXEP.getText()) < 0
										|| Integer.parseInt(txtYEP.getText()) < 0) {
									JOptionPane.showMessageDialog(null, "Coordinates must be greater than 0!",
											"message", JOptionPane.ERROR_MESSAGE);
								} else {
									line = new Line(
											new Point(Integer.parseInt(txtXSP.getText()),
													Integer.parseInt(txtYSP.getText())),
											new Point(Integer.parseInt(txtXEP.getText()),
													Integer.parseInt(txtYEP.getText())),
											false, btnColorLine.getBackground());
									confirm = true;
									setVisible(false);

								}
							} catch (Exception NumberFormatException) {
								JOptionPane.showMessageDialog(null, "Coordinates must be integers.!", "Message",
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
			pnlNorth.setForeground(new Color(0, 0, 0));
			getContentPane().add(pnlNorth, BorderLayout.NORTH);
			GridBagLayout gbl_pnlNorth = new GridBagLayout();
			gbl_pnlNorth.columnWidths = new int[] { 176, 80, 0, 0, 0, 0, 0, 0 };
			gbl_pnlNorth.rowHeights = new int[] { 16, 0 };
			gbl_pnlNorth.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
			gbl_pnlNorth.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
			pnlNorth.setLayout(gbl_pnlNorth);
			{
				JLabel lblStartPoint = new JLabel("Start Point");
				lblStartPoint.setFont(new Font("Tahoma", Font.BOLD, 15));
				lblStartPoint.setForeground(new Color(255, 255, 255));
				GridBagConstraints gbc_lblStartPoint = new GridBagConstraints();
				gbc_lblStartPoint.insets = new Insets(0, 0, 0, 5);
				gbc_lblStartPoint.anchor = GridBagConstraints.NORTH;
				gbc_lblStartPoint.gridx = 0;
				gbc_lblStartPoint.gridy = 0;
				pnlNorth.add(lblStartPoint, gbc_lblStartPoint);
			}
			{
				JLabel lblEndPoint = new JLabel("End Point");
				lblEndPoint.setFont(new Font("Tahoma", Font.BOLD, 15));
				lblEndPoint.setForeground(new Color(255, 255, 255));
				GridBagConstraints gbc_lblEndPoint = new GridBagConstraints();
				gbc_lblEndPoint.gridwidth = 5;
				gbc_lblEndPoint.gridx = 2;
				gbc_lblEndPoint.gridy = 0;
				pnlNorth.add(lblEndPoint, gbc_lblEndPoint);
			}
		}

	}

	public JTextField getTxtXSP() {
		return txtXSP;
	}

	public void setTxtXSP(JTextField txtXSP) {
		this.txtXSP = txtXSP;
	}

	public JTextField getTxtYSP() {
		return txtYSP;
	}

	public void setTxtYSP(JTextField txtYSP) {
		this.txtYSP = txtYSP;
	}

	public JTextField getTxtXEP() {
		return txtXEP;
	}

	public void setTxtXEP(JTextField txtXEP) {
		this.txtXEP = txtXEP;
	}

	public JTextField getTxtYEP() {
		return txtYEP;
	}

	public void setTxtYEP(JTextField txtYEP) {
		this.txtYEP = txtYEP;
	}

	public boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	public JButton getBtnColorLine() {
		return btnColorLine;
	}

	public void setBtnColorLine(JButton btnColor) {
		this.btnColorLine = btnColor;
	}

	public Color getColor() {
		return colorLine;
	}

	public void setColor(Color colorLine) {
		this.colorLine = colorLine;
	}

}
