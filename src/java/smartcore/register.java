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
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 * @author rio
 */

@Stateless
@Path("/register")
public class register {
    @POST
    public String adduser(@FormParam("user") String user, @FormParam("password") String password) {
        return Integer.toString($adduser(user,password));
    }
    
    int $adduser(String user, String password) {
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
            ResultSet rs = statement.executeQuery("select count (*) as total from users where username = \""+ user +"\" and  password = \""+ password +"\"");
            
            if("1".equals(rs.getString("total"))) {      //Si user ja existeix a la BD
                connection.close();
                return 0;
            }
            
            rs = statement.executeQuery("select count (*) as size from users");
            int size = rs.getInt("size");
            
            String update = "error";
            update = "INSERT INTO users VALUES ('" +
                                    (size+1) + "','" +
                                    user + "','" +
                                    password + "');";
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
