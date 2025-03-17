package Home;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class REMOVE extends JFrame {

    private JPanel contentPane;
    private JTextField ID;

    // Database connection parameters
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USERNAME = "system";
    private static final String DB_PASSWORD = "psw";
    private JTextField tableName;
    private JComboBox<String> comboBox;
    private JTable table;
    public Connection connection;

    private void connectToDataBase() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
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
                    REMOVE frame = new REMOVE();
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
    public REMOVE() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 181, 98));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblNewLabel = new JLabel("REMOVE");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(140, 11, 108, 25);
        contentPane.add(lblNewLabel);

        ID = new JTextField();
        ID.setBounds(268, 120, 133, 20);
        contentPane.add(ID);
        ID.setColumns(10);

        JButton btnNewButton = new JButton("REMOVE");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = comboBox.getSelectedItem().toString();
                String selectedTable = tableName.getText();
                removeRowFromDatabase(selectedTable, id);
                displayTable(selectedTable);
            }
        });
        btnNewButton.setBounds(367, 327, 89, 23);
        contentPane.add(btnNewButton);

        JButton btnReturn = new JButton("RETURN");
        btnReturn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Menu ee = new Menu();
                ee.setVisible(true);
                dispose();
            }
        });
        btnReturn.setBounds(263, 327, 89, 23);
        contentPane.add(btnReturn);

        tableName = new JTextField();
        tableName.setBounds(24, 120, 143, 20);
        contentPane.add(tableName);
        tableName.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("Table :");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_2.setBounds(21, 95, 46, 14);
        contentPane.add(lblNewLabel_2);

        comboBox = new JComboBox<>();
        comboBox.setFont(new Font("Tahoma", Font.BOLD, 11));
        comboBox.setModel(new DefaultComboBoxModel<>(new String[] { "", "numC", "numP", "numM", "numMo", "numI", "numV" }));
        comboBox.setBounds(177, 119, 71, 22);
        contentPane.add(comboBox);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(24, 170, 397, 150);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
    }

    /**
     * Remove a row from the database based on the provided table name and primary key.
     */
    private void removeRowFromDatabase(String table, String id) {
        String query = "DELETE FROM " + table + " WHERE " + id + " = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, ID.getText());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Data deleted successfully.");
            } else {
                System.out.println("Failed to delete data.");
            }
        } catch (SQLException ee) {
            System.out.println("Failed to delete data.");
            ee.printStackTrace();
        }
    }

    /**
     * Display the contents of the specified table.
     */
    private void displayTable(String tabl) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM " + tabl;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Create a DefaultTableModel to hold the table data
            DefaultTableModel tableModel = new DefaultTableModel();
            table.setModel(tableModel);

            // Get the column names from the result set
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(resultSet.getMetaData().getColumnName(i));
            }

            // Add rows to the table model
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
