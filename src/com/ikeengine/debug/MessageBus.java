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
    
    public void addMessage(Message message) {
        messages.add(message);
    }
    
    public void addMessages(List<Message> messages) {
        this.messages.addAll(messages);
    }
    
    public void print(String fpsString) {
        if(console != null)
            console.update(this, fpsString);
    }
    
    public void clean() {
        int pointer = 0;
        while(pointer < messages.size()) {
            if(messages.get(pointer) == null)
                messages.remove(pointer);
            else
                pointer++;
        }   
    }
    
    public void clear() {
        messages.clear();
    }
    
    public List<Message> getMessages() {
        return messages;
    }
    
    public double getDelta() {
        return delta;
    }
    
    public boolean isDebug() {
        return debug;
    }
    
    public void setDelta(double delta) {
        this.delta = delta;
    }
    
    public void dispose() {
        if(console != null) 
            console.dispose();
    }
}
