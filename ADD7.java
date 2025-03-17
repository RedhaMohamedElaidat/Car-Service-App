package Home;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ADD7 extends JFrame {

    private JPanel contentPane;
    private JTextField numII;
    private JTextField numPP;
    private JTextField dateD;
    private JTextField dateF;
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

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ADD7 frame = new ADD7();
                    frame.setVisible(true);
                    frame.connectToDataBase();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ADD7() {
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

        JLabel lblNewLabel = new JLabel("ADD");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(176, 11, 98, 32);
        contentPane_1.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("num Intervenant");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_1.setBounds(10, 58, 100, 14);
        contentPane_1.add(lblNewLabel_1);

        numII = new JTextField();
        numII.setColumns(10);
        numII.setBounds(24, 83, 86, 20);
        contentPane_1.add(numII);

        JLabel lblNewLabel_1_1 = new JLabel("num Employe");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_1_1.setBounds(120, 58, 81, 14);
        contentPane_1.add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_2 = new JLabel("Date debut");
        lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_1_2.setBounds(222, 58, 69, 14);
        contentPane_1.add(lblNewLabel_1_2);

        JLabel lblNewLabel_1_3 = new JLabel("Date fin");
        lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_1_3.setBounds(324, 58, 46, 14);
        contentPane_1.add(lblNewLabel_1_3);

        numPP = new JTextField();
        numPP.setColumns(10);
        numPP.setBounds(120, 83, 86, 20);
        contentPane_1.add(numPP);

        dateD = new JTextField();
        dateD.setColumns(10);
        dateD.setBounds(222, 83, 86, 20);
        contentPane_1.add(dateD);

        dateF = new JTextField();
        dateF.setColumns(10);
        dateF.setBounds(324, 83, 86, 20);
        contentPane_1.add(dateF);

        JButton btnNewButton = new JButton("ADD");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String a = numII.getText();
                String b = numPP.getText();
                String c = dateD.getText();
                String d = dateF.getText();
                ADD7(a, b, c, d);
            }
        });
        btnNewButton.setBounds(176, 134, 89, 23);
        contentPane_1.add(btnNewButton);
    }

    public void ADD7(String a, String b, String c, String d) {
        try {
            // Check if the referenced value exists in the parent table
            String parentQuery = "SELECT COUNT(*) FROM Vehicule WHERE numV = ?";
            PreparedStatement parentStatement = connection.prepareStatement(parentQuery);
            parentStatement.setString(1, b);  // Assuming b is the value of numPP
            ResultSet parentResult = parentStatement.executeQuery();
            parentResult.next();
            int parentCount = parentResult.getInt(1);

            if (parentCount > 0) {
                // Insert data into the child table
                String childQuery = "INSERT INTO Intervenants(numII, numPP, dateD, dateF) VALUES (?, ?, ?, ?)";
                PreparedStatement childStatement = connection.prepareStatement(childQuery);
                childStatement.setString(1, a);
                childStatement.setString(2, b);
                childStatement.setString(3, c);
                childStatement.setString(4, d);

                int rowsAffected = childStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Data inserted successfully.");
                } else {
                    System.out.println("Failed to insert data.");
                }
            } else {
                System.out.println("Parent key not found in the Vehicule table.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to insert data.");
            e.printStackTrace();
        }
    }
}
