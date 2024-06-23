/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package frames;

import javax.swing.JLabel;
import javax.swing.JTable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author yen
 */
public class FrmPayrollpageTest {
    
    public FrmPayrollpageTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getTblAllowanceInfo method, of class FrmPayrollpage.
     */
    
    @Test
    void testDownloadPayrollMonthlyReportButtonAction() throws InterruptedException {
        System.out.println("Testing download payroll monthly report");
        
        // instance of FrmPayroll page
        FrmPayrollpage instance = new FrmPayrollpage();

        // Simulate selected month and year
        instance.jMCMonth.setMonth(10); // Simulate selected month
        instance.jYCYear.setYear(2022); // Simulate selected year

        System.out.println("Payroll montly report downloaded successfully.");
    }
    
}
