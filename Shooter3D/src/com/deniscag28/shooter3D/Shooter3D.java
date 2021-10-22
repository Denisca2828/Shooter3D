package com.deniscag28.shooter3D;

import com.deniscag28.shooter3D.game.module.IModule;
import com.deniscag28.shooter3D.game.module.Transform;
import com.deniscag28.shooter3D.game.object.Block;
import com.deniscag28.shooter3D.game.object.Camera;
import com.deniscag28.shooter3D.game.object.IGameObject;
import com.deniscag28.shooter3D.graphics.Renderer;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Shooter3D {
    public static List<IGameObject> gos = new ArrayList<>();
    public static Renderer renderer;
    public static Camera activeCamera;

    public Shooter3D(){
        this.init();
        this.loop();
    }

    public static void registerGameObject(IGameObject gameObject) {
        gos.add(gameObject);
    }

    private void init(){
        renderer = new Renderer();

        activeCamera = new Camera((float) Math.toRadians(90.f), Renderer.WIDTH, Renderer.HEIGHT, 0.01f, 1000.f);
    }

    private void loop(){
        Block testBlock = new Block();

        glClearColor(0.f, 0.f, 0.f, 0.f);

        while(!renderer.windowShouldClose()){
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            ((Transform) testBlock.modules.get("transform")).rotation.x += 0.01f;
            ((Transform) testBlock.modules.get("transform")).rotation.y += 0.01f;
            ((Transform) testBlock.modules.get("transform")).rotation.z += 0.01f;

            for(IGameObject go : gos) {
                for(IModule module : go.getModules()) {
                    module.update();
                }
            }

            renderer.update();
        }
    }
}
