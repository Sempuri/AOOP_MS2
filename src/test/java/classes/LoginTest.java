package classes;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

public class LoginTest {

    private Login instance;
    
    @BeforeEach
    public void setUp() {
        // Initialize with known values
        String[] accInfo = {"emp1", "MotorPH1", "Employee"};
        instance = new Login(accInfo);
    }
    
    @Test
    public void testVerifyLogin() throws ClassNotFoundException, SQLException {
        System.out.println("verifyLogin");

        // Valid credentials
        boolean result = instance.verifyLogin("emp1", "MotorPH1".toCharArray());
        assertTrue(result, "Login should succeed with valid credentials");
        assertEquals("Employee", instance.getRole(), "Role should be 'Employee'");
    }

    @Test
    public void testInvalidLogin() throws ClassNotFoundException, SQLException {
        System.out.println("verifyInvalidLogin");

        // Invalid password for existing user "emp1"
        boolean result = instance.verifyLogin("emp2", "MotorPH1".toCharArray());
        assertFalse(result, "Login should fail with invalid password");
    }

    @Test
    public void testNonExistingUser() throws ClassNotFoundException, SQLException {
        System.out.println("verifyNonExistingUser");

        // Non-existing user "nonexistentuser"
        boolean result = instance.verifyLogin("emp123", "MotorPH1".toCharArray());
        assertFalse(result, "Login should fail for non-existing user");
    }
}

