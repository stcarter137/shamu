package shamu.media.render;

/** Geometry class that subclasses the Mesh class representing the underlying data structure of subdivision surface model, which consists of a triangle or quadrilateral mesh, whose faces are recursively subdivided
 *  to create a refined mesh of triangular or quadrilateral faces.
 *
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class SubdivisionMesh extends Mesh {

   /**
    * Default capacity of the underlying edge array data structures of this subdivision mesh.
    */

   public static final int DEFAULT_EDGE_CAPACITY = 640000;

   /**
    * The array of updated existing vertices stored through each subdivision scheme iteration.
    */

   protected float[] updatedVertexArray = null;

   /**
    * Edge array data structure of this subdivision mesh such that every two elements maps an edge to vertices in the corresponding vertex
    * array of this subdivision mesh.
    */

   protected int[] indexedEdgeVerticesArray = new int[DEFAULT_EDGE_CAPACITY];

   /**
    * Edge array data structure of this subdivision mesh such that every two elements maps an edge to adjacent faces in the corresponding face array
    * of this subdivision mesh.
    */

   protected int[] indexedEdgeFacesArray = new int[DEFAULT_EDGE_CAPACITY];

   /**
    * Face array data structure of this subdivision mesh such that every n elements maps a face to edges in the corresponding edge array
    * data structures of this subdivision mesh.
    */

   protected int[] indexedFaceEdgesArray = new int[DEFAULT_FACE_CAPACITY];

   /**
    * The array of face center vertices stored through each subdivision scheme iteration.
    */

   protected float[] faceCenterVertexArray = null;

   /**
    * The index of the element in the updated vertex array of this subdivision mesh one element past the last added updated vertex.
    */

   protected int updatedVertexArrayStop;

   /**
    * The index of the element one element past the last edge in the data structure mapping edges to vertices of this subdivision mesh.
    */

   protected int indexedEdgeVerticesArrayStop = 0;

   /**
    * The index of the element one element past the last edge in the data structure mapping edges to faces of this subdivision mesh.
    */

   protected int indexedEdgeFacesArrayStop = 0;

   /**
    * The index of the element one element past the last edge in the data structure mapping faces to edges of this subdivision mesh.
    */

   protected int indexedFaceEdgesArrayStop = 0;

   /**
    * The index of the element in the face center vertex array of this subdivision mesh one element past the last added face center vertex.
    */

   protected int faceCenterVertexArrayStop;

   /**
    * Constructs a subdivision mesh with the default vertex array, vertex normal array, vertex texel array, vertex color array, edge array, and face array
    * capacities with the polygonal mesh type specified by the integer arguments.
    *
    * @param meshType an integer specifying whether the underlying base mesh of this geometry model is triangle based or quadrilateral based. Must be either _TRIANGLE_MESH or _QUADRILATERAL_MESH.
    */

   protected SubdivisionMesh(int meshType) { super(meshType); }

   /**
    * Adds the face indices specified by the integer array argument to the underlying data structure of this subdivision mesh,
    * maintaining the format of the indexed edge and indexed face array data structures of this subdivision mesh.
    *
    * @param indices the references to the face vertices added to the underlying data structure of this subdivision mesh.
    */

   public void addFace(int[] indices) {

      handleCapacityOverload(indexedFaceVerticesArray);

      for(int i = 0; i < meshPrimitiveSize; i++)
	     indexedFaceVerticesArray[indexedFaceVerticesArrayStop++] = indices[i];

      for(int j = 0; j < meshPrimitiveSize; j++) {

         int index_1 = indices[(j + 0) % meshPrimitiveSize];
         int index_2 = indices[(j + 1) % meshPrimitiveSize];

         int iteratorIndex = 0;

	     while(iteratorIndex < indexedEdgeVerticesArrayStop) {

	        if(index_1 == indexedEdgeVerticesArray[iteratorIndex] && index_2 == indexedEdgeVerticesArray[iteratorIndex + 1])
		   	   break;

	        else if(index_2 == indexedEdgeVerticesArray[iteratorIndex] && index_1 == indexedEdgeVerticesArray[iteratorIndex + 1])
			   break;

	        else
		       iteratorIndex += 2;

	     }

         if(iteratorIndex == indexedEdgeVerticesArrayStop) {

		    indexedEdgeVerticesArray[indexedEdgeVerticesArrayStop++] = index_1;
		    indexedEdgeVerticesArray[indexedEdgeVerticesArrayStop++] = index_2;

		    indexedEdgeFacesArray[indexedEdgeFacesArrayStop++] = indexedFaceVerticesArrayStop / meshPrimitiveSize - 1;
		    indexedEdgeFacesArray[indexedEdgeFacesArrayStop++] = -1;

	     }

         else {

		    indexedEdgeFacesArray[iteratorIndex + 1] = indexedEdgeFacesArray[iteratorIndex + 0];
		    indexedEdgeFacesArray[iteratorIndex + 0] = indexedFaceVerticesArrayStop / meshPrimitiveSize - 1;

         }

         indexedFaceEdgesArray[indexedFaceEdgesArrayStop++] = iteratorIndex / 2;

      }

   }

   /**
    * Adds the face indices specified by the integer array argument to the underlying data structure of this subdivision mesh,
    * maintaining the format of the indexed edge and indexed face array data structures of this subdivision mesh.
    *
    * @param indices the array containing the indices of the faces added to the underlying data structure of this subdivision mesh.
    * @param n the number of elements in the array argument added to the underlying data structure of this subdivision mesh.
    *
    */

   public void addFaces(int[] indices) {

      int[] next = new int[4];

      for(int i = 0; i < indices.length; i += meshPrimitiveSize) {

   	     next[0] = indices[(i + 0) % indices.length];
	     next[1] = indices[(i + 1) % indices.length];
	     next[2] = indices[(i + 2) % indices.length];
	     next[3] = indices[(i + 3) % indices.length];

         addFace(next);

      }

   }

   /**
    * Returns the capacity of the updated vertex array in the underlying data structure of this subdivision mesh.
    *
    * @return the capacity of the updated vertex array in the underlying data structure of this subdivision mesh.
    */

   public int getUpdatedVertexArrayCapacity() { return updatedVertexArray.length; }

   /**
    * Returns the capacity of the indexed edge vertices array in the underlying data structure of this subdivision mesh.
    *
    * @return the capacity of the indexed edge vertices array in the underlying data structure of this subdivision mesh.
    */

   public int getIndexedEdgeVerticesArrayCapacity() { return indexedEdgeVerticesArray.length; }

   /**
    * Returns the capacity of the indexed edge faces array in the underlying data structure of this subdivision mesh.
    *
    * @return the capacity of the indexed edge faces array in the underlying data structure of this subdivision mesh.
    */

   public int getIndexedEdgeFacesArrayCapacity() { return indexedEdgeFacesArray.length; }

   /**
    * Returns the capacity of the indexed face edges array in the underlying data structure of this subdivision mesh.
    *
    * @return the capacity of the indexed face edges array in the underlying data structure of this subdivision mesh.
    */

   public int getIndexedFaceEdgesArrayCapacity() { return indexedFaceEdgesArray.length; }

   /**
    * Returns the index of the element in the updated vertex array of this subdivision mesh one element past the last updated vertex.
    *
    * @return the index of the element in the updated vertex array of this subdivision mesh one element past the last updated vertex.
    */

   public int getUpdatedVertexArrayStop() { return updatedVertexArrayStop; }

   /**
    * Returns the index of the element in the indexed edge vertices array one element past the last edge in the indexed edge vertices array of this subdivision mesh.
    *
    * @return the index of the element in the indexed edge vertices array one element past the last edge in the indexed edge vertices array of this subdivision mesh.
    */

   public int getIndexedEdgeVerticesArrayStop() { return indexedEdgeVerticesArrayStop; }

   /**
    * Returns the index of the element in the indexed edge faces array one element past the last edge in the indexed edge faces array of this subdivision mesh.
    *
    * @return the index of the element in the indexed edge faces array one element past the last edge in the indexed edge faces array of this subdivision mesh.
    */

   public int getIndexedEdgeFacesArrayStop() { return indexedEdgeFacesArrayStop; }

   /**
    * Returns the index of the element in the indexed face edges array one element past the last face in the indexed face edges array of this subdivision mesh.
    *
    * @return the index of the element in the indexed face edges array one element past the last face in the indexed face edges array of this subdivision mesh.
    */

   public int getIndexedFaceEdgesArrayStop() { return indexedFaceEdgesArrayStop; }

   /**
    * Returns the index of the element in the face center vertex array one element past the last face center vertex of this subdivision mesh.
    *
    * @return the index of the element in the face center vertex array one element past the last face center vertex of this subdivision mesh.
    */

   public int getFaceCenterVertexArrayStop() { return faceCenterVertexArrayStop; }

   /**
    * Retrieves the address of the indexed edge vertices array of this subdivision mesh.
    *
    * @param indexedEdgeVerticesAddressArray the array of arrays to store the indexed edge vertices array.
    */

   public void getIndexedEdgeVerticesAddress(int[][] indexedEdgeVerticesAddressArray) { indexedEdgeVerticesAddressArray[0] = indexedEdgeVerticesArray; }

   /**
    * Retrieves the address of the indexed edge faces array of this subdivision mesh.
    *
    * @param indexedEdgeFacesAddressArray the array of arrays to store the indexed edge faces array.
    */

   public void getIndexedEdgeFacesAddress(int[][] indexedEdgeFacesAddressArray) { indexedEdgeFacesAddressArray[0] = indexedEdgeFacesArray; }

   /**
    * Retrieves the address of the updated vertex array of this subdivision mesh.
    *
    * @param updatedVerticesAddressArray the array of arrays to store the updated vertex array.
    */

   public void getUpdatedVerticesAddress(float[][] updatedVerticesAddressArray) { updatedVerticesAddressArray[0] = updatedVertexArray; }

   /**
    * Retrieves the address of the indexed face edges array of this subdivision mesh.
    *
    * @param indexedFaceEdgesAddressArray the array of arrays to store the indexed face edges array.
    */

   public void getIndexedFaceEdgesAddress(int[][] indexedFaceEdgesAddressArray) { indexedFaceEdgesAddressArray[0] = indexedFaceEdgesArray; }

   /**
    * Retrieves the address of the face center vertex array of this subdivision mesh.
    *
    * @param faceCenterVerticesAddressArray the array of arrays to store the face center vertex array.
    */

   public void getFaceCenterVerticesAddress(float[][] faceCenterVerticesAddressArray) { faceCenterVerticesAddressArray[0] = faceCenterVertexArray; }


}