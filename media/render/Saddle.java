package shamu.media.render;

/** Procedural modeling class representing a saddle surface that is parameterized as the monge patch [x, y, z] = [x, (x * z) / alpha , z].
 *
 *  @see MongePatch
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class Saddle extends MongePatch {

   /**
    * The alpha parameter associated with this saddle.
    */

   protected float alpha;

   /**
    * Constructs a default saddle.
    */

   public Saddle(int subdivisionsU1, int subdivisionsU2) { super(); }

   /**
    * Constructs a saddle having the alpha parameter and boundary determined by the floating point arguments.
    *
    * @param alpha the value that the alpha parameter of this saddle is set to.
    * @param lowerBoundsU1 the value that the u1 local variable lower bound that determines the boundary of this saddle is set to.
    * @param lowerBoundsU2 the value that the u2 local variable lower bound that determines the boundary of this saddle is set to.
    * @param upperBoundsU1 the value that the u1 local variable upper bound that determines the boundary of this saddle is set to.
    * @param upperBoundsU2 the value that the u2 local variable upper bound that determines the boundary of this saddle is set to.
    */

   public Saddle(float alpha, float lowerBoundsU1, float lowerBoundsU2, float upperBoundsU1, float upperBoundsU2) {

   	  super(lowerBoundsU1, lowerBoundsU2, upperBoundsU1, upperBoundsU2);

	  this.alpha = alpha;

   }

   /**
    * Calculates and returns the y-coordinate of the point on this saddle that is mapped from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point on this saddle that is mapped from the floating point arguments.
    */

   public float mapY(float u1, float u2) { return u1 * u2 / alpha; }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the partial derivative of this saddle with respect to the u1 local variable
    * from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the partial derivative of this saddle with respect to the u1 local variable from the floating point arguments.
    */

   public float partialU1MapY(float u1, float u2) { return u2 / alpha; }

   /**
    * Calculates and returns the y-coordinate of the point mapped by the partial derivative of this saddle with respect to the u2 local variable
    * from the floating point arguments.
    *
    * @param u1 the u1 local variable argument that this mapping is evaluated at.
    * @param u2 the u2 local variable argument that this mapping is evaluated at.
    * @return the y-coordinate of the point mapped by the partial derivative of this saddle with respect to the u2 local variable from the floating point arguments.
    */

   public float partialU2MapY(float u1, float u2) { return u1 / alpha; }

   /**
    * Returns the alpha parameter associated with this saddle.
    *
    * @return the alpha parameter associated with this saddle.
    */

   public float getAlpha() { return alpha; }

   /**
    * Sets the value of the alpha parameter associated with this saddle to the floating point argument.
    *
    * @param alpha the value that the alpha parameter associated with this saddle is set to.
    */

   public void setAlpha(float alpha) { this.alpha = alpha; }

}


