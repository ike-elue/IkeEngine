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

    private int buttonCode;

    private Mouse(int buttonCode) {
        this.buttonCode = buttonCode;
    }

    public int getButtonCode() {
        return buttonCode;
    }

    public void setButtonCode(int buttonCode) {
        this.buttonCode = buttonCode;
    }
}
