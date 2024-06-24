/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frames;

import classes.Employee;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Raya
 */
public class FrmRequest extends javax.swing.JFrame {
    private Employee[] employee1;
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";
    
    /**
     * Creates new form FrmRequest
     */
  /*  public FrmRequest() {
        initComponents();
        employee1 = Employee.readEmployee("src/main/java/files/Employee.csv");
        setResizable(false);
        showDate();
    
    } */
    public FrmRequest() {
        initComponents();
        setResizable(false);

        setPreferredSize(new Dimension(1120, 670));

        // Set frame properties
        pack();  // Adjust size to the preferred size

        displayBgFromDatabase("FrmRequest");
        
        // Get today's date
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        
        // Set min date for jDCStartDate to today
        jDCStartDate.setMinSelectableDate(today.getTime());

        // Set min date for jDCEndDate to tomorrow initially
        Calendar tomorrow = (Calendar) today.clone();
        tomorrow.add(Calendar.DAY_OF_YEAR, 1);
        jDCEndDate.setMinSelectableDate(tomorrow.getTime());

        // Add property change listener to jDCStartDate
        jDCStartDate.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    // Get the selected start date
                    Date selectedStartDate = jDCStartDate.getDate();
                    if (selectedStartDate != null) {
                        // Create a new calendar instance based on the selected start date
                        Calendar startDate = Calendar.getInstance();
                        startDate.setTime(selectedStartDate);
                        // Add one day to the start date to set as min selectable date for end date
                        startDate.add(Calendar.DAY_OF_YEAR, 1);
                        jDCEndDate.setMinSelectableDate(startDate.getTime());
                    }
                }
            }
        });

        showDate();
        displayRemainingLeaveCredits();

        // Retrieve employee data from the database
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Prepare SQL statement to retrieve employee data
            String sql = "SELECT * FROM employee";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                // Execute the SQL query
                try (ResultSet resultSet = statement.executeQuery()) {
                    // Pass the result set to the employee1 array for storage
                    employee1 = convertResultSetToEmployeeArray(resultSet);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }
    
    public JLabel getLblRemainingCredits() {
        return lblRemainingCredits;
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

    // Method to convert ResultSet to an array of Employee objects
    private Employee[] convertResultSetToEmployeeArray(ResultSet resultSet) throws SQLException {
        // Get the number of rows in the ResultSet
        int numRows = 0;
            while (resultSet.next()) {
            numRows++;
        // Process data here
    }

        // Create an array to store Employee objects
        Employee[] employees = new Employee[numRows];

        // Iterate through the ResultSet and populate the array
        int index = 0;
        while (resultSet.next()) {
            // Retrieve employee data from the ResultSet
            int employeeId = resultSet.getInt("EmployeeNum");
            String firstName = resultSet.getString("FirstName");
            String lastName = resultSet.getString("LastName");

            // Create an Employee object and add it to the array
            employees[index] = new Employee(employeeId, firstName, lastName);
            index++;
        }

        return employees;
    }

    public void setEmployeeId(String employeeId) {
       lblReqEid.setText(employeeId);
    }
    
//getter
    public JLabel getLblReqEid() {
        return lblReqEid;
    }

   
    public JLabel getLblReqFName() {    
        return lblReqFName;
    }

 
    
    //for showing of date
    public void showDate() {
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd"); // Change to ISO 8601 format
        String date = s.format(d);
        lblDate.setText(date);


        // Set the locale and date format for jDCStartDate
        jDCStartDate.setLocale(Locale.US); // Set locale to US or your desired locale
        jDCStartDate.setDateFormatString("yyyy-MM-dd");

        // Set the locale and date format for jDCEndDate
        jDCEndDate.setLocale(Locale.US); // Set locale to US or your desired locale
        jDCEndDate.setDateFormatString("yyyy-MM-dd");

    }

    // Helper method to format Date to String
    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Change to ISO 8601 format
        return date != null ? dateFormat.format(date) : "";
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings(value = "unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblFName = new javax.swing.JLabel();
        lblEid = new javax.swing.JLabel();
        salary_button = new javax.swing.JButton();
        attendance_button = new javax.swing.JButton();
        profile_button = new javax.swing.JButton();
        btnLeaveLog = new javax.swing.JButton();
        logOut_button = new javax.swing.JButton();
        lblWelcomeMsg1 = new javax.swing.JLabel();
        lblReqFName = new javax.swing.JLabel();
        lblIDniEmployee = new javax.swing.JLabel();
        lblReqEid = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        lblLeaveCredits = new javax.swing.JLabel();
        lblTypeOfLeave = new javax.swing.JLabel();
        lblRemainingCredits = new javax.swing.JLabel();
        txtLeavetype = new javax.swing.JComboBox<>();
        lblStartdate = new javax.swing.JLabel();
        jDCStartDate = new com.toedter.calendar.JDateChooser();
        lblEnddate = new javax.swing.JLabel();
        jDCEndDate = new com.toedter.calendar.JDateChooser();
        lblReasonOfRequest = new javax.swing.JLabel();
        txtReasonOfRequest = new javax.swing.JTextField();
        btnSubmit = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        lblFName.setForeground(new java.awt.Color(255, 255, 255));
        lblFName.setText("firstname");

        lblEid.setForeground(new java.awt.Color(255, 255, 255));
        lblEid.setText("EmployeeID");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        salary_button.setBackground(new java.awt.Color(255, 255, 255));
        salary_button.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        salary_button.setForeground(new java.awt.Color(93, 72, 72));
        salary_button.setText("Salary");
        salary_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salary_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(salary_button);
        salary_button.setBounds(710, 560, 173, 50);

        attendance_button.setBackground(new java.awt.Color(255, 255, 255));
        attendance_button.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        attendance_button.setForeground(new java.awt.Color(93, 72, 72));
        attendance_button.setText("Attendance");
        attendance_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attendance_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(attendance_button);
        attendance_button.setBounds(460, 560, 173, 50);

        profile_button.setBackground(new java.awt.Color(255, 255, 255));
        profile_button.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        profile_button.setForeground(new java.awt.Color(93, 72, 72));
        profile_button.setText("Profile");
        profile_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profile_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(profile_button);
        profile_button.setBounds(210, 560, 173, 50);

        btnLeaveLog.setBackground(new java.awt.Color(255, 255, 255));
        btnLeaveLog.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnLeaveLog.setForeground(new java.awt.Color(93, 72, 72));
        btnLeaveLog.setText("View Leave Request Log");
        btnLeaveLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeaveLogActionPerformed(evt);
            }
        });
        getContentPane().add(btnLeaveLog);
        btnLeaveLog.setBounds(450, 440, 180, 39);

        logOut_button.setBackground(new java.awt.Color(255, 255, 255));
        logOut_button.setFont(new java.awt.Font("Avenir Next", 1, 24)); // NOI18N
        logOut_button.setForeground(new java.awt.Color(102, 77, 77));
        logOut_button.setText("Log out");
        logOut_button.setAutoscrolls(true);
        logOut_button.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        logOut_button.setBorderPainted(false);
        logOut_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOut_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(logOut_button);
        logOut_button.setBounds(980, 30, 100, 30);

        lblWelcomeMsg1.setFont(new java.awt.Font("Verdana", 0, 25)); // NOI18N
        lblWelcomeMsg1.setForeground(new java.awt.Color(60, 45, 45));
        lblWelcomeMsg1.setText("Welcome,");
        getContentPane().add(lblWelcomeMsg1);
        lblWelcomeMsg1.setBounds(160, 20, 123, 32);

        lblReqFName.setFont(new java.awt.Font("Verdana", 1, 25)); // NOI18N
        lblReqFName.setForeground(new java.awt.Color(185, 136, 136));
        lblReqFName.setText("firstname");
        getContentPane().add(lblReqFName);
        lblReqFName.setBounds(290, 20, 320, 32);

        lblIDniEmployee.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        lblIDniEmployee.setForeground(new java.awt.Color(60, 45, 45));
        lblIDniEmployee.setText("Employee ID:");
        getContentPane().add(lblIDniEmployee);
        lblIDniEmployee.setBounds(160, 50, 91, 17);

        lblReqEid.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblReqEid.setForeground(new java.awt.Color(60, 45, 45));
        lblReqEid.setText("###");
        getContentPane().add(lblReqEid);
        lblReqEid.setBounds(260, 50, 33, 18);

        lblDate.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblDate.setForeground(new java.awt.Color(255, 255, 255));
        lblDate.setText("02/21/2024");
        getContentPane().add(lblDate);
        lblDate.setBounds(170, 460, 110, 17);

        lblLeaveCredits.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        lblLeaveCredits.setForeground(new java.awt.Color(255, 255, 255));
        lblLeaveCredits.setText("Remaining Leave Credits:");
        getContentPane().add(lblLeaveCredits);
        lblLeaveCredits.setBounds(870, 240, 150, 20);

        lblTypeOfLeave.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblTypeOfLeave.setForeground(new java.awt.Color(255, 255, 255));
        lblTypeOfLeave.setText("Type of Leave:");
        getContentPane().add(lblTypeOfLeave);
        lblTypeOfLeave.setBounds(750, 280, 120, 17);

        lblRemainingCredits.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        lblRemainingCredits.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lblRemainingCredits);
        lblRemainingCredits.setBounds(1010, 240, 60, 20);

        txtLeavetype.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtLeavetype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sick Leave", "Emergency Leave", "Vacation Leave", "Bereavement Leave", "Single Parent Leave", "Maternity Leave" }));
        txtLeavetype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLeavetypeActionPerformed(evt);
            }
        });
        getContentPane().add(txtLeavetype);
        txtLeavetype.setBounds(750, 310, 246, 26);

        lblStartdate.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblStartdate.setForeground(new java.awt.Color(255, 255, 255));
        lblStartdate.setText("Start date:");
        getContentPane().add(lblStartdate);
        lblStartdate.setBounds(450, 280, 90, 17);

        jDCStartDate.setDateFormatString("MM/dd/yyyy");
        jDCStartDate.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        getContentPane().add(jDCStartDate);
        jDCStartDate.setBounds(450, 310, 246, 27);

        lblEnddate.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblEnddate.setForeground(new java.awt.Color(255, 255, 255));
        lblEnddate.setText("End date:");
        getContentPane().add(lblEnddate);
        lblEnddate.setBounds(450, 350, 90, 17);

        jDCEndDate.setDateFormatString("MM/dd/yyyy");
        jDCEndDate.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        getContentPane().add(jDCEndDate);
        jDCEndDate.setBounds(450, 380, 246, 27);

        lblReasonOfRequest.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblReasonOfRequest.setForeground(new java.awt.Color(255, 255, 255));
        lblReasonOfRequest.setText("Reason for Request:");
        getContentPane().add(lblReasonOfRequest);
        lblReasonOfRequest.setBounds(750, 350, 160, 17);

        txtReasonOfRequest.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        getContentPane().add(txtReasonOfRequest);
        txtReasonOfRequest.setBounds(750, 380, 246, 27);

        btnSubmit.setBackground(new java.awt.Color(255, 255, 255));
        btnSubmit.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        btnSubmit.setForeground(new java.awt.Color(93, 72, 72));
        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });
        getContentPane().add(btnSubmit);
        btnSubmit.setBounds(890, 440, 106, 39);

        jLabel2.setText("req");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 0, 1120, 670);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logOut_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOut_buttonActionPerformed
        // TODO add your handling code here:
        FrmLogin logOut = null;
    try {
        logOut = new FrmLogin();
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(FrmRequest.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
        Logger.getLogger(FrmRequest.class.getName()).log(Level.SEVERE, null, ex);
    }
            logOut.show();
            logOut.setLocationRelativeTo(null); // Center the frame
            
            dispose();
    }//GEN-LAST:event_logOut_buttonActionPerformed
