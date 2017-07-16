package com.ikeengine.scene;

import com.ikeengine.debug.Message;
import java.util.HashMap;

/**
 *
 * @author Jonathan Elue
 */
public class SceneManager {

    private final boolean debug;
    private final HashMap<String, Scene> scenes;
    private final HashMap<String, Scene> foreScenes;
    private Scene currentScene;
    private Scene currentForeScene;
    private Message currentSwitchMessage;
    
    public SceneManager(boolean debug) {
        this.debug = debug;
        scenes = new HashMap<>();
        currentScene = null;
        foreScenes = new HashMap<>();
        currentForeScene = null;
        currentSwitchMessage = null;
    }

    public void switchScene(String tag, Message onEnterInfo) {
        Scene tempScene = currentScene;
        
        if (scenes.containsKey(tag)) 
            tempScene = scenes.get(tag);

        if (tempScene != currentScene) {
            currentScene = tempScene;
            currentSwitchMessage = onEnterInfo;
            if(debug)
                System.out.println("Current Scene: " + tag);
        }
        else
            System.out.println("Invalid Id Num...\nSame state activated...\n");
    }

    public void addScene(String tag, Scene scene) {
        scenes.put(tag, scene);
        if(currentScene == null)
            switchScene(tag, null);
    }

    public void addForeScene(String tag, Scene scene) {
        foreScenes.put(tag, scene);
    }
    
    public void activateForeScene(String tag) {
        if(foreScenes.containsKey(tag))
            currentForeScene = foreScenes.get(tag);
    }
    
    public void deactivateForeScene() {
        currentForeScene = null;
    }
    
    public boolean isForeSceneActive() {
        return currentForeScene != null;
    }
    
    public Scene getCurrentScene() {
        return currentScene;
    }

    public Scene getCurrentForeScene() {
        return currentForeScene;
    }
    
    public Scene getScene(String tag) {
        return scenes.get(tag);
    }
    
    public Message getCurrentSwitchMessage() {
        Message message = new Message(currentSwitchMessage);
        currentSwitchMessage = null;
        return message;
    }
}
