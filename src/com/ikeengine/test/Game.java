/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ikeengine.test;

import com.ikeengine.core.AbstractGame;
import com.ikeengine.debug.MessageBus;
import com.ikeengine.scene.Scene;
import com.ikeengine.scene.SceneManager;
import com.ikeengine.util.ComponentUtil;
import com.ikeengine.util.Loader;
import java.io.File;

/**
 *
 * @author Jonathan Elue
 */
public class Game extends AbstractGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game g = new Game("IkeEngine Test", 640, 480, 5, null, true);
        g.start();
    }

    public Game(String title, int width, int height, int threadCount, ComponentUtil cu, boolean debug) {
        super(title, width, height, threadCount, cu, debug);
    }

    @Override
    public void init(Loader loader, SceneManager s, MessageBus bus) {
        Scene scene = new Scene(bus);
        
// Without Text Files
//        int test = scene.generateGameObject();
//        scene.addComponent(new TestComponent(test, 
//                new MessageActivator(new String[] {"KEY_INPUT"}, new int[][] {{TestComponent.ACTION}})));
        
// With Text Files
        scene.generateGameObject(loader.loadObject(new File("res/Player.txt")));

        s.addScene("Test Scene", scene);
    }

    

    
}
