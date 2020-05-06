/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiserver;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author Nuwanga
 */
public interface ServerService extends Remote {
    public  ArrayList sensorlist()  throws RemoteException;
    public int addSensor(String id, String fno, String rno) throws RemoteException;
    public int updateSensor(String id,String active, String fno, String rno, String slevel, String co2level) throws RemoteException;
    public int delSensor(String id) throws RemoteException;
}


 
