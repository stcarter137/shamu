package shamu.media.render;

/** Class responsible for storing current geometry, material, texture, and world transformation matrix state information, and notifying view objects that
 *  new mesh objects are to be rendered into their frame buffers.
 *
 *  @see Receptor
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class RenderNotifier {

   protected java.util.LinkedList<BeamInterceptor> visuals  = new java.util.LinkedList<BeamInterceptor>();

   //protected java.util.LinkedList<Matrix4D> transformers = new java.util.LinkedList<Matrix4D>();

   /**
    * The motion attributes applied to the current mesh object being rendered.
    */

    protected MotionAttributes currentMotionAttributes = null;

   /**
    * The rendering attributes applied to the current mesh object being rendered.
    */

    protected RenderingAttributes currentRenderingAttributes = null;

   /**
    * The current geometry to be rendered into the frame buffer of each view object registered to this render notifier.
    */

    protected Geometry currentGeometry = null;

    protected ToolkitI currentToolkit = null;

   /**
    * The current object to world matrix applied to the current mesh object.
    */

   protected Matrix4D currentMatrix = null;

   /**
    * The current texture map array applied to the current mesh of this render notifier.
    */

   protected TextureMap[] currentTextureMapArray = {null, null, null, null, null, null, null, null};

   /**
    * The current texture attributes array applied to the current mesh of this render notifier.
    */

   protected TextureAttributes[] currentTextureAttributesArray = {null, null, null, null, null, null, null, null};

   /**
    * The vertex coordinates of the current primitive associated with this render notifier.
    */

   protected float[] currentPrimitiveCoordinates = null;

   /**
    * The index of the current primitive associated with this render notifier.
    */

   protected int currentPrimitiveIndex;

   /**
    * The horizontal pixel offset for antialiasing the current mesh object being rendered.
    */

   protected int antiAliasingOffsetX;

   /**
    * The vertical pixel offset for antialiasing the current mesh object being rendered.
    */

   protected int antiAliasingOffsetY;

   /**
    * The x-axis index to the updated aperature angle for applying the depth of field effect to the current mesh object being rendered.
    */

   protected int focusOffsetIndexX;

   /**
    * The y-axis index to the updated aperature angle for applying the depth of field effect to the current mesh object being rendered.
    */

   protected int focusOffsetIndexY;

   /**
    * The index to the updated time value for applying motion blur to the current mesh object being rendered.
    */

   protected int motionOffsetIndex;

   /**
    * The value that the dimensions of the pixel is divided into used for antialiasing the current mesh object being rendered.
    */

   protected int superSamplingDimension = 1;

   /**
    * The value of the number of jittered view segments for applying the depth of field effect to the current mesh object being rendered.
    */

   protected int focusBlurDimension = 1;

   /**
    * The value of the number of time segments for applying motion blur to the current mesh object being rendered.
    */

   protected int motionBlurDimension = 1;

   /**
    * Red wavelength diffuse lighting coefficient in the range 0.0 - 1.0, of the material of the current mesh associated with this render notifier.
    */

   protected float currentDiffuseCoefficientR = 1.0f;

   /**
    * Green wavelength diffuse lighting coefficient in the range 0.0 - 1.0, of the material of the current mesh associated with this render notifier.
    */

   protected float currentDiffuseCoefficientG = 1.0f;

   /**
    * Blue wavelength diffuse lighting coefficient in the range 0.0 - 1.0, of the material of the current mesh associated with this render notifier.
    */

   protected float currentDiffuseCoefficientB = 1.0f;

   /**
    * Red wavelength specular lighting coefficient in the range 0.0 - 1.0, of the material of the current mesh associated with this render notifier.
    */

   protected float currentSpecularCoefficientR = 1.0f;

   /**
    * Green wavelength specular lighting coefficient in the range 0.0 - 1.0, of the material of the current mesh associated with this render notifier.
    */

   protected float currentSpecularCoefficientG = 1.0f;

   /**
    * Blue wavelength specular lighting coefficient in the range 0.0 - 1.0, of the material of the current mesh associated with this render notifier.
    */

   protected float currentSpecularCoefficientB = 1.0f;

   /**
    * Red wavelength emissive lighting coefficient in the range 0.0 - 1.0, of the material of the current mesh associated with this render notifier.
    */

   protected float currentEmissiveCoefficientR = 1.0f;

   /**
    * Green wavelength emissive lighting coefficient in the range 0.0 - 1.0, of the material of the current mesh associated with this render notifier.
    */

   protected float currentEmissiveCoefficientG = 1.0f;

   /**
    * Blue wavelength emissive lighting coefficient in the range 0.0 - 1.0, of the material of the current mesh associated with this render notifier.
    */

   protected float currentEmissiveCoefficientB = 1.0f;

   /**
    * Red wavelength fresnel reflectivity coefficient at an angle of incidence of zero under illumination in the rgba model of the material of the current mesh associated with this render notifier.
    */

   protected float currentFresnelCoefficientR = 1.0f;

   /**
    * Green wavelength fresnel reflectivity coefficient at an angle of incidence of zero under illumination in the rgba model of the material of the current mesh associated with this render notifier.
    */

   protected float currentFresnelCoefficientG = 1.0f;

   /**
    * Blue wavelength fresnel reflectivity coefficient at an angle of incidence of zero under illumination in the rgba model of the material of the current mesh associated with this render notifier.
    */

   protected float currentFresnelCoefficientB = 1.0f;

   /**
    * Red wavelength diffuse reflectivity of the material of the current mesh associated with this render notifier.
    */

   protected float currentDiffuseReflectivityR = 1.0f;

   /**
    * Green wavelength diffuse reflectivity of the material of the current mesh associated with this render notifier.
    */

   protected float currentDiffuseReflectivityG = 1.0f;

   /**
    * Blue wavelength diffuse reflectivity of the material of the current mesh associated with this render notifier.
    */

   protected float currentDiffuseReflectivityB = 1.0f;

   /**
    * Specular exponent in the range 0.0 - 500.0, of the material of the current mesh associated with this render notifier.
    */

   protected float currentShininess = 5.0f;

   /**
    * Root mean square of microfacets slope in the Cook-Torrance model of the material of the current mesh associated with this render notifier.
    */

   protected float currentMicrofacetRoughness = 1.0f;

   /**
    * Array of lights associated with this render notifier.
    */

   protected Light[] lightArray = new Light[864];

   /**
    * Observers registered to this render notifier.
    */

   protected java.util.LinkedList<Receptor> observers = new java.util.LinkedList<Receptor>();

   /**
    * Constructs a default render notifier.
    */

   public RenderNotifier() {}

   /**
    * Registers the camera argument to this render notifier.
    *
    * @param camera the observer to be registered to this render notifier.
    */

   public void addObserver(Receptor camera) { observers.add(camera); }

   /**
    * Unregisters the camera argument from this render notifier.
    *
    * @param camera the observer to be unregistered from this render notifier.
    * @return true if the camera object is in the list of registered observers of this render notifier, false otherwise.
    */

   public boolean removeObserver(Receptor camera) { return observers.remove(camera); }

   /**
    * Returns the iterator corresponding to the list of observers associated with this render notifier.
    *
    * @return the iterator corresponding to the list of observers associated with this render notifier.
    */

   //public java.util.Iterator<Receptor> iterator() { return observers.listIterator(0); }

   /**
    * Triangulates the current mesh associated with this render notifier in object space coordinates, and notifies each camera object registered to this
    * render notifier of modifications needing to be made to their frame buffers.
    */

   protected void notifyObservers() {

      java.util.ListIterator<Receptor> iterator = observers.listIterator(0);

      while(iterator.hasNext()) {

        Receptor camera = iterator.next();

         camera.notify(this);

      }

   }

   /**
    * Initializes each camera object registered to this this render notifier for rendering a scene.
    */

   protected void startObservers() {

	  java.util.ListIterator<Receptor> iterator = observers.listIterator(0);


      while(iterator.hasNext()) {

         Receptor camera = iterator.next();

         camera.start();

      }

   }

   /**
    * Adds the current contents of the virtual buffer of each camera object registered to this render notifier to the accumulation buffer
    * of each view object associated with this render notifier.
    */

   protected void forwardObservers() {

	  java.util.ListIterator<Receptor> iterator = observers.listIterator(0);

      while(iterator.hasNext()) {

         Receptor camera = iterator.next();

         camera.forward();

	 }

   }

   /**
    * Finalizes the accumulation buffer of each camera object registered to this render notifier and displays it in the device associated with each view object.
    */

   protected void stopObservers() {

      java.util.ListIterator<Receptor> iterator = observers.listIterator(0);

      while(iterator.hasNext()) {

         Receptor camera = iterator.next();

         java.awt.image.WritableRaster raster = camera.getAccumulationBuffer();

         int normalizingFactor = superSamplingDimension * superSamplingDimension * focusBlurDimension * focusBlurDimension * motionBlurDimension;

         for(int i = 0; i < raster.getWidth(); i++) {
	        for(int j = 0; j < raster.getHeight(); j++) {

               float[] sample = new float[3];

               raster.getPixel(i, j, sample);

               sample[0] /= normalizingFactor;
               sample[1] /= normalizingFactor;
               sample[2] /= normalizingFactor;

	   	       raster.setPixel(i, j, sample);

	        }

         }

         camera.stop();

      }

   }

    public void shadow(Light tlight, float x1,float  y1, float  z1, float[] modulators) throws ShadowException {

     //     java.util.Iterator<TracerContext> iterator = visuals.listIterator(0);

          java.util.Iterator<BeamInterceptor> iterator = visuals.listIterator(0);


          while(iterator.hasNext()) {

 //              TracerContext context = iterator.next();

                BeamInterceptor interceptor = iterator.next();


//               BeamInterceptor obj3D = context.object3D;
//               Matrix4D matrix = context.inverseMatrix;

//		       BeamInterceptor obj3D = (BeamInterceptor)enum_.next();
//		       Matrix4D matrix = (Matrix4D)enum_.next();

               Light light = (Light)tlight.clone();

               float xl  = ((PointLight)light).getSourceX();
               float yl  = ((PointLight)light).getSourceY();
               float zl  = ((PointLight)light).getSourceZ();

               //TracerGeometry geometry = (TracerGeometry)obj3D.getGeometry();

               try {

			       //float t1 = geometry.intersection(x1, y1, z1, xl, yl, zl, matrix);

                   float t1 = interceptor.intersection(x1, y1, z1, xl, yl, zl);

                   if( t1 > .0001f && t1 < 1.0f) {

					     modulators[0] = interceptor.getShadowingModulatorR();
					     modulators[1] = interceptor.getShadowingModulatorG();
					     modulators[2] = interceptor.getShadowingModulatorB();

                       throw new ShadowException();

				   }

			   } catch(NullIntersectionException e) {}

		    }

       }
