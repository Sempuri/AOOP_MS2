/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frames;

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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author yen
 */
public class FrmEditPassword extends javax.swing.JFrame {
    
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";
    
    private FrmITpage itPageRef;
    private JTable tblResetPass;

    /**
     * Creates new form FrmEditPassword
     */
    public FrmEditPassword(FrmITpage itPageRef) {
        initComponents();
        
        setResizable(false);
        setLocationRelativeTo(null); // Center the frame
        setPreferredSize(new Dimension(630, 720));
        
        // Set frame properties
        pack();  // Adjust size to the preferred size
    
        displayBgFromDatabase("FrmEditPassword");
        showDate();
        tblResetPass = new JTable();
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
    
    //for showing of date
    public void showDate() {
        Date d = new Date();
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd"); // Output date pattern
        String formattedDate = outputFormat.format(d); // Format the date
        lblDate.setText(formattedDate); // Set the formatted date to the label
    }
    
    // Method to populate fields with password information
    public void populateFields(String[] passwordInfo) {
        txtEmployeeID.setText(passwordInfo[0]);
        txtUsername.setText(passwordInfo[1]);
        txtNewPassword.setText(passwordInfo[3]);
        jCBStatus.setSelectedItem(passwordInfo[4]);

        if (itPageRef != null) {
            String firstName = itPageRef.getLblFName().getText();

            // Fetch the last name of the HR approver from the database
            String lastName = fetchLastNameFromDatabase(firstName);

            txtApprover.setText(lastName);
        } else {
            // Handle case where itPageRef is null
            txtApprover.setText("Approver Not Found");
        }

            txtDateResponded.setText(getLblDate().getText());
            txtReqDate.setText(passwordInfo[2]);

    }
    

        
    // Method to fetch the last name from the database based on the first name
    private String fetchLastNameFromDatabase(String firstName) {
        String lastName = ""; // Initialize last name variable

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT LastName FROM employee WHERE FirstName = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, firstName); // Set the first name as parameter

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        lastName = resultSet.getString("LastName"); // Retrieve the last name from the result set
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle database exception
        }

