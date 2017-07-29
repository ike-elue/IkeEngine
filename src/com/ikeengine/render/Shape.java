package com.ikeengine.render;

import com.ikeengine.shader.ShaderProgram;
import com.ikeengine.util.RawModel;
import com.ikeengine.util.Transform;
import org.joml.Vector3f;

/**
 *
 * @author Jonathan Elue
 */
public class Shape extends View{
    public final int vertexCount;
    private final RawModel model;
    
    public Shape(boolean isFilled, String shaderName, RawModel model) {
        super("shape", shaderName);
        if(isFilled)
            type += "_fill";
        else
            type += "_draw";
        vertexCount = model.vertexCount;
        this.model = model;
    }
    
    /**
     * Enables the model
     */
    public void enable() {
        model.enable();
    }
    
    /**
     * Disables the model
     */
    public void disable() {
        model.disable();
    }

    @Override
    public void setShaderValues(ShaderProgram shader, Transform transform, Vector3f coordinates) {}
}
