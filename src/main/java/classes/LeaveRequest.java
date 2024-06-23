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
public class LeaveRequest {
    //Attributes
    public int EmployeeID;
    private Date ReqDate;
    private String LeaveType;
    private Date StartDate;
    private Date EndDate;
    private String Reason;
    private String Status;
    private String Approver;
    private Date DateResponded;

    public LeaveRequest(int requestId, int employeeId, String dateOfRequest, String leaveType, String startDate, String endDate, String reason, String status, String approver, String approvedDate) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public LeaveRequest(int employeeId, String dateOfRequest, String leaveType, String startDate, String endDate, String reason, String status, String approver, String approvedDate) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    //getter
    public int getEmployeeID() {
        return EmployeeID;
    }

    public Date getReqDate() {
        return ReqDate;
    }

    public String getLeaveType() {
        return LeaveType;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public String getReason() {
        return Reason;
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

    public void setLeaveType(String LeaveType) {
        this.LeaveType = LeaveType;
    }

    public void setStartDate(Date StartDate) {
        this.StartDate = StartDate;
    }

    public void setEndDate(Date EndDate) {
        this.EndDate = EndDate;
    }

    public void setReason(String Reason) {
        this.Reason = Reason;
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
public LeaveRequest(String[] leaveInfo) {
    this.EmployeeID = Integer.parseInt(leaveInfo[0]);
    this.ReqDate = parseDate(leaveInfo[1]);
    this.LeaveType = leaveInfo[2];
    this.StartDate = parseDate(leaveInfo[3]);
    this.EndDate = parseDate(leaveInfo[4]);
    this.Reason = leaveInfo[5];
    this.Status = leaveInfo.length > 6 ? leaveInfo[6] : ""; // Handle cases where leaveInfo may have fewer elements
    this.Approver = leaveInfo.length > 7 ? leaveInfo[7] : ""; // Similarly handle Approver
    this.DateResponded = leaveInfo.length > 8 ? parseDate(leaveInfo[8]) : null; // And ApprovedDate
}

//For reading of Request.csv
public static LeaveRequest[] readLeaveRequest(String fileLeaveRequest){
    String line = "";
    String csvSplitBy = ",";
    LeaveRequest[] leaveRequest = null;
    BufferedReader br = null;
    
    try{
        br = new BufferedReader(new FileReader(fileLeaveRequest));
        
        br.readLine();//skip header or 1st line sa csv
        
        // Count number of lines excluding header
        int numLeaveRequest = (int)br.lines().count(); // + 1; // Add 1 to include header
        leaveRequest = new LeaveRequest[numLeaveRequest];
        br.close();
        
        // Reinitialize BufferedReader to read from the beginning
        br = new BufferedReader(new FileReader(fileLeaveRequest));
        br.readLine(); //Skip header line
        
        int index = 0;      
        while((line = br.readLine()) != null) {
        String[] data = line.split(csvSplitBy);
        leaveRequest[index] = new LeaveRequest(data);
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
    return leaveRequest;
}

//Method for adding or writing leave request record in the csv file
    public void AddLeaveRequest(String CSVFilename) throws IOException {
    try (CSVWriter writer = new CSVWriter(new FileWriter(CSVFilename, true))) {
        String[] leaveRecord = new String[10];
        leaveRecord[0] = String.valueOf(EmployeeID);
        
        // Assuming date is a Date object
        Date date = new Date();

        // Create a SimpleDateFormat object with the desired date format
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        // Convert the Date object to a String
        String reqDateString = sdf.format(ReqDate);
        String startDateString = sdf.format(StartDate);
        String endDateString = sdf.format(EndDate);
        String dateRespondedString = sdf.format(DateResponded);
        
        leaveRecord[1] = reqDateString;
        leaveRecord[2] = LeaveType;
        leaveRecord[3] = startDateString;
        leaveRecord[4] = endDateString;
        leaveRecord[5] = Reason;
        leaveRecord[6] = Status;
        leaveRecord[7] = Approver;
        leaveRecord[8] = dateRespondedString;
       
        writer.writeNext(leaveRecord);
    }
}


}
