/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Nuwanga
 */
public class RMIServer extends UnicastRemoteObject implements ServerService{
	

    public RMIServer() throws RemoteException{
        super();
    }
    private static Connection con;
    private PreparedStatement ps;

    /*Method to get sensor list and related data values of the sensors*/
    public ArrayList sensorlist() throws RemoteException {
        
        /*Array List for each coloumn in table*/
        ArrayList<String> pk = new ArrayList<String>();
        ArrayList<String> id = new ArrayList<String>();
        ArrayList<String> active = new ArrayList<String>();
        ArrayList<String> fnum = new ArrayList<String>();
        ArrayList<String> rnum = new ArrayList<String>();
        ArrayList<String> slevel = new ArrayList<String>();
        ArrayList<String> co2level = new ArrayList<String>();
        
        /*Array list to add the above arrayliist. Here 7 arraylist will be added to this array list. Reason for this
        is you cant pass a ResultSet directly using RMI. But you can pass arraylists using RMI*/
        ArrayList<ArrayList> list = new ArrayList<ArrayList>();
        
        //calling rest api to fetch data from database
        String jsonString;
        String finalJsonString = null;
        try {
            URL url = new URL("http://localhost:3000");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            //System.out.println("Output from Server .... \n");
            while ((jsonString = br.readLine()) != null) {
                    //System.out.println(jsonString);
                    finalJsonString = jsonString;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
              e.printStackTrace();
        } catch (Exception e) {
              e.printStackTrace();
        }
        //System.out.println(finalJsonString);

        //manipulating json object
        try{
            JSONArray jsonArray = new JSONArray(finalJsonString);

            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                
                pk.add(jsonObject1.optString("sensorId"));
                id.add(jsonObject1.optString("sensorNo"));
                active.add(jsonObject1.optString("active"));
                fnum.add(jsonObject1.optString("floorNo"));
                rnum.add(jsonObject1.optString("roomNo"));
                slevel.add(jsonObject1.optString("smokeLevel"));
                co2level.add(jsonObject1.optString("co2Level"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        
        /*7 array lists will be added to the list arraylist*/
        list.add(pk);
        list.add(id);
        list.add(active);
        list.add(fnum);
        list.add(rnum);
        list.add(slevel);
        list.add(co2level);
        
        return list;
    }
    
    
    /*Method to add sensors*/
    public int addSensor(String id, String fno, String rno) throws RemoteException {
        int responseCode = 0;
        
        //invoking rest api to add data
        try {
            URL url = new URL("http://localhost:3000/add");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            //setting json object
            String input = "{\"sensorNo\":"+id
                    + ",\"active\" :1,\"floorNo\":"+fno
                    + ", \"roomNo\":" +rno
                    + ", \"smokeLevel\": 5, \"co2Level\": 5}";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();


            //saving response code to a variable
            responseCode = conn.getResponseCode();


            conn.disconnect();

            } catch (MalformedURLException e) {           
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        if(responseCode == 200){
            return 1;
        }
        else
            return 0;
    }
    
    
    /*method to edit sensors*/
    public int updateSensor(String id,String active, String fno, String rno, String slevel, String co2level) throws RemoteException {
        int responseCode = 0;
        
        //invoking rest api to update data
        try {
            URL url = new URL("http://localhost:3000/update");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");

            //setting json object
            String input = "{\"sensorNo\":"+id
                    + ",\"active\" :" +active
                    + ",\"floorNo\":"+fno
                    + ", \"roomNo\":" +rno
                    + ", \"smokeLevel\":" +slevel
                    + ", \"co2Level\": " +co2level
                    + "}";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();


            //saving response code to a variable
            responseCode = conn.getResponseCode();

            conn.disconnect();

            } catch (MalformedURLException e) {           
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        if(responseCode == 200){
            return 1;
        }
        else
            return 0;

    }

    /*method to delete sensors*/
    public int delSensor(String id) throws RemoteException {
              int responseCode = 0;
        
        //invoking rest api to delete data
        try {
            URL url = new URL("http://localhost:3000/delete");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/json");

            //setting json object
            String input = "{\"sensorNo\":"+id
                    + "}";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();


            //saving response code to a variable
            responseCode = conn.getResponseCode();

            conn.disconnect();

            } catch (MalformedURLException e) {           
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        if(responseCode == 200){
            return 1;
        }
        else
            return 0;
    }
    
   public static void main(String[] args){
       /*To establish RMI connection*/
      System.setProperty("java.security.policy", "file:allowall.policy");
      
      try {
          Registry reg = LocateRegistry.createRegistry(1099);
          System.out.print(reg);
          ServerService cl = new RMIServer();
          reg.rebind("SensorService", cl);
      } catch (Exception e){
          e.printStackTrace();
      }
      
    }   
}
