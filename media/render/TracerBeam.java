package shamu.media.render;

public class TracerBeam {

	public TracerBeam() {}

    public  void trace(BeamShader beamShader, BeamDetector detector, RenderNotifier notifier, float startX, float startY, float startZ, float endX, float endY, float endZ, float[] colorCoords, int depth, float arg1, float arg2, float arg3)  throws NullIntersectionException {

        if(depth < 3) {

            float[] worldCoords = new float[3];
            float[] normalCoords = new float[3];
            float[] transmissionDirection = new float[] {0, 0, 0.0f };//[3];
            float[] reflectionDirection = new float[3];
            float[] objectCoords = new float[3];
            float[] tempColorCoords = new float[3];
            float[] textureCoords = new float[2];

            java.util.ListIterator<BeamInterceptor> iterator = notifier.getVisuals()
                                                                	   .listIterator(0);
            
            BeamInterceptor interceptor = detector.hit(iterator, startX, startY, startZ, endX, endY, endZ, worldCoords);

            Matrix4D matrix = interceptor.getMatrix();

            objectCoords[0] = matrix.getElement(0, 0) * worldCoords[0]  + matrix.getElement(0, 1) * worldCoords[1]  + matrix.getElement(0, 2) * worldCoords[2]  + matrix.getElement(0, 3);
	        objectCoords[1] = matrix.getElement(1, 0) * worldCoords[0]  + matrix.getElement(1, 1) * worldCoords[1]  + matrix.getElement(1, 2) * worldCoords[2]  + matrix.getElement(1, 3);
           	objectCoords[2] = matrix.getElement(2, 0) * worldCoords[0]  + matrix.getElement(2, 1) * worldCoords[1]  + matrix.getElement(2, 2) * worldCoords[2]  + matrix.getElement(2, 3);

           	float startX_ = matrix.getElement(0, 0) * startX  + matrix.getElement(0, 1) * startY  + matrix.getElement(0, 2) * startZ  + matrix.getElement(0, 3);
	        float startY_ = matrix.getElement(1, 0) * startX  + matrix.getElement(1, 1) * startY  + matrix.getElement(1, 2) * startZ  + matrix.getElement(1, 3);
           	float startZ_ = matrix.getElement(2, 0) * startX  + matrix.getElement(2, 1) * startY  + matrix.getElement(2, 2) * startZ + matrix.getElement(2, 3);

           	float endX_ = matrix.getElement(0, 0) * endX  + matrix.getElement(0, 1) * endY  + matrix.getElement(0, 2) * endZ  + matrix.getElement(0, 3);
	        float endY_ = matrix.getElement(1, 0) * endX  + matrix.getElement(1, 1) * endY  + matrix.getElement(1, 2) * endZ  + matrix.getElement(1, 3);
           	float endZ_ = matrix.getElement(2, 0) * endX  + matrix.getElement(2, 1) * endY  + matrix.getElement(2, 2) * endZ + matrix.getElement(2, 3);

		    initializeImageShaderMaterial(beamShader, notifier, interceptor);
            initializeImageShaderEye(beamShader, notifier, matrix);
            initializeImageShaderLightArray(beamShader, notifier, matrix);
       		initializeImageShaderTextureMapArray(beamShader, notifier, interceptor);

			TracerGeometry geom = (TracerGeometry )interceptor.getGeometry();

            geom.normal(normalCoords, objectCoords);
            geom.texturize(textureCoords, objectCoords);

            float reflectivityR = interceptor.getDiffuseReflectivityR();
            float reflectivityG = interceptor.getDiffuseReflectivityG();
            float reflectivityB = interceptor.getDiffuseReflectivityB();

            float transparencyR = interceptor.getTransparencyR();
            float transparencyG = interceptor.getTransparencyG();
            float transparencyB = interceptor.getTransparencyB();

            beamShader.set(objectCoords, normalCoords);

	        colorCoords[0] = Math.max(0.0f, arg1*(beamShader.outputR)); //(float)Math.min(1.0f, colorCoords[0] + arg1*(beamShader.outputR) * indirectR);
	        colorCoords[1] = Math.max(0.0f, arg2*(beamShader.outputG)); //(float)Math.min(1.0f, colorCoords[1] + arg2*(beamShader.outputG) * indirectG);
	        colorCoords[2] = Math.max(0.0f, arg3*(beamShader.outputB)); //(float)Math.min(1.0f, colorCoords[2] + arg3*(beamShader.outputB)  * indirectB);

            if(interceptor.getRenderingAttributes().getCurveHighlightsAttribute() == RenderingAttributes.INTEGRAL_CURVES_ENABLED)
               this.highlightCurves(interceptor, objectCoords, textureCoords, colorCoords, new float[] { arg1, arg2,  arg3 } );

            if(interceptor.getRenderingAttributes().getHeatSourceAttribute() == RenderingAttributes.HEAT_SOURCE_ENABLED )
               this.highlightHeatSource(interceptor, objectCoords, textureCoords, colorCoords, new float[] { arg1, arg2, arg3 });

            if(interceptor.getRenderingAttributes().getShadowingAttribute() == RenderingAttributes.SHADOWING_ENABLED )
               this.highlightShadows(notifier, worldCoords, colorCoords);

           // colorCoords[0] *= (transparencyR);
           // colorCoords[1] *= (transparencyG);
           // colorCoords[2] *= (transparencyB);

            float index2 = interceptor.getIndexOfRefraction();
            float index1 = interceptor.getOuterIndexOfRefraction();

            //float directionX = startX - worldCoords[0];//endX;
            //float directionY = startY - worldCoords[1];//endY;
            //float directionZ = startZ - worldCoords[2];//endZ;

            float ix = startX_ - endX_;
            float iy = startY_ - endY_;
            float iz = startZ_ - endZ_;

            float r = (float)Math.sqrt(ix * ix + iy * iy  + iz * iz);

            ix /= r;
            iy /= r;
            iz /= r;

            float[] reflectedColors = new float[3];
            float[] transmittedColors = new float[3];

            Matrix4D imatrix = new Matrix4D();
            matrix.invert(imatrix);

            //float normalX = imatrix.getElement(0, 0) * normalCoords[0]   + imatrix.getElement(0, 1) * normalCoords[1]  + imatrix.getElement(0, 2) * normalCoords[2];
	        //float normalY = imatrix.getElement(1, 0) * normalCoords[0]   + imatrix.getElement(1, 1) * normalCoords[1]  + imatrix.getElement(1, 2) * normalCoords[2];
            //float normalZ = imatrix.getElement(2, 0) * normalCoords[0]   + imatrix.getElement(2, 1) * normalCoords[1]  + imatrix.getElement(2, 2) * normalCoords[2];

           	RefractionStrategy refractionStrategy = interceptor.getRefractionStrategy();

            try { 
            	TracerBeam transmittedBeam = new TracerBeam();
            	
            	//calculateTransmissionDirection(directionX, directionY, directionZ, normalX, normalY, normalZ, index1, index2, transmissionDirection, refractionStrategy);             	
            	calculateTransmissionDirection(ix, iy, iz, normalCoords[0], normalCoords[1], normalCoords[2], index1, index2, transmissionDirection, refractionStrategy); 

            	float dx = imatrix.getElement(0, 0) * transmissionDirection[0]   + imatrix.getElement(0, 1) * transmissionDirection[1]  + imatrix.getElement(0, 2) * transmissionDirection[2];
	        	float dy = imatrix.getElement(1, 0) * transmissionDirection[0]   + imatrix.getElement(1, 1) * transmissionDirection[1]  + imatrix.getElement(1, 2) * transmissionDirection[2];
            	float dz = imatrix.getElement(2, 0) * transmissionDirection[0]   + imatrix.getElement(2, 1) * transmissionDirection[1]  + imatrix.getElement(2, 2) * transmissionDirection[2];

  				transmittedBeam.trace(beamShader, detector, notifier, worldCoords[0] + .001f * dx, worldCoords[1] + .001f * dy, worldCoords[2]  + .001f * dz,   worldCoords[0] + dx , worldCoords[1] + dy, worldCoords[2] + dz, transmittedColors, depth + 1,  arg1 * (1 - transparencyR), arg2 * (1 - transparencyG), arg3 * (1 - transparencyB)); 
  				//transmittedBeam.trace(beamShader, detector, notifier, worldCoords[0] + .001f * transmissionDirection[0], worldCoords[1] + .001f * transmissionDirection[1], worldCoords[2]  + .001f * transmissionDirection[2],   worldCoords[0] + transmissionDirection[0] , worldCoords[1] + transmissionDirection[1], worldCoords[2] + transmissionDirection[2], transmittedColors, depth + 1,  arg1 * (1 - transparencyR), arg2 * (1 - transparencyG), arg3 * (1 - transparencyB)); 


            } catch(Exception e) {}

            	//System.out.println("hello");

                //transmissionDirection[0] = -directionX;
                //transmissionDirection[1] = -directionY;
                //transmissionDirection[2] = -directionZ;

            //}

            try { 

            	TracerBeam reflectedBeam = new TracerBeam();
            	
            	//calculateReflectionDirection(directionX, directionY, directionZ, normalX, normalY, normalZ, reflectionDirection); 
            	calculateReflectionDirection(ix, iy, iz, normalCoords[0], normalCoords[1], normalCoords[2], reflectionDirection); 

            	float dx = imatrix.getElement(0, 0) * reflectionDirection[0]   + imatrix.getElement(0, 1) * reflectionDirection[1]  + imatrix.getElement(0, 2) * reflectionDirection[2];
	        	float dy = imatrix.getElement(1, 0) * reflectionDirection[0]   + imatrix.getElement(1, 1) * reflectionDirection[1]  + imatrix.getElement(1, 2) * reflectionDirection[2];
            	float dz = imatrix.getElement(2, 0) * reflectionDirection[0]   + imatrix.getElement(2, 1) * reflectionDirection[1]  + imatrix.getElement(2, 2) * reflectionDirection[2];


				//reflectedBeam.trace(beamShader, detector, notifier, worldCoords[0] + .001f * reflectionDirection[0], worldCoords[1] + .001f * reflectionDirection[1], worldCoords[2]  + .001f * reflectionDirection[2],   worldCoords[0] + reflectionDirection[0] , worldCoords[1] +  reflectionDirection[1], worldCoords[2]  + reflectionDirection[2], reflectedColors, depth + 1,  arg1 * reflectivityR , arg2 * reflectivityG, arg3 * reflectivityB);
				reflectedBeam.trace(beamShader, detector, notifier, worldCoords[0] + .001f * dx, worldCoords[1] + .001f * dy, worldCoords[2]  + .001f * dz,   worldCoords[0] + dx, worldCoords[1] + dy, worldCoords[2]  + dz, reflectedColors, depth + 1,  arg1 * reflectivityR , arg2 * reflectivityG, arg3 * reflectivityB);


			} catch(Exception e) {}

          //  TracerBeam reflectedBeam = new TracerBeam();
           // TracerBeam transmittedBeam = new TracerBeam();

            //try{  reflectedBeam.trace(beamShader, detector, notifier, worldCoords[0] + .001f * reflectionDirection[0], worldCoords[1] + .001f * reflectionDirection[1], worldCoords[2]  + .001f * reflectionDirection[2],   worldCoords[0] + reflectionDirection[0] , worldCoords[1] +  reflectionDirection[1], worldCoords[2]  + reflectionDirection[2], reflectedColors, depth + 1,  arg1 * reflectivityR , arg2 * reflectivityG, arg3 * reflectivityB); }  catch(Exception e) {}
            //try{  transmittedBeam.trace(beamShader, detector, notifier, worldCoords[0] + .001f * transmissionDirection[0], worldCoords[1] + .001f * transmissionDirection[1], worldCoords[2]  + .001f * transmissionDirection[2],   worldCoords[0] + transmissionDirection[0] , worldCoords[1] + transmissionDirection[1], worldCoords[2] + transmissionDirection[2], transmittedColors, depth + 1,  arg1 * (1 - transparencyR), arg2 * (1 - transparencyG), arg3 * (1 - transparencyB)); }  catch(Exception e) {}

	       colorCoords[0]= (float)Math.min(1.0f, colorCoords[0] + reflectedColors[0] + transmittedColors[0]);
	       colorCoords[1]= (float)Math.min(1.0f, colorCoords[1] + reflectedColors[1] + transmittedColors[1]);
	       colorCoords[2]= (float)Math.min(1.0f, colorCoords[2] + reflectedColors[2] + transmittedColors[2]);

	    }

	}

