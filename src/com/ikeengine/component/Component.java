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
public abstract class Component implements Callable<Message[]> {

    public static final int PAUSED = -1, UNPAUSED = -2;
    private final Message referenceMessage;
    
    public final int id;
    public final String name;

    private final List<Message> messages;
    private final MessageActivator activator;
    private final int[] internalActivator, internalInitActivator;
    private final Message internalMessage;
    private MessageBus bus;

    private boolean paused;
    
    public Component(int id, MessageActivator activator, String name) {
        this.id = id;
        messages = new ArrayList<>();
        referenceMessage = new Message(id, name).setMessage("init_scene", null);
        this.activator = activator;
        internalActivator = getConstantMethods();
        internalInitActivator = getInitSceneMethods();
        this.name = name + "_component_" + id;
        activator.setName(this.name);
        internalMessage = new Message(id, name);
        bus = null;
        paused = false;
    }

    /**
     * Updates the component based on the current method number 
     * @param delta
     * @param methodNum
     * @param associatedData
     * @return 
     */
    public abstract Message update(double delta, int methodNum, Object associatedData);

    /**
     * Returns the method numbers that the component wants to always be activated 
     * @return 
     */
    protected abstract int[] getConstantMethods();

    /**
     * Returns the method numbers that the component wants to activate when a new scene is entered
     * @return 
     */
    protected abstract int[] getInitSceneMethods();
    
    @Override
    public Message[] call() throws Exception {
        messages.clear();
        for (int i = 0; i < activator.length; i++) {
            if (bus == null)
                return null;
            if (internalActivator != null)
                for (int methodNum : internalActivator)
                    add(update(bus.getDelta(), methodNum, null));

            if(internalInitActivator != null)
                if(bus.getMessages().contains(referenceMessage))
                    for (int methodNum : internalInitActivator)
                        add(update(bus.getDelta(), methodNum, null));
            
            // May add back null factor for infinate messages
            if (activator.messageTypes[i] != null) {
                for (Message m : bus.getMessages()) {
                    if(m == null)
                        continue;
                    if (activator.comparableData[i] == null) {
                        if (activator.messageTypes[i].equalsIgnoreCase(m.getMessage())) {
                            for (int methodNum : activator.methodNums[i]) {
                                if(methodNum == UNPAUSED)
                                    paused = false;
                                if (methodNum == PAUSED) {
                                    paused = true;
                                    return returnMessages();
                                }
                                if (!paused)
                                    add(update(bus.getDelta(), methodNum, m.getData()));
                            }
                        }
                    } else if (activator.comparableData[i].equals(m.getData())) {
                        for (int methodNum : activator.methodNums[i]) {
                            if(methodNum == UNPAUSED)
                                paused = false;
                            if (methodNum == PAUSED) {
                                paused = true;
                                return returnMessages();
                            }
                            if (!paused)
                                add(update(bus.getDelta(), methodNum, m.getData()));
                        }
                    }
                }
            }
        }
        return returnMessages();
    }

    private void add(Message m) {
        if (m == null) {
            return;
        }
        messages.add(m);
    }

    private Message[] returnMessages() {
        if (messages.isEmpty()) {
            return null;
        }
        return (Message[]) messages.toArray(new Message[messages.size()]);
    }

    /**
     * Returns the internal message of the component
     * @return 
     */
    public Message getMessage() {
        return internalMessage;
    }

    /**
     * Sets the reference to the message bus
     * @param bus 
     */
    public void setMessageBus(MessageBus bus) {
        this.bus = bus;
    }

    /**
     * Sets and returns the internal message
     * @param message
     * @param data
     * @return 
     */
    public Message setMessage(String message, Object data) {
        return internalMessage.setMessage(message, data);
    }
}
