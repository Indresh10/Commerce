package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

import src.CartProducts.RefreshListener;

import java.awt.GridLayout;
import javax.swing.JTextPane;

public class Summary extends JDialog implements ActionListener {

	/**
	 * Launch the application.
	 */
	private final JPanel contentPanel = new JPanel();
	private JLabel lblTotal;
	private int user_id;
	private float total = 0.0f;
	/**
	 * 
	 */
	private static final long serialVersionUID = -2527272763910285445L;

	/**
	 * Create the frame.
	 */
	public Summary(int user_id,String mode) {
		this.user_id = user_id;
		setTitle("Summary");
//		setExtendedState(getExtendedState()|JFrame.MAXIMIZED_BOTH);
		setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
//		setBounds(100, 100, 925, 554);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		{
			JPanel Price = new JPanel();
			Price.setAlignmentX(Component.RIGHT_ALIGNMENT);
			contentPanel.add(Price, BorderLayout.SOUTH);
			Price.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
			{
				JLabel lblNewLabel_1 = new JLabel(" Total ");
				lblNewLabel_1.setBackground(new Color(255, 255, 224));
				lblNewLabel_1.setOpaque(true);
				lblNewLabel_1.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
				lblNewLabel_1.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
				Price.add(lblNewLabel_1);
			}
			{
				lblTotal = new JLabel(String.format("₹ %.2f ", total));
				lblTotal.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
				lblTotal.setOpaque(true);
				lblTotal.setBackground(new Color(238, 232, 170));
				lblTotal.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 20));
				Price.add(lblTotal);
			}
		}
		{
			addCart();
		}
		{
			JPanel Details = new JPanel();
			contentPanel.add(Details, BorderLayout.NORTH);
			Details.setLayout(new GridLayout(1, 2, 5, 0));
			{
				SqlConnection connection = SqlConnection.getInstance();
				try(Statement stmt = connection.getCon().createStatement()){
					ResultSet set = stmt.executeQuery("select username,Address from users where id="+user_id);
					set.next();
					JTextPane txtpnHello = new JTextPane();
					txtpnHello.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
					txtpnHello.setOpaque(false);
					txtpnHello.setText("Name: "+set.getString(1)+"\nAddress:"+set.getString(2));
					txtpnHello.setEditable(false);
					txtpnHello.setFont(new Font("Segoe UI", Font.PLAIN, 18));
					Details.add(txtpnHello);
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
				
			}
			{
				JLabel lblNewLabel = new JLabel("Payment Mode: "+mode);
				lblNewLabel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
				Details.add(lblNewLabel);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
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
				panel.add(new CartProducts(cart,null,false),c);
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

}

