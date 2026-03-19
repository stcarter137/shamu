package shamu.media.render;

public class BeamReceptor extends Receptor {

     protected TracerFunctional detector;

    // protected BeamShader beamShader;

     public BeamReceptor(int pixelFormat, int bufferWidth, int bufferHeight) {  super(pixelFormat, bufferWidth, bufferHeight); }
/*
     protected void traceBeam(RenderNotifier notifier, float startX, float startY, float startZ, float endX, float endY, float endZ, float[] colorCoords, int depth, float arg1, float arg2, float arg3)  throws NullIntersectionException {

         if(depth < 3) {

             beamShader= new CookTorranceBeamShader(this);
             detector = new BeamDetector();
             //Object[] data = new Object[3];
             
             //float[] posCoords = (float[])data[2];
             float[] posCoords = new float[3];
             float[] normalCoords = new float[3];
             float[] transmissionDirection = new float[] {0, 0, 1.0f };//[3];
             float[] reflectionDirection = new float[3];
             float[] newCoords = new float[3];
             float[] tempColorCoords = new float[3];
             float[] textureCoords = new float[2];

             java.util.ListIterator<BeamInterceptor> iterator = notifier.getVisuals()
                                                                 .listIterator(0);

             BeamInterceptor interceptor = detector.hit(iterator, startX, startY, startZ, endX, endY, endZ, posCoords);
             Matrix4D matrix = interceptor.getMatrix();

             //Matrix4D matrix = (Matrix4D)data[1];
             //BeamInterceptor interceptor = (BeamInterceptor)data[0];

             Matrix4D imatrix = new Matrix4D();
             //matrix.invert(imatrix);

             interceptor.getMatrix()
                        .invert(imatrix);

             newCoords[0] = matrix.getElement(0, 0) * posCoords[0]  + matrix.getElement(0, 1) * posCoords[1]  + matrix.getElement(0, 2) * posCoords[2]  + matrix.getElement(0, 3);
	         newCoords[1] = matrix.getElement(1, 0) * posCoords[0]  + matrix.getElement(1, 1) * posCoords[1]  + matrix.getElement(1, 2) * posCoords[2]  + matrix.getElement(1, 3);
             newCoords[2] = matrix.getElement(2, 0) * posCoords[0]  + matrix.getElement(2, 1) * posCoords[1]  + matrix.getElement(2, 2) * posCoords[2]  + matrix.getElement(2, 3);

		     initializeImageShaderMaterial(notifier, interceptor);
             initializeImageShaderEye(notifier, matrix);
             initializeImageShaderLightArray(notifier, matrix);
       		 initializeImageShaderTextureMapArray(notifier, interceptor);

			TracerGeometry geom = (TracerGeometry )interceptor.getGeometry();

            geom.normal(normalCoords, newCoords);
            geom.texturize(textureCoords, newCoords);

            float normalX = imatrix.getElement(0, 0) * normalCoords[0]   + imatrix.getElement(0, 1) * normalCoords[1]  + imatrix.getElement(0, 2) * normalCoords[2];
	        float normalY = imatrix.getElement(1, 0) * normalCoords[0]   + imatrix.getElement(1, 1) * normalCoords[1]  + imatrix.getElement(1, 2) * normalCoords[2];
            float normalZ = imatrix.getElement(2, 0) * normalCoords[0]   + imatrix.getElement(2, 1) * normalCoords[1]  + imatrix.getElement(2, 2) * normalCoords[2];

//            float reflectivityR = ((Object3D)data[0]).getDiffuseReflectivityR();
//            float reflectivityG = ((Object3D)data[0]).getDiffuseReflectivityG();
//            float reflectivityB = ((Object3D)data[0]).getDiffuseReflectivityB();

//            float transparencyR = ((Object3D)data[0]).getTransparencyR();
//            float transparencyG = ((Object3D)data[0]).getTransparencyG();
//            float transparencyB = ((Object3D)data[0]).getTransparencyB();

            float reflectivityR = interceptor.getDiffuseReflectivityR();
            float reflectivityG = interceptor.getDiffuseReflectivityG();
            float reflectivityB = interceptor.getDiffuseReflectivityB();

            float transparencyR = interceptor.getTransparencyR();
            float transparencyG = interceptor.getTransparencyG();
            float transparencyB = interceptor.getTransparencyB();

            beamShader.set(newCoords, normalCoords);

	        colorCoords[0] = Math.max(0.0f, arg1*(beamShader.outputR)); //(float)Math.min(1.0f, colorCoords[0] + arg1*(beamShader.outputR) * indirectR);
	        colorCoords[1] = Math.max(0.0f, arg2*(beamShader.outputG)); //(float)Math.min(1.0f, colorCoords[1] + arg2*(beamShader.outputG) * indirectG);
	        colorCoords[2] = Math.max(0.0f, arg3*(beamShader.outputB)); //(float)Math.min(1.0f, colorCoords[2] + arg3*(beamShader.outputB)  * indirectB);

            if(interceptor.getRenderingAttributes().getCurveHighlightsAttribute() == RenderingAttributes.INTEGRAL_CURVES_ENABLED)
               this.highlightCurves(interceptor, newCoords, textureCoords, colorCoords, new float[] { arg1, arg2,  arg3 } );

            if(interceptor.getRenderingAttributes().getHeatSourceAttribute() == RenderingAttributes.HEAT_SOURCE_ENABLED )
               this.highlightHeatSource(interceptor, newCoords, textureCoords, colorCoords, new float[] { arg1, arg2, arg3 });

            if(interceptor.getRenderingAttributes().getShadowingAttribute() == RenderingAttributes.SHADOWING_ENABLED )
               this.highlightShadows(notifier, posCoords, colorCoords);

            colorCoords[0] *= (transparencyR);
            colorCoords[1] *= (transparencyG);
            colorCoords[2] *= (transparencyB);

            float index2 = interceptor.getIndexOfRefraction();
            float index1 = interceptor.getOuterIndexOfRefraction();

            float directionX = startX - endX;
            float directionY = startY - endY;
            float directionZ = startZ - endZ;;

            float r = (float)Math.sqrt(directionX * directionX + directionY * directionY + directionZ * directionZ);

            directionX /= r;
            directionY /= r;
            directionZ /= r;

            float[] reflectedColors = new float[3];
            float[] transmittedColors = new float[3];

            try { 
                calculateTransmissionDirection(directionX, directionY, directionZ, normalX, normalY, normalZ, index1, index2, transmissionDirection); 
               // interceptor.swap();
            } catch(Exception e) {

                transmissionDirection[0] = 0;
                transmissionDirection[1] = 0;
                transmissionDirection[2] = 0;

            }

            try { calculateReflectionDirection(directionX, directionY, directionZ, normalX, normalY, normalZ, reflectionDirection); } catch(Exception e) {}

            try{  traceBeam(notifier, posCoords[0] + .001f * reflectionDirection[0], posCoords[1] + .001f * reflectionDirection[1], posCoords[2]  + .001f * reflectionDirection[2],   posCoords[0] + reflectionDirection[0] , posCoords[1] +  reflectionDirection[1], posCoords[2]  + reflectionDirection[2], reflectedColors, depth + 1,  arg1 * reflectivityR , arg2 * reflectivityG, arg3 * reflectivityB); }  catch(Exception e) {}
            try{  traceBeam(notifier, posCoords[0] + .001f * transmissionDirection[0], posCoords[1] + .001f * transmissionDirection[1], posCoords[2]  + .001f * transmissionDirection[2],   posCoords[0] + transmissionDirection[0] , posCoords[1] + transmissionDirection[1], posCoords[2] + transmissionDirection[2], transmittedColors, depth + 1,  arg1 * (1 - transparencyR), arg2 * (1 - transparencyG), arg3 * (1 - transparencyB)); }  catch(Exception e) {}

	       colorCoords[0]= (float)Math.min(1.0f, colorCoords[0] + reflectedColors[0] + transmittedColors[0]);
	       colorCoords[1]= (float)Math.min(1.0f, colorCoords[1] + reflectedColors[1] + transmittedColors[1]);
	       colorCoords[2]= (float)Math.min(1.0f, colorCoords[2] + reflectedColors[2] + transmittedColors[2]);

           //System.out.println(colorCoords[0] + ", " + colorCoords[1] + ", " + colorCoords[2]);

	    }

	}*/
/*
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
*/

/*
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
*/
/*     
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
*/
/*
     protected void calculateTransmissionDirection(float incidenceX, float incidenceY, float incidenceZ, float normalX, float normalY, float normalZ, float indexOfRefraction1, float indexOfRefraction2, float[] transmissionDirection) throws InternalReflectionException {

         float index1 = indexOfRefraction1;
         float index2 = indexOfRefraction2;

         float dot = incidenceX * normalX + incidenceY * normalY + incidenceZ * normalZ;

	     if(dot < 0.0f) {

			//float temp = index2;

			//index2 = index1;
			//index1 = temp;

            normalX *= -1.0f;
            normalY *= -1.0f;
            normalZ *= -1.0f;

		   }

           float factor = index1 / index2;

		   float cos1 = dot;//(float)Math.abs(dot);
		   float sin1_sq = 1.0f - (float)Math.pow(cos1, 2);

		   float sin2_sq = (float)Math.pow(factor, 2) * sin1_sq;



		   if(sin2_sq > 1.0f) {

		      throw new InternalReflectionException();
           }

           float cos2 = (float)Math.sqrt(1- sin2_sq);

//		   float cos2 = (float)Math.sqrt(1 - Math.pow(sin2, 2));

           //System.out.println(cos1 + ", " + cos2);

//           transmissionDirection[0] = -factor * incidenceX - (cos2 + factor * cos1) * normalX;
//           transmissionDirection[1] = -factor * incidenceY - (cos2 + factor * cos1) * normalY;
//           transmissionDirection[2] = -factor * incidenceZ - (cos2 + factor * cos1) * normalZ;


           transmissionDirection[0] = cos2 * normalX + (1.0f / factor) * (-cos1 * normalX - incidenceX);
           transmissionDirection[1] = cos2 * normalY + (1.0f / factor) * (-cos1 * normalY - incidenceY);
           transmissionDirection[2] = cos2 * normalZ + (1.0f / factor) * (-cos1 * normalZ - incidenceZ);


          // System.out.println(-incidenceX + ", " + transmissionDirection[0]);

         float mag = (float)Math.sqrt(Math.pow(transmissionDirection[0], 2) + Math.pow(transmissionDirection[1], 2) + Math.pow(transmissionDirection[2], 2));

         transmissionDirection[0] /= mag;
         transmissionDirection[1] /= mag;
         transmissionDirection[2] /= mag;



     }
*/
     protected void notify(RenderNotifier notifier) {

         BeamShader beamShader = new CookTorranceBeamShader(this);
         BeamDetector detector = new BeamDetector();

         int width = accumulationBuffer.getWidth();
         int height = accumulationBuffer.getHeight();

         float offsetX = (int)notifier.getAntiAliasingOffsetX();
         float offsetY = (int)notifier.getAntiAliasingOffsetY();

         int dimension = notifier.getSuperSamplingDimension();

         for(int i = 0; i < width; i++) {
	 	     for(int j = 0; j < height; j++) {

			    float xscreen =  ((i + offsetX/dimension) - width / 2.0f) / (width / 2.0f);
			    float yscreen =  ((j + offsetY/dimension) - height / 2.0f) / (height / 2.0f);


		//	    float x = xscreen *  (float)Math.tan(fieldOfView / 2.0f )*  1000;
		//	    float y = yscreen *  (float)Math.tan(fieldOfView / 2.0f) * 1000;
		//	    float z = nearPlane;

		    	float x = xscreen * nearPlane * (float)Math.tan(fieldOfView / 2.0f);
			    float y = yscreen * nearPlane * (float)Math.tan(fieldOfView / 2.0f);
	 		    float z = nearPlane;

                //System.out.println(x + ", " + y + ", " + z);

                try {

                    float[] colors = new float[3];
                    TracerBeam beam = new TracerBeam();

			        beam.trace(beamShader, detector, notifier, 0, 0, 0,  x , y, z, colors, 0, 1.0f, 1.0f, 1.0f);

                    colors[0] *= 255.0f;
                    colors[1] *= 255.0f;
                    colors[2] *= 255.0f;

                    virtualBuffer.setPixel(i, j, colors);

                }  catch(Exception e) {}

             }

         }

     }

