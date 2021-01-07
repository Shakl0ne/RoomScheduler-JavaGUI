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
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoomQueries {
    private static Connection connection;
    private static PreparedStatement addRoom;
    private static PreparedStatement dropRoom;
    private static PreparedStatement getAllPossibleRooms;
    private static PreparedStatement getSeats;
    private static ResultSet resultSet;
    
    public static List<RoomEntry> getAllPossibleRooms(){
        connection = DBConnection.getConnection();
        List<RoomEntry> possibleRooms = new ArrayList<RoomEntry>();
          
        try{
            getAllPossibleRooms = connection.prepareStatement("SELECT * FROM rooms ORDER BY seats");
            resultSet = getAllPossibleRooms.executeQuery();
            
            while(resultSet.next()){
                possibleRooms.add(new RoomEntry(
                    resultSet.getString("name"),
                    resultSet.getInt("seats")));

            }
            
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        return possibleRooms;
        
    }
    
    public static void addRoom(String name, int seats){
        connection = DBConnection.getConnection();
        try{
            addRoom = connection.prepareStatement("INSERT INTO rooms"+ "(name, seats)"+ "VALUES (?, ?)");
            addRoom.setString(1,name);
            addRoom.setInt(2,seats);
            addRoom.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        
    }
    
    public static void dropRoom(String name){
        connection = DBConnection.getConnection();
        try{
            dropRoom = connection.prepareStatement("DELETE FROM rooms WHERE name = ?");
            dropRoom.setString(1,name);
            dropRoom.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        
    }
    public static Integer getSeats(String name){
        connection = DBConnection.getConnection();
        Integer seats = 0;
        try{
            getSeats = connection.prepareStatement("SELECT seats FROM rooms WHERE name = ?");
            getSeats.setString(1,name);
            resultSet = getSeats.executeQuery();
            
            while(resultSet.next()){
                seats = resultSet.getInt("seats");

            }
            
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }

        return seats;
        
    }
        
}
