package com.deniscag28.shooter3D.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import com.deniscag28.shooter3D.Utils;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

public class Mesh {

    private final int vaoId;
    private final List<Integer> vboIds = new ArrayList<>();
    private final int vertexCount;

    public Mesh(float[] positions, float[] normals, float[] textCoords, int[] indices) {
        vertexCount = indices.length;

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        int vboId = glGenBuffers();
        vboIds.add(vboId);
        FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(positions.length);
        verticesBuffer.put(positions).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        vboId = glGenBuffers();
        vboIds.add(vboId);
        FloatBuffer normalsBuffer = MemoryUtil.memAllocFloat(normals.length);
        normalsBuffer.put(normals).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, normalsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

        vboId = glGenBuffers();
        vboIds.add(vboId);
        FloatBuffer textCoordsBuffer = MemoryUtil.memAllocFloat(textCoords.length);
        textCoordsBuffer.put(textCoords).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);

        vboId = glGenBuffers();
        vboIds.add(vboId);
        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        indicesBuffer.put(indices).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void cleanUp() {
        glDisableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        for(int i : vboIds) glDeleteBuffers(i);

        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }

    public static Mesh loadObj(String objPath){
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Vector2f> textCoords = new ArrayList<>();

        List<Float> verticesA = new ArrayList<>();
        List<Float> normalsA = new ArrayList<>();
        List<Float> textCoordsA = new ArrayList<>();
        List<Integer> indicesA = new ArrayList<>();

        String[] objLines = Utils.readFile(objPath).split("\n");

        for(String line : objLines){
            String[] args = line.split(" ");

            if(args[0].equals("v")){
                float x = Float.parseFloat(args[1]);
                float y = Float.parseFloat(args[2]);
                float z = Float.parseFloat(args[3]);
                vertices.add(new Vector3f(x, y, z));
            }
            if(args[0].equals("vt")){
                float x = Float.parseFloat(args[1]);
                float y = Float.parseFloat(args[2]);
                textCoords.add(new Vector2f(x, y));
            }
            if(args[0].equals("vn")){
                float x = Float.parseFloat(args[1]);
                float y = Float.parseFloat(args[2]);
                float z = Float.parseFloat(args[3]);
                normals.add(new Vector3f(x, y, z));
            }
            if(args[0].equals("f")){
                String[] arg1 = args[1].split("/");
                String[] arg2 = args[2].split("/");
                String[] arg3 = args[3].split("/");
                Vector3f vi1 = vertices.get(Integer.parseInt(arg1[0]) - 1);
                Vector3f vi2 = vertices.get(Integer.parseInt(arg2[0]) - 1);
                Vector3f vi3 = vertices.get(Integer.parseInt(arg3[0]) - 1);
                Vector2f vti1 = textCoords.get(Integer.parseInt(arg1[1]) - 1);
                Vector2f vti2 = textCoords.get(Integer.parseInt(arg2[1]) - 1);
                Vector2f vti3 = textCoords.get(Integer.parseInt(arg3[1]) - 1);
                Vector3f vni1 = normals.get(Integer.parseInt(arg1[2]) - 1);
                Vector3f vni2 = normals.get(Integer.parseInt(arg2[2]) - 1);
                Vector3f vni3 = normals.get(Integer.parseInt(arg3[2]) - 1);
                verticesA.add(vi1.x);verticesA.add(vi1.y);verticesA.add(vi1.z);
                verticesA.add(vi2.x);verticesA.add(vi2.y);verticesA.add(vi2.z);
                verticesA.add(vi3.x);verticesA.add(vi3.y);verticesA.add(vi3.z);
                normalsA.add(vni1.x);normalsA.add(vni1.y);normalsA.add(vni1.z);
                normalsA.add(vni2.x);normalsA.add(vni2.y);normalsA.add(vni2.z);
                normalsA.add(vni3.x);normalsA.add(vni3.y);normalsA.add(vni3.z);
                textCoordsA.add(vti1.x);textCoordsA.add(vti1.y);
                textCoordsA.add(vti2.x);textCoordsA.add(vti2.y);
                textCoordsA.add(vti3.x);textCoordsA.add(vti3.y);
                indicesA.add(verticesA.size() / 3 - 3);
                indicesA.add(verticesA.size() / 3 - 2);
                indicesA.add(verticesA.size() / 3 - 1);
            }
        }

        float[] verticesB = new float[verticesA.size()];
        float[] normalsB = new float[normalsA.size()];
        float[] textCoordsB = new float[textCoordsA.size()];
        int[] indicesB = new int[indicesA.size()];

        for(int i = 0;i < verticesA.size();i++) verticesB[i] = verticesA.get(i);
        for(int i = 0;i < normalsA.size();i++) normalsB[i] = normalsA.get(i);
        for(int i = 0;i < textCoordsA.size();i++) textCoordsB[i] = textCoordsA.get(i);
        for(int i = 0;i < indicesA.size();i++) indicesB[i] = indicesA.get(i);

        return new Mesh(verticesB, normalsB, textCoordsB, indicesB);
    }
}