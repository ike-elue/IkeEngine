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
   
    /**
     * Adds component to list
     * @param c 
     */
    public void addComponent(Component c) {
        components.add(c);
    }
    
    /**
     * Removes component of specific id
     * @param id 
     */
    public void removeComponent(int id) {
        components.stream().filter((c) -> (c.id == id)).forEach((c) -> {
            components.remove(c);
        });
    }
    
    /**
     * Returns component list type
     * @return 
     */
    public String getType() {
        return type;
    }
    
    /**
     * Returns components in list
     * @return 
     */
    public List<Component> getComponents() {
        return components;
    }
}
