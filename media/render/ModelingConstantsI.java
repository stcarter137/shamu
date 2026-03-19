package shamu.media.render;

/** Interface containing constants used in implementing procedural modeling algorithms.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public interface ModelingConstantsI {

   /**
    * Number of coordinates per point in the 2D plane.
    */

   public static final int PLANE_POINT_STRIDE = 2;

   /**
    * Number of coordinates per point in 3D space.
    */

   public static final int SPACE_POINT_STRIDE = 3;

   /**
    * Number of coordinates per surface tangent in 3D space.
    */

   public static final int TANGENT_STRIDE = 3;

   /**
    * Number of coordinates per surface normal in 3D space.
    */

   public static final int NORMAL_STRIDE = 3;

   /**
    * Number of coordinates per surface texel in 3D space.
    */

   public static final int TEXEL_STRIDE  = 2;

   /**
    * Number of components per color in RGB space.
    */

   public static final int COLOR_STRIDE  = 3;

}