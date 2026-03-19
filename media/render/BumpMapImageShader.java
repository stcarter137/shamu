package shamu.media.render;

/** Image shader class that performs diffuse bump map per pixel lighting calculations to simulate irregular surfaces, using the
 *  Blinn bump mapping algorithm.
 *
 *  @see TextureMap
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class BumpMapImageShader extends PhongImageShader {

   /**
    * Constructs a bump map image shader associated with the camera object argument.
    *
    * @param camera the camera object associated with this bump map image shader.
    */

   protected BumpMapImageShader(VertexReceptor receptor) { super(receptor); }

   /**
    * Computes the perturbed normal vector coordinates of the pixel defined by the floating point arguments using barycentric interpolation.
    *
    * @param barycentricU the barycentric u-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param barycentricV the barycentric v-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param barycentricW the barycentric w-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param normal the array in which the perturbed normal vector coordinates are stored.
    */

   protected void computeNormal(float barycentricU, float barycentricV, float barycentricW, float[] normal) {

      float[] tangentU1 = new float[3];
      float[] tangentU2 = new float[3];

      float[] displacementA = new float[3];
      float[] displacementB = new float[3];

      computeTangentU1(barycentricU, barycentricV, barycentricW, tangentU1);
      computeTangentU2(barycentricU, barycentricV, barycentricW, tangentU2);

      normal[0] = tangentU2[1] * tangentU1[2] - tangentU2[2] * tangentU1[1];
      normal[1] = tangentU2[2] * tangentU1[0] - tangentU2[0] * tangentU1[2];
      normal[2] = tangentU2[0] * tangentU1[1] - tangentU2[1] * tangentU1[0];

      displacementA[0] = normal[1] * tangentU1[2] - normal[2] * tangentU1[1];
      displacementA[1] = normal[2] * tangentU1[0] - normal[0] * tangentU1[2];
      displacementA[2] = normal[0] * tangentU1[1] - normal[1] * tangentU1[1];

      displacementB[0] = normal[1] * tangentU2[2] - normal[2] * tangentU2[1];
      displacementB[1] = normal[2] * tangentU2[0] - normal[0] * tangentU2[2];
      displacementB[2] = normal[0] * tangentU2[1] - normal[1] * tangentU2[1];

      float displacementMagnitude = (float)Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1] + normal[2] * normal[2]);

      displacementA[0] /= displacementMagnitude;
      displacementA[1] /= displacementMagnitude;
      displacementA[2] /= displacementMagnitude;

      displacementB[0] /= displacementMagnitude;
      displacementB[1] /= displacementMagnitude;
      displacementB[2] /= displacementMagnitude;

      float[] texel = new float[2];
      float[] bumpGradient = new float[4];
      float[] _normal = new float[4];

      computeTexel(barycentricU, barycentricV, barycentricW, texel);

      float transformU = texel[0] * textureAttributesArray[1].getTextureMatrixAttribute().getElement(0, 0) + texel[1] * textureAttributesArray[1].getTextureMatrixAttribute().getElement(0, 1) + textureAttributesArray[0].getTextureMatrixAttribute().getElement(0, 2);
      float transformV = texel[0] * textureAttributesArray[1].getTextureMatrixAttribute().getElement(1, 0) + texel[1] * textureAttributesArray[1].getTextureMatrixAttribute().getElement(1, 1) + textureAttributesArray[0].getTextureMatrixAttribute().getElement(1, 2);

      if(textureMapArray[1].getBoundaryModeU() == TextureMap.WRAP_MODE)
         transformU -= (float)Math.floor(transformU);

      else if(textureMapArray[1].getBoundaryModeU() == TextureMap.CLAMP_MODE && transformU > 1.0f)
         transformU = 1.0f;

      else if(textureMapArray[1].getBoundaryModeU() == TextureMap.CLAMP_MODE && transformU < 0.0f)
         transformU = 0.0f;

      if(textureMapArray[1].getBoundaryModeV() == TextureMap.WRAP_MODE)
         transformV -= (float)Math.floor(transformV);

      else if(textureMapArray[1].getBoundaryModeV() == TextureMap.CLAMP_MODE && transformV > 1.0f)
         transformV = 1.0f;

      else if(textureMapArray[1].getBoundaryModeV() == TextureMap.CLAMP_MODE && transformV < 0.0f)
         transformV = 0.0f;

       textureMapArray[1].readTexel(TextureMap.BUMP_MAP_FLAG, transformU, transformV, _normal);

