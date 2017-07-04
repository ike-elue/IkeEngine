package com.ikeengine.input;

import com.ikeengine.debug.Message;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

/**
 *
 * @author Jonathan Elue
 */
public class MouseButtonHandler extends GLFWMouseButtonCallback {

    private final boolean[] buttons = new boolean[16];
    private boolean[] buttonsLast = new boolean[16];

    private final Message inputs = new Message(-1, "Mouse Handler");
    
    @Override
    public void invoke(long window, int button, int action, int mods) {
        buttonsLast = buttons.clone();
        try {
            buttons[button] = (action != GLFW_RELEASE);
        } catch (ArrayIndexOutOfBoundsException exc) {
            exc.printStackTrace();
            System.out.println("Invalid Button");
        }
    }

    public void update() {
        buttonsLast = buttons.clone();
    }

    public boolean isMouseButtonDown(Mouse button) {
        return buttons[button.getButtonCode()];
    }

    public boolean isMouseButtonPressed(Mouse button) {
        return buttons[button.getButtonCode()]
                && !buttonsLast[button.getButtonCode()];
    }

    public boolean isMouseButtonReleased(Mouse button) {
        return !buttons[button.getButtonCode()]
                && buttonsLast[button.getButtonCode()];
    }
    
    public Message getHandler() {
        inputs.setMessage("MOUSE_INPUT", this);
        return inputs;
    }
}
