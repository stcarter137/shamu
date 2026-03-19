package shamu.media.render;

/** Class indicating that a mesh primitive size is not supported.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class IllegalMeshTypeException extends RuntimeException {

   /**
    * Constructs an illegal mesh type exception without a specific message.
    */

   public IllegalMeshTypeException() { super(); }

   /**
    * Constructs a illegal mesh type exception with the message specified by the string argument.
    *
    * @param message the detailed message associated with this illegal mesh type exception.
    */

   public IllegalMeshTypeException(String message) { super(message); }

}