//_normal[0] = 2 * _normal[0] - 1;
_normal[1] = 2 * _normal[1] - 1;
//System.out.println(transformU + " " + transformV);
bumpGradient[0] = 1f*(float)Math.cos(100*transformU)* (float)Math.sin(50*transformV);
bumpGradient[1] = 1f*(float)Math.cos(100*transformV)* (float)Math.sin(50*transformU);

//System.out.println(_normal[0] + ", " + _normal[2]);

     // float diffNormalX = _normal[0];
      //float diffNormalY = _normal[1];
      //float diffNormalZ = _normal[2];
      //bumpGradient[0] =4*_normal[0];//=+= diffNormalX * displacementA[0];
     // bumpGradient[0] += diffNormalY * displacementA[1];
     // bumpGradient[0] += diffNormalZ * displacementA[2];

//      bumpGradient[1] = 4 *_normal[1];//-= diffNormalX * displacementB[0];
     // bumpGradient[0] -= diffNormalY * displacementB[1];
     // bumpGradient[0] -= diffNormalZ * displacementB[2];

     displacementA[0] *= bumpGradient[0];
      displacementA[1] *= bumpGradient[0];
      displacementA[2] *= bumpGradient[0];

      displacementB[0] *= bumpGradient[1];
      displacementB[1] *= bumpGradient[1];
      displacementB[2] *= bumpGradient[1];

      displacementA[0] -= displacementB[0];
      displacementA[1] -= displacementB[1];
      displacementA[2] -= displacementB[2];

      normal[0] += displacementA[0];
      normal[1] += displacementA[1];
      normal[2] += displacementA[2];

      float normalMagnitude = (float)Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1] + normal[2] * normal[2]);

      normal[0] /= normalMagnitude;
      normal[1] /= normalMagnitude;
      normal[2] /= normalMagnitude;
   }

