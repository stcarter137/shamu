package shamu.media.render;

/** Class representing a torus knot parameterized as [x, y, z] = [(alpha + beta * cos(nu * t)) * sin(mu * t), gamma * sin(nu * t), (alpha + beta * cos(nu * t)) * cos(mu * t)].
 *
 *  @see ProfileCurve3D
 *
 *  @author Scott T. Carter
 *  @version 1.4
 */

public class TorusKnot extends ProfileCurve3D {

   /**
    * The alpha parameter of this torus knot.
    */

   protected float alpha;

   /**
    * The beta parameter of this torus knot.
    */

   protected float beta;

   /**
    * The gamma parameter of this torus knot.
    */

   protected float gamma;

   /**
    * The mu parameter of this torus knot.
    */

   protected float mu;

   /**
    * The nu parameter of this torus knot.
    */

   protected float nu;

   /**
    * Constructs a default torus knot.
    */

   public TorusKnot() { super(); }

   /**
    * Constructs a torus knot having the alpha parameter, beta parameter, gamma parameter, mu parameter, nu parameter, rho parameter and boundary determined by the floating point arguments.
    *
    * @param alpha the value that the alpha parameter of this torus knot is set to.
    * @param beta the value that the beta parameter of this torus knot is set to.
    * @param gamma the value that the gamma parameter of this torus knot is set to.
    * @param mu the value that the mu parameter of this torus knot is set to.
    * @param nu the value that the nu parameter of this torus knot is set to.
    * @param lowerBoundsT the value that the parametric variable lower bound that determines the boundary of this torus knot is set to.
    * @param upperBoundsT the value that the parametric variable upper bound that determines the boundary of this torus knot is set to.
    */

   public TorusKnot(float alpha, float beta, float gamma, float mu, float nu, float lowerBoundsT, float upperBoundsT) {

   	  super(lowerBoundsT, upperBoundsT);

      this.alpha = alpha;
      this.beta = beta;
      this.gamma = gamma;

      this.mu = mu;
      this.nu = nu;

   }

   /**
    * Calculates and returns the x-coordinate of the point on this torus knot that is mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point on this torus knot that is mapped from the floating point argument.
    *
    */

   public float mapX(float t) { return (alpha + beta * (float)Math.cos(nu * t)) * (float)Math.sin(mu * t); }

   /**
    * Calculates and returns the y-coordinate of the point on this torus knot that is mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point on this torus knot that is mapped from the floating point argument.
    */

   public float mapY(float t) { return gamma * (float)Math.sin(nu * t); }

   /**
    * Calculates and returns the z-coordinate of the point on this torus knot that is mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point on this torus knot that is mapped from the floating point argument.
    */

   public float mapZ(float t) { return (alpha + beta * (float)Math.cos(nu * t)) * (float)Math.cos(mu * t); }

   /**
    * Calculates and returns the x-coordinate of the point mapped by the derivative of this torus knot from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point mapped by the derivative of this torus knot from the floating point argument.
    */

   public float derivativeMapX(float t) { return -nu * beta * (float)Math.sin(nu * t) * (float)Math.sin(mu * t) + (alpha + beta * (float)Math.cos(nu * t)) * mu * (float)Math.cos(mu * t); }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the derivative of this torus knot from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the derivative of this torus knot from the floating point argument.
    */

   public float derivativeMapY(float t) { return nu * gamma * (float)Math.cos(nu * t); }

   /**
    * Calculates and returns the z-coordinate of the point mapped by the derivative of this torus knot from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point mapped by the derivative of this torus knot from the floating point argument.
    */

   public float derivativeMapZ(float t) { return -nu * beta * (float)Math.sin(nu * t) * (float)Math.cos(mu * t) - (alpha + beta * (float)Math.cos(nu * t)) * mu * (float)Math.sin(mu * t); }

   /**
    * Calculates and returns the x-coordinate of the point mapped by the second derivative of this torus knot from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point mapped by the second derivative of this torus knot from the floating point argument.
    */

   public float secondDerivativeMapX(float t) { return -(float)Math.pow(nu, 2) * beta *(float)Math.cos(nu * t) * (float)Math.sin(mu * t) + -nu * beta * (float)Math.sin(nu * t) * mu * (float)Math.cos(mu * t) + -nu * beta * (float)Math.sin(nu * t) * mu * (float)Math.cos(mu * t) - (alpha + beta * (float)Math.cos(nu * t)) * (float)Math.pow(mu, 2) * (float)Math.sin(mu * t); }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the second derivative of this torus knot from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the second derivative of this torus knot from the floating point argument.
    */

   public float secondDerivativeMapY(float t) { return -(float)Math.pow(nu, 2) * gamma * (float)Math.sin(nu * t); }

   /**
    * Calculates and returns the z-coordinate of the point mapped by the second derivative of this torus knot from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point mapped by the second derivative of this torus knot from the floating point argument.
    */

