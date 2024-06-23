/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frames;

import classes.CSVFileManager;
import java.awt.Dimension;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author EMAN
 */
public class FrmEditEmployee extends javax.swing.JFrame {
    
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";
    
    private FrmRole roleFrame;

    private FrmHRpage hrPageRef;
    /**
     * Creates new form FrmEditEmployee
     */
   public FrmEditEmployee(FrmHRpage hrPageRef) {
        initComponents();
        setPreferredSize(new Dimension(1000, 670));
        
        // Set frame properties
        pack();  // Adjust size to the preferred size
        
        displayBgFromDatabase("FrmEditEmployee");
    
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





// Method for clearing textfields and combo box
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

        lblEmployeeID = new javax.swing.JLabel();
        lblTip = new javax.swing.JLabel();
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
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        cbStatus = new javax.swing.JComboBox<>();
        lblRoleID = new javax.swing.JLabel();
        txtRoleID = new javax.swing.JTextField();
        btnRoleID = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1000, 670));
        setMinimumSize(new java.awt.Dimension(1000, 670));
        setPreferredSize(new java.awt.Dimension(1000, 670));
        setSize(new java.awt.Dimension(1000, 670));
        getContentPane().setLayout(null);

        lblEmployeeID.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblEmployeeID.setForeground(new java.awt.Color(255, 255, 255));
        lblEmployeeID.setText("Employee ID:");
        getContentPane().add(lblEmployeeID);
        lblEmployeeID.setBounds(80, 150, 90, 30);

        lblTip.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        lblTip.setForeground(new java.awt.Color(255, 255, 255));
        lblTip.setText("Tip: Refresh tables after updating information.");
        lblTip.setToolTipText("");
        getContentPane().add(lblTip);
        lblTip.setBounds(60, 570, 280, 33);

        lblLastName.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblLastName.setForeground(new java.awt.Color(255, 255, 255));
        lblLastName.setText("Last Name:");
        getContentPane().add(lblLastName);
        lblLastName.setBounds(90, 200, 80, 30);

        lblFirstName.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblFirstName.setForeground(new java.awt.Color(255, 255, 255));
        lblFirstName.setText("First Name:");
        getContentPane().add(lblFirstName);
        lblFirstName.setBounds(88, 250, 80, 30);

        lblBirthday.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblBirthday.setForeground(new java.awt.Color(255, 255, 255));
        lblBirthday.setText("Birthday:");
        getContentPane().add(lblBirthday);
        lblBirthday.setBounds(104, 300, 60, 30);

        lblAddress1.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblAddress1.setForeground(new java.awt.Color(255, 255, 255));
        lblAddress1.setText("Address:");
        getContentPane().add(lblAddress1);
        lblAddress1.setBounds(103, 350, 70, 30);

        lblPhoneNum.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblPhoneNum.setForeground(new java.awt.Color(255, 255, 255));
        lblPhoneNum.setText("Phone #:");
        getContentPane().add(lblPhoneNum);
        lblPhoneNum.setBounds(103, 400, 60, 30);

        lblSSS.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblSSS.setForeground(new java.awt.Color(255, 255, 255));
        lblSSS.setText("SSS #:");
        getContentPane().add(lblSSS);
        lblSSS.setBounds(120, 450, 50, 30);

        lblPhilhealth.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblPhilhealth.setForeground(new java.awt.Color(255, 255, 255));
        lblPhilhealth.setText("Philhealth #:");
        getContentPane().add(lblPhilhealth);
        lblPhilhealth.setBounds(543, 150, 80, 30);

        lblTIN.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblTIN.setForeground(new java.awt.Color(255, 255, 255));
        lblTIN.setText("TIN #:");
        getContentPane().add(lblTIN);
        lblTIN.setBounds(582, 200, 50, 30);

        lblPagibig.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblPagibig.setForeground(new java.awt.Color(255, 255, 255));
        lblPagibig.setText("Pag-ibig #:");
        getContentPane().add(lblPagibig);
        lblPagibig.setBounds(550, 250, 80, 30);

        txtEmployeeID.setEditable(false);
        txtEmployeeID.setBackground(new java.awt.Color(255, 255, 255));
        txtEmployeeID.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtEmployeeID.setForeground(new java.awt.Color(42, 35, 35));
        getContentPane().add(txtEmployeeID);
        txtEmployeeID.setBounds(230, 150, 195, 28);

        txtLastName.setBackground(new java.awt.Color(255, 255, 255));
        txtLastName.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtLastName.setForeground(new java.awt.Color(42, 35, 35));
        getContentPane().add(txtLastName);
        txtLastName.setBounds(230, 200, 195, 28);

        txtFirstName.setBackground(new java.awt.Color(255, 255, 255));
        txtFirstName.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtFirstName.setForeground(new java.awt.Color(42, 35, 35));
        getContentPane().add(txtFirstName);
        txtFirstName.setBounds(230, 250, 195, 28);

        jDCBirthday.setBackground(new java.awt.Color(255, 255, 255));
        jDCBirthday.setForeground(new java.awt.Color(42, 35, 35));
        jDCBirthday.setDateFormatString("MM/dd/yyyy");
        jDCBirthday.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        getContentPane().add(jDCBirthday);
        jDCBirthday.setBounds(230, 300, 195, 27);

        txtAddress.setBackground(new java.awt.Color(255, 255, 255));
        txtAddress.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtAddress.setForeground(new java.awt.Color(42, 35, 35));
        getContentPane().add(txtAddress);
        txtAddress.setBounds(230, 350, 195, 28);

        txtPhoneNum.setBackground(new java.awt.Color(255, 255, 255));
        txtPhoneNum.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtPhoneNum.setForeground(new java.awt.Color(42, 35, 35));
        getContentPane().add(txtPhoneNum);
        txtPhoneNum.setBounds(230, 400, 195, 28);

        txtSSSNum.setBackground(new java.awt.Color(255, 255, 255));
        txtSSSNum.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtSSSNum.setForeground(new java.awt.Color(42, 35, 35));
        getContentPane().add(txtSSSNum);
        txtSSSNum.setBounds(230, 450, 195, 28);

        txtPhilhealthNum.setBackground(new java.awt.Color(255, 255, 255));
        txtPhilhealthNum.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtPhilhealthNum.setForeground(new java.awt.Color(42, 35, 35));
        getContentPane().add(txtPhilhealthNum);
        txtPhilhealthNum.setBounds(690, 150, 200, 28);

        txtTINNum.setBackground(new java.awt.Color(255, 255, 255));
        txtTINNum.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtTINNum.setForeground(new java.awt.Color(42, 35, 35));
        getContentPane().add(txtTINNum);
        txtTINNum.setBounds(690, 200, 200, 28);

        txtPagibigNum.setBackground(new java.awt.Color(255, 255, 255));
        txtPagibigNum.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtPagibigNum.setForeground(new java.awt.Color(42, 35, 35));
        getContentPane().add(txtPagibigNum);
        txtPagibigNum.setBounds(690, 250, 200, 28);

        lblStatus.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(255, 255, 255));
        lblStatus.setText("Status:");
        getContentPane().add(lblStatus);
        lblStatus.setBounds(576, 300, 50, 30);

        lblSupervisor.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblSupervisor.setForeground(new java.awt.Color(255, 255, 255));
        lblSupervisor.setText("Supervisor:");
        getContentPane().add(lblSupervisor);
        lblSupervisor.setBounds(549, 350, 80, 30);

        txtSupervisor.setBackground(new java.awt.Color(255, 255, 255));
        txtSupervisor.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtSupervisor.setForeground(new java.awt.Color(42, 35, 35));
        getContentPane().add(txtSupervisor);
        txtSupervisor.setBounds(690, 350, 200, 28);

        btnSave.setBackground(new java.awt.Color(255, 255, 255));
        btnSave.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSave.setForeground(new java.awt.Color(58, 50, 50));
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
        btnCancel.setForeground(new java.awt.Color(58, 50, 50));
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancel);
        btnCancel.setBounds(810, 540, 80, 28);

        cbStatus.setBackground(new java.awt.Color(255, 255, 255));
        cbStatus.setForeground(new java.awt.Color(42, 35, 35));
        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Regular", "Probationary" }));
        getContentPane().add(cbStatus);
        cbStatus.setBounds(690, 300, 200, 27);

        lblRoleID.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lblRoleID.setForeground(new java.awt.Color(255, 255, 255));
        lblRoleID.setText("Role ID:");
        getContentPane().add(lblRoleID);
        lblRoleID.setBounds(570, 400, 70, 30);

        txtRoleID.setBackground(new java.awt.Color(255, 255, 255));
        txtRoleID.setForeground(new java.awt.Color(42, 35, 35));
        getContentPane().add(txtRoleID);
        txtRoleID.setBounds(690, 400, 200, 27);

        btnRoleID.setBackground(new java.awt.Color(255, 255, 255));
        btnRoleID.setForeground(new java.awt.Color(66, 56, 56));
        btnRoleID.setText("Role ID Reference");
        btnRoleID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRoleIDActionPerformed(evt);
            }
        });
        getContentPane().add(btnRoleID);
        btnRoleID.setBounds(710, 440, 160, 20);

        jLabel1.setText("editemp");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, -40, 1010, 720);

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

        // Get the selected row index
        int selectedRowIndex = hrPageRef.getTblEmpInfo().getSelectedRow();
        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Prepare empInfo array with updated employee information
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

        // Update table in FrmHRpage with the edited employee record
        hrPageRef.updateTableRow(selectedRowIndex, empInfo);

        // Update CSV file with data from the table in FrmHRpage
        String csvFilePath = "src/main/java/files/Employee.csv";
        JTable table = hrPageRef.getTblEmpInfo(); // Assuming getTable() returns the JTable instance in FrmHRpage
        CSVFileManager.updateCSVFile(csvFilePath, table);

        // Show success message
        JOptionPane.showMessageDialog(this, "Employee record updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        // Clear text fields after saving
        clearTextFields();
    }//GEN-LAST:event_btnSaveActionPerformed
