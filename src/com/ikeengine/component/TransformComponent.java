package com.ikeengine.component;

import com.ikeengine.debug.Message;
import com.ikeengine.debug.MessageActivator;
import com.ikeengine.util.Transform;

/**
 *
 * @author Jonathan Elue
 */
public class TransformComponent extends Component{

    public static final int SEND = 0, COPY = 1;
    private Transform transform;
    
    public TransformComponent(int id, MessageActivator activator) {
        super(id, activator, "transform");
    }

    @Override
    public Message update(double delta, int methodNum, Object associatedData) {
        switch(methodNum) {
            case SEND: 
                return send();
            case COPY:
                copy((Transform) associatedData);
                return null;
            default:
                return null;
        }
    }
    
    public Message send() {
        return new Message(setMessage(name + "_transformation", transform));
    }

    public void copy(Transform transform) {
        this.transform.copy(transform);
    }
    
}
