package Home;

import java.awt.EventQueue;
import Home.ADD;
import Home.REMOVE;
import Home.MODIFEY;
import Home.SEARCH;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import Home.SEARCH;
public class Employe extends JFrame {
	private static Connection connection;
	private JPanel contentPane;
	public static final String DB_URL="jdbc:oracle:thin:@localhost:1521:xe";
	public static final String DB_USR="system";
	public static final String DB_password="psw";
	
	private void connectToDataBase() {
		try {
			connection=DriverManager.getConnection(DB_URL, DB_USR, DB_password);
			System.out.println("Connect successfuly");
		}catch(SQLException e) {
			System.out.println("Failed");
			e.printStackTrace();
		}
		
		
		
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Employe frame = new Employe();
					frame.setVisible(true);
					frame.connectToDataBase();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Employe() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 181, 98));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Employe ");
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setFont(new Font("Yu Gothic Medium", Font.BOLD, 20));
		lblNewLabel.setBounds(174, 11, 163, 33);
		contentPane.add(lblNewLabel);
		
		JList list = new JList();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {" ADD", "", " REMOVE", "", " SEARCH", "", " AFFICHE"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setBounds(135, 189, 179, -101);
		contentPane.add(list);
		
		JList<?> list_1 = new JList();
		list_1.setFont(new Font("Sitka Subheading", Font.BOLD | Font.ITALIC, 13));
		list_1.setBackground(new Color(255, 181, 98));
		list_1.setValueIsAdjusting(true);
		list_1.setLayoutOrientation(JList.VERTICAL_WRAP);
		list_1.setModel(new AbstractListModel() {
			String[] values = new String[] {" ADD", "", " REMOVE", "", " SEARCH", "", " AFFICHE"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list_1.setBounds(117, 54, 197, 146);
		contentPane.add(list_1);
		
		JButton btnNewButton = new JButton("Continu");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String selectedValue = (String) list.getSelectedValue();
					if(selectedValue != null && selectedValue.trim().equals("ADD")) {
						ADD ee=new ADD();
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
			}});
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
		btnNewButton_1.setBounds(233, 227, 89, 23);
		contentPane.add(btnNewButton_1);
	}
}
