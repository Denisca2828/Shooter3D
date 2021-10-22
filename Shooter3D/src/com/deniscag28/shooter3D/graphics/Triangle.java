package com.deniscag28.shooter3D.graphics;

public class Triangle {
    public int[] verticesI = new int[3];
    public int[] textCoordsI = new int[3];
    public int[] normalsI = new int[3];

    public Triangle(int[] verticesI, int[] textCoordsI, int[] normalsI){
        this.verticesI = verticesI;
        this.textCoordsI = textCoordsI;
        this.normalsI = normalsI;
    }
}
