 package shamu.media.render;

public class  BeamShader implements RenderingConstantsI {

   /**
    * Field that will store the output red component of a pixel processed by this image shader.
    */

   protected float outputR = 0.0f;

   /**
    * Field that will store the output green component of a pixel processed by this image shader.
    */

   protected float outputG = 0.0f;

   /**
    * Field that will store the output blue component of a pixel processed by this image shader.
    */

   protected float outputB = 0.0f;

   /**
    * Field that will store the output alpha component of a pixel processed by this image shader.
    */

   protected float outputA = 1.0f;

   /**
    * Field containing the x-coordinate of the camera point used for lighting calculations in object coordinate space.
    */

   protected float eyeX;

   /**
    * Field containing the y-coordinate of the camera point used for lighting calculations in object coordinate space.
    */

   protected float eyeY;

   /**
    * Field containing the z-coordinate of the camera point used for lighting calculations in object coordinate space.
    */

   protected float eyeZ;

   /**
    * Red wavelength diffuse lighting coefficient in the range 0.0 - 1.0 of the material of the current primitive whose pixels are processed by this image shader.
    */

   protected float diffuseCoefficientR;

   /**
    * Green wavelength diffuse lighting coefficient in the range 0.0 - 1.0 of the material of the current primitive whose pixels are processed by this image shader.
    */

   protected float diffuseCoefficientG;

   /**
    * Blue wavelength diffuse lighting coefficient in the range 0.0 - 1.0 of the material of the current primitive whose pixels are processed by this image shader.
    */

   protected float diffuseCoefficientB;

   /**
    * Red wavelength specular lighting coefficient in the range 0.0 - 1.0 of the material of the current primitive whose pixels are processed by this image shader.
    */

   protected float specularCoefficientR;

   /**
    * Green wavelength specular lighting coefficientin the range 0.0 - 1.0 of the material of the current primitive whose pixels are processed by this image shader.
    */

   protected float specularCoefficientG;

   /**
    * Blue wavelength specular lighting coefficient in the range 0.0 - 1.0 of the material of the current primitive whose pixels are processed by this image shader.
    */

   protected float specularCoefficientB;

   /**
    * Red wavelength emissive lighting coefficient in the range 0.0 - 1.0 of the material of the current primitive whose pixels are processed by this image shader.
    */

   protected float emissiveCoefficientR;

   /**
    * Green wavelength emissive lighting coefficient in the range 0.0 - 1.0 of the material of the current primitive whose pixels are processed by this image shader.
    */

   protected float emissiveCoefficientG;

   /**
    * Blue wavelength emissive lighting coefficient in the range 0.0 - 1.0 of the material of the current primitive whose pixels are processed by this image shader.
    */

   protected float emissiveCoefficientB;

   /**
    * Red wavelength fresnel reflectivity coefficient at an angle of incidence of zero under illumination in the rgba model of the material of the current primitive
    * whose pixels are processed by this image shader.
    */

   protected float fresnelCoefficientR=.5f;

   /**
    * Green wavelength fresnel reflectivity coefficient at an angle of incidence of zero under illumination in the rgba model of the material of the current primitive
    * whose pixels are processed by this image shader.
    */

   protected float fresnelCoefficientG=.5f;

   /**
    * Blue wavelength fresnel reflectivity coefficient at an angle of incidence of zero under illumination in the rgba model of the material of the current primitive
    * whose pixels are processed by this image shader.
    */

   protected float fresnelCoefficientB=.5f;

   /**
    * Red wavelength diffuse reflectivity of the material of the current primitive whose pixels are processed by this image shader.
    */

   protected float diffuseReflectivityR;

   /**
    * Green wavelength diffuse reflectivity of the material of the current primitive whose pixels are processed by this image shader.
    */

   protected float diffuseReflectivityG;

   /**
    * Blue wavelength diffuse reflectivity of the material of the current primitive whose pixels are processed by this image shader.
    */

   protected float diffuseReflectivityB;

   /**
    * Specular exponent in the range 0.0 - 500.0 of the material of the current primitive whose pixels are processed by this image shader.
    */

   protected float shininess;

   /**
    * Root mean square of microfacets slope in the Cook-Torrance model of the material of the current primitive whose pixels are processed by this image shader.
    */

