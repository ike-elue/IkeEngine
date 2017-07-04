package com.ikeengine.input;

import com.ikeengine.debug.Message;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import org.lwjgl.glfw.GLFWKeyCallback;

/**
 *
 * @author Jonathan Elue
 */
public class KeyboardHandler extends GLFWKeyCallback {

    private final boolean[] keys = new boolean[15000];
    private boolean[] keysLast = new boolean[15000];

    private final Message inputs = new Message(-1, "Key Handler");

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        keysLast = keys.clone();
        try {
            keys[key] = (action != GLFW_RELEASE);
        } catch (ArrayIndexOutOfBoundsException exc) {
            exc.printStackTrace();
            System.out.println("Invalid Key");
        }
    }

    public void update() {
        keysLast = keys.clone();
    }

    public boolean isKeyDown(Key key) {
        return keys[key.getKeycode()];
    }

    public boolean isKeyPressed(Key key) {
        return keys[key.getKeycode()] && !keysLast[key.getKeycode()];
    }

    public boolean isKeyReleased(Key key) {
        return !keys[key.getKeycode()] && keysLast[key.getKeycode()];
    }

    public Message getHandler() {
        inputs.setMessage("KEY_INPUT", this);
        return inputs;
    }
}
