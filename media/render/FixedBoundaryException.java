package shamu.media.render;

/** Class indicating that the boundary of a parametric variable is fixed.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class FixedBoundaryException extends RuntimeException {

   /**
    * Constructs a fixed boundary exception without a specific message.
    */

   public FixedBoundaryException() { super(); }

   /**
    * Constructs a fixed boundary exception with the message specified by the string argument.
    *
    * @param message the detailed message associated with this fixed boundary exception.
    */

   public FixedBoundaryException(String message) { super(message); }

}