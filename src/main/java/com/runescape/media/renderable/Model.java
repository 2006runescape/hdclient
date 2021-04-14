package com.runescape.media.renderable;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.Client;
import com.runescape.gpu.GpuPlugin;
import com.runescape.gpu.Perspective;
import com.runescape.media.*;
import com.runescape.net.requester.OnDemandFetcherParent;
import com.runescape.net.Stream;

public final class Model extends Renderable {

    public float[][] getFaceTextureUCoordinates() {
        computeTextureUVCoordinates();
        return faceTextureUCoordinates;
    }

    public float[][] getFaceTextureVCoordinates() {
        computeTextureUVCoordinates();
        return faceTextureVCoordinates;
    }

    public transient float[][] faceTextureUCoordinates;
    public transient float[][] faceTextureVCoordinates;

    public boolean uvsCalculated = false;

    public void computeTextureUVCoordinates() {
        if (uvsCalculated) {
            return;
        }
        this.faceTextureUCoordinates = new float[trianglesCount][];
        this.faceTextureVCoordinates = new float[trianglesCount][];

        for (int i = 0; i < trianglesCount; i++) {
            int textureCoordinate;
            if (textures == null) {
                textureCoordinate = -1;
            } else {
                textureCoordinate = textures[i];
            }

            final int textureIdx;
            if (materials == null) {
                textureIdx = -1;
            } else {
                textureIdx = materials[i] & 0xFFFF;
            }

            if (textureIdx != -1) {
                final float[] u = new float[3];
                final float[] v = new float[3];

                if (textureCoordinate == -1) {
                    u[0] = 0.0F;
                    v[0] = 1.0F;

                    u[1] = 1.0F;
                    v[1] = 1.0F;

                    u[2] = 0.0F;
                    v[2] = 0.0F;
                } else {
                    textureCoordinate &= 0xFF;

                    byte textureRenderType = 0;
                    if (textureTypes != null) {
                        textureRenderType = textureTypes[textureCoordinate];
                    }

                    if (textureRenderType == 0) {
                        final int faceVertexIdx1 = trianglesX[i];
                        final int faceVertexIdx2 = trianglesY[i];
                        final int faceVertexIdx3 = trianglesZ[i];

                        final short triangleVertexIdx1 = (short) texturesX[textureCoordinate];
                        final short triangleVertexIdx2 = (short) texturesY[textureCoordinate];
                        final short triangleVertexIdx3 = (short) texturesZ[textureCoordinate];

                        final float triangleX = (float) verticesX[triangleVertexIdx1];
                        final float triangleY = (float) verticesY[triangleVertexIdx1];
                        final float triangleZ = (float) verticesZ[triangleVertexIdx1];

                        final float f_882_ = (float) verticesX[triangleVertexIdx2] - triangleX;
                        final float f_883_ = (float) verticesY[triangleVertexIdx2] - triangleY;
                        final float f_884_ = (float) verticesZ[triangleVertexIdx2] - triangleZ;
                        final float f_885_ = (float) verticesX[triangleVertexIdx3] - triangleX;
                        final float f_886_ = (float) verticesY[triangleVertexIdx3] - triangleY;
                        final float f_887_ = (float) verticesZ[triangleVertexIdx3] - triangleZ;
                        final float f_888_ = (float) verticesX[faceVertexIdx1] - triangleX;
                        final float f_889_ = (float) verticesY[faceVertexIdx1] - triangleY;
                        final float f_890_ = (float) verticesZ[faceVertexIdx1] - triangleZ;
                        final float f_891_ = (float) verticesX[faceVertexIdx2] - triangleX;
                        final float f_892_ = (float) verticesY[faceVertexIdx2] - triangleY;
                        final float f_893_ = (float) verticesZ[faceVertexIdx2] - triangleZ;
                        final float f_894_ = (float) verticesX[faceVertexIdx3] - triangleX;
                        final float f_895_ = (float) verticesY[faceVertexIdx3] - triangleY;
                        final float f_896_ = (float) verticesZ[faceVertexIdx3] - triangleZ;

                        final float f_897_ = f_883_ * f_887_ - f_884_ * f_886_;
                        final float f_898_ = f_884_ * f_885_ - f_882_ * f_887_;
                        final float f_899_ = f_882_ * f_886_ - f_883_ * f_885_;
                        float f_900_ = f_886_ * f_899_ - f_887_ * f_898_;
                        float f_901_ = f_887_ * f_897_ - f_885_ * f_899_;
                        float f_902_ = f_885_ * f_898_ - f_886_ * f_897_;
                        float f_903_ = 1.0F / (f_900_ * f_882_ + f_901_ * f_883_ + f_902_ * f_884_);

                        u[0] = (f_900_ * f_888_ + f_901_ * f_889_ + f_902_ * f_890_) * f_903_;
                        u[1] = (f_900_ * f_891_ + f_901_ * f_892_ + f_902_ * f_893_) * f_903_;
                        u[2] = (f_900_ * f_894_ + f_901_ * f_895_ + f_902_ * f_896_) * f_903_;

                        f_900_ = f_883_ * f_899_ - f_884_ * f_898_;
                        f_901_ = f_884_ * f_897_ - f_882_ * f_899_;
                        f_902_ = f_882_ * f_898_ - f_883_ * f_897_;
                        f_903_ = 1.0F / (f_900_ * f_885_ + f_901_ * f_886_ + f_902_ * f_887_);

                        v[0] = (f_900_ * f_888_ + f_901_ * f_889_ + f_902_ * f_890_) * f_903_;
                        v[1] = (f_900_ * f_891_ + f_901_ * f_892_ + f_902_ * f_893_) * f_903_;
                        v[2] = (f_900_ * f_894_ + f_901_ * f_895_ + f_902_ * f_896_) * f_903_;
                    }
                }

                this.faceTextureUCoordinates[i] = u;
                this.faceTextureVCoordinates[i] = v;
            }
        }
        uvsCalculated = true;
    }

    public byte[] textureTypes;
    public byte[] textures;
    public short[] materials;

    void decodeOld(final byte[] data) {
        boolean var2 = false;
        boolean var3 = false;
        final Stream var4 = new Stream(data);
        final Stream var5 = new Stream(data);
        final Stream var6 = new Stream(data);
        final Stream var7 = new Stream(data);
        final Stream var8 = new Stream(data);
        var4.currentOffset = data.length - 18;
        final int var9 = var4.readUnsignedWord();
        final int var10 = var4.readUnsignedWord();
        final int var11 = var4.readUnsignedByte();
        final int var12 = var4.readUnsignedByte();
        final int var13 = var4.readUnsignedByte();
        final int var14 = var4.readUnsignedByte();
        final int var15 = var4.readUnsignedByte();
        final int var16 = var4.readUnsignedByte();
        final int var17 = var4.readUnsignedWord();
        final int var18 = var4.readUnsignedWord();
        final int var19 = var4.readUnsignedWord();
        final int var20 = var4.readUnsignedWord();
        final byte var21 = 0;
        int var45 = var21 + var9;
        final int var23 = var45;
        var45 += var10;
        final int var24 = var45;
        if (var13 == 255) {
            var45 += var10;
        }
        final int var25 = var45;
        if (var15 == 1) {
            var45 += var10;
        }
        final int var26 = var45;
        if (var12 == 1) {
            var45 += var10;
        }
        final int var27 = var45;
        if (var16 == 1) {
            var45 += var9;
        }
        final int var28 = var45;
        if (var14 == 1) {
            var45 += var10;
        }
        final int var29 = var45;
        var45 += var20;
        final int var30 = var45;
        var45 += var10 * 2;
        final int var31 = var45;
        var45 += var11 * 6;
        final int var32 = var45;
        var45 += var17;
        final int var33 = var45;
        var45 += var18;
        this.verticesCount = var9;
        this.trianglesCount = var10;
        this.texturesCount = var11;
        this.verticesX = new int[var9];
        this.verticesY = new int[var9];
        this.verticesZ = new int[var9];
        this.trianglesX = new int[var10];
        this.trianglesY = new int[var10];
        this.trianglesZ = new int[var10];
        if (var11 > 0) {
            this.textureTypes = new byte[var11];
            this.texturesX = new int[var11];
            this.texturesY = new int[var11];
            this.texturesZ = new int[var11];
        }
        if (var16 == 1) {
            this.vertexData = new int[var9];
        }
        if (var12 == 1) {
            this.types = new int[var10];
            this.textures = new byte[var10];
            this.materials = new short[var10];
        }
        if (var13 == 255) {
            this.priorities = new int[var10];
        } else {
            this.priority = (byte) var13;
        }
        if (var14 == 1) {
            this.alphas = new int[var10];
        }
        if (var15 == 1) {
            this.triangleData = new int[var10];
        }
        this.colors = new int[var10];
        var4.currentOffset = var21;
        var5.currentOffset = var32;
        var6.currentOffset = var33;
        var7.currentOffset = var45;
        var8.currentOffset = var27;
        int var35 = 0;
        int var36 = 0;
        int var37 = 0;
        int var38;
        int var39;
        int var40;
        int var41;
        int var42;
        for (var38 = 0; var38 < var9; ++var38) {
            var39 = var4.readUnsignedByte();
            var40 = 0;
            if ((var39 & 1) != 0) {
                var40 = var5.method421();
            }
            var41 = 0;
            if ((var39 & 2) != 0) {
                var41 = var6.method421();
            }
            var42 = 0;
            if ((var39 & 4) != 0) {
                var42 = var7.method421();
            }
            this.verticesX[var38] = var35 + var40;
            this.verticesY[var38] = var36 + var41;
            this.verticesZ[var38] = var37 + var42;
            var35 = this.verticesX[var38];
            var36 = this.verticesY[var38];
            var37 = this.verticesZ[var38];
            if (var16 == 1) {
                this.vertexData[var38] = var8.readUnsignedByte();
            }
        }
        var4.currentOffset = var30;
        var5.currentOffset = var26;
        var6.currentOffset = var24;
        var7.currentOffset = var28;
        var8.currentOffset = var25;
        for (var38 = 0; var38 < var10; ++var38) {
            this.colors[var38] = (short) var4.readUnsignedWord();
            if (var12 == 1) {
                var39 = var5.readUnsignedByte();
                if ((var39 & 1) == 1) {
                    this.types[var38] = 1;
                    var2 = true;
                } else {
                    this.types[var38] = 0;
                }
                if ((var39 & 2) == 2) {
                    this.textures[var38] = (byte) (var39 >> 2);
                    this.materials[var38] = (short) this.colors[var38];
                    this.colors[var38] = 127;
                    if (this.materials[var38] != -1) {
                        var3 = true;
                    }
                } else {
                    this.textures[var38] = -1;
                    this.materials[var38] = -1;
                }
            }
            if (var13 == 255) {
                this.priorities[var38] = var6.readSignedByte();
            }
            if (var14 == 1) {
                this.alphas[var38] = var7.readSignedByte();
                if (this.alphas[var38] < 0) {
                    this.alphas[var38] = 256 + this.alphas[var38];
                }
            }
            if (var15 == 1) {
                this.triangleData[var38] = var8.readUnsignedByte();
            }
        }
        var4.currentOffset = var29;
        var5.currentOffset = var23;
        var38 = 0;
        var39 = 0;
        var40 = 0;
        var41 = 0;
        int var43;
        int var44;
        for (var42 = 0; var42 < var10; ++var42) {
            var43 = var5.readUnsignedByte();
            if (var43 == 1) {
                var38 = var4.method421() + var41;
                var39 = var4.method421() + var38;
                var40 = var4.method421() + var39;
                var41 = var40;
                this.trianglesX[var42] = var38;
                this.trianglesY[var42] = var39;
                this.trianglesZ[var42] = var40;
            }
            if (var43 == 2) {
                var39 = var40;
                var40 = var4.method421() + var41;
                var41 = var40;
                this.trianglesX[var42] = var38;
                this.trianglesY[var42] = var39;
                this.trianglesZ[var42] = var40;
            }
            if (var43 == 3) {
                var38 = var40;
                var40 = var4.method421() + var41;
                var41 = var40;
                this.trianglesX[var42] = var38;
                this.trianglesY[var42] = var39;
                this.trianglesZ[var42] = var40;
            }
            if (var43 == 4) {
                var44 = var38;
                var38 = var39;
                var39 = var44;
                var40 = var4.method421() + var41;
                var41 = var40;
                this.trianglesX[var42] = var38;
                this.trianglesY[var42] = var44;
                this.trianglesZ[var42] = var40;
            }
        }
        var4.currentOffset = var31;
        for (var42 = 0; var42 < var11; ++var42) {
            this.textureTypes[var42] = 0;
            this.texturesX[var42] = (short) var4.readUnsignedWord();
            this.texturesY[var42] = (short) var4.readUnsignedWord();
            this.texturesZ[var42] = (short) var4.readUnsignedWord();
        }
        if (this.textures != null) {
            boolean var46 = false;
            for (var43 = 0; var43 < var10; ++var43) {
                var44 = this.textures[var43] & 255;
                if (var44 != 255) {
                    if (this.trianglesX[var43] == (this.texturesX[var44] & '\uffff') && this.trianglesY[var43] == (this.texturesY[var44] & '\uffff') && this.trianglesZ[var43] == (this.texturesZ[var44] & '\uffff')) {
                        this.textures[var43] = -1;
                    } else {
                        var46 = true;
                    }
                }
            }
            if (!var46) {
                this.textures = null;
            }
        }
        if (!var3) {
            this.materials = null;
        }
        if (!var2) {
            this.types = null;
        }
    }

