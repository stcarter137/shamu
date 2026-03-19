package shamu.media.render;

/** Class containing values of attributes used in rendering a 3D Shamu model.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class RenderingAttributes implements RenderingConstantsI {

   /**
    * The flag indicating the local shading algorithm used in rendering.
    */

   protected int localShadingAttribute = COOK_TORRANCE_SHADING_ENABLED;

   /**
    * The flag indicating the texturing algorithm used in rendering.
    */

   protected int texturingAttribute = TEXTURING_DISABLED;

   /**
    * The flag indicating the bump mapping algorithm used in rendering.
    */

   protected int bumpingAttribute = BUMP_MAPPING_DISABLED;

  /**
    * The flag indicating the state of integral curves on a surface highlighted in rendering.
    */

   protected int curveHighlightsAttribute = INTEGRAL_CURVES_DISABLED;

  /**
    * The flag indicating the state of a heat source on a surface highlighted in rendering.
    */

   protected int heatSourceAttribute = HEAT_SOURCE_DISABLED;

  /**
    * The flag indicating the state of shadows cast by a surface in a lighted environment.
    */

   protected int shadowingAttribute = SHADOWING_ENABLED;

   /**
    * The flag indicating the state of the depth buffer used for hidden surface removal .
    */

   protected int depthBufferStateAttribute = DEPTH_BUFFER_ENABLED;

   /**
    * Contructs a default rendering attributes object.
    */

   protected RenderingAttributes() {}

   /**
    * Returns the flag indicating the local shading algorithm used in rendering.
    *
    * @return the flag indicating the local shading algorithm used in rendering.
    */

   public int getLocalShadingAttribute() { return localShadingAttribute; }

   /**
    * Returns the flag indicating the texturing algorithm used in rendering.
    *
    * @return the flag indicating the texturing algorithm used in rendering.
    */

   public int getTexturingAttribute() { return texturingAttribute; }

   /**
    * Returns the flag indicating the bump mapping algorithm used in rendering.
    *
    * @return the flag indicating the bump mapping algorithm used in rendering.
    */

   public int getBumpingAttribute() { return bumpingAttribute; }

   /**
    * Returns the flag indicating state of integral curves on a surface highlighted in rendering.
    *
    * @return the flag indicating the state of integral curves on a surface highlighted in rendering.
    */

   public int getCurveHighlightsAttribute() { return curveHighlightsAttribute; }

   /**
    * Returns the flag indicating the state of a heat sourceon a surface highlighted in rendering.
    *
    * @return the flag indicating the state of a heat source on a surface highlighted in rendering.
    */

   public int getHeatSourceAttribute() { return heatSourceAttribute; }

   /**
    * Returns the flag indicating the state of shadows cast by a surface in a lighted environment
    *
    * @return the flag indicating the state of shadows cast by a surface in a lighted environment.
    */

   public int getShadowingAttribute() { return shadowingAttribute; }

   /**
    * Returns the flag indicating the state of the depth buffer used for hidden surface removal.
    *
    * @return the flag indicating the state of the depth buffere used for hidden surface removalg.
    */

   public int getDepthBufferStateAttribute() { return depthBufferStateAttribute; }

   /**
    * Sets the local shading attribute associated with this rendering attributes object to the integer argument. Currently only RenderingConstantsI.PHONG_ENABLED_SHADING, RenderingConstantsI.PHONG_DISABLED_SHADING are valid.
    *
    * @param the value that the local shading attribute associated with this rendering attributes object is set to.
    */

   public void setLocalShadingAttribute(int localShadingAttribute) { this.localShadingAttribute = localShadingAttribute; }

   /**
    * Sets the texturing attribute associated with this rendering attributes object to the integer argument. Must be equal to RenderingConstantsI.TEXTURING_DISABLED, RenderingConstantsI._UNMODULATED_TEXTURING, or RenderingConstantsI.PHONG_MODULATED_TEXTURING.
    *
    * @param the value that the texturing attribute associated with this rendering attributes object is set to.
    */

   public void setTexturingAttribute(int texturingAttribute) { this.texturingAttribute = texturingAttribute; }

   /**
    * Sets the bumping attribute associated with this rendering attributes object to the integer argument. Must be equal to RenderingConstantsI.BUMP_MAPPING_DISABLED or RenderingConstantsI.TRUE_BUMP_MAPPING_ENABLED.
    *
    * @param the value that the bumping attribute associated with this rendering attributes object is set to.
    */

   public void setBumpingAttribute(int bumpingAttribute) { this.bumpingAttribute = bumpingAttribute; }

   /**
    * Sets the highlighted integral curves attribute associated with this rendering attributes object to the integer argument. Must be equal to RenderingConstantsI.INTEGRAL_CURVES_DISABLED or RenderingConstantsI.INTEGRAL_CURVES_ENABLED
    *
    * @param the value that the hightlighted integral curves attribute associated with this rendering attributes object is set to.
    */

   public void  setCurveHighlightsAttribute(int curveHighlightsAttribute) { this.curveHighlightsAttribute = curveHighlightsAttribute; }

   /**
    * Sets the highlighted heat source attribute associated with this rendering attributes object to the integer argument. Must be equal to RenderingConstantsI.HEAT_SOURCE_DISABLED or RenderingConstantsI.HEAT_SOURCE_ENABLED
    *
    * @param the value that the hightlighted heat source attribute associated with this rendering attributes object is set to.
    */

   public void  setHeatSourceAttribute(int heatSourceAttribute) { this.heatSourceAttribute = heatSourceAttribute; }

   /**
    * Sets the highlighted shadowing attribute associated with this rendering attributes object to the integer argument. Must be equal to RenderingConstantsI.SHADOWING_DISABLED or RenderingConstantsI.SHADOWING_ENABLED
    *
    * @param the value that the shadowing source attribute associated with this rendering attributes object is set to.
    */

   public void setShadowingAttribute(int shadowingAttribute) { this.shadowingAttribute = shadowingAttribute; }

   /**
    * Sets the depth buffer state attribute associated with this rendering attributes object to the integer argument. Must be equal to RenderingConstantsI.DEPTH_BUFFER_DISABLED or RenderingConstantsI.DEPTH_BUFFER_ENABLED.
    *
    * @param the value that the depth buffer state attribute associated with this rendering attributes object is set to.
    */

   public void setDepthBufferStateAttribute(int depthBufferStateAttribute) { this.depthBufferStateAttribute = depthBufferStateAttribute; }

}

