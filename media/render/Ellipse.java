package shamu.media.render;

/** Class representing a curve that is parameterized as [x(t), y(t)] = [ gamma + alpha * cos(t), beta * sin(t)].
 *
 *  @see ProfileCurve2D
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class Ellipse extends ProfileCurve2D {

   /**
    * The alpha parameter of this ellipse.
    */

   protected float alpha;

   /**
    * The beta parameter of this ellipse.
    */

   protected float beta;

   /**
    * The gamma parameter of this ellipse.
    */

   protected float gamma;

   /**
    * Constructs a default ellipse.
    */

   public Ellipse() { super(); }

   /**
    * Constructs an ellipse having the alpha parameter, beta parameter, gamma parameter, and boundary determined by the floating point arguments.
    *
    * @param alpha the value that the alpha parameter of this ellipse is set to.
    * @param beta the value that the beta parameter of this ellipse is set to.
    * @param gamma the value that the gamma parameter of this ellipse is set to.
    * @param lowerBoundsT the value that the parametric variable lower bound that determines the boundary of this ellipse is set to.
    * @param upperBoundsT the value that the parametric variable upper bound that determines the boundary of this ellipse is set to.
    */

   public Ellipse(float alpha, float beta, float gamma, float lowerBoundsT, float upperBoundsT) {

      super(lowerBoundsT, upperBoundsT);

      this.alpha = alpha;
      this.beta = beta;
      this.gamma = gamma;

   }

   /**
    * Calculates and returns the x-coordinate of the point on this ellipse in (x, y) space mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point on this ellipse in (x, y) space mapped from the floating point argument.
    */

   public float mapX(float t) { return  gamma + alpha * (float)Math.cos(t); }

   /**
    * Calculates and returns the y-coordinate of the point on this ellipse in (x, y) space mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point on this ellipse in (x, y) space mapped from the floating point argument.
    */

   public float mapY(float t) { return  beta * (float)Math.sin(t); }

   /**
    * Calculates and returns the x-coordinate of the point in (x, y) space mapped by the derivative of this ellipse from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point in (x, y) space mapped by the derivative of this ellipse from the floating point argument.
    */

   public float derivativeMapX(float t) { return -alpha * (float)Math.sin(t); }

   /**
    * Calculates and returns the y-coordinate of the point in (x, y) space mapped by the derivative of this ellipse from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point in (x, y) space mapped by the derivative of this ellipse from the floating point argument.
    */

   public float derivativeMapY(float t) { return  beta * (float)Math.cos(t); }

   /**
    * Returns the alpha parameter associated with this ellipse.
    *
    * @return the alpha parameter associated with this ellipse.
    */

   public float getAlpha() { return alpha; }

   /**
    * Returns the beta parameter associated with this ellipse.
    *
    * @return the beta parameter associated with this ellipse.
    */

   public float getBeta() { return beta; }

   /**
    * Returns the gamma parameter associated with this ellipse.
    *
    * @return the gamma parameter associated with this ellipse.
    */

   public float getGamma() { return gamma; }

   /**
    * Sets the value of the alpha parameter associated with this ellipse to the floating point argument.
    *
    * @param alpha the value that the alpha parameter associated with this ellipse is set to.
    */

   public void setAlpha(float alpha) { this.alpha = alpha; }

   /**
    * Sets the value of the beta parameter associated with this ellipse to the floating point argument.
    *
    * @param beta the value that the beta parameter associated with this ellipse is set to.
    */

   public void setBeta(float beta) { this.beta = beta; }

   /**
    * Sets the value of the gamma parameter associated with this ellipse to the floating point argument.
    *
    * @param gamma the value that the gamma parameter associated with this ellipse is set to.
    */

   public void setGamma(float gamma) { this.gamma = gamma; }

}