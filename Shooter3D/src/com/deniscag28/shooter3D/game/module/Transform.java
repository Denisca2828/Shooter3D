package com.deniscag28.shooter3D.game.module;

import org.joml.Vector3f;

public class Transform implements IModule{
    public Vector3f position;
    public Vector3f rotation;
    public float scale;

    public Transform(Vector3f position, Vector3f rotation, float scale){
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    @Override
    public void update() {
    }
}