    public short[] getFaceTextures() {
        return materials;
    }

    public int getRadius() {
        return radius;
    }

    public int getModelHeight() {
        return modelHeight;
    }

    public int getXYZMag() {
        return XYZMag;
    }

    public int getTrianglesCount() {
        return trianglesCount;
    }

    public int bufferOffset = -1;

    public void setBufferOffset(final int offset) {
        bufferOffset = offset;
    }

    public int uVBufferOffset = -1;

    public void setUvBufferOffset(final int offset) {
        uVBufferOffset = offset;
    }

    public int getBufferOffset() {
        return bufferOffset;
    }

    public int sceneId = -1;

    public void setSceneId(final int id) {
        sceneId = id;
    }

    public int getSceneId() {
        return sceneId;
    }

    public int getUvBufferOffset() {
        return uVBufferOffset;
    }

    public void setModelHeight(final int height) {
        super.modelHeight = height;
    }

    public int[] getVerticesX() {
        return verticesX;
    }

    public int[] getVerticesY() {
        return verticesY;
    }

    public int[] getVerticesZ() {
        return verticesZ;
    }

    public int[] getTrianglesX() {
        return trianglesX;
    }

    public int[] getTrianglesY() {
        return trianglesY;
    }

    public int[] getTrianglesZ() {
        return trianglesZ;
    }

    public int[] getFaceColors1() {
        return colorsX;
    }

    public int[] getFaceColors2() {
        return colorsY;
    }

    public int[] getFaceColors3() {
        return colorsZ;
    }

    public int[] getFaceRenderPriorities() {
        return priorities;
    }

    public int[] getTriangleTransparencies() {
        return alphas;
    }

    public static final Model aModel_1621 = new Model();
    public static final int[] anIntArray1688 = new int[1000];
    private static final int[] anIntArray1678 = new int[10];
    private static final int[] anIntArray1679 = new int[10];
    private static final int[] anIntArray1680 = new int[10];
    public static boolean aBoolean1684;
    public static int anInt1685;
    public static int anInt1686;
    public static int anInt1687;
    public static int sine[];
    public static int cosine[];
    private static int[] anIntArray1622 = new int[2000];
    private static int[] anIntArray1623 = new int[2000];
    private static int[] anIntArray1624 = new int[2000];
    private static int[] anIntArray1625 = new int[2000];
    private static ModelHeader[] aClass21Array1661;
    private static OnDemandFetcherParent aOnDemandFetcherParent_1662;
    private static boolean[] hidden = new boolean[4096];
    private static boolean[] hide = new boolean[4096];
    private static int[] viewportX = new int[4096];
    private static int[] viewportY = new int[4096];
    private static int[] anIntArray1667 = new int[4096];
    private static int[] viewportTextureX = new int[4096];
    private static int[] viewportTextureY = new int[4096];
    private static int[] viewportTextureZ = new int[4096];
    private static int[] anIntArray1671 = new int[1500];
    private static int[][] anIntArrayArray1672 = new int[1500][512];
    private static int[] anIntArray1673 = new int[12];
    private static int[][] anIntArrayArray1674 = new int[12][2000];
    private static int[] anIntArray1675 = new int[2000];
    private static int[] anIntArray1676 = new int[2000];
    private static int[] anIntArray1677 = new int[12];
    private static int animateX;
    private static int animateY;
    private static int animateZ;
    private static int[] palette;
    private static int[] modelIntArray4;

    static {
        sine = Rasterizer3D.anIntArray1470;
        cosine = Rasterizer3D.anIntArray1471;
        palette = Rasterizer3D.anIntArray1482;
        modelIntArray4 = Rasterizer3D.anIntArray1469;
    }

    public int verticesCount;
    public int verticesX[];
    public int verticesY[];
    public int verticesZ[];
    public int trianglesCount;
    public int trianglesX[];
    public int trianglesY[];
    public int trianglesZ[];
    public int types[];
    public int colors[];
    public int anInt1646;
    public int anInt1647;
    public int anInt1648;
    public int anInt1649;
    public int XYZMag;
    public int bottomY;
    public int anInt1654;
    public int vertexGroups[][];
    public int triangleGroups[][];
    public boolean aBoolean1659;
    public FaceNormal[] faceNormals;
    public VertexNormal[] vertexNormals;
    public VertexNormal[] vertexNormalsOffsets;
    private int[] colorsX;
    private int[] colorsY;
    private int[] colorsZ;
    private int[] priorities;
    private int[] alphas;
    private int priority;
    private int texturesCount;
    private int[] texturesX;
    private int[] texturesY;
    private int[] texturesZ;
    private int diameter;
    private int radius;
    private int[] vertexData;
    private int[] triangleData;
    private Model() {
        aBoolean1659 = false;
    }

    private Model(int i) {
        if (true) {
            decodeOld(aClass21Array1661[i].aByteArray368);
            return;
        }
        aBoolean1659 = false;
        ModelHeader class21 = aClass21Array1661[i];
        verticesCount = class21.anInt369;
        trianglesCount = class21.anInt370;
        texturesCount = class21.anInt371;
        verticesX = new int[verticesCount];
        verticesY = new int[verticesCount];
        verticesZ = new int[verticesCount];
        trianglesX = new int[trianglesCount];
        trianglesY = new int[trianglesCount];
        trianglesZ = new int[trianglesCount];
        texturesX = new int[texturesCount];
        texturesY = new int[texturesCount];
        texturesZ = new int[texturesCount];
        if (class21.anInt376 >= 0)
            vertexData = new int[verticesCount];
        if (class21.anInt380 >= 0)
            types = new int[trianglesCount];
        if (class21.anInt381 >= 0)
            priorities = new int[trianglesCount];
        else
            priority = -class21.anInt381 - 1;
        if (class21.anInt382 >= 0)
            alphas = new int[trianglesCount];
        if (class21.anInt383 >= 0)
            triangleData = new int[trianglesCount];
        colors = new int[trianglesCount];
        Stream stream = new Stream(class21.aByteArray368);
        stream.currentOffset = class21.anInt372;
        Stream stream_1 = new Stream(class21.aByteArray368);
        stream_1.currentOffset = class21.anInt373;
        Stream stream_2 = new Stream(class21.aByteArray368);
        stream_2.currentOffset = class21.anInt374;
        Stream stream_3 = new Stream(class21.aByteArray368);
        stream_3.currentOffset = class21.anInt375;
        Stream stream_4 = new Stream(class21.aByteArray368);
        stream_4.currentOffset = class21.anInt376;
        int k = 0;
        int l = 0;
        int i1 = 0;
        for (int j1 = 0; j1 < verticesCount; j1++) {
            int k1 = stream.readUnsignedByte();
            int i2 = 0;
            if ((k1 & 1) != 0)
                i2 = stream_1.method421();
            int k2 = 0;
            if ((k1 & 2) != 0)
                k2 = stream_2.method421();
            int i3 = 0;
            if ((k1 & 4) != 0)
                i3 = stream_3.method421();
            verticesX[j1] = k + i2;
            verticesY[j1] = l + k2;
            verticesZ[j1] = i1 + i3;
            k = verticesX[j1];
            l = verticesY[j1];
            i1 = verticesZ[j1];
            if (vertexData != null)
                vertexData[j1] = stream_4.readUnsignedByte();
        }

        stream.currentOffset = class21.anInt379;
        stream_1.currentOffset = class21.anInt380;
        stream_2.currentOffset = class21.anInt381;
        stream_3.currentOffset = class21.anInt382;
        stream_4.currentOffset = class21.anInt383;
        for (int l1 = 0; l1 < trianglesCount; l1++) {
            colors[l1] = stream.readUnsignedWord();
            if (types != null)
                types[l1] = stream_1.readUnsignedByte();
            if (priorities != null)
                priorities[l1] = stream_2.readUnsignedByte();
            if (alphas != null)
                alphas[l1] = stream_3.readUnsignedByte();
            if (triangleData != null)
                triangleData[l1] = stream_4.readUnsignedByte();
        }

        stream.currentOffset = class21.anInt377;
        stream_1.currentOffset = class21.anInt378;
        int j2 = 0;
        int l2 = 0;
        int j3 = 0;
        int k3 = 0;
        for (int l3 = 0; l3 < trianglesCount; l3++) {
            int i4 = stream_1.readUnsignedByte();
            if (i4 == 1) {
                j2 = stream.method421() + k3;
                k3 = j2;
                l2 = stream.method421() + k3;
                k3 = l2;
                j3 = stream.method421() + k3;
                k3 = j3;
                trianglesX[l3] = j2;
                trianglesY[l3] = l2;
                trianglesZ[l3] = j3;
            }
            if (i4 == 2) {
                j2 = j2;
                l2 = j3;
                j3 = stream.method421() + k3;
                k3 = j3;
                trianglesX[l3] = j2;
                trianglesY[l3] = l2;
                trianglesZ[l3] = j3;
            }
            if (i4 == 3) {
                j2 = j3;
                l2 = l2;
                j3 = stream.method421() + k3;
                k3 = j3;
                trianglesX[l3] = j2;
                trianglesY[l3] = l2;
                trianglesZ[l3] = j3;
            }
            if (i4 == 4) {
                int k4 = j2;
                j2 = l2;
                l2 = k4;
                j3 = stream.method421() + k3;
                k3 = j3;
                trianglesX[l3] = j2;
                trianglesY[l3] = l2;
                trianglesZ[l3] = j3;
            }
        }

        stream.currentOffset = class21.anInt384;
        for (int j4 = 0; j4 < texturesCount; j4++) {
            texturesX[j4] = stream.readUnsignedWord();
            texturesY[j4] = stream.readUnsignedWord();
            texturesZ[j4] = stream.readUnsignedWord();
        }

    }