*/
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // Validate input before saving
        if (!validateInput()) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please check the fields and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the selected row index
        int selectedRowIndex = hrPageRef.getTblEmpInfo().getSelectedRow();
        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Fetch current values from the text fields
        String employeeID = txtEmployeeID.getText();
        String lastName = txtLastName.getText();
        String firstName = txtFirstName.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String birthday = dateFormat.format(jDCBirthday.getDate());
        String address = txtAddress.getText();
        String phoneNumber = txtPhoneNum.getText();
        String sssNumber = txtSSSNum.getText();
        String philhealthNumber = txtPhilhealthNum.getText();
        String tinNumber = txtTINNum.getText();
        String pagibigNumber = txtPagibigNum.getText();
        String status = (String) cbStatus.getSelectedItem();
        int roleId;
        try {
            roleId = Integer.parseInt(txtRoleID.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Role ID must be an integer.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method without saving
        }
        String supervisor = txtSupervisor.getText();

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Prepare the SQL SELECT statement to retrieve the existing employee details
            String selectQuery = "SELECT LastName, FirstName, Birthday, Address, PhoneNumber, SssNumber, PhilhealthNumber, TinNumber, PagibigNumber, Status, RoleID, ImmediateSupervisor " +
                                 "FROM employee WHERE EmployeeNum = ?";
            try (PreparedStatement selectStatement = conn.prepareStatement(selectQuery)) {
                selectStatement.setString(1, employeeID);
                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Retrieve the existing employee details
                        String existingLastName = resultSet.getString("LastName");
                        String existingFirstName = resultSet.getString("FirstName");
                        String existingBirthday = resultSet.getString("Birthday");
                        String existingAddress = resultSet.getString("Address");
                        String existingPhoneNumber = resultSet.getString("PhoneNumber");
                        String existingSssNumber = resultSet.getString("SssNumber");
                        String existingPhilhealthNumber = resultSet.getString("PhilhealthNumber");
                        String existingTinNumber = resultSet.getString("TinNumber");
                        String existingPagibigNumber = resultSet.getString("PagibigNumber");
                        String existingStatus = resultSet.getString("Status");
                        int existingRoleId = resultSet.getInt("RoleID");
                        String existingSupervisor = resultSet.getString("ImmediateSupervisor");

                        // Check if any changes have been made
                        if (lastName.equals(existingLastName) &&
                            firstName.equals(existingFirstName) &&
                            birthday.equals(existingBirthday) &&
                            address.equals(existingAddress) &&
                            phoneNumber.equals(existingPhoneNumber) &&
                            sssNumber.equals(existingSssNumber) &&
                            philhealthNumber.equals(existingPhilhealthNumber) &&
                            tinNumber.equals(existingTinNumber) &&
                            pagibigNumber.equals(existingPagibigNumber) &&
                            status.equals(existingStatus) &&
                            roleId == existingRoleId &&
                            supervisor.equals(existingSupervisor)) {

                            // No changes detected
                            JOptionPane.showMessageDialog(this, "No changes detected.", "No Changes", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                    }
                }
            }

        // Check if EmployeeNum is an integer
        int employeeNum;
        try {
            employeeNum = Integer.parseInt(txtEmployeeID.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Employee ID must be an integer.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method without saving
        }
        
    
        try {
            roleId = Integer.parseInt(txtRoleID.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Role ID must be an integer.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method without saving
        }

        // Check if RoleID exists in the role table
        boolean roleIdExists = false;
        String checkRoleQuery = "SELECT COUNT(*) AS count FROM role WHERE RoleID = ?";
        try (PreparedStatement checkRoleStatement = conn.prepareStatement(checkRoleQuery)) {
            checkRoleStatement.setInt(1, roleId);
            try (ResultSet checkRoleResult = checkRoleStatement.executeQuery()) {
                if (checkRoleResult.next()) {
                    int count = checkRoleResult.getInt("count");
                    if (count > 0) {
                        roleIdExists = true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error checking RoleID: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method without saving due to error
        }

        // If RoleID does not exist, display an error message and return
        if (!roleIdExists) {
            JOptionPane.showMessageDialog(this, "Invalid Role ID. This Role ID does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method without saving
        }

        // Retrieve the selected status from the comboBox
        String selectedStatus = (String) cbStatus.getSelectedItem();
        
        // Check if the employee with the same first name and last name already exists
        if (employeeExists(txtFirstName.getText(), txtLastName.getText(), employeeNum)) {
            JOptionPane.showMessageDialog(this, "An employee with the same first name and last name already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method without saving
        }

            // Prepare the SQL UPDATE statement
            String sql = "UPDATE employee SET LastName = ?, FirstName = ?, Birthday = ?, Address = ?, PhoneNumber = ?, SssNumber = ?, PhilhealthNumber = ?, TinNumber = ?, PagibigNumber = ?, Status = ?, RoleID = ?, ImmediateSupervisor = ? WHERE EmployeeNum = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                // Set parameters for the prepared statement
                statement.setString(1, txtLastName.getText()); // Last Name
                statement.setString(2, txtFirstName.getText()); // First Name

                String formattedDate = dateFormat.format(jDCBirthday.getDate());
                statement.setString(3, formattedDate); // Birthday

                statement.setString(4, txtAddress.getText()); // Address
                statement.setString(5, txtPhoneNum.getText()); // Phone Number
                statement.setString(6, txtSSSNum.getText()); // SSS Number
                statement.setString(7, txtPhilhealthNum.getText()); // Philhealth Number
                statement.setString(8, txtTINNum.getText()); // TIN Number
                statement.setString(9, txtPagibigNum.getText()); // Pagibig Number
                statement.setString(10, selectedStatus); // Status
                statement.setInt(11, roleId); // Role ID
                statement.setString(12, txtSupervisor.getText()); // Supervisor
                statement.setInt(13, Integer.parseInt(txtEmployeeID.getText())); // Employee ID

                // Execute the UPDATE statement
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    // Show success message
                    JOptionPane.showMessageDialog(this, "Employee record updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Update table row
                    updateTableRow(selectedRowIndex);

                    // Clear text fields after saving
                    clearTextFields();
                    
                    // Close the current frame
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update employee record.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Method to check if an employee with the same first name and last name already exists
    private boolean employeeExists(String firstName, String lastName, int currentEmployeeID) {
        boolean exists = false;
        String query = "SELECT COUNT(*) AS count FROM employee WHERE FirstName = ? AND LastName = ? AND EmployeeNum != ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setInt(3, currentEmployeeID); // Exclude the current employee being edited
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    exists = count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
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

    private void updateTableRow(int rowIndex) {
        DefaultTableModel model = (DefaultTableModel) hrPageRef.getTblEmpInfo().getModel();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Retrieve the selected status from the comboBox
        String selectedStatus = (String) cbStatus.getSelectedItem();

        // Retrieve the role and department names based on the role ID
        int roleId = Integer.parseInt(txtRoleID.getText());
        String positionName = getPositionName(roleId);
        String departmentName = getDepartmentName(roleId);

        // Update the specified row in the table model
        model.setValueAt(txtEmployeeID.getText(), rowIndex, 0);              // Employee ID
        model.setValueAt(txtLastName.getText(), rowIndex, 1);                // Last Name
        model.setValueAt(txtFirstName.getText(), rowIndex, 2);               // First Name
        model.setValueAt(dateFormat.format(jDCBirthday.getDate()), rowIndex, 3);  // Birthday
        model.setValueAt(txtAddress.getText(), rowIndex, 4);                 // Address
        model.setValueAt(txtPhoneNum.getText(), rowIndex, 5);                // Phone Number
        model.setValueAt(txtSSSNum.getText(), rowIndex, 6);                  // SSS Number
        model.setValueAt(txtPhilhealthNum.getText(), rowIndex, 7);            // Philhealth Number
        model.setValueAt(txtTINNum.getText(), rowIndex, 8);                  // TIN Number
        model.setValueAt(txtPagibigNum.getText(), rowIndex, 9);              // Pagibig Number
        model.setValueAt(selectedStatus, rowIndex, 10);                      // Status
        model.setValueAt(positionName, rowIndex, 11);                        // Position Name
        model.setValueAt(departmentName, rowIndex, 12);                      // Department Name
        model.setValueAt(txtSupervisor.getText(), rowIndex, 13);             // Supervisor
    }


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
                roleFrame.setLocationRelativeTo(this); // Center the frame relative to FrmEditEmployee
            } else {
                // Instantiate FrmRole and set it as the roleFrame
                roleFrame = new FrmRole(null, this, null); // Passing null for newEmpPage and deleteEmpPage
                roleFrame.setVisible(true);
                roleFrame.setLocationRelativeTo(this); // Center the frame relative to FrmEditEmployee

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
            java.util.logging.Logger.getLogger(FrmEditEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmEditEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmEditEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmEditEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new FrmEditEmployee().setVisible(true);
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
    private javax.swing.JLabel lblTip;
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
