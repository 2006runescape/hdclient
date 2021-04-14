package com.runescape.scene;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.Client;
import com.runescape.collection.NodeList;
import com.runescape.gpu.GpuPlugin;
import com.runescape.media.renderable.Model;
import com.runescape.media.Raster;
import com.runescape.media.Rasterizer3D;
import com.runescape.media.VertexNormal;
import com.runescape.media.renderable.Renderable;
import com.runescape.scene.tile.*;

public final class Scene {

    private static int drawDistance = 25;

    public static int fogDepth = 1;
    public static boolean smoothBanding = true;

    public Tile[][][] getTiles() {
        return tiles;
    }

    public static int getDrawDistance() {
        return drawDistance;
    }

    public static void setDrawDistance(final int distance) {
        drawDistance = distance;
    }

    private static final int[] anIntArray463 = {
            53, -53, -53, 53
    };
    private static final int[] anIntArray464 = {
            -53, -53, 53, 53
    };
    private static final int[] anIntArray465 = {
            -45, 45, 45, -45
    };
    private static final int[] anIntArray466 = {
            45, 45, -45, -45
    };
    private static final int anInt472;
    private static final SceneCluster[] aClass47Array476 = new SceneCluster[500];
    private static final int[] anIntArray478 = {
            19, 55, 38, 155, 255, 110, 137, 205, 76
    };
    private static final int[] anIntArray479 = {
            160, 192, 80, 96, 0, 144, 80, 48, 160
    };
    private static final int[] anIntArray480 = {
            76, 8, 137, 4, 0, 1, 38, 2, 19
    };
    private static final int[] anIntArray481 = {
            0, 0, 2, 0, 0, 2, 1, 1, 0
    };
    private static final int[] anIntArray482 = {
            2, 0, 0, 2, 0, 0, 0, 4, 4
    };
    private static final int[] anIntArray483 = {
            0, 4, 4, 8, 0, 0, 8, 0, 0
    };
    private static final int[] anIntArray484 = {
            1, 1, 0, 0, 0, 8, 0, 0, 8
    };
    private static final int[] anIntArray485 = {
            41, 39248, 41, 4643, 41, 41, 41, 41, 41, 41,
            41, 41, 41, 41, 41, 43086, 41, 41, 41, 41,
            41, 41, 41, 8602, 41, 28992, 41, 41, 41, 41,
            41, 5056, 41, 41, 41, 7079, 41, 41, 41, 41,
            41, 41, 41, 41, 41, 41, 3131, 41, 41, 41
    };
    public static boolean lowMem = true;
    public static int anInt470 = -1;
    public static int anInt471 = -1;
    private static int tileUpdateCount;
    private static int screenPlane;
    private static int cycle;
    private static int minTileX;
    private static int maxTileX;
    private static int minTileZ;
    private static int maxTileZ;
    private static int screenCenterX;
    private static int screenCenterZ;
    public static int cameraX2;
    public static int cameraY2;
    public static int cameraZ2;
    private static int pitchSin;
    private static int pitchCos;
    private static int yawSin;
    private static int yawCos;
    private static GameObject[] aClass28Array462 = new GameObject[100];
    private static boolean checkClick;
    private static int anInt468;
    private static int anInt469;
    private static int[] anIntArray473;
    private static SceneCluster[][] aClass47ArrayArray474;
    private static int anInt475;
    private static NodeList aClass19_477 = new NodeList();
    private static boolean[][][][] visibilityMaps = new boolean[8][32][51][51];
    private static boolean[][] renderArea;
    private static int centerX;
    private static int centerY;
    private static int anInt495;
    private static int anInt496;
    private static int width;
    private static int height;

    static {
        anInt472 = 4;
        anIntArray473 = new int[anInt472];
        aClass47ArrayArray474 = new SceneCluster[anInt472][500];
    }

    private final int maxY;
    private final int maxX;
    private final int maxZ;
    private final int[][][] tileHeights;
    private final Tile[][][] tiles;
    private final GameObject[] obj5Cache;
    private final int[][][] anIntArrayArrayArray445;
    private final int[] anIntArray486;
    private final int[] anIntArray487;
    private final int[][] anIntArrayArray489 = {
            new int[16], {
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1
    }, {
            1, 0, 0, 0, 1, 1, 0, 0, 1, 1,
            1, 0, 1, 1, 1, 1
    }, {
            1, 1, 0, 0, 1, 1, 0, 0, 1, 0,
            0, 0, 1, 0, 0, 0
    }, {
            0, 0, 1, 1, 0, 0, 1, 1, 0, 0,
            0, 1, 0, 0, 0, 1
    }, {
            0, 1, 1, 1, 0, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1
    }, {
            1, 1, 1, 0, 1, 1, 1, 0, 1, 1,
            1, 1, 1, 1, 1, 1
    }, {
            1, 1, 0, 0, 1, 1, 0, 0, 1, 1,
            0, 0, 1, 1, 0, 0
    }, {
            0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
            0, 0, 1, 1, 0, 0
    }, {
            1, 1, 1, 1, 1, 1, 1, 1, 0, 1,
            1, 1, 0, 0, 1, 1
    },
            {
                    1, 1, 1, 1, 1, 1, 0, 0, 1, 0,
                    0, 0, 1, 0, 0, 0
            }, {
            0, 0, 0, 0, 0, 0, 1, 1, 0, 1,
            1, 1, 0, 1, 1, 1
    }, {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 1, 1, 1, 1
    }
    };
    private final int[][] anIntArrayArray490 = {
            {
                    0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                    10, 11, 12, 13, 14, 15
            }, {
            12, 8, 4, 0, 13, 9, 5, 1, 14, 10,
            6, 2, 15, 11, 7, 3
    }, {
            15, 14, 13, 12, 11, 10, 9, 8, 7, 6,
            5, 4, 3, 2, 1, 0
    }, {
            3, 7, 11, 15, 2, 6, 10, 14, 1, 5,
            9, 13, 0, 4, 8, 12
    }
    };
    private boolean aBoolean434;
    private int minLevel;
    private int obj5CacheCurrPos;
    private int anInt488;
    public Scene(int ai[][][]) {
        int i = 104;//was parameter
        int j = 104;//was parameter
        int k = 4;//was parameter
        aBoolean434 = true;
        obj5Cache = new GameObject[5000];
        anIntArray486 = new int[10000];
        anIntArray487 = new int[10000];
        maxY = k;
        maxX = j;
        maxZ = i;
        tiles = new Tile[k][j][i];
        anIntArrayArrayArray445 = new int[k][j + 1][i + 1];
        tileHeights = ai;
        initToNull();
    }

    public static void nullLoader() {
        aClass28Array462 = null;
        anIntArray473 = null;
        aClass47ArrayArray474 = null;
        aClass19_477 = null;
        visibilityMaps = null;
        renderArea = null;
    }

    public static void method277(int i, int j, int k, int l, int i1, int j1, int l1,
                                 int i2) {
        SceneCluster class47 = new SceneCluster();
        class47.anInt787 = j / 128;
        class47.anInt788 = l / 128;
        class47.anInt789 = l1 / 128;
        class47.anInt790 = i1 / 128;
        class47.anInt791 = i2;
        class47.anInt792 = j;
        class47.anInt793 = l;
        class47.anInt794 = l1;
        class47.anInt795 = i1;
        class47.anInt796 = j1;
        class47.anInt797 = k;
        aClass47ArrayArray474[i][anIntArray473[i]++] = class47;
    }

    public static void method310(int i, int j, int k, int l, int ai[]) {
        anInt495 = 0;
        anInt496 = 0;
        width = k;
        height = l;
        centerX = k / 2;
        centerY = l / 2;
        boolean aflag[][][][] = new boolean[9][32][53][53];
        for (int i1 = 128; i1 <= 384; i1 += 32) {
            for (int j1 = 0; j1 < 2048; j1 += 64) {
                pitchSin = Model.sine[i1];
                pitchCos = Model.cosine[i1];
                yawSin = Model.sine[j1];
                yawCos = Model.cosine[j1];
                int l1 = (i1 - 128) / 32;
                int j2 = j1 / 64;
                for (int l2 = -26; l2 <= 26; l2++) {
                    for (int j3 = -26; j3 <= 26; j3++) {
                        int k3 = l2 * 128;
                        int i4 = j3 * 128;
                        boolean flag2 = false;
                        for (int k4 = -i; k4 <= j; k4 += 128) {
                            if (!method311(ai[l1] + k4, i4, k3))
                                continue;
                            flag2 = true;
                            break;
                        }

                        aflag[l1][j2][l2 + 25 + 1][j3 + 25 + 1] = flag2;
                    }

                }

            }

        }

        for (int k1 = 0; k1 < 8; k1++) {
            for (int i2 = 0; i2 < 32; i2++) {
                for (int k2 = -25; k2 < 25; k2++) {
                    for (int i3 = -25; i3 < 25; i3++) {
                        boolean flag1 = false;
                        label0:
                        for (int l3 = -1; l3 <= 1; l3++) {
                            for (int j4 = -1; j4 <= 1; j4++) {
                                if (aflag[k1][i2][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1])
                                    flag1 = true;
                                else if (aflag[k1][(i2 + 1) % 31][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1])
                                    flag1 = true;
                                else if (aflag[k1 + 1][i2][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1]) {
                                    flag1 = true;
                                } else {
                                    if (!aflag[k1 + 1][(i2 + 1) % 31][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1])
                                        continue;
                                    flag1 = true;
                                }
                                break label0;
                            }

                        }

                        visibilityMaps[k1][i2][k2 + 25][i3 + 25] = flag1;
                    }

                }

            }

        }

    }

    private static boolean method311(int i, int j, int k) {
        int l = j * yawSin + k * yawCos >> 16;
        int i1 = j * yawCos - k * yawSin >> 16;
        int j1 = i * pitchSin + i1 * pitchCos >> 16;
        int k1 = i * pitchCos - i1 * pitchSin >> 16;
        if (j1 < 50 || (j1 >= 3500 && !GpuPlugin.process()))
            return false;
        int l1 = centerX + (l << 9) / j1;
        int i2 = centerY + (k1 << 9) / j1;
        return l1 >= anInt495 && l1 <= width && i2 >= anInt496 && i2 <= height;
    }

    public void initToNull() {
        for (int j = 0; j < maxY; j++) {
            for (int k = 0; k < maxX; k++) {
                for (int i1 = 0; i1 < maxZ; i1++)
                    tiles[j][k][i1] = null;

            }

        }
        for (int l = 0; l < anInt472; l++) {
            for (int j1 = 0; j1 < anIntArray473[l]; j1++)
                aClass47ArrayArray474[l][j1] = null;

            anIntArray473[l] = 0;
        }

        for (int k1 = 0; k1 < obj5CacheCurrPos; k1++)
            obj5Cache[k1] = null;

        obj5CacheCurrPos = 0;
        for (int l1 = 0; l1 < aClass28Array462.length; l1++)
            aClass28Array462[l1] = null;

    }

    public void method275(int i) {
        minLevel = i;
        for (int k = 0; k < maxX; k++) {
            for (int l = 0; l < maxZ; l++)
                if (tiles[i][k][l] == null)
                    tiles[i][k][l] = new Tile(i, k, l);

        }

    }

