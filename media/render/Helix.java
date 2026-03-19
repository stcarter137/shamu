package shamu.media.render;

/** Class representing a helix curve parameterized as [x, y, z] = [alpha * cos(t), beta * t, alpha * sin(t)].
 *
 *  @see ProfileCurve3D
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class Helix extends ProfileCurve3D {

   /**
    * The alpha parameter of the this helix curve.
    */

   protected float alpha;

   /**
    * The beta parameter of the this helix curve.
    */

   protected float beta;

   /**
    * Constructs a default helix curve.
    */

   public Helix() { super(); }

   /**
    * Constructs a helix curve having the alpha parameter, beta parameter, rho parameter, and boundary determined by the floating point arguments.
    *
    * @param alpha the value that the alpha parameter of this helix curve is set to.
    * @param beta the value that the beta parameter of this helix curve is set to.
    * @param lowerBoundsT the value that the parametric variable lower bound that determines the boundary of this helix curve is set to.
    * @param upperBoundsT the value that the parametric variable upper bound that determines the boundary of this helix curve is set to.
    */

   public Helix(float alpha, float beta, float lowerBoundsT, float upperBoundsT) {

	  super(lowerBoundsT, upperBoundsT);

      this.alpha = alpha;
      this.beta = beta;

   }

   /**
    * Calculates and returns the x-coordinate of the point on the this helix curve that is mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point on this helix curve that is mapped from the floating point argument.
    */

   public float mapX(float t) { return alpha * (float)Math.cos(t); }

   /**
    * Calculates and returns the y-coordinate of the point on the this helix curve that is mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point on this helix curve that is mapped from the floating point argument.
    */

   public float mapY(float t) { return beta * t; }

   /**
    * Calculates and returns the z-coordinate of the point on the this helix curve that is mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point on this helix curve that is mapped from the floating point argument.
    */

   public float mapZ(float t) { return alpha * (float)Math.sin(t); }

   /**
    * Calculates and returns the x-coordinate of the point mapped by the derivative of the this helix curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point mapped by the derivative of this helix curve from the floating point argument.
    */

   public float derivativeMapX(float t) { return -alpha * (float)Math.sin(t); }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the derivative of the this helix curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the derivative of this helix curve from the floating point argument.
    */

   public float derivativeMapY(float t) { return beta; }

   /**
    * Calculates and returns the z-coordinate of the point mapped by the derivative of the this helix curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point mapped by the derivative of this helix curve from the floating point argument.
    */

   public float derivativeMapZ(float t) { return alpha * (float)Math.cos(t); }

   /**
    * Calculates and returns the x-coordinate of the point mapped by the second derivative of this helix curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point mapped by the second derivative of this helix curve from the floating point argument.
    */

   public float secondDerivativeMapX(float t) { return -alpha * (float)Math.cos(t); }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the second derivative of this helix curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the second derivative of this helix curve from the floating point argument.
    */

   public float secondDerivativeMapY(float t) { return 0; }

   /**
    * Calculates and returns the z-coordinate of the point mapped by the second derivative of this helix curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point mapped by the second derivative of this helix curve from the floating point argument.
    */

   public float secondDerivativeMapZ(float t) { return -alpha * (float)Math.sin(t); }

   /**
    * Calculates and returns the x-coordinate of the point mapped by the derivative ofthis helix curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point mapped by the derivative of this helix curve from the floating point argument.
    */

   public float thirdDerivativeMapX(float t) { return alpha * (float)Math.sin(t); }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the third derivative of this helix curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the third derivative of this helix curve from the floating point argument.
    */

   public float thirdDerivativeMapY(float t) { return 0; }

   /**
    * Calculates and returns the z-coordinate of the point mapped by the third derivative of the this helix curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point mapped by the third derivative of this helix curve from the floating point argument.
    */

   public float thirdDerivativeMapZ(float t) { return -alpha * (float)Math.cos(t); }

   /**
    * Returns the alpha parameter associated with this helix curve.
    *
    * @return the alpha parameter associated with this helix curve.
    */

   public float getAlpha() { return alpha; }

   /**
    * Returns the beta parameter associated with this helix curve.
    *
    * @return the beta parameter associated with this helix curve.
    */

   public float getBeta() { return beta; }

   /**
    * Sets the value of the alpha parameter associated with this helix curve to the floating point argument.
    *
    * @param alpha the value that the alpha parameter associated with this helix curve is set to.
    */

   public void setAlpha(float alpha) { this.alpha = alpha; }

   /**
    * Sets the value of the beta parameter associated with this helix curve to the floating point argument.
    *
    * @param beta the value that the beta parameter associated with this helix curve is set to.
    */

   public void setBeta(float beta) { this.beta = beta; }

}