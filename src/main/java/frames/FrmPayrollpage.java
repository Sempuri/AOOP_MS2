/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author EMAN
 */
public class FrmPayrollpage extends javax.swing.JFrame {
    
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";
    
    FrmPayrollpage _payrollPage = this;

    /**
     * Creates new form FrmPayrollpage
     */
    public FrmPayrollpage() {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null); // Center the frame
        setPreferredSize(new Dimension(1120, 670));
        
        // Set frame properties
        pack();  // Adjust size to the preferred size
    
        displayBgFromDatabase("FrmPayrollpage");
        
        setAlternateRowAndColumnColorRenderer(tblSalaryInfo);
        setAlternateRowAndColumnColorRenderer(tblAllowanceInfo);
        
        // Add a DocumentListener to the employee info search text field
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    searchAccounts(txtSearch.getText().trim());
                } catch (SQLException ex) {
                    Logger.getLogger(FrmPayrollpage.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(FrmPayrollpage.this, "Error searching for employee salary and allowance: " + ex.getMessage(), "Search Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    searchAccounts(txtSearch.getText().trim());
                } catch (SQLException ex) {
                    Logger.getLogger(FrmPayrollpage.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(FrmPayrollpage.this, "Error searching for employee salary and allowance: " + ex.getMessage(), "Search Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not used for plain text components
            }
        });
    }
    
    public JTable getTblAllowanceInfo() {
        return tblAllowanceInfo;
    }

    public JTable getTblSalaryInfo() {
        return tblSalaryInfo;
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
    public JLabel getLblEid() {
        return lblEid;
    }

    public JLabel getLblFName() {
        return lblFName;
    }

    public JTable getTblEmpInfo() {
        return tblSalaryInfo;
    }

// Method to set custom renderer for the date column in tblSalaryInfo
public void setCustomDateRendererForSalary() {
    TableCellRenderer renderer = new DefaultTableCellRenderer() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            if (value instanceof Date) {
                value = formatter.format(value); // Format the date before rendering
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    };

    tblSalaryInfo.getColumnModel().getColumn(6).setCellRenderer(renderer); 
}

// Method to set custom renderer for the date column in tblAllowanceInfo
public void setCustomDateRendererForAllowance() {
    TableCellRenderer renderer = new DefaultTableCellRenderer() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            if (value instanceof Date) {
                value = formatter.format(value); // Format the date before rendering
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    };
    
    tblAllowanceInfo.getColumnModel().getColumn(6).setCellRenderer(renderer);
}

    // For updating a specific row in tblSalaryInfo
    public void updateSalaryTableRow(int rowIndex, String[] salaryInfo) {
        DefaultTableModel model = (DefaultTableModel) tblSalaryInfo.getModel();

        // Update the specified row in the table model
        for (int i = 0; i < Math.min(salaryInfo.length, model.getColumnCount()); i++) {
            model.setValueAt(salaryInfo[i], rowIndex, i);
        }
    }


    // For updating a specific row in tblAllowanceInfo
    public void updateAllowanceTableRow(int rowIndex, String[] allowanceInfo) {
        DefaultTableModel model = (DefaultTableModel) tblAllowanceInfo.getModel();

        // Update the specified row in the table model
        for (int i = 0; i < Math.min(allowanceInfo.length, model.getColumnCount()); i++) {
            model.setValueAt(allowanceInfo[i], rowIndex, i);
        }
    }

    public void addSalaryInfo(String empID, String lastName, String position, 
                              String basicSalary, String grossSemiMR, String hourlyRate,
                              String dateUpdated) {
        DefaultTableModel model = (DefaultTableModel) tblSalaryInfo.getModel();
        model.addRow(new Object[]{empID, lastName, position, basicSalary, grossSemiMR, hourlyRate, dateUpdated});

        TableColumnModel columnModel = tblSalaryInfo.getColumnModel();
        TableColumn dateUpdatedColumn = columnModel.getColumn(6);
        columnModel.removeColumn(dateUpdatedColumn);
        columnModel.addColumn(dateUpdatedColumn);
    }

    public void addAllowanceInfo(String allowanceID, String employeeNum, String position, String riceSubsidy, String phoneAllowance, 
                                 String clothingAllowance, String dateUpdated) {
        DefaultTableModel model = (DefaultTableModel) tblAllowanceInfo.getModel();
        model.addRow(new Object[]{allowanceID, employeeNum, position, riceSubsidy, phoneAllowance, clothingAllowance, dateUpdated});

        TableColumnModel columnModel = tblAllowanceInfo.getColumnModel();
        TableColumn dateUpdatedColumn = columnModel.getColumn(6);
        columnModel.removeColumn(dateUpdatedColumn);
        columnModel.addColumn(dateUpdatedColumn);
    }

    public void addEmployeeInfo(String employeeNum, String lastName, String position, 
                                String riceSubsidy, String phoneAllowance, String clothingAllowance, 
                                String basicSalary, String grossSemiMR, String hourlyRate, String dateUpdated) {
        DefaultTableModel salaryModel = (DefaultTableModel) tblSalaryInfo.getModel();
        salaryModel.addRow(new Object[]{employeeNum, lastName, position, basicSalary, grossSemiMR, hourlyRate, dateUpdated});

        DefaultTableModel allowanceModel = (DefaultTableModel) tblAllowanceInfo.getModel();
        allowanceModel.addRow(new Object[]{employeeNum, lastName, riceSubsidy, phoneAllowance, clothingAllowance, dateUpdated});
    }




   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSlider1 = new javax.swing.JSlider();
        lblWelcomeMsg = new javax.swing.JLabel();
        jYCYear = new com.toedter.calendar.JYearChooser();
        lblPeriodEndDate = new javax.swing.JLabel();
        jMCMonth = new com.toedter.calendar.JMonthChooser();
        lblPeriodStartDate = new javax.swing.JLabel();
        lblFName = new javax.swing.JLabel();
        lblIDniEmployee = new javax.swing.JLabel();
        lblEid = new javax.swing.JLabel();
        btnDownloadMonthlyReport = new javax.swing.JButton();
        lblSearch = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnRefresh = new javax.swing.JButton();
        logOut_button = new javax.swing.JButton();
        lblAllTable = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSalaryInfo = new javax.swing.JTable();
        btnEditSalaryInfo = new javax.swing.JButton();
        btnEditAllowanceInfo = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAllowanceInfo = new javax.swing.JTable();
        lblSalTable = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1120, 670));
        setMinimumSize(new java.awt.Dimension(1120, 670));
        setPreferredSize(new java.awt.Dimension(1120, 670));
        setSize(new java.awt.Dimension(1120, 670));
        getContentPane().setLayout(null);

        lblWelcomeMsg.setFont(new java.awt.Font("Verdana", 0, 25)); // NOI18N
        lblWelcomeMsg.setForeground(new java.awt.Color(60, 45, 45));
        lblWelcomeMsg.setText("Welcome, Payroll");
        getContentPane().add(lblWelcomeMsg);
        lblWelcomeMsg.setBounds(160, 20, 217, 32);

        jYCYear.setStartYear(2022);
        jYCYear.setValue(2022);
        getContentPane().add(jYCYear);
        jYCYear.setBounds(1020, 110, 60, 20);

        lblPeriodEndDate.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPeriodEndDate.setForeground(new java.awt.Color(92, 78, 78));
        lblPeriodEndDate.setText("Year:");
        getContentPane().add(lblPeriodEndDate);
        lblPeriodEndDate.setBounds(980, 110, 130, 20);

        jMCMonth.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        getContentPane().add(jMCMonth);
        jMCMonth.setBounds(810, 110, 150, 20);

        lblPeriodStartDate.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPeriodStartDate.setForeground(new java.awt.Color(92, 78, 78));
        lblPeriodStartDate.setText("Month:");
        getContentPane().add(lblPeriodStartDate);
        lblPeriodStartDate.setBounds(760, 110, 141, 20);

        lblFName.setFont(new java.awt.Font("Verdana", 1, 25)); // NOI18N
        lblFName.setForeground(new java.awt.Color(196, 135, 81));
        lblFName.setText("firstname");
        getContentPane().add(lblFName);
        lblFName.setBounds(390, 20, 470, 32);

        lblIDniEmployee.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        lblIDniEmployee.setForeground(new java.awt.Color(60, 45, 45));
        lblIDniEmployee.setText("Employee ID:");
        getContentPane().add(lblIDniEmployee);
        lblIDniEmployee.setBounds(160, 50, 96, 18);

        lblEid.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        lblEid.setForeground(new java.awt.Color(60, 45, 45));
        lblEid.setText("###");
        getContentPane().add(lblEid);
        lblEid.setBounds(260, 50, 33, 18);

        btnDownloadMonthlyReport.setBackground(new java.awt.Color(255, 255, 255));
        btnDownloadMonthlyReport.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnDownloadMonthlyReport.setForeground(new java.awt.Color(105, 86, 86));
        btnDownloadMonthlyReport.setText("Generate Monthly Payroll Report");
        btnDownloadMonthlyReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownloadMonthlyReportActionPerformed(evt);
            }
        });
        getContentPane().add(btnDownloadMonthlyReport);
        btnDownloadMonthlyReport.setBounds(830, 150, 230, 30);

        lblSearch.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblSearch.setForeground(new java.awt.Color(92, 78, 78));
        lblSearch.setText("Search:");
        getContentPane().add(lblSearch);
        lblSearch.setBounds(30, 120, 60, 30);

        txtSearch.setBackground(new java.awt.Color(255, 255, 255));
        txtSearch.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        txtSearch.setForeground(new java.awt.Color(58, 46, 46));
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        getContentPane().add(txtSearch);
        txtSearch.setBounds(90, 120, 120, 27);

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
        btnRefresh.setBounds(220, 120, 110, 30);

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
        logOut_button.setBounds(960, 30, 100, 30);

        lblAllTable.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblAllTable.setForeground(new java.awt.Color(92, 78, 78));
        lblAllTable.setText("Allowance Table");
        getContentPane().add(lblAllTable);
        lblAllTable.setBounds(20, 390, 200, 30);

        tblSalaryInfo.setBackground(new java.awt.Color(243, 236, 217));
        tblSalaryInfo.setForeground(new java.awt.Color(47, 40, 40));
        tblSalaryInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Emp ID", "Last Name", "Position", "Basic Salary", "Gross Semi MR", "Hourly Rate", "Date Updated"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSalaryInfo.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tblSalaryInfo.setSelectionForeground(new java.awt.Color(255, 0, 0));
        jScrollPane1.setViewportView(tblSalaryInfo);
        if (tblSalaryInfo.getColumnModel().getColumnCount() > 0) {
            tblSalaryInfo.getColumnModel().getColumn(2).setPreferredWidth(120);
            tblSalaryInfo.getColumnModel().getColumn(5).setPreferredWidth(65);
        }

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 220, 1080, 140);

        btnEditSalaryInfo.setBackground(new java.awt.Color(255, 255, 255));
        btnEditSalaryInfo.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnEditSalaryInfo.setForeground(new java.awt.Color(105, 86, 86));
        btnEditSalaryInfo.setText("Edit Salary Information");
        btnEditSalaryInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditSalaryInfoActionPerformed(evt);
            }
        });
        getContentPane().add(btnEditSalaryInfo);
        btnEditSalaryInfo.setBounds(860, 370, 240, 28);

        btnEditAllowanceInfo.setBackground(new java.awt.Color(255, 255, 255));
        btnEditAllowanceInfo.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnEditAllowanceInfo.setForeground(new java.awt.Color(105, 86, 86));
        btnEditAllowanceInfo.setText("Edit Allowance Information");
        btnEditAllowanceInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditAllowanceInfoActionPerformed(evt);
            }
        });
        getContentPane().add(btnEditAllowanceInfo);
        btnEditAllowanceInfo.setBounds(860, 580, 240, 28);

        tblAllowanceInfo.setBackground(new java.awt.Color(243, 236, 217));
        tblAllowanceInfo.setForeground(new java.awt.Color(47, 40, 40));
        tblAllowanceInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Emp ID", "Last Name", "Position", "Rice Subsidy", "Phone Allowance", "Clothing Allowance", "Date Updated"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAllowanceInfo.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tblAllowanceInfo.setSelectionForeground(new java.awt.Color(255, 0, 0));
        jScrollPane2.setViewportView(tblAllowanceInfo);
        if (tblAllowanceInfo.getColumnModel().getColumnCount() > 0) {
            tblAllowanceInfo.getColumnModel().getColumn(2).setPreferredWidth(120);
        }

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(20, 430, 1080, 140);

        lblSalTable.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblSalTable.setForeground(new java.awt.Color(92, 78, 78));
        lblSalTable.setText("Salary Table");
        getContentPane().add(lblSalTable);
        lblSalTable.setBounds(20, 180, 140, 30);

        jLabel1.setText("payroll");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, -10, 1130, 690);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logOut_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOut_buttonActionPerformed
        // TODO add your handling code here:
        FrmLogin logOut = null;
        try {
            logOut = new FrmLogin();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FrmPayrollpage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FrmPayrollpage.class.getName()).log(Level.SEVERE, null, ex);
        }
        logOut.show();
        logOut.setLocationRelativeTo(null); // Center the frame

        dispose();
    }//GEN-LAST:event_logOut_buttonActionPerformed

    private boolean isEditSalaryFormOpen = false; // Flag to track if the edit salary form is open
    
    private void btnEditSalaryInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditSalaryInfoActionPerformed
        // Check if the edit salary form is already open
        if (!isEditSalaryFormOpen) {
            // Set the flag to indicate that the edit salary form is now open
            isEditSalaryFormOpen = true;

            // Get the selected row index
            int viewRowIndex = tblSalaryInfo.getSelectedRow();

            // Check if a row is selected
            if (viewRowIndex == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row to edit.", "Error", JOptionPane.ERROR_MESSAGE);
                // Reset the flag since the edit salary form is not opened
                isEditSalaryFormOpen = false;
                return;
            }
            
            // Convert view row index to model row index
            int modelRowIndex = tblSalaryInfo.convertRowIndexToModel(viewRowIndex);

            // Retrieve the data from the selected row
            DefaultTableModel model = (DefaultTableModel) tblSalaryInfo.getModel();
            String[] empInfo = new String[model.getColumnCount()];
            for (int i = 0; i < model.getColumnCount(); i++) {
                empInfo[i] = model.getValueAt(modelRowIndex, i).toString();
            }

            // Pass the reference to the existing FrmHRpage instance to FrmEditEmployee
            FrmEditSalary editSalaryForm = new FrmEditSalary(this); // Pass the reference
            editSalaryForm.populateFields(empInfo);
            editSalaryForm.setVisible(true);
            editSalaryForm.setLocationRelativeTo(null); // Center the frame

            // Add a window listener to detect when the edit salary form is closed
            editSalaryForm.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    // Reset the flag when the edit salary form is closed
                    isEditSalaryFormOpen = false;
                }
            });
        }
    }//GEN-LAST:event_btnEditSalaryInfoActionPerformed

    private boolean isEditAllowanceFormOpen = false; // Flag to track if the edit allowance form is open
     
    private void btnEditAllowanceInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditAllowanceInfoActionPerformed
        // Check if the edit allowance form is already open
        if (!isEditAllowanceFormOpen) {
            // Set the flag to indicate that the edit allowance form is now open
            isEditAllowanceFormOpen = true;

            // Get the selected row index
            int viewRowIndex = tblAllowanceInfo.getSelectedRow();

            // Check if a row is selected
            if (viewRowIndex == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row to edit.", "Error", JOptionPane.ERROR_MESSAGE);
                // Reset the flag since the edit allowance form is not opened
                isEditAllowanceFormOpen = false;
                return;
            }
            
            // Convert view row index to model row index
            int modelRowIndex = tblAllowanceInfo.convertRowIndexToModel(viewRowIndex);

            // Retrieve the data from the selected row
            DefaultTableModel model = (DefaultTableModel) tblAllowanceInfo.getModel();
            String[] empInfo = new String[model.getColumnCount()];
            for (int i = 0; i < model.getColumnCount(); i++) {
                empInfo[i] = model.getValueAt(modelRowIndex, i).toString();
            }

            // Pass the reference to the existing FrmHRpage instance to FrmEditEmployee
            FrmEditAllowance editAllowanceForm = new FrmEditAllowance(this); // Pass the reference
            editAllowanceForm.populateFields(empInfo);
            editAllowanceForm.setVisible(true);
            editAllowanceForm.setLocationRelativeTo(null); // Center the frame

            // Add a window listener to detect when the edit allowance form is closed
            editAllowanceForm.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    // Reset the flag when the edit allowance form is closed
                    isEditAllowanceFormOpen = false;
                }
            });
        }
    }//GEN-LAST:event_btnEditAllowanceInfoActionPerformed
   
    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // Clear existing data from the salary table
        DefaultTableModel salaryModel = (DefaultTableModel) tblSalaryInfo.getModel();
        salaryModel.setRowCount(0); // Set row count to 0 to clear all rows

        // Clear existing data from the allowance table
        DefaultTableModel allowanceModel = (DefaultTableModel) tblAllowanceInfo.getModel();
        allowanceModel.setRowCount(0); // Set row count to 0 to clear all rows
        
        txtSearch.setText("");

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Retrieve updated data for salary table
            String salarySQL = "SELECT e.EmployeeNum, e.LastName, r.Position, s.BasicSalary, s.GrossSMRate, s.HourlyRate, s.DateUpdated " +
                               "FROM employee e " +
                               "JOIN role r ON e.RoleID = r.RoleID " +
                               "JOIN salary s ON e.EmployeeNum = s.EmployeeNum " +
                               "ORDER BY e.EmployeeNum"; // Order by EmployeeNum column
            try (PreparedStatement salaryStatement = conn.prepareStatement(salarySQL);
                 ResultSet salaryResultSet = salaryStatement.executeQuery()) {
                while (salaryResultSet.next()) {
                    Object[] salaryRowData = {
                        salaryResultSet.getInt("EmployeeNum"),
                        salaryResultSet.getString("LastName"),
                        salaryResultSet.getString("Position"),
                        String.format("%.2f", salaryResultSet.getDouble("BasicSalary")),
                        String.format("%.2f", salaryResultSet.getDouble("GrossSMRate")),
                        String.format("%.2f", salaryResultSet.getDouble("HourlyRate")),
                        salaryResultSet.getDate("DateUpdated")
                    };
                    salaryModel.addRow(salaryRowData); // Add row to the salary table model
                }
            }

            // Retrieve updated data for allowance table
            String allowanceSQL = "SELECT e.EmployeeNum, e.LastName, r.Position, a.RiceSubsidy, a.PhoneAllowance, a.ClothingAllowance, a.DateUpdated " +
                                  "FROM employee e " +
                                  "JOIN role r ON e.RoleID = r.RoleID " +
                                  "JOIN allowance a ON e.EmployeeNum = a.EmployeeNum " +
                                  "ORDER BY e.EmployeeNum"; // Order by EmployeeNum column
            try (PreparedStatement allowanceStatement = conn.prepareStatement(allowanceSQL);
                 ResultSet allowanceResultSet = allowanceStatement.executeQuery()) {
                while (allowanceResultSet.next()) {
                    Object[] allowanceRowData = {
                        allowanceResultSet.getInt("EmployeeNum"),
                        allowanceResultSet.getString("LastName"),
                        allowanceResultSet.getString("Position"),
                        String.format("%.2f", allowanceResultSet.getDouble("RiceSubsidy")),
                        String.format("%.2f", allowanceResultSet.getDouble("PhoneAllowance")),
                        String.format("%.2f", allowanceResultSet.getDouble("ClothingAllowance")),
                        allowanceResultSet.getDate("DateUpdated")
                    };
                    allowanceModel.addRow(allowanceRowData); // Add row to the allowance table model
                }
            }
            
            // Print a message to indicate that the tables have been refreshed
            System.out.println("Tables Refreshed.");
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnDownloadMonthlyReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownloadMonthlyReportActionPerformed
        // Get the selected month and year
        int selectedMonth = jMCMonth.getMonth() + 1; // Month is zero-based, so add 1
        int selectedYear = jYCYear.getYear();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String fetchJasperQuery = "SELECT TemplateContent FROM report_templates WHERE TemplateName = ?";
            try (PreparedStatement fetchJasperStmt = connection.prepareStatement(fetchJasperQuery)) {
                fetchJasperStmt.setString(1, "payroll_summary_report"); // Assuming 'monthly_report' is the template name
                try (ResultSet jasperRs = fetchJasperStmt.executeQuery()) {
                    if (jasperRs.next()) {
                        // Read compiled Jasper file content from database
                        InputStream jasperStream = jasperRs.getBinaryStream("TemplateContent");

                        // Load JasperReport from the input stream
                        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

                        // Proceed with filling the report and generating the output
                        generateMonthlyReport(jasperReport, connection, selectedMonth, selectedYear);
                    } else {
                        JOptionPane.showMessageDialog(this, "Jasper template 'monthly_report' not found in database.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (JRException ex) {
                    Logger.getLogger(FrmPayrollpage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException e) {
            // Handle exceptions
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating monthly report: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDownloadMonthlyReportActionPerformed

    private void generateMonthlyReport(JasperReport jasperReport, Connection connection, int selectedMonth, int selectedYear) {
        String fetchMonthlyReportQuery = "SELECT * FROM mps_report WHERE Month = ? AND Year = ?";

        try (PreparedStatement fetchMonthlyReportStmt = connection.prepareStatement(fetchMonthlyReportQuery)) {
            fetchMonthlyReportStmt.setInt(1, selectedMonth);
            fetchMonthlyReportStmt.setInt(2, selectedYear);
            try (ResultSet rs = fetchMonthlyReportStmt.executeQuery()) {
                List<Map<String, ?>> dataList = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> dataMap = new HashMap<>();
                    dataMap.put("EmployeeNum", rs.getInt("EmployeeNum"));
                    dataMap.put("FirstName", rs.getString("FirstName"));
                    dataMap.put("LastName", rs.getString("LastName"));
                    dataMap.put("Position", rs.getString("Position"));
                    dataMap.put("Department", rs.getString("Department"));
                    dataMap.put("Month", rs.getInt("Month"));
                    dataMap.put("Year", rs.getInt("Year"));
                    dataMap.put("GrossSalary", rs.getBigDecimal("GrossSalary"));
                    dataMap.put("SSSnumber", rs.getString("SSSnumber"));
                    dataMap.put("SSSdeduction", rs.getBigDecimal("SSSdeduction"));
                    dataMap.put("PhilhealthNumber", rs.getString("PhilhealthNumber"));
                    dataMap.put("PhilhealthDeduction", rs.getBigDecimal("PhilhealthDeduction"));
                    dataMap.put("PagibigNumber", rs.getString("PagibigNumber"));
                    dataMap.put("PagibigDeduction", rs.getBigDecimal("PagibigDeduction"));
                    dataMap.put("TINnumber", rs.getString("TINnumber"));
                    dataMap.put("WithholdingTax", rs.getBigDecimal("WithholdingTax"));
                    dataMap.put("NetMonthlySalary", rs.getBigDecimal("NetMonthlySalary"));

                    dataList.add(dataMap);
                }

                if (dataList.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No data found for the selected month and year.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(dataList);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);

                Path downloadsPath = Paths.get(System.getProperty("user.home"), "Downloads");
                String fileName = downloadsPath.resolve("Monthly_Report_" + selectedMonth + "_" + selectedYear + ".pdf").toString();
                JasperExportManager.exportReportToPdfFile(jasperPrint, fileName);
                Desktop.getDesktop().open(new File(fileName));

                JOptionPane.showMessageDialog(this, "Monthly report downloaded successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException | JRException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating monthly report: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
        
     // Method to perform search operation in TblSalaryInfo and TblAllowance table
    private void searchAccounts(String searchText) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) _payrollPage.getTblSalaryInfo().getModel();
        TableRowSorter<DefaultTableModel> salarySorter = new TableRowSorter<>(model);
        _payrollPage.getTblSalaryInfo().setRowSorter(salarySorter);
        salarySorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText)); // Case-insensitive search
        
        // Search in TblAllowanceInfo table
        DefaultTableModel allowanceModel = (DefaultTableModel) _payrollPage.getTblAllowanceInfo().getModel();
        TableRowSorter<DefaultTableModel> allowanceSorter = new TableRowSorter<>(allowanceModel);
        _payrollPage.getTblAllowanceInfo().setRowSorter(allowanceSorter);
        allowanceSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText)); // Case-insensitive search
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
            java.util.logging.Logger.getLogger(FrmPayrollpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPayrollpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPayrollpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPayrollpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPayrollpage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnDownloadMonthlyReport;
    private javax.swing.JButton btnEditAllowanceInfo;
    private javax.swing.JButton btnEditSalaryInfo;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JLabel jLabel1;
    public com.toedter.calendar.JMonthChooser jMCMonth;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSlider jSlider1;
    public com.toedter.calendar.JYearChooser jYCYear;
    private javax.swing.JLabel lblAllTable;
    private javax.swing.JLabel lblEid;
    private javax.swing.JLabel lblFName;
    private javax.swing.JLabel lblIDniEmployee;
    private javax.swing.JLabel lblPeriodEndDate;
    private javax.swing.JLabel lblPeriodStartDate;
    private javax.swing.JLabel lblSalTable;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblWelcomeMsg;
    private javax.swing.JButton logOut_button;
    private javax.swing.JTable tblAllowanceInfo;
    private javax.swing.JTable tblSalaryInfo;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
