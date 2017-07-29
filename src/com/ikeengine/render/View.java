package com.ikeengine.render;

import com.ikeengine.shader.ShaderProgram;
import com.ikeengine.util.Transform;
import org.joml.Vector3f;

/**
 *
 * @author Jonathan Elue
 */
public abstract class View {
    private final String shaderName;
    protected String type;
    public View(String type, String shaderName) {
        this.type = type;
        this.shaderName = shaderName;
    }
    
    /**
     * Sets values of uniforms needed for shader
     * @param shader
     * @param transform
     * @param coordinates 
     */
    public abstract void setShaderValues(ShaderProgram shader, Transform transform, Vector3f coordinates);
    
    /**
     * Returns type of view this is
     * @return 
     */
    public String getType() {
        return type;
    }
    
    /**
     * Returns shader name
     * @return 
     */
    public String getShaderName() {
        return shaderName;
    }
}
