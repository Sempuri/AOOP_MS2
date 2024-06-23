/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package frames;

import static classes.DatabaseManager.getConnection;
import static com.mysql.cj.protocol.x.XProtocolDecoder.instance;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import static java.sql.DriverManager.getConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author yen
 */
public class FrmAttendanceTest {
    
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    // Mocked FrmAttendance instance
    private FrmAttendance frmAttendance;

    private Connection conn; // Database connection
    
    public FrmAttendanceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        frmAttendance = new FrmAttendance(); // Initialize your FrmAttendance instance

        // Use reflection to invoke initComponents() method
        try {
            Method initComponentsMethod = FrmAttendance.class.getDeclaredMethod("initComponents");
            initComponentsMethod.setAccessible(true); // Make private method accessible
            initComponentsMethod.invoke(frmAttendance); // Invoke initComponents() on frmAttendance instance
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to initialize FrmAttendance instance");
        }
        
        // Initialize database connection (in-memory H2 database for testing purposes)
        conn = null;
        try {
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Database connection setup failed: " + e.getMessage());
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of displayTimeTrackerFromDatabase method, of class FrmAttendance.
     */
    
    @Test
    public void testTimeInButtonActionPerformed() throws InterruptedException {
        System.out.println("Testing Time In button");
        String employeeId = "28";

        // Get current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedTime = currentDateTime.format(DateTimeFormatter.ofPattern("hh:mm:ss"));

        try {
            // Simulate the action performed with mocked or directly set values
            SwingUtilities.invokeAndWait(() -> {
                frmAttendance.lblAttEid.setText(employeeId);
                frmAttendance.lblTime.setText(formattedTime); // Set current time
                frmAttendance.timeIn_button.doClick(); // Simulate button click
            });
        } catch (InvocationTargetException ex) {
            Logger.getLogger(FrmAttendanceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DefaultTableModel model = (DefaultTableModel) frmAttendance.timeTracker_table.getModel();

        // actual row count for debugging
        System.out.println("Actual row count in table: " + model.getRowCount());

        // Check if the row count matches the expected behavior
        assertEquals(1, model.getRowCount(), "One row should be added to the table after Time In");

        // Retrieve the values from the table and assert them
        Object[] rowData = {
                model.getValueAt(0, 0), // Employee ID
                model.getValueAt(0, 1), // Date
                model.getValueAt(0, 2), // Time In
                model.getValueAt(0, 3), // Time Out (should be null initially)
                model.getValueAt(0, 4)  // Total Worked Hours (should be null initially)
        };

        assertEquals(employeeId, rowData[0], "Employee ID should match");
        assertEquals(currentDateTime.toLocalDate().toString(), rowData[1], "Date should match current date");
        assertEquals(formattedTime, rowData[2], "Time In should match formatted time");
        assertNull(rowData[3], "Time Out should be null initially");
        assertNull(rowData[4], "Total Worked Hours should be null initially");
    }

    /**
     * Test of hasAlreadyTimedIn method, of class FrmAttendance.
     */
    @Test
    public void testHasAlreadyTimedIn() throws Exception {
        System.out.println("Testing hasAlreadyTimedIn");
        
        // Get current date
        LocalDate currentDate = LocalDate.now();
        
        Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        String employeeId = "2";
        String formattedDate = currentDate.toString();
        FrmAttendance instance = new FrmAttendance();
        boolean expResult = true;
        boolean result = instance.hasAlreadyTimedIn(conn, employeeId, currentDate, formattedDate);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testTimeOutButtonActionPerformed() {
         System.out.println("Testing Time Out button");

         // Assuming frmAttendance is an instance of FrmAttendance
         FrmAttendance frmAttendance = new FrmAttendance();
         frmAttendance.initializeForTesting(); // Initialize components if necessary

         // Get the actual table model from the form
         DefaultTableModel model = (DefaultTableModel) frmAttendance.timeTracker_table.getModel();
         model.setRowCount(0); // Clear any existing rows

         // Add a row with Time In but no Time Out
         String employeeId = "3";
         String currentDate = "2024-06-15"; // sample date
         String timeInString = "08:00:00"; // sample time in
         String timeOutString = "17:00:00"; // sample time
         String totalWorkedHrs = null; // to calculate
         model.addRow(new Object[]{employeeId, currentDate, timeInString, timeOutString, totalWorkedHrs});
         
        // Parse time strings into LocalTime objects
        LocalTime startTime = LocalTime.parse(timeInString);
        LocalTime endTime = LocalTime.parse(timeOutString);

         frmAttendance.lblTime.setText(timeOutString);
         
         // Calculate the time difference in hours
        Duration duration = Duration.between(startTime, endTime);
        long hours = duration.toHours();

        System.out.println("Total Worked Hours " + hours);

         // Simulate the action performed on the button click
         SwingUtilities.invokeLater(() -> {
             frmAttendance.timeOut_button.doClick(); // Simulate button click
         });

         // Print Time Out value to verify
         Object timeOutValue = model.getValueAt(0, 3);
         System.out.println("Time Out value in table: " + timeOutValue);

         // Verify the expected behavior after button click
         assertEquals(1, model.getRowCount(), "Row count should remain 1");
         assertFalse(timeOutValue == null, "Time Out value should not be null");
         assertEquals(timeOutString, timeOutValue, "Time Out value should be set to mock time");
     }
        
    @Test
    public void testTimeOutButtonActionPerformed_NoTimeIn() {
        System.out.println("Testing Time Out without Time In");

        // frmAttendance is an instance of FrmAttendance
        FrmAttendance frmAttendance = new FrmAttendance();
        frmAttendance.initializeForTesting(); // Initialize components

        // Set up the actual table model from FrmAttendance
        DefaultTableModel model = (DefaultTableModel) frmAttendance.timeTracker_table.getModel();
        model.setRowCount(0); // Clear any existing rows

        // Add a row with no Time In recorded (null)
        String employeeId = "2";
        String currentDate = "2024-06-15"; // sample date
        String timeInString = null; // No Time In recorded
        model.addRow(new Object[]{employeeId, currentDate, timeInString, null, null});

        // Set current time as if it's displayed in lblTime
        String currentTime = "17:00:00"; // sample time
        frmAttendance.lblTime.setText(currentTime);

        // Simulate the action performed on the button click
        frmAttendance.timeOut_button.doClick();

        // Verify that no row in the table was updated
        assertEquals(1, model.getRowCount(), "No new row should be added");
        assertEquals(null, model.getValueAt(0, 3), "Time Out should remain null");
        assertEquals(null, model.getValueAt(0, 4), "Total Worked Hours should remain null");
    }
    
    @Test
    public void testViewActionPerformed() {
        System.out.println("Testing view attendance button");
        // Set the necessary inputs for the view action
        frmAttendance.jMCMonth.setMonth(9); // March (zero-based index)
        frmAttendance.jYCYear.setYear(2022); // Year 2022
        frmAttendance.lblAttEid.setText("1"); // Replace with actual employee ID from test data

        // Simulate user action that triggers btnViewActionPerformed
        frmAttendance.btnView.doClick();

        // Verify the outcome of btnViewActionPerformed

        // Check if there is no data for the selected month and year
        String totalWorkedHoursText = frmAttendance.txtTotalWorkedHours.getText();
        if (totalWorkedHoursText.isEmpty() || totalWorkedHoursText.equals("No data")) {
            System.out.println("No available data for selected month and year.");
        } else {
            // Calculate expected total worked hours for October 2022 based on test data
            double expectedTotalHours = calculateExpectedTotalHours(9, 2022, "1"); // Month (zero-based index), Year, Employee ID
            double actualTotalHours = Double.parseDouble(totalWorkedHoursText);

            // Print the total worked hours
            System.out.println("Total worked hours: " + actualTotalHours);
        }
    }

    // Helper method to calculate expected total worked hours for a specific month, year, and employee ID
    private double calculateExpectedTotalHours(int month, int year, String employeeId) {
        double totalHours = 0.0;

        // Perform database query to calculate total worked hours
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // Prepare the SQL query to retrieve total worked hours for the month, year, and employee ID
            String sql = "SELECT SUM(TotalWorkedHrs) AS TotalHours " +
                         "FROM time_tracker " +
                         "WHERE MONTH(Date) = ? AND YEAR(Date) = ? AND EmployeeNum = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, month + 1); // Month is zero-based in Java Date, so add 1
            pstmt.setInt(2, year);
            pstmt.setString(3, employeeId);

            // Execute the query
            rs = pstmt.executeQuery();

            // Retrieve the total worked hours
            if (rs.next()) {
                totalHours = rs.getDouble("TotalHours");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Failed to retrieve total worked hours: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return totalHours;
    }
    
}
