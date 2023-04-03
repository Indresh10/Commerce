package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ProductDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9212489567247889956L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblqty;
	private int qty = 1;
	private int user_id;
	private Product product;
	

	/**
	 * Create the dialog.
	 */
	public ProductDialog(Product product,int user_id) {
		this.user_id = user_id;
		this.product = product;
		setTitle("Product");
		setModal(true);
		setBounds(100, 100, 573, 337);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel image = new JLabel();
		image.setBounds(26, 26, 200, 200);
		image.setIcon(scaleImage(new ImageIcon("images//"+product.imagepath), 200,200));
		contentPanel.add(image);
		
		JLabel lblName = new JLabel(product.name);
		lblName.setFocusable(false);
		lblName.setHorizontalTextPosition(SwingConstants.LEFT);
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		lblName.setBounds(241, 27, 298, 43);
		contentPanel.add(lblName);
		
		JLabel lblPrice = new JLabel(String.format(" ₹%.2f ", product.price));
		lblPrice.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPrice.setFont(new Font("Segoe UI Semibold", Font.BOLD, 18));
		lblPrice.setBounds(241, 192, 298, 29);
		contentPanel.add(lblPrice);
		
		JTextArea txtrDescription = new JTextArea();
		txtrDescription.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		txtrDescription.setWrapStyleWord(true);
		txtrDescription.setOpaque(false);
		txtrDescription.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtrDescription.setText(product.description);
		txtrDescription.setLineWrap(true);
		txtrDescription.setFocusable(false);
		txtrDescription.setEditable(false);
		txtrDescription.setBounds(251, 80, 288, 113);
		contentPanel.add(txtrDescription);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnDecr = new JButton("-");
				btnDecr.setFont(new Font("Tahoma", Font.PLAIN, 18));
				btnDecr.setActionCommand("Sub");
				btnDecr.addActionListener(this);
				buttonPane.add(btnDecr);
			}
			{
				lblqty = new JLabel(String.valueOf(qty));
				lblqty.setFocusable(false);
				lblqty.setFont(new Font("Tahoma", Font.PLAIN, 18));
				buttonPane.add(lblqty);
			}
			{
				JButton btnIncr = new JButton("+");
				btnIncr.setMargin(new Insets(2, 10, 2, 10));
				btnIncr.setFont(new Font("Tahoma", Font.PLAIN, 18));
				btnIncr.setActionCommand("Add");
				btnIncr.addActionListener(this);
				buttonPane.add(btnIncr);
			}
			{
				JButton btnAddCart = new JButton("Add to Cart");
				btnAddCart.setFont(new Font("Tahoma", Font.PLAIN, 18));
				btnAddCart.setMnemonic('A');
				btnAddCart.setActionCommand("OK");
				btnAddCart.addActionListener(this);
				buttonPane.add(btnAddCart);
				getRootPane().setDefaultButton(btnAddCart);
			}
		}
	}
	private ImageIcon scaleImage(ImageIcon icon, int w, int h)
    {
        int nw = icon.getIconWidth();
        int nh = icon.getIconHeight();

        if(icon.getIconWidth() > w)
        {
          nw = w;
          nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
        }

        if(nh > h)
        {
          nh = h;
          nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
        }

        return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_SMOOTH));
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Add")){
			qty++;
			lblqty.setText(String.valueOf(qty));
		}
		if(e.getActionCommand().equals("Sub")){
			qty--;
			if(qty<1) qty = 1;
			lblqty.setText(String.valueOf(qty));
		}
		if(e.getActionCommand().equals("OK")) {
			SqlConnection connection = SqlConnection.getInstance();
			try(Statement stmt = connection.getCon().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)){
				ResultSet rs = stmt.executeQuery("select * from cart");
				rs.moveToInsertRow();
				rs.updateInt("user_id",user_id);
				rs.updateInt("product_id",product.id);
				rs.updateInt("quantity",qty);
				rs.updateFloat("total",product.price * qty);
				rs.insertRow();
				rs.beforeFirst();
				JOptionPane.showMessageDialog(contentPanel, "Sucess added to cart");
				dispose();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(contentPanel, "Can't add to cart","Error",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
