package com.ikeengine.debug;

/**
 *
 * @author Jonathan Elue
 */
public class MessageActivator {
    public final String[] messageTypes;
    public final Object[] comparableData;
    public final int[][] methodNums;
    public final int length;
    
    private String lastKnownName;
    
    public MessageActivator(String[] messageTypes, Object[] comparableData, int[][] methodNums) {
        if(messageTypes.length != comparableData.length || messageTypes.length != methodNums.length || comparableData.length != methodNums.length) {
            this.messageTypes = null;
            this.comparableData = null;
            this.methodNums = null;
            length = -1;
        }
        else {
            this.messageTypes = messageTypes;
            this.comparableData = comparableData;
            this.methodNums = methodNums;
            length = messageTypes.length;
        }
        lastKnownName = "";
    }
    
    /**
     * Sets all messages that want to equal the name to the name given
     * @param name 
     */
    public void setName(String name) {
        for(int i = 0; i < messageTypes.length; i++)
            if(messageTypes[i].equalsIgnoreCase("") || messageTypes[i].equalsIgnoreCase(lastKnownName))
                messageTypes[i] = name;
        lastKnownName = name;
    }
    
}
