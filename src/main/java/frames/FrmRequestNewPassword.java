/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frames;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;


/**
 *
 * @author yen
 */
public class FrmRequestNewPassword extends javax.swing.JFrame {
    
    private FrmLogin loginPageRef;
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";
    
    private Timer timer;
    
    public String getEmpID() {
        return txtEmpID.getText().trim();
    }

    public String getUsername() {
        return txtUsername.getText().trim();
    }

    public String getPassword() {
        return new String(txtNewPassword.getPassword()).trim();
    }

    public java.util.Date getRequestDate() {
        return jDCReqDate.getDate();
    }

    /**
     * Creates new form FrmRequestNewPassword
     */
    public FrmRequestNewPassword() throws ClassNotFoundException, SQLException {
        initComponents();
        
        setResizable(false);
        setLocationRelativeTo(null); // Center the frame
        this.loginPageRef = loginPageRef;
        loginPageRef = new FrmLogin(); // Initialize loginPageRef
        setPreferredSize(new Dimension(586, 467));
        
        // Set frame properties
        pack();  // Adjust size to the preferred size
    
        displayBgFromDatabase("FrmRequestNewPassword");
        
        showDate();
        // Make the date choosers non-editable
        jDCReqDate.setEnabled(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDate(); // Update the date and time every second
            }
        });
        timer.start(); // Start the timer
        
        setDateFormatForJDateChoosers();
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
    
    // Date Format
    private void setDateFormatForJDateChoosers() {
        jDCReqDate.setDateFormatString("yyyy-MM-dd");
    }
    
    // Method to show current date
    public void showDate() {
        Date currentDate = new Date();
        // Set the formatted date to the label
        lblDate.setText(currentDate.toString());
        
        jDCReqDate.setDate(currentDate); // Set the date in the date chooser
    }
    
    // Method to populate fields
    public void populateFields(String username, String reqDate) {
        // Set the request date in the date chooser
        if (reqDate != null && !reqDate.isEmpty()) {
            Date date = parseDate(reqDate);
            if (date != null) {
                jDCReqDate.setDate(date);
            } else {
                System.err.println("Failed to parse date: " + reqDate);
            }
        } else {
            System.err.println("Empty or null reqDate provided.");
        }
    }
    
    // Parse date from string
    private Date parseDate(String dateString) {
        if (dateString.isEmpty()) {
            return null; 
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblDate = new javax.swing.JLabel();
        lblEmpID = new javax.swing.JLabel();
        CBShowPass = new javax.swing.JCheckBox();
        lblUsername = new javax.swing.JLabel();
        txtNewPassword = new javax.swing.JPasswordField();
        txtEmpID = new javax.swing.JTextField();
        txtUsername = new javax.swing.JTextField();
        lblNewPassword = new javax.swing.JLabel();
        btnSubmit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblReqDate = new javax.swing.JLabel();
        jDCReqDate = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(586, 467));
        setMinimumSize(new java.awt.Dimension(586, 467));
        setPreferredSize(new java.awt.Dimension(586, 467));
        setSize(new java.awt.Dimension(586, 467));
        getContentPane().setLayout(null);

        lblDate.setFont(new java.awt.Font("Thasadith", 1, 14)); // NOI18N
        lblDate.setForeground(new java.awt.Color(48, 40, 40));
        lblDate.setText("Date");
        getContentPane().add(lblDate);
        lblDate.setBounds(50, 0, 260, 30);

        lblEmpID.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblEmpID.setForeground(new java.awt.Color(255, 255, 255));
        lblEmpID.setText("Employee ID:");
        lblEmpID.setToolTipText("");
        getContentPane().add(lblEmpID);
        lblEmpID.setBounds(110, 120, 100, 17);

        CBShowPass.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        CBShowPass.setForeground(new java.awt.Color(255, 255, 255));
        CBShowPass.setText("Show Password");
        CBShowPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBShowPassActionPerformed(evt);
            }
        });
        getContentPane().add(CBShowPass);
        CBShowPass.setBounds(270, 256, 120, 20);

        lblUsername.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblUsername.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername.setText("Username:");
        lblUsername.setToolTipText("");
        getContentPane().add(lblUsername);
        lblUsername.setBounds(110, 170, 100, 17);

        txtNewPassword.setBackground(new java.awt.Color(255, 255, 255));
        txtNewPassword.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtNewPassword.setForeground(new java.awt.Color(61, 53, 53));
        txtNewPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNewPasswordActionPerformed(evt);
            }
        });
        getContentPane().add(txtNewPassword);
        txtNewPassword.setBounds(270, 220, 200, 30);

        txtEmpID.setBackground(new java.awt.Color(255, 255, 255));
        txtEmpID.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtEmpID.setForeground(new java.awt.Color(64, 49, 49));
        getContentPane().add(txtEmpID);
        txtEmpID.setBounds(270, 120, 200, 30);

        txtUsername.setBackground(new java.awt.Color(255, 255, 255));
        txtUsername.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(64, 49, 49));
        getContentPane().add(txtUsername);
        txtUsername.setBounds(270, 170, 200, 30);

        lblNewPassword.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblNewPassword.setForeground(new java.awt.Color(255, 255, 255));
        lblNewPassword.setText("New Password:");
        getContentPane().add(lblNewPassword);
        lblNewPassword.setBounds(110, 220, 160, 17);

        btnSubmit.setBackground(new java.awt.Color(255, 255, 255));
        btnSubmit.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSubmit.setForeground(new java.awt.Color(76, 63, 76));
        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });
        getContentPane().add(btnSubmit);
        btnSubmit.setBounds(300, 340, 86, 28);

        btnCancel.setBackground(new java.awt.Color(255, 255, 255));
        btnCancel.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(76, 63, 76));
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancel);
        btnCancel.setBounds(390, 340, 80, 28);

        lblReqDate.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblReqDate.setForeground(new java.awt.Color(255, 255, 255));
        lblReqDate.setText("Date of Request:");
        getContentPane().add(lblReqDate);
        lblReqDate.setBounds(110, 290, 160, 17);

        jDCReqDate.setBackground(new java.awt.Color(255, 255, 255));
        jDCReqDate.setForeground(new java.awt.Color(50, 41, 41));
        jDCReqDate.setDateFormatString("MM/dd/yyyy");
        jDCReqDate.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        getContentPane().add(jDCReqDate);
        jDCReqDate.setBounds(270, 290, 200, 27);

        jLabel1.setText("forgotpass");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, -30, 590, 490);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        String empID = getEmpID();
        String username = getUsername();
        String password = getPassword();
        Date reqDate = getRequestDate();

        if (empID.isEmpty() || username.isEmpty() || password.isEmpty() || reqDate == null) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate if empID is numeric
        if (!isNumeric(empID)) {
            JOptionPane.showMessageDialog(this, "Invalid Employee ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Validate the Employee ID and Username
            String validateSql = "SELECT * FROM login WHERE EmployeeNum = ? AND Username = ?";
            try (PreparedStatement validateStmt = conn.prepareStatement(validateSql)) {
                validateStmt.setString(1, empID);
                validateStmt.setString(2, username);
                ResultSet rs = validateStmt.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(this, "Employee ID and Username do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Insert the password request
            String sql = "INSERT INTO password_request (EmployeeNum, Username, RequestDate, NewPassword, Status, Approver, DateResponded) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, Integer.parseInt(empID));
                pstmt.setString(2, username);
                pstmt.setDate(3, new java.sql.Date(reqDate.getTime()));
                pstmt.setString(4, password);
                pstmt.setString(5, "Pending");
                pstmt.setString(6, ""); 
                pstmt.setString(7, ""); 
                int rowsInserted = pstmt.executeUpdate();

                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Password request submitted successfully.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to submit password request.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnSubmitActionPerformed
*/
    public void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {                                          
        String empID = getEmpID();
        String username = getUsername();
        String password = getPassword();
        Date reqDate = getRequestDate();

        if (empID.isEmpty() || username.isEmpty() || password.isEmpty() || reqDate == null) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate if empID is numeric
        if (!isNumeric(empID)) {
            JOptionPane.showMessageDialog(this, "Invalid Employee ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Validate the Employee ID and Username
            String validateSql = "SELECT * FROM login WHERE EmployeeNum = ? AND Username = ?";
            try (PreparedStatement validateStmt = conn.prepareStatement(validateSql)) {
                validateStmt.setString(1, empID);
                validateStmt.setString(2, username);
                ResultSet rs = validateStmt.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(this, "Employee ID and Username do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Insert the password request
            String sql = "INSERT INTO password_request (EmployeeNum, Username, RequestDate, NewPassword, Status, Approver, DateResponded) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, Integer.parseInt(empID));
                pstmt.setString(2, username);
                pstmt.setDate(3, new java.sql.Date(reqDate.getTime()));
                pstmt.setString(4, password);
                pstmt.setString(5, "Pending");
                pstmt.setString(6, ""); 
                pstmt.setString(7, ""); 
                int rowsInserted = pstmt.executeUpdate();

                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Password request submitted successfully.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to submit password request.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }          
    
    // Helper method to check if a string is numeric
    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void txtNewPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNewPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNewPasswordActionPerformed

    private void CBShowPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBShowPassActionPerformed
        // TODO add your handling code here:
        if (CBShowPass.isSelected()) {
            // Show the password
            txtNewPassword.setEchoChar((char) 0);
        } else {
            // Hide the password
            txtNewPassword.setEchoChar('●');
        }
    }//GEN-LAST:event_CBShowPassActionPerformed

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
            java.util.logging.Logger.getLogger(FrmRequestNewPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmRequestNewPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmRequestNewPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmRequestNewPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new FrmRequestNewPassword().setVisible(true);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(FrmRequestNewPassword.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CBShowPass;
    private javax.swing.JButton btnCancel;
    public javax.swing.JButton btnSubmit;
    public com.toedter.calendar.JDateChooser jDCReqDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblEmpID;
    private javax.swing.JLabel lblNewPassword;
    private javax.swing.JLabel lblReqDate;
    private javax.swing.JLabel lblUsername;
    public javax.swing.JTextField txtEmpID;
    public javax.swing.JPasswordField txtNewPassword;
    public javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
