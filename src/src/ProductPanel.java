package src;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import java.awt.Color;

public class ProductPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3936871305193966249L;
	
	/**
	 * Create the panel.
	 */
	public ProductPanel(Product product,int user_id) {
		setBackground(new Color(204, 204, 255));
		setLayout(new BorderLayout());
		JLabel lblNewLabel = new JLabel(product.name);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Segoe Print", Font.PLAIN, 24));
		add(lblNewLabel, BorderLayout.NORTH);
		
		JLabel lblNewLabel_1 = new JLabel(String.format("₹%.2f", product.price));
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(lblNewLabel_1, BorderLayout.PAGE_END);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(scaleImage(new ImageIcon("images//"+product.imagepath), 363,360));
		add(lblNewLabel_2, BorderLayout.CENTER);
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				ProductDialog dialog = new ProductDialog(product,user_id);
				dialog.setVisible(true);
			}
		});
		
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
