package shamu.media.render;

/** <p>Abstract class representing a subdivision procedure that operates on a triangle or quadrilateral mesh, whose faces are recursively subdivided
 *  to create a refined mesh of triangular or quadrilateral faces.</p>
 *
 *  In the subdivision refinement steps, each edge in the underlying data structure is replaced with two new edges defined by the subdivision scheme,
 *  which must be implemented by concrete subclasses, such that each new pair of edges share the new vertex and each contain one of the vertices referenced
 *  by the original edge.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

abstract public class SubdivisionProcedure implements ProcedureI, ModelingConstantsI {

   /**
    * The control net that determines the limiting surface of this subdivision procedure.
    */

   protected ControlNet controlNet;

   /**
    * Constructs a subdivision procedure having the control net specified by the control net argument.
    *
    * @param controlNet the control net that the control net of this subdivision procedure is set to.
    */

   protected SubdivisionProcedure(ControlNet controlNet) { this.controlNet = controlNet; }

   /**
    * Edge subdividing method that replaces the next edge in the underlying data structure of the subdivision mesh argument at the position specified by the index argument with two new edges. A new
    * vertex is created and added to the end of the updated vertex array of the subdivision mesh argument, and the value returned corresponds to the value of the element in the indexed edge array of
    * the subdivision mesh argument, one element past the last edge inserted into the indexed edge array of the subdivision mesh argument.
    *
    * @param subdivisionMesh the subdivision mesh modified by this subdivision procedure.
    * @param index the index of the edge in the indexed edge array of the subdivision mesh argument to be replaced by two new edges.
    * @return the index of the element in the indexed edge array of the subdivision mesh argument one element past the last edge inserted into the indexed edge array of the subdivision mesh argument.
    */

   abstract protected int subdivideNextEdge(SubdivisionMesh subdivisionMesh, int index);

   /**
    * Modifies the indexed face array of the subdivision mesh argument by replacing the face specified by the index argument with four new faces
    * created in the subdivision refinement step of this Catmull-Clark subdivision procedure, as well as adding the necessary four new edges to the
    * end of the indexed edge array of the subdivision mesh argument. Returns the index of the element in the face array of the subdivision mesh
    * argument one element past the last face inserted into the indexed face array of the subdivision mesh argument.
    *
    * @param subdivisionMesh the subdivision mesh modified by this subdivision procedure.
    * @param index the index of the face in the indexed face array of the subdivision mesh argument to be replaced by four new faces.
    * @return the index of the element in the indexed face array of the subdivision mesh argument one element past the last face inserted into the indexed face array of the subdivision mesh argument.
    */

   abstract protected int subdivideNextFace(SubdivisionMesh subdivisionMesh, int index);

   /**
    * Computes modifications of each vertex in the underlying data structure of the subdivision mesh argument.
    *
    * @param subdivisionMesh the subdivision mesh modified by this subdivision procedure.
    */

   abstract protected void generateVertexUpdates(SubdivisionMesh subdivisionMesh);

   /**
    * Computes and sets the tangent space basis vectors for each vertex in the current vertex array of the subdivision mesh argument, in order to
    * compute normal vectors and to use the classical Blinn bump mapping equation.
    *
    * @param subdivisionMesh the subdivision mesh modified by this subdivision procedure.
    */

   abstract protected void generateTangentBasisVectors(SubdivisionMesh subdivisionMesh);

   /**
    * Computes and sets the normal vector for each vertex in the current vertex array of the subdivision mesh argument.
    *
    * @param subdivisionMesh the subdivision mesh modified by this subdivision procedure.
    */

   protected void generateNormalVectors(SubdivisionMesh subdivisionMesh) {

	  int index = 0;

	  while(index < subdivisionMesh.vertexArrayStop) {

		 float tangentX1 = subdivisionMesh.tangentU1Array[index + 0];
		 float tangentY1 = subdivisionMesh.tangentU1Array[index + 1];
		 float tangentZ1 = subdivisionMesh.tangentU1Array[index + 2];

		 float tangentX2 = subdivisionMesh.tangentU2Array[index++];
		 float tangentY2 = subdivisionMesh.tangentU2Array[index++];
		 float tangentZ2 = subdivisionMesh.tangentU2Array[index++];

         float normalX = tangentY2 * tangentZ1 - tangentZ2 * tangentY1;
         float normalY = tangentZ2 * tangentX1 - tangentX2 * tangentZ1;
         float normalZ = tangentX2 * tangentY1 - tangentY2 * tangentX1;

         float normalFactor   = (float)Math.sqrt(normalX * normalX + normalY * normalY + normalZ * normalZ);

         normalX /= normalFactor;
         normalY /= normalFactor;
         normalZ /= normalFactor;

         subdivisionMesh.addNormal(normalX, normalY, normalZ);

     }

   }

   /**
    * Subdivides each polygonal face in the subdivision mesh argument into four new connected polygons defined by concrete subdivision procedure subclasses
    * of this subdivision procedure, updating the vertex array, indexed edge arrays, and indexed face arrays of the subdivision mesh argument.
    *
    * @param subdivisionMesh the subdivision mesh modified by this subdivision procedure.
    */

   protected void subdivide(SubdivisionMesh subdivisionMesh) {

      int index = 0;

      generateVertexUpdates(subdivisionMesh);

      while(index < subdivisionMesh.indexedEdgeVerticesArrayStop)
   	     index = subdivideNextEdge(subdivisionMesh, index);

      index = 0;

      while(index < subdivisionMesh.indexedFaceVerticesArrayStop)
         index = subdivideNextFace(subdivisionMesh, index);

      subdivisionMesh.vertexArray = subdivisionMesh.updatedVertexArray;
      subdivisionMesh.vertexArrayStop = subdivisionMesh.updatedVertexArrayStop;

   }

   /**
    * Returns the index of the face in the indexed face arrays of the subdivision mesh argument that shares the edge indexed by the edge
    * index argument with the face indexed by the face index argument.
    *
    * @param subdivisionMesh the subdivision mesh queried.
    * @param faceIndex the index of the face that shares the edge indexed by the edge index argument with the face indexed by the return value.
    * @param edgeIndex the index of the edge that the face indexed by the return value and the face indexed by the face index argument share.
    * @return the index of the face that shares the edge indexed by the edge index argument with the face indexed by the face index argument
    */

   public int querySharingFace(SubdivisionMesh subdivisionMesh, int faceIndex, int edgeIndex) {

      int nextIndex = subdivisionMesh.indexedEdgeFacesArray[2 * edgeIndex + 0];

      if(nextIndex == faceIndex)
         nextIndex = subdivisionMesh.indexedEdgeFacesArray[2 * edgeIndex + 1];

      return nextIndex;

   }

   /**
    * Returns the index of the next edge in the indexed edge arrays of the subdivision mesh argument in winding order adjacent to the vertex indexed
    * by the vertex index argument, following the edge indexed by the edge index argument.
    *
    * @param subdivisionMesh the subdivision mesh queried.
    * @param faceIndex the index of the face that shares the vertex indexed by the vertex index argument with the edge indexed by the return value and the edge indexed by the edge index argument.
    * @param edgeIndex the index of the edge in the winding order list of edges adjacent to the vertex indexed by the vertex index argument that the edge indexed by the return value follows.
    * @param vertexIndex the index of the vertex whose winding order list of adjacent edges is queried.
    * @return the index of the next edge in winding order adjacent to the vertex indexed by the vertex index argument, following the edge indexed by the edge index argument.
    */

   public int queryNextEdge(SubdivisionMesh subdivisionMesh, int faceIndex, int edgeIndex, int vertexIndex) {

      for(int i = 0; i < subdivisionMesh.meshPrimitiveSize; i++) {

		 int nextIndex = subdivisionMesh.indexedFaceEdgesArray[subdivisionMesh.meshPrimitiveSize * faceIndex + i];

         if(nextIndex != edgeIndex && (subdivisionMesh.indexedEdgeVerticesArray[2 * nextIndex + 0] == vertexIndex || subdivisionMesh.indexedEdgeVerticesArray[2 * nextIndex + 1] == vertexIndex))
            return nextIndex;

      }

      return -1;

   }

   /**
    * Returns the index of the face in the indexed face arrays of the subdivision mesh argument obtained by retrieving the next edge in the
    * winding order list of edges adjacent to the vertex indexed by the vertex argument following the edge indexed by the edge index argument.
    *
    * @param subdivisionMesh the subdivision mesh queried.
    * @param edgeIndex the index of the edge in the winding order list of edges adjacent to the vertex indexed by the vertex index argument that defines the winding face indexed by the return value.
    * @param vertexIndex the index of the vertex whose winding order list of adjacent edges is queried.
    * @return the index of the face obtained by retrieving the next edge in the winding order list of edges adjacent to the vertex indexed by the vertex argument following the edge indexed by the edge index argument.
    */

   protected int queryWindingFace(SubdivisionMesh subdivisionMesh, int edgeIndex, int vertexIndex) {

      int faceIndex1 = subdivisionMesh.indexedEdgeFacesArray[2 * edgeIndex + 0];
      int faceIndex2 = subdivisionMesh.indexedEdgeFacesArray[2 * edgeIndex + 1];

      int adjacentIndex1 = queryNextEdge(subdivisionMesh, faceIndex1, edgeIndex, vertexIndex);
      int adjacentIndex2 = queryNextEdge(subdivisionMesh, faceIndex2, edgeIndex, vertexIndex);

      int[] indices = new int[subdivisionMesh.meshPrimitiveSize];

      for(int i = 0; i < subdivisionMesh.meshPrimitiveSize; i++)
         indices[i] = subdivisionMesh.indexedFaceEdgesArray[subdivisionMesh.meshPrimitiveSize * faceIndex1 + i];

      for(int i = 0; i < subdivisionMesh.meshPrimitiveSize; i++)
         if(edgeIndex == indices[i] && adjacentIndex1 == indices[(i + (subdivisionMesh.meshPrimitiveSize - 1)) % subdivisionMesh.meshPrimitiveSize])
            return faceIndex1;

      return faceIndex2;

   }

   /**
    * Returns the control net associated with this subdivision procedure.
    *
    * @return the control net associated with this subdivision procedure.
    */

   public ControlNet getControlNet() { return controlNet; }

   /**
    * Sets the control net associated with this subdivision procedure to the control net argument.
    *
    * @param the control net that the control net associated with this subdivision procedure is set to.
    */

   public void setControlNet(ControlNet controlNet) { this.controlNet = controlNet; }

}