package shamu.media.render;

/** Class encaspulating 2D image texture maps used for pixel shading, allowing programmer controlled bias and scale factors. For bump maps,
 *  the green and blue channels of the underlying image associated with the texture map represent bump gradients normalized to the range [-1, 1].
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class TextureMap {

   /**
    * Texture boundary mode flag identifying this texture map as clamping texels outside the range [0, 1] to the boundary of this texture map.
    */

   public static final int WRAP_MODE = 0xFD0000;

   /**
    * Texture boundary mode flag identifying this texture map as wrapping texels outside the range [0, 1] such that only the fractional part of texels are considered.
    */

   public static final int CLAMP_MODE = 0xEC0000;

   /**
    * Texture stage flag identifying this texture map as the base texture stage of the texturing pipeline.
    */

   public static final int BASE_TEXTURE_FLAG = 0x0000AF;

   /**
    * Texture stage flag identifying this texture map as the bump map stage of the texturing pipeline.
    */

   public static final int BUMP_MAP_FLAG = 0x0000BF;

   /**
    * Texture stage flag identifying this texture map as the environment map stage of the texturing pipeline.
    */

   public static final int ENVIRONMENT_MAP_FLAG = 0x0000DF;

   /**
    * Texture stage flag identifying this texture map as the environment map stage of the texturing pipeline.
    */

   public static final int HEATSOURCE_MAP_FLAG = 0x0000EF;

   public static final int CURVE_MAP_FLAG = 0x0000FF;

   /**
    * Flag identifying texel colors associated with this texture map are computed by bilinear interpolation.
    */

   public static final int BILINEAR_FILTER = 0x00001A;

   /**
    * Flag identifying texel colors associated with this texture map are computed by determining the nearest neighbor.
    */

   public static final int NEAREST_NEIGHBOR_FILTER = 0x00001C;

   /**
    * Color model object assicoated with the image encapsulated by this texture map
    */ 

   protected java.awt.image.ColorModel colorModel;

   /**
    * Raster object assocated with the image encapsulated by this texture map
    */

   protected java.awt.image.Raster pixelData;

   /**
    * The magnification filter used in computing texel color of this texture map.
    */

   protected int magnificationFilter = BILINEAR_FILTER;

   /**
    * The minification filter used in computing texel color of this texture map.
    */

   protected int minificationFilter = BILINEAR_FILTER;

   /**
    * The boundary mode of texel u-coordinates associated with this texture map.
    */

   protected int boundaryModeU = WRAP_MODE;

   /**
    * The boundary mode of texel v-coordinates associated with this texture map.
    */

   protected int boundaryModeV = WRAP_MODE;

   /**
    * The scale factor applied to texel data associated with this texture map.
    */

   protected float scaleFactor = 1.0f;

   /**
    * The bias factor applied to texel data associated with this texture map.
    */

   protected float biasFactor = 0.0f;

   /**
    * The offset  applied to every texel's u-coordinate associated with this texture map.
    */

   protected float offsetU = 0.0f;

   /**
    * The offset  applied to every texel's v-coordinate associated with this texture map.
    */

   protected float offsetV = 0.0f;

   protected boolean enabled = true;

   /**
    * Constructs a texture map associated with the rendered image argument.
    *
    * @param image2D the image that this texture map is set to be associated with.
    */

   public TextureMap(java.awt.image.RenderedImage image2D) { 

      this.colorModel = image2D.getColorModel();
      
      this.pixelData = image2D.getData();

   }

   public void disable() { enabled = false; }

   public void enable() { enabled = true; }

   public boolean isEnabled() { return enabled; }

   /**
    * Interpolates the decoded texel data corresponding to the texel neighborhood determined by the integer and floating point arguments and stores the interpolated data in the floating point array argument.
    *
    * @param textureFlag the flag identifying the algorithm for decoding texel data. Must be equal to _BASE_TEXTURE_FLAG, _NORMAL_MAP_FLAG, _LIGHT_MAP_FLAG, or _ENVIRONMENT_FLAG.
    * @param u the u-coordinate of the texel specifying the texel whose data is interpolated.
    * @param v the v-coordinate of the texel specifying the texel whose data is interpolated.
    * @param texelData the array storing the interpolated texel data.
    */

   public void readTexel(int textureFlag, float u, float v, float[] texelData) {

      try {

         int width = pixelData.getWidth();
         int height = pixelData.getHeight();

         float adjustedU = (float)(Math.max(0, width  * (u + offsetU) - 1));
         float adjustedV = (float)(Math.max(0, height * (v + offsetV) - 1));

         float fractU = adjustedU - (float)Math.floor(adjustedU);
         float fractV = adjustedV - (float)Math.floor(adjustedV);

         float x1 = (float)Math.floor(adjustedU);
         float x2 = (float)Math.ceil(adjustedU);

         float y1 = (float)Math.floor(adjustedV);
         float y2 = (float)Math.ceil(adjustedV);

         int[] unnormalizedComponentsUpperLeft = new int[4];
         int[] unnormalizedComponentsUpperRight = new int[4];

         int[] unnormalizedComponentsLowerLeft = new int[4];
         int[] unnormalizedComponentsLowerRight = new int[4];

         Object pixelUpperLeft = pixelData.getDataElements((int)x1, (int)y1, null);
         Object pixelUpperRight = pixelData.getDataElements((int)x2, (int)y1, null);

         Object pixelLowerLeft = pixelData.getDataElements((int)x1, (int)y2, null);
         Object pixelLowerRight = pixelData.getDataElements((int)x2, (int)y2, null);

         colorModel.getComponents(pixelUpperLeft, unnormalizedComponentsUpperLeft, 0);
         colorModel.getComponents(pixelUpperRight, unnormalizedComponentsUpperRight, 0);

         colorModel.getComponents(pixelLowerLeft, unnormalizedComponentsLowerLeft, 0);
         colorModel.getComponents(pixelLowerRight, unnormalizedComponentsLowerRight, 0);

         float[] normalizedComponentsUpperLeft = new float[4];
         float[] normalizedComponentsUpperRight = new float[4];

         float[] normalizedComponentsLowerLeft = new float[4];
         float[] normalizedComponentsLowerRight = new float[4];

         pixelData.getPixel((int)x1, (int)y1, normalizedComponentsUpperLeft);
         pixelData.getPixel((int)x2, (int)y1, normalizedComponentsUpperRight);
         pixelData.getPixel((int)x1, (int)y1, normalizedComponentsLowerLeft);
         pixelData.getPixel((int)x2, (int)y1, normalizedComponentsLowerRight);

         colorModel.getNormalizedComponents(unnormalizedComponentsUpperLeft, 0, normalizedComponentsUpperLeft, 0);
         colorModel.getNormalizedComponents(unnormalizedComponentsUpperRight, 0, normalizedComponentsUpperRight, 0);
         colorModel.getNormalizedComponents(unnormalizedComponentsLowerLeft, 0, normalizedComponentsLowerLeft, 0);
         colorModel.getNormalizedComponents(unnormalizedComponentsLowerRight, 0, normalizedComponentsLowerRight, 0);

         if(magnificationFilter == BILINEAR_FILTER) {

            texelData[0] = (1 - fractV) * ((1 - fractU) * normalizedComponentsUpperLeft[0] + fractU * normalizedComponentsUpperRight[0]) + fractV * ((1 - fractU) * normalizedComponentsLowerLeft[0] + fractU * normalizedComponentsLowerRight[0]);
            texelData[1] = (1 - fractV) * ((1 - fractU) * normalizedComponentsUpperLeft[1] + fractU * normalizedComponentsUpperRight[1]) + fractV * ((1 - fractU) * normalizedComponentsLowerLeft[1] + fractU * normalizedComponentsLowerRight[1]);
            texelData[2] = (1 - fractV) * ((1 - fractU) * normalizedComponentsUpperLeft[2] + fractU * normalizedComponentsUpperRight[2]) + fractV * ((1 - fractU) * normalizedComponentsLowerLeft[2] + fractU * normalizedComponentsLowerRight[2]);
            texelData[3] = (1 - fractV) * ((1 - fractU) * normalizedComponentsUpperLeft[3] + fractU * normalizedComponentsUpperRight[3]) + fractV * ((1 - fractU) * normalizedComponentsLowerLeft[3] + fractU * normalizedComponentsLowerRight[3]);

	     }

         else if(magnificationFilter == NEAREST_NEIGHBOR_FILTER) {

            if(fractU < 0.5f && fractV < 0.5f) {

               texelData[0] = normalizedComponentsUpperLeft[0];
               texelData[1] = normalizedComponentsUpperLeft[1];
               texelData[2] = normalizedComponentsUpperLeft[2];
               texelData[3] = normalizedComponentsUpperLeft[3];

            }

            else if(fractU < 0.5f) {

               texelData[0] = normalizedComponentsLowerLeft[0];
               texelData[1] = normalizedComponentsLowerLeft[1];
               texelData[2] = normalizedComponentsLowerLeft[2];
               texelData[3] = normalizedComponentsLowerLeft[3];

            }

            else if(fractV < 0.5f) {

               texelData[0] = normalizedComponentsUpperRight[0];
               texelData[1] = normalizedComponentsUpperRight[1];
               texelData[2] = normalizedComponentsUpperRight[2];
               texelData[3] = normalizedComponentsUpperRight[3];

            }

            else {

               texelData[0] = normalizedComponentsLowerRight[0];
               texelData[1] = normalizedComponentsLowerRight[1];
               texelData[2] = normalizedComponentsLowerRight[2];
               texelData[3] = normalizedComponentsLowerRight[3];

            }

	     }

         texelData[0] -= biasFactor;
         texelData[1] -= biasFactor;
         texelData[2] -= biasFactor;
         texelData[3] -= biasFactor;

         texelData[0] *= scaleFactor;
         texelData[1] *= scaleFactor;
         texelData[2] *= scaleFactor;
         texelData[3] *= scaleFactor;

         if(textureFlag == BUMP_MAP_FLAG) {

            texelData[0] = texelData[1];
            texelData[1] = texelData[2];

         }

       } catch(Exception e) {System.out.println(e);}

   }

   /**
    * Returns the color model object of the image encapsulated by this text map.
    */

   public java.awt.image.ColorModel getColorModel() { return this.colorModel; }

      /**
    * Returns the color model object of the image encapsulated by this text map.
    */

   public java.awt.image.Raster getPixelData() { return this.pixelData; }

   /**
    * Returns the magnification filter associated with this texture map.
    *
    * @return the magnification filter associated with this texture map.
    */

   public int getMagnificationFilter() { return magnificationFilter; }

   /**
    * Returns the minification filter associated with this texture map.
    *
    * @return the minification filter associated with this texture map.
    */

   public int getMinificationFilter() { return minificationFilter; }

   /**
    * Returns the boundary mode of texel u-coordinates associated with this texture map.
    *
    * @return the boundary mode of texel u-coordinates associated with this texture map.
    */

   public int getBoundaryModeU() { return boundaryModeU; }

   /**
    * Returns the boundary mode of texel v-coordinates associated with this texture map.
    *
    * @return the boundary mode of texel v-coordinates associated with this texture map.
    */

   public int getBoundaryModeV() { return boundaryModeV; }

   /**
    * Returns the scale factor of texel data associated with this texture map.
    *
    * @return the scale factor of textel data associated with this texture map.
    */

   public float getScaleFactor() { return scaleFactor; }

   /**
    * Returns the bias factor of texel data associated with this texture map.
    *
    * @return the bias factor of textel data associated with this texture map.
    */

   public float getBiasFactor() { return biasFactor; }

   /**
     * Returns the offset  of every texel's u-coordinate associated with this texture map.
     *
     * @return the offset of every texel's u-coordinate associated with this texture map.
     */

   public float getOffsetU() { return offsetU; }

   /**
     * Returns the offset  of every texel's v-coordinate associated with this texture map.
     *
     * @return the offset of every texel's v-coordinate associated with this texture map.
     */

   public float getOffsetV() { return offsetV; }

   /**
    * Sets the ColorModel object associcated with this texture map to the ColorModel parameter.
    * 
    * @param colorModel the object that the color model associated withthis texture map is set to
    */

   public void setColorModei(java.awt.image.ColorModel colorModel) { this.colorModel = colorModel; }   

   /**
    * Sets the Raster object associcated with this texture map to the Raster parameter.
    * 
    * @param pixelData the Raster object that the pixel data associated with this texture map is set to
    */

   public void setPixelData(java.awt.image.Raster pixelData) { this.pixelData = pixelData; }   

   /**
    * Sets the magnification filter associated with this texture map to the integer argument.
    *
    * @param magnificationFilter the value that the magnification filter associated with this texture map is set to.
    */

   public void setMagnificationFilter(int magnificationFilter) { this.magnificationFilter = magnificationFilter; }

   /**
    * Sets the minification filter associated with this texture map to the integer argument.
    *
    * @param minificationFilter the value that the minification filter associated with this texture map is set to.
    */

   public void setMinificationFilter(int minificationFilter) { this.magnificationFilter = minificationFilter; }

   /**
    * Sets the boundary mode of texel u-coordinates associated with this texture map to the integer argument. Must be equal to WRAP_MODE or CLAMP_MODE.
    *
    * @param boundaryModeU the value that the u-coordinate boundary mode associated with this texture map is set to.
    */

   public void setBoundaryModeU(int boundaryModeU) { this.boundaryModeU = boundaryModeU; }

   /**
    * Sets the boundary mode of texel v-coordinates associated with this texture map to the integer argument. Must be equal to WRAP_MODE or CLAMP_MODE.
    *
    * @param boundaryModeV the value that the v-coordinate boundary mode associated with this texture map is set to.
    */

   public void setBoundaryModeV(int boundaryModeV) { this.boundaryModeV = boundaryModeV; }

   /**
    * Sets the scale factor of texel data associated with this texture map to the integer argument.
    *
    * @param scaleFactor the value that the scale factor associated with this texture map is set to.
    */

   public void setScaleFactor(float scaleFactor) { this.scaleFactor = scaleFactor; }

   /**
    * Sets the bias factor of texel data associated with this texture map to the integer argument.
    *
    * @param biasFactor the value that the bias factor associated with this texture map is set to.
    */

   public void setBiasFactor(float biasFactor) { this.biasFactor = biasFactor; }

   /**
     * Sets  the offset  of every texel's u-coordinate associated with this texture map to the floating point argument.
     *
     * @param offsetV the offset of every texel's u-coordinate associated with this texture map.
     */

   public void setOffsetU(float offsetU) { this.offsetU = offsetU; }

   /**
     * Sets  the offset  of every texel's v-coordinate associated with this texture map to the floating point argument.
     *
     * @param offsetV the offset of every texel's v-coordinate associated with this texture map.
     */

   public void setOffsetV(float offsetV) { this.offsetV = offsetV; }

}
