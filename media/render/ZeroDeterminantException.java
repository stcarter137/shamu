package shamu.media.render;

/** Class indicating that the determinant of a matrix is zero.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class ZeroDeterminantException extends RuntimeException {

   /**
    * Constructs a zero determinant exception without a detailed message.
    */

   public ZeroDeterminantException() {}

   /**
    * Constructs a zero determinant exception with the detailed message specified by the string argument.
    *
    * @param message the detailed message associated with this zero determinant exception.
    */

   public ZeroDeterminantException(String message) { super(message); }

}


