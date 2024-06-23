/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package classes;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CSVFileManager {

    public static void updateCSVFile(String csvFilePath, JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();
        int columnCount = model.getColumnCount();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
            // Write the column headers
            for (int i = 0; i < columnCount; i++) {
                writer.write(model.getColumnName(i));
                if (i < columnCount - 1) {
                    writer.write(",");
                }
            }
            writer.newLine();

            // Write each row of data
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    Object value = model.getValueAt(i, j);
                    if (value != null) { // Perform null check
                        if (value instanceof Date) {
                            // Format date value using SimpleDateFormat
                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                            String formattedDate = dateFormat.format((Date) value);
                            writer.write(formattedDate);
                        } else {
                            writer.write(value.toString());
                        }
                    }
                    if (j < columnCount - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        }
}
/**
 *
 * @author EMAN
 */

    

