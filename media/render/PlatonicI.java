package shamu.media.render;

/** Collection of constants used in representing the five Platonic polyhedra whose vertices lie on the surface of a sphere.
 *
 *  @author Scott T. Carter
 *  @version 1.4
 */

public interface PlatonicI {

   /**
    * Constant used in inscription of regular of regular dodecahedron and icosahedron inside a sphere.
    */

   public static final float GOLDEN_RATIO = ((float)Math.sqrt(5) + 1.0f) / 2.0f;

   /**
    * First vertex of an icosahedron inscribed in a sphere.
    */

   public static final float[] ICOSAHEDRON_COORDINATE_0  = {1.0f,  0.0f, GOLDEN_RATIO};

   /**
    * Second vertex of an icosahedron inscribed in a sphere.
    */

   public static final float[] ICOSAHEDRON_COORDINATE_1  = {-1.0f,  0.0f, GOLDEN_RATIO};

   /**
    * Third vertex of an icosahedron inscribed in a sphere.
    */

   public static final float[] ICOSAHEDRON_COORDINATE_2  = { 1.0f,  0.0f, -GOLDEN_RATIO};

   /**
    * Fourth vertex of an icosahedron inscribed in a sphere.
    */

   public static final float[] ICOSAHEDRON_COORDINATE_3  = {-1.0f,  0.0f, -GOLDEN_RATIO};

   /**
    * Fifth vertex of an icosahedron inscribed in a sphere.
    */

   public static final float[] ICOSAHEDRON_COORDINATE_4  = { 0.0f, GOLDEN_RATIO,  1.0f};

   /**
    * Sixth vertex of an icosahedron inscribed in a sphere.
    */

   public static final float[] ICOSAHEDRON_COORDINATE_5  = {0.0f,  GOLDEN_RATIO, -1.0f};

   /**
    * Seventh vertex of an icosahedron inscribed in a sphere.
    */

   public static final float[] ICOSAHEDRON_COORDINATE_6  = {0.0f, -GOLDEN_RATIO,  1.0f};

   /**
    * Eighth vertex of an icosahedron inscribed in a sphere.
    */

   public static final float[] ICOSAHEDRON_COORDINATE_7  = {0.0f, -GOLDEN_RATIO,  -1.0f};

   /**
    * Ninth vertex of an icosahedron inscribed in a sphere.
    */

   public static final float[] ICOSAHEDRON_COORDINATE_8  = {GOLDEN_RATIO, 1.0f, 0.0f};

   /**
    * Tenth vertex of an icosahedron inscribed in a sphere.
    */

   public static final float[] ICOSAHEDRON_COORDINATE_9 = {GOLDEN_RATIO, -1.0f, 0.0f};

   /**
    * Eleventh vertex of an icosahedron inscribed in a sphere.
    */

   public static final float[] ICOSAHEDRON_COORDINATE_10 = {-GOLDEN_RATIO,  1.0f, 0.0f};

   /**
    * Twelfth vertex of an icosahedron inscribed in a sphere.
    */

   public static final float[] ICOSAHEDRON_COORDINATE_11 = {-GOLDEN_RATIO, -1.0f, 0.0f};

   /**
    * First vertex of a dodecahedron inscribed in a sphere.
    */

   public static final float[] DODECAHEDRON_COORDINATE_0  = {2.0f * (float)Math.cos(1.0f * Math.PI / 5.0f), -GOLDEN_RATIO - 1, 2.0f * (float)Math.sin(1.0f * Math.PI / 5.0f)};

   /**
    * Second vertex of a dodecahedron inscribed in a sphere.
    */

   public static final float[] DODECAHEDRON_COORDINATE_1  = {2.0f * (float)Math.cos(3.0f * Math.PI / 5.0f), -GOLDEN_RATIO - 1, 2.0f * (float)Math.sin(3.0f * Math.PI / 5.0f)};

