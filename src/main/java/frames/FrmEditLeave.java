/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frames;

import classes.CSVFileManager;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author EMAN
 */
public class FrmEditLeave extends javax.swing.JFrame {
    
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";
    
    private FrmHRpage hrPageRef;
    /**
     * Creates new form FrmEditLeave
     */
    public FrmEditLeave(FrmHRpage hrPageRef) {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null); // Center the frame
        setPreferredSize(new Dimension(630, 720));
        
        // Set frame properties
        pack();  // Adjust size to the preferred size
    
        displayBgFromDatabase("FrmEditLeave");
        
        this.hrPageRef = hrPageRef;
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
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        String date = s.format(d);
        lblDate.setText(date);
    }

    // Method for populating textfields and jDCchooser
    public void populateFields(String[] leaveInfo) {
        txtEmployeeID.setText(leaveInfo[0]);
        txtLeaveType.setText(leaveInfo[2]);
        txtReason.setText(leaveInfo[5]);
        // Get the first name of the HR approver from the label
        String firstName = hrPageRef.getLblFName().getText();

        // Fetch the last name of the HR approver from the database
        String lastName = fetchLastNameFromDatabase(firstName);

        // Display the last name in the txtApprover field
        txtApprover.setText(lastName);
        
        txtDateResponded.setText(getLblDate().getText());

        // Set the date fields directly without parsing
        txtReqDate.setText(leaveInfo[1]); // Assuming leaveInfo[1] is in yyyy-MM-dd format
        txtStartDate.setText(leaveInfo[3]); // Assuming leaveInfo[3] is in yyyy-MM-dd format
        txtEndDate.setText(leaveInfo[4]); // Assuming leaveInfo[4] is in yyyy-MM-dd format
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


    // Method for clearing textfields
    private void clearTextFields() {
        txtEmployeeID.setText("");
        txtReqDate.setText("");
        txtLeaveType.setText("");
        txtStartDate.setText("");
        txtEndDate.setText("");
        txtReason.setText("");
        jCBStatus.setSelectedItem(null);
        txtApprover.setText("");
        txtDateResponded.setText("");
    }

    // Getter for lblDate
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
        lblRequestDate = new javax.swing.JLabel();
        lblFirstName = new javax.swing.JLabel();
        lblBirthday = new javax.swing.JLabel();
        lblAddress1 = new javax.swing.JLabel();
        lblPhoneNum = new javax.swing.JLabel();
        lblSSS = new javax.swing.JLabel();
        lblPhilhealth = new javax.swing.JLabel();
        lblTIN = new javax.swing.JLabel();
        txtEmployeeID = new javax.swing.JTextField();
        txtReqDate = new javax.swing.JTextField();
        txtLeaveType = new javax.swing.JTextField();
        txtStartDate = new javax.swing.JTextField();
        txtEndDate = new javax.swing.JTextField();
        txtReason = new javax.swing.JTextField();
        jCBStatus = new javax.swing.JComboBox<>();
        txtApprover = new javax.swing.JTextField();
        txtDateResponded = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        lblDateToday.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblDateToday.setForeground(new java.awt.Color(255, 255, 255));
        lblDateToday.setText("Current Date:");
        getContentPane().add(lblDateToday);
        lblDateToday.setBounds(190, 110, 140, 30);

        lblDate.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        lblDate.setForeground(new java.awt.Color(255, 255, 255));
        lblDate.setText("02/21/2024");
        getContentPane().add(lblDate);
        lblDate.setBounds(340, 110, 150, 30);

        lblEmployeeID.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblEmployeeID.setForeground(new java.awt.Color(255, 255, 255));
        lblEmployeeID.setText("Employee ID:");
        getContentPane().add(lblEmployeeID);
        lblEmployeeID.setBounds(155, 180, 100, 30);

        lblRequestDate.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblRequestDate.setForeground(new java.awt.Color(255, 255, 255));
        lblRequestDate.setText("Request Date:");
        getContentPane().add(lblRequestDate);
        lblRequestDate.setBounds(148, 220, 110, 30);

        lblFirstName.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblFirstName.setForeground(new java.awt.Color(255, 255, 255));
        lblFirstName.setText("Leave Type:");
        getContentPane().add(lblFirstName);
        lblFirstName.setBounds(159, 260, 90, 30);

        lblBirthday.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblBirthday.setForeground(new java.awt.Color(255, 255, 255));
        lblBirthday.setText("Start Date:");
        getContentPane().add(lblBirthday);
        lblBirthday.setBounds(167, 300, 90, 30);

        lblAddress1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblAddress1.setForeground(new java.awt.Color(255, 255, 255));
        lblAddress1.setText("End Date:");
        getContentPane().add(lblAddress1);
        lblAddress1.setBounds(169, 340, 80, 30);

        lblPhoneNum.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPhoneNum.setForeground(new java.awt.Color(255, 255, 255));
        lblPhoneNum.setText("Reason:");
        getContentPane().add(lblPhoneNum);
        lblPhoneNum.setBounds(179, 380, 70, 30);

        lblSSS.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblSSS.setForeground(new java.awt.Color(255, 255, 255));
        lblSSS.setText("Status:");
        getContentPane().add(lblSSS);
        lblSSS.setBounds(184, 480, 70, 30);

        lblPhilhealth.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPhilhealth.setForeground(new java.awt.Color(255, 255, 255));
        lblPhilhealth.setText("Approver:");
        getContentPane().add(lblPhilhealth);
        lblPhilhealth.setBounds(163, 520, 80, 30);

        lblTIN.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblTIN.setForeground(new java.awt.Color(255, 255, 255));
        lblTIN.setText("Date Responded:");
        getContentPane().add(lblTIN);
        lblTIN.setBounds(120, 560, 120, 30);

        txtEmployeeID.setEditable(false);
        txtEmployeeID.setBackground(new java.awt.Color(255, 255, 255));
        txtEmployeeID.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtEmployeeID.setForeground(new java.awt.Color(48, 37, 37));
        getContentPane().add(txtEmployeeID);
        txtEmployeeID.setBounds(290, 180, 200, 28);

        txtReqDate.setEditable(false);
        txtReqDate.setBackground(new java.awt.Color(255, 255, 255));
        txtReqDate.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtReqDate.setForeground(new java.awt.Color(48, 37, 37));
        getContentPane().add(txtReqDate);
        txtReqDate.setBounds(290, 220, 200, 28);

        txtLeaveType.setEditable(false);
        txtLeaveType.setBackground(new java.awt.Color(255, 255, 255));
        txtLeaveType.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtLeaveType.setForeground(new java.awt.Color(48, 37, 37));
        getContentPane().add(txtLeaveType);
        txtLeaveType.setBounds(290, 260, 200, 28);

        txtStartDate.setEditable(false);
        txtStartDate.setBackground(new java.awt.Color(255, 255, 255));
        txtStartDate.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtStartDate.setForeground(new java.awt.Color(48, 37, 37));
        getContentPane().add(txtStartDate);
        txtStartDate.setBounds(290, 300, 200, 28);

        txtEndDate.setEditable(false);
        txtEndDate.setBackground(new java.awt.Color(255, 255, 255));
        txtEndDate.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtEndDate.setForeground(new java.awt.Color(48, 37, 37));
        getContentPane().add(txtEndDate);
        txtEndDate.setBounds(290, 340, 200, 28);

        txtReason.setEditable(false);
        txtReason.setBackground(new java.awt.Color(255, 255, 255));
        txtReason.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtReason.setForeground(new java.awt.Color(48, 37, 37));
        getContentPane().add(txtReason);
        txtReason.setBounds(290, 380, 210, 80);

        jCBStatus.setBackground(new java.awt.Color(255, 255, 255));
        jCBStatus.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jCBStatus.setForeground(new java.awt.Color(48, 37, 37));
        jCBStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Approved", "Rejected" }));
        getContentPane().add(jCBStatus);
        jCBStatus.setBounds(290, 480, 210, 28);

        txtApprover.setEditable(false);
        txtApprover.setBackground(new java.awt.Color(255, 255, 255));
        txtApprover.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtApprover.setForeground(new java.awt.Color(48, 37, 37));
        txtApprover.setEnabled(false);
        getContentPane().add(txtApprover);
        txtApprover.setBounds(290, 520, 200, 28);

        txtDateResponded.setEditable(false);
        txtDateResponded.setBackground(new java.awt.Color(255, 255, 255));
        txtDateResponded.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtDateResponded.setForeground(new java.awt.Color(48, 37, 37));
        getContentPane().add(txtDateResponded);
        txtDateResponded.setBounds(290, 560, 200, 28);

        btnSave.setBackground(new java.awt.Color(255, 255, 255));
        btnSave.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSave.setForeground(new java.awt.Color(63, 53, 53));
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        getContentPane().add(btnSave);
        btnSave.setBounds(330, 610, 76, 28);

        btnCancel.setBackground(new java.awt.Color(255, 255, 255));
        btnCancel.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(63, 53, 53));
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancel);
        btnCancel.setBounds(420, 610, 80, 28);

        jLabel2.setText("editleave");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, -50, 640, 790);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        // Get the selected row index
        int selectedRowIndex = hrPageRef.getTblLeaveReq().getSelectedRow();
        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a leave request to respond.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Prepare leaveInfo array with updated leave information
        String[] leaveInfo = {
            txtEmployeeID.getText(),                // Employee ID
            txtReqDate.getText(),                   // Request Date
            txtLeaveType.getText(),                 // Type of Leave
            txtStartDate.getText(),                 // Start Date
            txtEndDate.getText(),                   // End Date
            txtReason.getText(),                    // Reason
            jCBStatus.getSelectedItem().toString(), // Status
            txtApprover.getText(),     // Approver from lblFName in FrmHRpage
            txtDateResponded.getText(), // Date from lblDate in FrmEditLeave
        };


        // Update table in FrmHRpage with the edited leave record
        hrPageRef.updateLeaveTableRow(selectedRowIndex, leaveInfo);

        // Update CSV file with data from the table in FrmHRpage
        String csvFilePath = "src/main/java/files/Request.csv";
        JTable table = hrPageRef.getTblLeaveReq(); // Assuming getTable() returns the JTable instance in FrmHRpage
        CSVFileManager.updateCSVFile(csvFilePath, table);

        // Show success message
        JOptionPane.showMessageDialog(this, "Selected Leave Request responded successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        // Clear text fields after saving
        clearTextFields();

    }//GEN-LAST:event_btnSaveActionPerformed
