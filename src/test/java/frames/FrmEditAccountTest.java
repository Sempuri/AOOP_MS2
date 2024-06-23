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

public class FrmEditAccountTest {

    private FrmEditAccount frmEditAccount;
    private FrmITpage itPageRef; // Assuming FrmITpage for refreshing table, adjust as needed

    @BeforeEach
    public void setUp() {
        // Initialize the FrmEditAccount instance
        SwingUtilities.invokeLater(() -> {
            // Instantiate FrmEditAccount with necessary dependencies (like itPageRef)
            frmEditAccount = new FrmEditAccount(itPageRef);
            frmEditAccount.setVisible(true); // Ensure frame is visible for interaction
        });
    }

    @AfterEach
    public void tearDown() {
        // Clean up after each test if necessary
        frmEditAccount = null;
        itPageRef = null;
    }

    @Test
    public void FrmEditAccount() {
        System.out.println("Testing edit account functionality");

        // Simulate input values
        SwingUtilities.invokeLater(() -> {
           
            frmEditAccount.txtEmployeeNum.setText("77");
            frmEditAccount.txtUsername.setText("rei");
            frmEditAccount.txtPassword.setText("321");
            frmEditAccount.jCBRoleAccess.setSelectedItem("Employee");
        });

        // Simulate button click 
        SwingUtilities.invokeLater(() -> {
            frmEditAccount.btnSave.doClick();
        });

        System.out.println("Account updated successfully!");
    }

    /**
     * Helper method to check if a JOptionPane message dialog is shown with a specific message.
     *
     * @param expectedMessage The expected message to check in the JOptionPane dialog.
     * @return true if the JOptionPane dialog with the expected message is shown; false otherwise.
     */
    private boolean isJOptionPaneShown(String expectedMessage) {
        JOptionPane optionPane = (JOptionPane) SwingUtilities.getAncestorOfClass(JOptionPane.class, frmEditAccount);
        if (optionPane != null && optionPane.getMessage() != null) {
            String message = optionPane.getMessage().toString();
            return message.contains(expectedMessage);
        }
        return false;
    }
}