    protected void calculateReflectionDirection(float incidenceX, float incidenceY, float incidenceZ, float normalX, float normalY, float normalZ, float[] reflectionDirection) {

        float dot = incidenceX * normalX + incidenceY * normalY + incidenceZ * normalZ;

        reflectionDirection[0]  = -incidenceX + 2.0f * normalX * dot;
        reflectionDirection[1]  = -incidenceY + 2.0f * normalY * dot;
        reflectionDirection[2]  = -incidenceZ + 2.0f * normalZ * dot;

        float mag = (float)Math.sqrt(Math.pow(reflectionDirection[0], 2) + Math.pow(reflectionDirection[1], 2) + Math.pow(reflectionDirection[2], 2));

        reflectionDirection[0] /= mag;
        reflectionDirection[1] /= mag;
        reflectionDirection[2] /= mag;

   	}

    protected void calculateTransmissionDirection(float incidenceX, float incidenceY, float incidenceZ, float normalX, float normalY, float normalZ, float indexOfRefraction1, float indexOfRefraction2, float[] transmissionDirection, RefractionStrategy refractionStrategy) throws InternalReflectionException {

        float[] refractionIndices = new float[] {indexOfRefraction1, indexOfRefraction2};
        float[] normalIndices = new float[] {normalX, normalY, normalZ};

        float cos_i = incidenceX * normalIndices[0] + incidenceY * normalIndices[1] + incidenceZ * normalIndices[2];

        cos_i = refractionStrategy.adjustToPhysicalMedium(cos_i, refractionIndices, normalIndices);

        //cos_i = incidenceX * normalIndices[0] + incidenceY * normalIndices[1] + incidenceZ * normalIndices[2];

        float factor = refractionIndices[0] / refractionIndices[1];

        //float sin_squared_t = 1 - (float)Math.pow(cos_t, 2);
        //float sin_squared_i = (float)Math.pow(factor, 2) * sin_squared_t; 

        float sin_squared_i = 1 - (float)Math.pow(cos_i, 2);
        float sin_squared_t = (float)Math.pow(factor, 2) * sin_squared_i;

        //float sin_squared_i = (float)Math.pow(factor, 2) - (float)Math.pow(cos_t, 2);


		if(sin_squared_t > 1.0f) {

			//sin_squared_t = 1.0f;
			throw new InternalReflectionException();
           
        }        


                //System.out.println(1 - sin_squared_i);
        float cos_t = (float)Math.sqrt(1 - sin_squared_t);


        transmissionDirection[0] = -factor * incidenceX + (-cos_t + factor * cos_i) * normalIndices[0];
        transmissionDirection[1] = -factor * incidenceY + (-cos_t + factor * cos_i) * normalIndices[1];
        transmissionDirection[2] = -factor * incidenceZ + (-cos_t + factor * cos_i) * normalIndices[2];     	


/*
        float omega = incidenceX * normalX + incidenceY * normalY + incidenceZ * normalZ;

        refractionStrategy.adjustToPhysicalMedium(omega, refractionIndices, normalIndices);

        float factor = refractionIndices[1] / refractionIndices[0];

		float cos1 = Math.abs(omega);
		float sin1_sq = 1.0f - (float)Math.pow(cos1, 2);

		float sin2_sq = (float)Math.pow(factor, 2) * sin1_sq;

		if(sin2_sq > 1.0f) {

			throw new InternalReflectionException();
           
        }

        float cos2 = (float)Math.sqrt(1- sin2_sq);
*/
//		   float cos2 = (float)Math.sqrt(1 - Math.pow(sin2, 2));

           //System.out.println(cos1 + ", " + cos2);

//           transmissionDirection[0] = -factor * incidenceX - (cos2 + factor * cos1) * normalX;
//           transmissionDirection[1] = -factor * incidenceY - (cos2 + factor * cos1) * normalY;
//           transmissionDirection[2] = -factor * incidenceZ - (cos2 + factor * cos1) * normalZ;


           //transmissionDirection[0] = cos2 * normalX + (1.0f / factor) * (-cos1 * normalX - incidenceX);
           //transmissionDirection[1] = cos2 * normalY + (1.0f / factor) * (-cos1 * normalY - incidenceY);
           //transmissionDirection[2] = cos2 * normalZ + (1.0f / factor) * (-cos1 * normalZ - incidenceZ);

/*
        transmissionDirection[0] = cos1 * normalIndices[0] + factor * (-cos2 * normalIndices[0] - incidenceX);
       	transmissionDirection[1] = cos1 * normalIndices[1] + factor * (-cos2 * normalIndices[1] - incidenceY);
        transmissionDirection[2] = cos1 * normalIndices[2] + factor * (-cos2 * normalIndices[2] - incidenceZ);
*/

        float mag = (float)Math.sqrt(Math.pow(transmissionDirection[0], 2) + Math.pow(transmissionDirection[1], 2) + Math.pow(transmissionDirection[2], 2));

        transmissionDirection[0] /= mag;
        transmissionDirection[1] /= mag;
        transmissionDirection[2] /= mag;

    }