    public void method276(int i, int j) {
        Tile class30_sub3 = tiles[0][j][i];
        for (int l = 0; l < 3; l++) {
            Tile class30_sub3_1 = tiles[l][j][i] = tiles[l + 1][j][i];
            if (class30_sub3_1 != null) {
                class30_sub3_1.plane--;
                for (int j1 = 0; j1 < class30_sub3_1.entityCount; j1++) {
                    GameObject class28 = class30_sub3_1.objects[j1];
                    if ((class28.uid >> 29 & 3) == 2 && class28.anInt523 == j && class28.anInt525 == i)
                        class28.anInt517--;
                }

            }
        }
        if (tiles[0][j][i] == null)
            tiles[0][j][i] = new Tile(0, j, i);
        tiles[0][j][i].bridge = class30_sub3;
        tiles[3][j][i] = null;
    }

    public void method278(int i, int j, int k, int l) {
        Tile class30_sub3 = tiles[i][j][k];
        if (class30_sub3 != null) {
            tiles[i][j][k].physicalLevel = l;
        }
    }

    public void method279(int i, int j, int k, int l, int i1, int j1, int k1,
                          int l1, int i2, int j2, int k2, int l2, int i3, int j3,
                          int k3, int l3, int i4, int j4, int k4, int l4) {
        if (l == 0) {
            SceneTilePaint class43 = new SceneTilePaint(k2, l2, i3, j3, -1, k4, false);
            for (int i5 = i; i5 >= 0; i5--)
                if (tiles[i5][j][k] == null)
                    tiles[i5][j][k] = new Tile(i5, j, k);

            tiles[i][j][k].underlay = class43;
            return;
        }
        if (l == 1) {
            SceneTilePaint class43_1 = new SceneTilePaint(k3, l3, i4, j4, j1, l4, k1 == l1 && k1 == i2 && k1 == j2);
            for (int j5 = i; j5 >= 0; j5--)
                if (tiles[j5][j][k] == null)
                    tiles[j5][j][k] = new Tile(j5, j, k);

            tiles[i][j][k].underlay = class43_1;
            return;
        }
        SceneTileModel class40 = new SceneTileModel(k, k3, j3, i2, j1, i4, i1, k2, k4, i3, j2, l1, k1, l, j4, l3, l2, j, l4);
        for (int k5 = i; k5 >= 0; k5--)
            if (tiles[k5][j][k] == null)
                tiles[k5][j][k] = new Tile(k5, j, k);

        tiles[i][j][k].overlay = class40;
    }

    public void method280(int i, int j, int k, Renderable class30_sub2_sub4, byte byte0, int i1,
                          int j1) {
        if (class30_sub2_sub4 == null)
            return;
        GroundObject class49 = new GroundObject();
        class49.renderable = class30_sub2_sub4;
        class49.anInt812 = j1 * 128 + 64;
        class49.anInt813 = k * 128 + 64;
        class49.anInt811 = j;
        class49.uid = i1;
        class49.aByte816 = byte0;
        if (tiles[i][j1][k] == null)
            tiles[i][j1][k] = new Tile(i, j1, k);
        tiles[i][j1][k].groundObject = class49;
    }

    public void method281(int i, int j, Renderable class30_sub2_sub4, int k, Renderable class30_sub2_sub4_1, Renderable class30_sub2_sub4_2,
                          int l, int i1) {
        GroundItem object4 = new GroundItem();
        object4.first = class30_sub2_sub4_2;
        object4.anInt46 = i * 128 + 64;
        object4.anInt47 = i1 * 128 + 64;
        object4.anInt45 = k;
        object4.uid = j;
        object4.second = class30_sub2_sub4;
        object4.third = class30_sub2_sub4_1;
        int j1 = 0;
        Tile class30_sub3 = tiles[l][i][i1];
        if (class30_sub3 != null) {
            for (int k1 = 0; k1 < class30_sub3.entityCount; k1++)
                if (class30_sub3.objects[k1].renderable instanceof Model) {
                    int l1 = ((Model) class30_sub3.objects[k1].renderable).anInt1654;
                    if (l1 > j1)
                        j1 = l1;
                }

        }
        object4.anInt52 = j1;
        if (tiles[l][i][i1] == null)
            tiles[l][i][i1] = new Tile(l, i, i1);
        tiles[l][i][i1].groundItem = object4;
    }

    public void method282(int i, Renderable class30_sub2_sub4, int j, int k, byte byte0, int l,
                          Renderable class30_sub2_sub4_1, int i1, int j1, int k1) {
        if (class30_sub2_sub4 == null && class30_sub2_sub4_1 == null)
            return;
        WallObject object1 = new WallObject();
        object1.uid = j;
        object1.aByte281 = byte0;
        object1.anInt274 = l * 128 + 64;
        object1.anInt275 = k * 128 + 64;
        object1.anInt273 = i1;
        object1.renderableOne = class30_sub2_sub4;
        object1.renderableTwo = class30_sub2_sub4_1;
        object1.orientation = i;
        object1.orientation1 = j1;
        for (int l1 = k1; l1 >= 0; l1--)
            if (tiles[l1][l][k] == null)
                tiles[l1][l][k] = new Tile(l1, l, k);

        tiles[k1][l][k].wallObject = object1;
    }

    public void method283(int i, int j, int k, int i1, int j1, int k1,
                          Renderable class30_sub2_sub4, int l1, byte byte0, int i2, int j2) {
        if (class30_sub2_sub4 == null)
            return;
        DecorativeObject class26 = new DecorativeObject();
        class26.uid = i;
        class26.aByte506 = byte0;
        class26.anInt500 = l1 * 128 + 64 + j1;
        class26.anInt501 = j * 128 + 64 + i2;
        class26.anInt499 = k1;
        class26.renderable = class30_sub2_sub4;
        class26.anInt502 = j2;
        class26.anInt503 = k;
        for (int k2 = i1; k2 >= 0; k2--)
            if (tiles[k2][l1][j] == null)
                tiles[k2][l1][j] = new Tile(k2, l1, j);

        tiles[i1][l1][j].wallDecoration = class26;
    }

    public boolean method284(int i, byte byte0, int j, int k, Renderable class30_sub2_sub4, int l, int i1,
                             int j1, int k1, int l1) {
        if (class30_sub2_sub4 == null) {
            return true;
        } else {
            int i2 = l1 * 128 + 64 * l;
            int j2 = k1 * 128 + 64 * k;
            return method287(i1, l1, k1, l, k, i2, j2, j, class30_sub2_sub4, j1, false, i, byte0);
        }
    }

    public boolean method285(int i, int j, int k, int l, int i1, int j1,
                             int k1, Renderable class30_sub2_sub4, boolean flag) {
        if (class30_sub2_sub4 == null)
            return true;
        int l1 = k1 - j1;
        int i2 = i1 - j1;
        int j2 = k1 + j1;
        int k2 = i1 + j1;
        if (flag) {
            if (j > 640 && j < 1408)
                k2 += 128;
            if (j > 1152 && j < 1920)
                j2 += 128;
            if (j > 1664 || j < 384)
                i2 -= 128;
            if (j > 128 && j < 896)
                l1 -= 128;
        }
        l1 /= 128;
        i2 /= 128;
        j2 /= 128;
        k2 /= 128;
        return method287(i, l1, i2, (j2 - l1) + 1, (k2 - i2) + 1, k1, i1, k, class30_sub2_sub4, j, true, l, (byte) 0);
    }

    public boolean method286(int j, int k, Renderable class30_sub2_sub4, int l, int i1, int j1,
                             int k1, int l1, int i2, int j2, int k2) {
        return class30_sub2_sub4 == null || method287(j, l1, k2, (i2 - l1) + 1, (i1 - k2) + 1, j1, k, k1, class30_sub2_sub4, l, true, j2, (byte) 0);
    }

    private boolean method287(int i, int j, int k, int l, int i1, int j1, int k1,
                              int l1, Renderable class30_sub2_sub4, int i2, boolean flag, int j2, byte byte0) {
        for (int k2 = j; k2 < j + l; k2++) {
            for (int l2 = k; l2 < k + i1; l2++) {
                if (k2 < 0 || l2 < 0 || k2 >= maxX || l2 >= maxZ)
                    return false;
                Tile class30_sub3 = tiles[i][k2][l2];
                if (class30_sub3 != null && class30_sub3.entityCount >= 5)
                    return false;
            }

        }

        GameObject class28 = new GameObject();
        class28.uid = j2;
        class28.aByte530 = byte0;
        class28.anInt517 = i;
        class28.anInt519 = j1;
        class28.anInt520 = k1;
        class28.anInt518 = l1;
        class28.renderable = class30_sub2_sub4;
        class28.anInt522 = i2;
        class28.anInt523 = j;
        class28.anInt525 = k;
        class28.anInt524 = (j + l) - 1;
        class28.anInt526 = (k + i1) - 1;
        for (int i3 = j; i3 < j + l; i3++) {
            for (int j3 = k; j3 < k + i1; j3++) {
                int k3 = 0;
                if (i3 > j)
                    k3++;
                if (i3 < (j + l) - 1)
                    k3 += 4;
                if (j3 > k)
                    k3 += 8;
                if (j3 < (k + i1) - 1)
                    k3 += 2;
                for (int l3 = i; l3 >= 0; l3--)
                    if (tiles[l3][i3][j3] == null)
                        tiles[l3][i3][j3] = new Tile(l3, i3, j3);

                Tile class30_sub3_1 = tiles[i][i3][j3];
                class30_sub3_1.objects[class30_sub3_1.entityCount] = class28;
                class30_sub3_1.entityFlags[class30_sub3_1.entityCount] = k3;
                class30_sub3_1.flags |= k3;
                class30_sub3_1.entityCount++;
            }

        }

        if (flag)
            obj5Cache[obj5CacheCurrPos++] = class28;
        return true;
    }

    public void clearObj5Cache() {
        for (int i = 0; i < obj5CacheCurrPos; i++) {
            GameObject object5 = obj5Cache[i];
            method289(object5);
            obj5Cache[i] = null;
        }

        obj5CacheCurrPos = 0;
    }

    private void method289(GameObject class28) {
        for (int j = class28.anInt523; j <= class28.anInt524; j++) {
            for (int k = class28.anInt525; k <= class28.anInt526; k++) {
                Tile class30_sub3 = tiles[class28.anInt517][j][k];
                if (class30_sub3 != null) {
                    for (int l = 0; l < class30_sub3.entityCount; l++) {
                        if (class30_sub3.objects[l] != class28)
                            continue;
                        class30_sub3.entityCount--;
                        for (int i1 = l; i1 < class30_sub3.entityCount; i1++) {
                            class30_sub3.objects[i1] = class30_sub3.objects[i1 + 1];
                            class30_sub3.entityFlags[i1] = class30_sub3.entityFlags[i1 + 1];
                        }

                        class30_sub3.objects[class30_sub3.entityCount] = null;
                        break;
                    }

                    class30_sub3.flags = 0;
                    for (int j1 = 0; j1 < class30_sub3.entityCount; j1++)
                        class30_sub3.flags |= class30_sub3.entityFlags[j1];

                }
            }

        }

    }

