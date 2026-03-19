package shamu.media.render;

/** Abstract class that is the parent class of all classes containing the lighting information of a 3D scene.
 *
 *  @author Scott T. Carter
 *  @version 1.4
 */

abstract public class Light {

   /**
    * The incident red wavelength energy flux in an infinitesimal solid angle defined by this light.
    */

   protected float incidentEnergyR = 5.0f;

   /**
    * The incident green wavelength energy flux in an infinitesimal solid angle defined by this light.
    */

   protected float incidentEnergyG = 5.0f;

   /**
    * The incident blue wavelength energy flux in an infinitesimal solid angle defined by this light.
    */

   protected float incidentEnergyB = 5.0f;

   protected float x;

   protected float y;

   protected float z;

   /**
    * The enabled flag of this light.
    */

   protected boolean enabled = true;

   /**
    * Constructs an light with default settings.
    */

   public Light() {}

   /**
    * Constructs a light having infinitesimal solid angle energy flux defined by the floating point array argument.
    *
    * @param incidentEnergy the array containing the infinitesimal solid angle energy flux for red, green, and blue wavelengths.
    */

   protected Light(float[] incidentEnergy, float[] coords) {

      this.incidentEnergyR = incidentEnergy[0];
      this.incidentEnergyG = incidentEnergy[1];
      this.incidentEnergyB = incidentEnergy[2];

      this.x = coords[0];
      this.y = coords[1];
      this.z = coords[2];

   }

   /**
    * Copies the data fields of this light into a new light and returns the new light.
    *
    * @return the clone of this light.
    */

   abstract public Object clone();

   /**
    * Applies the matrix argument to any coordinate information of this light.
    *
    * @param matrix the matrix applied to any coordinate information of this light.
    */

   abstract public void set(Matrix4D matrix);

   /**
    * Calculates and sets the unit incidence vector of this light at the point determined by the floating point arguments.
    *
    * @param surfaceElementX the x-coordinate relative to some object coordinate system of an infinitesimal element of a surface.
    * @param surfaceElementY the y-coordinate relative to some object coordinate system of an infinitesimal element of a surface.
    * @param surfaceElementZ the z-coordinate relative to some object coordinate system of an infinitesimal element of a surface.
    * @param incidenceCoordinates the array to store the coordinates of the unit vector defining the incidence vector of this light.
    */

   abstract protected void setIncidenceCoordinates(float surfaceElementX, float surfaceElementY, float surfaceElementZ, float[] incidenceCoordinates);

   /**
    * Calculates and returns the distance attenuation determining the incidentEnergy of this light at the point determined by the floating
    * point arguments. This method always returns 1.0 unless this light is a point light.
    *
    * @param incidenceX the x-coordinate relative to some object coordinate system of the point at which the distance attenuation of this light is calculated.
    * @param incidenceY the y-coordinate relative to some object coordinate system of the point at which the distance attenuation of this light is calculated.
    * @param incidenceZ the z-coordinate relative to some object coordinate system of the point at which the distance attenuation of this light is calculated.
    * @return the distance attenuation determining the incidentEnergy of this light at the point defined by the floating point arguments relative to some object coordinate system.
    *
    * @see PointLight#getDistanceAttenuation(float, float, float)
    */

   protected float getDistanceAttenuation(float incidenceX, float incidenceY, float incidenceZ) { return 1.0f; }

   /**
    * Calculates and returns the angular attenuation determining the incidentEnergy of this light at the point determined by the floating
    * point arguments. This method always returns 1.0 unless this light is a spotlight.
    *
    * @param incidenceX the x-coordinate relative to some object coordinate system of the position at which the angular attenuation of this light is calculated.
    * @param incidenceY the y-coordinate relative to some object coordinate system of the position at which the angular attenuation of this light is calculated.
    * @param incidenceZ the z-coordinate relative to some object coordinate system of the position at which the angular attenuation of this light is calculated.
    * @return the angular attenuation determining the incidentEnergy of this light at the position defined by the floating point arguments relative to some object coordinate system.
    *
    * @see SpotLight#getAngularAttenuation(float, float, float)
    */

   protected float getAngularAttenuation(float incidenceX, float incidenceY, float incidenceZ) { return 1.0f; }

   /**
    * Calculates and sets the unit reflection vector of this light at the point determined by the floating point arguments.
    *
    * @param surfaceElementX the x-coordinate relative to some object coordinate system of an infinitesimal element of a surface.
    * @param surfaceElementY the y-coordinate relative to some object coordinate system of an infinitesimal element of a surface.
    * @param surfaceElementZ the z-coordinate relative to some object coordinate system of an infinitesimal element of a surface.
    * @param normalX the x-coordinate of the vector relative to some object coordinate system normal to the surface element.
    * @param normalY the y-coordinate of the vector relative to some object coordinate system normal to the surface element.
    * @param normalZ the z-coordinate of the vector relative to some object coordinate system normal to the surface element.
    * @param reflectionCoordinates the array to store the coordinates of the unit vector defining the reflection vector of this light.
    */

