package com.runescape.media;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.Client;
import com.runescape.gpu.GpuPlugin;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.util.Hashtable;

public final class ProducingGraphicsBuffer {

    public final int[] pixels;
    private final int width;
    private final int height;
    private final Component component;
    private final Image image;

    public int[] getPixels() {
        return pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ProducingGraphicsBuffer(int w, int h, Component component) {
        width = w;
        height = h;
        pixels = new int[w * h + 1];
        DataBufferInt data = new DataBufferInt(pixels, pixels.length);
        DirectColorModel model;
        final boolean gpu = GpuPlugin.process();
        if (gpu) {
            model = new DirectColorModel(ColorSpace.getInstance(1000), 32, 16711680, 65280, 255, -16777216, true, 3);
        } else {
            model = new DirectColorModel(32, 16711680, 65280, 255);
        }
        WritableRaster raster = java.awt.image.Raster.createWritableRaster(model.createCompatibleSampleModel(width, height), data, null);
        image = new BufferedImage(model, raster, gpu, new Hashtable());
        this.component = component;
        init();
    }

    public void init() {
        Raster.initDrawingArea(height, width, pixels);
    }

    public void draw(Graphics g, int k, int i) {
        if (GpuPlugin.process()) {
            return;
        }
        g.drawImage(image, k, i, component);
    }
}
