/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensorsclient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nuwanga
 */
public class DBConnect {
    
    private static Connection conn;
    
    public static Connection DBConn(){
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/sensor_db", "root", "");
            System.out.println("Database Connected!");
                    
                    } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return conn;
    }
    
}
