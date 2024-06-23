/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frames;
import classes.Login;
import classes.Employee;
import java.awt.Dimension;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author yen
 */
public class FrmLogin extends javax.swing.JFrame {
    
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";
    
    private Employee[] employee1;
    private javax.swing.JTable tblResetPass;
    FrmLogin _loginPage = this;
    
    private FrmRequestNewPassword requestNewPassword;
    private Connection conn; // Ensure this is properly initialized
    private boolean isRequestNewPasswordFormOpen = false; // Track form open state

    public FrmLogin(Connection connection) {
        this.conn = connection;
    }

    public FrmLogin() throws ClassNotFoundException, SQLException {
        readEmployeesFromDatabase();
        initComponents();
        setResizable(false);
        
        setPreferredSize(new Dimension(1120, 670));
   
        // Set frame properties
        pack();  // Adjust size to the preferred size
        
        displayBgFromDatabase("FrmLogin");
        
        setLocationRelativeTo(null); // Center the frame
        // Call a method to fetch and display the image from the database
        
        try {
            employee1 = readEmployeesFromDatabase();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error fetching employee data from database: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }


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

    // Method to read employees from database
    private Employee[] readEmployeesFromDatabase() throws SQLException, ClassNotFoundException {

        List<Employee> employees = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String sql = "SELECT employee.EmployeeNum, employee.FirstName, employee.LastName, role.Position AS Role, role.Department, " +
                        "employee.ImmediateSupervisor, employee.Status, employee.SssNumber, employee.PhilhealthNumber, employee.TinNumber, employee.PagibigNumber " +
                        "FROM employee " +
                        "JOIN role ON employee.RoleID = role.RoleID";

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("EmployeeNum");
                        String firstName = resultSet.getString("FirstName");
                        String lastName = resultSet.getString("LastName");
                        String role = resultSet.getString("Role"); // Use the alias name 'Role'
                        String department = resultSet.getString("Department");
                        String supervisor = resultSet.getString("ImmediateSupervisor");
                        String status = resultSet.getString("Status");
                        String sssNumber = resultSet.getString("SssNumber");
                        String philhealthNumber = resultSet.getString("PhilhealthNumber");
                        String tinNumber = resultSet.getString("TinNumber");
                        String pagibigNumber = resultSet.getString("PagibigNumber");

                        // Create Employee object and add to list
                        Employee employee = new Employee(id, firstName, lastName, role, department, supervisor, status, sssNumber, philhealthNumber, tinNumber, pagibigNumber);
                        employees.add(employee);
                    }
                }
            }
        }

        // Convert list to array
        return employees.toArray(new Employee[0]);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        btnForgotPassword = new javax.swing.JButton();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        jCBRole = new javax.swing.JComboBox<>();
        btnLogin = new javax.swing.JButton();
        CBShowPass = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(218, 226, 227));
        setMinimumSize(new java.awt.Dimension(1080, 632));
        getContentPane().setLayout(null);

        btnForgotPassword.setBackground(new java.awt.Color(255, 255, 255));
        btnForgotPassword.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btnForgotPassword.setForeground(new java.awt.Color(134, 115, 115));
        btnForgotPassword.setText("Forgot?");
        btnForgotPassword.setBorder(null);
        btnForgotPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForgotPasswordActionPerformed(evt);
            }
        });
        getContentPane().add(btnForgotPassword);
        btnForgotPassword.setBounds(833, 320, 60, 20);

        txtUsername.setBackground(new java.awt.Color(255, 255, 255));
        txtUsername.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(61, 53, 53));
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });
        getContentPane().add(txtUsername);
        txtUsername.setBounds(658, 215, 240, 37);

        txtPassword.setBackground(new java.awt.Color(255, 255, 255));
        txtPassword.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(61, 53, 53));
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });
        getContentPane().add(txtPassword);
        txtPassword.setBounds(658, 313, 240, 37);

        jCBRole.setBackground(new java.awt.Color(255, 255, 255));
        jCBRole.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jCBRole.setForeground(new java.awt.Color(61, 53, 53));
        jCBRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Employee", "HR", "Payroll", "IT" }));
        jCBRole.setMaximumSize(new java.awt.Dimension(235, 40));
        jCBRole.setMinimumSize(new java.awt.Dimension(235, 40));
        jCBRole.setPreferredSize(new java.awt.Dimension(235, 40));
        jCBRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBRoleActionPerformed(evt);
            }
        });
        getContentPane().add(jCBRole);
        jCBRole.setBounds(658, 433, 240, 40);

        btnLogin.setBackground(new java.awt.Color(255, 255, 255));
        btnLogin.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(81, 69, 69));
        btnLogin.setText("LOG IN");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        getContentPane().add(btnLogin);
        btnLogin.setBounds(658, 500, 240, 40);

        CBShowPass.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        CBShowPass.setForeground(new java.awt.Color(134, 115, 115));
        CBShowPass.setText("Show Password");
        CBShowPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBShowPassActionPerformed(evt);
            }
        });
        getContentPane().add(CBShowPass);
        CBShowPass.setBounds(658, 355, 120, 20);

        jLabel1.setText("1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, -10, 1140, 690);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    
    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        String selectedRole = (String) jCBRole.getSelectedItem();
        if (selectedRole == null || selectedRole.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a role.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String username = txtUsername.getText();
        char[] passwordChars = txtPassword.getPassword();

        if (username.isEmpty() || passwordChars.length == 0) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Instantiate the Login object with the login details
        String[] loginDetails = {username, new String(passwordChars), selectedRole};
        Login _login = new Login(loginDetails);

       try {
            if (_login.verifyLogin(username, passwordChars) && checkUserRole(_login, selectedRole)) {
                System.out.println("Login successful");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials or Role. Try Again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FrmLogin.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Database connection error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openPageBasedOnRole(String selectedRole, Login _login) throws ClassNotFoundException {
        switch (selectedRole) {
            case "Employee":
                openEmployeeInformationFrame(_login);
                break;
            case "HR":
                openHRPage(_login);
                break;
            case "Payroll":
                openPayrollPage(_login);
                break;
            case "IT":
                openITPage(_login);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Invalid Role Selection.", "Error", JOptionPane.ERROR_MESSAGE);
            break;
        }
    }

    private boolean checkUserRole(Login _login, String selectedRole) throws ClassNotFoundException {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String sql = "SELECT Role FROM login WHERE Username = ? AND Password = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, _login.getUsername());
                statement.setString(2, _login.getPassword());
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String role = resultSet.getString("Role");
                        // Allow login if the user's role matches the selected role or if the selected role is "Employee"
                        if (role.equals(selectedRole) || selectedRole.equals("Employee")) {
                            openPageBasedOnRole(selectedRole, _login);
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private void openITPage(Login _login) {
        JOptionPane.showMessageDialog(this, "Login Successful!");
        dispose();
        FrmITpage _itPage = new FrmITpage();

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Fetch employee information based on the login credentials
            String sqlEmployeeInfo = "SELECT e.EmployeeNum, e.FirstName " +
                                     "FROM employee e " +
                                     "INNER JOIN login l ON e.EmployeeNum = l.EmployeeNum " +
                                     "WHERE l.Username = ?";
            try (PreparedStatement statementEmployeeInfo = conn.prepareStatement(sqlEmployeeInfo)) {
                statementEmployeeInfo.setString(1, _login.getUsername());
                try (ResultSet resultSetEmployeeInfo = statementEmployeeInfo.executeQuery()) {
                    if (resultSetEmployeeInfo.next()) {
                        _itPage.getLblEid().setText(String.valueOf(resultSetEmployeeInfo.getInt("EmployeeNum")));
                        _itPage.getLblFName().setText(resultSetEmployeeInfo.getString("FirstName"));
                    }
                }
            }

            // Fetch password reset requests from password_request table
            String sqlPasswordRequests = "SELECT * FROM password_request";
            try (PreparedStatement statementPasswordRequests = conn.prepareStatement(sqlPasswordRequests);
                 ResultSet resultSetPasswordRequests = statementPasswordRequests.executeQuery()) {
                // Display password reset requests in tblResetPass
                _itPage.displayPasswordInfo(_itPage, resultSetPasswordRequests);
            }

            // Fetch account information from the database
            String accountSql = "SELECT * FROM login";
            try (PreparedStatement accountStatement = conn.prepareStatement(accountSql);
                 ResultSet accountResultSet = accountStatement.executeQuery()) {
                // Display account information
                displayAccountInfo(_itPage, accountResultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        _itPage.setVisible(true);
        _itPage.setLocationRelativeTo(null); // Center the frame
    }

    
    // Method to display account information in TblAccounts sorted by EmployeeNum
    private void displayAccountInfo(FrmITpage _itPage, ResultSet accountResultSet) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) _itPage.getTblAccounts().getModel();
        model.setRowCount(0); // Clear existing rows before adding new ones

        // Store the rows in a list
        List<Object[]> rows = new ArrayList<>();
        while (accountResultSet.next()) {
            int empId = accountResultSet.getInt("EmployeeNum");
            String username = accountResultSet.getString("Username");
            String password = accountResultSet.getString("Password");
            String role = accountResultSet.getString("Role");
            rows.add(new Object[]{empId, username, password, role});
        }

        // Sort the rows based on EmployeeNum
        Collections.sort(rows, new Comparator<Object[]>() {
            public int compare(Object[] row1, Object[] row2) {
                Integer empId1 = (Integer) row1[0];
                Integer empId2 = (Integer) row2[0];
                return empId1.compareTo(empId2);
            }
        });

        // Add the sorted rows to the table model
        for (Object[] row : rows) {
            model.addRow(row);
        }
    }
    
    // Method to display password reset requests in tblResetPass
    public void displayPasswordInfo(FrmITpage _itPage, ResultSet resultSet) throws SQLException {
        List<Object[]> passwordData = new ArrayList<>();

        while (resultSet.next()) {
           passwordData.add(new Object[]{
                resultSet.getInt("EmployeeNum"),
                resultSet.getString("Username"),
                resultSet.getString("RequestDate"),
                resultSet.getString("NewPassword"),
                resultSet.getString("Status"),
                resultSet.getString("Approver"),
                resultSet.getString("DateResponded")
            });
        }
        
        // Update the UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            DefaultTableModel model = (DefaultTableModel) _itPage.getTblResetPass().getModel();
            for (Object[] row : passwordData) {
                model.addRow(row);
            }
            // Refresh the table to ensure the data is displayed
            model.fireTableDataChanged();
        });
    }

    
    private void openHRPage(Login _login) {
        JOptionPane.showMessageDialog(this, "Login Successful!");
        dispose();

        // Create an instance of FrmHRpage
        FrmHRpage _hrPage = new FrmHRpage();

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Fetch employee information based on the login credentials
            String sqlEmployeeInfo = "SELECT employee.EmployeeNum, employee.FirstName FROM employee INNER JOIN login ON employee.EmployeeNum = login.EmployeeNum WHERE login.Username = ?";
            try (PreparedStatement statementEmployeeInfo = conn.prepareStatement(sqlEmployeeInfo)) {
                statementEmployeeInfo.setString(1, _login.getUsername());
                try (ResultSet resultSetEmployeeInfo = statementEmployeeInfo.executeQuery()) {
                    if (resultSetEmployeeInfo.next()) {
                        _hrPage.getLblEid().setText(String.valueOf(resultSetEmployeeInfo.getInt("EmployeeNum")));
                        _hrPage.getLblFName().setText(resultSetEmployeeInfo.getString("FirstName"));
                    }
                }
            }

            String sqlEmployees = "SELECT * FROM employee";
            try (PreparedStatement statementEmployees = conn.prepareStatement(sqlEmployees);
                 ResultSet resultSetEmployees = statementEmployees.executeQuery()) {
                displayEmployeeInfo(_hrPage, resultSetEmployees, conn);
            }

            // Fetch leave requests
            String sqlLeaveRequests = "SELECT * FROM leave_request";
            try (PreparedStatement statementLeaveRequests = conn.prepareStatement(sqlLeaveRequests);
                 ResultSet resultSetLeaveRequests = statementLeaveRequests.executeQuery()) {
                System.out.println("Fetched leave requests."); // Debug statement
                displayLeaveInfo(_hrPage, resultSetLeaveRequests);
            }

            // Fetch overtime requests
            String sqlOvertimeRequests = "SELECT * FROM overtime_request";
            try (PreparedStatement statementOvertimeRequests = conn.prepareStatement(sqlOvertimeRequests);
                 ResultSet resultSetOvertimeRequests = statementOvertimeRequests.executeQuery()) {
                System.out.println("Fetched overtime requests."); // Debug statement
                displayOvertimeInfo(_hrPage, resultSetOvertimeRequests);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        // Make the HR page visible
        SwingUtilities.invokeLater(() -> {
            _hrPage.setVisible(true);
            _hrPage.setLocationRelativeTo(null); // Center the frame
        });
    }

    private void displayEmployeeInfo(FrmHRpage _hrPage, ResultSet resultSet, Connection conn) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) _hrPage.getTblEmpInfo().getModel();
        while (resultSet.next()) {
            // Fetch additional information from the Role table
            String position = "";
            String department = "";
            int roleID = resultSet.getInt("RoleID");

            String sqlRoleInfo = "SELECT Position, Department FROM role WHERE RoleID = ?";
            try (PreparedStatement statementRoleInfo = conn.prepareStatement(sqlRoleInfo)) {
                statementRoleInfo.setInt(1, roleID);
                try (ResultSet resultSetRoleInfo = statementRoleInfo.executeQuery()) {
                    if (resultSetRoleInfo.next()) {
                        position = resultSetRoleInfo.getString("Position");
                        department = resultSetRoleInfo.getString("Department");
                    }
                }
            }

            // Add the employee information to the table
            model.addRow(new Object[]{
                resultSet.getInt("EmployeeNum"),
                resultSet.getString("LastName"),
                resultSet.getString("FirstName"),
                resultSet.getString("Birthday"),
                resultSet.getString("Address"),
                resultSet.getString("PhoneNumber"),
                resultSet.getString("SssNumber"),
                resultSet.getString("PhilhealthNumber"),
                resultSet.getString("TinNumber"),
                resultSet.getString("PagibigNumber"),
                resultSet.getString("Status"),
                position,     // Add Position fetched from Role table
                department,   // Add Department fetched from Role table
                resultSet.getString("ImmediateSupervisor")
            });
        }
    }


    private void displayLeaveInfo(FrmHRpage _hrPage, ResultSet resultSet) throws SQLException {
        List<Object[]> leaveData = new ArrayList<>();

        // Extract data from ResultSet
        while (resultSet.next()) {
            leaveData.add(new Object[]{
                resultSet.getInt("EmployeeNum"),
                resultSet.getString("RequestDate"),
                resultSet.getString("LeaveType"),
                resultSet.getString("StartDate"),
                resultSet.getString("EndDate"),
                resultSet.getString("Reason"),
                resultSet.getString("Status"),
                resultSet.getString("Approver"),
                resultSet.getString("DateResponded")
            });
        }

        // Update the UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            DefaultTableModel model = (DefaultTableModel) _hrPage.getTblLeaveReq().getModel();
            for (Object[] row : leaveData) {
                model.addRow(row);
            }
            // Refresh the table to ensure the data is displayed
            model.fireTableDataChanged();
        });
    }

    private void displayOvertimeInfo(FrmHRpage _hrPage, ResultSet resultSet) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) _hrPage.getTblOvertimeReq().getModel();
        System.out.println("Populating Overtime Info Table"); // Debug statement

        while (resultSet.next()) {

            model.addRow(new Object[]{
                resultSet.getInt("EmployeeNum"),
                resultSet.getString("RequestDate"),
                resultSet.getString("OTDate"),
                resultSet.getString("TimeIn"),
                resultSet.getString("TimeOut"),
                resultSet.getString("OTHrs"),
                resultSet.getString("Status"),
                resultSet.getString("Approver"),
                resultSet.getString("DateResponded")
            });
        }

        // Print model data for debugging
        int rowCount = model.getRowCount();
        System.out.println("Row count after adding data: " + rowCount);
        for (int i = 0; i < rowCount; i++) {
            System.out.print("Row " + i + ": ");
            for (int j = 0; j < model.getColumnCount(); j++) {
                System.out.print(model.getValueAt(i, j) + " ");
            }
            System.out.println();
        }

        // Refresh the table to ensure the data is displayed
        model.fireTableDataChanged();
    }
    
    private void openPayrollPage(Login _login) {
        JOptionPane.showMessageDialog(this, "Login Successful!");
        dispose();
        FrmPayrollpage _payrollPage = new FrmPayrollpage();

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Fetch logged-in user information
            String sqlEmployeeInfo = "SELECT e.EmployeeNum, e.LastName, e.FirstName " +
                                     "FROM employee e " +
                                     "INNER JOIN login l ON e.EmployeeNum = l.EmployeeNum " +
                                     "WHERE l.Username = ?";

            try (PreparedStatement statementEmployeeInfo = conn.prepareStatement(sqlEmployeeInfo)) {
                statementEmployeeInfo.setString(1, _login.getUsername());
                try (ResultSet resultSetEmployeeInfo = statementEmployeeInfo.executeQuery()) {
                    if (resultSetEmployeeInfo.next()) {
                        _payrollPage.getLblEid().setText(String.valueOf(resultSetEmployeeInfo.getInt("EmployeeNum")));
                        _payrollPage.getLblFName().setText(resultSetEmployeeInfo.getString("FirstName"));
                    }
                }
            }

            // Fetch all employee information including salary and allowance
            String sql = "SELECT e.EmployeeNum, e.LastName, " +
                         "r.Position, " +
                         "a.RiceSubsidy, a.PhoneAllowance, a.ClothingAllowance, " +
                         "s.BasicSalary, s.GrossSMRate, s.HourlyRate, " +
                         "a.DateUpdated, s.DateUpdated " +
                         "FROM employee e " +
                         "LEFT JOIN salary s ON e.EmployeeNum = s.EmployeeNum " +
                         "LEFT JOIN allowance a ON e.EmployeeNum = a.EmployeeNum " +
                         "LEFT JOIN role r ON e.RoleID = r.RoleID";

            try (PreparedStatement statement = conn.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                // Display all employee information
                displayPayrollInfo(_payrollPage, resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        _payrollPage.setVisible(true);
        _payrollPage.setLocationRelativeTo(null); // Center the frame
    }

    public void displayPayrollInfo(FrmPayrollpage payrollPage, ResultSet resultSet) {
        try {
            if (resultSet == null) {
                throw new SQLException("The ResultSet is null. Cannot process payroll info.");
            }

            // Process the result set
            while (resultSet.next()) {
                String employeeNum = resultSet.getString("EmployeeNum");
                String lastName = resultSet.getString("LastName");
                String position = resultSet.getString("Position");

                // Use default values for null values
                String riceSubsidy = resultSet.getString("RiceSubsidy");
                if (resultSet.wasNull()) riceSubsidy = "0.0";

                String phoneAllowance = resultSet.getString("PhoneAllowance");
                if (resultSet.wasNull()) phoneAllowance = "0.0";

                String clothingAllowance = resultSet.getString("ClothingAllowance");
                if (resultSet.wasNull()) clothingAllowance = "0.0";

                String basicSalary = resultSet.getString("BasicSalary");
                if (resultSet.wasNull()) basicSalary = "0.0";

                String grossSemiMR = resultSet.getString("GrossSMRate");
                if (resultSet.wasNull()) grossSemiMR = "0.0";

                String hourlyRate = resultSet.getString("HourlyRate");
                if (resultSet.wasNull()) hourlyRate = "0.0";

                // Get the date updated
                String dateUpdated = resultSet.getString("DateUpdated");
                if (resultSet.wasNull()) dateUpdated = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                // Assuming separate methods for adding info to each table
                payrollPage.addSalaryInfo(
                    employeeNum, lastName, position, 
                    basicSalary, grossSemiMR, hourlyRate, dateUpdated
                );

                payrollPage.addAllowanceInfo(
                    employeeNum, lastName, position,
                    riceSubsidy, phoneAllowance, clothingAllowance, 
                    dateUpdated
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }


    private void openEmployeeInformationFrame(Login _login) {
        JOptionPane.showMessageDialog(this, "Login Successful!");
        dispose();
        FrmEmployee_Information _profile = new FrmEmployee_Information();

        try (Connection con = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = con.prepareStatement(
                 "SELECT employee.EmployeeNum, employee.FirstName, employee.LastName, role.Position, role.Department, " +
                 "employee.ImmediateSupervisor, employee.Status, employee.SssNumber, employee.PhilhealthNumber, employee.TinNumber, employee.PagibigNumber " +
                 "FROM employee " +
                 "JOIN role ON employee.RoleID = role.RoleID " +
                 "JOIN login ON employee.EmployeeNum = login.EmployeeNum " +
                 "WHERE login.Username = ?"
             );
        ) {
            // Set the username as a string parameter for the query
            statement.setString(1, _login.getUsername());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                _profile.setVisible(true);
                _profile.setLocationRelativeTo(null); // Center the frame
                _profile.getLblEmpStatus().setText(resultSet.getString("Status"));
                _profile.getTxtEmployeeID().setText(String.valueOf(resultSet.getInt("EmployeeNum")));
                _profile.getTxtFirstName().setText(resultSet.getString("FirstName"));
                _profile.getTxtLastName().setText(resultSet.getString("LastName"));
                _profile.getTxtRole().setText(resultSet.getString("Position")); // Get the position from role table
                _profile.getTxtDepartment().setText(resultSet.getString("Department")); // Get the department from role table
                _profile.getTxtSupervisor().setText(resultSet.getString("ImmediateSupervisor"));
                _profile.getTxtSSS_number().setText(resultSet.getString("SssNumber"));
                _profile.getTxtPagibig_number().setText(resultSet.getString("PagibigNumber"));
                _profile.getTxtPhilhealth_number().setText(resultSet.getString("PhilhealthNumber"));
                _profile.getTxtTin_number().setText(resultSet.getString("TinNumber"));
                _profile.getLblEid().setText(String.valueOf(resultSet.getInt("EmployeeNum")));
                _profile.getLblFName().setText(resultSet.getString("FirstName"));

                // Log output for debugging
                System.out.println("SssNumber: " + resultSet.getString("SssNumber") + ", Department: " + resultSet.getString("Department") +
                    ", FirstName: " + resultSet.getString("FirstName") + ", LastName: " + resultSet.getString("LastName") +
                    ", PhilhealthNumber: " + resultSet.getString("PhilhealthNumber") + ", Position: " + resultSet.getString("Position") +
                    ", Status: " + resultSet.getString("Status") + ", Supervisor: " + resultSet.getString("ImmediateSupervisor") +
                    ", TinNumber: " + resultSet.getString("TinNumber"));
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching employee information!");
     }

    }//GEN-LAST:event_btnLoginActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void jCBRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBRoleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCBRoleActionPerformed

    private void CBShowPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBShowPassActionPerformed
        // TODO add your handling code here:
        if (CBShowPass.isSelected()) {
        // Show the password
        txtPassword.setEchoChar((char) 0);
        } else {
        // Hide the password
        txtPassword.setEchoChar('●');
        }   
    }//GEN-LAST:event_CBShowPassActionPerformed
  
    private void btnForgotPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForgotPasswordActionPerformed
       // Check if the request new password form is already open
        if (!isRequestNewPasswordFormOpen) {
            // Set the flag to indicate that the request new password form is now open
            isRequestNewPasswordFormOpen = true;

            try {
                // Create and open the request new password form
                FrmRequestNewPassword requestNewPassword = new FrmRequestNewPassword();
                requestNewPassword.setVisible(true);
                requestNewPassword.setLocationRelativeTo(null); // Center the frame

                // Add a window listener to detect when the request new password form is closed
                requestNewPassword.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        // Reset the flag when the request new password form is closed
                        isRequestNewPasswordFormOpen = false;
                    }
                });
            } catch (ClassNotFoundException | SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error initializing password request form.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
                isRequestNewPasswordFormOpen = false; // Reset the flag if an error occurs
            }
        }
    }//GEN-LAST:event_btnForgotPasswordActionPerformed

    
    // Method to fetch the employee ID from the database based on the username
    private String fetchEmployeeID(String username) {
        String employeeID = "";
        try {
            Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            String query = "SELECT EmployeeNum FROM login WHERE Username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                employeeID = rs.getString("EmployeeNum");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeID;
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
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new FrmLogin().setVisible(true);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FrmLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CBShowPass;
    private javax.swing.JButton btnForgotPassword;
    private javax.swing.JButton btnLogin;
    private javax.swing.JComboBox<String> jCBRole;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
