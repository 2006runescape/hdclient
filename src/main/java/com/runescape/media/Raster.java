package com.runescape.media;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.collection.NodeSub;
import com.runescape.gpu.GpuPlugin;

public class Raster extends NodeSub {

    protected static void drawAlpha(int[] pixels, int index, int value, int color, int alpha) {
        if (!GpuPlugin.process()) {
            pixels[index] = value;
            return;
        }

        if (alpha == 0) {
            return;
        }

        int prevColor = pixels[index];

        if ((prevColor & 0xFF000000) == 0 || alpha == 255) {
            // No transparency, so we can cheat to save CPU resources
            pixels[index] = (color & 0xFFFFFF) | (alpha << 24);
            return;
        }

        if ((prevColor & 0xFF000000) == 0xFF000000) {
            // When the background is opaque, the result will also be opaque,
            // so we can simply use the value calculated by Jagex.
            pixels[index] = value | 0xFF000000;
            return;
        }

        int prevAlpha = (prevColor >>> 24) * (255 - alpha) >>> 8;
        int finalAlpha = alpha + prevAlpha;

        // Scale alphas so (relativeAlpha >>> 8) is approximately equal to (alpha /
        // finalAlpha).
        // Avoiding extra divisions increase performance by quite a bit.
        // And with divisions we get a problems if dividing a number where
        // the last bit is 1 (as it will become negative).
        int relativeAlpha1 = (alpha << 8) / finalAlpha;
        int relativeAlpha2 = (prevAlpha << 8) / finalAlpha;

        // Red and blue are calculated at the same time to save CPU cycles
        int finalColor = (((color & 0xFF00FF) * relativeAlpha1 + (prevColor & 0xFF00FF) * relativeAlpha2 & 0xFF00FF00) | ((color & 0x00FF00) * relativeAlpha1 + (prevColor & 0x00FF00) * relativeAlpha2 & 0x00FF0000)) >>> 8;

        pixels[index] = finalColor | (finalAlpha << 24);
    }

    public static int pixels[];
    public static int width;
    public static int height;
    public static int topY;
    public static int bottomY;
    public static int topX;
    public static int bottomX;
    public static int centerX;
    public static int centerY;
    public static int anInt1387;

    public Raster() {
    }

    public static void initDrawingArea(int i, int j, int ai[]) {
        pixels = ai;
        width = j;
        height = i;
        setDrawingArea(i, 0, j, 0);
    }

    public static void defaultDrawingAreaSize() {
        topX = 0;
        topY = 0;
        bottomX = width;
        bottomY = height;
        centerX = bottomX - 1;
        centerY = bottomX / 2;
    }

    public static void setDrawingArea(int i, int j, int k, int l) {
        if (j < 0)
            j = 0;
        if (l < 0)
            l = 0;
        if (k > width)
            k = width;
        if (i > height)
            i = height;
        topX = j;
        topY = l;
        bottomX = k;
        bottomY = i;
        centerX = bottomX - 1;
        centerY = bottomX / 2;
        anInt1387 = bottomY / 2;
    }

    public static void setAllPixelsToZero() {
        int i = width * height;
        for (int j = 0; j < i; j++)
            pixels[j] = 0;

    }

    public static void method335(int i, int j, int k, int l, int i1, int k1) {
        if (k1 < topX) {
            k -= topX - k1;
            k1 = topX;
        }
        if (j < topY) {
            l -= topY - j;
            j = topY;
        }
        if (k1 + k > bottomX)
            k = bottomX - k1;
        if (j + l > bottomY)
            l = bottomY - j;
        int l1 = 256 - i1;
        int i2 = (i >> 16 & 0xff) * i1;
        int j2 = (i >> 8 & 0xff) * i1;
        int k2 = (i & 0xff) * i1;
        int k3 = width - k;
        int l3 = k1 + j * width;
        for (int i4 = 0; i4 < l; i4++) {
            for (int j4 = -k; j4 < 0; j4++) {
                int l2 = (pixels[l3] >> 16 & 0xff) * l1;
                int i3 = (pixels[l3] >> 8 & 0xff) * l1;
                int j3 = (pixels[l3] & 0xff) * l1;
                int k4 = ((i2 + l2 >> 8) << 16) + ((j2 + i3 >> 8) << 8) + (k2 + j3 >> 8);
                //pixels[l3++] = k4;
                drawAlpha(pixels, l3++, k4, k4, i1);
            }

            l3 += k3;
        }
    }

