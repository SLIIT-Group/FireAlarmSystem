package sensorsclient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class SensorClient {
    private static Connection conn = DBConnect.DBConn();
    private static PreparedStatement pst;
    private static ResultSet rs;
    
    public static void main(String args[]) {
    	//creating timer  
    	Timer t = new Timer();
    	
    	//creating timertask
    	TimerTask tt = new TimerTask() {  
    	    @Override  
    	    public void run(){
    	    	Random rand = new Random();
    	    	
                try {
                    String query = "SELECT * FROM sensor_list";
    	            pst = conn.prepareStatement(query);
    	            rs = pst.executeQuery();

                        while (rs.next()) {
                            //System.out.println(rs.getInt(1));
                            pst = conn.prepareStatement("UPDATE sensor_list SET  smokeLevel = ?, co2Level = ? WHERE sensorId = ? ");
                            pst.setString(1, Integer.toString(rand.nextInt(5)));
                            pst.setString(2, Integer.toString(rand.nextInt(8)));
                            pst.setString(3, Integer.toString(rs.getInt(1)));

                            pst.execute();
                        }
					
    				   				
                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }

    	    };  
    	};

    	//executing the timer
    	t.scheduleAtFixedRate(tt,0,10000);
    }
}
