package shamu.media.render;

/** Class representing a uniform B-spline curve of general degree defined in 3D space by a set of control points.
 *
 *  @see ProfileCurve3D
 *
 *  @author Scott T. Carter
 *  @version 1.4
 */

public class BezierCurve3D extends ProfileCurve3D {

   /**
    * The degree of this Bezier curve.
    */

   protected final int degree;

   /**
    * The arrayof control points of this Bezier curve.
    */

   protected float[] controlPointArray;

   /**
    * Constructs a default Bezier curve with the degree specified by the integer arguments.
    *
    * @param degree the value that the degree of this Bezier curve is set to.
    */

   public BezierCurve3D(int degree) {

	  super(0.0f, 1.0f);

	  this.degree = degree;

	  this.controlPointArray = new float[SPACE_POINT_STRIDE  * (degree + 1)];

   }

   /**
    * Constructs a Bezier curve with the degree specified by the integer argument, and the control point array determined by the array argument.
    *
    * @param degree the value that the degree of this Bezier curve is set to.
    * @param controlPointArray the array that the control point array of this Bezier curve is set to.
    */

   public BezierCurve3D(int degree, float[] controlPointArray) {

	  super(0, 1.0f);

	  this.degree = degree;

      this.controlPointArray = controlPointArray;

   }

   /**
    * Calculates and returns the x-coordinate of the point on this Bezier curve that is mapped from the
    * floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point on this Bezier curve that is mapped from the parametric floating point argument.
    */

   public float mapX(float t) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         sum += controlPointArray[SPACE_POINT_STRIDE  * i + 0] * CurveEvaluator.evaluate(this, i, degree, t);

