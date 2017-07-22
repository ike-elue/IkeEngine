package com.ikeengine.util;

import com.ikeengine.render.Texture;
import com.ikeengine.shader.ShaderProgram;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

/**
 *
 * @author Jonathan Elue
 */
public class Loader {

    private final HashMap<String, ShaderProgram> shaders;
    private final HashMap<String, Texture> textures;
    private final HashMap<String, RawModel> models;
    private final ArrayList<Integer> vbos;

    private static final int BYTES_PER_PIXEL = 4;

    private final int screenWidth, screenHeight;

    public Loader(int screenWidth, int screenHeight) {
        shaders = new HashMap<>();
        textures = new HashMap<>();
        models = new HashMap<>();
        vbos = new ArrayList<>();
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public RawModel getModel(String name) {
        return models.get(name);
    }

    public void loadToVAO(String name, float[] positions, float[] textureCoords, int[] indices) {
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 2, textureCoords);
        unbindVAO();
        models.put(name, new RawModel(vaoID, indices.length));
    }

    private int createVAO() {
        int vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);
        return vaoID;
    }

    private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
        int vboID = glGenBuffers();
        vbos.add(vboID);
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(attributeNumber, coordinateSize, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private void unbindVAO() {
        glBindVertexArray(0);
    }

    private void bindIndicesBuffer(int[] indices) {
        int vboID = glGenBuffers();
        vbos.add(vboID);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    }

    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public String loadShaderData(String fileName) throws Exception {
        String result;
        try (InputStream in = Loader.class.getClass().getResourceAsStream(fileName);
                Scanner scanner = new Scanner(in, "UTF-8")) {
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    }

    public void addShader(String shaderName, ShaderProgram program) {
        shaders.put(shaderName, program);
        program.setName(shaderName);
    }

    public ShaderProgram getShaderProgram(String name) {
        return shaders.get(name);
    }

    public void dispose() {
        vbos.stream().forEach((vbo) -> {
            glDeleteBuffers(vbo);
        });

        shaders.keySet().stream().forEach((s) -> {
            shaders.get(s).cleanup();
        });

        textures.keySet().stream().forEach((t) -> {
            textures.get(t).cleanUp();
        });

        models.keySet().stream().forEach((m) -> {
            models.get(m).cleanUp();
        });
    }

    public int[] getPixels(String path) {
        try {
            BufferedImage image = ImageIO.read(Loader.class.getResourceAsStream(path));
            int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0,
                    image.getWidth());
            image.flush();
            return pixels;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    

    public void loadTexture(String name, int width, int height, int[] pixels, String shaderName) {
        if (!shaders.containsKey(shaderName)) {
            return;
        }
        if (pixels == null) {
            return;
        }
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height
                * BYTES_PER_PIXEL); // 4 for RGBA, 3 for RGB

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = pixels[y * width + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF)); // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF)); // Green component
                buffer.put((byte) (pixel & 0xFF)); // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF)); // Alpha component.
                // Only for RGBA
            }
        }

        buffer.flip(); // NEVER FORGET THIS

        int textureID = glGenTextures(); // Generate texture ID
        glBindTexture(GL_TEXTURE_2D, textureID); // Bind texture ID

        // Setup wrap mode
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        // Setup texture scaling filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        // Send pixel data to OpenGL
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA,
                GL_UNSIGNED_BYTE, buffer);

        // Add the texture ID so we can bind it later again
        textures.put(name, new Texture(name, textureID, width, height, screenWidth, screenHeight, shaders.get(shaderName), this));
    }

}
