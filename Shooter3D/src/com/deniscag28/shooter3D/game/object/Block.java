package com.deniscag28.shooter3D.game.object;

import com.deniscag28.shooter3D.Shooter3D;
import com.deniscag28.shooter3D.Utils;
import com.deniscag28.shooter3D.game.module.IModule;
import com.deniscag28.shooter3D.game.module.Rendering;
import com.deniscag28.shooter3D.game.module.Transform;
import com.deniscag28.shooter3D.graphics.Material;
import com.deniscag28.shooter3D.graphics.Mesh;
import com.deniscag28.shooter3D.graphics.ShaderProgram;
import org.joml.Vector3f;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Block implements IGameObject {
    public Map<String, IModule> modules = new HashMap<>();

    public Block(){
        Shooter3D.registerGameObject(this);

        modules.put("transform", new Transform(new Vector3f(0, 0, -5), new Vector3f(0, 0, 0), 1));
        modules.put("rendering", new Rendering(this.modules,
                ShaderProgram.loadShader("./", "block"),
                Mesh.loadObj("block.obj"),
                new Material(Color.BLUE))); // Utils.loadTexture("./block.png")
    }

    @Override
    public Collection<IModule> getModules() {
        return modules.values();
    }
}
