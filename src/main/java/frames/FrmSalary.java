/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frames;

import classes.Employee;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Raya
 */
public class FrmSalary extends javax.swing.JFrame {
    private Employee[] employee1;
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";

    /**
     * Creates new form FrmSalary
     */
   /* public FrmSalary() {
        initComponents();
        setResizable(false);
        employee1 = Employee.readEmployee("src/main/java/files/Employee.csv");
    } */

    public FrmSalary() {
        initComponents();
        // Call the initializeContributionTables method before using the contribution tables

        // Add ChangeListener to month chooser
            jMCMonth.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                @Override
                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                    if ("month".equals(evt.getPropertyName())) {
                        clearSalaryFields();
                    }
                }
            });

            // Add ChangeListener to year chooser
            jYCYear.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                @Override
                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                    if ("year".equals(evt.getPropertyName())) {
                        clearSalaryFields();
                    }
                }
            });

        //initializeContributionTables();
        setResizable(false);

        setPreferredSize(new Dimension(1120, 670));

        // Set frame properties
        pack();  // Adjust size to the preferred size

        displayBgFromDatabase("FrmSalary");

        // Connect to the database
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Fetch employee data from the database
            String sql = "SELECT * FROM employee";
            try (PreparedStatement statement = conn.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                // Count the number of employees
                int numEmployees = 0;
                    while (resultSet.next()) {
                    numEmployees++;
                    // Process data here
                }

                // Initialize the array to hold employee objects
                employee1 = new Employee[numEmployees];

                // Populate the array with employee data
                int index = 0;
                while (resultSet.next()) {
                    // Extract employee information from the result set
                    int id = resultSet.getInt("EmployeeNum");
                    String lastName = resultSet.getString("LastName");
                    String firstName = resultSet.getString("FirstName");
                    // Assuming other employee attributes are similarly retrieved

                    // Create an Employee object and add it to the array
                    employee1[index] = new Employee(id, firstName, lastName);
                    index++;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // Handle any exceptions
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
                    jLabel2.setIcon(icon);
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
    
    // Method to clear the salary fields
    private void clearSalaryFields() {
        txtHourlyRate.setText("");
        txtMonthlyRate.setText("");
        txtGrossSalary.setText("");
        txtRiceSubsidy.setText("");
        txtPhoneAllowance.setText("");
        txtClothingAllowance.setText("");
        txtTotalAllowance.setText("");
        txtSSSdeductions.setText("");
        txtPagibigDeductions.setText("");
        txtPhilhealthDeductions.setText("");
        txtWithholdingTax.setText("");
        txtTotalDeductions.setText("");
        txtNetSalary.setText("");

        // Reset the salaryViewed flag
        salaryViewed = false;
    }



    //getter
    public JLabel getLblSalEid() {
        return lblSalEid;
    }

    public JLabel getLblSalFName() {
        return lblSalFName;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblWelcomeMsg = new javax.swing.JLabel();
        lblSalFName = new javax.swing.JLabel();
        lblIDniEmployee = new javax.swing.JLabel();
        lblSalEid = new javax.swing.JLabel();
        logOut_button = new javax.swing.JButton();
        lblPeriodStartDate = new javax.swing.JLabel();
        jMCMonth = new com.toedter.calendar.JMonthChooser();
        profile_button = new javax.swing.JButton();
        attendance_button = new javax.swing.JButton();
        request_button = new javax.swing.JButton();
        jYCYear = new com.toedter.calendar.JYearChooser();
        lblPeriodEndDate = new javax.swing.JLabel();
        btnViewSalary = new javax.swing.JButton();
        lblHourlyRate = new javax.swing.JLabel();
        txtHourlyRate = new javax.swing.JTextField();
        lblMonthlyRate = new javax.swing.JLabel();
        txtMonthlyRate = new javax.swing.JTextField();
        lblGrossSalary = new javax.swing.JLabel();
        txtGrossSalary = new javax.swing.JTextField();
        lblNetSalary = new javax.swing.JLabel();
        txtNetSalary = new javax.swing.JTextField();
        lblSSSdeductions = new javax.swing.JLabel();
        txtSSSdeductions = new javax.swing.JTextField();
        lblPagibigDeductions = new javax.swing.JLabel();
        txtPagibigDeductions = new javax.swing.JTextField();
        lblPhilhealthDeductions = new javax.swing.JLabel();
        txtPhilhealthDeductions = new javax.swing.JTextField();
        lblTotalDeductions = new javax.swing.JLabel();
        txtTotalDeductions = new javax.swing.JTextField();
        lblWithholdingTax = new javax.swing.JLabel();
        txtWithholdingTax = new javax.swing.JTextField();
        lblRiceSubsidy = new javax.swing.JLabel();
        txtRiceSubsidy = new javax.swing.JTextField();
        lblPhoneAllowance = new javax.swing.JLabel();
        txtPhoneAllowance = new javax.swing.JTextField();
        lblClothingAllowance = new javax.swing.JLabel();
        txtClothingAllowance = new javax.swing.JTextField();
        lblTotalAllowance = new javax.swing.JLabel();
        txtTotalAllowance = new javax.swing.JTextField();
        btnDownloadPayslip = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(218, 226, 227));
        setBounds(new java.awt.Rectangle(0, 25, 1120, 670));
        setMaximumSize(new java.awt.Dimension(1120, 670));
        setMinimumSize(new java.awt.Dimension(1120, 670));
        setPreferredSize(new java.awt.Dimension(1120, 670));
        setSize(new java.awt.Dimension(1120, 670));
        getContentPane().setLayout(null);

        lblWelcomeMsg.setFont(new java.awt.Font("Verdana", 0, 25)); // NOI18N
        lblWelcomeMsg.setForeground(new java.awt.Color(60, 45, 45));
        lblWelcomeMsg.setText("Welcome,");
        getContentPane().add(lblWelcomeMsg);
        lblWelcomeMsg.setBounds(160, 20, 123, 32);

        lblSalFName.setFont(new java.awt.Font("Verdana", 1, 25)); // NOI18N
        lblSalFName.setForeground(new java.awt.Color(185, 136, 136));
        lblSalFName.setText("firstname");
        getContentPane().add(lblSalFName);
        lblSalFName.setBounds(290, 20, 500, 32);

        lblIDniEmployee.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        lblIDniEmployee.setForeground(new java.awt.Color(60, 45, 45));
        lblIDniEmployee.setText("Employee ID:");
        getContentPane().add(lblIDniEmployee);
        lblIDniEmployee.setBounds(160, 50, 91, 17);

        lblSalEid.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        lblSalEid.setForeground(new java.awt.Color(60, 45, 45));
        lblSalEid.setText("###");
        getContentPane().add(lblSalEid);
        lblSalEid.setBounds(260, 48, 33, 20);

        logOut_button.setBackground(new java.awt.Color(255, 255, 255));
        logOut_button.setFont(new java.awt.Font("Avenir Next", 1, 24)); // NOI18N
        logOut_button.setForeground(new java.awt.Color(102, 77, 77));
        logOut_button.setText("Log out");
        logOut_button.setAutoscrolls(true);
        logOut_button.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        logOut_button.setBorderPainted(false);
        logOut_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOut_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(logOut_button);
        logOut_button.setBounds(980, 30, 100, 30);

        lblPeriodStartDate.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPeriodStartDate.setForeground(new java.awt.Color(255, 255, 255));
        lblPeriodStartDate.setText("Month:");
        getContentPane().add(lblPeriodStartDate);
        lblPeriodStartDate.setBounds(110, 190, 180, 15);

        jMCMonth.setBackground(new java.awt.Color(255, 255, 255));
        jMCMonth.setForeground(new java.awt.Color(49, 39, 39));
        jMCMonth.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        getContentPane().add(jMCMonth);
        jMCMonth.setBounds(110, 210, 150, 22);

        profile_button.setBackground(new java.awt.Color(255, 255, 255));
        profile_button.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        profile_button.setForeground(new java.awt.Color(93, 72, 72));
        profile_button.setText("Profile");
        profile_button.setMaximumSize(new java.awt.Dimension(131, 30));
        profile_button.setMinimumSize(new java.awt.Dimension(131, 30));
        profile_button.setPreferredSize(new java.awt.Dimension(131, 30));
        profile_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profile_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(profile_button);
        profile_button.setBounds(210, 560, 173, 50);

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
        attendance_button.setBounds(460, 560, 173, 50);

        request_button.setBackground(new java.awt.Color(255, 255, 255));
        request_button.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        request_button.setForeground(new java.awt.Color(93, 72, 72));
        request_button.setText("Request");
        request_button.setMaximumSize(new java.awt.Dimension(131, 30));
        request_button.setMinimumSize(new java.awt.Dimension(131, 30));
        request_button.setPreferredSize(new java.awt.Dimension(131, 30));
        request_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                request_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(request_button);
        request_button.setBounds(710, 560, 173, 50);

        jYCYear.setBackground(new java.awt.Color(255, 255, 255));
        jYCYear.setStartYear(2022);
        jYCYear.setValue(2022);
        getContentPane().add(jYCYear);
        jYCYear.setBounds(270, 210, 60, 20);

        lblPeriodEndDate.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPeriodEndDate.setForeground(new java.awt.Color(255, 255, 255));
        lblPeriodEndDate.setText("Year:");
        getContentPane().add(lblPeriodEndDate);
        lblPeriodEndDate.setBounds(270, 190, 170, 15);

        btnViewSalary.setBackground(new java.awt.Color(255, 255, 255));
        btnViewSalary.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnViewSalary.setForeground(new java.awt.Color(49, 40, 40));
        btnViewSalary.setText("View Salary");
        btnViewSalary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewSalaryActionPerformed(evt);
            }
        });
        getContentPane().add(btnViewSalary);
        btnViewSalary.setBounds(170, 240, 110, 20);

        lblHourlyRate.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblHourlyRate.setForeground(new java.awt.Color(255, 255, 255));
        lblHourlyRate.setText("Hourly Rate:");
        getContentPane().add(lblHourlyRate);
        lblHourlyRate.setBounds(100, 270, 140, 18);

        txtHourlyRate.setEditable(false);
        txtHourlyRate.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        getContentPane().add(txtHourlyRate);
        txtHourlyRate.setBounds(100, 290, 240, 30);

        lblMonthlyRate.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblMonthlyRate.setForeground(new java.awt.Color(255, 255, 255));
        lblMonthlyRate.setText("Monthly Rate:");
        getContentPane().add(lblMonthlyRate);
        lblMonthlyRate.setBounds(100, 330, 110, 15);

        txtMonthlyRate.setEditable(false);
        txtMonthlyRate.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        getContentPane().add(txtMonthlyRate);
        txtMonthlyRate.setBounds(100, 350, 240, 30);

        lblGrossSalary.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblGrossSalary.setForeground(new java.awt.Color(255, 255, 255));
        lblGrossSalary.setText("Gross Salary:");
        getContentPane().add(lblGrossSalary);
        lblGrossSalary.setBounds(100, 390, 140, 18);

        txtGrossSalary.setEditable(false);
        txtGrossSalary.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        getContentPane().add(txtGrossSalary);
        txtGrossSalary.setBounds(100, 410, 240, 30);

        lblNetSalary.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblNetSalary.setForeground(new java.awt.Color(255, 255, 255));
        lblNetSalary.setText("Net Salary:");
        getContentPane().add(lblNetSalary);
        lblNetSalary.setBounds(100, 450, 124, 20);

        txtNetSalary.setEditable(false);
        txtNetSalary.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        getContentPane().add(txtNetSalary);
        txtNetSalary.setBounds(100, 470, 240, 30);

        lblSSSdeductions.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblSSSdeductions.setForeground(new java.awt.Color(255, 255, 255));
        lblSSSdeductions.setText("SSS Deductions:");
        getContentPane().add(lblSSSdeductions);
        lblSSSdeductions.setBounds(440, 200, 170, 18);

        txtSSSdeductions.setEditable(false);
        txtSSSdeductions.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtSSSdeductions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSSSdeductionsActionPerformed(evt);
            }
        });
        getContentPane().add(txtSSSdeductions);
        txtSSSdeductions.setBounds(440, 220, 240, 30);

        lblPagibigDeductions.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPagibigDeductions.setForeground(new java.awt.Color(255, 255, 255));
        lblPagibigDeductions.setText("Pag-IBIG Deductions:");
        getContentPane().add(lblPagibigDeductions);
        lblPagibigDeductions.setBounds(440, 260, 160, 15);

        txtPagibigDeductions.setEditable(false);
        txtPagibigDeductions.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        getContentPane().add(txtPagibigDeductions);
        txtPagibigDeductions.setBounds(440, 280, 240, 30);

        lblPhilhealthDeductions.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPhilhealthDeductions.setForeground(new java.awt.Color(255, 255, 255));
        lblPhilhealthDeductions.setText("PhilHealth Deductions:");
        getContentPane().add(lblPhilhealthDeductions);
        lblPhilhealthDeductions.setBounds(440, 320, 160, 15);

        txtPhilhealthDeductions.setEditable(false);
        txtPhilhealthDeductions.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        getContentPane().add(txtPhilhealthDeductions);
        txtPhilhealthDeductions.setBounds(440, 340, 240, 30);

        lblTotalDeductions.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblTotalDeductions.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalDeductions.setText("Total Deductions:");
        getContentPane().add(lblTotalDeductions);
        lblTotalDeductions.setBounds(440, 440, 177, 23);

        txtTotalDeductions.setEditable(false);
        txtTotalDeductions.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        getContentPane().add(txtTotalDeductions);
        txtTotalDeductions.setBounds(440, 460, 240, 30);

        lblWithholdingTax.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblWithholdingTax.setForeground(new java.awt.Color(255, 255, 255));
        lblWithholdingTax.setText("Withholding Tax:");
        getContentPane().add(lblWithholdingTax);
        lblWithholdingTax.setBounds(440, 380, 130, 15);

        txtWithholdingTax.setEditable(false);
        txtWithholdingTax.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        getContentPane().add(txtWithholdingTax);
        txtWithholdingTax.setBounds(440, 400, 240, 30);

        lblRiceSubsidy.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblRiceSubsidy.setForeground(new java.awt.Color(255, 255, 255));
        lblRiceSubsidy.setText("Rice Subsidy:");
        getContentPane().add(lblRiceSubsidy);
        lblRiceSubsidy.setBounds(780, 200, 140, 18);

        txtRiceSubsidy.setEditable(false);
        txtRiceSubsidy.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtRiceSubsidy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRiceSubsidyActionPerformed(evt);
            }
        });
        getContentPane().add(txtRiceSubsidy);
        txtRiceSubsidy.setBounds(780, 220, 240, 30);

        lblPhoneAllowance.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPhoneAllowance.setForeground(new java.awt.Color(255, 255, 255));
        lblPhoneAllowance.setText("Phone Allowance:");
        getContentPane().add(lblPhoneAllowance);
        lblPhoneAllowance.setBounds(780, 260, 140, 15);

        txtPhoneAllowance.setEditable(false);
        txtPhoneAllowance.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtPhoneAllowance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneAllowanceActionPerformed(evt);
            }
        });
        getContentPane().add(txtPhoneAllowance);
        txtPhoneAllowance.setBounds(780, 280, 240, 30);

        lblClothingAllowance.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblClothingAllowance.setForeground(new java.awt.Color(255, 255, 255));
        lblClothingAllowance.setText("Clothing Allowance:");
        getContentPane().add(lblClothingAllowance);
        lblClothingAllowance.setBounds(780, 320, 190, 18);

        txtClothingAllowance.setEditable(false);
        txtClothingAllowance.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtClothingAllowance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClothingAllowanceActionPerformed(evt);
            }
        });
        getContentPane().add(txtClothingAllowance);
        txtClothingAllowance.setBounds(780, 340, 240, 30);

        lblTotalAllowance.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblTotalAllowance.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalAllowance.setText("Total Allowance:");
        getContentPane().add(lblTotalAllowance);
        lblTotalAllowance.setBounds(780, 380, 170, 20);

        txtTotalAllowance.setEditable(false);
        txtTotalAllowance.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtTotalAllowance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalAllowanceActionPerformed(evt);
            }
        });
        getContentPane().add(txtTotalAllowance);
        txtTotalAllowance.setBounds(780, 400, 240, 30);

        btnDownloadPayslip.setBackground(new java.awt.Color(255, 255, 255));
        btnDownloadPayslip.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        btnDownloadPayslip.setForeground(new java.awt.Color(60, 45, 45));
        btnDownloadPayslip.setText("Generate Salary Payslip");
        btnDownloadPayslip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownloadPayslipActionPerformed(evt);
            }
        });
        getContentPane().add(btnDownloadPayslip);
        btnDownloadPayslip.setBounds(790, 460, 220, 30);

        jLabel2.setText("sal");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 0, 1120, 670);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logOut_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOut_buttonActionPerformed
        // TODO add your handling code here:
        FrmLogin logOut = null;
    try {
        logOut = new FrmLogin();
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(FrmSalary.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
        Logger.getLogger(FrmSalary.class.getName()).log(Level.SEVERE, null, ex);
    }
            logOut.show();
            logOut.setLocationRelativeTo(null); // Center the frame
            
            dispose();
    }//GEN-LAST:event_logOut_buttonActionPerformed

    private void request_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_request_buttonActionPerformed
        // TODO add your handling code here:
        FrmRequest request = new FrmRequest();
        FrmEmployee_Information _profile = new FrmEmployee_Information();
        request.getLblReqEid().setText(lblSalEid.getText());
        request.getLblReqFName().setText(lblSalFName.getText());
        request.displayRemainingLeaveCredits();
        request.show();
        request.setLocationRelativeTo(null); // Center the frame
        dispose();
    }//GEN-LAST:event_request_buttonActionPerformed

    private void txtSSSdeductionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSSSdeductionsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSSSdeductionsActionPerformed

    private void txtRiceSubsidyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRiceSubsidyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRiceSubsidyActionPerformed

    private void txtPhoneAllowanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhoneAllowanceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneAllowanceActionPerformed

    private void txtClothingAllowanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClothingAllowanceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClothingAllowanceActionPerformed

    private void txtTotalAllowanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalAllowanceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalAllowanceActionPerformed

    private void profile_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profile_buttonActionPerformed
        // TODO add your handling code here:
        FrmEmployee_Information _profile = new FrmEmployee_Information();
        _profile.setVisible(true);
        _profile.setLocationRelativeTo(null); // Center the frame  
    
    try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
        String query = "SELECT employee.EmployeeNum, employee.FirstName, employee.LastName, role.Position, role.Department, " +
                       "employee.ImmediateSupervisor, employee.Status, employee.SssNumber, " +
                       "employee.PhilhealthNumber, employee.TinNumber, employee.PagibigNumber " +
                       "FROM employee " +
                       "JOIN role ON employee.RoleID = role.RoleID " +
                       "WHERE employee.EmployeeNum = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(lblSalEid.getText()));
            ResultSet resultSet = stmt.executeQuery();
            
            if (resultSet.next()) {
                _profile.setVisible(true);
                _profile.setLocationRelativeTo(null); // Center the frame
                _profile.getLblEmpStatus().setText(resultSet.getString("Status"));
                _profile.getTxtEmployeeID().setText(String.valueOf(resultSet.getInt("EmployeeNum")));
                _profile.getTxtFirstName().setText(resultSet.getString("FirstName"));
                _profile.getTxtLastName().setText(resultSet.getString("LastName"));
                _profile.getTxtRole().setText(resultSet.getString("Position"));
                _profile.getTxtDepartment().setText(resultSet.getString("Department"));
                _profile.getTxtSupervisor().setText(resultSet.getString("ImmediateSupervisor"));
                _profile.getTxtSSS_number().setText(resultSet.getString("SssNumber"));
                _profile.getTxtPagibig_number().setText(resultSet.getString("PagibigNumber"));
                _profile.getTxtPhilhealth_number().setText(resultSet.getString("PhilhealthNumber"));
                _profile.getTxtTin_number().setText(resultSet.getString("TinNumber"));
                _profile.getLblEid().setText(String.valueOf(resultSet.getInt("EmployeeNum")));
                _profile.getLblFName().setText(resultSet.getString("FirstName"));
                
                

                System.out.println(resultSet.getString("LastName"));
            }
        }
    } catch (SQLException e) {
        // Handle database connection or query errors
        e.printStackTrace();
    }

    dispose();
    }//GEN-LAST:event_profile_buttonActionPerformed
