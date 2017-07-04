package com.ikeengine.util;

import com.ikeengine.component.Component;
import com.ikeengine.debug.MessageActivator;

/**
 *
 * @author Jonathan Elue
 */
public class Extracter {
    private String name;
    private String[] components;
    private MessageActivator activator;
    private ComponentUtil cu;
    
    public Component getComponent(int id, String nameOfComponent) {
        return cu.getComponent(id, activator, nameOfComponent);
    }
    
    public String[] getComponents() {
        return components;
    }
    
    public String getName() {
        return name;
    }
    
    public MessageActivator getMessageActivator() {
        return activator;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setComponents(String[] components) {
        this.components = components;
    }
    
    public void setMessageActivator(MessageActivator activator) {
        this.activator = activator;
    }
    
    public void setComponentUtil(ComponentUtil cu) {
        this.cu = cu;
    }
}
