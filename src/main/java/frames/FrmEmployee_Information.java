package frames;

import javax.swing.JLabel;
import javax.swing.JTextField;
import frames.FrmSalary;
import frames.FrmRequest;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author yen
 */
public class FrmEmployee_Information extends javax.swing.JFrame {
    
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";

    private JTable timeTracker_table;

    /**
     * Creates new form Employee_Information
     */
    public FrmEmployee_Information() {
        
        initComponents();
        setResizable(false);
        
        setPreferredSize(new Dimension(1120, 670));
        
        // Set frame properties
        pack();  // Adjust size to the preferred size
        setLocationRelativeTo(null);  // Center the frame on the screen
        
        // Call a method to fetch and display the image from the database
        displayImageFromDatabase("empImg");
        displayBgFromDatabase("FrmEmployee_Information");
    }

    // Method to fetch and display image from the database
    private void displayImageFromDatabase(String frameName) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
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
                    jLabel5.setIcon(icon);
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

    
//Getter Method

    public JLabel getLblEmpStatus() {
        return lblEmpStatus;
    }

    public JTextField getTxtDepartment() {
        return txtDepartment;
    }

    public JTextField getTxtEmployeeID() {
        return txtEmployeeID;
    }

    public JTextField getTxtFirstName() {
        return txtFirstName;
    }

    public JTextField getTxtLastName() {
        return txtLastName;
    }

    public JTextField getTxtPagibig_number() {
        return txtPagibig_number;
    }

    public JTextField getTxtPhilhealth_number() {
        return txtPhilhealth_number;
    }

    public JTextField getTxtRole() {
        return txtRole;
    }

    public JTextField getTxtSSS_number() {
        return txtSSS_number;
    }

    public JTextField getTxtSupervisor() {
        return txtSupervisor;
    }

    public JTextField getTxtTin_number() {
        return txtTin_number;
    }

    public JLabel getLblEid() {
        return lblEid;
    }

