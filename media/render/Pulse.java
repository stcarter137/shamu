package shamu.media.render;

/** Class representing a pulse curve that is parameterized as a [x(t), y(t)] = [ t, alpha * exp(gamma * (t - beta)^2)].
 *
 *  @see ProfileCurve2D
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class Pulse extends ProfileCurve2D {

   /**
    * The alpha parameter of this pulse.
    */

   protected float alpha;

   /**
    * The beta parameter of this pulse.
    */

   protected float beta;

   /**
    * The gamma parameter of this pulse.
    */

   protected float gamma;

   /**
    * Constructs a default pulse.
    */

   public Pulse() { super(); }

   /**
    * Constructs a pulse having the alpha parameter, beta parameter, and boundary determined by the floating point arguments.
    *
    * @param alpha the value that the alpha parameter of this pulse is set to.
    * @param beta the value that the beta parameter of this pulse is set to.
    * @param gamma the value that the gamma parameter of this pulse is set to.
    * @param lowerBoundsT the value that the parametric variable lower bound that determines the boundary of this pulse is set to.
    * @param upperBoundsT the value that the parametric variable upper bound that determines the boundary of this pulse is set to.
    */

   public Pulse(float alpha, float beta, float gamma, float lowerBoundsT, float upperBoundsT) {

      super(lowerBoundsT, upperBoundsT);

      this.alpha = alpha;
      this.beta = beta;
      this.gamma = gamma;

   }

   /**
    * Calculates and returns the x-coordinate of the point on this pulse in (x, y) space mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point on this pulse in (x, y) space mapped from the floating point argument.
    */

   public float mapX(float t) { return  t; }


   /**
    * Calculates and returns the y-coordinate of the point on this pulse in (x, y) space mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point on this pulse in (x, y) space mapped from the floating point argument.
    */

   public float mapY(float t) {return (float)(-alpha * Math.exp(beta * Math.pow(t - gamma, 2.0f))); }

   /**
    * Calculates and returns the x-coordinate of the point in (x, y) space mapped by the derivative of this pulse from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point in (x, y) space mapped by the derivative of this pulse from the floating point argument.
    */

   public float derivativeMapX(float t) { return 1; }

   /**
    * Calculates and returns the y-coordinate of the point in (x, y) space mapped by the derivative of the generating curve associated with
    * this pulse from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point in (x, y) space mapped by the derivative of this pulse from the floating point argument.
    */

   public float derivativeMapY(float t) { return (float)(-2 * alpha * beta * (t - gamma) * Math.exp(beta * Math.pow(t - gamma, 2.0f))); }

   /**
    * Returns the alpha parameter of associated with this pulse.
    *
    * @return the alpha parameter of associated with this pulse.
    */

   public float getAlpha() { return alpha; }

   /**
    * Returns the beta parameter associated with this pulse.
    *
    * @return the beta parameter associated with this pulse.
    */

   public float getBeta() { return beta; }

   /**
    * Returns the gamma parameter associated with this pulse.
    *
    * @return the gamma parameter associated with this pulse.
    */

   public float getGamma() { return gamma; }

   /**
    * Sets the value of the alpha parameter associated with this pulse to the floating point argument.
    *
    * @param alpha the value that the alpha parameter associated with this pulse is set to.
    */

   public void setAlpha(float alpha) { this.alpha = alpha; }

   /**
    * Sets the value of the beta parameter associated with this pulse to the floating point argument.
    *
    * @param beta the value that the beta parameter associated with this pulse is set to.
    */

   public void setBeta(float beta) { this.beta = beta; }

   /**
    * Sets the value of the gammaparameter associated with this pulse to the floating point argument.
    *
    * @param gamma the value that the gamma parameter associated with this pulse is set to.
    */

   public void setGamma(float gamma) { this.gamma = gamma; }

}
