package com.ikeengine.input;

import com.ikeengine.debug.Message;
import com.ikeengine.debug.MessageBus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

/**
 *
 * @author Jonathan Elue
 */
public class MouseButtonHandler extends GLFWMouseButtonCallback {

    private final List<Integer> messages = new ArrayList<>();
    private final HashMap<Integer, Mouse> buttons = new HashMap<>();
    
    public MouseButtonHandler() {
        super();
        for(Mouse mouse : Mouse.values())
            buttons.put(mouse.value(), mouse);
    }
    
    @Override
    public void invoke(long window, int button, int action, int mods) {
        if(!buttons.containsKey(button))
            return;
        buttons.get(button).determineValues(action == GLFW_RELEASE);
        if(buttons.get(button).canBeSent() && !messages.contains(button))
            messages.add(button);
        else if(!buttons.get(button).canBeSent() && messages.contains(button))
            messages.remove((Integer)button);
    }
    
    /**
     * Sends any necessary mouse button info to message bus
     * @param bus 
     */
    public void getMessages(MessageBus bus) {
        messages.stream().forEach((i) -> {
            bus.addMessage(new Message(-1, "MouseButton Handler").setMessage("MOUSE_INPUT", buttons.get(i)));
        });
    }
    
    /**
     * Updates/Removes mouse buttons in list based on their variables
     */
    public void update() {
        int i = 0;
        while(i < messages.size()) {
            buttons.get(messages.get(i)).update();
            if(!buttons.get(messages.get(i)).down)
                messages.remove(messages.get(i));
            else
                i++;
        }
    }
}
