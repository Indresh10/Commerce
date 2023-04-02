package src;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Register extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7934382876117847379L;
	private final JPanel contentPanel = new JPanel();

	public interface RegisterUser{
		boolean register(String uname,String email, String address, String password);
	}

	/**
	 * Create the dialog.
	 */
	private JTextField txtUname,txtEmail,txtAddress;
	private JPasswordField txtPass,txtConfPass;
	public Register(RegisterUser ruser) {
		setTitle("Register");
		setBounds(100, 100, 450, 470);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBounds(new Rectangle(40, 50, 350, 400));
		contentPanel.setBorder(new EmptyBorder(50, 10, 10, 10));
		setModal(true);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(new GridLayout(0, 2, 50,30 ));
		{
			JLabel lblNewLabel = new JLabel("Username");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPanel.add(lblNewLabel);
			txtUname = new JTextField();
			txtUname.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPanel.add(txtUname);
		}
		{
			JLabel lblNewLabel = new JLabel("Email");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPanel.add(lblNewLabel);
			txtEmail = new JTextField();
			txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPanel.add(txtEmail);
		}
		{
			JLabel lblNewLabel = new JLabel("Address");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPanel.add(lblNewLabel);
			txtAddress = new JTextField();
			txtAddress.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPanel.add(txtAddress);
		}
		{
			JLabel lblNewLabel = new JLabel("Password");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPanel.add(lblNewLabel);
			txtPass = new JPasswordField();
			txtPass.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPanel.add(txtPass);
		}
		{
			JLabel lblNewLabel = new JLabel("Confirm Password");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPanel.add(lblNewLabel);
			txtConfPass = new JPasswordField();
			txtConfPass.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPanel.add(txtConfPass);
		}
		contentPanel.setBounds(45, 30, 348, 450);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 550, 430, 50);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Register");
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						if(String.valueOf(txtPass.getPassword()).equals(String.valueOf(txtConfPass.getPassword()))) {
							if(ruser.register(txtUname.getText(), txtEmail.getText(), txtAddress.getText(), String.valueOf(txtPass.getPassword()))) {
								JOptionPane.showMessageDialog(contentPanel, "User Registered, Login to Continue");
								dispose();
							}else {
								JOptionPane.showMessageDialog(contentPanel, "Invalid data","Error", JOptionPane.ERROR_MESSAGE);
							}
						}else {
							JOptionPane.showMessageDialog(contentPanel, "Password doesn't match","Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
					
				});
				buttonPane.add(cancelButton);
			}
		}
	}

}
