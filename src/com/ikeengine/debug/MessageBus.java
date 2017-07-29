package com.ikeengine.debug;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonathan Elue
 */
public class MessageBus {
    private final List<Message> messages;
    private double delta;
    private final boolean debug;
    private final Console console;
    
    public MessageBus(String title, boolean debug) {
        messages = new ArrayList<>();
        this.debug = debug;
        if(debug)
            console = new Console(title);
        else
            console = null;
    }
    
    /**
     * Adds message to message bus
     * @param message 
     */
    public void addMessage(Message message) {
        messages.add(message);
    }
    
    /**
     * Adds all messages in the list to the message bus
     * @param messages 
     */
    public void addMessages(List<Message> messages) {
        this.messages.addAll(messages);
    }
    
    /**
     * Prints to console if console is active
     * @param fpsString 
     */
    public void print(String fpsString) {
        if(console != null)
            console.update(this, fpsString);
    }
    
    /**
     * Removes null messages
     */
    public void clean() {
        int pointer = 0;
        while(pointer < messages.size()) {
            if(messages.get(pointer) == null)
                messages.remove(pointer);
            else
                pointer++;
        }   
    }
    
    /**
     * Clears the messages in message bus
     */
    public void clear() {
        messages.clear();
    }
    
    /**
     * Returns current messages in the message bus
     * @return 
     */
    public List<Message> getMessages() {
        return messages;
    }
    
    /**
     * Returns most currently set delta
     * @return 
     */
    public double getDelta() {
        return delta;
    }
    
    /**
     * Returns true if debug mode is active
     * @return 
     */
    public boolean isDebug() {
        return debug;
    }
    
    /**
     * Sets Delta
     * @param delta 
     */
    public void setDelta(double delta) {
        this.delta = delta;
    }
    
    /**
     * Closes Console
     */
    public void dispose() {
        if(console != null) 
            console.dispose();
    }
}
