package shamu.media.render;

/** Class representing a curve that is parameterized as [x(t), y(t)] = [ t, alpha * t ^ 2].
 *
 *  @see ProfileCurve2D
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class Parabola extends ProfileCurve2D {

   /**
    * The alpha parameter of this parabola.
    */

   protected float alpha;

   /**
    * Constructs a default parabola.
    */

   public Parabola() { super(); }

   /**
    * Constructs an parabola having the alpha parameter and boundary determined by the floating point arguments.
    *
    * @param alpha the value that the alpha parameter of this parabola is set to.
    * @param lowerBoundsT the value that the parametric variable lower bound that determines the boundary of this parabola is set to.
    * @param upperBoundsT the value that the parametric variable upper bound that determines the boundary of this parabola is set to.
    */

   public Parabola(float alpha, float lowerBoundsT, float upperBoundsT) {

      super(lowerBoundsT, upperBoundsT);

      this.alpha = alpha;

   }

   /**
    * Calculates and returns the x-coordinate of the point on this parabola in (x, y) space mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point on this parabola in (x, y) space mapped from the floating point argument.
    */

   public float mapX(float t) { return  t; }

   /**
    * Calculates and returns the y-coordinate of the point on this parabola in (x, y) space mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point on this parabola in (x, y) space mapped from the floating point argument.
    */

   public float mapY(float t) { return  alpha * (float)Math.pow(t, 2); }

   /**
    * Calculates and returns the x-coordinate of the point in (x, y) space mapped by the derivative of this parabola from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point in (x, y) space mapped by the derivative of this parabola from the floating point argument.
    */

   public float derivativeMapX(float t) { return 1; }

   /**
    * Calculates and returns the y-coordinate of the point in (x, y) space mapped by the derivative of this parabola from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point in (x, y) space mapped by the derivative of this parabola from the floating point argument.
    */

   public float derivativeMapY(float t) { return  2 * alpha * t; }

   /**
    * Returns the alpha parameter associated with this parabola.
    *
    * @return the alpha parameter associated with this parabola.
    */

   public float getAlpha() { return alpha; }

   /**
    * Sets the value of the alpha parameter associated with this parabola to the floating point argument.
    *
    * @param alpha the value that the alpha parameter associated with this parabola is set to.
    */

   public void setAlpha(float alpha) { this.alpha = alpha; }

}