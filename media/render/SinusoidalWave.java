package shamu.media.render;

/** Class representing a sinusoidal wave curve that is parameterized as [x(t), y(t)] = [ alpha * exp(beta * t) * cos(gamma * t), t].
 *
 *  @see ProfileCurve2D
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class SinusoidalWave extends ProfileCurve2D {

   /**
    * The alpha parameter associated with this sinusoidal wave.
    */

   protected float alpha;

   /**
    * The beta parameter associated with this sinusoidal wave.
    */

   protected float beta;

   /**
    * The gamma parameter associated with this sinusoidal wave.
    */

   protected float gamma;

   /**
    * Constructs a default sinusoidal wave.
     */

   public SinusoidalWave() { super(); }

   /**
    * Constructs a sinusoidal wave with the level of detail specified by the integer arguments and having the alpha parameter, beta parameter, and boundary determined by the floating point arguments.
    *
    * @param alpha the value that the alpha parameter of this sinusoidal wave is set to.
    * @param beta the value that the beta parameter of this sinusoidal wave is set to.
    * @param gamma the value that the gamma parameter of this sinusoidal wave is set to.
    * @param lowerBoundsT the value that the parametric variable lower bound that determines the boundary of this sinusoidal wave is set to.
    * @param upperBoundsT the value that the parametric variable upper bound that determines the boundary of this sinusoidal wave is set to.
    */

   public SinusoidalWave(float alpha, float beta, float gamma, float lowerBoundsT, float upperBoundsT) {

      super(lowerBoundsT, upperBoundsT);

      this.alpha = alpha;
      this.beta = beta;
      this.gamma = gamma;

   }

   /**
    * Calculates and returns the x-coordinate of the point on the generating curve associated with this sinusoidal wave in (x, y) space mapped from the
    * floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point on this sinusoidal wave in (x, y) space mapped from the floating point argument.
    */

   public float mapX(float t) { return  t; }

   /**
    * Calculates and returns the y-coordinate of the point on this sinusoidal wave in (x, y) space mapped from the
    * floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point on this sinusoidal wave in (x, y) space mapped from the floating point argument.
    */

   public float mapY(float t) {return (float)(-alpha * Math.exp(beta * t)* Math.cos(gamma * t)); }

   /**
    * Calculates and returns the x-coordinate of the point in (x, y) space mapped by the derivative of this sinusoidal wave from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point in (x, y) space mapped by the derivative of this sinusoidal wave from the floating point argument.
    */

   public float derivativeMapX(float t) { return 1; }

   /**
    * Calculates and returns the y-coordinate of the point in (x, y) space mapped by the derivative of this sinusoidal wave from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point in (x, y) space mapped by the derivative of this sinusoidal wave from the floating point argument.
    */

   public float derivativeMapY(float t) { return  (float)(-alpha * beta * Math.exp(beta * t) * Math.cos(gamma * t) + alpha * gamma * Math.exp(beta * t) * Math.sin(gamma * t)); }

   /**
    * Returns the alpha parameter associated with this sinusoidal wave.
    *
    * @return the alpha parameter associated this sinusoidal wave.
    */

   public float getAlpha() { return alpha; }

   /**
    * Returns the beta parameter associated with this sinusoidal wave.
    *
    * @return the beta parameter associated with this sinusoidal wave.
    */

   public float getBeta() { return beta; }

   /**
    * Returns the gamma parameter associated with this sinusoidal wave.
    *
    * @return the gamma parameter associated with this sinusoidal wave.
    */

   public float getGamma() { return gamma; }

   /**
    * Sets the value of the alpha parameter associated with this sinusoidal wave to the floating point argument.
    *
    * @param alpha the value that the alpha parameter associated with this sinusoidal wave is set to.
    */

   public void setAlpha(float alpha) { this.alpha = alpha; }

   /**
    * Sets the value of the beta parameter associated with this sinusoidal wave to the floating point argument.
    *
    * @param beta the value that the beta parameter associated with this sinusoidal wave is set to.
    */

   public void setBeta(float beta) { this.beta = beta; }

   /**
    * Sets the value of the gamma parameter associated with this sinusoidal wave to the floating point argument.
    *
    * @param gamma the value that the gamma parameter associated with this sinusoidal wave is set to.
    */

   public void setGamma(float gamma) { this.gamma = gamma; }

}


