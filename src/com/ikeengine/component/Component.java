package com.ikeengine.component;

import com.ikeengine.debug.Message;
import com.ikeengine.debug.MessageActivator;
import com.ikeengine.debug.MessageBus;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author Jonathan Elue
 */
public abstract class Component implements Callable<Message[]>{
    
    public final int id;
    private final List<Message> messages;
    private final MessageActivator activator;
    private final String name;
    private final Message message;
    private String[] methodMessages;
    private MessageBus bus;
    
    public Component(int id, MessageActivator activator, String name) {
        this.id = id;
        messages = new ArrayList<>();
        this.activator = activator;
        this.name = name;
        message = new Message(id, name);
        bus = null;
    }
    
    public abstract Message update(double delta, int methodNum, Object associatedData);

    @Override
    public Message[] call() throws Exception {
        messages.clear();
        methodMessages = activator.matchMessages(bus);
        if(methodMessages.length == 0)
            return null;
        for(String m : methodMessages)
            for(int num : activator.getMethodNums(m))
                add(update(bus.getDelta(), num, activator.getData(m, bus)));
        if(messages.isEmpty())
            return null;
        return (Message[]) messages.toArray(new Message[messages.size()]);
    }
    
    private void add(Message m) {
        if(m == null)
            return;
        messages.add(m);
    }
    
    public String getName() {
        return name;
    }
    
    public Message getMessage() {
        return message;
    }
    
    public void setMessageBus(MessageBus bus) {
        this.bus = bus;
    } 
    
    public void setMessage(String message, Object data) {
        this.message.setMessage(message, data);
    }
}