   protected void setReflectionCoordinates(float surfaceElementX, float surfaceElementY, float surfaceElementZ, float normalX, float normalY, float normalZ, float[] reflectionCoordinates) {

      float[] incidenceCoordinates = new float[3];

      setIncidenceCoordinates(surfaceElementX, surfaceElementY, surfaceElementZ, incidenceCoordinates);

      float alpha = 2 * (normalX * incidenceCoordinates[0] + normalY * incidenceCoordinates[1] + normalZ * incidenceCoordinates[2]);

      reflectionCoordinates[0] = alpha * normalX - incidenceCoordinates[0];
      reflectionCoordinates[1] = alpha * normalY - incidenceCoordinates[1];
      reflectionCoordinates[2] = alpha * normalZ - incidenceCoordinates[2];

      float factor = (float)Math.sqrt(reflectionCoordinates[0] * reflectionCoordinates[0] +
                                      reflectionCoordinates[1] * reflectionCoordinates[1] +
                                      reflectionCoordinates[2] * reflectionCoordinates[2]);

      if(factor != 0.0f) {

         reflectionCoordinates[0] /= factor;
         reflectionCoordinates[1] /= factor;
         reflectionCoordinates[2] /= factor;

      }

      else {

         reflectionCoordinates[0] = 0.0f;
         reflectionCoordinates[1] = 0.0f;
         reflectionCoordinates[2] = 0.0f;

      }

   }

   /**
    * Calculates and sets the unit view vector of this light at the point determined by the floating point arguments.
    *
    * @param surfaceElementX the x-coordinate relative to some object coordinate system of an infinitesimal element of a surface.
    * @param surfaceElementY the y-coordinate relative to some object coordinate system of an infinitesimal element of a surface.
    * @param surfaceElementZ the z-coordinate relative to some object coordinate system of an infinitesimal element of a surface.
    * @param eyeX the x-coordinate of the view point relative to some object coordinate system.
    * @param eyeY the y-coordinate of the view point relative to some object coordinate system.
    * @param eyeZ the z-coordinate of the view point relative to some object coordinate system.
    * @param viewCoordinates the array to store the coordinates of the unit vector defining the view vector of this light.
    */
/*
   protected void setViewCoordinates(float surfaceElementX, float surfaceElementY, float surfaceElementZ, float eyeX, float eyeY, float eyeZ, float[] viewCoordinates) {

      viewCoordinates[0] = eyeX - surfaceElementX;
      viewCoordinates[1] = eyeY - surfaceElementY;
      viewCoordinates[2] = eyeZ - surfaceElementZ;

      float factor = (float)Math.sqrt(viewCoordinates[0] * viewCoordinates[0] +
                                      viewCoordinates[1] * viewCoordinates[1] +
                                      viewCoordinates[2] * viewCoordinates[2]);

      viewCoordinates[0] /= factor;
      viewCoordinates[1] /= factor;
      viewCoordinates[2] /= factor;


   }
*/
   /**
    * Returns the incident red wavelength energy flux in an infinitesimal solid angle defined by this light.
    *
    * @return the incident red wavelength energy flux in an infinitesimal solid angle defined by this light.
    */

   public float getIncidentEnergyR() { return incidentEnergyR; }

   /**
    * Returns the incident green wavelength energy flux in an infinitesimal solid angle defined by this light.
    *
    * @return the incident green wavelength energy flux in an infintesimal solid angle defined by this light.
    */

   public float getIncidentEnergyG() { return incidentEnergyG; }

   /**
    * Returns the incident blue wavelength energy flux in an infinitesimal solid angle defined by this light.
    *
    * @return the incident blue wavelength energy flux in an infinitesimal solid angle defined by this light.
    */

   public float getIncidentEnergyB() { return incidentEnergyB; }

   /**
    * Returns the enabled flag of this light.
    *
    * @return the enabled flag of this light.
    */

   public boolean getEnabled() { return enabled; }

   /**
    * Sets the incident red wavelength energy flux in an infinitesimal solid angle defined by this light to the floating point argument.
    *
    * @param incidentEnergyR the value that the incident red wavelength energy flux in an infinitesimal solid angle defined by this light is set to.
    */

   public void setIncidentEnergyR(float incidentEnergyR) { this.incidentEnergyR = incidentEnergyR; }

   /**
    * Sets the incident green wavelength energy flux in an infinitesimal solid angle defined by this light to the floating point argument.
    *
    * @param incidentEnergyG the value that the incident green wavelength energy flux in an infinitesimal solid angle defined by this light is set to.
    */

   public void setIncidentEnergyG(float incidentEnergyG) { this.incidentEnergyG = incidentEnergyG; }

   /**
    * Sets the incident blue wavelength energy flux in an infinitesimal solid angle defined by this light to the floating point argument.
    *
    * @param incidentEnergyR the value that the incident blue wavelength energy flux in an infinitesimal solid angle defined by this light is set to.
    */

   public void setIncidentEnergyB(float incidentEnergyB) { this.incidentEnergyB = incidentEnergyB; }

   /**
    * Sets the enabled flag of this light to the boolean argument.
    *
    * @param enabled the value that the enabled flag of this light is set to.
    */

   public void setEnabled(boolean enabled) { this.enabled = enabled; }

}
