package shamu.media.render;

/** Class containing values of attributes used in applying motion effects when rendering a 3D Dolphin model.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class MotionAttributes {

   /**
    * The linear speed attribute associated with this motion atttributes object.
    */

   protected float linearSpeed = 0.0f;

   /**
    * The angular speed attribute associated with this motion atttributes object.
    */

   protected float angularSpeed = 0.0f;

   /**
    * The linear motion direction vector x-coordinate attribute associated with this motion attributes object.
    */

   protected float motionDirectionX = 0.0f;

   /**
    * The linear motion direction vector y-coordinate attribute associated with this motion attributes object.
    */

   protected float motionDirectionY = 0.0f;

   /**
    * The linear motion direction vector z-coordinate attribute associated with this motion attributes object.
    */

   protected float motionDirectionZ = 1.0f;

   /**
    * The angular motion axis vector x-coordinate attribute associated with this motion attributes object.
    */

   protected float motionAxisX = 0.0f;

   /**
    * The angular motion axis vector y-coordinate attribute associated with this motion attributes object.
    */

   protected float motionAxisY = 0.0f;

   /**
    * The angular motion axis vector z-coordinate attribute associated with this motion attributes object.
    */

   protected float motionAxisZ = 1.0f;

   /**
    * Constructs a default motion attributes object.
    */

   public MotionAttributes() {}

   /**
    * Returns the linear speed attribute associated with this motion attributes object.
    *
    * @return the linear speed attribute associated with this motion attributes object.
    */

   public float getLinearSpeed() { return linearSpeed; }

   /**
    * Returns the angular speed attribute associated with this motion attributes object.
    *
    * @return the angular speed attribute associated with this motion attributes object.
    */

   public float getAngularSpeed() { return angularSpeed; }

   /**
    * Returns the linear motion direction vector x-coordinate attribute associated with this motion attributes object.
    *
    * @return the linear motion direction vector x-coordinate attribute associated with this motion attributes object.
    */

   public float getMotionDirectionX() { return motionDirectionX; }

   /**
    * Returns the linear motion direction vector y-coordinate attribute associated with this motion attributes object.
    *
    * @return the linear motion direction vector y-coordinate attribute associated with this motion attributes object.
    */

   public float getMotionDirectionY() { return motionDirectionY; }

   /**
    * Returns the linear motion direction vector z-coordinate attribute associated with this motion attributes object.
    *
    * @return the linear motion direction vector z-coordinate attribute associated with this motion attributes object.
    */

   public float getMotionDirectionZ() { return motionDirectionZ; }

   /**
    * Returns the angular motion axis vector x-coordinate attribute associated with this motion attributes object.
    *
    * @return the angular motion axis vector x-coordinate attribute associated with this motion attributes object.
    */

   public float getMotionAxisX() { return motionAxisX; }

   /**
    * Returns the angular motion axis vector y-coordinate attribute associated with this motion attributes object.
    *
    * @return the angular motion axis vector y-coordinate attribute associated with this motion attributes object.
    */

   public float getMotionAxisY() { return motionAxisY; }

   /**
    * Returns the angular motion axis vector z-coordinate attribute associated with this motion attributes object.
    *
    * @return the angular motion axis vector z-coordinate attribute associated with this motion attributes object.
    */

   public float getMotionAxisZ() { return motionAxisZ; }

   /**
    * Sets the linear speed attribute associated with this motion attributes object to the floating point argument.
    *
    * @param the value that the linear speed attribute associated with this motion attributes object is set to.
    */

   public void setLinearSpeed(float linearSpeed) { this.linearSpeed = linearSpeed; }

   /**
    * Sets the angular speed attribute associated with this motion attributes object to the floating point argument.
    *
    * @param the value that the angular speed attribute associated with this motion attributes object is set to.
    */

   public void setAngularSpeed(float angularSpeed) { this.angularSpeed = angularSpeed; }

   /**
    * Sets the linear motion direction vector x-coordinate attribute associated with this motion attributes object to the floating point argument.
    *
    * @param the value that the linear motion direction vector x-coordinate attribute associated with this motion attributes object is set to.
    */

   public void setMotionDirectionX(float motionDirectionX) { this.motionDirectionX = motionDirectionX; }

   /**
    * Sets the linear motion direction vector y-coordinate attribute associated with this motion attributes object to the floating point argument.
    *
    * @param the value that the linear motion direction vector y-coordinate attribute associated with this motion attributes object is set to.
    */

   public void setMotionDirectionY(float motionDirectionY) { this.motionDirectionY = motionDirectionY; }

   /**
    * Sets the linear motion direction vector z-coordinate attribute associated with this motion attributes object to the floating point argument.
    *
    * @param the value that the linear motion direction vector z-coordinate attribute associated with this motion attributes object is set to.
    */

   public void setMotionDirectionZ(float motionDirectionZ) { this.motionDirectionZ = motionDirectionZ; }

   /**
    * Sets the angular motion axis vector x-coordinate attribute associated with this motion attributes object to the floating point argument.
    *
    * @param the value that the angular motion axis vector x-coordinate attribute associated with this motion attributes object is set to.
    */

   public void setMotionAxisX(float motionAxisX) { this.motionAxisX = motionAxisX; }

   /**
    * Sets the angular motion axis vector y-coordinate attribute associated with this motion attributes object to the floating point argument.
    *
    * @param the value that the angular motion axis vector y-coordinate attribute associated with this motion attributes object is set to.
    */

   public void setMotionAxisY(float motionAxisY) { this.motionAxisY = motionAxisY; }

   /**
    * Sets the angular motion axis vector z-coordinate attribute associated with this motion attributes object to the floating point argument.
    *
    * @param the value that the angular motion axis vector z-coordinate attribute associated with this motion attributes object is set to.
    */

   public void setMotionAxisZ(float motionAxisZ) { this.motionAxisZ = motionAxisZ; }

}