   protected float microfacetRoughness=1.5f;

   /**
    * Lights used for lighting calculations with coordinate information set in object coordinate space.
    */

   protected Light[] lightArray = new Light[64];

   /**
    * Texture map array corresponding to the current primitive whose pixels are processed by this image shader.
    */

   protected TextureMap[] textureMapArray = {null, null, null, null, null, null, null, null};

   /**
    * Texture attributes array parallel to the texture map array applied to the texel mapped by the current pixel being processed by this image shader.
    */

   protected TextureAttributes[] textureAttributesArray = {null, null, null, null, null, null, null, null};

   /**
    * View that may be referenced when pixel processing is performed by this image shader.
    */

   protected BeamReceptor receptor;

   /**
    * Constructs a image shader associated with the camera object argument.
    *
    * @param camera the camera object associated with this image shader.
    */

   protected BeamShader(BeamReceptor receptor) { this.receptor = receptor; }

   protected void computeViewCoordinates(float surfaceElementX, float surfaceElementY, float surfaceElementZ, float[] viewCoordinates) {

      viewCoordinates[0] = eyeX - surfaceElementX;
      viewCoordinates[1] = eyeY - surfaceElementY;
      viewCoordinates[2] = eyeZ - surfaceElementZ;

      float factor = (float)Math.sqrt(viewCoordinates[0] * viewCoordinates[0] +
                                      viewCoordinates[1] * viewCoordinates[1] +
                                      viewCoordinates[2] * viewCoordinates[2]);

      viewCoordinates[0] /= factor;
      viewCoordinates[1] /= factor;
      viewCoordinates[2] /= factor;


   }

   protected void set(float[] coords, float[] normal) {
	  // float x, float y, float z, float nx, float ny, float nz) {

	  float[] incidence = new float[3];
	  float[] reflection = new float[3];
	  float[] view = new float[3];

     computeViewCoordinates(coords[0], coords[1], coords[2], view);


	  float diffuseR =  0.0f;
	  float diffuseG =  0.0f;
	  float diffuseB =  0.0f;

	  float specularR =  0.0f;
	  float specularG =  0.0f;
	  float specularB =  0.0f;

	  for(int i = 0; i < 16 && lightArray[i] != null; i++) {

         if(lightArray[i].getEnabled()) {

		    lightArray[i].setIncidenceCoordinates(coords[0], coords[1], coords[2], incidence);
		    lightArray[i].setReflectionCoordinates(coords[0], coords[1], coords[2], normal[0], normal[1], normal[2], reflection);
//            lightArray[i].setViewCoordinates(coords[0], coords[1], coords[2], eyeX, eyeY, eyeZ, view);


            float distanceAttenuation = lightArray[i].getDistanceAttenuation(incidence[0], incidence[1], incidence[2]);
            float angularAttenuation = lightArray[i].getAngularAttenuation(incidence[0], incidence[1], incidence[2]);

		    float diffuse  = distanceAttenuation * angularAttenuation * Math.max(0.0f, incidence[0] * normal[0] + incidence[1] * normal[1] + incidence[2] * normal[2]);
	        float specular = distanceAttenuation * angularAttenuation * Math.max(0.0f, (float)Math.pow(reflection[0] * view[0] + reflection[1] * view[1] + reflection[2] * view[2], shininess));

            diffuseR += lightArray[i].incidentEnergyR * diffuse;
            diffuseG += lightArray[i].incidentEnergyG * diffuse;
            diffuseB += lightArray[i].incidentEnergyB * diffuse;

            specularR += lightArray[i].incidentEnergyR * specular;
            specularG += lightArray[i].incidentEnergyG * specular;
            specularB += lightArray[i].incidentEnergyB * specular;

		//    float diffuse  = distanceAttenuation * angularAttenuation * (incidence[0] * normal[0] + incidence[1] * normal[1] + incidence[2] * normal[2]);
      //      float specular = distanceAttenuation * angularAttenuation *((float)Math.pow(reflection[0] * view[0] + reflection[1] * view[1] + reflection[2] * view[2], shininess));

      //      diffuseR += lightArray[i].incidentEnergyR * Math.max(0.0f, diffuse);
      //      diffuseG += lightArray[i].incidentEnergyG * Math.max(0.0f,  diffuse);
      //      diffuseB += lightArray[i].incidentEnergyB * Math.max(0.0f, diffuse);

      //      specularR += lightArray[i].incidentEnergyR * Math.max(0.0f, specular);
      //      specularG += lightArray[i].incidentEnergyG *  Math.max(0.0f, specular);
      //      specularB += lightArray[i].incidentEnergyB * Math.max(0.0f, specular);

	     }

      }

	  outputR= Math.min(1.0f, diffuseR * diffuseCoefficientR + specularR * specularCoefficientR + emissiveCoefficientR);
	  outputG =Math.min(1.0f, diffuseG * diffuseCoefficientG + specularG * specularCoefficientG + emissiveCoefficientG);
	  outputB = Math.min(1.0f, diffuseB * diffuseCoefficientB + specularB * specularCoefficientB + emissiveCoefficientB);

   }

