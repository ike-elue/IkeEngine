package com.ikeengine.render;

import com.ikeengine.shader.ShaderProgram;

/**
 *
 * @author Jonathan Elue
 */
public abstract class View {
    protected final ShaderProgram shader;
    private final String type;
    public View(String type, ShaderProgram shader) {
        this.type = type;
        this.shader = shader;
    }
    
    public abstract void setShaderValues(RenderPacket packet);
    
    public String getType() {
        return type;
    }
    
    public String getShaderName() {
        return shader.getName();
    }
}