/*
    private void attendance_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attendance_buttonActionPerformed
        // TODO add your handling code here:
        // Get the logged-in employee's ID and name
            String employeeId = lblSalEid.getText();
            String employeeName = lblSalFName.getText();

            // Open the attendance frame
            FrmAttendance attendance = new FrmAttendance();
            attendance.getLblAttEid().setText(employeeId);
            attendance.getLblAttFName().setText(employeeName);
            attendance.setVisible(true);
            attendance.setLocationRelativeTo(null); // Center the frame

            // Display attendance data for the logged-in employee
            attendance.displayTimeTrackerFromCSV(employeeId);

            // Close the current frame
            dispose();
    }//GEN-LAST:event_attendance_buttonActionPerformed
*/
    private void attendance_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
      // Get the logged-in employee's ID and name
        int employeeId = Integer.parseInt(lblSalEid.getText());
        String employeeName = lblSalFName.getText();

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
    }               
    
/*  
    private void btnViewSalaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewSalaryActionPerformed
        // TODO add your handling code here:
        // Read the "Employee.csv" file to get hourly rate and monthly rate
        String employeeFilePath = "src/main/java/files/Employee.csv";
        String attendanceFilePath = "src/main/java/files/OldAttendance.csv";

        // Get the selected month and year
        int selectedMonth = jMCMonth.getMonth() + 1; // Month is zero-based, so add 1
        int selectedYear = jYCYear.getYear();

        // Initialize gross salary
        double grossSalary = 0.0;

        // Initialize hourly rate and monthly rate
        double hourlyRate = 0.0;
        double monthlyRate = 0.0;
        
        // Initialize rice subsidy, phone allowance, and clothing allowance
        double riceSubsidy = 0.0;
        double phoneAllowance = 0.0;
        double clothingAllowance = 0.0;
        
        // Initialize SSS, Pagibig, Philhealth, and Withholding Tax deductions
        double sssDeduction = 0.0;
        double phealth = 0.0;
        double pagibig = 0.0;
        double withTax = 0.0;
        
        
        boolean dataFound = false; // Flag to check if data is found for the selected month and year

        try (BufferedReader employeeReader = new BufferedReader(new FileReader(employeeFilePath));
             BufferedReader attendanceReader = new BufferedReader(new FileReader(attendanceFilePath))) {

            // Read and discard the header row of both files
            String employeeHeader = employeeReader.readLine();
            String attendanceHeader = attendanceReader.readLine();

            // Search for the employee row that matches the employee number
            String employeeLine;
            while ((employeeLine = employeeReader.readLine()) != null) {
                String[] employeeParts = employeeLine.split(",");
                if (employeeParts.length > 0 && employeeParts[0].equals(lblSalEid.getText())) {
                    // Retrieve the hourly rate value from column index 19
                    hourlyRate = Double.parseDouble(employeeParts[19]);
                    // Retrieve the monthly rate value from column index 14
                    monthlyRate = Double.parseDouble(employeeParts[14]);
                     // Retrieve the rice subsidy value from column index 15
                    riceSubsidy = Double.parseDouble(employeeParts[15]);
                    // Retrieve the phone allowance value from column index 16
                    phoneAllowance = Double.parseDouble(employeeParts[16]);
                    // Retrieve the clothing allowance value from column index 17
                    clothingAllowance = Double.parseDouble(employeeParts[17]);
                    break; // Exit the loop once the employee data is processed
                }
            }

            // Search for the attendance records for the selected month and year
            String attendanceLine;
            while ((attendanceLine = attendanceReader.readLine()) != null) {
                String[] attendanceParts = attendanceLine.split(",");
                if (attendanceParts.length > 0 && attendanceParts[0].equals(lblSalEid.getText())) {
                    // Parse the date to check if it matches the selected month and year
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    Date date = dateFormat.parse(attendanceParts[3]); // Assuming date is in column index 3
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    int recordMonth = calendar.get(Calendar.MONTH) + 1; // Month is zero-based, so add 1
                    int recordYear = calendar.get(Calendar.YEAR);
                    if (recordMonth == selectedMonth && recordYear == selectedYear) {
                        // Accumulate the total worked hours from column index 6
                        double totalWorkedHours = Double.parseDouble(attendanceParts[6]);
                        // Calculate the gross salary by multiplying total worked hours with hourly rate
                        grossSalary += totalWorkedHours * hourlyRate;
                        dataFound = true; // Set the flag to true if data is found
                    }
                }
            }

            // If no data is found for the selected month and year, display a popup message
            if (!dataFound) {
                JOptionPane.showMessageDialog(this, "No data available for the selected month and year. Please pick another month and year.", "Data Not Found", JOptionPane.INFORMATION_MESSAGE);
                
                // Clear or disable the text fields related to salary details
                txtHourlyRate.setText("");
                txtMonthlyRate.setText("");
                txtGrossSalary.setText("");
                
                // Clear or disable other related text fields
                txtRiceSubsidy.setText("");
                txtPhoneAllowance.setText("");
                txtClothingAllowance.setText("");
                txtTotalAllowance.setText("");
                
                // Clear or disable deductions text fields
                txtSSSdeductions.setText("");
                txtPagibigDeductions.setText("");
                txtPhilhealthDeductions.setText("");
                txtWithholdingTax.setText("");
                txtTotalDeductions.setText("");
                
                // Clear or disable net salary text field
                txtNetSalary.setText("");

                // Return from the method as no further processing is required
                return;
            }
            
            // Compute the total allowance
            double totalAllowance = riceSubsidy + phoneAllowance + clothingAllowance;
            
            // Compute deductions based on gross monthly salary
            if (grossSalary >= 24750.0) {
                sssDeduction = 1125;
                phealth = (grossSalary * 0.03) * 0.5;
                pagibig = 100;
            } else if (grossSalary < 24750.0) {
                sssDeduction = grossSalary / 22.4691;
                phealth = (grossSalary * 0.03) * 0.5;
                pagibig = 100;
            }
            
            // Initialize and Compute the Total Deductions of SSS, Philhealth and Pagibig, and the Tax Income
            double totDeduction = sssDeduction + phealth + pagibig;
            double taxIncome = grossSalary - totDeduction;
            
            //Initialize Net Salary
            double netSalary = 0.0;
            
            // Compute net monthly wage based on tax income
            if (taxIncome >= 33333.0) {
                withTax = (taxIncome - 33333.00) * 0.25 + 2500.00;
                netSalary = (taxIncome - withTax)+ totalAllowance;
            } else if (taxIncome < 33333) {
                withTax = (taxIncome - 20833.00) * 0.20;
                netSalary = (taxIncome - withTax) + totalAllowance;
            }
            
            // Set the hourly rate and monthly rate values into their respective text fields with two decimal places
            txtHourlyRate.setText(String.format("%.2f", hourlyRate));
            txtMonthlyRate.setText(String.format("%.2f", monthlyRate));

            // Set the gross salary value into the txtGrossSalary text field with two decimal places
            txtGrossSalary.setText(String.format("%.2f", grossSalary));

            // Set the rice subsidy, phone allowance, and clothing allowance values into their respective text fields
            txtRiceSubsidy.setText(String.format("%.2f", riceSubsidy));
            txtPhoneAllowance.setText(String.format("%.2f", phoneAllowance));
            txtClothingAllowance.setText(String.format("%.2f", clothingAllowance));
            
            // Set the total allowance value into the txtTotalAllowance text field
            txtTotalAllowance.setText(String.format("%.2f", totalAllowance));
            
            // Set the SSS, Pagibig, Philhealth, and Withholding Tax deductions values into their respective text fields
            txtSSSdeductions.setText(String.format("%.2f", sssDeduction));
            txtPagibigDeductions.setText(String.format("%.2f", pagibig));
            txtPhilhealthDeductions.setText(String.format("%.2f", phealth));
            txtWithholdingTax.setText(String.format("%.2f", withTax));
            
            // Set the total deductions value into the txtTotalDeductions text field
            txtTotalDeductions.setText(String.format("%.2f", totDeduction));
            
            // Set the Net Salary value into the txtNetSalary text field
            txtNetSalary.setText(String.format("%.2f", netSalary));
            
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            // Handle the exceptions appropriately
        }
    }//GEN-LAST:event_btnViewSalaryActionPerformed
*/
    public boolean salaryViewed = false;
  /*  
    private void btnDownloadPayslipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownloadPayslipActionPerformed
        // Check if the salary details have been viewed
        if (!salaryViewed) {
            JOptionPane.showMessageDialog(this, "Please view the salary details first.", "View Salary Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get the selected month and year
        int selectedMonth = jMCMonth.getMonth() + 1; // Month is zero-based, so add 1
        int selectedYear = jYCYear.getYear();

        // Fetch logged-in employee details (assuming lblSalEid holds the employee ID)
        String employeeNum = lblSalEid.getText(); // Employee ID

        // Check if the salary details are already populated
        if (txtMonthlyRate.getText().isEmpty() || txtHourlyRate.getText().isEmpty() || txtNetSalary.getText().isEmpty()) {
            // Populate the salary details first
            if (!populateSalaryDetails(employeeNum, selectedMonth, selectedYear)) {
                // If no data found, return without proceeding to generate payslip
                JOptionPane.showMessageDialog(this, "No data available for the selected month and year. Please pick another month and year.", "Data Not Found", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Fetch employee details from the database
            String fetchEmployeeQuery = "SELECT LastName, FirstName FROM payslip WHERE EmployeeNum = ?";
            String employeeLastName = null;
            String employeeFirstName = null;
            try (PreparedStatement fetchEmployeeStmt = connection.prepareStatement(fetchEmployeeQuery)) {
                fetchEmployeeStmt.setString(1, employeeNum);
                try (ResultSet employeeRs = fetchEmployeeStmt.executeQuery()) {
                    if (employeeRs.next()) {
                        employeeLastName = employeeRs.getString("LastName");
                        employeeFirstName = employeeRs.getString("FirstName");
                        System.out.println("Employee Last Name: " + employeeLastName);
                        System.out.println("Employee First Name: " + employeeFirstName);
                    } else {
                        JOptionPane.showMessageDialog(this, "Employee not found in database.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            String fetchJasperQuery = "SELECT TemplateContent FROM report_templates WHERE TemplateName = ?";
            try (PreparedStatement fetchJasperStmt = connection.prepareStatement(fetchJasperQuery)) {
                fetchJasperStmt.setString(1, "payslip"); // Assuming 'payslip' is the template name
                try (ResultSet jasperRs = fetchJasperStmt.executeQuery()) {
                    if (jasperRs.next()) {
                        // Read compiled Jasper file content from database
                        InputStream jasperStream = jasperRs.getBinaryStream("TemplateContent");

                        // Load JasperReport from the input stream
                        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

                        // Proceed with filling the report and generating the output
                        generatePayslipReport(jasperReport, connection, employeeNum, selectedMonth, selectedYear);
                    } else {
                        JOptionPane.showMessageDialog(this, "Jasper template 'payslip' not found in database.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException | JRException e) {
            // Handle exceptions
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating payslip: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDownloadPayslipActionPerformed
*/
    public void btnDownloadPayslipActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        // Check if the salary details have been viewed
        if (!salaryViewed) {
            JOptionPane.showMessageDialog(this, "Please view the salary details first.", "View Salary Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get the selected month and year
        int selectedMonth = jMCMonth.getMonth() + 1; // Month is zero-based, so add 1
        int selectedYear = jYCYear.getYear();

        // Fetch logged-in employee details (assuming lblSalEid holds the employee ID)
        String employeeNum = lblSalEid.getText(); // Employee ID

        // Check if the salary details are already populated
        if (txtMonthlyRate.getText().isEmpty() || txtHourlyRate.getText().isEmpty() || txtNetSalary.getText().isEmpty()) {
            // Populate the salary details first
            if (!populateSalaryDetails(employeeNum, selectedMonth, selectedYear)) {
                // If no data found, return without proceeding to generate payslip
                JOptionPane.showMessageDialog(this, "No data available for the selected month and year. Please pick another month and year.", "Data Not Found", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Fetch employee details from the database
            String fetchEmployeeQuery = "SELECT LastName, FirstName FROM payslip WHERE EmployeeNum = ?";
            String employeeLastName = null;
            String employeeFirstName = null;
            try (PreparedStatement fetchEmployeeStmt = connection.prepareStatement(fetchEmployeeQuery)) {
                fetchEmployeeStmt.setString(1, employeeNum);
                try (ResultSet employeeRs = fetchEmployeeStmt.executeQuery()) {
                    if (employeeRs.next()) {
                        employeeLastName = employeeRs.getString("LastName");
                        employeeFirstName = employeeRs.getString("FirstName");
                        System.out.println("Employee Last Name: " + employeeLastName);
                        System.out.println("Employee First Name: " + employeeFirstName);
                    } else {
                        JOptionPane.showMessageDialog(this, "Employee not found in database.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            String fetchJasperQuery = "SELECT TemplateContent FROM report_templates WHERE TemplateName = ?";
            try (PreparedStatement fetchJasperStmt = connection.prepareStatement(fetchJasperQuery)) {
                fetchJasperStmt.setString(1, "payslip"); // Assuming 'payslip' is the template name
                try (ResultSet jasperRs = fetchJasperStmt.executeQuery()) {
                    if (jasperRs.next()) {
                        // Read compiled Jasper file content from database
                        InputStream jasperStream = jasperRs.getBinaryStream("TemplateContent");

                        // Load JasperReport from the input stream
                        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

                        // Proceed with filling the report and generating the output
                        generatePayslipReport(jasperReport, connection, employeeNum, selectedMonth, selectedYear);
                    } else {
                        JOptionPane.showMessageDialog(this, "Jasper template 'payslip' not found in database.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException | JRException e) {
            // Handle exceptions
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating payslip: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean populateSalaryDetails(String employeeNum, int selectedMonth, int selectedYear) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Query to get the payslip details for the selected month and year
            String payslipQuery = "SELECT MonthlyRate, HourlyRate, GrossSalary, RiceSubsidy, PhoneAllowance, ClothingAllowance, SSSdeduction, PagibigDeduction, PhilhealthDeduction, WithholdingTax, TotalAllowance, TotalContribution, NetMonthlySalary " +
                                  "FROM payslip " +
                                  "WHERE EmployeeNum = ? AND Month = ? AND Year = ?";
            try (PreparedStatement payslipStmt = connection.prepareStatement(payslipQuery)) {
                payslipStmt.setString(1, employeeNum);
                payslipStmt.setInt(2, selectedMonth);
                payslipStmt.setInt(3, selectedYear);
                try (ResultSet payslipRs = payslipStmt.executeQuery()) {
                    if (payslipRs.next()) {
                        // Data found, retrieve and set the values to the respective text fields
                        txtMonthlyRate.setText(String.format("%.2f", payslipRs.getDouble("MonthlyRate")));
                        txtHourlyRate.setText(String.format("%.2f", payslipRs.getDouble("HourlyRate")));
                        txtGrossSalary.setText(String.format("%.2f", payslipRs.getDouble("GrossSalary")));
                        txtRiceSubsidy.setText(String.format("%.2f", payslipRs.getDouble("RiceSubsidy")));
                        txtPhoneAllowance.setText(String.format("%.2f", payslipRs.getDouble("PhoneAllowance")));
                        txtClothingAllowance.setText(String.format("%.2f", payslipRs.getDouble("ClothingAllowance")));
                        txtSSSdeductions.setText(String.format("%.2f", payslipRs.getDouble("SSSdeduction")));
                        txtPagibigDeductions.setText(String.format("%.2f", payslipRs.getDouble("PagibigDeduction")));
                        txtPhilhealthDeductions.setText(String.format("%.2f", payslipRs.getDouble("PhilhealthDeduction")));
                        txtWithholdingTax.setText(String.format("%.2f", payslipRs.getDouble("WithholdingTax")));
                        txtTotalAllowance.setText(String.format("%.2f", payslipRs.getDouble("TotalAllowance")));
                        txtTotalDeductions.setText(String.format("%.2f", payslipRs.getDouble("TotalContribution")));
                        txtNetSalary.setText(String.format("%.2f", payslipRs.getDouble("NetMonthlySalary")));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching salary details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private void generatePayslipReport(JasperReport jasperReport, Connection connection, String employeeNum, int selectedMonth, int selectedYear) {
        String fetchPayslipQuery = "SELECT PayslipID, EmployeeNum, FirstName, LastName, Position, Department, Month, Year, " +
                                   "MonthlyRate, HourlyRate, TotalWorkedHrs, OTHrs, LeaveCreditsUsedinHours, " +
                                   "GrossSalary, RiceSubsidy, PhoneAllowance, ClothingAllowance, TotalAllowance, " +
                                   "SSSdeduction, PhilhealthDeduction, PagibigDeduction, WithholdingTax, TotalContribution, " +
                                   "NetMonthlySalary " +
                                   "FROM payslip " +
                                   "WHERE EmployeeNum = ? AND Month = ? AND Year = ?";

        try (PreparedStatement fetchPayslipStmt = connection.prepareStatement(fetchPayslipQuery)) {
            fetchPayslipStmt.setString(1, employeeNum);
            fetchPayslipStmt.setInt(2, selectedMonth);
            fetchPayslipStmt.setInt(3, selectedYear);
            try (ResultSet payslipRs = fetchPayslipStmt.executeQuery()) {
                if (payslipRs.next()) {
                    Map<String, Object> dataMap = new HashMap<>();
                    dataMap.put("PayslipID", payslipRs.getInt("PayslipID"));
                    dataMap.put("EmployeeNum", payslipRs.getInt("EmployeeNum"));
                    dataMap.put("FirstName", payslipRs.getString("FirstName"));
                    dataMap.put("LastName", payslipRs.getString("LastName"));
                    dataMap.put("Position", payslipRs.getString("Position"));
                    dataMap.put("Department", payslipRs.getString("Department"));
                    dataMap.put("Month", payslipRs.getInt("Month"));
                    dataMap.put("Year", payslipRs.getInt("Year"));
                    dataMap.put("MonthlyRate", payslipRs.getBigDecimal("MonthlyRate"));
                    dataMap.put("HourlyRate", payslipRs.getBigDecimal("HourlyRate"));
                    dataMap.put("TotalWorkedHrs", payslipRs.getBigDecimal("TotalWorkedHrs"));
                    dataMap.put("OTHrs", payslipRs.getBigDecimal("OTHrs"));
                    dataMap.put("LeaveCreditsUsedinHours", payslipRs.getBigDecimal("LeaveCreditsUsedinHours"));
                    dataMap.put("GrossSalary", payslipRs.getBigDecimal("GrossSalary"));
                    dataMap.put("RiceSubsidy", payslipRs.getBigDecimal("RiceSubsidy"));
                    dataMap.put("PhoneAllowance", payslipRs.getBigDecimal("PhoneAllowance"));
                    dataMap.put("ClothingAllowance", payslipRs.getBigDecimal("ClothingAllowance"));
                    dataMap.put("TotalAllowance", payslipRs.getBigDecimal("TotalAllowance"));
                    dataMap.put("SSSdeduction", payslipRs.getBigDecimal("SSSdeduction"));
                    dataMap.put("PhilhealthDeduction", payslipRs.getBigDecimal("PhilhealthDeduction"));
                    dataMap.put("PagibigDeduction", payslipRs.getBigDecimal("PagibigDeduction"));
                    dataMap.put("WithholdingTax", payslipRs.getBigDecimal("WithholdingTax"));
                    dataMap.put("TotalContribution", payslipRs.getBigDecimal("TotalContribution"));
                    dataMap.put("NetMonthlySalary", payslipRs.getBigDecimal("NetMonthlySalary"));

                    System.out.println("DataMap contents: " + dataMap);

                    if (dataMap.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "No data found to generate payslip.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    List<Map<String, ?>> dataList = new ArrayList<>();
                    dataList.add(dataMap);
                    JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(dataList);

                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);

                    String lastName = payslipRs.getString("LastName");
                    Path downloadsPath = Paths.get(System.getProperty("user.home"), "Downloads");
                    String fileName = downloadsPath.resolve("Payslip_" + lastName + "_" + selectedMonth + "_" + selectedYear + ".pdf").toString();
                    JasperExportManager.exportReportToPdfFile(jasperPrint, fileName);
                    Desktop.getDesktop().open(new File(fileName));

                    JOptionPane.showMessageDialog(this, "Payslip downloaded successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Payslip not found for the selected month and year.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException | JRException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating payslip: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnViewSalaryActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // Get the selected month and year
        int selectedMonth = jMCMonth.getMonth() + 1; // Month is zero-based, so add 1
        int selectedYear = jYCYear.getYear();

        if (!populateSalaryDetails(lblSalEid.getText(), selectedMonth, selectedYear)) {
            JOptionPane.showMessageDialog(this, "No data available for the selected month and year. Please pick another month and year.", "Data Not Found", JOptionPane.INFORMATION_MESSAGE);

            // Clear or disable the text fields related to salary details
            clearSalaryFields();

            // Return from the method as no further processing is required
            return;
        }

        // Set the salaryViewed flag to true after viewing salary details
        salaryViewed = true;
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
            java.util.logging.Logger.getLogger(FrmSalary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmSalary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmSalary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmSalary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmSalary().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton attendance_button;
    public javax.swing.JButton btnDownloadPayslip;
    public javax.swing.JButton btnViewSalary;
    private javax.swing.JLabel jLabel2;
    public com.toedter.calendar.JMonthChooser jMCMonth;
    public com.toedter.calendar.JYearChooser jYCYear;
    private javax.swing.JLabel lblClothingAllowance;
    private javax.swing.JLabel lblGrossSalary;
    private javax.swing.JLabel lblHourlyRate;
    private javax.swing.JLabel lblIDniEmployee;
    private javax.swing.JLabel lblMonthlyRate;
    private javax.swing.JLabel lblNetSalary;
    private javax.swing.JLabel lblPagibigDeductions;
    private javax.swing.JLabel lblPeriodEndDate;
    private javax.swing.JLabel lblPeriodStartDate;
    private javax.swing.JLabel lblPhilhealthDeductions;
    private javax.swing.JLabel lblPhoneAllowance;
    private javax.swing.JLabel lblRiceSubsidy;
    private javax.swing.JLabel lblSSSdeductions;
    public javax.swing.JLabel lblSalEid;
    private javax.swing.JLabel lblSalFName;
    private javax.swing.JLabel lblTotalAllowance;
    private javax.swing.JLabel lblTotalDeductions;
    private javax.swing.JLabel lblWelcomeMsg;
    private javax.swing.JLabel lblWithholdingTax;
    private javax.swing.JButton logOut_button;
    private javax.swing.JButton profile_button;
    private javax.swing.JButton request_button;
    public javax.swing.JTextField txtClothingAllowance;
    public javax.swing.JTextField txtGrossSalary;
    public javax.swing.JTextField txtHourlyRate;
    public javax.swing.JTextField txtMonthlyRate;
    public javax.swing.JTextField txtNetSalary;
    public javax.swing.JTextField txtPagibigDeductions;
    public javax.swing.JTextField txtPhilhealthDeductions;
    public javax.swing.JTextField txtPhoneAllowance;
    public javax.swing.JTextField txtRiceSubsidy;
    public javax.swing.JTextField txtSSSdeductions;
    public javax.swing.JTextField txtTotalAllowance;
    public javax.swing.JTextField txtTotalDeductions;
    public javax.swing.JTextField txtWithholdingTax;
    // End of variables declaration//GEN-END:variables
}
