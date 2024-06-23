/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frames;

import classes.CSVFileManager;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author EMAN
 */
public class FrmEditAccount extends javax.swing.JFrame {
    
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";

    private FrmITpage itPageRef;
    /**
     * Creates new form FrmEditAccount
     */
    public FrmEditAccount(FrmITpage itPageRef) {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null); // Center the frame
        setPreferredSize(new Dimension(586, 467));
        
        // Set frame properties
        pack();  // Adjust size to the preferred size
    
        displayBgFromDatabase("FrmEditAccount");
        
        this.itPageRef = itPageRef;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
    
    
    //method for populating textfields
    public void populateFields(String[] accountInfo) {
        txtEmployeeNum.setText(accountInfo[0]);
        txtUsername.setText(accountInfo[1]);
        txtPassword.setText(accountInfo[2]);
        jCBRoleAccess.setSelectedItem(accountInfo[3]);
    }
    
    //method for clearing textfields
    private void clearTextFields() {
        txtEmployeeNum.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        jCBRoleAccess.setSelectedItem(null);
    }
    
    //event handler method for close button
    private void FrameClose(java.awt.event.MouseEvent evt) {                            
        this.dispose();
    }    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        lblRoleAccess = new javax.swing.JLabel();
        jCBRoleAccess = new javax.swing.JComboBox<>();
        lblNote = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblEmployeeNum = new javax.swing.JLabel();
        txtEmployeeNum = new javax.swing.JTextField();
        btnViewEmp = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(586, 467));
        setMinimumSize(new java.awt.Dimension(586, 467));
        setPreferredSize(new java.awt.Dimension(586, 467));
        setSize(new java.awt.Dimension(586, 467));
        getContentPane().setLayout(null);

        lblUsername.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername.setText("Username:");
        getContentPane().add(lblUsername);
        lblUsername.setBounds(130, 190, 73, 17);

        txtUsername.setBackground(new java.awt.Color(255, 255, 255));
        txtUsername.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(60, 51, 51));
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });
        getContentPane().add(txtUsername);
        txtUsername.setBounds(250, 190, 220, 28);

        lblPassword.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(255, 255, 255));
        lblPassword.setText("Password:");
        getContentPane().add(lblPassword);
        lblPassword.setBounds(130, 250, 82, 18);

        txtPassword.setBackground(new java.awt.Color(255, 255, 255));
        txtPassword.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(60, 51, 51));
        getContentPane().add(txtPassword);
        txtPassword.setBounds(250, 250, 220, 28);

        lblRoleAccess.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblRoleAccess.setForeground(new java.awt.Color(255, 255, 255));
        lblRoleAccess.setText("Role Access:");
        getContentPane().add(lblRoleAccess);
        lblRoleAccess.setBounds(130, 310, 98, 18);

        jCBRoleAccess.setBackground(new java.awt.Color(255, 255, 255));
        jCBRoleAccess.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jCBRoleAccess.setForeground(new java.awt.Color(60, 47, 47));
        jCBRoleAccess.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Employee", "HR", "Payroll", "IT" }));
        getContentPane().add(jCBRoleAccess);
        jCBRoleAccess.setBounds(250, 310, 220, 28);

        lblNote.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        lblNote.setForeground(new java.awt.Color(64, 52, 52));
        lblNote.setText("Note: Refresh the table after editing an account.");
        getContentPane().add(lblNote);
        lblNote.setBounds(47, 410, 440, 17);

        btnSave.setBackground(new java.awt.Color(255, 255, 255));
        btnSave.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSave.setForeground(new java.awt.Color(81, 66, 66));
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        getContentPane().add(btnSave);
        btnSave.setBounds(320, 360, 76, 28);

        btnCancel.setBackground(new java.awt.Color(255, 255, 255));
        btnCancel.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(81, 66, 66));
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancel);
        btnCancel.setBounds(400, 360, 80, 28);

        lblEmployeeNum.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblEmployeeNum.setForeground(new java.awt.Color(255, 255, 255));
        lblEmployeeNum.setText("Employee ID:");
        getContentPane().add(lblEmployeeNum);
        lblEmployeeNum.setBounds(130, 110, 120, 20);

        txtEmployeeNum.setEditable(false);
        txtEmployeeNum.setBackground(new java.awt.Color(255, 255, 255));
        txtEmployeeNum.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtEmployeeNum.setForeground(new java.awt.Color(60, 51, 51));
        getContentPane().add(txtEmployeeNum);
        txtEmployeeNum.setBounds(250, 110, 220, 28);

        btnViewEmp.setBackground(new java.awt.Color(255, 255, 255));
        btnViewEmp.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnViewEmp.setForeground(new java.awt.Color(56, 49, 49));
        btnViewEmp.setText("View Employees");
        btnViewEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewEmpActionPerformed(evt);
            }
        });
        getContentPane().add(btnViewEmp);
        btnViewEmp.setBounds(284, 140, 150, 30);

        jLabel1.setText("editacc");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, -40, 590, 510);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
         // Check if any of the text fields are empty
        if (txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty() || jCBRoleAccess.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method without saving
        }
        
        // Get the selected row index
        int selectedRowIndex = itPageRef.getTblAccounts().getSelectedRow();
        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Prepare empInfo array with updated employee information
        String[] accountInfo = {
            txtUsername.getText(),                              // Username
            txtPassword.getText(),                              // Password
            jCBRoleAccess.getSelectedItem().toString(),         //Role Access
        };

        // Update table in FrmITpage with the edited account record
        itPageRef.updateTableRow(selectedRowIndex, accountInfo);

        // Update CSV file with data from the table in FrmITpage
        String csvFilePath = "src/main/java/files/Users.csv";
        JTable table = itPageRef.getTblAccounts(); // Assuming getTable() returns the JTable instance in FrmITpage
        CSVFileManager.updateCSVFile(csvFilePath, table);

        // Show success message
        JOptionPane.showMessageDialog(this, "Account updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        // Clear text fields after saving
        clearTextFields();
    }//GEN-LAST:event_btnSaveActionPerformed
