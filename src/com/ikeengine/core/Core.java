package com.ikeengine.core;

import com.ikeengine.audio.AudioManager;
import com.ikeengine.debug.MessageBus;
import com.ikeengine.input.CursorPosHandler;
import com.ikeengine.input.KeyboardHandler;
import com.ikeengine.input.MouseButtonHandler;
import com.ikeengine.physics.CollisionWorld;
import com.ikeengine.render.Renderer;
import com.ikeengine.scene.SceneManager;
import com.ikeengine.util.Loader;
import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GLContext;

/**
 *
 * @author Jonathan Elue
 */
public class Core implements Runnable {

    public static boolean running;
    private Thread thread;
    private String fpsString;
    
    private final ThreadPool pool;
    public final MessageBus bus;
    public final SceneManager s;
    
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWCursorPosCallback cursorPosCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;

    private long window;
    public final String title;
    public final int width, height;
    public final boolean debug;
    public final AbstractGame game;
    
    private final Loader loader;
    private final Renderer renderer;
    private AudioManager audio;
    private final CollisionWorld cworld;
    
    public Core(String title, int width, int height, int threadCount, boolean debug, AbstractGame game) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.debug = debug;
        this.game = game;
        thread = null;
        fpsString = "";
        
        bus = new MessageBus(title, debug);
        pool = new ThreadPool(threadCount, bus); 
        s = new SceneManager(debug);
        renderer = new Renderer(width, height);
        cworld = new CollisionWorld();
        
        loader = new Loader(width, height);
    }

    public void begin() {
        if (thread == null) {
            thread = new Thread(this);
            running = true;
            thread.start();
        }
    }

    private void init() {
        initGLFW();
        initOpenGL();
        initOpenAL();
    }

    private void initGLFW() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));

        if (glfwInit() != GL_TRUE) {
            // Throw an error.
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are
        // already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden
        // aft
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE); // Allows our window to not be
        // resizable (too
        // complicated)

        window = glfwCreateWindow(width, height, title, NULL, NULL);

        if (window == NULL) {
            // Throw an Error
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwSetKeyCallback(window, keyCallback = new KeyboardHandler());
        glfwSetCursorPosCallback(window,
                cursorPosCallback = new CursorPosHandler());
        glfwSetMouseButtonCallback(window,
                mouseButtonCallback = new MouseButtonHandler());

        // Center window
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        int x = GLFWvidmode.width(vidmode);
        int y = GLFWvidmode.height(vidmode);
        int winposx = (int) ((x - width) / 2.0);
        int winposy = (int) ((y - height) / 2.0);
        glfwSetWindowPos(window, winposx, winposy);

        glfwMakeContextCurrent(window);

        GLContext.createFromCurrent();
        glfwSwapInterval(1);

        glfwShowWindow(window);
    }

    private void initOpenGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        glOrtho(0, width, 0, height, -1, 1);

        glMatrixMode(GL_MODELVIEW);

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);

        System.out.println("OpenGL: " + glGetString(GL_VERSION));
    }

    private void initOpenAL() {
        audio = new AudioManager();
    }

    @Override
    public void run() {
        init();
        game.init(loader, s, bus);

        double fps = 0;
        double fpsRefined = 0;
        
        double frameCap = 1.0f / 60.0f;

        double firstTime;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passedTime;
        double unprocessedTime = 0;

        double frameTime = 0;
        int frames = 0;

        while (running) {

            boolean render = false;

            // Calculations for Delta Time and Loop
            firstTime = System.nanoTime() / 1000000000.0;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;

            unprocessedTime += passedTime;

            frameTime += passedTime;

            while (unprocessedTime >= frameCap) {
                if(fps == 0)
                    fpsRefined = 0;
                else
                    fpsRefined = 1/fps;
                update(fpsRefined);
                unprocessedTime -= frameCap;
                render = true;

                if (frameTime >= 1) {
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                }
            }

            if (render) {
                render();
                if(debug)
                    fpsString = "FPS: " + fps + " Delta: " + fpsRefined;
                frames++;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (glfwWindowShouldClose(window) == GL_TRUE)
                running = false;
        }

        dispose();
        System.exit(0);
    }

    private void input() {
        ((KeyboardHandler)keyCallback).update();
        ((MouseButtonHandler)mouseButtonCallback).update();
        
        glfwPollEvents();
        
        ((KeyboardHandler)keyCallback).getMessages(bus);
        ((MouseButtonHandler)mouseButtonCallback).getMessages(bus);
        ((CursorPosHandler)cursorPosCallback).sendCoords(bus);
    }

    public void update(double delta) {
        bus.setDelta(delta);
        input();
        cworld.update(bus);
        if(debug)
            bus.print(fpsString);
        updateScene();
    }
    
    private void updateScene() {
        pool.update(s);
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        renderer.render(bus, loader);
	glfwSwapBuffers(window);
    }

    private void dispose() {
        
        if(audio != null)
            AudioManager.cleanUp();
        
        loader.dispose();
        
        glfwDestroyWindow(window);
        
        keyCallback.release();
        mouseButtonCallback.release();
        cursorPosCallback.release();

        // Final Cleanup
        pool.dispose();
        glfwTerminate();
        errorCallback.release();

        System.out.println("Cleaned Up!");
    }
}
