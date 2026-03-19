package shamu.media.render;

/** Abstract class representing a mathematical curve in 3D space used to generate a tube or seashell.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

abstract public class ProfileCurve3D implements ModelingConstantsI {

   /**
    * The parametric variable lower bound that determines the boundary of this profile curve.
    */

   protected float lowerBoundsT;

   /**
    * The parametric variable upper bound that determines the boundary of this profile curve.
    */

   protected float upperBoundsT;

   /**
    * Constructs a default 3D profile curve.
    */

   protected ProfileCurve3D() {}

   /**
    * Constructs a 3D profile curve with a boundary defined by the floating point arguments.
    *
    * @param lowerBoundsT the value that the parametric variable lower bound that determines the boundary of this profile curve is set to.
    * @param upperBoundsT the value that the parametric variable upper bound that determines the boundary of this profile curve is set to.
    */

   protected ProfileCurve3D(float lowerBoundsT, float upperBoundsT) {

	  this.lowerBoundsT = lowerBoundsT;
	  this.upperBoundsT = upperBoundsT;

   }

   /**
    * Calculates and returns the x-coordinate of the point on this profile curve that is mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point on this profile curve that is mapped from the floating point argument.
    */

   abstract public float mapX(float t);

   /**
    * Calculates and returns the y-coordinate of the point on this profile curve that is mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point on this profile curve that is mapped from the floating point argument.
    */

   abstract public float mapY(float t);

   /**
    * Calculates and returns the z-coordinate of the point on this profile curve that is mapped from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point on this profile curve that is mapped from the floating point argument.
    */

   abstract public float mapZ(float t);

   /**
    * Calculates and returns the x-coordinate of the point mapped by the derivative of the generating curve  from
    * the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point mapped by the derivative of the generating curve  from the floating point argument.
    */

   abstract public float derivativeMapX(float t);

   /**
    * Calculates and returns the y-coordinate of the point mapped by the derivative of the generating curve  from
    * the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the derivative of the generating curve  from the floating point argument.
    */

   abstract public float derivativeMapY(float t);

   /**
    * Calculates and returns the z-coordinate of the point mapped by the derivative of the generating curve  from
    * the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point mapped by the derivative of the generating curve  from the floating point argument.
    */

   abstract public float derivativeMapZ(float t);

   /**
    * Calculates and returns the x-coordinate of the point mapped by the second derivative of the generating curve  from
    * the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point mapped by the second derivative of the generating curve  from the floating point argument.
    */

   abstract public float secondDerivativeMapX(float t);

   /**
    * Calculates and returns the y-coordinate of the point mapped by the second derivative of the generating curve  from
    * the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the second derivative of the generating curve  from the floating point argument.
    */

   abstract public float secondDerivativeMapY(float t);

   /**
    * Calculates and returns the z-coordinate of the point mapped by the second derivative of the generating curve  from
    * the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point mapped by the second derivative of the generating curve  from the floating point argument.
    */

   abstract public float secondDerivativeMapZ(float t);

   /**
    * Calculates and returns the x-coordinate of the point mapped by the third derivative of the generating curve  from
    * the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the point mapped by the third derivative of the generating curve  from the floating point argument.
    */

   abstract public float thirdDerivativeMapX(float t);

   /**
    * Calculates and returns the y-coordinate of the point mapped by the third derivative of the generating curve  from
    * the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the third derivative of the generating curve  from the floating point argument.
    */

   abstract public float thirdDerivativeMapY(float t);

   /**
    * Calculates and returns the z-coordinate of the point mapped by the third derivative of the generating curve  from
    * the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the point mapped by the third derivative of the generating curve  from the floating point argument.
    */

   abstract public float thirdDerivativeMapZ(float t);

   /**
    * Calculates and returns the x-coordinate of the unit vector mapped by the Frenet-Serret tangent mapping of the generating curve associated with
    * this tube from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the unit vector mapped by the Frenet-Serret tangent mapping of the generating curve  from the floating point argument.
    */

   public float tangentMapX(float t) {

	  float x = derivativeMapX(t);
	  float y = derivativeMapY(t);
	  float z = derivativeMapZ(t);

      return x / (float)Math.sqrt(x * x + y * y + z * z);

   }

   /**
    * Calculates and returns the y-coordinate of the unit vector mapped by the Frenet-Serret tangent mapping of the generating curve associated with
    * this tube from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the unit vector mapped by the Frenet-Serret tangent mapping of the generating curve  from the floating point argument.
    */

   public float tangentMapY(float t) {

	  float x = derivativeMapX(t);
	  float y = derivativeMapY(t);
	  float z = derivativeMapZ(t);

      return y / (float)Math.sqrt(x * x + y * y + z * z);

    }

   /**
    * Calculates and returns the z-coordinate of the unit vector mapped by the Frenet-Serret tangent mapping of the generating curve associated with
    * this tube from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the unit vector mapped by the Frenet-Serret tangent mapping of the generating curve  from the floating point argument.
    */

   public float tangentMapZ(float t) {

	  float x = derivativeMapX(t);
	  float y = derivativeMapY(t);
	  float z = derivativeMapZ(t);

      return z / (float)Math.sqrt(x * x + y * y + z * z);

   }

   /**
    * Calculates and returns the x-coordinate of the unit vector mapped by the Frenet-Serret normal mapping of the generating curve associated with
    * this tube from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the unit vector mapped by the Frenet-Serret normal mapping of the generating curve  from the floating point argument.
    */

   public float normalMapX(float t) { return binormalMapY(t) * tangentMapZ(t) - binormalMapZ(t) * tangentMapY(t); }

   /**
    * Calculates and returns the y-coordinate of the unit vector mapped by the Frenet-Serret normal mapping of the generating curve associated with
    * this tube from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the unit vector mapped by the Frenet-Serret normal mapping of the generating curve  from the floating point argument.
    */

   public float normalMapY(float t) { return binormalMapZ(t) * tangentMapX(t) - binormalMapX(t) * tangentMapZ(t); }

   /**
    * Calculates and returns the z-coordinate of the unit vector mapped by the Frenet-Serret normal mapping of the generating curve associated with
    * this tube from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the unit vector mapped by the Frenet-Serret normal mapping of the generating curve  from the floating point argument.
    */

   public float normalMapZ(float t) { return binormalMapX(t) * tangentMapY(t) - binormalMapY(t) * tangentMapX(t); }

   /**
    * Calculates and returns the x-coordinate of the unit vector mapped by the Frenet-Serret binormal mapping of the generating curve associated with
    * this tube from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the unit vector mapped by the Frenet-Serret binormal mapping of the generating curve  from the floating point argument.
    */

   public float binormalMapX(float t) {

      float x = derivativeMapY(t) * secondDerivativeMapZ(t) - derivativeMapZ(t) * secondDerivativeMapY(t);
      float y = derivativeMapZ(t) * secondDerivativeMapX(t) - derivativeMapX(t) * secondDerivativeMapZ(t);
      float z = derivativeMapX(t) * secondDerivativeMapY(t) - derivativeMapY(t) * secondDerivativeMapX(t);

      return x / (float)Math.sqrt(x * x + y * y + z * z);

   }

   /**
    * Calculates and returns the y-coordinate of the unit vector mapped by the Frenet-Serret binormal mapping of the generating curve associated with
    * this tube from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the unit vector mapped by the Frenet-Serret binormal mapping of the generating curve  from the floating point argument.
    */

   public float binormalMapY(float t) {

      float x = derivativeMapY(t) * secondDerivativeMapZ(t) - derivativeMapZ(t) * secondDerivativeMapY(t);
      float y = derivativeMapZ(t) * secondDerivativeMapX(t) - derivativeMapX(t) * secondDerivativeMapZ(t);
      float z = derivativeMapX(t) * secondDerivativeMapY(t) - derivativeMapY(t) * secondDerivativeMapX(t);

      return y / (float)Math.sqrt(x * x + y * y + z * z);

   }

   /**
    * Calculates and returns the z-coordinate of the unit vector mapped by the Frenet-Serret binormal mapping of the generating curve associated with
    * this tube from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the unit vector mapped by the Frenet-Serret binormal mapping of the generating curve  from the floating point argument.
    */

   public float binormalMapZ(float t) {

      float x = derivativeMapY(t) * secondDerivativeMapZ(t) - derivativeMapZ(t) * secondDerivativeMapY(t);
      float y = derivativeMapZ(t) * secondDerivativeMapX(t) - derivativeMapX(t) * secondDerivativeMapZ(t);
      float z = derivativeMapX(t) * secondDerivativeMapY(t) - derivativeMapY(t) * secondDerivativeMapX(t);

      return z / (float)Math.sqrt(x * x + y * y + z * z);

   }

   /**
    * Calculates and returns the x-coordinate of the vector mapped by the derivative of the Frenet-Serret tangent mapping of the generating curve
    *  from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the vector mapped by the derivative of the Frenet-Serret tangent mapping of the generating curve  from the floating point argument.
    */

   public float tangentDerivativeMapX(float t) {

	  float x = derivativeMapX(t);
	  float y = derivativeMapY(t);
	  float z = derivativeMapZ(t);

      float speed = (float)Math.sqrt(x * x + y * y + z * z);

      return speed * kappa(t) * normalMapX(t);

   }

   /**
    * Calculates and returns the y-coordinate of the vector mapped by the derivative of the Frenet-Serret tangent mapping of the generating curve
    *  from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the vector mapped by the derivative of the Frenet-Serret tangent mapping of the generating curve  from the floating point argument.
    */

   public float tangentDerivativeMapY(float t) {

	  float x = derivativeMapX(t);
	  float y = derivativeMapY(t);
	  float z = derivativeMapZ(t);

      float speed = (float)Math.sqrt(x * x + y * y + z * z);

      return speed * kappa(t) * normalMapY(t);

   }

   /**
    * Calculates and returns the z-coordinate of the vector mapped by the derivative of the Frenet-Serret tangent mapping of the generating curve
    *  from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the vector mapped by the derivative of the Frenet-Serret tangent mapping of the generating curve  from the floating point argument.
    */

   public float tangentDerivativeMapZ(float t) {

	  float x = derivativeMapX(t);
	  float y = derivativeMapY(t);
	  float z = derivativeMapZ(t);

      float speed = (float)Math.sqrt(x * x + y * y + z * z);

      return speed * kappa(t) * normalMapZ(t);

   }

   /**
    * Calculates and returns the x-coordinate of the vector mapped by the derivative of the Frenet-Serret normal mapping of the generating curve
    *  from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the vector mapped by the derivative of the Frenet-Serret normal mapping of the generating curve  from the floating point argument.
    */

   public float normalDerivativeMapX(float t) {

	  float x = derivativeMapX(t);
	  float y = derivativeMapY(t);
	  float z = derivativeMapZ(t);

      float speed = (float)Math.sqrt(x * x + y * y + z * z);

      return -speed * (kappa(t) * tangentMapX(t) - tau(t) * binormalMapX(t));

   }

   /**
    * Calculates and returns the y-coordinate of the vector mapped by the derivative of the Frenet-Serret normal mapping of the generating curve
    *  from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the vector mapped by the derivative of the Frenet-Serret normal mapping of the generating curve  from the floating point argument.
    */

   public float normalDerivativeMapY(float t) {

	  float x = derivativeMapX(t);
	  float y = derivativeMapY(t);
	  float z = derivativeMapZ(t);

      float speed = (float)Math.sqrt(x * x + y * y + z * z);

      return -speed * (kappa(t) * tangentMapY(t) - tau(t) * binormalMapY(t));

   }

   /**
    * Calculates and returns the z-coordinate of the vector mapped by the derivative of the Frenet-Serret normal mapping of the generating curve
    *  from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the vector mapped by the derivative of the Frenet-Serret normal mapping of the generating curve  from the floating point argument.
    */

   public float normalDerivativeMapZ(float t) {

	  float x = derivativeMapX(t);
	  float y = derivativeMapY(t);
	  float z = derivativeMapZ(t);

      float speed = (float)Math.sqrt(x * x + y * y + z * z);

      return -speed * (kappa(t) * tangentMapZ(t) - tau(t) * binormalMapZ(t));

   }

   /**
    * Calculates and returns the x-coordinate of the vector mapped by the derivative of the Frenet-Serret binormal mapping of the generating curve
    *  from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the x-coordinate of the vector mapped by the derivative of the Frenet-Serret binormal mapping of the generating curve  from the floating point argument.
    */

   public float binormalDerivativeMapX(float t) {

	  float x = derivativeMapX(t);
	  float y = derivativeMapY(t);
	  float z = derivativeMapZ(t);

      float speed = (float)Math.sqrt(x * x + y * y + z * z);

      return -speed * tau(t) * normalMapX(t);

   }

   /**
    * Calculates and returns the y-coordinate of the vector mapped by the derivative of the Frenet-Serret binormal mapping of the generating curve
    *  from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the vector mapped by the derivative of the Frenet-Serret binormal mapping of the generating curve  from the floating point argument.
    */

   public float binormalDerivativeMapY(float t) {

	  float x = derivativeMapX(t);
	  float y = derivativeMapY(t);
	  float z = derivativeMapZ(t);

      float speed = (float)Math.sqrt(x * x + y * y + z * z);

      return -speed * tau(t) * normalMapY(t);

   }

   /**
    * Calculates and returns the z-coordinate of the vector mapped by the derivative of the Frenet-Serret binormal mapping of the generating curve
    *  from the floating point argument.
    *
    * @param t the parametric variable argument that this mapping is evaluated at.
    * @return the z-coordinate of the vector mapped by the derivative of the Frenet-Serret binormal mapping of the generating curve  from the floating point argument.
    */

   public float binormalDerivativeMapZ(float t) {

	  float x = derivativeMapX(t);
	  float y = derivativeMapY(t);
	  float z = derivativeMapZ(t);

      float speed = (float)Math.sqrt(x * x + y * y + z * z);

      return -speed * tau(t) * normalMapZ(t);

   }

   /**
    * Calculates and returns the Frenet-Serret curvature of the generating curve  at the point determined by the floating point argument.
    *
    * @param t the parametric variable argument that the Frenet-Serret curvature is evaluated at.
    * @return the Frenet-Serret curvature of the generating curve  at the point determined by the floating point argument.
    */

   public float kappa(float t) {

      float x1 = derivativeMapY(t) * secondDerivativeMapZ(t) - derivativeMapZ(t) * secondDerivativeMapY(t);
      float y1 = derivativeMapZ(t) * secondDerivativeMapX(t) - derivativeMapX(t) * secondDerivativeMapZ(t);
      float z1 = derivativeMapX(t) * secondDerivativeMapY(t) - derivativeMapY(t) * secondDerivativeMapX(t);

      float x2 = derivativeMapX(t);
      float y2 = derivativeMapY(t);
      float z2 = derivativeMapZ(t);

      return (float)Math.sqrt(x1 * x1 + y1 * y1 + z1 * z1) / (float)Math.pow(x2 * x2 + y2 * y2 + z2 * z2, 1.5f);

   }

   /**
    * Calculates and returns the Frenet-Serret torsion of the generating curve  at the point determined by the floating point argument.
    *
    * @param t the parametric variable argument that the Frenet-Serret torsion is evaluated at.
    * @return the Frenet-Serret torsion of the generating curve  at the point determined by the floating point argument.
    */

   public float tau(float t) {

      float x1 = derivativeMapY(t) * secondDerivativeMapZ(t) - derivativeMapZ(t) * secondDerivativeMapY(t);
      float y1 = derivativeMapZ(t) * secondDerivativeMapX(t) - derivativeMapX(t) * secondDerivativeMapZ(t);
      float z1 = derivativeMapX(t) * secondDerivativeMapY(t) - derivativeMapY(t) * secondDerivativeMapX(t);

      float x2 = thirdDerivativeMapX(t);
      float y2 = thirdDerivativeMapY(t);
      float z2 = thirdDerivativeMapZ(t);

      return (x1 * x2 + y1 * y2 + z1 * z2) / (x1 * x1 + y1 * y1 + z1 * z1);

   }

   /**
    * Returns the parametric variable lower bound that determines the boundary of this profile curve.
    *
    * @return the parametric variable lower bound that determines the boundary of this profile curve.
    */

   public float getLowerBoundsT() { return lowerBoundsT; }

   /**
    * Returns the parametric variable upper bound that determines the boundary of this profile curve.
    *
    * @return the parametric variable upper bound that determines the boundary of this profile curve.
    */

   public float getUpperBoundsT() { return upperBoundsT; }

   /**
    * Sets the parametric variable lower bound to the floating point argument.
    *
    * @param lowerBoundsT the value that the parametric variable lower bound is set to.
    */

   public void setLowerBoundsT(float lowerBoundsT) { this.lowerBoundsT = lowerBoundsT; }

   /**
    * Sets the parametric variable upper bound to the floating point argument.
    *
    * @param upperBoundsT the value that the parametric variable upper bound is set to.
    */

   public void setUpperBoundsT(float upperBoundsT) { this.upperBoundsT = upperBoundsT; }

}