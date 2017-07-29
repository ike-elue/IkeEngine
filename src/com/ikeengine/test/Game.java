/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ikeengine.test;

import com.ikeengine.component.TestComponent;
import com.ikeengine.core.AbstractGame;
import com.ikeengine.debug.MessageActivator;
import com.ikeengine.debug.MessageBus;
import com.ikeengine.input.Key;
import com.ikeengine.scene.Scene;
import com.ikeengine.scene.SceneManager;
import com.ikeengine.util.Loader;

/**
 *
 * @author Jonathan Elue
 */
public class Game extends AbstractGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game g = new Game("IkeEngine Test", 640, 480, 2, true);
        g.start();
    }

    public Game(String title, int width, int height, int threadCount, boolean debug) {
        super(title, width, height, threadCount, debug);
    }

    @Override
    public void init(Loader loader, SceneManager s, MessageBus bus) {
        Scene scene = new Scene(bus);

        int test = scene.generateGameObject();
        scene.addComponent(new TestComponent(test,
                new MessageActivator(new String[]{"KEY_INPUT", "REPEAT"}, new Object[] {Key.GLFW_SPACE, null}, new int[][]{{TestComponent.ACTION}, {TestComponent.REPEAT}})));

        s.addScene("Test Scene", scene);
    }

}
