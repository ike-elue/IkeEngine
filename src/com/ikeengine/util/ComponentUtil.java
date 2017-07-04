package com.ikeengine.util;

import com.ikeengine.component.Component;
import com.ikeengine.component.TestComponent;
import com.ikeengine.debug.MessageActivator;

/**
 *
 * @author Jonathan Elue
 */
public class ComponentUtil {
    public Component getComponent(int id, MessageActivator activator, String name) {
        switch(name.toLowerCase()) {
            case "test":
                return new TestComponent(id, activator);
            default:
                System.out.println("Invalid Component");
                return null;
        } 
    }
}