    public void method290(int i, int k, int l, int i1) {
        Tile class30_sub3 = tiles[i1][l][i];
        if (class30_sub3 == null)
            return;
        DecorativeObject class26 = class30_sub3.wallDecoration;
        if (class26 != null) {
            int j1 = l * 128 + 64;
            int k1 = i * 128 + 64;
            class26.anInt500 = j1 + ((class26.anInt500 - j1) * k) / 16;
            class26.anInt501 = k1 + ((class26.anInt501 - k1) * k) / 16;
        }
    }

    public void method291(int i, int j, int k, byte byte0) {
        Tile class30_sub3 = tiles[j][i][k];
        if (byte0 != -119)
            aBoolean434 = !aBoolean434;
        if (class30_sub3 != null) {
            class30_sub3.wallObject = null;
        }
    }

    public void method292(int j, int k, int l) {
        Tile class30_sub3 = tiles[k][l][j];
        if (class30_sub3 != null) {
            class30_sub3.wallDecoration = null;
        }
    }

    public void method293(int i, int k, int l) {
        Tile class30_sub3 = tiles[i][k][l];
        if (class30_sub3 == null)
            return;
        for (int j1 = 0; j1 < class30_sub3.entityCount; j1++) {
            GameObject class28 = class30_sub3.objects[j1];
            if ((class28.uid >> 29 & 3) == 2 && class28.anInt523 == k && class28.anInt525 == l) {
                method289(class28);
                return;
            }
        }

    }

    public void method294(int i, int j, int k) {
        Tile class30_sub3 = tiles[i][k][j];
        if (class30_sub3 == null)
            return;
        class30_sub3.groundObject = null;
    }

    public void method295(int i, int j, int k) {
        Tile class30_sub3 = tiles[i][j][k];
        if (class30_sub3 != null) {
            class30_sub3.groundItem = null;
        }
    }

    public WallObject method296(int i, int j, int k) {
        Tile class30_sub3 = tiles[i][j][k];
        if (class30_sub3 == null)
            return null;
        else
            return class30_sub3.wallObject;
    }

    public DecorativeObject method297(int i, int k, int l) {
        Tile class30_sub3 = tiles[l][i][k];
        if (class30_sub3 == null)
            return null;
        else
            return class30_sub3.wallDecoration;
    }

    public GameObject method298(int i, int j, int k) {
        Tile class30_sub3 = tiles[k][i][j];
        if (class30_sub3 == null)
            return null;
        for (int l = 0; l < class30_sub3.entityCount; l++) {
            GameObject class28 = class30_sub3.objects[l];
            if ((class28.uid >> 29 & 3) == 2 && class28.anInt523 == i && class28.anInt525 == j)
                return class28;
        }
        return null;
    }

    public GroundObject method299(int i, int j, int k) {
        Tile class30_sub3 = tiles[k][j][i];
        if (class30_sub3 == null || class30_sub3.groundObject == null)
            return null;
        else
            return class30_sub3.groundObject;
    }

    public int method300(int i, int j, int k) {
        Tile class30_sub3 = tiles[i][j][k];
        if (class30_sub3 == null || class30_sub3.wallObject == null)
            return 0;
        else
            return class30_sub3.wallObject.uid;
    }

    public int method301(int i, int j, int l) {
        Tile class30_sub3 = tiles[i][j][l];
        if (class30_sub3 == null || class30_sub3.wallDecoration == null)
            return 0;
        else
            return class30_sub3.wallDecoration.uid;
    }

    public int method302(int i, int j, int k) {
        Tile class30_sub3 = tiles[i][j][k];
        if (class30_sub3 == null)
            return 0;
        for (int l = 0; l < class30_sub3.entityCount; l++) {
            GameObject class28 = class30_sub3.objects[l];
            if ((class28.uid >> 29 & 3) == 2 && class28.anInt523 == j && class28.anInt525 == k)
                return class28.uid;
        }

        return 0;
    }

    public int method303(int i, int j, int k) {
        Tile class30_sub3 = tiles[i][j][k];
        if (class30_sub3 == null || class30_sub3.groundObject == null)
            return 0;
        else
            return class30_sub3.groundObject.uid;
    }

    public int method304(int i, int j, int k, int l) {
        Tile class30_sub3 = tiles[i][j][k];
        if (class30_sub3 == null)
            return -1;
        if (class30_sub3.wallObject != null && class30_sub3.wallObject.uid == l)
            return class30_sub3.wallObject.aByte281 & 0xff;
        if (class30_sub3.wallDecoration != null && class30_sub3.wallDecoration.uid == l)
            return class30_sub3.wallDecoration.aByte506 & 0xff;
        if (class30_sub3.groundObject != null && class30_sub3.groundObject.uid == l)
            return class30_sub3.groundObject.aByte816 & 0xff;
        for (int i1 = 0; i1 < class30_sub3.entityCount; i1++)
            if (class30_sub3.objects[i1].uid == l)
                return class30_sub3.objects[i1].aByte530 & 0xff;

        return -1;
    }

    public void method305(int i, int k, int i1) {
        int j = 64;//was parameter
        int l = 768;//was parameter
        int j1 = (int) Math.sqrt(k * k + i * i + i1 * i1);
        int k1 = l * j1 >> 8;
        for (int l1 = 0; l1 < maxY; l1++) {
            for (int i2 = 0; i2 < maxX; i2++) {
                for (int j2 = 0; j2 < maxZ; j2++) {
                    Tile class30_sub3 = tiles[l1][i2][j2];
                    if (class30_sub3 != null) {
                        WallObject class10 = class30_sub3.wallObject;
                        if (class10 != null && class10.renderableOne != null && class10.renderableOne.vertexNormals != null) {
                            method307(l1, 1, 1, i2, j2, (Model) class10.renderableOne);
                            if (class10.renderableTwo != null && class10.renderableTwo.vertexNormals != null) {
                                method307(l1, 1, 1, i2, j2, (Model) class10.renderableTwo);
                                method308((Model) class10.renderableOne, (Model) class10.renderableTwo, 0, 0, 0, false);
                                ((Model) class10.renderableTwo).method480(j, k1, k, i, i1);
                            }
                            ((Model) class10.renderableOne).method480(j, k1, k, i, i1);
                        }
                        for (int k2 = 0; k2 < class30_sub3.entityCount; k2++) {
                            GameObject class28 = class30_sub3.objects[k2];
                            if (class28 != null && class28.renderable != null && class28.renderable.vertexNormals != null) {
                                method307(l1, (class28.anInt524 - class28.anInt523) + 1, (class28.anInt526 - class28.anInt525) + 1, i2, j2, (Model) class28.renderable);
                                ((Model) class28.renderable).method480(j, k1, k, i, i1);
                            }
                        }

                        GroundObject class49 = class30_sub3.groundObject;
                        if (class49 != null && class49.renderable.vertexNormals != null) {
                            method306(i2, l1, (Model) class49.renderable, j2);
                            ((Model) class49.renderable).method480(j, k1, k, i, i1);
                        }
                    }
                }

            }

        }

    }

    private void method306(int i, int j, Model model, int k) {
        if (i < maxX) {
            Tile class30_sub3 = tiles[j][i + 1][k];
            if (class30_sub3 != null && class30_sub3.groundObject != null && class30_sub3.groundObject.renderable.vertexNormals != null)
                method308(model, (Model) class30_sub3.groundObject.renderable, 128, 0, 0, true);
        }
        if (k < maxX) {
            Tile class30_sub3_1 = tiles[j][i][k + 1];
            if (class30_sub3_1 != null && class30_sub3_1.groundObject != null && class30_sub3_1.groundObject.renderable.vertexNormals != null)
                method308(model, (Model) class30_sub3_1.groundObject.renderable, 0, 0, 128, true);
        }
        if (i < maxX && k < maxZ) {
            Tile class30_sub3_2 = tiles[j][i + 1][k + 1];
            if (class30_sub3_2 != null && class30_sub3_2.groundObject != null && class30_sub3_2.groundObject.renderable.vertexNormals != null)
                method308(model, (Model) class30_sub3_2.groundObject.renderable, 128, 0, 128, true);
        }
        if (i < maxX && k > 0) {
            Tile class30_sub3_3 = tiles[j][i + 1][k - 1];
            if (class30_sub3_3 != null && class30_sub3_3.groundObject != null && class30_sub3_3.groundObject.renderable.vertexNormals != null)
                method308(model, (Model) class30_sub3_3.groundObject.renderable, 128, 0, -128, true);
        }
    }

    private void method307(int i, int j, int k, int l, int i1, Model model) {
        boolean flag = true;
        int j1 = l;
        int k1 = l + j;
        int l1 = i1 - 1;
        int i2 = i1 + k;
        for (int j2 = i; j2 <= i + 1; j2++)
            if (j2 != maxY) {
                for (int k2 = j1; k2 <= k1; k2++)
                    if (k2 >= 0 && k2 < maxX) {
                        for (int l2 = l1; l2 <= i2; l2++)
                            if (l2 >= 0 && l2 < maxZ && (!flag || k2 >= k1 || l2 >= i2 || l2 < i1 && k2 != l)) {
                                Tile class30_sub3 = tiles[j2][k2][l2];
                                if (class30_sub3 != null) {
                                    int i3 = (tileHeights[j2][k2][l2] + tileHeights[j2][k2 + 1][l2] + tileHeights[j2][k2][l2 + 1] + tileHeights[j2][k2 + 1][l2 + 1]) / 4 - (tileHeights[i][l][i1] + tileHeights[i][l + 1][i1] + tileHeights[i][l][i1 + 1] + tileHeights[i][l + 1][i1 + 1]) / 4;
                                    WallObject class10 = class30_sub3.wallObject;
                                    if (class10 != null && class10.renderableOne != null && class10.renderableOne.vertexNormals != null)
                                        method308(model, (Model) class10.renderableOne, (k2 - l) * 128 + (1 - j) * 64, i3, (l2 - i1) * 128 + (1 - k) * 64, flag);
                                    if (class10 != null && class10.renderableTwo != null && class10.renderableTwo.vertexNormals != null)
                                        method308(model, (Model) class10.renderableTwo, (k2 - l) * 128 + (1 - j) * 64, i3, (l2 - i1) * 128 + (1 - k) * 64, flag);
                                    for (int j3 = 0; j3 < class30_sub3.entityCount; j3++) {
                                        GameObject class28 = class30_sub3.objects[j3];
                                        if (class28 != null && class28.renderable != null && class28.renderable.vertexNormals != null) {
                                            int k3 = (class28.anInt524 - class28.anInt523) + 1;
                                            int l3 = (class28.anInt526 - class28.anInt525) + 1;
                                            method308(model, (Model) class28.renderable, (class28.anInt523 - l) * 128 + (k3 - j) * 64, i3, (class28.anInt525 - i1) * 128 + (l3 - k) * 64, flag);
                                        }
                                    }

                                }
                            }

                    }

                j1--;
                flag = false;
            }

    }

