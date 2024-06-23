package frames;
import classes.Employee;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import javax.swing.JLabel;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
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
public class FrmAttendance extends javax.swing.JFrame {
    
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";

    public void displayTimeTrackerFromDatabase(int employeeId) {
        DefaultTableModel model = (DefaultTableModel) timeTracker_table.getModel();
        model.setRowCount(0); // Clear existing rows
    
        String query = "SELECT * FROM time_tracker WHERE EmployeeNum = ?";
    
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(query)) {
         
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
        
            while (rs.next()) {
                int id = rs.getInt("EmployeeNum");
                String date = rs.getString("Date");
                String timeIn = rs.getString("TimeIn");
                String timeOut = rs.getString("TimeOut");
                double totalHours = rs.getDouble("TotalWorkedHrs");
            
                // Add the row to the table
                model.addRow(new Object[]{id, date, timeIn, timeOut, totalHours});
            }
        
            rs.close();
        
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error connecting to database: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            // Log the exception for debugging purposes
            e.printStackTrace();
        }
    }

  

    public class AttendanceRecord {
        private String date;
        private String timeIn;
        private String timeOut;
        private double hoursAttended;

        // Constructors, getters, and setters
        public AttendanceRecord(String date, String timeIn, String timeOut, double hoursAttended) {
            this.date = date;
            this.timeIn = timeIn;
            this.timeOut = timeOut;
            this.hoursAttended = hoursAttended;
        }

        public JLabel getLblEid() {
            return lblAttEid;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTimeIn() {
            return timeIn;
        }

        public void setTimeIn(String timeIn) {
            this.timeIn = timeIn;
        }

        public String getTimeOut() {
            return timeOut;
        }

        public void setTimeOut(String timeOut) {
            this.timeOut = timeOut;
        }

        public double getHoursAttended() {
            return hoursAttended;
        }

        public void setHoursAttended(double hoursAttended) {
            this.hoursAttended = hoursAttended;
        }
    }
    
    public void initializeForTesting() {
        initComponents(); // Call private initComponents method
    }

    // Method to display OldAttendance data
    public void displayOldAttendanceFromDatabase(int employeeId) {
    List<String[]> oldAttendanceData = fetchOldAttendanceData(employeeId);
    DefaultTableModel model = (DefaultTableModel) attendance_table.getModel();
    model.setRowCount(0); // Clear existing rows
    
        // Add rows to the table model with only Date, Time In, and Time Out columns
        for (String[] row : oldAttendanceData) {
            if (row.length >= 6) { // Ensure the array has at least six elements
                String[] rowData = {row[3], row[4], row[5]}; // Date, Time In, Time Out
                model.addRow(rowData);
            } else {
                // Skip adding this row
                
            }
        }
    }
    
    // Method to fetch old attendance data based on employee ID
    private List<String[]> fetchOldAttendanceData(int employeeId) {
        List<String[]> oldAttendanceData = new ArrayList<>();

        try {
            // Establish a connection to your database
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Prepare a SQL statement to select old attendance data for the given employee
            String sql = "SELECT * FROM time_tracker WHERE EmployeeNum = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, employeeId);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Iterate through the result set and populate the list of string arrays
            while (resultSet.next()) {
                String date = resultSet.getString("Date");
                String timeIn = resultSet.getString("TimeIn");
                String timeOut = resultSet.getString("TimeOut");

                // Add the retrieved data as a string array to the list
                String[] rowData = {date, timeIn, timeOut};
                oldAttendanceData.add(rowData);
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle potential exceptions
        }

        return oldAttendanceData;
    }


    private Employee[] employee1;
    DefaultTableModel model;
    
    public FrmAttendance() {
        initComponents();
        setResizable(false);

        setPreferredSize(new Dimension(1120, 670));

        // Set frame properties
        pack();  // Adjust size to the preferred size

        displayBgFromDatabase("FrmAttendance");
        
        setAlternateRowAndColumnColorRenderer(attendance_table);
        setAlternateRowAndColumnColorRenderer(timeTracker_table);

        // Connect to the database
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Fetch employee data from the database
            String sql = "SELECT * FROM employee";
            try (PreparedStatement statement = conn.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                // Count the number of employees
                int numEmployees = 0;
                while (resultSet.next()) {
                    numEmployees++;
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

            // Fetch image data from the database based on the frame name 'attImg'
            String imgSql = "SELECT Img FROM Image WHERE Frame = ?";
            try (PreparedStatement imgStatement = conn.prepareStatement(imgSql)) {
                // Set the frame name parameter
                imgStatement.setString(1, "attImg");

                // Execute the query
                ResultSet imgResultSet = imgStatement.executeQuery();

                // Check if a result was found
                if (imgResultSet.next()) {
                    // Retrieve the image data as a byte array
                    byte[] imgByteArray = imgResultSet.getBytes("Img");

                    // Convert the byte array to an ImageIcon
                    ImageIcon icon = new ImageIcon(imgByteArray);

                    // Set the ImageIcon as the icon for the JLabel
                    jLabel2.setIcon(icon);
                } else {
                    System.out.println("Image not found.");
                }
            }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                // Handle any exceptions
        }

        showDate();
        showTime();
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
                    jLabel4.setIcon(icon);
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

        private static final Color EVEN_COLUMN_COLOR = new Color(252, 235, 230);
        private static final Color ODD_COLUMN_COLOR = new Color(252, 247, 245); 

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

    public JLabel getLblAttEid() {
        return lblAttEid;
    }

    public JLabel getLblAttFName() {
        return lblAttFName;
    }

    public JTable getTimeTracker_table() {
        return timeTracker_table;
    }
    
   
    //method for show date
     public void showDate(){
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat ("yyyy-MM-dd");
        String date = s.format(d);
        lblDate.setText(date);
    }
    
    public void showTime(){
        new Timer (0, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent as) {
                Date d = new Date();
                SimpleDateFormat s = new SimpleDateFormat ("HH:mm:ss");
                String time = s.format(d);
                lblTime.setText(time);
            }
        }).start();
    }
    

    // Method to save the table data to the database
    private void saveTableDataToDatabase() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Prepare the insert statement
            String insertQuery = "INSERT INTO time_tracker (`EmployeeNum`, `Date`, `TimeIn`, `TimeOut`, `TotalWorkedHrs`) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                DefaultTableModel model = (DefaultTableModel) timeTracker_table.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    // Set the parameters for the insert statement
                    pstmt.setInt(1, Integer.parseInt(model.getValueAt(i, 0).toString())); // Assuming Employee ID is in column index 0
                    pstmt.setString(2, model.getValueAt(i, 1).toString()); // Assuming Date is in column index 1
                    pstmt.setString(3, model.getValueAt(i, 2).toString()); // Assuming Time In is in column index 2
                    pstmt.setString(4, model.getValueAt(i, 3).toString()); // Assuming Time Out is in column index 3
                    pstmt.setDouble(5, Double.parseDouble(model.getValueAt(i, 4).toString())); // Assuming Total Worked Hrs is in column index 4

                    // Execute the insert statement
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    // Method to display the contents of the database table in the table for a specific employee
    public void displayTimeTrackerFromDatabase(String employeeId) {
        DefaultTableModel model = (DefaultTableModel) timeTracker_table.getModel();
        model.setRowCount(0); // Clear existing rows

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Prepare a SQL statement to select the time tracker records for the specified employee
            String sql = "SELECT * FROM time_tracker WHERE EmployeeNum = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                // Set the employee ID parameter
                statement.setString(1, employeeId);

                // Execute the query
                try (ResultSet resultSet = statement.executeQuery()) {
                    // Process the result set and populate the table model
                    while (resultSet.next()) {
                        Object[] row = new Object[4]; // Assuming there are four columns: employee_id, date, startTime, endTime
                        row[0] = resultSet.getString("EmployeeNum");
                        row[1] = resultSet.getString("Date");
                        row[2] = resultSet.getString("TimeIn");
                        row[3] = resultSet.getString("TimeOut");
                        model.addRow(row);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // Handle the exception appropriately
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        checkbox1 = new java.awt.Checkbox();
        lblWelcomeMsg = new javax.swing.JLabel();
        lblAttFName = new javax.swing.JLabel();
        lblIDniEmployee = new javax.swing.JLabel();
        lblAttEid = new javax.swing.JLabel();
        logOut_button = new javax.swing.JButton();
        lblTime = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        timeIn_button = new javax.swing.JButton();
        timeOut_button = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jMCMonth = new com.toedter.calendar.JMonthChooser();
        jYCYear = new com.toedter.calendar.JYearChooser();
        btnView = new javax.swing.JButton();
        lblTotalWorkedHours = new javax.swing.JLabel();
        txtTotalWorkedHours = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        attendance_table = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        timeTracker_table = new javax.swing.JTable();
        btnReqOvertime = new javax.swing.JButton();
        btnViewOvertimeHistory = new javax.swing.JButton();
        profile_button = new javax.swing.JButton();
        salary_button = new javax.swing.JButton();
        request_button = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        checkbox1.setLabel("checkbox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(218, 226, 227));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(1080, 632));
        getContentPane().setLayout(null);

        lblWelcomeMsg.setFont(new java.awt.Font("Verdana", 0, 25)); // NOI18N
        lblWelcomeMsg.setForeground(new java.awt.Color(60, 45, 45));
        lblWelcomeMsg.setText("Welcome,");
        getContentPane().add(lblWelcomeMsg);
        lblWelcomeMsg.setBounds(160, 20, 123, 32);

        lblAttFName.setFont(new java.awt.Font("Verdana", 1, 25)); // NOI18N
        lblAttFName.setForeground(new java.awt.Color(185, 136, 136));
        lblAttFName.setText("firstname");
        getContentPane().add(lblAttFName);
        lblAttFName.setBounds(290, 20, 470, 32);

        lblIDniEmployee.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        lblIDniEmployee.setForeground(new java.awt.Color(60, 45, 45));
        lblIDniEmployee.setText("Employee ID:");
        getContentPane().add(lblIDniEmployee);
        lblIDniEmployee.setBounds(160, 50, 96, 17);

        lblAttEid.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        lblAttEid.setForeground(new java.awt.Color(60, 45, 45));
        lblAttEid.setText("###");
        getContentPane().add(lblAttEid);
        lblAttEid.setBounds(260, 50, 33, 17);

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
        logOut_button.setBounds(980, 30, 100, 30);

        lblTime.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        lblTime.setForeground(new java.awt.Color(255, 255, 255));
        lblTime.setText("08:00:00");
        getContentPane().add(lblTime);
        lblTime.setBounds(718, 206, 130, 50);

        lblDate.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblDate.setForeground(new java.awt.Color(255, 255, 255));
        lblDate.setText("2024-02-21");
        getContentPane().add(lblDate);
        lblDate.setBounds(726, 250, 120, 17);

        timeIn_button.setBackground(new java.awt.Color(255, 255, 255));
        timeIn_button.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        timeIn_button.setForeground(new java.awt.Color(93, 72, 72));
        timeIn_button.setText("Time in");
        timeIn_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeIn_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(timeIn_button);
        timeIn_button.setBounds(660, 280, 94, 33);

        timeOut_button.setBackground(new java.awt.Color(255, 255, 255));
        timeOut_button.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        timeOut_button.setForeground(new java.awt.Color(93, 72, 72));
        timeOut_button.setText("Time out");
        timeOut_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeOut_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(timeOut_button);
        timeOut_button.setBounds(790, 280, 94, 33);

        jLabel2.setForeground(new java.awt.Color(242, 242, 242));
        jLabel2.setText("2");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(95, 182, 100, 100);

        jMCMonth.setBackground(new java.awt.Color(255, 255, 255));
        jMCMonth.setForeground(new java.awt.Color(51, 40, 40));
        jMCMonth.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jMCMonth.setYearChooser(jYCYear);
        getContentPane().add(jMCMonth);
        jMCMonth.setBounds(220, 170, 150, 27);

        jYCYear.setBackground(new java.awt.Color(255, 255, 255));
        jYCYear.setForeground(new java.awt.Color(71, 52, 52));
        jYCYear.setStartYear(2022);
        jYCYear.setValue(2022);
        getContentPane().add(jYCYear);
        jYCYear.setBounds(220, 210, 150, 27);

        btnView.setBackground(new java.awt.Color(255, 255, 255));
        btnView.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnView.setForeground(new java.awt.Color(94, 84, 84));
        btnView.setText("View");
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });
        getContentPane().add(btnView);
        btnView.setBounds(380, 190, 70, 30);

        lblTotalWorkedHours.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblTotalWorkedHours.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalWorkedHours.setText("Total Worked Hours");
        getContentPane().add(lblTotalWorkedHours);
        lblTotalWorkedHours.setBounds(220, 240, 210, 30);

        txtTotalWorkedHours.setEditable(false);
        txtTotalWorkedHours.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        getContentPane().add(txtTotalWorkedHours);
        txtTotalWorkedHours.setBounds(218, 270, 230, 32);

        attendance_table.setBackground(new java.awt.Color(192, 178, 196));
        attendance_table.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        attendance_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Time In", "Time Out"
            }
        ));
        attendance_table.setGridColor(new java.awt.Color(173, 202, 206));
        attendance_table.setSelectionBackground(new java.awt.Color(255, 255, 255));
        attendance_table.setSelectionForeground(new java.awt.Color(255, 0, 0));
        attendance_table.setShowGrid(true);
        jScrollPane1.setViewportView(attendance_table);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(80, 380, 360, 120);

        timeTracker_table.setBackground(new java.awt.Color(240, 234, 211));
        timeTracker_table.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        timeTracker_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee ID", "Date", "Time In", "Time Out", "Total Worked Hours"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        timeTracker_table.setGridColor(new java.awt.Color(173, 202, 206));
        timeTracker_table.setSelectionBackground(new java.awt.Color(255, 255, 255));
        timeTracker_table.setSelectionForeground(new java.awt.Color(255, 0, 0));
        timeTracker_table.setShowGrid(true);
        jScrollPane2.setViewportView(timeTracker_table);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(500, 320, 540, 139);

        btnReqOvertime.setBackground(new java.awt.Color(255, 255, 255));
        btnReqOvertime.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnReqOvertime.setForeground(new java.awt.Color(93, 72, 72));
        btnReqOvertime.setText("Request Overtime");
        btnReqOvertime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReqOvertimeActionPerformed(evt);
            }
        });
        getContentPane().add(btnReqOvertime);
        btnReqOvertime.setBounds(540, 470, 161, 28);

        btnViewOvertimeHistory.setBackground(new java.awt.Color(255, 255, 255));
        btnViewOvertimeHistory.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnViewOvertimeHistory.setForeground(new java.awt.Color(93, 72, 72));
        btnViewOvertimeHistory.setText("View Overtime Request History");
        btnViewOvertimeHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewOvertimeHistoryActionPerformed(evt);
            }
        });
        getContentPane().add(btnViewOvertimeHistory);
        btnViewOvertimeHistory.setBounds(740, 470, 254, 28);

        profile_button.setBackground(new java.awt.Color(255, 255, 255));
        profile_button.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        profile_button.setForeground(new java.awt.Color(93, 72, 72));
        profile_button.setText("Profile");
        profile_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profile_buttonActionPerformed(evt);
            }
        });
        getContentPane().add(profile_button);
        profile_button.setBounds(210, 560, 173, 50);

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

        jLabel4.setText("att");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(0, 0, 1120, 670);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void request_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_request_buttonActionPerformed
        // TODO add your handling code here:
        FrmRequest request = new FrmRequest();
            FrmEmployee_Information _profile = new FrmEmployee_Information();
        request.getLblReqEid().setText(lblAttEid.getText());
        request.getLblReqFName().setText(lblAttFName.getText()); 
        request.displayRemainingLeaveCredits();
        request.show();
        request.setLocationRelativeTo(null); // Center the frame
         dispose();
    }//GEN-LAST:event_request_buttonActionPerformed

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
            stmt.setInt(1, Integer.parseInt(lblAttEid.getText()));
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

    private void logOut_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOut_buttonActionPerformed
        // TODO add your handling code here:
        FrmLogin logOut = null;
        try {
            logOut = new FrmLogin();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FrmAttendance.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FrmAttendance.class.getName()).log(Level.SEVERE, null, ex);
        }
            logOut.show();
            logOut.setLocationRelativeTo(null); // Center the frame
            
            dispose();
    }//GEN-LAST:event_logOut_buttonActionPerformed
