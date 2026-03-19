package shamu.media.render;

/** Abstract procedural modeling class representing a family of surfaces in which the y-coordinate of points on the surface can be expressed as y = f(x, z),
 *  i.e. a function of (x, z). The u1 local variable of this surface corresponds to the z-coordinate of a point in the surface, and the u2 local
 *  variable of this surface corresponds to the x-coordinate of a point on the surface.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

abstract public class MongePatch extends ParametricProcedure {

   /**
    * Constructs a default monge patch.
    */

   protected MongePatch() { super(); }

   /**
    * Constructs a monge patch having the boundary determined by the floating point arguments.
    *
    * @param lowerBoundsU1 the value that the u1 local variable lower bound that determines the boundary of this monge patch is set to.
    * @param lowerBoundsU2 the value that the u2 local variable lower bound that determines the boundary of this monge patch is set to.
    * @param upperBoundsU1 the value that the u1 local variable upper bound that determines the boundary of this monge patch is set to.
    * @param upperBoundsU2 the value that the u2 local variable upper bound that determines the boundary of this monge patch is set to.
    */

   protected MongePatch(float lowerBoundsU1, float lowerBoundsU2, float upperBoundsU1, float upperBoundsU2) { super(lowerBoundsU1, lowerBoundsU2, upperBoundsU1, upperBoundsU2); }

   /**
    * Calculates and returns the x-coordinate of the point on this monge patch that is mapped from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point on this monge patch that is mapped from the floating point arguments.
    */

   public float mapX(float u1, float u2) { return u2; }

   /**
    * Calculates and returns the z-coordinate of the point on this monge patch that is mapped from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point on this monge patch that is mapped from the floating point arguments.
    */

   public float mapZ(float u1, float u2) { return u1; }

   /**
    * Calculates and returns the x-coordinate of the point mapped by the partial derivative of this monge patch with respect to the u1 local
    * variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point mapped by the partial derivative of this monge patch with respect to the u1 local variable from the floating point arguments.
    */

   public float partialU1MapX(float u1, float u2) { return 0; }

   /**
    * Calculates and returns the z-coordinate of the point mapped by the partial derivative of this monge patch with respect to the u1 local
    * variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point mapped by the partial derivative of this monge patch with respect to the u1 local variable from the floating point arguments.
    */

   public float partialU1MapZ(float u1, float u2) { return 1; }

   /**
    * Calculates and returns the x-coordinate of the point mapped by the partial derivative of this monge patch with respect to the u2 local
    * variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point mapped by the partial derivative of this monge patch with respect to the u2 local variable from the floating point arguments.
    */

   public float partialU2MapX(float u1, float u2) { return 1; }

   /**
    * Calculates and returns the z-coordinate of the point mapped by the partial derivative of this monge patch with respect to the u2 local
    * variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point mapped by the partial derivative of this monge patch with respect to the u2 local variable from the floating point arguments.
    */

   public float partialU2MapZ(float u1, float u2) { return 0; }

   public void computeNormal(float u1, float u2, float[] normal) {

      normal[0] =  partialU2MapY(u1, u2);
      normal[2] =  partialU1MapY(u1, u2);
      normal[1] = -1.0f;

      float normalFactor   = (float)Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1] + normal[2] * normal[2]);

      normal[0] /= normalFactor;
      normal[1] /= normalFactor;
      normal[2] /= normalFactor;

   }   

}


