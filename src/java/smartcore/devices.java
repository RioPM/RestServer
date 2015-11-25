/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartcore;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
        if (device.size() == 0) {
            Device d1 = new Device(1, "LlumPassadis", 1);
            Device d2 = new Device(2, "LlumMenjador", 1);
            device.add(0, d1);
            device.add(1, d2);
        }
        return $getdevices();
    }
    
    String $getdevices() {
        String result = null;
        /*for (int i = 0; i < device.length; i++) {
            if (i == 0) result = Arrays.toString(device);
            else result += Arrays.toString(device);
        }*/
        /*if (device.length > 0) result = Arrays.toString(device);*/
        for (Device d: device) {
            if (device.indexOf(d) == 0 && d != null) result = d.toString() + "\n";
            else result += (d.toString() + "\n");
        }
        System.out.println(result);
        return result;
    }
    
    @POST
    public String adddevice(@FormParam("name") String name, @FormParam("type") int type) {
        return Integer.toString($adddevice(name,type));
    }
    
    int $adddevice(String name, int type) {
        int result = -1;
        /*device = new Device[] {
            new Device(1, "LlumPassadis", 1),
            new Device(2, "LlumMenjador", 1),
            new Device(3, name, type)
        };
        if (device.length > 2) result = 1;*/
        int id = device.size();
        if (name != null) device.add(new Device(id+1, name + (id+1), type));
        if (id < device.size()) result = 1;
        return result;
    }
    
}
