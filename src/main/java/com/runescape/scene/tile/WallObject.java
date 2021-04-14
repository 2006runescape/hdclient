package com.runescape.scene.tile;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.media.renderable.Renderable;

public final class WallObject {

    public Renderable getRenderable1() {
        return renderableOne;
    }

    public Renderable getRenderable2() {
        return renderableTwo;
    }

    public int bufferOffset = -1;

    public void setBufferOffset(final int offset) {
        bufferOffset = offset;
    }

    public Renderable renderableOne;
    public Renderable renderableTwo;
    public int uid;
    public int anInt273;
    public int anInt274;
    public int anInt275;
    public int orientation;
    public int orientation1;
    public byte aByte281;

    public WallObject() {
    }
}
