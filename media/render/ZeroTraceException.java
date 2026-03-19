package shamu.media.render;

/** Class indicating that the trace of a matrix is zero.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class ZeroTraceException extends RuntimeException {

   /**
    * Constructs a zero trace exception without a detailed message.
    */

   public ZeroTraceException() {}

   /**
    * Constructs a zero trace exception with the message specified by the string argument.
    *
    * @param message the detailed message associated with this zero trace exception.
    */

   public ZeroTraceException(String message) { super(message); }

}



