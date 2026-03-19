package shamu.media.render;

/** Class responsible for rendering all mesh nodes in a scene graph. Associated with a scene graph by maintaining a linked list of locales associated
 *  with a scene graph. Uses the observer pattern coordinating view objects with a notifier and updates their corresponding frame buffers during
 *  rendering.
 *
 *  @see RenderNotifier
 *
 *  @author	Scott T. Carter
 *  @version 1.4
 */

public class SceneGraphAgent {

   /**
    * RenderNotifier associated with this scene graph agent.
    */

   protected RenderNotifier notifier;

   /**
    * List of scene locale objects representing a scene graph associated with this scene graph agent.
    */

   protected java.util.LinkedList<SceneLocale> locales = new java.util.LinkedList<SceneLocale>();

   /**
    * Constructs a scene graph agent associated with the notifier argument.
    *
    * @param notifier the notifier associated with this scene graph agent.
    */

   public SceneGraphAgent(RenderNotifier notifier) { this.notifier = notifier; }

   /**
    * Initializes each observer registered to the notifier associated with this scene graph agent for rendering.
    */

   public void wakeup() { notifier.startObservers(); }

   /**
    * Starts rendering each locale in the scene graph associated with this scene graph agent into the frame buffers of each view object registered to the
    * notifier associated with this scene graph agent.
    *
    * @see #render(SceneLocale)
    */

   public void start() {

      int cameraLimit = notifier.getFocusBlurDimension() / 2;
      int spatialLimit = notifier.getSuperSamplingDimension() / 2;
      int temporalLimit = notifier.getMotionBlurDimension();

      for(int t = 0; t < temporalLimit; t++) {

         notifier.setMotionOffsetIndex(t);

         for(int m = -cameraLimit; m <= cameraLimit; m++) {
            for(int k = -cameraLimit; k <= cameraLimit; k++) {

               for(int j = -spatialLimit; j <= spatialLimit; j++) {
                  for(int i = -spatialLimit; i <= spatialLimit; i++) {

                        notifier.setAntiAliasingOffsetX(i);
                        notifier.setAntiAliasingOffsetY(j);

                        notifier.setFocusOffsetIndexX(k);
                        notifier.setFocusOffsetIndexY(m);

                        java.util.ListIterator<SceneLocale> iterator = locales.listIterator(0);

                        while(iterator.hasNext())
                           render(iterator.next());

                        notifier.notifyObservers();

                        notifier.forwardObservers();

                  }

               }

            }

         }

      }

   }

   /**
    * Calls the notifier associated with this scene graph agent to display the frame buffer associated with each view object registered to the notifier
    * associated with this scene graph agent, into the device associated with each view.
    */

   public void stop() { notifier.stopObservers(); }

   /**
    * Renders the locale argument into the frame buffer of each view object registered to the notifier associated with this scene graph agent.
    * Automatically called for each locale in the scene graph associated with this scene graph agent by a call to the start() method of this scene graph agent.
    *
    * @param locale the locale in the scene graph associated with this scene graph agent that is traversed and rendered.
    * @see #start()
    */

   protected void render(SceneLocale locale) {

//      java.util.LinkedList<SceneL> branches = locale.getBranches();

      java.util.ListIterator<SceneGraphBranch> iterator = locale.getBranches()
                                                                .listIterator(0);

      while(iterator.hasNext())
         update(iterator.next());

   }

   /**
    * Updates the branch argument in the scene graph associated with this scene graph agent by rendering all mesh node descendents of the branch argument.
    *
    * @param branch the branch in the scene graph associated with this scene graph agent that is traversed and updated.
    */

   protected void update(SceneGraphBranch branch) {

      Matrix4D matrix = new Matrix4D();

      matrix.setElement(0, 0, 1.0f);
      matrix.setElement(0, 1, 0.0f);
      matrix.setElement(0, 2, 0.0f);
      matrix.setElement(0, 3, 0.0f);

      matrix.setElement(1, 0, 0.0f);
      matrix.setElement(1, 1, 1.0f);
      matrix.setElement(1, 2, 0.0f);
      matrix.setElement(1, 3, 0.0f);

      matrix.setElement(2, 0, 0.0f);
      matrix.setElement(2, 1, 0.0f);
      matrix.setElement(2, 2, 1.0f);
      matrix.setElement(2, 3, 0.0f);

      matrix.setElement(3, 0, 0.0f);
      matrix.setElement(3, 1, 0.0f);
      matrix.setElement(3, 2, 0.0f);
      matrix.setElement(3, 3, 1.0f);
	
     branch.traverse(notifier, matrix);

   }

   /**
    * Adds the scene locale argument to the linked list representation of the scene graph associated with this scene graph agent.
    *
    * @param locale the scene locale to be added to the linked list representation of the scene graph associated with this scene graph agent.
    */

   public void addLocale(SceneLocale locale) { locales.add(locale); }

   /**
    * Removes the first occurence of the scene locale argument from the linked list representation of the scene graph associated with this scene graph agent.
    *
    * @param locale the scene locale to be removed from the linked list representation of the scene graph associated with this scene graph agent.
    * @return true if the scene locale argument is in the linked list representation of the scene graph associated with this scene graph agent, false otherwise.
    */

   public boolean removeLocale(SceneLocale locale) { return locales.remove(locale); }

   /**
    * Returns the notifier associated with this scene graph agent.
    *
    * @return the notifier associated with this scene graph agent.
    */

   public RenderNotifier getRenderNotifier() { return notifier; }

   /**
    * Sets the notifier associated with this scene graph agent to the notifier argument.
    *
    * @param notifier the notifier that the notifier associated with this scene graph agent is set to.
    */

   public void setRenderNotifier(RenderNotifier notifier) { this.notifier = notifier; }

}