    public Model(final int count, final Model[] models) {
        aBoolean1659 = false;
        boolean var3 = false;
        boolean var4 = false;
        boolean var5 = false;
        boolean var6 = false;
        boolean var7 = false;
        boolean var8 = false;
        verticesCount = 0;
        trianglesCount = 0;
        texturesCount = 0;
        priority = -1;

        int var9;
        Model var10;
        for (var9 = 0; var9 < count; ++var9) {
            var10 = models[var9];
            if (var10 != null) {
                verticesCount += var10.verticesCount;
                trianglesCount += var10.trianglesCount;
                texturesCount += var10.texturesCount;
                if (var10.priorities != null) {
                    var4 = true;
                } else {
                    if (priority == -1) {
                        priority = var10.priority;
                    }

                    if (priority != var10.priority) {
                        var4 = true;
                    }
                }

                var3 |= var10.types != null;
                var5 |= var10.alphas != null;
                var6 |= var10.triangleData != null;
                var7 |= var10.materials != null;
                var8 |= var10.textures != null;
            }
        }

        verticesX = new int[verticesCount];
        verticesY = new int[verticesCount];
        verticesZ = new int[verticesCount];
        vertexData = new int[verticesCount];
        trianglesX = new int[trianglesCount];
        trianglesY = new int[trianglesCount];
        trianglesZ = new int[trianglesCount];
        if (var3) {
            types = new int[trianglesCount];
        }

        if (var4) {
            priorities = new int[trianglesCount];
        }

        if (var5) {
            alphas = new int[trianglesCount];
        }

        if (var6) {
            triangleData = new int[trianglesCount];
        }

        if (var7) {
            materials = new short[trianglesCount];
        }

        if (var8) {
            textures = new byte[trianglesCount];
        }

        colors = new int[trianglesCount];
        if (texturesCount > 0) {
            textureTypes = new byte[texturesCount];
            texturesX = new int[texturesCount];
            texturesY = new int[texturesCount];
            texturesZ = new int[texturesCount];
        }

        verticesCount = 0;
        trianglesCount = 0;
        texturesCount = 0;

        for (var9 = 0; var9 < count; ++var9) {
            var10 = models[var9];
            if (var10 != null) {
                int var11;
                for (var11 = 0; var11 < var10.trianglesCount; ++var11) {
                    if (var3 && var10.types != null) {
                        types[trianglesCount] = var10.types[var11];
                    }

                    if (var4) {
                        if (var10.priorities != null) {
                            priorities[trianglesCount] = var10.priorities[var11];
                        } else {
                            priorities[trianglesCount] = var10.priority;
                        }
                    }

                    if (var5 && var10.alphas != null) {
                        alphas[trianglesCount] = var10.alphas[var11];
                    }

                    if (var6 && var10.triangleData != null) {
                        triangleData[trianglesCount] = var10.triangleData[var11];
                    }

                    if (var7) {
                        if (var10.materials != null) {
                            materials[trianglesCount] = var10.materials[var11];
                        } else {
                            materials[trianglesCount] = -1;
                        }
                    }

                    if (var8) {
                        if (var10.textures != null && var10.textures[var11] != -1) {
                            textures[trianglesCount] = (byte) (texturesCount + var10.textures[var11]);
                        } else {
                            textures[trianglesCount] = -1;
                        }
                    }

                    colors[trianglesCount] = var10.colors[var11];
                    trianglesX[trianglesCount] = method465(var10, var10.trianglesX[var11]);
                    trianglesY[trianglesCount] = method465(var10, var10.trianglesY[var11]);
                    trianglesZ[trianglesCount] = method465(var10, var10.trianglesZ[var11]);
                    ++trianglesCount;
                }

                for (var11 = 0; var11 < var10.texturesCount; ++var11) {
                    final byte var12 = textureTypes[texturesCount] = var10.textureTypes[var11];
                    if (var12 == 0) {
                        texturesX[texturesCount] = method465(var10, var10.texturesX[var11]);
                        texturesY[texturesCount] = method465(var10, var10.texturesY[var11]);
                        texturesZ[texturesCount] = method465(var10, var10.texturesZ[var11]);
                    }
                    ++texturesCount;
                }
            }
        }
    }

    public Model(final Model[] models) {
        final int count = 2;
        aBoolean1659 = false;
        boolean var3 = false;
        boolean var4 = false;
        boolean var5 = false;
        boolean var6 = false;
        this.verticesCount = 0;
        this.trianglesCount = 0;
        this.texturesCount = 0;
        this.priority = -1;
        int var7;
        Model var8;
        for (var7 = 0; var7 < count; ++var7) {
            var8 = models[var7];
            if (var8 != null) {
                this.verticesCount += var8.verticesCount;
                this.trianglesCount += var8.trianglesCount;
                this.texturesCount += var8.texturesCount;
                if (var8.priorities != null) {
                    var3 = true;
                } else {
                    if (this.priority == -1) {
                        this.priority = var8.priority;
                    }
                    if (this.priority != var8.priority) {
                        var3 = true;
                    }
                }
                var4 |= var8.alphas != null;
                var5 |= var8.materials != null;
                var6 |= var8.textures != null;
            }
        }
        this.verticesX = new int[this.verticesCount];
        this.verticesY = new int[this.verticesCount];
        this.verticesZ = new int[this.verticesCount];
        this.trianglesX = new int[this.trianglesCount];
        this.trianglesY = new int[this.trianglesCount];
        this.trianglesZ = new int[this.trianglesCount];
        this.colorsX = new int[this.trianglesCount];
        this.colorsY = new int[this.trianglesCount];
        this.colorsZ = new int[this.trianglesCount];
        if (var3) {
            this.priorities = new int[this.trianglesCount];
        }
        if (var4) {
            this.alphas = new int[this.trianglesCount];
        }
        if (var5) {
            this.materials = new short[this.trianglesCount];
        }
        if (var6) {
            this.textures = new byte[this.trianglesCount];
        }
        if (this.texturesCount > 0) {
            this.texturesX = new int[this.texturesCount];
            this.texturesY = new int[this.texturesCount];
            this.texturesZ = new int[this.texturesCount];
        }
        this.verticesCount = 0;
        this.trianglesCount = 0;
        this.texturesCount = 0;
        for (var7 = 0; var7 < count; ++var7) {
            var8 = models[var7];
            if (var8 != null) {
                int var9;
                for (var9 = 0; var9 < var8.trianglesCount; ++var9) {
                    this.trianglesX[this.trianglesCount] = this.verticesCount + var8.trianglesX[var9];
                    this.trianglesY[this.trianglesCount] = this.verticesCount + var8.trianglesY[var9];
                    this.trianglesZ[this.trianglesCount] = this.verticesCount + var8.trianglesZ[var9];
                    this.colorsX[this.trianglesCount] = var8.colorsX[var9];
                    this.colorsY[this.trianglesCount] = var8.colorsY[var9];
                    this.colorsZ[this.trianglesCount] = var8.colorsZ[var9];
                    if (var3) {
                        if (var8.priorities != null) {
                            this.priorities[this.trianglesCount] = var8.priorities[var9];
                        } else {
                            this.priorities[this.trianglesCount] = var8.priority;
                        }
                    }
                    if (var4 && var8.alphas != null) {
                        this.alphas[this.trianglesCount] = var8.alphas[var9];
                    }
                    if (var5) {
                        if (var8.materials != null) {
                            this.materials[this.trianglesCount] = var8.materials[var9];
                        } else {
                            this.materials[this.trianglesCount] = -1;
                        }
                    }
                    if (var6) {
                        if (var8.textures != null && var8.textures[var9] != -1) {
                            this.textures[this.trianglesCount] = (byte) (this.texturesCount + var8.textures[var9]);
                        } else {
                            this.textures[this.trianglesCount] = -1;
                        }
                    }
                    ++this.trianglesCount;
                }
                for (var9 = 0; var9 < var8.texturesCount; ++var9) {
                    this.texturesX[this.texturesCount] = this.verticesCount + var8.texturesX[var9];
                    this.texturesY[this.texturesCount] = this.verticesCount + var8.texturesY[var9];
                    this.texturesZ[this.texturesCount] = this.verticesCount + var8.texturesZ[var9];
                    ++this.texturesCount;
                }
                for (var9 = 0; var9 < var8.verticesCount; ++var9) {
                    this.verticesX[this.verticesCount] = var8.verticesX[var9];
                    this.verticesY[this.verticesCount] = var8.verticesY[var9];
                    this.verticesZ[this.verticesCount] = var8.verticesZ[var9];
                    ++this.verticesCount;
                }
            }
        }
        method466();
    }

    public Model(boolean colored, boolean flag1, boolean animated, Model model) {
        aBoolean1659 = false;
        verticesCount = model.verticesCount;
        trianglesCount = model.trianglesCount;
        texturesCount = model.texturesCount;
        int var6;
        if (animated) {
            verticesX = model.verticesX;
            verticesY = model.verticesY;
            verticesZ = model.verticesZ;
        } else {
            verticesX = new int[verticesCount];
            verticesY = new int[verticesCount];
            verticesZ = new int[verticesCount];

            for (var6 = 0; var6 < verticesCount; ++var6) {
                verticesX[var6] = model.verticesX[var6];
                verticesY[var6] = model.verticesY[var6];
                verticesZ[var6] = model.verticesZ[var6];
            }
        }

        if (colored) {
            colors = model.colors;
        } else {
            colors = new int[trianglesCount];

            for (var6 = 0; var6 < trianglesCount; ++var6) {
                colors[var6] = model.colors[var6];
            }
        }

        if (!true && model.materials != null) {
            materials = new short[trianglesCount];

            for (var6 = 0; var6 < trianglesCount; ++var6) {
                materials[var6] = model.materials[var6];
            }
        } else {
            materials = model.materials;
        }

        if (flag1) {
            alphas = model.alphas;
        } else {
            alphas = new int[trianglesCount];
            if (model.alphas == null) {
                for (int l = 0; l < trianglesCount; l++) {
                    alphas[l] = 0;
                }
            } else {
                System.arraycopy(model.alphas, 0, alphas, 0, trianglesCount);
            }
        }

        trianglesX = model.trianglesX;
        trianglesY = model.trianglesY;
        trianglesZ = model.trianglesZ;
        types = model.types;
        priorities = model.priorities;
        textures = model.textures;
        priority = model.priority;
        textureTypes = model.textureTypes;
        texturesX = model.texturesX;
        texturesY = model.texturesY;
        texturesZ = model.texturesZ;
        vertexData = model.vertexData;
        triangleData = model.triangleData;
        vertexGroups = model.vertexGroups;
        triangleGroups = model.triangleGroups;
        vertexNormals = model.vertexNormals;
        faceNormals = model.faceNormals;
        vertexNormalsOffsets = model.vertexNormalsOffsets;
    }

