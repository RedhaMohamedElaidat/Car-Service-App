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

public class ADD4 extends JFrame {

	private JPanel contentPane;
	private JTextField numMo;
	private JTextField numMM;
	private JTextField modele;
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
					ADD4 frame = new ADD4();
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
	public ADD4() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 181, 98));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ADD Modele");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(155, 21, 146, 31);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("num Modele");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(21, 94, 86, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("num Marque");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1.setBounds(155, 94, 81, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("model");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_2.setBounds(314, 94, 46, 14);
		contentPane.add(lblNewLabel_1_2);
		
		numMo = new JTextField();
		numMo.setBounds(21, 127, 86, 20);
		contentPane.add(numMo);
		numMo.setColumns(10);
		
		numMM = new JTextField();
		numMM.setColumns(10);
		numMM.setBounds(150, 127, 86, 20);
		contentPane.add(numMM);
		
		modele = new JTextField();
		modele.setColumns(10);
		modele.setBounds(298, 127, 86, 20);
		contentPane.add(modele);
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!numMo.getText().isEmpty()&&!numMM.getText().isEmpty()&&!modele.getText().isEmpty()) {
	               ADD4(numMo.getText(),numMM.getText(),modele.getText());
					}
					try {
	                    displayTable();
	                } catch (SQLException e1) {
	                    e1.printStackTrace();
	                }	
				
			}
			public void ADD4(String a,String b,String c) {
				String query="INSERT INTO Modele (numMo, numMM, modele) VALUES (?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, a);
                    statement.setString(2, b);
                    statement.setString(3, c);

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
            public void displayTable() throws SQLException {
                String query = "SELECT * FROM Modele";
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
		
		JButton btnNewButton_1 = new JButton("Return");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Model ee=new Model();
				ee.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.setBounds(236, 227, 89, 23);
		contentPane.add(btnNewButton_1);
	}

}
