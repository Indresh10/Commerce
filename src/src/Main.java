package src;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import src.Register.RegisterUser;

public class Main extends JFrame implements Login.LoginCheck,RegisterUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private JDesktopPane contentPane;
	protected CartDialog dialog;
	private SqlConnection connection;
	private JLabel lblUser;
	private int user_id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Main frame = new Main();
		frame.setVisible(true);
		frame.showLogin();
	}

	private void showLogin() {
		Login login = new Login(this,this);
		login.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		connection = SqlConnection.getInstance();
		setTitle("Welcome to Shoppers");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setExtendedState(getExtendedState()|MAXIMIZED_BOTH);
		setDefaultLookAndFeelDecorated(true);
		contentPane = new JDesktopPane();
//		contentPane.setBorder(new EmptyBorder(0, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBorder(null);
		toolBar.setFloatable(false);
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 250, 154));
		panel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		panel.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(205, 92, 92)));
		toolBar.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel userPanel = new JPanel();
		userPanel.setBackground(new Color(0, 250, 154));
		userPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
		lblUser = new JLabel("Welcome");
		lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		userPanel.add(lblUser);
		
		JButton btnNewButton = new JButton("Logout");
		btnNewButton.setBorder(UIManager.getBorder("Button.border"));
		btnNewButton.setBackground(new Color(233, 150, 122));
		btnNewButton.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		btnNewButton.setToolTipText("Logout User");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		userPanel.add(btnNewButton);
		panel.add(userPanel,BorderLayout.WEST);
		JButton btnNewButton2 = new JButton("Cart");
		btnNewButton2.setBackground(new Color(255, 69, 0));
		btnNewButton2.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		btnNewButton2.setToolTipText("Product Cart");
		btnNewButton2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog = new CartDialog();
				dialog.setVisible(true);
			}
		});
		panel.add(btnNewButton2,BorderLayout.EAST);
		
	}

	private void addProducts() {
		JPanel products = new JPanel();
		products.setBorder(null);
		products.setBackground(new Color(175, 238, 238));
		products.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		Statement stmt;
		try {
			stmt = connection.getCon().createStatement();
			ResultSet rs = stmt.executeQuery("select * from products");
			int i=0;
			while(rs.next()) {
				Product product = new Product(rs.getInt(1), rs.getString(2), rs.getString(5), rs.getString(4), rs.getFloat(3));
				c.gridx = i%4;
				c.gridy = Math.floorDivExact(i, 4);
				c.weightx = 0.25;
				c.insets = new Insets(10, 10, 10, 10);
				products.add(new ProductPanel(product,user_id),c);
				i++;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JScrollPane scrollPane = new JScrollPane(products);
		scrollPane.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		contentPane.add(scrollPane, BorderLayout.CENTER);
	}

	@Override
	public boolean loginCheck(String username, String password) {
		try {
			Statement stmt = connection.getCon().createStatement();
			ResultSet rs = stmt.executeQuery("select id,username,password from users where username = '"+username+"' limit 1");
			rs.next();
			lblUser.setText(lblUser.getText() + ", " + username);
			user_id = rs.getInt(1);
			addProducts();
			return rs.getString(2).equals(username) && rs.getString(3).equals(password);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}

	@Override
	public boolean register(String uname, String email, String address, String password) {
		if(uname.equals("") || email.equals("") || address.equals("") || password.equals(""))
			return false;
		else {
			try(Statement stmt = connection.getCon().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)){
				ResultSet rs = stmt.executeQuery("select * from users");
				rs.moveToInsertRow();
				rs.updateString("username",uname);
				rs.updateString("email",email);
				rs.updateString("Address",address);
				rs.updateString("password",password);
				rs.insertRow();
				rs.beforeFirst();
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				return false;
			}
		}
	}

}
