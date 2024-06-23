/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author EMAN
 */
public class FrmOvertimeHistory extends javax.swing.JFrame {
    
    private FrmAttendance attendancePageRef;
    
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";
    
    /**
     * Creates new form FrmOvertimeHistory
     */
    public FrmOvertimeHistory(FrmAttendance attendancePageRef) {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null); // Center the frame
        setPreferredSize(new Dimension(828, 449));
        
        // Set frame properties
        pack();  // Adjust size to the preferred size
        
        displayBgFromDatabase("FrmOvertimeHistory");
        
        setAlternateRowAndColumnColorRenderer(tblOvertimeReqHistory);
        
        this.attendancePageRef = attendancePageRef;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    // Method to fetch and display image from the database
    private void displayBgFromDatabase(String frameName) {
        try (Connection conn = DriverManager.getConnection(imgJDBC_URL, imgUSERNAME, imgPASSWORD)) {
            // Prepare SQL statement to retrieve image data from the database
            String SELECT_IMAGE = "SELECT Img FROM Image WHERE Frame = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(SELECT_IMAGE)) {
                // Set the frame name as a parameter for the prepared statement
                pstmt.setString(1, frameName);

                // Execute the SELECT statement
                ResultSet resultSet = pstmt.executeQuery();

                // Check if a result was found
                if (resultSet.next()) {
                    // Retrieve the image data as a byte array
                    byte[] imgByteArray = resultSet.getBytes("Img");

                    // Convert the byte array to an ImageIcon
                    ImageIcon icon = new ImageIcon(imgByteArray);

                    // Set the ImageIcon as the icon for the JLabel
                    jLabel1.setIcon(icon);
                } else {
                    System.out.println("Image not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }   
    
    //getter
    public JTable getTblOvertimeReqHistory() {
        return tblOvertimeReqHistory;
    }

    public JLabel getLblReqEid() {
        return lblReqEid;
    }

    private void setAlternateRowAndColumnColorRenderer(JTable table) {
        // Ensure that the custom renderer is applied to all columns
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(new AlternateRowAndColumnColorRenderer());
        }
    }

    // Inner class for custom cell renderer
    private static class AlternateRowAndColumnColorRenderer extends DefaultTableCellRenderer {

        private static final Color EVEN_COLUMN_COLOR = new Color(250, 241, 225);
        private static final Color ODD_COLUMN_COLOR = new Color(255, 255, 255); 

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Determine base color based on column
            Color baseColor = (column % 2 == 0) ? EVEN_COLUMN_COLOR : ODD_COLUMN_COLOR;

            // Adjust color based on row
            if (row % 2 != 0) {
                // Make the row color slightly darker
                baseColor = new Color(
                    Math.max(baseColor.getRed() - 15, 0),
                    Math.max(baseColor.getGreen() - 15, 0),
                    Math.max(baseColor.getBlue() - 10, 0)
                );
            }

            c.setBackground(baseColor);

            return c;
        }
    }
    
    //for creation of new row into the overtime history table
    public void updateTable(String[] overtimehistoryInfo) {
        DefaultTableModel model = (DefaultTableModel) tblOvertimeReqHistory.getModel();
        String[] formattedovertimehistoryInfo = new String[overtimehistoryInfo.length];


        // Copy other fields directly
        for (int i = 0; i < overtimehistoryInfo.length; i++) {
                formattedovertimehistoryInfo[i] = overtimehistoryInfo[i];
            }
        

        // Add the formatted employee information to the table model
        model.addRow(formattedovertimehistoryInfo);
    }
    
    
    public void displayOvertimeRequestsForEmployee(String employeeId) {
        DefaultTableModel model = (DefaultTableModel) tblOvertimeReqHistory.getModel();
        model.setRowCount(0); // Clear existing rows

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM overtime_request WHERE EmployeeNum = ?";

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, employeeId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        // Extract data from the result set
                        int empID = resultSet.getInt("EmployeeNum");
                        String requestDate = resultSet.getString("RequestDate");
                        String otDate = resultSet.getString("OTDate");
                        String timeIn = resultSet.getString("TimeIn");
                        String timeOut = resultSet.getString("TimeOut");
                        double totalWorkedHrs = resultSet.getDouble("OTHrs");
                        String status = resultSet.getString("Status");
                        String approver = resultSet.getString("Approver");
                        String dateResponded = resultSet.getString("DateResponded");

                        // Add the extracted data to the table model
                        model.addRow(new Object[]{empID, requestDate, otDate, timeIn, timeOut, totalWorkedHrs, status, approver, dateResponded});
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while fetching overtime requests: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void insertOvertimeRequest(String employeeId, String requestDate, String otDate, String timeIn, String timeOut, double totalWorkedHrs, String status, String approver, String dateResponded) {
        // SQL query to insert data into the OvertimeRequest table
        String sql = "INSERT INTO overtime_request (EmployeeNum, RequestDate, OTDate, TimeIn, TimeOut, OTHrs, Status, Approver, DateResponded) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            preparedStatement.setInt(1, Integer.parseInt(employeeId));
            preparedStatement.setString(2, requestDate);
            preparedStatement.setString(3, otDate);
            preparedStatement.setString(4, timeIn);
            preparedStatement.setString(5, timeOut);
            preparedStatement.setDouble(6, totalWorkedHrs);
            preparedStatement.setString(7, status);
            preparedStatement.setString(8, approver);
            preparedStatement.setString(9, null); // Set DateResponded to NULL

            // Execute the insert query
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

/*
// Method to display the contents of the database table in the table for a specific employee
public void displayTimeTrackerFromDatabase(String employeeId) {
    DefaultTableModel model = (DefaultTableModel) timeTracker_table.getModel();
    model.setRowCount(0); // Clear existing rows

    try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);) {
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Prepare a SQL statement to select the time tracker records for the specified employee
        String sql = "SELECT * FROM TimeTracker WHERE Employee ID = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            // Set the employee ID parameter
            statement.setString(1, employeeId);

            // Execute the query
            try (ResultSet resultSet = statement.executeQuery()) {
                // Process the result set and populate the table model
                while (resultSet.next()) {
                    Object[] row = new Object[4]; // Assuming there are four columns: employee_id, date, startTime, endTime
                    row[0] = resultSet.getString("Employee ID");
                    row[1] = resultSet.getString("Date");
                    row[2] = resultSet.getString("Time In");
                    row[3] = resultSet.getString("Time Out");
                    model.addRow(row);
                }
            }
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        // Handle the exception appropriately
    }
}
*/

   
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings(value = "unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIDniEmployee = new javax.swing.JLabel();
        lblReqEid = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblOvertimeReqHistory = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(828, 449));
        setMinimumSize(new java.awt.Dimension(828, 449));
        setPreferredSize(new java.awt.Dimension(828, 449));
        setSize(new java.awt.Dimension(828, 449));
        getContentPane().setLayout(null);

        lblIDniEmployee.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblIDniEmployee.setForeground(new java.awt.Color(53, 66, 68));
        lblIDniEmployee.setText("Employee ID:");
        getContentPane().add(lblIDniEmployee);
        lblIDniEmployee.setBounds(640, 30, 96, 18);

        lblReqEid.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblReqEid.setForeground(new java.awt.Color(53, 66, 68));
        lblReqEid.setText("###");
        getContentPane().add(lblReqEid);
        lblReqEid.setBounds(750, 30, 33, 18);

        btnClose.setBackground(new java.awt.Color(255, 255, 255));
        btnClose.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnClose.setForeground(new java.awt.Color(82, 65, 73));
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        getContentPane().add(btnClose);
        btnClose.setBounds(380, 340, 76, 30);

        tblOvertimeReqHistory.setBackground(new java.awt.Color(255, 244, 210));
        tblOvertimeReqHistory.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tblOvertimeReqHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee ID", "Req Date", "Date of OT", "Time In", "Time Out", "Tot Worked Hrs", "Status", "Approver", "Approved Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOvertimeReqHistory.setGridColor(new java.awt.Color(173, 202, 206));
        tblOvertimeReqHistory.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tblOvertimeReqHistory.setSelectionForeground(new java.awt.Color(255, 0, 0));
        tblOvertimeReqHistory.setShowGrid(true);
        jScrollPane2.setViewportView(tblOvertimeReqHistory);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(50, 90, 730, 240);

        jLabel1.setText("oth");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, -50, 830, 500);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmOvertimeHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmOvertimeHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmOvertimeHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmOvertimeHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new FrmOvertimeHistory().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblIDniEmployee;
    private javax.swing.JLabel lblReqEid;
    private javax.swing.JTable tblOvertimeReqHistory;
    // End of variables declaration//GEN-END:variables

    public void displayDataForEmployee(String employeeId) {
        DefaultTableModel model = (DefaultTableModel) tblOvertimeReqHistory.getModel();
        model.setRowCount(0); // Clear existing rows

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM overtime_request WHERE EmployeeNum = ?";

            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, employeeId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        // Extract data from the result set
                        int empID = resultSet.getInt("EmployeeNum");
                        String requestDate = resultSet.getString("RequestDate");
                        String otDate = resultSet.getString("OTDate");
                        String timeIn = resultSet.getString("TimeIn");
                        String timeOut = resultSet.getString("TimeOut");
                        double totalWorkedHrs = resultSet.getDouble("OTHrs");
                        String status = resultSet.getString("Status");
                        String approver = resultSet.getString("Approver");
                        String dateResponded = resultSet.getString("DateResponded");

                        // Add the extracted data to the table model
                        model.addRow(new Object[]{empID, requestDate, otDate, timeIn, timeOut, totalWorkedHrs, status, approver, dateResponded});
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while fetching overtime requests: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