/*
       public BeamInterceptor hit(float startX,float  startY, float startZ, float endX, float endY, float endZ, float[] data) throws NullIntersectionException {

            //java.util.Iterator<TracerContext> iterator = visuals.listIterator();

            java.util.Iterator<BeamInterceptor> iterator = visuals.listIterator();

            float tmin = -1.0f;

            BeamInterceptor nearestInterceptor = null;

            while(iterator.hasNext()) {

               // TracerContext context = iterator.next();    

                BeamInterceptor interceptor = iterator.next();

                try {

                    float t = interceptor.intersection(startX, startY, startZ, endX, endY, endZ);

                    //float t = context.intersection(startX, startY, startZ, endX, endY, endZ);

                    if((t > 0)  && (tmin < 0.0f || t <= tmin)) {
                        
                        tmin = t;

                        nearestInterceptor = interceptor;

                        data[0] = startX  + t * (endX - startX);
                        data[1] = startY  + t * (endY - startY);
                        data[2] = startZ  + t * (endZ - startZ);
                    
                        //context.update(t, startX, startY, startZ, endX - startX, endY - startY, endZ - startZ, data);

                    }

                } catch(Exception e) {}

/*
                 BeamInterceptor obj3D = context.object3D;
                 Matrix4D matrix = context.inverseMatrix;

                 TracerGeometry geometry = (TracerGeometry)obj3D.getGeometry();

                 try {

                       float t = geometry.intersection(startX, startY, startZ, endX, endY, endZ, matrix);

                       if((t > 0)  && (tmin < 0.0f || t <= tmin)) {

                           tmin = t;

                           data[0]= obj3D;
                           data[1] = matrix;
                           data[2] = new float[3];

                           float directionX = endX - startX;
                           float directionY = endY - startY;
                           float directionZ = endZ - startZ;

			               ((float[])data[2])[0]=  startX  + directionX * t;
		                   ((float[])data[2])[1] = startY  + directionY * t;
			               ((float[])data[2])[2] = startZ  + directionZ * t;

				     }

			    } catch(Exception e) {}

		   }

           if(tmin < 0.0f)
              throw new NullIntersectionException();

           return nearestInterceptor;




     }
  */   
     public java.util.LinkedList<BeamInterceptor> getVisuals() { return visuals; }

     /**
      * Returns the light in the light array associated with this render notifier indexed by the integer argument.
      *
      * @param index the index of the light in the light array associated with this render notifier that is returned.
      * @return the light in the light array associated with this render notifier indexed by the integer argument.
      */

     public Light getLight(int index) { return lightArray[index]; }

     /**
       * Sets the light in the light array associated with this render notifier indexed by the integer argument to the light argument.
       *
       * @param light the light that the light at the position in the light array associated with this render notifier indexed by the index argument is set to.
       * @param index the index in the light array associated with this render notifier that the light argument is inserted into.
       */

      public void setLight(Light light, int index) { lightArray[index] = light; }

      /**
        * Returns the current motion attributes associated with this render notifier.
        *
        * @return the current motion attributes associated with this render notifier.
        */

       public MotionAttributes getCurrentMotionAttributes() { return currentMotionAttributes; }

       /**
         * Returns the current rendering attributes associated with this render notifier.
    *
    * @return the current rendering attributes associated with this render notifier.
    */

    public RenderingAttributes getCurrentRenderingAttributes() { return currentRenderingAttributes; }

   /**
    * Returns the current geometry associated with this render notifier.
    *
    * @return the current geometry associated with this render notifier.
    */

   public Geometry getCurrentGeometry() { return currentGeometry; }

   public ToolkitI getCurrentToolkit() { return currentToolkit; }

   /**
    * Returns the current object to world matrix associated with this render notifier.
    *
    * @return the current object to world matrix associated with this render notifier.
    */

   public Matrix4D getCurrentMatrix() { return currentMatrix; }

   /**
    * Returns the current texture map array associated with this render notifier.
    *
    * @return the current texture map array associated with this render notifier.
    */

   public TextureMap[] getCurrentTextureMapArray() { return currentTextureMapArray; }

   /**
    * Returns the current texture attributes array associated with this render notifier.
    *
    * @return the current texture attributes array associated with this render notifier.
    */

   public TextureAttributes[] getCurrentTextureAttributesArray() { return currentTextureAttributesArray; }

   /**
    * Returns the vertex coordinate array in screen coordinates of the current primitive associated with this render notifier.
    *
    * @return the vertex coordinate array in screen coordinates of the current primitive associated with this render notifier.
    */

   public float[] getCurrentPrimitiveCoordinates() { return currentPrimitiveCoordinates; }

   /**
    * Returns the index of the current primitive associated with this render notifier.
    *
    * @return the index of the current primitive associated with this render notifier.
    */

   public int getCurrentPrimitiveIndex() { return currentPrimitiveIndex; }

   /**
    * Returns the horizontal pixel offset for antialiasing the current mesh object being rendered.
    *
    * @return the horizontal pixel offset for antialiasing the current mesh object being rendered.
    */

   protected int getAntiAliasingOffsetX() { return antiAliasingOffsetX; }

   /**
    * Returns the vertical pixel offset for antialiasing the current mesh object being rendered.
    *
    * @return the vertical pixel offset for antialiasing the current mesh object being rendered.
    */

   public int getAntiAliasingOffsetY() { return antiAliasingOffsetY; }

   /**
    * Returns the x-axis index to the aperature angle for applying the depth of field effect to the current mesh object being rendered.
    *
    * @return the x-axis index to the aperature angle for applying the depth of field effect to the current mesh object being rendered.
    */

   public int getFocusOffsetIndexX() { return focusOffsetIndexX; }

   /**
    * Returns the y-axis index to the aperature angle for applying the depth of field effect to the current mesh object being rendered.
    *
    * @return the y-axis index to the aperature angle for applying the depth of field effect to the current mesh object being rendered.
    */

   public int getFocusOffsetIndexY() { return focusOffsetIndexY; }

   /**
    * Returns the index to the time value for applying motion blur to the current mesh object being rendered.
    *
    * @return the index to the time value for applying motion blur to the current mesh object being rendered.
    */

   public int getMotionOffsetIndex() { return motionOffsetIndex; }

   /**
    * Returns the value that the dimensions of the pixel is divided into when antialiasing the current mesh object being rendered.
    *
    * @return the value that the dimensions of the pixel is divided into when antialiasing the current mesh object being rendered.
    */

   public int getSuperSamplingDimension() { return superSamplingDimension; }

   /**
    * Returns the value of the number of jittered view segments for applying the depth of field effect to the current mesh object being rendered.
    *
    * @return the value of the number of jittered view segments for applying the depth of field effect to the current mesh object being rendered.
    */

   public int getFocusBlurDimension() { return focusBlurDimension; }

   /**
    * Returns the value of the number of time segments for applying motion blur to the current mesh object being rendered.
    *
    * @return the value of the number of time segments for applying motion blur to the current mesh object being rendered.
    */

   public int getMotionBlurDimension() { return motionBlurDimension; }

   /**
    * Gets the red wavelength diffuse lighting coefficient of the current material associated with this render notifier.
    *
    * @return the red wavelength diffuse lighting coefficient of the current material associated with this render notifier.
    */

   public float getCurrentDiffuseCoefficientR() { return currentDiffuseCoefficientR; }

   /**
    * Gets the green wavelength diffuse lighting coefficient of the current material associated with this render notifier.
    *
    * @return the green wavelength diffuse lighting coefficient of the current material associated with this render notifier.
    */

   public float getCurrentDiffuseCoefficientG() { return currentDiffuseCoefficientG; }

   /**
    * Gets the blue wavelength diffuse lighting coefficient of the current material associated with this render notifier.
    *
    * @return the blue wavelength diffuse lighting coefficient of the current material associated with this render notifier.
    */

   public float getCurrentDiffuseCoefficientB() { return currentDiffuseCoefficientB; }

   /**
    * Gets the red wavelength specular lighting coefficient of the current material associated with this render notifier.
    *
    * @return the red wavelength specular lighting coefficient of the current material associated with this render notifier.
    */

   public float getCurrentSpecularCoefficientR() { return currentSpecularCoefficientR; }

   /**
    * Gets the green wavelength specular lighting coefficient of the current material associated with this render notifier.
    *
    * @return the green wavelength specular lighting coefficient of the current material associated with this render notifier.
    */

   public float getCurrentSpecularCoefficientG() { return currentSpecularCoefficientG; }

   /**
    * Gets the blue wavelength specular lighting coefficient of the current material associated with this render notifier.
    *
    * @return the blue wavelength specular lighting coefficient of the current material associated with this render notifier.
    */

   public float getCurrentSpecularCoefficientB() { return currentSpecularCoefficientB; }

   /**
    * Gets the red wavelength emissive lighting coefficient of the current material associated with this render notifier.
    *
    * @return the red wavelength of emissive lighting coefficient of the current material associated with this render notifier.
    */

   public float getCurrentEmissiveCoefficientR() { return currentEmissiveCoefficientR; }

   /**
    * Gets the green wavelength emissive lighting coefficient of the current material associated with this render notifier.
    *
    * @return the green wavelength emissive lighting coefficient of the current material associated with this render notifier.
    */

   public float getCurrentEmissiveCoefficientG() { return currentEmissiveCoefficientG; }

   /**
    * Gets the blue wavelength emissive lighting coefficient of the current material associated with this render notifier.
    *
    * @return the blue wavelength emissive lighting coefficient of the current material associated with this render notifier.
    */

   public float getCurrentEmissiveCoefficientB() { return currentEmissiveCoefficientB; }

   /**
    * Returns the red wavelength fresnel reflectivity coefficient at an angle of incidence of zero of the current material associated with this render notifier.
    *
    * @return the red wavelength fresnel reflectivity coefficient at an angle of incidence of zero of the current material associated with this render notifier.
    */

   public float getCurrentFresnelCoefficientR() { return currentFresnelCoefficientR; }

   /**
    * Returns the green wavelength fresnel reflectivity coefficient at an angle of incidence of zero of the current material associated with this render notifier.
    *
    * @return the green wavelength fresnel reflectivity coefficient at an angle of incidence of zero of the current material associated with this render notifier.
    */

   public float getCurrentFresnelCoefficientG() { return currentFresnelCoefficientG; }

   /**
    * Returns the blue wavelength fresnel reflectivity coefficient at an angle of incidence of zero of the current material associated with this render notifier.
    *
    * @return the blue wavelength fresnel reflectivity coefficient at an angle of incidence of zero of the current material associated with this render notifier.
    */

   public float getCurrentFresnelCoefficientB() { return currentFresnelCoefficientB; }

   /**
    * Gets the red wavelength diffuse reflectivity of the current material associated with this render notifier.
    *
    * @return the red wavelength diffuse reflectivity of the current material associated with this render notifier.
    */

   public float getCurrentDiffuseReflectivityR() { return currentDiffuseReflectivityR; }

   /**
    * Gets the green wavelength diffuse reflectivity of the current material associated with this render notifier.
    *
    * @return the green wavelength diffuse reflectivity of the current material associated with this render notifier.
    */

   public float getCurrentDiffuseReflectivityG() { return currentDiffuseReflectivityG; }

   /**
    * Gets the blue wavelength diffuse reflectivity of the current material associated with this render notifier.
    *
    * @return the blue wavelength diffuse reflectivity of the current material associated with this render notifier.
    */

   public float getCurrentDiffuseReflectivityB() { return currentDiffuseReflectivityB; }

   /**
    * Gets the specular exponent of the current material associated with this render notifier.
    *
    * @return the specular exponent of the current material associated with this render notifier.
    */

   public float getCurrentShininess() { return currentShininess; }

   /**
    * Returns the microfacet roughness of the current material associated with this render notifier.
    *
    * @return the microfacet roughness of the current material associated with this render notifier.
    */

   protected float getCurrentMicrofacetRoughness() { return currentMicrofacetRoughness; }

   /**
    * Sets the motion attributes associated with this render notifier to the motion attributes object argument.
    *
    * @param currentMotionAttributes the motion attributes object that the current motion attributes associated with this render notifier is set to.
    */

   public void setCurrentMotionAttributes(MotionAttributes currentMotionAttributes) { this.currentMotionAttributes = currentMotionAttributes; }

   /**
    * Sets the rendering attributes associated with this render notifier to the rendering attributes object argument.
    *
    * @param currentRenderingAttributes the rendering attributes object that the current rendering attributes associated with this render notifier is set to.
    */

   public void setCurrentRenderingAttributes(RenderingAttributes currentRenderingAttributes) { this.currentRenderingAttributes = currentRenderingAttributes; }

   /**
    * Sets the current geometry associated with this render notifier to the geometry argument.
    *
    * @param currentGeometry the geometry that the current geometry associated with this render notifier is set to.
    */

   public void setCurrentGeometry(Geometry currentGeometry) { this.currentGeometry = currentGeometry; }

   public void setCurrentToolkit(ToolkitI currentToolkit) { this.currentToolkit = currentToolkit; }

   /**
    * Sets the current matrix associated with this render notifier to the matrix argument.
    *
    * @param currentMatrix the world matrix that the current matrix associated with this render notifier is set to.
    */

   public void setCurrentMatrix(Matrix4D currentMatrix) { this.currentMatrix = currentMatrix; }

   /**
    * Sets the current texture map array associated with this render notifier to the texture map array argument.
    *
    * @param textureMapArray the texture map array that the current texture map array associated with this render notifier is set to.
    */

   public void setCurrentTextureMapArray(TextureMap[] currentTextureMapArray) { this.currentTextureMapArray = currentTextureMapArray; }

   /**
    * Sets the current texture attributes array associated with this render notifier to be the texture attributes array argument.
    *
    * @param textureAttributesArray the texture attributes array that the current texture attributes array associated with this render notifier is set to.
    */

   public void setCurrentTextureAttributesArray(TextureAttributes[] currentTextureAttributesArray) { this.currentTextureAttributesArray = currentTextureAttributesArray; }

   /**
    * Sets the current vertex coordinate array in screen coordinates associated with this render notifier to the floating point array argument.
    *
    * @param currentPrimitiveCoordinates the floating point array that the current vertex coordinate array associated with this render notifier is set to.
    */

   public void setCurrentPrimitiveCoordinates(float[] currentPrimitiveCoordinates) { this.currentPrimitiveCoordinates = currentPrimitiveCoordinates; }

   /**
    * Sets the current primitive index associated with this render notifier to the integer argument.
    *
    * @param currentPrimitiveIndex the value that the current primitive index associated with this render notifier is set to.
    */

   public void setCurrentPrimitiveIndex(int currentPrimitiveIndex) { this.currentPrimitiveIndex = currentPrimitiveIndex; }

   /**
    * Sets the horizontal pixel offset for antialiasing the current mesh object being rendered to the integer argument.
    *
    * @param antiAliasingOffsetX the value that the horizontal pixel offset for antialiasing the current mesh object being rendered is set to.
    */

   public void setAntiAliasingOffsetX(int antiAliasingOffsetX) { this.antiAliasingOffsetX = antiAliasingOffsetX; }

   /**
    * Sets the vertical pixel offset for antialiasing the current mesh object being rendered to the integer argument.
    *
    * @param antiAliasingOffsetY the value that the vertical pixel offset for antialiasing the current mesh object being rendered is set to.
    */

   public void setAntiAliasingOffsetY(int antiAliasingOffsetY) { this.antiAliasingOffsetY = antiAliasingOffsetY; }

   /**
    * Sets the x-axis index to the aperature angle for applying the depth of field effect to the current mesh object being rendered to the integer argument.
    *
    * @param aperatureOffsetIndexX the value that the x-axis index to the aperature angle for applying the depth of field effect to the current mesh object being rendered is set to.
    */

   public void setFocusOffsetIndexX(int focusOffsetIndexX) { this.focusOffsetIndexX = focusOffsetIndexX; }

   /**
    * Sets the y-axis index to the aperature angle for applying the depth of field effect to the current mesh object being rendered to the integer argument.
    *
    * @param aperatureOffsetIndexY the value that the y-axis index to the aperature angle for applying the depth of field effect to the current mesh object being rendered is set to.
    */

   public void setFocusOffsetIndexY(int focusOffsetIndexY) { this.focusOffsetIndexY = focusOffsetIndexY; }

   /**
    * Sets the index to the time value for applying motion blur to the current mesh object being rendered to the integer argument.
    *
    * @param motionOffsetIndex the value that the index to the time value for applying motion blur to the current mesh object being rendered is set to.
    */

   public void setMotionOffsetIndex(int motionOffsetIndex) { this.motionOffsetIndex = motionOffsetIndex; }

   /**
    * Sets the value that the dimensions of the pixel is divided into when antialiasing the current mesh object being rendered to the integer argument.
    *
    * @param superSamplingDimension the value that the dimensions of the pixel is divided into when antialiasing the current mesh object being rendered is set to.
    */

   public void setSuperSamplingDimension(int superSamplingDimension) { this.superSamplingDimension = superSamplingDimension; }

   /**
    * Sets the value of the number of jittered view segments for applying the depth of field effect to the current mesh object being rendered to the integer argument.
    *
    * @param focusBlurDimension the value that the number of jittered view segments for applying motion blur to the current mesh object being rendered is set to.
    */

   public void setFocusBlurDimension(int focusBlurDimension) { this.focusBlurDimension = focusBlurDimension; }

   /**
    * Sets the value of the number of time segments for applying motion blur to the current mesh object being rendered to the integer argument.
    *
    * @param motionBlurSamplingDimension the value that the number of time segments for applying motion blur to the current mesh object being rendered is set to.
    */

   public void setMotionBlurDimension(int motionBlurDimension) { this.motionBlurDimension = motionBlurDimension; }

   /**
    * Sets the red wavelength diffuse lighting coefficient of the current material associated with this render notifier to the floating point argument.
    *
    * @param currentDiffuseCoefficientR the value that the red wavelength diffuse lighting coefficient of the current material associated with this render notifier is set to.
    */

   public void setCurrentDiffuseCoefficientR(float currentDiffuseCoefficientR) { this.currentDiffuseCoefficientR = currentDiffuseCoefficientR; }

   /**
    * Sets the green wavelength diffuse lighting coefficient of the current material associated with this render notifier to the floating point argument.
    *
    * @param currentDiffuseCoefficientG the value that the green wavelength diffuse lighting coefficient of the current material associated with this render notifier is set to.
    */

   public void setCurrentDiffuseCoefficientG(float currentDiffuseCoefficientG) { this.currentDiffuseCoefficientG = currentDiffuseCoefficientG; }

   /**
    * Sets the blue wavelength diffuse lighting coefficient of the current material associated with this render notifier to the floating point argument.
    *
    * @param currentDiffuseCoefficientB the value that the blue wavelength diffuse lighting coefficient of the current material associated with this render notifier is set to.
     */

   public void setCurrentDiffuseCoefficientB(float currentDiffuseCoefficientB) { this.currentDiffuseCoefficientB = currentDiffuseCoefficientB; }

   /**
    * Sets the red wavelength specular lighting coefficient of the current material associated with this render notifier to the floating point argument.
    *
    * @param currentSpecularCoefficientR the value that the red wavelength specular lighting coefficient of the current material associated with this render notifier is set to.
    */

   public void setCurrentSpecularCoefficientR(float currentSpecularCoefficientR) { this.currentSpecularCoefficientR = currentSpecularCoefficientR; }

   /**
    * Sets the green wavelength specular lighting coefficient of the current material associated with this render notifier to the floating point argument.
    *
    * @param currentSpecularCoefficientG the value that the green wavelength specular lighting coefficient of the current material associated with this render notifier is set to.
    */

   public void setCurrentSpecularCoefficientG(float currentSpecularCoefficientG) { this.currentSpecularCoefficientG = currentSpecularCoefficientG; }

   /**
    * Sets the blue wavelength specular lighting coefficient of the current material associated with this render notifier to the floating point argument.
    *
    * @param currentSpecularCoefficientB the value that the blue wavelength specular lighting coefficient of the current material associated with this render notifier is set to.
    */

   public void setCurrentSpecularCoefficientB(float currentSpecularCoefficientB) { this.currentSpecularCoefficientB = currentSpecularCoefficientB; }

   /**
    * Sets the red wavelength emissive lighting coefficient of the current material associated with this render notifier to the floating point argument.
    *
    * @param currentEmissiveCoefficientR the value that the red wavelength emissive lighting coefficient of the current material associated with this render notifier is set to.
    */

   public void setCurrentEmissiveCoefficientR(float currentEmissiveCoefficientR) { this.currentEmissiveCoefficientR = currentEmissiveCoefficientR; }

   /**
    * Sets the green wavelength emissive lighting coefficient of the current material associated with this render notifier to the floating point argument.
    *
    * @param currentEmissiveCoefficientG the value that the green wavelength emissive lighting coefficient of the current material associated with this render notifier is set to.
    */

   public void setCurrentEmissiveCoefficientG(float currentEmissiveCoefficientG) { this.currentEmissiveCoefficientG = currentEmissiveCoefficientG; }

   /**
    * Sets the blue wavelength emissive lighting coefficient of the current material associated with this render notifier to the floating point argument.
    *
    * @param currentEmissiveCoefficientB the value that the blue wavelength emissive lighting coefficient of the current material associated with this render notifier is set to.
    */

   public void setCurrentEmissiveCoefficientB(float currentEmissiveCoefficientB) { this.currentEmissiveCoefficientB = currentEmissiveCoefficientB; }

   /**
    * Sets the red wavelength fresnel reflectivity coefficient at an angle of incidence of zero of the current material currently associated with this render notifier to the floating point argument.
    *
    * @param currentFresnelCoefficientR the value that the red wavelength fresnel reflectivity coefficient at an angle of incidence of zero of the current material associated with this render notifier is set to.
    */

   public void setCurrentFresnelCoefficientR(float currentFresnelCoefficientR) { this.currentFresnelCoefficientR = currentFresnelCoefficientR; }

   /**
    * Sets the green wavelength fresnel reflectivity coefficient at an angle of incidence of zero of the current material currently associated with this render notifier to the floating point argument.
    *
    * @param currentFresnelCoefficientG the value that the green wavelength fresnel reflectivity coefficient at an angle of incidence of zero of the current material associated with this render notifier is set to.
    */

   public void setCurrentFresnelCoefficientG(float currentFresnelCoefficientG) { this.currentFresnelCoefficientG = currentFresnelCoefficientG; }

   /**
    * Sets the blue wavelength fresnel reflectivity coefficient at an angle of incidence of zero of the current material currently associated with this render notifier to the floating point argument.
    *
    * @param currentFresnelCoefficientB the value that the blue wavelength fresnel reflectivity coefficient at an angle of incidence of zero of the current material associated with this render notifier is set to.
    */

   public void setCurrentFresnelCoefficientB(float currentFresnelCoefficientB) { this.currentFresnelCoefficientB = currentFresnelCoefficientB; }

   /**
    * Sets the red wavelength diffuse reflectivity of the current material associated with this render notifier to the floating point argument.
    *
    * @param currentDiffuseReflectivityR the value that the red wavelength diffuse reflectivity of the current material associated with this render notifier is set to.
    */

   public void setCurrentDiffuseReflectivityR(float currentDiffuseReflectivityR) { this.currentDiffuseReflectivityR = currentDiffuseReflectivityR; }

   /**
    * Sets the green wavelength diffuse reflectivity of the current material associated with this render notifier to the floating point argument.
    *
    * @param currentDiffuseReflectivityG the value that the green wavelength diffuse reflectivity of the current material associated with this render notifier is set to.
    */

   public void setCurrentDiffuseReflectivityG(float currentDiffuseReflectivityG) { this.currentDiffuseReflectivityG = currentDiffuseReflectivityG; }

   /**
    * Sets the red wavelength diffuse reflectivity of the current material associated with this render notifier to the floating point argument.
    *
    * @param currentDiffuseReflectivityR the value that the red wavelength diffuse reflectivity of the current material associated with this render notifier is set to.
    */

   public void setCurrentDiffuseReflectivityB(float currentDiffuseReflectivityB) { this.currentDiffuseReflectivityB = currentDiffuseReflectivityB; }

   /**
    * Sets the specular exponent of the current material associated with this render notifier to the floating point argument.
    *
    * @param currentShininess the value that the specular exponent of the current material associated with this render notifier is set to.
    */

   public void setCurrentShininess(float currentShininess) { this.currentShininess = currentShininess; }

   /**
    * Sets the microfacet roughness of the current material associated with this render notifier to the floating point argument.
    *
    * @param currentMicrofacetRoughness the value that the microfacet roughness of the current material associated with this render notifier is set to.
    */

   public void setCurrentMicrofacetRoughness(float currentMicrofacetRoughness) { this.currentMicrofacetRoughness = currentMicrofacetRoughness; }

}
