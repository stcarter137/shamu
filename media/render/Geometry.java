package shamu.media.render;

/** Abstract class representing a data structure for geometry data of a 3D object. Subclasses must implement the actual representation of the data.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

abstract public class Geometry {

  /**
   * Constructs a default geometry.
   */

  protected Geometry() {}

  /**
   * Returns the size attribute, which is dependent on subclass implementations, of this geometry.
   *
   * @return the size attribute of this geometry.
   */

  abstract public int getSize();

  abstract public float intersection(float startX, float startY, float startZ, float directionX, float directionY, float directionZ, Matrix4D matrix) throws NullIntersectionException;

}