    private void method308(Model model, Model model_1, int i, int j, int k, boolean flag) {
        anInt488++;
        int l = 0;
        int ai[] = model_1.verticesX;
        int i1 = model_1.verticesCount;
        for (int j1 = 0; j1 < model.verticesCount; j1++) {
            VertexNormal class33 = model.vertexNormals[j1];
            VertexNormal class33_1 = model.vertexNormalsOffsets[j1];
            if (class33_1.magnitude != 0) {
                int i2 = model.verticesY[j1] - j;
                if (i2 <= model_1.bottomY) {
                    int j2 = model.verticesX[j1] - i;
                    if (j2 >= model_1.anInt1646 && j2 <= model_1.anInt1647) {
                        int k2 = model.verticesZ[j1] - k;
                        if (k2 >= model_1.anInt1649 && k2 <= model_1.anInt1648) {
                            for (int l2 = 0; l2 < i1; l2++) {
                                VertexNormal class33_2 = model_1.vertexNormals[l2];
                                VertexNormal class33_3 = model_1.vertexNormalsOffsets[l2];
                                if (j2 == ai[l2] && k2 == model_1.verticesZ[l2] && i2 == model_1.verticesY[l2] && class33_3.magnitude != 0) {
                                    class33.x += class33_3.x;
                                    class33.y += class33_3.y;
                                    class33.z += class33_3.z;
                                    class33.magnitude += class33_3.magnitude;
                                    class33_2.x += class33_1.x;
                                    class33_2.y += class33_1.y;
                                    class33_2.z += class33_1.z;
                                    class33_2.magnitude += class33_1.magnitude;
                                    l++;
                                    anIntArray486[j1] = anInt488;
                                    anIntArray487[l2] = anInt488;
                                }
                            }

                        }
                    }
                }
            }
        }

        if (l < 3 || !flag)
            return;
        for (int k1 = 0; k1 < model.trianglesCount; k1++)
            if (anIntArray486[model.trianglesX[k1]] == anInt488 && anIntArray486[model.trianglesY[k1]] == anInt488 && anIntArray486[model.trianglesZ[k1]] == anInt488)
                model.types[k1] = -1;

        for (int l1 = 0; l1 < model_1.trianglesCount; l1++)
            if (anIntArray487[model_1.trianglesX[l1]] == anInt488 && anIntArray487[model_1.trianglesY[l1]] == anInt488 && anIntArray487[model_1.trianglesZ[l1]] == anInt488)
                model_1.types[l1] = -1;

    }

    public void method309(int ai[], int i, int k, int l, int i1) {
        int j = 512;//was parameter
        Tile class30_sub3 = tiles[k][l][i1];
        if (class30_sub3 == null)
            return;
        SceneTilePaint class43 = class30_sub3.underlay;
        if (class43 != null) {
            int j1 = class43.rgb;
            if (j1 == 0)
                return;
            for (int k1 = 0; k1 < 4; k1++) {
                ai[i] = j1;
                ai[i + 1] = j1;
                ai[i + 2] = j1;
                ai[i + 3] = j1;
                i += j;
            }

            return;
        }
        SceneTileModel class40 = class30_sub3.overlay;
        if (class40 == null)
            return;
        int l1 = class40.shape;
        int i2 = class40.rotation;
        int j2 = class40.underlay;
        int k2 = class40.overlay;
        int ai1[] = anIntArrayArray489[l1];
        int ai2[] = anIntArrayArray490[i2];
        int l2 = 0;
        if (j2 != 0) {
            for (int i3 = 0; i3 < 4; i3++) {
                ai[i] = ai1[ai2[l2++]] != 0 ? k2 : j2;
                ai[i + 1] = ai1[ai2[l2++]] != 0 ? k2 : j2;
                ai[i + 2] = ai1[ai2[l2++]] != 0 ? k2 : j2;
                ai[i + 3] = ai1[ai2[l2++]] != 0 ? k2 : j2;
                i += j;
            }

            return;
        }
        for (int j3 = 0; j3 < 4; j3++) {
            if (ai1[ai2[l2++]] != 0)
                ai[i] = k2;
            if (ai1[ai2[l2++]] != 0)
                ai[i + 1] = k2;
            if (ai1[ai2[l2++]] != 0)
                ai[i + 2] = k2;
            if (ai1[ai2[l2++]] != 0)
                ai[i + 3] = k2;
            i += j;
        }

    }

    public void method312(int i, int j) {
        checkClick = true;
        anInt468 = j;
        anInt469 = i;
        anInt470 = -1;
        anInt471 = -1;
    }

    public void drawScene(int x2, int z2, int yaw, int y2, int plane, int pitch) {
        if (GpuPlugin.process()) {
            Client.instance.gpu.drawScene(x2, y2, z2, pitch, yaw, plane);
        }
        final int distance = GpuPlugin.process() ? 90 : 25;
        if (x2 < 0)
            x2 = 0;
        else if (x2 >= maxX * 128)
            x2 = maxX * 128 - 1;
        if (z2 < 0)
            z2 = 0;
        else if (z2 >= maxZ * 128)
            z2 = maxZ * 128 - 1;
        cycle++;
        pitchSin = Model.sine[pitch];
        pitchCos = Model.cosine[pitch];
        yawSin = Model.sine[yaw];
        yawCos = Model.cosine[yaw];
        renderArea = visibilityMaps[(pitch - 128) / 32][yaw / 64];
        cameraX2 = x2;
        cameraY2 = y2;
        cameraZ2 = z2;
        screenCenterX = x2 / 128;
        screenCenterZ = z2 / 128;
        screenPlane = plane;
        minTileX = screenCenterX - distance;
        if (minTileX < 0)
            minTileX = 0;
        minTileZ = screenCenterZ - distance;
        if (minTileZ < 0)
            minTileZ = 0;
        maxTileX = screenCenterX + distance;
        if (maxTileX > maxX)
            maxTileX = maxX;
        maxTileZ = screenCenterZ + distance;
        if (maxTileZ > maxZ)
            maxTileZ = maxZ;
        method319();
        tileUpdateCount = 0;
        for (int k1 = minLevel; k1 < maxY; k1++) {
            Tile aclass30_sub3[][] = tiles[k1];
            for (int i2 = minTileX; i2 < maxTileX; i2++) {
                for (int k2 = minTileZ; k2 < maxTileZ; k2++) {
                    Tile class30_sub3 = aclass30_sub3[i2][k2];
                    if (class30_sub3 != null)
                        if (class30_sub3.physicalLevel > plane || (!GpuPlugin.process() && !renderArea[(i2 - screenCenterX) + distance][(k2 - screenCenterZ) + distance] && tileHeights[k1][i2][k2] - y2 < 2000)) {
                            class30_sub3.draw = false;
                            class30_sub3.visible = false;
                            class30_sub3.wallCullDirection = 0;
                        } else {
                            class30_sub3.draw = true;
                            class30_sub3.visible = true;
                            class30_sub3.drawEntities = class30_sub3.entityCount > 0;
                            tileUpdateCount++;
                        }
                }

            }

        }

        for (int l1 = minLevel; l1 < maxY; l1++) {
            Tile aclass30_sub3_1[][] = tiles[l1];
            for (int l2 = -distance; l2 <= 0; l2++) {
                int i3 = screenCenterX + l2;
                int k3 = screenCenterX - l2;
                if (i3 >= minTileX || k3 < maxTileX) {
                    for (int i4 = -distance; i4 <= 0; i4++) {
                        int k4 = screenCenterZ + i4;
                        int i5 = screenCenterZ - i4;
                        if (i3 >= minTileX) {
                            if (k4 >= minTileZ) {
                                Tile class30_sub3_1 = aclass30_sub3_1[i3][k4];
                                if (class30_sub3_1 != null && class30_sub3_1.draw)
                                    draw(class30_sub3_1, true);
                            }
                            if (i5 < maxTileZ) {
                                Tile class30_sub3_2 = aclass30_sub3_1[i3][i5];
                                if (class30_sub3_2 != null && class30_sub3_2.draw)
                                    draw(class30_sub3_2, true);
                            }
                        }
                        if (k3 < maxTileX) {
                            if (k4 >= minTileZ) {
                                Tile class30_sub3_3 = aclass30_sub3_1[k3][k4];
                                if (class30_sub3_3 != null && class30_sub3_3.draw)
                                    draw(class30_sub3_3, true);
                            }
                            if (i5 < maxTileZ) {
                                Tile class30_sub3_4 = aclass30_sub3_1[k3][i5];
                                if (class30_sub3_4 != null && class30_sub3_4.draw)
                                    draw(class30_sub3_4, true);
                            }
                        }
                        if (tileUpdateCount == 0) {
                            checkClick = false;
                            return;
                        }
                    }

                }
            }

        }

        for (int j2 = minLevel; j2 < maxY; j2++) {
            Tile aclass30_sub3_2[][] = tiles[j2];
            for (int j3 = -distance; j3 <= 0; j3++) {
                int l3 = screenCenterX + j3;
                int j4 = screenCenterX - j3;
                if (l3 >= minTileX || j4 < maxTileX) {
                    for (int l4 = -distance; l4 <= 0; l4++) {
                        int j5 = screenCenterZ + l4;
                        int k5 = screenCenterZ - l4;
                        if (l3 >= minTileX) {
                            if (j5 >= minTileZ) {
                                Tile class30_sub3_5 = aclass30_sub3_2[l3][j5];
                                if (class30_sub3_5 != null && class30_sub3_5.draw)
                                    draw(class30_sub3_5, false);
                            }
                            if (k5 < maxTileZ) {
                                Tile class30_sub3_6 = aclass30_sub3_2[l3][k5];
                                if (class30_sub3_6 != null && class30_sub3_6.draw)
                                    draw(class30_sub3_6, false);
                            }
                        }
                        if (j4 < maxTileX) {
                            if (j5 >= minTileZ) {
                                Tile class30_sub3_7 = aclass30_sub3_2[j4][j5];
                                if (class30_sub3_7 != null && class30_sub3_7.draw)
                                    draw(class30_sub3_7, false);
                            }
                            if (k5 < maxTileZ) {
                                Tile class30_sub3_8 = aclass30_sub3_2[j4][k5];
                                if (class30_sub3_8 != null && class30_sub3_8.draw)
                                    draw(class30_sub3_8, false);
                            }
                        }
                        if (tileUpdateCount == 0) {
                            checkClick = false;
                            return;
                        }
                    }

                }
            }

        }

        checkClick = false;
    }

