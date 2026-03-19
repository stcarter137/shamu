package shamu.media.render;

/** Procedural modeling class representing the Catmull-Clark subdivision scheme created by Ed Catmull and Jim Clark of the University of Utah, using
 *  a mask diagram of adjacent vertices and which generalizes bicubic B-spline patches. The subdivision scheme can be applied to a quadrilateral mesh
 *  of arbitrary topology, corresponding to a limiting surface that is a member of the C2 family of surfaces.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class CatmullClarkSubdivisionProcedure extends SubdivisionProcedure {

   /**
    * Constructs a Catmull-Clark subdivision procedure having the control net specified by the control net argument.
    *
    * @param controlNet the control net that the control net of this Catmull-Clark subdivision procedure is set to.
    */

   public CatmullClarkSubdivisionProcedure(ControlNet controlNet) { super(controlNet); }

   /**
    * Computes the face center vertex for each face in the underlying data structure of the subdivision mesh argument as the average of the vertices
    * of each face, and adds each successive face center into the face center array of the subdivision mesh argument.
    *
    * @param subdivisionMesh the subdivision mesh modified by this Catmull-Clark subdivision procedure.
    */

   protected void generateFaceCenters(SubdivisionMesh subdivisionMesh) {

      int index = 0;

      subdivisionMesh.faceCenterVertexArrayStop = 0;

      subdivisionMesh.faceCenterVertexArray = new float[SPACE_POINT_STRIDE  * subdivisionMesh.indexedFaceVerticesArrayStop / 4];

      while(index < subdivisionMesh.indexedFaceVerticesArrayStop) {

     	 int index1 = subdivisionMesh.indexedFaceVerticesArray[index++];
	     int index2 = subdivisionMesh.indexedFaceVerticesArray[index++];
	     int index3 = subdivisionMesh.indexedFaceVerticesArray[index++];
	     int index4 = subdivisionMesh.indexedFaceVerticesArray[index++];

         float x = subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index1 + 0];
         float y = subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index1 + 1];
         float z = subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index1 + 2];

         x += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index2 + 0];
         y += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index2 + 1];
         z += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index2 + 2];

         x += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index3 + 0];
         y += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index3 + 1];
         z += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index3 + 2];

         x += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index4 + 0];
         y += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index4 + 1];
         z += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index4 + 2];

	     subdivisionMesh.faceCenterVertexArray[subdivisionMesh.faceCenterVertexArrayStop++] = x / 4.0f;
         subdivisionMesh.faceCenterVertexArray[subdivisionMesh.faceCenterVertexArrayStop++] = y / 4.0f;
         subdivisionMesh.faceCenterVertexArray[subdivisionMesh.faceCenterVertexArrayStop++] = z / 4.0f;

      }

   }

   /**
    * Computes modifications of each vertex in the underlying data structure of the subdivision mesh argument.
    *
    * @param subdivisionMesh the subdivision mesh modified by this Catmull-Clark subdivision procedure.
    */

   protected void generateVertexUpdates(SubdivisionMesh subdivisionMesh) {

      int index = 0;

      int[] valencyArray = null;

      generateFaceCenters(subdivisionMesh);

      subdivisionMesh.updatedVertexArray = new float[subdivisionMesh.vertexArrayStop + SPACE_POINT_STRIDE  * subdivisionMesh.indexedEdgeVerticesArrayStop / 2 + subdivisionMesh.faceCenterVertexArrayStop];

      valencyArray = new int[subdivisionMesh.vertexArrayStop / SPACE_POINT_STRIDE ];

      while(index < subdivisionMesh.indexedEdgeVerticesArrayStop) {

         int index1 = subdivisionMesh.indexedEdgeVerticesArray[index + 0];
         int index2 = subdivisionMesh.indexedEdgeVerticesArray[index + 1];

         int index3 = subdivisionMesh.indexedEdgeFacesArray[index + 0];
         int index4 = subdivisionMesh.indexedEdgeFacesArray[index + 1];

         valencyArray[index1]++;
         valencyArray[index2]++;

         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index1 + 0] += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index2 + 0];
         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index1 + 1] += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index2 + 1];
         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index1 + 2] += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index2 + 2];

         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index1 + 0] += 0.5f * subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * index3 + 0];
         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index1 + 1] += 0.5f * subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * index3 + 1];
         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index1 + 2] += 0.5f * subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * index3 + 2];

         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index1 + 0] += 0.5f * subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * index4 + 0];
         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index1 + 1] += 0.5f * subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * index4 + 1];
         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index1 + 2] += 0.5f * subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * index4 + 2];

         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index2 + 0] += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index1 + 0];
         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index2 + 1] += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index1 + 1];
         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index2 + 2] += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index1 + 2];

         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index2 + 0] += 0.5f * subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * index3 + 0];
         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index2 + 1] += 0.5f * subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * index3 + 1];
         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index2 + 2] += 0.5f * subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * index3 + 2];

         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index2 + 0] += 0.5f * subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * index4 + 0];
         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index2 + 1] += 0.5f * subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * index4 + 1];
         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index2 + 2] += 0.5f * subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * index4 + 2];

         index += 2;

      }

      index = 0;

      while(index < subdivisionMesh.vertexArrayStop) {

         int n = valencyArray[index / SPACE_POINT_STRIDE ];

         subdivisionMesh.updatedVertexArray[index + 0] /= (float)(n * n);
         subdivisionMesh.updatedVertexArray[index + 1] /= (float)(n * n);
         subdivisionMesh.updatedVertexArray[index + 2] /= (float)(n * n);

         subdivisionMesh.updatedVertexArray[index + 0] += ((float)(n - 2) / (float)n) * subdivisionMesh.vertexArray[index + 0];
         subdivisionMesh.updatedVertexArray[index + 1] += ((float)(n - 2) / (float)n) * subdivisionMesh.vertexArray[index + 1];
         subdivisionMesh.updatedVertexArray[index + 2] += ((float)(n - 2) / (float)n) * subdivisionMesh.vertexArray[index + 2];

         index += SPACE_POINT_STRIDE ;

      }

      subdivisionMesh.updatedVertexArrayStop = subdivisionMesh.vertexArrayStop;

   }

   /**
    * Edge subdividing method that replaces the next edge in the underlying data structure of the subdivision mesh argument at the position specified by the index argument with two new edges. A new
    * vertex is created and added to the end of the updated vertex array of the subdivision mesh argument, and the value returned corresponds to the value of the element in the indexed edge array of
    * the subdivision mesh argument, one element past the last edge inserted into the indexed edge array of the subdivision mesh argument.
    *
    * @param subdivisionMesh the subdivision mesh modified by this Catmull-Clark subdivision procedure.
    * @param index the index of the edge in the indexed edge array of the subdivision mesh argument to be replaced by two new edges.
    * @return the index of the element in the indexed edge array of the subdivision mesh argument one element past the last edge inserted into the indexed edge array of the subdivision mesh argument.
    */

   protected int subdivideNextEdge(SubdivisionMesh subdivisionMesh, int index) {

      int vertexIndex1 = subdivisionMesh.indexedEdgeVerticesArray[index + 0];
      int vertexIndex2 = subdivisionMesh.indexedEdgeVerticesArray[index + 1];

      int faceIndex1 = subdivisionMesh.indexedEdgeFacesArray[index + 0];
      int faceIndex2 = subdivisionMesh.indexedEdgeFacesArray[index + 1];

      float x = subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * vertexIndex1 + 0];
      float y = subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * vertexIndex1 + 1];
      float z = subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * vertexIndex1 + 2];

      x += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * vertexIndex2 + 0];
      y += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * vertexIndex2 + 1];
      z += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * vertexIndex2 + 2];

      x += subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex1 + 0];
      y += subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex1 + 1];
      z += subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex1 + 2];;

      x += subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex2 + 0];
      y += subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex2 + 1];
      z += subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex2 + 2];

      subdivisionMesh.updatedVertexArray[subdivisionMesh.updatedVertexArrayStop++] = x / 4.0f;
      subdivisionMesh.updatedVertexArray[subdivisionMesh.updatedVertexArrayStop++] = y / 4.0f;
      subdivisionMesh.updatedVertexArray[subdivisionMesh.updatedVertexArrayStop++] = z / 4.0f;

      subdivisionMesh.indexedEdgeVerticesArray[index + 0] = vertexIndex1;
      subdivisionMesh.indexedEdgeVerticesArray[index + 1] = subdivisionMesh.updatedVertexArrayStop / SPACE_POINT_STRIDE  - 1;

      System.arraycopy(subdivisionMesh.indexedEdgeVerticesArray, index + 2, subdivisionMesh.indexedEdgeVerticesArray, index + 4, subdivisionMesh.indexedEdgeVerticesArrayStop - index - 2);
      subdivisionMesh.indexedEdgeVerticesArrayStop += 2;

      subdivisionMesh.indexedEdgeVerticesArray[index + 2] = vertexIndex2;
      subdivisionMesh.indexedEdgeVerticesArray[index + 3] = subdivisionMesh.updatedVertexArrayStop / SPACE_POINT_STRIDE  - 1;

      subdivisionMesh.indexedEdgeFacesArray[index + 0] = -1;
      subdivisionMesh.indexedEdgeFacesArray[index + 1] = -1;

      System.arraycopy(subdivisionMesh.indexedEdgeFacesArray, index + 2, subdivisionMesh.indexedEdgeFacesArray, index + 4, subdivisionMesh.indexedEdgeFacesArrayStop - index - 2);
      subdivisionMesh.indexedEdgeFacesArrayStop += 2;

      subdivisionMesh.indexedEdgeFacesArray[index + 2] = -1;
      subdivisionMesh.indexedEdgeFacesArray[index + 3] = -1;

      return index + 4;

   }

   /**
    * Modifies the indexed face array of the subdivision mesh argument by replacing the face specified by the index argument with four new faces
    * created in the subdivision refinement step of this Catmull-Clark subdivision procedure, as well as adding the necessary four new edges to the
    * end of the indexed edge array of the subdivision mesh argument. Returns the index of the element in the face array of the subdivision mesh
    * argument one element past the last face inserted into the indexed face array of the subdivision mesh argument.
    *
    * @param subdivisionMesh the subdivision mesh modified by this Catmull-Clark subdivision procedure.
    * @param index the index of the face in the indexed face array of the subdivision mesh argument to be replaced by four new faces.
    * @return the index of the element in the indexed face array of the subdivision mesh argument one element past the last face inserted into the indexed face array of the subdivision mesh argument.
    */

   protected int subdivideNextFace(SubdivisionMesh subdivisionMesh, int index) {

      int[] vertexGroup1 = new int[4];
      int[] vertexGroup2 = new int[4];

      int[] vertexGroup3 = new int[4];
      int[] vertexGroup4 = new int[4];

      int[] vertexGroup5 = new int[4];
      int[] vertexGroup6 = new int[4];

      int[] edgeGroup1 = new int[4];
      int[] edgeGroup2 = new int[4];

      int[] edgeGroup3 = new int[4];
      int[] edgeGroup4 = new int[4];

      int[] edgeGroup5 = new int[4];
      int[] edgeGroup6 = new int[4];

      vertexGroup1[0] = subdivisionMesh.indexedFaceVerticesArray[index + 0];
      vertexGroup1[1] = subdivisionMesh.indexedFaceVerticesArray[index + 1];
      vertexGroup1[2] = subdivisionMesh.indexedFaceVerticesArray[index + 2];
      vertexGroup1[3] = subdivisionMesh.indexedFaceVerticesArray[index + 3];

      edgeGroup1[0] = 2 * subdivisionMesh.indexedFaceEdgesArray[index + 0] + 0;
      edgeGroup1[1] = 2 * subdivisionMesh.indexedFaceEdgesArray[index + 1] + 0;
      edgeGroup1[2] = 2 * subdivisionMesh.indexedFaceEdgesArray[index + 2] + 0;
      edgeGroup1[3] = 2 * subdivisionMesh.indexedFaceEdgesArray[index + 3] + 0;

      edgeGroup2[0] = 2 * subdivisionMesh.indexedFaceEdgesArray[index + 0] + 1;
      edgeGroup2[1] = 2 * subdivisionMesh.indexedFaceEdgesArray[index + 1] + 1;
      edgeGroup2[2] = 2 * subdivisionMesh.indexedFaceEdgesArray[index + 2] + 1;
      edgeGroup2[3] = 2 * subdivisionMesh.indexedFaceEdgesArray[index + 3] + 1;

      vertexGroup2[0] = subdivisionMesh.indexedEdgeVerticesArray[2 * edgeGroup1[0] + 1];
      vertexGroup2[1] = subdivisionMesh.indexedEdgeVerticesArray[2 * edgeGroup1[1] + 1];
      vertexGroup2[2] = subdivisionMesh.indexedEdgeVerticesArray[2 * edgeGroup1[2] + 1];
      vertexGroup2[3] = subdivisionMesh.indexedEdgeVerticesArray[2 * edgeGroup1[3] + 1];

      if(subdivisionMesh.indexedEdgeVerticesArray[2 * edgeGroup1[0]] != vertexGroup1[0]) {

         edgeGroup1[0] = 2 * subdivisionMesh.indexedFaceEdgesArray[index + 0] + 1;
         edgeGroup2[0] = 2 * subdivisionMesh.indexedFaceEdgesArray[index + 0] + 0;

      }

      if(subdivisionMesh.indexedEdgeVerticesArray[2 * edgeGroup1[1]] != vertexGroup1[1]) {

         edgeGroup1[1] = 2 * subdivisionMesh.indexedFaceEdgesArray[index + 1] + 1;
         edgeGroup2[1] = 2 * subdivisionMesh.indexedFaceEdgesArray[index + 1] + 0;

      }

      if(subdivisionMesh.indexedEdgeVerticesArray[2 * edgeGroup1[2]] != vertexGroup1[2]) {

	     edgeGroup1[2] = 2 * subdivisionMesh.indexedFaceEdgesArray[index + 2] + 1;
	     edgeGroup2[2] = 2 * subdivisionMesh.indexedFaceEdgesArray[index + 2] + 0;

      }

      if(subdivisionMesh.indexedEdgeVerticesArray[2 * edgeGroup1[3]] != vertexGroup1[3]) {

         edgeGroup1[3] = 2 * subdivisionMesh.indexedFaceEdgesArray[index + 3] + 1;
	     edgeGroup2[3] = 2 * subdivisionMesh.indexedFaceEdgesArray[index + 3] + 0;

      }

      subdivisionMesh.updatedVertexArray[subdivisionMesh.updatedVertexArrayStop++] = subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * index / 16 + 0];
      subdivisionMesh.updatedVertexArray[subdivisionMesh.updatedVertexArrayStop++] = subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * index / 16 + 1];
      subdivisionMesh.updatedVertexArray[subdivisionMesh.updatedVertexArrayStop++] = subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * index / 16 + 2];

      vertexGroup3[0] = vertexGroup2[3];
      vertexGroup3[1] = vertexGroup1[0];
      vertexGroup3[2] = vertexGroup2[0];
      vertexGroup3[3] = subdivisionMesh.updatedVertexArrayStop / SPACE_POINT_STRIDE  - 1;

      vertexGroup4[0] = vertexGroup2[0];
      vertexGroup4[1] = vertexGroup1[1];
      vertexGroup4[2] = vertexGroup2[1];
      vertexGroup4[3] = subdivisionMesh.updatedVertexArrayStop / SPACE_POINT_STRIDE  - 1;

      vertexGroup5[0] = vertexGroup2[1];
      vertexGroup5[1] = vertexGroup1[2];
      vertexGroup5[2] = vertexGroup2[2];
      vertexGroup5[3] = subdivisionMesh.updatedVertexArrayStop / SPACE_POINT_STRIDE  - 1;

      vertexGroup6[0] = vertexGroup2[2];
      vertexGroup6[1] = vertexGroup1[3];
      vertexGroup6[2] = vertexGroup2[3];
      vertexGroup6[3] = subdivisionMesh.updatedVertexArrayStop / SPACE_POINT_STRIDE  - 1;

      subdivisionMesh.indexedFaceVerticesArray[index + 0] = vertexGroup3[0];
      subdivisionMesh.indexedFaceVerticesArray[index + 1] = vertexGroup3[1];
      subdivisionMesh.indexedFaceVerticesArray[index + 2] = vertexGroup3[2];
      subdivisionMesh.indexedFaceVerticesArray[index + 3] = vertexGroup3[3];

      System.arraycopy(subdivisionMesh.indexedFaceVerticesArray, index + 4, subdivisionMesh.indexedFaceVerticesArray, index + 16, subdivisionMesh.indexedFaceVerticesArrayStop - index - 4);
      subdivisionMesh.indexedFaceVerticesArrayStop += 12;

      subdivisionMesh.indexedFaceVerticesArray[index +  4] = vertexGroup4[0];
      subdivisionMesh.indexedFaceVerticesArray[index +  5] = vertexGroup4[1];
      subdivisionMesh.indexedFaceVerticesArray[index +  6] = vertexGroup4[2];
      subdivisionMesh.indexedFaceVerticesArray[index +  7] = vertexGroup4[3];

      subdivisionMesh.indexedFaceVerticesArray[index +  8] = vertexGroup5[0];
      subdivisionMesh.indexedFaceVerticesArray[index +  9] = vertexGroup5[1];
      subdivisionMesh.indexedFaceVerticesArray[index + 10] = vertexGroup5[2];
      subdivisionMesh.indexedFaceVerticesArray[index + 11] = vertexGroup5[3];

      subdivisionMesh.indexedFaceVerticesArray[index + 12] = vertexGroup6[0];
      subdivisionMesh.indexedFaceVerticesArray[index + 13] = vertexGroup6[1];
      subdivisionMesh.indexedFaceVerticesArray[index + 14] = vertexGroup6[2];
      subdivisionMesh.indexedFaceVerticesArray[index + 15] = vertexGroup6[3];

      subdivisionMesh.indexedEdgeVerticesArray[subdivisionMesh.indexedEdgeVerticesArrayStop++] = vertexGroup2[0];
      subdivisionMesh.indexedEdgeVerticesArray[subdivisionMesh.indexedEdgeVerticesArrayStop++] = subdivisionMesh.updatedVertexArrayStop / SPACE_POINT_STRIDE  - 1;

      subdivisionMesh.indexedEdgeVerticesArray[subdivisionMesh.indexedEdgeVerticesArrayStop++] = vertexGroup2[1];
      subdivisionMesh.indexedEdgeVerticesArray[subdivisionMesh.indexedEdgeVerticesArrayStop++] = subdivisionMesh.updatedVertexArrayStop / SPACE_POINT_STRIDE  - 1;

      subdivisionMesh.indexedEdgeVerticesArray[subdivisionMesh.indexedEdgeVerticesArrayStop++] = vertexGroup2[2];
      subdivisionMesh.indexedEdgeVerticesArray[subdivisionMesh.indexedEdgeVerticesArrayStop++] = subdivisionMesh.updatedVertexArrayStop / SPACE_POINT_STRIDE  - 1;

      subdivisionMesh.indexedEdgeVerticesArray[subdivisionMesh.indexedEdgeVerticesArrayStop++] = vertexGroup2[3];
      subdivisionMesh.indexedEdgeVerticesArray[subdivisionMesh.indexedEdgeVerticesArrayStop++] = subdivisionMesh.updatedVertexArrayStop / SPACE_POINT_STRIDE  - 1;

      subdivisionMesh.indexedEdgeFacesArray[subdivisionMesh.indexedEdgeFacesArrayStop++] = (index + 16) / 4 - 4;
      subdivisionMesh.indexedEdgeFacesArray[subdivisionMesh.indexedEdgeFacesArrayStop++] = (index + 16) / 4 - 3;

      subdivisionMesh.indexedEdgeFacesArray[subdivisionMesh.indexedEdgeFacesArrayStop++] = (index + 16) / 4 - 3;
      subdivisionMesh.indexedEdgeFacesArray[subdivisionMesh.indexedEdgeFacesArrayStop++] = (index + 16) / 4 - 2;

      subdivisionMesh.indexedEdgeFacesArray[subdivisionMesh.indexedEdgeFacesArrayStop++] = (index + 16) / 4 - 2;
      subdivisionMesh.indexedEdgeFacesArray[subdivisionMesh.indexedEdgeFacesArrayStop++] = (index + 16) / 4 - 1;

      subdivisionMesh.indexedEdgeFacesArray[subdivisionMesh.indexedEdgeFacesArrayStop++] = (index + 16) / 4 - 1;
      subdivisionMesh.indexedEdgeFacesArray[subdivisionMesh.indexedEdgeFacesArrayStop++] = (index + 16) / 4 - 4;

      edgeGroup3[0] = edgeGroup2[3];
      edgeGroup3[1] = edgeGroup1[0];
      edgeGroup3[2] = subdivisionMesh.indexedEdgeVerticesArrayStop / 2 - 4;
      edgeGroup3[3] = subdivisionMesh.indexedEdgeVerticesArrayStop / 2 - 1;

      edgeGroup4[0] = edgeGroup2[0];
      edgeGroup4[1] = edgeGroup1[1];
      edgeGroup4[2] = subdivisionMesh.indexedEdgeVerticesArrayStop / 2 - 3;
      edgeGroup4[3] = subdivisionMesh.indexedEdgeVerticesArrayStop / 2 - 4;

      edgeGroup5[0] = edgeGroup2[1];
      edgeGroup5[1] = edgeGroup1[2];
      edgeGroup5[2] = subdivisionMesh.indexedEdgeVerticesArrayStop / 2 - 2;
      edgeGroup5[3] = subdivisionMesh.indexedEdgeVerticesArrayStop / 2 - 3;

      edgeGroup6[0] = edgeGroup2[2];
      edgeGroup6[1] = edgeGroup1[3];
      edgeGroup6[2] = subdivisionMesh.indexedEdgeVerticesArrayStop / 2 - 1;
      edgeGroup6[3] = subdivisionMesh.indexedEdgeVerticesArrayStop / 2 - 2;

      subdivisionMesh.indexedFaceEdgesArray[index + 0] = edgeGroup3[0];
      subdivisionMesh.indexedFaceEdgesArray[index + 1] = edgeGroup3[1];
      subdivisionMesh.indexedFaceEdgesArray[index + 2] = edgeGroup3[2];
      subdivisionMesh.indexedFaceEdgesArray[index + 3] = edgeGroup3[3];

      System.arraycopy(subdivisionMesh.indexedFaceEdgesArray, index + 4, subdivisionMesh.indexedFaceEdgesArray, index + 16, subdivisionMesh.indexedFaceEdgesArrayStop - index - 4);
      subdivisionMesh.indexedFaceEdgesArrayStop += 12;

      subdivisionMesh.indexedFaceEdgesArray[index +  4] = edgeGroup4[0];
      subdivisionMesh.indexedFaceEdgesArray[index +  5] = edgeGroup4[1];
      subdivisionMesh.indexedFaceEdgesArray[index +  6] = edgeGroup4[2];
      subdivisionMesh.indexedFaceEdgesArray[index +  7] = edgeGroup4[3];

      subdivisionMesh.indexedFaceEdgesArray[index +  8] = edgeGroup5[0];
      subdivisionMesh.indexedFaceEdgesArray[index +  9] = edgeGroup5[1];
      subdivisionMesh.indexedFaceEdgesArray[index + 10] = edgeGroup5[2];
      subdivisionMesh.indexedFaceEdgesArray[index + 11] = edgeGroup5[3];

      subdivisionMesh.indexedFaceEdgesArray[index + 12] = edgeGroup6[0];
      subdivisionMesh.indexedFaceEdgesArray[index + 13] = edgeGroup6[1];
      subdivisionMesh.indexedFaceEdgesArray[index + 14] = edgeGroup6[2];
      subdivisionMesh.indexedFaceEdgesArray[index + 15] = edgeGroup6[3];

      if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[0]] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[0]] = (index + 16) / 4 - 4;

      else if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[0] + 1] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[0] + 1] = (index + 16) / 4 - 4;

      if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[0]] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[0]] = (index + 16) / 4 - 3;

      else if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[0] + 1] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[0] + 1] = (index + 16) / 4 - 3;

      if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[1]] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[1]] = (index + 16) / 4 - 3;

      else if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[1] + 1] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[1] + 1] = (index + 16) / 4 - 3;

      if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[1]] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[1]] = (index + 16) / 4 - 2;

      else if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[1] + 1] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[1] + 1] = (index + 16) / 4 - 2;

      if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[2]] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[2]] = (index + 16) / 4 - 2;

      else if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[2] + 1] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[2] + 1] = (index + 16) / 4 - 2;

      if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[2]] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[2]] = (index + 16) / 4 - 1;

      else if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[2] + 1] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[2] + 1] = (index + 16) / 4 - 1;

      if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[3]] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[3]] = (index + 16) / 4 - 1;

      else if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[3] + 1] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[3] + 1] = (index + 16) / 4 - 1;

      if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[3]] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[3]] = (index + 16) / 4 - 4;

      else if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[3] + 1] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[3] + 1] = (index + 16) / 4 - 4;

      return index + 16;

   }

   /**
    * Computes and sets the tangent space basis vectors for each vertex in the current vertex array of the subdivision mesh argument, in order to
    * compute normal vectors and to use the classical Blinn bump mapping equation.
    *
    * @param subdivisionMesh the subdivision mesh modified by this Catmull-Clark subdivision procedure.
    */

   protected void generateTangentBasisVectors(SubdivisionMesh subdivisionMesh) {

      generateFaceCenters(subdivisionMesh);

      int[] countingArray = new int[subdivisionMesh.vertexArrayStop / SPACE_POINT_STRIDE ];
      int[] startingArray = new int[subdivisionMesh.vertexArrayStop / SPACE_POINT_STRIDE ];

      for(int i = 0; i < subdivisionMesh.indexedEdgeVerticesArrayStop; i++) {

		 countingArray[subdivisionMesh.indexedEdgeVerticesArray[i]]++;
         startingArray[subdivisionMesh.indexedEdgeVerticesArray[i]] = i / 2;

      }

      for(int i = 0; i < subdivisionMesh.vertexArrayStop; i += SPACE_POINT_STRIDE ) {

		 int index = i / SPACE_POINT_STRIDE ;

         int edgeIndex1 = startingArray[i / SPACE_POINT_STRIDE ];
         int faceIndex1 = queryWindingFace(subdivisionMesh, edgeIndex1, index);

         int edgeIndex2 = queryNextEdge(subdivisionMesh, faceIndex1, edgeIndex1, index);
         int faceIndex2 = querySharingFace(subdivisionMesh, faceIndex1, edgeIndex2);

         float constantFactor = 1.0f + (float)Math.cos(2.0f * Math.PI / (float)countingArray[index]) + (float)Math.cos(Math.PI / (float)countingArray[index]) * (float)Math.sqrt(2.0f * (9.0f + (float)Math.cos(2.0f * Math.PI / (float)countingArray[index])));

         int relativeIndex = 0;

         while(relativeIndex < countingArray[index]) {

            int faceIndex3 = subdivisionMesh.indexedEdgeFacesArray[2 * edgeIndex1 + 0];
            int faceIndex4 = subdivisionMesh.indexedEdgeFacesArray[2 * edgeIndex2 + 0];

            int nextVertex1 = subdivisionMesh.indexedEdgeVerticesArray[2 * edgeIndex1 + 0];
            int nextVertex2 = subdivisionMesh.indexedEdgeVerticesArray[2 * edgeIndex2 + 0];

            if(faceIndex3 == faceIndex1)
               faceIndex3 = subdivisionMesh.indexedEdgeFacesArray[2 * edgeIndex1 + 1];

            if(faceIndex4 == faceIndex2)
               faceIndex4 = subdivisionMesh.indexedEdgeFacesArray[2 * edgeIndex2 + 1];

            if(nextVertex1 == index)
               nextVertex1 = subdivisionMesh.indexedEdgeVerticesArray[2 * edgeIndex1 + 1];

            if(nextVertex2 == index)
               nextVertex2 = subdivisionMesh.indexedEdgeVerticesArray[2 * edgeIndex2 + 1];

            float edgeX1 = subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * nextVertex1 + 0];
            float edgeY1 = subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * nextVertex1 + 1];
            float edgeZ1 = subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * nextVertex1 + 2];

            float edgeX2 = subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * nextVertex2 + 0];
            float edgeY2 = subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * nextVertex2 + 1];
            float edgeZ2 = subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * nextVertex2 + 2];

            edgeX1 += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index + 0];
            edgeY1 += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index + 1];
            edgeZ1 += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index + 2];

            edgeX2 += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index + 0];
            edgeY2 += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index + 1];
            edgeZ2 += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index + 2];

            edgeX1 += subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex1 + 0];
            edgeY1 += subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex1 + 1];
            edgeZ1 += subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex1 + 2];

            edgeX2 += subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex2 + 0];
            edgeY2 += subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex2 + 1];
            edgeZ2 += subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex2 + 2];

            edgeX1 += subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex3 + 0];
            edgeY1 += subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex3 + 1];
            edgeZ1 += subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex3 + 2];

            edgeX2 += subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex4 + 0];
            edgeY2 += subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex4 + 1];
            edgeZ2 += subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex4 + 2];

            edgeX1 /= 4.0f;
            edgeY1 /= 4.0f;
            edgeZ1 /= 4.0f;

            edgeX2 /= 4.0f;
            edgeY2 /= 4.0f;
            edgeZ2 /= 4.0f;

            float faceCenterX1 = subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex1 + 0];
            float faceCenterY1 = subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex1 + 1];
            float faceCenterZ1 = subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex1 + 2];

            float faceCenterX2 = subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex2 + 0];
            float faceCenterY2 = subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex2 + 1];
            float faceCenterZ2 = subdivisionMesh.faceCenterVertexArray[SPACE_POINT_STRIDE  * faceIndex2 + 2];

		    float cosine1 = (float)Math.cos(2.0f * Math.PI * (relativeIndex + 0) / (float)countingArray[index]);
		    float cosine2 = (float)Math.cos(2.0f * Math.PI * (relativeIndex + 1) / (float)countingArray[index]);

            float ux = constantFactor * (float)Math.cos(2.0f * Math.PI * relativeIndex / (float)countingArray[index]) * edgeX1 + (cosine1 + cosine2) * faceCenterX1;
            float uy = constantFactor * (float)Math.cos(2.0f * Math.PI * relativeIndex / (float)countingArray[index]) * edgeY1 + (cosine1 + cosine2) * faceCenterY1;
            float uz = constantFactor * (float)Math.cos(2.0f * Math.PI * relativeIndex / (float)countingArray[index]) * edgeZ1 + (cosine1 + cosine2) * faceCenterZ1;

            float vx = constantFactor * (float)Math.cos(2.0f * Math.PI * relativeIndex / (float)countingArray[index]) * edgeX2 + (cosine1 + cosine2) * faceCenterX2;
            float vy = constantFactor * (float)Math.cos(2.0f * Math.PI * relativeIndex / (float)countingArray[index]) * edgeY2 + (cosine1 + cosine2) * faceCenterY2;
            float vz = constantFactor * (float)Math.cos(2.0f * Math.PI * relativeIndex / (float)countingArray[index]) * edgeZ2 + (cosine1 + cosine2) * faceCenterZ2;

	        subdivisionMesh.tangentU2Array[SPACE_POINT_STRIDE  * index + 0] += ux;
            subdivisionMesh.tangentU2Array[SPACE_POINT_STRIDE  * index + 1] += uy;
            subdivisionMesh.tangentU2Array[SPACE_POINT_STRIDE  * index + 2] += uz;

	        subdivisionMesh.tangentU1Array[SPACE_POINT_STRIDE  * index + 0] += vx;
            subdivisionMesh.tangentU1Array[SPACE_POINT_STRIDE  * index + 1] += vy;
            subdivisionMesh.tangentU1Array[SPACE_POINT_STRIDE  * index + 2] += vz;

            edgeIndex1 = edgeIndex2;
            faceIndex1 = faceIndex2;

            edgeIndex2 = queryNextEdge(subdivisionMesh, faceIndex2, edgeIndex2, index);
            faceIndex2 = querySharingFace(subdivisionMesh, faceIndex2, edgeIndex2);

            relativeIndex++;

	     }

	  }

      subdivisionMesh.tangentU1ArrayStop = subdivisionMesh.vertexArrayStop;
      subdivisionMesh.tangentU2ArrayStop = subdivisionMesh.vertexArrayStop;

   }

}
