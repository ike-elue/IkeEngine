package com.ikeengine.util;

import com.ikeengine.debug.MessageActivator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Jonathan Elue
 */
public class Loader {
    private final ComponentUtil cu;
    public Loader(ComponentUtil cu) {
        if(cu==null)
            this.cu = new ComponentUtil();
        else
            this.cu = cu;
    }
    
    public Extracter loadObject(File file) {
        Scanner scan = null;
        Extracter e = null;
        try {
            scan = new Scanner(file);
            e = new Extracter();
            e.setName(scan.nextLine());
            e.setComponents(scan.nextLine().split(","));
            String[] messageTypes;
            String[] methodNums;
            int[][] realMethodNums;
            int biggest; 
            for(int i = 0; i < e.getComponents().length; i++) {
                messageTypes = scan.nextLine().split(",");
                methodNums = scan.nextLine().split("|");
                biggest = getBiggest(methodNums, ",");
                realMethodNums = new int[messageTypes.length][biggest];
                empty(realMethodNums);
                for(int j = 0; j < methodNums.length; j++) {
                    set(realMethodNums, methodNums[j].split(","), j);
                }
                e.setMessageActivator(new MessageActivator(messageTypes, realMethodNums));
            }
            e.setComponentUtil(cu);
        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }
        return e;
    }
    
    private int getBiggest(String[] array, String pattern) {
        int biggest = 0;
        int holder;
        for(String s : array) {
            holder = s.split(pattern).length;
            if(biggest < holder)
                biggest = holder;
        }
        return biggest;
    }
    
    private void set(int[][] nums, String[] arrayOfNums, int row) {
        for(int col = 0; col < arrayOfNums.length; col++)
            nums[row][col] = Integer.parseInt(arrayOfNums[col]);
    }
    
    private void empty(int[][] array) {
        for(int i = 0; i < array.length; i++)
            for(int j = 0; j < array[i].length; j++)
                array[i][j] = -1;
    }
}
