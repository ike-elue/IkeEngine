package com.ikeengine.input;

import com.ikeengine.debug.Message;
import com.ikeengine.debug.MessageBus;
import org.joml.Vector2d;
import org.lwjgl.glfw.GLFWScrollCallback;

/**
 *
 * @author Jonathan Elue
 */
public class ScrollWheelHandler extends GLFWScrollCallback{

    private final Vector2d scroll = new Vector2d();
    private final Message info = new Message(-1, "ScrollWheel Handler");
    
    @Override
    public void invoke(long window, double xOffset, double yOffset) {
        scroll.set(xOffset, yOffset);
    }
    
    public void sendScroll(MessageBus bus) {
        bus.addMessage(info.setMessage("SCROLL_INPUT", scroll));
    }
}