    public void highlightCurves(BeamInterceptor interceptor, float[] posCoords, float[] textureCoords, float[] colorCoords, float[] args) {

		TextureMap map = interceptor.getTextureMap(TextureMap.CURVE_MAP_FLAG);
        TextureAttributes attributes = interceptor.getTextureAttributes(TextureMap.CURVE_MAP_FLAG);

        if(map != null && map.isEnabled()) {

            float[] colorData = new float[4];

            textureCoords[0] = 48f * textureCoords[0];
            textureCoords[1] = 48f * textureCoords[1];

            textureCoords[0] = (textureCoords[0]) - (float)Math.floor(textureCoords[0]);
            textureCoords[1] = (textureCoords[1]) - (float)Math.floor(textureCoords[1]);

            map.readTexel(TextureMap.BILINEAR_FILTER, textureCoords[0] , textureCoords[1] , colorData);

            colorCoords[0] += (args[0] * 1.0f * colorData[0]);
            colorCoords[1] += (args[1] * 1.0f * colorData[1]);
            colorCoords[2] += (args[2] * 1.0f * colorData[2]);

            map.readTexel(TextureMap.BILINEAR_FILTER, textureCoords[1] , textureCoords[0] , colorData);

            colorCoords[0] += (args[0] * 1.0f * colorData[0]);
            colorCoords[1] += (args[1] * 1.0f * colorData[1]);
            colorCoords[2] += (args[2] * 1.0f * colorData[2]);

            colorCoords[0] = Math.min(1.0f, colorCoords[0]);
            colorCoords[1] = Math.min(1.0f, colorCoords[1]);
            colorCoords[2] = Math.min(1.0f, colorCoords[2]);

	    }



     }

