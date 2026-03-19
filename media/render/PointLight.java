package shamu.media.render;

/** Class containing lighting information of a source radiating from a fixed point in space whose intensity distribution has spherical symmetry.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class PointLight extends Light {

   /**
    * The constant attenuation factor used to calculate the distance attenuation of this point light.
    */

   protected float attenuationFactorConstant = 1.0f;

   /**
    * The linear attenuation factor used to calculate the distance attenuation of this point light.
    */

   protected float attenuationFactorLinear = 0.0f;

   /**
    * The quadratic attenuation factor used to calculate the distance attenuation of this point light.
    */

   protected float attenuationFactorQuadratic = 0.0f;

   /**
    * Constructs a point light with default settings.
    */

   public PointLight() { super(); }

   /**
    * Constructs a point light having the source coordinates, infinitesimal solid angle energy flux for red, green, and blue wavelengths,
    * and attenuation factors defined by the floating point array arguments.
    *
    * @param source the array containing the x, y, z coordinates defining the source point of this point light.
    * @param incidentEnergy the array containing the infinitesimal solid angle energy flux for red, green, and blue wavelengths.
    * @param attenuationFactor the array containing the constant, linear, and quadratic attenuation factors defining the distance attenuation terms of this point light.
    */

   public PointLight(float[] source, float[] incidentEnergy, float[] attenuationFactor) {

      super(incidentEnergy, source);

      this.attenuationFactorConstant  = attenuationFactor[0];
      this.attenuationFactorLinear    = attenuationFactor[1];
      this.attenuationFactorQuadratic = attenuationFactor[2];

      this.x = source[0];
      this.y = source[1];
      this.z = source[2];

   }

   /**
    * Copies the data fields of this point light into a new point light and returns the new point light.
    *
    * @return the clone of this point light.
    */

   public Object clone() {

      PointLight copy = new PointLight();

      copy.attenuationFactorConstant = attenuationFactorConstant;
      copy.attenuationFactorLinear = attenuationFactorLinear;
      copy.attenuationFactorQuadratic = attenuationFactorQuadratic;

      copy.incidentEnergyR = incidentEnergyR;
      copy.incidentEnergyG = incidentEnergyG;
      copy.incidentEnergyB = incidentEnergyB;

      copy.x = this.x;
      copy.y = this.y;
      copy.z = this.z;

      return copy;

   }

   /**
    * Calculates and sets the unit incidence vector of this point light at the point determined by the floating point arguments.
    *
    * @param surfaceElementX the x-coordinate relative to some object coordinate system of an infinitesimal element of a surface.
    * @param surfaceElementY the y-coordinate relative to some object coordinate system of an infinitesimal element of a surface.
    * @param surfaceElementZ the z-coordinate relative to some object coordinate system of an infinitesimal element of a surface.
    * @param incidenceCoordinates the array to store the coordinates of the unit vector defining the incidence vector of this point light.
    */

   protected void setIncidenceCoordinates(float surfaceElementX, float surfaceElementY, float surfaceElementZ, float[] incidenceCoordinates) {

      incidenceCoordinates[0] = x - surfaceElementX;
      incidenceCoordinates[1] = y - surfaceElementY;
      incidenceCoordinates[2] = z - surfaceElementZ;

      float factor = (float)Math.sqrt(incidenceCoordinates[0] * incidenceCoordinates[0] +
                                      incidenceCoordinates[1] * incidenceCoordinates[1] +
                                      incidenceCoordinates[2] * incidenceCoordinates[2]);

      incidenceCoordinates[0] /= factor;
      incidenceCoordinates[1] /= factor;
      incidenceCoordinates[2] /= factor;

   }


   /**
    * Calculates and returns the angular attenuation determining the incidentEnergy of this light at the point defined by the floating point arguments.
    *
    * @param incidenceX the x-coordinate relative to some object coordinate system of the point at which the linear attenuation of this point light is calculated.
    * @param incidenceY the y-coordinate relative to some object coordinate system of the point at which the linear attenuation of this point light is calculated.
    * @param incidenceZ the z-coordinate relative to some object coordinate system of the point at which the linear attenuation of this point light is calculated.
    * @return the linear attenuation determining the incidentEnergy of this point light at the point defined by the floating point arguments relative to some object coordinate system.
    */

   protected float getDistanceAttenuation(float incidenceX, float incidenceY, float incidenceZ) {

	  float dx = incidenceX - x;
	  float dy = incidenceY - y;
	  float dz = incidenceZ - z;

	  float d = (float)Math.sqrt(dx * dx + dy * dy + dz * dz);

	  return 1.0f / (attenuationFactorConstant + d * attenuationFactorLinear + d * d * attenuationFactorQuadratic);

   }

   /**
    * Applies the matrix argument to the current source point of this light.
    *
    * @param matrix the matrix applied to the source point of this point light.
    */

   public void set(Matrix4D matrix) {

      float x = matrix.getElement(0, 0) * this.x + matrix.getElement(0, 1) * this.y + matrix.getElement(0, 2) * this.z + matrix.getElement(0, 3);
      float y = matrix.getElement(1, 0) * this.x + matrix.getElement(1, 1) * this.y + matrix.getElement(1, 2) * this.z + matrix.getElement(1, 3);
      float z = matrix.getElement(2, 0) * this.x + matrix.getElement(2, 1) * this.y + matrix.getElement(2, 2) * this.z + matrix.getElement(2, 3);

      this.x = x;
      this.y = y;
      this.z = z;

   }

   /**
    * Returns the constant attenuation factor used to calculate the distance attenuation of this point light.
    *
    * @return the constant attenuation factor used to calculate the distance attenuation of this point light.
    */

   public float getAttenuationFactorConstant() { return attenuationFactorConstant; }

   /**
    * Returns the linear attenuation factor used to calculate the distance attenuation of this point light.
    *
    * @return the linear attenuation factor used to calculate the distance attenuation of this point light.
    */

   public float getAttenuationFactorLinear() { return attenuationFactorLinear; }

   /**
    * Returns the quadratic attenuation factor used to calculate the distance attenuation of this point light.
    *
    * @return the quadratic attenuation factor used to calculate the distance attenuation of this point light.
    */

   public float getAttenuationFactorQuadratic() { return attenuationFactorQuadratic; }

   /**
    * Returns the x-coordinate of the source point of this point light.
    *
    * @return the x-coordinate of the source point of this point light.
    */

   public float getSourceX() { return this.x; }

   /**
    * Returns the y-coordinate of the source point of this point light.
    *
    * @return the y-coordinate of the source point of this point light.
    */

   public float getSourceY() { return this.y; }

   /**
    * Returns the z-coordinate of the source point of this point light.
    *
    * @return the z-coordinate of the source point of this point light.
    */

   public float getSourceZ() { return this.z; }

   /**
    * Sets the constant attenuation factor associated with this point light to the floating point argument.
    *
    * @param attenuationFactorConstant the value that the constant attenuation factor associated with this point light is set to.
    */

   public void setAttenuationFactorConstant(float attenuationFactorConstant) { this.attenuationFactorConstant = attenuationFactorConstant; }

   /**
    * Sets the linear attenuation factor associated with this point light to the floating point argument.
    *
    * @param attenuationFactorLinear the value that the linear attenuation factor associated with this point light is set to.
    */

   public void setAttenuationFactorLinear(float attenuationFactorLinear) { this.attenuationFactorLinear = attenuationFactorLinear; }

   /**
    * Sets the quadratic attenuation factor associated with this point light to the floating point argument.
    *
    * @param attenuationFactorQuadratic the value that the quadratic attenuation factor associated with this point light is set to.
    */

   public void setAttenuationFactorQuadratic(float attenuationFactorQuadratic) { this.attenuationFactorQuadratic = attenuationFactorQuadratic; }

   /**
    * Sets the x-coordinate of the source point associated with this point light to the floating point argument.
    *
    * @param sourceX the value that the x-coordinate of the source point of this point light is set to.
    */

   public void setSourceX(float sourceX) { this.x = sourceX; }

   /**
    * Sets the y-coordinate of the source point associated with this point light to the floating point argument.
    *
    * @param sourceY the value that the y-coordinate of the source point of this point light is set to.
    */

   public void setSourceY(float sourceY) { this.y= sourceY; }

   /**
    * Sets the z-coordinate of the source point associated with this point light to the floating point argument.
    *
    * @param sourceZ the value that the z-coordinate of the source point of this point light is set to.
    */

   public void setSourceZ(float sourceZ) { this.z = sourceZ; }

}
