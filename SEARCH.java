package Home;

import java.awt.Color;
import java.awt.EventQueue;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class SEARCH extends JFrame {
    /**
     *
     */
	public Connection connection1;
	
	
	
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private JScrollPane scrollPane;
    private JTextField textField;

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USER = "system";
    private static final String DB_PASSWORD = "psw";
    private Connection connection;
    private JTextField textField_1;
    private JComboBox comboBox;

  
		
		
		
	
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SEARCH frame = new SEARCH();
                    frame.setVisible(true);
                    
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public SEARCH() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 181, 98));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Tables:");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel.setBounds(10, 11, 100, 25);
        contentPane.add(lblNewLabel);

        JButton btnDisplayTables = new JButton("SEE ALL");
        btnDisplayTables.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnDisplayTables.setBounds(10, 47, 88, 25);
        contentPane.add(btnDisplayTables);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 83, 414, 167);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
        table.setFillsViewportHeight(true);
        table.setColumnSelectionAllowed(true);
        table.setCellSelectionEnabled(true);

        textField = new JTextField();
        textField.setBounds(108, 49, 79, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        JButton btnNewButton = new JButton("SEARCH");
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnNewButton.setBounds(197, 48, 79, 23);
        contentPane.add(btnNewButton);

        textField_1 = new JTextField();
        textField_1.setBounds(345, 49, 79, 20);
        contentPane.add(textField_1);
        textField_1.setColumns(10);
        
        comboBox = new JComboBox();
        comboBox.setFont(new Font("Tahoma", Font.BOLD, 11));
        comboBox.setForeground(new Color(0, 0, 0));
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "numC", "numP", "numM", "numMo", "numV", "numI"}));
        comboBox.setBounds(280, 48, 61, 22);
        contentPane.add(comboBox);

        btnDisplayTables.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tableName = textField.getText();
                if (tableName.isEmpty()) {
                    JOptionPane.showMessageDialog(contentPane, "Please enter a table name.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    displayTable(tableName);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(contentPane, "Failed to retrieve data from the table.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tableName = textField.getText();
                String idText = textField_1.getText();
                String selectedColumn = comboBox.getSelectedItem().toString();

                if (tableName.isEmpty()) {
                    JOptionPane.showMessageDialog(contentPane, "Please enter a table name.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (idText.isEmpty()) {
                    JOptionPane.showMessageDialog(contentPane, "Please enter an ID.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int id = Integer.parseInt(idText);
                    executeSearchQueryByID(tableName, selectedColumn, id);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(contentPane, "Invalid ID format. Please enter a valid integer ID.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(contentPane, "Failed to retrieve data from the table.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    public void displayTable(String tableName) throws SQLException {
        
        String query = "SELECT * FROM " + tableName;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            connection1 = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = connection1.createStatement();
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

            table.setModel(tableModel);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (connection1 != null) {
                connection1.close();
            }
        }
    }
    
    public void executeSearchQueryByID(String tableName, String columnName, int id) throws SQLException {
        
        String query = "SELECT * FROM " + tableName + " WHERE " + columnName + " = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection1 = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = connection1.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

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

            table.setModel(tableModel);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (connection1 != null) {
                connection1.close();
            }
        }
    }
}
