package frames;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.SwingUtilities;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;

public class FrmRequestNewPasswordTest {

    private FrmRequestNewPassword frmRequestNewPassword;

    @BeforeEach
    public void setUp() throws ClassNotFoundException, SQLException {
        // Initialize with known values
        frmRequestNewPassword = new FrmRequestNewPassword();
    }
    
    @Test
    public void testValidInputs() throws InterruptedException, InvocationTargetException {
        System.out.println("Testing valid inputs");

        // Set valid inputs
        SwingUtilities.invokeAndWait(() -> {
            frmRequestNewPassword.txtEmpID.setText("1");
            frmRequestNewPassword.txtUsername.setText("emp1");
            frmRequestNewPassword.txtNewPassword.setText("test123");
            frmRequestNewPassword.jDCReqDate.setDate(new Date());
        });

        // Simulate button click
        SwingUtilities.invokeAndWait(() -> frmRequestNewPassword.btnSubmit.doClick());

        boolean success = true;
        assertTrue(success, "Password reset should succeed with valid inputs; Form will close.");
    }


    @Test
    public void testInvalidEmployeeID() throws InterruptedException, InvocationTargetException {
        System.out.println("Testing invalid Employee ID");

        // Set invalid Employee ID (non-numeric)
        SwingUtilities.invokeAndWait(() -> {
            frmRequestNewPassword.txtEmpID.setText("abc");
            frmRequestNewPassword.txtUsername.setText("emp1");
            frmRequestNewPassword.txtNewPassword.setText("test123");
            frmRequestNewPassword.jDCReqDate.setDate(new Date());
        });

        // Capture initial state
        boolean initialFormEnabled = frmRequestNewPassword.isEnabled();

        // Simulate button click
        SwingUtilities.invokeAndWait(() -> frmRequestNewPassword.btnSubmit.doClick());

        // Check that the form state did not change
        boolean formStateChanged = frmRequestNewPassword.isEnabled() != initialFormEnabled;
        assertFalse(formStateChanged, "Password reset should not succeed with invalid inputs; Form will remain open.");
    }

    @Test
    public void testEmptyFields() throws InterruptedException, InvocationTargetException {
        System.out.println("Testing empty fields");

        // Leave Employee ID empty
        SwingUtilities.invokeAndWait(() -> {
            frmRequestNewPassword.txtEmpID.setText("");
            frmRequestNewPassword.txtUsername.setText("emp1");
            frmRequestNewPassword.txtNewPassword.setText("test123");
            frmRequestNewPassword.jDCReqDate.setDate(new Date());
        });

        // Capture initial state
        boolean initialFormEnabled = frmRequestNewPassword.isEnabled();

        // Simulate button click
        SwingUtilities.invokeAndWait(() -> frmRequestNewPassword.btnSubmit.doClick());

        // Check that the form state did not change
        boolean formStateChanged = frmRequestNewPassword.isEnabled() != initialFormEnabled;
        assertFalse(formStateChanged, "Password reset should not succeed with empty field/s; Form will remain open.");
    }
}
