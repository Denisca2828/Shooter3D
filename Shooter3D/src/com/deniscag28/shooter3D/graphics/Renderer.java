package com.deniscag28.shooter3D.graphics;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Renderer {
    public static final float WIDTH = 800;
    public static final float HEIGHT = 500;
    
    private final long _window;

    public Renderer(){
        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        _window = glfwCreateWindow((int) WIDTH, (int) HEIGHT, "Shooter3D", NULL, NULL);
        if ( _window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(_window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true);
        });

        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(_window, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    _window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(_window);

        glfwShowWindow(_window);

        GL.createCapabilities();

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
    }

    public boolean windowShouldClose(){
        return glfwWindowShouldClose(_window);
    }

    public void update(){
        glfwSwapBuffers(_window);
        glfwPollEvents();
    }
}
