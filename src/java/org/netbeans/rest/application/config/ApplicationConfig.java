/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.rest.application.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Rio
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(smartcore.adddevices.class);
        resources.add(smartcore.delete.class);
        resources.add(smartcore.devices.class);
        resources.add(smartcore.devicestate.class);
        resources.add(smartcore.getdevices.class);
        resources.add(smartcore.login.class);
        resources.add(smartcore.register.class);
        resources.add(smartcore.state.class);
    }
    
}
