package com.ikeengine.component;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonathan Elue
 */
public class Components {
    private final String type;
    private final List<Component> components;
    
    public Components(String type) {
        this.type = type;
        components = new ArrayList<>();
    } 
   
    public void addComponent(Component c) {
        components.add(c);
    }
    
    public void removeComponent(int id) {
        components.stream().filter((c) -> (c.id == id)).forEach((c) -> {
            components.remove(c);
        });
    }
    
    public String getType() {
        return type;
    }
    
    public List<Component> getComponents() {
        return components;
    }
}
