package shamu.media.render;

/** Class that subclasses SceneGraphNode representing a non-terminal scene graph node that may have one or more scene graph node children.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class SceneGraphBranch extends SceneGraphNode {

   /**
    * List of scene graph node children of this scene graph branch.
    */

   protected java.util.LinkedList<SceneGraphNode> children = new java.util.LinkedList<SceneGraphNode>();

   /**
    * Constructs a scene graph branch whose list of scene graph node children is empty.
    */

   public SceneGraphBranch() { super(); }

   /**
    * Adds a child scene graph node to this list of scene graph node children assocated with this scene graph branch.
    *
    * @param child the scene graph node to be added to the list of scene graph node children associated with this scene graph branch.
    */

   public void addChild(SceneGraphNode child) { children.add(child); }

   /**
    * Removes the first occurence of the scene graph node argument from the list of scene graph node children associated with this scene graph branch.
    *
    * @param child the scene graph node to be removed from the list of scene graph node children associated with this scene graph branch.
    * @return true if the scene graph node argument is in the list of scene graph node children associated with this scene graph branch, false otherwise.
    */

   public boolean removeChild(SceneGraphNode child) { return children.remove(child); }

   /**
    * Recursively traverses each subtree associated with this scene graph branch, updating the render notifier argument state information, and notifying
    * the render notifierargument's registered view objects of changes needed to be made to their frame buffers.
    *
	* @param notifier the render notifier whose state information is updated in the recursive call and who notifies view objects of changes needed to made to their frame buffers.
	* @param currentMatrix the matrix that is passed to each subtree associated with this scene graph branch.
    */

   protected void traverse(RenderNotifier notifier, Matrix4D currentMatrix) {

      java.util.ListIterator iterator = children.listIterator(0);

      while(iterator.hasNext()) {

		  SceneGraphNode node = (SceneGraphNode)iterator.next();

   	  node.traverse(notifier, currentMatrix);

	}

   }

   /**
    * Returns the list of scene graph node children of this scene graph branch.
    *
    * @return the list of scene graph node children of this scene graph branch.
    */

   public java.util.LinkedList<SceneGraphNode> getChildren() { return children; }

   /**
    * Sets the list of children associated with this scene graph branch to the linked list argument.
    *
    * @param children the list of scene graph nodes that the children associated with this scene graph branch is set to.
    */

   public void setChildren(java.util.LinkedList<SceneGraphNode> children) { this.children = children; }

}
