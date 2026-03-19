package shamu.media.render;

/** Abstract procedure class representing a parametric surface in three dimensional euclidean space. Concrete subclasses must provide implementations of the x, y, z mappings; the x, y, z partial derivative mappings with respect to the u1 local
 *  variable; and the x, y, z partial derivative mappings with respect to the u2 local variable.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

abstract public class ParametricProcedure implements ProcedureI, ModelingConstantsI {

   /**
    * The ul local variable lower bound that determines the boundary of this parametric procedure.
    */

   protected float lowerBoundsU1;

   /**
    * The u2 local variable lower bound that determines the boundary of this parametric procedure.
    */

   protected float lowerBoundsU2;

   /**
    * The ul local variable upper bound that determines the boundary of this parametric procedure.
    */

   protected float upperBoundsU1;

   /**
    * The u2 local variable upper bound that determines the boundary of this parametric procedure.
    */

   protected float upperBoundsU2;

   /**
    * Constructs a default parametric procedure.
    */

   protected ParametricProcedure() { super(); }

   /**
    * Constructs a parametric procedure having the boundary defined by the floating point arguments.
    *
    * @param lowerBoundsU1 the value that the u1 local variable lower bound that determines the boundary of this parametric procedure is set to.
    * @param lowerBoundsU2 the value that the u2 local variable lower bound that determines the boundary of this parametric procedure is set to.
    * @param upperBoundsU1 the value that the u1 local variable upper bound that determines the boundary of this parametric procedure is set to.
    * @param upperBoundsU2 the value that the u2 local variable upper bound that determines the boundary of this parametric procedure is set to.
    */

   protected ParametricProcedure(float lowerBoundsU1, float lowerBoundsU2, float upperBoundsU1, float upperBoundsU2) {

      this.lowerBoundsU1 = lowerBoundsU1;
      this.lowerBoundsU2 = lowerBoundsU2;

      this.upperBoundsU1 = upperBoundsU1;
      this.upperBoundsU2 = upperBoundsU2;

   }

   /**
    * Calculates and returns the x-coordinate of the point on this parametric procedure that is mapped from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point on this parametric procedure that is mapped from the floating point arguments.
    */

   abstract public float mapX(float u1, float u2);

   /**
    * Calculates and returns the y-coordinate of the point on this parametric procedure that is mapped from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point on this parametric procedure that is mapped from the floating point arguments.
    */

   abstract public float mapY(float u1, float u2);

   /**
    * Calculates and returns the z-coordinate of the point on this parametric procedure that is mapped from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point on this parametric procedure that is mapped from the floating point arguments.
    */

   abstract public float mapZ(float u1, float u2);

   /**
    * Calculates and returns the x-coordinate of the point mapped by the partial derivative of this parametric procedure with respect to the u1
    * local variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point mapped by the partial derivative of this parametric procedure with respect to the u1 local variable from the floating point arguments.
    */

   abstract public float partialU1MapX(float u1, float u2);

   /**
    * Calculates and returns the y-coordinate of the point mapped by the partial derivative of this parametric procedure with respect to the u1
    * local variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the partial derivative of this parametric procedure with respect to the u1 local variable from the floating point arguments.
    */

   abstract public float partialU1MapY(float u1, float u2);

   /**
    * Calculates and returns the z-coordinate of the point mapped by the partial derivative of this parametric procedure with respect to the u1
    * local variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point mapped by the partial derivative of this parametric procedure with respect to the u1 local variable from the floating point arguments.
    */

   abstract public float partialU1MapZ(float u1, float u2);

   /**
    * Calculates and returns the x-coordinate of the point mapped by the partial derivative of this parametric procedure with respect to the u2
    * local variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point mapped by the partial derivative of this parametric procedure with respect to the u2 local variable from the floating point arguments.
    */

   abstract public float partialU2MapX(float u1, float u2);
   /**
    * Calculates and returns the y-coordinate of the point mapped by the partial derivative of this parametric procedure with respect to the u2
    * local variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the partial derivative of this parametric procedure with respect to the u2 local variable from the floating point arguments.
    */

   abstract public float partialU2MapY(float u1, float u2);

   /**
    * Calculates and returns the z-coordinate of the point mapped by the partial derivative of this parametric procedure with respect to the u2
    * local variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point mapped by the partial derivative of this parametric procedure with respect to the u2 local variable from the floating point arguments.
    */

   abstract public float partialU2MapZ(float u1, float u2);

   public void computeNormal(float u1, float u2, float[] normal) {

      float tangentX1 = partialU1MapX(u1, u2);
      float tangentY1 = partialU1MapY(u1, u2);
      float tangentZ1 = partialU1MapZ(u1, u2);

      float tangentX2 = partialU2MapX(u1, u2);
      float tangentY2 = partialU2MapY(u1, u2);
      float tangentZ2 = partialU2MapZ(u1, u2);

      normal[0] =  0.0f;
      normal[1] = -1.0f;
      normal[2] =  0.0f;

      if((tangentX1 != 0 || tangentY1 != 0 || tangentZ1 != 0) && (tangentX2 != 0 || tangentY2 != 0 || tangentZ2 != 0)) {

         normal[0] = tangentY2 * tangentZ1 - tangentZ2 * tangentY1;
         normal[1] = tangentZ2 * tangentX1 - tangentX2 * tangentZ1;
         normal[2] = tangentX2 * tangentY1 - tangentY2 * tangentX1;

         float normalFactor   = (float)Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1] + normal[2] * normal[2]);

         normal[0] /= normalFactor;
         normal[1] /= normalFactor;
         normal[2] /= normalFactor;

      }

   }

   /**
    * Returns the ul local variable lower bound that determines the boundary of this parametric procedure.
    *
    * @return the u1 local variable lower bound that determines the boundary of this parametric procedure.
    */

   public float getLowerBoundsU1() { return lowerBoundsU1; }

   /**
    * Returns the u2 local variable lower bound that determines the boundary of this parametric procedure.
    *
    * @return the u2 local variable lower bound that determines the boundary of this parametric procedure.
    */

   public float getLowerBoundsU2() { return lowerBoundsU2; }

   /**
    * Returns the ul local variable upper bound that determines the boundary of this parametric procedure.
    *
    * @return the u1 local variable upper bound that determines the boundary of this parametric procedure.
    */

   public float getUpperBoundsU1() { return upperBoundsU1; }

   /**
    * Returns the u2 local variable upper bound that determines the boundary of this parametric procedure.
    *
    * @return the u2 local variable upper bound that determines the boundary of this parametric procedure.
    */

   public float getUpperBoundsU2() { return upperBoundsU2; }

   /**
    * Sets the u1 local variable lower bound to the floating point argument.
    *
    * @param lowerBoundsU1 the value that the u1 local variable lower bound is set to.
    */

   public void setLowerBoundsU1(float lowerBoundsU1) { this.lowerBoundsU1 = lowerBoundsU1; }

   /**
    * Sets the u2 local variable lower bound to the floating point argument.
    *
    * @param lowerBoundsU2 the value that the u2 local variable lower bound is set to.
    */

   public void setLowerBoundsU2(float lowerBoundsU2) { this.lowerBoundsU2 = lowerBoundsU2; }

   /**
    * Sets the u1 local variable upper bound to the floating point argument.
    *
    * @param upperBoundsU1 the value that the u1 local variable upper bound is set to.
    */

   public void setUpperBoundsU1(float upperBoundsU1) { this.upperBoundsU1 = upperBoundsU1; }

   /**
    * Sets the u2 local variable upper bound to the floating point argument.
    *
    * @param upperBoundsU2 the value that the u2 local variable upper bound is set to.
    */

   public void setUpperBoundsU2(float upperBoundsU2) { this.upperBoundsU2 = upperBoundsU2; }

}

