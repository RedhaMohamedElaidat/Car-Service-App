package Home;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Siggin extends JFrame {
    public Connection connection;
    private JPanel contentPane;
    private JTextField UserName;
    private JTextField Password;

    public static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    public static final String DB_USR = "system";
    public static final String DB_password = "psw";

    private void connectToDataBase() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USR, DB_password);
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
                    Siggin frame = new Siggin();
                    frame.setVisible(true);
                    frame.connectToDataBase();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Siggin() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(254, 210, 146));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\ridae\\OneDrive\\Images\\Pic of project\\téléchargement.png"));
        lblNewLabel.setBounds(10, 11, 236, 239);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Create account");
        lblNewLabel_1.setForeground(new Color(255, 0, 0));
        lblNewLabel_1.setFont(new Font("Yu Gothic Medium", Font.BOLD, 20));
        lblNewLabel_1.setBounds(234, 11, 166, 33);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("User name :");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_2.setBounds(234, 71, 80, 14);
        contentPane.add(lblNewLabel_2);

        UserName = new JTextField();
        UserName.setBounds(311, 68, 113, 20);
        contentPane.add(UserName);
        UserName.setColumns(10);

        JLabel lblNewLabel_2_1 = new JLabel("Password :");
        lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_2_1.setBounds(234, 132, 80, 14);
        contentPane.add(lblNewLabel_2_1);

        JButton btnNewButton = new JButton("Sign in");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateUser(UserName.getText(), Password.getText());
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnNewButton.setBounds(335, 227, 89, 23);
        contentPane.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Return ");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Loggin ee = new Loggin();
                ee.setVisible(true);
                dispose();
            }
        });
        btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnNewButton_1.setBounds(236, 227, 89, 23);
        contentPane.add(btnNewButton_1);

        Password = new JTextField();
        Password.setBounds(311, 129, 113, 20);
        contentPane.add(Password);
        Password.setColumns(10);
    }

    public void CreateUser(String x, String y) {
        String query = "CREATE USER " + x + " IDENTIFIED BY " + y;
        String query2 = "GRANT ALL PRIVILEGES TO " + x;
        String query3 = "INSERT INTO users VALUES ('" + x + "', '" + y + "')";

        Statement stmt = null;
        Statement stmt2 = null;
        ResultSet rs = null;
        ResultSet rss = null;
        Statement stmt3 = null;
        ResultSet rss3 = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USR, DB_password);
            System.out.println("Connected successfully");
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);

            // If the execution reaches this point, the user creation was successful
            stmt.close();
            rs.close();
            stmt2 = connection.createStatement();
            rss = stmt2.executeQuery(query2);
            stmt2.close();
            rss.close();
            stmt3 = connection.createStatement();
            rss3 = stmt3.executeQuery(query2);
            stmt3.close();
            rss3.close();

            toConnect(x, y);

            // Show message dialog for successful account creation
            JOptionPane.showMessageDialog(contentPane, "Account created successfully!",
                    "Account Creation", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            // Show message dialog for username already exists
            JOptionPane.showMessageDialog(contentPane, "Username already exists. Please choose another username.",
                    "Account Creation Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Failed");
            e.printStackTrace();
        }
    }

    public void toConnect(String s, String Z) {
        try {
            connection = DriverManager.getConnection(DB_URL, s, Z);
            System.out.println("Connected successfully");
            Menu ee = new Menu();
            ee.setVisible(true);
            dispose();
        } catch (SQLException e) {
            System.out.println("Failed");
            e.printStackTrace();
        }
    }
}
