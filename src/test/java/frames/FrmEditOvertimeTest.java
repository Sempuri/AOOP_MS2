package frames;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;
import javax.swing.JTable;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

@PrepareForTest({ FrmEditOvertime.class }) // Adjust with classes that are PowerMockito prepared
@MockitoSettings(strictness = Strictness.LENIENT)
public class FrmEditOvertimeTest {

    private FrmEditOvertime frmEditOvertime;

    @Mock
    private FrmHRpage mockHRpage;

    @BeforeEach
    public void setUp() {
        // Initialize necessary components
        PowerMockito.mockStatic(SwingUtilities.class);
        JTable mockTable = Mockito.mock(JTable.class);
        
        // Mock behavior for getTblOvertimeReq()
        Mockito.when(mockHRpage.getTblOvertimeReq()).thenReturn(mockTable);
        Mockito.when(mockTable.getSelectedRow()).thenReturn(0); // Set selected row index as needed

        try {
            // Initialize the FrmEditOvertime instance on EDT
            SwingUtilities.invokeAndWait(() -> {
                frmEditOvertime = new FrmEditOvertime(mockHRpage);
                frmEditOvertime.setVisible(true);
            });
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSubmit_EditOvertime() throws InterruptedException, InvocationTargetException {
        System.out.println("Testing edit overtime");

        // Simulate setting input values
        SwingUtilities.invokeAndWait(() -> {
            frmEditOvertime.txtEmployeeID.setText("1");
            frmEditOvertime.txtReqDate.setText("2024-06-18");
            frmEditOvertime.txtOtDate.setText("2024-06-19");
            frmEditOvertime.txtTimeIn.setText("09:00:00");
            frmEditOvertime.txtTimeOut.setText("18:00:00");
            frmEditOvertime.txtTotalWorkedHours.setText("9");
            frmEditOvertime.jCBStatus.setSelectedItem("Approved");
            frmEditOvertime.txtApprover.setText("Cristobal");
            frmEditOvertime.txtDateResponded.setText("2024-06-18");
        });

        // Simulate button click to submit
        SwingUtilities.invokeAndWait(() -> {
            frmEditOvertime.btnSubmit.doClick();
        });

        // println statement for successful submission
        System.out.println("Overtime request responded successfully");
    }
}
