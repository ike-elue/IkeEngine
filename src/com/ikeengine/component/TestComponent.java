package com.ikeengine.component;

import com.ikeengine.debug.Message;
import com.ikeengine.debug.MessageActivator;
import com.ikeengine.input.Key;
import com.ikeengine.input.KeyboardHandler;

/**
 *
 * @author Jonathan Elue
 */
public class TestComponent extends Component{

    public static final int ACTION = 0;
    
    public TestComponent(int id, MessageActivator activator) {
        super(id, activator, "Test");
    }

    @Override
    public Message update(double delta, int methodNum, Object associatedData) {
        switch(methodNum) {
            case ACTION:
                return action((KeyboardHandler) associatedData);
            default:
                return null;
        }
    }
    
    public Message action(KeyboardHandler k) {
        if(k.isKeyPressed(Key.GLFW_SPACE)) {
            setMessage("Message Received", null);
            System.out.println("BOOM");
            return getMessage();
        }
        return null;
    }
    
}
