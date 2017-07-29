package com.ikeengine.input;

import com.ikeengine.debug.Message;
import com.ikeengine.debug.MessageBus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import org.lwjgl.glfw.GLFWKeyCallback;

/**
 *
 * @author Jonathan Elue
 */
public class KeyboardHandler extends GLFWKeyCallback {

    private final List<Integer> messages = new ArrayList<>();
    private final HashMap<Integer, Key> keys = new HashMap<>();
    
    public KeyboardHandler() {
        super();
        for(Key key : Key.values())
            keys.put(key.value(), key);
    }
    
    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if(!keys.containsKey(key))
            return;
        try {
            keys.get(key).determineValues(action == GLFW_RELEASE);
            if(keys.get(key).canBeSent() && !messages.contains(key))
                messages.add(key);
            else if(!keys.get(key).canBeSent() && messages.contains(key))
                messages.remove((Integer)key);
        } catch (ArrayIndexOutOfBoundsException exc) {
            exc.printStackTrace();
            System.out.println("Invalid Key");
        }
    }

    /**
     * Sends any necessary key info to message bus
     * @param bus 
     */
    public void getMessages(MessageBus bus) {
        messages.stream().forEach((i) -> {
            bus.addMessage(new Message(-1, "Keyboard Handler").setMessage("KEY_INPUT", keys.get(i)));
        });
    }
    
    /**
     * Updates/Removes mouse buttons in list based on their variables
     */
    public void update() {
        int i = 0;
        while(i < messages.size()) {
            keys.get(messages.get(i)).update();
            if(!keys.get(messages.get(i)).down)
                messages.remove(messages.get(i));
            else
                i++;
        }
    }
}