    /**
      * Initializes the light array of the image shader associated with this camera for the pixel setting stage of the 3D rendering pipeline.
      *
      * @param RenderNotifier the notifier that this camera is registered to.
       */
/*
    protected void initializeImageShaderLightArray(RenderNotifier notifier, Matrix4D worldToObjectMatrix) {

         for(int i = 0; i < 16; i++) {

		     Light nextLight = notifier.getLight(i);

		     if(nextLight != null) {

		         Light localLight = (Light)nextLight.clone();
                 localLight.set(worldToObjectMatrix);

                 beamShader.setLight(localLight, i);

              }

         }

    }
*/
    /**
      * Initializes the eye position coordinate fields of the image shader associated with this camera for the pixel setting stage of the 3D rendering pipeline.
      *
      * @param RenderNotifier the notifier that this camera is registered to.
       */
/*
    protected void initializeImageShaderEye(RenderNotifier notifier, Matrix4D worldToObjectMatrix) {

        float x = worldToObjectMatrix.getElement(0, 3);
        float y = worldToObjectMatrix.getElement(1, 3);
        float z = worldToObjectMatrix.getElement(2, 3);

	    beamShader.setEyeX(worldToObjectMatrix.getElement(0, 3));
	    beamShader.setEyeY(worldToObjectMatrix.getElement(1, 3));
	    beamShader.setEyeZ(worldToObjectMatrix.getElement(2, 3));

    }
*/
    /**
      * Initializes the material fields of the image shader associated with this camera for the pixel setting stage of the 3D rendering pipeline.
      *
      * @param RenderNotifier the notifier that this camera is registered to.
      */
/*
   protected void initializeImageShaderMaterial(RenderNotifier notifier, Object3D object3D) {

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
*/
     /*
   protected void initializeImageShaderTextureMapArray(RenderNotifier notifier, Object3D object3D) {

       for(int j = 0; j < beamShader.textureMapArray.length; j++)
		   beamShader.textureMapArray[j] = object3D.textureMapArray[j];

   }
*/
}