    public Model(boolean adjust, boolean flag1, Model model) {
        aBoolean1659 = false;
        verticesCount = model.verticesCount;
        trianglesCount = model.trianglesCount;
        texturesCount = model.texturesCount;
        if (adjust) {
            verticesY = new int[verticesCount];
            System.arraycopy(model.verticesY, 0, verticesY, 0, verticesCount);
        } else {
            verticesY = model.verticesY;
        }
        colorsX = model.colorsX;
        colorsY = model.colorsY;
        colorsZ = model.colorsZ;
        types = model.types;
        verticesX = model.verticesX;
        verticesZ = model.verticesZ;
        trianglesX = model.trianglesX;
        trianglesY = model.trianglesY;
        trianglesZ = model.trianglesZ;
        priorities = model.priorities;
        alphas = model.alphas;
        textures = model.textures;
        colors = model.colors;
        materials = model.materials;
        priority = model.priority;
        textureTypes = model.textureTypes;
        texturesX = model.texturesX;
        texturesY = model.texturesY;
        texturesZ = model.texturesZ;
        super.modelHeight = model.modelHeight;
        XYZMag = model.XYZMag;
        radius = model.radius;
        diameter = model.diameter;
    }

    public static void nullLoader() {
        aClass21Array1661 = null;
        hidden = null;
        hide = null;
        viewportX = null;
        viewportY = null;
        anIntArray1667 = null;
        viewportTextureX = null;
        viewportTextureY = null;
        viewportTextureZ = null;
        anIntArray1671 = null;
        anIntArrayArray1672 = null;
        anIntArray1673 = null;
        anIntArrayArray1674 = null;
        anIntArray1675 = null;
        anIntArray1676 = null;
        anIntArray1677 = null;
        sine = null;
        cosine = null;
        palette = null;
        modelIntArray4 = null;
    }

    public static void method459(int i, OnDemandFetcherParent onDemandFetcherParent) {
        aClass21Array1661 = new ModelHeader[i];
        aOnDemandFetcherParent_1662 = onDemandFetcherParent;
    }

    public static void method460(byte abyte0[], int j) {
        if (abyte0 == null) {
            ModelHeader class21 = aClass21Array1661[j] = new ModelHeader();
            class21.anInt369 = 0;
            class21.anInt370 = 0;
            class21.anInt371 = 0;
            return;
        }
        Stream stream = new Stream(abyte0);
        stream.currentOffset = abyte0.length - 18;
        ModelHeader class21_1 = aClass21Array1661[j] = new ModelHeader();
        class21_1.aByteArray368 = abyte0;
        class21_1.anInt369 = stream.readUnsignedWord();
        class21_1.anInt370 = stream.readUnsignedWord();
        class21_1.anInt371 = stream.readUnsignedByte();
        int k = stream.readUnsignedByte();
        int l = stream.readUnsignedByte();
        int i1 = stream.readUnsignedByte();
        int j1 = stream.readUnsignedByte();
        int k1 = stream.readUnsignedByte();
        int l1 = stream.readUnsignedWord();
        int i2 = stream.readUnsignedWord();
        int j2 = stream.readUnsignedWord();
        int k2 = stream.readUnsignedWord();
        int l2 = 0;
        class21_1.anInt372 = l2;
        l2 += class21_1.anInt369;
        class21_1.anInt378 = l2;
        l2 += class21_1.anInt370;
        class21_1.anInt381 = l2;
        if (l == 255)
            l2 += class21_1.anInt370;
        else
            class21_1.anInt381 = -l - 1;
        class21_1.anInt383 = l2;
        if (j1 == 1)
            l2 += class21_1.anInt370;
        else
            class21_1.anInt383 = -1;
        class21_1.anInt380 = l2;
        if (k == 1)
            l2 += class21_1.anInt370;
        else
            class21_1.anInt380 = -1;
        class21_1.anInt376 = l2;
        if (k1 == 1)
            l2 += class21_1.anInt369;
        else
            class21_1.anInt376 = -1;
        class21_1.anInt382 = l2;
        if (i1 == 1)
            l2 += class21_1.anInt370;
        else
            class21_1.anInt382 = -1;
        class21_1.anInt377 = l2;
        l2 += k2;
        class21_1.anInt379 = l2;
        l2 += class21_1.anInt370 * 2;
        class21_1.anInt384 = l2;
        l2 += class21_1.anInt371 * 6;
        class21_1.anInt373 = l2;
        l2 += l1;
        class21_1.anInt374 = l2;
        l2 += i2;
        class21_1.anInt375 = l2;
        l2 += j2;
    }

    public static void method461(int j) {
        aClass21Array1661[j] = null;
    }

    public static Model method462(int j) {
        if (aClass21Array1661 == null)
            return null;
        ModelHeader class21 = aClass21Array1661[j];
        if (class21 == null) {
            aOnDemandFetcherParent_1662.method548(j);
            return null;
        } else {
            return new Model(j);
        }
    }

    public static boolean method463(int i) {
        if (aClass21Array1661 == null)
            return false;
        ModelHeader class21 = aClass21Array1661[i];
        if (class21 == null) {
            aOnDemandFetcherParent_1662.method548(i);
            return false;
        } else {
            return true;
        }
    }

    private static int method481(int i, int j, int k) {
        if ((k & 2) == 2) {
            if (j < 0)
                j = 0;
            else if (j > 127)
                j = 127;
            j = 127 - j;
            return j;
        }
        j = j * (i & 0x7f) >> 7;
        if (j < 2)
            j = 2;
        else if (j > 126)
            j = 126;
        return (i & 0xff80) + j;
    }

    public void method464(Model model, boolean flag) {
        verticesCount = model.verticesCount;
        trianglesCount = model.trianglesCount;
        texturesCount = model.texturesCount;
        if (anIntArray1622.length < verticesCount) {
            anIntArray1622 = new int[verticesCount + 100];
            anIntArray1623 = new int[verticesCount + 100];
            anIntArray1624 = new int[verticesCount + 100];
        }
        verticesX = anIntArray1622;
        verticesY = anIntArray1623;
        verticesZ = anIntArray1624;
        for (int k = 0; k < verticesCount; k++) {
            verticesX[k] = model.verticesX[k];
            verticesY[k] = model.verticesY[k];
            verticesZ[k] = model.verticesZ[k];
        }

        if (flag) {
            alphas = model.alphas;
        } else {
            if (anIntArray1625.length < trianglesCount)
                anIntArray1625 = new int[trianglesCount + 100];
            alphas = anIntArray1625;
            if (model.alphas == null) {
                for (int l = 0; l < trianglesCount; l++)
                    alphas[l] = 0;

            } else {
                System.arraycopy(model.alphas, 0, alphas, 0, trianglesCount);

            }
        }
        types = model.types;
        colors = model.colors;
        priorities = model.priorities;
        priority = model.priority;
        triangleGroups = model.triangleGroups;
        vertexGroups = model.vertexGroups;
        trianglesX = model.trianglesX;
        trianglesY = model.trianglesY;
        trianglesZ = model.trianglesZ;
        colorsX = model.colorsX;
        colorsY = model.colorsY;
        colorsZ = model.colorsZ;
        texturesX = model.texturesX;
        texturesY = model.texturesY;
        texturesZ = model.texturesZ;
        textures = model.textures;
        textureTypes = model.textureTypes;
        materials = model.materials;
    }

    private int method465(Model model, int i) {
        int j = -1;
        int k = model.verticesX[i];
        int l = model.verticesY[i];
        int i1 = model.verticesZ[i];
        for (int j1 = 0; j1 < verticesCount; j1++) {
            if (k != verticesX[j1] || l != verticesY[j1] || i1 != verticesZ[j1])
                continue;
            j = j1;
            break;
        }

        if (j == -1) {
            verticesX[verticesCount] = k;
            verticesY[verticesCount] = l;
            verticesZ[verticesCount] = i1;
            if (model.vertexData != null)
                vertexData[verticesCount] = model.vertexData[i];
            j = verticesCount++;
        }
        return j;
    }

    public void method466() {
        super.modelHeight = 0;
        XYZMag = 0;
        bottomY = 0;
        for (int i = 0; i < verticesCount; i++) {
            int j = verticesX[i];
            int k = verticesY[i];
            int l = verticesZ[i];
            if (-k > super.modelHeight)
                super.modelHeight = -k;
            if (k > bottomY)
                bottomY = k;
            int i1 = j * j + l * l;
            if (i1 > XYZMag)
                XYZMag = i1;
        }
        XYZMag = (int) (Math.sqrt(XYZMag) + 0.98999999999999999D);
        radius = (int) (Math.sqrt(XYZMag * XYZMag + super.modelHeight * super.modelHeight) + 0.98999999999999999D);
        diameter = radius + (int) (Math.sqrt(XYZMag * XYZMag + bottomY * bottomY) + 0.98999999999999999D);
    }

    public void method467() {
        super.modelHeight = 0;
        bottomY = 0;
        for (int i = 0; i < verticesCount; i++) {
            int j = verticesY[i];
            if (-j > super.modelHeight)
                super.modelHeight = -j;
            if (j > bottomY)
                bottomY = j;
        }

        radius = (int) (Math.sqrt(XYZMag * XYZMag + super.modelHeight * super.modelHeight) + 0.98999999999999999D);
        diameter = radius + (int) (Math.sqrt(XYZMag * XYZMag + bottomY * bottomY) + 0.98999999999999999D);
    }

    private void method468() {
        super.modelHeight = 0;
        XYZMag = 0;
        bottomY = 0;
        anInt1646 = 0xf423f;
        anInt1647 = 0xfff0bdc1;
        anInt1648 = 0xfffe7961;
        anInt1649 = 0x1869f;
        for (int j = 0; j < verticesCount; j++) {
            int k = verticesX[j];
            int l = verticesY[j];
            int i1 = verticesZ[j];
            if (k < anInt1646)
                anInt1646 = k;
            if (k > anInt1647)
                anInt1647 = k;
            if (i1 < anInt1649)
                anInt1649 = i1;
            if (i1 > anInt1648)
                anInt1648 = i1;
            if (-l > super.modelHeight)
                super.modelHeight = -l;
            if (l > bottomY)
                bottomY = l;
            int j1 = k * k + i1 * i1;
            if (j1 > XYZMag)
                XYZMag = j1;
        }

        XYZMag = (int) Math.sqrt(XYZMag);
        radius = (int) Math.sqrt(XYZMag * XYZMag + super.modelHeight * super.modelHeight);
        diameter = radius + (int) Math.sqrt(XYZMag * XYZMag + bottomY * bottomY);
    }

