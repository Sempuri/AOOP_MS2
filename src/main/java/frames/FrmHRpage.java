/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frames;

import javax.swing.JLabel;
import javax.swing.JTable;
import classes.Employee;
import com.opencsv.exceptions.CsvValidationException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
/**
 *
 * @author EMAN
 */
public class FrmHRpage extends javax.swing.JFrame {
    
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";
    
    private FrmHRpage _hrPage = this;
    
    /**
     * Creates new form FrmHRpage
     */
    
    // Method to retrieve the status of an overtime request based on its index
    public String getOvertimeRequestStatus(int rowIndex) {
        // Assuming your table model stores the status in a specific column (e.g., 6th column)
        return (String) tblOvertimeReq.getModel().getValueAt(rowIndex, 6);
    }

    public FrmHRpage() {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null); // Center the frame
        setPreferredSize(new Dimension(1500, 830));
        
        // Set frame properties
        pack();  // Adjust size to the preferred size
    
        displayBgFromDatabase("FrmHRpage");
        
        // Set custom renderer for the date column
        setCustomDateRenderer();
        
        // Set custom renderer for all columns in the table
        setAlternateRowAndColumnColorRenderer(tblEmpInfo);
        setAlternateRowAndColumnColorRenderer(tblOvertimeReq);
        setAlternateRowAndColumnColorRenderer(tblLeaveReq);
        
