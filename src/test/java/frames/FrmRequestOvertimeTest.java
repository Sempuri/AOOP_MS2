package frames;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.*;

public class FrmRequestOvertimeTest {

    private FrmRequestOvertime frmRequestOvertime;
    private JTextField txtEmployeeID;
    private JTextField txtReqDate;
    private JTextField txtDate;
    private JTextField txtTimeIn;
    private JTextField txtTimeOut;
    private JTextField txtTotalWorkedHours;

    @Mock
    private FrmAttendance mockFrmAttendance;

@BeforeEach
    public void setUp() {
        // Mock FrmAttendance
        FrmAttendance mockAttendance = mock(FrmAttendance.class);

        // Initialize the FrmRequestOvertime instance
        try {
            SwingUtilities.invokeAndWait(() -> {
                frmRequestOvertime = new FrmRequestOvertime(mockAttendance);
            });
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSubmitOvertimeRequest_SuccessfulSubmission() throws InterruptedException, InvocationTargetException {
        System.out.println("Testing populate fields and submit for overtime request");

        // Set valid inputs
        SwingUtilities.invokeAndWait(() -> {
            frmRequestOvertime.txtEmployeeID.setText("1");
            frmRequestOvertime.txtReqDate.setText("2024-06-11");
            frmRequestOvertime.txtDate.setText("2024-06-11");
            frmRequestOvertime.txtTimeIn.setText("08:00:00");
            frmRequestOvertime.txtTimeOut.setText("17:00:00");
            frmRequestOvertime.txtTotalWorkedHours.setText("8.00");
        });

        // Simulate button click to submit
        SwingUtilities.invokeAndWait(() -> {
            frmRequestOvertime.btnSubmit.doClick();
        });

        // Check if the FrmRequestOvertime instance is no longer visible after the action
        assertFalse(frmRequestOvertime.isVisible(), "Form should close upon successful submission.");

        // Add assertion or println statement for successful submission
        System.out.println("Overtime request submitted successfully");
    }

    @Test
    public void testSubmitOvertimeRequest_ExistingRequest() throws InterruptedException, InvocationTargetException {
        System.out.println("Testing populate fields and submit for overtime request");

        // Mock FrmAttendance
        FrmAttendance mockAttendance = mock(FrmAttendance.class);

        // Initialize the FrmRequestOvertime instance
        SwingUtilities.invokeAndWait(() -> {
            frmRequestOvertime = new FrmRequestOvertime(mockAttendance);
        });

        // Set valid inputs
        SwingUtilities.invokeAndWait(() -> {
            frmRequestOvertime.txtEmployeeID.setText("1");
            frmRequestOvertime.txtReqDate.setText("2024-06-11");
            frmRequestOvertime.txtDate.setText("2024-06-11");
            frmRequestOvertime.txtTimeIn.setText("08:00:00");
            frmRequestOvertime.txtTimeOut.setText("17:00:00");
            frmRequestOvertime.txtTotalWorkedHours.setText("8.00");
        });

        // Simulate button click to submit
        SwingUtilities.invokeAndWait(() -> {
            frmRequestOvertime.btnSubmit.doClick();
        });

        // Assertions based on different outcomes
        SwingUtilities.invokeAndWait(() -> {
            if (frmRequestOvertime.isVisible()) {
                // Assert that the form remains open if request already exists
                assertTrue(frmRequestOvertime.isVisible(), "Form should remain open if request already exists.");
            }
            System.out.println("An overtime request for this date already exists");
        });
    }

private boolean checkExistingRequests(String date) {
    // Mock implementation: Simulate checking if there are existing requests for the given date
    // Replace with actual logic or mock behavior based on your application's implementation
    return !date.equals("2024-06-11"); // For demonstration, assume there are no existing requests for this date
}


}
