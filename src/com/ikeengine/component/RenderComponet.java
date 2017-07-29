package com.ikeengine.component;

import com.ikeengine.debug.Message;
import com.ikeengine.debug.MessageActivator;

/**
 *
 * @author Jonathan Elue
 */
public class RenderComponet extends Component {

    public RenderComponet(int id, MessageActivator activator) {
        super(id, activator, "render");
    }

    @Override
    public Message update(double delta, int methodNum, Object associatedData) {
        return null;
    }

    @Override
    public int[] getConstantMethods() {
        return null;
    }
    
    @Override
    public int[] getInitSceneMethods() {
        return null;
    }
    
}
