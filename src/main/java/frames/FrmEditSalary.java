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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author EMAN
 */
public class FrmEditSalary extends javax.swing.JFrame {
    
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
    public FrmEditSalary(FrmPayrollpage payrollPageRef) {
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
        jDCSalaryUpd.setEnabled(false);
        
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
        jDCSalaryUpd.setDate(currentDate);
    }
    
    // Date Format
    private void setDateFormatForJDateChoosers() {
        jDCSalaryUpd.setDateFormatString("yyyy-MM-dd");
    }


    //method for populating textfields
    public void populateFields(String[] empInfo) {
        if (empInfo != null && empInfo.length >= 7) {
        txtEmployeeID.setText(empInfo[0]);
        txtLastName.setText(empInfo[1]);
        txtPosition.setText(empInfo[2]);
        txtBasicSalary.setText(empInfo[3]);
        txtGrossSemi.setText(empInfo[4]);
        txtHourlyRate.setText(empInfo[5]);

        // Set the date if available
            String dateUpdated = empInfo[6];
            Date date = parseDate(dateUpdated);
            jDCSalaryUpd.setDate(date); // Even if date is null, set it to jDCAllowanceUpd
        } else {
            clearTextFields();
        }
    }


    // Method to clear text fields including date choosers
    private void clearTextFields() {
        txtEmployeeID.setText("");
        txtLastName.setText("");
        txtPosition.setText("");
        clearSalaryFields();
    }
    
    public void clearSalaryFields() {
        txtBasicSalary.setText("");
        txtGrossSemi.setText("");
        txtHourlyRate.setText("");
        jDCSalaryUpd.setDate(null);
    }
    
