package com.ikeengine.core;

import com.ikeengine.debug.MessageBus;
import com.ikeengine.scene.SceneManager;
import com.ikeengine.util.Loader;

/**
 *
 * @author Jonathan Elue
 */
public abstract class AbstractGame {
    private final Core core;
    
    public AbstractGame(String title, int width, int height, int threadCount, boolean debug) {
        core = new Core(title, width, height, threadCount, debug, this);
    }
    
    /**
     * Starts Game
     */
    public void start() {
        core.begin();
    }
    
    /**
     * Function that dictates objects, scenes, and resources
     * @param loader
     * @param s
     * @param bus 
     */
    public abstract void init(Loader loader, SceneManager s, MessageBus bus);
}
