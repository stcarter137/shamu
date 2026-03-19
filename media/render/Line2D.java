package shamu.media.render;

/** Class representing a line in 2D space parameterized as t[x(t), y(t)] = [x1 + t * (x2 - x1), y1 + t * (y2 - y1)].
 *
 *  @see ProfileCurve2D
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class Line2D extends ProfileCurve2D {

   /**
    * The x1 parameter of this line.
    */

   protected float coordinateX1;

   /**
    * The x2 parameter of this line.
    */

   protected float coordinateX2;

   /**
    * The y1 parameter of this line.
    */

   protected float coordinateY1;

   /**
    * The y2 parameter of this line.
    */

   protected float coordinateY2;

   /**
    * Constructs a default line.
    */

   public Line2D() { super(); }

   /**
    * Constructs a line having the x1 parameter, x2 parameter, y1 parameter, y2 parameter, and boundary determined by the floating point arguments.
    *
    * @param coordinateX1 the value that the x1 parameter of this line is set to.
    * @param coordinateX2 the value that the x2 parameter of this line is set to.
    * @param coordinateY1 the value that the y1 parameter of this line is set to.
    * @param coordinateY2 the value that the y1 parameter of this line is set to.
    * @param lowerBoundsT the value that the parametric variable lower bound that determines the boundary of this line is set to.
    * @param upperBoundsT the value that the parametric variable upper bound that determines the boundary of this line is set to.
    */

   public Line2D(float coordinateX1, float coordinateX2, float coordinateY1, float coordinateY2, float lowerBoundsT, float upperBoundsT) {

	  super(lowerBoundsT, upperBoundsT);

	  this.coordinateX1 = coordinateX1;
	  this.coordinateX2 = coordinateX2;

	  this.coordinateY1 = coordinateY1;
	  this.coordinateY2 = coordinateY2;

   }

   /**
    * Calculates and returns the x-coordinate of the point on this line in (x, y) space mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point on this line in (x, y) space mapped from the floating point argument.
    */

   public float mapX(float t) { return coordinateX1 + t * (coordinateX2 - coordinateX1); }

   /**
    * Calculates and returns the y-coordinate of the point on this line in (x, y) space mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point on this line in (x, y) space mapped from the floating point argument.
    */

   public float mapY(float t) { return coordinateY1 + t * (coordinateY2 - coordinateY1); }

   /**
    * Calculates and returns the x-coordinate of the point in (x, y) space mapped by the derivative of this line from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point in (x, y) space mapped by the derivative of this line from the floating point argument.
    */

   public float derivativeMapX(float t) { return coordinateX2 - coordinateX1; }

   /**
    * Calculates and returns the y-coordinate of the point in (x, y) space mapped by the derivative of this line from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point in (x, y) space mapped by the derivative of this line from the floating point argument.
    */

   public float derivativeMapY(float t) { return coordinateY2 - coordinateY1; }

   /**
    * Returns the x1 parameter associated with this line.
    *
    * @return the x1 parameter associated with this line.
    */

   public float getCoordinateX1() { return coordinateX1; }

   /**
    * Returns the x2 parameter associated with this line.
    *
    * @return the x2 parameter associated with this line.
    */

   public float getCoordinateX2() { return coordinateX2; }

   /**
    * Returns the y1 parameter associated with this line.
    *
    * @return the y1 parameter associated with this line.
    */

   public float getCoordinateY1() { return coordinateY1; }

   /**
    * Returns the y2 parameter associated with this line.
    *
    * @return the y2 parameter associated with this line.
    */

   public float getCoordinateY2() { return coordinateY2; }

   /**
    * Sets the value of the x1 parameter associated with this line to the floating point argument.
    *
    * @param coordinateX1 the value that the x1 parameter associated with this line is set to.
    */

   public void setCoordinateX1(float coordinateX1) { this.coordinateX1 = coordinateX1; }

   /**
    * Sets the value of the x2 parameter associated with this line to the floating point argument.
    *
    * @param coordinateX2 the value that the x2 parameter associated with this line is set to.
    */

   public void setCoordinateX2(float coordinateX2) { this.coordinateX2 = coordinateX2; }

   /**
    * Sets the value of the y1 parameter associated with this line to the floating point argument.
    *
    * @param coordinateY1 the value that the y1 parameter associated with this line is set to.
    */

   public void setCoordinateY1(float coordinateY1) { this.coordinateY1 = coordinateY1; }

   /**
    * Sets the value of the y2 parameter associated with this line to the floating point argument.
    *
    * @param coordinateY2 the value that the y2 parameter associated with this line is set to.
    */

   public void setCoordinateY2(float coordinateY2) { this.coordinateY2 = coordinateY2; }

}
