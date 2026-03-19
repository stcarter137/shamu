package shamu.media.render;

/** Class representing the root of a subgraph in a scene graph containing high resolution information.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class SceneLocale  {

   /**
    * The state determining whether this locale is rendered when passed into the render() method of a rendering engine object.
    */

   protected boolean enabled = true;

   /**
    * The branches of a scene graph that have this scene locale as their parent.
    */

   protected java.util.LinkedList<SceneGraphBranch> branches = new java.util.LinkedList<SceneGraphBranch>();

   /**
    * Constructs an empty scene locale having no children.
    */

   public SceneLocale() {}

   /**
    * Adds the scene graph branch argument to the list of branch children associated with this scene locale.
    *
    * @param branch the child branch added to the list of branch children associated with this scene locale.
    */

   public void addBranch(SceneGraphBranch branch) { branches.add(branch); }

   public void removeBranch(SceneGraphBranch branch) { branches.remove(branch); }

   /**
    * Returns the enabled state associated with this scene locale.
    *
    * @param return the enabled state associated with this scene locale.
    */

   public boolean getEnabled() { return enabled; }

   /**
    * Returns the list of branch children associated with this scene locale.
    *
    * @return the list of branch children associated with this scene locale.
    */

   public java.util.LinkedList<SceneGraphBranch> getBranches() { return branches; }

   /**
    * Sets the enabled state associated withh this scene locale to the boolean value.
    *
    * @param enabled the boolean value that the enabled state associated with this scene locale is set to.
    */

   public void setEnabled(boolean enabled) { this.enabled = enabled; }

   /**
    * Sets the list of branch children associated with this scene locale to the linked list argument.
    *
    * @param branches the linked list that the list of branch children associated with this scene locale is set to.
    */

   public void setBranches(java.util.LinkedList<SceneGraphBranch> branches) { this.branches = branches; }

}

