package shamu.media.render;

/** Class representing a uniform bezier curve of general degree defined in 2D space by a set of control points.
 *
 *  @see ProfileCurve2D
 *
 *  @author Scott T. Carter
 *  @version 1.4
 */

public class BezierCurve2D extends ProfileCurve2D {

   /**
    * The degree of this Bezier curve.
    */

   protected final int degree;

   /**
    * The array of control points of this Bezier curve.
    */

   protected float[] controlPointArray;

   /**
    * Constructs a default Bezier curvee with the degree specified by the integer arguments having an empty control point array.
    *
    * @param degree the value that the degree of the Bezier curve is set to.
    */

   public BezierCurve2D(int degree) {

	  super(0.0f, 1.0f);

	  this.degree = degree;

	  this.controlPointArray = new float[PLANE_POINT_STRIDE  * (degree + 1)];

   }

   /**
    * Constructs a Bezier curve with the degree specified by the integer argument, having the control point array arrays determined by the array argument.
    *
    * @param degree the value that the degree of this Bezier curve is set to.
    * @param controlPointArray the array that the control point array of this Bezier curve is set to.
    */

   public BezierCurve2D(int degree, float[] controlPointArray) {

      super(0.0f, 1.0f);

      this.degree = degree;

      this.controlPointArray = controlPointArray;

   }

   /**
    * Calculates and returns the x-coordinate of the point on this Bezier curve in (x, y) space mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point on this Bezier curve in (x, y) space mapped from the floating point argument.
    */

   public float mapX(float t) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         sum += controlPointArray[PLANE_POINT_STRIDE  * i + 0] * CurveEvaluator.evaluate(this, i, degree, t);

      return sum;

   }

   /**
    * Calculates and returns the y-coordinate of the point on this Bezier curve in (x, y) space mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point on this Bezier curve in (x, y) space mapped from the floating point argument.
    */

   public float mapY(float t) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         sum += controlPointArray[PLANE_POINT_STRIDE  * i + 1] * CurveEvaluator.evaluate(this, i, degree, t);

      return sum;

   }

   /**
    * Calculates and returns the x-coordinate of the point in (x, y) space mapped by the derivative of this Bezier curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point in (x, y) space mapped by the derivative of this Bezier curve from the floating point argument.
    */

   public float derivativeMapX(float t) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         sum += controlPointArray[PLANE_POINT_STRIDE  * i + 0] * (CurveEvaluator.evaluate(this, i - 1, degree - 1, t) - CurveEvaluator.evaluate(this, i, degree - 1, t)) * degree;

      return sum;

   }

   /**
    * Calculates and returns the y-coordinate of the point in (x, y) space mapped by the derivative of this Bezier curve from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point in (x, y) space mapped by the derivative of this Bezier curve from the floating point argument.
    */

   public float derivativeMapY(float t) {

      float sum = 0.0f;

      for(int i = 0; i <= degree; i++)
         sum += controlPointArray[PLANE_POINT_STRIDE  * i + 1] * (CurveEvaluator.evaluate(this, i - 1, degree - 1, t) - CurveEvaluator.evaluate(this, i, degree - 1, t)) * degree;

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
    * @param index the index of the control point in the control point array that is set to the point determined by the floating point argument.
    * @return the index of the position immediately following the control point set in the control point array of this Bezier curve.
    */

   public void setControlPoint(float x, float y, int index) {

	  controlPointArray[index++] = x;
	  controlPointArray[index++] = y;

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



 //  public int binomialCoefficient(int n, int k) throws Exception {

//	  if(k == 0)
//	     return 1;

//	  else if(n == k)
//	     return 1;

//	  else if(n > k && k > 0)
//	     return binomialCoefficient(n -1, k) + binomialCoefficient(n - 1, k - 1);

//	  throw new Exception();

 //  }

}