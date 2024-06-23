/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package frames;

import static com.mysql.cj.protocol.x.XProtocolDecoder.instance;
import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author yen
 */
public class FrmSalaryTest {
    
    private FrmSalary frmSalaryCalculator;
    private JButton btnDownloadPayslip;
    
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    public FrmSalaryTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        // Initialize FrmSalaryCalculator instance
        frmSalaryCalculator = new FrmSalary();
        
        btnDownloadPayslip = new JButton(); // Create a new JButton for testing
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void testBtnViewSalaryActionPerformed() {
        System.out.println("Testing view salary button");

        String employeeId = "1"; // Replace with actual employee ID from your database
        int selectedMonth = 3; // March (1-based index)
        int selectedYear = 2022;

        boolean salaryDataFound = false;
        boolean allowanceDataFound = false;
        boolean attendanceDataFound = false;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Query to fetch salary details
            String salaryQuery = "SELECT BasicSalary, HourlyRate FROM salary WHERE EmployeeNum = ?";
            try (PreparedStatement salaryStmt = connection.prepareStatement(salaryQuery)) {
                salaryStmt.setString(1, employeeId);
                try (ResultSet salaryRs = salaryStmt.executeQuery()) {
                    if (salaryRs.next()) {
                        double monthlyRate = salaryRs.getDouble("BasicSalary");
                        double hourlyRate = salaryRs.getDouble("HourlyRate");
                        // Set values to FrmSalaryCalculator fields (simulating GUI update)
                        frmSalaryCalculator.txtMonthlyRate.setText(String.format("%.2f", monthlyRate));
                        frmSalaryCalculator.txtHourlyRate.setText(String.format("%.2f", hourlyRate));

                        salaryDataFound = true;
                    } else {
                        System.out.println("No available data for the selected month and year. Please select another month and year.");
                    }
                }
            }

            // Query to fetch allowance details
            String allowanceQuery = "SELECT RiceSubsidy, PhoneAllowance, ClothingAllowance FROM allowance WHERE EmployeeNum = ?";
            try (PreparedStatement allowanceStmt = connection.prepareStatement(allowanceQuery)) {
                allowanceStmt.setString(1, employeeId);
                try (ResultSet allowanceRs = allowanceStmt.executeQuery()) {
                    if (allowanceRs.next()) {
                        double riceSubsidy = allowanceRs.getDouble("RiceSubsidy");
                        double phoneAllowance = allowanceRs.getDouble("PhoneAllowance");
                        double clothingAllowance = allowanceRs.getDouble("ClothingAllowance");
                        // Set values to FrmSalaryCalculator fields (simulating GUI update)
                        frmSalaryCalculator.txtRiceSubsidy.setText(String.format("%.2f", riceSubsidy));
                        frmSalaryCalculator.txtPhoneAllowance.setText(String.format("%.2f", phoneAllowance));
                        frmSalaryCalculator.txtClothingAllowance.setText(String.format("%.2f", clothingAllowance));

                        allowanceDataFound = true;
                    } else {
                        System.out.println("No available data for the selected month and year. Please select another month and year.");
                    }
                }
            }

            // Query to fetch total worked hours for the selected month and year
            String attendanceQuery = "SELECT SUM(TotalWorkedHrs) AS TotalHours FROM time_tracker WHERE EmployeeNum = ? AND MONTH(Date) = ? AND YEAR(Date) = ?";
            try (PreparedStatement attendanceStmt = connection.prepareStatement(attendanceQuery)) {
                attendanceStmt.setString(1, employeeId);
                attendanceStmt.setInt(2, selectedMonth);
                attendanceStmt.setInt(3, selectedYear);
                try (ResultSet attendanceRs = attendanceStmt.executeQuery()) {
                    if (attendanceRs.next()) {
                        double totalWorkedHours = attendanceRs.getDouble("TotalHours");
                        if (totalWorkedHours > 0) { // Ensure totalWorkedHours is greater than zero
                            // Calculate gross salary based on hourly rate and total worked hours
                            double hourlyRate = Double.parseDouble(frmSalaryCalculator.txtHourlyRate.getText());
                            double grossSalary = totalWorkedHours * hourlyRate;

                            // Calculate deductions
                            double sssDeduction = calculateSSSDeduction(grossSalary); // Replace with your calculation method
                            double phealth = calculatePhilhealthDeduction(grossSalary); // Replace with your calculation method
                            double pagibig = calculatePagibigDeduction(grossSalary); // Replace with your calculation method
                            double withTax = calculateWithholdingTax(grossSalary); // Replace with your calculation method

                            // Calculate net salary
                            double netSalary = grossSalary - sssDeduction - phealth - pagibig - withTax;

                            // Set values to FrmSalaryCalculator fields (simulating GUI update)
                            frmSalaryCalculator.txtGrossSalary.setText(String.format("%.2f", grossSalary));
                            frmSalaryCalculator.txtNetSalary.setText(String.format("%.2f", netSalary));

                            System.out.println("Salary information retrieved.");

                            attendanceDataFound = true;
                        } else {
                            System.out.println("No available data for the selected month and year. Please select another month and year.");
                        }
                    } else {
                        System.out.println("No available data for total worked hours in the selected month or year.");
                    }
                }
            }

            if (!salaryDataFound && !allowanceDataFound && !attendanceDataFound) {
                System.out.println("No available data for the selected month and year.");
            }

        } catch (SQLException e) {
            fail("SQLException occurred: " + e.getMessage());
        }
    }

    
    // Methods for calculating deductions (replace with your actual calculation methods)
    private double calculateSSSDeduction(double grossSalary) {
        if (grossSalary >= 24750.0) {
            return 1125.0;
        } else {
            return grossSalary / 22.4691;
        }
    }

    private double calculatePhilhealthDeduction(double grossSalary) {
        return (grossSalary * 0.03) * 0.5;
    }

    private double calculatePagibigDeduction(double grossSalary) {
        return 100.0;
    }

    private double calculateWithholdingTax(double grossSalary) {
        double taxIncome = grossSalary - calculateTotalDeductions(grossSalary);
        if (taxIncome >= 33333.0) {
            return (taxIncome - 33333.00) * 0.25 + 2500.00;
        } else {
            return (taxIncome - 20833.00) * 0.20;
        }
    }

    private double calculateTotalDeductions(double grossSalary) {
        double sssDeduction = calculateSSSDeduction(grossSalary);
        double phealth = calculatePhilhealthDeduction(grossSalary);
        double pagibig = calculatePagibigDeduction(grossSalary);
        return sssDeduction + phealth + pagibig;
    }

    private double calculateNetSalary(double grossSalary, double sssDeduction, double phealth, double pagibig, double withTax) {
        return grossSalary - sssDeduction - phealth - pagibig - withTax;
    }
    
   
    @Test
    void testDownloadPayslipButtonAction() throws InterruptedException {
        System.out.println("Testing download payslip");
        
        // instance of FrmSalary
        FrmSalary instance = new FrmSalary();

        instance.salaryViewed = true;

        // Simulate selected month and year
        instance.jMCMonth.setMonth(10); // Simulate selected month
        instance.jYCYear.setYear(2022); // Simulate selected year

        // Simulate employee ID
        instance.lblSalEid.setText("1"); // Simulate employee ID

        System.out.println("Payslip downloaded successfully.");
    }
}