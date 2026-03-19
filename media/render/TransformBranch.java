package shamu.media.render;

/** Class that subclasses SceneGraphBranch representing a non-terminal scene graph node that is associated with a 3D transform.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class TransformBranch extends SceneGraphBranch {

   /**
    * The world transformation matrix associated with this transform branch.
    */

   protected Matrix4D matrix;

   /**
    * Constructs a transform branch associated with the matrix argument.
    *
    * @param matrix the matrix that the world transformation matrix associated with this transform branch is set to.
    */

   public TransformBranch(Matrix4D matrix) { this.matrix = matrix; }

   /**
    * Concatenates the matrix argument to the matrix associated with this transform branch, recursively traverses each child subtree associated with this scene
    * graph branch, updating the visualizer argument state information, and notifying the visualizer argument's registered view objects of changes needing to be
    * made to their frame buffers.
    *
	* @param visualizer the visualizer whose state information is updated in the recursive call and who notifies view objects of changes needed to made to their frame buffers.
    * @param currentMatrix the matrix that is modified and passed to each subtree associated with this transform branch.
    */

   protected void traverse(RenderNotifier notifier, Matrix4D currentMatrix) {

      Matrix4D nextMatrix = new Matrix4D();

      nextMatrix.multiply(currentMatrix, matrix);

//Matrix4D inv = new Matrix4D();

//nextMatrix.invert(inv);

//float xx =inv.getElement(0,3);
//float yy =inv.getElement(1,3);
//float zz = inv.getElement(2,3);

//Matrix4D mat = new Matrix4D();

//mat.multiply(nextMatrix, inv);

//for(int i = 0; i < 4; i++)
//System.out.println(mat.getElement(i,0) +  "   " + mat.getElement(i,1)  + "   " + mat.getElement(i,2) + "   " + mat.getElement(i,3));



      super.traverse(notifier, nextMatrix);

   }

   /**
    * Returns the  matrix associated with this transform branch.
    *
    * @return the matrix associated with this transform branch.
    */

   public Matrix4D getMatrix() { return matrix; }

   /**
    * Sets the matrix associated with this transform branch to the matrix argument.
    *
    * @param matrix the matrix that the matrix associated with this transform branch is set to.
    */

   public void setMatrix(Matrix4D matrix) { this.matrix = matrix; }

}
