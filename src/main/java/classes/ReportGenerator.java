import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class ReportGenerator {

    // Database connection details
    private static final String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final String USERNAME = "sql12713427";
    private static final String PASSWORD = "7l87SNw2yD";

    // Method to generate report dynamically
    public void generateReport(String templateName, Map<String, Object> parameters) {
        String fetchTemplateQuery = "SELECT TemplateContent FROM report_templates WHERE TemplateName = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(fetchTemplateQuery)) {

            stmt.setString(1, templateName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String jrxmlContent = rs.getString("TemplateContent");

                    // Compile JRXML content to JasperReport
                    JasperReport jasperReport = JasperCompileManager.compileReport(new ByteArrayInputStream(jrxmlContent.getBytes()));

                    // Generate the report
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

                    // Display or export the report (you can customize this part)
                    JOptionPane.showMessageDialog(null, "Report generated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Template not found in database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException | JRException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error generating report: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
/*
    public static void main(String[] args) {
        DynamicReportGenerator reportGenerator = new DynamicReportGenerator();

        // Example parameters for the report
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("param1", value1); // Replace with actual parameters
        parameters.put("param2", value2);

        // Example template name (adjust as per your database records)
        String templateName = "your_template_name";

        // Generate the report
        reportGenerator.generateReport(templateName, parameters);
    } */
}