    private void draw(Tile class30_sub3, boolean flag) {
        aClass19_477.insertHead(class30_sub3);
        do {
            Tile class30_sub3_1;
            do {
                class30_sub3_1 = (Tile) aClass19_477.popHead();
                if (class30_sub3_1 == null)
                    return;
            } while (!class30_sub3_1.visible);
            int i = class30_sub3_1.x;
            int j = class30_sub3_1.y;
            int k = class30_sub3_1.plane;
            int l = class30_sub3_1.renderLevel;
            Tile aclass30_sub3[][] = tiles[k];
            if (class30_sub3_1.draw) {
                if (flag) {
                    if (k > 0) {
                        Tile class30_sub3_2 = tiles[k - 1][i][j];
                        if (class30_sub3_2 != null && class30_sub3_2.visible)
                            continue;
                    }
                    if (i <= screenCenterX && i > minTileX) {
                        Tile class30_sub3_3 = aclass30_sub3[i - 1][j];
                        if (class30_sub3_3 != null && class30_sub3_3.visible && (class30_sub3_3.draw || (class30_sub3_1.flags & 1) == 0))
                            continue;
                    }
                    if (i >= screenCenterX && i < maxTileX - 1) {
                        Tile class30_sub3_4 = aclass30_sub3[i + 1][j];
                        if (class30_sub3_4 != null && class30_sub3_4.visible && (class30_sub3_4.draw || (class30_sub3_1.flags & 4) == 0))
                            continue;
                    }
                    if (j <= screenCenterZ && j > minTileZ) {
                        Tile class30_sub3_5 = aclass30_sub3[i][j - 1];
                        if (class30_sub3_5 != null && class30_sub3_5.visible && (class30_sub3_5.draw || (class30_sub3_1.flags & 8) == 0))
                            continue;
                    }
                    if (j >= screenCenterZ && j < maxTileZ - 1) {
                        Tile class30_sub3_6 = aclass30_sub3[i][j + 1];
                        if (class30_sub3_6 != null && class30_sub3_6.visible && (class30_sub3_6.draw || (class30_sub3_1.flags & 2) == 0))
                            continue;
                    }
                } else {
                    flag = true;
                }
                class30_sub3_1.draw = false;
                if (class30_sub3_1.bridge != null) {
                    Tile class30_sub3_7 = class30_sub3_1.bridge;
                    if (class30_sub3_7.underlay != null) {
                        if (!method320(0, i, j))
                            drawTileUnderlay(class30_sub3_7.underlay, 0, pitchSin, pitchCos, yawSin, yawCos, i, j);
                    } else if (class30_sub3_7.overlay != null && !method320(0, i, j))
                        drawTileOverlay(i, pitchSin, yawSin, class30_sub3_7.overlay, pitchCos, j, yawCos);
                    WallObject class10 = class30_sub3_7.wallObject;
                    if (class10 != null)
                        class10.renderableOne.render(0, pitchSin, pitchCos, yawSin, yawCos, class10.anInt274 - cameraX2, class10.anInt273 - cameraY2, class10.anInt275 - cameraZ2, class10.uid);
                    for (int i2 = 0; i2 < class30_sub3_7.entityCount; i2++) {
                        GameObject class28 = class30_sub3_7.objects[i2];
                        if (class28 != null)
                            class28.renderable.render(class28.anInt522, pitchSin, pitchCos, yawSin, yawCos, class28.anInt519 - cameraX2, class28.anInt518 - cameraY2, class28.anInt520 - cameraZ2, class28.uid);
                    }

                }
                boolean flag1 = false;
                if (class30_sub3_1.underlay != null) {
                    if (!method320(l, i, j)) {
                        flag1 = true;
                        drawTileUnderlay(class30_sub3_1.underlay, l, pitchSin, pitchCos, yawSin, yawCos, i, j);
                    }
                } else if (class30_sub3_1.overlay != null && !method320(l, i, j)) {
                    flag1 = true;
                    drawTileOverlay(i, pitchSin, yawSin, class30_sub3_1.overlay, pitchCos, j, yawCos);
                }
                int j1 = 0;
                int j2 = 0;
                WallObject class10_3 = class30_sub3_1.wallObject;
                DecorativeObject class26_1 = class30_sub3_1.wallDecoration;
                if (class10_3 != null || class26_1 != null) {
                    if (screenCenterX == i)
                        j1++;
                    else if (screenCenterX < i)
                        j1 += 2;
                    if (screenCenterZ == j)
                        j1 += 3;
                    else if (screenCenterZ > j)
                        j1 += 6;
                    j2 = anIntArray478[j1];
                    class30_sub3_1.wallDrawFlags = anIntArray480[j1];
                }
                if (class10_3 != null) {
                    if ((class10_3.orientation & anIntArray479[j1]) != 0) {
                        if (class10_3.orientation == 16) {
                            class30_sub3_1.wallCullDirection = 3;
                            class30_sub3_1.wallUncullDirection = anIntArray481[j1];
                            class30_sub3_1.wallCullOppositeDirection = 3 - class30_sub3_1.wallUncullDirection;
                        } else if (class10_3.orientation == 32) {
                            class30_sub3_1.wallCullDirection = 6;
                            class30_sub3_1.wallUncullDirection = anIntArray482[j1];
                            class30_sub3_1.wallCullOppositeDirection = 6 - class30_sub3_1.wallUncullDirection;
                        } else if (class10_3.orientation == 64) {
                            class30_sub3_1.wallCullDirection = 12;
                            class30_sub3_1.wallUncullDirection = anIntArray483[j1];
                            class30_sub3_1.wallCullOppositeDirection = 12 - class30_sub3_1.wallUncullDirection;
                        } else {
                            class30_sub3_1.wallCullDirection = 9;
                            class30_sub3_1.wallUncullDirection = anIntArray484[j1];
                            class30_sub3_1.wallCullOppositeDirection = 9 - class30_sub3_1.wallUncullDirection;
                        }
                    } else {
                        class30_sub3_1.wallCullDirection = 0;
                    }
                    if ((class10_3.orientation & j2) != 0 && !method321(l, i, j, class10_3.orientation))
                        class10_3.renderableOne.render(0, pitchSin, pitchCos, yawSin, yawCos, class10_3.anInt274 - cameraX2, class10_3.anInt273 - cameraY2, class10_3.anInt275 - cameraZ2, class10_3.uid);
                    if ((class10_3.orientation1 & j2) != 0 && !method321(l, i, j, class10_3.orientation1))
                        class10_3.renderableTwo.render(0, pitchSin, pitchCos, yawSin, yawCos, class10_3.anInt274 - cameraX2, class10_3.anInt273 - cameraY2, class10_3.anInt275 - cameraZ2, class10_3.uid);
                }
                if (class26_1 != null && !method322(l, i, j, class26_1.renderable.modelHeight))
                    if ((class26_1.anInt502 & j2) != 0)
                        class26_1.renderable.render(class26_1.anInt503, pitchSin, pitchCos, yawSin, yawCos, class26_1.anInt500 - cameraX2, class26_1.anInt499 - cameraY2, class26_1.anInt501 - cameraZ2, class26_1.uid);
                    else if ((class26_1.anInt502 & 0x300) != 0) {
                        int j4 = class26_1.anInt500 - cameraX2;
                        int l5 = class26_1.anInt499 - cameraY2;
                        int k6 = class26_1.anInt501 - cameraZ2;
                        int i8 = class26_1.anInt503;
                        int k9;
                        if (i8 == 1 || i8 == 2)
                            k9 = -j4;
                        else
                            k9 = j4;
                        int k10;
                        if (i8 == 2 || i8 == 3)
                            k10 = -k6;
                        else
                            k10 = k6;
                        if ((class26_1.anInt502 & 0x100) != 0 && k10 < k9) {
                            int i11 = j4 + anIntArray463[i8];
                            int k11 = k6 + anIntArray464[i8];
                            class26_1.renderable.render(i8 * 512 + 256, pitchSin, pitchCos, yawSin, yawCos, i11, l5, k11, class26_1.uid);
                        }
                        if ((class26_1.anInt502 & 0x200) != 0 && k10 > k9) {
                            int j11 = j4 + anIntArray465[i8];
                            int l11 = k6 + anIntArray466[i8];
                            class26_1.renderable.render(i8 * 512 + 1280 & 0x7ff, pitchSin, pitchCos, yawSin, yawCos, j11, l5, l11, class26_1.uid);
                        }
                    }
                if (flag1) {
                    GroundObject class49 = class30_sub3_1.groundObject;
                    if (class49 != null)
                        class49.renderable.render(0, pitchSin, pitchCos, yawSin, yawCos, class49.anInt812 - cameraX2, class49.anInt811 - cameraY2, class49.anInt813 - cameraZ2, class49.uid);
                    GroundItem object4_1 = class30_sub3_1.groundItem;
                    if (object4_1 != null && object4_1.anInt52 == 0) {
                        if (object4_1.second != null)
                            object4_1.second.render(0, pitchSin, pitchCos, yawSin, yawCos, object4_1.anInt46 - cameraX2, object4_1.anInt45 - cameraY2, object4_1.anInt47 - cameraZ2, object4_1.uid);
                        if (object4_1.third != null)
                            object4_1.third.render(0, pitchSin, pitchCos, yawSin, yawCos, object4_1.anInt46 - cameraX2, object4_1.anInt45 - cameraY2, object4_1.anInt47 - cameraZ2, object4_1.uid);
                        if (object4_1.first != null)
                            object4_1.first.render(0, pitchSin, pitchCos, yawSin, yawCos, object4_1.anInt46 - cameraX2, object4_1.anInt45 - cameraY2, object4_1.anInt47 - cameraZ2, object4_1.uid);
                    }
                }
                int k4 = class30_sub3_1.flags;
                if (k4 != 0) {
                    if (i < screenCenterX && (k4 & 4) != 0) {
                        Tile class30_sub3_17 = aclass30_sub3[i + 1][j];
                        if (class30_sub3_17 != null && class30_sub3_17.visible)
                            aClass19_477.insertHead(class30_sub3_17);
                    }
                    if (j < screenCenterZ && (k4 & 2) != 0) {
                        Tile class30_sub3_18 = aclass30_sub3[i][j + 1];
                        if (class30_sub3_18 != null && class30_sub3_18.visible)
                            aClass19_477.insertHead(class30_sub3_18);
                    }
                    if (i > screenCenterX && (k4 & 1) != 0) {
                        Tile class30_sub3_19 = aclass30_sub3[i - 1][j];
                        if (class30_sub3_19 != null && class30_sub3_19.visible)
                            aClass19_477.insertHead(class30_sub3_19);
                    }
                    if (j > screenCenterZ && (k4 & 8) != 0) {
                        Tile class30_sub3_20 = aclass30_sub3[i][j - 1];
                        if (class30_sub3_20 != null && class30_sub3_20.visible)
                            aClass19_477.insertHead(class30_sub3_20);
                    }
                }
            }
            if (class30_sub3_1.wallCullDirection != 0) {
                boolean flag2 = true;
                for (int k1 = 0; k1 < class30_sub3_1.entityCount; k1++) {
                    if (class30_sub3_1.objects[k1].anInt528 == cycle || (class30_sub3_1.entityFlags[k1] & class30_sub3_1.wallCullDirection) != class30_sub3_1.wallUncullDirection)
                        continue;
                    flag2 = false;
                    break;
                }

                if (flag2) {
                    WallObject class10_1 = class30_sub3_1.wallObject;
                    if (!method321(l, i, j, class10_1.orientation))
                        class10_1.renderableOne.render(0, pitchSin, pitchCos, yawSin, yawCos, class10_1.anInt274 - cameraX2, class10_1.anInt273 - cameraY2, class10_1.anInt275 - cameraZ2, class10_1.uid);
                    class30_sub3_1.wallCullDirection = 0;
                }
            }
            if (class30_sub3_1.drawEntities)
                try {
                    int i1 = class30_sub3_1.entityCount;
                    class30_sub3_1.drawEntities = false;
                    int l1 = 0;
                    label0:
                    for (int k2 = 0; k2 < i1; k2++) {
                        GameObject class28_1 = class30_sub3_1.objects[k2];
                        if (class28_1.anInt528 == cycle)
                            continue;
                        for (int k3 = class28_1.anInt523; k3 <= class28_1.anInt524; k3++) {
                            for (int l4 = class28_1.anInt525; l4 <= class28_1.anInt526; l4++) {
                                Tile class30_sub3_21 = aclass30_sub3[k3][l4];
                                if (class30_sub3_21.draw) {
                                    class30_sub3_1.drawEntities = true;
                                } else {
                                    if (class30_sub3_21.wallCullDirection == 0)
                                        continue;
                                    int l6 = 0;
                                    if (k3 > class28_1.anInt523)
                                        l6++;
                                    if (k3 < class28_1.anInt524)
                                        l6 += 4;
                                    if (l4 > class28_1.anInt525)
                                        l6 += 8;
                                    if (l4 < class28_1.anInt526)
                                        l6 += 2;
                                    if ((l6 & class30_sub3_21.wallCullDirection) != class30_sub3_1.wallCullOppositeDirection)
                                        continue;
                                    class30_sub3_1.drawEntities = true;
                                }
                                continue label0;
                            }

                        }

                        aClass28Array462[l1++] = class28_1;
                        int i5 = screenCenterX - class28_1.anInt523;
                        int i6 = class28_1.anInt524 - screenCenterX;
                        if (i6 > i5)
                            i5 = i6;
                        int i7 = screenCenterZ - class28_1.anInt525;
                        int j8 = class28_1.anInt526 - screenCenterZ;
                        if (j8 > i7)
                            class28_1.anInt527 = i5 + j8;
                        else
                            class28_1.anInt527 = i5 + i7;
                    }

                    while (l1 > 0) {
                        int i3 = -50;
                        int l3 = -1;
                        for (int j5 = 0; j5 < l1; j5++) {
                            GameObject class28_2 = aClass28Array462[j5];
                            if (class28_2.anInt528 != cycle)
                                if (class28_2.anInt527 > i3) {
                                    i3 = class28_2.anInt527;
                                    l3 = j5;
                                } else if (class28_2.anInt527 == i3) {
                                    int j7 = class28_2.anInt519 - cameraX2;
                                    int k8 = class28_2.anInt520 - cameraZ2;
                                    int l9 = aClass28Array462[l3].anInt519 - cameraX2;
                                    int l10 = aClass28Array462[l3].anInt520 - cameraZ2;
                                    if (j7 * j7 + k8 * k8 > l9 * l9 + l10 * l10)
                                        l3 = j5;
                                }
                        }

                        if (l3 == -1)
                            break;
                        GameObject class28_3 = aClass28Array462[l3];
                        class28_3.anInt528 = cycle;
                        if (!method323(l, class28_3.anInt523, class28_3.anInt524, class28_3.anInt525, class28_3.anInt526, class28_3.renderable.modelHeight))
                            class28_3.renderable.render(class28_3.anInt522, pitchSin, pitchCos, yawSin, yawCos, class28_3.anInt519 - cameraX2, class28_3.anInt518 - cameraY2, class28_3.anInt520 - cameraZ2, class28_3.uid);
                        for (int k7 = class28_3.anInt523; k7 <= class28_3.anInt524; k7++) {
                            for (int l8 = class28_3.anInt525; l8 <= class28_3.anInt526; l8++) {
                                Tile class30_sub3_22 = aclass30_sub3[k7][l8];
                                if (class30_sub3_22.wallCullDirection != 0)
                                    aClass19_477.insertHead(class30_sub3_22);
                                else if ((k7 != i || l8 != j) && class30_sub3_22.visible)
                                    aClass19_477.insertHead(class30_sub3_22);
                            }

                        }

                    }
                    if (class30_sub3_1.drawEntities)
                        continue;
                } catch (Exception _ex) {
                    class30_sub3_1.drawEntities = false;
                }
            if (!class30_sub3_1.visible || class30_sub3_1.wallCullDirection != 0)
                continue;
            if (i <= screenCenterX && i > minTileX) {
                Tile class30_sub3_8 = aclass30_sub3[i - 1][j];
                if (class30_sub3_8 != null && class30_sub3_8.visible)
                    continue;
            }
            if (i >= screenCenterX && i < maxTileX - 1) {
                Tile class30_sub3_9 = aclass30_sub3[i + 1][j];
                if (class30_sub3_9 != null && class30_sub3_9.visible)
                    continue;
            }
            if (j <= screenCenterZ && j > minTileZ) {
                Tile class30_sub3_10 = aclass30_sub3[i][j - 1];
                if (class30_sub3_10 != null && class30_sub3_10.visible)
                    continue;
            }
            if (j >= screenCenterZ && j < maxTileZ - 1) {
                Tile class30_sub3_11 = aclass30_sub3[i][j + 1];
                if (class30_sub3_11 != null && class30_sub3_11.visible)
                    continue;
            }
            class30_sub3_1.visible = false;
            tileUpdateCount--;
            GroundItem object4 = class30_sub3_1.groundItem;
            if (object4 != null && object4.anInt52 != 0) {
                if (object4.second != null)
                    object4.second.render(0, pitchSin, pitchCos, yawSin, yawCos, object4.anInt46 - cameraX2, object4.anInt45 - cameraY2 - object4.anInt52, object4.anInt47 - cameraZ2, object4.uid);
                if (object4.third != null)
                    object4.third.render(0, pitchSin, pitchCos, yawSin, yawCos, object4.anInt46 - cameraX2, object4.anInt45 - cameraY2 - object4.anInt52, object4.anInt47 - cameraZ2, object4.uid);
                if (object4.first != null)
                    object4.first.render(0, pitchSin, pitchCos, yawSin, yawCos, object4.anInt46 - cameraX2, object4.anInt45 - cameraY2 - object4.anInt52, object4.anInt47 - cameraZ2, object4.uid);
            }
            if (class30_sub3_1.wallDrawFlags != 0) {
                DecorativeObject class26 = class30_sub3_1.wallDecoration;
                if (class26 != null && !method322(l, i, j, class26.renderable.modelHeight))
                    if ((class26.anInt502 & class30_sub3_1.wallDrawFlags) != 0)
                        class26.renderable.render(class26.anInt503, pitchSin, pitchCos, yawSin, yawCos, class26.anInt500 - cameraX2, class26.anInt499 - cameraY2, class26.anInt501 - cameraZ2, class26.uid);
                    else if ((class26.anInt502 & 0x300) != 0) {
                        int l2 = class26.anInt500 - cameraX2;
                        int j3 = class26.anInt499 - cameraY2;
                        int i4 = class26.anInt501 - cameraZ2;
                        int k5 = class26.anInt503;
                        int j6;
                        if (k5 == 1 || k5 == 2)
                            j6 = -l2;
                        else
                            j6 = l2;
                        int l7;
                        if (k5 == 2 || k5 == 3)
                            l7 = -i4;
                        else
                            l7 = i4;
                        if ((class26.anInt502 & 0x100) != 0 && l7 >= j6) {
                            int i9 = l2 + anIntArray463[k5];
                            int i10 = i4 + anIntArray464[k5];
                            class26.renderable.render(k5 * 512 + 256, pitchSin, pitchCos, yawSin, yawCos, i9, j3, i10, class26.uid);
                        }
                        if ((class26.anInt502 & 0x200) != 0 && l7 <= j6) {
                            int j9 = l2 + anIntArray465[k5];
                            int j10 = i4 + anIntArray466[k5];
                            class26.renderable.render(k5 * 512 + 1280 & 0x7ff, pitchSin, pitchCos, yawSin, yawCos, j9, j3, j10, class26.uid);
                        }
                    }
                WallObject class10_2 = class30_sub3_1.wallObject;
                if (class10_2 != null) {
                    if ((class10_2.orientation1 & class30_sub3_1.wallDrawFlags) != 0 && !method321(l, i, j, class10_2.orientation1))
                        class10_2.renderableTwo.render(0, pitchSin, pitchCos, yawSin, yawCos, class10_2.anInt274 - cameraX2, class10_2.anInt273 - cameraY2, class10_2.anInt275 - cameraZ2, class10_2.uid);
                    if ((class10_2.orientation & class30_sub3_1.wallDrawFlags) != 0 && !method321(l, i, j, class10_2.orientation))
                        class10_2.renderableOne.render(0, pitchSin, pitchCos, yawSin, yawCos, class10_2.anInt274 - cameraX2, class10_2.anInt273 - cameraY2, class10_2.anInt275 - cameraZ2, class10_2.uid);
                }
            }
            if (k < maxY - 1) {
                Tile class30_sub3_12 = tiles[k + 1][i][j];
                if (class30_sub3_12 != null && class30_sub3_12.visible)
                    aClass19_477.insertHead(class30_sub3_12);
            }
            if (i < screenCenterX) {
                Tile class30_sub3_13 = aclass30_sub3[i + 1][j];
                if (class30_sub3_13 != null && class30_sub3_13.visible)
                    aClass19_477.insertHead(class30_sub3_13);
            }
            if (j < screenCenterZ) {
                Tile class30_sub3_14 = aclass30_sub3[i][j + 1];
                if (class30_sub3_14 != null && class30_sub3_14.visible)
                    aClass19_477.insertHead(class30_sub3_14);
            }
            if (i > screenCenterX) {
                Tile class30_sub3_15 = aclass30_sub3[i - 1][j];
                if (class30_sub3_15 != null && class30_sub3_15.visible)
                    aClass19_477.insertHead(class30_sub3_15);
            }
            if (j > screenCenterZ) {
                Tile class30_sub3_16 = aclass30_sub3[i][j - 1];
                if (class30_sub3_16 != null && class30_sub3_16.visible)
                    aClass19_477.insertHead(class30_sub3_16);
            }
        } while (true);
    }

