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

    /**
     * Returns integer value that represents mouse button
     *
     * @return
     */
    public Integer value() {
        return buttonCode;
    }

    /**
     * Determines states based on mouse button being released
     *
     * @param isReleased
     */
    public void determineValues(boolean isReleased) {
        pressed = !isReleased && !down;
        released = isReleased && down;
        down = !isReleased;
    }

    /**
     * Determines if mouse button should be posted to message bus based on its
     * states
     *
     * @return
     */
    public boolean canBeSent() {
        return down || pressed || released;
    }

    /**
     * Updates mouse button
     */
    public void update() {
        pressed = false;
        released = false;
    }
}
