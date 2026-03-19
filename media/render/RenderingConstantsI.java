package shamu.media.render;

/** Interface containing constants used in implementing shading algorithms for rendering.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public interface RenderingConstantsI {

   /**
    * Constant identifying that a depth buffer is set as the display raster of a device.
    */

   public static final int DEPTH_BUFFER_DISPLAY = 0xFF000F;

   /**
    * Constant identifying that an color buffer is set as the display raster of a device.
    */

   public static final int COLOR_BUFFER_DISPLAY = 0xF000FF;

   /**
    * Constant identifying that a vitual buffer is to be cleared with a value of {0, 0}.
    */

   public static final int VIRTUAL_BUFFER_CLEAR = 0xF0000C;

   /**
    * Constant identifying that a depth buffer is to be cleared with a value of 1.0.
    */

   public static final int DEPTH_BUFFER_CLEAR = 0xF0000A;

   /**
    * Constant identifying that an accumulation buffer is to be cleared with a value of {0.0, 0.0, 0.0}.
    */

   public static final int ACCUMULATION_BUFFER_CLEAR = 0xF0000B;

   /**
    * Constant identifying models visualized without using textures.
    */

   public static final int TEXTURING_DISABLED = 0x000000;

   /**
    * Constant identifying models visualized with pixels shaded with colors calculated from a single texture map.
    */

   public static final int TEXTURING_ENABLED = 0x0BBBBB;

   /**
    * Constant identifying models visualized without using bump mapping algorithms.
    */

   public static final int BUMP_MAPPING_DISABLED = 0xDDDDD0;

   /**
    * Constant identifying models visualized using the original Blinn bump mapping algorithm.
    */

   public static final int TRUE_BUMP_MAPPING_ENABLED = 0xEEEEE0;

   /**
     * Constant identifying models visualized with hightlighted integral curves
     */

   public static final int INTEGRAL_CURVES_ENABLED = 0xAEEEEE;

   /**
     * Constant identifying models visualized with a hightlighted heat source
     */

   public static final int HEAT_SOURCE_ENABLED  = 0xADDDDD;

   /**
     * Constant identifying a rendering state that includes shadows.
     */

   public static final int SHADOWING_ENABLED = 0xCFFFFF;

   /**
    * Constant identifying models visualized using flat shading.
    */

   public static final int FLAT_SHADING_ENABLED = 0xEFEFEF;

   /**
    * Constant identifying models visualized using Phong shading.
    */

   public static final int PHONG_SHADING_ENABLED = 0x0FFFFF;

   /**
    * Constant identifying models visualized using Cook-Torrance shading.
    */

   public static final int COOK_TORRANCE_SHADING_ENABLED = 0xAFFFFF;

   public static final int INDEXED_PALLETTE_SHADING = 0xFEDCBA;

  /**
     * Constant identifying models visualized without hightlighted integral curves
     */

   public static final int INTEGRAL_CURVES_DISABLED = 0xBEEEEE;

  /**
     * Constant identifying models visualized without a hightlighted heat source.
     */

   public static final int HEAT_SOURCE_DISABLED  = 0xBDDDDD;

   /**
     * Constant identifying a rendering state that does not includes shadows.
     */

   public static final int SHADOWING_DISABLED = 0xCEEEEE;

   /**
    * Constant identifying models visualized without using interpolation shading.
    */

   public static final int LIGHTING_DISABLED = 0x0EEEEE;

   /**
    * Constant identifying models visualized using hidden surface removal.
    */

   public static final int DEPTH_BUFFER_ENABLED = 0xBFFFFF;

   /**
    * Constant identifying models visualized without using hidden surface removal.
    */

   public static final int DEPTH_BUFFER_DISABLED = 0xFBBBBB;

}