    public void method469() {
        if (vertexData != null) {
            int ai[] = new int[256];
            int j = 0;
            for (int l = 0; l < verticesCount; l++) {
                int j1 = vertexData[l];
                ai[j1]++;
                if (j1 > j)
                    j = j1;
            }

            vertexGroups = new int[j + 1][];
            for (int k1 = 0; k1 <= j; k1++) {
                vertexGroups[k1] = new int[ai[k1]];
                ai[k1] = 0;
            }

            for (int j2 = 0; j2 < verticesCount; j2++) {
                int l2 = vertexData[j2];
                vertexGroups[l2][ai[l2]++] = j2;
            }

            vertexData = null;
        }
        if (triangleData != null) {
            int ai1[] = new int[256];
            int k = 0;
            for (int i1 = 0; i1 < trianglesCount; i1++) {
                int l1 = triangleData[i1];
                ai1[l1]++;
                if (l1 > k)
                    k = l1;
            }

            triangleGroups = new int[k + 1][];
            for (int i2 = 0; i2 <= k; i2++) {
                triangleGroups[i2] = new int[ai1[i2]];
                ai1[i2] = 0;
            }

            for (int k2 = 0; k2 < trianglesCount; k2++) {
                int i3 = triangleData[k2];
                triangleGroups[i3][ai1[i3]++] = k2;
            }

            triangleData = null;
        }
    }

    public void method470(int i) {
        if (vertexGroups == null)
            return;
        if (i == -1)
            return;
        Class36 class36 = Class36.method531(i);
        if (class36 == null)
            return;
        SkinList class18 = class36.aClass18_637;
        animateX = 0;
        animateY = 0;
        animateZ = 0;
        for (int k = 0; k < class36.anInt638; k++) {
            int l = class36.anIntArray639[k];
            method472(class18.anIntArray342[l], class18.anIntArrayArray343[l], class36.anIntArray640[k], class36.anIntArray641[k], class36.anIntArray642[k]);
        }

    }

    public void method471(int ai[], int j, int k) {
        if (k == -1)
            return;
        if (ai == null || j == -1) {
            method470(k);
            return;
        }
        Class36 class36 = Class36.method531(k);
        if (class36 == null)
            return;
        Class36 class36_1 = Class36.method531(j);
        if (class36_1 == null) {
            method470(k);
            return;
        }
        SkinList class18 = class36.aClass18_637;
        animateX = 0;
        animateY = 0;
        animateZ = 0;
        int l = 0;
        int i1 = ai[l++];
        for (int j1 = 0; j1 < class36.anInt638; j1++) {
            int k1;
            for (k1 = class36.anIntArray639[j1]; k1 > i1; i1 = ai[l++]) ;
            if (k1 != i1 || class18.anIntArray342[k1] == 0)
                method472(class18.anIntArray342[k1], class18.anIntArrayArray343[k1], class36.anIntArray640[j1], class36.anIntArray641[j1], class36.anIntArray642[j1]);
        }

        animateX = 0;
        animateY = 0;
        animateZ = 0;
        l = 0;
        i1 = ai[l++];
        for (int l1 = 0; l1 < class36_1.anInt638; l1++) {
            int i2;
            for (i2 = class36_1.anIntArray639[l1]; i2 > i1; i1 = ai[l++]) ;
            if (i2 == i1 || class18.anIntArray342[i2] == 0)
                method472(class18.anIntArray342[i2], class18.anIntArrayArray343[i2], class36_1.anIntArray640[l1], class36_1.anIntArray641[l1], class36_1.anIntArray642[l1]);
        }

    }

    private void method472(int i, int ai[], int j, int k, int l) {
        int i1 = ai.length;
        if (i == 0) {
            int j1 = 0;
            animateX = 0;
            animateY = 0;
            animateZ = 0;
            for (int k2 = 0; k2 < i1; k2++) {
                int l3 = ai[k2];
                if (l3 < vertexGroups.length) {
                    int ai5[] = vertexGroups[l3];
                    for (int i5 = 0; i5 < ai5.length; i5++) {
                        int j6 = ai5[i5];
                        animateX += verticesX[j6];
                        animateY += verticesY[j6];
                        animateZ += verticesZ[j6];
                        j1++;
                    }

                }
            }

            if (j1 > 0) {
                animateX = animateX / j1 + j;
                animateY = animateY / j1 + k;
                animateZ = animateZ / j1 + l;
                return;
            } else {
                animateX = j;
                animateY = k;
                animateZ = l;
                return;
            }
        }
        if (i == 1) {
            for (int k1 = 0; k1 < i1; k1++) {
                int l2 = ai[k1];
                if (l2 < vertexGroups.length) {
                    int ai1[] = vertexGroups[l2];
                    for (int i4 = 0; i4 < ai1.length; i4++) {
                        int j5 = ai1[i4];
                        verticesX[j5] += j;
                        verticesY[j5] += k;
                        verticesZ[j5] += l;
                    }

                }
            }

            return;
        }
        if (i == 2) {
            for (int l1 = 0; l1 < i1; l1++) {
                int i3 = ai[l1];
                if (i3 < vertexGroups.length) {
                    int ai2[] = vertexGroups[i3];
                    for (int j4 = 0; j4 < ai2.length; j4++) {
                        int k5 = ai2[j4];
                        verticesX[k5] -= animateX;
                        verticesY[k5] -= animateY;
                        verticesZ[k5] -= animateZ;
                        int k6 = (j & 0xff) * 8;
                        int l6 = (k & 0xff) * 8;
                        int i7 = (l & 0xff) * 8;
                        if (i7 != 0) {
                            int j7 = sine[i7];
                            int i8 = cosine[i7];
                            int l8 = verticesY[k5] * j7 + verticesX[k5] * i8 >> 16;
                            verticesY[k5] = verticesY[k5] * i8 - verticesX[k5] * j7 >> 16;
                            verticesX[k5] = l8;
                        }
                        if (k6 != 0) {
                            int k7 = sine[k6];
                            int j8 = cosine[k6];
                            int i9 = verticesY[k5] * j8 - verticesZ[k5] * k7 >> 16;
                            verticesZ[k5] = verticesY[k5] * k7 + verticesZ[k5] * j8 >> 16;
                            verticesY[k5] = i9;
                        }
                        if (l6 != 0) {
                            int l7 = sine[l6];
                            int k8 = cosine[l6];
                            int j9 = verticesZ[k5] * l7 + verticesX[k5] * k8 >> 16;
                            verticesZ[k5] = verticesZ[k5] * k8 - verticesX[k5] * l7 >> 16;
                            verticesX[k5] = j9;
                        }
                        verticesX[k5] += animateX;
                        verticesY[k5] += animateY;
                        verticesZ[k5] += animateZ;
                    }

                }
            }

            return;
        }
        if (i == 3) {
            for (int i2 = 0; i2 < i1; i2++) {
                int j3 = ai[i2];
                if (j3 < vertexGroups.length) {
                    int ai3[] = vertexGroups[j3];
                    for (int k4 = 0; k4 < ai3.length; k4++) {
                        int l5 = ai3[k4];
                        verticesX[l5] -= animateX;
                        verticesY[l5] -= animateY;
                        verticesZ[l5] -= animateZ;
                        verticesX[l5] = (verticesX[l5] * j) / 128;
                        verticesY[l5] = (verticesY[l5] * k) / 128;
                        verticesZ[l5] = (verticesZ[l5] * l) / 128;
                        verticesX[l5] += animateX;
                        verticesY[l5] += animateY;
                        verticesZ[l5] += animateZ;
                    }

                }
            }

            return;
        }
        if (i == 5 && triangleGroups != null && alphas != null) {
            for (int j2 = 0; j2 < i1; j2++) {
                int k3 = ai[j2];
                if (k3 < triangleGroups.length) {
                    int ai4[] = triangleGroups[k3];
                    for (int l4 = 0; l4 < ai4.length; l4++) {
                        int i6 = ai4[l4];
                        alphas[i6] += j * 8;
                        if (alphas[i6] < 0)
                            alphas[i6] = 0;
                        if (alphas[i6] > 255)
                            alphas[i6] = 255;
                    }

                }
            }

        }
    }

    public void method473() {
        for (int j = 0; j < verticesCount; j++) {
            int k = verticesX[j];
            verticesX[j] = verticesZ[j];
            verticesZ[j] = -k;
        }

    }

    public void method474(int i) {
        int k = sine[i];
        int l = cosine[i];
        for (int i1 = 0; i1 < verticesCount; i1++) {
            int j1 = verticesY[i1] * l - verticesZ[i1] * k >> 16;
            verticesZ[i1] = verticesY[i1] * k + verticesZ[i1] * l >> 16;
            verticesY[i1] = j1;
        }
    }

    public void method475(int i, int j, int l) {
        for (int i1 = 0; i1 < verticesCount; i1++) {
            verticesX[i1] += i;
            verticesY[i1] += j;
            verticesZ[i1] += l;
        }

    }

    public void method476(int i, int j) {
        for (int k = 0; k < trianglesCount; k++)
            if (colors[k] == i)
                colors[k] = j;

    }

    public void method477() {
        for (int j = 0; j < verticesCount; j++)
            verticesZ[j] = -verticesZ[j];

        for (int k = 0; k < trianglesCount; k++) {
            int l = trianglesX[k];
            trianglesX[k] = trianglesZ[k];
            trianglesZ[k] = l;
        }
    }

    public void method478(int i, int j, int l) {
        for (int i1 = 0; i1 < verticesCount; i1++) {
            verticesX[i1] = (verticesX[i1] * i) / 128;
            verticesY[i1] = (verticesY[i1] * l) / 128;
            verticesZ[i1] = (verticesZ[i1] * j) / 128;
        }

    }


    public void light() {
        if (vertexNormals == null) {
            vertexNormals = new VertexNormal[verticesCount];

            int var1;
            for (var1 = 0; var1 < verticesCount; ++var1) {
                vertexNormals[var1] = new VertexNormal();
            }

            for (var1 = 0; var1 < trianglesCount; ++var1) {
                final int var2 = trianglesX[var1];
                final int var3 = trianglesY[var1];
                final int var4 = trianglesZ[var1];
                final int var5 = verticesX[var3] - verticesX[var2];
                final int var6 = verticesY[var3] - verticesY[var2];
                final int var7 = verticesZ[var3] - verticesZ[var2];
                final int var8 = verticesX[var4] - verticesX[var2];
                final int var9 = verticesY[var4] - verticesY[var2];
                final int var10 = verticesZ[var4] - verticesZ[var2];
                int var11 = var6 * var10 - var9 * var7;
                int var12 = var7 * var8 - var10 * var5;

                int var13;
                for (var13 = var5 * var9 - var8 * var6; var11 > 8192 || var12 > 8192 || var13 > 8192 || var11 < -8192 || var12 < -8192 || var13 < -8192; var13 >>= 1) {
                    var11 >>= 1;
                    var12 >>= 1;
                }

                int var14 = (int) Math.sqrt((double) (var11 * var11 + var12 * var12 + var13 * var13));
                if (var14 <= 0) {
                    var14 = 1;
                }

                var11 = var11 * 256 / var14;
                var12 = var12 * 256 / var14;
                var13 = var13 * 256 / var14;
                final int var15;
                if (types == null) {
                    var15 = 0;
                } else {
                    var15 = types[var1];
                }

                if (var15 == 0) {
                    VertexNormal var16 = vertexNormals[var2];
                    var16.x += var11;
                    var16.y += var12;
                    var16.z += var13;
                    ++var16.magnitude;
                    var16 = vertexNormals[var3];
                    var16.x += var11;
                    var16.y += var12;
                    var16.z += var13;
                    ++var16.magnitude;
                    var16 = vertexNormals[var4];
                    var16.x += var11;
                    var16.y += var12;
                    var16.z += var13;
                    ++var16.magnitude;
                } else if (var15 == 1) {
                    if (faceNormals == null) {
                        faceNormals = new FaceNormal[trianglesCount];
                    }

                    final FaceNormal var17 = faceNormals[var1] = new FaceNormal();
                    var17.x = var11;
                    var17.y = var12;
                    var17.z = var13;
                }
            }

        }
    }

