package com.ikeengine.util;

import com.ikeengine.shader.ShaderProgram;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Jonathan Elue
 */
public class Loader {
    private final HashMap<String, ShaderProgram> shaders;
    
    public Loader() {
        shaders = new HashMap<>();
    }
    
    public String loadShader(String fileName) throws Exception {
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
        shaders.keySet().stream().forEach((s) -> {
            shaders.get(s).cleanup();
        });
    }

}
