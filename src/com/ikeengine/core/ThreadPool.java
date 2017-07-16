package com.ikeengine.core;

import com.ikeengine.debug.Message;
import com.ikeengine.debug.MessageBus;
import com.ikeengine.scene.Scene;
import com.ikeengine.scene.SceneManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonathan Elue
 */
public class ThreadPool {
    private final ExecutorService executor;
    private final List<Future> futures;
    private final List<Message> messageBuffer;
    private final MessageBus bus;
    
    /**
     * Constructor
     * @param threadCount
     * @param bus
     */
    public ThreadPool(int threadCount, MessageBus bus) {
        executor = Executors.newFixedThreadPool(threadCount);
        futures = new ArrayList<>();
        messageBuffer = new ArrayList<>();
        this.bus = bus;
    }

    /**
     * Runs the run() method in each Component of the scene
     * @param eManager reference to EngineManager (holds the list)
     */
    private void execute(Scene scene) {
        scene.getComponents().stream().forEach((cs) -> {
            cs.getComponents().stream().map((c) -> executor.submit(c)).forEach((f) -> {
                futures.add(f);
            });
        });
        
        futures.stream().forEach((f) -> {
            try {
                Message[] temp = (Message[]) f.get();
                if(temp != null)
                    messageBuffer.addAll(Arrays.asList(temp));
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(ThreadPool.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        bus.clear();
        bus.addMessages(messageBuffer);
        bus.clean();
        messageBuffer.clear();
        futures.clear();
    }

    
    /**
     * @param s 
     */
    public void update(SceneManager s) {
        if(s.isForeSceneActive())
            execute(s.getCurrentForeScene());
        else if(s.getCurrentScene() != null)
            execute(s.getCurrentScene());
    }
    
    public void dispose() {
        try {
            System.out.println("attempt to shutdown executor");
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        } finally {
            if (!executor.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            executor.shutdownNow();
            System.out.println("shutdown finished");
        }
    }
}