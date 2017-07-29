package com.ikeengine.physics;

import com.ikeengine.debug.Message;
import com.ikeengine.debug.MessageBus;
import com.ikeengine.util.Collider;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonathan Elue
 */
public class CollisionWorld {

    private final List<Collider> objects;
    
    public CollisionWorld() {
        objects = new ArrayList<>();
    }
    
    /**
     * Updates all colliders found in message bus and sends messages to those
     * who have collided
     *
     * @param bus
     */
    public void update(MessageBus bus) {
        
        bus.getMessages().stream().forEach((m) -> {
            if(m.getMessage().equalsIgnoreCase("collider") && !objects.contains((Collider)m.getData()))
                addCollider((Collider) m.getData());
            if(m.getMessage().equalsIgnoreCase("init_scene"))
                objects.clear();
        });

        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                Collider c1 = objects.get(i);
                Collider c2 = objects.get(j);
                if (c1.transform.getTranslation().x < c2.transform.getTranslation().x + c2.transform.getScale().x
                        && c1.transform.getTranslation().x + c1.transform.getScale().x > c2.transform.getTranslation().x
                        && c1.transform.getTranslation().y < c2.transform.getTranslation().y + c2.transform.getScale().y
                        && c1.transform.getScale().y + c1.transform.getTranslation().y > c2.transform.getTranslation().y) {

                    bus.addMessage(new Message(-1, "CollisionWorld").setMessage(c2.name, c1));
                    bus.addMessage(new Message(-1, "CollisionWorld").setMessage(c1.name, c2));
                }
            }
        }
    }

    /**
     * Adds Collider to world
     * @param c 
     */
    private void addCollider(Collider c) {
        objects.add(c);
    }
}
