package com.runescape.media.renderable;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.cache.def.ItemDef;

public final class Item extends Renderable {

    public int ID;
    public int x;
    public int y;
    public int anInt1559;
    public Item() {
    }

    public final Model getRotatedModel() {
        ItemDef itemDef = ItemDef.forID(ID);
        return itemDef.method201(anInt1559);
    }
}
