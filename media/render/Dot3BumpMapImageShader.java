package shamu.media.render;

/** Image shader class that performs diffuse bump map per pixel lighting calculations to simulate irregular surfaces, using the
 *  Blinn bump mapping algorithm.
 *
 *  @see TextureMap
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class Dot3BumpMapImageShader extends PhongImageShader {

   /**
    * Constructs a bump map image shader associated with the receptor object argument.
    *
    * @param receptor the receptor object associated with this bump map image shader.
    */

   protected Dot3BumpMapImageShader(VertexReceptor receptor) { super(receptor); }

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

  //    float[] displacementA = new float[3];
  //    float[] displacementB = new float[3];

  //    computeTangentU1(barycentricU, barycentricV, barycentricW, tangentU1);
  //    computeTangentU2(barycentricU, barycentricV, barycentricW, tangentU2);

  //    float projectionFactor = tangentU1[0] * tangentU2[0] + tangentU1[1] * tangentU2[1] + tangentU1[2] * tangentU2[2];

  //    tangentU2[0] = tangentU2[0] - projectionFactor * tangentU1[0];
  //    tangentU2[1] = tangentU2[1] - projectionFactor * tangentU1[1];
  //    tangentU2[2] = tangentU2[2] - projectionFactor * tangentU1[2];

  //    float normalizeFactorU1 = (float)Math.sqrt(tangentU1[0] * tangentU1[0] + tangentU1[1] * tangentU1[1] + tangentU1[2] * tangentU1[2]);
  //    float normalizeFactorU2 = (float)Math.sqrt(tangentU2[0] * tangentU2[0] + tangentU2[1] * tangentU2[1] + tangentU2[2] * tangentU2[2]);

  //    normal[0] = tangentU2[1] * tangentU1[2] - tangentU2[2] * tangentU1[1] ;
  //    normal[1] = tangentU2[2] * tangentU1[0] - tangentU2[0] * tangentU1[2];
  //    normal[2] = tangentU2[0] * tangentU1[1] - tangentU2[1] * tangentU1[0];

  //    float magnitudeN = (float)Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1] + normal[2] * normal[2]);

  //    float[] _tangentU1 = new float[4];
  //    float[] _tangentU2 = new float[4];

  //    _tangentU1[0] = tangentU1[0] / normalizeFactorU1;
  //    _tangentU1[1] = tangentU1[1] / normalizeFactorU1;
  //    _tangentU1[2] = tangentU1[2] / normalizeFactorU1;

  //    _tangentU2[0] = tangentU2[0] / normalizeFactorU2;
  //    _tangentU2[1] = tangentU2[1] / normalizeFactorU2;
  //    _tangentU2[2] = tangentU2[2] / normalizeFactorU2;

  //    float[] unitnormal = new float[4];

  //    unitnormal[0] = _tangentU2[1] * _tangentU1[2] - _tangentU2[2] * _tangentU1[1];
  //    unitnormal[1] = _tangentU2[2] * _tangentU1[0] - _tangentU2[0] * _tangentU1[2];
  //    unitnormal[2] = _tangentU2[0] * _tangentU1[1] - _tangentU2[1] * _tangentU1[0];


  //    displacementA[0] = normal[1] * tangentU2[2] - normal[2] * tangentU2[1];
  //    displacementA[1] = normal[2] * tangentU2[0] - normal[0] * tangentU2[2];
  //    displacementA[2] = normal[0] * tangentU2[1] - normal[1] * tangentU2[1];

  //    displacementB[0] = normal[1] * tangentU1[2] - normal[2] * tangentU1[1];
  //    displacementB[1] = normal[2] * tangentU1[0] - normal[0] * tangentU1[2];
  //    displacementB[2] = normal[0] * tangentU1[1] - normal[1] * tangentU1[1];

  //    float magnitudeA = displacementA[0] * displacementA[0] + displacementA[1] * displacementA[1] + displacementA[2] * displacementA[2];
  //    float magnitudeB = displacementB[0] * displacementB[0] + displacementB[1] * displacementB[1] + displacementB[2] * displacementB[2];

      float[] texel = new float[2];
  //    float[] bumpGradient = new float[4];

  //    float[] transformedNormal = new float[4];
  //    float[] _normalprime = new float[4];

      computeTexel(barycentricU, barycentricV, barycentricW, texel);

      float transformU = texel[0] * textureAttributesArray[1].getTextureMatrixAttribute().getElement(0, 0) + texel[1] * textureAttributesArray[1].getTextureMatrixAttribute().getElement(0, 1) + textureAttributesArray[0].getTextureMatrixAttribute().getElement(0, 2);
      float transformV = texel[0] * textureAttributesArray[1].getTextureMatrixAttribute().getElement(1, 0) + texel[1] * textureAttributesArray[1].getTextureMatrixAttribute().getElement(1, 1) + textureAttributesArray[0].getTextureMatrixAttribute().getElement(1, 2);
