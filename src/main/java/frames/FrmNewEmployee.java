/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frames;
import classes.CSVFileManager;
import classes.Employee;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author EMAN
 */
public class FrmNewEmployee extends javax.swing.JFrame {
    
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";
    
    private FrmRole roleFrame;

    /**
     * Creates new form FrmNewEmployee
     */
    private FrmHRpage hrPageRef;
    
    private javax.swing.JTable tblEmpInfo;

    public FrmNewEmployee(FrmHRpage hrPage) {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null); // Center the frame
        setPreferredSize(new Dimension(1000, 670));
        
        // Set frame properties
        pack();  // Adjust size to the preferred size
    
        displayBgFromDatabase("FrmNewEmployee");
        
        // Inside your constructor or initialization method
        tblEmpInfo = new javax.swing.JTable();
        tblEmpInfo.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            
        

        },
            new String [] {
                "EmpID", "Last Name", "First Name", "Birthday", "Address", "Phone #", "SSS #", "Philhealth #", "TIN #", "Pagibig #", "Status", "Position", "Department", "Supervisor"
            }
        ));
        
        this.hrPageRef = hrPage;
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

    //event handler method for close button
    private void FrameClose(java.awt.event.MouseEvent evt) {                            
        this.dispose();
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
        txtSupervisor.setText("");
        txtRoleID.setText("");
    }

    
    // Method to validate input
    private boolean validateInput() {
        try {
            // Validate numeric fields
            Integer.parseInt(txtEmployeeID.getText());

 
        return true; // Return true if all fields pass validation
    } catch (NumberFormatException | NullPointerException ex) {
        return false; // Return false if any field fails validation
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

        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
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
        cbStatus = new javax.swing.JComboBox<>();
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
        txtRoleID = new javax.swing.JTextField();
        lblRoleID = new javax.swing.JLabel();
        btnRoleID = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1000, 670));
        setMinimumSize(new java.awt.Dimension(1000, 670));
        setPreferredSize(new java.awt.Dimension(1000, 670));
        setSize(new java.awt.Dimension(1000, 670));
        getContentPane().setLayout(null);

        btnSave.setBackground(new java.awt.Color(255, 255, 255));
        btnSave.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSave.setForeground(new java.awt.Color(82, 66, 66));
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        getContentPane().add(btnSave);
        btnSave.setBounds(720, 540, 76, 28);

        btnCancel.setBackground(new java.awt.Color(255, 255, 255));
        btnCancel.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(82, 66, 66));
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancel);
        btnCancel.setBounds(810, 540, 80, 28);

        lblEmployeeID.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblEmployeeID.setForeground(new java.awt.Color(255, 255, 255));
        lblEmployeeID.setText("Employee ID:");
        getContentPane().add(lblEmployeeID);
        lblEmployeeID.setBounds(84, 150, 100, 30);

        lblLastName.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblLastName.setForeground(new java.awt.Color(255, 255, 255));
        lblLastName.setText("Last Name:");
        getContentPane().add(lblLastName);
        lblLastName.setBounds(94, 200, 90, 30);

        lblFirstName.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblFirstName.setForeground(new java.awt.Color(255, 255, 255));
        lblFirstName.setText("First Name:");
        getContentPane().add(lblFirstName);
        lblFirstName.setBounds(92, 250, 90, 30);

        lblBirthday.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblBirthday.setForeground(new java.awt.Color(255, 255, 255));
        lblBirthday.setText("Birthday:");
        getContentPane().add(lblBirthday);
        lblBirthday.setBounds(106, 300, 70, 30);

        lblAddress1.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblAddress1.setForeground(new java.awt.Color(255, 255, 255));
        lblAddress1.setText("Address:");
        getContentPane().add(lblAddress1);
        lblAddress1.setBounds(104, 350, 70, 30);

        lblPhoneNum.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblPhoneNum.setForeground(new java.awt.Color(255, 255, 255));
        lblPhoneNum.setText("Phone #:");
        getContentPane().add(lblPhoneNum);
        lblPhoneNum.setBounds(104, 400, 70, 30);

        lblSSS.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblSSS.setForeground(new java.awt.Color(255, 255, 255));
        lblSSS.setText("SSS #:");
        getContentPane().add(lblSSS);
        lblSSS.setBounds(120, 450, 60, 30);

        lblPhilhealth.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblPhilhealth.setForeground(new java.awt.Color(255, 255, 255));
        lblPhilhealth.setText("Philhealth #:");
        getContentPane().add(lblPhilhealth);
        lblPhilhealth.setBounds(533, 150, 100, 30);

        lblTIN.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblTIN.setForeground(new java.awt.Color(255, 255, 255));
        lblTIN.setText("TIN #:");
        getContentPane().add(lblTIN);
        lblTIN.setBounds(572, 200, 60, 30);

        lblPagibig.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblPagibig.setForeground(new java.awt.Color(255, 255, 255));
        lblPagibig.setText("Pag-ibig #:");
        getContentPane().add(lblPagibig);
        lblPagibig.setBounds(542, 250, 90, 30);

        cbStatus.setBackground(new java.awt.Color(255, 255, 255));
        cbStatus.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        cbStatus.setForeground(new java.awt.Color(50, 44, 44));
        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Regular", "Probationary" }));
        getContentPane().add(cbStatus);
        cbStatus.setBounds(690, 300, 200, 27);

        txtEmployeeID.setBackground(new java.awt.Color(255, 255, 255));
        txtEmployeeID.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtEmployeeID.setForeground(new java.awt.Color(56, 44, 44));
        getContentPane().add(txtEmployeeID);
        txtEmployeeID.setBounds(230, 150, 200, 28);

        txtLastName.setBackground(new java.awt.Color(255, 255, 255));
        txtLastName.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtLastName.setForeground(new java.awt.Color(56, 44, 44));
        getContentPane().add(txtLastName);
        txtLastName.setBounds(230, 200, 200, 28);

        txtFirstName.setBackground(new java.awt.Color(255, 255, 255));
        txtFirstName.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtFirstName.setForeground(new java.awt.Color(56, 44, 44));
        getContentPane().add(txtFirstName);
        txtFirstName.setBounds(230, 250, 200, 28);

        jDCBirthday.setBackground(new java.awt.Color(255, 255, 255));
        jDCBirthday.setForeground(new java.awt.Color(48, 38, 38));
        jDCBirthday.setDateFormatString("MM/dd/yyyy");
        jDCBirthday.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        getContentPane().add(jDCBirthday);
        jDCBirthday.setBounds(230, 300, 200, 27);

        txtAddress.setBackground(new java.awt.Color(255, 255, 255));
        txtAddress.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtAddress.setForeground(new java.awt.Color(56, 44, 44));
        getContentPane().add(txtAddress);
        txtAddress.setBounds(230, 350, 200, 28);

        txtPhoneNum.setBackground(new java.awt.Color(255, 255, 255));
        txtPhoneNum.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtPhoneNum.setForeground(new java.awt.Color(56, 44, 44));
        getContentPane().add(txtPhoneNum);
        txtPhoneNum.setBounds(230, 400, 200, 28);

        txtSSSNum.setBackground(new java.awt.Color(255, 255, 255));
        txtSSSNum.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtSSSNum.setForeground(new java.awt.Color(56, 44, 44));
        getContentPane().add(txtSSSNum);
        txtSSSNum.setBounds(230, 450, 200, 28);

        txtPhilhealthNum.setBackground(new java.awt.Color(255, 255, 255));
        txtPhilhealthNum.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtPhilhealthNum.setForeground(new java.awt.Color(56, 44, 44));
        getContentPane().add(txtPhilhealthNum);
        txtPhilhealthNum.setBounds(690, 150, 200, 28);

        txtTINNum.setBackground(new java.awt.Color(255, 255, 255));
        txtTINNum.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtTINNum.setForeground(new java.awt.Color(56, 44, 44));
        getContentPane().add(txtTINNum);
        txtTINNum.setBounds(690, 200, 200, 28);

        txtPagibigNum.setBackground(new java.awt.Color(255, 255, 255));
        txtPagibigNum.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtPagibigNum.setForeground(new java.awt.Color(56, 44, 44));
        getContentPane().add(txtPagibigNum);
        txtPagibigNum.setBounds(690, 250, 200, 28);

        lblStatus.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(255, 255, 255));
        lblStatus.setText("Status:");
        getContentPane().add(lblStatus);
        lblStatus.setBounds(568, 300, 70, 30);

        lblSupervisor.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblSupervisor.setForeground(new java.awt.Color(255, 255, 255));
        lblSupervisor.setText("Supervisor:");
        getContentPane().add(lblSupervisor);
        lblSupervisor.setBounds(541, 350, 90, 30);

        txtSupervisor.setBackground(new java.awt.Color(255, 255, 255));
        txtSupervisor.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtSupervisor.setForeground(new java.awt.Color(56, 44, 44));
        getContentPane().add(txtSupervisor);
        txtSupervisor.setBounds(690, 350, 200, 28);

        txtRoleID.setBackground(new java.awt.Color(255, 255, 255));
        txtRoleID.setForeground(new java.awt.Color(56, 44, 44));
        getContentPane().add(txtRoleID);
        txtRoleID.setBounds(690, 400, 200, 30);

        lblRoleID.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblRoleID.setForeground(new java.awt.Color(255, 255, 255));
        lblRoleID.setText("Role ID:");
        lblRoleID.setToolTipText("");
        getContentPane().add(lblRoleID);
        lblRoleID.setBounds(563, 400, 70, 30);

        btnRoleID.setBackground(new java.awt.Color(255, 255, 255));
        btnRoleID.setForeground(new java.awt.Color(68, 56, 56));
        btnRoleID.setText("Role ID Reference");
        btnRoleID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRoleIDActionPerformed(evt);
            }
        });
        getContentPane().add(btnRoleID);
        btnRoleID.setBounds(710, 440, 160, 20);

        jLabel1.setText("newemp");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, -30, 1000, 700);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        // Validate input before saving
    if (!validateInput()) {
        JOptionPane.showMessageDialog(this, "Invalid input. Please check the fields and try again.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Prepare empInfo array with employee information
    String[] empInfo = {
        txtEmployeeID.getText(),                // Employee ID
        txtLastName.getText(),                  // Last Name
        txtFirstName.getText(),                 // First Name
        new SimpleDateFormat("MM/dd/yyyy").format(jDCBirthday.getDate()), // Birthday
        txtAddress.getText(),                   // Address
        txtPhoneNum.getText(),                  // Phone Number
        txtSSSNum.getText(),                    // SSS Number
        txtPhilhealthNum.getText(),             // Philhealth Number
        txtTINNum.getText(),                    // TIN Number
        txtPagibigNum.getText(),                // Pagibig Number
        txtStatus.getText(),                    // Status
        txtPosition.getText(),                  // Position
        txtDepartment.getText(),                // Department
        txtSupervisor.getText(),                // Supervisor
        txtBasicSalary.getText(),               // Basic Salary
        txtRiceSubsidy.getText(),               // Rice Subsidy
        txtPhoneAllowance.getText(),            // Phone Allowance
        txtClothingAllowance.getText(),         // Clothing Allowance
        txtGrossSemi.getText(),                 // Gross Semi
        txtHourlyRate.getText()                 // Hourly Rate
    };

    // Update table in FrmHRpage with the new employee record
    hrPageRef.updateTable(empInfo);
    
    // Update CSV file with data from the table in FrmHRpage
    String csvFilePath = "src/main/java/files/Employee.csv";
    JTable table = hrPageRef.getTblEmpInfo(); // Assuming getTable() returns the JTable instance in FrmHRpage
    CSVFileManager.updateCSVFile(csvFilePath, table);
    
    // Show success message
    JOptionPane.showMessageDialog(this, "Employee record saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    
    // Clear text fields after saving
    clearTextFields();


    }//GEN-LAST:event_btnSaveActionPerformed
*/

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {
        // Check if any of the text fields are empty
        if (anyFieldIsEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method without saving
        }

        // Check if EmployeeNum is an integer
        int employeeNum;
        try {
            employeeNum = Integer.parseInt(txtEmployeeID.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Employee ID must be an integer.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method without saving
        }

        // Check if RoleID is an integer
        int roleId;
        try {
            roleId = Integer.parseInt(txtRoleID.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Role ID must be an integer.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method without saving
        }

        // Retrieve the role and department names
        String positionName = getPositionName(roleId);
        String departmentName = getDepartmentName(roleId);

        // Retrieve the selected status from the comboBox
        String selectedStatus = (String) cbStatus.getSelectedItem();

        // Prepare employeeInfo array with employee information
        String[] employeeInfo = {
            txtEmployeeID.getText(),               // Employee ID
            txtLastName.getText(),                 // Last Name
            txtFirstName.getText(),                // First Name
            formatDate(jDCBirthday.getDate()),     // Birthday
            txtAddress.getText(),                  // Address
            txtPhoneNum.getText(),                 // Phone Number
            txtSSSNum.getText(),                   // SSS Number
            txtPhilhealthNum.getText(),            // Philhealth Number
            txtTINNum.getText(),                   // TIN Number
            txtPagibigNum.getText(),               // Pagibig Number
            selectedStatus,                        // Status
            txtRoleID.getText(),                   // Role ID
            txtSupervisor.getText(),               // Supervisor
            positionName,                          // Role Name
            departmentName                         // Department Name
        };

        // Debugging: Print the employeeInfo array
        System.out.println("Employee Info: " + Arrays.toString(employeeInfo));

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Check if the EmployeeNum already exists
            String checkSql = "SELECT COUNT(*) FROM employee WHERE EmployeeNum = ?";
            try (PreparedStatement checkStatement = conn.prepareStatement(checkSql)) {
                checkStatement.setInt(1, employeeNum);
                ResultSet rs = checkStatement.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(this, "The Employee Number already exists. Please enter a unique Employee Number.", "Duplicate Entry", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method without saving
                }
            }

            // Check if the RoleID exists
            String checkRoleSql = "SELECT COUNT(*) FROM role WHERE RoleID = ?";
            try (PreparedStatement checkRoleStatement = conn.prepareStatement(checkRoleSql)) {
                checkRoleStatement.setInt(1, roleId);
                ResultSet rs = checkRoleStatement.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    JOptionPane.showMessageDialog(this, "The Role ID does not exist. Please enter a valid Role ID.", "Invalid Role ID", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method without saving
                }
            }

            // Check if the Employee with the same first and last names already exists
            String checkNameSql = "SELECT COUNT(*) FROM employee WHERE LastName = ? AND FirstName = ?";
            try (PreparedStatement checkNameStatement = conn.prepareStatement(checkNameSql)) {
                checkNameStatement.setString(1, txtLastName.getText());
                checkNameStatement.setString(2, txtFirstName.getText());
                ResultSet rs = checkNameStatement.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(this, "This employee already exists. Please enter another employee.", "Duplicate Entry", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method without saving
                }
            }

            // Prepare the SQL INSERT statement for the employee table
            String sql = "INSERT INTO employee (EmployeeNum, LastName, FirstName, Birthday, Address, PhoneNumber, SssNumber, PhilhealthNumber, TinNumber, PagibigNumber, Status, RoleID, ImmediateSupervisor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                // Set parameters for the prepared statement
                for (int i = 0; i < employeeInfo.length - 2; i++) { // Exclude RoleName and DepartmentName for the database insert
                    statement.setString(i + 1, employeeInfo[i]);
                }

                // Execute the INSERT statement
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    // Show success message
                    JOptionPane.showMessageDialog(this, "Employee record saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Insert into the salary table
                    String insertSalarySQL = "INSERT INTO salary (EmployeeNum, BasicSalary, GrossSMRate, HourlyRate, DateUpdated) VALUES (?, 0.00, 0.00, 0.00, ?)";
                    try (PreparedStatement salaryStatement = conn.prepareStatement(insertSalarySQL)) {
                        salaryStatement.setInt(1, employeeNum);
                        salaryStatement.setString(2, formatDate(new Date())); // Current date as the DateUpdated
                        salaryStatement.executeUpdate();
                    }

                    // Insert into the allowance table
                    String insertAllowanceSQL = "INSERT INTO allowance (EmployeeNum, RiceSubsidy, PhoneAllowance, ClothingAllowance, DateUpdated) VALUES (?, 0.00, 0.00, 0.00, ?)";
                    try (PreparedStatement allowanceStatement = conn.prepareStatement(insertAllowanceSQL)) {
                        allowanceStatement.setInt(1, employeeNum);
                        allowanceStatement.setString(2, formatDate(new Date())); // Current date as the DateUpdated
                        allowanceStatement.executeUpdate();
                    }
                    
                    // Insert into the deductions table
                    String insertDeductionSQL = "INSERT INTO deduction (EmployeeNum, LastName, FirstName, Month, Year, GrossSalary, SSSdeduction, PagibigDeduction, PhilhealthDeduction, TotalDeduction, TaxableIncome, WithholdingTax) VALUES (?, ?, ?, 0, 0, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00)";
                    try (PreparedStatement deductionStatement = conn.prepareStatement(insertDeductionSQL)) {
                        deductionStatement.setString(1, txtEmployeeID.getText()); // Assuming txtEmployeeID holds the EmployeeNum value
                        deductionStatement.setString(2, txtLastName.getText()); // Assuming txtLastName holds the LastName value
                        deductionStatement.setString(3, txtFirstName.getText()); // Assuming txtFirstName holds the FirstName value
                        deductionStatement.executeUpdate();
                    }
                    
                    // Insert into the gross_salary table
                    String insertGrossSalarySQL = "INSERT INTO gross_salary (EmployeeNum, LastName, FirstName, HourlyRate, Month, Year, TotalWorkedHrs, OTHrs, LeaveCreditsUsedinHours, TotalGS) VALUES (?, ?, ?, 0.00, 0, 0, 0.00, 0.00, 0.00, 0.00)";
                    try (PreparedStatement grossSalaryStatement = conn.prepareStatement(insertGrossSalarySQL)) {
                        grossSalaryStatement.setString(1, txtEmployeeID.getText()); // Assuming txtEmployeeID holds the EmployeeNum value
                        grossSalaryStatement.setString(2, txtLastName.getText()); // Assuming txtLastName holds the LastName value
                        grossSalaryStatement.setString(3, txtFirstName.getText()); // Assuming txtFirstName holds the FirstName value
                        grossSalaryStatement.executeUpdate();
                    }
                    
                    // Insert into the leave_credits table
                    String insertLeaveCreditsUsedSQL = "INSERT INTO leave_credits_used (EmployeeNum, LastName, FirstName, Month, Year, LeaveCreditsUsed, LeaveCreditsRemaining) VALUES (?, ?, ?, 0, 0, 0.00, 15.00)";
                    try (PreparedStatement leaveCreditsUsedStatement = conn.prepareStatement(insertLeaveCreditsUsedSQL)) {
                        leaveCreditsUsedStatement.setString(1, txtEmployeeID.getText()); // Assuming txtEmployeeID holds the EmployeeNum value
                        leaveCreditsUsedStatement.setString(2, txtLastName.getText()); // Assuming txtLastName holds the LastName value
                        leaveCreditsUsedStatement.setString(3, txtFirstName.getText()); // Assuming txtFirstName holds the FirstName value
                        leaveCreditsUsedStatement.executeUpdate();
                    }

                    // Update table in FrmHRpage with the new employee record
                    hrPageRef.updateTable(employeeInfo);

                    // Clear text fields after saving
                    clearTextFields();
                    
                    // Close the current frame
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to save employee record.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private String getPositionName(int roleId) {
        Map<Integer, String> positionsMap = new HashMap<>();
        positionsMap.put(1, "Chief Executive Officer");
        positionsMap.put(2, "Chief Operating Officer");
        positionsMap.put(3, "Chief Finance Officer");
        positionsMap.put(4, "Chief Marketing Officer");
        positionsMap.put(5, "IT Operations and Systems");
        positionsMap.put(6, "HR Manager");
        positionsMap.put(7, "HR Team Leader");
        positionsMap.put(8, "HR Rank and File");
        positionsMap.put(9, "Accounting Head");
        positionsMap.put(10, "Payroll Manager");
        positionsMap.put(11, "Payroll Team Leader");
        positionsMap.put(12, "Payroll Rank and File");
        positionsMap.put(13, "Account Manager");
        positionsMap.put(14, "Account Team Leader");
        positionsMap.put(15, "Account Rank and File");
        positionsMap.put(16, "Sales & Marketing");
        positionsMap.put(17, "Supply Chain and Logistics");
        positionsMap.put(18, "Customer Service and Relations");

        return positionsMap.getOrDefault(roleId, "Unknown Position");
    }

    private String getDepartmentName(int roleId) {
        Map<Integer, String> departmentsMap = new HashMap<>();
        departmentsMap.put(1, "Executive");
        departmentsMap.put(2, "Operations");
        departmentsMap.put(3, "Payroll");
        departmentsMap.put(4, "Marketing");
        departmentsMap.put(5, "IT");
        departmentsMap.put(6, "HR");
        departmentsMap.put(7, "HR");
        departmentsMap.put(8, "HR");
        departmentsMap.put(9, "Payroll");
        departmentsMap.put(10, "Payroll");
        departmentsMap.put(11, "Payroll");
        departmentsMap.put(12, "Payroll");
        departmentsMap.put(13, "Operations");
        departmentsMap.put(14, "Operations");
        departmentsMap.put(15, "Operations");
        departmentsMap.put(16, "Marketing");
        departmentsMap.put(17, "Marketing");
        departmentsMap.put(18, "Marketing");

        return departmentsMap.getOrDefault(roleId, "Unknown Department");
    }

    // Helper method to check if any of the text fields are empty
    private boolean anyFieldIsEmpty() {
    // Add your checks for other fields as needed
        if (txtEmployeeID.getText().isEmpty() || txtLastName.getText().isEmpty() || txtFirstName.getText().isEmpty() ||
            jDCBirthday.getDate() == null || txtAddress.getText().isEmpty() || txtPhoneNum.getText().isEmpty() ||
            txtSSSNum.getText().isEmpty() || txtPhilhealthNum.getText().isEmpty() || txtTINNum.getText().isEmpty() ||
            txtPagibigNum.getText().isEmpty() || cbStatus.getSelectedItem() == null || txtRoleID.getText().isEmpty() ||
            txtSupervisor.getText().isEmpty()) {
            return true;
        }
        return false;
    }
    
    // Method to set the FrmRole reference
    public void setRoleFrame(FrmRole roleFrame) {
        this.roleFrame = roleFrame;
    }


    
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
                roleFrame.setLocationRelativeTo(this); // Center the frame relative to FrmNewEmployee
            } else {
                // Instantiate FrmRole and set it as the roleFrame
                roleFrame = new FrmRole(this, null, null); // Passing null for editEmpPage and deleteEmpPage
                roleFrame.setVisible(true);
                roleFrame.setLocationRelativeTo(this); // Center the frame relative to FrmNewEmployee

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
            java.util.logging.Logger.getLogger(FrmNewEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmNewEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmNewEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmNewEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
           
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnRoleID;
    public javax.swing.JButton btnSave;
    public javax.swing.JComboBox<String> cbStatus;
    public com.toedter.calendar.JDateChooser jDCBirthday;
    private javax.swing.JLabel jLabel1;
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
