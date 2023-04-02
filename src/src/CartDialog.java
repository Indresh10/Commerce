package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;

import src.Payment.OkListener;

public class CartDialog extends JDialog implements ActionListener,OkListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8163967290394233607L;
	private final JPanel contentPanel = new JPanel();
	private Float total = 0.0f;
	private JLabel lblTotal;
	/**
	 * Create the dialog.
	 */
	public CartDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Cart");
		setModal(true);
		setBounds(100, 100, 925, 554);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			{
				JPanel panel = new JPanel();
				panel.setLayout(new GridBagLayout());
				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.HORIZONTAL;
				for(int i=1;i<=10;i++) {
					String prod = "Product "+i;
					Float price = 100.0f * i;
					Random rand = new Random();
					int qty = rand.nextInt(10-1 +1) + 1;
					c.gridx = 0;
					c.gridy = i-1;
					c.weightx = 1;
					c.ipady = 150;
					c.insets = new Insets(2, 2, 2, 2);
					panel.add(new CartProducts(prod, price, qty),c);
					total += qty*price;
				}
				JScrollPane scrollPane = new JScrollPane(panel);
				contentPanel.add(scrollPane, BorderLayout.CENTER);
			}
		}
		{
			JPanel panel = new JPanel();
			panel.setAlignmentX(Component.RIGHT_ALIGNMENT);
			contentPanel.add(panel, BorderLayout.SOUTH);
			panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
			{
				JLabel lblNewLabel_1 = new JLabel(" Total ");
				lblNewLabel_1.setBackground(new Color(255, 255, 224));
				lblNewLabel_1.setOpaque(true);
				lblNewLabel_1.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
				lblNewLabel_1.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
				panel.add(lblNewLabel_1);
			}
			{
				JLabel lblTotal = new JLabel(String.format("₹ %.2f ", total));
				lblTotal.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
				lblTotal.setOpaque(true);
				lblTotal.setBackground(new Color(238, 232, 170));
				lblTotal.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 20));
				panel.add(lblTotal);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Buy");
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
				okButton.setActionCommand("OK");
				okButton.addActionListener(this);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Close");
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if(s.equals("Cancel")){
			dispose();
		}else {
			Payment pay = new Payment(this);
			pay.setVisible(true);
		}
		
	}
	
	public void setTotal(Float total) {
		this.total = total;
		this.lblTotal.setText(String.format("₹ %.2f ",total));
	}


	@Override
	public void onOK() {
		// TODO Auto-generated method stub
		Summary sum = new Summary();
		sum.setVisible(true);
		dispose();
	}

}
