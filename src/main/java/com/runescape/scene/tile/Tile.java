package com.runescape.scene.tile;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 


import com.runescape.collection.Node;

public final class Tile extends Node {

    public Tile getBridge() {
        return bridge;
    }

    public SceneTilePaint getSceneTilePaint() {
        return underlay;
    }

    public SceneTileModel getSceneTileModel() {
        return overlay;
    }

    public WallObject getWallObject() {
        return wallObject;
    }

    public DecorativeObject getDecorativeObject() {
        return wallDecoration;
    }

    public GroundObject getGroundObject() {
        return groundObject;
    }

    public GroundItem getGroundItem() {
        return groundItem;
    }

    public GameObject[] getGameObjects() {
        return objects;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRenderLevel() {
        return renderLevel;
    }

    public final GameObject[] objects;
    public final int x;
    public final int y;
    public final int renderLevel;
    public final int[] entityFlags;
    public SceneTilePaint underlay;
    public SceneTileModel overlay;
    public WallObject wallObject;
    public DecorativeObject wallDecoration;
    public GroundObject groundObject;
    public GroundItem groundItem;
    public Tile bridge;
    public int plane;
    public int entityCount;
    public int flags;
    public int physicalLevel;
    public boolean draw;
    public boolean visible;
    public boolean drawEntities;
    public int wallCullDirection;
    public int wallUncullDirection;
    public int wallCullOppositeDirection;
    public int wallDrawFlags;

    public Tile(int i, int j, int k) {
        objects = new GameObject[5];
        entityFlags = new int[5];
        renderLevel = plane = i;
        x = j;
        y = k;
    }
}
