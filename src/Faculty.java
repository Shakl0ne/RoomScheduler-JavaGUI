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

public class Faculty {
    private static Connection connection;
    private static PreparedStatement addFaculty;
    private static PreparedStatement getAllFaculty;
    private static ResultSet resultSet;
    
    public static void addFaculty(String name){
        connection = DBConnection.getConnection();
        try{
            addFaculty = connection.prepareStatement("INSERT INTO faculty (name) values (?)");
            addFaculty.setString(1,name);
            addFaculty.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        
    }
    public static ArrayList<String> getAllFaculty(){
        connection = DBConnection.getConnection();
        ArrayList<String> faculty = new ArrayList<String>();
          
        try{
            getAllFaculty = connection.prepareStatement("SELECT name FROM faculty ORDER BY name");
            resultSet = getAllFaculty.executeQuery();
            
            while(resultSet.next()){
                faculty.add(resultSet.getString(1));
            }
            
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return faculty;
    }
}
