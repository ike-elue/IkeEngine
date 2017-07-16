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
    public final String name;
    
    private final List<Message> messages;
    private final MessageActivator activator;
    private final Message internalMessage;
    private MessageBus bus;
    
    public Component(int id, MessageActivator activator, String name) {
        this.id = id;
        messages = new ArrayList<>();
        this.activator = activator;
        this.name = name + "_component_" + id;
        internalMessage = new Message(id, name);
        bus = null;
    }
    
    public abstract Message update(double delta, int methodNum, Object associatedData);

    @Override
    public Message[] call() throws Exception {
        messages.clear();
        for(int i = 0; i < activator.length; i++) {
            if(bus == null)
                return null;
            if(activator.messageTypes[i] == null)
                for(int methodNum : activator.methodNums[i])
                    add(update(bus.getDelta(), methodNum, null));
            else {
                for(Message m : bus.getMessages()) {
                    if(activator.comparableData[i] == null){
                        if(activator.messageTypes[i].equalsIgnoreCase(m.getMessage()))
                            for(int methodNum : activator.methodNums[i])
                                add(update(bus.getDelta(), methodNum, m.getData()));
                    }
                    else if(activator.comparableData[i].equals(m.getData()))
                        for(int methodNum : activator.methodNums[i])
                            add(update(bus.getDelta(), methodNum, m.getData()));
                }    
            }
        }
        if(messages.isEmpty())
            return null;
        return (Message[]) messages.toArray(new Message[messages.size()]);
    }
    
    private void add(Message m) {
        if(m == null)
            return;
        messages.add(m);
    }
    
    public Message getMessage() {
        return internalMessage;
    }
    
    public void setMessageBus(MessageBus bus) {
        this.bus = bus;
    } 
    
    public Message setMessage(String message, Object data) {
        return internalMessage.setMessage(message, data);
    }
}
