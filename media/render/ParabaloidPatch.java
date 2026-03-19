package shamu.media.render;

/** Procedural modeling class representing a parabaloid surface that is parameterized as the monge patch [x, y, z] = [x, (x ^ 2) / alpha + (z ^ 2) / beta, z].
 *
 *  @see MongePatch
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class ParabaloidPatch extends MongePatch {

   /**
    * The alpha parameter associated with this parabaloid patch.
    */

   protected float alpha;

   /**
    * The beta parameter associated this parabaloid patch.
    */

   protected float beta;

   /**
    * Constructs a default parabaloid.
    */

   public ParabaloidPatch() { super(); }

   /**
    * Constructs a parabaloid having the alpha parameter, beta parameter, and boundary determined by the floating point arguments.
    *
    * @param alpha the value that the alpha parameter of this parabaloid patch is set to.
    * @param beta the value that the beta parameter of this parabaloid patch is set to.
    * @param lowerBoundsU1 the value that the u1 local variable lower bound that determines the boundary of this parabaloid patch is set to.
    * @param lowerBoundsU2 the value that the u2 local variable lower bound that determines the boundary of this parabaloid patch is set to.
    * @param upperBoundsU1 the value that the u1 local variable upper bound that determines the boundary of this parabaloid patch is set to.
    * @param upperBoundsU2 the value that the u2 local variable upper bound that determines the boundary of this parabaloid patch is set to.
    */

   public ParabaloidPatch(float alpha, float beta, float lowerBoundsU1, float lowerBoundsU2, float upperBoundsU1, float upperBoundsU2) {

      super(lowerBoundsU1, lowerBoundsU2, upperBoundsU1, upperBoundsU2);

      this.alpha = alpha;
      this.beta = beta;

   }

   /**
    * Calculates and returns the y-coordinate of the point on this parabaloid patch that is mapped from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point on this parabaloid patch that is mapped from the floating point arguments.
    */

   public float mapY(float u1, float u2) { return ((float)Math.pow(u1, 2.0) / alpha + (float)Math.pow(u2, 2.0) / beta); }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the partial derivative of this parabaloid patch with respect to the u1 local
    * variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the partial derivative of this parabaloid patch with respect to the u1 local variable from the floating point arguments.
    */

   public float partialU1MapY(float u1, float u2) { return 2 * u1 / alpha; }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the partial derivative of this parabaloid patch with respect to the u2 local
    * variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the partial derivative of this parabaloid patch with respect to the u2 local variable from the floating point arguments.
    */

   public float partialU2MapY(float u1, float u2) { return 2 * u2 / beta; }

   /**
    * Returns the alpha parameter associated with this parabaloid patch.
    *
    * @return the alpha parameter associated with this parabaloid patch.
    */

   public float getAlpha() { return alpha; }

   /**
    * Returns the beta parameter associated with this parabaloid patch.
    *
    * @return the beta parameter associated with this parabaloid patch.
    */

   public float getBeta() { return beta; }

   /**
    * Sets the value of the alpha parameter associated with this parabaloid patch to the floating point argument.
    *
    * @param alpha the value that the alpha parameter associated with this parabaloid patch is set to.
    */

   public void setAlpha(float alpha) { this.alpha = alpha; }

   /**
    * Sets the value of the beta parameter associated with this parabaloid patch to the floating point argument.
    *
    * @param beta the value that the beta parameter associated with this parabaloid patch is set to.
    */

   public void setBeta(float beta) { this.beta = beta; }

}
