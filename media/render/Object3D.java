package shamu.media.render;

//import signalprocessing.imaging.ImageAgent;

/** Scene graph node subclass that represents a terminal node in a scene graph that is associated with a visible object in the virtual world
 *  containing procedural modeling and material information.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

abstract public class Object3D extends SceneGraphNode {

   /**
    * The motion attributes associated with this 3D object.
    */

   protected MotionAttributes motionAttributes = new MotionAttributes();

   /**
    * The rendering attributes associated with this 3D object.
    */

   protected RenderingAttributes renderingAttributes = new RenderingAttributes();

   /**
    * The geometry associated with this 3D object.
    */

   //protected Geometry geometry;

   /**
    * The array of texture maps associated with this 3D object.
    */

   protected TextureMap[] textureMapArray = {null, null, null, null, null, null, null, null};

   /**
    * The array of texture attributes associated with this 3D object.
    */

   protected TextureAttributes[] textureAttributesArray = {new TextureAttributes(), new TextureAttributes(), new TextureAttributes(), new TextureAttributes(), new TextureAttributes(), new TextureAttributes(), new TextureAttributes(), new TextureAttributes()};

   /**
    * Red wavelength diffuse lighting coefficient in the range 0.0 - 1.0, of the material associated with this 3D object.
    */

   protected float diffuseCoefficientR = 1.0f;

   /**
    * Green wavelength diffuse lighting coefficient in the range 0.0 - 1.0, of the material associated with this 3D object.
    */

   protected float diffuseCoefficientG = 1.0f;

   /**
    * Blue wavelength diffuse lighting coefficient in the range 0.0 - 1.0, of the material associated with this 3D object.
    */

   protected float diffuseCoefficientB = 1.0f;

   /**
    * Red wavelength specular lighting coefficientl in the range 0.0 - 1.0, of the material associated with this 3D object.
    */

   protected float specularCoefficientR = 0.0f;

   /**
    * Green wavelength specular lighting coefficient in the range 0.0 - 1.0, of the material associated with this 3D object.
    */

   protected float specularCoefficientG = 0.0f;

   /**
    * Blue wavelength specular lighting coefficient in the rgba model in the range 0.0 - 1.0, of the material associated with this 3D object.
    */

   protected float specularCoefficientB = 0.0f;

   /**
    * Red wavelength emissive lighting coefficient in the range 0.0 - 1.0, of the material associated with this 3D object.
    */

   protected float emissiveCoefficientR = 0.0f;

   /**
    * Blue wavelength emissive lighting coefficient in the range 0.0 - 1.0, of the material associated with this 3D object.
    */

   protected float emissiveCoefficientG = 0.0f;

   /**
    * Green wavelength emissive lighting coefficient in the range 0.0 - 1.0, of the material associated with this 3D object.
    */

   protected float emissiveCoefficientB = 0.0f;

   /**
    * Red wavelength fresnel reflectivity coefficient at an angle of incidence of zero under illumination in the rgba model of the material associated with this 3D object.
    */

   protected float fresnelCoefficientR = 0.2f;

   /**
    * Green wavelength of the fresnel reflectivity coefficient at an angle of incidence of zero under illumination in the rgba model of the material associated with this 3D object.
    */

   protected float fresnelCoefficientG = 0.2f;

   /**
    * Blue wavelength fresnel reflectivity coefficient at an angle of incidence of zero under illumination in the rgba model of the material associated with this 3D object.
    */

   protected float fresnelCoefficientB = 0.4f;

   /**
    * Red wavelength diffuse reflectivity of the material associated with this 3D object.
    */

   protected float diffuseReflectivityR = 0.5f;

   /**
    * Green wavelength diffuse reflectivity of the material associated with this 3D object.
    */

   protected float diffuseReflectivityG = 0.5f;

   /**
    * Blue wavelength diffuse reflectivity of the material associated with this 3D object.
    */

   protected float diffuseReflectivityB = 0.5f;

   protected float transparencyR = 1.0f;

   protected float transparencyG = 1.0f;

   protected float transparencyB = 1.0f;

   /**
    * Red wavelength shadow modulator of the material associated with this 3D object.
    */

   protected float shadowingModulatorR = 0.15f;

   /**
    * Green wavelength shadow modulator of the material associated with this 3D object.
    */

   protected float shadowingModulatorG = 0.15f;

   /**
    *  Blue wavelength shadow modulator of the material associated with this 3D object.
    */

   protected float shadowingModulatorB = 0.15f;

   /**
    * Measure of the specular exponent in the Phong model of the material associated with this 3D object.
    */

   protected float shininess = 5.0f;

   /**
    * Root mean square of microfacets slope in the Cook-Torrance model of the material associated with this 3D object.
    */

   protected float microfacetRoughness = 0.8f;

   /**
     * Index of refraction for light rays entering the material associated with this 3D object.
     */

   protected float indexOfRefraction = 1.0f;

   /**
     * Index of refraction for light rays exiting the material associated with this 3D object.
     */

   protected float outerIndexOfRefraction =  1.0f;

   /**
    * Constructs a default 3D object without any specified geometry
    */

   protected Object3D() {}

   protected Object3D(int localShadingAttribute) {

     this();

     renderingAttributes.setLocalShadingAttribute(localShadingAttribute);

  }
