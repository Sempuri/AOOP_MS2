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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author EMAN
 */
public class FrmDeleteEmployee extends javax.swing.JFrame {
    
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";

    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";
    
    private FrmHRpage hrPageRef; 
    private FrmRole roleFrame;

    /**
     * Creates new form FrmDeleteEmployee
     */
    public FrmDeleteEmployee(FrmHRpage hrPageRef) {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null); // Center the frame
        setPreferredSize(new Dimension(1000, 670));
        
        // Set frame properties
        pack();  // Adjust size to the preferred size

        displayBgFromDatabase("FrmDeleteEmployee");
        
        this.hrPageRef = hrPageRef;
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
    
    //method for populating textfields
    // Method for populating textfields and combo box
public void populateFields(String[] empInfo) {
    txtEmployeeID.setText(empInfo[0]);
    txtLastName.setText(empInfo[1]);
    txtFirstName.setText(empInfo[2]);
    
    // Assuming empInfo[3] contains a string representing the date in "yyyy-MM-dd" format
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try {
        Date date = dateFormat.parse(empInfo[3]); // Parse the date string
        jDCBirthday.setDate(date); // Set the date in the JDateChooser
    } catch (ParseException ex) {
        ex.printStackTrace();  // Handle parsing exception
    }
    
    txtAddress.setText(empInfo[4]);
    txtPhoneNum.setText(empInfo[5]);
    txtSSSNum.setText(empInfo[6]);
    txtPhilhealthNum.setText(empInfo[7]);
    txtTINNum.setText(empInfo[8]);
    txtPagibigNum.setText(empInfo[9]);
    cbStatus.setSelectedItem(empInfo[10]);
    
    // Fetch Role ID from the database based on the provided position name
    String positionName = empInfo[11]; // Assuming this is the position name (role) in the frame
    int roleId = getRoleIdFromDatabase(positionName);
    
    // Set the Role ID field to the retrieved Role ID
    txtRoleID.setText(String.valueOf(roleId)); 
    txtSupervisor.setText(empInfo[13]);

}

// Method to fetch Role ID from the database based on the role name (position)
private int getRoleIdFromDatabase(String positionName) {
    int roleId = 0; // Default value
    try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
        String sql = "SELECT RoleID FROM role WHERE Position = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, positionName);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    roleId = rs.getInt("RoleID");
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return roleId;
}
    
