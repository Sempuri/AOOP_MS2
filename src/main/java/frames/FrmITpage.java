/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author EMAN
 */
public class FrmITpage extends javax.swing.JFrame {
    
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";

    FrmITpage _itPage = this;
    private FrmNewAccount itPageRef;
    
    /**
     * Creates new form FrmITpage
     */
    public FrmITpage() {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null); // Center the frame
        setPreferredSize(new Dimension(1120, 670));
        
        // Set frame properties
        pack();  // Adjust size to the preferred size
        
        displayBgFromDatabase("FrmITpage");
        
        setAlternateRowAndColumnColorRenderer(tblAccounts);
        setAlternateRowAndColumnColorRenderer(tblResetPass);
        
        // Add a DocumentListener to the employee acc search text field
        txtSearchAccount.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    searchAccounts(txtSearchAccount.getText().trim());
                } catch (SQLException ex) {
                    Logger.getLogger(FrmITpage.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(FrmITpage.this, "Error searching for employee accounts: " + ex.getMessage(), "Search Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    searchAccounts(txtSearchAccount.getText().trim());
                } catch (SQLException ex) {
                    Logger.getLogger(FrmITpage.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(FrmITpage.this, "Error searching for employee accounts: " + ex.getMessage(), "Search Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not used for plain text components
            }
        });
        
        txtSearchPassword.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    searchPasswords(txtSearchPassword.getText().trim());
                } catch (SQLException ex) {
                    Logger.getLogger(FrmITpage.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(FrmITpage.this, "Error searching for password requests: " + ex.getMessage(), "Search Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    searchPasswords(txtSearchPassword.getText().trim());
                } catch (SQLException ex) {
                    Logger.getLogger(FrmITpage.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(FrmITpage.this, "Error searching for password requests: " + ex.getMessage(), "Search Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not used for plain text components
            }
        });
    }
    
    private void setAlternateRowAndColumnColorRenderer(JTable table) {
        // Ensure that the custom renderer is applied to all columns
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(new AlternateRowAndColumnColorRenderer());
        }
    }

    // Inner class for custom cell renderer
    private static class AlternateRowAndColumnColorRenderer extends DefaultTableCellRenderer {

        private static final Color EVEN_COLUMN_COLOR = new Color(252, 235, 230);
        private static final Color ODD_COLUMN_COLOR = new Color(252, 247, 245); 

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Determine base color based on column
            Color baseColor = (column % 2 == 0) ? EVEN_COLUMN_COLOR : ODD_COLUMN_COLOR;

            // Adjust color based on row
            if (row % 2 != 0) {
                // Make the row color slightly darker
                baseColor = new Color(
                    Math.max(baseColor.getRed() - 15, 0),
                    Math.max(baseColor.getGreen() - 15, 0),
                    Math.max(baseColor.getBlue() - 10, 0)
                );
            }

            c.setBackground(baseColor);

            return c;
        }
    }
    
    // Method to fetch and display image from the database
    private void displayBgFromDatabase(String frameName) {
        try (Connection conn = DriverManager.getConnection(imgJDBC_URL, imgUSERNAME, imgPASSWORD)) {
            // Prepare SQL statement to retrieve image data from the database
            String SELECT_IMAGE = "SELECT Img FROM Image WHERE Frame = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(SELECT_IMAGE)) {
                // Set the frame name as a parameter for the prepared statement
                pstmt.setString(1, frameName);

                // Execute the SELECT statement
                ResultSet resultSet = pstmt.executeQuery();

                // Check if a result was found
                if (resultSet.next()) {
                    // Retrieve the image data as a byte array
                    byte[] imgByteArray = resultSet.getBytes("Img");

                    // Convert the byte array to an ImageIcon
                    ImageIcon icon = new ImageIcon(imgByteArray);

                    // Set the ImageIcon as the icon for the JLabel
                    jLabel1.setIcon(icon);
                } else {
                    System.out.println("Image not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }
    
    // Method to retrieve the status of a pass request based on its index
    public String getPasswordRequestStatus(int rowIndex) {
        // Assuming your table model stores the status in a specific column
        return (String) tblResetPass.getModel().getValueAt(rowIndex, 4);
    }
    
    // Method to refresh the tblAccounts table
    public void refreshTblAccounts() {
        DefaultTableModel modelAccounts = (DefaultTableModel) tblAccounts.getModel();
        modelAccounts.setRowCount(0); // Clear existing rows

        // Retrieve updated data for tblAccounts from the database
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sqlAccounts = "SELECT * FROM login ORDER BY EmployeeNum"; // Order by EmployeeNum column
            try (PreparedStatement statement = conn.prepareStatement(sqlAccounts);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Object[] rowData = {
                        resultSet.getInt("EmployeeNum"),
                        resultSet.getString("Username"),
                        resultSet.getString("Password"),
                        resultSet.getString("Role")
                    };
                    modelAccounts.addRow(rowData); // Add row to the table model
                }
            }

            // Print a message to indicate that tblAccounts has been refreshed
            System.out.println("tblAccounts Refreshed.");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Method to refresh tblResetPass table
    public void refreshTblResetPass() {
        DefaultTableModel modelResetPass = (DefaultTableModel) tblResetPass.getModel();
        modelResetPass.setRowCount(0); // Clear existing rows

        // Retrieve updated data for tblResetPass from the database
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sqlResetPass = "SELECT * FROM password_request";
            try (PreparedStatement statement = conn.prepareStatement(sqlResetPass);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Object[] rowData = {
                            resultSet.getInt("EmployeeNum"),
                            resultSet.getString("Username"),
                            resultSet.getString("RequestDate"),
                            resultSet.getString("NewPassword"),
                            resultSet.getString("Status"),
                            resultSet.getString("Approver"),
                            resultSet.getString("DateResponded")
                    };
                    modelResetPass.addRow(rowData); // Add row to the table model
                }
            }

            // Print a message to indicate that tblResetPass has been refreshed
            System.out.println("tblResetPass Refreshed.");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateTable(Object[] accountInfo) {
        // Add the account information to the table
        DefaultTableModel model = (DefaultTableModel) tblAccounts.getModel();

        // Print out the account information for debugging
        System.out.println("Employee ID: " + accountInfo[0]);
        System.out.println("Username: " + accountInfo[1]);
        System.out.println("Password: " + accountInfo[2]);
        System.out.println("Role: " + accountInfo[3]);

        // Create a new Object[] array with the account information in the correct order
        Object[] rowData = {
            accountInfo[0],  // Employee ID
            accountInfo[1],  // Username
            accountInfo[2],  // Password
            accountInfo[3]   // Role
        };

        // Add the new row to the table model
        model.addRow(rowData);
    }
  
    //for updating a specific row -> for edit account info 
    public void updateTableRow(int rowIndex, String[] accountInfo) {
        DefaultTableModel model = (DefaultTableModel) tblAccounts.getModel();
        

        // Update the specified row in the table model
        for (int i = 0; i < accountInfo.length; i++) {
            model.setValueAt(accountInfo[i], rowIndex, i);
        }
    }
    
    //for updating a specific row -> for edit password info 
    public void updatePasswordTableRow(int rowIndex, String[] passwordtInfo) {
        DefaultTableModel model = (DefaultTableModel) tblResetPass.getModel();
        

        // Update the specified row in the table model
        for (int i = 0; i < passwordtInfo.length; i++) {
            model.setValueAt(passwordtInfo[i], rowIndex, i);
        }
    }
    
    //getter
    public JTable getTblAccounts() {
        return tblAccounts;
    }
    
    public JTable getTblResetPass(){
        return tblResetPass;
    }

    public JLabel getLblEid() {
        return lblEid;
    }

    public JLabel getLblFName() {
        return lblFName;
    }
    
    
    
    // Method to display password reset requests in tblResetPass
    public void displayPasswordInfo(FrmITpage _itPage, ResultSet resultSet) throws SQLException {
        List<Object[]> passwordData = new ArrayList<>();

        while (resultSet.next()) {
           passwordData.add(new Object[]{
                resultSet.getInt("EmployeeNum"),
                resultSet.getString("Username"),
                resultSet.getString("RequestDate"),
                resultSet.getString("NewPassword"),
                resultSet.getString("Status"),
                resultSet.getString("Approver"),
                resultSet.getString("DateResponded")
            });
        }
        
        // Update the UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            DefaultTableModel model = (DefaultTableModel) _itPage.getTblResetPass().getModel();
            for (Object[] row : passwordData) {
                model.addRow(row);
            }
            // Refresh the table to ensure the data is displayed
            model.fireTableDataChanged();
        });
    }

    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblWelcomeMsg = new javax.swing.JLabel();
        lblFName = new javax.swing.JLabel();
        lblSearchPassword = new javax.swing.JLabel();
        lblPassReq = new javax.swing.JLabel();
        lblIDniEmployee = new javax.swing.JLabel();
        lblEid = new javax.swing.JLabel();
        txtSearchPassword = new javax.swing.JTextField();
        lblSearchAccount = new javax.swing.JLabel();
        txtSearchAccount = new javax.swing.JTextField();
        logOut_button = new javax.swing.JButton();
        lblEmpInfoTable = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblResetPass = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblAccounts = new javax.swing.JTable();
        btnRespond = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1120, 670));
        setMinimumSize(new java.awt.Dimension(1120, 670));
        setPreferredSize(new java.awt.Dimension(1120, 670));
        setSize(new java.awt.Dimension(1120, 670));
        getContentPane().setLayout(null);

        lblWelcomeMsg.setFont(new java.awt.Font("Verdana", 0, 25)); // NOI18N
        lblWelcomeMsg.setForeground(new java.awt.Color(60, 45, 45));
        lblWelcomeMsg.setText("Welcome, IT");
        getContentPane().add(lblWelcomeMsg);
        lblWelcomeMsg.setBounds(160, 20, 158, 32);

        lblFName.setFont(new java.awt.Font("Verdana", 1, 25)); // NOI18N
        lblFName.setForeground(new java.awt.Color(136, 185, 172));
        lblFName.setText("firstname");
        getContentPane().add(lblFName);
        lblFName.setBounds(330, 20, 310, 32);

        lblSearchPassword.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblSearchPassword.setForeground(new java.awt.Color(77, 74, 57));
        lblSearchPassword.setText("Search:");
        getContentPane().add(lblSearchPassword);
        lblSearchPassword.setBounds(504, 508, 70, 30);

        lblPassReq.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        lblPassReq.setForeground(new java.awt.Color(77, 74, 57));
        lblPassReq.setText("Change Password Request Table");
        getContentPane().add(lblPassReq);
        lblPassReq.setBounds(500, 150, 400, 30);

        lblIDniEmployee.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        lblIDniEmployee.setForeground(new java.awt.Color(60, 45, 45));
        lblIDniEmployee.setText("Employee ID:");
        getContentPane().add(lblIDniEmployee);
        lblIDniEmployee.setBounds(160, 50, 96, 18);

        lblEid.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblEid.setForeground(new java.awt.Color(60, 45, 45));
        lblEid.setText("###");
        getContentPane().add(lblEid);
        lblEid.setBounds(260, 50, 33, 18);

        txtSearchPassword.setBackground(new java.awt.Color(255, 255, 255));
        txtSearchPassword.setForeground(new java.awt.Color(68, 54, 54));
        txtSearchPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchPasswordActionPerformed(evt);
            }
        });
        getContentPane().add(txtSearchPassword);
        txtSearchPassword.setBounds(568, 510, 90, 27);

        lblSearchAccount.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblSearchAccount.setForeground(new java.awt.Color(77, 74, 57));
        lblSearchAccount.setText("Search:");
        getContentPane().add(lblSearchAccount);
        lblSearchAccount.setBounds(50, 508, 70, 30);

        txtSearchAccount.setBackground(new java.awt.Color(255, 255, 255));
        txtSearchAccount.setForeground(new java.awt.Color(68, 54, 54));
        txtSearchAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchAccountActionPerformed(evt);
            }
        });
        getContentPane().add(txtSearchAccount);
        txtSearchAccount.setBounds(110, 510, 90, 27);

        logOut_button.setBackground(new java.awt.Color(255, 255, 255));
        logOut_button.setFont(new java.awt.Font("Avenir Next", 1, 24)); // NOI18N
        logOut_button.setForeground(new java.awt.Color(102, 77, 77));
        logOut_button.setText("Log out");
        logOut_button.setToolTipText("");
        logOut_button.setAutoscrolls(true);
        logOut_button.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        logOut_button.setBorderPainted(false);
        logOut_button.setOpaque(true);
        logOut_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOut_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(logOut_button);
        logOut_button.setBounds(980, 20, 110, 40);

        lblEmpInfoTable.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        lblEmpInfoTable.setForeground(new java.awt.Color(77, 74, 57));
        lblEmpInfoTable.setText("Employee Accounts Table");
        getContentPane().add(lblEmpInfoTable);
        lblEmpInfoTable.setBounds(50, 150, 221, 30);

        tblResetPass.setBackground(new java.awt.Color(207, 236, 229));
        tblResetPass.setForeground(new java.awt.Color(60, 52, 52));
        tblResetPass.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Emp ID", "Username", "Date of Request", "New Password", "Status", "Approver", "Approved Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblResetPass.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tblResetPass.setSelectionForeground(new java.awt.Color(255, 0, 0));
        jScrollPane4.setViewportView(tblResetPass);
        if (tblResetPass.getColumnModel().getColumnCount() > 0) {
            tblResetPass.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        getContentPane().add(jScrollPane4);
        jScrollPane4.setBounds(500, 190, 570, 310);

        tblAccounts.setBackground(new java.awt.Color(236, 207, 207));
        tblAccounts.setForeground(new java.awt.Color(60, 52, 52));
        tblAccounts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Emp ID", "Username", "Password", "Role"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAccounts.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tblAccounts.setSelectionForeground(new java.awt.Color(255, 0, 0));
        jScrollPane3.setViewportView(tblAccounts);

        getContentPane().add(jScrollPane3);
        jScrollPane3.setBounds(50, 190, 420, 310);

        btnRespond.setBackground(new java.awt.Color(255, 255, 255));
        btnRespond.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnRespond.setForeground(new java.awt.Color(68, 58, 58));
        btnRespond.setText("Respond");
        btnRespond.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRespondActionPerformed(evt);
            }
        });
        getContentPane().add(btnRespond);
        btnRespond.setBounds(960, 510, 110, 28);

        btnAdd.setBackground(new java.awt.Color(255, 255, 255));
        btnAdd.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(68, 58, 58));
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        getContentPane().add(btnAdd);
        btnAdd.setBounds(230, 510, 76, 28);

        btnEdit.setBackground(new java.awt.Color(255, 255, 255));
        btnEdit.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(68, 58, 58));
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        getContentPane().add(btnEdit);
        btnEdit.setBounds(310, 510, 76, 28);

        btnDelete.setBackground(new java.awt.Color(255, 255, 255));
        btnDelete.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(68, 58, 58));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        getContentPane().add(btnDelete);
        btnDelete.setBounds(390, 510, 79, 28);

        btnRefresh.setBackground(new java.awt.Color(255, 255, 255));
        btnRefresh.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(68, 58, 58));
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        getContentPane().add(btnRefresh);
        btnRefresh.setBounds(990, 160, 80, 20);

        jLabel1.setText("it");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, -20, 1120, 690);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private boolean isAddAccountFormOpen = false; // Flag to track if the add account form is open
    
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // Check if the add account form is already open
        if (!isAddAccountFormOpen) {
            // Set the flag to indicate that the add account form is now open
            isAddAccountFormOpen = true;

            // Open the form to input new employee data
            FrmNewAccount _addAccount = new FrmNewAccount(this);
            _addAccount.setVisible(true);
            _addAccount.setLocationRelativeTo(null);

            // Add a window listener to detect when the add account form is closed
            _addAccount.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    // Reset the flag when the add account form is closed
                    isAddAccountFormOpen = false;
                }
            });
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private boolean isEditAccountFormOpen = false; // Flag to track if the edit account form is open
    
    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // Check if the edit account form is already open
        if (!isEditAccountFormOpen) {
            // Set the flag to indicate that the edit account form is now open
            isEditAccountFormOpen = true;

            // Get the selected row index
            int viewRowIndex = tblAccounts.getSelectedRow();

            // Check if a row is selected
            if (viewRowIndex == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row to edit.", "Error", JOptionPane.ERROR_MESSAGE);
                // Reset the flag since the edit account form is not opened
                isEditAccountFormOpen = false;
                return;
            }
            
            // Convert view row index to model row index
            int modelRowIndex = tblAccounts.convertRowIndexToModel(viewRowIndex);

            // Retrieve the data from the selected row
            DefaultTableModel model = (DefaultTableModel) tblAccounts.getModel();
            String[] accountInfo = new String[model.getColumnCount()];
            for (int i = 0; i < model.getColumnCount(); i++) {
                accountInfo[i] = model.getValueAt(modelRowIndex, i).toString();
            }

            // Pass the reference to the existing FrmHRpage instance to FrmEditAccount
            FrmEditAccount editAccountForm = new FrmEditAccount(this); // Pass the reference
            editAccountForm.populateFields(accountInfo);
            editAccountForm.setVisible(true);
            editAccountForm.setLocationRelativeTo(null); // Center the frame

            // Add a window listener to detect when the edit account form is closed
            editAccountForm.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    // Reset the flag when the edit account form is closed
                    isEditAccountFormOpen = false;
                }
            });
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private boolean isDeleteAccountFormOpen = false; // Flag to track if the delete account form is open
    
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // Check if the delete account form is already open
        if (!isDeleteAccountFormOpen) {
            // Set the flag to indicate that the delete account form is now open
            isDeleteAccountFormOpen = true;

            // Get the selected row index
            int viewRowIndex = tblAccounts.getSelectedRow();

            // Check if a row is selected
            if (viewRowIndex == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                // Reset the flag since the delete account form is not opened
                isDeleteAccountFormOpen = false;
                return;
            }
            
            // Convert view row index to model row index
            int modelRowIndex = tblAccounts.convertRowIndexToModel(viewRowIndex);

            // Retrieve the data from the selected row
            DefaultTableModel model = (DefaultTableModel) tblAccounts.getModel();
            String[] accountInfo = new String[model.getColumnCount()];
            for (int i = 0; i < model.getColumnCount(); i++) {
                accountInfo[i] = model.getValueAt(modelRowIndex, i).toString();
            }

            // Pass the reference to the existing FrmHRpage instance to FrmDeleteAccount
            FrmDeleteAccount deleteAccountForm = new FrmDeleteAccount(this); // Pass the reference
            deleteAccountForm.populateFields(accountInfo);
            deleteAccountForm.setVisible(true);
            deleteAccountForm.setLocationRelativeTo(null); // Center the frame

            // Add a window listener to detect when the delete account form is closed
            deleteAccountForm.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    // Reset the flag when the delete account form is closed
                    isDeleteAccountFormOpen = false;
                }
            });
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void logOut_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOut_buttonActionPerformed
        // TODO add your handling code here:
        FrmLogin logOut = null;
        try {
            logOut = new FrmLogin();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FrmITpage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FrmITpage.class.getName()).log(Level.SEVERE, null, ex);
        }
        logOut.show();
        logOut.setLocationRelativeTo(null); // Center the frame

        dispose();
    }//GEN-LAST:event_logOut_buttonActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // Refresh tblAccounts
        DefaultTableModel modelAccounts = (DefaultTableModel) tblAccounts.getModel();
        modelAccounts.setRowCount(0); // Clear existing rows

        // Refresh tblResetPass
        DefaultTableModel modelResetPass = (DefaultTableModel) tblResetPass.getModel();
        modelResetPass.setRowCount(0); // Clear existing rows
        
        txtSearchAccount.setText("");
        txtSearchPassword.setText("");

        // Retrieve updated data for tblAccounts from the database
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sqlAccounts = "SELECT * FROM login ORDER BY EmployeeNum"; // Order by EmployeeNum column
            try (PreparedStatement statement = conn.prepareStatement(sqlAccounts);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Object[] rowData = {
                        resultSet.getInt("EmployeeNum"),
                        resultSet.getString("Username"),
                        resultSet.getString("Password"),
                        resultSet.getString("Role")
                    };
                    modelAccounts.addRow(rowData); // Add row to the table model
                }
            }

            // Print a message to indicate that tblAccounts has been refreshed
            System.out.println("tblAccounts Refreshed.");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        // Retrieve updated data for tblResetPass from the database
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sqlResetPass = "SELECT * FROM password_request";
            try (PreparedStatement statement = conn.prepareStatement(sqlResetPass);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Object[] rowData = {
                        resultSet.getInt("EmployeeNum"),
                        resultSet.getString("Username"),
                        resultSet.getString("RequestDate"),
                        resultSet.getString("NewPassword"),
                        resultSet.getString("Status"),
                        resultSet.getString("Approver"),
                        resultSet.getString("DateResponded")
                    };
                    modelResetPass.addRow(rowData); // Add row to the table model
                }
            }

            // Print a message to indicate that tblResetPass has been refreshed
            System.out.println("tblResetPass Refreshed.");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txtSearchAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchAccountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchAccountActionPerformed

    private boolean isEditPasswordFormOpen = false; // Flag to track if the edit overtime form is open
    
    private void btnRespondActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRespondActionPerformed
      // Check if the edit password form is already open
        if (!isEditPasswordFormOpen) {
            // Set the flag to indicate that the edit overtime form is now open
            isEditPasswordFormOpen = true;

            // Get the selected row index
            int viewRowIndex = tblResetPass.getSelectedRow();

            // Check if a row is selected
            if (viewRowIndex == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row to respond.", "Error", JOptionPane.ERROR_MESSAGE);
                // Reset the flag since the edit overtime form is not opened
                isEditPasswordFormOpen = false;
                return;
            }
            
            // Convert view row index to model row index
            int modelRowIndex = tblResetPass.convertRowIndexToModel(viewRowIndex);

            // Retrieve the data from the selected row
            DefaultTableModel model = (DefaultTableModel) tblResetPass.getModel();
            String[] passwordInfo = new String[model.getColumnCount()];
            for (int i = 0; i < model.getColumnCount(); i++) {
                Object value = model.getValueAt(modelRowIndex, i);
                passwordInfo[i] = (value != null) ? value.toString() : ""; // Handle null values gracefully
            }

            // Pass the reference to the existing FrmITpage instance to FrmEditOvertime
            FrmEditPassword editPasswordRequest = new FrmEditPassword(this); // Pass the reference
            editPasswordRequest.populateFields(passwordInfo);
            editPasswordRequest.setVisible(true);
            editPasswordRequest.setLocationRelativeTo(null); // Center the frame

            // Add a window listener to detect when the edit password form is closed
            editPasswordRequest.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    // Reset the flag when the edit password form is closed
                    isEditPasswordFormOpen = false;
                }
            });
        }
    }//GEN-LAST:event_btnRespondActionPerformed

    private void txtSearchPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchPasswordActionPerformed

    // Method to perform search operation in TblEmpInfo table
    private void searchAccounts(String searchText) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) _itPage.getTblAccounts().getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        _itPage.getTblAccounts().setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText)); // Case-insensitive search
    }
    
    // Method to perform search operation in TblEmpInfo table
    private void searchPasswords (String searchText) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) _itPage.getTblResetPass().getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        _itPage.getTblResetPass().setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText)); // Case-insensitive search
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmITpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmITpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmITpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmITpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmITpage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRespond;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblEid;
    private javax.swing.JLabel lblEmpInfoTable;
    private javax.swing.JLabel lblFName;
    private javax.swing.JLabel lblIDniEmployee;
    private javax.swing.JLabel lblPassReq;
    private javax.swing.JLabel lblSearchAccount;
    private javax.swing.JLabel lblSearchPassword;
    private javax.swing.JLabel lblWelcomeMsg;
    private javax.swing.JButton logOut_button;
    private javax.swing.JTable tblAccounts;
    private javax.swing.JTable tblResetPass;
    private javax.swing.JTextField txtSearchAccount;
    private javax.swing.JTextField txtSearchPassword;
    // End of variables declaration//GEN-END:variables
}
