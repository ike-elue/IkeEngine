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

    private final List<Collider> objects = new ArrayList<>();

    public void update(MessageBus bus) {
        bus.getMessages().stream().filter((m) -> (m.getMessage().equalsIgnoreCase("collider"))).forEach((m) -> {
            addCollider((Collider) m.getData());
        });
        
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                Collider c1 = objects.get(i);
                Collider c2 = objects.get(j);
                if (c1.transform.translation.x < c2.transform.translation.x + c2.transform.scale.x
                        && c1.transform.translation.x + c1.transform.scale.x > c2.transform.translation.x
                        && c1.transform.translation.y < c2.transform.translation.y + c2.transform.scale.y
                        && c1.transform.scale.y + c1.transform.translation.y > c2.transform.translation.y) {

                    bus.addMessage(new Message(-1, "CollisionWorld").setMessage(c2.name, c1));
                    bus.addMessage(new Message(-1, "CollisionWorld").setMessage(c1.name, c2));
                }
            }
        }
        objects.clear();
    }

    public void addCollider(Collider c) {
        objects.add(c);
    }
}
