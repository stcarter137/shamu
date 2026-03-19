package shamu.media.render;

/** Procedural modeling class representing a monkey saddle surface that is parameterized as the monge patch [x, y, z] = [x, (x ^ 3) / alpha - 3 * (z ^ 2) / beta, z].
 *
 *  @see MongePatch
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class MonkeySaddle extends MongePatch {

   /**
    * The alpha parameter associated with this monkey saddle.
    */

   protected float alpha;

   /**
    * The beta parameter associated with this monkey saddle.
    */

   protected float beta;

   /**
    * Constructs a default monkey saddle.
    */

   public MonkeySaddle() { super(); }

   /**
    * Constructs a monkey saddle having the alpha parameter, beta parameter, and boundary determined by the floating point arguments.
    *
    * @param alpha the value that the alpha parameter of this monkey saddle is set to.
    * @param beta the value that the beta parameter of this monkey saddle is set to.
    * @param lowerBoundsU1 the value that the u1 local variable lower bound that determines the boundary of this monkey saddle is set to.
    * @param lowerBoundsU2 the value that the u2 local variable lower bound that determines the boundary of this monkey saddle is set to.
    * @param upperBoundsU1 the value that the u1 local variable upper bound that determines the boundary of this monkey saddle is set to.
    * @param upperBoundsU2 the value that the u2 local variable upper bound that determines the boundary of this monkey saddle is set to.
    */

   public MonkeySaddle(float alpha, float beta, float lowerBoundsU1, float lowerBoundsU2, float upperBoundsU1, float upperBoundsU2) {

      super(lowerBoundsU1, lowerBoundsU2, upperBoundsU1, upperBoundsU2);

      this.alpha = alpha;
      this.beta = beta;

   }

   /**
    * Calculates and returns the y-coordinate of the point on this monkey saddle that is mapped from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point on this monkey saddle that is mapped from the floating point arguments.
    */

   public float mapY(float u1, float u2) { return (float)Math.pow(u1, 3.0f) / alpha - 3.0f * u1 * (float)Math.pow(u2, 2.0) / beta; }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the partial derivative of this monkey saddle with respect to the u1 local
    * variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the partial derivative of this monkey saddle with respect to the u1 local variable from the floating point arguments.
    */

   public float partialU1MapY(float u1, float u2) { return 3.0f * (float)Math.pow(u1, 2.0) / alpha - 3.0f * (float)Math.pow(u2, 2.0) / beta; }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the partial derivative of this monkey saddle with respect to the u2 local
    * variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the partial derivative of this monkey saddle with respect to the u2 local variable from the floating point arguments.
    */

   public float partialU2MapY(float u1, float u2) { return -6.0f * u1 * u2 / beta; }

   /**
    * Returns the alpha parameter associated with this monkey saddle.
    *
    * @return the alpha parameter associated with this monkey saddle.
    */

   public float getAlpha() { return alpha; }

   /**
    * Returns the beta parameter associated with this monkey saddle.
    *
    * @return the beta parameter associated with this monkey saddle.
    */

   public float getBeta() { return beta; }

   /**
    * Sets the value of the alpha parameter associated with this monkey saddle to the floating point argument.
    *
    * @param alpha the value that the alpha parameter associated with this monkey saddle is set to.
    */

   public void setAlpha(float alpha) { this.alpha = alpha; }

   /**
    * Sets the value of the beta parameter associated with this monkey saddle to the floating point argument.
    *
    * @param beta the value that the beta parameter associated with this monkey saddle is set to.
    */

   public void setBeta(float beta) { this.beta = beta; }

}