/*
   protected Object3D(Geometry geometry) {

	   this.geometry = geometry;
	 //  this.initializeCurveMap();

	   }
*/
   /**
    * Generates geometry data and updates the current state of the notifier argument. Notifies the notifier argument's registered view objects of changes needed to be made to their frame buffers.
    *
	  * @param notifier the render notifier whose state information is updated and who notifies view objects of changes needed to made to their frame buffers.
    * @param currentMatrix the matrix that is set as the current world transformation in the state information of the notifier argument.
    */

   abstract protected void traverse(RenderNotifier notifier, Matrix4D currentMatrix);

   /**
    * Return the texture map in the texture map array associated with this 3D object and determined by the texture stage flag argument.
    *
    * @param flag the texture stage flag determining the texture map return value in the texture map array associated with this 3D object.
    * @return the texture map in the texture map array associated with this 3D object and determined by the texture stage flag argument.
    */

   public TextureMap getTextureMap(int flag) {

	  if(flag == TextureMap.BASE_TEXTURE_FLAG)
	     return textureMapArray[0];

	  else if(flag == TextureMap.BUMP_MAP_FLAG)
	     return textureMapArray[1];

	  else if(flag == TextureMap.HEATSOURCE_MAP_FLAG)
	     return textureMapArray[2];

	  else if(flag == TextureMap.CURVE_MAP_FLAG)
	     return textureMapArray[3];

	  else
	     return null;

   }

   /**
    * Return the texture attributes in the texture attributes array associated with this 3D object and determined by the texture stage flag argument.
    *
    * @param flag the texture stage flag determining the texture attributes return value in the texture attributes array associated with this 3D object.
    * @return the texture map in the texture attributes array associated with this 3D object and determined by the texture stage flag argument.
    */

   public TextureAttributes getTextureAttributes(int flag) {

	  if(flag == TextureMap.BASE_TEXTURE_FLAG)
	     return textureAttributesArray[0];

	  else if(flag == TextureMap.BUMP_MAP_FLAG)
	     return textureAttributesArray[1];

	  else if(flag == TextureMap.HEATSOURCE_MAP_FLAG)
	     return textureAttributesArray[2];

	  else if(flag == TextureMap.CURVE_MAP_FLAG)
	     return textureAttributesArray[3];

	  else
	     return null;

   }

   /**
    * Sets the texture map in the texture map array associated with this 3D object and determined by the texture stage flag to the texture map argument.
    *
    * @param textureMap the texture map that the texture map associated with this 3D object and determined by the integer argument is set to.
    * @param flag the flag identifying the texture stage associated with the texture map argument.
    */

   public void setTextureMap(TextureMap textureMap, int flag) {

	  if(flag == TextureMap.BASE_TEXTURE_FLAG)
	     textureMapArray[0] = textureMap;

	  else if(flag == TextureMap.BUMP_MAP_FLAG)
	     textureMapArray[1] = textureMap;

	  else if(flag == TextureMap.HEATSOURCE_MAP_FLAG)
	     textureMapArray[2] = textureMap;

	  else if(flag == TextureMap.CURVE_MAP_FLAG)
	     textureMapArray[3] = textureMap;

   }

   /**
    * Sets the texture matrix in the texture matrix array associated with this 3D object and determined by the texture stage flag to the matrix argument.
    *
    * @param textureMatrix the texture matrix that the texture matrix associated with this 3D object and determined by the integer argument is set to.
    * @param flag the flag identifying the texture stage associated with the texture matrix argument.
    */

   public void setTextureAttributes(TextureAttributes textureAttributes, int flag) {

	  if(flag == TextureMap.BASE_TEXTURE_FLAG)
	     textureAttributesArray[0] = textureAttributes;

	  else if(flag == TextureMap.BUMP_MAP_FLAG)
	     textureAttributesArray[1] = textureAttributes;

	  else if(flag == TextureMap.HEATSOURCE_MAP_FLAG)
	     textureAttributesArray[2] = textureAttributes;

	  else if(flag == TextureMap.CURVE_MAP_FLAG)
	     textureAttributesArray[3] = textureAttributes;

   }

   /**
    * Returns the motion attributes associated with this 3D object.
    *
    * @return the motion attributes associated with this 3D object.
    */

   public MotionAttributes getMotionAttributes() { return this.motionAttributes; }

   /**
    * Returns the rendering attributes associated with this 3D object.
    *
    * @return the rendering attributes associated with this 3D object.
    */

   public RenderingAttributes getRenderingAttributes() { return this.renderingAttributes; }

   /**
    * Returns the geometry associated with this 3D object.
    *
    * @return the geometry associated with this 3D object.
    */

   //public Geometry getGeometry() { return geometry; }

   /**
    * Returns the red wavelength diffuse lighting coefficient of the material associated with this 3D object.
    *
    * @return the red wavelength diffuse lighting coefficient of the material associated with this 3D object.
    */

   public float getDiffuseCoefficientR() { return diffuseCoefficientR; }

   /**
    * Returns the green wavelength diffuse lighting coefficient of the material associated with this 3D object.
    *
    * @return the green wavelength diffuse lighting coefficient of the material associated with this 3D object.
    */

   public float getDiffuseCoefficientG() { return diffuseCoefficientG; }

   /**
    * Returns the blue wavelength diffuse lighting coefficient of the material associated with this 3D object.
    *
    * @return the blue wavelength diffuse lighting coefficient of the material associated with this 3D object.
    */

   public float getDiffuseCoefficientB() { return diffuseCoefficientB; }

   /**
    * Returns the red wavelength specular lighting coefficient of the material associated with this 3D object.
    *
    * @return the red wavelength material associated with this 3D object.
    */

   public float getSpecularCoefficientR() { return specularCoefficientR; }

   /**
    * Returns the green wavelength specular lighting coefficient of the material associated with this 3D object.
    *
    * @return the green wavelength specular lighting coefficient of the material associated with this 3D object.
    */

   public float getSpecularCoefficientG() { return specularCoefficientG; }

   /**
    * Returns the blue wavelength specular lighting coefficient of the material associated with this 3D object.
    *
    * @return the blue wavelength specular lighting coefficient of the material associated with this 3D object.
    */

   public float getSpecularCoefficientB() { return specularCoefficientB; }

   /**
    * Returns the red wavelength emissive lighting coefficient of the material associated with this 3D object.
    *
    * @return the red wavelength emissive lighting coefficient of the material associated with this 3D object.
    */

   public float getEmissiveCoefficientR() { return emissiveCoefficientR; }

   /**
    * Returns the green wavelength emissive lighting coefficient of the material associated with this 3D object.
    *
    * @return the green wavelength emissive lighting coefficient of the material associated with this 3D object.
    */

   public float getEmissiveCoefficientG() { return emissiveCoefficientG; }

   /**
    * Returns the blue wavelength emissive lighting coefficient of the material associated with this 3D object.
    *
    * @return the blue wavelength emissive lighting coefficient of the material associated with this 3D object.
    */

   public float getEmissiveCoefficientB() { return emissiveCoefficientB; }

   /**
    * Returns the red wavelength fresnel reflectivity coefficient at an angle of incidence of zero of the material associated with this 3D object.
    *
    * @return the red wavelength fresnel reflectivity coefficient at an angle of incidence of zero of the material associated with this 3D object.
    */

   public float getFresnelCoefficientR() { return fresnelCoefficientR; }

   /**
    * Returns the green wavelength fresnel reflectivity coefficient at an angle of incidence of zero of the material associated with this 3D object.
    *
    * @return the green wavelength fresnel reflectivity coefficient at an angle of incidence of zero of the material associated with this 3D object.
    */

   public float getFresnelCoefficientG() { return fresnelCoefficientG; }

   /**
    * Returns the blue wavelength fresnel reflectivity coefficient at an angle of incidence of zero of the material associated with this 3D object.
    *
    * @return the blue wavelength fresnel reflectivity coefficient at an angle of incidence of zero of the material associated with this 3D object.
    */

   public float getFresnelCoefficientB() { return fresnelCoefficientB; }

   /**
    * Returns the red wavelength diffuse reflectivity of the material associated with this 3D object.
    *
    * @return the red wavelength diffuse reflectivity of the material associated with this 3D object.
    */

   public float getDiffuseReflectivityR() { return diffuseReflectivityR; }

   /**
    * Returns the green wavelength diffuse reflectivity of the material associated with this 3D object.

    * @return the green wavelength diffuse reflectivity of the material associated with this 3D object.
    */

   public float getDiffuseReflectivityG() { return diffuseReflectivityG; }

   /**
    * Returns the blue wavelength diffuse reflectivity of the material associated with this 3D object.
    *
    * @return the blue wavelength diffuse reflectivity of the material associated with this 3D object.
    */

   public float getDiffuseReflectivityB() { return diffuseReflectivityB; }

   public float getTransparencyR() { return transparencyR; }

   public float getTransparencyG() { return transparencyG; }

   public float getTransparencyB() { return transparencyB; }

   /**
    * Returns the red wavelength shadow modulator of the material associated with this 3D object.
    *
    * @return the red wavelength shadow modulator of the material associated with this 3D object.
    */

   public float getShadowingModulatorR() { return shadowingModulatorR; }

   /**
    * Returns the green wavelength shadow modulator of the material associated with this 3D object.
    *
    * @return the green wavelength shadow modulator of the material associated with this 3D object.
    */

   public float getShadowingModulatorG() { return shadowingModulatorG; }

   /**
    * Returns the blue wavelength shadow modulator of the material associated with this 3D object.
    *
    * @return the blue wavelength shadow modulator of the material associated with this 3D object.
    */

   public float getShadowingModulatorB() { return shadowingModulatorB; }

   /**
    * Returns the specular exponent of the material associated with this 3D object.
    *
    * @return the specular exponent of the material associated with this 3D object.
    */

   public float getShininess() { return shininess; }

   /**
    * Returns the microfacet roughness of the material associated with this 3D object.
    *
    * @return the microfacet roughness of the material associated with this 3D object.
    */

   public float getMicrofacetRoughness() { return microfacetRoughness; }

   /**
    * Sets the index of refraction for light rays entering the material associated with this 3D object to the floating point value.
    *
    * @return the index of refraction  for light rays entering the material associated with this 3D object.
    */

   public float getIndexOfRefraction() { return indexOfRefraction; }

   /**
    * Sets the index of refraction for light rays exiting the material associated with this 3D object to the floating point value.
    *
    * @return the index of refraction  for light rays exiting the material associated with this 3D object.
    */

   public float getOuterIndexOfRefraction() { return outerIndexOfRefraction; }

   /**
    * Sets the motion attributes associated with this 3D object to the motion attributes argument.
    *
    * @param motionAttributes the motion attributes that the motion attributes associated with this 3D object is set to.
    */

   public void setMotionAttributes(MotionAttributes motionAttributes) { this.motionAttributes = motionAttributes; }

   /**
    * Sets the rendering attributes associated with this 3D object to the rendering attributes argument.
    *
    * @param renderingAttributes the rendering attributes that the rendering attributes associated with this 3D object is set to.
    */

   public void setRenderingAttributes(RenderingAttributes renderingAttributes) { this.renderingAttributes = renderingAttributes; }

   /**
    * Sets the geometry associated with this 3D object to the geometry argument.
    *
    * @param geometry the toolkit that the geometry associated with this 3D object is set to.
    */

   //public void setGeometry(Geometry geometry) { this.geometry = geometry; }

   /**
    * Sets the red wavelength diffuse lighting coefficient of the material associated with this 3D object to the floating point argument.
    *
    * @param diffuseCoefficientR the value that the red wavelength diffuse lighting coefficient of the material associated with this 3D object is set to.
    */

   public void setDiffuseCoefficientR(float diffuseCoefficientR) { this.diffuseCoefficientR = diffuseCoefficientR; }

   /**
    * Sets the green wavelength diffuse lighting coefficient of the material associated with this 3D object to the floating point argument.
    *
    * @param diffuseCoefficientG the value that the green wavelength diffuse lighting coefficient of the material associated with this 3D object is set to.
    */

   public void setDiffuseCoefficientG(float diffuseCoefficientG) { this.diffuseCoefficientG = diffuseCoefficientG; }

   /**
    * Sets the blue wavelength diffuse lighting coefficient of the material associated with this 3D object to the floating point argument.
    *
    * @param diffuseCoefficientB the value that the blue wavelength diffuse lighting coefficient of the material associated with this 3D object is set to.
    */

   public void setDiffuseCoefficientB(float diffuseCoefficientB) { this.diffuseCoefficientB = diffuseCoefficientB; }

   /**
    * Sets the red wavelength specular lighting coefficient of the material associated with this 3D object to the floating point argument.
    *
    * @param specularCoefficientR the value that the red wavelength specular lighting coefficient of the material associated with this 3D object is set to.
    */

   public void setSpecularCoefficientR(float specularCoefficientR) { this.specularCoefficientR = specularCoefficientR; }

   /**
    * Sets the green wavelength specular lighting coefficient of the material associated with this 3D object to the floating point argument.
    *
    * @param specularCoefficientG the value that the green wavelength specular lighting coefficient of the material associated with this 3D object is set to.
    */

   public void setSpecularCoefficientG(float specularCoefficientG) { this.specularCoefficientG = specularCoefficientG; }

   /**
    * Sets the blue wavelength specular lighting coefficient of the material associated with this 3D object to the floating point argument.
    *
    * @param specularCoefficientB the value that the blue wavelength diffuse lighting coefficient of the material associated with this 3D object is set to.
    */

   public void setSpecularCoefficientB(float specularCoefficientB) { this.specularCoefficientB = specularCoefficientB; }

   /**
    * Sets the red wavelength emissive lighting coefficient of the material associated with this 3D object to the floating point argument.
    *
    * @param emissiveCoefficientR the value that the red wavelength emissive lighting coefficient of the material associated with this 3D object is set to.
    */

   public void setEmissiveCoefficientR(float emissiveCoefficientR) { this.emissiveCoefficientR = emissiveCoefficientR; }

   /**
    * Sets the green wavelength emissive lighting coefficient of the material associated with this 3D object to the floating point argument.
    *
    * @param emissiveCoefficientG the value that the green emissive lighting coefficient of the wavelength material associated with this 3D object is set to.
    */

   public void setEmissiveCoefficientG(float emissiveCoefficientG) { this.emissiveCoefficientG = emissiveCoefficientG; }

   /**
    * Sets the blue wavelength emissive lighting coefficient of the material associated with this 3D object to the floating point argument.
    *
    * @param emissiveCoefficientB the value that the blue wavelength emissive lighting coefficient of the material associated with this 3D object is set to.
    */

   public void setEmissiveCoefficientB(float emissiveCoefficientB) { this.emissiveCoefficientB = emissiveCoefficientB; }

   /**
    * Sets the red wavelength fresnel reflectivity coefficient at an angle of incidence of zero of the material currently associated with this 3D object to the floating point argument.
    *
    * @param fresnelCoefficientR the value that the red wavelength fresnel reflectivity coefficient at an angle of incidence of zerot of the material currently associated with this 3D object is set to.
    */

   public void setFresnelCoefficientR(float fresnelCoefficientR) { this.fresnelCoefficientR = fresnelCoefficientR; }

   /**
    * Sets the green wavelength fresnel reflectivity coefficient at an angle of incidence of zero of the material associated with this 3D object to the floating point argument.
    *
    * @param fresnelCoefficientG the value that the green wavelength fresnel reflectivity coefficient at an angle of incidence of zero of the material associated with this 3D object is set to.
    */

   public void setFresnelCoefficientG(float fresnelCoefficientG) { this.fresnelCoefficientG = fresnelCoefficientG; }

   /**
    * Sets the blue wavelength fresnel reflectivity coefficient at an angle of incidence of zero of the material associated with this 3D object to the floating point argument.
    *
    * @param fresnelCoefficientB the value that the blue wavelength fresnel reflectivity coefficient at an angle of incidence of zero of the material associated with this 3D object is set to.
    */

   public void setFresnelCoefficientB(float fresnelCoefficientB) { this.fresnelCoefficientB = fresnelCoefficientB; }

   /**
    * Sets the red wavelength reflectivity of the material associated with this 3D object to the floating point argument.
    *
    * @param diffuseReflectivityR the value that the red wavelength diffuse reflectivity of the material associated with this 3D object is set to.
    */

   public void setDiffuseReflectivityR(float diffuseReflectivityR) { this.diffuseReflectivityR = diffuseReflectivityR; }

   /**
    * Sets the green wavelength reflectivity of the material associated with this 3D object to the floating point argument.
    *
    * @param diffuseReflectivityG the value that the green wavelength diffuse reflectivity of the material associated with this 3D object is set to.
    */

   public void setDiffuseReflectivityG(float diffuseReflectivityG) { this.diffuseReflectivityG = diffuseReflectivityG; }

   /**
    * Sets the blue wavelength reflectivity of the material associated with this 3D object to the floating point argument.
    *
    * @param diffuseReflectivityB the value that the blue wavelength diffuse reflectivity of the material associated with this 3D object is set to.
    */

   public void setDiffuseReflectivityB(float diffuseReflectivityB) { this.diffuseReflectivityB = diffuseReflectivityB; }

   public void setTransparencyR(float transparencyR) { this.transparencyR = transparencyR; }

   public void setTransparencyG(float transparencyG) { this.transparencyG = transparencyG; }

   public void setTransparencyB(float transparencyB) { this.transparencyB = transparencyB; }

   /**
    * Sets the red wavelength shadow modulator  of the material associated with this 3D object to the floating point argument.
    *
    * @param shadowModulatorR he value that the red wavelength shadow modulator of the material associated with this 3D object is set to.
    */

   public void setShadowingModulatorR(float shadowingModulatorR) { this.shadowingModulatorR = shadowingModulatorR; }

   /**
    * Sets the green wavelength shadow modulator  of the material associated with this 3D object to the floating point argument.
    *
    * @param shadowModulatorG he value that the green  wavelength shadow modulator of the material associated with this 3D object is set to.
    */

   public void setShadowingModulatorG(float shadowingModulatorG) { this.shadowingModulatorG = shadowingModulatorG; }

   /**
    * Sets the blue wavelength shadow modulator  of the material associated with this 3D object to the floating point argument.
    *
    * @param shadowModulatorB he value that the blue wavelength shadow modulator of the material associated with this 3D object is set to.
    */

   public void setShadowingModulatorB(float shadowingModulatorB) { this.shadowingModulatorB = shadowingModulatorB; }

   /**
    * Sets the specular exponent of the material associated with this 3D object to the floating point argument.
    *
    * @param shininess the value that the specular exponent of the material associated with this 3D object is set to.
    */

   public void setShininess(float shininess) { this.shininess = shininess; }

   /**
    * Sets the microfacet roughness of the material associated with this 3D object to the floating point value.
    *
    * @param microfacetRoughness the value that the microfacet roughness of the material associated with this 3D object is set to.
    */

   public void setMicrofacetRoughness(float microfacetRoughness) { this.microfacetRoughness = microfacetRoughness; }

   /**
    * Sets the index of refraction for light rays entering the material associated with this 3D object to the floating point value.
    *
    * @param indexOfRefraction the value that the index of refraction  for light rays entering the material associated with this 3D object is set to.
    */

   public void setIndexOfRefraction(float indexOfRefraction) { this.indexOfRefraction = indexOfRefraction; }

   /**
    * Sets the index of refraction for light rays exiting the material associated with this 3D object to the floating point value.
    *
    * @param outerIndexOfRefraction the value that the index of refraction  for light rays exiting the material associated with this 3D object is set to.
    */

   public void setOuterIndexOfRefraction(float outerIndexOfRefraction) { this.outerIndexOfRefraction = outerIndexOfRefraction; }

}
