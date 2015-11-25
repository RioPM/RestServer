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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 *
 * @author Rio
 */
@Stateless
@Path("/login")
public class login {
    
    @GET
    public String login(@QueryParam("username") String username, @QueryParam("password") String password) {
        return Integer.toString($login(username, password));
    }
    
    int $login (String username, String password) {
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
            ResultSet rs = statement.executeQuery("select count (*) as total from users where username = \""+ username +"\" and  password = \""+ password +"\"");
            
            if("1".equals(rs.getString("total"))) {      //Si les credencials introduides coincideixen
                connection.close();
                return 1;
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
    
}
