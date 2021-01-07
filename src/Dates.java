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
import java.sql.Date;

public class Dates {
    private static Connection connection;
    private static PreparedStatement addDate;
    private static PreparedStatement getAllDates;
    private static ResultSet resultSet;
    
    public static void addDate(Date date){
        connection = DBConnection.getConnection();
        try{
            addDate = connection.prepareStatement("INSERT INTO dates (date) values (?)");
            addDate.setDate(1, date);
            addDate.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        
    }
    public static ArrayList<Date> getAllDates() {
        connection = DBConnection.getConnection();
        ArrayList<Date> date = new ArrayList<Date>();
          
        try{
            getAllDates = connection.prepareStatement("SELECT date FROM dates");
            resultSet = getAllDates.executeQuery();
            
            while(resultSet.next()){
                date.add(resultSet.getDate(1));
            }
            
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return date;
    }
        

    
}

