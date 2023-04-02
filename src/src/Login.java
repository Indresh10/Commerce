package src;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import src.Register.RegisterUser;

public class Login extends JDialog implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public interface LoginCheck{
		boolean loginCheck(String username, String Password);
	}

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	private LoginCheck lcheck;
	private JTextField textField;
	private JPasswordField passwordField;
	public Login( LoginCheck lcheck, RegisterUser ruser) {
		setTitle("Login\r\n");
		setModal(true);
		setBounds(100, 100, 450, 300);
		this.lcheck = lcheck;
		getContentPane().setLayout(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(new GridLayout(2, 2, 10, 25));
		{
			JLabel lblNewLabel = new JLabel("Username");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPanel.add(lblNewLabel);
			textField = new JTextField();
			textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPanel.add(textField);
			textField.setColumns(1);
		}
		{
			JLabel lblNewLabel = new JLabel("Password");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPanel.add(lblNewLabel);
			
			passwordField = new JPasswordField();
			passwordField.setFont(new Font("Tahoma", Font.PLAIN, 18));
			contentPanel.add(passwordField);
		}
		
		contentPanel.setBounds(45, 50, 348, 131);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 222, 430, 31);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("Login");
				okButton.setActionCommand("OK");
				okButton.addActionListener(this);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}
		MouseListener mListener = new MouseListener() {			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				showRegister(ruser);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
		JLabel lblNewLabel_1 = new JLabel("Register");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Segoe UI Semilight", Font.ITALIC, 18));
		lblNewLabel_1.setBounds(179, 191, 75, 25);
		lblNewLabel_1.addMouseListener(mListener);
		getContentPane().add(lblNewLabel_1);
		WindowAdapter adapter = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				System.exit(0);
			}
		};
		addWindowListener(adapter);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if(s.equals("Cancel")){
			System.exit(0);
		}
		if(s.equals("OK")) {
			if(lcheck.loginCheck(textField.getText().trim(),String.valueOf(passwordField.getPassword()).trim())) {
				JOptionPane.showMessageDialog(contentPanel, "Welcome");
				this.dispose();
			}else {
				JOptionPane.showMessageDialog(contentPanel, "Invalid Credentials","Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	private void showRegister(RegisterUser ruser) {
		Register reg = new Register(ruser);
		reg.setVisible(true);
	}
}