   public TextureMap getTextureMap(int flag) {

	  if(flag == TextureMap.BASE_TEXTURE_FLAG)
	     return textureMapArray[0];

	  else if(flag == TextureMap.BUMP_MAP_FLAG)
	     return textureMapArray[1];

	  else if(flag == TextureMap.HEATSOURCE_MAP_FLAG)
	     return textureMapArray[2];

	  else
	     return null;

   }

   public void  activateFrontFacingTextures() {

        TextureMap map =  getTextureMap(TextureMap.HEATSOURCE_MAP_FLAG);

         if(map != null)
            map.enable();

   }

   public void  deactivateFrontFacingTextures() {

        TextureMap map =  getTextureMap(TextureMap.HEATSOURCE_MAP_FLAG);

         if(map != null)
            map.disable();

   }

   /*
    * Sets the red component of the output color of this image shader to the floating point argument.
    *
    * @param outputR the value that the red component of the output color of this image shader is set to.
    */

   protected void setOutputR(float outputR) { this.outputR = outputR; }

   /**
    * Sets the green component of the output color of this image shader to the floating point argument.
    *
    * @param outputG the value that the green component of the output color of this image shader is set to.
    */

   protected void setOutputG(float outputG) { this.outputG = outputG; }

   /**
    * Sets the blue component of the output color of this image shader to the floating point argument.
    *
    * @param outputB the value that the blue component of the output color of this image shader is set to.
    */

   protected void setOutputB(float outputB) { this.outputB = outputB; }

   /**
    * Sets the alpha component of the output color of this image shader to the floating point argument.
    *
    * @param outputA the value that the alpha component of the output color of this image shader is set to.
    */

   protected void setOutputA(float outputA) { this.outputA = outputA; }

   /**
    * Sets the x-coordinate of the camera point used for lighting calculations in object coordinate space to the floating point argument.
    *
    * @param eyeX the value that the x-coordinate of the camera point of the camera in object coordinate space used for lighting calculations is set to.
    */

   protected void setEyeX(float eyeX) { this.eyeX = eyeX; }

   /**
    * Sets the y-coordinate of the camera point used for lighting calculations in object coordinate space to the floating point argument.
    *
    * @param eyeY the value that the y-coordinate of the camera point in object coordinate space used for lighting calculations is set to.
    */

   protected void setEyeY(float eyeY) { this.eyeY = eyeY; }

   /**
    * Sets the z-coordinate of the camera point used for lighting calculations in object coordinate space to the floating point argument.
    *
    * @param eyeZ the value that the z-coordinate of the camera point in object coordinate space used for lighting calculations is set to.
    */

   protected void setEyeZ(float eyeZ) { this.eyeZ = eyeZ; }

   /**
    * Sets the red wavelength diffuse lighting coefficient of the material currently associated with this image shader to the floating point argument.
    *
    * @param diffuseCoefficientR the value that the red wavelength diffuse lighting coefficient of the material currently associated with this image shader is set to.
    */

   protected void setDiffuseCoefficientR(float diffuseCoefficientR) { this.diffuseCoefficientR = diffuseCoefficientR; }

   /**
    * Sets the green wavelength diffuse lighting coefficient of the material currently associated with this image shader to the floating point argument.
    *
    * @param diffuseCoefficientG the value that the green wavelength diffuse lighting coefficient of the material currently associated with this image shader is set to.
    */

