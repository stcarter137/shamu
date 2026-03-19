package shamu.media.render;

/** Procedural modeling class representing a plane in 3D space that is parameterized as the monge patch [x, y, z] = [x, alpha * x + beta * z + gamma, z].
 *
 *  @see MongePatch
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class Plane extends MongePatch {

   /**
    * The alpha parameter of this plane.
    */

   protected float alpha;

   /**
    * The beta parameter of this plane.
    */

   protected float beta;

   /**
    * The gamma parameter of this plane.
    */

   protected float gamma;

   /**
    * Constructs a default plane.
    */

   public Plane() { super(); }

   /**
    * Constructs a plane having the alpha parameter, beta parameter, gamma parameter, and boundary determined by the floating point arguments.
    *
    * @param alpha the value that the alpha parameter of this plane is set to.
    * @param beta the value that the beta parameter of this plane is set to.
    * @param gamma the value that the gamma parameter of this plane is set to.
    * @param lowerBoundsU1 the value that the u1 local variable lower bound that determines the boundary of this plane is set to.
    * @param lowerBoundsU2 the value that the u2 local variable lower bound that determines the boundary of this plane is set to.
    * @param upperBoundsU1 the value that the u1 local variable upper bound that determines the boundary of this plane is set to.
    * @param upperBoundsU2 the value that the u2 local variable upper bound that determines the boundary of this plane is set to.
    */

   public Plane(float alpha, float beta, float gamma, float lowerBoundsU1,  float lowerBoundsU2, float upperBoundsU1, float upperBoundsU2) {

      super(lowerBoundsU1, lowerBoundsU2, upperBoundsU1, upperBoundsU2);

      this.alpha = alpha;
      this.beta = beta;
      this.gamma = gamma;

   }

   /**
    * Calculates and returns the y-coordinate of the point on this plane that is mapped from the local variable floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point on this plane that is mapped from the local variable floating point arguments.
    */

   public float mapY(float u1, float u2) { return alpha * u1 + beta * u2 + gamma; }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the partial derivative of this plane with respect to the u1 local variable
    * from the local variable floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the partial derivative of this plane with respect to the u1 local variable from the local variable floating point arguments.
    */

   public float partialU1MapY(float u1, float u2) { return alpha; }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the partial derivative of this plane with respect to the u2 local variable
    * from the local variable floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the partial derivative of this plane with respect to the u2 local variable from the local variable floating point arguments.
    */

   public float partialU2MapY(float u1, float u2) { return beta; }

   /**
    * Returns the alpha parameter associated with this plane.
    *
    * @return the alpha parameter associated with this plane.
    */

   public float getAlpha() { return alpha; }

   /**
    * Returns the beta parameter associated with this plane.
    *
    * @return the beta parameter associated with this plane.
    */

   public float getBeta() { return beta; }

   /**
    * Returns the gamma parameter associated with this plane.
    *
    * @return the gamma parameter associated with this plane.
    */

   public float getGamma() { return gamma; }

   /**
    * Sets the value of the alpha parameter associated with this plane to the floating point argument.
    *
    * @param alpha the value that the alpha parameter associated with this plane is set to.
    */

   public void setAlpha(float alpha) { this.alpha = alpha; }

   /**
    * Sets the value of the beta parameter associated with this plane to the floating point argument.
    *
    * @param beta the value that the beta parameter associated with this plane is set to.
    */

   public void setBeta(float beta) { this.beta = beta; }

   /**
    * Sets the value of the gamma parameter associated with this plane to the floating point argument.
    *
    * @param gamma the value that the gamma parameter associated with this plane is set to.
    */

   public void setGamma(float gamma) { this.gamma = gamma; }

}