*/
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // Check if any of the text fields are empty
        if (txtEmployeeNum.getText().isEmpty() || txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty() || jCBRoleAccess.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method without saving
        }

        // Get the selected row index
        int selectedRowIndex = itPageRef.getTblAccounts().getSelectedRow();
        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select an account to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Prepare the account information
        String oldUsername = itPageRef.getTblAccounts().getValueAt(selectedRowIndex, 1).toString(); // Assuming Username is in the second column
        String newUsername = txtUsername.getText();
        String password = txtPassword.getText();
        String role = jCBRoleAccess.getSelectedItem().toString();

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Check if the new username already exists
            String checkQuery = "SELECT COUNT(*) FROM login WHERE Username = ? AND Username != ?";
            try (PreparedStatement checkStatement = conn.prepareStatement(checkQuery)) {
                checkStatement.setString(1, newUsername);
                checkStatement.setString(2, oldUsername);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        // New username already exists
                        JOptionPane.showMessageDialog(this, "This username already exists. Please try again with a different username.", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Exit the method without saving
                    }
                }
            }

            // Prepare the SQL SELECT statement to retrieve the existing account details
            String selectQuery = "SELECT Username, Password, Role FROM login WHERE Username = ?";
            try (PreparedStatement selectStatement = conn.prepareStatement(selectQuery)) {
                selectStatement.setString(1, oldUsername);
                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Retrieve the existing account details
                        String existingUsername = resultSet.getString("Username");
                        String existingPassword = resultSet.getString("Password");
                        String existingRole = resultSet.getString("Role");

                        // Check if any changes have been made to the account details
                        if (newUsername.equals(existingUsername) && password.equals(existingPassword) && role.equals(existingRole)) {
                            // No changes detected
                            JOptionPane.showMessageDialog(this, "No changes detected.", "No Changes", JOptionPane.INFORMATION_MESSAGE);
                            // Exit the method without saving
                            return;
                        }
                    }
                }
            }

            // Prepare the SQL UPDATE statement
            String sql = "UPDATE login SET Username = ?, Password = ?, Role = ? WHERE Username = ?";

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                // Set parameters for the prepared statement
                statement.setString(1, newUsername);
                statement.setString(2, password);
                statement.setString(3, role);
                statement.setString(4, oldUsername);

                // Execute the update statement
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    // Show success message
                    JOptionPane.showMessageDialog(this, "Account updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Update table in FrmITpage with the edited account record
                    itPageRef.updateTableRow(selectedRowIndex, new String[]{txtEmployeeNum.getText(), newUsername, password, role});

                    // Clear text fields after saving
                    clearTextFields();

                    // Close the current frame
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update account.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private boolean isExistingEmployeeFrameOpen = false; // Flag to track if the existing employee frame is open
    
    private void btnViewEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewEmpActionPerformed
        // Check if the existing employee frame is already open
        if (!isExistingEmployeeFrameOpen) {
            // Set the flag to indicate that the existing employee frame is now open
            isExistingEmployeeFrameOpen = true;

            FrmExistingEmployee existingEmployeeFrame = new FrmExistingEmployee();
            // Make the frame visible
            existingEmployeeFrame.setVisible(true);

            // Add a window listener to detect when the existing employee frame is closed
            existingEmployeeFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    // Reset the flag when the existing employee frame is closed
                    isExistingEmployeeFrameOpen = false;
                }
            });
        }        
    }//GEN-LAST:event_btnViewEmpActionPerformed

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
            java.util.logging.Logger.getLogger(FrmEditAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmEditAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmEditAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmEditAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new FrmEditAccount().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    public javax.swing.JButton btnSave;
    private javax.swing.JButton btnViewEmp;
    public javax.swing.JComboBox<String> jCBRoleAccess;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblEmployeeNum;
    private javax.swing.JLabel lblNote;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblRoleAccess;
    private javax.swing.JLabel lblUsername;
    public javax.swing.JTextField txtEmployeeNum;
    public javax.swing.JTextField txtPassword;
    public javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