   public void  highlightShadows(RenderNotifier notifier, float[] posCoords, float[] colorCoords) {

	     float[] shadowingModulators = new float[] {1.0f, 1.0f, 1.0f};

               for(int i = 0; i < 16; i++) {

                    try {

		                 Light nextLight = notifier.getLight(i);

		                 if(nextLight != null) {

   		                    Light localLight = (Light)nextLight.clone();

                            if(localLight.getEnabled())
                               notifier.shadow(localLight, posCoords[0], posCoords[1], posCoords[2], shadowingModulators);

                          }

                     }  catch(Exception e) {}

	           }

          colorCoords[0]  *= shadowingModulators[0];
          colorCoords[1]  *=  shadowingModulators[1];
          colorCoords[2]  *=  shadowingModulators[2];

	}

    public void highlightHeatSource(BeamInterceptor interceptor, float[] posCoords, float[] textureCoords, float colorCoords[], float[] args) {

        TextureMap map = interceptor.getTextureMap(TextureMap.HEATSOURCE_MAP_FLAG);
        TextureAttributes attributes = interceptor.getTextureAttributes(TextureMap.HEATSOURCE_MAP_FLAG);

         if(map != null && map.isEnabled()) {

			 int[] heatBands = {attributes.getHeatBandIndexR(), attributes.getHeatBandIndexG(), attributes.getHeatBandIndexB()};

             float[] colorData = new float[4];

              textureCoords[0] = 36f * textureCoords[0];
              textureCoords[1] = 36f * textureCoords[1];

              textureCoords[0] = (textureCoords[0]) - (float)Math.floor(textureCoords[0]);
              textureCoords[1] = (textureCoords[1]) - (float)Math.floor(textureCoords[1]);

               map.readTexel(TextureMap.BILINEAR_FILTER, textureCoords[0] , textureCoords[1] , colorData);

               colorCoords[0] += (args[0] * 1.0f * colorData[heatBands[2]]);
               colorCoords[1] += (args[1] * 1.0f * .9f * colorData[heatBands[2]]);
               colorCoords[2] += (args[2] * 1.0f * colorData[heatBands[2]]);


	    }

	}

