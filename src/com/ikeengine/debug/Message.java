package com.ikeengine.debug;

/**
 *
 * @author Jonathan Elue
 */
public class Message {
    public final int id;
    public final String name;
    protected String message;
    protected Object associatedData;
    
    public Message(int id, String name) {
        this.id = id;
        this.name = name;
        message = "";
        associatedData = null;
    }
    
    public Message(Message m) {
        id = m.id;
        name = m.name;
        message = m.message;
        associatedData = m.associatedData;
    }
    
    /**
     * Returns the Data associated with the message 
     * @return 
     */
    public Object getData() {
        return associatedData;
    }
    
    /**
     * Returns the String representation of the message
     * @return 
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * Sets Message with String and Data and returns self
     * @param message
     * @param associatedData
     * @return 
     */
    public Message setMessage(String message, Object associatedData) {
        this.message = message;
        this.associatedData = associatedData;
        return this;
    }
    
    @Override
    public String toString() {
        return "ID - " + id + ", Name - " + name  + ", Message - " + message + ", Data - " + associatedData;
    }
    
    @Override
    public boolean equals(Object o) {
        return (o instanceof Message ? (((Message)o).message.equalsIgnoreCase(message)) : false);
    }
}
