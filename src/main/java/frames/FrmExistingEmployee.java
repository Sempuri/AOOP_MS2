/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frames;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author yen
 */
public class FrmExistingEmployee extends javax.swing.JFrame {
    
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";

    /**
     * Creates new form FrmExistingEmployee
     */
    public FrmExistingEmployee() {
        initComponents();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null); // Center the frame
        setPreferredSize(new Dimension(550, 690));
        
        // Set frame properties
        pack();  // Adjust size to the preferred size
        
        displayBgFromDatabase("FrmExistingEmployee");
        displayEmployeeInfo();
        
    }
    
    public JTable getTblExistingEmp() {
        return tblExistingEmp;
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


    private void displayEmployeeInfo() {
    DefaultTableModel model = (DefaultTableModel) tblExistingEmp.getModel();
    model.setRowCount(0); // Clear existing rows before adding new ones

    String query = "SELECT l.Username, e.EmployeeNum, e.LastName, e.FirstName, r.Position, r.Department " +
                   "FROM login l " +
                   "RIGHT JOIN employee e ON l.EmployeeNum = e.EmployeeNum " + // Use RIGHT JOIN to prioritize EmployeeNum
                   "INNER JOIN role r ON e.RoleID = r.RoleID " +
                   "ORDER BY e.EmployeeNum"; // Prioritize by EmployeeNum

    try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         PreparedStatement statement = conn.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {
            String username = resultSet.getString("Username");
            int employeeNum = resultSet.getInt("EmployeeNum");
            // Handle the possibility of null values for LastName, FirstName, Position, and Department
            String lastName = resultSet.getString("LastName");
            String firstName = resultSet.getString("FirstName");
            String position = resultSet.getString("Position");
            String department = resultSet.getString("Department");

            // Check for null values and replace them with empty strings
            if (lastName == null) {
                lastName = "";
            }
            if (firstName == null) {
                firstName = "";
            }
            if (position == null) {
                position = "";
            }
            if (department == null) {
                department = "";
            }

            model.addRow(new Object[]{username, employeeNum, lastName, firstName, position, department});
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
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

        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblExistingEmp = new javax.swing.JTable();
        lblEmployeeList = new javax.swing.JLabel();
        btnCloseList = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(550, 670));
        setMinimumSize(new java.awt.Dimension(550, 670));
        setPreferredSize(new java.awt.Dimension(550, 670));
        setSize(new java.awt.Dimension(550, 670));
        getContentPane().setLayout(null);

        tblExistingEmp.setBackground(new java.awt.Color(243, 226, 226));
        tblExistingEmp.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tblExistingEmp.setForeground(new java.awt.Color(47, 39, 39));
        tblExistingEmp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Username", "Emp ID", "Last Name", "First Name", "Position", "Department"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblExistingEmp.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tblExistingEmp.setSelectionForeground(new java.awt.Color(255, 0, 0));
        jScrollPane1.setViewportView(tblExistingEmp);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(30, 50, 490, 560);

        lblEmployeeList.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        lblEmployeeList.setForeground(new java.awt.Color(255, 255, 255));
        lblEmployeeList.setText("Employee List");
        getContentPane().add(lblEmployeeList);
        lblEmployeeList.setBounds(210, 10, 150, 22);

        btnCloseList.setBackground(new java.awt.Color(255, 255, 255));
        btnCloseList.setForeground(new java.awt.Color(64, 55, 55));
        btnCloseList.setText("Close");
        btnCloseList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseListActionPerformed(evt);
            }
        });
        getContentPane().add(btnCloseList);
        btnCloseList.setBounds(430, 620, 82, 27);

        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, -20, 560, 700);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseListActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCloseListActionPerformed

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
            java.util.logging.Logger.getLogger(FrmExistingEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmExistingEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmExistingEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmExistingEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmExistingEmployee().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCloseList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblEmployeeList;
    private javax.swing.JTable tblExistingEmp;
    // End of variables declaration//GEN-END:variables
}
