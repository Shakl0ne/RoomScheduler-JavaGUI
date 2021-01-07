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

public class ReservationQueries {
    private static Connection connection;
    private static PreparedStatement addReservationEntry;
    private static PreparedStatement getReservationsByDate;
    private static PreparedStatement getReservationsByFaculty;
    private static PreparedStatement getRoomsReservedByDate;
    private static PreparedStatement deleteReservation;
    private static PreparedStatement deleteReservationByRoom;
    private static PreparedStatement getSeatsByRoom;
    private static ResultSet resultSet;
    

    
    public static void addReservationEntry(String faculty, String room, Date date, int seats, Timestamp timestamp){
        connection = DBConnection.getConnection();
        try{
            addReservationEntry = connection.prepareStatement("INSERT INTO reservations"+ "(faculty, room, date, seats, timestamp)"+ "VALUES (?, ?, ?, ?, ?)");
            addReservationEntry.setString(1,faculty);
            addReservationEntry.setString(2,room);
            addReservationEntry.setDate(3, date);
            addReservationEntry.setInt(4,seats);
            addReservationEntry.setTimestamp(5,timestamp);
            addReservationEntry.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
            
    }
    public static List<ReservationEntry> getReservationsByDate(Date date){
        connection = DBConnection.getConnection();
        List<ReservationEntry> reservationByDate = new ArrayList<ReservationEntry>();

        try{
            getReservationsByDate = connection.prepareStatement("SELECT * FROM reservations WHERE date = ? ORDER BY timestamp");
            getReservationsByDate.setDate(1, date);
            resultSet = getReservationsByDate.executeQuery();
        
        }            
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        
        try {
            
            while(resultSet.next()){
                reservationByDate.add(new ReservationEntry(
                        resultSet.getString("faculty"),
                        resultSet.getString("room"),
                        resultSet.getDate("date"),
                        resultSet.getInt("seats"),
                        resultSet.getTimestamp("timestamp")));

                }
        
        }   
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
        return reservationByDate;
    }

    public static List<ReservationEntry> getReservationsByFaculty(String faculty){
        connection = DBConnection.getConnection();
        List<ReservationEntry> reservationByFaculty = new ArrayList<ReservationEntry>();

        try{
            getReservationsByFaculty = connection.prepareStatement("SELECT * FROM reservations WHERE faculty = ?");
            getReservationsByFaculty.setString(1, faculty);
            resultSet = getReservationsByFaculty.executeQuery();
        
        }            
        catch(SQLException sqlException){ 
            sqlException.printStackTrace();
        }
        
        try{        
            while(resultSet.next()){
                reservationByFaculty.add(new ReservationEntry(
                        resultSet.getString("faculty"),
                        resultSet.getString("room"),
                        resultSet.getDate("date"),
                        resultSet.getInt("seats"),
                        resultSet.getTimestamp("timestamp")));
                }
        
        }   
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
        return reservationByFaculty;
    }
    public static void deleteReservation(String faculty,Date date){
        connection = DBConnection.getConnection();
        try{
            deleteReservation = connection.prepareStatement("DELETE FROM reservations WHERE faculty = ? AND date = ?");
            deleteReservation.setString(1,faculty);
            deleteReservation.setDate(2, date);
            deleteReservation.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<String> getRoomsReservedByDate(Date date){
        connection = DBConnection.getConnection();
        ArrayList<String> roomsReservedByDate = new ArrayList<String>();
        
        try{
            getRoomsReservedByDate = connection.prepareStatement("SELECT room FROM reservations WHERE date = ?");
            getRoomsReservedByDate.setDate(1, date);
            resultSet = getRoomsReservedByDate.executeQuery();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        try{
            while(resultSet.next()){
                roomsReservedByDate.add(resultSet.getString("room"));
            }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return roomsReservedByDate;
    }
    
    public static void deleteReservationByRoom(String room,Date date){
        connection = DBConnection.getConnection();
        try{
            deleteReservationByRoom = connection.prepareStatement("DELETE FROM reservations WHERE room = ? AND date = ?");
            deleteReservationByRoom.setString(1,room);
            deleteReservationByRoom.setDate(2, date);
            deleteReservationByRoom.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        
    }        
    public static Integer getSeatsByRoom(String room, Date date){
        connection = DBConnection.getConnection();
        Integer seats = 0;

        try{
            getSeatsByRoom = connection.prepareStatement("SELECT seats FROM reservations WHERE room = ? AND date = ?");
            getSeatsByRoom.setString(1, room);
            getSeatsByRoom.setDate(2, date);
            resultSet = getReservationsByFaculty.executeQuery();
            seats = resultSet.getInt("Seats");
        }            
        catch(SQLException sqlException){ 
            sqlException.printStackTrace();
        }
    return seats;
    }
}