    public void method479(final int ambient, final int contrast, final int x, final int y, final int z, final boolean flag) {
        light();
        final int magnitude = (int) Math.sqrt(x * x + y * y + z * z);
        final int k1 = contrast * magnitude >> 8;
        colorsX = new int[trianglesCount];
        colorsY = new int[trianglesCount];
        colorsZ = new int[trianglesCount];

        for (int var16 = 0; var16 < trianglesCount; ++var16) {
            int var17;
            if (types == null) {
                var17 = 0;
            } else {
                var17 = types[var16];
            }

            final int var18;
            if (alphas == null) {
                var18 = 0;
            } else {
                var18 = alphas[var16];
            }

            final short var12;
            if (materials == null) {
                var12 = -1;
            } else {
                var12 = materials[var16];
            }

            if (var18 == -2) {
                var17 = 3;
            }

            if (var18 == -1) {
                var17 = 2;
            }

            VertexNormal var13;
            int var14;
            final FaceNormal var19;
            if (var12 == -1) {
                if (var17 == 0) {
                    final int var15 = colors[var16] & '\uffff';
                    if (vertexNormalsOffsets != null && vertexNormalsOffsets[trianglesX[var16]] != null) {
                        var13 = vertexNormalsOffsets[trianglesX[var16]];
                    } else {
                        var13 = vertexNormals[trianglesX[var16]];
                    }

                    var14 = (y * var13.y + z * var13.z + x * var13.x) / (k1 * var13.magnitude) + ambient;
                    colorsX[var16] = method2792(var15, var14);
                    if (vertexNormalsOffsets != null && vertexNormalsOffsets[trianglesY[var16]] != null) {
                        var13 = vertexNormalsOffsets[trianglesY[var16]];
                    } else {
                        var13 = vertexNormals[trianglesY[var16]];
                    }

                    var14 = (y * var13.y + z * var13.z + x * var13.x) / (k1 * var13.magnitude) + ambient;
                    colorsY[var16] = method2792(var15, var14);
                    if (vertexNormalsOffsets != null && vertexNormalsOffsets[trianglesZ[var16]] != null) {
                        var13 = vertexNormalsOffsets[trianglesZ[var16]];
                    } else {
                        var13 = vertexNormals[trianglesZ[var16]];
                    }

                    var14 = (y * var13.y + z * var13.z + x * var13.x) / (k1 * var13.magnitude) + ambient;
                    colorsZ[var16] = method2792(var15, var14);
                } else if (var17 == 1) {
                    var19 = faceNormals[var16];
                    var14 = (y * var19.y + z * var19.z + x * var19.x) / (k1 / 2 + k1) + ambient;
                    colorsX[var16] = method2792(colors[var16] & '\uffff', var14);
                    colorsZ[var16] = -1;
                } else if (var17 == 3) {
                    colorsX[var16] = 128;
                    colorsZ[var16] = -1;
                } else {
                    colorsZ[var16] = -2;
                }
            } else if (var17 == 0) {
                if (vertexNormalsOffsets != null && vertexNormalsOffsets[trianglesX[var16]] != null) {
                    var13 = vertexNormalsOffsets[trianglesX[var16]];
                } else {
                    var13 = vertexNormals[trianglesX[var16]];
                }

                var14 = (y * var13.y + z * var13.z + x * var13.x) / (k1 * var13.magnitude) + ambient;
                colorsX[var16] = method2820(var14);
                if (vertexNormalsOffsets != null && vertexNormalsOffsets[trianglesY[var16]] != null) {
                    var13 = vertexNormalsOffsets[trianglesY[var16]];
                } else {
                    var13 = vertexNormals[trianglesY[var16]];
                }

                var14 = (y * var13.y + z * var13.z + x * var13.x) / (k1 * var13.magnitude) + ambient;
                colorsY[var16] = method2820(var14);
                if (vertexNormalsOffsets != null && vertexNormalsOffsets[trianglesZ[var16]] != null) {
                    var13 = vertexNormalsOffsets[trianglesZ[var16]];
                } else {
                    var13 = vertexNormals[trianglesZ[var16]];
                }

                var14 = (y * var13.y + z * var13.z + x * var13.x) / (k1 * var13.magnitude) + ambient;
                colorsZ[var16] = method2820(var14);
            } else if (var17 == 1) {
                var19 = faceNormals[var16];
                var14 = (y * var19.y + z * var19.z + x * var19.x) / (k1 / 2 + k1) + ambient;
                colorsX[var16] = method2820(var14);
                colorsZ[var16] = -1;
            } else {
                colorsZ[var16] = -2;
            }
        }
        method466();
    }

    static final int method2820(int var0) {
        if (var0 < 2) {
            var0 = 2;
        } else if (var0 > 126) {
            var0 = 126;
        }

        return var0;
    }

    static final int method2792(final int var0, int var1) {
        var1 = (var0 & 127) * var1 >> 7;
        if (var1 < 2) {
            var1 = 2;
        } else if (var1 > 126) {
            var1 = 126;
        }

        return (var0 & '\uff80') + var1;
    }

    public void method480(int i, int j, int k, int l, int i1) {
        for (int j1 = 0; j1 < trianglesCount; j1++) {
            int k1 = trianglesX[j1];
            int i2 = trianglesY[j1];
            int j2 = trianglesZ[j1];
            if (types == null) {
                int i3 = colors[j1];
                VertexNormal class33 = super.vertexNormals[k1];
                int k2 = i + (k * class33.x + l * class33.y + i1 * class33.z) / (j * class33.magnitude);
                colorsX[j1] = method481(i3, k2, 0);
                class33 = super.vertexNormals[i2];
                k2 = i + (k * class33.x + l * class33.y + i1 * class33.z) / (j * class33.magnitude);
                colorsY[j1] = method481(i3, k2, 0);
                class33 = super.vertexNormals[j2];
                k2 = i + (k * class33.x + l * class33.y + i1 * class33.z) / (j * class33.magnitude);
                colorsZ[j1] = method481(i3, k2, 0);
            } else if ((types[j1] & 1) == 0) {
                int j3 = colors[j1];
                int k3 = types[j1];
                VertexNormal class33_1 = super.vertexNormals[k1];
                int l2 = i + (k * class33_1.x + l * class33_1.y + i1 * class33_1.z) / (j * class33_1.magnitude);
                colorsX[j1] = method481(j3, l2, k3);
                class33_1 = super.vertexNormals[i2];
                l2 = i + (k * class33_1.x + l * class33_1.y + i1 * class33_1.z) / (j * class33_1.magnitude);
                colorsY[j1] = method481(j3, l2, k3);
                class33_1 = super.vertexNormals[j2];
                l2 = i + (k * class33_1.x + l * class33_1.y + i1 * class33_1.z) / (j * class33_1.magnitude);
                colorsZ[j1] = method481(j3, l2, k3);
            }
        }

        super.vertexNormals = null;
        vertexNormalsOffsets = null;
        vertexData = null;
        triangleData = null;
        if (types != null) {
            for (int l1 = 0; l1 < trianglesCount; l1++)
                if ((types[l1] & 2) == 2)
                    return;

        }
        colors = null;
    }

    public void method482(int j, int k, int l, int i1, int j1, int k1) {
        int i = 0; //was a parameter
        int l1 = Rasterizer3D.textureInt1;
        int i2 = Rasterizer3D.textureInt2;
        int j2 = sine[i];
        int k2 = cosine[i];
        int l2 = sine[j];
        int i3 = cosine[j];
        int j3 = sine[k];
        int k3 = cosine[k];
        int l3 = sine[l];
        int i4 = cosine[l];
        int j4 = j1 * l3 + k1 * i4 >> 16;
        for (int k4 = 0; k4 < verticesCount; k4++) {
            int l4 = verticesX[k4];
            int i5 = verticesY[k4];
            int j5 = verticesZ[k4];
            if (k != 0) {
                int k5 = i5 * j3 + l4 * k3 >> 16;
                i5 = i5 * k3 - l4 * j3 >> 16;
                l4 = k5;
            }
            if (i != 0) {
                int l5 = i5 * k2 - j5 * j2 >> 16;
                j5 = i5 * j2 + j5 * k2 >> 16;
                i5 = l5;
            }
            if (j != 0) {
                int i6 = j5 * l2 + l4 * i3 >> 16;
                j5 = j5 * i3 - l4 * l2 >> 16;
                l4 = i6;
            }
            l4 += i1;
            i5 += j1;
            j5 += k1;
            int j6 = i5 * i4 - j5 * l3 >> 16;
            j5 = i5 * l3 + j5 * i4 >> 16;
            i5 = j6;
            anIntArray1667[k4] = j5 - j4;
            viewportX[k4] = l1 + (l4 << 9) / j5;
            viewportY[k4] = i2 + (i5 << 9) / j5;
            if (texturesCount > 0) {
                viewportTextureX[k4] = l4;
                viewportTextureY[k4] = i5;
                viewportTextureZ[k4] = j5;
            }
        }

        try {
            method483(false, false, 0);
        } catch (Exception _ex) {
        }
    }

