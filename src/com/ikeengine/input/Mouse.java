package com.ikeengine.input;

import static org.lwjgl.glfw.GLFW.*;

/**
 *
 * @author Jonathan Elue
 */
public enum Mouse {
    GLFW_RBUTTON(GLFW_MOUSE_BUTTON_RIGHT),
    GLFW_LBUTTON(GLFW_MOUSE_BUTTON_LEFT),
    GLFW_MBUTTON(GLFW_MOUSE_BUTTON_MIDDLE);

    private final Integer buttonCode;
    public boolean down, pressed, released;

    private Mouse(int buttonCode) {
        this.buttonCode = buttonCode;
        down = false;
        pressed = false;
        released = false;
    }

    public Integer value() {
        return buttonCode;
    }

    public void determineValues(boolean isReleased) {
        pressed = !isReleased && !down;
        released = isReleased && down;
        down = !isReleased;
    }

    public boolean canBeSent() {
        return down || pressed || released;
    }

    public void update() {
        pressed = false;
        released = false;
    }
}
