package shamu.media.render;

/** Class representing a matrix "shader" in the 3D rendering pipeline that transforms an input vertex in object coordinates to homogeneous screen
 *  space coordinates. Each instance contains four output registers that will store the output homgeneous coordinates for an input vertex.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class TransformationShader {

   /**
    * Field that will store the output x-coordinate in homogeneous screen space coordinates of a vertex processed by this transformation shader.
    */

   protected float outputX;

   /**
    * Field that will store the output y-coordinate in homogeneous screen space coordinates of a vertex processed by this transformation shader.
    */

   protected float outputY;

   /**
    * Field that will store the output z-coordinate in homogeneous screen space coordinates of a vertex processed by this transformation shader.
    */

   protected float outputZ;

   /**
    * Field that will store the output w-coordinate in homogeneous screen space coordinates of a vertex processed by this transformation shader.
    */

   protected float outputW;

   /**
    * Matrix used for transforming input vertices from object space coordinates to homogeneous screen space coordinates .
    */

   protected Matrix4D objectToScreenMatrix = null;

   /**
    * Receptor that may be referenced when vertex processing is performed by this transformation shader.
    */

   protected VertexReceptor receptor;

   /**
    * Constructs a transformation shader associated with the receptor object argument.
    *
    * @param receptor the receptor associated with this transformation shader.
    */

   protected TransformationShader(VertexReceptor receptor) { this.receptor = receptor; }

   /**
    * Updates this transformation shader with new data corresponding to the notifier argument.
    *
    * @param notifier the notifier containing geometric and rendering data of the current model being rendered.
    */

   protected void update(RenderNotifier notifier) { receptor.initializeTransformationShaderMatrix(notifier); }

   /**
    * Sets the vertex determined by the floating point arguments, transforming the vertex to screen space coordinates and storing
    * the output coordinates in the output fields of this transformation shader.
    *
    * @param x the object space x-coordinate of the vertex being processed.
    * @param y the object space y-coordinate of the vertex being processed.
    * @param z the object space z-coordinate of the vertex being processed.
    */

   protected void set(float x, float y, float z) {

      outputX = objectToScreenMatrix.getElement(0, 0) * x + objectToScreenMatrix.getElement(0, 1) * y + objectToScreenMatrix.getElement(0, 2) * z + objectToScreenMatrix.getElement(0, 3);
      outputY = objectToScreenMatrix.getElement(1, 0) * x + objectToScreenMatrix.getElement(1, 1) * y + objectToScreenMatrix.getElement(1, 2) * z + objectToScreenMatrix.getElement(1, 3);
      outputZ = objectToScreenMatrix.getElement(2, 0) * x + objectToScreenMatrix.getElement(2, 1) * y + objectToScreenMatrix.getElement(2, 2) * z + objectToScreenMatrix.getElement(2, 3);
      outputW = objectToScreenMatrix.getElement(3, 0) * x + objectToScreenMatrix.getElement(3, 1) * y + objectToScreenMatrix.getElement(3, 2) * z + objectToScreenMatrix.getElement(3, 3);

   }

   /**
    * Sets the matrix transforming input vertices from object space coordinates to homogeneous screen space coordinates, to the matrix argument.
    *
    * @param objectToScreenMatrix the matrix set as the matrix transforming input vertices from object space coordinates to homogeneous screen space coordinates.
    */

   protected void setObjectToScreenMatrix(Matrix4D objectToScreenMatrix) { this.objectToScreenMatrix = objectToScreenMatrix; }

   /**
    * Returns the output x-coordinate in homogeneous screen space coordinates of the last vertex processed by this transformation shader.
    *
    * @return the output x-coordinate in homogeneous screen space coordinates of the last vertex processed by this transformation shader.
    */

   public float getOutputX() { return outputX; }

   /**
    * Returns the output y-coordinate in homogeneous screen space coordinates of the last vertex processed by this transformation shader.
    *
    * @return the output y-coordinate in homogeneous screen space coordinates of the last vertex processed by this transformation shader.
    */

   public float getOutputY() { return outputY; }

   /**
    * Returns the output z-coordinate in homogeneous screen space coordinates of the last vertex processed by this transformation shader.
    *
    * @return the output z-coordinate in homogeneous screen space coordinates of the last vertex processed by this transformation shader.
    */

   public float getOutputZ() { return outputZ; }

   /**
    * Returns the output w-coordinate in homogeneous screen space coordinates of the last vertex processed by this transformation shader.
    *
    * @return the output w-coordinate in homogeneous screen space coordinates of the last vertex processed by this transformation shader.
    */

   public float getOutputW() { return outputW; }

}