   /**
    * Third vertex of a dodecahedron inscribed in a sphere.
    */

   public static final float[] DODECAHEDRON_COORDINATE_2  = {2.0f * (float)Math.cos(5.0f * Math.PI / 5.0f), -GOLDEN_RATIO - 1, 2.0f * (float)Math.sin(5.0f * Math.PI / 5.0f)};

   /**
    * Fourth vertex of a dodecahedron inscribed in a sphere.
    */

   public static final float[] DODECAHEDRON_COORDINATE_3  = {2.0f * (float)Math.cos(7.0f * Math.PI / 5.0f), -GOLDEN_RATIO - 1, 2.0f * (float)Math.sin(7.0f * Math.PI / 5.0f)};

   /**
    * Fifth vertex of a dodecahedron inscribed in a sphere.
    */

   public static final float[] DODECAHEDRON_COORDINATE_4  = {2.0f * (float)Math.cos(9.0f * Math.PI / 5.0f), -GOLDEN_RATIO - 1, 2.0f * (float)Math.sin(9.0f * Math.PI / 5.0f)};

   /**
    * Sixth vertex of a dodecahedron inscribed in a sphere.
    */

   public static final float[] DODECAHEDRON_COORDINATE_5  = {2.0f * GOLDEN_RATIO * (float)Math.cos(1.0f * Math.PI / 5.0f), -GOLDEN_RATIO +  1, 2.0f * GOLDEN_RATIO * (float)Math.sin(1.0f * Math.PI / 5.0f)};

   /**
    * Seventh vertex of a dodecahedron inscribed in a sphere.
    */

   public static final float[] DODECAHEDRON_COORDINATE_6  = {2.0f * GOLDEN_RATIO * (float)Math.cos(3.0f * Math.PI / 5.0f), -GOLDEN_RATIO + 1, 2.0f * GOLDEN_RATIO * (float)Math.sin(3.0f * Math.PI / 5.0f)};

   /**
    * Eighth vertex of a dodecahedron inscribed in a sphere.
    */

   public static final float[] DODECAHEDRON_COORDINATE_7  = {2.0f * GOLDEN_RATIO * (float)Math.cos(5.0f * Math.PI / 5.0f), -GOLDEN_RATIO + 1, 2.0f * GOLDEN_RATIO * (float)Math.sin(5.0f * Math.PI / 5.0f)};

   /**
    * Ninth vertex of a dodecahedron inscribed in a sphere.
    */

   public static final float[] DODECAHEDRON_COORDINATE_8  = {2.0f * GOLDEN_RATIO * (float)Math.cos(7.0f * Math.PI / 5.0f), -GOLDEN_RATIO + 1, 2.0f * GOLDEN_RATIO * (float)Math.sin(7.0f * Math.PI / 5.0f)};

   /**
    * Tenth vertex of a dodecahedron inscribed in a sphere.
    */

   public static final float[] DODECAHEDRON_COORDINATE_9  = {2.0f * GOLDEN_RATIO * (float)Math.cos(9.0f * Math.PI / 5.0f), -GOLDEN_RATIO + 1, 2.0f * GOLDEN_RATIO * (float)Math.sin(9.0f * Math.PI / 5.0f)};

   /**
    * Eleventh vertex of a dodecahedron inscribed in a sphere.
    */

   public static final float[] DODECAHEDRON_COORDINATE_10  = {2.0f * GOLDEN_RATIO * (float)Math.cos(0.0f * Math.PI / 5.0f), GOLDEN_RATIO -  1, 2.0f * GOLDEN_RATIO * (float)Math.sin(0.0f * Math.PI / 5.0f)};

   /**
    * Twelfth vertex of a dodecahedron inscribed in a sphere.
    */

   public static final float[] DODECAHEDRON_COORDINATE_11  = {2.0f * GOLDEN_RATIO * (float)Math.cos(2.0f * Math.PI / 5.0f), GOLDEN_RATIO - 1, 2.0f * GOLDEN_RATIO * (float)Math.sin(2.0f * Math.PI / 5.0f)};

