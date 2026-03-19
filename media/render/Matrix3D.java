package shamu.media.render;

/** Class representing a floating point 3 X 3 matrix containing standard matrix operations.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class Matrix3D implements java.io.Serializable  {

   /**
    * The elements in column major order of this matrix.
    */

   protected float[][] elements = {{1.0f, 0.0f, 0.0f}, {0.0f, 1.0f, 0.0f}, {0.0f, 0.0f, 1.0f}};

   /**
    * Creates a 3 X 3 matrix that rotates points and vectors about the x-axis at the angle determined by the floating point argument.
    *
    * @param theta the value that determines the angle of rotation defining the rotation matrix.
    * @return a 3 X 3 matrix that rotates points and vectors about the x-axis at the angle determined by the floating point argument.
    */

   public static Matrix3D createRotationMatrixX(float theta) {

	  Matrix3D matrix = new Matrix3D();

      float cosine = (float)Math.cos(theta);
      float sine   = (float)Math.sin(theta);

	  matrix.setElement(0, 0, 1.0f);
	  matrix.setElement(0, 1, 0.0f);
	  matrix.setElement(0, 2, 0.0f);

	  matrix.setElement(1, 0, 0.0f);
	  matrix.setElement(1, 1, cosine);
	  matrix.setElement(1, 2, -sine);

	  matrix.setElement(2, 0, 0.0f);
	  matrix.setElement(2, 1, sine);
	  matrix.setElement(2, 2, cosine);

      return matrix;

   }

   /**
    * Creates a 3 X 3 matrix that rotates points and vectors about the y-axis at the angle determined by the floating point argument.
    *
    * @param theta the value that determines the angle of rotation defining the rotation matrix.
    * @return a 3 X 3 matrix that rotates points and vectors about the y-axis at the angle determined by the floating point argument.
    */

   public static Matrix3D createRotationMatrixY(float theta) {

	  Matrix3D matrix = new Matrix3D();

      float cosine = (float)Math.cos(theta);
      float sine   = (float)Math.sin(theta);

	  matrix.setElement(0, 0, cosine);
	  matrix.setElement(0, 1, 0.0f);
	  matrix.setElement(0, 2, sine);

	  matrix.setElement(1, 0, 0.0f);
	  matrix.setElement(1, 1, 1.0f);
	  matrix.setElement(1, 2, 0.0f);

	  matrix.setElement(2, 0, -sine);
	  matrix.setElement(2, 1, 0.0f);
	  matrix.setElement(2, 2, cosine);

      return matrix;

   }

   /**
    * Creates a 3 X 3 matrix that rotates points and vectors about the z-axis at the angle determined by the floating point argument.
    *
    * @param theta the value that determines the angle of rotation defining the rotation matrix.
    * @return a 3 X 3 matrix that rotates points and vectors about the z-axis at the angle determined by the floating point argument.
    */

   public static Matrix3D createRotationMatrixZ(float theta) {

	  Matrix3D matrix = new Matrix3D();

      float cosine = (float)Math.cos(theta);
      float sine   = (float)Math.sin(theta);

	  matrix.setElement(0, 0, cosine);
	  matrix.setElement(0, 1, -sine);
	  matrix.setElement(0, 2, 0.0f);

	  matrix.setElement(1, 0, sine);
	  matrix.setElement(1, 1, cosine);
	  matrix.setElement(1, 2, 0.0f);

	  matrix.setElement(2, 0, 0.0f);
	  matrix.setElement(2, 1, 0.0f);
	  matrix.setElement(2, 2, 1.0f);

      return matrix;

   }

   /**
    * Constructs a 3 X 3 matrix whose elements are the identity matrix.
    */

   public Matrix3D() {}

   /**
    * Constructs a 3 X 3 rotation matrix defined by the quaternion argument.
    *
    * @param quaternion the quaternion defining the rotation axis and angle of rotation of the rotation matrix.
    */

   public Matrix3D(Quaternion quaternion) {

      elements[0][0] =  1 - 2 * (float)Math.pow(quaternion.y, 2) - 2 * (float)Math.pow(quaternion.z, 2);
      elements[0][1] =  2 * quaternion.x * quaternion.y + 2 * quaternion.w * quaternion.z;
      elements[0][2] = -2 * quaternion.w * quaternion.y + 2 * quaternion.x * quaternion.z;

      elements[1][0] =  2 * quaternion.x * quaternion.y - 2 * quaternion.w * quaternion.z;
      elements[1][1] =  1 - 2 * (float)Math.pow(quaternion.x, 2) - 2 * (float)Math.pow(quaternion.z, 2);
      elements[1][2] =  2 * quaternion.w * quaternion.x + 2 * quaternion.y * quaternion.z;

      elements[2][0] =  2 * quaternion.w * quaternion.y + 2 * quaternion.x * quaternion.z;
      elements[2][1] = -2 * quaternion.w * quaternion.x + 2 * quaternion.y * quaternion.z;
      elements[2][2] =  1 - 2 * (float)Math.pow(quaternion.x, 2) - 2 * (float)Math.pow(quaternion.y, 2);

   }

   public static Matrix3D createEulerMatrix(float yawl, float pitch, float roll) {

	      Quaternion q1 = new Quaternion(0, 1, 0, yawl);
	      Quaternion q2 = new Quaternion((float)Math.sin(yawl), 0, (float)Math.cos(yawl), pitch);
	      Quaternion q3 = new Quaternion((float)Math.cos(yawl) * (float)Math.sin(pitch), -(float)Math.cos(pitch), -(float)Math.sin(yawl) * (float)Math.sin(pitch), roll);

	      q1.multiply(q2);
	      q1.multiply(q3);

	      return new Matrix3D(q1);

  }

   /**
    * Constructs a 3 X 3 scaling matrix defined by the floating point arguments.
    *
    * @param factorX the vector component defining the x scale component of the 3 X 3 scaling matrix.
    * @param factorY the vector component defining the y scale component of the 3 X 3 scaling matrix.
    * @param factorZ the vector component defining the z scale component of the 3 X 3 scaling matrix.
    */

   public Matrix3D(float factorX, float factorY, float factorZ) {

      elements[0][0] = factorX;
      elements[1][1] = factorY;
      elements[2][2] = factorZ;

   }

   /**
    * Sets the elements of the matrix argument equal to the elements of the transpose of this matrix.
    *
    * @param matrix the matrix whose elements are set to the transpose of this matrix.
    */

   public void transpose(Matrix3D matrix) {

      for(int i = 0; i < 3; i++)
         for(int j = 0; j < 3; j++)
   		    matrix.elements[i][j] = elements[j][i];

   }

   /**
    * Sets the elements of the matrix argument equal to the elements of the inverse of this matrix.
    *
    * @param matrix the matrix whose elements are set to the inverse of this matrix.
    * @throws ZeroDeterminantException if the determinant of the matrix argument is zero.
    */

   public void invert(Matrix3D matrix){

      float det = determinant();

      if(det != 0) {

  	     matrix.elements[0][0] =  (elements[1][1] * elements[2][2] - elements[1][2] * elements[2][1]) / det;
	     matrix.elements[0][1] =  (elements[0][2] * elements[2][1] - elements[0][1] * elements[2][2]) / det;
	     matrix.elements[0][2] =  (elements[0][1] * elements[1][2] - elements[0][2] * elements[1][1]) / det;

	     matrix.elements[1][0] =  (elements[1][2] * elements[2][0] - elements[1][0] * elements[2][2]) / det;
	     matrix.elements[1][1] =  (elements[0][0] * elements[2][2] - elements[0][2] * elements[2][0]) / det;
	     matrix.elements[1][2] =  (elements[0][2] * elements[1][0] - elements[0][0] * elements[1][2]) / det;

	     matrix.elements[2][0] =  (elements[1][0] * elements[2][1] - elements[1][1] * elements[2][0]) / det;
	     matrix.elements[2][1] =  (elements[0][1] * elements[2][0] - elements[0][0] * elements[2][1]) / det;
	     matrix.elements[2][2] =  (elements[0][0] * elements[1][1] - elements[0][1] * elements[1][0]) / det;

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

      return elements[0][0] * (elements[1][1]* elements[2][2] - elements[1][2] * elements[2][1]) -
             elements[1][0] * (elements[0][1]* elements[2][2] - elements[0][2] * elements[2][1]) +
  		     elements[2][0] * (elements[0][1]* elements[1][2] - elements[0][2] * elements[1][1]);

   }

   /**
    * Calculates and returns the trace of this matrix.
    *
    * @return the trace of this matrix.
    */

   public float trace() {

      float sum = 0.0f;

      for(int i = 0; i < 3; i++)
   	     sum += elements[i][i];

      return sum;

   }

   /**
    * Modifies this matrix by adding the matrix argument, which is not modified, to this matrix.
    *
    * @param matrix the matrix that is added to this matrix.
    */

   public void add(Matrix3D matrix) {

      for(int i = 0; i < 3; i++)
   	     for(int j = 0; j < 3; j++)
		    elements[i][j] += matrix.elements[i][j];

   }

   /**
    * Modifies this matrix by assigning the sum of the matrix arguments, which are not modified, to this matrix.
    *
    * @param matrix1 the matrix that the second matrix argument is added to.
    * @param matrix2 the matrix that is added to the first matrix argument.
    */

   public void add(Matrix3D matrix1, Matrix3D matrix2) {

      for(int i = 0; i < 3; i++)
   	     for(int j = 0; j < 3; j++)
		    elements[i][j] = matrix1.elements[i][j] + matrix2.elements[i][j];

   }

   /**
    * Modifies this matrix by subtracting the matrix argument, which is not modified, from this matrix.
    *
    * @param matrix the matrix that is subtracted from this matrix.
    */

   public void subtract(Matrix3D matrix) {

      for(int i = 0; i < 3; i++)
   	     for(int j = 0; j < 3; j++)
		    elements[i][j] -= matrix.elements[i][j];

   }

   /**
    * Modifies this matrix by assigning the difference of the matrix arguments, which are not modified, to this matrix.
    *
    * @param matrix1 the matrix that the second matrix argument is subtracted from.
    * @param matrix2 the matrix that is subtracted from the first matrix argument.
    */

   public void subtract(Matrix3D matrix1, Matrix3D matrix2) {

      for(int i = 0; i < 3; i++)
   	     for(int j = 0; j < 3; j++)
		    elements[i][j] = matrix1.elements[i][j] - matrix2.elements[i][j];

   }

   /**
    * Modifies this matrix by assigning the product of the matrix argument, which is not modified, and this matrix, to this matrix.
    *
    * @param matrix the matrix that is multiplied by this matrix.
    */

   public void multiply(Matrix3D matrix) {

      float[][] product = new float[3][3];

      for(int i = 0; i < 3; i++)
  	     for(int j = 0; j < 3; j++)
	        for(int k = 0; k < 3; k++)
	           product[j][i] += elements[j][k] * matrix.elements[k][i];

      for(int i = 0; i < 3; i++)
         System.arraycopy(product[i], 0, elements[i], 0, 3);

   }

   /**
    * Modifies this matrix by assigning the product of the matrix arguments, which are not modified, to this matrix.
    *
    * @param matrix1 the matrix that is multiplied by the second matrix argument.
    * @param matrix2 the matrix that the first matrix argument is multiplied by.
    */

   public void multiply(Matrix3D matrix1, Matrix3D matrix2) {

      for(int i = 0; i < 3; i++)
         java.util.Arrays.fill(elements[i], 0);

      for(int i = 0; i < 3; i++)
      	  for(int j = 0; j < 3; j++)
	         for(int k = 0; k < 3; k++)
	            elements[j][i] += matrix2.elements[j][k] * matrix1.elements[k][i];

   }

   /**
    * Sets this matrix to the equivalent rotation matrix defined by the quaternion argument.
    *
    * @param quaternion the quaternion that defines the rotation matrix that this matrix is set to.
    */

   public void set(Quaternion quaternion) {

      elements[0][0] =  1 - 2 * (float)(Math.pow(quaternion.y, 2) + Math.pow(quaternion.z, 2));
      elements[0][1] =  2 * quaternion.x * quaternion.y + 2 * quaternion.w * quaternion.z;
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
    *
    * @return the element of this matrix specified by the row and column index arguments.
    */

   public float getElement(int i, int j) { return elements[j][i]; }

   /**
    * Sets the value of the element of this matrix specified by the row and column index arguments to the floating point argument.
    *
    * @param i the row index of the element to be modified.
    * @param j the column index of the element to be modified.
    * @param val the value that the element specified by the row and column index arguments is set to.
    */

   public void setElement(int i, int j, float val) { elements[j][i] = val; }

}


