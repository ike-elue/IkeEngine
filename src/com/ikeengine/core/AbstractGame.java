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
    
    public void start() {
        core.begin();
    }
    
    public abstract void init(Loader loader, SceneManager s, MessageBus bus);
    
    public Core getCore() {
        return core;
    }
}