    private void drawTileUnderlay(SceneTilePaint tile, int z, int pitchSin, int pitchCos, int yawSin, int yawCos, int x, int y) {
        if (GpuPlugin.process()) {
            Client.instance.gpu.drawScenePaint(0, pitchSin, pitchCos, yawSin, yawCos, -cameraX2, -cameraY2, -cameraZ2, tile, z, x, y, Client.instance.get3dZoom(), centerX, centerY);
            //return;
        }
        int l1;
        int i2 = l1 = (x << 7) - cameraX2;
        int j2;
        int k2 = j2 = (y << 7) - cameraZ2;
        int l2;
        int i3 = l2 = i2 + 128;
        int j3;
        int k3 = j3 = k2 + 128;
        int l3 = tileHeights[z][x][y] - cameraY2;
        int i4 = tileHeights[z][x + 1][y] - cameraY2;
        int j4 = tileHeights[z][x + 1][y + 1] - cameraY2;
        int k4 = tileHeights[z][x][y + 1] - cameraY2;
        int l4 = k2 * yawSin + i2 * yawCos >> 16;
        k2 = k2 * yawCos - i2 * yawSin >> 16;
        i2 = l4;
        l4 = l3 * pitchCos - k2 * pitchSin >> 16;
        k2 = l3 * pitchSin + k2 * pitchCos >> 16;
        l3 = l4;
        if (k2 < 50)
            return;
        l4 = j2 * yawSin + i3 * yawCos >> 16;
        j2 = j2 * yawCos - i3 * yawSin >> 16;
        i3 = l4;
        l4 = i4 * pitchCos - j2 * pitchSin >> 16;
        j2 = i4 * pitchSin + j2 * pitchCos >> 16;
        i4 = l4;
        if (j2 < 50)
            return;
        l4 = k3 * yawSin + l2 * yawCos >> 16;
        k3 = k3 * yawCos - l2 * yawSin >> 16;
        l2 = l4;
        l4 = j4 * pitchCos - k3 * pitchSin >> 16;
        k3 = j4 * pitchSin + k3 * pitchCos >> 16;
        j4 = l4;
        if (k3 < 50)
            return;
        l4 = j3 * yawSin + l1 * yawCos >> 16;
        j3 = j3 * yawCos - l1 * yawSin >> 16;
        l1 = l4;
        l4 = k4 * pitchCos - j3 * pitchSin >> 16;
        j3 = k4 * pitchSin + j3 * pitchCos >> 16;
        k4 = l4;
        if (j3 < 50)
            return;
        int i5 = Rasterizer3D.textureInt1 + (i2 << 9) / k2;
        int j5 = Rasterizer3D.textureInt2 + (l3 << 9) / k2;
        int k5 = Rasterizer3D.textureInt1 + (i3 << 9) / j2;
        int l5 = Rasterizer3D.textureInt2 + (i4 << 9) / j2;
        int i6 = Rasterizer3D.textureInt1 + (l2 << 9) / k3;
        int j6 = Rasterizer3D.textureInt2 + (j4 << 9) / k3;
        int k6 = Rasterizer3D.textureInt1 + (l1 << 9) / j3;
        int l6 = Rasterizer3D.textureInt2 + (k4 << 9) / j3;
        Rasterizer3D.alpha = 0;
        if ((i6 - k6) * (l5 - l6) - (j6 - l6) * (k5 - k6) > 0) {
            Rasterizer3D.hidden = i6 < 0 || k6 < 0 || k5 < 0 || i6 > Raster.centerX || k6 > Raster.centerX || k5 > Raster.centerX;
            if (checkClick && method318(anInt468, anInt469, j6, l6, l5, i6, k6, k5)) {
                anInt470 = x;
                anInt471 = y;
            }
            if (tile.texture == -1) {
                if (tile.neColor != 0xbc614e)
                    Rasterizer3D.drawGouraud(j6, l6, l5, i6, k6, k5, tile.neColor, tile.nwColor, tile.seColor);
            } else if (!lowMem) {
                if (tile.flat)
                    Rasterizer3D.drawTextured(j6, l6, l5, i6, k6, k5, tile.neColor, tile.nwColor, tile.seColor, i2, i3, l1, l3, i4, k4, k2, j2, j3, tile.texture);
                else
                    Rasterizer3D.drawTextured(j6, l6, l5, i6, k6, k5, tile.neColor, tile.nwColor, tile.seColor, l2, l1, i3, j4, k4, i4, k3, j3, j2, tile.texture);
            } else {
                int i7 = anIntArray485[tile.texture];
                Rasterizer3D.drawGouraud(j6, l6, l5, i6, k6, k5, method317(i7, tile.neColor), method317(i7, tile.nwColor), method317(i7, tile.seColor));
            }
        }
        if ((i5 - k5) * (l6 - l5) - (j5 - l5) * (k6 - k5) > 0) {
            Rasterizer3D.hidden = i5 < 0 || k5 < 0 || k6 < 0 || i5 > Raster.centerX || k5 > Raster.centerX || k6 > Raster.centerX;
            if (checkClick && method318(anInt468, anInt469, j5, l5, l6, i5, k5, k6)) {
                anInt470 = x;
                anInt471 = y;
            }
            if (tile.texture == -1) {
                if (tile.swColor != 0xbc614e) {
                    Rasterizer3D.drawGouraud(j5, l5, l6, i5, k5, k6, tile.swColor, tile.seColor, tile.nwColor);
                }
            } else {
                if (!lowMem) {
                    Rasterizer3D.drawTextured(j5, l5, l6, i5, k5, k6, tile.swColor, tile.seColor, tile.nwColor, i2, i3, l1, l3, i4, k4, k2, j2, j3, tile.texture);
                    return;
                }
                int j7 = anIntArray485[tile.texture];
                Rasterizer3D.drawGouraud(j5, l5, l6, i5, k5, k6, method317(j7, tile.swColor), method317(j7, tile.seColor), method317(j7, tile.nwColor));
            }
        }
    }

