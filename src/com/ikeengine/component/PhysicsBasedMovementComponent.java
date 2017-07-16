package com.ikeengine.component;

import com.ikeengine.debug.Message;
import com.ikeengine.debug.MessageActivator;

/**
 *
 * @author Jonathan Elue
 */
public class PhysicsBasedMovementComponent extends Component{

    public PhysicsBasedMovementComponent(int id, MessageActivator activator) {
        super(id, activator, "physics_movement");
    }

    @Override
    public Message update(double delta, int methodNum, Object associatedData) {
        return null;
    }

}
