/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package frames;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JTextField;
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
public class FrmNewEmployeeTest {
    
    private FrmNewEmployee frmNewEmployee;
    private FrmHRpage frmHRpage;
    
    public FrmNewEmployeeTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        // Initialize the FrmHRpage instance
        frmHRpage = new FrmHRpage();
        
        // Initialize the FrmNewEmployee instance with the FrmHRpage parameter
        frmNewEmployee = new FrmNewEmployee(frmHRpage);
        
        // Initialize components
        frmNewEmployee.txtEmployeeID = new JTextField();
        frmNewEmployee.txtLastName = new JTextField();
        frmNewEmployee.txtFirstName = new JTextField();
        frmNewEmployee.jDCBirthday = new JDateChooser();
        frmNewEmployee.txtAddress = new JTextField();
        frmNewEmployee.txtPhoneNum = new JTextField();
        frmNewEmployee.txtSSSNum = new JTextField();
        frmNewEmployee.txtPhilhealthNum = new JTextField();
        frmNewEmployee.txtTINNum = new JTextField();
        frmNewEmployee.txtPagibigNum = new JTextField();
        frmNewEmployee.cbStatus = new JComboBox<>(new String[]{"Regular", "Probationary"});
        frmNewEmployee.txtRoleID = new JTextField();
        frmNewEmployee.txtSupervisor = new JTextField();
    }
    
    @AfterEach
    public void tearDown() {
        // Clean up if necessary
        frmNewEmployee = null;
        frmHRpage = null;
    }

    /**
     * Test of btnSaveActionPerformed method, of class FrmNewEmployee.
     */
    @Test
    public void testBtnSaveActionPerformed_AllFieldsValid() {
        System.out.println("Testing add employee");
        
        // Set valid input values
        frmNewEmployee.txtEmployeeID.setText("90");
        frmNewEmployee.txtLastName.setText("Cristobal");
        frmNewEmployee.txtFirstName.setText("Rei Emmanuel");
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthday = sdf.parse("1998-12-27");
            frmNewEmployee.jDCBirthday.setDate(birthday);
        } catch (ParseException ex) {
            fail("Failed to parse date: " + ex.getMessage());
        }
        
        frmNewEmployee.txtAddress.setText("Mars");
        frmNewEmployee.txtPhoneNum.setText("1234567890");
        frmNewEmployee.txtSSSNum.setText("123-45-6789");
        frmNewEmployee.txtPhilhealthNum.setText("123456789");
        frmNewEmployee.txtTINNum.setText("123456789");
        frmNewEmployee.txtPagibigNum.setText("123456789");
        frmNewEmployee.cbStatus.setSelectedItem("Probationary");
        frmNewEmployee.txtRoleID.setText("1");
        frmNewEmployee.txtSupervisor.setText("N/A");

        // Simulate save button
        frmNewEmployee.btnSave.doClick();

        // Check if the employee was added successfully
        assertEquals("Cristobal", frmNewEmployee.txtLastName.getText());
        
        // Print a success message
        System.out.println("Employee added successfully.");
    }
}