*/
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {
        // Get the selected row index
        int selectedRowIndex = hrPageRef.getTblLeaveReq().getSelectedRow();
        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a leave request to respond.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
         // Convert view row index to model row index
        int modelRowIndex = hrPageRef.getTblLeaveReq().convertRowIndexToModel(selectedRowIndex);

        String status = "";
        if (jCBStatus.getSelectedItem() != null) {
            status = jCBStatus.getSelectedItem().toString();
        } else {
            // Handle the case where no status is selected
            JOptionPane.showMessageDialog(this, "Invalid action.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if the leave request has already been responded to
        String currentStatus = getCurrentStatusOfLeaveRequest(modelRowIndex);
        if (!currentStatus.equals("Pending")) {
            JOptionPane.showMessageDialog(this, "This leave request has already been responded to.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get employee ID
        String employeeID = txtEmployeeID.getText();

        // Prepare leaveInfo array with updated leave information
        String[] leaveInfo = {
            txtEmployeeID.getText(),                // Employee ID
            txtReqDate.getText(),                   // Request Date
            txtLeaveType.getText(),                 // Type of Leave
            txtStartDate.getText(),                 // Start Date
            txtEndDate.getText(),                   // End Date
            txtReason.getText(),                    // Reason
            status,                                 // Status
            txtApprover.getText(),                  // Approver
            txtDateResponded.getText()              // Date Responded
        };

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Prepare the SQL UPDATE statement
            String sql = "UPDATE leave_request SET EmployeeNum = ?, RequestDate = ?, LeaveType = ?, StartDate = ?, EndDate = ?, Reason = ?, Status = ?, Approver = ?, DateResponded = ? WHERE EmployeeNum = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                // Set parameters for the prepared statement
                for (int i = 0; i < leaveInfo.length; i++) {
                    statement.setString(i + 1, leaveInfo[i]);
                }

                // Set EmployeeNum for the WHERE clause
                statement.setInt(10, Integer.parseInt(leaveInfo[0]));

                // Execute the UPDATE statement
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    // Update table in FrmHRpage with the edited leave record
                    hrPageRef.updateLeaveTableRow(modelRowIndex, leaveInfo);

                    // Deduct days from employee's leave credits if leave request is approved
                    if (status.equals("Approved")) {
                        // Calculate the number of days in the leave request
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date startDate = dateFormat.parse(txtStartDate.getText());
                        Date endDate = dateFormat.parse(txtEndDate.getText());
                        long diffInMillies = endDate.getTime() - startDate.getTime();
                        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1; // +1 to include both start and end date

                        // Deduct leave credits based on the number of days, excluding weekends
                        deductLeaveCredit(conn, employeeID, dateFormat.format(startDate), dateFormat.format(endDate));
                    }

                    // Show success message
                    JOptionPane.showMessageDialog(this, "Selected Leave Request responded successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Clear text fields after saving
                    clearTextFields();
                    
                    // Close the current frame
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to respond to leave request.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deductLeaveCredit(Connection conn, String employeeID, String startDateStr, String endDateStr) throws SQLException {
        try {
            // Calculate the number of days in the leave request
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);
            long diffInMillies = endDate.getTime() - startDate.getTime();
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1; // +1 to include both start and end date

            // Calculate the number of days excluding weekends
            long numberOfDays = calculateBusinessDays(startDate, endDate);

            // Get current month and year
            Calendar cal = Calendar.getInstance();
            int currentMonth = cal.get(Calendar.MONTH) + 1; // Adding 1 to adjust for zero-based indexing
            int currentYear = cal.get(Calendar.YEAR);

            // Check if employee's leave credits exist and deduct days
            String query = "SELECT LeaveCreditsRemaining, Month, Year FROM leave_credits_used WHERE EmployeeNum = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, employeeID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        double currentLeaveCreditsRemaining = resultSet.getDouble("LeaveCreditsRemaining");
                        int month = resultSet.getInt("Month");
                        int year = resultSet.getInt("Year");

                        if (currentLeaveCreditsRemaining > 0) {
                            // Deduct days from leave credits
                            double newLeaveCreditsRemaining = currentLeaveCreditsRemaining - numberOfDays;
                            if (newLeaveCreditsRemaining < 0) {
                                JOptionPane.showMessageDialog(this, "Insufficient leave credits.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            // Update the leave credits and set month and year if not set already
                            String updateQuery = "UPDATE leave_credits_used SET LeaveCreditsUsed = ?, LeaveCreditsRemaining = ?, Month = ?, Year = ? WHERE EmployeeNum = ?";
                            try (PreparedStatement updateStatement = conn.prepareStatement(updateQuery)) {
                                double newLeaveCreditsUsed = currentLeaveCreditsRemaining - newLeaveCreditsRemaining;
                                updateStatement.setDouble(1, newLeaveCreditsUsed);
                                updateStatement.setDouble(2, newLeaveCreditsRemaining);

                                // Set month and year if not set already
                                if (month == 0 && year == 0) {
                                    updateStatement.setInt(3, currentMonth);
                                    updateStatement.setInt(4, currentYear);
                                } else {
                                    updateStatement.setInt(3, month); // Retain existing month if already set
                                    updateStatement.setInt(4, year); // Retain existing year if already set
                                }

                                updateStatement.setString(5, employeeID);
                                updateStatement.executeUpdate();
                            }
                        } else {
                            // Reset leave credits for the current year (assuming default of 15.00 leave credits)
                            resetLeaveCredits(conn, employeeID, String.valueOf(currentYear));
                        }
                    } else {
                        // No entry for the employee, insert a new row with current month and year
                        insertNewLeaveCredits(conn, employeeID, currentMonth, currentYear);
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error parsing dates: " + e.getMessage(), "Date Parsing Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Method to calculate the number of business days between two dates, excluding weekends
    private long calculateBusinessDays(Date startDate, Date endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        int workDays = 0;
        while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()) {
            int dayOfWeek = startCal.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
                workDays++;
            }
            startCal.add(Calendar.DAY_OF_MONTH, 1);
        }

        return workDays;
    }

    // Method to get the current year
    private String getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    // Method to reset leave credits for the current year
    private void resetLeaveCredits(Connection conn, String employeeID, String year) throws SQLException {
        int defaultLeaveCredits = 15; // Set the default leave credits for the new year
        String updateQuery = "UPDATE leave_credits_used SET LeaveCreditsRemaining = ? WHERE EmployeeNum = ? AND Year = ?";
        try (PreparedStatement updateStatement = conn.prepareStatement(updateQuery)) {
            updateStatement.setInt(1, defaultLeaveCredits);
            updateStatement.setString(2, employeeID);
            updateStatement.setString(3, year);
            updateStatement.executeUpdate();
        }
    }

    // Method to insert new leave credits for the current year
    private void insertNewLeaveCredits(Connection conn, String employeeID, int currentMonth, int currentYear) throws SQLException {
        double defaultLeaveCredits = 15.00; // Set the default leave credits for the new year
        String insertQuery = "INSERT INTO leave_credits_used (EmployeeNum, Month, Year, LeaveCreditsUsed, LeaveCreditsRemaining) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement insertStatement = conn.prepareStatement(insertQuery)) {
            insertStatement.setString(1, employeeID);
            insertStatement.setInt(2, currentMonth); // Set current month
            insertStatement.setInt(3, currentYear); // Set current year
            insertStatement.setDouble(4, 0.00); // Set initial LeaveCreditsUsed as 0.00
            insertStatement.setDouble(5, defaultLeaveCredits); // Set initial LeaveCreditsRemaining as defaultLeaveCredits

            insertStatement.executeUpdate();
        }
    }


    // Method to retrieve the current status of the leave request
    private String getCurrentStatusOfLeaveRequest(int rowIndex) {
        DefaultTableModel model = (DefaultTableModel) hrPageRef.getTblLeaveReq().getModel();
        // Assuming the status column is at index 6 (0-indexed) in the table
        Object statusObj = model.getValueAt(rowIndex, 6);
        if (statusObj != null) {
            return statusObj.toString();
        } else {
            return ""; // Or handle null status as needed
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
            java.util.logging.Logger.getLogger(FrmEditLeave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmEditLeave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmEditLeave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmEditLeave.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new FrmEditLeave().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    public javax.swing.JButton btnSave;
    public javax.swing.JComboBox<String> jCBStatus;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblAddress1;
    private javax.swing.JLabel lblBirthday;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblDateToday;
    private javax.swing.JLabel lblEmployeeID;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblPhilhealth;
    private javax.swing.JLabel lblPhoneNum;
    private javax.swing.JLabel lblRequestDate;
    private javax.swing.JLabel lblSSS;
    private javax.swing.JLabel lblTIN;
    public javax.swing.JTextField txtApprover;
    public javax.swing.JTextField txtDateResponded;
    public javax.swing.JTextField txtEmployeeID;
    public javax.swing.JTextField txtEndDate;
    public javax.swing.JTextField txtLeaveType;
    public javax.swing.JTextField txtReason;
    public javax.swing.JTextField txtReqDate;
    public javax.swing.JTextField txtStartDate;
    // End of variables declaration//GEN-END:variables
}
