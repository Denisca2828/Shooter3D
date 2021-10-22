package com.deniscag28.shooter3D.game.module;

import com.deniscag28.shooter3D.Shooter3D;
import com.deniscag28.shooter3D.graphics.Material;
import com.deniscag28.shooter3D.graphics.Mesh;
import com.deniscag28.shooter3D.graphics.ShaderProgram;
import org.joml.Matrix4f;

import java.util.Map;

import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.*;

public class Rendering implements IModule{
    public boolean active = true;

    private Map<String, IModule> parentModules;
    private ShaderProgram shader;
    private Mesh mesh;
    private Material material;

    public Rendering(Map<String, IModule> parentModules, ShaderProgram shader, Mesh mesh, Material material){
        this.parentModules = parentModules;
        this.shader = shader;
        this.shader.createUniform("projectionMatrix");
        this.shader.createUniform("worldMatrix");
        this.shader.createUniform("texture_sampler");
        this.mesh = mesh;
        this.material = material;
    }

    @Override
    public void update() {
        if(active && parentModules.containsKey("transform")){
            Transform transform = (Transform) parentModules.get("transform");

            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, this.material.texture.textureId);

            shader.bind();

            shader.setUniform("texture_sampler", this.material.texture.textureId);
            shader.setUniform("projectionMatrix", Shooter3D.activeCamera.getProjectionMatrix());
            Matrix4f worldMatrix = Shooter3D.activeCamera.getWorldMatrix(transform.position, transform.rotation, transform.scale);
            shader.setUniform("worldMatrix", worldMatrix);

            glBindVertexArray(mesh.getVaoId());

            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);
            glEnableVertexAttribArray(2);

            glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);

            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);
            glDisableVertexAttribArray(2);

            glBindVertexArray(0);

            shader.unbind();
        }
    }
}