    public boolean validateInput() {
        try {
            // Validate numeric fields
            Integer.parseInt(txtEmployeeID.getText());

            if (!txtBasicSalary.getText().isEmpty()) {
                Double.parseDouble(txtBasicSalary.getText());
            }
            if (!txtGrossSemi.getText().isEmpty()) {
                Double.parseDouble(txtGrossSemi.getText());
            }
            if (!txtHourlyRate.getText().isEmpty()) {
                Double.parseDouble(txtHourlyRate.getText());
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
        lblBSalary = new javax.swing.JLabel();
        txtBasicSalary = new javax.swing.JTextField();
        lblGrossSemi = new javax.swing.JLabel();
        txtGrossSemi = new javax.swing.JTextField();
        lblHourlyRate = new javax.swing.JLabel();
        txtHourlyRate = new javax.swing.JTextField();
        btnCancel = new javax.swing.JButton();
        btnSaveSalary = new javax.swing.JButton();
        lblSalaryDetails = new javax.swing.JLabel();
        lblEmpDetails = new javax.swing.JLabel();
        lblSalaryUpd = new javax.swing.JLabel();
        jDCSalaryUpd = new com.toedter.calendar.JDateChooser();
        lblNote = new javax.swing.JLabel();
        lblSalDate = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1000, 670));
        setMinimumSize(new java.awt.Dimension(1000, 670));
        setPreferredSize(new java.awt.Dimension(1000, 670));
        setSize(new java.awt.Dimension(1000, 670));
        getContentPane().setLayout(null);

        lblEmployeeID.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblEmployeeID.setForeground(new java.awt.Color(255, 255, 255));
        lblEmployeeID.setText("Employee ID:");
        getContentPane().add(lblEmployeeID);
        lblEmployeeID.setBounds(258, 150, 90, 30);

        lblLastName.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblLastName.setForeground(new java.awt.Color(255, 255, 255));
        lblLastName.setText("Last Name:");
        getContentPane().add(lblLastName);
        lblLastName.setBounds(268, 190, 80, 30);

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
        lblPosition.setBounds(284, 230, 70, 30);

        txtPosition.setEditable(false);
        txtPosition.setBackground(new java.awt.Color(255, 255, 255));
        txtPosition.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtPosition.setForeground(new java.awt.Color(56, 45, 45));
        getContentPane().add(txtPosition);
        txtPosition.setBounds(410, 230, 280, 28);

        lblBSalary.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblBSalary.setForeground(new java.awt.Color(255, 255, 255));
        lblBSalary.setText("Basic Salary:");
        getContentPane().add(lblBSalary);
        lblBSalary.setBounds(262, 370, 90, 30);

        txtBasicSalary.setBackground(new java.awt.Color(255, 255, 255));
        txtBasicSalary.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtBasicSalary.setForeground(new java.awt.Color(56, 45, 45));
        getContentPane().add(txtBasicSalary);
        txtBasicSalary.setBounds(410, 370, 280, 28);

        lblGrossSemi.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblGrossSemi.setForeground(new java.awt.Color(255, 255, 255));
        lblGrossSemi.setText("Gross Semi-Monthly Rate:");
        getContentPane().add(lblGrossSemi);
        lblGrossSemi.setBounds(185, 410, 170, 30);

        txtGrossSemi.setBackground(new java.awt.Color(255, 255, 255));
        txtGrossSemi.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtGrossSemi.setForeground(new java.awt.Color(56, 45, 45));
        getContentPane().add(txtGrossSemi);
        txtGrossSemi.setBounds(410, 410, 280, 28);

        lblHourlyRate.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblHourlyRate.setForeground(new java.awt.Color(255, 255, 255));
        lblHourlyRate.setText("Hourly Rate:");
        getContentPane().add(lblHourlyRate);
        lblHourlyRate.setBounds(262, 450, 90, 30);

        txtHourlyRate.setBackground(new java.awt.Color(255, 255, 255));
        txtHourlyRate.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtHourlyRate.setForeground(new java.awt.Color(56, 45, 45));
        getContentPane().add(txtHourlyRate);
        txtHourlyRate.setBounds(410, 450, 280, 28);

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

        btnSaveSalary.setBackground(new java.awt.Color(255, 255, 255));
        btnSaveSalary.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnSaveSalary.setForeground(new java.awt.Color(75, 66, 66));
        btnSaveSalary.setText("Save Salary Information");
        btnSaveSalary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveSalaryActionPerformed(evt);
            }
        });
        getContentPane().add(btnSaveSalary);
        btnSaveSalary.setBounds(410, 550, 200, 26);

        lblSalaryDetails.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblSalaryDetails.setForeground(new java.awt.Color(255, 255, 255));
        lblSalaryDetails.setText("Salary Details");
        getContentPane().add(lblSalaryDetails);
        lblSalaryDetails.setBounds(460, 320, 120, 30);

        lblEmpDetails.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblEmpDetails.setForeground(new java.awt.Color(255, 255, 255));
        lblEmpDetails.setText("Employee Details");
        getContentPane().add(lblEmpDetails);
        lblEmpDetails.setBounds(440, 102, 160, 30);

        lblSalaryUpd.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblSalaryUpd.setForeground(new java.awt.Color(255, 255, 255));
        lblSalaryUpd.setText("Date Updated:");
        getContentPane().add(lblSalaryUpd);
        lblSalaryUpd.setBounds(249, 490, 120, 30);

        jDCSalaryUpd.setBackground(new java.awt.Color(255, 255, 255));
        jDCSalaryUpd.setForeground(new java.awt.Color(50, 41, 41));
        jDCSalaryUpd.setDateFormatString("MM/dd/yyyy");
        jDCSalaryUpd.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        getContentPane().add(jDCSalaryUpd);
        jDCSalaryUpd.setBounds(410, 490, 280, 27);

        lblNote.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        lblNote.setForeground(new java.awt.Color(64, 52, 52));
        lblNote.setText("Note: Refresh the table after editing salary information.");
        getContentPane().add(lblNote);
        lblNote.setBounds(670, 610, 330, 17);

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

    private void btnSaveSalaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveSalaryActionPerformed
        // Validate input before saving
        if (!validateInput()) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please check the fields and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the selected row index
        int selectedRowIndex = payrollPageRef.getTblSalaryInfo().getSelectedRow();
        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }    
        
        // Fetch current values from the text fields
        String employeeID = txtEmployeeID.getText();
        String lastName = txtLastName.getText();
        String position = txtPosition.getText();
        String basicSalaryStr = txtBasicSalary.getText();
        String grossSemiStr = txtGrossSemi.getText();
        String hourlyRateStr = txtHourlyRate.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateUpdated = dateFormat.format(jDCSalaryUpd.getDate());

        // Validate if basicSalary, grossSemi, and hourlyRate are valid double values
        if (!isValidDouble(basicSalaryStr) || !isValidDouble(grossSemiStr) || !isValidDouble(hourlyRateStr)) {
            JOptionPane.showMessageDialog(this, "Invalid values. Please check the fields and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Parse the string values to double for comparison and format to two decimal places
        DecimalFormat df = new DecimalFormat("#.##");

        double basicSalary = Double.parseDouble(df.format(Double.parseDouble(basicSalaryStr)));
        double grossSemi = Double.parseDouble(df.format(Double.parseDouble(grossSemiStr)));
        double hourlyRate = Double.parseDouble(df.format(Double.parseDouble(hourlyRateStr)));

        // Prepare empInfo array with updated employee information
        String[] empInfo = {
            employeeID,
            lastName,
            position,
            basicSalaryStr,
            grossSemiStr,
            hourlyRateStr,
            dateUpdated
        };

        // Update the employee record in the database
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Retrieve the existing salary information from the database
            String selectQuery = "SELECT BasicSalary, GrossSMRate, HourlyRate, DateUpdated FROM salary WHERE EmployeeNum = ?";
            try (PreparedStatement selectStatement = conn.prepareStatement(selectQuery)) {
                selectStatement.setString(1, employeeID);
                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        double existingBasicSalary = resultSet.getDouble("BasicSalary");
                        double existingGrossSemi = resultSet.getDouble("GrossSMRate");
                        double existingHourlyRate = resultSet.getDouble("HourlyRate");
                        String existingDateUpdated = resultSet.getString("DateUpdated");

                        // Check if any changes have been made
                        if (Double.compare(basicSalary, existingBasicSalary) == 0 &&
                            Double.compare(grossSemi, existingGrossSemi) == 0 &&
                            Double.compare(hourlyRate, existingHourlyRate) == 0 &&
                            dateUpdated.equals(existingDateUpdated)) {

                            // No changes detected
                            JOptionPane.showMessageDialog(this, "No changes detected.", "No Changes", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                    }
                }
            }

            String updateSalarySQL = "UPDATE salary SET BasicSalary = ?, GrossSMRate = ?, HourlyRate = ?, DateUpdated = ? WHERE EmployeeNum = ?";
            try (PreparedStatement updateSalaryStatement = conn.prepareStatement(updateSalarySQL)) {
                updateSalaryStatement.setString(1, empInfo[3]); // Basic Salary
                updateSalaryStatement.setString(2, empInfo[4]); // Gross Semi
                updateSalaryStatement.setString(3, empInfo[5]); // Hourly Rate
                updateSalaryStatement.setString(4, empInfo[6]); // DateUpdated
                updateSalaryStatement.setString(5, empInfo[0]); // EmployeeNum

                int rowsAffected = updateSalaryStatement.executeUpdate();
                if (rowsAffected <= 0) {
                    JOptionPane.showMessageDialog(this, "Failed to update salary information.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Employee salary information updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearTextFields();
                    payrollPageRef.updateSalaryTableRow(selectedRowIndex, empInfo);

                    // Close the current frame
                    dispose();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSaveSalaryActionPerformed

    // Helper method to format date
    private String getFormattedDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    
    
    // Method to validate if a string is a valid double
    private boolean isValidDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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
            java.util.logging.Logger.getLogger(FrmEditSalary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmEditSalary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmEditSalary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmEditSalary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
    public javax.swing.JButton btnSaveSalary;
    public com.toedter.calendar.JDateChooser jDCSalaryUpd;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JLabel lblBSalary;
    private javax.swing.JLabel lblEmpDetails;
    private javax.swing.JLabel lblEmployeeID;
    private javax.swing.JLabel lblGrossSemi;
    private javax.swing.JLabel lblHourlyRate;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblNote;
    private javax.swing.JLabel lblPosition;
    private javax.swing.JLabel lblSalDate;
    private javax.swing.JLabel lblSalaryDetails;
    private javax.swing.JLabel lblSalaryUpd;
    public javax.swing.JTextField txtBasicSalary;
    public javax.swing.JTextField txtEmployeeID;
    public javax.swing.JTextField txtGrossSemi;
    public javax.swing.JTextField txtHourlyRate;
    public javax.swing.JTextField txtLastName;
    public javax.swing.JTextField txtPosition;
    // End of variables declaration//GEN-END:variables
}
