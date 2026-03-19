package shamu.media.render;

/** Geometry class representing the underlying data structure of a polygonal mesh.
 *
 *  @see Mesh
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class Mesh extends Geometry implements java.io.Serializable, ModelingConstantsI {

   /**
    * Constant used to contruct a mesh of triangles.
    */

   public static final int TRIANGLES = 0xAAAAAA;

   /**
    * Constant used to construct a mesh of quadrilaterals.
    */

   public static final int QUADRILATERALS = 0xBBBBBB;

   /**
    * Default capacity of underlying vertex array of this mesh.
    */

   public static final int DEFAULT_VERTEX_CAPACITY  = 640000;

   /**
    * Default capacity of underlying face array referencing the vertex array of this mesh.
    */

   public static final int DEFAULT_FACE_CAPACITY = 640000;

   /**
    * Array of vertices of this mesh.
    */

   protected float[] vertexArray = new float[SPACE_POINT_STRIDE  * DEFAULT_VERTEX_CAPACITY / SPACE_POINT_STRIDE ];

   /**
    * Array of vertex tangents in the u1 tangential direction parallel to the vertex array of this mesh.
    */

   protected float[] tangentU1Array = new float[TANGENT_STRIDE * DEFAULT_VERTEX_CAPACITY / SPACE_POINT_STRIDE ];

   /**
    * Array of vertex tangents in the u2 tangential direction parallel to the vertex array of this mesh.
    */

   protected float[] tangentU2Array = new float[TANGENT_STRIDE * DEFAULT_VERTEX_CAPACITY / SPACE_POINT_STRIDE ];

   /**
    * Array of vertex normals parallel to the vertex array of this mesh.
    */

   protected float[] normalArray = new float[NORMAL_STRIDE * DEFAULT_VERTEX_CAPACITY / SPACE_POINT_STRIDE ];

   /**
    * Array of vertex texels parallel to the vertex array of this mesh.
    */

   protected float[] texelArray = new float[TEXEL_STRIDE * DEFAULT_VERTEX_CAPACITY / SPACE_POINT_STRIDE ];

   /**
    * Array of vertex colors parallel to the vertex array of this mesh.
    */

   protected byte[] colorArray = new byte[COLOR_STRIDE * DEFAULT_VERTEX_CAPACITY / SPACE_POINT_STRIDE ];

   /**
    * Array of indexed vertex references of this mesh such that every n references defines a face of this mesh.
    */

   protected int[] indexedFaceVerticesArray = new int[DEFAULT_FACE_CAPACITY];

   /**
    * Index of the element in the vertex array one element past the last vertex added to the array.
    */

   protected int vertexArrayStop = 0;

   /**
    * Index of the element in the vertex u1 tangent array one element past the last vertex u1 tangent added to the array.
    */

   protected int tangentU1ArrayStop = 0;

   /**
    * Index of the element in the vertex u2 tangent array one element past the last vertex u2 tangent added to the array.
    */

   protected int tangentU2ArrayStop = 0;

   /**
    * Index of the element in the vertex normal array one element past the last vertex normal added to the array.
    */

   protected int normalArrayStop = 0;

   /**
    * Index of the element in the vertex texel array one element past the last vertex texel added to the array.
    */

   protected int texelArrayStop = 0;

   /**
    * Index of the element in the vertex color array one element past the last vertex color added to the array.
    */

   protected int colorArrayStop = 0;

   /**
    * Index of the element in the indexed face vertices array one element past the last face added to the array.
    */

   protected int indexedFaceVerticesArrayStop = 0;

   /**
    * Number of array elements per face in the indexed face array of this mesh.
    */

   protected int meshPrimitiveSize;

   /**
    * Constructs a mesh having the default vertex array, vertex tangent array, vertex normal array, vertex texel array, vertex color array, and indexed face array
    * capacities with a mesh of polygons defined by the integer argument, which must be TRIANGLES or QUADRILATERALS.
    *
    * @param meshType an integer specifying whether the underlying base mesh of this mesh is triangle based or quadrilateral based. Must be either TRIANGLE_MESH or QUADRILATERAL_MESH.
    * @throws IllegalMeshTypeException if the integer argument is not a valid mesh type, i.e. TRIANGLES or QUADRILATERALS.
    */

   public Mesh(int meshType) {

      if(meshType == TRIANGLES)
	     meshPrimitiveSize = 3;

      else if(meshType == QUADRILATERALS)
	     meshPrimitiveSize = 4;

      else
         throw new IllegalMeshTypeException();

   }

   public void apply(Matrix3D matrix) {

      int npoints = vertexArrayStop / SPACE_POINT_STRIDE;

      for(int k = 0; k < npoints; k++) {

         vertexArray[SPACE_POINT_STRIDE * k + 0] *= matrix.elements[0][0];
         vertexArray[SPACE_POINT_STRIDE * k + 1] *= matrix.elements[1][1];  
         vertexArray[SPACE_POINT_STRIDE * k + 2] *= matrix.elements[2][2];

      }

   }

   public float intersection(float startX, float startY, float startZ, float directionX, float directionY, float directionZ, Matrix4D matrix) throws NullIntersectionException {

      throw new NullIntersectionException();

   }

   /**
    * Adds the vertex coordinates specified by the floating point arguments to the underlying data structure of this mesh.
    *
    * @param x the x-coordinate of the vertex added to the underlying data structure of this mesh.
    * @param y the y-coordinate of the vertex added to the underlying data structure of this mesh.
    * @param z the z-coordinate of the vertex added to the underlying data structure of this mesh.
    */

   public void addVertex(float x, float y, float z) {

      handleCapacityOverload(vertexArray);

      vertexArray[vertexArrayStop++] = x;
      vertexArray[vertexArrayStop++] = y;
      vertexArray[vertexArrayStop++] = z;

   }

   /**
    * Adds the u1 tangent coordinates specified by the floating point arguments to the underlying data structure of this mesh.
    *
    * @param tx the x-coordinate of the u1 tangent added to the underlying data structure of this mesh.
    * @param ty the y-coordinate of the u1 tangent added to the underlying data structure of this mesh.
    * @param tz the z-coordinate of the u1 tangent added to the underlying data structure of this mesh.
    */

   public void addTangentU1(float tx, float ty, float tz) {

      handleCapacityOverload(tangentU1Array);

      tangentU1Array[tangentU1ArrayStop++] = tx;
      tangentU1Array[tangentU1ArrayStop++] = ty;
      tangentU1Array[tangentU1ArrayStop++] = tz;

   }

   /**
    * Adds the u2 tangent coordinates specified by the floating point arguments to the underlying data structure of this mesh.
    *
    * @param tx the x-coordinate of the u2 tangent added to the underlying data structure of this mesh.
    * @param ty the y-coordinate of the u2 tangent added to the underlying data structure of this mesh.
    * @param tz the z-coordinate of the u2 tangent added to the underlying data structure of this mesh.
    */

   public void addTangentU2(float tx, float ty, float tz) {

      handleCapacityOverload(tangentU2Array);

      tangentU2Array[tangentU2ArrayStop++] = tx;
      tangentU2Array[tangentU2ArrayStop++] = ty;
      tangentU2Array[tangentU2ArrayStop++] = tz;

   }

   /**
    * Adds the normal coordinates specified by the floating point arguments to the underlying data structure of this mesh.
    *
    * @param nx the x-coordinate of the normal added to the underlying data structure of this mesh.
    * @param ny the y-coordinate of the normal added to the underlying data structure of this mesh.
    * @param nz the z-coordinate of the normal added to the underlying data structure of this mesh.
    */

   public void addNormal(float nx, float ny, float nz) {

      handleCapacityOverload(normalArray);

      normalArray[normalArrayStop++] = nx;
      normalArray[normalArrayStop++] = ny;
      normalArray[normalArrayStop++] = nz;

   }

   /**
    * Adds the texel coordinates specified by the floating point arguments to the underlying data structure of this mesh.
    *
    * @param u the u-coordinate of the texel added to the underlying data structure of this mesh.
    * @param v the v-coordinate of the texel added to the underlying data structure of this mesh.
    */

   public void addTexel(float u, float v) {

      handleCapacityOverload(texelArray);

      texelArray[texelArrayStop++] = u;
      texelArray[texelArrayStop++] = v;

   }

   /**
    * Adds the color components specified by the byte arguments to the underlying data structure of this mesh.
    *
    * @param r the red component of the color added to the underlying data structure of this mesh.
    * @param g the green component of the color added to the underlying data structure of this mesh.
    * @param b the blue component of the color added to the underlying data structure of this mesh.
    */

   public void addColor(byte r, byte g, byte b) {

      handleCapacityOverload(colorArray);

      colorArray[colorArrayStop++] = r;
      colorArray[colorArrayStop++] = g;
      colorArray[colorArrayStop++] = b;

   }

   /**
    * Adds the face indices specified by the integer array argument to the underlying data structure of this mesh.
    *
    * @param indices the references to the face vertices added to the underlying data structure of this mesh.
    */

   public void addFace(int[] indices) {

      handleCapacityOverload(indexedFaceVerticesArray);

      for(int i = 0; i < meshPrimitiveSize; i++)
         indexedFaceVerticesArray[indexedFaceVerticesArrayStop++] = indices[i];

   }

   /**
    * Adds the vertex coordinates specified by the floating point array argument to the underlying data structure of this mesh.
    *
    * @param vertices the array containing the coordinates of the vertices added the underlying data structure of to this mesh.
    */

   public void addVertices(float[] vertices) {

      for(int i = 0; i < vertices.length; i++) {

         handleCapacityOverload(vertexArray);

         vertexArray[vertexArrayStop++] = vertices[i];

      }

   }

   /**
    * Adds the u1 tangent coordinates specified by the floating point array argument to the underlying data structure of this mesh.
    *
    * @param tangentsU1 the array containing the coordinates of the u1 tangents added to the underlying data structure of this mesh.
    */

   public void addTangentsU1(float[] tangentsU1) {

      for(int i = 0; i < tangentsU1.length; i++) {

         handleCapacityOverload(tangentU1Array);

         tangentU1Array[tangentU1ArrayStop++] = tangentsU1[i];

      }

   }

   /**
    * Adds the u2 tangent coordinates specified by the floating point array argument to the underlying data structure of this mesh.
    *
    * @param tangentsU2 the array containing the coordinates of the u1 tangents added to the underlying data structure of this mesh.
    */

   public void addTangentsU2(float[] tangentsU2) {

      for(int i = 0; i < tangentsU2.length; i++) {

         handleCapacityOverload(tangentU2Array);

         tangentU2Array[tangentU2ArrayStop++] = tangentsU2[i];

      }

   }

   /**
    * Adds the normal coordinates specified by the floating point array argument to the underlying data structure of this mesh.
    *
    * @param normals the array containing the coordinates of the normals added to the underlying data structure of this mesh.
    */

   public void addNormals(float[] normals) {

      for(int i = 0; i < normals.length; i++) {

         handleCapacityOverload(normalArray);

         normalArray[normalArrayStop++] = normals[i];

      }

   }

   /**
    * Adds the texel coordinates specified by the floating point array argument to the underlying data structure of this mesh.
    *
    * @param texels the array containing the coordinates of the texels added to the underlying data structure of this mesh.
    */

   public void addTexels(float[] texels) {

      for(int i = 0; i < texels.length; i++) {

         handleCapacityOverload(texelArray);

         texelArray[texelArrayStop++] = texels[i];

      }

   }

   /**
    * Adds the color components specified by the byte array argument to the underlying data structure of this mesh.
    *
    * @param colors the array containing the components of the colors added to the underlying data structure of this mesh.
    */

   public void addColors(byte[] colors) {

      for(int i = 0; i < colors.length; i++) {

         handleCapacityOverload(colorArray);

         colorArray[colorArrayStop++] = colors[i];

      }

   }

   /**
    * Adds the faces specified by the integer array argument to the underlying data structure of this mesh.
    *
    * @param indices the array containing the indices of the faces added to the underlying data structure of this mesh.
    */

   public void addFaces(int[] indices) {

      for(int i = 0; i < indices.length; i ++) {

         handleCapacityOverload(indexedFaceVerticesArray);

         indexedFaceVerticesArray[indexedFaceVerticesArrayStop++] = indices[i];

      }

   }

   /**
    * Sets a vertex at the position specified by the integer argument in the underlying data structure of this mesh to the coordinates specified by the floating point arguments and returns the index of the next position in the vertex array
    * of this mesh.
    *
    * @param x the value that the x-coordinate of the indexed vertex in the underlying data structure of this mesh is set to.
    * @param y the value that the y-coordinate of the indexed vertex in the underlying data structure of this mesh is set to.
    * @param z the value that the z-coordinate of the indexed vertex in the underlying data structure of this mesh is set to.
    * @param index the index referring to the position in the vertex array whose coordinates are set.
    * @return the index of the position immediately following the vertex set in the underlying data structure of this mesh.
    */

   public int setVertex(float x, float y, float z, int index) {

      vertexArray[index++] = x;
      vertexArray[index++] = y;
      vertexArray[index++] = z;

      return index;

   }

   /**
    * Sets a u1 tangent at the position specified by the integer argument in the underlying data structure of this mesh to the coordinates specified by the floating point arguments and returns the index of the next position in the vertex normal array
    * of this mesh.
    *
    * @param tx the value that the x-coordinate of the indexed u1 tangent in the underlying data structure of this mesh is set to.
    * @param ty the value that the y-coordinate of the indexed u1 tangent in the underlying data structure of this mesh is set to.
    * @param tz the value that the z-coordinate of the indexed u1 tangent in the underlying data structure of this mesh is set to.
    * @param index the index referring to the position in the vertex u1 tangent array whose coordinates are set.
    * @return the index of the position immediately following the vertex u1 tangent set in the underlying data structure of this mesh.
    */

   public int setTangentU1(float tx, float ty, float tz, int index) {

      tangentU1Array[index++] = tx;
      tangentU1Array[index++] = ty;
      tangentU1Array[index++] = tz;

      return index;

   }

   /**
    * Sets a u2 tangent at the position specified by the integer argument in the underlying data structure of this mesh to the coordinates specified by the floating point arguments and returns the index of the next position in the vertex normal array
    * of this mesh.
    *
    * @param tx the value that the x-coordinate of the indexed u2 tangent in the underlying data structure of this mesh is set to.
    * @param ty the value that the y-coordinate of the indexed u2 tangent in the underlying data structure of this mesh is set to.
    * @param tz the value that the z-coordinate of the indexed u2 tangent in the underlying data structure of this mesh is set to.
    * @param index the index referring to the position in the vertex u2 tangent array whose coordinates are set.
    * @return the index of the position immediately following the vertex u2 tangent set in the underlying data structure of this mesh.
    */

   public int setTangentU2(float tx, float ty, float tz, int index) {

      tangentU2Array[index++] = tx;
      tangentU2Array[index++] = ty;
      tangentU2Array[index++] = tz;

      return index;

   }

   /**
    * Sets a normal at the position specified by the integer argument in the underlying data structure of this mesh to the coordinates specified by the floating point arguments and returns the index of the next position in the vertex normal array
    * of this mesh.
    *
    * @param nx the value that the x-coordinate of the indexed normal in the underlying data structure of this mesh is set to.
    * @param ny the value that the y-coordinate of the indexed normal in the underlying data structure of this mesh is set to.
    * @param nz the value that the z-coordinate of the indexed normal in the underlying data structure of this mesh is set to.
    * @param index the index referring to the position in the vertex normal array whose coordinates are set.
    * @return the index of the position immediately following the vertex normal set in the underlying data structure of this mesh.
    */

   public int setNormal(float nx, float ny, float nz, int index) {

      normalArray[index++] = nx;
      normalArray[index++] = ny;
      normalArray[index++] = nz;

      return index;

   }

   /**
    * Sets a texel at the position specified by the integer argument in the underlying data structure of this mesh to the coordinates specified by the floating point arguments and returns the index of the next position in the vertex texel array
    * of this mesh.
    *
    * @param u the value that the u-coordinate of the indexed texel in the underlying data structure of this mesh is set to.
    * @param v the value that the v-coordinate of the indexed texel in the underlying data structure of this mesh is set to.
    * @param index the index referring to the position in the vertex texel array whose coordinates are set.
    * @return the index of the position immediately following the texel set in the underlying data structure of this mesh.
    */

   public int setTexel(float u, float v, int index) {

      texelArray[index++] = u;
      texelArray[index++] = v;

      return index;

   }

   /**
    * Sets a color at the position specified by the integer argument in the underlying data structure of this mesh to the components specified by the byte arguments and returns the index of the next position in the vertex color array
    * of this mesh.
    *
    * @param r the value that the r-component of the indexed vertex color in the underlying data structure of this mesh is set to.
    * @param g the value that the g-component of the indexed vertex color in the underlying data structure of this mesh is set to.
    * @param b the value that the b-component of the indexed vertex color in the underlying data structure of this mesh is set to.
    * @param index the index referring to the position in the vertex color array whose rgb components are set.
    * @return the index of the position immediately following the vertex color set in the underlying data structure of this mesh.
    */

   public int setColor(byte r, byte g, byte b, int index) {

      colorArray[index++] = r;
      colorArray[index++] = g;
      colorArray[index++] = b;

      return index;

   }

   /**
    * Sets a face at the position specified by the integer argument to the indices specified by the integer array argument and returns the index of the next position in the indexed face array
    * of this mesh.
    *
    * @param indices the indices that the face in the underlying data structure of this mesh are set to.
    * @param index the index referring to the position in the indexed face array whose indices are set.
    * @return the index of the position immediately following the indices set in the underlying data structure of of this mesh.
    */

   public int setFace(int[] indices, int index) {

      for(int i = 0; i < meshPrimitiveSize; i++)
         indexedFaceVerticesArray[index++] = indices[i];

      return index;

   }

   /**
    * Sets the vertices, starting at the position specified by the integer argument, in the underlying data structure of this mesh to the array of coordinates specified by the floating point array argument
    * and returns the index of the position following the last vertex set in the underlying data structure of this mesh.
    *
    * @param vertices the collection of coordinates that the subset of vertices in the underlying data structure of this mesh are set to.
    * @param index the index referring to the first position in the vertex array whose coordinates are set.
    * @return the index of the position immediately following the last vertex set in the underlying data structure of this mesh.
    */

   public int setVertices(float[] vertices, int index) {

      for(int i = 0; i < vertices.length; i++)
         vertexArray[index++] = vertices[i];

      return index;

   }

   /**
    * Sets the u1 tangents, starting at the position specified by the integer argument, in the underlying data structure of this mesh to the array of coordinates specified by the floating point array argument
    * and returns the index of the position following the last u1 tangent set in the underlying data structure of this mesh.
    *
    * @param tangentsU1 the collection of coordinates that the subset of u1 tangents in the underlying data structure of this mesh are set to.
    * @param index the index referring to the first position in the vertex u1 tangent array whose coordinates are set.
    * @return the index of the position immediately following the last u1 tangent set in the underlying data structure of this mesh.
    */

   public int setTangentsU1(float[] tangentsU1, int index) {

      for(int i = 0; i < tangentsU1.length ; i++)
         tangentU1Array[index++] = tangentsU1[i];

      return index;

   }

   /**
    * Sets the u2 tangents, starting at the position specified by the integer argument, in the underlying data structure of this mesh to the array of coordinates specified by the floating point array argument
    * and returns the index of the position following the last u2 tangent set in the underlying data structure of this mesh.
    *
    * @param tangentsU2 the collection of coordinates that the subset of u2 tangents in the underlying data structure of this mesh are set to.
    * @param index the index referring to the first position in the vertex u2 tangent array whose coordinates are set.
    * @return the index of the position immediately following the last u2 tangent set in the underlying data structure of this mesh.
    */

   public int setTangentsU2(float[] tangentsU2, int index) {

      for(int i = 0; i < tangentsU2.length ; i++)
         tangentU2Array[index++] = tangentsU2[i];

      return index;

   }

   /**
    * Sets the normals, starting at the position specified by the integer argument, in the underlying data structure of this mesh to the array of coordinates specified by the floating point array argument
    * and returns the index of the position following the last normal set in the underlying data structure of this mesh.
    *
    * @param normals the collection of coordinates that the subset of normals in the underlying data structure of this mesh are set to.
    * @param index the index referring to the first position in the vertex normal array whose coordinates are set.
    * @return the index of the position immediately following the last normal set in the underlying data structure of this mesh.
    */

   public int setNormals(float[] normals, int index) {

      for(int i = 0; i < normals.length ; i++)
         normalArray[index++] = normals[i];

      return index;

   }

   /**
    * Sets the texels, starting at the position specified by the integer argument, in the underlying data structure of this mesh to the array of coordinates specified by the floating point array argument
    * and returns the index of the position following the last texel set in the underlying data structure of this mesh.
    *
    * @param texels the collection of coordinates that the subset of texels in the underlying data structure of this mesh are set to.
    * @param index the index referring to the first position in the vertex texel array whose coordinates are set.
    * @return the index of the position immediately following the last texel set in the underlying data structure of this mesh.
    */

   public int setTexels(float[] texels, int index) {

      for(int i = 0; i < texels.length; i++)
         texelArray[index++] = texels[i];

      return index;

   }

   /**
    * Sets the colors, starting at the position specified by the integer argument, in the underlying data structure of this mesh to the array of components specified by the byte array argument
    * and returns the index of the position following the last color set in the underlying data structure of this mesh.
    *
    * @param colors the collection of components that the subset of vertex colors in the underlying data structure of this mesh are set to.
    * @param index the index referring to the first position in the vertex color array whose components are set.
    * @return the index of the position immediately following the last vertex color set in the underlying data structure of this mesh.
    */

   public int setColors(byte[] colors, int index) {

      for(int i = 0; i < colors.length; i++)
         colorArray[index++] = colors[i];

      return index;

   }

   /**
    * Sets the faces, starting at the position specified by the integer argument, in the underlying data structure of this mesh to the array of coordinates specified by the integer array argument
    * and returns the index of the position following the last face set in the underlying data structure of this mesh.
    *
    * @param indices the collection of indices that the subset of faces in the underlying data structure of this mesh are set to.
    * @param index the index referring to the first position in the indexed face array whose coordinates are set.
    * @return the index of the position immediately following the last face set in the underlying data structure of this mesh.
    */

   public int setFaces(int[] indices, int index) {

      for(int i = 0; i < indices.length; i++)
         indexedFaceVerticesArray[index++] = indices[i];

      return index;

   }

   /**
    * Retrieves the vertex coordinates of the vertex specified by the integer argument in the underlying data structure of this mesh and places the
    * coordinates at the address specified by the floating point array argument.
    *
    * @param vertex the address at which the vertex coordinates are stored.
    * @param index the index of the vertex in the underlying data structure of this mesh whose coordinates are retrieved.
    */

   public void getVertex(float[] vertex, int index) {

      vertex[0] = vertexArray[index++];
      vertex[1] = vertexArray[index++];
      vertex[2] = vertexArray[index++];

   }

   /**
    * Retrieves the u1 tangent coordinates of the tangent specified by the integer argument in the underlying data structure of this mesh and places the
    * coordinates at the address specified by the floating point array argument.
    *
    * @param tangentU1 the address at which the u1 tangent coordinates are stored.
    * @param index the index of the vertex in the underlying data structure of this mesh whose coordinates are retrieved.
    */

   public void getTangentU1(float[] tangentU1, int index) {

      tangentU1[0] = tangentU1Array[index++];
      tangentU1[1] = tangentU1Array[index++];
      tangentU1[2] = tangentU1Array[index++];

   }

   /**
    * Retrieves the u2 tangent coordinates of the tangent specified by the integer argument in the underlying data structure of this mesh and places the
    * coordinates at the address specified by the floating point array argument.
    *
    * @param tangentU2 the address at which the u2 tangent coordinates are stored.
    * @param index the index of the vertex in the underlying data structure of this mesh whose coordinates are retrieved.
    */

   public void getTangentU2(float[] tangentU2, int index) {

      tangentU2[0] = tangentU2Array[index++];
      tangentU2[1] = tangentU2Array[index++];
      tangentU2[2] = tangentU2Array[index++];

   }

   /**
    * Retrieves the coordinates of the normal specified by the integer argument in the underlying data structure of this mesh and places the
    * coordinates at the address specified by the floating point array argument.
    *
    * @param normal the address at which the normal coordinates are stored.
    * @param index the index of the vertex in the underlying data structure of this mesh whose coordinates are retrieved.
    */

   public void getNormal(float[] normal, int index) {

      normal[0] = normalArray[index++];
      normal[1] = normalArray[index++];
      normal[2] = normalArray[index++];

   }

   /**
    * Retrieves the texel coordinates of the texel specified by the integer argument in the underlying data structure of this mesh and places the
    * coordinates at the address specified by the floating point array argument.
    *
    * @param texel the address at which the texel coordinates are stored.
    * @param index the index of the texel in the underlying data structure of this mesh whose coordinates are retrieved.
    */

   public void getTexel(float[] texel, int index) {

      texel[0] = texelArray[index++];
      texel[1] = texelArray[index++];

   }

   /**
    * Retrieves the color components of the color specified by the integer argument in the underlying data structure of this mesh and places the
    * components at the address specified by the byte array argument.
    *
    * @param color the address at which the color components are stored.
    * @param index the index of the color in the underlying data structure of this mesh whose components are retrieved.
    */

   public void getColor(byte[] color, int index) {

      color[0] = colorArray[index++];
      color[1] = colorArray[index++];
      color[2] = colorArray[index++];

   }

   /**
    * Retrieves the face indices of the face specified by the integer argument in the underlying data structure of this mesh and places the
    * indices at the address specified by the integer array argument.
    *
    * @param face the address at which the face indices are stored.
    * @param index the index of the face in the underlying data structure of this mesh whose indices are retrieved.
    */

   public void getFace(int[] indices, int index) {

      for(int i = 0; i < meshPrimitiveSize; i++)
	     indices[i] = indexedFaceVerticesArray[index++];

   }

   /**
    * Retrieves the vertex coordinates of a set of successive vertices, starting at the position specified by the integer argument, in the underlying data structure of this mesh and places
    * the coordinates at the address specified by the floating point array argument
    *
    * @param vertices the address at which the vertices coordinates are stored.
    * @param index the index of the first vertex in the underlying data structure of this mesh whose coordinates are retrieved.
    */

   public void getVertices(float[] vertices, int index) {

      for(int i = 0; i < vertices.length; i++)
         vertices[i] = vertexArray[index++];

   }

   /**
    * Retrieves the u1 tangent coordinates of a set of successive u1 tangents, starting at the position specified by the integer argument, in the underlying data structure of this mesh and places
    * the coordinates at the address specified by the floating point array argument.
    *
    * @param tangentsU1 the address at which the u1 tangents coordinates are stored.
    * @param index the index of the first u1 tangent in the underlying data structure of this mesh whose coordinates are retrieved.
    */

   public void getTangentsU1(float[] tangentsU1, int index) {

      for(int i = 0; i < tangentsU1.length; i++)
         tangentsU1[i] = tangentU1Array[index++];

   }

   /**
    * Retrieves the u2 tangent coordinates of a set of successive u2 tangents, starting at the position specified by the integer argument, in the underlying data structure of this mesh and places
    * the coordinates at the address specified by the floating point array argument.
    *
    * @param tangentsU2 the address at which the u2 tangents coordinates are stored.
    * @param index the index of the first u2 tangent in the underlying data structure of this mesh whose coordinates are retrieved.
    */

   public void getTangentsU2(float[] tangentsU2, int index) {

      for(int i = 0; i < tangentsU2.length; i++)
         tangentsU2[i] = tangentU2Array[index++];

   }

   /**
    * Retrieves the normal coordinates of a set of successive normals, starting at the position specified by the integer argument, in the underlying data structure of this mesh and places
    * the coordinates at the address specified by the floating point array argument.
    *
    * @param normals the address at which the normals coordinates are stored.
    * @param index the index of the first normal in the underlying data structure of this mesh whose coordinates are retrieved.
    */

   public void getNormals(float[] normals, int index) {

      for(int i = 0; i < normals.length; i++)
         normals[i] = normalArray[index++];

   }

   /**
    * Retrieves the texel coordinates of a set of successive texels, starting at the position specified by the integer argument, in the underlying data structure of this mesh and places
    * the coordinates at the address specified by the floating point array argument.
    *
    * @param texels the address at which the texels coordinates are stored.
    * @param index the index of the first texel in the underlying data structure of this mesh whose coordinates are retrieved.
    */

   public void getTexels(float[] texels, int index) {

      for(int i = 0; i < texels.length; i++)
         texels[i] = texelArray[index++];

   }

   /**
    * Retrieves the color components of a set of successive colors, starting at the position specified by the integer argument, in the underlying data structure of this mesh and places
    * the coordinates at the address specified by the byte array argument.
    *
    * @param colors the address at which the color components are stored.
    * @param index the index of the first color in the underlying data structure of this mesh whose components are retrieved.
    */

   public void getColors(byte[] colors, int index) {

      for(int i = 0; i < colors.length; i++)
         colors[i] = colorArray[index++];

   }

   /**
    * Retrieves the face indices of a set of successive faces, starting at the position specified by the integer argument, in the underlying data structure of this mesh and places
    * the indices at the address specified by the integer array argument.
    *
    * @param indices the address at which the face indices are stored.
    * @param index the index of the first face index in the underlying data structure of this mesh whose faces are retrieved.
    */

   public void getFaces(int[] indices, int index) {

      for(int i = 0; i < indices.length; i++)
         indices[i] = indexedFaceVerticesArray[index++];

   }

   /**
    * Removes the vertex specified by the integer argument from this mesh.
    *
    * @param index the index of the vertex array specifying the vertex to be removed from this mesh.
    * @param vertex the array to store the coordinates of the removed vertex.
    */

   public void removeVertex(int index, float[] vertex) {

      vertex[0] = vertexArray[index + 0];
      vertex[1] = vertexArray[index + 1];
      vertex[2] = vertexArray[index + 2];

      System.arraycopy(vertexArray, index + SPACE_POINT_STRIDE , vertexArray, index, vertexArrayStop - index);

      vertexArrayStop -= SPACE_POINT_STRIDE ;

   }

   /**
    * Removes the u1 tangent specified by the integer argument from this mesh.
    *
    * @param index the index of the vertex u1 tangent array specifying the u1 tangent to be removed from this mesh.
    * @param tangentU1 the array to store the coordinates of the removed u1 tangent.
    */

   public void removeTangentU1(int index, float[] tangentU1) {

      tangentU1[0] = tangentU1Array[index + 0];
      tangentU1[1] = tangentU1Array[index + 1];
      tangentU1[2] = tangentU1Array[index + 2];

      System.arraycopy(tangentU1Array, index + TANGENT_STRIDE, tangentU1Array, index, tangentU1ArrayStop - index);

      tangentU1ArrayStop -= TANGENT_STRIDE;

   }

   /**
    * Removes the u2 tangent specified by the integer argument from this mesh.
    *
    * @param index the index of the vertex u2 tangent array specifying the u2 tangent to be removed from this mesh.
    * @param tangentU2 the array to store the coordinates of the removed u2 tangent.
    */

   public void removeTangentU2(int index, float[] tangentU2) {

      tangentU2[0] = tangentU2Array[index + 0];
      tangentU2[1] = tangentU2Array[index + 1];
      tangentU2[2] = tangentU2Array[index + 2];

      System.arraycopy(tangentU2Array, index + TANGENT_STRIDE, tangentU2Array, index, tangentU2ArrayStop - index);

      tangentU2ArrayStop -= TANGENT_STRIDE;

   }

   /**
    * Removes the normal specified by the integer argument from this mesh.
    *
    * @param index the index of the vertex normal array specifying the normal to be removed from this mesh.
    * @param normal the array to store the coordinates of the removed normal.
    */

   public void removeNormal(int index, float[] normal) {

      normal[0] = normalArray[index + 0];
      normal[1] = normalArray[index + 1];
      normal[2] = normalArray[index + 2];

      System.arraycopy(normalArray, index + NORMAL_STRIDE, normalArray, index, normalArrayStop - index);

      normalArrayStop -= NORMAL_STRIDE;

   }

   /**
    * Removes the texel specified by the integer argument from this mesh.
    *
    * @param index the index of the vertex texel array specifying the texel to be removed from this mesh.
    * @param texel the array to store the coordinates of the removed texel.
    */

   public void removeTexel(int index, float[] texel) {

      texel[0] = texelArray[index + 0];
      texel[1] = texelArray[index + 1];

      System.arraycopy(texelArray, index + TEXEL_STRIDE, texelArray, index, texelArrayStop - index);

      texelArrayStop -= TEXEL_STRIDE;

   }

   /**
    * Removes the color specified by the integer argument from this mesh.
    *
    * @param index the index of the vertex color array specifying the color to be removed from this mesh.
    * @param color the array to store the components of the removed color.
    */

   public void removeColor(int index, byte[] color) {

      color[0] = colorArray[index + 0];
      color[1] = colorArray[index + 1];
      color[2] = colorArray[index + 2];

      System.arraycopy(colorArray, index + COLOR_STRIDE, colorArray, index, colorArrayStop - index);

      colorArrayStop -= COLOR_STRIDE;

   }

   /**
    * Removes the face specified by the integer argument from this mesh.
    *
    * @param index the index of the face array specifying the face to be removed from this mesh.
    * @param indices the array to store the indices of the removed face.
    */

   public void removeFace(int index, float[] indices) {

      for(int i = 0; i < indices.length; i++)
         indices[i] = indexedFaceVerticesArray[i];

      System.arraycopy(indexedFaceVerticesArray, index + indices.length, indexedFaceVerticesArray, index, indexedFaceVerticesArrayStop - index);

      indexedFaceVerticesArrayStop -= indices.length;

   }

   /**
    * Removes the vertices at the position in the underlying data structure of this meshfrom this mesh.
    *
    * @param indices the indices specifying the vertices in the vertex array to be removed from this mesh.
    * @param vertices the array to store the coordinates of the removed vertices.
    */

   public void removeVertices(int[] indices, float[] vertices) {

      for(int i = 0; i < indices.length; i++)
         vertices[i] = vertexArray[i];

      for(int i = 0; i < indices.length; i++)
         System.arraycopy(vertexArray, indices[i] + SPACE_POINT_STRIDE , vertexArray, indices[i], vertexArrayStop - indices[i]);

      vertexArrayStop -= SPACE_POINT_STRIDE ;

   }

   /**
    * Removes the u1 tangents specified by the integer array argument from this mesh.
    *
    * @param indices the indices specifying the u1 tangents in the vertex u1 tangent array to be removed from this mesh.
    * @param tangentsU1 the array to store the coordinates of the removed u1 tangents.
    */

   public void removeTangentsU1(int[] indices, float[] tangentsU1) {

      for(int i = 0; i < indices.length; i++) {

		 tangentsU1[i + 0] = tangentU1Array[indices[i] + 0];
         tangentsU1[i + 1] = tangentU1Array[indices[i] + 1];
         tangentsU1[i + 2] = tangentU1Array[indices[i] + 2];

      }

      for(int i = 0; i < indices.length; i++)
         System.arraycopy(tangentU1Array, indices[i] + TANGENT_STRIDE, tangentU1Array, indices[i], tangentU1ArrayStop - indices[i]);

      tangentU1ArrayStop -= TANGENT_STRIDE;

   }

   /**
    * Removes the u2 tangents specified by the integer array argument from this mesh.
    *
    * @param indices the indices specifying the u2 tangents in the vertex u2 tangent array to be removed from this mesh.
    * @param tangentsU2 the array to store the coordinates of the removed u2 tangents.
    */

   public void removeTangentsU2(int[] indices, float[] tangentsU2) {

      for(int i = 0; i < indices.length; i++) {

		 tangentsU2[i + 0] = tangentU2Array[indices[i] + 0];
         tangentsU2[i + 1] = tangentU2Array[indices[i] + 1];
         tangentsU2[i + 2] = tangentU2Array[indices[i] + 2];

      }

      for(int i = 0; i < indices.length; i++)
         System.arraycopy(tangentU2Array, indices[i] + TANGENT_STRIDE, tangentU2Array, indices[i], tangentU2ArrayStop - indices[i]);

      tangentU2ArrayStop -= TANGENT_STRIDE;

   }

   /**
    * Removes the normals specified by the integer array argument from this mesh.
    *
    * @param indices the indices specifying the normals in the vertex normal array to be removed from this mesh.
    * @param normals the array to store the coordinates of the removed normals.
    */

   public void removeNormals(int[] indices, float[] normals) {

      for(int i = 0; i < indices.length; i++) {

		 normals[i + 0] = normalArray[indices[i] + 0];
         normals[i + 1] = normalArray[indices[i] + 1];
         normals[i + 2] = normalArray[indices[i] + 2];

      }

      for(int i = 0; i < indices.length; i++)
         System.arraycopy(normalArray, indices[i] + NORMAL_STRIDE, normalArray, indices[i], normalArrayStop - indices[i]);

      normalArrayStop -= NORMAL_STRIDE;

   }

   /**
    * Removes the texels specified by the integer array argument from this mesh.
    *
    * @param indices the indices specifying the texels in the vertex texel array to be removed from this mesh.
    * @param texels the array to store the coordinates of the removed texels.
    */

   public void removeTexels(int[] indices, float[] texels) {

      for(int i = 0; i < indices.length; i++) {

		 texels[i + 0] = texelArray[indices[i] + 0];
         texels[i + 1] = texelArray[indices[i] + 1];

      }

      for(int i = 0; i < indices.length; i++)
         System.arraycopy(texelArray, indices[i] + TEXEL_STRIDE, texelArray, indices[i], texelArrayStop - indices[i]);

      texelArrayStop -= TEXEL_STRIDE;

   }

   /**
    * Removes the colors specified by the integer array argument from this mesh.
    *
    * @param indices the indices specifying the colors in the vertex color array to be removed from this mesh.
    * @param colors the array to store the components of the removed colors.
    */

   public void removeColors(int[] indices, byte[] colors) {

      for(int i = 0; i < indices.length; i++) {

		 colors[i + 0] = colorArray[indices[i] + 0];
         colors[i + 1] = colorArray[indices[i] + 1];
         colors[i + 2] = colorArray[indices[i] + 2];

      }

      for(int i = 0; i < indices.length; i++)
         System.arraycopy(colorArray, indices[i] + COLOR_STRIDE, colorArray, indices[i], colorArrayStop - indices[i]);

      colorArrayStop -= COLOR_STRIDE;

   }

   /**
    * Removes the faces specified by the integer array argument from this mesh.
    *
    * @param indices the indices specifying the indexed faces in the indexed face array to be removed from this mesh.
    * @param faces the array to store the indicess of the removed faces.
    */

   public void removeFaces(int[] indices, int[] faces) {

      for(int i = 0; i < indices.length; i++)
         for(int j = 0; j < meshPrimitiveSize; j++)
            faces[i] = indexedFaceVerticesArray[indices[i] + j];

      for(int i = 0; i < indices.length; i++)
         System.arraycopy(indexedFaceVerticesArray, indices[i] + meshPrimitiveSize, indexedFaceVerticesArray, indices[i], indexedFaceVerticesArrayStop - indices[i]);

      indexedFaceVerticesArrayStop -= indices.length;

   }

   public void faceNormal(float[] normal, int[] faceIndices) {

	   float x0 = vertexArray[faceIndices[0] * SPACE_POINT_STRIDE];
	   float y0 = vertexArray[faceIndices[0] * SPACE_POINT_STRIDE + 1];
	   float z0 = vertexArray[faceIndices[0] * SPACE_POINT_STRIDE + 2];

	   float x1 = vertexArray[faceIndices[1] * SPACE_POINT_STRIDE];
	   float y1 = vertexArray[faceIndices[1] * SPACE_POINT_STRIDE + 1];
	   float z1 = vertexArray[faceIndices[1] * SPACE_POINT_STRIDE + 2];

	   float x2 = vertexArray[faceIndices[2] * SPACE_POINT_STRIDE];
	   float y2 = vertexArray[faceIndices[2] * SPACE_POINT_STRIDE + 1];
	   float z2 = vertexArray[faceIndices[2] * SPACE_POINT_STRIDE + 2];

	   float vx = x1 - x0;
	   float vy = y1 - y0;
	   float vz = z1 - z0;

	   float wx = x2 - x0;
	   float wy = y2 - y0;
	   float wz = z2 - z0;

	   normal[0] = (vy * wz - vz * wy);
	   normal[1] = (vz * wx - vx * wz);
	   normal[2] = (vx * wy - vy * wx);

	  // float magnitude = (float)Math.sqrt(Math.pow(normal[0], 2) + Math.pow(normal[1], 2) + Math.pow(normal[2], 2));

	  // normal[0] /= magnitude;
	  // normal[1] /= magnitude;
	  // normal[2] /= magnitude;

   }

   public void resetVertexNormals() {

	   	int n = normalArray.length;

	   	this.normalArray = new float[n];

		int numFaces = indexedFaceVerticesArrayStop / 3;

		for(int k = 0;  k < numFaces; k++) {

			int[] faceIndices = new int[3];
			getFace(faceIndices, 3 * k);

			float[] normal = new float[3];
			faceNormal(normal, faceIndices);

//System.out.println(normal[0] + " " +  normal[1] + " " + normal[2]);

			addCoordinates(normalArray, normal, faceIndices[0]);
			addCoordinates(normalArray, normal, faceIndices[1]);
			addCoordinates(normalArray, normal, faceIndices[2]);

		}

		normalizeCoordinates(normalArray, normalArrayStop);

	}

	public void addCoordinates(float[] coordinateArray, float[] coordinates, int index) {

		for(int k = 0; k < coordinates.length; k++)
			coordinateArray[3 * index + k ] += coordinates[k];

	}

	public void normalizeCoordinates(float[] coordinateArray, int coordinateArrayStop) {

		int numVectors = coordinateArrayStop / 3;

		for(int k = 0; k < numVectors; k++) {

			float magnitude =(float)Math.sqrt(Math.pow(coordinateArray[3 * k], 2) + Math.pow(coordinateArray[3 * k + 1], 2) + Math.pow(coordinateArray[3 * k + 2], 2));

			coordinateArray[3 * k] /= magnitude;
			coordinateArray[3 * k + 1] /= magnitude;
			coordinateArray[3 * k + 2] /= magnitude;

		}

	}

   /**
    * Converts the indexed faces array of this mesh into an indexed array of triangular faces.
    */

   public void optimize() {

	  if(meshPrimitiveSize == 4) {

	     int[] optimizedIndices = new int[DEFAULT_FACE_CAPACITY];

	     for(int i = 0; i < indexedFaceVerticesArrayStop; i += meshPrimitiveSize) {

	        optimizedIndices[3 * i / 2 + 0] = indexedFaceVerticesArray[i + 0];
	        optimizedIndices[3 * i / 2 + 1] = indexedFaceVerticesArray[i + 1];
	        optimizedIndices[3 * i / 2 + 2] = indexedFaceVerticesArray[i + 2];

	        optimizedIndices[3 * i / 2 + 3] = indexedFaceVerticesArray[i + 2];
	        optimizedIndices[3 * i / 2 + 4] = indexedFaceVerticesArray[i + 3];
	        optimizedIndices[3 * i / 2 + 5] = indexedFaceVerticesArray[i + 0];

	     }

         indexedFaceVerticesArray = optimizedIndices;

         indexedFaceVerticesArrayStop = 3 * indexedFaceVerticesArrayStop / 2;

         meshPrimitiveSize = 3;

      }

   }

   /**
    * Checks which underlying array data structure of this mesh the object argument is a reference to and resizes the array if it's capacity
    * had been exceeded, copying the elements of the underlying array data structure into a new array with twice the capacity, and setting the underlying
    * array data structure to the new array.
    *
    * @param source the underlying array data structure that is checked for resizing.
    */

   protected void handleCapacityOverload(Object source) {

      if(source == vertexArray && vertexArrayStop >= vertexArray.length) {

	     vertexArray = new float[2 * vertexArray.length];
	     System.arraycopy(source, 0, vertexArray, 0, vertexArray.length / 2);

      }

      else if(source == tangentU1Array && tangentU1ArrayStop >= tangentU1Array.length) {

	     tangentU1Array = new float[2 * tangentU1Array.length];
	     System.arraycopy(source, 0, tangentU1Array, 0, tangentU1Array.length / 2);

      }

      else if(source == tangentU2Array && tangentU2ArrayStop >= tangentU2Array.length) {

	     tangentU2Array = new float[2 * tangentU2Array.length];
	     System.arraycopy(source, 0, tangentU2Array, 0, tangentU2Array.length / 2);

      }

      else if(source == normalArray && normalArrayStop >= normalArray.length) {

	     normalArray = new float[2 * normalArray.length];
	     System.arraycopy(source, 0, normalArray, 0, normalArray.length / 2);

      }

      else if(source == texelArray && texelArrayStop >= texelArray.length) {

	     texelArray = new float[2 * texelArray.length];
	     System.arraycopy(source, 0, texelArray, 0, texelArray.length / 2);

      }

      else if(source == colorArray && colorArrayStop >= colorArray.length) {

	     colorArray = new byte[2 * colorArray.length];
	     System.arraycopy(source, 0, colorArray, 0, colorArray.length / 2);

      }

      else if(source == indexedFaceVerticesArray && indexedFaceVerticesArrayStop >= indexedFaceVerticesArray.length) {

	     indexedFaceVerticesArray = new int[2 * indexedFaceVerticesArray.length];
	     System.arraycopy(source, 0, indexedFaceVerticesArray, 0, indexedFaceVerticesArray.length / 2);

      }

   }

   /**
    * Returns the capacity of the vertex array in the underlying data structure of this mesh.
    *
    * @return the capacity of the vertex array in the underlying data structure of this mesh.
    */

   public int getVertexArrayCapacity() { return vertexArray.length; }

   /**
    * Returns the capacity of the vertex u1 tangent array in the underlying data structure of this mesh.
    *
    * @return the capacity of the vertex u1 tangent array in the underlying data structure of this mesh.
    */

   public int getTangentU1ArrayCapacity() { return tangentU1Array.length; }

   /**
    * Returns the capacity of the vertex u2 tangent array in the underlying data structure of this mesh.
    *
    * @return the capacity of the vertex u2 tangent array in the underlying data structure of this mesh.
    */

   public int getTangentU2ArrayCapacity() { return tangentU2Array.length; }

   /**
    * Returns the capacity of the vertex normal array in the underlying data structure of this mesh.
    *
    * @return the capacity of the vertex normal array in the underlying data structure of this mesh.
    */

   public int getNormalArrayCapacity() { return normalArray.length; }

   /**
    * Returns the capacity of the vertex texel array in the underlying data structure of this mesh.
    *
    * @return the capacity of the vertex texel array in the underlying data structure of this mesh.
    */

   public int getTexelArrayCapacity() { return texelArray.length; }

   /**
    * Returns the capacity of the vertex color array in the underlying data structure of this mesh.
    *
    * @return the capacity of the vertex color array in the underlying data structure of this mesh.
    */

   public int getColorArrayCapacity() { return colorArray.length; }

   /**
    * Returns the capacity of the indexed face array in the underlying data structure of this mesh.
    *
    * @return the capacity of the indexed face array in the underlying data structure of this mesh.
    */

   public int getIndexedFaceVerticesArrayCapacity() { return indexedFaceVerticesArray.length; }

   /**
    * Returns the index of the element in the vertex array one element past the last vertex in this mesh.
    *
    * @return the index of the element in the vertex array one element past the last vertex in this mesh.
    */

   public int getVertexArrayStop() { return vertexArrayStop; }

   /**
    * Returns the index of the element in the vertex u1 tangent array one element past the last vertex u1 tangent in this mesh.
    *
    * @return the index of the element in the vertex u1 tangent array one element past the last vertex u1 tangent in this mesh.
    */

   public int getTangentU1ArrayStop() { return tangentU1ArrayStop; }

   /**
    * Returns the index of the element in the vertex u2 tangent array one element past the last vertex u2 tangent in this mesh.
    *
    * @return the index of the element in the vertex u2 tangent array one element past the last vertex u2 tangent in this mesh.
    */

   public int getTangentU2ArrayStop() { return tangentU2ArrayStop; }

   /**
    * Returns the index of the element in the vertex normal array one element past the last vertex normal in this mesh.
    *
    * @return the index of the element in the vertex normal array one element past the last vertex normal in this mesh.
    */

   public int getNormalArrayStop() { return normalArrayStop; }

   /**
    * Returns the index of the element in the vertex texel array one element past the last vertex texel in this mesh.
    *
    * @return the index of the element in the vertex texel array one element past the last vertex texel in this mesh.
    */

   public int getTexelArrayStop() { return texelArrayStop; }

   /**
    * Returns the index of the element in the vertex color array one element past the last vertex color in this mesh.
    *
    * @return the index of the element in the vertex color array one element past the last vertex color in this mesh.
    */

   public int getColorArrayStop() { return colorArrayStop; }

   /**
    * Returns the index of the element in the indexed face array one element past the last face in this mesh.
    *
    * @return the index of the element in the indexed face array one element past the last face in this mesh.
    */

   public int getIndexedFaceVerticesArrayStop() { return indexedFaceVerticesArrayStop; }

   /**
    * Retrieves the address of the vertex array associated with this mesh.
    *
    * @param verticesAddressArray the array of arrays to store the retrieved vertex array.
    */

   public void getVerticesAddress(float[][] verticesAddressArray) { verticesAddressArray[0] = vertexArray; }

   /**
    * Retrieves the address of the vertex u1 tangent array associated with this mesh.
    *
    * @param tangentsU1AddressArray the array of arrays to store the retrieved vertex u1 tangent array.
    */

   public void getTangentsU1Address(float[][] tangentsU1AddressArray) { tangentsU1AddressArray[0] = tangentU1Array; }

   /**
    * Retrieves the address of the vertex u2 tangent array associated with this mesh.
    *
    * @param tangentsU1AddressArray the array of arrays to store the retrieved vertex u2 tangent array.
    */

   public void getTangentsU2Address(float[][] tangentsU2AddressArray) { tangentsU2AddressArray[0] = tangentU2Array; }

   /**
    * Retrieves the address of the vertex u2 tangent array associated with this mesh.
    *
    * @param tangentsU1AddressArray the array of arrays to store the retrieved vertex u2 tangent array.
    */

   public void getNormalsAddress(float[][] normalsAddressArray) { normalsAddressArray[0] = normalArray; }

   /**
    * Retrieves the address of the vertex texel array associated with this mesh.
    *
    * @param texelsAddressArray the array of arrays to store the retrieved vertex texel array.
    */

   public void getTexelsAddress(float[][] texelsAddressArray) { texelsAddressArray[0] = texelArray; }

   /**
    * Retrieves the address of the vertex color array associated with this mesh.
    *
    * @param colorsAddressArray the array of arrays to store the retrieved vertex color array.
    */

   public void getColorsAddress(byte[][] colorsAddressArray) { colorsAddressArray[0] = colorArray; }

   /**
    * Retrieves the address of the indexed face array associated with this mesh.
    *
    * @param indexedFaceVerticesAddressArray the array of arrays to store the retrieved indexed face array.
    */

   public void getIndexedFaceVerticesAddress(int[][] indexedFaceVerticesAddressArray) { indexedFaceVerticesAddressArray[0] = indexedFaceVerticesArray; }

   /**
    * Returns the number of array elements per face in the indexed face array of this mesh.
    *
    * @return the number of array elements per face in the indexed face array of this mesh.
    */

   public int getSize() { return meshPrimitiveSize; }

}