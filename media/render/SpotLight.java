package shamu.media.render;

/** Class containing lighting information of a source radiating from a fixed point in space whose incidentEnergy distribution is defined by a
 *  space cone. The incidentEnergy distribution is zero for points lying outside the boundary of the cone associated with instances of this class.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class SpotLight extends PointLight {

   /**
    * The falloff exponential term used to calculate the angular attenuation factor of this spotlight.
    */

   protected float falloff = 1.0f;

   /**
    * The half-angle determining the inner cone of light of this spotlight.
    */

   protected float umbra = (float)Math.PI / 4;

   /**
    * The half-angle determining the outer cone of light of this spotlight.
    */

   protected float penumbra = (float)Math.PI / 4;

   /**
    * The x-coordinate of the unit vector determining the axis of the cone of light associated with this spotlight.
    */

   protected float axisX = 0.0f;

   /**
    * The y-coordinate of the unit vector determining the axis of the cone of light associated with this spotlight.
    */

   protected float axisY = 0.0f;

   /**
    * The z-coordinate of the unit vector determining the axis of the cone of light associated with this spotlight.
    */

   protected float axisZ = 1.0f;

   /**
    * Constructs a spotlight with default settings.
    */

   public SpotLight() { super(); }

   /**
    * Constructs a spotlight having the umbra, penumbra and falloff values determined by the floating point arguments and the axis, source,
    * attenuation factors and infinitesimal solid angle energy flux for red, green, and blue wavelengths determined by the floating point
    * array arguments.
    *
    * @param umbra the half-angle in radians determining the inner cone of light associated with this spotlight.
    * @param penumbra the half-angle in radians determining the outer cone of light associate with this spotlight.
    * @param axis the array containing the coordinates defining the axis vector of this spotlight.
    * @param source the array containing the coordinates defining the source point of this spotlight.
    * @param incidentEnergy the array containing the infinitesimal solid angle energy flux for red, green, and blue wavelengths.
    * @param attenuationFactor the array containing the constant, linear, and quadratic attenuation factors defining the distance attenuation of thi spotlight.
    * @param falloff the falloff exponential term used to calculate the angular attenuation of this spotlight.
    */

   public SpotLight(float umbra, float penumbra, float[] axis, float[] source, float[] incidentEnergy, float[] attenuationFactor, float falloff) {

      super(source, incidentEnergy, attenuationFactor);

      this.falloff = falloff;

      this.axisX = axis[0];
      this.axisY = axis[1];
      this.axisZ = axis[2];

      this.umbra = umbra;
      this.penumbra = penumbra;

      float factor = (float)Math.sqrt(Math.pow(axisX, 2.0f) +
                                      Math.pow(axisY, 2.0f) +
                                      Math.pow(axisZ, 2.0f));

      this.axisX /= factor;
      this.axisY /= factor;
      this.axisZ /= factor;

   }

   /**
    * Copies the data fields of this spotlight into a new spotlight and returns the new spotlight.
    *
    * @return the clone of this spotlight.
    */

   public Object clone() {

	  SpotLight copy = new SpotLight();

      copy.falloff = falloff;

	  copy.umbra = umbra;
      copy.penumbra = penumbra;

	  copy.axisX = axisX;
	  copy.axisY = axisY;
	  copy.axisZ = axisZ;

	  copy.x = this.x;
	  copy.y = this.y;
	  copy.z = this.z;

	  copy.incidentEnergyR = incidentEnergyR;
	  copy.incidentEnergyG = incidentEnergyG;
	  copy.incidentEnergyB = incidentEnergyB;

	  return copy;

   }

   /**
    * Calculates and returns the angular attenuation determining the incidentEnergy of this spotlight at the point defined by the floating
    * point arguments. Returns a non-zero value if and only if the point defined by the floating point arguments lies inside the outer
    * cone of light associated with this spotlight.
    *
    * @param incidenceX the x-coordinate of the point relative to some object coordinate system at which the angular attenuation of this spotlight is calculated.
    * @param incidenceY the y-coordinate of the point relative to some object coordinate system at which the angular attenuation of this spotlight is calculated.
    * @param incidenceZ the z-coordinate of the point relative to some object coordinate system at which the angular attenuation of this spotlight is calculated.
    * @return the angular attenuation determining the incidentEnergy of this spotlight at the point defined by the floating point arguments.
    */

   public float getAngularAttenuation(float incidenceX, float incidenceY, float incidenceZ) {

      float cosine0 = -incidenceX * axisX - incidenceY * axisY - incidenceZ * axisZ;

      float cosine1 = (float)Math.cos(penumbra);
      float cosine2 = (float)Math.cos(umbra);

      if(cosine0 <= cosine1)
         return 0.0f;

      else if(cosine0 > cosine2)
         return 1.0f;

      else
         return (float)Math.pow((cosine0 - cosine1) / (cosine2 - cosine1), falloff);

   }

   /**
    * Applies the matrix argument to the current source point and axis vector of this spotlight.
    *
    * @param matrix the matrix applied to the source point and axis vector of this spotlight.
    */

   public void set(Matrix4D matrix) {

      float x = matrix.getElement(0, 0) * this.x + matrix.getElement(0, 1) * this.y + matrix.getElement(0, 2) * this.z + matrix.getElement(0, 3);
      float y = matrix.getElement(1, 0) * this.x + matrix.getElement(1, 1) * this.y + matrix.getElement(0, 2) * this.z + matrix.getElement(1, 3);
      float z = matrix.getElement(2, 0) * this.x + matrix.getElement(2, 1) * this.y + matrix.getElement(0, 2) * this.z + matrix.getElement(2, 3);

      float nx = matrix.getElement(0, 0) * axisX + matrix.getElement(0, 1) * axisY + matrix.getElement(0, 2) * axisZ;
      float ny = matrix.getElement(1, 0) * axisX + matrix.getElement(1, 1) * axisY + matrix.getElement(1, 2) * axisZ;
      float nz = matrix.getElement(2, 0) * axisX + matrix.getElement(2, 1) * axisY + matrix.getElement(2, 2) * axisZ;

      float factor = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);

      nx /= factor;
      ny /= factor;
      nz /= factor;

      this.x = x;
      this.y = y;
      this.z = z;

      axisX = nx;
      axisY = ny;
      axisZ = nz;

   }

   /**
    * Returns the half-angle determining the inner cone of light associated with this spotlight.
    *
    * @return the half-angle determining the inner cone of light associated with this spotlight.
    */

   public float getUmbra() { return umbra; }

   /**
    * Returns the half-angle determining the outer cone of light associated with this spotlight.
    *
    * @return the half-angle determining the outer cone of light associated with this spotlight.
    */

   public float getPenumbra() { return penumbra; }

   /**
    * Returns the falloff exponential factor used to determine the angular attenuation of this spotlight.
    *
    * @return the falloff exponential factor used to determine the angular attenuation of this spotlight.
    */

   public float getFalloff() { return falloff; }

   /**
    * Returns the x-coordinate of the unit vector determining the axis of the cone of light associated with this spotlight.
    *
    * @return the x-coordinate of the unit vector determining the axis of the cone of light associated with this spotlight.
    */

   public float getAxisX() { return axisX; }

   /**
    * Returns the y-coordinate of the unit vector determining the axis of the cone of light associated with this spotlight.
    *
    * @return the y-coordinate of the unit vector determining the axis of the cone of light associated with this spotlight.
    */

   public float getAxisY() { return axisY; }

   /**
    * Returns the z-coordinate of the unit vector determining the axis of the cone of light associated with this spotlight.
    *
    * @return the z-coordinate of the unit vector determining the axis of the cone of light associated with this spotlight.
    */

   public float getAxisZ() { return axisZ; }

   /**
    * Sets the half-angle determining the inner cone of light associated with this spotlight to the floating point argument.
    *
    * @param umbra the measure in radians that the half-angle determining the inner cone of light associated with this spotlight is set to.
    */

   public void setUmbra(float umbra) { this.umbra = umbra; }

   /**
    * Sets the half-angle determining the outer cone of light associated with this spotlight to the floating point argument.
    *
    * @param penumbra the measure in radians that the half-angle determining the outer cone of light associated with this spotlight is set to.
    */

   public void setPenumbra(float penumbra) { this.penumbra = penumbra; }

   /**
    * Sets the falloff exponential term used to determine the angular attenuation of this spotlight.
    *
    * @param falloff the value that the falloff exponential term of this spotlight is set to.
    */

   public void setFalloff(float falloff) { this.falloff = falloff; }

   /**
    * Sets the x-coordinate of the unit vector determining the axis of the cone of light associated with this spotlight to the floating point argument.
    *
    * @param axisX the value that the x-coordinate of the normalized vector determining the axis of the cone of light associated with this spotlight is set to.
    */

   public void setAxisX(float axisX) { this.axisX = axisX; }

   /**
    * Sets the y-coordinate of the unit vector determining the axis of the cone of light associated with this spotlight to the floating point argument.
    *
    * @param axisY the value that the y-coordinate of the normalized vector determining the axis of the cone of light associated with this spotlight is set to.
    */

   public void setAxisY(float axisY) { this.axisY = axisY; }

   /**
    * Sets the z-coordinate of the unit vector determining the axis of the cone of light associated with this spotlight to the floating point argument.
    *
    * @param axisZ the value that the z-coordinate of the normalized vector determining the axis of the cone of light associated with this spotlight is set to.
    */

   public void setAxisZ(float axisZ) { this.axisZ = axisZ; }

}
