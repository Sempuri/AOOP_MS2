/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;
import com.opencsv.CSVWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import javax.swing.JTable;


/**
 *
 * @author EMAN
 */
public class Employee {
    
    private static final java.lang.String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713427";
    private static final java.lang.String USERNAME = "sql12713427";
    private static final java.lang.String PASSWORD = "7l87SNw2yD";
    
    //image database
    private static final java.lang.String imgJDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12713671";
    private static final java.lang.String imgUSERNAME = "sql12713671";
    private static final java.lang.String imgPASSWORD = "KwHeJTT2C4";

    //Attributes
    public int _employeeID;
    private String _firstName;
    private String _lastName;
    private String _role;
    private String _department;
    private String _supervisor;
    private String _status;
    private Date _birthday;
    private String _sssNumber;
    private String _pagibigNumber;
    private String _philhealthNumber;
    private String _tinNumber;

    private String _address;
    private String _phonenumber;
    private String _position;
    private double _basicSalary;
    private double _riceSubsidy;
    private double _phoneAllowance;
    private double _clothingAllowance;
    private double _grossSemi;
    private double _hourlyRate;

    public Employee(int id, String firstName, String lastName, String role, String department, String supervisor, String status, String sssNumber, String philhealthNumber, String tinNumber, String pagibigNumber){
}

