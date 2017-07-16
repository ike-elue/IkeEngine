package com.ikeengine.component;

import com.ikeengine.debug.Message;
import com.ikeengine.debug.MessageActivator;
import com.ikeengine.input.Key;

/**
 *
 * @author Jonathan Elue
 */
public class TestComponent extends Component{

    public static final int ACTION = 0;
    public static final int REPEAT = 1;
    
    public TestComponent(int id, MessageActivator activator) {
        super(id, activator, "Test");
    }

    @Override
    public Message update(double delta, int methodNum, Object associatedData) {
        switch(methodNum) {
            case ACTION:
                return action((Key) associatedData);
            case REPEAT:
                return repeat(associatedData);
            default:
                return null;
        }
    }
    
    public Message action(Key key) {
        if(key.down)
            return new Message(setMessage("Message Received", null));
        return null;
    }
    
    public Message repeat(Object o) {
        return new Message(setMessage("go", o));
    }
    
}
