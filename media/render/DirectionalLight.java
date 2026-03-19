package shamu.media.render;

/** Class containing lighting information of a light whose "source" is located at infinity, i.e. parallel rays of light.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class DirectionalLight extends Light {

   /**
    * Constructs a directional light with default settings. By default, a new directional light is white with a direction vector of (0, 0, 1).
    */

   public DirectionalLight() { super(); }

   /**
    * Constructs a directional light having the direction vector coordinates and infinitesimal solid angle energy flux for red, green, and blue wavelengths
    * determined by the floating point array arguments.
    *
    * @param direction the array containing the x, y, z coordinates defining the direction vector of this directional light.
    * @param incidentEnergy the array containing the infinitesimal solid angle energy flux for red, green, and blue wavelengths.
    */

   public DirectionalLight(float[] direction, float[] incidentEnergy) {

      super(incidentEnergy, direction);

      //this.x = direction[0];
      //this.y = direction[1];
      //this.z = direction[2];

   }

   /**
    * Constructs a directional light having the direction vector coordinates determined by the floating point arguments and color components determined
    * by the floating point array argument.
    *
    * @param theta the longitudinal angle defining the direction vector of this directional light.
    * @param phi the azimuthal angle defining the direction vector of this directional light.
    * @param incidentEnergy the array containing red, green, blue component intensities defining the incidentEnergy of this directional light.
    */

   public DirectionalLight(float theta, float phi, float[] incidentEnergy) {

      super(incidentEnergy, new float[] { (float)Math.sin(phi) * (float)Math.cos(theta),
                                          (float)Math.cos(phi),
                                          (float)Math.sin(phi) * (float)Math.sin(theta) });

   }

   /**
    * Copies the data fields of this directional light into a new directional light and returns the new directional light.
    *
    * @return the clone of this directional light.
    */

   public Object clone() {

	  DirectionalLight copy = new DirectionalLight();

	  copy.incidentEnergyR = incidentEnergyR;
	  copy.incidentEnergyG = incidentEnergyG;
	  copy.incidentEnergyB = incidentEnergyB;

	  copy.x = this.x;
	  copy.y = this.y;
	  copy.z = this.z;

	  return copy;

   }

   /**
    * Calculates and sets the unit incidence vector coordinates of this directional light at the point determined by the floating point arguments.
    *
    * @param surfaceElementX the x-coordinate of the position relative to some object coordinate system of an infinitesimal element of a surface.
    * @param surfaceElementY the y-coordinate of the position relative to some object coordinate system of an infinitesimal element of a surface.
    * @param surfaceElementZ the z-coordinate of the position relative to some object coordinate system of an infinitesimal element of a surface.
    * @param incidenceCoordinates the array to store the coordinates of the unit vector defining the incidence vector of this directional light.
    */

   protected void setIncidenceCoordinates(float surfaceElementX, float surfaceElementY, float surfaceElementZ, float[] incidenceCoordinates) {

	  incidenceCoordinates[0] = -this.x;
	  incidenceCoordinates[1] = -this.y;
	  incidenceCoordinates[2] = -this.z;

      float factor = (float)Math.sqrt(incidenceCoordinates[0] * incidenceCoordinates[0] +
                                      incidenceCoordinates[1] * incidenceCoordinates[1] +
                                      incidenceCoordinates[2] * incidenceCoordinates[2]);

      incidenceCoordinates[0] /= factor;
      incidenceCoordinates[1] /= factor;
      incidenceCoordinates[2] /= factor;

   }

   /**
    * Applies the matrix argument to the current direction vector of this light.
    *
    * @param matrix the matrix applied to the direction vector of this directional light.
    */

   public void set(Matrix4D matrix) {

      float nx = matrix.getElement(0, 0) * this.x + matrix.getElement(0, 1) * this.y + matrix.getElement(0, 2) * this.z;
      float ny = matrix.getElement(1, 0) * this.x + matrix.getElement(1, 1) * this.y + matrix.getElement(1, 2) * this.z;
      float nz = matrix.getElement(2, 0) * this.x + matrix.getElement(2, 1) * this.y + matrix.getElement(2, 2) * this.z;

      float factor = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);

      nx /= factor;
      ny /= factor;
      nz /= factor;

      this.x = nx;
      this.y = ny;
      this.z = nz;

   }

   /**
    * Returns the x-coordinate of the direction vector of this directional light.
    *
    * @return the x-coordinate of the direction vector of this directional light.
    */

   public float getDirectionX() { return this.x; }

   /**
    * Returns the y-coordinate of the direction vector of this directional light.
    *
    * @return the y-coordinate of the direction vector of this directional light.
    */

   public float getDirectionY() { return this.y; }

   /**
    * Returns the z-coordinate of the direction vector of this directional light.
    *
    * @return the z-coordinate of the direction vector of this directional light.
    */

   public float getDirectionZ() { return this.z; }

   /**
    * Sets the x-coordinate of the direction vector associated with this directional light to the floating point argument.
    *
    * @param directionX the value that the x-coordinate of the direction vector of this directional light set to.
    */

   public void setDirectionX(float directionX) { this.x = directionX; }

   /**
    * Sets the y-coordinate of the direction vector associated with this directional light to the floating point argument.
    *
    * @param directionY the value that the y-coordinate of the direction vector of this directional light set to.
    */

   public void setDirectionY(float directionY) { this.y= directionY; }

   /**
    * Sets the z-coordinate of the direction vector associated with this directional light to the floating point argument.
    *
    * @param directionZ the value that the z-coordinate of the direction vector of this directional light set to.
    */

   public void setDirectionZ(float directionZ) { this.z = directionZ; }

}
