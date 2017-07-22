package com.ikeengine.input;

import com.ikeengine.debug.Message;
import com.ikeengine.debug.MessageBus;
import org.joml.Vector2d;
import org.lwjgl.glfw.GLFWCursorPosCallback;

/**
 *
 * @author Jonathan Elue
 */
public class CursorPosHandler extends GLFWCursorPosCallback {

    private final Vector2d mouse = new Vector2d();
    private final Message coords = new Message(-1, "Cursor Handler");

    @Override
    public void invoke(long window, double xpos, double ypos) {
        mouse.set(xpos, ypos);
    }

    public void sendCoords(MessageBus bus) {
        bus.addMessage(coords.setMessage("CURSOR_INPUT", mouse));
    }
}
