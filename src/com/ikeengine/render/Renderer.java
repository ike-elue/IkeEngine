package com.ikeengine.render;

import com.ikeengine.debug.Message;
import com.ikeengine.debug.MessageBus;
import com.ikeengine.shader.ShaderProgram;
import com.ikeengine.util.Loader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.joml.Vector3f;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

/**
 *
 * @author Jonathan Elue
 */
public class Renderer {

    public final int width, height;
    public ShaderProgram shader;
    public final List<Message> messages, prevMessages;
    public final Set<String> shaders;
    public Message camera;

    public Renderer(int width, int height) {
        this.width = width;
        this.height = height;
        shader = null;
        camera = null;
        messages = new ArrayList<>();
        prevMessages = new ArrayList<>();
        shaders = new HashSet<>();
    }

    public void render(MessageBus bus, Loader loader) {
        if(!messages.isEmpty()) {
            prevMessages.clear();
            prevMessages.addAll(messages);
            messages.clear();
            shaders.clear();
        }
        
        bus.getMessages().stream().forEach((m) -> {
            if (m.getMessage().toLowerCase().contains("render")) {
                messages.add(m);
            }
            if (m.getMessage().equalsIgnoreCase("Camera_Coords")) {
                camera = m;
            }
        });

        if(messages.isEmpty()) {
            shaders.stream().forEach((s) -> {
            loader.getShaderProgram(s).bind();
            prevMessages.stream().forEach((m) -> {
                    if (((RenderPacket) m.getData()).getView().getShaderName().equalsIgnoreCase(s)) // Can add more sorting later
                    {
                        miniRender((RenderPacket) m.getData());
                    }
                });
                loader.getShaderProgram(s).unbind();
            });
            return;
        }
        
        int i = 0;
        while (i < messages.size()) {
            if (messages.get(i).getData() instanceof RenderPacket) {
                shaders.add(((RenderPacket) messages.get(i).getData()).getView().getShaderName());
                i++;
            } else {
                messages.remove(messages.get(i));
            }
        }

        shaders.stream().forEach((s) -> {
            loader.getShaderProgram(s).bind();
            messages.stream().forEach((m) -> {
                if (((RenderPacket) m.getData()).getView().getShaderName().equalsIgnoreCase(s)) // Can add more sorting later
                {
                    miniRender((RenderPacket) m.getData());
                }
            });
            loader.getShaderProgram(s).unbind();
        });
    }

    public void miniRender(RenderPacket packet) {
        packet.getView().setShaderValues(packet.getTransform(), getRealCoords(packet.getTransform().translation));
        switch(packet.getView().getType().toLowerCase()) {
            case "texture":
                drawTexture((Texture) packet.getView());
                break;
            case "shape_fill":
                fillShape((Shape) packet.getView());
                break;
            case "shape_draw":
                drawShape((Shape) packet.getView());
                break;
            default:
                break;
        }
    }

    private Vector3f getRealCoords(Vector3f pos) {
        Vector3f position = new Vector3f(pos.x - width/2, pos.y - height/2, pos.z);
        return position.div(width/2, height/2, 1);
    }
    
    public void drawShape(Shape shape) {
        shape.enable();	
        glDrawElements(GL_LINES, shape.vertexCount, GL_UNSIGNED_INT, 0);
        shape.disable();
    }
    
    public void fillShape(Shape shape) {
        shape.enable();
        glDrawElements(GL_TRIANGLES, shape.vertexCount, GL_UNSIGNED_INT, 0);
        shape.disable();
    }
    
    public void drawTexture(Texture texture) {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture.id);
        texture.enable();
        glDrawElements(GL_TRIANGLES, texture.vertexCount, GL_UNSIGNED_INT, 0);
        texture.disable();
    }
}