    public Employee(int id, String firstName, String lastName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    //Getter Methods
    public int GetEmployeeID() {
        return _employeeID;
    }
    public String GetFirstName() {
        return _firstName;
    }
    public String GetLastName() {
        return _lastName;
    }
    public String GetRole() {
        return _role;
    }
    public String GetDepartment() {
        return _department;
    }
    public String GetSupervisor() {
        return _supervisor;
    }
    public String GetStatus() {
        return _status;
    }
    public Date GetBirthday() {
        return _birthday;
    }
    public String GetSSSNumber() {
        return _sssNumber;
    }
    public String GetPagibigNumber() {
        return _pagibigNumber;
    }
    public String GetPhilhealthNumber() {
        return _philhealthNumber;
    }
    public String GetTinNumber() {
        return _tinNumber;
    }

    public String getAddress() {
        return _address;
    }

    public String getPhonenumber() {
        return _phonenumber;
    }

    public String getPosition() {
        return _position;
    }

    public double getBasicSalary() {
        return _basicSalary;
    }

    public double getRiceSubsidy() {
        return _riceSubsidy;
    }

    public double getPhoneAllowance() {
        return _phoneAllowance;
    }

    public double getClothingAllowance() {
        return _clothingAllowance;
    }

    public double getGrossSemi() {
        return _grossSemi;
    }

    public double getHourlyRate() {
        return _hourlyRate;
    }
    
    
    
    //Setter Methods
    
    public void setEmployeeID(int EmployeeID){
        _employeeID = EmployeeID;
    }
    public void setFirstName(String FirstName){
        _firstName = FirstName;
    }
    public void setLastName(String LastName){
        _lastName = LastName;
    }
    public void setRole(String Role){
        _role = Role;
    }
    public void setDepartment(String Department){
        _department = Department;
    }
    public void setSupervisor(String Supervisor){
        _supervisor = Supervisor;
    }
    public void setStatus(String Status){
        _status = Status;
    }
    public void setBirthday(Date Birthday){
        _birthday = Birthday;
    }
    public void setSSSNumber(String SSSNumber){
        _sssNumber = SSSNumber;
    }
    public void setPagibigNumber(String PagibigNumber){
        _pagibigNumber = PagibigNumber;
    }
    public void setPhilhealthNumber(String PhilhealthNumber){
        _philhealthNumber = PhilhealthNumber;
    }
    public void setTinNumber(String TinNumber){
        _tinNumber = TinNumber;
    }

    //addition to the setter
    public void setAddress(String _address) {
        this._address = _address;
    }

    public void setPhonenumber(String _phonenumber) {
        this._phonenumber = _phonenumber;
    }

    public void setPosition(String _position) {
        this._position = _position;
    }

    public void setBasicSalary(double _basicSalary) {
        this._basicSalary = _basicSalary;
    }

    public void setRiceSubsidy(double _riceSubsidy) {
        this._riceSubsidy = _riceSubsidy;
    }

    public void setPhoneAllowance(double _phoneAllowance) {
        this._phoneAllowance = _phoneAllowance;
    }

    public void setClothingAllowance(double _clothingAllowance) {
        this._clothingAllowance = _clothingAllowance;
    }

    public void setGrossSemi(double _grossSemi) {
        this._grossSemi = _grossSemi;
    }

    public void setHourlyRate(double _hourlyRate) {
        this._hourlyRate = _hourlyRate;
    }

 
    //constructor
    public Employee(String[] empInfo) {
        this._employeeID = Integer.parseInt(empInfo[0]);
        this._firstName = empInfo[2];
        this._lastName = empInfo[1];
        this._role = empInfo[11];
        this._department = empInfo[12];
        this._supervisor = empInfo[13];
        this._status = empInfo[10];
        
        // Assuming empInfo[3] contains the date in the format "MM/dd/yyyy"
        String dateStr = empInfo[3];
        String[] dateParts = dateStr.split("/");

        int month = Integer.parseInt(dateParts[0]);
        int day = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day); // Month is 0-based in Calendar, hence month - 1

        Date birthday = cal.getTime();

        this._birthday = birthday;
        
        this._sssNumber = empInfo[6];
        this._pagibigNumber = empInfo[9];
        this._philhealthNumber = empInfo[7];
        this._tinNumber = empInfo[8];
        
        this._address = empInfo[4];
        this._phonenumber = empInfo[5];
        this._position = empInfo[11];
        this._basicSalary = Double.parseDouble(empInfo[14]);
        this._riceSubsidy = Double.parseDouble(empInfo[15]);
        this._phoneAllowance = Double.parseDouble(empInfo[16]);
        this._clothingAllowance = Double.parseDouble(empInfo[17]);
        this._grossSemi = Double.parseDouble(empInfo[18]);
        this._hourlyRate = Double.parseDouble(empInfo[19]);
        
        
        
    }
//For reading of employee.csv
/*public static Employee[] readEmployee(String fileEmployee){
    String line = "";
    String csvSplitBy = ",";
    Employee[] employees = null;
    BufferedReader br = null;
    
    try{
        br = new BufferedReader(new FileReader(fileEmployee));
        
        br.readLine();//skip header or 1st line sa csv
        
        // Count number of lines excluding header
        int numEmployee = (int)br.lines().count(); // + 1; // Add 1 to include header
        employees = new Employee[numEmployee];
        br.close();
        
        // Reinitialize BufferedReader to read from the beginning
        br = new BufferedReader(new FileReader(fileEmployee));
        br.readLine(); //Skip header line
        
        int index = 0;      
        while((line = br.readLine()) != null) {
        String[] data = line.split(csvSplitBy);
        employees[index] = new Employee(data);
        index++;
          
    }
        
    } catch (IOException e){
        e.printStackTrace();
        
    } finally {
        if (br !=null){
            try {
                br.close();
            }
            catch (IOException e){
        e.printStackTrace();
        }
            
    }
}
    return employees;
}*/
    
public static Employee[] readEmployeesFromDatabase() {
    Connection conn = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    List<Employee> employeeList = new ArrayList<>();

    try {
        // Establish database connection
        Connection con = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        String sql = "SELECT * FROM employee";
        statement = con.prepareStatement(sql);

        // Execute the query
        resultSet = statement.executeQuery();

        // Iterate over the result set
        while (resultSet.next()) {
            int id = resultSet.getInt("EmployeeNum");
            String firstName = resultSet.getString("FirstName");
            String lastName = resultSet.getString("LastName");
            String role = resultSet.getString("Role");
            String department = resultSet.getString("Department");
            String supervisor = resultSet.getString("ImmediateSupervisor");
            String status = resultSet.getString("Status");
            Date birthday = resultSet.getDate("Birthday");
            String sssNumber = resultSet.getString("SssNumber");
            String pagibigNumber = resultSet.getString("PagibigNumber");
            String philhealthNumber = resultSet.getString("PhilhealthNumber");
            String tinNumber = resultSet.getString("TinNumber");
            String address = resultSet.getString("Address");
            String phoneNumber = resultSet.getString("PhoneNumber");
            String position = resultSet.getString("Position");
            double basicSalary = resultSet.getDouble("BasicSalary");
            double riceSubsidy = resultSet.getDouble("RiceSubsidy");
            double phoneAllowance = resultSet.getDouble("PhoneAllowance");
            double clothingAllowance = resultSet.getDouble("ClothingAllowance");
            double grossSemi = resultSet.getDouble("GrossSemiMR");
            double hourlyRate = resultSet.getDouble("HourlyRate");

            Employee employee = new Employee(id, firstName, lastName);
            employee.setRole(role);
            employee.setDepartment(department);
            employee.setSupervisor(supervisor);
            employee.setStatus(status);
            employee.setBirthday(birthday);
            employee.setSSSNumber(sssNumber);
            employee.setPagibigNumber(pagibigNumber);
            employee.setPhilhealthNumber(philhealthNumber);
            employee.setTinNumber(tinNumber);
            employee.setAddress(address);
            employee.setPhonenumber(phoneNumber);
            employee.setPosition(position);
            employee.setBasicSalary(basicSalary);
            employee.setRiceSubsidy(riceSubsidy);
            employee.setPhoneAllowance(phoneAllowance);
            employee.setClothingAllowance(clothingAllowance);
            employee.setGrossSemi(grossSemi);
            employee.setHourlyRate(hourlyRate);

            employeeList.add(employee);
        }
    } catch (SQLException e) {
    } finally {
        // Close resources
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    // Convert list to array
    Employee[] employees = new Employee[employeeList.size()];
    return employeeList.toArray(employees);
}


 // Method to retrieve employees from the database
 public static List<Employee> getEmployeesFromDatabase() {
    List<Employee> employeeList = new ArrayList<>();
    String query = "SELECT * FROM employee";

    try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         PreparedStatement statement = conn.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {
            int id = resultSet.getInt("EmployeeNum");
            String firstName = resultSet.getString("FirstName");
            String lastName = resultSet.getString("LastName");
            String role = resultSet.getString("Role");
            String department = resultSet.getString("Department");
            String supervisor = resultSet.getString("ImmediateSupervisor");
            String status = resultSet.getString("Status");
            Date birthday = resultSet.getDate("Birthday");
            String sssNumber = resultSet.getString("SssNumber");
            String pagibigNumber = resultSet.getString("PagibigNumber");
            String philhealthNumber = resultSet.getString("PhilhealthNumber");
            String tinNumber = resultSet.getString("TinNumber");
            String address = resultSet.getString("Address");
            String phoneNumber = resultSet.getString("PhoneNumber");
            String position = resultSet.getString("Position");
            double basicSalary = resultSet.getDouble("BasicSalary");
            double riceSubsidy = resultSet.getDouble("RiceSubsidy");
            double phoneAllowance = resultSet.getDouble("PhoneAllowance");
            double clothingAllowance = resultSet.getDouble("ClothingAllowance");
            double grossSemi = resultSet.getDouble("GrossSemiMR");
            double hourlyRate = resultSet.getDouble("HourlyRate");

            Employee employee = new Employee(id, firstName, lastName);
            employee.setRole(role);
            employee.setDepartment(department);
            employee.setSupervisor(supervisor);
            employee.setStatus(status);
            employee.setBirthday(birthday);
            employee.setSSSNumber(sssNumber);
            employee.setPagibigNumber(pagibigNumber);
            employee.setPhilhealthNumber(philhealthNumber);
            employee.setTinNumber(tinNumber);
            employee.setAddress(address);
            employee.setPhonenumber(phoneNumber);
            employee.setPosition(position);
            employee.setBasicSalary(basicSalary);
            employee.setRiceSubsidy(riceSubsidy);
            employee.setPhoneAllowance(phoneAllowance);
            employee.setClothingAllowance(clothingAllowance);
            employee.setGrossSemi(grossSemi);
            employee.setHourlyRate(hourlyRate);

            employeeList.add(employee);
        }
    } catch (SQLException e) {
    }

    return employeeList;
}



//Method for adding or writing employee record in the csv file
  /*  public void AddEmployee(String CSVFilename) throws IOException {
    try (CSVWriter writer = new CSVWriter(new FileWriter(CSVFilename, true))) {
        String[] employeeRecord = new String[20];
        employeeRecord[0] = String.valueOf(_employeeID);
        employeeRecord[1] = _lastName;
        employeeRecord[2] = _firstName;
       
        // Assuming date is a Date object
        Date date = new Date();

        // Create a SimpleDateFormat object with the desired date format
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        // Convert the Date object to a String
        String bdayString = sdf.format(_birthday);
        
        employeeRecord[3] = bdayString;
        employeeRecord[4] = _address;
        employeeRecord[5] = _phonenumber;
        employeeRecord[6] = _sssNumber;
        employeeRecord[7] = _philhealthNumber;
        employeeRecord[8] = _tinNumber;
        employeeRecord[9] = _pagibigNumber;
        employeeRecord[10] = _status;
        employeeRecord[11] = _position;
        employeeRecord[12] = _department;
        employeeRecord[13] = _supervisor;
        employeeRecord[14] = String.valueOf(_basicSalary);
        employeeRecord[15] = String.valueOf(_riceSubsidy);
        employeeRecord[16] = String.valueOf(_phoneAllowance);
        employeeRecord[17] = String.valueOf(_clothingAllowance);
        employeeRecord[18] = String.valueOf(_grossSemi);
        employeeRecord[19] = String.valueOf(_hourlyRate);
        
        writer.writeNext(employeeRecord);
    }
} */

public void addEmployeeToDatabase() {
    try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);) {
        // Prepare the SQL INSERT statement
        String sql = "INSERT INTO employee (EmployeeNum, LastName, FirstName, Birthday, Address, PhoneNumber, SssNumber, PhilhealthNumber, TinNumber, PagibigNumber, Status, Position, Department, ImmediateSupervisor, BasicSalary, RiceSubsidy, PhoneAllowance, ClothingAllowance, GrossSemiMR, HourlyRate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            // Set the values for the parameters in the SQL statement
            statement.setInt(1, _employeeID);
            statement.setString(2, _lastName);
            statement.setString(3, _firstName);
            statement.setDate(4, new java.sql.Date(_birthday.getTime()));
            statement.setString(5, _address);
            statement.setString(6, _phonenumber);
            statement.setString(7, _sssNumber);
            statement.setString(8, _philhealthNumber);
            statement.setString(9, _tinNumber);
            statement.setString(10, _pagibigNumber);
            statement.setString(11, _status);
            statement.setString(12, _position);
            statement.setString(13, _department);
            statement.setString(14, _supervisor);
            statement.setDouble(15, _basicSalary);
            statement.setDouble(16, _riceSubsidy);
            statement.setDouble(17, _phoneAllowance);
            statement.setDouble(18, _clothingAllowance);
            statement.setDouble(19, _grossSemi);
            statement.setDouble(20, _hourlyRate);

            // Execute the SQL INSERT statement
            statement.executeUpdate();
        }
    } catch (SQLException e) {
    }
}

}
   