   /**
    * Thirteenth vertex of a dodecahedron inscribed in a sphere.
    */

   public static final float[] DODECAHEDRON_COORDINATE_12  = {2.0f * GOLDEN_RATIO * (float)Math.cos(4.0f * Math.PI / 5.0f), GOLDEN_RATIO - 1, 2.0f * GOLDEN_RATIO * (float)Math.sin(4.0f * Math.PI / 5.0f)};

   /**
    * Fourteenth vertex of a dodecahedron inscribed in a sphere.
    */

   public static final float[] DODECAHEDRON_COORDINATE_13  = {2.0f * GOLDEN_RATIO * (float)Math.cos(6.0f * Math.PI / 5.0f), GOLDEN_RATIO - 1, 2.0f * GOLDEN_RATIO * (float)Math.sin(6.0f * Math.PI / 5.0f)};

   /**
    * Fifteenth vertex of a dodecahedron inscribed in a sphere.
    */

   public static final float[] DODECAHEDRON_COORDINATE_14  = {2.0f * GOLDEN_RATIO * (float)Math.cos(8.0f * Math.PI / 5.0f), GOLDEN_RATIO - 1, 2.0f * GOLDEN_RATIO * (float)Math.sin(8.0f * Math.PI / 5.0f)};

   /**
    * Sixteenth vertex of a dodecahedron inscribed in a sphere.
    */

   public static final float[] DODECAHEDRON_COORDINATE_15  = {2.0f * (float)Math.cos(0.0f * Math.PI / 5.0f), GOLDEN_RATIO +  1, 2.0f * (float)Math.sin(0.0f * Math.PI / 5.0f)};

   /**
    * Seventeenth vertex of a dodecahedron inscribed in a sphere.
    */

   public static final float[] DODECAHEDRON_COORDINATE_16  = {2.0f * (float)Math.cos(2.0f * Math.PI / 5.0f), GOLDEN_RATIO + 1, 2.0f * (float)Math.sin(2.0f * Math.PI / 5.0f)};

   /**
    * Eighteenth vertex of a dodecahedron inscribed in a sphere.
    */

   public static final float[] DODECAHEDRON_COORDINATE_17  = {2.0f * (float)Math.cos(4.0f * Math.PI / 5.0f), GOLDEN_RATIO + 1, 2.0f * (float)Math.sin(4.0f * Math.PI / 5.0f)};

   /**
    * Nineteenth vertex of a dodecahedron inscribed in a sphere.
    */

   public static final float[] DODECAHEDRON_COORDINATE_18  = {2.0f * (float)Math.cos(6.0f * Math.PI / 5.0f), GOLDEN_RATIO + 1, 2.0f * (float)Math.sin(6.0f * Math.PI / 5.0f)};

   /**
    * Twentieth vertex of a dodecahedron inscribed in a sphere.
    */

   public static final float[] DODECAHEDRON_COORDINATE_19  = {2.0f * (float)Math.cos(8.0f * Math.PI / 5.0f), GOLDEN_RATIO + 1, 2.0f * (float)Math.sin(8.0f * Math.PI / 5.0f)};

   /**
    * First vertex of an octahedron inscribed in a sphere.
    */

   public static final float[] OCTAHEDRON_COORDINATE_0 = { 1.0f,  0.0f,  0.0f};

   /**
    * Second vertex of an octahedron inscribed in a sphere.
    */

   public static final float[] OCTAHEDRON_COORDINATE_1 = {-1.0f,  0.0f,  0.0f};

   /**
    * Third vertex of an octahedron inscribed in a sphere.
    */

   public static final float[] OCTAHEDRON_COORDINATE_2 = { 0.0f,  1.0f,  0.0f};

   /**
    * Forth vertex of an octahedron inscribed in a sphere.
    */

