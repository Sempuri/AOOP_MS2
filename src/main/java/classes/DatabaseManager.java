/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author yen
 */

public class DatabaseManager {

    // JDBC URL, username, and password for your database
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";

    // Method to establish a connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    // Method to execute a query and return a ResultSet
    public static ResultSet executeQuery(String sql, Object... params) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            statement = conn.prepareStatement(sql);
            // Set parameters if there are any
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            // Execute query and get the result set
            resultSet = statement.executeQuery();
        } finally {
            // Close resources
            closeResources(conn, statement, null);
        }
        return resultSet;
    }

    // Method to close the connection, statement, and result set
    private static void closeResources(Connection conn, PreparedStatement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Method to retrieve payroll information from the database
    public static ResultSet retrievePayrollInfoFromDatabase() throws SQLException {
        ResultSet resultSet = null;
        try {
            String sql = "SELECT e.EmployeeNum, e.LastName, e.FirstName, e.Position, " +
                     "s.BasicSalary, s.GrossSMRate, s.HourlyRate, " +
                     "a.RiceSubsidy, a.PhoneAllowance, a.ClothingAllowance, " +
                     "COALESCE(a.DateUpdated, s.DateUpdated) AS DateUpdated " +
                     "FROM employee e " +
                     "LEFT JOIN allowance a ON e.EmployeeNum = a.EmployeeNum " +
                     "LEFT JOIN salary s ON e.EmployeeNum = s.EmployeeNum";
            resultSet = executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
        return resultSet;
    }
}
