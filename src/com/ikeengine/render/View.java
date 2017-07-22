package com.ikeengine.render;

import com.ikeengine.shader.ShaderProgram;
import com.ikeengine.util.Transform;
import org.joml.Vector3f;

/**
 *
 * @author Jonathan Elue
 */
public abstract class View {
    protected final ShaderProgram shader;
    protected String type;
    public View(String type, ShaderProgram shader) {
        this.type = type;
        this.shader = shader;
    }
    
    public abstract void setShaderValues(Transform transform, Vector3f coordinates);
    
    public String getType() {
        return type;
    }
    
    public String getShaderName() {
        return shader.getName();
    }
}
