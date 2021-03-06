package com.ikeengine.shader;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

/**
 *
 * @author Jonathan Elue
 */
public abstract class ShaderProgram {

    private String name;
    
    private final int programId;

    private int vertexShaderId;

    private int fragmentShaderId;

    private final Map<String, Integer> uniforms;

    public ShaderProgram(String vertexShaderCode, String fragmentShaderCode) throws Exception {
        name = "ShaderProgram";
        programId = glCreateProgram();
        if (programId == 0) {
            throw new Exception("Could not create Shader");
        }
        uniforms = new HashMap<>();
        createVertexShader(vertexShaderCode);
        createFragmentShader(fragmentShaderCode);
        link();
        initUniforms();
    }
    
    /**
     * Returns name of shader
     * @return 
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets name of shader
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Initializes Uniforms 
     */
    protected abstract void initUniforms();

    /**
     * Sets the value given to the uniform of the camera if needed
     * @param v 
     */
    public abstract void setCamera(Vector2f v);
    
    /**
     * Creates uniform if possible
     * @param uniformName
     * @throws Exception 
     */
    public void createUniform(String uniformName) throws Exception {
        int uniformLocation = glGetUniformLocation(programId, uniformName);
        if (uniformLocation < 0) {
            throw new Exception("Could not find uniform:" + uniformName);
        }
        uniforms.put(uniformName, uniformLocation);
    }

    /**
     * Sets Matrix4f to uniform
     * @param uniformName
     * @param value 
     */
    public void setUniform(String uniformName, Matrix4f value) {
        if(uniforms.containsKey(uniformName)){
            FloatBuffer fb = BufferUtils.createFloatBuffer(16);
            value.get(fb);
            glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
        }
    }
    
    /**
     * Sets int to uniform
     * @param uniformName
     * @param value 
     */
    public void setUniform(String uniformName, int value) {
        if(uniforms.containsKey(uniformName))
            glUniform1i(uniforms.get(uniformName), value);
    }

    /**
     * Sets boolean to uniform
     * @param uniformName
     * @param value 
     */
    public void setUniform(String uniformName, boolean value) {
        if(uniforms.containsKey(uniformName))
            glUniform1f(uniforms.get(uniformName), value ? 1 : 0);
    }

    /**
     * Sets float to uniform
     * @param uniformName
     * @param value 
     */
    public void setUniform(String uniformName, float value) {
        if(uniforms.containsKey(uniformName))
            glUniform1f(uniforms.get(uniformName), value);
    }

    /**
     * Sets Vector2f to uniform
     * @param uniformName
     * @param value 
     */
    public void setUniform(String uniformName, Vector2f value) {
        if(uniforms.containsKey(uniformName))
            glUniform2f(uniforms.get(uniformName), value.x, value.y);
    }

    /**
     * Sets Vector3f to uniform
     * @param uniformName
     * @param value 
     */
    public void setUniform(String uniformName, Vector3f value) {
        if(uniforms.containsKey(uniformName))
            glUniform3f(uniforms.get(uniformName), value.x, value.y, value.z);
    }

    /**
     * Sets Vector4f to uniform
     * @param uniformName
     * @param value 
     */
    public void setUniform(String uniformName, Vector4f value) {
        if(uniforms.containsKey(uniformName))
            glUniform4f(uniforms.get(uniformName), value.x, value.y, value.z, value.w);
    }

    private void createVertexShader(String shaderCode) throws Exception {
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
    }

    private void createFragmentShader(String shaderCode) throws Exception {
        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }

    private int createShader(String shaderCode, int shaderType) throws Exception {
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0) {
            throw new Exception("Error creating shader. Type: " + shaderType);
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }

        glAttachShader(programId, shaderId);

        return shaderId;
    }

    private final void link() throws Exception {
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024));
        }

        if (vertexShaderId != 0) {
            glDetachShader(programId, vertexShaderId);
        }

        if (fragmentShaderId != 0) {
            glDetachShader(programId, fragmentShaderId);
        }

        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
        }
    }

    /**
     * Binds Shader
     */
    public void bind() {
        glUseProgram(programId);
    }

    /**
     * Unbinds Shader
     */
    public void unbind() {
        glUseProgram(0);
    }

    /**
     * Cleans up resources
     */
    public void cleanup() {
        unbind();
        if (programId != 0) {
            glDeleteProgram(programId);
        }
    }
}
