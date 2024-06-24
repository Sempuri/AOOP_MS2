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
public class FrmLeaveHistory extends javax.swing.JFrame {
    
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";
    
    private FrmRequest requestPageRef;
    
    
    /**
     * Creates new form FrmLeaveHistory
     */
    public FrmLeaveHistory(FrmRequest requestPageRef) {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null); // Center the frame
        setPreferredSize(new Dimension(901, 471));
        
        // Set frame properties
        pack();  // Adjust size to the preferred size
    
        displayBgFromDatabase("FrmLeaveHistory");
        
        setAlternateRowAndColumnColorRenderer(tblLeaveReqHistory);
        
        this.requestPageRef = requestPageRef;
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
    
    //getter
    public JLabel getLblReqEid() {
        return lblReqEid;
    }

    //for displaying of data for employee
    public void displayDataForEmployee(String employeeId) {
    DefaultTableModel model = (DefaultTableModel) tblLeaveReqHistory.getModel();
    model.setRowCount(0); // Clear existing rows
    
    try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
        // Prepare the SQL statement
        String sql = "SELECT * FROM leave_request WHERE EmployeeNum = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employeeId);
            
            // Execute the query and process the results
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Extract data from the result set
                    String empId = rs.getString("EmployeeNum");
                    String requestDate = rs.getString("RequestDate");
                    String leaveType = rs.getString("LeaveType");
                    String startDate = rs.getString("StartDate");
                    String endDate = rs.getString("EndDate");
                    String reason = rs.getString("Reason");
                    String status = rs.getString("Status");
                    String approver = rs.getString("Approver");
                    String approvedDate = rs.getString("DateResponded");
                    
                    // Add the data to the table model
                    model.addRow(new Object[]{empId, requestDate, leaveType, startDate, endDate, reason, status, approver, approvedDate});
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception appropriately
        JOptionPane.showMessageDialog(this, "Error fetching data from database: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}

    
    //event handler method for close button
    private void FrameClose(java.awt.event.MouseEvent evt) {                            
        this.dispose();
    }    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        lblIDniEmployee = new javax.swing.JLabel();
        lblReqEid = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLeaveReqHistory = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        jCheckBox1.setText("jCheckBox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(901, 471));
        setMinimumSize(new java.awt.Dimension(901, 471));
        setPreferredSize(new java.awt.Dimension(901, 471));
        setSize(new java.awt.Dimension(901, 471));
        getContentPane().setLayout(null);

        lblIDniEmployee.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblIDniEmployee.setForeground(new java.awt.Color(53, 66, 68));
        lblIDniEmployee.setText("Employee ID:");
        getContentPane().add(lblIDniEmployee);
        lblIDniEmployee.setBounds(710, 30, 96, 18);

        lblReqEid.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblReqEid.setForeground(new java.awt.Color(53, 66, 68));
        lblReqEid.setText("###");
        getContentPane().add(lblReqEid);
        lblReqEid.setBounds(820, 30, 33, 18);

        btnClose.setBackground(new java.awt.Color(255, 255, 255));
        btnClose.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnClose.setForeground(new java.awt.Color(74, 64, 70));
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        getContentPane().add(btnClose);
        btnClose.setBounds(410, 390, 88, 41);

        tblLeaveReqHistory.setBackground(new java.awt.Color(242, 235, 197));
        tblLeaveReqHistory.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tblLeaveReqHistory.setForeground(new java.awt.Color(66, 55, 55));
        tblLeaveReqHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee ID", "Req Date", "Leave Type", "Start Date", "End Date", "Reason", "Status", "Approver", "Date Responded"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLeaveReqHistory.setGridColor(new java.awt.Color(173, 202, 206));
        tblLeaveReqHistory.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tblLeaveReqHistory.setSelectionForeground(new java.awt.Color(255, 0, 0));
        tblLeaveReqHistory.setShowGrid(true);
        jScrollPane2.setViewportView(tblLeaveReqHistory);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(50, 100, 800, 270);

        jLabel1.setText("lh");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, -30, 910, 500);

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
            java.util.logging.Logger.getLogger(FrmLeaveHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmLeaveHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmLeaveHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmLeaveHistory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new FrmLeaveHistory().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblIDniEmployee;
    private javax.swing.JLabel lblReqEid;
    private javax.swing.JTable tblLeaveReqHistory;
    // End of variables declaration//GEN-END:variables
}
