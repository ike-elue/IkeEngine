package com.ikeengine.input;

import com.ikeengine.debug.Message;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWCursorPosCallback;

/**
 *
 * @author Jonathan Elue
 */

public class CursorPosHandler extends GLFWCursorPosCallback {

	private double mouseX, mouseY;
        private final Message coords = new Message(-1, "Cursor Handler");
        
        
	@Override
	public void invoke(long window, double xpos, double ypos) {
		mouseX = xpos;
		mouseY = ypos;
	}

	public double getXPos() {
		return mouseX;
	}

	public double getYPos() {
		return mouseY;
	}
        
        public Message getCoords() {
            coords.setMessage("CURSOR_INPUT", new Vector2f((float)mouseX, (float)mouseY));
            return coords;
        }
}
