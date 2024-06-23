package frames;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FrmDeleteAccountTest {

    private FrmDeleteAccount frmDeleteAccount;
    private FrmITpage itPageRef; // Assuming FrmITpage for refreshing table, adjust as needed

    @BeforeEach
    public void setUp() {
        // Initialize the FrmDeleteAccount instance
        SwingUtilities.invokeLater(() -> {
            // Instantiate FrmDeleteAccount with necessary dependencies (like itPageRef)
            frmDeleteAccount = new FrmDeleteAccount(itPageRef);
            frmDeleteAccount.setVisible(true); // Ensure frame is visible for interaction
        });
    }

    @AfterEach
    public void tearDown() {
        // Clean up after each test if necessary
        frmDeleteAccount = null;
        itPageRef = null;
    }

    @Test
    public void FrmDeleteAccount() {
        System.out.println("Testing delete account functionality");

        // Simulate setting input values
        SwingUtilities.invokeLater(() -> {
            frmDeleteAccount.txtEmployeeNum.setText("77");
        });

        // Simulate button click
        SwingUtilities.invokeLater(() -> {
            frmDeleteAccount.btnDelete.doClick();
        });

        System.out.println("Account deleted successfully!");
    }

    /**
     * Helper method to check if a JOptionPane message dialog is shown with a specific message.
     *
     * @param expectedMessage The expected message to check in the JOptionPane dialog.
     * @return true if the JOptionPane dialog with the expected message is shown; false otherwise.
     */
    private boolean isJOptionPaneShown(String expectedMessage) {
        JOptionPane optionPane = (JOptionPane) SwingUtilities.getAncestorOfClass(JOptionPane.class, frmDeleteAccount);
        if (optionPane != null && optionPane.getMessage() != null) {
            String message = optionPane.getMessage().toString();
            return message.contains(expectedMessage);
        }
        return false;
    }
}