    //method for clearing textfields
    private void clearTextFields() {
    txtEmployeeID.setText("");
    txtLastName.setText("");
    txtFirstName.setText("");
    jDCBirthday.setDate(null);
    txtAddress.setText("");
    txtPhoneNum.setText("");
    txtSSSNum.setText("");
    txtPhilhealthNum.setText("");
    txtTINNum.setText("");
    txtPagibigNum.setText("");
    cbStatus.setSelectedIndex(-1);
    txtRoleID.setText("");
    txtSupervisor.setText("");
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
        lblLastName = new javax.swing.JLabel();
        lblFirstName = new javax.swing.JLabel();
        lblBirthday = new javax.swing.JLabel();
        lblAddress1 = new javax.swing.JLabel();
        lblPhoneNum = new javax.swing.JLabel();
        lblSSS = new javax.swing.JLabel();
        lblPhilhealth = new javax.swing.JLabel();
        lblTIN = new javax.swing.JLabel();
        lblPagibig = new javax.swing.JLabel();
        txtEmployeeID = new javax.swing.JTextField();
        txtLastName = new javax.swing.JTextField();
        txtFirstName = new javax.swing.JTextField();
        jDCBirthday = new com.toedter.calendar.JDateChooser();
        txtAddress = new javax.swing.JTextField();
        txtPhoneNum = new javax.swing.JTextField();
        txtSSSNum = new javax.swing.JTextField();
        txtPhilhealthNum = new javax.swing.JTextField();
        txtTINNum = new javax.swing.JTextField();
        txtPagibigNum = new javax.swing.JTextField();
        lblStatus = new javax.swing.JLabel();
        lblSupervisor = new javax.swing.JLabel();
        txtSupervisor = new javax.swing.JTextField();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        cbStatus = new javax.swing.JComboBox<>();
        lblRoleID = new javax.swing.JLabel();
        txtRoleID = new javax.swing.JTextField();
        btnRoleID = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        lblEmployeeID.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblEmployeeID.setForeground(new java.awt.Color(255, 255, 255));
        lblEmployeeID.setText("Employee ID:");
        getContentPane().add(lblEmployeeID);
        lblEmployeeID.setBounds(81, 150, 82, 30);

        lblLastName.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblLastName.setForeground(new java.awt.Color(255, 255, 255));
        lblLastName.setText("Last Name:");
        getContentPane().add(lblLastName);
        lblLastName.setBounds(90, 200, 70, 30);

        lblFirstName.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblFirstName.setForeground(new java.awt.Color(255, 255, 255));
        lblFirstName.setText("First Name:");
        getContentPane().add(lblFirstName);
        lblFirstName.setBounds(87, 250, 72, 30);

        lblBirthday.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblBirthday.setForeground(new java.awt.Color(255, 255, 255));
        lblBirthday.setText("Birthday:");
        getContentPane().add(lblBirthday);
        lblBirthday.setBounds(104, 300, 55, 30);

        lblAddress1.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblAddress1.setForeground(new java.awt.Color(255, 255, 255));
        lblAddress1.setText("Address:");
        getContentPane().add(lblAddress1);
        lblAddress1.setBounds(103, 350, 55, 30);

        lblPhoneNum.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblPhoneNum.setForeground(new java.awt.Color(255, 255, 255));
        lblPhoneNum.setText("Phone #:");
        getContentPane().add(lblPhoneNum);
        lblPhoneNum.setBounds(104, 400, 70, 30);

        lblSSS.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblSSS.setForeground(new java.awt.Color(255, 255, 255));
        lblSSS.setText("SSS #:");
        getContentPane().add(lblSSS);
        lblSSS.setBounds(120, 450, 37, 30);

        lblPhilhealth.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblPhilhealth.setForeground(new java.awt.Color(255, 255, 255));
        lblPhilhealth.setText("Philhealth #:");
        getContentPane().add(lblPhilhealth);
        lblPhilhealth.setBounds(550, 150, 78, 30);

        lblTIN.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblTIN.setForeground(new java.awt.Color(255, 255, 255));
        lblTIN.setText("TIN #:");
        getContentPane().add(lblTIN);
        lblTIN.setBounds(589, 200, 38, 30);

        lblPagibig.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblPagibig.setForeground(new java.awt.Color(255, 255, 255));
        lblPagibig.setText("Pag-ibig #:");
        getContentPane().add(lblPagibig);
        lblPagibig.setBounds(557, 250, 70, 30);

        txtEmployeeID.setEditable(false);
        txtEmployeeID.setBackground(new java.awt.Color(255, 255, 255));
        txtEmployeeID.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtEmployeeID.setForeground(new java.awt.Color(45, 36, 36));
        getContentPane().add(txtEmployeeID);
        txtEmployeeID.setBounds(220, 150, 195, 28);

        txtLastName.setEditable(false);
        txtLastName.setBackground(new java.awt.Color(255, 255, 255));
        txtLastName.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtLastName.setForeground(new java.awt.Color(45, 36, 36));
        getContentPane().add(txtLastName);
        txtLastName.setBounds(220, 200, 195, 28);

        txtFirstName.setEditable(false);
        txtFirstName.setBackground(new java.awt.Color(255, 255, 255));
        txtFirstName.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtFirstName.setForeground(new java.awt.Color(45, 36, 36));
        getContentPane().add(txtFirstName);
        txtFirstName.setBounds(220, 250, 195, 28);

        jDCBirthday.setBackground(new java.awt.Color(255, 255, 255));
        jDCBirthday.setForeground(new java.awt.Color(49, 38, 38));
        jDCBirthday.setDateFormatString("MM/dd/yyyy");
        jDCBirthday.setEnabled(false);
        jDCBirthday.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        getContentPane().add(jDCBirthday);
        jDCBirthday.setBounds(220, 300, 195, 27);

        txtAddress.setEditable(false);
        txtAddress.setBackground(new java.awt.Color(255, 255, 255));
        txtAddress.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtAddress.setForeground(new java.awt.Color(45, 36, 36));
        getContentPane().add(txtAddress);
        txtAddress.setBounds(220, 350, 195, 28);

        txtPhoneNum.setEditable(false);
        txtPhoneNum.setBackground(new java.awt.Color(255, 255, 255));
        txtPhoneNum.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtPhoneNum.setForeground(new java.awt.Color(45, 36, 36));
        getContentPane().add(txtPhoneNum);
        txtPhoneNum.setBounds(220, 400, 195, 28);

        txtSSSNum.setEditable(false);
        txtSSSNum.setBackground(new java.awt.Color(255, 255, 255));
        txtSSSNum.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtSSSNum.setForeground(new java.awt.Color(45, 36, 36));
        getContentPane().add(txtSSSNum);
        txtSSSNum.setBounds(220, 450, 195, 28);

        txtPhilhealthNum.setEditable(false);
        txtPhilhealthNum.setBackground(new java.awt.Color(255, 255, 255));
        txtPhilhealthNum.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtPhilhealthNum.setForeground(new java.awt.Color(45, 36, 36));
        getContentPane().add(txtPhilhealthNum);
        txtPhilhealthNum.setBounds(690, 150, 200, 28);

        txtTINNum.setEditable(false);
        txtTINNum.setBackground(new java.awt.Color(255, 255, 255));
        txtTINNum.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtTINNum.setForeground(new java.awt.Color(45, 36, 36));
        getContentPane().add(txtTINNum);
        txtTINNum.setBounds(690, 200, 200, 28);

        txtPagibigNum.setEditable(false);
        txtPagibigNum.setBackground(new java.awt.Color(255, 255, 255));
        txtPagibigNum.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtPagibigNum.setForeground(new java.awt.Color(45, 36, 36));
        getContentPane().add(txtPagibigNum);
        txtPagibigNum.setBounds(690, 250, 200, 28);

        lblStatus.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(255, 255, 255));
        lblStatus.setText("Status:");
        getContentPane().add(lblStatus);
        lblStatus.setBounds(583, 300, 43, 30);

