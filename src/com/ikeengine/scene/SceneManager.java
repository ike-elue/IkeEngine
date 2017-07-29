package com.ikeengine.scene;

import com.ikeengine.debug.Message;
import com.ikeengine.debug.MessageBus;
import java.util.HashMap;

/**
 *
 * @author Jonathan Elue
 */
public class SceneManager {

    private final boolean debug;
    private final HashMap<String, Scene> scenes;
    private Scene currentScene;
    private final Message currentSwitchMessage;
    private boolean switched;
    
    public SceneManager(boolean debug) {
        this.debug = debug;
        scenes = new HashMap<>();
        currentScene = null;
        currentSwitchMessage = new Message(-1, "Scene Manager");
        switched = false;
    }

    /**
     * Switches the scene to another if the tag is correct
     * @param tag
     * @param onEnterInfo 
     */
    public void switchScene(String tag, Message onEnterInfo) {
        Scene tempScene = currentScene;
        
        if (scenes.containsKey(tag)) 
            tempScene = scenes.get(tag);

        if (tempScene != currentScene) {
            currentScene = tempScene;
            currentSwitchMessage.setMessage("Switched Scenes", onEnterInfo);
            switched = true;
            if(debug)
                System.out.println("Current Scene: " + tag);
        }
        else
            System.out.println("Invalid Id Num...\nSame state activated...\n");
    }

    /**
     * Adds Scene to manager
     * @param tag
     * @param scene 
     */
    public void addScene(String tag, Scene scene) {
        scenes.put(tag, scene);
        if(currentScene == null) {
            currentScene = scenes.get(tag);
            if(debug)
                System.out.println("Current Scene: " + tag);
        }
    }
    
    /**
     * Returns current scene of game
     * @return 
     */
    public Scene getCurrentScene() {
        return currentScene;
    }

    /**
     * Sends current switch message if the current Scene has switched.
     * If it happens, return true
     * @param bus 
     * @return
     */
    public boolean evalutateSwitchedScene(MessageBus bus) {
        if(switched) {
            bus.addMessage(currentSwitchMessage);
            switched = false;
            return true;
        }
        return false;
    }
}
