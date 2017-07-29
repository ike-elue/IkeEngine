package com.ikeengine.scene;

import com.ikeengine.component.Component;
import com.ikeengine.component.Components;
import com.ikeengine.debug.MessageBus;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonathan Elue
 */
public class Scene {
    private int pointer;
    private final List<Components> components;
    private final MessageBus bus;
    
    public Scene(MessageBus bus) {
        pointer = 0;
        components = new ArrayList<>();
        this.bus = bus;
    }
    
    /**
     * Creates id for game object
     * @return 
     */
    public int generateGameObject() {
        return pointer++;
    }
    
    /**
     * Adds Component of a specific game object to Scene 
     * @param c 
     */
    public void addComponent(Component c) {
        boolean found = false;
        for(Components comps : components) {
            if(comps.getType().equalsIgnoreCase(c.name)) {
                found = true;
                c.setMessageBus(bus);
                comps.addComponent(c);
                break;
            }
        }
        if(found)
            return;
        components.add(new Components(c.name));
        addComponent(c);
    }
    
    /**
     * Adds multiple Components of a specific game object to Scene
     * @param cs 
     */
    public void addComponents(Component[] cs) {
        for(Component c : cs)
            addComponent(c);
    }
  
    /**
     * Returns categories of Components that the Scene contains
     * @return 
     */
    public List<Components> getComponents() {
        return components;
    }
    
}