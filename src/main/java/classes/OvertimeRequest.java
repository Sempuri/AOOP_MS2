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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author EMAN
 */
public class OvertimeRequest {
    
    //Attributes
    public int EmployeeID;
    private Date ReqDate;
    private Date OtDate;
    private String TimeIn;
    private String TimeOut;
    private double TotalWorkedHours;
    private String Status;
    private String Approver;
    private Date DateResponded;

    public OvertimeRequest(int employeeId, String requestDate, String otDate, String timeIn, String timeOut, double totalWorkedHrs, String status, String approver, String dateResponded) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
    //getter
    public int getEmployeeID() {
        return EmployeeID;
    }

    public Date getReqDate() {
        return ReqDate;
    }

    public Date getOtDate() {
        return OtDate;
    }

    public String getTimeIn() {
        return TimeIn;
    }

    public String getTimeOut() {
        return TimeOut;
    }

    public double getTotalWorkedHours() {
        return TotalWorkedHours;
    }

    public String getStatus() {
        return Status;
    }

    public String getApprover() {
        return Approver;
    }

    public Date getDateResponded() {
        return DateResponded;
    }
    
    
    //setter

    public void setEmployeeID(int EmployeeID) {
        this.EmployeeID = EmployeeID;
    }

    public void setReqDate(Date ReqDate) {
        this.ReqDate = ReqDate;
    }

    public void setOtDate(Date OtDate) {
        this.OtDate = OtDate;
    }

    public void setTimeIn(String TimeIn) {
        this.TimeIn = TimeIn;
    }

    public void setTimeOut(String TimeOut) {
        this.TimeOut = TimeOut;
    }

    public void setTotalWorkedHours(double TotalWorkedHours) {
        this.TotalWorkedHours = TotalWorkedHours;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public void setApprover(String Approver) {
        this.Approver = Approver;
    }

    public void setDateResponded(Date DateResponded) {
        this.DateResponded = DateResponded;
    }
    
    // Method to parse date string into a Date object
    private Date parseDate(String dateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            return dateFormat.parse(dateStr);
        } catch (ParseException ex) {
            ex.printStackTrace(); // Handle parsing exception
            return null;
        }
    }
    
    // Constructor
    public OvertimeRequest(String[] overtimeInfo) {
        this.EmployeeID = Integer.parseInt(overtimeInfo[0]);
        this.ReqDate = parseDate(overtimeInfo[1]);
        this.OtDate = parseDate(overtimeInfo[2]);
        this.TimeIn = overtimeInfo[3];
        this.TimeOut = overtimeInfo[4];
        this.TotalWorkedHours = Double.parseDouble(overtimeInfo[5]);
        this.Status = overtimeInfo.length > 6 ? overtimeInfo[6] : ""; // Handle cases where leaveInfo may have fewer elements
        this.Approver = overtimeInfo.length > 7 ? overtimeInfo[7] : ""; // Similarly handle Approver
        this.DateResponded = overtimeInfo.length > 8 ? parseDate(overtimeInfo[8]) : null; // And ApprovedDate
    } 
    
    //For reading of Request.csv
    public static OvertimeRequest[] readOvertimeRequest(String fileOvertimeRequest){
        String line = "";
        String csvSplitBy = ",";
        OvertimeRequest[] overtimeRequest = null;
        BufferedReader br = null;

        try{
            br = new BufferedReader(new FileReader(fileOvertimeRequest));

            br.readLine();//skip header or 1st line sa csv

            // Count number of lines excluding header
            int numOvertimeRequest = (int)br.lines().count(); // + 1; // Add 1 to include header
            overtimeRequest = new OvertimeRequest[numOvertimeRequest];
            br.close();

            // Reinitialize BufferedReader to read from the beginning
            br = new BufferedReader(new FileReader(fileOvertimeRequest));
            br.readLine(); //Skip header line

            int index = 0;      
            while((line = br.readLine()) != null) {
            String[] data = line.split(csvSplitBy);
            overtimeRequest[index] = new OvertimeRequest(data);
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
        return overtimeRequest;
    }

    
    //Method for adding or writing leave request record in the csv file
    public void AddOvertimeRequest(String CSVFilename) throws IOException {
    try (CSVWriter writer = new CSVWriter(new FileWriter(CSVFilename, true))) {
        String[] overtimeRecord = new String[10];
        overtimeRecord[0] = String.valueOf(EmployeeID);
        
        // Assuming date is a Date object
        Date date = new Date();

        // Create a SimpleDateFormat object with the desired date format
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        // Convert the Date object to a String
        String reqDateString = sdf.format(ReqDate);
        String otDateString = sdf.format(OtDate);
        String dateRespondedString = sdf.format(DateResponded);
        
        overtimeRecord[1] = reqDateString;
        overtimeRecord[2] = otDateString;
        overtimeRecord[3] = TimeIn;
        overtimeRecord[4] = TimeOut;
        overtimeRecord[5] = String.valueOf(TotalWorkedHours);
        overtimeRecord[6] = Status;
        overtimeRecord[7] = Approver;
        overtimeRecord[8] = dateRespondedString;
       
        writer.writeNext(overtimeRecord);
    }
}
    
}
