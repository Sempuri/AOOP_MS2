package frames;

import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author 
 */
public class FrmDeleteEmployeeTest {
    
    private FrmDeleteEmployee frmDeleteEmployee;
    private FrmHRpage frmHRpage;
    
    public FrmDeleteEmployeeTest() {
    }
    
    @BeforeEach
    public void setUp() {
        // Initialize the FrmHRpage instance
        frmHRpage = new FrmHRpage();
        
        // Initialize the FrmEditEmployee instance with the FrmHRpage parameter
        frmDeleteEmployee = new FrmDeleteEmployee(frmHRpage);
        
        // Initialize components
        frmDeleteEmployee.txtEmployeeID = new JTextField();
        frmDeleteEmployee.txtLastName = new JTextField();
        frmDeleteEmployee.txtFirstName = new JTextField();
        frmDeleteEmployee.jDCBirthday = new JDateChooser();
        frmDeleteEmployee.txtAddress = new JTextField();
        frmDeleteEmployee.txtPhoneNum = new JTextField();
        frmDeleteEmployee.txtSSSNum = new JTextField();
        frmDeleteEmployee.txtPhilhealthNum = new JTextField();
        frmDeleteEmployee.txtTINNum = new JTextField();
        frmDeleteEmployee.txtPagibigNum = new JTextField();
        frmDeleteEmployee.cbStatus = new JComboBox<>(new String[]{"Regular", "Probationary"});
        frmDeleteEmployee.txtRoleID = new JTextField();
        frmDeleteEmployee.txtSupervisor = new JTextField();
        
        // Mock the table model data or simulate it as needed
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("EmployeeNum");
        tableModel.addColumn("LastName");
        tableModel.addColumn("FirstName");
        tableModel.addColumn("Birthday");
        tableModel.addColumn("Address");
        tableModel.addColumn("PhoneNumber");
        tableModel.addColumn("SssNumber");
        tableModel.addColumn("PhilhealthNumber");
        tableModel.addColumn("TinNumber");
        tableModel.addColumn("PagibigNumber");
        tableModel.addColumn("Status");
        tableModel.addColumn("Position");
        tableModel.addColumn("Department");
        tableModel.addColumn("ImmediateSupervisor");

        // Add sample data to the table model (adjust as per your actual data structure)
        tableModel.addRow(new Object[]{
            90, "Cristobal", "Rei Emmanuel", "1998-11-27", "Mars", "1234567890", "123-45-6789",
            "123456789", "123456789", "123456789", "Regular", "Test", "Test", "N/A"
        });

        // Set the table model to the JTable
        frmHRpage.getTblEmpInfo().setModel(tableModel);

        // Set row selection based on available data
        if (tableModel.getRowCount() > 0) {
            frmHRpage.getTblEmpInfo().setRowSelectionInterval(0, 0); // Select the first row
        }
    }
    
    @AfterEach
    public void tearDown() {
        // Clean up if necessary
        frmDeleteEmployee = null;
        frmHRpage = null;
    }

    /**
     * Test of btnSaveActionPerformed method, of class FrmEditEmployee.
     */
    @Test
    public void testBtnSaveActionPerformed_AllFieldsValid() {
        System.out.println("Testing delete employee");

        // Set up employee details in the form fields
        frmDeleteEmployee.txtEmployeeID.setText("90");
        frmDeleteEmployee.txtLastName.setText("Cristobal");
        frmDeleteEmployee.txtFirstName.setText("Rei Emmanuel");
        frmDeleteEmployee.jDCBirthday.setDateFormatString("yyyy-MM-dd");
        ((JTextField) frmDeleteEmployee.jDCBirthday.getDateEditor().getUiComponent()).setText("1998-12-27");
        frmDeleteEmployee.txtAddress.setText("Mars");
        frmDeleteEmployee.txtPhoneNum.setText("1234567890");
        frmDeleteEmployee.txtSSSNum.setText("123-45-6789");
        frmDeleteEmployee.txtPhilhealthNum.setText("123456789");
        frmDeleteEmployee.txtTINNum.setText("123456789");
        frmDeleteEmployee.txtPagibigNum.setText("123456789");
        frmDeleteEmployee.cbStatus.setSelectedItem("Regular");
        frmDeleteEmployee.txtRoleID.setText("1");
        frmDeleteEmployee.txtSupervisor.setText("N/A");

        // Click the delete button
        SwingUtilities.invokeLater(() -> {
            frmDeleteEmployee.btnDelete.doClick();
        });

        System.out.println("Employee record deleted successfully!");
    }
}