   public static final float[] OCTAHEDRON_COORDINATE_3 = { 0.0f, -1.0f,  0.0f};

   /**
    * Fifth vertex of an octahedron inscribed in a sphere.
    */

   public static final float[] OCTAHEDRON_COORDINATE_4 = { 0.0f,  0.0f,  1.0f};

   /**
    * Sixth vertex of an octahedron inscribed in a sphere.
    */

   public static final float[] OCTAHEDRON_COORDINATE_5 = { 0.0f,  0.0f, -1.0f};

   /**
    * First vertex of a cube inscribed in a sphere.
    */

   public static final float[] CUBE_COORDINATE_0 = {-1.0f, -1.0f, -1.0f};

   /**
    * Second vertex of a cube inscribed in a sphere.
    */

   public static final float[] CUBE_COORDINATE_1 = {-1.0f,  1.0f, -1.0f};

   /**
    * Third vertex of a cube inscribed in a sphere.
    */

   public static final float[] CUBE_COORDINATE_2 = { 1.0f, -1.0f, -1.0f};

   /**
    * Fourth vertex of a cube inscribed in a sphere.
    */

   public static final float[] CUBE_COORDINATE_3 = { 1.0f,  1.0f, -1.0f};

   /**
    * Fifth vertex of a cube inscribed in a sphere.
    */

   public static final float[] CUBE_COORDINATE_4 = {-1.0f, -1.0f,  1.0f};

   /**
    * Sixth vertex of a cube inscribed in a sphere.
    */

   public static final float[] CUBE_COORDINATE_5 = {-1.0f,  1.0f,  1.0f};

   /**
    * Seventh vertex of a cube inscribed in a sphere.
    */

   public static final float[] CUBE_COORDINATE_6 = { 1.0f, -1.0f,  1.0f};

   /**
    * Eighth vertex of a cube inscribed in a sphere.
    */

   public static final float[] CUBE_COORDINATE_7 = { 1.0f,  1.0f,  1.0f};

   /**
    * First vertex of an tetrahedron inscribed in a sphere.
    */

   public static final float[] TETRAHEDRON_COORDINATE_0 = {-1.0f, -1.0f,  1.0f};

   /**
    * Second vertex of an tetrahedron inscribed in a sphere.
    */

   public static final float[] TETRAHEDRON_COORDINATE_1 = {-1.0f,  1.0f, -1.0f};

   /**
    * Third vertex of an tetrahedron inscribed in a sphere.
    */

   public static final float[] TETRAHEDRON_COORDINATE_2 = { 1.0f, -1.0f, -1.0f};

   /**
    * Fourth vertex of an tetrahedron inscribed in a sphere.
    */

   public static final float[] TETRAHEDRON_COORDINATE_3 = { 1.0f,  1.0f,  1.0f};

   /**
    * First indexed face of an icosahedron inscribed in a sphere.
    */

   public static final int[] ICOSAHEDRON_FACE_0 = {6, 0, 1};

   /**
    * Second indexed face of an icosahedron inscribed in a sphere.
    */

   public static final int[] ICOSAHEDRON_FACE_1 = {6, 1, 11};

   /**
    * Third indexed face of an icosahedron inscribed in a sphere.
    */

   public static final int[] ICOSAHEDRON_FACE_2 = {9, 8, 0};

   /**
    * Fourth indexed face of an icosahedron inscribed in a sphere.
    */

   public static final int[] ICOSAHEDRON_FACE_3 = {0, 8, 4};

   /**
    * Fifth indexed face of an icosahedron inscribed in a sphere.
    */

   public static final int[] ICOSAHEDRON_FACE_4 = {0, 4, 1};

   /**
    * Sixth indexed face of an icosahedron inscribed in a sphere.
    */

   public static final int[] ICOSAHEDRON_FACE_5 = {1, 4, 10};

