package frames;

import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author 
 */
public class FrmEditEmployeeTest {
    
    private FrmEditEmployee frmEditEmployee;
    private FrmHRpage frmHRpage;
    
    public FrmEditEmployeeTest() {
    }
    
    @BeforeEach
    public void setUp() {
        // Initialize the FrmHRpage instance
        frmHRpage = new FrmHRpage();
        
        // Initialize the FrmEditEmployee instance with the FrmHRpage parameter
        frmEditEmployee = new FrmEditEmployee(frmHRpage);
        
        // Initialize components
        frmEditEmployee.txtEmployeeID = new JTextField();
        frmEditEmployee.txtLastName = new JTextField();
        frmEditEmployee.txtFirstName = new JTextField();
        frmEditEmployee.jDCBirthday = new JDateChooser();
        frmEditEmployee.txtAddress = new JTextField();
        frmEditEmployee.txtPhoneNum = new JTextField();
        frmEditEmployee.txtSSSNum = new JTextField();
        frmEditEmployee.txtPhilhealthNum = new JTextField();
        frmEditEmployee.txtTINNum = new JTextField();
        frmEditEmployee.txtPagibigNum = new JTextField();
        frmEditEmployee.cbStatus = new JComboBox<>(new String[]{"Regular", "Probationary"});
        frmEditEmployee.txtRoleID = new JTextField();
        frmEditEmployee.txtSupervisor = new JTextField();
        
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
            "123456789", "123456789", "123456789", "Regular", "Manager", "IT", "N/A"
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
        frmEditEmployee = null;
        frmHRpage = null;
    }

    /**
     * Test of btnSaveActionPerformed method, of class FrmEditEmployee.
     */
    @Test
    public void testBtnSaveActionPerformed_AllFieldsValid() {
        System.out.println("Testing edit employee");

        frmEditEmployee.txtEmployeeID.setText("90");
        frmEditEmployee.txtLastName.setText("Cristobal");
        frmEditEmployee.txtFirstName.setText("Sempuri");
        frmEditEmployee.jDCBirthday.setDateFormatString("yyyy-MM-dd");
        ((JTextField) frmEditEmployee.jDCBirthday.getDateEditor().getUiComponent()).setText("1998-12-27");
        frmEditEmployee.txtAddress.setText("Mars");
        frmEditEmployee.txtPhoneNum.setText("9876543210");
        frmEditEmployee.txtSSSNum.setText("987-65-4321");
        frmEditEmployee.txtPhilhealthNum.setText("987654321");
        frmEditEmployee.txtTINNum.setText("987654321");
        frmEditEmployee.txtPagibigNum.setText("987654321");
        frmEditEmployee.cbStatus.setSelectedItem("Probationary");
        frmEditEmployee.txtRoleID.setText("1");
        frmEditEmployee.txtSupervisor.setText("N/A");

        // Simulate save button
        frmEditEmployee.btnSave.doClick();

        DefaultTableModel tableModel = (DefaultTableModel) frmHRpage.getTblEmpInfo().getModel();
        assertEquals("Cristobal", tableModel.getValueAt(0, 1)); 

        // Print a message if the update was successful
        System.out.println("Employee information updated successfully!");
    }


}
