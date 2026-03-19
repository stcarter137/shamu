package shamu.media.render;

/** Class representing a Cook-Torrance image shader in the 3D rendering pipeline that performs depth testing and Cook-Torrance per pixel lighting calculations. Each instance
 *  contains four ouput registers that will store the output colors for an input pixel.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class CookTorranceImageShader extends IlluminationImageShader implements RenderingConstantsI {

   /**
    * Constructs a Cook-Torrance image shader associated with the receptor object argument.
    *
    * @param receptor the receptor associated with this Cook-Torrance image shader.
    */

   protected CookTorranceImageShader(VertexReceptor receptor) { 
      super(receptor); 
   }

   /**
    * Computes the color intensities of the pixel defined by the floating point arguments using the Cook-Torrance lighting model and stores the result in the floating point array argument.
    *
    * @param barycentricU the barycentric u-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param barycentricV the barycentric v-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param barycentricW the barycentric w-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param sample the array in which the computed lit pixel intensities are stored.
    */

   protected void computeLitPixel(float barycentricU, float barycentricV, float barycentricW, float[] sample) throws BidirectionalIlluminationException {

      float[] position = new float[3];
      float[] normal = new float[3];

      computePosition(barycentricU, barycentricV, barycentricW, position);
      computeNormal(barycentricU, barycentricV, barycentricW, normal);

      float[] incidence = new float[3];
      float[] half = new float[3];
      float[] receptor = new float[3];

      computeViewCoordinates(position[0], position[1], position[2], receptor);

      float dotNV = normal[0] * receptor[0] + normal[1] * receptor[1] + normal[2] * receptor[2];

      if(dotNV < 0.0f)
         throw new BidirectionalIlluminationException();

	  float diffuseR =  0.0f;
	  float diffuseG =  0.0f;
	  float diffuseB =  0.0f;

	  float specularR =  0.0f;
	  float specularG =  0.0f;
	  float specularB =  0.0f;

	  for(int i = 0; i < 16 && lightArray[i] != null; i++) {

         if(lightArray[i].getEnabled()) {

            lightArray[i].setIncidenceCoordinates(position[0], position[1], position[2], incidence);
          //  lightArray[i].setViewCoordinates(position[0], position[1], position[2], eyeX, eyeY, eyeZ, receptor);

 //           float dotNV = normal[0] * receptor[0] + normal[1] * receptor[1] + normal[2] * receptor[2];
/*
            if(dotNV < 0) {

               normal[0] *= -1;
               normal[1] *= -1;
               normal[2] *= -1;

               dotNV *= -1;

microfacetRoughness = 4.8f;

           //    fresnelCoefficientR = 000.6f;
             //  fresnelCoefficientG = .000008f;
              // fresnelCoefficientB = 2.9f;              

            }
*/
  //          else
  //             microfacetRoughness = 2.2f;

            half[0] = incidence[0] + receptor[0];
            half[1] = incidence[1] + receptor[1];
            half[2] = incidence[2] + receptor[2];

            float factor = (float)Math.sqrt(half[0] * half[0] + half[1] * half[1] + half[2] * half[2]);

            half[0] /= factor;
            half[1] /= factor;
            half[2] /= factor;

            //float dotNV = normal[0] * receptor[0] + normal[1] * receptor[1] + normal[2] * receptor[2];

            float dotNH = normal[0] * half[0] + normal[1] * half[1] + normal[2] * half[2];
            float dotVH = receptor[0] * half[0] + receptor[1] * half[1] + receptor[2] * half[2];
            float dotNL = normal[0] * incidence[0] + normal[1] * incidence[1] + normal[2] * incidence[2];

           // if(dotNH >= 0.0f && dotNL >= 0.0f) {

            float termA = microfacetRoughness * microfacetRoughness;
            float termB = dotNH * dotNH;

            float factorD = (float)Math.exp(-(1.0f - termB) / (termA * termB)) / (4.0f * termA * termB * termB);
            float factorG = (float)Math.min(1, Math.abs(Math.min(2 * dotNH * dotNV / dotVH, 2 * dotNH * dotNL / dotVH)));

            float refractiveIndexR = (1 + (float)Math.sqrt(fresnelCoefficientR)) / (1 - (float)Math.sqrt(fresnelCoefficientR));
            float refractiveIndexG = (1 + (float)Math.sqrt(fresnelCoefficientG)) / (1 - (float)Math.sqrt(fresnelCoefficientG));
            float refractiveIndexB = (1 + (float)Math.sqrt(fresnelCoefficientB)) / (1 - (float)Math.sqrt(fresnelCoefficientB));

            float fresnelFactorR = (float)Math.sqrt(refractiveIndexR * refractiveIndexR + dotVH * dotVH - 1);
            float fresnelFactorG = (float)Math.sqrt(refractiveIndexG * refractiveIndexG + dotVH * dotVH - 1);
            float fresnelFactorB = (float)Math.sqrt(refractiveIndexB * refractiveIndexB + dotVH * dotVH - 1);

            fresnelFactorR = 0.5f * (float)(Math.pow(fresnelFactorR - dotVH, 2.0f) / Math.pow(fresnelFactorR + dotVH, 2.0f)) * (1.0f + (float)(Math.pow(dotVH * (fresnelFactorR + dotVH) - 1, 2.0f) / Math.pow(dotVH * (fresnelFactorR - dotVH) + 1, 2.0f)));
            fresnelFactorG = 0.5f * (float)(Math.pow(fresnelFactorG - dotVH, 2.0f) / Math.pow(fresnelFactorG + dotVH, 2.0f)) * (1.0f + (float)(Math.pow(dotVH * (fresnelFactorG + dotVH) - 1, 2.0f) / Math.pow(dotVH * (fresnelFactorG - dotVH) + 1, 2.0f)));
            fresnelFactorB = 0.5f * (float)(Math.pow(fresnelFactorB - dotVH, 2.0f) / Math.pow(fresnelFactorB + dotVH, 2.0f)) * (1.0f + (float)(Math.pow(dotVH * (fresnelFactorB + dotVH) - 1, 2.0f) / Math.pow(dotVH * (fresnelFactorB - dotVH) + 1, 2.0f)));

            float distanceAttenuation = lightArray[i].getDistanceAttenuation(incidence[0], incidence[1], incidence[2]);
            float angularAttenuation = lightArray[i].getAngularAttenuation(incidence[0], incidence[1], incidence[2]);

        //    if(dotNV > 0.0f ) {

               specularR += lightArray[i].incidentEnergyR * distanceAttenuation * angularAttenuation *(factorD * factorG * fresnelFactorR / ((float)Math.PI * dotNV));
               specularG += lightArray[i].incidentEnergyG * distanceAttenuation * angularAttenuation *(factorD * factorG * fresnelFactorG / ((float)Math.PI * dotNV));
               specularB += lightArray[i].incidentEnergyB * distanceAttenuation * angularAttenuation *(factorD * factorG * fresnelFactorB / ((float)Math.PI * dotNV));

               diffuseR += lightArray[i].incidentEnergyR * distanceAttenuation * angularAttenuation * dotNL;
               diffuseG += lightArray[i].incidentEnergyG * distanceAttenuation * angularAttenuation * dotNL;
               diffuseB += lightArray[i].incidentEnergyB * distanceAttenuation * angularAttenuation * dotNL;

               sample[0] += diffuseR * diffuseCoefficientR * diffuseReflectivityR + specularR * specularCoefficientR + emissiveCoefficientR;
               sample[1] += diffuseG * diffuseCoefficientG * diffuseReflectivityG + specularG * specularCoefficientG + emissiveCoefficientG;
               sample[2] += diffuseB * diffuseCoefficientB * diffuseReflectivityB + specularB * specularCoefficientB + emissiveCoefficientB;
               
        //   }

          //  else {

               //normal[0] *= -1;
               //normal[1] *= -1;
               //normal[2] *= -1;

        // float[] reflection = new float[3];

        //       lightArray[i].setReflectionCoordinates(position[0], position[1], position[2], normal[0], normal[1], normal[2], reflection);
   

          // float specular = distanceAttenuation * angularAttenuation * Math.max(0.0f, (float)Math.pow(reflection[0] * receptor[0] + reflection[1] * receptor[1] + reflection[2] * receptor[2], 4));

            //specularR += lightArray[i].incidentEnergyR * specular;
            //specularG += lightArray[i].incidentEnergyG * specular;
            //specularB += lightArray[i].incidentEnergyB * specular;


         //  System.out.println(dotNL);          
/*
               diffuseR += lightArray[i].incidentEnergyR * distanceAttenuation * angularAttenuation * (-dotNL);
               diffuseG += lightArray[i].incidentEnergyG * distanceAttenuation * angularAttenuation * (-dotNL);
               diffuseB += lightArray[i].incidentEnergyB * distanceAttenuation * angularAttenuation * (-dotNL);

               sample[0] += .141f * diffuseR * diffuseReflectivityR;
               sample[1] += .36f* diffuseG * diffuseReflectivityG;
               sample[2] += .80f * diffuseB * diffuseReflectivityB;
*/


                //  diffuseR += lightArray[i].incidentEnergyR * distanceAttenuation * angularAttenuation * 20000 * (float)Math.abs(dotNL);
                 // diffuseG += lightArray[i].incidentEnergyG * distanceAttenuation * angularAttenuation * 20000 * (float)Math.abs(dotNL);
                 // diffuseB += lightArray[i].incidentEnergyB * distanceAttenuation * angularAttenuation * 20000 * (float)Math.abs(dotNL);


            // }
           

	     }

      }

	  sample[0] = Math.max(Math.min(1.0f, sample[0]), 0.0f);//diffuseR * diffuseCoefficientR * diffuseReflectivityR + specularR * specularCoefficientR + emissiveCoefficientR);
	  sample[1] = Math.max(Math.min(1.0f, sample[1]), 0.0f);//* diffuseCoefficientG * diffuseReflectivityG + specularG * specularCoefficientG + emissiveCoefficientG);
	  sample[2] = Math.max(Math.min(1.0f, sample[2]), 0.0f);//diffuseB * diffuseCoefficientB * diffuseReflectivityB + specularB * specularCoefficientB + emissiveCoefficientB);

   }

   protected void handleBidirectionaIllumination(float barycentricU, float barycentricV, float barycentricW, float[] sample) {

      System.out.println("Testing repository updating")

      float[] normal = new float[3];
      float[] position = new float[3];

      computeNormal(barycentricU, barycentricV, barycentricW, normal);
      computePosition(barycentricU, barycentricV, barycentricW, position);

//      float[] incidence = new float[3];
///      lightArray[0].setIncidenceCoordinates(position[0], position[1], position[2], incidence);
      
      float[] reflection = new float[3];
     // computeViewCoordinates(position[0], position[1], position[2], view);
      //lightArray[0].setReflectionCoordinates(position[0], position[1], position[2], -normal[0], -normal[1], -normal[2], reflection);
   

      computeReflectionCoordinates(position[0], position[1], position[2], -normal[0], -normal[1], -normal[2], reflection);
   
      float u = (float)(Math.acos(reflection[1]) / Math.PI);
      float v = (float)(Math.atan(reflection[2] / reflection[0]) / Math.PI + 0.5);


      //float dotNV = -normal[0] * view[0] - normal[1] * view[1] - normal[2] * view[2];
      
      //float[] texel = new float[] {dotNV, dotNV};

      //System.out.println(dotNH);

     // texel[0] = (4.0f * texel[0] + 4.0f) / 16.0f;
     // texel[1] = (4.0f * texel[1] + 4.0f) / 16.0f;

//      texel[0] = 0.5f * texel[0];
//      texel[1] = 0.5f;// * texel[1];

      float[] texel = new float[] {-u - .5f, -v - .5f};

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

      textureMapArray[0].readTexel(TextureMap.BASE_TEXTURE_FLAG, transformU,  transformV, textureColors);

                  sample[0] += .22f*textureColors[2];
                  sample[1] += .62f*textureColors[2];
                  sample[2] += textureColors[2];


/*
      float[] position = new float[3];
      float[] normal = new float[3];

      computePosition(barycentricU, barycentricV, barycentricW, position);
      computeNormal(barycentricU, barycentricV, barycentricW, normal);

      float[] incidence = new float[3];      

      lightArray[0].setIncidenceCoordinates(position[0], position[1], position[2], incidence);

      float dotNL = normal[0] * incidence[0] + normal[1] * incidence[1] + normal[2] * incidence[2];

      float distanceAttenuation = lightArray[0].getDistanceAttenuation(incidence[0], incidence[1], incidence[2]);
      float angularAttenuation = lightArray[0].getAngularAttenuation(incidence[0], incidence[1], incidence[2]);

     float diffuseR =  0.0f;
     float diffuseG =  0.0f;
     float diffuseB =  0.0f;

     float specularR =  0.0f;
     float specularG =  0.0f;
     float specularB =  0.0f;

      diffuseR += lightArray[0].incidentEnergyR * distanceAttenuation * angularAttenuation * (-dotNL);
      diffuseG += lightArray[0].incidentEnergyG * distanceAttenuation * angularAttenuation * (-dotNL);
      diffuseB += lightArray[0].incidentEnergyB * distanceAttenuation * angularAttenuation * (-dotNL);

      sample[0] += .141f * diffuseR * diffuseReflectivityR;
      sample[1] += .36f* diffuseG * diffuseReflectivityG;
      sample[2] += .80f * diffuseB * diffuseReflectivityB;

     sample[0] = Math.max(Math.min(1.0f, sample[0]), 0.0f);//diffuseR * diffuseCoefficientR * diffuseReflectivityR + specularR * specularCoefficientR + emissiveCoefficientR);
     sample[1] = Math.max(Math.min(1.0f, sample[1]), 0.0f);//* diffuseCoefficientG * diffuseReflectivityG + specularG * specularCoefficientG + emissiveCoefficientG);
     sample[2] = Math.max(Math.min(1.0f, sample[2]), 0.0f);//diffuseB * diffuseCoefficientB * diffuseReflectivityB + specularB * specularCoefficientB + emissiveCoefficientB);

*/

   }  

}
