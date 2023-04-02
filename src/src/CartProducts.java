package src;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JButton;

public class CartProducts extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1421200220099908624L;

	/**
	 * Create the panel.
	 */
	public CartProducts(String name,Float price,int qty) {
		setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 160, 122));
		panel.setBounds(0, 0, 200, 158);
		add(panel);
		
		JLabel lblName = new JLabel(name);
		lblName.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		lblName.setBounds(210, 10, 361, 25);
		add(lblName);
		
		JLabel lblQty = new JLabel(String.valueOf(qty));
		lblQty.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblQty.setHorizontalTextPosition(SwingConstants.CENTER);
		lblQty.setForeground(new Color(255, 255, 255));
		lblQty.setOpaque(true);
		lblQty.setBackground(new Color(255, 69, 0));
		lblQty.setHorizontalAlignment(SwingConstants.CENTER);
		lblQty.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblQty.setBounds(317, 119, 44, 25);
		add(lblQty);
		String pr = String.format("₹%.2f", price);
		JLabel lblPrice = new JLabel(pr);
		lblPrice.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrice.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblPrice.setBounds(361, 119, 109, 25);
		add(lblPrice);
		
		JButton btnNewButton = new JButton("Remove");
		btnNewButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		btnNewButton.setBounds(480, 115, 91, 33);
		add(btnNewButton);
		
		JButton btnChangeQty = new JButton("Edit");
		btnChangeQty.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		btnChangeQty.setBounds(210, 115, 91, 33);
		add(btnChangeQty);

	}
}