   /**
    * Seventh indexed face of an icosahedron inscribed in a sphere.
    */

   public static final int[] ICOSAHEDRON_FACE_6 = {11, 1, 10};

   /**
    * Eighth indexed face of an icosahedron inscribed in a sphere.
    */

   public static final int[] ICOSAHEDRON_FACE_7 = {7, 9, 6};

   /**
    * Nineth indexed face of an icosahedron inscribed in a sphere.
    */

   public static final int[] ICOSAHEDRON_FACE_8 = {7, 6, 11};

   /**
    * Tenth indexed face of an icosahedron inscribed in a sphere.
    */

   public static final int[] ICOSAHEDRON_FACE_9 = {7, 11, 3};

   /**
    * Eleventh indexed face of an icosahedron inscribed in a sphere.
    */

   public static final int[] ICOSAHEDRON_FACE_10 = {7, 3, 2};

   /**
    * Twelfth indexed face of an icosahedron inscribed in a sphere.
    */

   public static final int[] ICOSAHEDRON_FACE_11 = {7, 2, 9};

   /**
    * Thirteenth indexed face of an icosahedron inscribed in a sphere.
    */

   public static final int[] ICOSAHEDRON_FACE_12 = {3, 10, 5};

   /**
    * Fourteenth indexed face of an icosahedron inscribed in a sphere.
    */

   public static final int[] ICOSAHEDRON_FACE_13 = {3, 5, 2};

   /**
    * Fifteenth indexed face of an icosahedron inscribed in a sphere.
    */

   public static final int[] ICOSAHEDRON_FACE_14 = {2, 5, 8};

   /**
    * Sixteenth indexed face of an icosahedron inscribed in a sphere.
    */

   public static final int[] ICOSAHEDRON_FACE_15 = {11, 10, 3};

   /**
    * Seventeenth indexed face of an icosahedron inscribed in a sphere.
    */

   public static final int[] ICOSAHEDRON_FACE_16 = {9, 2, 8};

   /**
    * Eighteenth indexed face of an icosahedron inscribed in a sphere.
    */

   public static final int[] ICOSAHEDRON_FACE_17 = {5, 10, 4};

   /**
    * Nineteenth indexed face of an icosahedron inscribed in a sphere.
    */

   public static final int[] ICOSAHEDRON_FACE_18 = {5, 4, 8};

   /**
    * Twentieth indexed face of an icosahedron inscribed in a sphere.
    */

   public static final int[] ICOSAHEDRON_FACE_19 = {6, 9, 0};

   /**
    * First indexed face of an dodecahedron inscribed in a sphere.
    */

   public static final int[] DODECAHEDRON_FACE_0 = {0, 1, 2, 3, 4};

   /**
    * Second indexed face of an dodecahedron inscribed in a sphere.
    */

   public static final int[] DODECAHEDRON_FACE_1 = {0, 5, 11, 6, 1};

   /**
    * Third indexed face of an dodecahedron inscribed in a sphere.
    */

   public static final int[] DODECAHEDRON_FACE_2 = {1, 6, 12, 7, 2};

   /**
    * Fourth indexed face of an dodecahedron inscribed in a sphere.
    */

   public static final int[] DODECAHEDRON_FACE_3 = {2, 7, 13, 8, 3};

   /**
    * Fifth indexed face of an dodecahedron inscribed in a sphere.
    */

   public static final int[] DODECAHEDRON_FACE_4 = {3, 8, 14, 9, 4};

   /**
    * Sixth indexed face of an dodecahedron inscribed in a sphere.
    */

   public static final int[] DODECAHEDRON_FACE_5 = {4, 9, 10, 5, 0};

   /**
    * Seventh indexed face of an dodecahedron inscribed in a sphere.
    */

   public static final int[] DODECAHEDRON_FACE_6 = {15, 10, 5, 11, 16};

   /**
    * Eighth indexed face of an dodecahedron inscribed in a sphere.
    */

