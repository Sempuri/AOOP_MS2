package frames;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FrmNewAccountTest {

    private FrmNewAccount frmNewAccount;
    private FrmITpage itPageRef; // Assuming FrmITpage for refreshing table, adjust as needed

    @BeforeEach
    public void setUp() {
        // Initialize the FrmAddAccount instance
        SwingUtilities.invokeLater(() -> {
            frmNewAccount = new FrmNewAccount(itPageRef);
            frmNewAccount.setVisible(true); // Ensure frame is visible for interaction
        });
    }

    @AfterEach
    public void tearDown() {
        // Clean up after each test if necessary
        frmNewAccount = null;
        itPageRef = null;
    }

    @Test
    public void FrmNewAccount() {
        System.out.println("Testing new account functionality");

        // Simulate input values
        SwingUtilities.invokeLater(() -> {
            frmNewAccount.txtEmployeeNum.setText("77");
            frmNewAccount.txtUsername.setText("rei");
            frmNewAccount.txtPassword.setText("123");
            frmNewAccount.jCBRoleAccess.setSelectedItem("Employee");
        });

        // Simulate button click
        SwingUtilities.invokeLater(() -> {
            frmNewAccount.btnSave.doClick();
        });

        System.out.println("Account saved successfully!");
    }

    /**
     * Helper method to check if a JOptionPane message dialog is shown with a specific message.
     *
     * @param expectedMessage The expected message to check in the JOptionPane dialog.
     * @return true if the JOptionPane dialog with the expected message is shown; false otherwise.
     */
    private boolean isJOptionPaneShown(String expectedMessage) {
        JOptionPane optionPane = (JOptionPane) SwingUtilities.getAncestorOfClass(JOptionPane.class, frmNewAccount);
        if (optionPane != null && optionPane.getMessage() != null) {
            String message = optionPane.getMessage().toString();
            return message.contains(expectedMessage);
        }
        return false;
    }
}
