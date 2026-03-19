package shamu.media.render;

/** Class indicating that a mathematical calculation, especially higher order vector differentiation, exceeds a preset time limit.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class CalculationTimeLimitException extends RuntimeException {

   /**
    * Constructs a calculation time limit exception without a specific message.
    */

   public CalculationTimeLimitException() { super(); }

   /**
    * Constructs a calculation time limit exception with the message specified by the string argument.
    *
    * @param message the detailed message associated with this calculation time limit exception.
    */

   public CalculationTimeLimitException(String message) { super(message); }

}