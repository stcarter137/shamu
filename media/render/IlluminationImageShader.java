package shamu.media.render;

/** Class representing an illumination image shader in the 3D rendering pipeline that performs depth testing and per pixel lighting calculations. Each instance
 *  contains four ouput registers that will store the output colors for an input pixel.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

abstract public class IlluminationImageShader extends ImageShader implements RenderingConstantsI {

   /**
    * Constructs an illumination image shader associated with the receptor object argument.
    *
    * @param receptor the receptor associated with this illumination image shader.
    */

   protected IlluminationImageShader(VertexReceptor receptor) { super(receptor); }

   /**
    * Computes the color intensities of the pixel defined by the floating point arguments using the lighting model associated with this illumination image shader and stores
    * the result in the floating point array argument.
    *
    * @param barycentricU the barycentric u-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param barycentricV the barycentric v-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param barycentricW the barycentric w-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param sample the array in which the computed lit pixel intensities are stored.
    */

   abstract protected void computeLitPixel(float barycentricU, float barycentricV, float barycentricW, float[] sample) throws BidirectionalIlluminationException;

   abstract protected void handleBidirectionaIllumination(float barycentricU, float barycentricV, float barycentricW, float[] sample);

   /**
    * Sets the pixel defined by the screen and device coordinate arguments and outputs the colored pixel into the output fields of this image shader
    * if it is determineed that the pixel being processed is visible and is inside the viewing frustum associated with this image shader.
    *
    * @param renderingAttributes the rendering attributes containing shading algorithm flags used in shading the pixel being processed.
    * @param barycentricU the barycentric u-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param barycentricV the barycentric v-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param barycentricW the barycentric w-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param screenX the screen space x-coordinate of the pixel being processed relative to supersampling image space.
    * @param screenY the screen space y-coordinate of the pixel being processed relative to supersampling image space.
    * @param deviceX the device space x-coordinate of the pixel being processed relative to supersampling image space.
    * @param deviceY the device space y-coordinate of the pixel being processed relative to supersampling image space.
    * @param pixelX the device space x-coordinate of the pixel being processed relative to image space.
    * @param pixelY the device space y-coordinate of the pixel being processed relative to image space.
    *
    * @return true if it is determined that the pixel being processed is visible and is inside the viewing frustum associated with this image shader, otherwise false.
    */

   protected boolean set(RenderingAttributes renderingAttributes, float barycentricU, float barycentricV, float barycentricW, float screenX, float screenY, float deviceX, float deviceY, int pixelX, int pixelY) {

      if(screenX > -1.0f && screenX < 1.0f && screenY > -1.0f && screenY < 1.0f) {         

         float[] sample = new float[3];

         if(renderingAttributes.getDepthBufferStateAttribute() == DEPTH_BUFFER_ENABLED) {

            float ux = coordinateX1 - coordinateX2;
            float uy = coordinateY1 - coordinateY2;
            float uz = coordinateZ1 - coordinateZ2;

            float vx = coordinateX1 - coordinateX3;
            float vy = coordinateY1 - coordinateY3;
            float vz = coordinateZ1 - coordinateZ3;

            float wx = uy * vz - uz * vy;
            float wy = uz * vx - ux * vz;
            float wz = ux * vy - uy * vx;

            float screenZ = coordinateZ1 - (wx * (screenX - coordinateX1) + wy * (screenY - coordinateY1)) / wz;

            float[] depth = new float[1];

            receptor.depthBuffer.getPixel(pixelX, pixelY, depth);

	        if(screenZ > 0.0f && screenZ < 1.0f && screenZ <= depth[0]) {

               receptor.depthBuffer.setPixel(pixelX, pixelY, new float[]{screenZ});

               try { computeLitPixel(barycentricU, barycentricV, barycentricW, sample); }

               catch(BidirectionalIlluminationException e) { handleBidirectionaIllumination(barycentricU, barycentricV, barycentricW, sample); }

               if(renderingAttributes.getTexturingAttribute() == TEXTURING_ENABLED) {

                  float[] texel = new float[2];

                  computeTexel(barycentricU, barycentricV, barycentricW, texel);

                  float transformU = texel[0] * textureAttributesArray[0].getTextureMatrixAttribute().getElement(0, 0) + texel[1] * textureAttributesArray[0].getTextureMatrixAttribute().getElement(0, 1) + textureAttributesArray[0].getTextureMatrixAttribute().getElement(0, 2);
                  float transformV = texel[0] * textureAttributesArray[0].getTextureMatrixAttribute().getElement(1, 0) + texel[1] * textureAttributesArray[0].getTextureMatrixAttribute().getElement(1, 1) + textureAttributesArray[0].getTextureMatrixAttribute().getElement(1, 2);

                  if(textureMapArray[0].getBoundaryModeU() == TextureMap.WRAP_MODE)
                     transformU -= (float)Math.floor(transformU);

                  else if(textureMapArray[0].getBoundaryModeU() == TextureMap.CLAMP_MODE && transformU > 1.0f)
                     transformU = 1.0f;

                  else if(textureMapArray[0].getBoundaryModeU() == TextureMap.CLAMP_MODE && transformU < 0.0f)
                     transformU = 0.0f;

                  if(textureMapArray[0].getBoundaryModeV() == TextureMap.WRAP_MODE)
                     transformV -= (float)Math.floor(transformV);

                  else if(textureMapArray[0].getBoundaryModeV() == TextureMap.CLAMP_MODE && transformV > 1.0f)
                     transformV = 1.0f;

                  else if(textureMapArray[0].getBoundaryModeV() == TextureMap.CLAMP_MODE && transformV < 0.0f)
                     transformV = 0.0f;

	              float[] textureColors = new float[4];

                  textureMapArray[0].readTexel(TextureMap.BASE_TEXTURE_FLAG, transformU, transformV, textureColors);

                  sample[0] += textureColors[0];
                  sample[1] += textureColors[1];
                  sample[2] += textureColors[2];

                  textureMapArray[0].readTexel(TextureMap.BASE_TEXTURE_FLAG, transformV, transformU, textureColors);

                  sample[0] += textureColors[0];
                  sample[1] += textureColors[1];
                  sample[2] += textureColors[2];

			   }

               outputR = (float)Math.min(sample[0], 1.0);
               outputG = (float)Math.min(sample[1], 1.0);
               outputB = (float)Math.min(sample[2], 1.0);

               ///System.out.println(outputB);

               return true;

		    }

	     }

         else {

            try { computeLitPixel(barycentricU, barycentricV, barycentricW, sample); }

            catch(BidirectionalIlluminationException e) {}

            if(renderingAttributes.getTexturingAttribute() == TEXTURING_ENABLED) {

               float[] texel = new float[2];

               computeTexel(barycentricU, barycentricV, barycentricW, texel); 

               float transformU = texel[0] * textureAttributesArray[0].getTextureMatrixAttribute().getElement(0, 0) + texel[1] * textureAttributesArray[0].getTextureMatrixAttribute().getElement(0, 1) + textureAttributesArray[0].getTextureMatrixAttribute().getElement(0, 2);
               float transformV = texel[0] * textureAttributesArray[0].getTextureMatrixAttribute().getElement(1, 0) + texel[1] * textureAttributesArray[0].getTextureMatrixAttribute().getElement(1, 1) + textureAttributesArray[0].getTextureMatrixAttribute().getElement(1, 2);

               if(textureMapArray[0].getBoundaryModeU() == TextureMap.WRAP_MODE)
                  transformU -= (float)Math.floor(transformU);

               else if(textureMapArray[0].getBoundaryModeU() == TextureMap.CLAMP_MODE && transformU > 1.0f)
                  transformU = 1.0f;

               else if(textureMapArray[0].getBoundaryModeU() == TextureMap.CLAMP_MODE && transformU < 0.0f)
                  transformU = 0.0f;

               if(textureMapArray[0].getBoundaryModeV() == TextureMap.WRAP_MODE)
                  transformV -= (float)Math.floor(transformV);

               else if(textureMapArray[0].getBoundaryModeV() == TextureMap.CLAMP_MODE && transformV > 1.0f)
                  transformV = 1.0f;

               else if(textureMapArray[0].getBoundaryModeV() == TextureMap.CLAMP_MODE && transformV < 0.0f)
                  transformV = 0.0f;

               float[] textureColors = new float[4];

               textureMapArray[0].readTexel(TextureMap.BASE_TEXTURE_FLAG, transformU, transformV, textureColors);

               sample[0] *= textureColors[0];
               sample[1] *= textureColors[1];
               sample[2] *= textureColors[2];

			}

            outputR = sample[0];
            outputG = sample[1];
            outputB = sample[2];

            return true;

	     }

      }

      return false;

   }

}