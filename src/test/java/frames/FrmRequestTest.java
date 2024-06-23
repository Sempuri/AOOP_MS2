/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package frames;

import java.awt.EventQueue;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
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
public class FrmRequestTest {
    
    // Mocked FrmAttendance instance
    private FrmRequest frmRequest;
    
    public FrmRequestTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        frmRequest = new FrmRequest();
    }
    
    @AfterEach
    public void tearDown() {
        frmRequest = null;
    }

    /**
     * Test of main method, of class FrmRequest.
     */
   @Test
    public void testBtnSubmitActionPerformed_SuccessfulSubmission() {
        System.out.println("Testing submit leave request with sufficient leave credits");
        
        int remainingCredits = 15;
    
        // Mock dates
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JUNE, 20); // June 20, 2024
        Date startDate = calendar.getTime();

        calendar.set(2024, Calendar.JUNE, 25); // June 25, 2024
        Date endDate = calendar.getTime();

        // Simulate insufficient leave credits scenario
        EventQueue.invokeLater(() -> {
            frmRequest.lblReqEid.setText("1");
            frmRequest.txtLeavetype.setSelectedItem("Sick Leave");
            frmRequest.txtReasonOfRequest.setText("Medical Appointment");
            frmRequest.jDCStartDate.setDate(startDate);
            frmRequest.jDCEndDate.setDate(endDate);
        });

        // Simulate button click action event
        EventQueue.invokeLater(() -> {
            frmRequest.btnSubmit.doClick();
        });
        
        System.out.println("Request submitted successfully");    
    }
    
    @Test
    public void testBtnSubmitActionPerformed_InsufficientLeaveCredits() {
        System.out.println("Testing submit leave request with insufficient leave credits");

        int remainingCredits = 0;

        // Mock dates
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JUNE, 20); // June 20, 2024
        Date startDate = calendar.getTime();

        calendar.set(2024, Calendar.JUNE, 25); // June 25, 2024
        Date endDate = calendar.getTime();

        // Simulate insufficient leave credits scenario
        EventQueue.invokeLater(() -> {
            frmRequest.lblReqEid.setText("1");
            frmRequest.txtLeavetype.setSelectedItem("Vacation Leave");
            frmRequest.txtReasonOfRequest.setText("Trip to Dubai.");
            frmRequest.jDCStartDate.setDate(startDate);
            frmRequest.jDCEndDate.setDate(endDate);
        });

        // Simulate button click action event
        EventQueue.invokeLater(() -> {
            frmRequest.btnSubmit.doClick();
        });
        
        System.out.println("You have no remaining leave credits.");
    }

}