/*
   protected void computeLitPixel(float barycentricU, float barycentricV, float barycentricW, float[] sample) {

   //   float[] normal = new float[3];

     // super.computeNormal(barycentricU, barycentricV, barycentricW, normal);

     // float factor = (float)Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1] + normal[2] * normal[2]);

	 // sample[0] = Math.max(-normal[0], 0);
	 // sample[1] = Math.max(-normal[1], 0);
	 // sample[2] = Math.max(-normal[2], 0);


      float[] position = new float[3];
      float[] positionprime = new float[3];
      float[] normalprime = new float[3];

      float[] tangentU1 = new float[3];
      float[] tangentU2 = new float[3];
      float[] _normal = new float[3];

      computePosition(barycentricU, barycentricV, barycentricW, position);
      computeNormal(barycentricU, barycentricV, barycentricW, normalprime);

      computeTangentU1(barycentricU, barycentricV, barycentricW, tangentU1);
      computeTangentU2(barycentricU, barycentricV, barycentricW, tangentU2);

      float normalizeFactorU1 = (float)Math.sqrt(tangentU1[0] * tangentU1[0] + tangentU1[1] * tangentU1[1] + tangentU1[2] * tangentU1[2]);

      tangentU1[0] /= normalizeFactorU1;
      tangentU1[1] /= normalizeFactorU1;
      tangentU1[2] /= normalizeFactorU1;

      float projectionFactor = tangentU1[0] * tangentU2[0] + tangentU1[1] * tangentU2[1] + tangentU1[2] * tangentU2[2];

      tangentU2[0] = tangentU2[0] - projectionFactor * tangentU1[0];
      tangentU2[1] = tangentU2[1] - projectionFactor * tangentU1[1];
      tangentU2[2] = tangentU2[2] - projectionFactor * tangentU1[2];

      float normalizeFactorU2 = (float)Math.sqrt(tangentU2[0] * tangentU2[0] + tangentU2[1] * tangentU2[1] + tangentU2[2] * tangentU2[2]);

      tangentU2[0] /= normalizeFactorU2;
      tangentU2[1] /= normalizeFactorU2;
      tangentU2[2] /= normalizeFactorU2;

      _normal[0] = tangentU2[1] * tangentU1[2] - tangentU2[2] * tangentU1[1];
      _normal[1] = tangentU2[2] * tangentU1[0] - tangentU2[0] * tangentU1[2];
      _normal[2] = tangentU2[0] * tangentU1[1] - tangentU2[1] * tangentU1[0];

     // normalprime[0] = tangentU1[0] * normal[0] + tangentU2[0] * normal[1] + _normal[0] * normal[2];
     // normalprime[1] = tangentU1[1] * normal[0] + tangentU2[1] * normal[1] + _normal[1] * normal[2];
     // normalprime[2] = tangentU1[2] * normal[0] + tangentU2[2] * normal[1] + _normal[2] * normal[2];

      positionprime[0] = tangentU1[0] * position[0] + tangentU2[0] * position[1] + _normal[0] * position[2];
      positionprime[1] = tangentU1[1] * position[0] + tangentU2[1] * position[1] + _normal[1] * position[2];
      positionprime[2] = tangentU1[2] * position[0] + tangentU2[2] * position[1] + _normal[2] * position[2];

      float eyeXprime = tangentU1[0] * eyeX + tangentU2[0] * eyeY + _normal[0] * eyeZ;
      float eyeYprime = tangentU1[1] * eyeX + tangentU2[1] * eyeY + _normal[1] * eyeZ;
      float eyeZprime = tangentU1[2] * eyeX + tangentU2[2] * eyeY + _normal[2] * eyeZ;

	  float[] incidence = new float[3];
	  float[] reflection = new float[3];
	  float[] view = new float[3];

	  float diffuseR =  0.0f;
	  float diffuseG =  0.0f;
	  float diffuseB =  0.0f;

	  float specularR =  0.0f;
	  float specularG =  0.0f;
	  float specularB =  0.0f;

	  for(int i = 0; i < 16 && lightArray[i] != null; i++) {

         if(lightArray[i].getEnabled()) {

		    lightArray[i].setIncidenceCoordinates(position[0], position[1], position[2], incidence);

		    float[] incidenceprime = new float[3];

		    incidenceprime[0] = tangentU1[0] * incidence[0] + tangentU2[0] * incidence[1] + _normal[0] * incidence[2];
			incidenceprime[1] = tangentU1[1] * incidence[0] + tangentU2[1] * incidence[1] + _normal[1] * incidence[2];
            incidenceprime[2] = tangentU1[2] * incidence[0] + tangentU2[2] * incidence[1] + _normal[2] * incidence[2];


		    lightArray[i].setReflectionCoordinates(positionprime[0], positionprime[1], positionprime[2], normalprime[0], normalprime[1], normalprime[2], reflection);
            lightArray[i].setViewCoordinates(positionprime[0], positionprime[1], positionprime[2], eyeXprime, eyeYprime, eyeZprime, view);

            float distanceAttenuation = lightArray[i].getDistanceAttenuation(incidence[0], incidence[1], incidence[2]);
            float angularAttenuation = lightArray[i].getAngularAttenuation(incidence[0], incidence[1], incidence[2]);

		    float diffuse  = distanceAttenuation * angularAttenuation * Math.max(0.0f, incidenceprime[0] * normalprime[0] + incidenceprime[1] * normalprime[1] + incidenceprime[2] * normalprime[2]);
	        float specular = distanceAttenuation * angularAttenuation * Math.max(0.0f, (float)Math.pow(reflection[0] * view[0] + reflection[1] * view[1] + reflection[2] * view[2], shininess));

            diffuseR += lightArray[i].incidentEnergyR * diffuse;
            diffuseG += lightArray[i].incidentEnergyG * diffuse;
            diffuseB += lightArray[i].incidentEnergyB * diffuse;

            specularR += lightArray[i].incidentEnergyR * specular;
            specularG += lightArray[i].incidentEnergyG * specular;
            specularB += lightArray[i].incidentEnergyB * specular;

	     }

      }

	  sample[0] = Math.min(1.0f, diffuseR * diffuseCoefficientR + specularR * specularCoefficientR + emissiveCoefficientR);
	  sample[1] = Math.min(1.0f, diffuseG * diffuseCoefficientG + specularG * specularCoefficientG + emissiveCoefficientG);
	  sample[2] = Math.min(1.0f, diffuseB * diffuseCoefficientB + specularB * specularCoefficientB + emissiveCoefficientB);


   }
*/
}