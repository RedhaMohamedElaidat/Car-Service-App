package Home;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class ADD2 extends JFrame {

	private JPanel contentPane;
	private JTextField numC;
	private JTextField prenomC;
	private JTextField nomC;
	private JTextField datenaissanceC;
	private JTextField adresse;
	private JTextField telprofC;
	private JTextField telprivC;
	private JTextField faxC;
	 public Connection connection;
	    public static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	    public static final String DB_USR = "system";
	    public static final String DB_PASSWORD = "psw";

	    private void connectToDataBase() {
	        try {
	            connection = DriverManager.getConnection(DB_URL, DB_USR, DB_PASSWORD);
	            System.out.println("Connected successfully");
	        } catch (SQLException e) {
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
					ADD2 frame = new ADD2();
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
	public ADD2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 181, 98));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ADD Client");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(154, 11, 148, 38);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("numC");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(20, 60, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("CIV");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1.setBounds(109, 60, 46, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("PrenomC");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_2.setBounds(213, 60, 64, 14);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("nomC");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_3.setBounds(313, 60, 46, 14);
		contentPane.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("datenaissanceC");
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_4.setBounds(10, 116, 96, 14);
		contentPane.add(lblNewLabel_1_4);
		
		numC = new JTextField();
		numC.setBounds(20, 85, 86, 20);
		contentPane.add(numC);
		numC.setColumns(10);
				JComboBox civ = new JComboBox();
				civ.setModel(new DefaultComboBoxModel(new String[] {"", "M", "Mme", "Mle"}));
		civ.setBounds(109, 84, 86, 22);
		contentPane.add(civ);
		
		prenomC = new JTextField();
		prenomC.setColumns(10);
		prenomC.setBounds(216, 85, 86, 20);
		contentPane.add(prenomC);
		
		nomC = new JTextField();
		nomC.setColumns(10);
		nomC.setBounds(313, 85, 86, 20);
		contentPane.add(nomC);
		
		datenaissanceC = new JTextField();
		datenaissanceC.setColumns(10);
		datenaissanceC.setBounds(20, 141, 86, 20);
		contentPane.add(datenaissanceC);
		
		JButton btnNewButton_1 = new JButton("Return");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client ee = new Client();
				ee.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.setBounds(225, 227, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(!numC.getText().isEmpty()&&!civ.getSelectedItem().toString().isEmpty() &&!prenomC.getText().isEmpty()&&!nomC.getText().isEmpty()&&!datenaissanceC.getText().isEmpty()&&!adresse.getText().isEmpty()) {
					
						
						ADD2(numC.getText(),civ.getSelectedItem().toString(),prenomC.getText(),nomC.getText(),datenaissanceC.getText(),adresse.getText(),telprofC.getText(),telprivC.getText(),faxC.getText());
						
						try {
	                        displayTable();
	                    } catch (SQLException e1) {
	                        e1.printStackTrace();
	                    }
				}
				
				
				
				
			}
			public void ADD2(String a,String b,String c,String d,String e,String f,String g,String h,String j) {
				String query="INSERT INTO Client(numC,civ,prenomC,nomC,datenaissanceC,ADREESCLIENT,telprofC,telprivC,faxC) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
		        try (PreparedStatement statement = connection.prepareStatement(query)) {
		            statement.setString(1, a);
		            statement.setString(2, b);
		            statement.setString(3, c);
		            statement.setString(4, d);
		            statement.setString(5, e);
		            statement.setString(6, f);
		            statement.setString(7, g);
		            statement.setString(8, h);
		            statement.setString(9, j);

		            int rowsAffected = statement.executeUpdate();
		            if (rowsAffected > 0) {
		                System.out.println("Data inserted successfully.");
		            } else {
		                System.out.println("Failed to insert data.");
		            }
		        } catch (SQLException ee) {
		            System.out.println("Failed to insert data.");
		            ee.printStackTrace();}
		        
			
			
			
			
			
			}

		    public void displayTable() throws SQLException {

		        String query = "SELECT * FROM Client";
		        Statement stmt = null;
		        ResultSet rs = null;

		        try {
		            stmt = connection.createStatement();
		            rs = stmt.executeQuery(query);

		            ResultSetMetaData rsmd = rs.getMetaData();
		            int columnCount = rsmd.getColumnCount();

		            DefaultTableModel tableModel = new DefaultTableModel();
		            for (int i = 1; i <= columnCount; i++) {
		                tableModel.addColumn(rsmd.getColumnName(i));
		            }

		            while (rs.next()) {
		                Object[] row = new Object[columnCount];
		                for (int i = 1; i <= columnCount; i++) {
		                    row[i - 1] = rs.getObject(i);
		                }
		                tableModel.addRow(row);
		            }
		            rs.close();
		            stmt.close();

		            System.out.println("Table content: ");
		            for (int i = 0; i < tableModel.getRowCount(); i++) {
		                for (int j = 0; j < tableModel.getColumnCount(); j++) {
		                    System.out.print(tableModel.getValueAt(i, j) + " ");
		                }
		                System.out.println();
		            }
		        } catch (SQLException e) {
		            System.out.println("Failed to retrieve data from the database.");
		            e.printStackTrace();
		        }
		    }
		});
		
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(335, 227, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1_4_1 = new JLabel("adresse");
		lblNewLabel_1_4_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_4_1.setBounds(116, 116, 46, 14);
		contentPane.add(lblNewLabel_1_4_1);
		
		adresse = new JTextField();
		adresse.setColumns(10);
		adresse.setBounds(116, 141, 86, 20);
		contentPane.add(adresse);
		
		JLabel lblNewLabel_1_4_2 = new JLabel("telprofC");
		lblNewLabel_1_4_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_4_2.setBounds(216, 116, 46, 14);
		contentPane.add(lblNewLabel_1_4_2);
		
		telprofC = new JTextField();
		telprofC.setColumns(10);
		telprofC.setBounds(216, 141, 86, 20);
		contentPane.add(telprofC);
		
		JLabel lblNewLabel_1_4_3 = new JLabel("telprivC");
		lblNewLabel_1_4_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_4_3.setBounds(313, 116, 46, 14);
		contentPane.add(lblNewLabel_1_4_3);
		
		telprivC = new JTextField();
		telprivC.setColumns(10);
		telprivC.setBounds(313, 141, 86, 20);
		contentPane.add(telprivC);
		
		JLabel lblNewLabel_1_4_4 = new JLabel("faxC");
		lblNewLabel_1_4_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_4_4.setBounds(20, 172, 46, 14);
		contentPane.add(lblNewLabel_1_4_4);
		
		faxC = new JTextField();
		faxC.setColumns(10);
		faxC.setBounds(20, 197, 86, 20);
		contentPane.add(faxC);
		


	}
}
