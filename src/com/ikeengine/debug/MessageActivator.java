package com.ikeengine.debug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Jonathan Elue
 */
public class MessageActivator {
    public final String[] messageTypes;
    public final Object[] comparableData;
    public final int[][] methodNums;
    public final int length;
    
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
    }
}
