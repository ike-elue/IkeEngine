package com.ikeengine.render;

import com.ikeengine.shader.ShaderProgram;
import com.ikeengine.util.Loader;
import com.ikeengine.util.RawModel;
import com.ikeengine.util.Transform;
import org.joml.Vector3f;
import static org.lwjgl.opengl.GL11.glDeleteTextures;

/**
 *
 * @author Jonathan Elue
 */
public class Texture extends View{

    public final int id;
    public final int width, height;
    private final RawModel model;
    public final int vertexCount;
    
    public Texture(String name, int id, int width, int height, int screenHeight, int screenWidth, ShaderProgram shader, Loader loader) {
        super("texture", shader);
        this.id = id;
        this.width = width;
        this.height = height;
        float[] positions = new float[] { 0, 0, 0, 0, 2f/screenHeight, 0, 2f/screenWidth, 2f/screenHeight, 0, 2f/screenWidth, 0, 0 };
	float[] textureCoords = new float[] {0f, 1f, 0f, 0f, 1f, 0f, 1f, 1f};
        int[] indices = new int[] { 0, 1, 2, 2, 3, 0 };
	loader.loadToVAO("texture_model_" + id, positions, textureCoords, indices);
        model = loader.getModel("texture_model_" + id);
        vertexCount = model.vertexCount;
    }

    public void enable() {
        model.enable();
    }
    
    public void disable() {
        model.disable();
    }
    
    public void cleanUp() {
        glDeleteTextures(id);
    }

    @Override
    public void setShaderValues(Transform transform, Vector3f coordinates) {}
}
