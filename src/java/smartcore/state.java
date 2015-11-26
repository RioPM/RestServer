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
import javax.ejb.Stateless;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 *
 * @author rio
 */

@Stateless
@Path("/state")
public class state {
    
    @GET
    public String getstate(@QueryParam("username") String username, @QueryParam("id") int id) {
        return Integer.toString($getstate(username,id));
    }
    
    int $getstate(String username, int id) {
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
            ResultSet rs = statement.executeQuery("select count (*) as total from users where id = \""+ id +"\"");
            if("1".equals(rs.getString("total"))) {      //Si existeix el device a la base de dades
                rs = statement.executeQuery("select state as state from devices where id = \""+ id +"\" and  user = \""+ username +"\"");
                int state = rs.getInt("state");
                connection.close();
                return state;
            }
            else {
                connection.close();
                return 0;
            }
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
        return -1;
    }
    
    @POST
    public String setstate(@FormParam("user") String user, @FormParam("id") int id) {
        return Integer.toString($setstate(user,id));
    }
    
    int $setstate(String user, int id) {
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
            ResultSet rs = statement.executeQuery("select state as state from devices where id = \""+ id +"\"");
            int state = rs.getInt("state");
            String update = "error";
            int new_state = 0;
            if (state == 0) new_state = 1;
            update = "UPDATE devices SET state = " + new_state + " WHERE id = " + id + ";";
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