/*
    private void timeIn_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeIn_buttonActionPerformed
       
    }//GEN-LAST:event_timeIn_buttonActionPerformed
*/
    public void timeIn_buttonActionPerformed(java.awt.event.ActionEvent evt) {
      try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
          // Get the current date
          LocalDate currentDate = LocalDate.now();

          // Format the current date as "yyyy-MM-dd" for MySQL
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
          String formattedDate = currentDate.format(formatter);

          // Get the employee ID
          String employeeId = lblAttEid.getText();

          // Check if the user has already timed in for the current date
          boolean alreadyTimedIn = hasAlreadyTimedIn(conn, employeeId, currentDate, formattedDate);

          if (alreadyTimedIn) {
              JOptionPane.showMessageDialog(this, "You have already timed in for today.", "Error", JOptionPane.ERROR_MESSAGE);
              return;
          }

          // Get the current time
          String currentTime = lblTime.getText();

          // Prepare SQL statement to insert time tracking data using a prepared statement
          String INSERT = "INSERT INTO time_tracker (`EmployeeNum`, Date, `TimeIn`, `TimeOut`, `TotalWorkedHrs`) VALUES (?, ?, ?, NULL, NULL)";

          try (PreparedStatement pstmt = conn.prepareStatement(INSERT)) {
              // Set parameters for the prepared statement
              pstmt.setString(1, employeeId);
              pstmt.setString(2, formattedDate); // Use the correctly formatted date
              pstmt.setString(3, currentTime);

              // Execute the INSERT statement
              int rowsInserted = pstmt.executeUpdate();

              if (rowsInserted > 0) {
                  JOptionPane.showMessageDialog(this, "Timed In Successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
              } else {
                  JOptionPane.showMessageDialog(this, "Time In Failed.", "Error", JOptionPane.ERROR_MESSAGE);
              }

          } catch (SQLException e) {
              // Print the stack trace to identify the specific SQL error
              e.printStackTrace();
              JOptionPane.showMessageDialog(this, "Error occurred while saving data to the database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          }

          // Add the new data directly to the table model
          DefaultTableModel model = (DefaultTableModel) timeTracker_table.getModel();
          model.addRow(new Object[]{employeeId, formattedDate, currentTime});

      } catch (SQLException e) {
          // Print the stack trace to identify the specific SQL error
          e.printStackTrace();
          JOptionPane.showMessageDialog(this, "Error occurred while connecting to the database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
  }


    public boolean hasAlreadyTimedIn(Connection conn, String employeeId, LocalDate currentDate, String formattedDate) throws SQLException {
        // Construct the SQL query to check if the user has already timed in for the current date
        String query = "SELECT COUNT(*) FROM time_tracker WHERE `EmployeeNum` = ? AND Date = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, employeeId);
            // Use the formatted date in the SQL query
            stmt.setString(2, formattedDate); // Use the formatted date

            // Execute the query and check if any records are returned
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next(); // Move cursor to the first row
            int count = resultSet.getInt(1); // Get the count from the first column

            return count > 0; // Return true if there are records, indicating that the user has already timed in
        }
    }

/*
    private void timeOut_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeOut_buttonActionPerformed

    }   */
    
    private void timeOut_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // Ensure model is properly initialized
        DefaultTableModel model = (DefaultTableModel) timeTracker_table.getModel();

        // Find the last inserted row index with a Time In but without a Time Out
        int lastInsertedRowIndex = -1;
        for (int i = 0; i < model.getRowCount(); i++) {
            // Check if there is a time in without a corresponding time out
            if (model.getValueAt(i, 2) != null && model.getValueAt(i, 3) == null) {
                lastInsertedRowIndex = i;
                break;
            }
        }

        if (lastInsertedRowIndex != -1) {
            // Get the time in value from the table
            String timeInString = (String) model.getValueAt(lastInsertedRowIndex, 2);

            // Get the current time and update the existing row with Time Out
            String currentTime = lblTime.getText();
            model.setValueAt(currentTime, lastInsertedRowIndex, 3);

            // Calculate total worked hours without subtracting break time
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss"); // Update format to include seconds

            try {
                Date timeIn = format.parse(timeInString);
                Date timeOut = format.parse(currentTime);

                // Handle cross-midnight case
                if (timeOut.before(timeIn)) {
                    timeOut = new Date(timeOut.getTime() + TimeUnit.DAYS.toMillis(1));
                }

                long difference = timeOut.getTime() - timeIn.getTime();

                // Convert milliseconds to hours and minutes
                long totalWorkedMillis = difference / (1000 * 60 * 60);
                long totalWorkedMinutes = (difference / (1000 * 60)) % 60;

                // Convert to double
                double totalWorkedHours = totalWorkedMillis + (totalWorkedMinutes / 60.0);
                
                // Format total worked hours to two decimal places
                DecimalFormat df = new DecimalFormat("#.##");
                totalWorkedHours = Double.parseDouble(df.format(totalWorkedHours));

                // Update the total worked hours column
                model.setValueAt(totalWorkedHours, lastInsertedRowIndex, 4);

                // Get the employee ID and date
                Object employeeIdObject = model.getValueAt(lastInsertedRowIndex, 0);
                String employeeId = employeeIdObject.toString(); // Convert to String
                String currentDate = lblDate.getText(); // Assuming lblDate displays the current date as a string

                // Update the database with the time out and total worked hours
                updateDatabase(employeeId, currentDate, timeInString, currentTime, totalWorkedHours);

                // Inform the user that Time Out has been successful
                JOptionPane.showMessageDialog(this, "You have successfully timed out.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (ParseException e) {
                e.printStackTrace();
                // Handle the exception appropriately
            }
        } else {
            // Inform the user that Time In has not been performed
            JOptionPane.showMessageDialog(this, "Please perform Time In first.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateDatabase(String employeeId, String currentDate, String timeInString, String currentTime, double totalWorkedHours) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "UPDATE time_tracker SET `TimeOut` = ?, `TotalWorkedHrs` = ? WHERE `Date` = ? AND `EmployeeNum` = ? AND `TimeIn` = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                // Convert currentDate and currentTime strings to appropriate SQL types directly
                java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);

                // Parse time strings with DateTimeFormatter
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalTime parsedTimeOut = LocalTime.parse(currentTime, timeFormatter);
                LocalTime parsedTimeIn = LocalTime.parse(timeInString, timeFormatter);

                // Convert parsed times to SQL Time
                java.sql.Time sqlTimeOut = java.sql.Time.valueOf(parsedTimeOut);
                java.sql.Time sqlTimeIn = java.sql.Time.valueOf(parsedTimeIn);

                // Set parameters for the prepared statement
                statement.setTime(1, sqlTimeOut);            // TimeOut
                statement.setDouble(2, totalWorkedHours);    // TotalWorkedHrs
                statement.setDate(3, sqlDate);               // Date
                statement.setInt(4, Integer.parseInt(employeeId)); // EmployeeNum
                statement.setTime(5, sqlTimeIn);             // TimeIn

                // Print parameters for debugging
                System.out.println("Time Out: " + sqlTimeOut);
                System.out.println("Total Worked Hours: " + totalWorkedHours);
                System.out.println("Date: " + sqlDate);
                System.out.println("Employee ID: " + employeeId);
                System.out.println("Time In: " + sqlTimeIn);

                // Execute the update statement
                int rowsUpdated = statement.executeUpdate();
                System.out.println("Rows Updated: " + rowsUpdated);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }


    // Method to check if two dates are the same day
    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
        
    }//GEN-LAST:event_timeOut_buttonActionPerformed
  
    private void salary_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salary_buttonActionPerformed
        // TODO add your handling code here:
        FrmSalary salary = new FrmSalary();
        FrmEmployee_Information _profile = new FrmEmployee_Information();
        salary.getLblSalEid().setText(lblAttEid.getText());
        salary.getLblSalFName().setText(lblAttFName.getText());
        salary.show();
        salary.setLocationRelativeTo(null); // Center the frame
            
            dispose();
    }//GEN-LAST:event_salary_buttonActionPerformed

/*
    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
       
    }//GEN-LAST:event_btnViewActionPerformed
*/
    
    public void btnViewActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // Get the selected month and year from the JMonthChooser and JYearChooser
        int selectedMonth = jMCMonth.getMonth() + 1; // Month is zero-based, so add 1
        int selectedYear = jYCYear.getYear();

        // Prepare the table model
        DefaultTableModel model = (DefaultTableModel) attendance_table.getModel();
        model.setRowCount(0); // Clear existing rows

        // Flag to check if data is found for the selected month and year
        boolean dataFound = false;

        // Total worked hours
        double totalHours = 0.0;

        // Database connection variables
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Create a database connection
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Prepare the SQL query to retrieve attendance data
            String sql = "SELECT Date, TimeIn, TimeOut, TotalWorkedHrs " +
                        "FROM time_tracker " +
                         "WHERE MONTH(Date) = ? AND YEAR(Date) = ? AND EmployeeNum = ?";
        
            // Create a prepared statement
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, selectedMonth);
            pstmt.setInt(2, selectedYear);
            pstmt.setString(3, lblAttEid.getText()); // Assuming lblAttEid contains the employee ID

            // Execute the query
            rs = pstmt.executeQuery();

            // Process the result set
            while (rs.next()) {
                // Retrieve data from the result set
                String date = rs.getString("Date");
                String timeIn = rs.getString("TimeIn");
                String timeOut = rs.getString("TimeOut");
                double workedHours = rs.getDouble("TotalWorkedHrs");

                // Add the data to the table model
                Object[] rowData = {date, timeIn, timeOut};
                model.addRow(rowData);

                // Accumulate total worked hours
                totalHours += workedHours;

                // Set dataFound flag to true
                dataFound = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        } finally {
            // Close resources in finally block
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately
            }
        }

        // Show message if no data is found
        if (!dataFound) {
            JOptionPane.showMessageDialog(this, "No data available for the selected month, year, and employee ID.", "Data Not Found", JOptionPane.INFORMATION_MESSAGE);

            // Clear or disable the text fields related to salary details
            txtTotalWorkedHours.setText("");

            // Return from the method as no further processing is required
            return;
        }

        // Update the txtTotalWorkedHours text field with the total hours
        txtTotalWorkedHours.setText(String.format("%.2f", totalHours)); 
    }

    private boolean isRequestOvertimeFormOpen = false; // Flag to track if the request overtime form is open
    
    private void btnReqOvertimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReqOvertimeActionPerformed
                                                
    // Check if the request overtime form is already open
    if (!isRequestOvertimeFormOpen) {
        // Set the flag to indicate that the request overtime form is now open
        isRequestOvertimeFormOpen = true;

        // Get the selected row index
        int selectedRowIndex = timeTracker_table.getSelectedRow();

        // Check if a row is selected
        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to request overtime.", "Error", JOptionPane.ERROR_MESSAGE);
            // Reset the flag since the request overtime form is not opened
            isRequestOvertimeFormOpen = false;
            return;
        }

        // Retrieve the data from the selected row
        DefaultTableModel model = (DefaultTableModel) timeTracker_table.getModel();
        String[] overtimeInfo = new String[model.getColumnCount() + 1]; // +1 to include OT hours column
        for (int i = 0; i < model.getColumnCount(); i++) {
            Object value = model.getValueAt(selectedRowIndex, i);
            overtimeInfo[i] = (value != null) ? value.toString() : ""; // Handle null values gracefully
        }

        // Calculate overtime hours
        String timeInStr = overtimeInfo[3]; // Assuming TimeIn is in the 4th column
        String timeOutStr = overtimeInfo[4]; // Assuming TimeOut is in the 5th column

        // Define working hours
        LocalTime endOfWorkingDay = LocalTime.of(17, 0); // 5 PM
        LocalTime startOfWorkingDay = LocalTime.of(8, 0); // 8 AM

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime timeIn = LocalTime.parse(timeInStr, formatter);
            LocalTime timeOut = LocalTime.parse(timeOutStr, formatter);

            // Check if timeOut is after endOfWorkingDay to calculate overtime
            if (timeOut.isAfter(endOfWorkingDay)) {
                long overtimeMinutes = Duration.between(endOfWorkingDay, timeOut).toMinutes();
                double overtimeHours = overtimeMinutes / 60.0;

                // Format overtime hours to two decimal places
                DecimalFormat df = new DecimalFormat("#.##");
                overtimeHours = Double.parseDouble(df.format(overtimeHours));

                overtimeInfo[overtimeInfo.length - 1] = String.valueOf(overtimeHours); // Add OT hours to the last column
            } else {
                overtimeInfo[overtimeInfo.length - 1] = "0.00"; // No overtime
            }

        } catch (DateTimeParseException e) {
            e.printStackTrace();
            overtimeInfo[overtimeInfo.length - 1] = "0.00"; // Handle parsing error, no overtime
        }

        // Pass the reference to the existing FrmHRpage instance to FrmRequestOvertime
        FrmRequestOvertime requestOvertime = new FrmRequestOvertime(this); // Pass the reference
        requestOvertime.populateFields(overtimeInfo);
        requestOvertime.setVisible(true);
        requestOvertime.setLocationRelativeTo(null); // Center the frame

        // Add a window listener to detect when the request overtime form is closed
        requestOvertime.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                // Reset the flag when the request overtime form is closed
                isRequestOvertimeFormOpen = false;
            }
        });
    }
    }//GEN-LAST:event_btnReqOvertimeActionPerformed

    private boolean isViewOvertimeHistoryOpen = false; // Flag to track if the view overtime history form is open
    
    private void btnViewOvertimeHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewOvertimeHistoryActionPerformed
        // Check if the view overtime history form is already open
        if (!isViewOvertimeHistoryOpen) {
            // Set the flag to indicate that the view overtime history form is now open
            isViewOvertimeHistoryOpen = true;

            FrmOvertimeHistory history = new FrmOvertimeHistory(this);
            history.getLblReqEid().setText(lblAttEid.getText());

            history.show();
            history.setLocationRelativeTo(null); // Center the frame

            String employeeId = lblAttEid.getText();
            history.displayDataForEmployee(employeeId); // Pass the employee ID

            // Add a window listener to detect when the view overtime history form is closed
            history.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    // Reset the flag when the view overtime history form is closed
                    isViewOvertimeHistoryOpen = false;
                }
            });
        }
    }//GEN-LAST:event_btnViewOvertimeHistoryActionPerformed

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
            java.util.logging.Logger.getLogger(FrmAttendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmAttendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmAttendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmAttendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmAttendance().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTable attendance_table;
    private javax.swing.JButton btnReqOvertime;
    public javax.swing.JButton btnView;
    private javax.swing.JButton btnViewOvertimeHistory;
    private java.awt.Checkbox checkbox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    public com.toedter.calendar.JMonthChooser jMCMonth;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public com.toedter.calendar.JYearChooser jYCYear;
    public javax.swing.JLabel lblAttEid;
    private javax.swing.JLabel lblAttFName;
    public javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblIDniEmployee;
    public javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblTotalWorkedHours;
    private javax.swing.JLabel lblWelcomeMsg;
    private javax.swing.JButton logOut_button;
    private javax.swing.JButton profile_button;
    private javax.swing.JButton request_button;
    private javax.swing.JButton salary_button;
    public javax.swing.JButton timeIn_button;
    public javax.swing.JButton timeOut_button;
    public javax.swing.JTable timeTracker_table;
    public javax.swing.JTextField txtTotalWorkedHours;
    // End of variables declaration//GEN-END:variables

    void addAttendanceRecord(String date, String startTime, String endTime) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    public void displayAttendanceData(List<String[]> attendanceData) {
    DefaultTableModel model = (DefaultTableModel) timeTracker_table.getModel();
    for (String[] rowData : attendanceData) {
        model.addRow(rowData);
    }
}


}

