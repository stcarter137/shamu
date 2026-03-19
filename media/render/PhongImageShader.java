package shamu.media.render;

/** Class representing a phong image shader in the 3D rendering pipeline that performs depth testing and Phong per pixel lighting calculations. Each instance
 *  contains four ouput registers that will store the output colors for an input pixel.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class PhongImageShader extends IlluminationImageShader implements RenderingConstantsI {

   /**
    * Constructs a phong image shader associated with the receptor object argument.
    *
    * @param receptor the receptor object associated with this phong image shader.
    */

   protected PhongImageShader(VertexReceptor receptor) { super(receptor); }

   /**
    * Computes the color intensities of the pixel defined by the floating point arguments using the Phong lighting model and stores the result in the floating point array argument.
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
      float[] reflection = new float[3];
      float[] view = new float[3];

      computeViewCoordinates(position[0], position[1], position[2], view);

      float dotNV = normal[0] * view[0] + normal[1] * view[1] + normal[2] * view[2];

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
		    lightArray[i].setReflectionCoordinates(position[0], position[1], position[2], normal[0], normal[1], normal[2], reflection);
            //lightArray[i].setViewCoordinates(position[0], position[1], position[2], eyeX, eyeY, eyeZ, view);

            float distanceAttenuation = lightArray[i].getDistanceAttenuation(incidence[0], incidence[1], incidence[2]);
            float angularAttenuation = lightArray[i].getAngularAttenuation(incidence[0], incidence[1], incidence[2]);

            float dotNL = incidence[0] * normal[0] + incidence[1] * normal[1] + incidence[2] * normal[2];
            float dotRV = reflection[0] * view[0] + reflection[1] * view[1] + reflection[2] * view[2];

		     float diffuse  = distanceAttenuation * angularAttenuation * Math.max(0.0f, dotNL);
	        float specular = distanceAttenuation * angularAttenuation * Math.max(0.0f, (float)Math.pow(dotRV, shininess));

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
