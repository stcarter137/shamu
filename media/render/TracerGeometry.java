package shamu.media.render;

/** Abstract class representing a data structure for geometry data of a 3D object implemented specifically for a ray tracer
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

abstract public class TracerGeometry extends Geometry {

  protected float curveParam = 0;

  /**
   * Constructs a default  tracer geometry.
   */

  protected TracerGeometry() {}

  protected float nearest(java.util.Vector<Double> reals) throws NullIntersectionException {

        if(reals.isEmpty())
           throw new NullIntersectionException();

        double nearestIntersection = ((Double)reals.firstElement()).doubleValue();

	    if(reals.size() > 1) {

            java.util.ListIterator iterator =  reals.listIterator(0);

            while(iterator.hasNext()) {

	            double nextReal  = ((Double)iterator.next()).doubleValue();

                 if(nearestIntersection < 0.0 && nextReal > 0.0)
                    nearestIntersection = nextReal;

                 else if(nextReal > 0.0 && nextReal < nearestIntersection)
                    nearestIntersection = nextReal;

	         }

         }

         if(nearestIntersection < 0.0)
             throw new NullIntersectionException();

	     return (float)nearestIntersection;

     }

   abstract public void normal(float[] normalCoords, float[] posCoords);

   abstract public void texturize(float[] textureCoords, float[] coords);

   public float jacobian(LineHighlight highlight, float[] textureCoords,  float[] posCoords) { return 1; }

}
