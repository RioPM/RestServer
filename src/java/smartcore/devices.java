/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 *
 * @author rio
 */
class Device {

    int id;
    String name;
    int type;
    
    public Device(int a, String b, int c) {
        id = a;
        name = b;
        type = c;
    }
    
    public String toString() {
        return id + " " + name + " " + type;
    }

}

@Stateless
@Path("/devices")
public class devices {
    
    private static List<Device> device = new ArrayList<Device> ();

    @GET
    public String getdevices(@QueryParam("username") String username, @QueryParam("password") String password) {
        /*device = new Device[] {
            new Device(1, "LlumPassadis", 1),
            new Device(2, "LlumMenjador", 1)
        };*/
        /*if (device.size() == 0) {
            Device d1 = new Device(1, "LlumPassadis", 1);
            Device d2 = new Device(2, "LlumMenjador", 1);
            device.add(0, d1);
            device.add(1, d2);
        }*/
        return $getdevices(username);
    }
    
    String $getdevices(String username) {
        String result = null;
        /*for (int i = 0; i < device.length; i++) {
            if (i == 0) result = Arrays.toString(device);
            else result += Arrays.toString(device);
        }*/
        /*if (device.length > 0) result = Arrays.toString(device);*/
        /*for (Device d: device) {
            if (device.indexOf(d) == 0 && d != null) result = d.toString() + "\n";
            else result += (d.toString() + "\n");
        }
        System.out.println(result);*/
        try {
            Class.forName("org.sqlite.JDBC");
        }
        catch (ClassNotFoundException e){
            System.err.println(e.getMessage());
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:/home/rio/NetBeansProjects/pti.sqlite");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select name,type,state from devices where user = \""+ username +"\"");
            
            while (rs.next()) {
                if (result == null) result = rs.getString("name") + " " + rs.getString("type") + " " + rs.getString("state") + "\n";
                else result += (rs.getString("name") + " " + rs.getString("type") + " " + rs.getString("state") + "\n");
            }
            connection.close();
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if (connection != null)
                    connection.close();
            }
            catch (SQLException e) {
                //Error en tancar la connexio
                System.err.println(e.getMessage());
            }
        }
        return result;
    }
    
    @POST
    public String adddevice(@FormParam("user") String user, @FormParam("name") String name, @FormParam("type") int type, @FormParam("state") int state) {
        return Integer.toString($adddevice(user,name,type,state));
    }
    
    int $adddevice(String user, String name, int type, int state) {
        int result = -1;
        /*device = new Device[] {
            new Device(1, "LlumPassadis", 1),
            new Device(2, "LlumMenjador", 1),
            new Device(3, name, type)
        };
        if (device.length > 2) result = 1;*/
        /*int id = device.size();
        if (name != null) device.add(new Device(id+1, name + (id+1), type));
        if (id < device.size()) result = 1;*/
        try {
            Class.forName("org.sqlite.JDBC");
        }
        catch (ClassNotFoundException e){
            System.err.println(e.getMessage());
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:/home/rio/NetBeansProjects/pti.sqlite");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select count (*) as size from devices");
            int size = rs.getInt("size");
            String id = String.valueOf(size+1);
            String update = "error";
            /*update = "INSERT INTO devices values ( '"+ 
                                    id +"','"+
                                    "\"" + name + "\"" +"','"+
                                    type +"','"+
                                    state +"','"+                    
                                    "\"" + user + "\"" +"','"+"');";*/
            update = "INSERT INTO devices VALUES ('" +
                                    (size+1) + "','" +
                                    name + "','" +
                                    type + "','" + 
                                    state + "','" +                    
                                    user + "');";
            System.out.println(update);
            statement.executeUpdate(update);
            result = size+2;
            connection.close();
            if (update.equals("error")) return 0;
            else return 1;
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if (connection != null)
                    connection.close();
            }
            catch (SQLException e) {
                //Error en tancar la connexio
                System.err.println(e.getMessage());
            }
        }
        return result;
    }
    
    @DELETE
    public String deletedevice(@FormParam("user") String user, @FormParam("name") String name, @FormParam("type") int type) {
        return Integer.toString($deletedevice(user,name,type));
    }
    
    int $deletedevice(String user, String name, int type) {
        int result = -1;
        try {
            Class.forName("org.sqlite.JDBC");
        }
        catch (ClassNotFoundException e){
            System.err.println(e.getMessage());
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:/home/rio/NetBeansProjects/pti.sqlite");
            Statement statement = connection.createStatement();
            String update = "error";
            update = "DELETE FROM devices WHERE user = \""+ user +"\"" + " and name = \"" + name + "\"";
            System.out.println(update);
            statement.executeUpdate(update);
            connection.close();
            if (update.equals("error")) return 0;
            else return 1;
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if (connection != null)
                    connection.close();
            }
            catch (SQLException e) {
                //Error en tancar la connexio
                System.err.println(e.getMessage());
            }
        }
        return result;
    }
}
