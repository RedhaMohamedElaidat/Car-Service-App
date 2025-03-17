package Home;
import Home.Employe;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.ListSelectionModel;
import java.awt.Font;
import javax.swing.JComboBox;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class Menu extends JFrame {

	private JPanel contentPane;
private static Connection connection;
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
					Menu e = new Menu();
					e.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Menu() {
		this.connection=connection;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 200, 145));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList list = new JList();
		list.setValueIsAdjusting(true);
		list.setFont(new Font("Sitka Small", Font.BOLD | Font.ITALIC, 11));
		list.setBackground(new Color(255, 200, 145));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"", "Employe ", "", "Client ", "", "Vehicule", "", "Model", "", "Marque", "", "Intervention", "", "Intervenant"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setSelectedIndex(7);
		list.setBounds(34, 95, 276, 155);
		contentPane.add(list);
		
		JLabel lblNewLabel = new JLabel("    Menu");
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setFont(new Font("Yu Gothic Medium", Font.BOLD, 20));
		lblNewLabel.setBounds(151, 11, 108, 26);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Please choose a table :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(34, 58, 133, 14);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Continu");
		btnNewButton.addActionListener(new ActionListener() {
		
				 public void actionPerformed(ActionEvent e) {
				        String selectedValue = (String) list.getSelectedValue();

				        if (selectedValue != null && selectedValue.trim().equals("Employe")) {
				            Employe employeFrame = new Employe();
				            employeFrame.setVisible(true);
				            
				            dispose(); // Close the current JFrame (Menu)
				        }
				         else if(selectedValue != null && selectedValue.trim().equals("Client")) {
				        	 Client ee = new Client();
				        	 ee.setVisible(true);
				        	 dispose();
				         }
				       
				         
				         
				         else if(selectedValue != null && selectedValue.trim().equals("Marque")) {
				        	 Maque ee=new Maque();
				        	 ee.setVisible(true);
				        	 dispose();
				        	 
				         }
				        else if(selectedValue != null && selectedValue.trim().equals("Model")) {
				        	Model ee=new Model();
				        	ee.setVisible(true);
				        	dispose();
				        }
				        else if(selectedValue != null && selectedValue.trim().equals("Intervention")) {
				        	Intervention ee=new Intervention();
				        	ee.setVisible(true);
				        	dispose();
				        }
				        else {
				        	Intervenant ee=new Intervenant();
				        	ee.setVisible(true);
				        	dispose();
				        }
				    }
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(335, 227, 89, 23);
		contentPane.add(btnNewButton);
	}
}
