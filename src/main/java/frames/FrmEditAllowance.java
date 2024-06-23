/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frames;

import classes.CSVFileManager;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.util.Arrays;
import javax.swing.Timer;


/**
 *
 * @author EMAN
 */
public class FrmEditAllowance extends javax.swing.JFrame {
    
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";
    
    private Timer timer;

    private FrmPayrollpage payrollPageRef;
    /**
     * Creates new form FrmEditSalary
     */
    public FrmEditAllowance(FrmPayrollpage payrollPageRef) {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null); // Center the frame
        setPreferredSize(new Dimension(1000, 670));
        
        // Set frame properties
        pack();  // Adjust size to the preferred size
    
        displayBgFromDatabase("FrmEditSalary");
        
        this.payrollPageRef = payrollPageRef;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        showDate();
        // Make the date choosers non-editable
        jDCAllowanceUpd.setEnabled(false);
        
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
                    jLabel2.setIcon(icon);
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
        // Get the current date
        Date currentDate = new Date();

        // Set the formatted date to the label
        lblSalDate.setText(currentDate.toString());

        // Set the date to the date choosers
        jDCAllowanceUpd.setDate(currentDate);
    }

    // Method to set date format for JDateChoosers
    private void setDateFormatForJDateChoosers() {
        jDCAllowanceUpd.setDateFormatString("yyyy-MM-dd");
    }

    
    public void populateFields(String[] empInfo) {
        if (empInfo != null && empInfo.length >= 7) {
            txtEmployeeID.setText(empInfo[0]);
            txtLastName.setText(empInfo[1]);
            txtPosition.setText(empInfo[2]);
            txtRiceSubsidy.setText(empInfo[3]);
            txtPhoneAllowance.setText(empInfo[4]);
            txtClothingAllowance.setText(empInfo[5]);

            // Set the date if available
            String dateUpdated = empInfo[6];
            Date date = parseDate(dateUpdated);
            jDCAllowanceUpd.setDate(date); // Even if date is null, set it to jDCAllowanceUpd
        } else {
            clearTextFields();
        }
    }

    public void clearTextFields() {
        txtEmployeeID.setText("");
        txtLastName.setText("");
        txtPosition.setText("");
        clearAllowanceFields();
    }

    public void clearAllowanceFields() {
        txtRiceSubsidy.setText("");
        txtPhoneAllowance.setText("");
        txtClothingAllowance.setText("");
        jDCAllowanceUpd.setDate(null);
    }

    public boolean validateInput() {
        try {
            // Validate numeric fields
            Integer.parseInt(txtEmployeeID.getText());

            if (!txtRiceSubsidy.getText().isEmpty()) {
                Double.parseDouble(txtRiceSubsidy.getText());
            }
            if (!txtPhoneAllowance.getText().isEmpty()) {
                Double.parseDouble(txtPhoneAllowance.getText());
            }
            if (!txtClothingAllowance.getText().isEmpty()) {
                Double.parseDouble(txtClothingAllowance.getText());
            }

            return true; // Return true if all fields pass validation
        } catch (NumberFormatException | NullPointerException ex) {
            return false; // Return false if any field fails validation
        }
    }

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

        jMenu1 = new javax.swing.JMenu();
        lblEmployeeID = new javax.swing.JLabel();
        lblLastName = new javax.swing.JLabel();
        txtEmployeeID = new javax.swing.JTextField();
        txtLastName = new javax.swing.JTextField();
        lblPosition = new javax.swing.JLabel();
        txtPosition = new javax.swing.JTextField();
        lblRiceSub = new javax.swing.JLabel();
        txtRiceSubsidy = new javax.swing.JTextField();
        lblPhoneAllowance = new javax.swing.JLabel();
        txtPhoneAllowance = new javax.swing.JTextField();
        lblClothingAllowance = new javax.swing.JLabel();
        txtClothingAllowance = new javax.swing.JTextField();
        btnSaveAllowance = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblEmpDetails = new javax.swing.JLabel();
        lblAllowanceDetails = new javax.swing.JLabel();
        lblAllowanceUpd = new javax.swing.JLabel();
        lblNote = new javax.swing.JLabel();
        jDCAllowanceUpd = new com.toedter.calendar.JDateChooser();
        lblSalDate = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1000, 670));
        setSize(new java.awt.Dimension(1000, 670));
        getContentPane().setLayout(null);

        lblEmployeeID.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblEmployeeID.setForeground(new java.awt.Color(255, 255, 255));
        lblEmployeeID.setText("Employee ID:");
        getContentPane().add(lblEmployeeID);
        lblEmployeeID.setBounds(284, 150, 80, 30);

        lblLastName.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblLastName.setForeground(new java.awt.Color(255, 255, 255));
        lblLastName.setText("Last Name:");
        getContentPane().add(lblLastName);
        lblLastName.setBounds(294, 190, 65, 30);

        txtEmployeeID.setEditable(false);
        txtEmployeeID.setBackground(new java.awt.Color(255, 255, 255));
        txtEmployeeID.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtEmployeeID.setForeground(new java.awt.Color(56, 45, 45));
        getContentPane().add(txtEmployeeID);
        txtEmployeeID.setBounds(410, 150, 280, 28);

        txtLastName.setEditable(false);
        txtLastName.setBackground(new java.awt.Color(255, 255, 255));
        txtLastName.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtLastName.setForeground(new java.awt.Color(56, 45, 45));
        getContentPane().add(txtLastName);
        txtLastName.setBounds(410, 190, 280, 28);

        lblPosition.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPosition.setForeground(new java.awt.Color(255, 255, 255));
        lblPosition.setText("Position:");
        getContentPane().add(lblPosition);
        lblPosition.setBounds(311, 230, 48, 30);

        txtPosition.setEditable(false);
        txtPosition.setBackground(new java.awt.Color(255, 255, 255));
        txtPosition.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtPosition.setForeground(new java.awt.Color(56, 45, 45));
        getContentPane().add(txtPosition);
        txtPosition.setBounds(410, 230, 280, 28);

        lblRiceSub.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblRiceSub.setForeground(new java.awt.Color(255, 255, 255));
        lblRiceSub.setText("Rice Subsidy:");
        getContentPane().add(lblRiceSub);
        lblRiceSub.setBounds(283, 360, 76, 40);

        txtRiceSubsidy.setBackground(new java.awt.Color(255, 255, 255));
        txtRiceSubsidy.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtRiceSubsidy.setForeground(new java.awt.Color(56, 45, 45));
        getContentPane().add(txtRiceSubsidy);
        txtRiceSubsidy.setBounds(410, 370, 280, 28);

        lblPhoneAllowance.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPhoneAllowance.setForeground(new java.awt.Color(255, 255, 255));
        lblPhoneAllowance.setText("Phone Allowance:");
        getContentPane().add(lblPhoneAllowance);
        lblPhoneAllowance.setBounds(259, 400, 100, 40);

        txtPhoneAllowance.setBackground(new java.awt.Color(255, 255, 255));
        txtPhoneAllowance.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtPhoneAllowance.setForeground(new java.awt.Color(56, 45, 45));
        getContentPane().add(txtPhoneAllowance);
        txtPhoneAllowance.setBounds(410, 410, 280, 28);

        lblClothingAllowance.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblClothingAllowance.setForeground(new java.awt.Color(255, 255, 255));
        lblClothingAllowance.setText("Clothing Allowance:");
        getContentPane().add(lblClothingAllowance);
        lblClothingAllowance.setBounds(247, 440, 111, 40);

        txtClothingAllowance.setBackground(new java.awt.Color(255, 255, 255));
        txtClothingAllowance.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtClothingAllowance.setForeground(new java.awt.Color(56, 45, 45));
        getContentPane().add(txtClothingAllowance);
        txtClothingAllowance.setBounds(410, 450, 280, 28);

        btnSaveAllowance.setBackground(new java.awt.Color(255, 255, 255));
        btnSaveAllowance.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnSaveAllowance.setForeground(new java.awt.Color(75, 66, 66));
        btnSaveAllowance.setText("Save Allowance Information");
        btnSaveAllowance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveAllowanceActionPerformed(evt);
            }
        });
        getContentPane().add(btnSaveAllowance);
        btnSaveAllowance.setBounds(390, 540, 220, 30);

        btnCancel.setBackground(new java.awt.Color(255, 255, 255));
        btnCancel.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(75, 66, 66));
        btnCancel.setText("Close");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancel);
        btnCancel.setBounds(840, 560, 80, 28);

        lblEmpDetails.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblEmpDetails.setForeground(new java.awt.Color(255, 255, 255));
        lblEmpDetails.setText("Employee Details");
        getContentPane().add(lblEmpDetails);
        lblEmpDetails.setBounds(440, 102, 160, 30);

        lblAllowanceDetails.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblAllowanceDetails.setForeground(new java.awt.Color(255, 255, 255));
        lblAllowanceDetails.setText("Allowance Details");
        getContentPane().add(lblAllowanceDetails);
        lblAllowanceDetails.setBounds(440, 320, 150, 30);

        lblAllowanceUpd.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblAllowanceUpd.setForeground(new java.awt.Color(255, 255, 255));
        lblAllowanceUpd.setText("Date Updated:");
        getContentPane().add(lblAllowanceUpd);
        lblAllowanceUpd.setBounds(274, 480, 110, 40);

        lblNote.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        lblNote.setForeground(new java.awt.Color(64, 52, 52));
        lblNote.setText("Note: Refresh the table after editing allowance information.");
        getContentPane().add(lblNote);
        lblNote.setBounds(640, 610, 330, 17);

        jDCAllowanceUpd.setBackground(new java.awt.Color(255, 255, 255));
        jDCAllowanceUpd.setForeground(new java.awt.Color(50, 41, 41));
        jDCAllowanceUpd.setDateFormatString("MM/dd/yyyy");
        jDCAllowanceUpd.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        getContentPane().add(jDCAllowanceUpd);
        jDCAllowanceUpd.setBounds(410, 490, 280, 27);

        lblSalDate.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblSalDate.setForeground(new java.awt.Color(255, 255, 255));
        lblSalDate.setText("Date");
        getContentPane().add(lblSalDate);
        lblSalDate.setBounds(720, 80, 220, 40);

        jLabel2.setText("editsal");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, -30, 1000, 700);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveAllowanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveAllowanceActionPerformed
        // Validate input before saving
        if (!validateInput()) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please check the fields and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the selected row index
        int selectedRowIndex = payrollPageRef.getTblAllowanceInfo().getSelectedRow();
        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Fetch current values from the text fields
        String employeeID = txtEmployeeID.getText();
        String lastName = txtLastName.getText();
        String position = txtPosition.getText();
        String riceSubsidyStr = txtRiceSubsidy.getText();
        String phoneAllowanceStr = txtPhoneAllowance.getText();
        String clothingAllowanceStr = txtClothingAllowance.getText();
        String dateUpdated = getFormattedDate(jDCAllowanceUpd.getDate());

        // Validate if riceSubsidy, phoneAllowance, and clothingAllowance are valid double values
        if (!isValidDouble(riceSubsidyStr) || !isValidDouble(phoneAllowanceStr) || !isValidDouble(clothingAllowanceStr)) {
            JOptionPane.showMessageDialog(this, "Invalid values. Please check the fields and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Format input values to two decimal places
        DecimalFormat df = new DecimalFormat("#.##");
        double riceSubsidy = Double.parseDouble(df.format(Double.parseDouble(riceSubsidyStr)));
        double phoneAllowance = Double.parseDouble(df.format(Double.parseDouble(phoneAllowanceStr)));
        double clothingAllowance = Double.parseDouble(df.format(Double.parseDouble(clothingAllowanceStr)));

        // Prepare empInfo array with updated employee information
        String[] empInfo = {
            employeeID,
            lastName,
            position,
            riceSubsidyStr,
            phoneAllowanceStr,
            clothingAllowanceStr,
            dateUpdated
        };

        // Update the employee record in the database
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Retrieve the existing salary information from the database
            String selectQuery = "SELECT RiceSubsidy, PhoneAllowance, ClothingAllowance, DateUpdated FROM allowance WHERE EmployeeNum = ?";
            try (PreparedStatement selectStatement = conn.prepareStatement(selectQuery)) {
                selectStatement.setString(1, employeeID);
                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        double existingRiceSubsidy = resultSet.getDouble("RiceSubsidy");
                        double existingPhoneAllowance = resultSet.getDouble("PhoneAllowance");
                        double existingClothingAllowance = resultSet.getDouble("ClothingAllowance");
                        String existingDateUpdated = resultSet.getString("DateUpdated");

                        // Check if any changes have been made
                        if (Double.compare(riceSubsidy, existingRiceSubsidy) == 0 &&
                            Double.compare(phoneAllowance, existingPhoneAllowance) == 0 &&
                            Double.compare(clothingAllowance, existingClothingAllowance) == 0 &&
                            dateUpdated.equals(existingDateUpdated)) {

                            // No changes detected
                            JOptionPane.showMessageDialog(this, "No changes detected.", "No Changes", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                    }
                }
            }
   
            String updateAllowanceSQL = "UPDATE allowance SET RiceSubsidy = ?, PhoneAllowance = ?, ClothingAllowance = ?, DateUpdated = ? WHERE EmployeeNum = ?";
            try (PreparedStatement updateAllowanceStatement = conn.prepareStatement(updateAllowanceSQL)) {
                updateAllowanceStatement.setString(1, empInfo[3]); // Rice Subsidy
                updateAllowanceStatement.setString(2, empInfo[4]); // Phone Allowance
                updateAllowanceStatement.setString(3, empInfo[5]); // Clothing Allowance
                updateAllowanceStatement.setString(4, empInfo[6]); // DateUpdated
                updateAllowanceStatement.setString(5, empInfo[0]); // EmployeeNum

                int rowsAffected = updateAllowanceStatement.executeUpdate();
                if (rowsAffected <= 0) {
                    JOptionPane.showMessageDialog(this, "Failed to update allowance information.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Employee allowance information updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearTextFields();
                    payrollPageRef.updateAllowanceTableRow(selectedRowIndex, empInfo);

                    // Close the current frame
                    dispose();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSaveAllowanceActionPerformed

    private boolean isValidDouble(String value) {
         try {
             Double.parseDouble(value);
             return true;
         } catch (NumberFormatException e) {
             return false;
         }
     }

    
    // Helper method to format date
    private String getFormattedDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
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
            java.util.logging.Logger.getLogger(FrmEditAllowance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmEditAllowance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmEditAllowance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmEditAllowance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new FrmEditSalary().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    public javax.swing.JButton btnSaveAllowance;
    public com.toedter.calendar.JDateChooser jDCAllowanceUpd;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JLabel lblAllowanceDetails;
    private javax.swing.JLabel lblAllowanceUpd;
    private javax.swing.JLabel lblClothingAllowance;
    private javax.swing.JLabel lblEmpDetails;
    private javax.swing.JLabel lblEmployeeID;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblNote;
    private javax.swing.JLabel lblPhoneAllowance;
    private javax.swing.JLabel lblPosition;
    private javax.swing.JLabel lblRiceSub;
    private javax.swing.JLabel lblSalDate;
    public javax.swing.JTextField txtClothingAllowance;
    public javax.swing.JTextField txtEmployeeID;
    public javax.swing.JTextField txtLastName;
    public javax.swing.JTextField txtPhoneAllowance;
    public javax.swing.JTextField txtPosition;
    public javax.swing.JTextField txtRiceSubsidy;
    // End of variables declaration//GEN-END:variables
}
