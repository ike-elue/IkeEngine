package com.ikeengine.component;

import com.ikeengine.debug.Message;
import com.ikeengine.debug.MessageActivator;
import com.ikeengine.util.Collider;
import com.ikeengine.util.Transform;

/**
 *
 * @author Jonathan Elue
 */
public class CollisionComponet extends Component {

    public static final int COPY = 0, SEND = 1, COLLIDED = 2;

    private final Collider c;

    public CollisionComponet(int id, MessageActivator activator) {
        super(id, activator, "collision");
        c = new Collider(name, new Transform());
    }

    @Override
    public Message update(double delta, int methodNum, Object associatedData) {
        switch (methodNum) {
            case COPY:
                c.transform.copy((Transform) associatedData);
                return null;
            case SEND:
                return new Message(setMessage("collider", c));
            case COLLIDED:
                return new Message(setMessage(id + "", (Collider) associatedData));
            default:
                return null;
        }
    }

}