    protected void initializeImageShaderLightArray(BeamShader beamShader, RenderNotifier notifier, Matrix4D worldToObjectMatrix) {

         for(int i = 0; i < 16; i++) {

		     Light nextLight = notifier.getLight(i);

		     if(nextLight != null) {

		         Light localLight = (Light)nextLight.clone();
                 localLight.set(worldToObjectMatrix);

                 beamShader.setLight(localLight, i);

              }

         }

    }

    protected void initializeImageShaderEye(BeamShader beamShader, RenderNotifier notifier, Matrix4D worldToObjectMatrix) {

        float x = worldToObjectMatrix.getElement(0, 3);
        float y = worldToObjectMatrix.getElement(1, 3);
        float z = worldToObjectMatrix.getElement(2, 3);

	    beamShader.setEyeX(worldToObjectMatrix.getElement(0, 3));
	    beamShader.setEyeY(worldToObjectMatrix.getElement(1, 3));
	    beamShader.setEyeZ(worldToObjectMatrix.getElement(2, 3));

    }

    protected void initializeImageShaderMaterial(BeamShader beamShader, RenderNotifier notifier, Object3D object3D) {

		beamShader.setDiffuseCoefficientR(object3D.getDiffuseCoefficientR());
		beamShader.setDiffuseCoefficientG(object3D.getDiffuseCoefficientG());
		beamShader.setDiffuseCoefficientB(object3D.getDiffuseCoefficientB());

		beamShader.setSpecularCoefficientR(object3D.getSpecularCoefficientR());
	  	beamShader.setSpecularCoefficientG(object3D.getSpecularCoefficientG());
	  	beamShader.setSpecularCoefficientB(object3D.getSpecularCoefficientB());

		beamShader.setEmissiveCoefficientR(object3D.getEmissiveCoefficientR());
		beamShader.setEmissiveCoefficientG(object3D.getEmissiveCoefficientG());
	  	beamShader.setEmissiveCoefficientB(object3D.getEmissiveCoefficientB());

	  	beamShader.setFresnelCoefficientR(object3D.getFresnelCoefficientR());
	  	beamShader.setFresnelCoefficientG(object3D.getFresnelCoefficientG());
	  	beamShader.setFresnelCoefficientB(object3D.getFresnelCoefficientB());

	  	beamShader.setDiffuseReflectivityR(object3D.getDiffuseReflectivityR());
	  	beamShader.setDiffuseReflectivityG(object3D.getDiffuseReflectivityG());
	  	beamShader.setDiffuseReflectivityB(object3D.getDiffuseReflectivityB());

	  	beamShader.setShininess(object3D.getShininess());
	  	beamShader.setMicrofacetRoughness(object3D.getMicrofacetRoughness());

    }

    protected void initializeImageShaderTextureMapArray(BeamShader beamShader, RenderNotifier notifier, Object3D object3D) {

       for(int j = 0; j < beamShader.textureMapArray.length; j++)
		   beamShader.textureMapArray[j] = object3D.textureMapArray[j];

    }

}






