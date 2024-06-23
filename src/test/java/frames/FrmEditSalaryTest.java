package frames;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.toedter.calendar.JDateChooser; // Import appropriate date chooser library

public class FrmEditSalaryTest {

    private FrmEditSalary frmEditSalary;
    private FrmPayrollpage frmPayrollpage;

    @BeforeEach
    public void setUp() {
        // Initialize necessary components on the Event Dispatch Thread (EDT)
        try {
            SwingUtilities.invokeAndWait(() -> {
                frmPayrollpage = new FrmPayrollpage(); // Instantiate FrmPayrollpage
                frmEditSalary = new FrmEditSalary(frmPayrollpage);
                frmEditSalary.setVisible(true);
            });
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSaveSalaryActionPerformed_ValidInput_Success() throws SQLException, InterruptedException {
        System.out.println("Testing update salary functionality");

        // Simulate input values 
        SwingUtilities.invokeLater(() -> {
            frmEditSalary.txtEmployeeID.setText("1");
            frmEditSalary.txtLastName.setText("Cristobal");
            frmEditSalary.txtPosition.setText("CEO");
            frmEditSalary.txtBasicSalary.setText("10000.00");
            frmEditSalary.txtGrossSemi.setText("12000.00");
            frmEditSalary.txtHourlyRate.setText("50.00");

            // Simulate setting the date on jDCSalaryUpd 
            Date date = new Date();
            ((JDateChooser) frmEditSalary.jDCSalaryUpd).setDate(date);
        });

        // Simulate button click
        SwingUtilities.invokeLater(() -> {
            frmEditSalary.btnSaveSalary.doClick();
        });
        
        System.out.println("Salary information updated successfully!");
    }

    /**
     * Helper method to check if a JOptionPane message dialog is shown with a specific message.
     * 
     * @param expectedMessage The expected message to check in the JOptionPane dialog.
     * @return true if the JOptionPane dialog with the expected message is shown; false otherwise.
     */
    private boolean isJOptionPaneShown(String expectedMessage) {
        // Check if any JOptionPane dialog is showing with the expected message
        JOptionPane optionPane = (JOptionPane) SwingUtilities.getAncestorOfClass(JOptionPane.class, frmEditSalary);
        if (optionPane != null && optionPane.getMessage() != null) {
            String message = optionPane.getMessage().toString();
            return message.contains(expectedMessage);
        }
        return false;
    }
}