/*
    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:                                                                          
        // TODO add your handling code here:                                                                          
        // Get the values from text fields
        String employeeId = lblReqEid.getText();
        String date = lblDate.getText();
        String leaveType = (String) txtLeavetype.getSelectedItem();

        // Get the selected start and end dates from JDateChooser components
        Date startDate = jDCStartDate.getDate();
        Date endDate = jDCEndDate.getDate();

        // Convert dates to string format
        String startDateStr = formatDate(startDate); // Implement formatDate method to convert Date to String
        String endDateStr = formatDate(endDate);     // Implement formatDate method to convert Date to String

        String reasonOfRequest = txtReasonOfRequest.getText();

        // Check if any of the fields are empty
        if (employeeId.isEmpty() || date.isEmpty() || leaveType == null || leaveType.isEmpty() || startDate == null || endDate == null || reasonOfRequest.isEmpty()) {
            // Display an error message or handle the empty fields appropriately
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method without proceeding further
        }

        // Specify the full path to the CSV file
        String filePath = "src/main/java/files/Request.csv";

        // Save the values to a CSV file
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            // Check if the file is empty (i.e., if this is the first entry)
            File file = new File(filePath);
            if (file.length() == 0) { // If the file is empty, write the header row
                writer.println("Employee ID,Request Date,Leave Type,Start Date,End Date,Reason,Status,Approver,Date Responded");
            }

            // Get the last row index
            int lastRowIndex = getLastRowIndex(filePath);

            // Write the current entry as a new line
            writer.println(employeeId + "," + date + "," + leaveType + "," + startDateStr + "," + endDateStr + "," + reasonOfRequest + ",Pending,,");

            // Display a confirmation message
            JOptionPane.showMessageDialog(this, "Request submitted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        // Clear input fields after submission
        clearInputFields();
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
        String employeeId = lblReqEid.getText();
        String leaveType = (String) txtLeavetype.getSelectedItem();
        String reasonOfRequest = txtReasonOfRequest.getText();

        // Get the selected start and end dates from JDateChooser components
        Date startDate = jDCStartDate.getDate();
        Date endDate = jDCEndDate.getDate();

        // Validate date selection
        if (startDate == null || endDate == null) {
            JOptionPane.showMessageDialog(this, "Please select both start and end dates.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if dates are not selected
        }

        // Check if any required fields are empty
        if (employeeId.isEmpty() || leaveType == null || leaveType.isEmpty() || reasonOfRequest.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if any fields are empty
        }

        // Fetch and display remaining leave credits
        displayRemainingLeaveCredits();

        // Check remaining leave credits
        int remainingCredits = Integer.parseInt(lblRemainingCredits.getText());
        if (remainingCredits <= 0) {
            JOptionPane.showMessageDialog(this, "You have no remaining leave credits.", "Insufficient Leave Credits", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if remaining credits are 0 or less
        }

        // Calculate the number of requested leave days
        long diffInMillies = endDate.getTime() - startDate.getTime();
        long numberOfDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1; // +1 to include both start and end dates

        // Check if requested leave days exceed remaining leave credits
        if (numberOfDays > remainingCredits) {
            JOptionPane.showMessageDialog(this, "Requested leave days exceed your remaining leave credits.", "Insufficient Leave Credits", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method if requested days exceed remaining credits
        }

        // Initialize date format for MySQL
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Convert dates to string format
        String startDateStr = dateFormat.format(startDate);
        String endDateStr = dateFormat.format(endDate);
        String requestDateStr = dateFormat.format(new Date()); // Current date as request date

        // Establish a database connection and save the data
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Prepare the SQL statement
            String sql = "INSERT INTO leave_request (`EmployeeNum`, `RequestDate`, `LeaveType`, `StartDate`, `EndDate`, `Reason`, `Status`) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // Set parameter values
                pstmt.setString(1, employeeId);
                pstmt.setString(2, requestDateStr);
                pstmt.setString(3, leaveType);
                pstmt.setString(4, startDateStr);
                pstmt.setString(5, endDateStr);
                pstmt.setString(6, reasonOfRequest);
                pstmt.setString(7, "Pending");

                // Execute the SQL statement
                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Request submitted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearInputFields(); // Clear input fields after successful submission
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to submit request.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error submitting request: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to clear input fields after submission
    private void clearInputFields() {
        txtLeavetype.setSelectedIndex(-1); // Reset to no selection
        jDCStartDate.setDate(null); // Clear the date
        jDCEndDate.setDate(null); // Clear the date
        txtReasonOfRequest.setText("");
    }


    // Method to fetch and display the remaining leave credits for the employee
    public void displayRemainingLeaveCredits() {
        String employeeIDText = lblReqEid.getText(); // Get the employee ID from lblReqEid

        if (employeeIDText == null || employeeIDText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Employee ID is not available.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int employeeID;
        try {
            employeeID = Integer.parseInt(employeeIDText);
        } catch (NumberFormatException e) {
            // Return 0 or handle this case in some other way
            lblRemainingCredits.setText("0");
            return;
        }

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT LeaveCreditsRemaining FROM leave_credits_used WHERE EmployeeNum = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, employeeID);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    // Retrieve the leave credits as a double
                    double remainingCredits = resultSet.getDouble("LeaveCreditsRemaining");
                    // Convert the double to an integer
                    int remainingCreditsInt = (int) remainingCredits;
                    lblRemainingCredits.setText(String.valueOf(remainingCreditsInt));
                } else {
                    lblRemainingCredits.setText("0"); // Or handle the case where employee ID is not found
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void profile_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profile_buttonActionPerformed
         // TODO add your handling code here:
        FrmEmployee_Information _profile = new FrmEmployee_Information();
        _profile.setVisible(true);
        _profile.setLocationRelativeTo(null); // Center the frame  
    
    try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
        String query = "SELECT employee.EmployeeNum, employee.FirstName, employee.LastName, role.Position, role.Department, " +
                       "employee.ImmediateSupervisor, employee.Status, employee.SssNumber, " +
                       "employee.PhilhealthNumber, employee.TinNumber, employee.PagibigNumber " +
                       "FROM employee " +
                       "JOIN role ON employee.RoleID = role.RoleID " +
                       "WHERE employee.EmployeeNum = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(lblReqEid.getText()));
            ResultSet resultSet = stmt.executeQuery();
            
            if (resultSet.next()) {
                _profile.setVisible(true);
                _profile.setLocationRelativeTo(null); // Center the frame
                _profile.getLblEmpStatus().setText(resultSet.getString("Status"));
                _profile.getTxtEmployeeID().setText(String.valueOf(resultSet.getInt("EmployeeNum")));
                _profile.getTxtFirstName().setText(resultSet.getString("FirstName"));
                _profile.getTxtLastName().setText(resultSet.getString("LastName"));
                _profile.getTxtRole().setText(resultSet.getString("Position"));
                _profile.getTxtDepartment().setText(resultSet.getString("Department"));
                _profile.getTxtSupervisor().setText(resultSet.getString("ImmediateSupervisor"));
                _profile.getTxtSSS_number().setText(resultSet.getString("SssNumber"));
                _profile.getTxtPagibig_number().setText(resultSet.getString("PagibigNumber"));
                _profile.getTxtPhilhealth_number().setText(resultSet.getString("PhilhealthNumber"));
                _profile.getTxtTin_number().setText(resultSet.getString("TinNumber"));
                _profile.getLblEid().setText(String.valueOf(resultSet.getInt("EmployeeNum")));
                _profile.getLblFName().setText(resultSet.getString("FirstName"));
                
                

                System.out.println(resultSet.getString("LastName"));
            }
        }
    } catch (SQLException e) {
        // Handle database connection or query errors
        e.printStackTrace();
    }

    dispose();                
    }//GEN-LAST:event_profile_buttonActionPerformed
/*
    private void attendance_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attendance_buttonActionPerformed
        // TODO add your handling code here:
       // Get the logged-in employee's ID and name
            String employeeId = lblReqEid.getText();
            String employeeName = lblReqFName.getText();

            // Open the attendance frame
            FrmAttendance attendance = new FrmAttendance();
            attendance.getLblAttEid().setText(employeeId);
            attendance.getLblAttFName().setText(employeeName);
            attendance.setVisible(true);
            attendance.setLocationRelativeTo(null); // Center the frame

            // Display attendance data for the logged-in employee
            attendance.displayTimeTrackerFromCSV(employeeId);

            // Close the current frame
            dispose();
    }//GEN-LAST:event_attendance_buttonActionPerformed
*/
    private void attendance_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // Get the logged-in employee's ID and name
        int employeeId = Integer.parseInt(lblReqEid.getText());
        String employeeName = lblReqFName.getText();

        // Open the attendance frame
        FrmAttendance attendance = new FrmAttendance();
        attendance.getLblAttEid().setText(Integer.toString(employeeId));
        attendance.getLblAttFName().setText(employeeName);
        attendance.setVisible(true);
        attendance.setLocationRelativeTo(null); // Center the frame
        
        // Display attendance data for the logged-in employee
        attendance.displayTimeTrackerFromDatabase(employeeId);
        attendance.displayOldAttendanceFromDatabase(employeeId);

        // Close the current frame
        dispose();
        }               

    private void salary_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salary_buttonActionPerformed
        // TODO add your handling code here:
        FrmSalary salary = new FrmSalary();
        FrmEmployee_Information _profile = new FrmEmployee_Information();
        salary.getLblSalEid().setText(lblReqEid.getText());
        salary.getLblSalFName().setText(lblReqFName.getText());
        salary.show();
        salary.setLocationRelativeTo(null); // Center the frame
            
        dispose();
    }//GEN-LAST:event_salary_buttonActionPerformed

    private void txtLeavetypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLeavetypeActionPerformed
        // TODO add your handling code here:  
    }//GEN-LAST:event_txtLeavetypeActionPerformed

    private boolean isLeaveHistoryFormOpen = false; // Flag to track if the leave history form is open
    
    private void btnLeaveLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeaveLogActionPerformed
        // Check if the leave history form is already open
        if (!isLeaveHistoryFormOpen) {
            // Set the flag to indicate that the leave history form is now open
            isLeaveHistoryFormOpen = true;

            FrmLeaveHistory history = new FrmLeaveHistory(this);
            history.getLblReqEid().setText(lblReqEid.getText());

            history.show();
            history.setLocationRelativeTo(null); // Center the frame

            String employeeId = lblReqEid.getText();
            history.displayDataForEmployee(employeeId); // Pass the employee ID

            // Add a window listener to detect when the leave history form is closed
            history.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    // Reset the flag when the leave history form is closed
                    isLeaveHistoryFormOpen = false;
                }
            });
        }
    }//GEN-LAST:event_btnLeaveLogActionPerformed

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
            java.util.logging.Logger.getLogger(FrmRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmRequest().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton attendance_button;
    private javax.swing.JButton btnLeaveLog;
    public javax.swing.JButton btnSubmit;
    public com.toedter.calendar.JDateChooser jDCEndDate;
    public com.toedter.calendar.JDateChooser jDCStartDate;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblEid;
    private javax.swing.JLabel lblEnddate;
    private javax.swing.JLabel lblFName;
    private javax.swing.JLabel lblIDniEmployee;
    private javax.swing.JLabel lblLeaveCredits;
    private javax.swing.JLabel lblReasonOfRequest;
    public javax.swing.JLabel lblRemainingCredits;
    public javax.swing.JLabel lblReqEid;
    private javax.swing.JLabel lblReqFName;
    private javax.swing.JLabel lblStartdate;
    private javax.swing.JLabel lblTypeOfLeave;
    private javax.swing.JLabel lblWelcomeMsg1;
    private javax.swing.JButton logOut_button;
    private javax.swing.JButton profile_button;
    private javax.swing.JButton salary_button;
    public javax.swing.JComboBox<String> txtLeavetype;
    public javax.swing.JTextField txtReasonOfRequest;
    // End of variables declaration//GEN-END:variables
}
