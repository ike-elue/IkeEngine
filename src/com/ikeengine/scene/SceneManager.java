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
    private Scene currentScene;
    private Message currentSwitchMessage;
    
    public SceneManager(boolean debug) {
        this.debug = debug;
        scenes = new HashMap<>();
        currentScene = null;
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

    public Scene getCurrentScene() {
        return currentScene;
    }

    public Scene getState(String tag) {
        return scenes.get(tag);
    }
    
    public Message getCurrentSwitchMessage() {
        Message message = new Message(currentSwitchMessage);
        currentSwitchMessage = null;
        return message;
    }
}
