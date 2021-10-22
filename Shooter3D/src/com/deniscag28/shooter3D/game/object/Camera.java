package com.deniscag28.shooter3D.game.object;

import com.deniscag28.shooter3D.game.module.IModule;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Collection;

public class Camera implements IGameObject{
    public float fov;
    public float width;
    public float height;
    public float near;
    public float far;

    public Camera(float fov, float width, float height, float near, float far){
        this.fov = fov;
        this.width = width;
        this.height = height;
        this.near = near;
        this.far = far;
    }

    public final Matrix4f getProjectionMatrix() {
        float aspectRatio = width / height;
        Matrix4f projectionMatrix = new Matrix4f();
        projectionMatrix.identity();
        projectionMatrix.perspective(fov, aspectRatio, near, far);
        return projectionMatrix;
    }

    public Matrix4f getWorldMatrix(Vector3f offset, Vector3f rotation, float scale) {
        Matrix4f worldMatrix = new Matrix4f();
        worldMatrix.identity().translate(offset).
                rotateX((float)Math.toRadians(rotation.x)).
                rotateY((float)Math.toRadians(rotation.y)).
                rotateZ((float)Math.toRadians(rotation.z)).
                scale(scale);
        return worldMatrix;
    }

    @Override
    public Collection<IModule> getModules() {
        return null;
    }
}
