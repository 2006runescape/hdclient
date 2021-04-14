package com.runescape.scene.tile;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class SceneTilePaint {

    public final int swColor;
    public final int seColor;
    public final int neColor;
    public final int nwColor;
    public final int texture;
    public final int rgb;
    public boolean flat;

    public SceneTilePaint(int i, int j, int k, int l, int i1, int j1, boolean flag) {
        flat = true;
        swColor = i;
        seColor = j;
        neColor = k;
        nwColor = l;
        texture = i1;
        rgb = j1;
        flat = flag;
    }

    public int getSwColor() {
        return swColor;
    }

    public int getSeColor() {
        return seColor;
    }

    public int getNeColor() {
        return neColor;
    }

    public int getNwColor() {
        return nwColor;
    }

    public int getTexture() {
        return texture;
    }

    public int bufferOffset = -1;

    public void setBufferOffset(final int offset) {
        bufferOffset = offset;
    }

    public int uVBufferOffset = -1;

    public void setUvBufferOffset(final int offset) {
        uVBufferOffset = offset;
    }

    public int bufferLength = -1;

    public void setBufferLen(final int length) {
        bufferLength = length;
    }

    public int getBufferLen() {
        return bufferLength;
    }

    public int getBufferOffset() {
        return bufferOffset;
    }

    public int getUvBufferOffset() {
        return uVBufferOffset;
    }
}
