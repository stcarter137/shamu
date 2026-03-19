package shamu.media.render;

/** Class indicating that the current texturing state defined in the RenderingConstantsI interface is incompatible with the current shading state.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class InvalidTexturingException extends RuntimeException {

   /**
    * Constructs an invalid texturing exception without a detailed message.
    */

   public InvalidTexturingException() {}

   /**
    * Constructs an invalid texturing exception with the message specified by the string argument.
    *
    * @param message the detailed message associated with this invalid texturing exception.
    */

   public InvalidTexturingException(String message) { super(message); }

}



