package com.ikeengine.component;

import com.ikeengine.debug.Message;
import com.ikeengine.debug.MessageActivator;
import com.ikeengine.util.Transform;
import org.joml.Vector2f;

/**
 *
 * @author Jonathan Elue
 */
public class CameraComponent extends Component{

    public static final int MOVE = 0, ROTATE = 1, SCALE = 2, SEND = 3; 
    
    private final Transform transform;
    
    public CameraComponent(int id, MessageActivator activator, String name) {
        super(id, activator, name);
        transform = new Transform();
    }

    @Override
    public Message update(double delta, int methodNum, Object associatedData) {
        switch(methodNum) {
            case MOVE:
                translate((Vector2f) associatedData);
                return null;
            case ROTATE: 
                rotate((Float) associatedData);
                return null;
            case SCALE: 
                scale((Vector2f) associatedData);
                return null;
            case SEND:
                return new Message(setMessage("camera_coords", transform));
            default: 
                return null;
        } 
    }
    
    /**
     * Translates the transform by the vector
     * @param v 
     */
    public void translate(Vector2f v) {
        transform.translate(v);
    }
    
    /**
     * Rotates the transform by the given amount
     * @param s
     */
    public void rotate(float s) {
        transform.rotate(s);
    }
    
    /**
     * Scales the transform by the vector
     * @param v 
     */
    public void scale(Vector2f v) {
        transform.scale(v);
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
