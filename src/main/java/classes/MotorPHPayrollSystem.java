/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;
import frames.FrmLogin;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author EMAN
 */


public class MotorPHPayrollSystem {
    
    // JDBC URL, username, and password for MySQL database
    private static final String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final String USERNAME = "sql12713427";
    private static final String PASSWORD = "7l87SNw2yD";

    public static void main(String[] args) {
        if (isInternetAvailable()) {
            ImageIcon icon = loadIconFromDatabase("icon");

            try {
                // Attempt to connect to the database
                Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

                // If successfully connected, launch the login frame
                FrmLogin _login = new FrmLogin();
                _login.setIconImage(icon.getImage()); // Set the loaded icon as application icon
                _login.setVisible(true);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Failed to connect to the database.\nError: " + e.getMessage(),
                        "Database Connection Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1); // Exit application on database connection failure
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MotorPHPayrollSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please check your connection. Internet is required to open the application.",
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0); // Exit application on internet connection failure
        }
    }

    // Method to check internet connectivity
    private static boolean isInternetAvailable() {
        try (Socket socket = new Socket()) {
            SocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53); // Google's DNS server
            socket.connect(socketAddress, 2000); // 2-second timeout
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Method to load icon from database
    private static ImageIcon loadIconFromDatabase(String frameName) {
        ImageIcon icon = null;

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT Img FROM Image WHERE Frame = ?")) {

            stmt.setString(1, frameName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Retrieve icon data from database
                    byte[] imageData = rs.getBytes("Img");

                    // Convert byte array to ImageIcon
                    if (imageData != null) {
                        icon = new ImageIcon(imageData);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading icon from database: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return icon;
    }
    
}
