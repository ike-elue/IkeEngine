package com.ikeengine.scene;

import com.ikeengine.component.Component;
import com.ikeengine.component.Components;
import com.ikeengine.component.TestComponent;
import com.ikeengine.debug.MessageActivator;
import com.ikeengine.debug.MessageBus;
import com.ikeengine.util.Extracter;
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
    
    public int generateGameObject() {
        return pointer++;
    }
    
    public void generateGameObject(Extracter e) {
        if(e == null)
            return;
        int id = generateGameObject();
        for(String component : e.getComponents())
            addComponent(e.getComponent(id, component));
    }
    
    public void addComponent(Component c) {
        boolean found = false;
        for(Components comps : components) {
            if(comps.getType().equalsIgnoreCase(c.getName())) {
                found = true;
                c.setMessageBus(bus);
                comps.addComponent(c);
                break;
            }
        }
        if(found)
            return;
        components.add(new Components(c.getName()));
        addComponent(c);
    }
    
    public void addComponents(Component[] cs) {
        for(Component c : cs)
            addComponent(c);
    }
  
    public List<Components> getComponents() {
        return components;
    }
    
}
