package shamu.media.render;

/** Procedural modeling class representing the Loop subdivision scheme created by Charles Loop of the University of Utah, using a mask diagram
 *  of adjacent vertices and which generalizes quartic triangular box splines. The subdivision scheme can be applied to a triangular mesh of
 *  arbitrary topology, corresponding to a limiting surface that is a member of the C2 family of surfaces.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class LoopSubdivisionProcedure extends SubdivisionProcedure {

   /**
    * Constructs a Loop subdivision procedure having the control net specified by the control net argument.
    *
    * @param controlNet the control net that the control net of this Loop subdivision procedure is set to.
    */

   public LoopSubdivisionProcedure(ControlNet controlNet) { super(controlNet); }

   /**
    * Computes modifications of each vertex in the underlying data structure of the subdivision mesh argument.
    *
    * @param subdivisionMesh the subdivision mesh modified by this Loop subdivision procedure.
    */

   protected void generateVertexUpdates(SubdivisionMesh subdivisionMesh) {

      int index = 0;

      int[] valencyArray = null;

      subdivisionMesh.updatedVertexArray = new float[subdivisionMesh.vertexArrayStop + SPACE_POINT_STRIDE  * subdivisionMesh.indexedEdgeVerticesArrayStop / 2];

      valencyArray = new int[subdivisionMesh.vertexArrayStop / SPACE_POINT_STRIDE ];

      while(index < subdivisionMesh.indexedEdgeVerticesArrayStop) {

         int index1 = subdivisionMesh.indexedEdgeVerticesArray[index + 0];
         int index2 = subdivisionMesh.indexedEdgeVerticesArray[index + 1];

         valencyArray[index1]++;
         valencyArray[index2]++;

         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index1 + 0] += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index2 + 0];
         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index1 + 1] += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index2 + 1];
         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index1 + 2] += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index2 + 2];

         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index2 + 0] += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index1 + 0];
         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index2 + 1] += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index1 + 1];
         subdivisionMesh.updatedVertexArray[SPACE_POINT_STRIDE  * index2 + 2] += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * index1 + 2];

         index += 2;

      }

      index = 0;

      while(index < subdivisionMesh.vertexArrayStop) {

         int n = valencyArray[index / SPACE_POINT_STRIDE ];

         float alpha = 2.0f * (float)Math.PI / n;
         float gamma = 0.625f - (float)Math.pow(3 + 2 * (float)Math.cos(alpha), 2.0f) / 64;
         float omega = n * (1 - gamma) / gamma;

         subdivisionMesh.updatedVertexArray[index + 0] += subdivisionMesh.vertexArray[index + 0] * omega;
         subdivisionMesh.updatedVertexArray[index + 1] += subdivisionMesh.vertexArray[index + 1] * omega;
         subdivisionMesh.updatedVertexArray[index + 2] += subdivisionMesh.vertexArray[index + 2] * omega;

         subdivisionMesh.updatedVertexArray[index + 0] /= (omega + n);
         subdivisionMesh.updatedVertexArray[index + 1] /= (omega + n);
         subdivisionMesh.updatedVertexArray[index + 2] /= (omega + n);

         index += SPACE_POINT_STRIDE ;

      }

      subdivisionMesh.updatedVertexArrayStop = subdivisionMesh.vertexArrayStop;

   }

   /**
    * Edge subdividing method that replaces the next edge in the underlying data structure of the subdivision mesh argument at the position specified by the index argument with two new edges. A new
    * vertex is created and added to the end of the updated vertex array of the subdivision mesh argument, and the value returned corresponds to the value of the element in the indexed edge array of
    * the subdivision mesh argument, one element past the last edge inserted into the indexed edge array of the subdivision mesh argument.
    *
    * @param subdivisionMesh the subdivision mesh modified by this Loop subdivision procedure.
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

      for(int i = 0; i < 3; i++) {

         int vertexIndex3 = subdivisionMesh.indexedFaceVerticesArray[3 * faceIndex1 + i];

         x += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * vertexIndex3 + 0];
         y += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * vertexIndex3 + 1];
         z += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * vertexIndex3 + 2];

      }

      for(int j = 0; j < 3; j++) {

         int vertexIndex4 = subdivisionMesh.indexedFaceVerticesArray[3 * faceIndex2 + j];

         x += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * vertexIndex4 + 0];
         y += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * vertexIndex4 + 1];
         z += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * vertexIndex4 + 2];

      }

      subdivisionMesh.updatedVertexArray[subdivisionMesh.updatedVertexArrayStop++] = x / 8.0f;
      subdivisionMesh.updatedVertexArray[subdivisionMesh.updatedVertexArrayStop++] = y / 8.0f;
      subdivisionMesh.updatedVertexArray[subdivisionMesh.updatedVertexArrayStop++] = z / 8.0f;

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
    * created in the subdivision refinement step of this Loop subdivision procedure, as well as adding the necessary four new edges to the end
    * of the indexed edge array of the subdivision mesh argument. Returns the index of the element in the face array of the subdivision mesh
    * argument one element past the last face inserted into the indexed face array of the subdivision mesh argument.
    *
    * @param subdivisionMesh the subdivision mesh modified by this Loop subdivision procedure.
    * @param index the index of the face in the indexed face array of the subdivision mesh argument to be replaced by four new faces.
    * @return the index of the element in the indexed face array of the subdivision mesh argument one element past the last face inserted into the indexed face array of the subdivision mesh argument.
    */

   protected int subdivideNextFace(SubdivisionMesh subdivisionMesh, int index) {

      int[] vertexGroup1 = new int[3];
      int[] vertexGroup2 = new int[3];

      int[] vertexGroup3 = new int[3];
      int[] vertexGroup4 = new int[3];

      int[] vertexGroup5 = new int[3];
      int[] vertexGroup6 = new int[3];

      int[] edgeGroup1 = new int[3];
      int[] edgeGroup2 = new int[3];

      int[] edgeGroup3 = new int[3];
      int[] edgeGroup4 = new int[3];

      int[] edgeGroup5 = new int[3];
      int[] edgeGroup6 = new int[3];

      vertexGroup1[0] = subdivisionMesh.indexedFaceVerticesArray[index + 0];
      vertexGroup1[1] = subdivisionMesh.indexedFaceVerticesArray[index + 1];
      vertexGroup1[2] = subdivisionMesh.indexedFaceVerticesArray[index + 2];

      edgeGroup1[0] = 2 * subdivisionMesh.indexedFaceEdgesArray[index + 0] + 0;
      edgeGroup1[1] = 2 * subdivisionMesh.indexedFaceEdgesArray[index + 1] + 0;
      edgeGroup1[2] = 2 * subdivisionMesh.indexedFaceEdgesArray[index + 2] + 0;

      edgeGroup2[0] = 2 * subdivisionMesh.indexedFaceEdgesArray[index + 0] + 1;
      edgeGroup2[1] = 2 * subdivisionMesh.indexedFaceEdgesArray[index + 1] + 1;
      edgeGroup2[2] = 2 * subdivisionMesh.indexedFaceEdgesArray[index + 2] + 1;

      vertexGroup2[0] = subdivisionMesh.indexedEdgeVerticesArray[2 * edgeGroup1[0] + 1];
      vertexGroup2[1] = subdivisionMesh.indexedEdgeVerticesArray[2 * edgeGroup1[1] + 1];
      vertexGroup2[2] = subdivisionMesh.indexedEdgeVerticesArray[2 * edgeGroup1[2] + 1];

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

      vertexGroup3[0] = vertexGroup2[0];
      vertexGroup3[1] = vertexGroup2[1];
      vertexGroup3[2] = vertexGroup2[2];

      vertexGroup4[0] = vertexGroup2[0];
      vertexGroup4[1] = vertexGroup1[1];
      vertexGroup4[2] = vertexGroup2[1];

      vertexGroup5[0] = vertexGroup2[1];
      vertexGroup5[1] = vertexGroup1[2];
      vertexGroup5[2] = vertexGroup2[2];

      vertexGroup6[0] = vertexGroup2[2];
      vertexGroup6[1] = vertexGroup1[0];
      vertexGroup6[2] = vertexGroup2[0];

      subdivisionMesh.indexedFaceVerticesArray[index + 0] = vertexGroup3[0];
      subdivisionMesh.indexedFaceVerticesArray[index + 1] = vertexGroup3[1];
      subdivisionMesh.indexedFaceVerticesArray[index + 2] = vertexGroup3[2];

      System.arraycopy(subdivisionMesh.indexedFaceVerticesArray, index + 3, subdivisionMesh.indexedFaceVerticesArray, index + 12, subdivisionMesh.indexedFaceVerticesArrayStop - index - 3);
      subdivisionMesh.indexedFaceVerticesArrayStop += 9;

      subdivisionMesh.indexedFaceVerticesArray[index +  3] = vertexGroup4[0];
      subdivisionMesh.indexedFaceVerticesArray[index +  4] = vertexGroup4[1];
      subdivisionMesh.indexedFaceVerticesArray[index +  5] = vertexGroup4[2];

      subdivisionMesh.indexedFaceVerticesArray[index +  6] = vertexGroup5[0];
      subdivisionMesh.indexedFaceVerticesArray[index +  7] = vertexGroup5[1];
      subdivisionMesh.indexedFaceVerticesArray[index +  8] = vertexGroup5[2];

      subdivisionMesh.indexedFaceVerticesArray[index +  9] = vertexGroup6[0];
      subdivisionMesh.indexedFaceVerticesArray[index + 10] = vertexGroup6[1];
      subdivisionMesh.indexedFaceVerticesArray[index + 11] = vertexGroup6[2];

      subdivisionMesh.indexedEdgeVerticesArray[subdivisionMesh.indexedEdgeVerticesArrayStop++] = vertexGroup2[0];
      subdivisionMesh.indexedEdgeVerticesArray[subdivisionMesh.indexedEdgeVerticesArrayStop++] = vertexGroup2[1];

      subdivisionMesh.indexedEdgeVerticesArray[subdivisionMesh.indexedEdgeVerticesArrayStop++] = vertexGroup2[1];
      subdivisionMesh.indexedEdgeVerticesArray[subdivisionMesh.indexedEdgeVerticesArrayStop++] = vertexGroup2[2];

      subdivisionMesh.indexedEdgeVerticesArray[subdivisionMesh.indexedEdgeVerticesArrayStop++] = vertexGroup2[2];
      subdivisionMesh.indexedEdgeVerticesArray[subdivisionMesh.indexedEdgeVerticesArrayStop++] = vertexGroup2[0];

      subdivisionMesh.indexedEdgeFacesArray[subdivisionMesh.indexedEdgeFacesArrayStop++] = (index + 12) / 3 - 4;
      subdivisionMesh.indexedEdgeFacesArray[subdivisionMesh.indexedEdgeFacesArrayStop++] = (index + 12) / 3 - 3;

      subdivisionMesh.indexedEdgeFacesArray[subdivisionMesh.indexedEdgeFacesArrayStop++] = (index + 12) / 3 - 4;
      subdivisionMesh.indexedEdgeFacesArray[subdivisionMesh.indexedEdgeFacesArrayStop++] = (index + 12) / 3 - 2;

      subdivisionMesh.indexedEdgeFacesArray[subdivisionMesh.indexedEdgeFacesArrayStop++] = (index + 12) / 3 - 4;
      subdivisionMesh.indexedEdgeFacesArray[subdivisionMesh.indexedEdgeFacesArrayStop++] = (index + 12) / 3 - 1;

      edgeGroup3[0] = subdivisionMesh.indexedEdgeVerticesArrayStop / 2 - 3;
      edgeGroup3[1] = subdivisionMesh.indexedEdgeVerticesArrayStop / 2 - 2;
      edgeGroup3[2] = subdivisionMesh.indexedEdgeVerticesArrayStop / 2 - 1;

      edgeGroup4[0] = edgeGroup2[0];
      edgeGroup4[1] = edgeGroup1[1];
      edgeGroup4[2] = subdivisionMesh.indexedEdgeVerticesArrayStop / 2 - 3;

      edgeGroup5[0] = edgeGroup2[1];
      edgeGroup5[1] = edgeGroup1[2];
      edgeGroup5[2] = subdivisionMesh.indexedEdgeVerticesArrayStop / 2 - 2;

      edgeGroup6[0] = edgeGroup2[2];
      edgeGroup6[1] = edgeGroup1[0];
      edgeGroup6[2] = subdivisionMesh.indexedEdgeVerticesArrayStop / 2 - 1;

      subdivisionMesh.indexedFaceEdgesArray[index + 0] = edgeGroup3[0];
      subdivisionMesh.indexedFaceEdgesArray[index + 1] = edgeGroup3[1];
      subdivisionMesh.indexedFaceEdgesArray[index + 2] = edgeGroup3[2];

      System.arraycopy(subdivisionMesh.indexedFaceEdgesArray, index + 3, subdivisionMesh.indexedFaceEdgesArray, index + 12, subdivisionMesh.indexedFaceEdgesArrayStop - index - 3);
      subdivisionMesh.indexedFaceEdgesArrayStop += 9;

      subdivisionMesh.indexedFaceEdgesArray[index +  3] = edgeGroup4[0];
      subdivisionMesh.indexedFaceEdgesArray[index +  4] = edgeGroup4[1];
      subdivisionMesh.indexedFaceEdgesArray[index +  5] = edgeGroup4[2];

      subdivisionMesh.indexedFaceEdgesArray[index +  6] = edgeGroup5[0];
      subdivisionMesh.indexedFaceEdgesArray[index +  7] = edgeGroup5[1];
      subdivisionMesh.indexedFaceEdgesArray[index +  8] = edgeGroup5[2];

      subdivisionMesh.indexedFaceEdgesArray[index +  9] = edgeGroup6[0];
      subdivisionMesh.indexedFaceEdgesArray[index + 10] = edgeGroup6[1];
      subdivisionMesh.indexedFaceEdgesArray[index + 11] = edgeGroup6[2];

      if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[0]] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[0]] = (index + 12) / 3 - 1;

      else if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[0] + 1] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[0] + 1] = (index + 12) / 3 - 1;

      if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[0]] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[0]] = (index + 12) / 3 - 3;

      else if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[0] + 1] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[0] + 1] = (index + 12) / 3 - 3;

      if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[1]] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[1]] = (index + 12) / 3 - 3;

      else if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[1] + 1] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[1] + 1] = (index + 12) / 3 - 3 ;

      if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[1]] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[1]] = (index + 12) / 3 - 2;

      else if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[1] + 1] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[1] + 1] = (index + 12) / 3 - 2;

      if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[2]] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[2]] = (index + 12) / 3 - 2;

      else if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[2] + 1] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup1[2] + 1] = (index + 12) / 3 - 2;

      if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[2]] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[2]] = (index + 12) / 3 - 1;

      else if(subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[2] + 1] == -1)
         subdivisionMesh.indexedEdgeFacesArray[2 * edgeGroup2[2] + 1] = (index + 12) / 3 - 1;

      return index + 12;

   }

   /**
    * Computes and sets the tangent space basis vectors for each vertex in the current vertex array of the subdivision mesh argument, in order to
    * compute normal vectors and to use the classical Blinn bump mapping equation.
    *
    * @param subdivisionMesh the subdivision mesh modified by this Loop subdivision procedure.
    */

   protected void generateTangentBasisVectors(SubdivisionMesh subdivisionMesh) {

      int[] countingArray = new int[subdivisionMesh.vertexArrayStop / SPACE_POINT_STRIDE ];
      int[] startingArray = new int[subdivisionMesh.vertexArrayStop / SPACE_POINT_STRIDE ];

      for(int i = 0; i < subdivisionMesh.indexedEdgeVerticesArrayStop; i++) {

		 countingArray[subdivisionMesh.indexedEdgeVerticesArray[i]]++;
         startingArray[subdivisionMesh.indexedEdgeVerticesArray[i]] = i / 2;

      }

      for(int i = 0; i < subdivisionMesh.vertexArrayStop; i += SPACE_POINT_STRIDE ) {

		 int index = i / SPACE_POINT_STRIDE ;

         int edgeIndex = startingArray[i / SPACE_POINT_STRIDE ];
         int faceIndex = queryWindingFace(subdivisionMesh, edgeIndex, index);

         int relativeIndex = 0;

         while(relativeIndex < countingArray[index]) {

            int nextVertex = subdivisionMesh.indexedEdgeVerticesArray[2 * edgeIndex + 0];

            if(nextVertex == index)
               nextVertex = subdivisionMesh.indexedEdgeVerticesArray[2 * edgeIndex + 1];

		    float cosine = (float)Math.cos(2.0f * Math.PI * relativeIndex / (float)countingArray[index]);
		    float sine   = (float)Math.sin(2.0f * Math.PI * relativeIndex / (float)countingArray[index]);

            float ux = cosine * subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * nextVertex + 0];
            float uy = cosine * subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * nextVertex + 1];
            float uz = cosine * subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * nextVertex + 2];

            float vx = sine * subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * nextVertex + 0];
            float vy = sine * subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * nextVertex + 1];
            float vz = sine * subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * nextVertex + 2];

	        subdivisionMesh.tangentU2Array[SPACE_POINT_STRIDE  * index + 0] += ux;
            subdivisionMesh.tangentU2Array[SPACE_POINT_STRIDE  * index + 1] += uy;
            subdivisionMesh.tangentU2Array[SPACE_POINT_STRIDE  * index + 2] += uz;

	        subdivisionMesh.tangentU1Array[SPACE_POINT_STRIDE  * index + 0] += vx;
            subdivisionMesh.tangentU1Array[SPACE_POINT_STRIDE  * index + 1] += vy;
            subdivisionMesh.tangentU1Array[SPACE_POINT_STRIDE  * index + 2] += vz;

            edgeIndex = queryNextEdge(subdivisionMesh, faceIndex, edgeIndex, index);
            faceIndex = querySharingFace(subdivisionMesh, faceIndex, edgeIndex);

            relativeIndex++;

	     }

	  }

      subdivisionMesh.tangentU1ArrayStop = subdivisionMesh.vertexArrayStop;
      subdivisionMesh.tangentU2ArrayStop = subdivisionMesh.vertexArrayStop;

   }

}