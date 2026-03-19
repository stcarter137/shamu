package shamu.media.render;

/** Class containing values of attributes used in applying a texture to a 3D Dolphin model.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class TextureAttributes {

   /**
    * Red component in the rgba model in the range 0.0 - 1.0, of the texture blending color associated with this texture attributes object.
    */

   protected float blendColorAttributeR = 0.0f;

   /**
    * Green component in the rgba model in the range 0.0 - 1.0, of the texture blending color associated with this texture attributes object.
    */

   protected float blendColorAttributeG = 0.0f;

   /**
    * Blue component in the rgba model in the range 0.0 - 1.0, of the texture blending color associated with this texture attributes object.
    */

   protected float blendColorAttributeB = 0.0f;

   /**
    * Alpha component in the rgba model in the range 0.0 - 1.0, of the texture blending color associated with this texture attributes object.
    */

   protected float blendColorAttributeA = 0.0f;

   /**
    * The matrix that transforms texels on the texture corresponding to this texture attributes object.
    */

  protected int heatBandIndexR = 0;

  protected int heatBandIndexG = 1;

  protected int heatBandIndexB = 2;

  protected Matrix3D textureMatrixAttribute = new Matrix3D(1f, 1f, 0);

   /**
    * Constructs a default texture attributes object.
    */

   public TextureAttributes() {}

   /**
    * Returns the red component of the texture blending color associated with this texture attributes object.
    *
    * @return the red component of the texture blending color associated with this texture attributes object.
    */

   public float getBlendColorAttributeR() { return blendColorAttributeR; }

   /**
    * Returns the green component of the texture blending color associated with this texture attributes object.
    *
    * @return the green component of the texture blending color associated with this texture attributes object.
    */

   public float getBlendColorAttributeG() { return blendColorAttributeG; }

   /**
    * Returns the blue component of the texture blending color associated with this texture attributes object.
    *
    * @return the blue component of the texture blending color associated with this texture attributes object.
    */

   public float getBlendColorAttributeB() { return blendColorAttributeB; }

   /**
    * Returns the alpha component of the texture blending color associated with this texture attributes object.
    *
    * @return the alpha component of the texture blending color associated with this texture attributes object.
    */

   public float getBlendColorAttributeA() { return blendColorAttributeA; }

   public int getHeatBandIndexR() { return heatBandIndexR; }

   public int getHeatBandIndexG() { return heatBandIndexG; }

   public int getHeatBandIndexB() { return heatBandIndexB; }


   /**
    * Returns the matrix associated with this texture attributes object that transform texels.
    *
    * @return the matrix associated with this texture attributes object that transform texels..
    */

   public Matrix3D getTextureMatrixAttribute() { return textureMatrixAttribute; }

   /**
    * Sets the red component of the texture blending color associated with this texture attributes object to the floating point argument.
    *
    * @param blendColorAttributeR the value that the red component of the texture blending color associated with this texture attributes object is set to.
    */

   public void setBlendColorAttributeR(float blendColorAttributeR) { this.blendColorAttributeR = blendColorAttributeR; }

   /**
    * Sets the green component of the texture blending color associated with this texture attributes object to the floating point argument.
    *
    * @param blendColorAttributeG the value that the green component of the texture blending color associated with this texture attributes object is set to.
    */

   public void setBlendColorAttributeG(float blendColorAttributeG) { this.blendColorAttributeG = blendColorAttributeG; }

   /**
    * Sets the blue component of the texture blending color associated with this texture attributes object to the floating point argument.
    *
    * @param blendColorAttributeB the value that the blue component of the texture blending color associated with this texture attributes object is set to.
    */

   public void setBlendColorAttributeB(float blendColorAttributeB) { this.blendColorAttributeB = blendColorAttributeB; }

   /**
    * Sets the alpha component of the texture blending color associated with this texture attributes object to the floating point argument.
    *
    * @param blendColorAttributeA the value that the alpha component of the texture blending color associated with this texture attributes object is set to.
    */

   public void setBlendColorAttributeA(float blendColorAttributeA) { this.blendColorAttributeA = blendColorAttributeA; }

   public void setHeatBandIndexR(int heatBandIndexR) { this.heatBandIndexR = heatBandIndexR; }

   public void setHeatBandIndexG(int heatBandIndexG) { this.heatBandIndexG = heatBandIndexG; }

   public void setHeatBandIndexB(int heatBandIndexB) { this.heatBandIndexB = heatBandIndexB; }

   /**
    * Sets the matrix associated with this texture attributes object that transforms texels to the matrix argument.
    *
    * @param textureMatrixAttribute the matrix that the matrix associated with this texture attributes object that transforms texels is set to.
    */

   public void setTextureMatrixAttribute(Matrix3D textureMatrixAttribute) { this.textureMatrixAttribute = textureMatrixAttribute; }

}