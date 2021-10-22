package com.deniscag28.shooter3D.graphics;

import org.lwjgl.BufferUtils;

import java.awt.*;
import java.nio.ByteBuffer;

public class Material {
    public Texture texture;

    public Material(Texture texture){
        this.texture = texture;
    }
    public Material(Color color){
        ByteBuffer buffer = BufferUtils.createByteBuffer(9*4);
        for(int i = 0;i < 9;i++){
            buffer.put((byte) color.getRed());
            buffer.put((byte) color.getGreen());
            buffer.put((byte) color.getBlue());
            buffer.put((byte) color.getAlpha());
        }
        this.texture = new Texture(buffer, 3, 3);
    }
}
