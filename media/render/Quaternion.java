package shamu.media.render;

/** Class representing a four coordinate quaternion. Used to define rotations of an arbitrary angle about an arbitrary rotation axis.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class Quaternion implements java.io.Serializable {

   /**
    * The w-coordinate of this quaternion.
    */

   public float w;

   /**
    * The x-coordinate of this quaternion.
    */

   public float x;

   /**
    * The y-coordinate of this quaternion.
    */

   public float y;

   /**
    * The z-coordinate of this quaternion.
    */

   public float z;

   /**
    * Constructs a quaternion whose coordinates are all zero.
    */

   public Quaternion() {}

   /**
    * Constructs a quaternion with an axis and angle of rotation determined by the floating point arguments.
    *
    * @param nx the unit vector component defining the x component of the axis of rotation of the quaternion.
    * @param ny the unit vector component defining the y component of the axis of rotation of the quaternion.
    * @param nz the unit vector component defining the z component of the axis of rotation of the quaternion.
    * @param theta the angle of rotation defining this quaternion.
    */

   public Quaternion(float nx, float ny, float nz, float theta) {

      float factor = (float)Math.sqrt(Math.pow(nx, 2.0f) + Math.pow(ny, 2.0f) + Math.pow(nz, 2.0f));

      nx /= factor;
      ny /= factor;
      nz /= factor;

      this.w = (float)Math.cos(theta / 2.0f);
      this.x = (float)Math.sin(theta / 2.0f) * nx;
      this.y = (float)Math.sin(theta / 2.0f) * ny;
      this.z = (float)Math.sin(theta / 2.0f) * nz;

   }

   /**
    * Modifies this quaternion by adding the quaternion argument, which is not modified, to this quaternion.
    *
    * @param quaternion the quaternion that is added to this quaternion.
    */

   public void add(Quaternion quaternion) {

      w += quaternion.w;
      x += quaternion.x;
      y += quaternion.y;
      z += quaternion.z;

   }

   /**
    * Modifies this quaternion by assigning the sum of the quaternion arguments, which are not modified, to this quaternion.
    *
    * @param quaternion1 the quaternion that the second quaternion argument is added to.
    * @param quaternion2 the quaternion that is added to the first quaternion argument.
    */

   public void add(Quaternion quaternion1, Quaternion quaternion2) {

      w = quaternion1.w + quaternion2.w;
      x = quaternion1.x + quaternion2.x;
      y = quaternion1.y + quaternion2.y;
      z = quaternion1.z + quaternion2.z;

   }

   /**
    * Modifies this quaternion by subtracting the quaternion argument, which is not modified, from this quaternion.
    *
    * @param quaternion the quaternion that is subtracted from this quaternion.
    */

   public void subtract(Quaternion quaternion) {

      w -= quaternion.w;
      x -= quaternion.x;
      y -= quaternion.y;
      z -= quaternion.z;

   }

   /**
    * Modifies this quaternion by assigning the difference of the quaternion arguments, which are not modified, to this quaternion.
    *
    * @param quaternion1 the quaternion that the second quaternion argument is subtracted from.
    * @param quaternion2 the quaternion that is subtracted from the first quaternion argument.
    */

   public void subtract(Quaternion quaternion1, Quaternion quaternion2) {

      w = quaternion1.w - quaternion2.w;
      x = quaternion1.x - quaternion2.x;
      y = quaternion1.y - quaternion2.y;
      z = quaternion1.z - quaternion2.z;

   }

   /**
    * Modifies this quaternion by assigning the product of the quaternion argument, which is not modified, and this quaternion, to this quaternion.
    *
    * @param quaternion the quaternion that is multiplied by this quaternion.
    */

   public void multiply(Quaternion quaternion) {

      float w = quaternion.w * this.w - quaternion.x * this.x - quaternion.y * this.y - quaternion.z * this.z;
      float x = quaternion.y * this.z - quaternion.z * this.y + quaternion.w * this.x + quaternion.x * this.w;
      float y = quaternion.z * this.x - quaternion.x * this.z + quaternion.w * this.y + quaternion.y * this.w;
      float z = quaternion.x * this.y - quaternion.y * this.x + quaternion.w * this.z + quaternion.z * this.w;

      this.w = w;
      this.x = x;
      this.y = y;
      this.z = z;

   }

   /**
    * Modifies this quaternion by assigning the product of the quaternion arguments, which are not modified, to this quaternion.
    *
    * @param quaternion1 the quaternion that is multiplied by the second quaternion argument.
    * @param quaternion2 the quaternion that the first quaternion argument is multiplied by.
    */

   public void multiply(Quaternion quaternion1, Quaternion quaternion2) {

      w = quaternion1.w * quaternion2.w - quaternion1.x * quaternion2.x - quaternion1.y * quaternion2.y - quaternion1.z * quaternion2.z;
      x = quaternion1.y * quaternion2.z - quaternion1.z * quaternion2.y + quaternion1.w * quaternion2.x + quaternion1.x * quaternion2.w;
      y = quaternion1.z * quaternion2.x - quaternion1.x * quaternion2.z + quaternion1.w * quaternion2.y + quaternion1.y * quaternion2.w;
      z = quaternion1.x * quaternion2.y - quaternion1.y * quaternion2.x + quaternion1.w * quaternion2.z + quaternion1.z * quaternion2.w;

   }

   /**
    * Modifies this quaternion by multiplying this quaternion by the scalar floating point argument.
    *
    * @param scalar the scalar floating point that this quaternion is multiplied by.
    */

   public void multiply(float scalar) {

      w *= scalar;
      x *= scalar;
      y *= scalar;
      z *= scalar;

   }

   /**
    * Modifies this quaternion by multiplying the quaternion argument, which is not modified, by the scalar floating point argument and assigning
    * it to this quaternion.
    *
    * @param scalar the scalar floating point that the quaternion argument is multiplied by.
    * @param quaternion the quaternion that the scalar argument is multiplied by.
    */

   public void multiply(float scalar, Quaternion quaternion) {

      w = scalar * quaternion.w;
      x = scalar * quaternion.x;
      y = scalar * quaternion.y;
      z = scalar * quaternion.z;

   }

   /**
    * Calculates and returns the inner product of this quaternion and the quaternion argument.
    *
    * @param quaternion the second quaternion defined in the quaternion space inner product.
    * @return the quaternion space inner product of this quaternion and the quaternion argument.
    */

   public float innerproduct(Quaternion quaternion) { return w * quaternion.w + x * quaternion.x + y * quaternion.y + z * quaternion.z; }

   /**
    * Modifies this quaternion by performing a spherical linear interpolation between the quaternion arguments, which are not modified, along
    * a geodesic on the unit sphere in quaternion space using the floating point argument as the interpolation parameter, and sets this quaternion
    * to the result of the interpolation. The interpolation parameter should be between 0 and 1.
    *
    * @param quaternion1 the first quaternion in the spherical linear interpolation.
    * @param quaternion2 the second quaternion in the spherical linear interpolation.
    * @param t the spherical linear interpolation parameter.
    */

   public void slerp(Quaternion quaternion1, Quaternion quaternion2, float t) {

      float omega = (float)Math.acos(quaternion1.innerproduct(quaternion2));

      float theta = t *  omega;

      Quaternion quaternion3 = new Quaternion();
      Quaternion quaternion4 = new Quaternion();

      quaternion3.multiply((float)Math.sin(omega - theta) / (float)Math.sin(omega), quaternion1);
      quaternion4.multiply((float)Math.sin(theta) / (float)Math.sin(omega), quaternion2);

      quaternion3.add(quaternion4);

      w = quaternion3.w;
      x = quaternion3.x;
      y = quaternion3.y;
      z = quaternion3.z;

   }

   /**
    * Modifies this quaternion by setting it to the quaternion that aligns the vector determined by
    * the first set of floating point arguments in the direction of the vector determined by the second
    * set of floating point arguments.
    *
    * @param ux the x-coordinate of the first vector argument.
    * @param uy the y-coordinate of the first vector argument.
    * @param uz the z-coordinate of the first vector argument.
    * @param vx the x-coordinate of the second vector argument.
    * @param vy the y-coordinate of the second vector argument.
    * @param vz the z-coordinate of the second vector argument.
    */

   public void set(float ux, float uy, float uz, float vx, float vy, float vz) {

      float factorU = (float)Math.sqrt(ux * ux + uy * uy + uz * uz);
      float factorV = (float)Math.sqrt(vx * vx + vy * vy + vz * vz);

      ux /= factorU;
      uy /= factorU;
      uz /= factorU;

      vx /= factorV;
      vy /= factorV;
      vz /= factorV;

      float wx = uy * vz - uz * vy;
      float wy = uz * vx - ux * vz;
      float wz = ux * vy - uy * vx;

      float factorW = (float)Math.sqrt(wx * wx + wy * wy + wz * wz);

      wx /= factorW;
      wy /= factorW;
      wz /= factorW;

      float theta = (float)Math.acos(ux* vx + uy * vy + uz * vz);

      w = (float)Math.cos(theta / 2.0f);
      x = (float)Math.sin(theta / 2.0f) * wx;
      y = (float)Math.sin(theta / 2.0f) * wy;
      z = (float)Math.sin(theta / 2.0f) * wz;

   }

   /**
    * Modifies this quaternion by setting it to the quaternion defined by the 4 X 4 rotation matrix argument.
    *
    * @param matrix the 4 X 4 rotation matrix defining the quaternion that this quaternion is set to.
    * @throws ZeroTraceException if the trace of the matrix argument is zero.
    */

   public void set(Matrix4D matrix) {

      float s =  matrix.trace();

      if(s != 0) {

         x = matrix.getElement(2, 1) - matrix.getElement(1, 2) / (4.0f * s);
         y = matrix.getElement(0, 2) - matrix.getElement(2, 0) / (4.0f * s);
   	     z = matrix.getElement(1, 0) - matrix.getElement(0, 1) / (4.0f * s);

         w = 0.5f * (float)Math.sqrt(s);

      }

      else
	     throw new ZeroTraceException();

   }

   /**
    * Modifies this quaternion by setting it to the quaternion defined by the 3 X 3 rotation matrix argument.
    *
    * @param matrix the 3 X 3 rotation matrix defining the quaternion that this quaternion is set to.
    * @throws ZeroTraceException if the trace of the matrix argument is zero.
    */

   public void set(Matrix3D matrix) {

      float s =  1 + matrix.trace();

      if(s != 0) {

         x = matrix.getElement(2, 1) - matrix.getElement(1, 2) / (4.0f * s);
         y = matrix.getElement(0, 2) - matrix.getElement(2, 0) / (4.0f * s);
   	     z = matrix.getElement(1, 0) - matrix.getElement(0, 1) / (4.0f * s);

         w = 0.5f * (float)Math.sqrt(s);

      }

      else
	     throw new ZeroTraceException();

   }

   /**
    * Modifies this quaternion by setting it to the quaternion determined by the euler angle arguments. The euler angles determine a sequence
    * of rotations about principal body fixed axes: A rotation about the body fixed y axis, a rotation about the body fixed z axis, and an additional
    * rotation about the body fixed y axis.
    *
    * @param euler1 the first euler angle.
    * @param euler2 the second euler angle.
    * @param euler3 the third euler angle.
    */

   public void set(float euler1, float euler2, float euler3) {

      Quaternion quaternion1 = new Quaternion(0.0f, -1.0f, 0.0f, euler1);
      Quaternion quaternion2 = new Quaternion(0.0f,  0.0f, 1.0f, euler2);
      Quaternion quaternion3 = new Quaternion(0.0f, -1.0f, 0.0f, euler3);

      multiply(quaternion2, quaternion1);
      multiply(quaternion3);

   }

   /**
    * Modifies this quaternion by setting it equal to the conjugate of the quaternion argument, which is not modified.
    *
    * @param quaternion the quaternion whose conjugate is calculated.
    */

   public void conjugate(Quaternion quaternion) {

      quaternion.w =  w;
      quaternion.x = -x;
      quaternion.y = -y;
      quaternion.z = -z;

   }

   /**
    * Modifies this quaternion to unit magnitude without modifying it's orientation in quaternion space.
    */

   public void normalize() {

      float factor = (float)Math.sqrt(innerproduct(this));

      w = w / factor;
      x = x / factor;
      y = y / factor;
      z = z / factor;

   }

}