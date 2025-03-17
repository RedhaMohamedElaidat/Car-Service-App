package Home;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JList;
import java.awt.Font;
import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.event.ActionEvent;

public class Maque extends JFrame {
	private static Connection connection;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Maque frame = new Maque();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Maque() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 181, 98));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList list = new JList();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"  ADD", "", "  REMOVE", "", "  SEARCH", "", "  MODIFEY"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setFont(new Font("Sitka Text", Font.BOLD | Font.ITALIC, 13));
		list.setBackground(new Color(255, 181, 98));
		list.setBounds(121, 69, 138, 142);
		contentPane.add(list);
		
		JLabel lblNewLabel = new JLabel("Marque");
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(179, 27, 98, 31);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Continu");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedValue = (String) list.getSelectedValue();
				if(selectedValue != null && selectedValue.trim().equals("ADD")) {
					ADD3 ee=new ADD3();
					ee.setVisible(true);
					dispose();
				}
				else if(selectedValue != null && selectedValue.trim().equals("REMOVE")) {
					REMOVE ee=new REMOVE();
					ee.setVisible(true);
					dispose();
				}
				else if(selectedValue != null && selectedValue.trim().equals("SEARCH")) {
					SEARCH ee=new SEARCH();
					ee.setVisible(true);
					dispose();
				}
				else if (selectedValue != null && selectedValue.trim().equals("MODIFEY")) {
					MODIFEY ee=new MODIFEY();
					ee.setVisible(true);
					dispose();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(335, 227, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Return");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Menu ee=new Menu();
				ee.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.setBounds(225, 227, 89, 23);
		contentPane.add(btnNewButton_1);
	}

}
