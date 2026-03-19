package shamu.media.render;

/** Abstract class representing a mathematical curve in 2D space to map onto a ray traced surface.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class LineHighlight extends CurveHighlight {

   protected float x1;

   protected float y1;

   protected float x2;

   protected float y2;

   /**
    * Constructs a default curve highlight.
    */

   public LineHighlight(TracerGeometry metricSpace) { super(metricSpace); }

   public LineHighlight(float x1, float y1, float x2, float y2, TracerGeometry metricSpace) {

       this(metricSpace);

       this.x1= x1;
       this.y1= y1;

       this.x2 = x2;
       this.y2 = y2;

   }

   public float metricSquared(float u, float v) {

        float[] intersectionCoords = new float[2];
        double[] coefficients = new double[4];

         this.intersection(u, v, intersectionCoords);

        return (float)(Math.pow(intersectionCoords[0] - u, 2) + Math.pow(intersectionCoords[1] - v, 2));

   }

   public void intersection(float x, float y, float[] intersectionCoordinates) throws NullIntersectionException {

	    float vx = y2 - y1;
	    float vy = x1 - x2;

        float magnitude = (float)Math.sqrt(vx * vx + vy * vy);

        vx /= magnitude;
        vy /= magnitude;

	    float t = (x1 - x) * vx + (y1 - y) * vy;

  /*      float wx1 = x  - x1;
        float wy1 = y - y1;

        float wx2 = x2 - x1;
        float wy2 = y2 - y1;

        float s =  wx1 * wx2 + wy1 * wy2;

        s /= ( wx2 * wx2 + wy2 * wy2);

        if(s < -0.1f || s > 1.1f)
           throw new NullIntersectionException();

        intersectionCoordinates[0] = x1 + wx2 * s;
        intersectionCoordinates[1] = y1 + wy2 * s;
*/

        intersectionCoordinates[0] = x + vx * t;
        intersectionCoordinates[1] = y + vy * t;

    }

   /**
    * Calculates and returns the x-coordinate of the point on this profile curve in (x, y) space mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point on this profile curve in (x, y) space mapped from the floating point argument.
    */

   public float mapX(float t) { return x1 + (x2 - x1) * t; }

   /**
    * Calculates and returns the y-coordinate of the point on this profile curve in (x, y) space mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point on this profile curve in (x, y) space mapped from the floating point argument.
    */

   public float mapY(float t) { return y1 + (y2 - y1) * t; }

   /**
    * Calculates and returns the x-coordinate of the point in (x, y) space mapped by the derivative of the generating curve associated with
    * this generalized helicoid from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point in (x, y) space mapped by the derivative of the generating curve  from the floating point argument.
    */

   public float derivativeMapX(float t) { return x2 - x1; }

   /**
    * Calculates and returns the y-coordinate of the point in (x, y) space mapped by the derivative of the generating curve associated with
    * this generalized helicoid from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point in (x, y) space mapped by the derivative of the generating curve  from the floating point argument.
    */

   public float derivativeMapY(float t) { return y2 - y1; }

}