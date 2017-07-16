package com.ikeengine.component;

import com.ikeengine.debug.Message;
import com.ikeengine.debug.MessageActivator;

/**
 *
 * @author Jonathan Elue
 */
public class CollisionComponet extends Component{

    public CollisionComponet(int id, MessageActivator activator) {
        super(id, activator, "collision");
    }

    @Override
    public Message update(double delta, int methodNum, Object associatedData) {
        return null;
    }

}