      return sum;

   }

   /**
    * Calculates and returns the y-coordinate of the point on this Bezier curve that is mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point on this Bezier curve that is mapped from the parametric floating point argument.
    */

   public float mapY(float t) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         sum += controlPointArray[SPACE_POINT_STRIDE  * i + 1] * CurveEvaluator.evaluate(this, i, degree, t);

      return sum;

   }

   /**
    * Calculates and returns the z-coordinate of the point on this Bezier curve that is mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point on this Bezier curve that is mapped from the parametric floating point argument.
    */

   public float mapZ(float t) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         sum += controlPointArray[SPACE_POINT_STRIDE  * i + 2] * CurveEvaluator.evaluate(this, i, degree, t);

      return sum;

   }

   /**
    * Calculates and returns the x-coordinate of the point mapped by the derivative of this Bezier curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point mapped by the derivative of this Bezier curve from the floating point argument.
    */

   public float derivativeMapX(float t) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         sum += controlPointArray[SPACE_POINT_STRIDE  * i + 0] * (CurveEvaluator.evaluate(this, i - 1, degree - 1, t) - CurveEvaluator.evaluate(this, i, degree - 1, t)) * degree;

      return sum;

   }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the derivative of this Bezier curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the derivative of this Bezier curve from the floating point argument.
    */

   public float derivativeMapY(float t) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         sum += controlPointArray[SPACE_POINT_STRIDE  * i + 1] * (CurveEvaluator.evaluate(this, i - 1, degree - 1, t) - CurveEvaluator.evaluate(this, i, degree - 1, t)) * degree;

      return sum;

   }

   /**
    * Calculates and returns the z-coordinate of the point mapped by the derivative of this Bezier curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point mapped by the derivative of this Bezier curve from the floating point argument.
    */

   public float derivativeMapZ(float t) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         sum += controlPointArray[SPACE_POINT_STRIDE  * i + 2] * (CurveEvaluator.evaluate(this, i - 1, degree - 1, t) - CurveEvaluator.evaluate(this, i, degree - 1, t)) * degree;

      return sum;

   }

   /**
    * Calculates and returns the x-coordinate of the point mapped by the second derivative of this Bezier curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point mapped by the second derivative of this Bezier curve from the floating point argument.
    */

   public float secondDerivativeMapX(float t) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         sum += controlPointArray[SPACE_POINT_STRIDE  * i + 0] * (CurveEvaluator.evaluate(this, i - 2, degree - 2, t) - 2 * CurveEvaluator.evaluate(this, i - 1, degree - 2, t) + CurveEvaluator.evaluate(this, i, degree - 2, t)) * (degree - 1) * degree;

      return sum;

   }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the second derivative of this Bezier curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the second derivative of this Bezier curve from the floating point argument.
    */

   public float secondDerivativeMapY(float t) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         sum += controlPointArray[SPACE_POINT_STRIDE  * i + 1] * (CurveEvaluator.evaluate(this, i - 2, degree - 2, t) - 2 * CurveEvaluator.evaluate(this, i - 1, degree - 2, t) + CurveEvaluator.evaluate(this, i, degree - 2, t)) * (degree - 1) * degree;

      return sum;

   }

   /**
    * Calculates and returns the z-coordinate of the point mapped by the second derivative of this Bezier curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point mapped by the second derivative of this Bezier curve from the floating point argument.
    */

   public float secondDerivativeMapZ(float t) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         sum += controlPointArray[SPACE_POINT_STRIDE  * i + 2] * (CurveEvaluator.evaluate(this, i - 2, degree - 2, t) - 2 * CurveEvaluator.evaluate(this, i - 1, degree - 2, t) + CurveEvaluator.evaluate(this, i, degree - 2, t)) * (degree - 1) * degree;

      return sum;

   }

   /**
    * Calculates and returns the x-coordinate of the point mapped by the third derivative of this Bezier curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point mapped by the third derivative of this Bezier curve from the floating point argument.
    */

   public float thirdDerivativeMapX(float t) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         sum += controlPointArray[SPACE_POINT_STRIDE  * i + 0] * (CurveEvaluator.evaluate(this, i - 3, degree - 3, t) - 3 * CurveEvaluator.evaluate(this, i - 2, degree - 3, t) + 3 * CurveEvaluator.evaluate(this, i - 1, degree - 3, t) - CurveEvaluator.evaluate(this, i, degree - 3, t)) * (degree - 2) * (degree - 1) * degree;

      return sum;

   }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the third derivative of this Bezier curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the third derivative of this Bezier curve from the floating point argument.
    */

   public float thirdDerivativeMapY(float t) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         sum += controlPointArray[SPACE_POINT_STRIDE  * i + 1] * (CurveEvaluator.evaluate(this, i - 3, degree - 3, t) - 3 * CurveEvaluator.evaluate(this, i - 2, degree - 3, t) + 3 * CurveEvaluator.evaluate(this, i - 1, degree - 3, t) - CurveEvaluator.evaluate(this, i, degree - 3, t)) * (degree - 2) * (degree - 1) * degree;

      return sum;

   }

   /**
    * Calculates and returns the z-coordinate of the point mapped by the third derivative of this Bezier curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point mapped by the third derivative of this Bezier curve from the floating point argument.
    */

   public float thirdDerivativeMapZ(float t) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         sum += controlPointArray[SPACE_POINT_STRIDE  * i + 2] * (CurveEvaluator.evaluate(this, i - 3, degree - 3, t) - 3 * CurveEvaluator.evaluate(this, i - 2, degree - 3, t) + 3 * CurveEvaluator.evaluate(this, i - 1, degree - 3, t) - CurveEvaluator.evaluate(this, i, degree - 3, t)) * (degree - 2) * (degree - 1) * degree;

      return sum;

   }

   /**
    * Retrieves the address of the control point array of this Bezier curve.
    *
    * @param controlPointsAddressArray the array of arrays to store the control point array of this Bezier curve.
    */

   public void getControlPointsAddress(float[][] controlPointsAddressArray) { controlPointsAddressArray[0] = controlPointArray; }

   /**
    * Sets the control point of this Bezier curve indexed by the integer argument to the point determined by the floating point arguments.
    *
    * @param x the value that the x-coordinate of the control point of this Bezier curve indexed by the integer argument is set to.
    * @param y the value that the y-coordinate of the control point of this Bezier curve indexed by the integer argument is set to.
    * @param z the value that the z-coordinate of the control point of this Bezier curve indexed by the integer argument is set to.
    * @param index the index of the control point in the control point array that is set to the point determined by the floating point argument.
    * @return the index of the position immediately following the control point set in the control point array of this Bezier curve.
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


