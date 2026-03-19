package shamu.media.render;

/** Procedural modeling class representing the spherical subdivision scheme, which consists of a spherical limiting surface and a control net
 *  that has the pre-condition of it's points lying on the limiting spherical surface.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class SphericalSubdivisionProcedure extends SubdivisionProcedure {

   /**
    * Constructs a spherical subdivision procedure having the control net specified by the control net argument, which should consist of points lying on the surface of a sphere.
    *
    * @param controlNet the control net that the control net of this spherical subdivision procedure is set to.
    */

   public SphericalSubdivisionProcedure(ControlNet controlNet) { super(controlNet); }

   /**
    * Copies the data in the vertex array of the subdivision mesh argument into the updated vertex array of the subdivision mesh argument.
    *
    * @param subdivisionMesh the subdivision mesh modified by this spherical subdivision procedure.
    */

   protected void generateVertexUpdates(SubdivisionMesh subdivisionMesh) {

      subdivisionMesh.updatedVertexArray = new float[subdivisionMesh.vertexArrayStop + SPACE_POINT_STRIDE  * subdivisionMesh.indexedEdgeVerticesArrayStop / 2];

      System.arraycopy(subdivisionMesh.vertexArray, 0, subdivisionMesh.updatedVertexArray, 0, subdivisionMesh.vertexArrayStop);

      subdivisionMesh.updatedVertexArrayStop = subdivisionMesh.vertexArrayStop;

   }

   /**
    * Edge subdividing method that replaces the next edge in the underlying data structure of the subdivision mesh argument at the position specified by the index argument with two new edges. A new
    * vertex is created, which will lie on the surface of the limiting sphere, and added to the end of the updated vertex array of the subdivision mesh argument, and the value returned corresponds
    * to the value of the element in the indexed edge array of the subdivision mesh argument, one element past the last edge inserted into the indexed edge array of the subdivision mesh argument.
    *
    * @param subdivisionMesh the subdivision mesh modified by this spherical subdivision procedure.
    * @param index the index of the edge in the indexed edge array of the subdivision mesh argument to be replaced by two new edges.
    * @return the index of the element in the indexed edge array of the subdivision mesh argument one element past the last edge inserted into the indexed edge array of the subdivision mesh argument.
    */

   protected int subdivideNextEdge(SubdivisionMesh subdivisionMesh, int index) {

      int vertexIndex1 = subdivisionMesh.indexedEdgeVerticesArray[index + 0];
      int vertexIndex2 = subdivisionMesh.indexedEdgeVerticesArray[index + 1];

      float x = subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * vertexIndex1 + 0];
      float y = subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * vertexIndex1 + 1];
      float z = subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * vertexIndex1 + 2];

      float radius = (float)Math.sqrt(Math.pow(x, 2.0f) + Math.pow(y, 2.0f) + Math.pow(z, 2.0f));

      x += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * vertexIndex2 + 0];
      y += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * vertexIndex2 + 1];
      z += subdivisionMesh.vertexArray[SPACE_POINT_STRIDE  * vertexIndex2 + 2];

      x /= 2.0f;
      y /= 2.0f;
      z /= 2.0f;

      float factor = (float)Math.sqrt(Math.pow(x, 2.0f) + Math.pow(y, 2.0f) + Math.pow(z, 2.0f));

      x *= (radius / factor);
      y *= (radius / factor);
      z *= (radius / factor);

      subdivisionMesh.updatedVertexArray[subdivisionMesh.updatedVertexArrayStop++] = x;
      subdivisionMesh.updatedVertexArray[subdivisionMesh.updatedVertexArrayStop++] = y;
      subdivisionMesh.updatedVertexArray[subdivisionMesh.updatedVertexArrayStop++] = z;

      subdivisionMesh.indexedEdgeFacesArray[index + 0] = -1;
      subdivisionMesh.indexedEdgeFacesArray[index + 1] = -1;

      System.arraycopy(subdivisionMesh.indexedEdgeFacesArray, index + 2, subdivisionMesh.indexedEdgeFacesArray, index + 4, subdivisionMesh.indexedEdgeFacesArrayStop - index - 2);
      subdivisionMesh.indexedEdgeFacesArrayStop += 2;

      subdivisionMesh.indexedEdgeFacesArray[index + 2] = -1;
      subdivisionMesh.indexedEdgeFacesArray[index + 3] = -1;

      subdivisionMesh.indexedEdgeVerticesArray[index + 0] = vertexIndex1;
      subdivisionMesh.indexedEdgeVerticesArray[index + 1] = subdivisionMesh.updatedVertexArrayStop / SPACE_POINT_STRIDE  - 1;

      System.arraycopy(subdivisionMesh.indexedEdgeVerticesArray, index + 2, subdivisionMesh.indexedEdgeVerticesArray, index + 4, subdivisionMesh.indexedEdgeVerticesArrayStop - index - 2);
      subdivisionMesh.indexedEdgeVerticesArrayStop += 2;

      subdivisionMesh.indexedEdgeVerticesArray[index + 2] = vertexIndex2;
      subdivisionMesh.indexedEdgeVerticesArray[index + 3] = subdivisionMesh.updatedVertexArrayStop / SPACE_POINT_STRIDE  - 1;

      return index + 4;

   }

   /**
    * Modifies the indexed face array of the subdivision mesh argument by replacing the face specified by the index argument with four new faces
    * created in the subdivision refinement step of this spherical subdivision procedure, as well as adding the necessary four new edges to the end
    * of the indexed edge array of the subdivision mesh argument. Returns the index of the element in the face array of the subdivision mesh
    * argument one element past the last face inserted into the indexed face array of the subdivision mesh argument.
    *
    * @param subdivisionMesh the subdivision mesh modified by this spherical subdivision procedure.
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
    * @param subdivisionMesh the subdivision mesh modified by this spherical subdivision procedure.
    */

   protected void generateTangentBasisVectors(SubdivisionMesh subdivisionMesh) {

      float sphericalFactor = subdivisionMesh.vertexArray[0] * subdivisionMesh.vertexArray[0] + subdivisionMesh.vertexArray[1] * subdivisionMesh.vertexArray[1] + subdivisionMesh.vertexArray[2] * subdivisionMesh.vertexArray[2];

	  int index = 0;

	  while(index < subdivisionMesh.vertexArrayStop) {

         float x = subdivisionMesh.vertexArray[index++];
         float y = subdivisionMesh.vertexArray[index++];
         float z = subdivisionMesh.vertexArray[index++];

         float determinant = (float)Math.sqrt(sphericalFactor - y * y);

         float ux = -z;
         float uy =  0;
         float uz =  x;

         float vx = -(x * y) / determinant;
         float vy =  determinant;
         float vz = -(y * z) / determinant;

         subdivisionMesh.addTangentU1(ux, uy, uz);
         subdivisionMesh.addTangentU2(vx, vy, vz);

      }

   }

   /**
    * Computes and sets the normal vector for each vertex in the current vertex array of the subdivision mesh argument.
    *
    * @param subdivisionMesh the subdivision mesh modified by this spherical subdivision procedure.
    */

   protected void generateNormalVectors(SubdivisionMesh subdivisionMesh) {

	  int index = 0;

	  while(index < subdivisionMesh.vertexArrayStop) {

         float normalX = subdivisionMesh.vertexArray[index++];
         float normalY = subdivisionMesh.vertexArray[index++];
         float normalZ = subdivisionMesh.vertexArray[index++];

         float normalFactor   = (float)Math.sqrt(normalX * normalX + normalY * normalY + normalZ * normalZ);

         normalX /= normalFactor;
         normalY /= normalFactor;
         normalZ /= normalFactor;

         subdivisionMesh.addNormal(normalX, normalY, normalZ);

      }

   }

}