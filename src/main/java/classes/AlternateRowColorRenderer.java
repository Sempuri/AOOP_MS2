import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class AlternateRowColorRenderer extends DefaultTableCellRenderer {
    private static final Color LIGHT_COLOR = new Color(240, 240, 240); // Light gray
    private static final Color DARK_COLOR = new Color(220, 220, 220); // Slightly darker gray

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

       // Define row colors
        if (row % 2 == 0) {
            c.setBackground(new Color(255, 255, 255)); // Even rows (white)
        } else {
            c.setBackground(new Color(255, 255, 255)); // Odd rows (white)
        }

        // Define column colors
        if (column % 2 == 0) {
            // Even columns
            if (row % 2 == 0) {
                c.setBackground(new Color(200, 200, 255)); // Light blue for even rows in even columns
            } else {
                c.setBackground(new Color(180, 180, 255)); // Light blue for odd rows in even columns (slightly darker)
            }
        } else {
            // Odd columns
            if (row % 2 == 0) {
                c.setBackground(new Color(255, 200, 200)); // Light red for even rows in odd columns
            } else {
                c.setBackground(new Color(255, 180, 180)); // Light red for odd rows in odd columns (slightly darker)
            }
        }

        return c;
    
    }
}