    private void drawTileOverlay(int x, int pitchSin, int yawSin, SceneTileModel tile, int pitchCos, int y, int yawCos) {
        if (GpuPlugin.process()) {
            final int cameraX2 = Scene.cameraX2;
            final int cameraY2 = Scene.cameraY2;
            final int cameraZ2 = Scene.cameraZ2;
            final int zoom = Client.instance.get3dZoom();
            final int centerX = Client.instance.getCenterX();
            final int centerY = Client.instance.getCenterY();

            Client.instance.gpu.drawSceneModel(0, pitchSin, pitchCos, yawSin, yawCos, -cameraX2, -cameraY2, -cameraZ2, tile, Client.instance.getPlane(), x, y, zoom, centerX, centerY);
            //return;
        }
        int k1 = tile.vertexX.length;
        for (int l1 = 0; l1 < k1; l1++) {
            int i2 = tile.vertexX[l1] - cameraX2;
            int k2 = tile.vertexY[l1] - cameraY2;
            int i3 = tile.vertexZ[l1] - cameraZ2;
            int k3 = i3 * yawSin + i2 * yawCos >> 16;
            i3 = i3 * yawCos - i2 * yawSin >> 16;
            i2 = k3;
            k3 = k2 * pitchCos - i3 * pitchSin >> 16;
            i3 = k2 * pitchSin + i3 * pitchCos >> 16;
            k2 = k3;
            if (i3 < 50)
                return;
            if (tile.triangleTextureId != null) {
                SceneTileModel.anIntArray690[l1] = i2;
                SceneTileModel.anIntArray691[l1] = k2;
                SceneTileModel.anIntArray692[l1] = i3;
            }
            SceneTileModel.anIntArray688[l1] = Rasterizer3D.textureInt1 + (i2 << 9) / i3;
            SceneTileModel.anIntArray689[l1] = Rasterizer3D.textureInt2 + (k2 << 9) / i3;
        }

        Rasterizer3D.alpha = 0;
        k1 = tile.faceX.length;
        for (int j2 = 0; j2 < k1; j2++) {
            int l2 = tile.faceX[j2];
            int j3 = tile.faceY[j2];
            int l3 = tile.faceZ[j2];
            int i4 = SceneTileModel.anIntArray688[l2];
            int j4 = SceneTileModel.anIntArray688[j3];
            int k4 = SceneTileModel.anIntArray688[l3];
            int l4 = SceneTileModel.anIntArray689[l2];
            int i5 = SceneTileModel.anIntArray689[j3];
            int j5 = SceneTileModel.anIntArray689[l3];
            if ((i4 - j4) * (j5 - i5) - (l4 - i5) * (k4 - j4) > 0) {
                Rasterizer3D.hidden = i4 < 0 || j4 < 0 || k4 < 0 || i4 > Raster.centerX || j4 > Raster.centerX || k4 > Raster.centerX;
                if (checkClick && method318(anInt468, anInt469, l4, i5, j5, i4, j4, k4)) {
                    anInt470 = x;
                    anInt471 = y;
                }
                if (tile.triangleTextureId == null || tile.triangleTextureId[j2] == -1) {
                    if (tile.triangleColorA[j2] != 0xbc614e)
                        Rasterizer3D.drawGouraud(l4, i5, j5, i4, j4, k4, tile.triangleColorA[j2], tile.triangleColorB[j2], tile.triangleColorC[j2]);
                } else if (!lowMem) {
                    if (tile.flat)
                        Rasterizer3D.drawTextured(l4, i5, j5, i4, j4, k4, tile.triangleColorA[j2], tile.triangleColorB[j2], tile.triangleColorC[j2], SceneTileModel.anIntArray690[0], SceneTileModel.anIntArray690[1], SceneTileModel.anIntArray690[3], SceneTileModel.anIntArray691[0], SceneTileModel.anIntArray691[1], SceneTileModel.anIntArray691[3], SceneTileModel.anIntArray692[0], SceneTileModel.anIntArray692[1], SceneTileModel.anIntArray692[3], tile.triangleTextureId[j2]);
                    else
                        Rasterizer3D.drawTextured(l4, i5, j5, i4, j4, k4, tile.triangleColorA[j2], tile.triangleColorB[j2], tile.triangleColorC[j2], SceneTileModel.anIntArray690[l2], SceneTileModel.anIntArray690[j3], SceneTileModel.anIntArray690[l3], SceneTileModel.anIntArray691[l2], SceneTileModel.anIntArray691[j3], SceneTileModel.anIntArray691[l3], SceneTileModel.anIntArray692[l2], SceneTileModel.anIntArray692[j3], SceneTileModel.anIntArray692[l3], tile.triangleTextureId[j2]);
                } else {
                    int k5 = anIntArray485[tile.triangleTextureId[j2]];
                    Rasterizer3D.drawGouraud(l4, i5, j5, i4, j4, k4, method317(k5, tile.triangleColorA[j2]), method317(k5, tile.triangleColorB[j2]), method317(k5, tile.triangleColorC[j2]));
                }
            }
        }

    }

    private int method317(int j, int k) {
        k = 127 - k;
        k = (k * (j & 0x7f)) / 160;
        if (k < 2)
            k = 2;
        else if (k > 126)
            k = 126;
        return (j & 0xff80) + k;
    }

    private boolean method318(int i, int j, int k, int l, int i1, int j1, int k1,
                              int l1) {
        if (j < k && j < l && j < i1)
            return false;
        if (j > k && j > l && j > i1)
            return false;
        if (i < j1 && i < k1 && i < l1)
            return false;
        if (i > j1 && i > k1 && i > l1)
            return false;
        int i2 = (j - k) * (k1 - j1) - (i - j1) * (l - k);
        int j2 = (j - i1) * (j1 - l1) - (i - l1) * (k - i1);
        int k2 = (j - l) * (l1 - k1) - (i - k1) * (i1 - l);
        return i2 * k2 > 0 && k2 * j2 > 0;
    }

