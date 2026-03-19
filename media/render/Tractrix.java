package shamu.media.render;

/** Class representing a curve in 2D space parametrized as [x(t), y(t)] = [alpha * sin(t),  alpha * (cos(t) + log(tan(t/2)))].
 *
 *  @see ProfileCurve2D
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class Tractrix extends ProfileCurve2D  {

   /**
    * The alpha parameter of this tractrix curve.
    */

   protected float alpha;

   /**
    * Constructs a default tractrix curve.
    */

   public Tractrix() { super(); }

   /**
    * Constructs a tractrix curve having the alpha parameter and boundary determined by the floating point argument.
    *
    * @param alpha the value that the alpha parameter of this tractrix curve is set to.
    * @param lowerBoundsT the value that the parametric variable lower bound that determines the boundary of this tractrix is set to.
    * @param upperBoundsT the value that the parametric variable upper bound that determines the boundary of this tractrix is set to.
    */

   public Tractrix(float alpha, float lowerBoundsT, float upperBoundsT) {

      super(lowerBoundsT, upperBoundsT);

      this.alpha = alpha;

   }

   /**
    * Calculates and returns the x-coordinate of the point on this tractrix curve in (x, y) space mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point on this tractrix curve in (x, y) space mapped from the floating point argument.
    */

   public float mapX(float t) { return alpha * (float)Math.sin(t); }

   /**
    * Calculates and returns the y-coordinate of the point on this tractrix curve in (x, y) space mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point on this tractrix curve in (x, y) space mapped from the floating point argument.
    */

   public float mapY(float t) { return  alpha * (float)(Math.cos(t) + Math.log(Math.tan(0.5f * t))); }

   /**
    * Calculates and returns the x-coordinate of the point in (x, y) space mapped by the derivative of this tractrix curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point in (x, y) space mapped by the derivative of this tractrix curve from the floating point argument.
    */

   public float derivativeMapX(float t) { return alpha * ((float)Math.cos(t)); }

   /**
    * Calculates and returns the y-coordinate of the point in (x, y) space mapped by the derivative of this tractrix curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point in (x, y) space mapped by the derivative of this tractrix curve from the floating point argument.
    */

   public float derivativeMapY(float t) { return alpha * (float)(-Math.sin(t) + 0.5f / (Math.tan(0.5f * t) * Math.cos(0.5f * t) * Math.cos(0.5f * t))); }

   /**
    * Returns the alpha parameter associated with this tractrix curve.
    *
    * @return the alpha parameter associnated with this tractrix curve.
    */

   public float getAlpha() { return alpha; }

   /**
    * Sets the alpha parameter associated with this tractrix curve to the floating point argument.
    *
    * @param alpha the value that the alpha parameter associated with this tractrix curve is set to.
    */

   public void setAlpha(float alpha) { this.alpha = alpha; }

}
