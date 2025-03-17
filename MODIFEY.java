package Home;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class MODIFEY extends JFrame {

    private JPanel contentPane;
    private JTextField TableNAME;
    private JTextField Valeur;

    // Database connection parameters

    private JTextField Attributs;
    private JTable table;
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
                    MODIFEY frame = new MODIFEY();
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
    public MODIFEY() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(254, 179, 95));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Modify");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(171, 11, 100, 27);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Table :");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_1.setBounds(10, 79, 84, 27);
        contentPane.add(lblNewLabel_1);

        TableNAME = new JTextField();
        TableNAME.setText("");
        TableNAME.setBounds(56, 82, 86, 20);
        contentPane.add(TableNAME);
        TableNAME.setColumns(10);

        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setFont(new Font("Tahoma", Font.BOLD, 11));
        comboBox.setModel(new DefaultComboBoxModel<>(new String[] {"", "numC", "numP", "numM", "numMo", "numV", "numI"}));
        comboBox.setBounds(152, 81, 68, 22);
        contentPane.add(comboBox);

        JComboBox<String> comboBox_1 = new JComboBox<>();
        comboBox_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        comboBox_1.setModel(new DefaultComboBoxModel<>(new String[] {"", "civ", "prenomC", "nomC", "adresse", "telprofC", "telprivC", "faxC", "nomP", "prenomP", "categorieP", "salair", "marque", "pays", "modele", "numVV", "typeI", "coutI"}));
        comboBox_1.setBounds(225, 81, 68, 22);
        contentPane.add(comboBox_1);

        Valeur = new JTextField();
        Valeur.setBounds(364, 82, 60, 20);
        contentPane.add(Valeur);
        Valeur.setColumns(10);

        Attributs = new JTextField();
        Attributs.setBounds(303, 82, 57, 20);
        contentPane.add(Attributs);
        Attributs.setColumns(10);
        
        JLabel lblNewLabel_2 = new JLabel("ID");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_2.setBounds(295, 64, 46, 14);
        contentPane.add(lblNewLabel_2);
        
        JButton btnNewButton = new JButton("Update");
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tableName = comboBox.getSelectedItem().toString();
                String fieldName = comboBox_1.getSelectedItem().toString();
                String newValue = Valeur.getText();
                String id = lblNewLabel_2.getText();
                updateFieldInDatabase(tableName, fieldName, newValue, id);
                displayTable(tableName);
            }
        });
        btnNewButton.setBounds(335, 227, 89, 23);
        contentPane.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Return");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Menu ee = new Menu();
                ee.setVisible(true);
                dispose();
            }
        });
        btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnNewButton_1.setBounds(236, 227, 89, 23);
        contentPane.add(btnNewButton_1);
        
        
        
       
        JLabel lblNewLabel_3 = new JLabel("New Value");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_3.setBounds(360, 64, 64, 14);
        contentPane.add(lblNewLabel_3);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 117, 414, 99);
        contentPane.add(scrollPane);
        
        table = new JTable();
        scrollPane.setViewportView(table);
    }

    /**
     * Update a field in the database for the specified table and field name.
     */
    private void updateFieldInDatabase(String tableName,String fieldName, String newValue,String id) {
    	tableName=TableNAME.getText();
    	id=Attributs.getText();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USR, DB_PASSWORD)) {
            String query = "UPDATE " + tableName + " SET " + fieldName + " = ? WHERE " + id + " = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newValue);
            statement.setString(2, Attributs.getText());
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Field updated successfully.");
            } else {
                System.out.println("No rows found with the provided condition.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Display the contents of the specified table.
     */
    private void displayTable(String tabl) {
    	tabl=TableNAME.getText();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USR, DB_PASSWORD)) {
            String query = "SELECT * FROM " + tabl;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            
            // Create the table model
            DefaultTableModel model = new DefaultTableModel();
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(resultSet.getMetaData().getColumnName(i));
            }
            
            // Populate the table model with data
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                model.addRow(row);
            }
            
            // Set the table model to the JTable
            table.setModel(model);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}


