package shamu.media.render;

/** Abstract class representing a general terminal or non-terminal node in a scene graph.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

abstract public class SceneGraphNode {

   /**
    * Constructs a new scene graph node.
    */

   protected SceneGraphNode() {}

   /**
    * Recursively traverses the subtree in the scene graph having this scene graph node as the root, updating the render notifier argument state information, and
    * notifying the render notifier argument's registered view objects of changes needed to be made to their frame buffers.
	*
	* @param notifier the notifier whose state information is updated in the recursive call and who notifies view objects of changes needed to made to their frame buffers.
	* @param currentMatrix the matrix that is passed to each subtree associated with this scene graph node.
    */

   abstract protected void traverse(RenderNotifier notifier, Matrix4D currentMatrix);

}