transformU *=2;
//transformV *=2;
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

       textureMapArray[1].readTexel(TextureMap.BUMP_MAP_FLAG, transformU, transformV, normal);


      normal[0] = 2 * normal[0] - 1;
      normal[1] = 2 * normal[1] - 1;
      normal[2] = 2 * normal[2] - 1;

     float  magnitudeN = (float)Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1] + normal[2] * normal[2]);

      normal[0] /= -magnitudeN;
      normal[1] /= -magnitudeN;
      normal[2] /= magnitudeN;



  //    _normalprime[0] =  _tangentU1[0] * normal[0] + _tangentU2[0] * normal[1] + unitnormal[0] * normal[2];
  //    _normalprime[1] =  _tangentU1[1] * normal[0] + _tangentU2[1] * normal[1] + unitnormal[1] * normal[2];
  //    _normalprime[2] =  _tangentU1[2] * normal[0] + _tangentU2[2] * normal[1] + unitnormal[2] * normal[2];

     // normal[0] = _normalprime[0];
     // normal[1] = _normalprime[1];
     // normal[2] = _normalprime[2];

  //    float factor = (float)Math.sqrt(_normalprime[0] * _normalprime[0] + _normalprime[1] * _normalprime[1] + _normalprime[2] * _normalprime[2]);

  //   _normalprime[0] /= factor;
  //   _normalprime[1] /= factor;
  //   _normalprime[2] /= factor;

  //   factor = magnitudeN / (_normalprime[0] * unitnormal[0] + _normalprime[1] * unitnormal[1] + _normalprime[2] * unitnormal[2]);

  //   _normalprime[0] *= factor;
  //   _normalprime[1] *= factor;
  //   _normalprime[2] *= factor;

  //    bumpGradient[0] = _normalprime[0] * displacementA[0] +  _normalprime[1] * displacementA[1] +  _normalprime[2] * displacementA[2];
  //    bumpGradient[1] = _normalprime[0] * displacementB[0] +  _normalprime[1] * displacementB[1] +  _normalprime[2] * displacementB[2];

  //    bumpGradient[0] /= magnitudeA;
  //    bumpGradient[1] /= magnitudeB;

  //    bumpGradient[0] *= magnitudeN;
  //    bumpGradient[1] *= magnitudeN;


  //    displacementA[0]  *= bumpGradient[0];
  //    displacementA[1] *= bumpGradient[0];
  //    displacementA[2] *= bumpGradient[0];

  //    displacementB[0] *= bumpGradient[1];
  //    displacementB[1] *= bumpGradient[1];
  //    displacementB[2] *= bumpGradient[1];

  //    displacementA[0] -= displacementB[0];
  //    displacementA[1] -= displacementB[1];
  //    displacementA[2] -= displacementB[2];

  //    normal[0] = displacementA[0];
  //    normal[1] = displacementA[1];
  //    normal[2] = displacementA[2];


  //    magnitudeN = (float)Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1] + normal[2] * normal[2]);

  //    normal[0] /= magnitudeN;
  //    normal[1] /= magnitudeN;
  //    normal[2] /= magnitudeN;

  //     magnitudeN = (float)Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1] + normal[2] * normal[2]);

 }


   protected void computeLitPixel(float barycentricU, float barycentricV, float barycentricW, float[] sample) throws BidirectionalIlluminationException {

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

      positionprime[0] = tangentU1[0] * position[0] + tangentU1[1] * position[1] + tangentU1[2] * position[2];
      positionprime[1] = tangentU2[1] * position[0] + tangentU2[1] * position[1] + tangentU2[2] * position[2];
      positionprime[2] = _normal[2] * position[0] + _normal[1] * position[1] + _normal[2] * position[2];

      //float eyeXprime = tangentU1[0] * eyeX + tangentU1[1] * eyeY + tangentU1[2] * eyeZ;
      //float eyeYprime = tangentU2[0] * eyeX + tangentU2[1] * eyeY + tangentU2[2] * eyeZ;
      //float eyeZprime = _normal[0] * eyeX + _normal[1] * eyeY + _normal[2] * eyeZ;

	  float[] incidence = new float[3];
	  float[] reflection = new float[3];
	  float[] view = new float[3];

     computeViewCoordinates(position[0], position[1], position[2], view);

     float viewXprime = tangentU1[0] * view[0] + tangentU1[1] * view[1] + tangentU1[2] * view[2];
     float viewYprime = tangentU2[0] * view[0] + tangentU2[1] * view[1] + tangentU2[2] * view[2];
     float viewZprime = _normal[0] * view[0]+ _normal[1] * view[1] + _normal[2] * view[2];

      float dotNV = _normal[0] * viewXprime + _normal[1] * viewYprime + _normal[2] * viewZprime;

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

		    float[] incidenceprime = new float[3];

		    incidenceprime[0] = tangentU1[0] * incidence[0] + tangentU1[1] * incidence[1] + tangentU1[2] * incidence[2];
			incidenceprime[1] = tangentU2[0] * incidence[0] + tangentU2[1] * incidence[1] + tangentU2[2] * incidence[2];
            incidenceprime[2] = _normal[0] * incidence[0] + _normal[1] * incidence[1] + _normal[2] * incidence[2];


		    lightArray[i].setReflectionCoordinates(positionprime[0], positionprime[1], positionprime[2], normalprime[0], normalprime[1], normalprime[2], reflection);
//            lightArray[i].setViewCoordinates(positionprime[0], positionprime[1], positionprime[2], eyeXprime, eyeYprime, eyeZprime, view);

            float distanceAttenuation = lightArray[i].getDistanceAttenuation(incidence[0], incidence[1], incidence[2]);
            float angularAttenuation = lightArray[i].getAngularAttenuation(incidence[0], incidence[1], incidence[2]);

		    float diffuse  = distanceAttenuation * angularAttenuation * Math.max(0.0f, incidenceprime[0] * normalprime[0] + incidenceprime[1] * normalprime[1] + incidenceprime[2] * normalprime[2]);
	        float specular = distanceAttenuation * angularAttenuation * Math.max(0.0f, (float)Math.pow(reflection[0] * viewXprime + reflection[1] * viewYprime + reflection[2] * viewZprime, shininess));

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

   protected void handleBidirectionaIllumination(float barycentricU, float barycentricV, float barycentricW, float[] sample) {}

}