   public static final int[] DODECAHEDRON_FACE_7 = {16, 11, 6, 12, 17};

   /**
    * Ninth indexed face of an dodecahedron inscribed in a sphere.
    */

   public static final int[] DODECAHEDRON_FACE_8 = {17, 12, 7, 13, 18};

   /**
    * Tenth indexed face of an dodecahedron inscribed in a sphere.
    */

   public static final int[] DODECAHEDRON_FACE_9 = {18, 13, 8, 14, 19};

   /**
    * Eleventh indexed face of an dodecahedron inscribed in a sphere.
    */

   public static final int[] DODECAHEDRON_FACE_10 = {19, 14, 9, 10, 15};

   /**
    * Twelfth indexed face of an dodecahedron inscribed in a sphere.
    */

   public static final int[] DODECAHEDRON_FACE_11 = {15, 16, 17, 18, 19};

   /**
    * First indexed face of an octahedron inscribed in a sphere.
    */

   public static final int[] OCTAHEDRON_FACE_0 = {2, 0, 5};

   /**
    * Second indexed face of an octahedron inscribed in a sphere.
    */

   public static final int[] OCTAHEDRON_FACE_1 = {2, 5, 1};

   /**
    * Third indexed face of an octahedron inscribed in a sphere.
    */

   public static final int[] OCTAHEDRON_FACE_2 = {2, 1, 4};

   /**
    * Fourth indexed face of an octahedron inscribed in a sphere.
    */

   public static final int[] OCTAHEDRON_FACE_3 = {2, 4, 0};

   /**
    * Fifth indexed face of an octahedron inscribed in a sphere.
    */

   public static final int[] OCTAHEDRON_FACE_4 = {3, 5, 0};

   /**
    * Sixth indexed face of an octahedron inscribed in a sphere.
    */

   public static final int[] OCTAHEDRON_FACE_5 = {3, 0, 4};

   /**
    * Seventh indexed face of an octahedron inscribed in a sphere.
    */

   public static final int[] OCTAHEDRON_FACE_6 = {3, 4, 1};

   /**
    * Eighth indexed face of an octahedron inscribed in a sphere.
    */

   public static final int[] OCTAHEDRON_FACE_7 = {3, 1, 5};

   /**
    * First indexed face of an cube inscribed in a sphere.
    */

   public static final int[] CUBE_FACE_0 = {0, 1, 3, 2};

   /**
    * Second indexed face of an cube inscribed in a sphere.
    */

   public static final int[] CUBE_FACE_1 = {2, 3, 7, 6};

   /**
    * Third indexed face of an cube inscribed in a sphere.
    */

   public static final int[] CUBE_FACE_2 = {4, 6, 7, 5};

   /**
    * Fourth indexed face of an cube inscribed in a sphere.
    */

   public static final int[] CUBE_FACE_3 = {0, 4, 5, 1};

   /**
    * Fifth indexed face of an cube inscribed in a sphere.
    */

   public static final int[] CUBE_FACE_4 = {0, 2, 6, 4};

   /**
    * Sixth indexed face of an cube inscribed in a sphere.
    */

   public static final int[] CUBE_FACE_5 = {1, 5, 7, 3};

   /**
    * First indexed face of an tetrahedron inscribed in a sphere.
    */

   public static final int[] TETRAHEDRON_FACE_0 = {0, 1, 2};

   /**
    * Second indexed face of an tetrahedron inscribed in a sphere.
    */

   public static final int[] TETRAHEDRON_FACE_1 = {2, 1, 3};

   /**
    * Third indexed face of an tetrahedron inscribed in a sphere.
    */

   public static final int[] TETRAHEDRON_FACE_2 = {0, 2, 3};

   /**
    * Fourth indexed face of an tetrahedron inscribed in a sphere.
    */

   public static final int[] TETRAHEDRON_FACE_3 = {0, 3, 1};

}