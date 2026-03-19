package shamu.media.render;

/** Abstract class representing a mathematical curve in 2D space used to generate a generalized helicoid.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

abstract public class ProfileCurve2D implements ModelingConstantsI {

   /**
    * The parametric variable lower bound that determines the boundary of this profile curve.
    */

   protected float lowerBoundsT;

   /**
    * The parametric variable upper bound that determines the boundary of this profile curve.
    */

   protected float upperBoundsT;

   /**
    * Constructs a default 2D profile curve.
    */

   protected ProfileCurve2D() {}

   /**
    * Constructs a 2D profile curve with a boundary defined by the floating point arguments.
    *
    * @param lowerBoundsT the value that the parametric variable lower bound that determines the boundary of this profile curve is set to.
    * @param upperBoundsT the value that the parametric variable upper bound that determines the boundary of this profile curve is set to.
    */

   protected ProfileCurve2D(float lowerBoundsT, float upperBoundsT) {

	  this.lowerBoundsT = lowerBoundsT;
	  this.upperBoundsT = upperBoundsT;

   }

   /**
    * Calculates and returns the x-coordinate of the point on this profile curve in (x, y) space mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point on this profile curve in (x, y) space mapped from the floating point argument.
    */

   abstract public float mapX(float t);

   /**
    * Calculates and returns the y-coordinate of the point on this profile curve in (x, y) space mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point on this profile curve in (x, y) space mapped from the floating point argument.
    */

   abstract public float mapY(float t);

   /**
    * Calculates and returns the x-coordinate of the point in (x, y) space mapped by the derivative of the generating curve associated with
    * this generalized helicoid from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point in (x, y) space mapped by the derivative of the generating curve  from the floating point argument.
    */

   abstract public float derivativeMapX(float t);

   /**
    * Calculates and returns the y-coordinate of the point in (x, y) space mapped by the derivative of the generating curve associated with
    * this generalized helicoid from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point in (x, y) space mapped by the derivative of the generating curve  from the floating point argument.
    */

   abstract public float derivativeMapY(float t);

   /**
    * Returns the parametric variable lower bound that determines the boundary of this profile curve.
    *
    * @return the parametric variable lower bound that determines the boundary of this profile curve.
    */

   public float getLowerBoundsT() { return lowerBoundsT; }

   /**
    * Returns the parametric variable upper bound that determines the boundary of this profile curve.
    *
    * @return the parametric variable upper bound that determines the boundary of this profile curve.
    */

   public float getUpperBoundsT() { return upperBoundsT; }

   /**
    * Sets the parametric variable lower bound to the floating point argument.
    *
    * @param lowerBoundsT the value that the parametric variable lower bound is set to.
    */

   public void setLowerBoundsT(float lowerBoundsT) { this.lowerBoundsT = lowerBoundsT; }

   /**
    * Sets the parametric variable upper bound to the floating point argument.
    *
    * @param upperBoundsT the value that the parametric variable upper bound is set to.
    */

   public void setUpperBoundsT(float upperBoundsT) { this.upperBoundsT = upperBoundsT; }

}