        return lastName; // Return the last name
    }

    // Getter for lblDate
    public JLabel getLblDate() {
        return lblDate;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblEmployeeID = new javax.swing.JLabel();
        lblReqDate = new javax.swing.JLabel();
        lblAddress1 = new javax.swing.JLabel();
        lblSSS = new javax.swing.JLabel();
        lblTIN = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        lblPhilhealth = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        lblDateToday = new javax.swing.JLabel();
        txtEmployeeID = new javax.swing.JTextField();
        txtUsername = new javax.swing.JTextField();
        txtReqDate = new javax.swing.JTextField();
        txtNewPassword = new javax.swing.JTextField();
        jCBStatus = new javax.swing.JComboBox<>();
        txtApprover = new javax.swing.JTextField();
        txtDateResponded = new javax.swing.JTextField();
        btnSubmit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(630, 720));
        setMinimumSize(new java.awt.Dimension(630, 720));
        setPreferredSize(new java.awt.Dimension(630, 720));
        setSize(new java.awt.Dimension(630, 720));
        getContentPane().setLayout(null);

        lblEmployeeID.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblEmployeeID.setForeground(new java.awt.Color(255, 255, 255));
        lblEmployeeID.setText("Employee ID:");
        getContentPane().add(lblEmployeeID);
        lblEmployeeID.setBounds(146, 170, 90, 30);

        lblReqDate.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblReqDate.setForeground(new java.awt.Color(255, 255, 255));
        lblReqDate.setText("Date of Request:");
        getContentPane().add(lblReqDate);
        lblReqDate.setBounds(122, 270, 130, 30);

        lblAddress1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblAddress1.setForeground(new java.awt.Color(255, 255, 255));
        lblAddress1.setText("New Password:");
        getContentPane().add(lblAddress1);
        lblAddress1.setBounds(130, 320, 130, 30);

        lblSSS.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblSSS.setForeground(new java.awt.Color(255, 255, 255));
        lblSSS.setText("Status:");
        getContentPane().add(lblSSS);
        lblSSS.setBounds(179, 370, 60, 30);

        lblTIN.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblTIN.setForeground(new java.awt.Color(255, 255, 255));
        lblTIN.setText("Date Responded:");
        getContentPane().add(lblTIN);
        lblTIN.setBounds(115, 470, 150, 30);

        lblUsername.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername.setText("Username:");
        getContentPane().add(lblUsername);
        lblUsername.setBounds(158, 220, 110, 30);

        lblPhilhealth.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPhilhealth.setForeground(new java.awt.Color(255, 255, 255));
        lblPhilhealth.setText("Approver:");
        getContentPane().add(lblPhilhealth);
        lblPhilhealth.setBounds(158, 420, 70, 30);

        lblDate.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        lblDate.setForeground(new java.awt.Color(255, 255, 255));
        lblDate.setText("02-21-2024");
        getContentPane().add(lblDate);
        lblDate.setBounds(320, 100, 140, 40);

        lblDateToday.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblDateToday.setForeground(new java.awt.Color(255, 255, 255));
        lblDateToday.setText("Current Date:");
        getContentPane().add(lblDateToday);
        lblDateToday.setBounds(170, 100, 140, 40);

        txtEmployeeID.setEditable(false);
        txtEmployeeID.setBackground(new java.awt.Color(255, 255, 255));
        txtEmployeeID.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtEmployeeID.setForeground(new java.awt.Color(56, 47, 47));
        getContentPane().add(txtEmployeeID);
        txtEmployeeID.setBounds(290, 170, 200, 28);

        txtUsername.setEditable(false);
        txtUsername.setBackground(new java.awt.Color(255, 255, 255));
        txtUsername.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(56, 47, 47));
        getContentPane().add(txtUsername);
        txtUsername.setBounds(290, 220, 200, 28);

        txtReqDate.setEditable(false);
        txtReqDate.setBackground(new java.awt.Color(255, 255, 255));
        txtReqDate.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtReqDate.setForeground(new java.awt.Color(56, 47, 47));
        getContentPane().add(txtReqDate);
        txtReqDate.setBounds(290, 270, 200, 28);

        txtNewPassword.setEditable(false);
        txtNewPassword.setBackground(new java.awt.Color(255, 255, 255));
        txtNewPassword.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtNewPassword.setForeground(new java.awt.Color(56, 47, 47));
        getContentPane().add(txtNewPassword);
        txtNewPassword.setBounds(290, 320, 200, 28);

        jCBStatus.setBackground(new java.awt.Color(255, 255, 255));
        jCBStatus.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jCBStatus.setForeground(new java.awt.Color(58, 46, 46));
        jCBStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Approved", "Rejected" }));
        getContentPane().add(jCBStatus);
        jCBStatus.setBounds(290, 370, 200, 28);

        txtApprover.setEditable(false);
        txtApprover.setBackground(new java.awt.Color(255, 255, 255));
        txtApprover.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtApprover.setForeground(new java.awt.Color(55, 42, 42));
        getContentPane().add(txtApprover);
        txtApprover.setBounds(290, 420, 200, 28);

        txtDateResponded.setEditable(false);
        txtDateResponded.setBackground(new java.awt.Color(255, 255, 255));
        txtDateResponded.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtDateResponded.setForeground(new java.awt.Color(55, 42, 42));
        getContentPane().add(txtDateResponded);
        txtDateResponded.setBounds(290, 470, 200, 28);

        btnSubmit.setBackground(new java.awt.Color(255, 255, 255));
        btnSubmit.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSubmit.setForeground(new java.awt.Color(71, 60, 60));
        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });
        getContentPane().add(btnSubmit);
        btnSubmit.setBounds(310, 550, 86, 28);

        btnCancel.setBackground(new java.awt.Color(255, 255, 255));
        btnCancel.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(71, 60, 60));
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancel);
        btnCancel.setBounds(410, 550, 80, 28);

        jLabel1.setText("changepass");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, -20, 630, 730);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        String empID = txtEmployeeID.getText();
        String username = txtUsername.getText();
        String requestDate = txtReqDate.getText();
        String newPassword = txtNewPassword.getText();
        String status = jCBStatus.getSelectedItem().toString();
        String approver = txtApprover.getText();
        String dateResponded = txtDateResponded.getText();

        // Get the selected view index
        int viewRowIndex = itPageRef.getTblResetPass().getSelectedRow();
        if (viewRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a request to respond.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convert view row index to model row index
        int modelRowIndex = itPageRef.getTblResetPass().convertRowIndexToModel(viewRowIndex);

        // Get the current status of the selected request based on model index
        String currentStatus = itPageRef.getPasswordRequestStatus(modelRowIndex);

        // Check if the request has already been approved or rejected
        if (currentStatus.equals("Approved") || currentStatus.equals("Rejected")) {
            // Inform the user and prevent further action
            JOptionPane.showMessageDialog(this, "This request has already been responded to.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method without proceeding further
        }

        // Update password request status in the password_request table
        updatePasswordRequest(empID, status, approver, dateResponded);

        if (status.equals("Approved")) {
            // Update password in the login table
            updateLoginPassword(username, newPassword);
        }

        // Display confirmation message or handle UI update accordingly
        JOptionPane.showMessageDialog(this, "Password request updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        // Refresh tblResetPass in FrmITpage
        if (itPageRef != null) {
            itPageRef.refreshTblResetPass(); // Call refresh method
        }

        // Close the current window
        this.dispose();
    }//GEN-LAST:event_btnSubmitActionPerformed

    // Method to refresh the tblResetPass table
    public void refreshTblResetPass() {
        DefaultTableModel modelResetPass = (DefaultTableModel) tblResetPass.getModel();
        modelResetPass.setRowCount(0); // Clear existing rows

        // Retrieve updated data for tblResetPass from the database
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sqlResetPass = "SELECT * FROM password_request"; // Order by EmployeeNum column
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
    
    // Method to update password request details in the password_request table
    private void updatePasswordRequest(String empID, String status, String approver, String dateResponded) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "UPDATE password_request SET Status = ?, Approver = ?, DateResponded = ? WHERE EmployeeNum = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, status);
                pstmt.setString(2, approver);
                pstmt.setString(3, dateResponded);
                pstmt.setString(4, empID);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update password request.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    // Method to update password in the login table
    private void updateLoginPassword(String username, String newPassword) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "UPDATE login SET Password = ? WHERE Username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, newPassword);
                pstmt.setString(2, username);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update password in login table.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Method to check if a response has already been recorded for empID in tblResetPass
    public boolean isAlreadyResponded(String empID) {
        DefaultTableModel model = (DefaultTableModel) tblResetPass.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String empIDInTable = model.getValueAt(i, 0).toString(); // Assuming EmployeeNum is in the first column
            if (empIDInTable.equals(empID)) {
                Object dateRespondedObj = model.getValueAt(i, 6); // Assuming DateResponded is in the seventh column
                if (dateRespondedObj != null && !dateRespondedObj.toString().isEmpty()) {
                    return true; // Found a row with matching empID and DateResponded is not empty
                }
            }
        }
        return false; // No matching empID with a non-empty DateResponded found
    }


    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

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
            java.util.logging.Logger.getLogger(FrmEditPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmEditPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmEditPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmEditPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               // new FrmEditPassword().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    public javax.swing.JButton btnSubmit;
    public javax.swing.JComboBox<String> jCBStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblAddress1;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblDateToday;
    private javax.swing.JLabel lblEmployeeID;
    private javax.swing.JLabel lblPhilhealth;
    private javax.swing.JLabel lblReqDate;
    private javax.swing.JLabel lblSSS;
    private javax.swing.JLabel lblTIN;
    private javax.swing.JLabel lblUsername;
    public javax.swing.JTextField txtApprover;
    public javax.swing.JTextField txtDateResponded;
    public javax.swing.JTextField txtEmployeeID;
    public javax.swing.JTextField txtNewPassword;
    public javax.swing.JTextField txtReqDate;
    public javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
