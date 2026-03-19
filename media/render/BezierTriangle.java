package shamu.media.render;

/** Procedural modeling class representing a Bezier triangle as a surface that parameterizes coordinates as the triangular region defined by the triangular
 *  bernstein polynomials of degree n and a grid of control points. The control points will control the curvature and torsion of a curve on the triangle,
 *  and the polygonal mesh defined by the control points will represent a polygonal approximation of the Bezier triangle. The default local variable bounds
 *  for this surface is the barycentric coordinate set {(u1, u2): u1 >= 0, u2 >= 0, u1 + u2 <= 1}.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class BezierTriangle extends ParametricProcedure {

   /**
    * The degree of this Bezier triangle.
    */

   protected final int degree;

   /**
    * The array of control points of this Bezier triangle.
    */

   protected float[][] controlPointArray;

   /**
    * Constructs a Bezier triangle having the degree specified by the integer argument, and having the default local variable bounds with an empty triangular grid of control points.
    *
    * @param degree the value that the degree of this Bezier triangle is set to.
    */

   public BezierTriangle(int degree) {

      super(0.0f, 0.0f, 1.0f, 1.0f);

      this.degree = degree;
      this.controlPointArray = new float[degree + 1][];

      for(int i = 0; i <= degree; i++)
         controlPointArray[i] = new float[SPACE_POINT_STRIDE  * (degree - i + 1)];

   }

   /**
    * Constructs a Bezier triangle specified by the floating point array argument having the default local variable bounds and the degree
    * specified by the integer argument. Each array element of the control point array should have exactly degree + 1 - i elements,
    * where i ranges from i = 0 to i = degree.
    *
    * @param degree the value that the degree of the this Bezier triangle is set to.
    * @param controlPointArray the array that control point array of this Bezier triangle is set to.
    */

   public BezierTriangle(int degree, float[][] controlPointArray) {

      super(0, 0, 1, 1);

      this.degree = degree;

      this.controlPointArray = controlPointArray;

   }

   /**
    * Calculates and returns the x-coordinate of the point on this Bezier triangle that is mapped from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point on this Bezier triangle that is mapped from the floating point arguments.
    */

   public float mapX(float u1, float u2) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         for(int j = 0; j <= degree - i; j++)
            sum += controlPointArray[i][SPACE_POINT_STRIDE  * j + 0] * CurveEvaluator.evaluate(this, i, j, degree - i - j, degree, u1, u2);

      return sum;

   }

   /**
    * Calculates and returns the y-coordinate of the point on this Bezier triangle that is mapped from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point on this Bezier triangle that is mapped from the floating point arguments.
    */

   public float mapY(float u1, float u2) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         for(int j = 0; j <= degree - i; j++)
            sum += controlPointArray[i][SPACE_POINT_STRIDE  * j + 1] * CurveEvaluator.evaluate(this, i, j, degree - i - j, degree, u1, u2);

      return sum;

   }

   /**
    * Calculates and returns the z-coordinate of the point on this Bezier triangle that is mapped from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point on this Bezier triangle that is mapped from the floating point arguments.
    */

   public float mapZ(float u1, float u2) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         for(int j = 0; j <= degree - i; j++)
            sum += controlPointArray[i][SPACE_POINT_STRIDE  * j + 2] * CurveEvaluator.evaluate(this, i, j, degree - i - j, degree, u1, u2);

      return sum;

   }

   /**
    * Calculates and returns the x-coordinate of the point mapped by the partial derivative of this Bezier triangle with respect to the u1 local
    * variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point mapped by the partial derivative of this Bezier triangle with respect to the u1 local variable from the floating point arguments.
    */

   public float partialU1MapX(float u1, float u2) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         for(int j = 0; j <= degree - i; j++)
            sum += controlPointArray[i][SPACE_POINT_STRIDE  * j + 0] * (CurveEvaluator.evaluate(this, i - 1, j, degree - i - j, degree - 1, u1, u2) - CurveEvaluator.evaluate(this, i, j, degree - i - j - 1, degree - 1, u1, u2 )) * degree;

      return sum;

   }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the partial derivative of this Bezier triangle with respect to the u1 local
    * variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the partial derivative of this Bezier triangle with respect to the u1 local variable from the floating point arguments.
    */

   public float partialU1MapY(float u1, float u2) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         for(int j = 0; j <= degree - i; j++)
            sum += controlPointArray[i][SPACE_POINT_STRIDE  * j + 1] * (CurveEvaluator.evaluate(this, i - 1, j, degree - i - j, degree - 1, u1, u2) - CurveEvaluator.evaluate(this, i, j, degree - i - j - 1, degree - 1, u1, u2 )) * degree;

       return sum;

   }

   /**
    * Calculates and returns the z-coordinate of the point mapped by the partial derivative of this Bezier triangle with respect to the u1 local
    * variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point mapped by the partial derivative of this Bezier triangle with respect to the u1 local variable from the floating point arguments.
    */

   public float partialU1MapZ(float u1, float u2) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         for(int j = 0; j <= degree - i; j++)
            sum += controlPointArray[i][SPACE_POINT_STRIDE  * j + 2] * (CurveEvaluator.evaluate(this, i - 1, j, degree - i - j, degree - 1, u1, u2) - CurveEvaluator.evaluate(this, i, j, degree - i - j - 1, degree - 1, u1, u2 )) * degree;

      return sum;

   }

   /**
    * Calculates and returns the x-coordinate of the point mapped by the partial derivative of this Bezier triangle with respect to the u2 local variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point mapped by the partial derivative of this Bezier triangle with respect to the u2 local variable from the floating point arguments.
    */

   public float partialU2MapX(float u1, float u2) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         for(int j = 0; j <= degree - i; j++)
            sum += controlPointArray[i][SPACE_POINT_STRIDE  * j + 0] * (CurveEvaluator.evaluate(this, i, j - 1, degree - i - j, degree - 1, u1, u2)  - CurveEvaluator.evaluate(this, i, j, degree - i - j - 1, degree - 1, u1, u2 )) * degree;

      return sum;

   }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the partial derivative of this Bezier triangle with respect to the u2 local
    * variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the partial derivative of this Bezier triangle with respect to the u2 local variable from the floating point arguments.
    */

   public float partialU2MapY(float u1, float u2) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         for(int j = 0; j <= degree - i; j++)
            sum += controlPointArray[i][SPACE_POINT_STRIDE  * j + 1] * (CurveEvaluator.evaluate(this, i, j - 1, degree - i - j, degree - 1, u1, u2)  - CurveEvaluator.evaluate(this, i, j, degree - i - j - 1, degree - 1, u1, u2 )) * degree;

      return sum;

   }

   /**
    * Calculates and returns the z-coordinate of the point mapped by the partial derivative of this Bezier triangle with respect to the u2 local
    * variable from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point mapped by the partial derivative of this Bezier triangle with respect to the u2 local variable from the floating point arguments.
    */

   public float partialU2MapZ(float u1, float u2) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         for(int j = 0; j <= degree - i; j++)
            sum += controlPointArray[i][SPACE_POINT_STRIDE  * j + 2] * (CurveEvaluator.evaluate(this, i, j - 1, degree - i - j, degree - 1, u1, u2)  - CurveEvaluator.evaluate(this, i, j, degree - i - j - 1, degree - 1, u1, u2 )) * degree;

      return sum;

   }

   /**
    * Retrieves the address of the control point array of this Bezier triangle.
    *
    * @param controlPointsAddressArray the array of arrays to store the control point array of this Bezier triangle.
    */

   public void getControlPointsAddress(float[][][] controlPointsAddressArray) { controlPointsAddressArray[0] = controlPointArray; }

   /**
    * Sets the control point of this Bezier triangle indexed by the integer arguments to the point determined by the floating point arguments.
    *
    * @param x the value that the x-coordinate of the control point of this Bezier triangle indexed by the integer argument is set to.
    * @param y the value that the y-coordinate of the control point of this Bezier triangle indexed by the integer argument is set to.
    * @param z the value that the z-coordinate of the control point of this Bezier triangle indexed by the integer argument is set to.
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