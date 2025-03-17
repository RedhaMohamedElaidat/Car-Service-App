package Home;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JLabel;
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

public class ADD6 extends JFrame {

	private JPanel contentPane;
	private JTextField numI;
	private JTextField numVV;
	private JTextField typeI;
	private JTextField dateDI;
	private JTextField dateFI;
	private JTextField coutI;
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
					ADD6 frame = new ADD6();
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
	public ADD6() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel contentPane_1 = new JPanel();
		contentPane_1.setLayout(null);
		contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane_1.setBackground(new Color(255, 181, 98));
		contentPane_1.setBounds(0, 0, 434, 261);
		contentPane.add(contentPane_1);
		
		JLabel lblNewLabel = new JLabel("ADD Interventions");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(101, 11, 210, 32);
		contentPane_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("num Intervention");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(10, 58, 100, 14);
		contentPane_1.add(lblNewLabel_1);
		
		numI = new JTextField();
		numI.setColumns(10);
		numI.setBounds(24, 83, 86, 20);
		contentPane_1.add(numI);
		
		JLabel lblNewLabel_1_1 = new JLabel("num Vehicule");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1.setBounds(120, 58, 81, 14);
		contentPane_1.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Type");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_2.setBounds(228, 58, 46, 14);
		contentPane_1.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Date debut");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_3.setBounds(307, 58, 78, 14);
		contentPane_1.add(lblNewLabel_1_3);
		
		numVV = new JTextField();
		numVV.setColumns(10);
		numVV.setBounds(115, 83, 86, 20);
		contentPane_1.add(numVV);
		
		typeI = new JTextField();
		typeI.setColumns(10);
		typeI.setBounds(211, 83, 86, 20);
		contentPane_1.add(typeI);
		
		dateDI = new JTextField();
		dateDI.setColumns(10);
		dateDI.setBounds(307, 83, 86, 20);
		contentPane_1.add(dateDI);
		
		JLabel lblNewLabel_1_4 = new JLabel("Date fin");
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_4.setBounds(30, 119, 46, 14);
		contentPane_1.add(lblNewLabel_1_4);
		
		JLabel lblNewLabel_1_5 = new JLabel("Cout Intervention");
		lblNewLabel_1_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_5.setBounds(120, 119, 118, 14);
		contentPane_1.add(lblNewLabel_1_5);
		
		dateFI = new JTextField();
		dateFI.setColumns(10);
		dateFI.setBounds(24, 141, 86, 20);
		contentPane_1.add(dateFI);
		
		coutI = new JTextField();
		coutI.setColumns(10);
		coutI.setBounds(115, 141, 86, 20);
		contentPane_1.add(coutI);
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!numI.getText().isEmpty()&&!numVV.getText().isEmpty()&&!typeI.getText().isEmpty()&&!coutI.getText().isEmpty()&&!dateDI.getText().isEmpty()&&!dateFI.getText().isEmpty()) {
				ADD6(numI.getText(),numVV.getText(),typeI.getText(),dateDI.getText(),dateFI.getText(),coutI.getText());	
					
				}
				
				
				
				
				
			}
			public void ADD6(String a,String b,String c,String d,String e,String f) {
				String query="INSERT INTO Interventions(numI,numVV,typeI,dateDI,dateFI,coutI) VALUES(?,?,?,?,?,?)";
				try (PreparedStatement statement = connection.prepareStatement(query)) {
		            statement.setString(1, a);
		            statement.setString(2, b);
		            statement.setString(3, c);
		            statement.setString(4, d);
		            statement.setString(5, e);
		            statement.setString(6, f);
		            int rowsAffected = statement.executeUpdate();
		            if (rowsAffected > 0) {
		                System.out.println("Data inserted successfully.");
		            } else {
		                System.out.println("Failed to insert data.");
		            }
		        } catch (SQLException ee) {
		            System.out.println("Failed to insert data.");
		            ee.printStackTrace();
		        }
				
			}
			 public void displayTable() {
	                String query = "SELECT * FROM Interventions";
	                try (Statement stmt = connection.createStatement();
	                     ResultSet rs = stmt.executeQuery(query)) {

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
		btnNewButton.setBounds(321, 227, 89, 23);
		contentPane_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Return");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Intervention ee=new Intervention();
				ee.setVisible(true);
				dispose();
				
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.setBounds(222, 227, 89, 23);
		contentPane_1.add(btnNewButton_1);
	}

}
