package com.ikeengine.component;

import com.ikeengine.debug.Message;
import com.ikeengine.debug.MessageActivator;
import com.ikeengine.util.Transform;

/**
 *
 * @author Jonathan Elue
 */
public class CameraFollowComponent extends Component {

    public static final int COPY = 0, SEND = 1;

    private final Transform transform;

    public CameraFollowComponent(int id, MessageActivator activator, String name) {
        super(id, activator, name);
        transform = new Transform();
    }

    @Override
    public Message update(double delta, int methodNum, Object associatedData) {
        switch (methodNum) {
            case COPY:
                transform.copy((Transform) associatedData);
                return null;
            case SEND:
                return new Message(setMessage("camera_coords", transform));
            default:
                return null;
        }
    }
    
    @Override
    public int[] getConstantMethods() {
        return new int[] {SEND};
    }
    
    @Override
    public int[] getInitSceneMethods() {
        return null;
    }

}
