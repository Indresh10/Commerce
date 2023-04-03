package src;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Payment extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1163940381177238351L;
	private final JPanel contentPanel = new JPanel();
	private JComboBox<String> combobox;
	public interface OkListener{
		void onOK(String mode);
	}
	/**
	 * Create the dialog.
	 */
	private OkListener oklist;
	public Payment(OkListener oklist) {
		this.oklist = oklist;
		setModal(true);
		setTitle("Payment Method");
		setBounds(100, 100, 250, 150);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		{
			JLabel lblNewLabel = new JLabel("Choose a payment method");
			lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
			lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			contentPanel.add(lblNewLabel);
		}
		{
			combobox = new JComboBox<>();
			combobox.setModel(new DefaultComboBoxModel<String>(new String[] {"Cash", "UPI", "Debit Card", "Credit Card", "NetBanking"}));
			combobox.setFont(new Font("Segoe UI", Font.PLAIN, 18));
			contentPanel.add(combobox);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Choose");
				okButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
				okButton.setActionCommand("OK");
				okButton.addActionListener(this);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Cancel")) {
			dispose();
		}else {
			oklist.onOK(combobox.getSelectedItem().toString());
			dispose();
		}
		
	}

}
