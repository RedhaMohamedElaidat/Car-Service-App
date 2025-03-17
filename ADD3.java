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

public class ADD3 extends JFrame {

    private JPanel contentPane;
    private JTextField numM;
    private JTextField maque;
    private JTextField pays;
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
                    ADD3 frame = new ADD3();
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
    public ADD3() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 181, 98));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel numMarque = new JLabel("Num Marque");
        numMarque.setFont(new Font("Tahoma", Font.BOLD, 11));
        numMarque.setBounds(44, 64, 86, 14);
        contentPane.add(numMarque);

        JLabel lblId = new JLabel("Marque");
        lblId.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblId.setBounds(194, 64, 46, 14);
        contentPane.add(lblId);

        JLabel lblId_1 = new JLabel("Pays");
        lblId_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblId_1.setBounds(314, 64, 46, 14);
        contentPane.add(lblId_1);

        numM = new JTextField();
        numM.setText("");
        numM.setBounds(44, 93, 86, 20);
        contentPane.add(numM);
        numM.setColumns(10);

        maque = new JTextField();
        maque.setText("");
        maque.setColumns(10);
        maque.setBounds(174, 93, 86, 20);
        contentPane.add(maque);

        pays = new JTextField();
        pays.setText("");
        pays.setColumns(10);
        pays.setBounds(304, 93, 86, 20);
        contentPane.add(pays);

        JLabel lblNewLabel_1 = new JLabel("ADD Marque");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel_1.setBounds(133, 11, 149, 25);
        contentPane.add(lblNewLabel_1);

        JButton btnNewButton = new JButton("ADD");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!numM.getText().isEmpty() && !maque.getText().isEmpty() && !pays.getText().isEmpty()) {
                    String num = numM.getText();
                    String marque = maque.getText();
                    String paysText = pays.getText();
                    insertData(num, marque, paysText);
                }
                try {
                    displayTable();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

            public void insertData(String num, String marque, String paysText) {
                String query = "INSERT INTO Marque (numM, marque, pays) VALUES (?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, num);
                    statement.setString(2, marque);
                    statement.setString(3, paysText);

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
                String query = "SELECT * FROM Marque";
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
        btnNewButton.setBounds(321, 227, 89, 23);
        contentPane.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Return");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Maque ee = new Maque();
                ee.setVisible(true);
                dispose();
            }
        });
        btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnNewButton_1.setBounds(222, 227, 89, 23);
        contentPane.add(btnNewButton_1);
    }
}