   public float secondDerivativeMapZ(float t) { return -(float)Math.pow(nu, 2) * beta *(float)Math.cos(nu * t) * (float)Math.cos(mu * t) + nu * beta * (float)Math.sin(nu * t) * mu * (float)Math.sin(mu * t) + nu * beta * (float)Math.sin(nu * t) * mu * (float)Math.sin(mu * t) - (alpha + beta * (float)Math.cos(nu * t)) * (float)Math.pow(mu, 2) * (float)Math.cos(mu * t); }

   /**
    * Calculates and returns the x-coordinate of the point mapped by the third derivative of this torus knot from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point mapped by the third derivative of this torus knot from the floating point argument.
    */

   public float thirdDerivativeMapX(float t) { return (float)Math.pow(nu, 3) * beta *(float)Math.sin(nu * t) * (float)Math.sin(mu * t) - (float)Math.pow(nu, 2) * beta * (float)Math.cos(nu * t) * mu * (float)Math.cos(mu * t) - (float)Math.pow(nu, 2) * beta * (float)Math.cos(nu * t) * mu * (float)Math.cos(mu * t) + nu * beta * (float)Math.sin(nu * t) * (float)Math.pow(mu, 2) * (float)Math.sin(mu * t) - (float)Math.pow(nu, 2) * beta *(float)Math.cos(nu * t) * mu * (float)Math.cos(mu * t) + nu * beta * (float)Math.sin(nu * t) * (float)Math.pow(mu, 2) * (float)Math.sin(mu * t) + nu * beta * (float)Math.sin(nu * t) * (float)Math.pow(mu, 2) * (float)Math.sin(mu * t) - (alpha + beta * (float)Math.cos(nu * t)) * (float)Math.pow(mu, 3) * (float)Math.cos(mu * t); }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the third derivative of this torus knot from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the third derivative of this torus knot from the floating point argument.
    */

   public float thirdDerivativeMapY(float t) { return  (float)Math.pow(nu, 3) * gamma * (float)Math.cos(nu * t); }

   /**
    * Calculates and returns the z-coordinate of the point mapped by the third derivative of this torus knot from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point mapped by the third derivative of this torus knot from the floating point argument.
    */

   public float thirdDerivativeMapZ(float t) { return (float)Math.pow(nu, 3) * beta *(float)Math.sin(nu * t) * (float)Math.cos(mu * t) + (float)Math.pow(nu, 2) * beta * (float)Math.cos(nu * t) * mu * (float)Math.sin(mu * t) + (float)Math.pow(nu, 2) * beta * (float)Math.cos(nu * t) * mu * (float)Math.sin(mu * t) + nu * beta * (float)Math.sin(nu * t) * (float)Math.pow(mu, 2) * (float)Math.cos(mu * t) + (float)Math.pow(nu, 2) * beta *(float)Math.cos(nu * t) * mu * (float)Math.sin(mu * t) + nu * beta * (float)Math.sin(nu * t) * (float)Math.pow(mu, 2) * (float)Math.cos(mu * t) + nu * beta * (float)Math.sin(nu * t) * (float)Math.pow(mu, 2) * (float)Math.cos(mu * t) + (alpha + beta * (float)Math.cos(nu * t)) * (float)Math.pow(mu, 3) * (float)Math.sin(mu * t); }

   /**
    * Returns the alpha parameter associated with this torus knot.
    *
    * @return the alpha parameter associnated with this torus knot.
    */

   public float getAlpha() { return alpha; }

   /**
    * Returns the beta parameter associated with this torus knot.
    *
    * @return the beta parameter associated with this torus knot.
    */

   public float getBeta() { return beta; }

   /**
    * Returns the gamma parameter associated with this torus knot.
    *
    * @return the gamma parameter associated with this torus knot.
    */

   public float getGamma() { return gamma; }

   /**
    * Returns the mu parameter associated with this torus knot.
    *
    * @return the mu parameter associated with this torus knot.
    */

   public float getMu() { return mu; }

   /**
    * Returns the nu parameter associated with this torus knot.
    *
    * @return the nu parameter associated with this torus knot.
    */

   public float getNu() { return nu; }

   /**
    * Sets the value of the alpha parameter associated with this torus knot to the floating point argument.
    *
    * @param alpha the value that the alpha parameter associated with this torus knot is set to.
    */

   public void setAlpha(float alpha) { this.alpha = alpha; }

   /**
    * Sets the value of the beta parameter associated with this torus knot to the floating point argument.
    *
    * @param beta the value that the beta parameter associated with this torus knot is set to.
    */

   public void setBeta(float beta) { this.beta = beta; }

   /**
    * Sets the value of the gamma parameter associated with this torus knot to the floating point argument.
    *
    * @param gamma the value that the gamma parameter associated with this torus knot is set to.
    */

   public void setGamma(float gamma) { this.gamma = gamma; }

   /**
    * Sets the value of the mu parameter associated with this torus knot to the floating point argument.
    *
    * @param mu the value that the mu parameter associated with this torus knot is set to.
    */

   public void setMu(float mu) { this.mu = mu; }

   /**
    * Sets the value of the nu parameter associated with this torus knot to the floating point argument.
    *
    * @param nu the value that the nu parameter associated with this torus knot is set to.
    */

   public void setNu(float nu) { this.nu = nu; }

}