    @Override
    public void render(int orientation, int pitchSine, int pitchCos, int yawSin, int yawCos, int offsetX, int offsetY, int offsetZ, int hash) {
        int j2 = offsetZ * yawCos - offsetX * yawSin >> 16;
        int k2 = offsetY * pitchSine + j2 * pitchCos >> 16;
        int l2 = XYZMag * pitchCos >> 16;
        int i3 = k2 + l2;
        final boolean gpu = GpuPlugin.process() && Rasterizer3D.world;
        if (i3 <= 50 || (k2 >= 3500 && !gpu))
            return;
        int j3 = offsetZ * yawSin + offsetX * yawCos >> 16;
        int k3 = j3 - XYZMag << 9;
        if (k3 / i3 >= Raster.centerY)
            return;
        int l3 = j3 + XYZMag << 9;
        if (l3 / i3 <= -Raster.centerY)
            return;
        int i4 = offsetY * pitchCos - j2 * pitchSine >> 16;
        int j4 = XYZMag * pitchSine >> 16;
        int k4 = i4 + j4 << 9;
        if (k4 / i3 <= -Raster.anInt1387)
            return;
        int l4 = j4 + (super.modelHeight * pitchCos >> 16);
        int i5 = i4 - l4 << 9;
        if (i5 / i3 >= Raster.anInt1387)
            return;
        int j5 = l2 + (super.modelHeight * pitchSine >> 16);
        boolean flag = false;
        if (k2 - j5 <= 50)
            flag = true;
        boolean flag1 = false;
        if (hash > 0 && aBoolean1684) {
            int k5 = k2 - l2;
            if (k5 <= 50)
                k5 = 50;
            if (j3 > 0) {
                k3 /= i3;
                l3 /= k5;
            } else {
                l3 /= i3;
                k3 /= k5;
            }
            if (i4 > 0) {
                i5 /= i3;
                k4 /= k5;
            } else {
                k4 /= i3;
                i5 /= k5;
            }
            int i6 = anInt1685 - Rasterizer3D.textureInt1;
            int k6 = anInt1686 - Rasterizer3D.textureInt2;
            if (i6 > k3 && i6 < l3 && k6 > i5 && k6 < k4) {
                if (aBoolean1659) {
                    anIntArray1688[anInt1687++] = hash;
                    if (gpu) {
                        renderOnGpu(orientation, pitchSine, pitchCos, yawSin, yawCos, offsetX, offsetY, offsetZ, hash);
                        return;
                    }
                } else {
                    flag1 = true;
                }
            }
        }
        int l5 = Rasterizer3D.textureInt1;
        int j6 = Rasterizer3D.textureInt2;
        int l6 = 0;
        int i7 = 0;
        if (orientation != 0) {
            l6 = sine[orientation];
            i7 = cosine[orientation];
        }
        for (int j7 = 0; j7 < verticesCount; j7++) {
            int k7 = verticesX[j7];
            int l7 = verticesY[j7];
            int i8 = verticesZ[j7];
            if (orientation != 0) {
                int j8 = i8 * l6 + k7 * i7 >> 16;
                i8 = i8 * i7 - k7 * l6 >> 16;
                k7 = j8;
            }
            k7 += offsetX;
            l7 += offsetY;
            i8 += offsetZ;
            int k8 = i8 * yawSin + k7 * yawCos >> 16;
            i8 = i8 * yawCos - k7 * yawSin >> 16;
            k7 = k8;
            k8 = l7 * pitchCos - i8 * pitchSine >> 16;
            i8 = l7 * pitchSine + i8 * pitchCos >> 16;
            l7 = k8;
            anIntArray1667[j7] = i8 - k2;
            if (i8 >= 50) {
                viewportX[j7] = l5 + (k7 << 9) / i8;
                viewportY[j7] = j6 + (l7 << 9) / i8;
            } else {
                viewportX[j7] = -5000;
                flag = true;
            }
            if ((flag || texturesCount > 0) && !gpu) {
                viewportTextureX[j7] = k7;
                viewportTextureY[j7] = l7;
                viewportTextureZ[j7] = i8;
            }
        }

        try {
            if (!gpu || (flag1 && !(Math.sqrt(offsetX * offsetX + offsetZ * offsetZ) > 35 * Perspective.LOCAL_TILE_SIZE))) {
                method483(flag, flag1, hash);
            }
            if (gpu) {
                renderOnGpu(orientation, pitchSine, pitchCos, yawSin, yawCos, offsetX, offsetY, offsetZ, hash);
            }
        } catch (Exception _ex) {
        }
    }

    void renderOnGpu(int orientation, int pitchSine, int pitchCos, int yawSin, int yawCos, int offsetX, int offsetY, int offsetZ, int hash) {
        Client.instance.gpu.draw(this, orientation, pitchSine, pitchCos, yawSin, yawCos, offsetX, offsetY, offsetZ, hash);
    }

