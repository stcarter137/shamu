package shamu.media.render;

/** Class representing a hyperbola that is parameterized as [x(t), y(t)] = [alpha * cosh(t), beta * sinh(t)].
 *
 *  @see ProfileCurve2D
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class Hyperbola extends ProfileCurve2D {

   /**
    * The alpha parameter of this hyperbola.
    */

   protected float alpha;

   /**
    * The beta parameter of this hyperbola.
    */

   protected float beta;

   /**
    * Constructs a default hyperbola.
    */

   public Hyperbola() { super(); }

   /**
    * Constructs a hyperbola with the level of detail specified by the integer arguments and having the alpha parameter, beta parameter, and boundary determined by the floating point arguments.
    *
    * @param alpha the value that the alpha parameter of this hyperbola is set to.
    * @param beta the value that the beta parameter of this hyperbola is set to.
    * @param lowerBoundsT the value that the parametric variable lower bound that determines the boundary of this hyperbola is set to.
    * @param upperBoundsT the value that the parametric variable upper bound that determines the boundary of this hyperbola is set to.
    */

   public Hyperbola(float alpha, float beta, float lowerBoundsT, float upperBoundsT) {

 	  super(lowerBoundsT, upperBoundsT);

      this.alpha = alpha;
      this.beta = beta;

   }

   /**
    * Calculates and returns the x-coordinate of the point on this hyperbola in (x, y) space mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point on this hyperbola in (x, y) space mapped from the floating point argument.
    */

   public float mapX(float t) { return alpha * cosh(t); }

   /**
    * Calculates and returns the y-coordinate of the point on this hyperbola in (x, y) space mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point on this hyperbola in (x, y) space mapped from the floating point argument.
    */

   public float mapY(float t) { return beta * sinh(t); }

   /**
    * Calculates and returns the x-coordinate of the point in (x, y) space mapped by the derivative of this hyperbola from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point in (x, y) space mapped by the derivative of this hyperbola from the floating point argument.
    */

   public float derivativeMapX(float t) { return alpha * sinh(t); }

   /**
    * Calculates and returns the y-coordinate of the point in (x, y) space mapped by the derivative of this hyperbola from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point in (x, y) space mapped by the derivative of this hyperbola from the floating point argument.
    */

   public float derivativeMapY(float t) { return beta * cosh(t); }

   /**
    * Calculates and returns the hyperbolic cosine function, defined as cosh(t) = [exp(t) + exp(-t)]  / 2, evaluated at the floating point argument.
    *
    * @param x the dependent variable that the hyperbolic cosine function is evaluated at.
    * @return the value returned by the hyperbolic cosine function evaluated at the floating point argument.
    */

   protected float cosh(float t) {return ( (float)Math.exp(t) + (float)Math.exp(-t)) / 2; }

   /**
    * Calculates and returns the hyperbolic sine function, defined as cosh(t) = [exp(t) - exp(-t)]  / 2, evaluated at the floating point argument.
    *
    * @param x the dependent variable that the hyperbolic sine function is evaluated at.
    * @return the value returned by the hyperbolic sine function evaluated at the floating point argument.
    */

   protected float sinh(float t) {return ( (float)Math.exp(t) - (float)Math.exp(-t)) / 2; }

   /**
    * Returns the alpha parameter associated with this hyperbola.
    *
    * @return the alpha parameter associated with this hyperbola.
    */

   public float getAlpha() { return alpha; }

   /**
    * Returns the beta parameter associated with this hyperbola.
    *
    * @return the beta parameter associated with this hyperbola.
    */

   public float getBeta() { return beta; }

   /**
    * Sets the value of the alpha parameter associated with this hyperbola to the floating point argument.
    *
    * @param alpha the value that the alpha parameter associated with this hyperbola is set to.
    */

   public void setAlpha(float alpha) { this.alpha = alpha; }

   /**
    * Sets the value of the beta parameter associated with this hyperbola to the floating point argument.
    *
    * @param beta the value that the beta parameter associated with this hyperbola is set to.
    */

   public void setBeta(float beta) { this.beta = beta; }

}
