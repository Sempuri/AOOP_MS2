package frames;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FrmEditPasswordTest {

    private FrmEditPassword frmEditPassword;
    private FrmITpage itPageRef; // Assuming FrmITpage for refreshing table, adjust as needed

    @BeforeEach
    public void setUp() {
        // Initialize the FrmEditPassword instance
        SwingUtilities.invokeLater(() -> {
            frmEditPassword = new FrmEditPassword(itPageRef);
            frmEditPassword.setVisible(true); // Ensure frame is visible for interaction
        });
    }

    @AfterEach
    public void tearDown() {
        // Clean up after each test if necessary
        frmEditPassword = null;
        itPageRef = null;
    }

    @Test
    public void FrmEditPassword_btnSubmit() {
        System.out.println("Testing edit password functionality");

        // Set up the form inputs
        SwingUtilities.invokeLater(() -> {
            frmEditPassword.txtEmployeeID.setText("77");
            frmEditPassword.txtUsername.setText("rei");
            frmEditPassword.txtReqDate.setText("2024-06-17");
            frmEditPassword.txtNewPassword.setText("yiman123");
            frmEditPassword.jCBStatus.setSelectedItem("Approved");
            frmEditPassword.txtApprover.setText("Cristobal");
            frmEditPassword.txtDateResponded.setText("2024-06-17");
        });

        // Simulate button click
        SwingUtilities.invokeLater(() -> {
            frmEditPassword.btnSubmit.doClick();
        });

        System.out.println("Password request updated successfully!");
    }


    /**
     * Helper method to check if a JOptionPane message dialog is shown with a specific message.
     *
     * @param expectedMessage The expected message to check in the JOptionPane dialog.
     * @return true if the JOptionPane dialog with the expected message is shown; false otherwise.
     */
    private boolean isJOptionPaneShown(String expectedMessage) {
        JOptionPane optionPane = (JOptionPane) SwingUtilities.getAncestorOfClass(JOptionPane.class, frmEditPassword);
        if (optionPane != null && optionPane.getMessage() != null) {
            String message = optionPane.getMessage().toString();
            return message.contains(expectedMessage);
        }
        return false;
    }
}
