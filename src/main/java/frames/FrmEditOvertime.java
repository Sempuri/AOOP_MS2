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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author EMAN
 */
public class FrmEditOvertime extends javax.swing.JFrame {
    
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";

    private FrmHRpage hrPageRef;
    /**
     * Creates new form FrmEditOvertime
     */
    public FrmEditOvertime(FrmHRpage hrPageRef) {
        this.hrPageRef = hrPageRef;
        initUI();
    }

    // Existing no-argument constructor
    public FrmEditOvertime() throws ClassNotFoundException, SQLException {
        this.hrPageRef = new FrmHRpage();
        initUI();
    }

    private void initUI() {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null); // Center the frame
        setPreferredSize(new Dimension(630, 720));
        pack(); // Adjust size to the preferred size
        displayBgFromDatabase("FrmEditOvertime");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        showDate();
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

    //event handler method for close button
    private void FrameClose(java.awt.event.MouseEvent evt) {                            
        this.dispose();
    }    

    //for showing of date
    public void showDate() {
        Date d = new Date();
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd"); // Output date pattern
        String formattedDate = outputFormat.format(d); // Format the date
        lblDate.setText(formattedDate); // Set the formatted date to the label
    }


    
    //method for populating textfields and jDCchooser
    public void populateFields(String[] overtimeInfo) {
        txtEmployeeID.setText(overtimeInfo[0]);
        txtTimeIn.setText(overtimeInfo[3]);
        txtTimeOut.setText(overtimeInfo[4]);
        txtTotalWorkedHours.setText(overtimeInfo[5]);
         // Get the first name of the HR approver from the label
        String firstName = hrPageRef.getLblFName().getText();

        // Fetch the last name of the HR approver from the database
        String lastName = fetchLastNameFromDatabase(firstName);

        // Display the last name in the txtApprover field
        txtApprover.setText(lastName);

        // Set the "Date Responded" field
        txtDateResponded.setText(getLblDate().getText());

        // Parse and set the date fields if the date strings are not empty
        SimpleDateFormat dateFormatInput = new SimpleDateFormat("yyyy-MM-dd"); // Adjusted input date pattern
        SimpleDateFormat dateFormatOutput = new SimpleDateFormat("yyyy-MM-dd"); // Output date pattern (no change needed)

        try {
            if (!overtimeInfo[1].isEmpty()) {
                Date date = dateFormatInput.parse(overtimeInfo[1]); // Parse the date string
                txtReqDate.setText(dateFormatOutput.format(date)); // Set the formatted date in the text field
            }
            if (!overtimeInfo[2].isEmpty()) {
                Date date1 = dateFormatInput.parse(overtimeInfo[2]);
                txtOtDate.setText(dateFormatOutput.format(date1)); // Set the formatted date in the text field
            }
            if (!overtimeInfo[8].isEmpty()) { // Check if Date Responded is not empty
                Date date2 = dateFormatInput.parse(overtimeInfo[8]); // Parse the Date Responded
                txtDateResponded.setText(dateFormatOutput.format(date2)); // Set the formatted Date Responded in the text field
            }
        } catch (ParseException ex) {
            ex.printStackTrace(); // Handle parsing exception
        }
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
    
    //method for clearing textfields
    private void clearTextFields() {
        txtEmployeeID.setText("");
        txtReqDate.setText("");
        txtOtDate.setText("");
        txtTimeIn.setText("");
        txtTimeOut.setText("");
        txtTotalWorkedHours.setText("");
        jCBStatus.setSelectedItem(null); // Ensure this is how your JComboBox should be cleared
        txtApprover.setText("");
        txtDateResponded.setText("");
    }


    
    //getter
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

        lblDateToday = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        lblEmployeeID = new javax.swing.JLabel();
        lblEmployeeID1 = new javax.swing.JLabel();
        lblLastName = new javax.swing.JLabel();
        lblFirstName = new javax.swing.JLabel();
        lblBirthday = new javax.swing.JLabel();
        lblAddress1 = new javax.swing.JLabel();
        lblSSS = new javax.swing.JLabel();
        lblPhilhealth = new javax.swing.JLabel();
        lblTIN = new javax.swing.JLabel();
        txtEmployeeID = new javax.swing.JTextField();
        txtReqDate = new javax.swing.JTextField();
        txtOtDate = new javax.swing.JTextField();
        txtTimeIn = new javax.swing.JTextField();
        txtTimeOut = new javax.swing.JTextField();
        txtTotalWorkedHours = new javax.swing.JTextField();
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

        lblDateToday.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblDateToday.setForeground(new java.awt.Color(255, 255, 255));
        lblDateToday.setText("Current Date:");
        getContentPane().add(lblDateToday);
        lblDateToday.setBounds(180, 100, 140, 40);

        lblDate.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        lblDate.setForeground(new java.awt.Color(255, 255, 255));
        lblDate.setText("02-21-2024");
        getContentPane().add(lblDate);
        lblDate.setBounds(340, 100, 140, 40);

        lblEmployeeID.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblEmployeeID.setForeground(new java.awt.Color(255, 255, 255));
        lblEmployeeID.setText("Employee ID:");
        getContentPane().add(lblEmployeeID);
        lblEmployeeID.setBounds(148, 170, 100, 30);

        lblEmployeeID1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblEmployeeID1.setForeground(new java.awt.Color(255, 255, 255));
        lblEmployeeID1.setText("Date of Request:");
        getContentPane().add(lblEmployeeID1);
        lblEmployeeID1.setBounds(125, 210, 120, 30);

        lblLastName.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblLastName.setForeground(new java.awt.Color(255, 255, 255));
        lblLastName.setText("Date of OT:");
        getContentPane().add(lblLastName);
        lblLastName.setBounds(155, 250, 90, 30);

        lblFirstName.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblFirstName.setForeground(new java.awt.Color(255, 255, 255));
        lblFirstName.setText("Time In:");
        getContentPane().add(lblFirstName);
        lblFirstName.setBounds(174, 290, 70, 30);

        lblBirthday.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblBirthday.setForeground(new java.awt.Color(255, 255, 255));
        lblBirthday.setText("Time Out:");
        getContentPane().add(lblBirthday);
        lblBirthday.setBounds(163, 330, 80, 30);

        lblAddress1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblAddress1.setForeground(new java.awt.Color(255, 255, 255));
        lblAddress1.setText("Total Worked Hours:");
        getContentPane().add(lblAddress1);
        lblAddress1.setBounds(100, 370, 140, 30);

        lblSSS.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblSSS.setForeground(new java.awt.Color(255, 255, 255));
        lblSSS.setText("Status:");
        getContentPane().add(lblSSS);
        lblSSS.setBounds(179, 410, 70, 30);

        lblPhilhealth.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPhilhealth.setForeground(new java.awt.Color(255, 255, 255));
        lblPhilhealth.setText("Approver:");
        getContentPane().add(lblPhilhealth);
        lblPhilhealth.setBounds(159, 450, 80, 30);

        lblTIN.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblTIN.setForeground(new java.awt.Color(255, 255, 255));
        lblTIN.setText("Date Responded:");
        getContentPane().add(lblTIN);
        lblTIN.setBounds(115, 490, 160, 30);

        txtEmployeeID.setEditable(false);
        txtEmployeeID.setBackground(new java.awt.Color(255, 255, 255));
        txtEmployeeID.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtEmployeeID.setForeground(new java.awt.Color(56, 47, 47));
        getContentPane().add(txtEmployeeID);
        txtEmployeeID.setBounds(290, 170, 200, 28);

        txtReqDate.setEditable(false);
        txtReqDate.setBackground(new java.awt.Color(255, 255, 255));
        txtReqDate.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtReqDate.setForeground(new java.awt.Color(56, 47, 47));
        getContentPane().add(txtReqDate);
        txtReqDate.setBounds(290, 210, 200, 28);

        txtOtDate.setEditable(false);
        txtOtDate.setBackground(new java.awt.Color(255, 255, 255));
        txtOtDate.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtOtDate.setForeground(new java.awt.Color(56, 47, 47));
        getContentPane().add(txtOtDate);
        txtOtDate.setBounds(290, 250, 200, 28);

        txtTimeIn.setEditable(false);
        txtTimeIn.setBackground(new java.awt.Color(255, 255, 255));
        txtTimeIn.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtTimeIn.setForeground(new java.awt.Color(56, 47, 47));
        getContentPane().add(txtTimeIn);
        txtTimeIn.setBounds(290, 290, 200, 28);

        txtTimeOut.setEditable(false);
        txtTimeOut.setBackground(new java.awt.Color(255, 255, 255));
        txtTimeOut.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtTimeOut.setForeground(new java.awt.Color(56, 47, 47));
        getContentPane().add(txtTimeOut);
        txtTimeOut.setBounds(290, 330, 200, 28);

        txtTotalWorkedHours.setEditable(false);
        txtTotalWorkedHours.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalWorkedHours.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtTotalWorkedHours.setForeground(new java.awt.Color(56, 47, 47));
        getContentPane().add(txtTotalWorkedHours);
        txtTotalWorkedHours.setBounds(290, 370, 200, 28);

        jCBStatus.setBackground(new java.awt.Color(255, 255, 255));
        jCBStatus.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jCBStatus.setForeground(new java.awt.Color(58, 46, 46));
        jCBStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Approved", "Rejected" }));
        getContentPane().add(jCBStatus);
        jCBStatus.setBounds(290, 410, 200, 28);

        txtApprover.setEditable(false);
        txtApprover.setBackground(new java.awt.Color(255, 255, 255));
        txtApprover.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtApprover.setForeground(new java.awt.Color(55, 42, 42));
        getContentPane().add(txtApprover);
        txtApprover.setBounds(290, 450, 200, 28);

        txtDateResponded.setEditable(false);
        txtDateResponded.setBackground(new java.awt.Color(255, 255, 255));
        txtDateResponded.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtDateResponded.setForeground(new java.awt.Color(55, 42, 42));
        getContentPane().add(txtDateResponded);
        txtDateResponded.setBounds(290, 490, 200, 28);

        btnSubmit.setBackground(new java.awt.Color(255, 255, 255));
        btnSubmit.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSubmit.setForeground(new java.awt.Color(71, 60, 60));
        btnSubmit.setText("Save");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });
        getContentPane().add(btnSubmit);
        btnSubmit.setBounds(310, 550, 76, 28);

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

        jLabel1.setText("editot");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, -40, 630, 770);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
        // Get the selected row index
        int selectedRowIndex = hrPageRef.getTblOvertimeReq().getSelectedRow();
        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a leave request to respond.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Prepare leaveInfo array with updated leave information
        String[] overtimeInfo = {
            txtEmployeeID.getText(),                        // Employee ID
            txtReqDate.getText(),                           // Request Date
            txtOtDate.getText(),                            // Date of Overtime
            txtTimeIn.getText(),                            // Time in
            txtTimeOut.getText(),                           // Time out
            txtTotalWorkedHours.getText(),                  // Total Worked Hours
            jCBStatus.getSelectedItem().toString(),         // Status
            txtApprover.getText(),                          // Approver from lblFName in FrmHRpage
            txtDateResponded.getText(),                     // Date from lblDate in FrmEditOvertime
        };


        // Update table in FrmHRpage with the edited leave record
        hrPageRef.updateOvertimeTableRow(selectedRowIndex, overtimeInfo);

        // Update CSV file with data from the table in FrmHRpage
        String csvFilePath = "src/main/java/files/OvertimeRequest.csv";
        JTable table = hrPageRef.getTblOvertimeReq(); // Assuming getTable() returns the JTable instance in FrmHRpage
        CSVFileManager.updateCSVFile(csvFilePath, table);

        // Show success message
        JOptionPane.showMessageDialog(this, "Selected Overtime Request responded successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        // Clear text fields after saving
        clearTextFields();
    }//GEN-LAST:event_btnSubmitActionPerformed
