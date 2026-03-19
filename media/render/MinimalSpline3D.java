package shamu.media.render;

/** Class representing a B-spline curve defined in 3D space.
 *
 *  @see ProfileCurve3D
 *
 *  @author Scott T. Carter
 *  @version 1.4
 */

public class MinimalSpline3D extends ProfileCurve3D {

   /**
    * The degree of this B-spline curve.
    */

   protected final int degree;

   /**
    * The array of control points of this B-spline curve.
    */

   protected float[] controlPointArray;

   /**
    * Constructs a default B-spline curve with the number of control points, and degree specified by the integer arguments.
    *
    * @param degree the value that the degree of this B-spline curve is set to.
    * @param length the number of control points this B-spline curve will contain.
    */

   public MinimalSpline3D(int degree, int length) {

	  super(degree, length);

	  this.degree = degree;

	  this.controlPointArray = new float[SPACE_POINT_STRIDE  * length];

   }

   /**
    * Constructs a B-spline curve with the degree specified by the integer argument, having a uniform knot vector and the control coordinate point array determined by the array argument.
    *
    * @param degree the value that the degree of this B-spline curve is set to.
    * @param controlPointArray the array that the control point array of this Bezier curve is set to.
    * @param lowerBoundsT the value that the parametric variable lower bound that determines the boundary of this B-spline curve is set to.
    * @param upperBoundsT the value that the parametric variable upper bound that determines the boundary of this B-spline curve is set to.
    */

   public MinimalSpline3D(int degree, float[] controlPointArray) {

	  super(degree, controlPointArray.length / SPACE_POINT_STRIDE );

	  this.degree = degree;

      this.controlPointArray = controlPointArray;

   }

   /**
    * Calculates and returns the x-coordinate of the point on this B-spline curve that is mapped from the
    * floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point on this B-spline curve that is mapped from the parametric floating point argument.
    */

   public float mapX(float t) {

      float sum = 0.0f;

      for(int i = 0; i < controlPointArray.length / SPACE_POINT_STRIDE ; i++)
         sum += controlPointArray[3 * i + 0] * CurveEvaluator.evaluate(this, i, degree, t);

      return sum;

   }

   /**
    * Calculates and returns the y-coordinate of the point on this B-spline curve that is mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point on this B-spline curve that is mapped from the parametric floating point argument.
    */

   public float mapY(float t) {

      float sum = 0.0f;

      for(int i = 0; i < controlPointArray.length / SPACE_POINT_STRIDE ; i++)
         sum += controlPointArray[SPACE_POINT_STRIDE  * i + 1] * CurveEvaluator.evaluate(this, i, degree, t);

      return sum;

   }

   /**
    * Calculates and returns the z-coordinate of the point on this B-spline curve that is mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point on this B-spline curve that is mapped from the parametric floating point argument.
    */

   public float mapZ(float t) {

      float sum = 0.0f;

      for(int i = 0; i < controlPointArray.length / SPACE_POINT_STRIDE ; i++)
         sum += controlPointArray[SPACE_POINT_STRIDE  * i + 2] * CurveEvaluator.evaluate(this, i, degree, t);

      return sum;

   }

   /**
    * Calculates and returns the x-coordinate of the point mapped by the derivative of this B-spline curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point mapped by the derivative of this B-spline curve from the floating point argument.
    */

   public float derivativeMapX(float t) {

      float sum = 0.0f;

      for(int i = 0; i < controlPointArray.length / SPACE_POINT_STRIDE ; i++)
         sum += controlPointArray[SPACE_POINT_STRIDE  * i + 0] * (CurveEvaluator.evaluate(this, i, degree - 1, t) - CurveEvaluator.evaluate(this, i + 1, degree - 1, t)) * degree;

      return sum;

   }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the derivative of this B-spline curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the derivative of this B-spline curve from the floating point argument.
    */

   public float derivativeMapY(float t) {

      float sum = 0.0f;

      for(int i = 0; i < controlPointArray.length / SPACE_POINT_STRIDE ; i++)
         sum += controlPointArray[SPACE_POINT_STRIDE  * i + 1] * (CurveEvaluator.evaluate(this, i, degree - 1, t) - CurveEvaluator.evaluate(this, i + 1, degree - 1, t)) * degree;

      return sum;

   }

   /**
    * Calculates and returns the z-coordinate of the point mapped by the derivative of this B-spline curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point mapped by the derivative of this B-spline curve from the floating point argument.
    */

   public float derivativeMapZ(float t) {

      float sum = 0.0f;

      for(int i = 0; i < controlPointArray.length / SPACE_POINT_STRIDE ; i++)
         sum += controlPointArray[SPACE_POINT_STRIDE  * i + 2] * (CurveEvaluator.evaluate(this, i, degree - 1, t) - CurveEvaluator.evaluate(this, i + 1, degree - 1, t)) * degree;

      return sum;

   }

