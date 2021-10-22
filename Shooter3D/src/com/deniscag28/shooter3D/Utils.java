package com.deniscag28.shooter3D;

import com.deniscag28.shooter3D.graphics.Texture;
import org.lwjgl.system.MemoryStack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Utils {
    public static String readFile(String filePath){
        StringBuilder output = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) output.append(line).append("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }
    public static Texture loadTexture(String texturePath) {
        int width;
        int height;
        ByteBuffer buf;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            buf = stbi_load(texturePath, w, h, channels, 4);
            if (buf == null) {
                System.out.println("Image file [" + texturePath  + "] not loaded: " + stbi_failure_reason());
                System.exit(-1);
            }

            width = w.get();
            height = h.get();
        }

        return new Texture(buf, width, height);
    }
}
