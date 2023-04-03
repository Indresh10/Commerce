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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;

import src.CartProducts.RefreshListener;
import src.Payment.OkListener;

public class CartDialog extends JDialog implements ActionListener,OkListener,RefreshListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8163967290394233607L;
	private final JPanel contentPanel = new JPanel();
	private Float total = 0.0f;
	private JLabel lblTotal;
	private int user_id;
	/**
	 * Create the dialog.
	 */
	public CartDialog(int user_id) {
		this.user_id = user_id;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Cart");
		setModal(true);
		setBounds(100, 100, 925, 554);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
			
		contentPanel.setLayout(new BorderLayout(0, 0));
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
				lblTotal = new JLabel(String.format("₹ %.2f ", total));
				lblTotal.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
				lblTotal.setOpaque(true);
				lblTotal.setBackground(new Color(238, 232, 170));
				lblTotal.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 20));
				panel.add(lblTotal);
			}
		}
		{
			addCart();
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


	private void addCart() {
		total = 0.0f;
		SqlConnection connection = SqlConnection.getInstance();
		try(Statement stmt = connection.getCon().createStatement()){
			ResultSet rs = stmt.executeQuery("select * from cartproduct where user_id="+user_id);
			JPanel panel = new JPanel();
			panel.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
			int i=0;
			while(rs.next()) {
				Cart cart = new Cart(rs.getInt(1), rs.getInt("quantity"), rs.getFloat("total"),rs.getFloat("price"),rs.getString("name") , rs.getString("img")); 
				c.gridx = 0;
				c.gridy = i;
				c.weightx = 1;
				c.ipady = 150;
				c.insets = new Insets(2, 2, 2, 2);
				panel.add(new CartProducts(cart,this,true),c);
				total += cart.total;
				i++;
			}
			JScrollPane scrollPane = new JScrollPane(panel);
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			lblTotal.setText(String.format(" ₹%.2f ", total));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	@Override
	public void onOK(String mode) {
		Summary sum = new Summary(user_id,mode);
		sum.setVisible(true);
		dispose();
	}


	@Override
	public void onRefresh() {
		addCart();
	}

}
