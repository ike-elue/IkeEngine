package com.ikeengine.debug;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonathan Elue
 */
public class MessageActivator {
    private final String[] messageTypes;
    private final int[][] methodNums;
    private final List<String> tempMessages;
    
    public MessageActivator(String[] messageTypes, int[][] methodNums) {
        this.messageTypes = messageTypes;
        this.methodNums = methodNums;
        tempMessages = new ArrayList<>();
    }
    
    public String[] matchMessages(MessageBus bus) {
        tempMessages.clear();
        bus.getMessages().stream().forEach((message) -> {
            for (String messageType : messageTypes) {
                if(messageType.startsWith("-1")) {
                    tempMessages.add(messageType);
                    continue;
                }
                if (message.getMessage().equalsIgnoreCase(messageType))
                    tempMessages.add(messageType);
            }
        });
        return (String[]) tempMessages.toArray(new String[tempMessages.size()]);
    }
    
    public int[] getMethodNums(String messageType) {
        for(int i = 0; i < messageTypes.length; i++) {
            if(messageTypes[i].equalsIgnoreCase(messageType))
                return methodNums[i];
        }
        return new int[] {-1};
    }
    
    public Object getData(String messageType, MessageBus bus) {
        for(Message m : bus.getMessages())
            if(m.getMessage().equalsIgnoreCase(messageType))
                return m.getData();
        return null;
    }
    
}
