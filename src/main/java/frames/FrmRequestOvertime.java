/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frames;

import classes.CSVFileManager;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author EMAN
 */
public class FrmRequestOvertime extends javax.swing.JFrame {

    private FrmAttendance attendancePageRef;
    private FrmOvertimeHistory historyPageRef;
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";
    
    /**
     * Creates new form FrmRequestOvertime
     */
    public FrmRequestOvertime(FrmAttendance attendancePageRef) {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null); // Center the frame
        this.attendancePageRef = attendancePageRef;
        historyPageRef = new FrmOvertimeHistory(attendancePageRef); // Initialize historyPageRef
        setPreferredSize(new Dimension(586, 467));
        
        // Set frame properties
        pack();  // Adjust size to the preferred size
    
        displayBgFromDatabase("FrmRequestOvertime");
        
        showDate();
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

    //event handler method for close button
    private void FrameClose(java.awt.event.MouseEvent evt) {                            
        this.dispose();
    }    

    //for showing of date
    public void showDate(){
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat ("yyyy-MM-dd");
        String date = s.format(d);
        lblDate.setText(date);
    }
    
    //method for populating textfields and jDCchooser
    public void populateFields(String[] overtimeInfo) {
        // Check if overtimeInfo has the expected length
        if (overtimeInfo == null || overtimeInfo.length < 5) {
            JOptionPane.showMessageDialog(this, "Invalid overtime information.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        txtEmployeeID.setText(overtimeInfo[0]);
        txtReqDate.setText(getLblDate().getText());
        txtDate.setText(overtimeInfo[1]);
        txtTimeIn.setText(overtimeInfo[2]);
        txtTimeOut.setText(overtimeInfo[3]);
        
        // Check if timeIn or timeOut is empty
        if (overtimeInfo[2].isEmpty() || overtimeInfo[3].isEmpty()) {
            txtTotalWorkedHours.setText("N/A"); 
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss"); // 24-hour format with seconds
        try {
            Date timeIn = dateFormat.parse(overtimeInfo[2]);
            Date timeOut = dateFormat.parse(overtimeInfo[3]);

            long differenceInMilliseconds = timeOut.getTime() - timeIn.getTime();
            double hoursWorked = (double) differenceInMilliseconds / (1000 * 60 * 60);

            double overtimeHours = hoursWorked - 9.0;

            if (overtimeHours < 0) {
                overtimeHours = 0;
            }

            txtTotalWorkedHours.setText(String.format("%.2f", overtimeHours));
        } catch (ParseException e) {
            e.printStackTrace();
            txtTotalWorkedHours.setText("Error calculating overtime");
        }
    }
    
    //method for clearing textfields
    private void clearTextFields() {
        txtEmployeeID.setText("");
        txtReqDate.setText("");
        txtDate.setText("");
        txtTimeIn.setText("");
        txtTimeOut.setText("");
        txtTotalWorkedHours.setText("");
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
        txtEmployeeID = new javax.swing.JTextField();
        txtReqDate = new javax.swing.JTextField();
        txtDate = new javax.swing.JTextField();
        txtTimeIn = new javax.swing.JTextField();
        txtTimeOut = new javax.swing.JTextField();
        txtTotalWorkedHours = new javax.swing.JTextField();
        btnSubmit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(586, 467));
        setMinimumSize(new java.awt.Dimension(586, 467));
        setPreferredSize(new java.awt.Dimension(586, 467));
        setSize(new java.awt.Dimension(586, 467));
        getContentPane().setLayout(null);

        lblDateToday.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblDateToday.setForeground(new java.awt.Color(255, 255, 255));
        lblDateToday.setText("Date Today:");
        getContentPane().add(lblDateToday);
        lblDateToday.setBounds(195, 80, 90, 40);

        lblDate.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblDate.setForeground(new java.awt.Color(255, 255, 255));
        lblDate.setText("02/21/2024");
        getContentPane().add(lblDate);
        lblDate.setBounds(300, 90, 128, 23);

        lblEmployeeID.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblEmployeeID.setForeground(new java.awt.Color(255, 255, 255));
        lblEmployeeID.setText("Employee ID:");
        getContentPane().add(lblEmployeeID);
        lblEmployeeID.setBounds(90, 130, 130, 15);

        lblEmployeeID1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblEmployeeID1.setForeground(new java.awt.Color(255, 255, 255));
        lblEmployeeID1.setText("Date of Request:");
        getContentPane().add(lblEmployeeID1);
        lblEmployeeID1.setBounds(90, 170, 150, 15);

        lblLastName.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblLastName.setForeground(new java.awt.Color(255, 255, 255));
        lblLastName.setText("Date:");
        getContentPane().add(lblLastName);
        lblLastName.setBounds(90, 210, 90, 15);

        lblFirstName.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblFirstName.setForeground(new java.awt.Color(255, 255, 255));
        lblFirstName.setText("Time In:");
        getContentPane().add(lblFirstName);
        lblFirstName.setBounds(90, 250, 100, 15);

        lblBirthday.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblBirthday.setForeground(new java.awt.Color(255, 255, 255));
        lblBirthday.setText("Time Out:");
        getContentPane().add(lblBirthday);
        lblBirthday.setBounds(90, 290, 110, 15);

        lblAddress1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblAddress1.setForeground(new java.awt.Color(255, 255, 255));
        lblAddress1.setText("Total Worked Hours:");
        getContentPane().add(lblAddress1);
        lblAddress1.setBounds(90, 330, 170, 15);

        txtEmployeeID.setEditable(false);
        txtEmployeeID.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        getContentPane().add(txtEmployeeID);
        txtEmployeeID.setBounds(310, 130, 195, 28);

        txtReqDate.setEditable(false);
        txtReqDate.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        getContentPane().add(txtReqDate);
        txtReqDate.setBounds(310, 170, 195, 28);

        txtDate.setEditable(false);
        txtDate.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        getContentPane().add(txtDate);
        txtDate.setBounds(310, 210, 195, 28);

        txtTimeIn.setEditable(false);
        txtTimeIn.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        getContentPane().add(txtTimeIn);
        txtTimeIn.setBounds(310, 250, 195, 28);

        txtTimeOut.setEditable(false);
        txtTimeOut.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        getContentPane().add(txtTimeOut);
        txtTimeOut.setBounds(310, 290, 195, 28);

        txtTotalWorkedHours.setEditable(false);
        txtTotalWorkedHours.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        getContentPane().add(txtTotalWorkedHours);
        txtTotalWorkedHours.setBounds(310, 330, 195, 28);

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
        btnSubmit.setBounds(360, 370, 86, 28);

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
        btnCancel.setBounds(450, 370, 80, 28);

        jLabel2.setText("or");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, -20, 590, 480);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
        // Get the selected row index
        int selectedRowIndex = attendancePageRef.getTimeTracker_table().getSelectedRow();
        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to request an overtime.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the values from text fields
        String employeeID = txtEmployeeID.getText();
        String reqDate = txtReqDate.getText();
        String otDate = txtDate.getText();
        String timeIn = txtTimeIn.getText();
        String timeOut = txtTimeOut.getText();
        String totalWorkedHrs = txtTotalWorkedHours.getText();

        // Specify the full path to the CSV file
        String filePath = "src/main/java/files/OvertimeRequest.csv";

        // Save the values to a CSV file
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            // Check if the file is empty (i.e., if this is the first entry)
            File file = new File(filePath);
            if (file.length() == 0) { // If the file is empty, write the header row
                writer.println("Employee ID,Request Date,Date of OT,Time In,Time Out,Total Worked Hrs,Status,Approver,Date Responded");
            }

            // Get the last row index
            int lastRowIndex = getLastRowIndex(filePath);

            // Write the current entry as a new line
            writer.println(employeeID + "," + reqDate + "," + otDate + "," + timeIn + "," + timeOut + "," + totalWorkedHrs + ",Pending,,");

            // Display a confirmation message
            JOptionPane.showMessageDialog(this, "Request submitted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        // Clear text fields after saving
        clearTextFields();
    }

    // Method to get the last row index in the CSV file
    private int getLastRowIndex(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int rowCount = 0;
            while (reader.readLine() != null) {
                rowCount++;
            }
            return rowCount;
        } catch (IOException e) {
            e.printStackTrace();
            return -1; // Error occurred while reading the file
        }
    }//GEN-LAST:event_btnSubmitActionPerformed
*/
    public void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // Get the values from text fields
        String employeeIDText = txtEmployeeID.getText();
        String reqDate = txtReqDate.getText();
        String otDate = txtDate.getText();
        String timeIn = txtTimeIn.getText();
        String timeOut = txtTimeOut.getText();
        String totalWorkedHrs = txtTotalWorkedHours.getText();

        // Check if the employee ID field is empty or not a valid integer
        if (employeeIDText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Employee ID is required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int employeeID;
        try {
            employeeID = Integer.parseInt(employeeIDText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Action.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if time out has been entered
        if (timeOut.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please time out first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if total worked hours has been calculated
        if (totalWorkedHrs.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please calculate the total worked hours.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if the total worked hours is 0
        try {
            double workedHours = Double.parseDouble(totalWorkedHrs);
            if (workedHours == 0) {
                JOptionPane.showMessageDialog(this, "There are no overtime hours.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid total worked hours format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if an overtime request already exists for the specified date and employee ID
        if (overtimeRequestExists(employeeID, otDate)) {
            JOptionPane.showMessageDialog(this, "An overtime request for this date already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Specify the SQL INSERT statement without the 'DateResponded' field
        String sql = "INSERT INTO overtime_request (EmployeeNum, RequestDate, OTDate, TimeIn, TimeOut, OTHrs, Status, DateResponded) VALUES (?, ?, ?, ?, ?, ?, ?, NULL)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement(sql)) {
            // Set parameters for the prepared statement
            statement.setInt(1, employeeID);
            statement.setString(2, reqDate); // Use the input reqDate directly
            statement.setString(3, otDate);
            statement.setString(4, timeIn);
            statement.setString(5, timeOut);
            statement.setString(6, totalWorkedHrs);
            statement.setString(7, "Pending"); // Assuming "Pending" is the default status

            // Execute the INSERT statement
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Request submitted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to submit request.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        // Clear text fields after saving
        clearTextFields();

        // Close the current frame
        this.dispose();
    }

// Method to check if an overtime request already exists for the specified date and employee ID
private boolean overtimeRequestExists(int employeeID, String otDate) {
    String sql = "SELECT COUNT(*) FROM overtime_request WHERE EmployeeNum = ? AND OTDate = ?";
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, employeeID);
        statement.setString(2, otDate);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception appropriately
    }
    return false; // Default to false in case of exceptions or other errors
}



   
   private int getLastRowIndex(Connection connection, String tableName) throws SQLException {
    String sql = "SELECT COUNT(*) FROM " + tableName;

    try (PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
    }

    return -1; // Error occurred while getting the row count
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
            java.util.logging.Logger.getLogger(FrmRequestOvertime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmRequestOvertime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmRequestOvertime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmRequestOvertime.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new FrmRequestOvertime().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    public javax.swing.JButton btnSubmit;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblAddress1;
    private javax.swing.JLabel lblBirthday;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblDateToday;
    private javax.swing.JLabel lblEmployeeID;
    private javax.swing.JLabel lblEmployeeID1;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblLastName;
    public javax.swing.JTextField txtDate;
    public javax.swing.JTextField txtEmployeeID;
    public javax.swing.JTextField txtReqDate;
    public javax.swing.JTextField txtTimeIn;
    public javax.swing.JTextField txtTimeOut;
    public javax.swing.JTextField txtTotalWorkedHours;
    // End of variables declaration//GEN-END:variables
}
