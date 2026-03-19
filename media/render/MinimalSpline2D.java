package shamu.media.render;

/** Class representing a uniform B-spline curve of general degree defined in 2D space by a set of control points.
 *
 *  @see ProfileCurve2D
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class MinimalSpline2D extends ProfileCurve2D {

   /**
    * The degree of this B-spline curve.
    */

   protected final int degree;

   /**
    * The array of control points of this B-spline curve.
    */

   protected float[] controlPointArray;

   /**
    * Constructs a default B-spline curvee with the number of control points and degree specified by the integer arguments having an empty control point array.
    *
    * @param degree the value that the degree of the B-spline curve is set to.
    * @param length the number of control points the B-spline curve will contain.
    */

   public MinimalSpline2D(int degree, int length) {

	  super(degree, length);

	  this.degree = degree;

	  this.controlPointArray = new float[PLANE_POINT_STRIDE  * length];

   }

   /**
    * Constructs a B-spline curve with the degree specified by the integer argument, having a uniform knot vector and the control coordinate point array determined by the array argument.
    *
    * @param degree the value that the degree of this B-spline curve is set to.
    * @param controlPointArray the array that the control point array of this B-spline curve is set to.
    */

   public MinimalSpline2D(int degree, float[] controlPointArray) {

      super(degree, controlPointArray.length / PLANE_POINT_STRIDE );

      this.degree = degree;

      this.controlPointArray = controlPointArray;

   }

   /**
    * Calculates and returns the x-coordinate of the point on this B-spline curve in (x, y) space mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point on this B-spline curve in (x, y) space mapped from the floating point argument.
    */

   public float mapX(float t) {

      float sum = 0.0f;

      for(int i = 0; i < controlPointArray.length / PLANE_POINT_STRIDE ; i++)
         sum += controlPointArray[PLANE_POINT_STRIDE  * i + 1] * CurveEvaluator.evaluate(this, i, degree, t);

      return sum;

   }

   /**
    * Calculates and returns the y-coordinate of the point on this B-spline curve in (x, y) space mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point on this B-spline curve in (x, y) space mapped from the floating point argument.
    */

   public float mapY(float t) {

      float sum = 0.0f;

      for(int i = 0; i < controlPointArray.length / PLANE_POINT_STRIDE ; i++)
         sum += controlPointArray[PLANE_POINT_STRIDE  * i + 1] * CurveEvaluator.evaluate(this, i, degree, t);

      return sum;

   }

   /**
    * Calculates and returns the x-coordinate of the point in (x, y) space mapped by the derivative of this B-spline curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point in (x, y) space mapped by the derivative of this B-spline curve from the floating point argument.
    */

   public float derivativeMapX(float t) {

      float sum = 0.0f;

      for(int i = 0; i < controlPointArray.length / PLANE_POINT_STRIDE ; i++)
         sum += controlPointArray[PLANE_POINT_STRIDE  * i + 0] * (CurveEvaluator.evaluate(this, i, degree - 1, t) - CurveEvaluator.evaluate(this, i + 1, degree - 1, t)) * degree;

      return sum;

   }

   /**
    * Calculates and returns the y-coordinate of the point in (x, y) space mapped by the derivative of this B-spline curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point in (x, y) space mapped by the derivative of this B-spline curve from the floating point argument.
    */

   public float derivativeMapY(float t) {

      float sum = 0.0f;

      for(int i = 0; i < controlPointArray.length / PLANE_POINT_STRIDE ; i++)
         sum += controlPointArray[PLANE_POINT_STRIDE  * i + 1] * (CurveEvaluator.evaluate(this, i, degree - 1, t) - CurveEvaluator.evaluate(this, i + 1, degree - 1, t)) * degree;

      return sum;

   }

   /**
    * Retrieves the address of the control point array of this B-spline curve.
    *
    * @param controlPointsAddressArray the array of arrays to store the control point array of this B-spline curve.
    */

   public void getControlPointsAddress(float[][] controlPointsAddressArray) { controlPointsAddressArray[0] = controlPointArray; }

   /**
    * Sets the control point of this B-spline curve indexed by the integer argument to the point determined by the floating point arguments.
    *
    * @param x the value that the x-coordinate of the control point of this B-spline curve indexed by the integer argument is set to.
    * @param y the value that the y-coordinate of the control point of this B-spline curve indexed by the integer argument is set to.
    * @param index the index of the control point in the control point array that is set to the point determined by the floating point argument.
    * @return the index of the position immediately following the control point set in the control point array of this B-spline curve.
    */

   public int setControlPoint(float x, float y, int index) {

	  controlPointArray[index++] = x;
	  controlPointArray[index++] = y;

      return index;

   }

   /**
    * Overrides the setLowerBoundsT() method of the parent class and throws an exception indicating that the parametric variable boundary is fixed.
    *
    * @param lowerBoundsT the value that is attempted to be set as the parametric variable lower bound.
    * @throws FixedBoundaryException
    */

   public void setLowerBoundsT(float lowerBoundsT) { throw new FixedBoundaryException(); }

   /**
    * Overrides the setUpperBoundsT() method of the parent class and throws an exception indicating that the parametric variable boundary is fixed.
    *
    * @param upperBoundsT the value that is attempted to be set as the parametric variable upper bound.
    * @throws FixedBoundaryException
    */

   public void setUpperBoundsT(float upperBoundsT) { throw new FixedBoundaryException(); }

}