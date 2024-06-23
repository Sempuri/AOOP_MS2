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
 * @author EMAN
 */
public class Login {
    //Attributes
    private String _username;
    private String _password;
    private String _role;
    
    //main database
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";
    
    
    //Setter Method only; No Getter method as its only purpose is to validate login credentials
    public void SetUsername(String Username) {
        _username = Username;
    }
    public void SetPassword(char[] Password){
        _password = new String(Password);
    }

    public void setRole(String _role) {
        this._role = _role;
    }
    
    //Getter Method (for verification and matching of employee ID)
    public String getUsername()  {    
        return _username; 
    }

    public String getPassword() {
        return _password;
    }
    
    public String getRole() {
        return _role;
    }
    
    
    // Constructor
    public Login(String[] accInfo) {
        this._username = accInfo[0];
        this._password = accInfo[1];
        this._role = accInfo[2];
    }
    
   public boolean verifyLogin(String enteredUsername, char[] enteredPassword) throws ClassNotFoundException, SQLException {
       String enteredPasswordString = new String(enteredPassword);
       
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String sql = "SELECT * FROM login WHERE BINARY Username = ? AND BINARY Password = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, enteredUsername);
                statement.setString(2, new String(enteredPasswordString));
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        _role = resultSet.getString("Role");
                        return true;
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
        return false;
    }

}

