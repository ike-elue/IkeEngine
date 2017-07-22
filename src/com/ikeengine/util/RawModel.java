package com.ikeengine.util;

import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.ARBVertexArrayObject.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;

/**
 *
 * @author Jonathan Elue
 */
public class RawModel {

    public final int vaoID;
    public final int vertexCount;
    private final int[] attributes; // Will be bettered later
    
    public RawModel(int vaoID, int vertexCount) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
        attributes = new int[2];
        attributes[0] = 0;
        attributes[1] = 1;
    }

    public void enable() {
        glBindVertexArray(vaoID);
        for(int num : attributes)
            glEnableVertexAttribArray(num);
    }
    public void disable() {
        for(int num : attributes)
            glDisableVertexAttribArray(num);
        glBindVertexArray(0);
    }

    public void cleanUp() {
        glDeleteVertexArrays(vaoID);
    }
}
