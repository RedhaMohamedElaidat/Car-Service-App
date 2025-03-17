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
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class ADD extends JFrame {
   
    private JPanel contentPane;
    private JTextField nump;
    private JTextField prenomP;
    private JTextField categorieP;
    private JTextField salair;
    private JTextField nomP;
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
                    ADD frame = new ADD();
                    frame.setVisible(true);
                    frame.connectToDataBase(); // Establish the database connection
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ADD() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 181, 98));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("ADD Employe");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(142, 15, 158, 32);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("numP");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_1.setBounds(30, 58, 46, 14);
        contentPane.add(lblNewLabel_1);

        nump = new JTextField();
        nump.setBounds(24, 83, 86, 20);
        contentPane.add(nump);
        nump.setColumns(10);

        JLabel lblNewLabel_1_2 = new JLabel("prenomP");
        lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_1_2.setBounds(211, 58, 80, 14);
        contentPane.add(lblNewLabel_1_2);

        JLabel lblNewLabel_1_3 = new JLabel("categorie");
        lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_1_3.setBounds(321, 58, 64, 14);
        contentPane.add(lblNewLabel_1_3);

        prenomP = new JTextField();
        prenomP.setBounds(211, 83, 86, 20);
        contentPane.add(prenomP);
        prenomP.setColumns(10);

        categorieP = new JTextField();
        categorieP.setBounds(307, 83, 86, 20);
        contentPane.add(categorieP);
        categorieP.setColumns(10);

        JLabel lblNewLabel_1_4 = new JLabel("salair");
        lblNewLabel_1_4.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_1_4.setBounds(182, 124, 46, 14);
        contentPane.add(lblNewLabel_1_4);

        salair = new JTextField();
        salair.setColumns(10);
        salair.setBounds(176, 146, 86, 20);
        contentPane.add(salair);

        JLabel lblNewLabel_1_2_1 = new JLabel("nomP");
        lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_1_2_1.setBounds(120, 58, 80, 14);
        contentPane.add(lblNewLabel_1_2_1);

        nomP = new JTextField();
        nomP.setColumns(10);
        nomP.setBounds(120, 83, 86, 20);
        contentPane.add(nomP);

        JButton btnNewButton = new JButton("ADD");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!nump.getText().isEmpty() && !nomP.getText().isEmpty() && !prenomP.getText().isEmpty()) {
                    Add(nump.getText(), nomP.getText(), prenomP.getText(), categorieP.getText(), salair.getText());
                    try {
                        displayTable("Employe");
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnNewButton.setBounds(321, 227, 89, 23);
        contentPane.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Return");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client ee = new Client();
                ee.setVisible(true);
                dispose();
            }
        });
        btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnNewButton_1.setBounds(222, 227, 89, 23);
        contentPane.add(btnNewButton_1);
    }

    public void Add(String numc, String nomC, String prenomC, String categorie, String salair) {
        String query = "INSERT INTO Employe (numP, nomP, prenomP, categorieP, salair) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, numc);
            statement.setString(2, nomC);
            statement.setString(3, prenomC);
            statement.setString(4, categorie);
            statement.setString(5, salair);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Data inserted successfully.");
            } else {
                System.out.println("Failed to insert data.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to insert data.");
            e.printStackTrace();
        }
    }

    public void displayTable(String tableName) throws SQLException {

        String query = "SELECT * FROM " + tableName;
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
}