    public static void method336(int i, int j, int k, int l, int i1, boolean itemSprite) {
        if (k < topX) {
            i1 -= topX - k;
            k = topX;
        }
        if (j < topY) {
            i -= topY - j;
            j = topY;
        }
        if (k + i1 > bottomX)
            i1 = bottomX - k;
        if (j + i > bottomY)
            i = bottomY - j;
        int k1 = width - i1;
        int l1 = k + j * width;
        for (int i2 = -i; i2 < 0; i2++) {
            for (int j2 = -i1; j2 < 0; j2++)
               // pixels[l1++] = l;
                drawAlpha(pixels, l1++, l, l, itemSprite ? 0 : 255);

            l1 += k1;
        }

    }

    public static void fillPixels(int i, int j, int k, int l, int i1) {
        method339(i1, l, j, i);
        method339((i1 + k) - 1, l, j, i);
        method341(i1, l, k, i);
        method341(i1, l, k, (i + j) - 1);
    }

    public static void method338(int i, int j, int k, int l, int i1, int j1) {
        method340(l, i1, i, k, j1);
        method340(l, i1, (i + j) - 1, k, j1);
        if (j >= 3) {
            method342(l, j1, k, i + 1, j - 2);
            method342(l, (j1 + i1) - 1, k, i + 1, j - 2);
        }
    }

    public static void method339(int i, int j, int k, int l) {
        if (i < topY || i >= bottomY)
            return;
        if (l < topX) {
            k -= topX - l;
            l = topX;
        }
        if (l + k > bottomX)
            k = bottomX - l;
        int i1 = l + i * width;
        for (int j1 = 0; j1 < k; j1++)
            //pixels[i1 + j1] = j;
            drawAlpha(pixels, i1 + j1, j, j, 255);

    }

    private static void method340(int i, int j, int k, int l, int i1) {
        if (k < topY || k >= bottomY)
            return;
        if (i1 < topX) {
            j -= topX - i1;
            i1 = topX;
        }
        if (i1 + j > bottomX)
            j = bottomX - i1;
        int j1 = 256 - l;
        int k1 = (i >> 16 & 0xff) * l;
        int l1 = (i >> 8 & 0xff) * l;
        int i2 = (i & 0xff) * l;
        int i3 = i1 + k * width;
        for (int j3 = 0; j3 < j; j3++) {
            int j2 = (pixels[i3] >> 16 & 0xff) * j1;
            int k2 = (pixels[i3] >> 8 & 0xff) * j1;
            int l2 = (pixels[i3] & 0xff) * j1;
            int k3 = ((k1 + j2 >> 8) << 16) + ((l1 + k2 >> 8) << 8) + (i2 + l2 >> 8);
            //pixels[i3++] = k3;
            drawAlpha(pixels, i3++, k3, k3, l);
        }

    }

    public static void method341(int i, int j, int k, int l) {
        if (l < topX || l >= bottomX)
            return;
        if (i < topY) {
            k -= topY - i;
            i = topY;
        }
        if (i + k > bottomY)
            k = bottomY - i;
        int j1 = l + i * width;
        for (int k1 = 0; k1 < k; k1++)
           // pixels[j1 + k1 * width] = j;
             drawAlpha(pixels, j1 + k1 * width, j, j, 255);

    }

    private static void method342(int i, int j, int k, int l, int i1) {
        if (j < topX || j >= bottomX)
            return;
        if (l < topY) {
            i1 -= topY - l;
            l = topY;
        }
        if (l + i1 > bottomY)
            i1 = bottomY - l;
        int j1 = 256 - k;
        int k1 = (i >> 16 & 0xff) * k;
        int l1 = (i >> 8 & 0xff) * k;
        int i2 = (i & 0xff) * k;
        int i3 = j + l * width;
        for (int j3 = 0; j3 < i1; j3++) {
            int j2 = (pixels[i3] >> 16 & 0xff) * j1;
            int k2 = (pixels[i3] >> 8 & 0xff) * j1;
            int l2 = (pixels[i3] & 0xff) * j1;
            int k3 = ((k1 + j2 >> 8) << 16) + ((l1 + k2 >> 8) << 8) + (i2 + l2 >> 8);
           // pixels[i3] = k3;
          //  i3 += width;
            drawAlpha(pixels, i3 += width, k3, k3, k);
        }

    }

}
