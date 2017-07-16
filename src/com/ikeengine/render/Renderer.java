package com.ikeengine.render;

import com.ikeengine.debug.Message;
import com.ikeengine.debug.MessageBus;
import com.ikeengine.shader.ShaderProgram;
import com.ikeengine.util.Loader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Jonathan Elue
 */
public class Renderer {
    public final int width, height;
    public ShaderProgram shader;
    public final List<Message> messages;
    public final Set<String> shaders;
    public Message camera;
    
    public Renderer(int width, int height) {
        this.width = width;
        this.height = height;
        shader = null;
        camera = null;
        messages = new ArrayList<>();
        shaders = new HashSet<>();
    }

    public void render(MessageBus bus, Loader loader) {
        bus.getMessages().stream().forEach((m) -> {
            if(m.getMessage().toLowerCase().contains("render"))
                messages.add(m);
            if(m.getMessage().equalsIgnoreCase("Camera_Coords"))
                camera = m;
        });
        
        int i = 0;
        while(i < messages.size()) {
             if(messages.get(i).getData() instanceof RenderPacket) {
                shaders.add(((RenderPacket) messages.get(i).getData()).getView().getShaderName());
                i++;
             }
             else
                 messages.remove(messages.get(i));
        }
       
        shaders.stream().forEach((s) -> {
            loader.getShaderProgram(s).bind();
            messages.stream().forEach((m) -> {
                if(((RenderPacket)m.getData()).getView().getShaderName().equalsIgnoreCase(s)) // Can add more sorting later
                    miniRender((RenderPacket) m.getData());
            });
            loader.getShaderProgram(s).unbind();
        });
    }
    public void miniRender(RenderPacket packet) {
        packet.getView().setShaderValues(packet);
    }
}
