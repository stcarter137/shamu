package shamu.media.render;

/** Class representing a floating point 4 X 4 matrix containing standard matrix operations.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class Matrix4D implements java.io.Serializable {

   /**
    * The elements in column major order of this matrix.
    */

   protected float[][] elements = {{1.0f, 0.0f, 0.0f, 0.0f}, {0.0f, 1.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 1.0f, 0.0f}, {0.0f, 0.0f, 0.0f, 1.0f}};

   /**
    * Creates a 4 X 4 perspective projection matrix determined by the floating point arguments.
    *
    * @param nearPlane the value that determines the near plane defining the perspective projection matrix.
    * @param farPlane the value that determines the far plane defining the perspective projection matrix.
    * @param fieldOfView the value that determines the field of view defining the perspective projection matrix.
    * @param aspectRatio the value that determines the aspect ratio defining the perspective projection matrix.
    * @return a 4 X 4 perspective projection matrix determined by the floating point arguments.
    */

   public static Matrix4D createPerspectiveProjection(float nearPlane, float farPlane, float fieldOfView, float aspectRatio) {

      Matrix4D matrix = new Matrix4D();

      matrix.setElement(0, 0, 1.0f);
      matrix.setElement(0, 1, 0.0f);
      matrix.setElement(0, 2, 0.0f);
      matrix.setElement(0, 3, 0.0f);

      matrix.setElement(1, 0, 0.0f);
      matrix.setElement(1, 1, aspectRatio);
      matrix.setElement(1, 2, 0.0f);
      matrix.setElement(1, 3, 0.0f);

      matrix.setElement(2, 0, 0.0f);
      matrix.setElement(2, 1, 0.0f);
      matrix.setElement(2, 2, (float)(nearPlane * Math.tan(fieldOfView / 2.0f) * farPlane / (nearPlane * (farPlane - nearPlane))));
      matrix.setElement(2, 3, (float)(-nearPlane * Math.tan(fieldOfView / 2.0f) * farPlane / (farPlane - nearPlane)));

      matrix.setElement(3, 0, 0.0f);
      matrix.setElement(3, 1, 0.0f);
      matrix.setElement(3, 2, (float)(nearPlane * Math.tan(fieldOfView / 2.0f) / nearPlane));
      matrix.setElement(3, 3, 0.0f);

      return matrix;

   }

   /**
    * Creates a 4 X 4 orthographic projection matrix determined by the floating point arguments.
    *
    * @param nearPlane the value that determines the near plane defining the orthographic projection matrix.
    * @param farPlane the value that determines the far plane defining the orthographic projection matrix.
    * @param fieldOfView the value that determines the field of view defining the orthographic projection matrix.
    * @param aspectRatio the value that determines the aspect ratio defining the orthographic projection matrix.
    * @return a 4 X 4 orthographic projection matrix determined by the floating point arguments.
    */

   public static Matrix4D createOrthographicProjection(float nearPlane, float farPlane, float fieldOfView, float aspectRatio) {

      Matrix4D matrix = new Matrix4D();

      matrix.setElement(1, 0, 0.0f);
      matrix.setElement(1, 1, 2 / (2 * nearPlane * (float)Math.tan(fieldOfView / 2.0f)));
      matrix.setElement(1, 2, 0.0f);
      matrix.setElement(1, 3, 0.0f);

      matrix.setElement(0, 0, matrix.getElement(1, 1) / aspectRatio);
      matrix.setElement(0, 1, 0.0f);
      matrix.setElement(0, 2, 0.0f);
      matrix.setElement(0, 3, 0.0f);

      matrix.setElement(2, 0, 0.0f);
      matrix.setElement(2, 1, 0.0f);
      matrix.setElement(2, 2, 1 / (farPlane - nearPlane));
      matrix.setElement(2, 3, nearPlane / (nearPlane - farPlane));

      matrix.setElement(3, 0, 0.0f);
      matrix.setElement(3, 1, 0.0f);
      matrix.setElement(3, 2, 0.0f);
      matrix.setElement(3, 3, 1.0f);

      return matrix;

   }

   /**
    * Constructs a 4 X 4 matrix whose elements are the identity matrix.
    */

   public Matrix4D() {}

   /**
    * Constructs a 4 X 4 matrix whose 3 X 3 rotation-scale matrix component is defined by the matrix argument and whose translation component
    * is defined by the floating point arguments.
    *
    * @param matrix the 3 X 3 matrix defining the rotation-scale matrix component of the 4 X 4 matrix.
    * @param translateX the vector component defining the x translation component of the 4 X 4 matrix.
    * @param translateY the vector component defining the y translation component of the 4 X 4 matrix.
    * @param translateZ the vector component defining the z translation component of the 4 X 4 matrix.
    */

   public Matrix4D(Matrix3D matrix, float translateX, float translateY, float translateZ) {

      elements[0][0] = matrix.getElement(0, 0);
      elements[0][1] = matrix.getElement(1, 0);
      elements[0][2] = matrix.getElement(2, 0);

      elements[1][0] = matrix.getElement(0, 1);
      elements[1][1] = matrix.getElement(1, 1);
      elements[1][2] = matrix.getElement(2, 1);

      elements[2][0] = matrix.getElement(0, 2);
      elements[2][1] = matrix.getElement(1, 2);
      elements[2][2] = matrix.getElement(2, 2);

      elements[3][0] = translateX;
      elements[3][1] = translateY;
      elements[3][2] = translateZ;

      elements[0][3] = 0;
      elements[1][3] = 0;
      elements[2][3] = 0;

      elements[3][3] = 1;

   }

   /**
    * Constructs a 4 X 4 matrix whose 3 X 3 rotation-scale matrix component is defined by the quaternion argument and whose translation component
    * is defined by the floating point arguments.
    *
    * @param quaternion the quaternion defining the rotation axis and angle of rotation of the 3 X 3 rotation-scale matrix component of the 4 X 4 matrix.
    * @param translateX the vector component defining the x translation component of the 4 X 4 matrix.
    * @param translateY the vector component defining the y translation component of the 4 X 4 matrix.
    * @param translateZ the vector component defining the z translation component of the 4 X 4 matrix.
    */

   public Matrix4D(Quaternion quaternion, float translateX, float translateY, float translateZ) {

      elements[0][0] =  1 - 2 * (float)Math.pow(quaternion.y, 2) - 2 * (float)Math.pow(quaternion.z, 2);
      elements[0][1] =  2 * quaternion.x * quaternion.y + 2 * quaternion.w * quaternion.z;
      elements[0][2] = -2 * quaternion.w * quaternion.y + 2 * quaternion.x * quaternion.z;

      elements[1][0] =  2 * quaternion.x * quaternion.y - 2 * quaternion.w * quaternion.z;
      elements[1][1] =  1 - 2 * (float)Math.pow(quaternion.x, 2) - 2 * (float)Math.pow(quaternion.z, 2);
      elements[1][2] =  2 * quaternion.w * quaternion.x + 2 * quaternion.y * quaternion.z;

      elements[2][0] =  2 * quaternion.w * quaternion.y + 2 * quaternion.x * quaternion.z;
      elements[2][1] = -2 * quaternion.w * quaternion.x + 2 * quaternion.y * quaternion.z;
      elements[2][2] =  1 - 2 * (float)Math.pow(quaternion.x, 2) - 2 * (float)Math.pow(quaternion.y, 2);

      elements[0][3] = 0;
      elements[1][3] = 0;
      elements[2][3] = 0;

      elements[3][0] = translateX;
      elements[3][1] = translateY;
      elements[3][2] = translateZ;

      elements[3][3] = 1;

   }

   /**
    * Constructs a 4 X 4 matrix whose 3 X 3 rotation-scale matrix component is defined by the quaternion argument.
    *
    * @param quaternion the quaternion defining the rotation axis and angle of rotation of the 3 X 3 rotation-scale matrix component of the 4 X 4 matrix.
    */

   public Matrix4D(Quaternion quaternion) {

      elements[0][0] =  1 - 2 * (float)Math.pow(quaternion.y, 2) - 2 * (float)Math.pow(quaternion.z, 2);
      elements[0][1] =  2 * quaternion.x * quaternion.y + 2 * quaternion.w * quaternion.z;
      elements[0][2] = -2 * quaternion.w * quaternion.y + 2 * quaternion.x * quaternion.z;

      elements[1][0] =  2 * quaternion.x * quaternion.y - 2 * quaternion.w * quaternion.z;
      elements[1][1] =  1 - 2 * (float)Math.pow(quaternion.x, 2) - 2 * (float)Math.pow(quaternion.z, 2);
      elements[1][2] =  2 * quaternion.w * quaternion.x + 2 * quaternion.y * quaternion.z;

      elements[2][0] =  2 * quaternion.w * quaternion.y + 2 * quaternion.x * quaternion.z;
      elements[2][1] = -2 * quaternion.w * quaternion.x + 2 * quaternion.y * quaternion.z;
      elements[2][2] =  1 - 2 * (float)Math.pow(quaternion.x, 2) - 2 * (float)Math.pow(quaternion.y, 2);

      elements[0][3] = 0;
      elements[1][3] = 0;
      elements[2][3] = 0;

      elements[3][0] = 0;
      elements[3][1] = 0;
      elements[3][2] = 0;

   }

   /**
    * Constructs a 4 X 4 matrix whose translation component vector is defined by the floating point arguments and whose 3 X 3 rotation-scale matrix
    * component is the identity matrix.
    *
    * @param translateX the vector component defining the x translation component of the 4 X 4 matrix.
    * @param translateY the vector component defining the y translation component of the 4 X 4 matrix.
    * @param translateZ the vector component defining the z translation component of the 4 X 4 matrix.
    */

   public Matrix4D(float translateX, float translateY, float translateZ) {

      elements[0][0] = 1;
      elements[1][1] = 1;
      elements[2][2] = 1;
      elements[3][3] = 1;

      elements[3][0] = translateX;
      elements[3][1] = translateY;
      elements[3][2] = translateZ;

   }

   /**
    * Sets the elements of the matrix argument equal to the elements of the transpose of this matrix.
    *
    * @param matrix the matrix whose elements are set to the transpose of this matrix.
    */

   public void transpose(Matrix4D matrix) {

      for(int i = 0; i < 4; i++)
         for(int j = 0; j < 4; j++)
   		    matrix.elements[i][j] = elements[j][i];

   }

   /**
    * Sets the elements of the matrix argument equal to the elements of the inverse of this matrix.
    *
    * @param matrix the matrix whose elements are set to the inverse of this matrix.
    * @throws ZeroDeterminantException if the determinant of the matrix argument is zero.
    */

   public void invert(Matrix4D matrix){

      float[][] subelements = new float[3][3];

      float det = determinant();

      if(det != 0) {

	     for(int i = 0; i < 4; i++) {
		    for(int j = 0; j < 4; j++) {

			   subelements[0][0] = elements[(i + 1) % 4][(j + 1) % 4];
              subelements[0][1] = elements[(i + 1) % 4][(j + 2) % 4];
              subelements[0][2] = elements[(i + 1) % 4][(j + 3) % 4];

              subelements[1][0] = elements[(i + 2) % 4][(j + 1) % 4];
              subelements[1][1] = elements[(i + 2) % 4][(j + 2) % 4];
              subelements[1][2] = elements[(i + 2) % 4][(j + 3) % 4];

              subelements[2][0] = elements[(i + 3) % 4][(j + 1) % 4];
              subelements[2][1] = elements[(i + 3) % 4][(j + 2) % 4];
              subelements[2][2] = elements[(i + 3) % 4][(j + 3) % 4];

               matrix.elements[j][i] = (float)Math.pow(-1, i + j) * (subelements[0][0] * (subelements[1][1]* subelements[2][2] - subelements[1][2] * subelements[2][1]) -
				                                                     subelements[1][0] * (subelements[0][1]* subelements[2][2] - subelements[0][2] * subelements[2][1]) +
															         subelements[2][0] * (subelements[0][1]* subelements[1][2] - subelements[0][2] * subelements[1][1])) / det;


		    }

	     }

      }

      else
         throw new ZeroDeterminantException();

   }

   /**
    * Calculates and returns the determinant of this matrix.
    *
    * @return the determinant of this matrix.
    */

   public float determinant() {

      float sum = 0.0f;

      float[][] subelements = new float[3][3];

      for(int i = 0; i < 4; i++) {

         subelements[0][0] = elements[(i + 1) % 4][1];
         subelements[0][1] = elements[(i + 1) % 4][2];
         subelements[0][2] = elements[(i + 1) % 4][3];

         subelements[1][0] = elements[(i + 2) % 4][1];
         subelements[1][1] = elements[(i + 2) % 4][2];
         subelements[1][2] = elements[(i + 2) % 4][3];

         subelements[2][0] = elements[(i + 3) % 4][1];
         subelements[2][1] = elements[(i + 3) % 4][2];
         subelements[2][2] = elements[(i + 3) % 4][3];

         sum += (float)Math.pow(-1, i) * elements[i][0] * (subelements[0][0] * (subelements[1][1]* subelements[2][2] - subelements[1][2] * subelements[2][1]) -
                                                           subelements[1][0] * (subelements[0][1]* subelements[2][2] - subelements[0][2] * subelements[2][1]) +
   		                                                   subelements[2][0] * (subelements[0][1]* subelements[1][2] - subelements[0][2] * subelements[1][1]));

      }

      return sum;

   }

   /**
    * Calculates and returns the trace of this matrix.
    *
    * @return the trace of this matrix.
    */

   public float trace() {

      float sum = 0.0f;

      for(int i = 0; i < 4; i++)
   	     sum += elements[i][i];

      return sum;

   }

   /**
    * Modifies this matrix by adding the matrix argument, which is not modified, to this matrix.
    *
    * @param matrix the matrix that is added to this matrix.
    */

   public void add(Matrix4D matrix) {

      for(int i = 0; i < 4; i++)
   	     for(int j = 0; j < 4; j++)
		    elements[i][j] += matrix.elements[i][j];

   }

   /**
    * Modifies this matrix by assigning the sum of the matrix arguments, which are not modified, to this matrix.
    *
    * @param matrix1 the matrix that the second matrix argument is added to.
    * @param matrix2 the matrix that is added to the first matrix argument.
    */

   public void add(Matrix4D matrix1, Matrix4D matrix2) {

      for(int i = 0; i < 4; i++)
	     for(int j = 0; j < 4; j++)
	   	    elements[i][j] = matrix1.elements[i][j] + matrix2.elements[i][j];

   }

   /**
    * Modifies this matrix by subtracting the matrix argument, which is not modified, from this matrix.
    *
    * @param matrix the matrix that is subtracted from this matrix.
    */

   public void subtract(Matrix4D matrix) {

      for(int i = 0; i < 4; i++)
   	     for(int j = 0; j < 4; j++)
		    elements[i][j] -= matrix.elements[i][j];

   }

   /**
    * Modifies this matrix by assigning the difference of the matrix arguments, which are not modified, to this matrix.
    *
    * @param matrix1 the matrix that the second matrix argument is subtracted from.
    * @param matrix2 the matrix that is subtracted from the first matrix argument.
    */

   public void subtract(Matrix4D matrix1, Matrix4D matrix2) {

      for(int i = 0; i < 4; i++)
   	     for(int j = 0; j < 4; j++)
		    elements[i][j] = matrix1.elements[i][j] - matrix2.elements[i][j];

   }

   /**
    * Modifies this matrix by assigning the product of the matrix argument, which is not modified, and this matrix, to this matrix.
    *
    * @param matrix the matrix that is multiplied by this matrix.
    */

   public void multiply(Matrix4D matrix) {

      float[][] product = new float[4][4];

      for(int i = 0; i < 4; i++)
     	 for(int j = 0; j < 4; j++)
	        for(int k = 0; k < 4; k++)
	           product[j][i] += elements[j][k] * matrix.elements[k][i];

      for(int i = 0; i < 4; i++)
         System.arraycopy(product[i], 0, elements[i], 0, 4);

   }

   /**
    * Modifies this matrix by assigning the product of the matrix arguments, which are not modified, to this matrix.
    *
    * @param matrix1 the matrix that is multiplied by the second matrix argument.
    * @param matrix2 the matrix that the first matrix argument is multiplied by.
    */

   public void multiply(Matrix4D matrix1, Matrix4D matrix2) {

      for(int i = 0; i < 4; i++)
         java.util.Arrays.fill(elements[i], 0);

      for(int i = 0; i < 4; i++)
     	 for(int j = 0; j < 4; j++)
	        for(int k = 0; k < 4; k++)
	           elements[j][i] += matrix2.elements[j][k] * matrix1.elements[k][i];

   }

   /**
    * Sets the rotation-scale component of this matrix to the equivalent rotation matrix defined by the quaternion argument.
    *
    * @param quaternion the quaternion that defines the rotation matrix that the rotation-scale component of this matrix is set to.
    */

   public void set(Quaternion quaternion) {

      elements[0][0] =  1 - 2 * (float)(Math.pow(quaternion.y, 2) + Math.pow(quaternion.z, 2));
      elements[0][1] =  2 * quaternion.x * quaternion.y+ 2 * quaternion.w * quaternion.z;
      elements[0][2] = -2 * quaternion.w * quaternion.y + 2 * quaternion.x * quaternion.z;

      elements[1][0] =  2 * quaternion.x * quaternion.y - 2 * quaternion.w * quaternion.z;
      elements[1][1] =  1 - 2 * (float)(Math.pow(quaternion.x, 2) + Math.pow(quaternion.z, 2));
      elements[1][2] =  2 * quaternion.w * quaternion.x + 2 * quaternion.y * quaternion.z;

      elements[2][0] =  2 * quaternion.w * quaternion.y + 2 * quaternion.x * quaternion.z;
      elements[2][1] = -2 * quaternion.w * quaternion.x + 2 * quaternion.y * quaternion.z;
      elements[2][2] =  1 - 2 * (float)(Math.pow(quaternion.x, 2) + Math.pow(quaternion.y, 2));

   }

   /**
    * Returns the value of the element of this matrix specified by the row and column index arguments.
    *
    * @param i the row index of the element to be returned.
    * @param j the column index of the element to be returned.
    * @return the element of this matrix specified by the row and column index arguments.
    */

   public float getElement(int i, int j) { return elements[j][i]; }

   /**
    * Sets the value of the element of this matrix specified by the row and coloumn index arguments to the floating point argument.
    *
    * @param i the row index of the element to be modified.
    * @param j the column index of the element to be modified.
    * @param val the value that the element specified by the row and column arguments is set to.
    */

   public void setElement(int i, int j, float val) { elements[j][i] = val; }

}