        lblSupervisor.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblSupervisor.setForeground(new java.awt.Color(255, 255, 255));
        lblSupervisor.setText("Supervisor:");
        getContentPane().add(lblSupervisor);
        lblSupervisor.setBounds(557, 350, 70, 30);

        txtSupervisor.setEditable(false);
        txtSupervisor.setBackground(new java.awt.Color(255, 255, 255));
        txtSupervisor.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtSupervisor.setForeground(new java.awt.Color(45, 36, 36));
        getContentPane().add(txtSupervisor);
        txtSupervisor.setBounds(690, 350, 200, 28);

        btnDelete.setBackground(new java.awt.Color(255, 255, 255));
        btnDelete.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(63, 50, 50));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        getContentPane().add(btnDelete);
        btnDelete.setBounds(720, 540, 79, 28);

        btnCancel.setBackground(new java.awt.Color(255, 255, 255));
        btnCancel.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(63, 50, 50));
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancel);
        btnCancel.setBounds(810, 540, 80, 28);

        cbStatus.setBackground(new java.awt.Color(255, 255, 255));
        cbStatus.setForeground(new java.awt.Color(48, 37, 37));
        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Regular", "Probationary" }));
        cbStatus.setEnabled(false);
        getContentPane().add(cbStatus);
        cbStatus.setBounds(690, 300, 200, 27);

        lblRoleID.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblRoleID.setForeground(new java.awt.Color(255, 255, 255));
        lblRoleID.setText("Role ID:");
        getContentPane().add(lblRoleID);
        lblRoleID.setBounds(578, 400, 70, 30);

        txtRoleID.setEditable(false);
        txtRoleID.setBackground(new java.awt.Color(255, 255, 255));
        txtRoleID.setForeground(new java.awt.Color(45, 36, 36));
        getContentPane().add(txtRoleID);
        txtRoleID.setBounds(690, 400, 200, 27);

        btnRoleID.setBackground(new java.awt.Color(255, 255, 255));
        btnRoleID.setForeground(new java.awt.Color(54, 45, 45));
        btnRoleID.setText("Role ID Reference");
        btnRoleID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRoleIDActionPerformed(evt);
            }
        });
        getContentPane().add(btnRoleID);
        btnRoleID.setBounds(710, 440, 160, 20);

        jLabel2.setText("delemp");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, -30, 1010, 700);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        // Retrieve the selected row index from FrmHRpage's table
        int selectedRowIndex = hrPageRef.getTblEmpInfo().getSelectedRow();
        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Show confirmation dialog box
        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this employee?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            // Remove the selected row from the table
            DefaultTableModel model = (DefaultTableModel) hrPageRef.getTblEmpInfo().getModel();
            model.removeRow(selectedRowIndex);

            // Update CSV file with data from the updated table
            String csvFilePath = "src/main/java/files/Employee.csv";
            CSVFileManager.updateCSVFile(csvFilePath, hrPageRef.getTblEmpInfo());

            // Show success message
            JOptionPane.showMessageDialog(this, "Employee record deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Close the FrmDeleteEmployee form
            this.dispose();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed
*/
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        // Retrieve the selected row index from FrmHRpage's table
        int selectedRowIndex = hrPageRef.getTblEmpInfo().getSelectedRow();
        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Retrieve the employee ID from the selected row
        String employeeID = hrPageRef.getTblEmpInfo().getValueAt(selectedRowIndex, 0).toString();
        int employeeNum = Integer.parseInt(employeeID);

        // Show confirmation dialog box
        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this employee?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
                conn.setAutoCommit(false); // Start transaction

                try {   
                    // Delete related records from password_request table
                    String deletePasswordRequestSQL = "DELETE FROM password_request WHERE EmployeeNum = ?";
                    try (PreparedStatement deletePasswordRequestStatement = conn.prepareStatement(deletePasswordRequestSQL)) {
                        deletePasswordRequestStatement.setInt(1, employeeNum);
                        deletePasswordRequestStatement.executeUpdate();
                    }
                    
                    // Delete related records from old leave_credits table
                    String deleteOldLeaveCreditsSQL = "DELETE FROM leave_credits WHERE EmployeeNum = ?";
                    try (PreparedStatement deleteOldLeaveCreditsStatement = conn.prepareStatement(deleteOldLeaveCreditsSQL)) {
                        deleteOldLeaveCreditsStatement.setInt(1, employeeNum);
                        deleteOldLeaveCreditsStatement.executeUpdate();
                    }
                    
                    // Delete related records from leave_credits table
                    String deleteLeaveCreditsSQL = "DELETE FROM leave_credits_used WHERE EmployeeNum = ?";
                    try (PreparedStatement deleteLeaveCreditsStatement = conn.prepareStatement(deleteLeaveCreditsSQL)) {
                        deleteLeaveCreditsStatement.setInt(1, employeeNum);
                        deleteLeaveCreditsStatement.executeUpdate();
                    }

                    // Delete related records from salary table
                    String deleteSalarySQL = "DELETE FROM salary WHERE EmployeeNum = ?";
                    try (PreparedStatement deleteSalaryStatement = conn.prepareStatement(deleteSalarySQL)) {
                        deleteSalaryStatement.setInt(1, employeeNum);
                        deleteSalaryStatement.executeUpdate();
                    }

                    // Delete related records from allowance table
                    String deleteAllowanceSQL = "DELETE FROM allowance WHERE EmployeeNum = ?";
                    try (PreparedStatement deleteAllowanceStatement = conn.prepareStatement(deleteAllowanceSQL)) {
                        deleteAllowanceStatement.setInt(1, employeeNum);
                        deleteAllowanceStatement.executeUpdate();
                    }
                    
                    // Delete related records from deduction table
                    String deleteDeductionSQL = "DELETE FROM deduction WHERE EmployeeNum = ?";
                    try (PreparedStatement deleteDeductionStatement = conn.prepareStatement(deleteDeductionSQL)) {
                        deleteDeductionStatement.setInt(1, employeeNum);
                        deleteDeductionStatement.executeUpdate();
                    }
                    
                    // Delete related records from gross salary table
                    String deleteGrossSalarySQL = "DELETE FROM gross_salary WHERE EmployeeNum = ?";
                    try (PreparedStatement deleteGrossSalaryStatement = conn.prepareStatement(deleteGrossSalarySQL)) {
                        deleteGrossSalaryStatement.setInt(1, employeeNum);
                        deleteGrossSalaryStatement.executeUpdate();
                    }
                    
                    // Delete related records from payslip table
                    String deletePayslipSQL = "DELETE FROM payslip WHERE EmployeeNum = ?";
                    try (PreparedStatement deletePayslipStatement = conn.prepareStatement(deletePayslipSQL)) {
                        deletePayslipStatement.setInt(1, employeeNum);
                        deletePayslipStatement.executeUpdate();
                    }
                    
                    // Delete related records from mps report table
                    String deleteMPSreportSQL = "DELETE FROM mps_report WHERE EmployeeNum = ?";
                    try (PreparedStatement deleteMPSreportStatement = conn.prepareStatement(deleteMPSreportSQL)) {
                        deleteMPSreportStatement.setInt(1, employeeNum);
                        deleteMPSreportStatement.executeUpdate();
                    }
                    
                    // Delete related records from leave request table
                    String deleteLeaveRequestSQL = "DELETE FROM leave_request WHERE EmployeeNum = ?";
                    try (PreparedStatement deleteLeaveRequestStatement = conn.prepareStatement(deleteLeaveRequestSQL)) {
                        deleteLeaveRequestStatement.setInt(1, employeeNum);
                        deleteLeaveRequestStatement.executeUpdate();
                    }
                    
                    // Delete related records from overtime request table
                    String deleteOvertimeRequestSQL = "DELETE FROM overtime_request WHERE EmployeeNum = ?";
                    try (PreparedStatement deleteOvertimeRequestStatement = conn.prepareStatement(deleteOvertimeRequestSQL)) {
                        deleteOvertimeRequestStatement.setInt(1, employeeNum);
                        deleteOvertimeRequestStatement.executeUpdate();
                    }
                    
                    // Delete related records from login table
                    String deleteLoginSQL = "DELETE FROM login WHERE EmployeeNum = ?";
                    try (PreparedStatement deleteLoginStatement = conn.prepareStatement(deleteLoginSQL)) {
                        deleteLoginStatement.setInt(1, employeeNum);
                        deleteLoginStatement.executeUpdate();
                    }
                    
                    // Delete related records from time tracker table
                    String deleteTimeTrackerSQL = "DELETE FROM time_tracker WHERE EmployeeNum = ?";
                    try (PreparedStatement deleteTimeTrackerStatement = conn.prepareStatement(deleteTimeTrackerSQL)) {
                        deleteTimeTrackerStatement.setInt(1, employeeNum);
                        deleteTimeTrackerStatement.executeUpdate();
                    }

                    // Prepare the SQL DELETE statement for the employee
                    String sql = "DELETE FROM employee WHERE EmployeeNum = ?";
                    try (PreparedStatement statement = conn.prepareStatement(sql)) {
                        // Set the employee ID as a parameter for the DELETE statement
                        statement.setInt(1, employeeNum);

                        // Execute the DELETE statement
                        int rowsAffected = statement.executeUpdate();
                        if (rowsAffected > 0) {
                            // Remove the selected row from the table
                            DefaultTableModel model = (DefaultTableModel) hrPageRef.getTblEmpInfo().getModel();
                            model.removeRow(selectedRowIndex);

                            // Show success message
                            JOptionPane.showMessageDialog(this, "Employee record deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                            // Commit transaction
                            conn.commit();

                            // Close the FrmDeleteEmployee form
                            this.dispose();
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to delete employee record.", "Error", JOptionPane.ERROR_MESSAGE);
                            conn.rollback(); // Rollback transaction
                        }
                    }
                } catch (SQLException e) {
                    conn.rollback(); // Rollback transaction in case of error
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    conn.setAutoCommit(true); // Reset auto-commit to true
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


/*
// Method to check if the employee has associated accounts
private boolean employeeHasAccounts(String employeeID) {
    boolean hasAccounts = false;
    try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
        // Prepare the SQL SELECT statement to check for associated accounts
        String sql = "SELECT COUNT(*) AS count FROM login WHERE EmployeeNum = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, employeeID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    hasAccounts = (count > 0);
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle exception
    }
    return hasAccounts;
}
*/

    
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private boolean isRoleFrameOpen = false; // Flag to track if the role frame is open
    
    private void btnRoleIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRoleIDActionPerformed
        // Check if the role frame is already open
        if (!isRoleFrameOpen) {
            // Set the flag to indicate that the role frame is now open
            isRoleFrameOpen = true;

            // Check if roleFrame is not null
            if (roleFrame != null) {
                roleFrame.setVisible(true);
                roleFrame.setLocationRelativeTo(this); // Center the frame relative to FrmDeleteEmployee
            } else {
                // Instantiate FrmRole and set it as the roleFrame
                roleFrame = new FrmRole(null, null, this); // Passing null for newEmpPage and editEmpPage
                roleFrame.setVisible(true);
                roleFrame.setLocationRelativeTo(this); // Center the frame relative to FrmDeleteEmployee

                // Add a window listener to detect when the role frame is closed
                roleFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        // Reset the flag when the role frame is closed
                        isRoleFrameOpen = false;
                    }
                });
            }
        }
    }//GEN-LAST:event_btnRoleIDActionPerformed

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
            java.util.logging.Logger.getLogger(FrmDeleteEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmDeleteEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmDeleteEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmDeleteEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new FrmDeleteEmployee().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    public javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRoleID;
    public javax.swing.JComboBox<String> cbStatus;
    public com.toedter.calendar.JDateChooser jDCBirthday;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblAddress1;
    private javax.swing.JLabel lblBirthday;
    private javax.swing.JLabel lblEmployeeID;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblPagibig;
    private javax.swing.JLabel lblPhilhealth;
    private javax.swing.JLabel lblPhoneNum;
    private javax.swing.JLabel lblRoleID;
    private javax.swing.JLabel lblSSS;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblSupervisor;
    private javax.swing.JLabel lblTIN;
    public javax.swing.JTextField txtAddress;
    public javax.swing.JTextField txtEmployeeID;
    public javax.swing.JTextField txtFirstName;
    public javax.swing.JTextField txtLastName;
    public javax.swing.JTextField txtPagibigNum;
    public javax.swing.JTextField txtPhilhealthNum;
    public javax.swing.JTextField txtPhoneNum;
    public javax.swing.JTextField txtRoleID;
    public javax.swing.JTextField txtSSSNum;
    public javax.swing.JTextField txtSupervisor;
    public javax.swing.JTextField txtTINNum;
    // End of variables declaration//GEN-END:variables
}