    private void method319() {
        int j = anIntArray473[screenPlane];
        SceneCluster aclass47[] = aClass47ArrayArray474[screenPlane];
        anInt475 = 0;
        for (int k = 0; k < j; k++) {
            SceneCluster class47 = aclass47[k];
            if (class47.anInt791 == 1) {
                int l = (class47.anInt787 - screenCenterX) + 25;
                if (l < 0 || l > 50)
                    continue;
                int k1 = (class47.anInt789 - screenCenterZ) + 25;
                if (k1 < 0)
                    k1 = 0;
                int j2 = (class47.anInt790 - screenCenterZ) + 25;
                if (j2 > 50)
                    j2 = 50;
                boolean flag = false;
                while (k1 <= j2)
                    if (renderArea[l][k1++]) {
                        flag = true;
                        break;
                    }
                if (!flag)
                    continue;
                int j3 = cameraX2 - class47.anInt792;
                if (j3 > 32) {
                    class47.anInt798 = 1;
                } else {
                    if (j3 >= -32)
                        continue;
                    class47.anInt798 = 2;
                    j3 = -j3;
                }
                class47.anInt801 = (class47.anInt794 - cameraZ2 << 8) / j3;
                class47.anInt802 = (class47.anInt795 - cameraZ2 << 8) / j3;
                class47.anInt803 = (class47.anInt796 - cameraY2 << 8) / j3;
                class47.anInt804 = (class47.anInt797 - cameraY2 << 8) / j3;
                aClass47Array476[anInt475++] = class47;
                continue;
            }
            if (class47.anInt791 == 2) {
                int i1 = (class47.anInt789 - screenCenterZ) + 25;
                if (i1 < 0 || i1 > 50)
                    continue;
                int l1 = (class47.anInt787 - screenCenterX) + 25;
                if (l1 < 0)
                    l1 = 0;
                int k2 = (class47.anInt788 - screenCenterX) + 25;
                if (k2 > 50)
                    k2 = 50;
                boolean flag1 = false;
                while (l1 <= k2)
                    if (renderArea[l1++][i1]) {
                        flag1 = true;
                        break;
                    }
                if (!flag1)
                    continue;
                int k3 = cameraZ2 - class47.anInt794;
                if (k3 > 32) {
                    class47.anInt798 = 3;
                } else {
                    if (k3 >= -32)
                        continue;
                    class47.anInt798 = 4;
                    k3 = -k3;
                }
                class47.anInt799 = (class47.anInt792 - cameraX2 << 8) / k3;
                class47.anInt800 = (class47.anInt793 - cameraX2 << 8) / k3;
                class47.anInt803 = (class47.anInt796 - cameraY2 << 8) / k3;
                class47.anInt804 = (class47.anInt797 - cameraY2 << 8) / k3;
                aClass47Array476[anInt475++] = class47;
            } else if (class47.anInt791 == 4) {
                int j1 = class47.anInt796 - cameraY2;
                if (j1 > 128) {
                    int i2 = (class47.anInt789 - screenCenterZ) + 25;
                    if (i2 < 0)
                        i2 = 0;
                    int l2 = (class47.anInt790 - screenCenterZ) + 25;
                    if (l2 > 50)
                        l2 = 50;
                    if (i2 <= l2) {
                        int i3 = (class47.anInt787 - screenCenterX) + 25;
                        if (i3 < 0)
                            i3 = 0;
                        int l3 = (class47.anInt788 - screenCenterX) + 25;
                        if (l3 > 50)
                            l3 = 50;
                        boolean flag2 = false;
                        label0:
                        for (int i4 = i3; i4 <= l3; i4++) {
                            for (int j4 = i2; j4 <= l2; j4++) {
                                if (!renderArea[i4][j4])
                                    continue;
                                flag2 = true;
                                break label0;
                            }

                        }

                        if (flag2) {
                            class47.anInt798 = 5;
                            class47.anInt799 = (class47.anInt792 - cameraX2 << 8) / j1;
                            class47.anInt800 = (class47.anInt793 - cameraX2 << 8) / j1;
                            class47.anInt801 = (class47.anInt794 - cameraZ2 << 8) / j1;
                            class47.anInt802 = (class47.anInt795 - cameraZ2 << 8) / j1;
                            aClass47Array476[anInt475++] = class47;
                        }
                    }
                }
            }
        }

    }

    private boolean method320(int i, int j, int k) {
        int l = anIntArrayArrayArray445[i][j][k];
        if (l == -cycle)
            return false;
        if (l == cycle)
            return true;
        int i1 = j << 7;
        int j1 = k << 7;
        if (method324(i1 + 1, tileHeights[i][j][k], j1 + 1) && method324((i1 + 128) - 1, tileHeights[i][j + 1][k], j1 + 1) && method324((i1 + 128) - 1, tileHeights[i][j + 1][k + 1], (j1 + 128) - 1) && method324(i1 + 1, tileHeights[i][j][k + 1], (j1 + 128) - 1)) {
            anIntArrayArrayArray445[i][j][k] = cycle;
            return true;
        } else {
            anIntArrayArrayArray445[i][j][k] = -cycle;
            return false;
        }
    }

    private boolean method321(int i, int j, int k, int l) {
        if (!method320(i, j, k))
            return false;
        int i1 = j << 7;
        int j1 = k << 7;
        int k1 = tileHeights[i][j][k] - 1;
        int l1 = k1 - 120;
        int i2 = k1 - 230;
        int j2 = k1 - 238;
        if (l < 16) {
            if (l == 1) {
                if (i1 > cameraX2) {
                    if (!method324(i1, k1, j1))
                        return false;
                    if (!method324(i1, k1, j1 + 128))
                        return false;
                }
                if (i > 0) {
                    if (!method324(i1, l1, j1))
                        return false;
                    if (!method324(i1, l1, j1 + 128))
                        return false;
                }
                return method324(i1, i2, j1) && method324(i1, i2, j1 + 128);
            }
            if (l == 2) {
                if (j1 < cameraZ2) {
                    if (!method324(i1, k1, j1 + 128))
                        return false;
                    if (!method324(i1 + 128, k1, j1 + 128))
                        return false;
                }
                if (i > 0) {
                    if (!method324(i1, l1, j1 + 128))
                        return false;
                    if (!method324(i1 + 128, l1, j1 + 128))
                        return false;
                }
                return method324(i1, i2, j1 + 128) && method324(i1 + 128, i2, j1 + 128);
            }
            if (l == 4) {
                if (i1 < cameraX2) {
                    if (!method324(i1 + 128, k1, j1))
                        return false;
                    if (!method324(i1 + 128, k1, j1 + 128))
                        return false;
                }
                if (i > 0) {
                    if (!method324(i1 + 128, l1, j1))
                        return false;
                    if (!method324(i1 + 128, l1, j1 + 128))
                        return false;
                }
                return method324(i1 + 128, i2, j1) && method324(i1 + 128, i2, j1 + 128);
            }
            if (l == 8) {
                if (j1 > cameraZ2) {
                    if (!method324(i1, k1, j1))
                        return false;
                    if (!method324(i1 + 128, k1, j1))
                        return false;
                }
                if (i > 0) {
                    if (!method324(i1, l1, j1))
                        return false;
                    if (!method324(i1 + 128, l1, j1))
                        return false;
                }
                return method324(i1, i2, j1) && method324(i1 + 128, i2, j1);
            }
        }
        if (!method324(i1 + 64, j2, j1 + 64))
            return false;
        if (l == 16)
            return method324(i1, i2, j1 + 128);
        if (l == 32)
            return method324(i1 + 128, i2, j1 + 128);
        if (l == 64)
            return method324(i1 + 128, i2, j1);
        if (l == 128) {
            return method324(i1, i2, j1);
        } else {
            System.out.println("Warning unsupported wall type");
            return true;
        }
    }

    private boolean method322(int i, int j, int k, int l) {
        if (!method320(i, j, k))
            return false;
        int i1 = j << 7;
        int j1 = k << 7;
        return method324(i1 + 1, tileHeights[i][j][k] - l, j1 + 1) && method324((i1 + 128) - 1, tileHeights[i][j + 1][k] - l, j1 + 1) && method324((i1 + 128) - 1, tileHeights[i][j + 1][k + 1] - l, (j1 + 128) - 1) && method324(i1 + 1, tileHeights[i][j][k + 1] - l, (j1 + 128) - 1);
    }

    private boolean method323(int i, int j, int k, int l, int i1, int j1) {
        if (j == k && l == i1) {
            if (!method320(i, j, l))
                return false;
            int k1 = j << 7;
            int i2 = l << 7;
            return method324(k1 + 1, tileHeights[i][j][l] - j1, i2 + 1) && method324((k1 + 128) - 1, tileHeights[i][j + 1][l] - j1, i2 + 1) && method324((k1 + 128) - 1, tileHeights[i][j + 1][l + 1] - j1, (i2 + 128) - 1) && method324(k1 + 1, tileHeights[i][j][l + 1] - j1, (i2 + 128) - 1);
        }
        for (int l1 = j; l1 <= k; l1++) {
            for (int j2 = l; j2 <= i1; j2++)
                if (anIntArrayArrayArray445[i][l1][j2] == -cycle)
                    return false;

        }

        int k2 = (j << 7) + 1;
        int l2 = (l << 7) + 2;
        int i3 = tileHeights[i][j][l] - j1;
        if (!method324(k2, i3, l2))
            return false;
        int j3 = (k << 7) - 1;
        if (!method324(j3, i3, l2))
            return false;
        int k3 = (i1 << 7) - 1;
        return method324(k2, i3, k3) && method324(j3, i3, k3);
    }

    private boolean method324(int i, int j, int k) {
        for (int l = 0; l < anInt475; l++) {
            SceneCluster class47 = aClass47Array476[l];
            if (class47.anInt798 == 1) {
                int i1 = class47.anInt792 - i;
                if (i1 > 0) {
                    int j2 = class47.anInt794 + (class47.anInt801 * i1 >> 8);
                    int k3 = class47.anInt795 + (class47.anInt802 * i1 >> 8);
                    int l4 = class47.anInt796 + (class47.anInt803 * i1 >> 8);
                    int i6 = class47.anInt797 + (class47.anInt804 * i1 >> 8);
                    if (k >= j2 && k <= k3 && j >= l4 && j <= i6)
                        return true;
                }
            } else if (class47.anInt798 == 2) {
                int j1 = i - class47.anInt792;
                if (j1 > 0) {
                    int k2 = class47.anInt794 + (class47.anInt801 * j1 >> 8);
                    int l3 = class47.anInt795 + (class47.anInt802 * j1 >> 8);
                    int i5 = class47.anInt796 + (class47.anInt803 * j1 >> 8);
                    int j6 = class47.anInt797 + (class47.anInt804 * j1 >> 8);
                    if (k >= k2 && k <= l3 && j >= i5 && j <= j6)
                        return true;
                }
            } else if (class47.anInt798 == 3) {
                int k1 = class47.anInt794 - k;
                if (k1 > 0) {
                    int l2 = class47.anInt792 + (class47.anInt799 * k1 >> 8);
                    int i4 = class47.anInt793 + (class47.anInt800 * k1 >> 8);
                    int j5 = class47.anInt796 + (class47.anInt803 * k1 >> 8);
                    int k6 = class47.anInt797 + (class47.anInt804 * k1 >> 8);
                    if (i >= l2 && i <= i4 && j >= j5 && j <= k6)
                        return true;
                }
            } else if (class47.anInt798 == 4) {
                int l1 = k - class47.anInt794;
                if (l1 > 0) {
                    int i3 = class47.anInt792 + (class47.anInt799 * l1 >> 8);
                    int j4 = class47.anInt793 + (class47.anInt800 * l1 >> 8);
                    int k5 = class47.anInt796 + (class47.anInt803 * l1 >> 8);
                    int l6 = class47.anInt797 + (class47.anInt804 * l1 >> 8);
                    if (i >= i3 && i <= j4 && j >= k5 && j <= l6)
                        return true;
                }
            } else if (class47.anInt798 == 5) {
                int i2 = j - class47.anInt796;
                if (i2 > 0) {
                    int j3 = class47.anInt792 + (class47.anInt799 * i2 >> 8);
                    int k4 = class47.anInt793 + (class47.anInt800 * i2 >> 8);
                    int l5 = class47.anInt794 + (class47.anInt801 * i2 >> 8);
                    int i7 = class47.anInt795 + (class47.anInt802 * i2 >> 8);
                    if (i >= j3 && i <= k4 && k >= l5 && k <= i7)
                        return true;
                }
            }
        }

        return false;
    }
}