    public JLabel getLblFName() {
        return lblFName;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        logOut_button = new javax.swing.JButton();
        lblFName = new javax.swing.JLabel();
        lblWelcomeMsg = new javax.swing.JLabel();
        lblIDniEmployee = new javax.swing.JLabel();
        lblEid = new javax.swing.JLabel();
        lblEmpStatus = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        attendance_button = new javax.swing.JButton();
        salary_button = new javax.swing.JButton();
        request_button = new javax.swing.JButton();
        lblEmployeeID = new javax.swing.JLabel();
        txtEmployeeID = new javax.swing.JTextField();
        lblFirstName = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        lblLastName = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        lblRole = new javax.swing.JLabel();
        txtRole = new javax.swing.JTextField();
        lblDepartment = new javax.swing.JLabel();
        txtDepartment = new javax.swing.JTextField();
        lblSupervisor = new javax.swing.JLabel();
        txtSupervisor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        lblSSS_number = new javax.swing.JLabel();
        txtSSS_number = new javax.swing.JTextField();
        lblPagibig_number = new javax.swing.JLabel();
        txtPagibig_number = new javax.swing.JTextField();
        lblPhilhealth_number = new javax.swing.JLabel();
        txtPhilhealth_number = new javax.swing.JTextField();
        lblTin_number = new javax.swing.JLabel();
        txtTin_number = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lblWelcomeMsg1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(218, 226, 227));
        setBounds(new java.awt.Rectangle(0, 25, 1120, 670));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(1120, 670));
        setPreferredSize(new java.awt.Dimension(1120, 670));
        setSize(new java.awt.Dimension(1120, 670));
        getContentPane().setLayout(null);

        logOut_button.setBackground(new java.awt.Color(255, 255, 255));
        logOut_button.setFont(new java.awt.Font("Avenir Next", 1, 24)); // NOI18N
        logOut_button.setForeground(new java.awt.Color(102, 77, 77));
        logOut_button.setText("Log out");
        logOut_button.setToolTipText("");
        logOut_button.setAutoscrolls(true);
        logOut_button.setBorder(null);
        logOut_button.setBorderPainted(false);
        logOut_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOut_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(logOut_button);
        logOut_button.setBounds(980, 30, 100, 30);

        lblFName.setFont(new java.awt.Font("Verdana", 1, 25)); // NOI18N
        lblFName.setForeground(new java.awt.Color(185, 136, 136));
        lblFName.setText("firstname");
        getContentPane().add(lblFName);
        lblFName.setBounds(290, 20, 640, 32);

        lblWelcomeMsg.setFont(new java.awt.Font("Verdana", 0, 25)); // NOI18N
        lblWelcomeMsg.setForeground(new java.awt.Color(60, 45, 45));
        lblWelcomeMsg.setText("Welcome,");
        getContentPane().add(lblWelcomeMsg);
        lblWelcomeMsg.setBounds(160, 20, 123, 32);

        lblIDniEmployee.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        lblIDniEmployee.setForeground(new java.awt.Color(60, 45, 45));
        lblIDniEmployee.setText("Employee ID:");
        getContentPane().add(lblIDniEmployee);
        lblIDniEmployee.setBounds(160, 50, 91, 17);

        lblEid.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        lblEid.setForeground(new java.awt.Color(60, 45, 45));
        lblEid.setText("###");
        getContentPane().add(lblEid);
        lblEid.setBounds(260, 50, 33, 17);

        lblEmpStatus.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        lblEmpStatus.setForeground(new java.awt.Color(255, 255, 255));
        lblEmpStatus.setPreferredSize(new java.awt.Dimension(55, 25));
        getContentPane().add(lblEmpStatus);
        lblEmpStatus.setBounds(200, 460, 150, 25);

        lblStatus.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(255, 255, 255));
        lblStatus.setText("Status:");
        getContentPane().add(lblStatus);
        lblStatus.setBounds(110, 460, 72, 23);

        attendance_button.setBackground(new java.awt.Color(255, 255, 255));
        attendance_button.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        attendance_button.setForeground(new java.awt.Color(93, 72, 72));
        attendance_button.setText("Attendance");
        attendance_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attendance_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(attendance_button);
        attendance_button.setBounds(210, 560, 173, 50);

        salary_button.setBackground(new java.awt.Color(255, 255, 255));
        salary_button.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        salary_button.setForeground(new java.awt.Color(93, 72, 72));
        salary_button.setText("Salary");
        salary_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salary_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(salary_button);
        salary_button.setBounds(460, 560, 173, 50);

        request_button.setBackground(new java.awt.Color(255, 255, 255));
        request_button.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        request_button.setForeground(new java.awt.Color(93, 72, 72));
        request_button.setText("Request");
        request_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                request_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(request_button);
        request_button.setBounds(710, 560, 173, 50);

        lblEmployeeID.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblEmployeeID.setForeground(new java.awt.Color(255, 255, 255));
        lblEmployeeID.setText("Employee ID:");
        getContentPane().add(lblEmployeeID);
        lblEmployeeID.setBounds(424, 217, 161, 17);

        txtEmployeeID.setEditable(false);
        txtEmployeeID.setBackground(new java.awt.Color(111, 105, 113));
        txtEmployeeID.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtEmployeeID.setForeground(new java.awt.Color(255, 255, 255));
        txtEmployeeID.setText("1");
        txtEmployeeID.setBorder(null);
        txtEmployeeID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmployeeIDActionPerformed(evt);
            }
        });
        getContentPane().add(txtEmployeeID);
        txtEmployeeID.setBounds(650, 217, 380, 18);

        lblFirstName.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblFirstName.setForeground(new java.awt.Color(255, 255, 255));
        lblFirstName.setText("First Name:");
        getContentPane().add(lblFirstName);
        lblFirstName.setBounds(424, 247, 161, 17);

        txtFirstName.setEditable(false);
        txtFirstName.setBackground(new java.awt.Color(138, 134, 139));
        txtFirstName.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtFirstName.setForeground(new java.awt.Color(255, 255, 255));
        txtFirstName.setText("2");
        txtFirstName.setBorder(null);
        getContentPane().add(txtFirstName);
        txtFirstName.setBounds(650, 247, 380, 18);

        lblLastName.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblLastName.setForeground(new java.awt.Color(255, 255, 255));
        lblLastName.setText("Last Name:");
        getContentPane().add(lblLastName);
        lblLastName.setBounds(424, 277, 161, 17);

        txtLastName.setEditable(false);
        txtLastName.setBackground(new java.awt.Color(111, 105, 113));
        txtLastName.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtLastName.setForeground(new java.awt.Color(255, 255, 255));
        txtLastName.setText("3");
        txtLastName.setBorder(null);
        getContentPane().add(txtLastName);
        txtLastName.setBounds(650, 277, 380, 18);

        lblRole.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblRole.setForeground(new java.awt.Color(255, 255, 255));
        lblRole.setText("Role:");
        getContentPane().add(lblRole);
        lblRole.setBounds(424, 307, 161, 17);

        txtRole.setEditable(false);
        txtRole.setBackground(new java.awt.Color(138, 134, 139));
        txtRole.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtRole.setForeground(new java.awt.Color(255, 255, 255));
        txtRole.setText("4");
        txtRole.setBorder(null);
        txtRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRoleActionPerformed(evt);
            }
        });
        getContentPane().add(txtRole);
        txtRole.setBounds(650, 307, 380, 18);

        lblDepartment.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblDepartment.setForeground(new java.awt.Color(255, 255, 255));
        lblDepartment.setText("Department:");
        getContentPane().add(lblDepartment);
        lblDepartment.setBounds(424, 338, 161, 17);

        txtDepartment.setEditable(false);
        txtDepartment.setBackground(new java.awt.Color(111, 105, 113));
        txtDepartment.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtDepartment.setForeground(new java.awt.Color(255, 255, 255));
        txtDepartment.setText("5");
        txtDepartment.setBorder(null);
        getContentPane().add(txtDepartment);
        txtDepartment.setBounds(650, 338, 380, 18);

        lblSupervisor.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblSupervisor.setForeground(new java.awt.Color(255, 255, 255));
        lblSupervisor.setText("Supervisor:");
        getContentPane().add(lblSupervisor);
        lblSupervisor.setBounds(424, 367, 161, 17);

        txtSupervisor.setEditable(false);
        txtSupervisor.setBackground(new java.awt.Color(138, 134, 139));
        txtSupervisor.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtSupervisor.setForeground(new java.awt.Color(255, 255, 255));
        txtSupervisor.setText("6");
        txtSupervisor.setBorder(null);
        txtSupervisor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSupervisorActionPerformed(evt);
            }
        });
        getContentPane().add(txtSupervisor);
        txtSupervisor.setBounds(650, 367, 380, 18);

        jLabel5.setText("1");
        jLabel5.setToolTipText("");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        getContentPane().add(jLabel5);
        jLabel5.setBounds(123, 202, 200, 210);

        lblSSS_number.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblSSS_number.setForeground(new java.awt.Color(255, 255, 255));
        lblSSS_number.setText("SSS Number:");
        getContentPane().add(lblSSS_number);
        lblSSS_number.setBounds(424, 397, 161, 17);

        txtSSS_number.setEditable(false);
        txtSSS_number.setBackground(new java.awt.Color(111, 105, 113));
        txtSSS_number.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtSSS_number.setForeground(new java.awt.Color(255, 255, 255));
        txtSSS_number.setText("7");
        txtSSS_number.setBorder(null);
        getContentPane().add(txtSSS_number);
        txtSSS_number.setBounds(650, 397, 380, 18);

        lblPagibig_number.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblPagibig_number.setForeground(new java.awt.Color(255, 255, 255));
        lblPagibig_number.setText("Pag-IBIG Number:");
        getContentPane().add(lblPagibig_number);
        lblPagibig_number.setBounds(424, 427, 161, 17);

        txtPagibig_number.setEditable(false);
        txtPagibig_number.setBackground(new java.awt.Color(138, 134, 139));
        txtPagibig_number.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtPagibig_number.setForeground(new java.awt.Color(255, 255, 255));
        txtPagibig_number.setText("8");
        txtPagibig_number.setBorder(null);
        getContentPane().add(txtPagibig_number);
        txtPagibig_number.setBounds(650, 427, 380, 18);

        lblPhilhealth_number.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblPhilhealth_number.setForeground(new java.awt.Color(255, 255, 255));
        lblPhilhealth_number.setText("PhilHealth Number:");
        getContentPane().add(lblPhilhealth_number);
        lblPhilhealth_number.setBounds(424, 457, 161, 17);

        txtPhilhealth_number.setEditable(false);
        txtPhilhealth_number.setBackground(new java.awt.Color(111, 105, 113));
        txtPhilhealth_number.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtPhilhealth_number.setForeground(new java.awt.Color(255, 255, 255));
        txtPhilhealth_number.setText("9");
        txtPhilhealth_number.setBorder(null);
        getContentPane().add(txtPhilhealth_number);
        txtPhilhealth_number.setBounds(650, 457, 380, 18);

        lblTin_number.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblTin_number.setForeground(new java.awt.Color(255, 255, 255));
        lblTin_number.setText("TIN Number:");
        getContentPane().add(lblTin_number);
        lblTin_number.setBounds(424, 487, 161, 17);

        txtTin_number.setEditable(false);
        txtTin_number.setBackground(new java.awt.Color(138, 134, 139));
        txtTin_number.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtTin_number.setForeground(new java.awt.Color(255, 255, 255));
        txtTin_number.setText("10");
        txtTin_number.setBorder(null);
        txtTin_number.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTin_numberActionPerformed(evt);
            }
        });
        getContentPane().add(txtTin_number);
        txtTin_number.setBounds(650, 487, 380, 18);

        jLabel1.setText("emp");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 1120, 670);

        lblWelcomeMsg1.setFont(new java.awt.Font("Verdana", 0, 25)); // NOI18N
        lblWelcomeMsg1.setForeground(new java.awt.Color(60, 45, 45));
        lblWelcomeMsg1.setText("Welcome,");
        getContentPane().add(lblWelcomeMsg1);
        lblWelcomeMsg1.setBounds(160, 20, 123, 32);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void request_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_request_buttonActionPerformed
        // TODO add your handling code here:
        FrmRequest request = new FrmRequest();
        FrmEmployee_Information _profile = new FrmEmployee_Information();
        request.getLblReqEid().setText(lblEid.getText());
        request.getLblReqFName().setText(lblFName.getText());  
        request.displayRemainingLeaveCredits();
        request.show();
        request.setLocationRelativeTo(null); // Center the frame
         dispose();
    }//GEN-LAST:event_request_buttonActionPerformed

    private void attendance_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attendance_buttonActionPerformed
        // Get the logged-in employee's ID and name
        int employeeId = Integer.parseInt(lblEid.getText());
        String employeeName = lblFName.getText();

            // Open the attendance frame
            FrmAttendance attendance = new FrmAttendance();
            attendance.getLblAttEid().setText(Integer.toString(employeeId));
            attendance.getLblAttFName().setText(employeeName);
            attendance.setVisible(true);
            attendance.setLocationRelativeTo(null); // Center the frame
        // Display attendance data for the logged-in employee
        attendance.displayTimeTrackerFromDatabase(employeeId);
        attendance.displayOldAttendanceFromDatabase(employeeId);

        // Close the current frame
        dispose();
    }//GEN-LAST:event_attendance_buttonActionPerformed


    private void logOut_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOut_buttonActionPerformed
        // TODO add your handling code here:
        FrmLogin logOut = null;
        try {
            logOut = new FrmLogin();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FrmEmployee_Information.class.getName()).log(Level.SEVERE, null, ex);
        }
            logOut.show();
            logOut.setLocationRelativeTo(null); // Center the frame
            
            dispose();
    }//GEN-LAST:event_logOut_buttonActionPerformed

    private void salary_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salary_buttonActionPerformed
        // TODO add your handling code here:
        FrmSalary salary = new FrmSalary();
        salary.getLblSalEid().setText(lblEid.getText());
        salary.getLblSalFName().setText(lblFName.getText());
        salary.show();
        salary.setLocationRelativeTo(null); // Center the frame

        dispose();
    }//GEN-LAST:event_salary_buttonActionPerformed

    private void txtEmployeeIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmployeeIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmployeeIDActionPerformed

    private void txtSupervisorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSupervisorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSupervisorActionPerformed

    private void txtTin_numberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTin_numberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTin_numberActionPerformed

    private void txtRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRoleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRoleActionPerformed

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
            java.util.logging.Logger.getLogger(FrmEmployee_Information.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmEmployee_Information.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmEmployee_Information.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmEmployee_Information.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new FrmEmployee_Information().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton attendance_button;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JLabel lblDepartment;
    private javax.swing.JLabel lblEid;
    private javax.swing.JLabel lblEmpStatus;
    private javax.swing.JLabel lblEmployeeID;
    private javax.swing.JLabel lblFName;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblIDniEmployee;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblPagibig_number;
    private javax.swing.JLabel lblPhilhealth_number;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblSSS_number;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblSupervisor;
    private javax.swing.JLabel lblTin_number;
    private javax.swing.JLabel lblWelcomeMsg;
    private javax.swing.JLabel lblWelcomeMsg1;
    private javax.swing.JButton logOut_button;
    private javax.swing.JButton request_button;
    private javax.swing.JButton salary_button;
    private javax.swing.JTextField txtDepartment;
    public javax.swing.JTextField txtEmployeeID;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtPagibig_number;
    private javax.swing.JTextField txtPhilhealth_number;
    private javax.swing.JTextField txtRole;
    private javax.swing.JTextField txtSSS_number;
    private javax.swing.JTextField txtSupervisor;
    private javax.swing.JTextField txtTin_number;
    // End of variables declaration//GEN-END:variables

    private void retrieveOldAttendanceFromDatabase(String employeeId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    Object getTxtBirthday() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}