   protected void setDiffuseCoefficientG(float diffuseCoefficientG) { this.diffuseCoefficientG = diffuseCoefficientG; }

   /**
    * Sets the blue wavelength diffuse lighting coefficient of the material currently associated with this image shader to the floating point argument.
    *
    * @param diffuseCoefficientB the value that the blue wavelength diffuse lighting coefficient of the material currently associated with this image shader is set to.
    */

   protected void setDiffuseCoefficientB(float diffuseCoefficientB) { this.diffuseCoefficientB = diffuseCoefficientB; }

   /**
    * Sets the red  wavelength specular lighting coefficient of the material currently associated with this image shader to the floating point argument.
    *
    * @param specularCoefficientR the value that the red  wavelength specular lighting coefficient of the material currently associated with this image shader is set to.
    */

   protected void setSpecularCoefficientR(float specularCoefficientR) { this.specularCoefficientR = specularCoefficientR; }

   /**
    * Sets the green wavelength specular lighting coefficient of the material currently associated with this image shader to the floating point argument.
    *
    * @param specularCoefficientG the value that the green  wavelength specular lighting coefficient of the material currently associated with this image shader is set to.
    */

   protected void setSpecularCoefficientG(float specularCoefficientG) { this.specularCoefficientG = specularCoefficientG; }

   /**
    * Sets the blue wavelength specular lighting coefficient of the material currently associated with this image shader to the floating point argument.
    *
    * @param specularCoefficientB the value that the blue  wavelength specular lighting coefficient of the material currently associated with this image shader is set to.
    */

   protected void setSpecularCoefficientB(float specularCoefficientB) { this.specularCoefficientB = specularCoefficientB; }

   /**
    * Sets the red wavelength emissive lighting coefficient of the material currently associated with this image shader to the floating point argument.
    *
    * @param emissiveCoefficientR the value that the red wavelength emissive lighting coefficient of the material currently associated with this image shader is set to.
    */

   protected void setEmissiveCoefficientR(float emissiveCoefficientR) { this.emissiveCoefficientR = emissiveCoefficientR; }

   /**
    * Sets the green wavelength emissive lighting coefficient of the material currently associated with this image shader to the floating point argument.
    *
    * @param emissiveCoefficientG the value that the green wavelength emissive lighting coefficient of the material currently associated with this image shader is set to.
    */

   protected void setEmissiveCoefficientG(float emissiveCoefficientG) { this.emissiveCoefficientG = emissiveCoefficientG; }

   /**
    * Sets the blue wavelength emissive lighting coefficient of the material currently associated with this image shader to the floating point argument.
    *
    * @param emissiveCoefficientB the value that the blue wavelength emissive lighting coefficient of the material currently associated with this image shader is set to.
    */

   protected void setEmissiveCoefficientB(float emissiveCoefficientB) { this.emissiveCoefficientB = emissiveCoefficientB; }

   /**
    * Sets the red component of the fresnel reflectivity coefficient at an angle of incidence of zero of the material currently associated with this image shader to the floating point argument.
    *
    * @param fresnelCoefficientR the value that the red component of the fresnel reflectivity coefficient at an angle of incidence of zero of the material currently associated with this image shader is set to.
    */

   protected void setFresnelCoefficientR(float fresnelCoefficientR) { this.fresnelCoefficientR = fresnelCoefficientR; }

   /**
    * Sets the green component of the fresnel reflectivity coefficient at an angle of incidence of zero of the material currently associated with this image shader to the floating point argument.
    *
    * @param fresnelCoefficientG the value that the green component of the fresnel reflectivity coefficient at an angle of incidence of zero of the material currently associated with this image shader is set to.
    */

   protected void setFresnelCoefficientG(float fresnelCoefficientG) { this.fresnelCoefficientG = fresnelCoefficientG; }

   /**
    * Sets the blue component of the fresnel reflectivity coefficient at an angle of incidence of zero of the material currently associated with this image shader to the floating point argument.
    *
    * @param fresnelCoefficientB the value that the blue component of the fresnel reflectivity coefficient at an angle of incidence of zero of the material currently associated with this image shader is set to.
    */

