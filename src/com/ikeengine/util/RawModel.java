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
    private final int[] attributes;
    
    /**
     * Constructs a model that can be drawn to the game
     * @param vaoID
     * @param vertexCount 
     * @param attributes
     */
    public RawModel(int vaoID, int vertexCount, int attributes) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
        this.attributes = new int[attributes];
        for(int i = 0; i < attributes; i++)
            this.attributes[i] = i;
    }

    /**
     * Binds RawModel
     */
    public void enable() {
        glBindVertexArray(vaoID);
        for(int num : attributes)
            glEnableVertexAttribArray(num);
    }
    
    /**
     * Unbinds RawModel
     */
    public void disable() {
        for(int num : attributes)
            glDisableVertexAttribArray(num);
        glBindVertexArray(0);
    }

    /**
     * Cleans used resource
     */
    public void cleanUp() {
        glDeleteVertexArrays(vaoID);
    }
}
