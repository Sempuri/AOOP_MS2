package frames;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

@PrepareForTest({ FrmEditLeave.class }) // Adjust with classes that are PowerMockito prepared
@MockitoSettings(strictness = Strictness.LENIENT)
public class FrmEditLeaveTest {

    private FrmEditLeave frmEditLeave;

    @Mock
    private FrmHRpage mockHRpage;

    @BeforeEach
    public void setUp() {
        // Initialize necessary components
        PowerMockito.mockStatic(SwingUtilities.class);
        JTable mockTable = Mockito.mock(JTable.class);
        DefaultTableModel mockTableModel = Mockito.mock(DefaultTableModel.class);

        // Mock behavior for getTblLeaveReq()
        Mockito.when(mockHRpage.getTblLeaveReq()).thenReturn(mockTable);
        Mockito.when(mockTable.getSelectedRow()).thenReturn(0); // Set selected row index as needed
        Mockito.when(mockTable.getModel()).thenReturn(mockTableModel);

        try {
            // Initialize the FrmEditLeave instance on EDT
            SwingUtilities.invokeAndWait(() -> {
                frmEditLeave = new FrmEditLeave(mockHRpage);
                frmEditLeave.setVisible(true);
            });
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSubmit_EditLeave() throws InterruptedException, InvocationTargetException {
        System.out.println("Testing edit leave");

        // Simulate setting input values
        SwingUtilities.invokeAndWait(() -> {
            frmEditLeave.txtEmployeeID.setText("1");
            frmEditLeave.txtReqDate.setText("2024-06-18");
            frmEditLeave.txtLeaveType.setText("Vacation leave");
            frmEditLeave.txtStartDate.setText("2024-06-24");
            frmEditLeave.txtEndDate.setText("2024-06-28");
            frmEditLeave.txtReason.setText("Trip to Dubai");
            frmEditLeave.jCBStatus.setSelectedItem("Approved");
            frmEditLeave.txtApprover.setText("Cristobal");
            frmEditLeave.txtDateResponded.setText("2024-06-18");
        });

        // Simulate button click to submit
        SwingUtilities.invokeAndWait(() -> {
            frmEditLeave.btnSave.doClick();
        });
        
        // println statement for successful submission
        System.out.println("Leave request responded successfully");
    }
}
