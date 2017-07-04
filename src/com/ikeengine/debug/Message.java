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
    
    public Object getData() {
        return associatedData;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message, Object associatedData) {
        this.message = message;
        this.associatedData = associatedData;
    }
    
    public boolean hasMessage() {
        return message != null;
    }
    
    @Override
    public String toString() {
        return "ID - " + id + ", Name - " + name  + ", Message - " + message + ", Data - " + associatedData;
    }
}
