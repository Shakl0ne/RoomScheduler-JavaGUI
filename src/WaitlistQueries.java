/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shaco
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;

public class WaitlistQueries {
    private static Connection connection;
    private static PreparedStatement getWaitlistByDate;
    private static PreparedStatement getWaitlistByFaculty;
    private static PreparedStatement addWaitlistEntry;
    private static PreparedStatement deleteWaitlistEntry;
    private static ResultSet resultSet;
    
    public static List<WaitlistEntry> getWaitlistByDate(Date date){
        connection = DBConnection.getConnection();
        List<WaitlistEntry> waitlistByDate = new ArrayList<WaitlistEntry>();

        try{
            getWaitlistByDate = connection.prepareStatement("SELECT * FROM waitlist WHERE date = ? ORDER BY timestamp");
            getWaitlistByDate.setDate(1,date);
            resultSet = getWaitlistByDate.executeQuery();
        
        }            
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        
        try {
            
            while(resultSet.next()){
                waitlistByDate.add(new WaitlistEntry(
                        resultSet.getString("faculty"),
                        resultSet.getDate("date"),
                        resultSet.getInt("seats"),
                        resultSet.getTimestamp("timestamp")));

                }
        
        }   
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
        return waitlistByDate;
    }    
    
    public static List<WaitlistEntry> getWaitlistByFaculty(String faculty){
        connection = DBConnection.getConnection();
        List<WaitlistEntry> waitlistByFaculty = new ArrayList<WaitlistEntry>();

        try{
            getWaitlistByFaculty = connection.prepareStatement("SELECT * FROM reservations WHERE faculty = ?");
            getWaitlistByFaculty.setString(1, faculty);
            resultSet = getWaitlistByFaculty.executeQuery();
        
        }            
        catch(SQLException sqlException){ 
            sqlException.printStackTrace();
        }
        
        try{        
            while(resultSet.next()){
                waitlistByFaculty.add(new WaitlistEntry(
                        resultSet.getString("faculty"),
                        resultSet.getDate("date"),
                        resultSet.getInt("seats"),
                        resultSet.getTimestamp("timestamp")));
                }
        
        }   
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
        return waitlistByFaculty;
    }
        
    public static void addWaitlistEntry(String faculty, Date date, int seats, Timestamp timestamp){
        connection = DBConnection.getConnection();
        try{
            addWaitlistEntry = connection.prepareStatement("INSERT INTO waitlist"+ "(faculty, date, seats, timestamp)"+ "VALUES (?, ?, ?, ?)");
            addWaitlistEntry.setString(1,faculty);
            addWaitlistEntry.setDate(2, date);
            addWaitlistEntry.setInt(3,seats);
            addWaitlistEntry.setTimestamp(4,timestamp);
            addWaitlistEntry.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
            
    }
  
    public static void deleteWaitlistEntry(String faculty, Date date){
        connection = DBConnection.getConnection();
        try{
            deleteWaitlistEntry = connection.prepareStatement("DELETE FROM waitlist WHERE faculty = ? AND date = ?");
            deleteWaitlistEntry.setString(1,faculty);
            deleteWaitlistEntry.setDate(2, date);
            deleteWaitlistEntry.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
            
    }
}
