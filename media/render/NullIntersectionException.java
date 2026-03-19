package shamu.media.render;

/** Class indicating that the intersection between a pair of raytracing geometric objects is the empty set.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class NullIntersectionException extends RuntimeException {

   /**
    * Constructs a null intersection exception without a specific message.
    */

   public NullIntersectionException() { super(); }

   /**
    * Constructs a null intersection exception with the message specified by the string argument.
    *
    * @param message the detailed message associated with this null intersection exception.
    */

   public NullIntersectionException(String message) { super(message); }

}
