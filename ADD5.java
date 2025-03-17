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

public class ADD5 extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
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
                    ADD5 frame = new ADD5();
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
    public ADD5() {
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

        JLabel lblNewLabel = new JLabel("ADD Vehicule");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(127, 21, 150, 31);
        contentPane_1.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("num Vehicule");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_1.setBounds(30, 94, 77, 14);
        contentPane_1.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("num Client");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_1_1.setBounds(153, 94, 83, 14);
        contentPane_1.add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_2 = new JLabel("num Modele");
        lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_1_2.setBounds(298, 94, 86, 14);
        contentPane_1.add(lblNewLabel_1_2);

        textField = new JTextField();
        textField.setColumns(10);
        textField.setBounds(21, 127, 86, 20);
        contentPane_1.add(textField);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(150, 127, 86, 20);
        contentPane_1.add(textField_1);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(298, 127, 86, 20);
        contentPane_1.add(textField_2);

        JButton btnNewButton = new JButton("ADD");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String numV = textField.getText();
                String numCC = textField_1.getText();
                String numMoo = textField_2.getText();
                String numIMMAT = textField_3.getText();
                String annee = textField_4.getText();

                addData(numV, numCC, numMoo, numIMMAT, annee);
                displayTable();
            }

            public void addData(String numV, String numCC, String numMoo, String numIMMAT, String annee) {
                String query = "INSERT INTO Vehicule (numV, numCC, numMoo, numIMMAT, annee) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, numV);
                    statement.setString(2, numCC);
                    statement.setString(3, numMoo);
                    statement.setString(4, numIMMAT);
                    statement.setString(5, annee);

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
                String query = "SELECT * FROM Vehicule";
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
        btnNewButton.setBounds(335, 227, 89, 23);
        contentPane_1.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Return");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Vehicule ee = new Vehicule();
                ee.setVisible(true);
                dispose();
            }
        });
        btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnNewButton_1.setBounds(236, 227, 89, 23);
        contentPane_1.add(btnNewButton_1);

        JLabel lblNewLabel_1_3 = new JLabel("Matricule");
        lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_1_3.setBounds(30, 158, 81, 14);
        contentPane_1.add(lblNewLabel_1_3);

        JLabel lblNewLabel_1_4 = new JLabel("Year");
        lblNewLabel_1_4.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_1_4.setBounds(155, 158, 81, 14);
        contentPane_1.add(lblNewLabel_1_4);

        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(21, 184, 86, 20);
        contentPane_1.add(textField_3);

        textField_4 = new JTextField();
        textField_4.setColumns(10);
        textField_4.setBounds(150, 184, 86, 20);
        contentPane_1.add(textField_4);
    }
}
