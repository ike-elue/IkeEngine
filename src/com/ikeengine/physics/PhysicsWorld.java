package com.ikeengine.physics;

import com.ikeengine.debug.Message;
import com.ikeengine.debug.MessageBus;
import java.util.ArrayList;
import java.util.List;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Vector2;

/**
 *
 * @author Jonathan Elue
 */
public class PhysicsWorld {

    private final World world;
    private final List<Message> messagesOfBodies;
    
    public PhysicsWorld() {
        world = new World();
        messagesOfBodies = new ArrayList<>();
    }
    
    public void update(double delta, MessageBus bus) {
        bus.getMessages().stream().forEach((m) -> {
            if(m.getMessage().equalsIgnoreCase("physics_body")) {
                if(!world.containsBody((Body) m.getData())) {
                    world.addBody((Body) m.getData());
                    messagesOfBodies.add(new Message(-1, "Physics_World"));
                }
            }
            if(m.getMessage().equalsIgnoreCase("physics_world_gravity"))
                world.setGravity((Vector2) m.getData());
            if(m.getMessage().equalsIgnoreCase("init_scene")) {
                world.removeAllBodies();
                messagesOfBodies.clear();
            }
            
        });
        
        if(world.isEmpty())
            return;
        
        world.update(delta);
        
        if(messagesOfBodies.size() != world.getBodyCount())
            return;
        for(int i = 0; i < world.getBodyCount(); i++)
            if(world.getBody(i).getUserData() instanceof String)
                bus.addMessage(messagesOfBodies.get(i).setMessage((String) world.getBody(i).getUserData(), world.getBody(i)));
    }
}
