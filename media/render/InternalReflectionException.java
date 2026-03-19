package shamu.media.render;

/** Class indicating that a backward traced transmitted beam should be terminated due to the occurence of total internal reflection in a substance.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class InternalReflectionException extends RuntimeException {

   /**
    * Constructs an internal reflection exception without a specific message.
    */

   public InternalReflectionException() { super(); }

   /**
    * Constructs an internal reflection exception with the message specified by the string argument.
    *
    * @param message the detailed message associated with this internal reflection exception.
    */

   public InternalReflectionException(String message) { super(message); }

}