*/
    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // Get the selected row index
        int selectedRowIndex = hrPageRef.getTblOvertimeReq().getSelectedRow();
        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select an overtime request to respond.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convert view row index to model row index
        int modelRowIndex = hrPageRef.getTblOvertimeReq().convertRowIndexToModel(selectedRowIndex);

        // Get the current status of the selected overtime request based on model index
        String currentStatus = hrPageRef.getOvertimeRequestStatus(modelRowIndex);
        
        // Check if currentStatus is null
        if (currentStatus == null) {
            JOptionPane.showMessageDialog(this, "Error fetching current status of the overtime request.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if the request has already been approved or rejected
        if (currentStatus.equals("Approved") || currentStatus.equals("Rejected")) {
            // Inform the user and prevent further action
            JOptionPane.showMessageDialog(this, "This overtime request has already been responded to.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method without proceeding further
        }

        // Define the date formats
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Format expected in the text fields and to be stored in the database

        String reqDate = txtReqDate.getText();
        String otDate = txtOtDate.getText();
        String dateResponded;

        // Parse and format dateResponded separately
        try {
            Date dateRespondedInput = dateFormat.parse(txtDateResponded.getText());
            dateResponded = dateFormat.format(dateRespondedInput);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Date format is incorrect for Date Responded.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Prepare overtimeInfo array with updated overtime information
        String[] overtimeInfo = {
            txtEmployeeID.getText(),        // Employee Number
            reqDate,                        // Request Date in yyyy-MM-dd
            otDate,                         // Date of Overtime in yyyy-MM-dd
            txtTimeIn.getText(),            // Time in
            txtTimeOut.getText(),           // Time out
            txtTotalWorkedHours.getText(),  // Total Worked Hours (OT Hours)
            jCBStatus.getSelectedItem().toString(), // Status
            txtApprover.getText(),          // Approver
            dateResponded                   // Date Responded in yyyy-MM-dd
        };

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Prepare the SQL UPDATE statement
            String sql = "UPDATE overtime_request SET EmployeeNum = ?, RequestDate = ?, OTDate = ?, TimeIn = ?, TimeOut = ?, OTHrs = ?, Status = ?, Approver = ?, DateResponded = ? WHERE EmployeeNum = ?";

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                // Set parameters for the prepared statement
                for (int i = 0; i < overtimeInfo.length; i++) {
                    statement.setString(i + 1, overtimeInfo[i]); // Set parameter index dynamically based on loop index
                }

                // Get the OvertimeRequestID of the selected row
                String overtimeRequestID = hrPageRef.getTblOvertimeReq().getValueAt(selectedRowIndex, 0).toString();
                statement.setString(10, overtimeRequestID);

                // Execute the UPDATE statement
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    // Update table in FrmHRpage with the edited overtime record
                    hrPageRef.updateOvertimeTableRow(modelRowIndex, overtimeInfo);


                    // Show success message
                    JOptionPane.showMessageDialog(this, "Selected Overtime Request responded successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Clear text fields after saving
                    clearTextFields();
                    
                    // Close the current frame
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to respond to overtime request.", "Error", JOptionPane.ERROR_MESSAGE);
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
                    new FrmEditOvertime().setVisible(true);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(FrmEditOvertime.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    public javax.swing.JButton btnSubmit;
    public javax.swing.JComboBox<String> jCBStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblAddress1;
    private javax.swing.JLabel lblBirthday;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblDateToday;
    private javax.swing.JLabel lblEmployeeID;
    private javax.swing.JLabel lblEmployeeID1;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblPhilhealth;
    private javax.swing.JLabel lblSSS;
    private javax.swing.JLabel lblTIN;
    public javax.swing.JTextField txtApprover;
    public javax.swing.JTextField txtDateResponded;
    public javax.swing.JTextField txtEmployeeID;
    public javax.swing.JTextField txtOtDate;
    public javax.swing.JTextField txtReqDate;
    public javax.swing.JTextField txtTimeIn;
    public javax.swing.JTextField txtTimeOut;
    public javax.swing.JTextField txtTotalWorkedHours;
    // End of variables declaration//GEN-END:variables
}