        // Add a DocumentListener to the employee info search text field
        txtSearchEmpInfo.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    searchEmployeeInfo(txtSearchEmpInfo.getText().trim());
                } catch (SQLException ex) {
                    Logger.getLogger(FrmHRpage.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(FrmHRpage.this, "Error searching for employee information: " + ex.getMessage(), "Search Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    searchEmployeeInfo(txtSearchEmpInfo.getText().trim());
                } catch (SQLException ex) {
                    Logger.getLogger(FrmHRpage.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(FrmHRpage.this, "Error searching for employee information: " + ex.getMessage(), "Search Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not used for plain text components
            }
        });      
        
        // Add a DocumentListener to the overtime request search text field
        txtSearchOvertime.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    searchOvertime(txtSearchOvertime.getText().trim());
                } catch (SQLException ex) {
                    Logger.getLogger(FrmHRpage.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(FrmHRpage.this, "Error searching for overtime requests: " + ex.getMessage(), "Search Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    searchOvertime(txtSearchOvertime.getText().trim());
                } catch (SQLException ex) {
                    Logger.getLogger(FrmHRpage.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(FrmHRpage.this, "Error searching for overtime requests: " + ex.getMessage(), "Search Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not used for plain text components
            }
        });
        
        // Add a DocumentListener to the overtime request search text field
        txtSearchLeave.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    searchLeave(txtSearchLeave.getText().trim());
                } catch (SQLException ex) {
                    Logger.getLogger(FrmHRpage.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(FrmHRpage.this, "Error searching for leave requests: " + ex.getMessage(), "Search Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    searchLeave(txtSearchLeave.getText().trim());
                } catch (SQLException ex) {
                    Logger.getLogger(FrmHRpage.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(FrmHRpage.this, "Error searching for leave requests: " + ex.getMessage(), "Search Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not used for plain text components
            }
        });
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

    
    private String getPositionName(int roleId) {
        Map<Integer, String> positionsMap = new HashMap<>();
        positionsMap.put(1, "Chief Executive Officer");
        positionsMap.put(2, "Chief Operating Officer");
        positionsMap.put(3, "Chief Finance Officer");
        positionsMap.put(4, "Chief Marketing Officer");
        positionsMap.put(5, "IT Operations and Systems");
        positionsMap.put(6, "HR Manager");
        positionsMap.put(7, "HR Team Leader");
        positionsMap.put(8, "HR Rank and File");
        positionsMap.put(9, "Accounting Head");
        positionsMap.put(10, "Payroll Manager");
        positionsMap.put(11, "Payroll Team Leader");
        positionsMap.put(12, "Payroll Rank and File");
        positionsMap.put(13, "Account Manager");
        positionsMap.put(14, "Account Team Leader");
        positionsMap.put(15, "Account Rank and File");
        positionsMap.put(16, "Sales & Marketing");
        positionsMap.put(17, "Supply Chain and Logistics");
        positionsMap.put(18, "Customer Service and Relations");

        return positionsMap.getOrDefault(roleId, "Unknown Position");
    }

    private String getDepartmentName(int roleId) {
        Map<Integer, String> departmentsMap = new HashMap<>();
        departmentsMap.put(1, "Executive");
        departmentsMap.put(2, "Operations");
        departmentsMap.put(3, "Payroll");
        departmentsMap.put(4, "Marketing");
        departmentsMap.put(5, "IT");
        departmentsMap.put(6, "HR");
        departmentsMap.put(7, "HR");
        departmentsMap.put(8, "HR");
        departmentsMap.put(9, "Payroll");
        departmentsMap.put(10, "Payroll");
        departmentsMap.put(11, "Payroll");
        departmentsMap.put(12, "Payroll");
        departmentsMap.put(13, "Operations");
        departmentsMap.put(14, "Operations");
        departmentsMap.put(15, "Operations");
        departmentsMap.put(16, "Marketing");
        departmentsMap.put(17, "Marketing");
        departmentsMap.put(18, "Marketing");

        return departmentsMap.getOrDefault(roleId, "Unknown Department");
    }

    //for creation of new row -> for add new employee
    public void updateTable(String[] employeeInfo) {
        // Add the employee information to the table
        DefaultTableModel model = (DefaultTableModel) tblEmpInfo.getModel();

        // Fetch Position and Department names based on the RoleID
        int roleId = Integer.parseInt(employeeInfo[11]); // Assuming RoleID is at index 11
        String positionName = getPositionName(roleId);
        String departmentName = getDepartmentName(roleId);

        // Get the employee ID from the input data
        int newEmployeeID = Integer.parseInt(employeeInfo[0]);

        // Find the index to insert the new row to maintain numerical order
        int rowIndex = 0;
        while (rowIndex < model.getRowCount() && Integer.parseInt(model.getValueAt(rowIndex, 0).toString()) < newEmployeeID) {
            rowIndex++;
        }

        // Insert the new row at the appropriate index
        model.insertRow(rowIndex, new Object[]{
            employeeInfo[0],  // EmpID
            employeeInfo[1],  // Last Name
            employeeInfo[2],  // First Name
            employeeInfo[3],  // Birthday
            employeeInfo[4],  // Address
            employeeInfo[5],  // Phone Number
            employeeInfo[6],  // SSS Number
            employeeInfo[7],  // Philhealth Number
            employeeInfo[8],  // TIN Number
            employeeInfo[9],  // Pagibig Number
            employeeInfo[10], // Status
            positionName,     // Position Name
            departmentName,   // Department Name
            employeeInfo[12]  // Supervisor
        });
    }



    //for updating a specific row -> for edit employee info (for Employee Information)
    public void updateTableRow(int rowIndex, String[] empInfo) {
        DefaultTableModel model = (DefaultTableModel) tblEmpInfo.getModel();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Retrieve the role and department names
        int roleId = Integer.parseInt(empInfo[11]);
        String positionName = getPositionName(roleId);
        String departmentName = getDepartmentName(roleId);


        // Update the values of the specified row in the table model
        model.setValueAt(empInfo[0], rowIndex, 0);  // Employee ID
        model.setValueAt(empInfo[1], rowIndex, 1);  // Last Name
        model.setValueAt(empInfo[2], rowIndex, 2);  // First Name
        model.setValueAt(empInfo[3], rowIndex, 3);  // Birthday
        model.setValueAt(empInfo[4], rowIndex, 4);  // Address
        model.setValueAt(empInfo[5], rowIndex, 5);  // Phone Number
        model.setValueAt(empInfo[6], rowIndex, 6);  // SSS Number
        model.setValueAt(empInfo[7], rowIndex, 7);  // Philhealth Number
        model.setValueAt(empInfo[8], rowIndex, 8);  // TIN Number
        model.setValueAt(empInfo[9], rowIndex, 9);  // Pagibig Number
        model.setValueAt(empInfo[10], rowIndex, 10); // Status
        model.setValueAt(positionName, rowIndex, 11);    // Position Name
        model.setValueAt(departmentName, rowIndex, 12);  // Department Name
        model.setValueAt(empInfo[12], rowIndex, 13);     // Supervisor
    }


    
  
    //for updating a specific row -> for edit employee info (for Leave Request)
    public void updateLeaveTableRow(int rowIndex, String[] leaveInfo) {
        DefaultTableModel model = (DefaultTableModel) tblLeaveReq.getModel();

        // Update the specified row in the table model with the leave information
        for (int i = 0; i < leaveInfo.length; i++) {
            model.setValueAt(leaveInfo[i], rowIndex, i);
        }
    }

    
    //for updating a specific row -> for edit employee info (for Overtime Request)
    public void updateOvertimeTableRow(int rowIndex, String[] overtimeInfo) {
        DefaultTableModel model = (DefaultTableModel) tblOvertimeReq.getModel();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Format the date field before updating the table
        overtimeInfo[8] = dateFormat.format(new Date());
        
        System.out.println("Formatted Date: " + overtimeInfo[8]); // Debug

        // Update the specified row in the table model
        for (int i = 0; i < overtimeInfo.length; i++) {
            model.setValueAt(overtimeInfo[i], rowIndex, i);
        }
    }
    
    
    // Method to set custom renderer for the date column
    private void setCustomDateRenderer() {
        TableCellRenderer renderer = new DefaultTableCellRenderer() {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                if (value instanceof Date) {
                    value = formatter.format(value); // Format the date before rendering
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };

        // Apply the renderer to the date column (replace 3 with the actual index of the date column)
        tblEmpInfo.getColumnModel().getColumn(3).setCellRenderer(renderer);
        tblLeaveReq.getColumnModel().getColumn(1).setCellRenderer(renderer);
        tblLeaveReq.getColumnModel().getColumn(3).setCellRenderer(renderer);
        tblLeaveReq.getColumnModel().getColumn(4).setCellRenderer(renderer);
        tblLeaveReq.getColumnModel().getColumn(8).setCellRenderer(renderer);
        tblOvertimeReq.getColumnModel().getColumn(1).setCellRenderer(renderer);
        tblOvertimeReq.getColumnModel().getColumn(2).setCellRenderer(renderer);
        tblOvertimeReq.getColumnModel().getColumn(8).setCellRenderer(renderer);
    }
    
    //getter
    public JLabel getLblEid() {

        return lblEid;
    }

    public JLabel getLblFName() {    
        return lblFName;
    }

    public JTable getTblEmpInfo() {
        return tblEmpInfo;
    }

    public JTable getTblLeaveReq() {
        return tblLeaveReq;
    }

    public JTable getTblOvertimeReq() {
        return tblOvertimeReq;
    }

    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings(value = "unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblWelcomeMsg = new javax.swing.JLabel();
        lblFName = new javax.swing.JLabel();
        lblSearchLeave = new javax.swing.JLabel();
        lblSearchOvertime = new javax.swing.JLabel();
        lblSearchEmpInfo = new javax.swing.JLabel();
        lblTip = new javax.swing.JLabel();
        lblIDniEmployee = new javax.swing.JLabel();
        lblEid = new javax.swing.JLabel();
        lblEmpInfoTable1 = new javax.swing.JLabel();
        txtSearchLeave = new javax.swing.JTextField();
        txtSearchOvertime = new javax.swing.JTextField();
        txtSearchEmpInfo = new javax.swing.JTextField();
        btnRefresh = new javax.swing.JButton();
        logOut_button = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmpInfo = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        lblOvertimeRequestTable = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblOvertimeReq = new javax.swing.JTable();
        btnRespondOvertime = new javax.swing.JButton();
        lblLeaveRequestTable = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLeaveReq = new javax.swing.JTable();
        btnRespondLeave = new javax.swing.JButton();
        btnViewLeaveCredits = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1500, 830));
        setMinimumSize(new java.awt.Dimension(1500, 830));
        setPreferredSize(new java.awt.Dimension(1500, 830));
        setSize(new java.awt.Dimension(1500, 830));
        getContentPane().setLayout(null);

        lblWelcomeMsg.setFont(new java.awt.Font("Verdana", 0, 25)); // NOI18N
        lblWelcomeMsg.setForeground(new java.awt.Color(60, 45, 45));
        lblWelcomeMsg.setText("Welcome, HR");
        getContentPane().add(lblWelcomeMsg);
        lblWelcomeMsg.setBounds(190, 20, 168, 32);

        lblFName.setFont(new java.awt.Font("Verdana", 1, 25)); // NOI18N
        lblFName.setForeground(new java.awt.Color(117, 102, 149));
        lblFName.setText("firstname");
        getContentPane().add(lblFName);
        lblFName.setBounds(370, 20, 440, 32);

        lblSearchLeave.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblSearchLeave.setForeground(new java.awt.Color(78, 99, 140));
        lblSearchLeave.setText("Search:");
        getContentPane().add(lblSearchLeave);
        lblSearchLeave.setBounds(774, 496, 70, 30);

        lblSearchOvertime.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblSearchOvertime.setForeground(new java.awt.Color(78, 99, 140));
        lblSearchOvertime.setText("Search:");
        getContentPane().add(lblSearchOvertime);
        lblSearchOvertime.setBounds(23, 496, 70, 30);

        lblSearchEmpInfo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblSearchEmpInfo.setForeground(new java.awt.Color(78, 99, 140));
        lblSearchEmpInfo.setText("Search:");
        getContentPane().add(lblSearchEmpInfo);
        lblSearchEmpInfo.setBounds(23, 108, 70, 30);

        lblTip.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        lblTip.setForeground(new java.awt.Color(96, 119, 163));
        lblTip.setText("Note: Drag the columns horizontally to view information.");
        lblTip.setToolTipText("");
        getContentPane().add(lblTip);
        lblTip.setBounds(25, 440, 300, 33);
        lblTip.getAccessibleContext().setAccessibleName("Tips");

        lblIDniEmployee.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblIDniEmployee.setForeground(new java.awt.Color(60, 45, 45));
        lblIDniEmployee.setText("Employee ID:");
        getContentPane().add(lblIDniEmployee);
        lblIDniEmployee.setBounds(190, 50, 96, 18);

        lblEid.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lblEid.setForeground(new java.awt.Color(60, 45, 45));
        lblEid.setText("###");
        getContentPane().add(lblEid);
        lblEid.setBounds(290, 50, 33, 18);

        lblEmpInfoTable1.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        lblEmpInfoTable1.setForeground(new java.awt.Color(78, 99, 140));
        lblEmpInfoTable1.setText("Employee Information Table");
        getContentPane().add(lblEmpInfoTable1);
        lblEmpInfoTable1.setBounds(630, 110, 294, 22);

        txtSearchLeave.setBackground(new java.awt.Color(255, 255, 255));
        txtSearchLeave.setForeground(new java.awt.Color(55, 45, 45));
        txtSearchLeave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchLeaveActionPerformed(evt);
            }
        });
        getContentPane().add(txtSearchLeave);
        txtSearchLeave.setBounds(830, 497, 120, 30);

        txtSearchOvertime.setBackground(new java.awt.Color(255, 255, 255));
        txtSearchOvertime.setForeground(new java.awt.Color(55, 45, 45));
        txtSearchOvertime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchOvertimeActionPerformed(evt);
            }
        });
        getContentPane().add(txtSearchOvertime);
        txtSearchOvertime.setBounds(80, 497, 120, 30);

        txtSearchEmpInfo.setBackground(new java.awt.Color(255, 255, 255));
        txtSearchEmpInfo.setForeground(new java.awt.Color(55, 45, 45));
        txtSearchEmpInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchEmpInfoActionPerformed(evt);
            }
        });
        getContentPane().add(txtSearchEmpInfo);
        txtSearchEmpInfo.setBounds(80, 110, 120, 30);

        btnRefresh.setBackground(new java.awt.Color(255, 255, 255));
        btnRefresh.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(68, 58, 58));
        btnRefresh.setText("Refresh All");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        getContentPane().add(btnRefresh);
        btnRefresh.setBounds(1340, 110, 140, 30);

        logOut_button.setBackground(new java.awt.Color(255, 255, 255));
        logOut_button.setFont(new java.awt.Font("Avenir Next", 1, 24)); // NOI18N
        logOut_button.setForeground(new java.awt.Color(102, 77, 77));
        logOut_button.setText("Log out");
        logOut_button.setToolTipText("");
        logOut_button.setAutoscrolls(true);
        logOut_button.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        logOut_button.setBorderPainted(false);
        logOut_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOut_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(logOut_button);
        logOut_button.setBounds(1270, 20, 110, 40);

        tblEmpInfo.setBackground(new java.awt.Color(255, 255, 255));
        tblEmpInfo.setForeground(new java.awt.Color(52, 46, 54));
        tblEmpInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Emp ID", "Last Name", "First Name", "Birthday", "Address", "Phone #", "SSS #", "Philhealth #", "TIN #", "Pag-ibig #", "Status", "Position", "Department", "Supervisor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblEmpInfo.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tblEmpInfo.setSelectionForeground(new java.awt.Color(255, 0, 0));
        tblEmpInfo.setShowGrid(false);
        jScrollPane1.setViewportView(tblEmpInfo);
        if (tblEmpInfo.getColumnModel().getColumnCount() > 0) {
            tblEmpInfo.getColumnModel().getColumn(0).setPreferredWidth(17);
            tblEmpInfo.getColumnModel().getColumn(1).setPreferredWidth(50);
            tblEmpInfo.getColumnModel().getColumn(2).setPreferredWidth(50);
            tblEmpInfo.getColumnModel().getColumn(3).setPreferredWidth(45);
            tblEmpInfo.getColumnModel().getColumn(4).setPreferredWidth(90);
            tblEmpInfo.getColumnModel().getColumn(5).setPreferredWidth(60);
            tblEmpInfo.getColumnModel().getColumn(6).setPreferredWidth(60);
            tblEmpInfo.getColumnModel().getColumn(7).setPreferredWidth(65);
            tblEmpInfo.getColumnModel().getColumn(8).setPreferredWidth(90);
            tblEmpInfo.getColumnModel().getColumn(10).setPreferredWidth(35);
            tblEmpInfo.getColumnModel().getColumn(12).setPreferredWidth(40);
        }

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 144, 1460, 300);

        btnAdd.setBackground(new java.awt.Color(255, 255, 255));
        btnAdd.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(64, 60, 74));
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        getContentPane().add(btnAdd);
        btnAdd.setBounds(1230, 450, 76, 28);

        btnEdit.setBackground(new java.awt.Color(255, 255, 255));
        btnEdit.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(64, 60, 74));
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        getContentPane().add(btnEdit);
        btnEdit.setBounds(1310, 450, 76, 28);

        btnDelete.setBackground(new java.awt.Color(255, 255, 255));
        btnDelete.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(64, 60, 74));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        getContentPane().add(btnDelete);
        btnDelete.setBounds(1390, 450, 79, 28);

        lblOvertimeRequestTable.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        lblOvertimeRequestTable.setForeground(new java.awt.Color(78, 99, 140));
        lblOvertimeRequestTable.setText("Overtime Request Table");
        getContentPane().add(lblOvertimeRequestTable);
        lblOvertimeRequestTable.setBounds(270, 500, 250, 23);

        tblOvertimeReq.setBackground(new java.awt.Color(255, 255, 255));
        tblOvertimeReq.setForeground(new java.awt.Color(52, 46, 54));
        tblOvertimeReq.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Emp ID", "Date of Request", "Date of OT", "Time In", "Time Out", "OT Hours", "Status", "Approver", "Date Responded"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOvertimeReq.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tblOvertimeReq.setSelectionForeground(new java.awt.Color(255, 0, 0));
        jScrollPane3.setViewportView(tblOvertimeReq);
        if (tblOvertimeReq.getColumnModel().getColumnCount() > 0) {
            tblOvertimeReq.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        getContentPane().add(jScrollPane3);
        jScrollPane3.setBounds(20, 530, 730, 200);

        btnRespondOvertime.setBackground(new java.awt.Color(255, 255, 255));
        btnRespondOvertime.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnRespondOvertime.setForeground(new java.awt.Color(64, 60, 74));
        btnRespondOvertime.setText("Respond");
        btnRespondOvertime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRespondOvertimeActionPerformed(evt);
            }
        });
        getContentPane().add(btnRespondOvertime);
        btnRespondOvertime.setBounds(20, 740, 94, 28);

        lblLeaveRequestTable.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        lblLeaveRequestTable.setForeground(new java.awt.Color(78, 99, 140));
        lblLeaveRequestTable.setText("Leave Request Table");
        getContentPane().add(lblLeaveRequestTable);
        lblLeaveRequestTable.setBounds(1040, 500, 209, 23);

        tblLeaveReq.setBackground(new java.awt.Color(255, 255, 255));
        tblLeaveReq.setForeground(new java.awt.Color(52, 46, 54));
        tblLeaveReq.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Emp ID", "Date of Request", "Leave Type", "Start Date", "End Date", "Reason", "Status", "Approver", "Approved Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLeaveReq.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tblLeaveReq.setSelectionForeground(new java.awt.Color(255, 0, 0));
        jScrollPane2.setViewportView(tblLeaveReq);
        if (tblLeaveReq.getColumnModel().getColumnCount() > 0) {
            tblLeaveReq.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(770, 530, 710, 200);

        btnRespondLeave.setBackground(new java.awt.Color(255, 255, 255));
        btnRespondLeave.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnRespondLeave.setForeground(new java.awt.Color(64, 60, 74));
        btnRespondLeave.setText("Respond");
        btnRespondLeave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRespondLeaveActionPerformed(evt);
            }
        });
        getContentPane().add(btnRespondLeave);
        btnRespondLeave.setBounds(770, 740, 94, 28);

        btnViewLeaveCredits.setBackground(new java.awt.Color(255, 255, 255));
        btnViewLeaveCredits.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnViewLeaveCredits.setForeground(new java.awt.Color(64, 60, 74));
        btnViewLeaveCredits.setText("View Employee Leave Credits");
        btnViewLeaveCredits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewLeaveCreditsActionPerformed(evt);
            }
        });
        getContentPane().add(btnViewLeaveCredits);
        btnViewLeaveCredits.setBounds(1219, 740, 260, 28);

        jLabel1.setText("hr");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, -20, 1520, 860);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logOut_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOut_buttonActionPerformed
        // TODO add your handling code here:
        FrmLogin logOut = null;
        try {
            logOut = new FrmLogin();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FrmHRpage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FrmHRpage.class.getName()).log(Level.SEVERE, null, ex);
        }
        logOut.show();
        logOut.setLocationRelativeTo(null); // Center the frame

        dispose();
    }//GEN-LAST:event_logOut_buttonActionPerformed

    private boolean isEmployeeFormOpen = false; // Flag to track if the form is open
    
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // Check if the form is already open
        if (!isEmployeeFormOpen) {
            // Set the flag to indicate that the form is now open
            isEmployeeFormOpen = true;

            // Open the form to input new employee data
            FrmNewEmployee _addEmployee = new FrmNewEmployee(this);
            _addEmployee.setVisible(true);
            _addEmployee.setLocationRelativeTo(null); // Center the frame

            // Add a window listener to detect when the form is closed
            _addEmployee.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    // Reset the flag when the form is closed
                    isEmployeeFormOpen = false;
                }
            });
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private boolean isEditFormOpen = false; // Flag to track if the edit form is open
    
    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // Check if the edit form is already open
        if (!isEditFormOpen) {
            // Set the flag to indicate that the edit form is now open
            isEditFormOpen = true;

            // Get the selected row index
            int viewRowIndex = tblEmpInfo.getSelectedRow();

            // Check if a row is selected
            if (viewRowIndex == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row to edit.", "Error", JOptionPane.ERROR_MESSAGE);
                // Reset the flag since the edit form is not opened
                isEditFormOpen = false;
                return;
            }
            
            // Convert view row index to model row index
            int modelRowIndex = tblEmpInfo.convertRowIndexToModel(viewRowIndex);

            // Retrieve the data from the selected row
            DefaultTableModel model = (DefaultTableModel) tblEmpInfo.getModel();
            String[] empInfo = new String[model.getColumnCount()];
            for (int i = 0; i < model.getColumnCount(); i++) {
                empInfo[i] = model.getValueAt(modelRowIndex, i).toString();
            }

            // Pass the reference to the existing FrmHRpage instance to FrmEditEmployee
            FrmEditEmployee editEmployeeForm = new FrmEditEmployee(this); // Pass the reference
            editEmployeeForm.populateFields(empInfo);
            editEmployeeForm.setVisible(true);
            editEmployeeForm.setLocationRelativeTo(null); // Center the frame

            // Add a window listener to detect when the edit form is closed
            editEmployeeForm.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    // Reset the flag when the edit form is closed
                    isEditFormOpen = false;
                }
            });
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private boolean isDeleteFormOpen = false; // Flag to track if the delete form is open
    
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // Check if the delete form is already open
        if (!isDeleteFormOpen) {
            // Set the flag to indicate that the delete form is now open
            isDeleteFormOpen = true;

            // Get the selected row index
            int viewRowIndex = tblEmpInfo.getSelectedRow();

            // Check if a row is selected
            if (viewRowIndex == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                // Reset the flag since the delete form is not opened
                isDeleteFormOpen = false;
                return;
            }
            
            // Convert view row index to model row index
            int modelRowIndex = tblEmpInfo.convertRowIndexToModel(viewRowIndex);

            // Retrieve the data from the selected row
            DefaultTableModel model = (DefaultTableModel) tblEmpInfo.getModel();
            String[] empInfo = new String[model.getColumnCount()];
            for (int i = 0; i < model.getColumnCount(); i++) {
                empInfo[i] = model.getValueAt(modelRowIndex, i).toString();
            }

            // Pass the reference to the existing FrmHRpage instance to FrmDeleteEmployee
            FrmDeleteEmployee deleteEmployeeForm = new FrmDeleteEmployee(this); // Pass the reference
            deleteEmployeeForm.populateFields(empInfo);
            deleteEmployeeForm.setVisible(true);
            deleteEmployeeForm.setLocationRelativeTo(null); // Center the frame

            // Add a window listener to detect when the delete form is closed
            deleteEmployeeForm.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    // Reset the flag when the delete form is closed
                    isDeleteFormOpen = false;
                }
            });
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private boolean isEditOvertimeFormOpen = false; // Flag to track if the edit overtime form is open
    
    private void btnRespondOvertimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRespondOvertimeActionPerformed
        // Check if the edit overtime form is already open
        if (!isEditOvertimeFormOpen) {
            // Set the flag to indicate that the edit overtime form is now open
            isEditOvertimeFormOpen = true;

            // Get the selected row index
            int viewRowIndex = tblOvertimeReq.getSelectedRow();

            // Check if a row is selected
            if (viewRowIndex == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row to respond.", "Error", JOptionPane.ERROR_MESSAGE);
                // Reset the flag since the edit overtime form is not opened
                isEditOvertimeFormOpen = false;
                return;
            }
            
            // Convert view row index to model row index
            int modelRowIndex = tblOvertimeReq.convertRowIndexToModel(viewRowIndex);

            // Retrieve the data from the selected row
            DefaultTableModel model = (DefaultTableModel) tblOvertimeReq.getModel();
            String[] overtimeInfo = new String[model.getColumnCount()];
            for (int i = 0; i < model.getColumnCount(); i++) {
                Object value = model.getValueAt(modelRowIndex, i);
                overtimeInfo[i] = (value != null) ? value.toString() : ""; // Handle null values gracefully
            }

            // Pass the reference to the existing FrmHRpage instance to FrmEditOvertime
            FrmEditOvertime editOvertimeRequest = new FrmEditOvertime(this); // Pass the reference
            editOvertimeRequest.populateFields(overtimeInfo);
            editOvertimeRequest.setVisible(true);
            editOvertimeRequest.setLocationRelativeTo(null); // Center the frame

            // Add a window listener to detect when the edit overtime form is closed
            editOvertimeRequest.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    // Reset the flag when the edit overtime form is closed
                    isEditOvertimeFormOpen = false;
                }
            });
        }
    }//GEN-LAST:event_btnRespondOvertimeActionPerformed

    private boolean isEditLeaveFormOpen = false; // Flag to track if the edit leave form is open
    
    private void btnRespondLeaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRespondLeaveActionPerformed
        // Check if the edit leave form is already open
        if (!isEditLeaveFormOpen) {
            // Set the flag to indicate that the edit leave form is now open
            isEditLeaveFormOpen = true;

            // Get the selected row index
            int viewRowIndex = tblLeaveReq.getSelectedRow();

            // Check if a row is selected
            if (viewRowIndex == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row to respond.", "Error", JOptionPane.ERROR_MESSAGE);
                // Reset the flag since the edit leave form is not opened
                isEditLeaveFormOpen = false;
                return;
            }
            
            // Convert view row index to model row index
            int modelRowIndex = tblLeaveReq.convertRowIndexToModel(viewRowIndex);

            // Retrieve the data from the selected row
            DefaultTableModel model = (DefaultTableModel) tblLeaveReq.getModel();
            String[] leaveInfo = new String[model.getColumnCount()];
            for (int i = 0; i < model.getColumnCount(); i++) {
                Object value = model.getValueAt(modelRowIndex, i);
                leaveInfo[i] = (value != null) ? value.toString() : ""; // Handle null values gracefully
            }

            // Pass the reference to the existing FrmHRpage instance to FrmEditLeave
            FrmEditLeave editLeaveRequest = new FrmEditLeave(this); // Pass the reference
            editLeaveRequest.populateFields(leaveInfo);
            editLeaveRequest.setVisible(true);
            editLeaveRequest.setLocationRelativeTo(null); // Center the frame

            // Add a window listener to detect when the edit leave form is closed
            editLeaveRequest.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    // Reset the flag when the edit leave form is closed
                    isEditLeaveFormOpen = false;
                }
            });
        }
    }//GEN-LAST:event_btnRespondLeaveActionPerformed

    private boolean isViewLeaveCreditsFrameOpen = false; // Flag to track if the view leave credits frame is open
    
    private void btnViewLeaveCreditsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewLeaveCreditsActionPerformed
        // Check if the view leave credits frame is already open
        if (!isViewLeaveCreditsFrameOpen) {
            // Set the flag to indicate that the view leave credits frame is now open
            isViewLeaveCreditsFrameOpen = true;

            // Query the database to retrieve all leave credits
            try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
                String sql = "SELECT EmployeeNum, LastName, FirstName, Year, LeaveCreditsRemaining FROM leave_credits_used";
                try (PreparedStatement statement = conn.prepareStatement(sql);
                     ResultSet resultSet = statement.executeQuery()) {
                    // Create a new instance of FrmViewLeaveCredits frame
                    FrmViewLeaveCredits viewLeaveCreditsFrame = new FrmViewLeaveCredits();

                    // Populate the table in FrmViewLeaveCredits with data from the result set
                    viewLeaveCreditsFrame.populateLeaveCredits(resultSet);

                    // Make the frame visible
                    viewLeaveCreditsFrame.setVisible(true);

                    // Add a window listener to detect when the view leave credits frame is closed
                    viewLeaveCreditsFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                            // Reset the flag when the view leave credits frame is closed
                            isViewLeaveCreditsFrameOpen = false;
                        }
                    });
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnViewLeaveCreditsActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Clear current contents of the tables
            clearTable(_hrPage.getTblEmpInfo());
            clearTable(_hrPage.getTblOvertimeReq());
            clearTable(_hrPage.getTblLeaveReq());
            
            // Clear search text fields if populated
            txtSearchEmpInfo.setText("");
            txtSearchOvertime.setText("");
            txtSearchLeave.setText("");

            // Refresh Employee Info
            String sqlEmployees = "SELECT * FROM employee";
            try (PreparedStatement statementEmployees = conn.prepareStatement(sqlEmployees);
                 ResultSet resultSetEmployees = statementEmployees.executeQuery()) {
                displayEmployeeInfo(_hrPage, resultSetEmployees, conn);
            }

            // Refresh Leave Requests
            String sqlLeaveRequests = "SELECT * FROM leave_request";
            try (PreparedStatement statementLeaveRequests = conn.prepareStatement(sqlLeaveRequests);
                 ResultSet resultSetLeaveRequests = statementLeaveRequests.executeQuery()) {
                displayLeaveInfo(_hrPage, resultSetLeaveRequests);
            }

            // Refresh Overtime Requests
            String sqlOvertimeRequests = "SELECT * FROM overtime_request";
            try (PreparedStatement statementOvertimeRequests = conn.prepareStatement(sqlOvertimeRequests);
                 ResultSet resultSetOvertimeRequests = statementOvertimeRequests.executeQuery()) {
                displayOvertimeInfo(_hrPage, resultSetOvertimeRequests);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txtSearchEmpInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchEmpInfoActionPerformed
                                
    }//GEN-LAST:event_txtSearchEmpInfoActionPerformed

    // Method to perform search operation in TblEmpInfo table
    private void searchEmployeeInfo(String searchText) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) _hrPage.getTblEmpInfo().getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        _hrPage.getTblEmpInfo().setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText)); // Case-insensitive search
    }
    
    private void txtSearchOvertimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchOvertimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchOvertimeActionPerformed

    // Method to perform search operation in TblEmpInfo table
    private void searchOvertime(String searchText) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) _hrPage.getTblOvertimeReq().getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        _hrPage.getTblOvertimeReq().setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText)); // Case-insensitive search
    }
    
    private void txtSearchLeaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchLeaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchLeaveActionPerformed

    // Method to perform search operation in TblEmpInfo table
    private void searchLeave(String searchText) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) _hrPage.getTblLeaveReq().getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        _hrPage.getTblLeaveReq().setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText)); // Case-insensitive search
    }
    
    private void clearTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // This will clear the table
    }

    private void displayEmployeeInfo(FrmHRpage _hrPage, ResultSet resultSet, Connection conn) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) _hrPage.getTblEmpInfo().getModel();
        while (resultSet.next()) {
            // Fetch additional information from the Role table
            String position = "";
            String department = "";
            int roleID = resultSet.getInt("RoleID");

            String sqlRoleInfo = "SELECT Position, Department FROM role WHERE RoleID = ?";
            try (PreparedStatement statementRoleInfo = conn.prepareStatement(sqlRoleInfo)) {
                statementRoleInfo.setInt(1, roleID);
                try (ResultSet resultSetRoleInfo = statementRoleInfo.executeQuery()) {
                    if (resultSetRoleInfo.next()) {
                        position = resultSetRoleInfo.getString("Position");
                        department = resultSetRoleInfo.getString("Department");
                    }
                }
            }

            // Add the employee information to the table
            model.addRow(new Object[]{
                resultSet.getInt("EmployeeNum"),
                resultSet.getString("LastName"),
                resultSet.getString("FirstName"),
                resultSet.getString("Birthday"),
                resultSet.getString("Address"),
                resultSet.getString("PhoneNumber"),
                resultSet.getString("SssNumber"),
                resultSet.getString("PhilhealthNumber"),
                resultSet.getString("TinNumber"),
                resultSet.getString("PagibigNumber"),
                resultSet.getString("Status"),
                position,     // Add Position fetched from Role table
                department,   // Add Department fetched from Role table
                resultSet.getString("ImmediateSupervisor")
            });
        }
    }

    private void displayLeaveInfo(FrmHRpage _hrPage, ResultSet resultSet) throws SQLException {
        List<Object[]> leaveData = new ArrayList<>();

        // Extract data from ResultSet
        while (resultSet.next()) {
            leaveData.add(new Object[]{
                resultSet.getInt("EmployeeNum"),
                resultSet.getString("RequestDate"),
                resultSet.getString("LeaveType"),
                resultSet.getString("StartDate"),
                resultSet.getString("EndDate"),
                resultSet.getString("Reason"),
                resultSet.getString("Status"),
                resultSet.getString("Approver"),
                resultSet.getString("DateResponded")
            });
        }

        // Update the UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            DefaultTableModel model = (DefaultTableModel) _hrPage.getTblLeaveReq().getModel();
            for (Object[] row : leaveData) {
                model.addRow(row);
            }
            // Refresh the table to ensure the data is displayed
            model.fireTableDataChanged();
        });
    }

    private void displayOvertimeInfo(FrmHRpage _hrPage, ResultSet resultSet) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) _hrPage.getTblOvertimeReq().getModel();
        System.out.println("Populating Overtime Info Table"); // Debug statement

        while (resultSet.next()) {

            model.addRow(new Object[]{
                resultSet.getInt("EmployeeNum"),
                resultSet.getString("RequestDate"),
                resultSet.getString("OTDate"),
                resultSet.getString("TimeIn"),
                resultSet.getString("TimeOut"),
                resultSet.getString("OTHrs"),
                resultSet.getString("Status"),
                resultSet.getString("Approver"),
                resultSet.getString("DateResponded")
            });
        }

        // Print model data for debugging
        int rowCount = model.getRowCount();
        System.out.println("Row count after adding data: " + rowCount);
        for (int i = 0; i < rowCount; i++) {
            System.out.print("Row " + i + ": ");
            for (int j = 0; j < model.getColumnCount(); j++) {
                System.out.print(model.getValueAt(i, j) + " ");
            }
            System.out.println();
        }

        // Refresh the table to ensure the data is displayed
        model.fireTableDataChanged();
    }


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
            java.util.logging.Logger.getLogger(FrmHRpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmHRpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmHRpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmHRpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmHRpage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRespondLeave;
    private javax.swing.JButton btnRespondOvertime;
    private javax.swing.JButton btnViewLeaveCredits;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblEid;
    private javax.swing.JLabel lblEmpInfoTable1;
    private javax.swing.JLabel lblFName;
    private javax.swing.JLabel lblIDniEmployee;
    private javax.swing.JLabel lblLeaveRequestTable;
    private javax.swing.JLabel lblOvertimeRequestTable;
    private javax.swing.JLabel lblSearchEmpInfo;
    private javax.swing.JLabel lblSearchLeave;
    private javax.swing.JLabel lblSearchOvertime;
    private javax.swing.JLabel lblTip;
    private javax.swing.JLabel lblWelcomeMsg;
    private javax.swing.JButton logOut_button;
    public javax.swing.JTable tblEmpInfo;
    private javax.swing.JTable tblLeaveReq;
    private javax.swing.JTable tblOvertimeReq;
    private javax.swing.JTextField txtSearchEmpInfo;
    private javax.swing.JTextField txtSearchLeave;
    private javax.swing.JTextField txtSearchOvertime;
    // End of variables declaration//GEN-END:variables
}
