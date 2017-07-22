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
    
    public Shape(boolean isFilled, ShaderProgram shader, RawModel model) {
        super("shape", shader);
        if(isFilled)
            type += "_fill";
        else
            type += "_draw";
        vertexCount = model.vertexCount;
        this.model = model;
    }
    
    public void enable() {
        model.enable();
    }
    
    public void disable() {
        model.disable();
    }

    @Override
    public void setShaderValues(Transform transform, Vector3f coordinates) {}
}
