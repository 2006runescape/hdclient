package com.runescape.media.renderable;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.collection.NodeSub;
import com.runescape.media.VertexNormal;

public class Renderable extends NodeSub {

    public int modelHeight;
    public VertexNormal vertexNormals[];

    public Renderable() {
        modelHeight = 1000;
    }

    public void render(int i, int j, int k, int l, int i1, int j1, int k1,
                       int l1, int i2) {
        Model model = getRotatedModel();
        if (model != null) {
            modelHeight = model.modelHeight;
            model.render(i, j, k, l, i1, j1, k1, l1, i2);
        }
    }

    public Model getRotatedModel() {
        return null;
    }
}
