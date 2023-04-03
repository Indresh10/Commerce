package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CartProducts extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1421200220099908624L;
	public interface RefreshListener{
		void onRefresh();
	}
	/**
	 * Create the panel.
	 */
	public CartProducts(Cart cart,RefreshListener listener,boolean show) {
		setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setLayout(null);
		
		JLabel image = new JLabel();
		image.setIcon(scaleImage(new ImageIcon("images//"+cart.imagepath), 200,158));
		image.setBounds(0, 0, 200, 158);
		add(image);
		
		JLabel lblName = new JLabel(cart.name);
		lblName.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		lblName.setBounds(210, 10, 361, 25);
		add(lblName);
		
		JLabel lblQty = new JLabel(String.valueOf(cart.qty));
		lblQty.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblQty.setHorizontalTextPosition(SwingConstants.CENTER);
		lblQty.setForeground(new Color(255, 255, 255));
		lblQty.setOpaque(true);
		lblQty.setBackground(new Color(255, 69, 0));
		lblQty.setHorizontalAlignment(SwingConstants.CENTER);
		lblQty.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblQty.setBounds(329, 119, 44, 25);
		add(lblQty);
		String pr = String.format("₹%.2f", cart.total);
		JLabel lblPrice = new JLabel(pr);
		lblPrice.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrice.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblPrice.setBounds(371, 119, 109, 25);
		add(lblPrice);
		if(show) {
		JButton btnNewButton = new JButton("Remove");
		btnNewButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		btnNewButton.setBounds(505, 115, 109, 33);
		SqlConnection connection = SqlConnection.getInstance();
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try(Statement stmt = connection.getCon().createStatement()){
					stmt.execute("delete from cart where id="+cart.id);
					listener.onRefresh();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}
		});
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("+");
		btnNewButton_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnNewButton_1.setBounds(210, 114, 58, 33);
		btnNewButton_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try(Statement stmt = connection.getCon().createStatement()){
					cart.qty+=1;
					cart.total = cart.price * cart.qty;
					stmt.execute("update cart set quantity = "+ cart.qty +" where id = "+cart.id);
					stmt.execute("update cart set total = "+cart.total +" where id = "+cart.id);
					listener.onRefresh();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}
		});
		add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("-");
		btnNewButton_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnNewButton_1_1.setBounds(272, 115, 47, 33);
		btnNewButton_1_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try(Statement stmt = connection.getCon().createStatement()){
					if(cart.qty >1) {
					cart.qty-=1;
					cart.total = cart.price * cart.qty;
					stmt.execute("update cart set quantity = "+ cart.qty +" where id = "+cart.id);
					stmt.execute("update cart set total = "+cart.total +" where id = "+cart.id);
					listener.onRefresh();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}
		});
		add(btnNewButton_1_1);
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

}
