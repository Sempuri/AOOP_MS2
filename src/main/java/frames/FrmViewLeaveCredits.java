/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author yen
 */
public class FrmViewLeaveCredits extends javax.swing.JFrame {
    
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";

    /**
     * Creates new form FrmViewLeaveCredits
     */
    public FrmViewLeaveCredits() {
        initComponents();
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null); // Center the frame
        setPreferredSize(new Dimension(550, 670));
        
        // Set frame properties
        pack();  // Adjust size to the preferred size
        
        displayBgFromDatabase("FrmViewLeaveCredits");
        
         setAlternateRowAndColumnColorRenderer(tblViewLeaveCredits);
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
    
    public void populateLeaveCredits(ResultSet resultSet) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) tblViewLeaveCredits.getModel();
        model.setRowCount(0); // Clear existing rows
        
        // Iterate through the result set and add each row to the table
        while (resultSet.next()) {
            Object[] rowData = {
                resultSet.getInt("EmployeeNum"),
                resultSet.getString("LastName"),
                resultSet.getString("FirstName"),
                resultSet.getInt("Year"),
                resultSet.getInt("LeaveCreditsRemaining")
            };
            model.addRow(rowData);
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

        private static final Color EVEN_COLUMN_COLOR = new Color(244, 240, 252);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblViewLeaveCredits = new javax.swing.JTable();
        lblNote = new javax.swing.JLabel();
        lblLeaveCredits = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(550, 670));
        setMinimumSize(new java.awt.Dimension(550, 670));
        setPreferredSize(new java.awt.Dimension(550, 670));
        setSize(new java.awt.Dimension(550, 670));
        getContentPane().setLayout(null);

        tblViewLeaveCredits.setBackground(new java.awt.Color(255, 255, 255));
        tblViewLeaveCredits.setForeground(new java.awt.Color(53, 41, 41));
        tblViewLeaveCredits.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Emp ID", "Last Name", "First Name", "Year", "Leave Credits Remaining"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblViewLeaveCredits.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tblViewLeaveCredits.setSelectionForeground(new java.awt.Color(255, 0, 0));
        jScrollPane1.setViewportView(tblViewLeaveCredits);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(50, 70, 456, 500);

        lblNote.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        lblNote.setForeground(new java.awt.Color(255, 255, 255));
        lblNote.setText("If leave credits are unused for the current year, the year will remain at 0.");
        getContentPane().add(lblNote);
        lblNote.setBounds(50, 560, 400, 40);

        lblLeaveCredits.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        lblLeaveCredits.setForeground(new java.awt.Color(255, 255, 255));
        lblLeaveCredits.setText("Employee Leave Credits");
        getContentPane().add(lblLeaveCredits);
        lblLeaveCredits.setBounds(180, 20, 260, 40);

        btnClose.setBackground(new java.awt.Color(255, 255, 255));
        btnClose.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnClose.setForeground(new java.awt.Color(61, 55, 55));
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        getContentPane().add(btnClose);
        btnClose.setBounds(420, 580, 90, 28);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(53, 44, 44));
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 550, 670);

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
            java.util.logging.Logger.getLogger(FrmViewLeaveCredits.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmViewLeaveCredits.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmViewLeaveCredits.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmViewLeaveCredits.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmViewLeaveCredits().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLeaveCredits;
    private javax.swing.JLabel lblNote;
    private javax.swing.JTable tblViewLeaveCredits;
    // End of variables declaration//GEN-END:variables
}