   /**
    * Calculates and returns the x-coordinate of the point mapped by the second derivative of this B-spline curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point mapped by the second derivative of this B-spline curve from the floating point argument.
    */

   public float secondDerivativeMapX(float t) {

      float sum = 0.0f;

      for(int i = 0; i < controlPointArray.length / SPACE_POINT_STRIDE ; i++)
         sum += controlPointArray[SPACE_POINT_STRIDE  * i + 0] * (CurveEvaluator.evaluate(this, i, degree - 2, t) - 2 * CurveEvaluator.evaluate(this, i + 1, degree - 2, t) + CurveEvaluator.evaluate(this, i + 2, degree - 2, t)) * (degree - 1) * degree;

      return sum;

   }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the second derivative of this B-spline curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the second derivative of this B-spline curve from the floating point argument.
    */

   public float secondDerivativeMapY(float t) {

      float sum = 0.0f;

      for(int i = 0; i < controlPointArray.length / SPACE_POINT_STRIDE ; i++)
         sum += controlPointArray[SPACE_POINT_STRIDE  * i + 1] * (CurveEvaluator.evaluate(this, i, degree - 2, t) - 2 * CurveEvaluator.evaluate(this, i + 1, degree - 2, t) + CurveEvaluator.evaluate(this, i + 2, degree - 2, t)) * (degree - 1) * degree;

      return sum;

   }

   /**
    * Calculates and returns the z-coordinate of the point mapped by the second derivative of this B-spline curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point mapped by the second derivative of this B-spline curve from the floating point argument.
    */

   public float secondDerivativeMapZ(float t) {

      float sum = 0.0f;

      for(int i = 0; i < controlPointArray.length / SPACE_POINT_STRIDE ; i++)
         sum += controlPointArray[SPACE_POINT_STRIDE  * i + 2] * (CurveEvaluator.evaluate(this, i, degree - 2, t) - 2 * CurveEvaluator.evaluate(this, i + 1, degree - 2, t) + CurveEvaluator.evaluate(this, i + 2, degree - 2, t)) * (degree - 1) * degree;

      return sum;

   }

   /**
    * Calculates and returns the x-coordinate of the point mapped by the third derivative of this B-spline curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point mapped by the third derivative of this B-spline curve from the floating point argument.
    */

   public float thirdDerivativeMapX(float t) {

      float sum = 0.0f;

      for(int i = 0; i < controlPointArray.length / SPACE_POINT_STRIDE ; i++)
         sum += controlPointArray[SPACE_POINT_STRIDE  * i + 0] * (CurveEvaluator.evaluate(this, i, degree - 3, t) - 3 * CurveEvaluator.evaluate(this, i + 1, degree - 3, t) + 3 * CurveEvaluator.evaluate(this, i + 2, degree - 3, t) - CurveEvaluator.evaluate(this, i + 3, degree - 3, t)) * (degree - 2) * (degree - 1) * degree;

      return sum;

   }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the third derivative of this B-spline curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the third derivative of this B-spline curve from the floating point argument.
    */

   public float thirdDerivativeMapY(float t) {

      float sum = 0.0f;

      for(int i = 0; i < controlPointArray.length / SPACE_POINT_STRIDE ; i++)
         sum += controlPointArray[SPACE_POINT_STRIDE  * i + 1] * (CurveEvaluator.evaluate(this, i, degree - 3, t) - 3 * CurveEvaluator.evaluate(this, i + 1, degree - 3, t) + 3 * CurveEvaluator.evaluate(this, i + 2, degree - 3, t) - CurveEvaluator.evaluate(this, i + 3, degree - 3, t)) * (degree - 2) * (degree - 1) * degree;

      return sum;

   }

   /**
    * Calculates and returns the z-coordinate of the point mapped by the third derivative of this B-spline curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point mapped by the third derivative of this B-spline curve from the floating point argument.
    */

   public float thirdDerivativeMapZ(float t) {

      float sum = 0.0f;

      for(int i = 0; i < controlPointArray.length / SPACE_POINT_STRIDE ; i++)
         sum += controlPointArray[SPACE_POINT_STRIDE  * i + 2] * (CurveEvaluator.evaluate(this, i, degree - 3, t) - 3 * CurveEvaluator.evaluate(this, i + 1, degree - 3, t) + 3 * CurveEvaluator.evaluate(this, i + 2, degree - 3, t) - CurveEvaluator.evaluate(this, i + 3, degree - 3, t)) * (degree - 2) * (degree - 1) * degree;

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
    * @param z the value that the z-coordinate of the control point of this B-spline curve indexed by the integer argument is set to.
    * @param index the index of the control point in the control point array that is set to the point determined by the floating point argument.
    * @return the index of the position immediately following the control point set in the control point array of this B-spline curve.
    */

   public int setControlPoint(float x, float y, float z, int index) {

	  controlPointArray[index++] = x;
	  controlPointArray[index++] = y;
	  controlPointArray[index++] = z;

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


