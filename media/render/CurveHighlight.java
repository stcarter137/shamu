package shamu.media.render;

/** Abstract class representing a mathematical curve in 2D space to map onto a ray traced surface.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

abstract public class CurveHighlight implements ModelingConstantsI  {

   protected TracerGeometry metricSpace;

   /**
    * Constructs a default curve highlight.
    */

   protected CurveHighlight(TracerGeometry metricSpace) { this.metricSpace = metricSpace; }

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

   public String compute() {return "Meet George Jetson"; }

}