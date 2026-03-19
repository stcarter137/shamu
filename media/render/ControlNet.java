package shamu.media.render;

/** Class representing the data structure of a control point graph for procedural subdivision surface modeling.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class ControlNet {

   /**
    * Number of array elements per control point in the control point array of this control net.
    */

   public static final int COORDINATE_STRIDE = 3;

   /**
    * Constant used to contruct a control net of triangles.
    */

   public static final int TRIANGLES = 0x111111;

   /**
    * Constant used to contruct a control net of quadrilaterals.
    */

   public static final int QUADRILATERALS = 0x222222;

   /**
    * Array of points of this control net.
    */

   protected float[] controlPointArray;

   /**
    * Array of face indices of this control net.
    */

   protected int[] controlIndexArray;

   /**
    * Index of the element in the control point array one element past the last point added to the array.
    */

   protected int controlPointArrayStop;

   /**
    * Index of the element in the control index array one element past the last face added to the array.
    */

   protected int controlIndexArrayStop;

   /**
    * Constructs a control net having the number of control points, the number of control faces, and net primitive type specified by the integer arguments.
    *
    * @param numPoints the number of control points in this control net.
    * @param numFaces the number of control faces in this control net.
    * @param primitiveType an integer specifying whether the underlying data structure of this control net is triangle based or quadrilateral based. Must be either TRIANGLES or QUADRILATERALS.
    */

   public ControlNet(int numPoints, int numFaces, int primitiveType) {

	  super();

	  controlPointArray = new float[3 * numPoints];

	  if(primitiveType == TRIANGLES)
	     controlIndexArray = new int[3 * numFaces];

	  else if(primitiveType == QUADRILATERALS)
	     controlIndexArray = new int[4 * numFaces];

   }

   /**
    * Adds the coordinates specified by the floating point argument to the underlying data structure of this control net.
    *
    * @param x the x-coordinate of the vertex added to the underlying data structure of this control net.
    * @param y the y-coordinate of the vertex added to the underlying data structure of this control net.
    * @param z the z-coordinate of the vertex added to the underlying data structure of this control net.
    */

   public void addControlPoint(float x, float y, float z) {

	  controlPointArray[controlPointArrayStop++] = x;
	  controlPointArray[controlPointArrayStop++] = y;
	  controlPointArray[controlPointArrayStop++] = z;

   }

   /**
    * Sets a control point at the position specified by the integer argument in the underlying data structure of this control net to the coordinates specified by the floating point arguments and returns the index of the next position in the control point array
    * of this control net.
    *
    * @param x the value that the x-coordinate of the indexed control point in the underlying data structure of this control net is set to.
    * @param y the value that the y-coordinate of the indexed control point in the underlying data structure of this control net is set to.
    * @param z the value that the z-coordinate of the indexed control point in the underlying data structure of this control net is set to.
    * @param index the index referring to the position in the control point array whose coordinates are set.
    * @return the index of the position immediately following the control point set in the underlying data structure of this control net.
    */

   public int setControlPoint(float x, float y, float z, int index) {

      controlPointArray[index++] = x;
      controlPointArray[index++] = y;
      controlPointArray[index++] = z;

      return index;

   }

   /**
    * Retrieves the control point coordinates of the control point specified by the integer argument in the underlying data structure of this control net and places the
    * coordinates at the address specified by the floating point array argument.
    *
    * @param controlPoint the address at which the control point coordinates are stored.
    * @param index the index of the control point in the underlying data structure of this control net whose coordinates are retrieved.
    */

   public void getControlPoint(float[] controlPoint, int index) {

      controlPoint[0] = controlPointArray[index++];
      controlPoint[1] = controlPointArray[index++];
      controlPoint[2] = controlPointArray[index++];

   }

   /**
    * Removes the control point specified by the integer argument from this control net.
    *
    * @param index the index of the control point array specifying the control point to be removed from this control net.
    * @param controlPoint the array to store the retrieved control point coordinates.
    */

   public void removeControlPoint(int index, float[] controlPoint) {

      controlPoint[0] = controlPointArray[index + 0];
      controlPoint[1] = controlPointArray[index + 1];
      controlPoint[2] = controlPointArray[index + 2];

      System.arraycopy(controlPointArray, index + COORDINATE_STRIDE, controlPointArray, index, controlPointArrayStop - index);

      controlPointArrayStop -= COORDINATE_STRIDE;

   }

   /**
    * Adds the control indices specified by the integer array argument to the underlying data structure of this control net.
    *
    * @param indices the indices added to the underlying data structure of this control net.
    */

   public void addControlIndices(int[] indices) {

	  for(int i = 0; i < indices.length; i++)
	     controlIndexArray[controlIndexArrayStop++] = indices[i];

   }

   /**
    * Sets a face at the position specified by the integer argument in the underlying data structure of this control net to the indices specified by the integer array argument and returns the index of the next position in the control indices array
    * of this control net.
    *
    * @param indices the indices that the face in the underlying data structure of this control net are set to.
    * @param index the index referring to the position in the control indices array whose indices are set.
    * @return the index of the position immediately following the indices set in the underlying data structure of this control net.
    */

   public int setControlIndices(int[] indices, int index) {

      for(int i = 0; i < indices.length; i++)
         controlIndexArray[index++] = indices[i];

      return index;

   }

   /**
    * Retrieves the indices of the face specified by the integer argument in the underlying data structure of this control net and places the
    * indices at the address specified by the integer array argument.
    *
    * @param indices the address at which the indices are stored.
    * @param index the index of the face in the underlying data structure of this control net whose indices are retrieved.
    */

   public void getControlIndices(int[] indices, int index) {

      for(int i = 0; i < indices.length; i++)
	     indices[i] = controlIndexArray[index++];

   }

   /**
    * Removes the face specified by the integer argument from this control net.
    *
    * @param index the index of the control indices array specifying the face to be removed from this control net.
    * @param controlPoint the array to store the retrieved face indices.
    */

   public void removeControlIndices(int index, int[] indices) {

      for(int i = 0; i < indices.length; i++)
         indices[i] = controlIndexArray[i];

      System.arraycopy(controlIndexArray, index + indices.length, controlIndexArray, index, controlIndexArrayStop - index);

      controlIndexArrayStop -= indices.length;

   }

   /**
    * Returns the index of the element in the control point array one element past the last control point in this control net.
    *
    * @return the index of the element in the contorl point array one element past the last control point in this control net.
    */

   public int getControlPointArrayStop() { return controlPointArrayStop; }

   /**
    * Returns the index of the element in the control indices array one element past the last control point in this control net.
    *
    * @return the index of the element in the contorl indices array one element past the last face in this control net.
    */

   public int getControlIndexArrayStop() { return controlIndexArrayStop; }

   /**
    * Retrieves the address of the control point array associated with this control net.
    *
    * @param controlPointsAddressArray the array of arrays to store the retrieved control point array.
    */

   public void getControlPointsAddress(float[][] controlPointsAddressArray) { controlPointsAddressArray[0] = controlPointArray; }

   /**
    * Retrieves the address of the control point indices associated with this control net.
    *
    * @param controlIndicesAddressArray the array of arrays to store the retrieved control indices array.
    */

   public void getControlIndicesAddress(int[][] controlIndicesAddressArray) { controlIndicesAddressArray[0] = controlIndexArray; }

}