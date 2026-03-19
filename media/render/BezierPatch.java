package shamu.media.render;

/** Procedural modeling class representing a Bezier patch as a surface that parameterizes coordinates on a surface as the tensor product defined by
 *  the bernstein polynomials of degree n and a grid of control points. The control points will control the curvature and torsion of a curve on
 *  the surface, and the polygonal mesh defined by the control points will represent a polygonal approximation of the Bezier patch. The default
 *  local variable bounds for this surface is the cartesian product [0, 1] X [0, 1].
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class BezierPatch extends ParametricProcedure {

   /**
    * The degree of this Bezier patch.
    */

   protected final int degree;

   /**
    * The array of control points of this Bezier patch.
    */

   protected float[][] controlPointArray;

   /**
    * Constructs a Bezier patch with the degree specified by the integer argument, and having the default local variable bounds and an empty grid
    * of control points.
    *
    * @param degree the value that the degree of this Bezier patch is set to.
    */

   public BezierPatch(int degree) {

	  super(0.0f, 0.0f, 1.0f, 1.0f);

      this.degree = degree;

      this.controlPointArray = new float[(degree + 1)][SPACE_POINT_STRIDE  * (degree + 1)];

   }

   /**
    * Constructs a Bezier patch specified by the floating point array argument having the default local variable bounds and the degree determined by the integer argument.
    * The array should have degree + 1 affay elements and each array element of the control point array should have exactly degree + 1 elements.
    *
    * @param controlPointArray the array that the control point array of this Bezier curve is set to.
    * @param degree the value that the degree of this Bezier patch is set to.
    */

   public BezierPatch(float[][] controlPointArray, int degree) {

      super(0.0f, 0.0f, 1.0f, 1.0f);

      this.degree = degree;

      this.controlPointArray = controlPointArray;

   }

   /**
    * Calculates and returns the x-coordinate of the point on this Bezier patch that is mapped from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point on this Bezier patch that is mapped from the floating point arguments.
    */

   public float mapX(float u1, float u2) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         for(int j = 0; j <= degree; j++)
            sum += controlPointArray[i][SPACE_POINT_STRIDE  * j + 0] * CurveEvaluator.evaluate(this, i, degree, u1) * CurveEvaluator.evaluate(this, j, degree, u2);

      return sum;

   }

   /**
    * Calculates and returns the y-coordinate of the point on this Bezier patch that is mapped from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point on this Bezier patch that is mapped from the floating point arguments.
    */

   public float mapY(float u1, float u2) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         for(int j = 0; j <= degree; j++)
            sum += controlPointArray[i][SPACE_POINT_STRIDE  * j + 1] * CurveEvaluator.evaluate(this, i, degree, u1) * CurveEvaluator.evaluate(this, j, degree, u2);

      return sum;

   }

   /**
    * Calculates and returns the z-coordinate of the point on this Bezier patch that is mapped from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point on this Bezier patch that is mapped from the floating point arguments.
    */

   public float mapZ(float u1, float u2) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         for(int j = 0; j <= degree; j++)
            sum += controlPointArray[i][SPACE_POINT_STRIDE  * j + 2] * CurveEvaluator.evaluate(this, i, degree, u1) * CurveEvaluator.evaluate(this, j, degree, u2);

      return sum;

   }

   /**
    * Calculates and returns the x-coordinate of the point mapped by the partial derivative of this Bezier patch with respect to the u1 local
    * variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point mapped by the partial derivative of this Bezier patch with respect to the u1 local variable from the floating point arguments.
    */

   public float partialU1MapX(float u1, float u2) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         for(int j = 0; j <= degree; j++)
            sum += controlPointArray[i][SPACE_POINT_STRIDE  * j + 0] * (CurveEvaluator.evaluate(this, i - 1, degree - 1, u1) - CurveEvaluator.evaluate(this, i, degree - 1, u1)) * CurveEvaluator.evaluate(this, j, degree, u2) * degree;

      return sum;

   }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the partial derivative of this Bezier patch with respect to the u1 local
    * variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the partial derivative of this Bezier patch with respect to the u1 local variable from the floating point arguments.
    */

   public float partialU1MapY(float u1, float u2) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         for(int j = 0; j <= degree; j++)
            sum += controlPointArray[i][SPACE_POINT_STRIDE  * j + 1] * (CurveEvaluator.evaluate(this, i - 1, degree - 1, u1) - CurveEvaluator.evaluate(this, i, degree - 1, u1)) * CurveEvaluator.evaluate(this, j, degree, u2) * degree;

      return sum;

   }

   /**
    * Calculates and returns the z-coordinate of the point mapped by the partial derivative of this Bezier patch with respect to the u1 local
    * variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point mapped by the partial derivative of this Bezier patch with respect to the u1 local variable from the floating point arguments.
    */

   public float partialU1MapZ(float u1, float u2) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         for(int j = 0; j <= degree; j++)
            sum += controlPointArray[i][SPACE_POINT_STRIDE  * j + 2] * (CurveEvaluator.evaluate(this, i - 1, degree - 1, u1) - CurveEvaluator.evaluate(this, i, degree - 1, u1)) * CurveEvaluator.evaluate(this, j, degree, u2) * degree;

      return sum;

   }

   /**
    * Calculates and returns the x-coordinate of the point mapped by the partial derivative of this Bezier patch with respect to the u2 local
    * variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point mapped by the partial derivative of this Bezier patch with respect to the u2 local variable from the floating point arguments.
    */

   public float partialU2MapX(float u1, float u2) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         for(int j = 0; j <= degree; j++)
            sum += controlPointArray[i][SPACE_POINT_STRIDE  * j + 0] * CurveEvaluator.evaluate(this, i, degree, u1) * (CurveEvaluator.evaluate(this, j - 1, degree - 1, u2) - CurveEvaluator.evaluate(this, j, degree - 1, u2)) * degree;

      return sum;

   }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the partial derivative of this Bezier patch with respect to the u2 local
    * variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the partial derivative of this Bezier patch with respect to the u2 local variable from the floating point arguments.
    */

   public float partialU2MapY(float u1, float u2) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         for(int j = 0; j <= degree; j++)
            sum += controlPointArray[i][SPACE_POINT_STRIDE  * j + 1] * CurveEvaluator.evaluate(this, i, degree, u1) * (CurveEvaluator.evaluate(this, j - 1, degree - 1, u2) - CurveEvaluator.evaluate(this, j, degree - 1, u2)) * degree;

      return sum;

   }

   /**
    * Calculates and returns the z-coordinate of the point mapped by the partial derivative of this Bezier patch with respect to the u2 local
    * variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point mapped by the partial derivative of this Bezier patch with respect to the u2 local variable from the floating point arguments.
    */

   public float partialU2MapZ(float u1, float u2) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         for(int j = 0; j <= degree; j++)
            sum += controlPointArray[i][SPACE_POINT_STRIDE  * j + 2] * CurveEvaluator.evaluate(this, i, degree, u1) * (CurveEvaluator.evaluate(this, j - 1, degree - 1, u2) - CurveEvaluator.evaluate(this, j, degree - 1, u2)) * degree;

      return sum;

   }

   /**
    * Subdivides this Bezier patch in the u1 tangential direction and sets the patch arguments to the resulting subpatches.
    *
    * @param patch1 the Bezier subpatch corresponding to the range [0.0, 0.5] of the local u1 variable of this Bezier patch.
    * @param patch2 the Bezier subpatch corresponding to the range [0.5, 1.0] of the local u1 variable of this Bezier patch.
    */

   protected void subdivideU1(BezierPatch patch1, BezierPatch patch2) {

      float[][] controlPointArrayR = new float[degree + 1][SPACE_POINT_STRIDE  * (degree + 1)];
      float[][] controlPointArrayQ = new float[degree + 1][SPACE_POINT_STRIDE  * (degree + 1)];

      if(degree == 2) {

         for(int i = 0; i <= degree; i++) {

            controlPointArrayQ[i][SPACE_POINT_STRIDE  * 0 + 0] = controlPointArray[i][SPACE_POINT_STRIDE  * 0 + 0];
            controlPointArrayQ[i][SPACE_POINT_STRIDE  * 0 + 1] = controlPointArray[i][SPACE_POINT_STRIDE  * 0 + 1];
            controlPointArrayQ[i][SPACE_POINT_STRIDE  * 0 + 2] = controlPointArray[i][SPACE_POINT_STRIDE  * 0 + 2];

            controlPointArrayQ[i][SPACE_POINT_STRIDE  * 1 + 0] = (controlPointArray[i][SPACE_POINT_STRIDE  * 0 + 0] + controlPointArray[i][SPACE_POINT_STRIDE  * 1 + 0]) / 2.0f;
            controlPointArrayQ[i][SPACE_POINT_STRIDE  * 1 + 1] = (controlPointArray[i][SPACE_POINT_STRIDE  * 0 + 1] + controlPointArray[i][SPACE_POINT_STRIDE  * 1 + 1]) / 2.0f;
            controlPointArrayQ[i][SPACE_POINT_STRIDE  * 1 + 2] = (controlPointArray[i][SPACE_POINT_STRIDE  * 0 + 2] + controlPointArray[i][SPACE_POINT_STRIDE  * 1 + 2]) / 2.0f;

            controlPointArrayR[i][SPACE_POINT_STRIDE  * 2 + 0] = controlPointArray[i][SPACE_POINT_STRIDE  * 2 + 0];
            controlPointArrayR[i][SPACE_POINT_STRIDE  * 2 + 1] = controlPointArray[i][SPACE_POINT_STRIDE  * 2 + 1];
            controlPointArrayR[i][SPACE_POINT_STRIDE  * 2 + 2] = controlPointArray[i][SPACE_POINT_STRIDE  * 2 + 2];

            controlPointArrayR[i][SPACE_POINT_STRIDE  * 1 + 0] = (controlPointArray[i][SPACE_POINT_STRIDE  * 1 + 0] + controlPointArray[i][SPACE_POINT_STRIDE  * 2 + 0]) / 2.0f;
            controlPointArrayR[i][SPACE_POINT_STRIDE  * 1 + 1] = (controlPointArray[i][SPACE_POINT_STRIDE  * 1 + 1] + controlPointArray[i][SPACE_POINT_STRIDE  * 2 + 1]) / 2.0f;
            controlPointArrayR[i][SPACE_POINT_STRIDE  * 1 + 2] = (controlPointArray[i][SPACE_POINT_STRIDE  * 1 + 2] + controlPointArray[i][SPACE_POINT_STRIDE  * 2 + 2]) / 2.0f;

            controlPointArrayQ[i][SPACE_POINT_STRIDE  * 2 + 0] = (controlPointArrayQ[i][SPACE_POINT_STRIDE  * 1 + 0] + controlPointArrayR[i][SPACE_POINT_STRIDE  * 1 + 0]) / 2.0f;
            controlPointArrayQ[i][SPACE_POINT_STRIDE  * 2 + 1] = (controlPointArrayQ[i][SPACE_POINT_STRIDE  * 1 + 1] + controlPointArrayR[i][SPACE_POINT_STRIDE  * 1 + 1]) / 2.0f;
            controlPointArrayQ[i][SPACE_POINT_STRIDE  * 2 + 2] = (controlPointArrayQ[i][SPACE_POINT_STRIDE  * 1 + 2] + controlPointArrayR[i][SPACE_POINT_STRIDE  * 1 + 2]) / 2.0f;

            controlPointArrayR[i][SPACE_POINT_STRIDE  * 0 + 0] = controlPointArrayQ[i][SPACE_POINT_STRIDE  * 2 + 0];
            controlPointArrayR[i][SPACE_POINT_STRIDE  * 0 + 1] = controlPointArrayQ[i][SPACE_POINT_STRIDE  * 2 + 1];
            controlPointArrayR[i][SPACE_POINT_STRIDE  * 0 + 2] = controlPointArrayQ[i][SPACE_POINT_STRIDE  * 2 + 2];

         }

      }

      else if(degree == 3) {

         for(int i = 0; i <= degree; i++) {

            controlPointArrayQ[i][SPACE_POINT_STRIDE  * 0 + 0] = controlPointArray[i][SPACE_POINT_STRIDE  * 0 + 0];
            controlPointArrayQ[i][SPACE_POINT_STRIDE  * 0 + 1] = controlPointArray[i][SPACE_POINT_STRIDE  * 0 + 1];
            controlPointArrayQ[i][SPACE_POINT_STRIDE  * 0 + 2] = controlPointArray[i][SPACE_POINT_STRIDE  * 0 + 2];

            controlPointArrayQ[i][SPACE_POINT_STRIDE  * 1 + 0] = (controlPointArray[i][SPACE_POINT_STRIDE  * 0 + 0] + controlPointArray[i][SPACE_POINT_STRIDE  * 1 + 0]) / 2.0f;
            controlPointArrayQ[i][SPACE_POINT_STRIDE  * 1 + 1] = (controlPointArray[i][SPACE_POINT_STRIDE  * 0 + 1] + controlPointArray[i][SPACE_POINT_STRIDE  * 1 + 1]) / 2.0f;
            controlPointArrayQ[i][SPACE_POINT_STRIDE  * 1 + 2] = (controlPointArray[i][SPACE_POINT_STRIDE  * 0 + 2] + controlPointArray[i][SPACE_POINT_STRIDE  * 1 + 2]) / 2.0f;

            controlPointArrayQ[i][SPACE_POINT_STRIDE  * 2 + 0] = controlPointArrayQ[i][SPACE_POINT_STRIDE  * 1 + 0] / 2.0f + (controlPointArray[i][SPACE_POINT_STRIDE  * 1 + 0] + controlPointArray[i][SPACE_POINT_STRIDE   * 2 + 0]) / 4.0f;
            controlPointArrayQ[i][SPACE_POINT_STRIDE  * 2 + 1] = controlPointArrayQ[i][SPACE_POINT_STRIDE  * 1 + 1] / 2.0f + (controlPointArray[i][SPACE_POINT_STRIDE  * 1 + 1] + controlPointArray[i][SPACE_POINT_STRIDE   * 2 + 1]) / 4.0f;
            controlPointArrayQ[i][SPACE_POINT_STRIDE  * 2 + 2] = controlPointArrayQ[i][SPACE_POINT_STRIDE  * 1 + 2] / 2.0f + (controlPointArray[i][SPACE_POINT_STRIDE  * 1 + 2] + controlPointArray[i][SPACE_POINT_STRIDE   * 2 + 2]) / 4.0f;

            controlPointArrayR[i][SPACE_POINT_STRIDE  * 3 + 0] = controlPointArray[i][SPACE_POINT_STRIDE  * 3 + 0];
            controlPointArrayR[i][SPACE_POINT_STRIDE  * 3 + 1] = controlPointArray[i][SPACE_POINT_STRIDE  * 3 + 1];
            controlPointArrayR[i][SPACE_POINT_STRIDE  * 3 + 2] = controlPointArray[i][SPACE_POINT_STRIDE  * 3 + 2];

            controlPointArrayR[i][SPACE_POINT_STRIDE  * 2 + 0] = (controlPointArray[i][SPACE_POINT_STRIDE  * 2 + 0] + controlPointArray[i][SPACE_POINT_STRIDE  * 3 + 0]) / 2.0f;
            controlPointArrayR[i][SPACE_POINT_STRIDE  * 2 + 1] = (controlPointArray[i][SPACE_POINT_STRIDE  * 2 + 1] + controlPointArray[i][SPACE_POINT_STRIDE  * 3 + 1]) / 2.0f;
            controlPointArrayR[i][SPACE_POINT_STRIDE  * 2 + 2] = (controlPointArray[i][SPACE_POINT_STRIDE  * 2 + 2] + controlPointArray[i][SPACE_POINT_STRIDE  * 3 + 2]) / 2.0f;

            controlPointArrayR[i][SPACE_POINT_STRIDE  * 1 + 0] = controlPointArrayR[i][SPACE_POINT_STRIDE  * 2 + 0] / 2.0f  + (controlPointArray[i][SPACE_POINT_STRIDE  * 1 + 0] + controlPointArray[i][SPACE_POINT_STRIDE  * 2 + 0]) / 4.0f;
            controlPointArrayR[i][SPACE_POINT_STRIDE  * 1 + 1] = controlPointArrayR[i][SPACE_POINT_STRIDE  * 2 + 1] / 2.0f  + (controlPointArray[i][SPACE_POINT_STRIDE  * 1 + 1] + controlPointArray[i][SPACE_POINT_STRIDE  * 2 + 1]) / 4.0f;
            controlPointArrayR[i][SPACE_POINT_STRIDE  * 1 + 2] = controlPointArrayR[i][SPACE_POINT_STRIDE  * 2 + 2] / 2.0f  + (controlPointArray[i][SPACE_POINT_STRIDE  * 1 + 2] + controlPointArray[i][SPACE_POINT_STRIDE  * 2 + 2]) / 4.0f;

            controlPointArrayQ[i][SPACE_POINT_STRIDE  * 3 + 0] = (controlPointArrayQ[i][SPACE_POINT_STRIDE  * 2 + 0] + controlPointArrayR[i][SPACE_POINT_STRIDE  * 1 + 0]) / 2.0f;
            controlPointArrayQ[i][SPACE_POINT_STRIDE  * 3 + 1] = (controlPointArrayQ[i][SPACE_POINT_STRIDE  * 2 + 1] + controlPointArrayR[i][SPACE_POINT_STRIDE  * 1 + 1]) / 2.0f;
            controlPointArrayQ[i][SPACE_POINT_STRIDE  * 3 + 2] = (controlPointArrayQ[i][SPACE_POINT_STRIDE  * 2 + 2] + controlPointArrayR[i][SPACE_POINT_STRIDE  * 1 + 2]) / 2.0f;

            controlPointArrayR[i][SPACE_POINT_STRIDE  * 0 + 0] = controlPointArrayQ[i][SPACE_POINT_STRIDE  * 3 + 0];
            controlPointArrayR[i][SPACE_POINT_STRIDE  * 0 + 1] = controlPointArrayQ[i][SPACE_POINT_STRIDE  * 3 + 1];
            controlPointArrayR[i][SPACE_POINT_STRIDE  * 0 + 2] = controlPointArrayQ[i][SPACE_POINT_STRIDE  * 3 + 2];

         }

      }

      for(int i = 0; i <= degree; i++) {

          System.arraycopy(controlPointArrayR[i], 0, patch1.controlPointArray[i], 0, SPACE_POINT_STRIDE  * (degree + 1));
          System.arraycopy(controlPointArrayQ[i], 0, patch2.controlPointArray[i], 0, SPACE_POINT_STRIDE  * (degree + 1));

      }

   }

   /**
    * Subdivides this Bezier patch in the u2 tangential direction and sets the patch arguments to the resulting subpatches.
    *
    * @param patch1 the Bezier subpatch corresponding to the range [0.0, 0.5] of the local u2 variable of this Bezier patch.
    * @param patch2 the Bezier subpatch corresponding to the range [0.5, 1.0] of the local u2 variable of this Bezier patch.
    */

  protected void subdivideU2(BezierPatch patch1, BezierPatch patch2) {

      float[][] controlPointArrayR = new float[degree + 1][SPACE_POINT_STRIDE  * (degree + 1)];
      float[][] controlPointArrayQ = new float[degree + 1][SPACE_POINT_STRIDE  * (degree + 1)];

      if(degree == 2) {

         for(int i = 0; i <= degree; i++) {

            controlPointArrayQ[0][SPACE_POINT_STRIDE  * i + 0] = controlPointArray[0][SPACE_POINT_STRIDE  * i + 0];
            controlPointArrayQ[0][SPACE_POINT_STRIDE  * i + 1] = controlPointArray[0][SPACE_POINT_STRIDE  * i + 1];
            controlPointArrayQ[0][SPACE_POINT_STRIDE  * i + 2] = controlPointArray[0][SPACE_POINT_STRIDE  * i + 2];

            controlPointArrayQ[1][SPACE_POINT_STRIDE  * i + 0] = (controlPointArray[0][SPACE_POINT_STRIDE  * i + 0] + controlPointArray[1][SPACE_POINT_STRIDE  * i + 0]) / 2.0f;
            controlPointArrayQ[1][SPACE_POINT_STRIDE  * i + 1] = (controlPointArray[0][SPACE_POINT_STRIDE  * i + 1] + controlPointArray[1][SPACE_POINT_STRIDE  * i + 1]) / 2.0f;
            controlPointArrayQ[1][SPACE_POINT_STRIDE  * i + 2] = (controlPointArray[0][SPACE_POINT_STRIDE  * i + 2] + controlPointArray[1][SPACE_POINT_STRIDE  * i + 2]) / 2.0f;

            controlPointArrayR[2][SPACE_POINT_STRIDE  * i + 0] = controlPointArray[2][SPACE_POINT_STRIDE  * i + 0];
            controlPointArrayR[2][SPACE_POINT_STRIDE  * i + 1] = controlPointArray[2][SPACE_POINT_STRIDE  * i + 1];
            controlPointArrayR[2][SPACE_POINT_STRIDE  * i + 2] = controlPointArray[2][SPACE_POINT_STRIDE  * i + 2];

            controlPointArrayR[1][SPACE_POINT_STRIDE  * i + 0] = (controlPointArray[1][SPACE_POINT_STRIDE  * i + 0] + controlPointArray[2][SPACE_POINT_STRIDE  * i + 0]) / 2.0f;
            controlPointArrayR[1][SPACE_POINT_STRIDE  * i + 1] = (controlPointArray[1][SPACE_POINT_STRIDE  * i + 1] + controlPointArray[2][SPACE_POINT_STRIDE  * i + 1]) / 2.0f;
            controlPointArrayR[1][SPACE_POINT_STRIDE  * i + 2] = (controlPointArray[1][SPACE_POINT_STRIDE  * i + 2] + controlPointArray[2][SPACE_POINT_STRIDE  * i + 2]) / 2.0f;

            controlPointArrayQ[2][SPACE_POINT_STRIDE  * i + 0] = (controlPointArrayQ[1][SPACE_POINT_STRIDE  * i + 0] + controlPointArrayR[1][SPACE_POINT_STRIDE  * i + 0]) / 2.0f;
            controlPointArrayQ[2][SPACE_POINT_STRIDE  * i + 1] = (controlPointArrayQ[1][SPACE_POINT_STRIDE  * i + 1] + controlPointArrayR[1][SPACE_POINT_STRIDE  * i + 1]) / 2.0f;
            controlPointArrayQ[2][SPACE_POINT_STRIDE  * i + 2] = (controlPointArrayQ[1][SPACE_POINT_STRIDE  * i + 2] + controlPointArrayR[1][SPACE_POINT_STRIDE  * i + 2]) / 2.0f;

            controlPointArrayR[0][SPACE_POINT_STRIDE  * i + 0] = controlPointArrayQ[2][SPACE_POINT_STRIDE  * i + 0];
            controlPointArrayR[0][SPACE_POINT_STRIDE  * i + 1] = controlPointArrayQ[2][SPACE_POINT_STRIDE  * i + 1];
            controlPointArrayR[0][SPACE_POINT_STRIDE  * i + 2] = controlPointArrayQ[2][SPACE_POINT_STRIDE  * i + 2];

         }

      }

      else if(degree == 3) {

         for(int i = 0; i <= degree; i++) {

            controlPointArrayQ[0][SPACE_POINT_STRIDE  * i + 0] = controlPointArray[0][SPACE_POINT_STRIDE  * i + 0];
            controlPointArrayQ[0][SPACE_POINT_STRIDE  * i + 1] = controlPointArray[0][SPACE_POINT_STRIDE  * i + 1];
            controlPointArrayQ[0][SPACE_POINT_STRIDE  * i + 2] = controlPointArray[0][SPACE_POINT_STRIDE  * i + 2];

            controlPointArrayQ[1][SPACE_POINT_STRIDE  * i + 0] = (controlPointArray[0][SPACE_POINT_STRIDE  * i + 0] + controlPointArray[1][SPACE_POINT_STRIDE  * i + 0]) / 2.0f;
            controlPointArrayQ[1][SPACE_POINT_STRIDE  * i + 1] = (controlPointArray[0][SPACE_POINT_STRIDE  * i + 1] + controlPointArray[1][SPACE_POINT_STRIDE  * i + 1]) / 2.0f;
            controlPointArrayQ[1][SPACE_POINT_STRIDE  * i + 2] = (controlPointArray[0][SPACE_POINT_STRIDE  * i + 2] + controlPointArray[1][SPACE_POINT_STRIDE  * i + 2]) / 2.0f;

            controlPointArrayQ[2][SPACE_POINT_STRIDE  * i + 0] = controlPointArrayQ[1][SPACE_POINT_STRIDE  * i + 0] / 2.0f + (controlPointArray[1][SPACE_POINT_STRIDE  * i + 0] + controlPointArray[2][SPACE_POINT_STRIDE  * i + 0]) / 4.0f;
            controlPointArrayQ[2][SPACE_POINT_STRIDE  * i + 1] = controlPointArrayQ[1][SPACE_POINT_STRIDE  * i + 1] / 2.0f + (controlPointArray[1][SPACE_POINT_STRIDE  * i + 1] + controlPointArray[2][SPACE_POINT_STRIDE  * i + 1]) / 4.0f;
            controlPointArrayQ[2][SPACE_POINT_STRIDE  * i + 2] = controlPointArrayQ[1][SPACE_POINT_STRIDE  * i + 2] / 2.0f + (controlPointArray[1][SPACE_POINT_STRIDE  * i + 2] + controlPointArray[2][SPACE_POINT_STRIDE  * i + 2]) / 4.0f;

            controlPointArrayR[3][SPACE_POINT_STRIDE  * i + 0] = controlPointArray[3][SPACE_POINT_STRIDE  * i + 0];
            controlPointArrayR[3][SPACE_POINT_STRIDE  * i + 1] = controlPointArray[3][SPACE_POINT_STRIDE  * i + 1];
            controlPointArrayR[3][SPACE_POINT_STRIDE  * i + 2] = controlPointArray[3][SPACE_POINT_STRIDE  * i + 2];

            controlPointArrayR[2][SPACE_POINT_STRIDE  * i + 0] = (controlPointArray[2][SPACE_POINT_STRIDE  * i + 0] + controlPointArray[3][SPACE_POINT_STRIDE  * i + 0]) / 2.0f;
            controlPointArrayR[2][SPACE_POINT_STRIDE  * i + 1] = (controlPointArray[2][SPACE_POINT_STRIDE  * i + 1] + controlPointArray[3][SPACE_POINT_STRIDE  * i + 1]) / 2.0f;
            controlPointArrayR[2][SPACE_POINT_STRIDE  * i + 2] = (controlPointArray[2][SPACE_POINT_STRIDE  * i + 2] + controlPointArray[3][SPACE_POINT_STRIDE  * i + 2]) / 2.0f;

            controlPointArrayR[1][SPACE_POINT_STRIDE  * i + 0] = controlPointArrayR[2][SPACE_POINT_STRIDE  * i + 0] / 2.0f  + (controlPointArray[1][SPACE_POINT_STRIDE  * i + 0] + controlPointArray[2][SPACE_POINT_STRIDE  * i + 0]) / 4.0f;
            controlPointArrayR[1][SPACE_POINT_STRIDE  * i + 1] = controlPointArrayR[2][SPACE_POINT_STRIDE  * i + 1] / 2.0f  + (controlPointArray[1][SPACE_POINT_STRIDE  * i + 1] + controlPointArray[2][SPACE_POINT_STRIDE  * i + 1]) / 4.0f;
            controlPointArrayR[1][SPACE_POINT_STRIDE  * i + 2] = controlPointArrayR[2][SPACE_POINT_STRIDE  * i + 2] / 2.0f  + (controlPointArray[1][SPACE_POINT_STRIDE  * i + 2] + controlPointArray[2][SPACE_POINT_STRIDE  * i + 2]) / 4.0f;

            controlPointArrayQ[3][SPACE_POINT_STRIDE  * i + 0] = (controlPointArrayQ[2][SPACE_POINT_STRIDE  * i + 0] + controlPointArrayR[1][SPACE_POINT_STRIDE  * i + 0]) / 2.0f;
            controlPointArrayQ[3][SPACE_POINT_STRIDE  * i + 1] = (controlPointArrayQ[2][SPACE_POINT_STRIDE  * i + 1] + controlPointArrayR[1][SPACE_POINT_STRIDE  * i + 1]) / 2.0f;
            controlPointArrayQ[3][SPACE_POINT_STRIDE  * i + 2] = (controlPointArrayQ[2][SPACE_POINT_STRIDE  * i + 2] + controlPointArrayR[1][SPACE_POINT_STRIDE  * i + 2]) / 2.0f;

            controlPointArrayR[0][SPACE_POINT_STRIDE  *i + 0] = controlPointArrayQ[3][SPACE_POINT_STRIDE  * i + 0];
            controlPointArrayR[0][SPACE_POINT_STRIDE  *i + 1] = controlPointArrayQ[3][SPACE_POINT_STRIDE  * i + 1];
            controlPointArrayR[0][SPACE_POINT_STRIDE  *i + 2] = controlPointArrayQ[3][SPACE_POINT_STRIDE  * i + 2];

         }

      }

      for(int i = 0; i <= degree; i++) {

          System.arraycopy(controlPointArrayR[i], 0, patch1.controlPointArray[i], 0, SPACE_POINT_STRIDE  * (degree + 1));
          System.arraycopy(controlPointArrayQ[i], 0, patch2.controlPointArray[i], 0, SPACE_POINT_STRIDE  * (degree + 1));

      }

   }

   /**
    * Subdivides this Bezier patch in the u1 and u2 tangential directions and sets the patch arguments to the resulting subpatches.
    *
    * @param patch1 the Bezier subpatch corresponding to the range [0.0, 0.5] of the local u1 variable of this Bezier patch and the range [0.0, 0.5] of the local u2 variable of this Bezier patch.
    * @param patch2 the Bezier subpatch corresponding to the range [0.5, 1.0] of the local u1 variable of this Bezier patch and the range [0.0, 0.5] of the local u2 variable of this Bezier patch.
    * @param patch3 the Bezier subpatch corresponding to the range [0.0, 0.5] of the local u1 variable of this Bezier patch and the range [0.5, 1.0] of the local u2 variable of this Bezier patch.
    * @param patch4 the Bezier subpatch corresponding to the range [0.5, 1.0] of the local u1 variable of this Bezier patch and the range [0.5, 1.0] of the local u2 variable of this Bezier patch.
    */

   public void subdivide(BezierPatch patch1, BezierPatch patch2, BezierPatch patch3, BezierPatch patch4) {

      BezierPatch tempPatch1 = new BezierPatch(degree);
      BezierPatch tempPatch2 = new BezierPatch(degree);

      subdivideU2(tempPatch1, tempPatch2);

      tempPatch1.subdivideU1(patch1, patch2);
      tempPatch2.subdivideU1(patch3, patch4);

   }

   /**
    * Retrieves the address of the control point array of this Bezier patch.
    *
    * @param controlPointsAddressArray the array of arrays to store the control point array of this Bezier patch.
    */

   public void getControlPointsAddress(float[][][] controlPointsAddressArray) { controlPointsAddressArray[0] = controlPointArray; }

   /**
    * Sets the control point of this Bezier patch indexed by the integer arguments to the point determined by the floating point arguments.
    *
    * @param x the value that the x-coordinate of the control point of this Bezier patch indexed by the integer argument is set to.
    * @param y the value that the y-coordinate of the control point of this Bezier patch indexed by the integer argument is set to.
    * @param z the value that the z-coordinate of the control point of this Bezier patch indexed by the integer argument is set to.
    * @param rowIndex the row index of the control point in the control point array that is set to the point determined by the floating point argument.
    * @param columnIndex the column index of the control point in the control point array that is set to the point determined by the floating point argument.
    */

   public void set(float x, float y, float z, int rowIndex, int columnIndex) {

      controlPointArray[rowIndex][SPACE_POINT_STRIDE  * columnIndex + 0] = x;
      controlPointArray[rowIndex][SPACE_POINT_STRIDE  * columnIndex + 1] = y;
      controlPointArray[rowIndex][SPACE_POINT_STRIDE  * columnIndex + 2] = z;

   }

   /**
    * Overrides the setLowerBoundsU1() method of the parent class and throws an exception indicating that the u1 local variable boundary is fixed.
    *
    * @param lowerBoundsU1 the value that is attempted to be set as the u1 local variable lower bound.
    * @throws FixedBoundaryException
    */

   public void setLowerBoundsU1(float lowerBoundsU1) { throw new FixedBoundaryException(); }

    /**
     * Overrides the setLowerBoundsU2() method of the parent class and throws an exception indicating that the u2 local variable boundary is fixed.
     *
     * @param lowerBoundsU2 the value that is attempted to be set as the u2 local variable lower bound.
     * @throws FixedBoundaryException
     */

   public void setLowerBoundsU2(float lowerBoundsU2) { throw new FixedBoundaryException(); }

   /**
    * Overrides the setUpperBoundsU1() method of the parent class and throws an exception indicating that the u1 local variable boundary is fixed.
    *
    * @param upperBoundsU1 the value that is attempted to be set as the u1 local variable upper bound.
    * @throws FixedBoundaryException
    */

   public void setUpperBoundsU1(float upperBoundsU1) { throw new FixedBoundaryException(); }

   /**
    * Overrides the setUpperBoundsU2() method of the parent class and throws an exception indicating that the u2 local variable boundary is fixed.
    *
    * @param upperBoundsU2 the value that is attempted to be set as the u2 local variable upper bound.
    * @throws FixedBoundaryException
    */

   public void setUpperBoundsU2(float upperBoundsU2) { throw new FixedBoundaryException(); }

}

