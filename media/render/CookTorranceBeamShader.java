package shamu.media.render;

public class CookTorranceBeamShader extends BeamShader implements RenderingConstantsI {

   protected CookTorranceBeamShader(BeamReceptor receptor) { super(receptor); }

   protected void set(float[] coords, float[] normal) {

	  float[] incidence = new float[3];
	  float[] half = new float[3];
	  float[] receptor = new float[3];

     computeViewCoordinates(coords[0], coords[1], coords[2], receptor);

	  float diffuseR =  0.0f;
	  float diffuseG =  0.0f;
	  float diffuseB =  0.0f;

	  float specularR =  0.0f;
	  float specularG =  0.0f;
	  float specularB =  0.0f;

	  for(int i = 0; i < 16 && lightArray[i] != null; i++) {

         if(lightArray[i].getEnabled()) {

            lightArray[i].setIncidenceCoordinates(coords[0],coords[1], coords[2], incidence);

            half[0] = incidence[0] + receptor[0];
            half[1] = incidence[1] + receptor[1];
            half[2] = incidence[2] + receptor[2];

            float factor = (float)Math.sqrt(half[0] * half[0] + half[1] * half[1] + half[2] * half[2]);

            half[0] /= factor;
            half[1] /= factor;
            half[2] /= factor;

            float dotNH = normal[0] * half[0] + normal[1] * half[1] + normal[2] * half[2];
            float dotNV = normal[0] * receptor[0] + normal[1] * receptor[1] + normal[2] * receptor[2];
            float dotVH = receptor[0] * half[0] + receptor[1] * half[1] + receptor[2] * half[2];
            float dotNL = normal[0] * incidence[0] + normal[1] * incidence[1] + normal[2] * incidence[2];

           activateFrontFacingTextures();
/*
           if(dotNV < 0.0f ) {

               deactivateFrontFacingTextures();

			  dotNH  *= -1;
			  dotNV  *= -1;
			  dotNL *= -1;

			  fresnelCoefficientR =  .6f;
			  fresnelCoefficientG =  .6f;
			  fresnelCoefficientB =  .6f;

			  diffuseCoefficientR = .00f;
			  diffuseCoefficientG = .00f;
			  diffuseCoefficientB = .0f;

			  specularCoefficientR = .90f;
			  specularCoefficientG = .90f;
			  specularCoefficientB = .90f;

              microfacetRoughness = 1.6f;
		   }
*/
            float termA = microfacetRoughness * microfacetRoughness;
            float termB = dotNH * dotNH;

            float factorD = (float)Math.exp(-(1.0f - termB) / (termA * termB)) / (4.0f * termA * termB * termB);
            float factorG = (float)Math.min(1, Math.min(2 * dotNH * dotNV / dotVH, 2 * dotNH * dotNL / dotVH));

            float refractiveIndexR = (1 + (float)Math.sqrt(fresnelCoefficientR)) / (1 - (float)Math.sqrt(fresnelCoefficientR));
            float refractiveIndexG = (1 + (float)Math.sqrt(fresnelCoefficientG)) / (1 - (float)Math.sqrt(fresnelCoefficientG));
            float refractiveIndexB = (1 + (float)Math.sqrt(fresnelCoefficientB)) / (1 - (float)Math.sqrt(fresnelCoefficientB));

            float fresnelFactorR = (float)Math.sqrt(refractiveIndexR * refractiveIndexR + dotVH * dotVH - 1);
            float fresnelFactorG = (float)Math.sqrt(refractiveIndexG * refractiveIndexG + dotVH * dotVH - 1);
            float fresnelFactorB = (float)Math.sqrt(refractiveIndexB * refractiveIndexB + dotVH * dotVH - 1);
;
            fresnelFactorR = 0.5f * (float)(Math.pow(fresnelFactorR - dotVH, 2.0f) / Math.pow(fresnelFactorR + dotVH, 2.0f)) * (1.0f + (float)(Math.pow(dotVH * (fresnelFactorR + dotVH) - 1, 2.0f) / Math.pow(dotVH * (fresnelFactorR - dotVH) + 1, 2.0f)));
            fresnelFactorG = 0.5f * (float)(Math.pow(fresnelFactorG - dotVH, 2.0f) / Math.pow(fresnelFactorG + dotVH, 2.0f)) * (1.0f + (float)(Math.pow(dotVH * (fresnelFactorG + dotVH) - 1, 2.0f) / Math.pow(dotVH * (fresnelFactorG - dotVH) + 1, 2.0f)));
            fresnelFactorB = 0.5f * (float)(Math.pow(fresnelFactorB - dotVH, 2.0f) / Math.pow(fresnelFactorB + dotVH, 2.0f)) * (1.0f + (float)(Math.pow(dotVH * (fresnelFactorB + dotVH) - 1, 2.0f) / Math.pow(dotVH * (fresnelFactorB - dotVH) + 1, 2.0f)));

            float distanceAttenuation = lightArray[i].getDistanceAttenuation(incidence[0], incidence[1], incidence[2]);
            float angularAttenuation = lightArray[i].getAngularAttenuation(incidence[0], incidence[1], incidence[2]);

            specularR += lightArray[i].incidentEnergyR * distanceAttenuation * angularAttenuation * Math.max(0.0f, factorD * factorG * fresnelFactorR / ((float)Math.PI * dotNV));
   	        specularG += lightArray[i].incidentEnergyG * distanceAttenuation * angularAttenuation * Math.max(0.0f, factorD * factorG * fresnelFactorG / ((float)Math.PI * dotNV));
	        specularB += lightArray[i].incidentEnergyB * distanceAttenuation * angularAttenuation * Math.max(0.0f, factorD * factorG * fresnelFactorB / ((float)Math.PI * dotNV));

            diffuseR += lightArray[i].incidentEnergyR * distanceAttenuation * angularAttenuation * Math.max(0.0f, dotNL);
            diffuseG += lightArray[i].incidentEnergyG * distanceAttenuation * angularAttenuation * Math.max(0.0f, dotNL);
            diffuseB += lightArray[i].incidentEnergyB * distanceAttenuation * angularAttenuation * Math.max(0.0f, dotNL);


	     }

      }

	  outputR= Math.min(1.0f, Math.max(diffuseR * diffuseCoefficientR * diffuseCoefficientR + specularR * specularCoefficientR + emissiveCoefficientR, 0.0f) );
	  outputG = Math.min(1.0f, Math.max(diffuseG * diffuseCoefficientG * diffuseCoefficientG + specularG * specularCoefficientG + emissiveCoefficientG, 0.0f) );
	  outputB = Math.min(1.0f, Math.max(diffuseB * diffuseCoefficientB * diffuseCoefficientB + specularB * specularCoefficientB + emissiveCoefficientB, 0.0f) );

   }

}