   protected void setFresnelCoefficientB(float fresnelCoefficientB) { this.fresnelCoefficientB = fresnelCoefficientB; }

   /**
    * Sets the red wavelength diffuse reflectivity of the material currently associated with this image shader to the floating point argument.
    *
    * @param diffuseReflectivityR the value that the red wavelength diffuse reflectivity of the material currently associated with this image shader is set to.
    */

   protected void setDiffuseReflectivityR(float diffuseReflectivityR) { this.diffuseReflectivityR = diffuseReflectivityR; }

   /**
    * Sets the green wavelength diffuse reflectivity of the material currently associated with this image shader to the floating point argument.
    *
    * @param diffuseReflectivityG the value that the green wavelength diffuse reflectivity of the material currently associated with this image shader is set to.
    */

   protected void setDiffuseReflectivityG(float diffuseReflectivityG) { this.diffuseReflectivityG = diffuseReflectivityG; }

   /**
    * Sets the blue wavelength diffuse reflectivity of the material currently associated with this image shader to the floating point argument.
    *
    * @param diffuseReflectivityB the value that the blue wavelength diffuse reflectivity of the material currently associated with this image shader is set to.
    */

   protected void setDiffuseReflectivityB(float diffuseReflectivityB) { this.diffuseReflectivityB = diffuseReflectivityB; }

   /**
    * Sets the specular exponent of the material currently associated with this image shader to the floating point argument.
    *
    * @param shininess the value that the specular exponent of the material currently associated with this image shader is set to.
    */

   protected void setShininess(float shininess) { this.shininess = shininess; }

   /**
    * Sets the microfacet roughness of the material currently associated with this image shader to the floating point argument.
    *
    * @param microfacetRoughness the value that the microfacet roughness of the material currently associated with this image shader is set to.
    */

   protected void setMicrofacetRoughness(float microfacetRoughness) { this.microfacetRoughness = microfacetRoughness; }

   /**
    * Sets the light associated with this image shader indexed by the integer argument to the light argument.
    *
    * @param light the light set to be associated with this image shader at the index determined by the second argument.
    * @param index the index of the light array of this image shader that the first argument is inserted into.
    */

   protected void setLight(Light light, int index) { this.lightArray[index] = light; }

   /**
    * Sets the texture map associated with this image shader indexed by the integer argument to the texture map argument.
    *
    * @param textureMap the texture map set to be associated with this image shader at the index determined by the second argument.
    * @param index the index of the texture map array associated with this image shader that the first argument is inserted into.
    */

   protected void setTextureMap(TextureMap textureMap, int index) { this.textureMapArray[index] = textureMap; }

   /**
    * Sets the texture matrix associated with this image shader indexed by the integer argument to the matrix argument.
    *
    * @param texture matrix the texture matrix set to be associated with this image shader at the index determined by the second argument.
    * @param index the index of the texture matrix array associated with this image shader that the first argument is inserted into.
    */

   protected void setTextureAttributes(TextureAttributes textureAttributes, int index) { this.textureAttributesArray[index] = textureAttributes; }

   /**
    * Sets the camera associated with this image shader to the camera argument.
    *
    * @param camera the camera object that the camera associated with this image shader is set to.
    */

   protected void setBeamReceptor(BeamReceptor receptor) { this.receptor = receptor; }

   /**
    * Returns the red component of the output color of the last visible pixel processed by this image shader
    *
    * @return the red component of the output color of the last visible pixel processed by this image shader.
    */

   public float getOutputR() { return outputR; }

   /**
    * Returns the green component of the output color of the last visible pixel processed by this image shader.
    *
    * @return the green component of the output color of the last visible pixel processed by this image shader.
    */

   public float getOutputG() { return outputG; }

   /**
    * Returns the blue component of the output color of the last visible pixel processed by this image shader.
    *
    * @return the blue component of the output color of the last visible pixel processed by this image shader.
    */

   public float getOutputB() { return outputB; }

   /**
    * Returns the alpha component of the output color of the last visible pixel processed by this image shader.
    *
    * @return the alpha component of the output color of the last visible pixel processed by this image shader.
    */

   public float getOutputA() { return outputA; }

}