    private void method483(boolean flag, boolean flag1, int i) {
        final boolean gpu = GpuPlugin.process() && Rasterizer3D.world;
        for (int j = 0; j < diameter; j++)
            anIntArray1671[j] = 0;

        for (int k = 0; k < trianglesCount; k++) {
            if (colorsZ[k] == -2) {
                continue;
            }
                int l = trianglesX[k];
                int k1 = trianglesY[k];
                int j2 = trianglesZ[k];
                int i3 = viewportX[l];
                int l3 = viewportX[k1];
                int k4 = viewportX[j2];
                if (gpu) {
                    if (i3 == -5000 || l3 == -5000 || k4 == -5000) {
                        continue;
                    }
                    if (flag1 && method486(anInt1685, anInt1686, viewportY[l], viewportY[k1], viewportY[j2], i3, l3, k4)) {
                        anIntArray1688[anInt1687++] = i;
                    }
                    continue;
                }
                if (flag && (i3 == -5000 || l3 == -5000 || k4 == -5000)) {
                    hide[k] = true;
                    int j5 = (anIntArray1667[l] + anIntArray1667[k1] + anIntArray1667[j2]) / 3 + radius;
                    anIntArrayArray1672[j5][anIntArray1671[j5]++] = k;
                } else {
                    if (flag1 && method486(anInt1685, anInt1686, viewportY[l], viewportY[k1], viewportY[j2], i3, l3, k4)) {
                        anIntArray1688[anInt1687++] = i;
                        flag1 = false;
                    }
                    if ((i3 - l3) * (viewportY[j2] - viewportY[k1]) - (viewportY[l] - viewportY[k1]) * (k4 - l3) > 0) {
                        hide[k] = false;
                        hidden[k] = i3 < 0 || l3 < 0 || k4 < 0 || i3 > Raster.centerX || l3 > Raster.centerX || k4 > Raster.centerX;
                        int k5 = (anIntArray1667[l] + anIntArray1667[k1] + anIntArray1667[j2]) / 3 + radius;
                        anIntArrayArray1672[k5][anIntArray1671[k5]++] = k;
                    }
                }
        }
        if (gpu) {
            return;
        }
        if (priorities == null) {
            for (int i1 = diameter - 1; i1 >= 0; i1--) {
                int l1 = anIntArray1671[i1];
                if (l1 > 0) {
                    int ai[] = anIntArrayArray1672[i1];
                    for (int j3 = 0; j3 < l1; j3++)
                        draw(ai[j3]);

                }
            }

            return;
        }
        for (int j1 = 0; j1 < 12; j1++) {
            anIntArray1673[j1] = 0;
            anIntArray1677[j1] = 0;
        }

        for (int i2 = diameter - 1; i2 >= 0; i2--) {
            int k2 = anIntArray1671[i2];
            if (k2 > 0) {
                int ai1[] = anIntArrayArray1672[i2];
                for (int i4 = 0; i4 < k2; i4++) {
                    int l4 = ai1[i4];
                    int l5 = priorities[l4];
                    int j6 = anIntArray1673[l5]++;
                    anIntArrayArray1674[l5][j6] = l4;
                    if (l5 < 10)
                        anIntArray1677[l5] += i2;
                    else if (l5 == 10)
                        anIntArray1675[j6] = i2;
                    else
                        anIntArray1676[j6] = i2;
                }

            }
        }

        int l2 = 0;
        if (anIntArray1673[1] > 0 || anIntArray1673[2] > 0)
            l2 = (anIntArray1677[1] + anIntArray1677[2]) / (anIntArray1673[1] + anIntArray1673[2]);
        int k3 = 0;
        if (anIntArray1673[3] > 0 || anIntArray1673[4] > 0)
            k3 = (anIntArray1677[3] + anIntArray1677[4]) / (anIntArray1673[3] + anIntArray1673[4]);
        int j4 = 0;
        if (anIntArray1673[6] > 0 || anIntArray1673[8] > 0)
            j4 = (anIntArray1677[6] + anIntArray1677[8]) / (anIntArray1673[6] + anIntArray1673[8]);
        int i6 = 0;
        int k6 = anIntArray1673[10];
        int ai2[] = anIntArrayArray1674[10];
        int ai3[] = anIntArray1675;
        if (i6 == k6) {
            i6 = 0;
            k6 = anIntArray1673[11];
            ai2 = anIntArrayArray1674[11];
            ai3 = anIntArray1676;
        }
        int i5;
        if (i6 < k6)
            i5 = ai3[i6];
        else
            i5 = -1000;
        for (int l6 = 0; l6 < 10; l6++) {
            while (l6 == 0 && i5 > l2) {
                draw(ai2[i6++]);
                if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
                    i6 = 0;
                    k6 = anIntArray1673[11];
                    ai2 = anIntArrayArray1674[11];
                    ai3 = anIntArray1676;
                }
                if (i6 < k6)
                    i5 = ai3[i6];
                else
                    i5 = -1000;
            }
            while (l6 == 3 && i5 > k3) {
                draw(ai2[i6++]);
                if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
                    i6 = 0;
                    k6 = anIntArray1673[11];
                    ai2 = anIntArrayArray1674[11];
                    ai3 = anIntArray1676;
                }
                if (i6 < k6)
                    i5 = ai3[i6];
                else
                    i5 = -1000;
            }
            while (l6 == 5 && i5 > j4) {
                draw(ai2[i6++]);
                if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
                    i6 = 0;
                    k6 = anIntArray1673[11];
                    ai2 = anIntArrayArray1674[11];
                    ai3 = anIntArray1676;
                }
                if (i6 < k6)
                    i5 = ai3[i6];
                else
                    i5 = -1000;
            }
            int i7 = anIntArray1673[l6];
            int ai4[] = anIntArrayArray1674[l6];
            for (int j7 = 0; j7 < i7; j7++)
                draw(ai4[j7]);

        }

        while (i5 != -1000) {
            draw(ai2[i6++]);
            if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
                i6 = 0;
                ai2 = anIntArrayArray1674[11];
                k6 = anIntArray1673[11];
                ai3 = anIntArray1676;
            }
            if (i6 < k6)
                i5 = ai3[i6];
            else
                i5 = -1000;
        }
    }

    public void draw(final int i) { // DONE
        if (hide[i]) {
            hide(i);
            return;
        }
        try {
            final int triangleX = trianglesX[i];
            final int triangleY = trianglesY[i];
            final int triangleZ = trianglesZ[i];
            Rasterizer3D.hidden = hidden[i];
            if (alphas == null) {
                Rasterizer3D.alpha = 0;
            } else {
                Rasterizer3D.alpha = alphas[i] & 255;
            }
            if (materials != null && materials[i] != -1) {
                final int textureX;
                final int textureY;
                final int textureZ;
                if (textures != null && textures[i] != -1) {
                    final int var8 = textures[i] & 255;
                    textureX = texturesX[var8];
                    textureY = texturesY[var8];
                    textureZ = texturesZ[var8];
                } else {
                    textureX = triangleX;
                    textureY = triangleY;
                    textureZ = triangleZ;
                }
                if (colorsZ[i] == -1) {
                    Rasterizer3D.drawTextured(viewportY[triangleX], viewportY[triangleY], viewportY[triangleZ], viewportX[triangleX], viewportX[triangleY], viewportX[triangleZ], colorsX[i], colorsX[i], colorsX[i], viewportTextureX[textureX], viewportTextureX[textureY], viewportTextureX[textureZ], viewportTextureY[textureX], viewportTextureY[textureY], viewportTextureY[textureZ], viewportTextureZ[textureX], viewportTextureZ[textureY], viewportTextureZ[textureZ], materials[i]);
                } else {
                    Rasterizer3D.drawTextured(viewportY[triangleX], viewportY[triangleY], viewportY[triangleZ], viewportX[triangleX], viewportX[triangleY], viewportX[triangleZ], colorsX[i], colorsY[i], colorsZ[i], viewportTextureX[textureX], viewportTextureX[textureY], viewportTextureX[textureZ], viewportTextureY[textureX], viewportTextureY[textureY], viewportTextureY[textureZ], viewportTextureZ[textureX], viewportTextureZ[textureY], viewportTextureZ[textureZ], materials[i]);
                }
            } else if (colorsZ[i] == -1) {
                Rasterizer3D.drawFlat(viewportY[triangleX], viewportY[triangleY], viewportY[triangleZ], viewportX[triangleX], viewportX[triangleY], viewportX[triangleZ], palette[colorsX[i]]);
            } else {
                Rasterizer3D.drawGouraud(viewportY[triangleX], viewportY[triangleY], viewportY[triangleZ], viewportX[triangleX], viewportX[triangleY], viewportX[triangleZ], colorsX[i], colorsY[i], colorsZ[i]);
            }
        } catch (final Exception e) {

        }
    }

    private void hide(int i) {
        int j = Rasterizer3D.textureInt1;
        int k = Rasterizer3D.textureInt2;
        int l = 0;
        int i1 = trianglesX[i];
        int j1 = trianglesY[i];
        int k1 = trianglesZ[i];
        int l1 = viewportTextureZ[i1];
        int i2 = viewportTextureZ[j1];
        int j2 = viewportTextureZ[k1];
        if (l1 >= 50) {
            anIntArray1678[l] = viewportX[i1];
            anIntArray1679[l] = viewportY[i1];
            anIntArray1680[l++] = colorsX[i];
        } else {
            int k2 = viewportTextureX[i1];
            int k3 = viewportTextureY[i1];
            int k4 = colorsX[i];
            if (j2 >= 50) {
                int k5 = (50 - l1) * modelIntArray4[j2 - l1];
                anIntArray1678[l] = j + (k2 + ((viewportTextureX[k1] - k2) * k5 >> 16) << 9) / 50;
                anIntArray1679[l] = k + (k3 + ((viewportTextureY[k1] - k3) * k5 >> 16) << 9) / 50;
                anIntArray1680[l++] = k4 + ((colorsZ[i] - k4) * k5 >> 16);
            }
            if (i2 >= 50) {
                int l5 = (50 - l1) * modelIntArray4[i2 - l1];
                anIntArray1678[l] = j + (k2 + ((viewportTextureX[j1] - k2) * l5 >> 16) << 9) / 50;
                anIntArray1679[l] = k + (k3 + ((viewportTextureY[j1] - k3) * l5 >> 16) << 9) / 50;
                anIntArray1680[l++] = k4 + ((colorsY[i] - k4) * l5 >> 16);
            }
        }
        if (i2 >= 50) {
            anIntArray1678[l] = viewportX[j1];
            anIntArray1679[l] = viewportY[j1];
            anIntArray1680[l++] = colorsY[i];
        } else {
            int l2 = viewportTextureX[j1];
            int l3 = viewportTextureY[j1];
            int l4 = colorsY[i];
            if (l1 >= 50) {
                int i6 = (50 - i2) * modelIntArray4[l1 - i2];
                anIntArray1678[l] = j + (l2 + ((viewportTextureX[i1] - l2) * i6 >> 16) << 9) / 50;
                anIntArray1679[l] = k + (l3 + ((viewportTextureY[i1] - l3) * i6 >> 16) << 9) / 50;
                anIntArray1680[l++] = l4 + ((colorsX[i] - l4) * i6 >> 16);
            }
            if (j2 >= 50) {
                int j6 = (50 - i2) * modelIntArray4[j2 - i2];
                anIntArray1678[l] = j + (l2 + ((viewportTextureX[k1] - l2) * j6 >> 16) << 9) / 50;
                anIntArray1679[l] = k + (l3 + ((viewportTextureY[k1] - l3) * j6 >> 16) << 9) / 50;
                anIntArray1680[l++] = l4 + ((colorsZ[i] - l4) * j6 >> 16);
            }
        }
        if (j2 >= 50) {
            anIntArray1678[l] = viewportX[k1];
            anIntArray1679[l] = viewportY[k1];
            anIntArray1680[l++] = colorsZ[i];
        } else {
            int i3 = viewportTextureX[k1];
            int i4 = viewportTextureY[k1];
            int i5 = colorsZ[i];
            if (i2 >= 50) {
                int k6 = (50 - j2) * modelIntArray4[i2 - j2];
                anIntArray1678[l] = j + (i3 + ((viewportTextureX[j1] - i3) * k6 >> 16) << 9) / 50;
                anIntArray1679[l] = k + (i4 + ((viewportTextureY[j1] - i4) * k6 >> 16) << 9) / 50;
                anIntArray1680[l++] = i5 + ((colorsY[i] - i5) * k6 >> 16);
            }
            if (l1 >= 50) {
                int l6 = (50 - j2) * modelIntArray4[l1 - j2];
                anIntArray1678[l] = j + (i3 + ((viewportTextureX[i1] - i3) * l6 >> 16) << 9) / 50;
                anIntArray1679[l] = k + (i4 + ((viewportTextureY[i1] - i4) * l6 >> 16) << 9) / 50;
                anIntArray1680[l++] = i5 + ((colorsX[i] - i5) * l6 >> 16);
            }
        }
        int j3 = anIntArray1678[0];
        int j4 = anIntArray1678[1];
        int j5 = anIntArray1678[2];
        int i7 = anIntArray1679[0];
        int j7 = anIntArray1679[1];
        int k7 = anIntArray1679[2];
        if ((j3 - j4) * (k7 - j7) - (i7 - j7) * (j5 - j4) > 0) {
            Rasterizer3D.hidden = false;
            if (l == 3) {
                if (j3 < 0 || j4 < 0 || j5 < 0 || j3 > Raster.centerX || j4 > Raster.centerX || j5 > Raster.centerX)
                    Rasterizer3D.hidden = true;
                int l7;
                if (types == null)
                    l7 = 0;
                else
                    l7 = types[i] & 3;
                if (l7 == 0)
                    Rasterizer3D.drawGouraud(i7, j7, k7, j3, j4, j5, anIntArray1680[0], anIntArray1680[1], anIntArray1680[2]);
                else if (l7 == 1)
                    Rasterizer3D.drawFlat(i7, j7, k7, j3, j4, j5, palette[colorsX[i]]);
                else if (l7 == 2) {
                    int j8 = types[i] >> 2;
                    int k9 = texturesX[j8];
                    int k10 = texturesY[j8];
                    int k11 = texturesZ[j8];
                    Rasterizer3D.drawTextured(i7, j7, k7, j3, j4, j5, anIntArray1680[0], anIntArray1680[1], anIntArray1680[2], viewportTextureX[k9], viewportTextureX[k10], viewportTextureX[k11], viewportTextureY[k9], viewportTextureY[k10], viewportTextureY[k11], viewportTextureZ[k9], viewportTextureZ[k10], viewportTextureZ[k11], colors[i]);
                } else if (l7 == 3) {
                    int k8 = types[i] >> 2;
                    int l9 = texturesX[k8];
                    int l10 = texturesY[k8];
                    int l11 = texturesZ[k8];
                    Rasterizer3D.drawTextured(i7, j7, k7, j3, j4, j5, colorsX[i], colorsX[i], colorsX[i], viewportTextureX[l9], viewportTextureX[l10], viewportTextureX[l11], viewportTextureY[l9], viewportTextureY[l10], viewportTextureY[l11], viewportTextureZ[l9], viewportTextureZ[l10], viewportTextureZ[l11], colors[i]);
                }
            }
            if (l == 4) {
                if (j3 < 0 || j4 < 0 || j5 < 0 || j3 > Raster.centerX || j4 > Raster.centerX || j5 > Raster.centerX || anIntArray1678[3] < 0 || anIntArray1678[3] > Raster.centerX)
                    Rasterizer3D.hidden = true;
                int i8;
                if (types == null)
                    i8 = 0;
                else
                    i8 = types[i] & 3;
                if (i8 == 0) {
                    Rasterizer3D.drawGouraud(i7, j7, k7, j3, j4, j5, anIntArray1680[0], anIntArray1680[1], anIntArray1680[2]);
                    Rasterizer3D.drawGouraud(i7, k7, anIntArray1679[3], j3, j5, anIntArray1678[3], anIntArray1680[0], anIntArray1680[2], anIntArray1680[3]);
                    return;
                }
                if (i8 == 1) {
                    int l8 = palette[colorsX[i]];
                    Rasterizer3D.drawFlat(i7, j7, k7, j3, j4, j5, l8);
                    Rasterizer3D.drawFlat(i7, k7, anIntArray1679[3], j3, j5, anIntArray1678[3], l8);
                    return;
                }
                if (i8 == 2) {
                    int i9 = types[i] >> 2;
                    int i10 = texturesX[i9];
                    int i11 = texturesY[i9];
                    int i12 = texturesZ[i9];
                    Rasterizer3D.drawTextured(i7, j7, k7, j3, j4, j5, anIntArray1680[0], anIntArray1680[1], anIntArray1680[2], viewportTextureX[i10], viewportTextureX[i11], viewportTextureX[i12], viewportTextureY[i10], viewportTextureY[i11], viewportTextureY[i12], viewportTextureZ[i10], viewportTextureZ[i11], viewportTextureZ[i12], colors[i]);
                    Rasterizer3D.drawTextured(i7, k7, anIntArray1679[3], j3, j5, anIntArray1678[3], anIntArray1680[0], anIntArray1680[2], anIntArray1680[3], viewportTextureX[i10], viewportTextureX[i11], viewportTextureX[i12], viewportTextureY[i10], viewportTextureY[i11], viewportTextureY[i12], viewportTextureZ[i10], viewportTextureZ[i11], viewportTextureZ[i12], colors[i]);
                    return;
                }
                if (i8 == 3) {
                    int j9 = types[i] >> 2;
                    int j10 = texturesX[j9];
                    int j11 = texturesY[j9];
                    int j12 = texturesZ[j9];
                    Rasterizer3D.drawTextured(i7, j7, k7, j3, j4, j5, colorsX[i], colorsX[i], colorsX[i], viewportTextureX[j10], viewportTextureX[j11], viewportTextureX[j12], viewportTextureY[j10], viewportTextureY[j11], viewportTextureY[j12], viewportTextureZ[j10], viewportTextureZ[j11], viewportTextureZ[j12], colors[i]);
                    Rasterizer3D.drawTextured(i7, k7, anIntArray1679[3], j3, j5, anIntArray1678[3], colorsX[i], colorsX[i], colorsX[i], viewportTextureX[j10], viewportTextureX[j11], viewportTextureX[j12], viewportTextureY[j10], viewportTextureY[j11], viewportTextureY[j12], viewportTextureZ[j10], viewportTextureZ[j11], viewportTextureZ[j12], colors[i]);
                }
            }
        }
    }

    private boolean method486(int i, int j, int k, int l, int i1, int j1, int k1,
                              int l1) {
        if (j < k && j < l && j < i1)
            return false;
        if (j > k && j > l && j > i1)
            return false;
        return !(i < j1 && i < k1 && i < l1) && (i <= j1 || i <= k1 || i <= l1);
    }
}
