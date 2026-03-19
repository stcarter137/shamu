package shamu.media.render;

/** Class representing a image shader in the 3D rendering pipeline that performs depth testing and per pixel lighting calculations. Each instance
 *  contains four ouput registers that will store the output colors for an input pixel.
 *
 *  @author  Scott T. Carter
 *  @version 1.3
 */

abstract public class ImageShader implements RenderingConstantsI {

   /**
    * Field that will store the output red component of a pixel processed by this image shader.
    */

   protected float outputR = 1.0f;

   /**
    * Field that will store the output green component of a pixel processed by this image shader.
    */

   protected float outputG = 1.0f;

   /**
    * Field that will store the output blue component of a pixel processed by this image shader.
    */

   protected float outputB = 1.0f;

   /**
    * Field that will store the output alpha component of a pixel processed by this image shader.
    */

   protected float outputA = 1.0f;

   /**
    * Field containing the x-coordinate of the first vertex used for pixel shading interpolation in screen coordinate space.
    */

   protected float coordinateX1;

   /**
    * Field containing the y-coordinate of the first vertex used for pixel shading interpolation in screen coordinate space.
    */

   protected float coordinateY1;

   /**
    * Field containing the z-coordinate of the first vertex used for pixel shading interpolation in creen coordinate space.
    */

   protected float coordinateZ1;

   /**
    * Field containing the w-coordinate inverse of the first vertex used for pixel shading interpolation in screen coordinate space.
    */

   protected float coordinateW1;

   /**
    * Field containing the x-coordinate of the second vertex used for pixel shading interpolation in screen coordinate space.
    */

   protected float coordinateX2;

   /**
    * Field containing the y-coordinate of the second vertex used for pixel shading interpolation in screen coordinate space.
    */

   protected float coordinateY2;

   /**
    * Field containing the z-coordinate of the second vertex used for pixel shading interpolation in screen coordinate space.
    */

   protected float coordinateZ2;

   /**
    * Field containing the w-coordinate inverse of the second vertex used for pixel shading interpolation in screen coordinate space.
    */

   protected float coordinateW2;

   /**
    * Field containing the x-coordinate of the third vertex used for pixel shading interpolation in screen coordinate space.
    */

   protected float coordinateX3;

   /**
    * Field containing the y-coordinate of the third vertex used for pixel shading interpolation in screen coordinate space.
    */

   protected float coordinateY3;

   /**
    * Field containing the z-coordinate of the third vertex used for pixel shading interpolation in screen coordinate space.
    */

   protected float coordinateZ3;

   /**
    * Field containing the w-coordinate inverse of the third vertex used for pixel shading interpolation in screen coordinate space.
    */

   protected float coordinateW3;

   /**
    * Field containing the x-coordinate of the first vertex used for pixel shading interpolation in object coordinate space.
    */

   protected float objectX1;

   /**
    * Field containing the y-coordinate of the first vertex used for pixel shading interpolation in object coordinate space.
    */

   protected float objectY1;

   /**
    * Field containing the z-coordinate of the first vertex used for pixel shading interpolation in object coordinate space.
    */

   protected float objectZ1;

   /**
    * Field containing the x-coordinate of the second vertex used for pixel shading interpolation in object coordinate space.
    */

   protected float objectX2;

   /**
    * Field containing the y-coordinate of the second vertex used for pixel shading interpolation in object coordinate space.
    */

   protected float objectY2;

   /**
    * Field containing the z-coordinate of the second vertex used for pixel shading interpolation in object coordinate space.
    */

   protected float objectZ2;

   /**
    * Field containing the x-coordinate of the third vertex used for pixel shading interpolation in object coordinate space.
    */

   protected float objectX3;

   /**
    * Field containing the y-coordinate of the third vertex used for pixel shading interpolation in object coordinate space.
    */

   protected float objectY3;

   /**
    * Field containing the z-coordinate of the third vertex used for pixel shading interpolation in object coordinate space.
    */

   protected float objectZ3;

   /**
    * Field containing the x-coordinate of the first vertex u1 tangent used for pixel shading interpolation in object coordinate space.
    */

   protected float tangentU1X1;

   /**
    * Field containing the y-coordinate of the first vertex u1 tangent used for pixel shading interpolation in object coordinate space.
    */

   protected float tangentU1Y1;

   /**
    * Field containing the z-coordinate of the first vertex u1 tangent used for pixel shading interpolation in object coordinate space.
    */

   protected float tangentU1Z1;

   /**
    * Field containing the x-coordinate of the second vertex u1 tangent used for pixel shading interpolation in object coordinate space.
    */

   protected float tangentU1X2;

   /**
    * Field containing the y-coordinate of the second vertex u1 tangent used for pixel shading interpolation in object coordinate space.
    */

   protected float tangentU1Y2;

   /**
    * Field containing the z-coordinate of the second vertex u1 tangent used for pixel shading interpolation in object coordinate space.
    */

   protected float tangentU1Z2;

   /**
    * Field containing the x-coordinate of the third vertex u1 tangent used for pixel shading interpolation in object coordinate space.
    */

   protected float tangentU1X3;

   /**
    * Field containing the y-coordinate of the third vertex u1 tangent used for pixel shading interpolation in object coordinate space.
    */

   protected float tangentU1Y3;

   /**
    * Field containing the z-coordinate of the third vertex u1 tangent used for pixel shading interpolation in object coordinate space.
    */

   protected float tangentU1Z3;

   /**
    * Field containing the x-coordinate of the first vertex u2 tangent used for pixel shading interpolation in object coordinate space.
    */

   protected float tangentU2X1;

   /**
    * Field containing the y-coordinate of the first vertex u2 tangent used for pixel shading interpolation in object coordinate space.
    */

   protected float tangentU2Y1;

   /**
    * Field containing the z-coordinate of the first vertex u2 tangent used for pixel shading interpolation in object coordinate space.
    */

   protected float tangentU2Z1;

   /**
    * Field containing the x-coordinate of the second vertex u2 tangent used for pixel shading interpolation in object coordinate space.
    */

   protected float tangentU2X2;

   /**
    * Field containing the y-coordinate of the second vertex u2 tangent used for pixel shading interpolation in object coordinate space.
    */

   protected float tangentU2Y2;

   /**
    * Field containing the z-coordinate of the second vertex u2 tangent used for pixel shading interpolation in object coordinate space.
    */

   protected float tangentU2Z2;

   /**
    * Field containing the x-coordinate of the third vertex u2 tangent used for pixel shading interpolation in object coordinate space.
    */

   protected float tangentU2X3;

   /**
    * Field containing the y-coordinate of the third vertex u2 tangent used for pixel shading interpolation in object coordinate space.
    */

   protected float tangentU2Y3;

   /**
    * Field containing the z-coordinate of the third vertex u2 tangent used for pixel shading interpolation in object coordinate space.
    */

   protected float tangentU2Z3;

   /**
    * Field containing the x-coordinate of the first vertex normal used for pixel shading interpolation in object coordinate space.
    */

   protected float normalX1;

   /**
    * Field containing the y-coordinate of the first vertex normal used for pixel shading interpolation in object coordinate space.
    */

   protected float normalY1;

   /**
    * Field containing the z-coordinate of the first vertex normal used for pixel shading interpolation in object coordinate space.
    */

   protected float normalZ1;

   /**
    * Field containing the x-coordinate of the second vertex normal used for pixel shading interpolation in object coordinate space.
    */

   protected float normalX2;

   /**
    * Field containing the y-coordinate of the second vertex normal used for pixel shading interpolation in object coordinate space.
    */

   protected float normalY2;

   /**
    * Field containing the z-coordinate of the second vertex normal used for pixel shading interpolation in object coordinate space.
    */

   protected float normalZ2;

   /**
    * Field containing the x-coordinate of the third vertex normal used for pixel shading interpolation in object coordinate space.
    */

   protected float normalX3;

   /**
    * Field containing the y-coordinate of the third vertex normal used for pixel shading interpolation in object coordinate space.
    */

   protected float normalY3;

   /**
    * Field containing the z-coordinate of the third vertex normal used for pixel shading interpolation in object coordinate space.
    */

   protected float normalZ3;

   /**
    * Field containing the perspective corrected u-coordinate of the first vertex texel used for pixel shading interpolation in texture coordinate space.
    */

   protected float texelU1;

   /**
    * Field containing the perspective corrected v-coordinate of the first vertex texel used for pixel shading interpolation in texture coordinate space.
    */

   protected float texelV1;

   /**
    * Field containing the perspective corrected u-coordinate of the second vertex texel used for pixel shading interpolation in texture coordinate space.
    */

   protected float texelU2;

   /**
    * Field containing the perspective corrected v-coordinate of the second vertex texel used for pixel shading interpolation in texture coordinate space.
    */

   protected float texelV2;

   /**
    * Field containing the perspective corrected u-coordinate of the third vertex texel used for pixel shading interpolation in texture coordinate space.
    */

   protected float texelU3;

   /**
    * Field containing the perspective corrected v-coordinate of the third vertex texel used for pixel shading interpolation in texture coordinate space.
    */

   protected float texelV3;

   /**
    * Field containing the x-coordinate of the receptor point used for lighting calculations in object coordinate space.
    */

   protected float eyeX;

   /**
    * Field containing the y-coordinate of the receptor point used for lighting calculations in object coordinate space.
    */

   protected float eyeY;

   /**
    * Field containing the z-coordinate of the receptor point used for lighting calculations in object coordinate space.
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

   protected float fresnelCoefficientR;

   /**
    * Green wavelength fresnel reflectivity coefficient at an angle of incidence of zero under illumination in the rgba model of the material of the current primitive
    * whose pixels are processed by this image shader.
    */

   protected float fresnelCoefficientG;

   /**
    * Blue wavelength fresnel reflectivity coefficient at an angle of incidence of zero under illumination in the rgba model of the material of the current primitive
    * whose pixels are processed by this image shader.
    */

   protected float fresnelCoefficientB;

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

   protected float microfacetRoughness;

   protected ToolkitI toolkit;

   /**
    * Lights used for lighting calculations with coordinate information set in object coordinate space.
    */

   protected Light[] lightArray = new Light[864];

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

   protected VertexReceptor receptor;

   /**
    * Constructs a image shader associated with the receptor object argument.
    *
    * @param receptor the receptor object associated with this image shader.
    */

   protected ImageShader(VertexReceptor receptor) { this.receptor = receptor; }

   /**
    * Updates this image shader with new data corresponding to the visualizer argument.
    *
    * @param notifier the render notifier containing geometric and rendering data of the current model being rendered.
    */

   protected void update(RenderNotifier notifier) {

      receptor.initializeImageShaderEye(notifier);
      receptor.initializeImageShaderMaterial(notifier);
      receptor.initializeImageShaderLightArray(notifier);
      receptor.initializeImageShaderTextureArray(notifier);
      receptor.initializeImageShaderToolkit(notifier);

      receptor.initializeImageShaderObjectCoordinates(notifier);
      receptor.initializeImageShaderWorldCoordinates(notifier);

   }

   /**
    * Calculates and sets the unit view vector of this light at the point determined by the floating point arguments.
    *
    * @param surfaceElementX the x-coordinate relative to some object coordinate system of an infinitesimal element of a surface.
    * @param surfaceElementY the y-coordinate relative to some object coordinate system of an infinitesimal element of a surface.
    * @param surfaceElementZ the z-coordinate relative to some object coordinate system of an infinitesimal element of a surface.
    * @param eyeX the x-coordinate of the view point relative to some object coordinate system.
    * @param eyeY the y-coordinate of the view point relative to some object coordinate system.
    * @param eyeZ the z-coordinate of the view point relative to some object coordinate system.
    * @param viewCoordinates the array to store the coordinates of the unit vector defining the view vector of this light.
    */

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

   protected void computeReflectionCoordinates(float surfaceElementX, float surfaceElementY, float surfaceElementZ, float normalX, float normalY, float normalZ, float[] reflectionCoordinates) {

      float[] incidenceCoordinates = new float[3];

      computeViewCoordinates(surfaceElementX, surfaceElementY, surfaceElementZ, incidenceCoordinates);

      float dot = incidenceCoordinates[0] * normalX + incidenceCoordinates[1] * normalY + incidenceCoordinates[2] * normalZ;

      reflectionCoordinates[0]  = -incidenceCoordinates[0] + 2.0f * normalX * dot;
      reflectionCoordinates[1]  = -incidenceCoordinates[1] + 2.0f * normalY * dot;
      reflectionCoordinates[2]  = -incidenceCoordinates[2] + 2.0f * normalZ * dot;

      float mag = (float)Math.sqrt(Math.pow(reflectionCoordinates[0], 2) + Math.pow(reflectionCoordinates[1], 2) + Math.pow(reflectionCoordinates[2], 2));

      reflectionCoordinates[0] /= mag;
      reflectionCoordinates[1] /= mag;
      reflectionCoordinates[2] /= mag;
/*

      float alpha = 2 * (normalX * incidenceCoordinates[0] + normalY * incidenceCoordinates[1] + normalZ * incidenceCoordinates[2]);

      reflectionCoordinates[0] = alpha * normalX - incidenceCoordinates[0];
      reflectionCoordinates[1] = alpha * normalY - incidenceCoordinates[1];
      reflectionCoordinates[2] = alpha * normalZ - incidenceCoordinates[2];

      float factor = (float)Math.sqrt(reflectionCoordinates[0] * reflectionCoordinates[0] +
                                      reflectionCoordinates[1] * reflectionCoordinates[1] +
                                      reflectionCoordinates[2] * reflectionCoordinates[2]);

      if(factor != 0.0f) {

         reflectionCoordinates[0] /= factor;
         reflectionCoordinates[1] /= factor;
         reflectionCoordinates[2] /= factor;

      }

      else {

         reflectionCoordinates[0] = 0.0f;
         reflectionCoordinates[1] = 0.0f;
         reflectionCoordinates[2] = 0.0f;

      }
*/
   }

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

   abstract protected boolean set(RenderingAttributes renderingAttributes, float barycentricU, float barycentricV, float barycentricW, float screenX, float screenY, float deviceX, float deviceY, int pixelX, int pixelY);

   /**
    * Computes the position coordinates of the pixel defined by the floating point arguments using barycentric interpolation.
    *
    * @param barycentricU the barycentric u-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param barycentricV the barycentric v-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param barycentricW the barycentric w-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param position the array in which the position coordinates are stored.
    */

   protected void computePosition(float barycentricU, float barycentricV, float barycentricW, float[] position) {

      position[0] = barycentricU * objectX1 + barycentricV * objectX2 + barycentricW * objectX3;
      position[1] = barycentricU * objectY1 + barycentricV * objectY2 + barycentricW * objectY3;
      position[2] = barycentricU * objectZ1 + barycentricV * objectZ2 + barycentricW * objectZ3;

   }

   /**
    * Computes the texel coordinates of the pixel defined by the floating point arguments using barycentric interpolation.
    *
    * @param barycentricU the barycentric u-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param barycentricV the barycentric v-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param barycentricW the barycentric w-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param texel the array in which the texel coordinates are stored.
    */

   protected void computeTexel(float barycentricU, float barycentricV, float barycentricW, float[] texel) {

      float inverseW1 = 1.0f / coordinateW1;
      float inverseW2 = 1.0f / coordinateW2;
      float inverseW3 = 1.0f / coordinateW3;

      float inverseW = barycentricU * inverseW1 + barycentricV * inverseW2 + barycentricW * inverseW3;

      float perspectiveCorrectedTexelU1 = texelU1 / coordinateW1;
      float perspectiveCorrectedTexelV1 = texelV1 / coordinateW1;

      float perspectiveCorrectedTexelU2 = texelU2 / coordinateW2;
      float perspectiveCorrectedTexelV2 = texelV2 / coordinateW2;

      float perspectiveCorrectedTexelU3 = texelU3 / coordinateW3;
      float perspectiveCorrectedTexelV3 = texelV3 / coordinateW3;

      texel[0] = barycentricU * perspectiveCorrectedTexelU1 + barycentricV * perspectiveCorrectedTexelU2 + barycentricW * perspectiveCorrectedTexelU3;
      texel[1] = barycentricU * perspectiveCorrectedTexelV1 + barycentricV * perspectiveCorrectedTexelV2 + barycentricW * perspectiveCorrectedTexelV3;

      texel[0] /= inverseW;
      texel[1] /= inverseW;

   }

   /**
    * Computes the normal vector coordinates of the pixel defined by the floating point arguments using barycentric interpolation.
    *
    * @param barycentricU the barycentric u-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param barycentricV the barycentric v-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param barycentricW the barycentric w-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param normal the array in which the normal vector coordinates are stored.
    */

   protected void computeNormal(float barycentricU, float barycentricV, float barycentricW, float[] normal) {

      float[] texel = new float[2];

      computeTexel(barycentricU, barycentricV, barycentricW, texel);
      
      toolkit.computeNormal(texel, normal);    

/*

      normal[0] = barycentricU * normalX1 + barycentricV * normalX2 + barycentricW * normalX3;
      normal[1] = barycentricU * normalY1 + barycentricV * normalY2 + barycentricW * normalY3;
      normal[2] = barycentricU * normalZ1 + barycentricV * normalZ2 + barycentricW * normalZ3;

      float factor = (float)Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1] + normal[2] * normal[2]);

      normal[0] /= factor;
      normal[1] /= factor;
      normal[2] /= factor;
*/
   }

   /**
    * Computes the tangent u1 vector coordinates of the pixel defined by the floating point arguments using barycentric interpolation.
    *
    * @param barycentricU the barycentric u-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param barycentricV the barycentric v-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param barycentricW the barycentric w-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param tangentU1 the array in which the tangent u1 vector coordinates are stored.
    */

   protected void computeTangentU1(float barycentricU, float barycentricV, float barycentricW, float[] tangentU1) {

      tangentU1[0] = barycentricU * tangentU1X1 + barycentricV * tangentU1X2 + barycentricW * tangentU1X3;
      tangentU1[1] = barycentricU * tangentU1Y1 + barycentricV * tangentU1Y2 + barycentricW * tangentU1Y3;
      tangentU1[2] = barycentricU * tangentU1Z1 + barycentricV * tangentU1Z2 + barycentricW * tangentU1Z3;

   }

   /**
    * Computes the tangent u2 vector coordinates of the pixel defined by the floating point arguments using barycentric interpolation.
    *
    * @param barycentricU the barycentric u-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param barycentricV the barycentric v-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param barycentricW the barycentric w-coordinate of the pixel being processed relative to the current triangle primitive being rendered.
    * @param tangentU2 the array in which the tangent u2 vector coordinates are stored.
    */

   protected void computeTangentU2(float barycentricU, float barycentricV, float barycentricW, float[] tangentU2) {

      tangentU2[0] = barycentricU * tangentU2X1 + barycentricV * tangentU2X2 + barycentricW * tangentU2X3;
      tangentU2[1] = barycentricU * tangentU2Y1 + barycentricV * tangentU2Y2 + barycentricW * tangentU2Y3;
      tangentU2[2] = barycentricU * tangentU2Z1 + barycentricV * tangentU2Z2 + barycentricW * tangentU2Z3;

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
    * Sets the x-coordinate of the first vertex used for pixel shading interpolation in screen coordinate space to the floating point argument.
    *
    * @param coordinateX1 the value that the x-coordinate of the first vertex in screen coordinate space used for pixel shading interpolation is set to.
    */

   protected void setCoordinateX1(float coordinateX1) { this.coordinateX1 = coordinateX1; }

   /**
    * Sets the y-coordinate of the first vertex used for pixel shading interpolation in screen coordinate space to the floating point argument.
    *
    * @param coordinateY1 the value that the y-coordinate of the first vertex in screen coordinate space used for pixel shading interpolation is set to.
    */

   protected void setCoordinateY1(float coordinateY1) { this.coordinateY1 = coordinateY1; }

   /**
    * Sets the z-coordinate of the first vertex used for pixel shading interpolation in screen coordinate space to the floating point argument.
    *
    * @param coordinateZ1 the value that the z-coordinate of the first vertex in screen coordinate space used for pixel shading interpolation is set to.
    */

   protected void setCoordinateZ1(float coordinateZ1) { this.coordinateZ1 = coordinateZ1; }

   /**
    * Sets the w-coordinate of the first vertex used for pixel shading interpolation in screen coordinate space to the floating point argument.
    *
    * @param coordinateW1 the value that the w-coordinate of the first vertex in screen coordinate space used for pixel shading interpolation is set to.
    */

   protected void setCoordinateW1(float coordinateW1) { this.coordinateW1 = coordinateW1; }

   /**
    * Sets the x-coordinate of the second vertex used for pixel shading interpolation in screen coordinate space to the floating point argument.
    *
    * @param coordinateX2 the value that the x-coordinate of the second vertex in screen coordinate space used for pixel shading interpolation is set to.
    */

   protected void setCoordinateX2(float coordinateX2) { this.coordinateX2 = coordinateX2; }

   /**
    * Sets the y-coordinate of the second vertex used for pixel shading interpolation in screen coordinate space to the floating point argument.
    *
    * @param coordinateY2 the value that the y-coordinate of the second vertex in screen coordinate space used for pixel shading interpolation is set to.
    */

   protected void setCoordinateY2(float coordinateY2) { this.coordinateY2 = coordinateY2; }

   /**
    * Sets the z-coordinate of the second vertex used for pixel shading interpolation in screen coordinate space to the floating point argument.
    *
    * @param coordinateZ2 the value that the z-coordinate of the second vertex in screen coordinate space used for pixel shading interpolation is set to.
    */

   protected void setCoordinateZ2(float coordinateZ2) { this.coordinateZ2 = coordinateZ2; }

   /**
    * Sets the w-coordinate of the second vertex used for pixel shading interpolation in screen coordinate space to the floating point argument.
    *
    * @param coordinateW2 the value that the w-coordinate of the second vertex in screen coordinate space used for pixel shading interpolation is set to.
    */

   protected void setCoordinateW2(float coordinateW2) { this.coordinateW2 = coordinateW2; }

   /**
    * Sets the x-coordinate of the third vertex used for pixel shading interpolation in screen coordinate space to the floating point argument.
    *
    * @param coordinateX3 the value that the x-coordinate of the third vertex in screen coordinate space used for pixel shading interpolation is set to.
    */

   protected void setCoordinateX3(float coordinateX3) { this.coordinateX3 = coordinateX3; }

   /**
    * Sets the y-coordinate of the third vertex used for pixel shading interpolation in screen coordinate space to the floating point argument.
    *
    * @param coordinateY3 the value that the y-coordinate of the third vertex in screen coordinate space used for pixel shading interpolation is set to.
    */

   protected void setCoordinateY3(float coordinateY3) { this.coordinateY3 = coordinateY3; }

   /**
    * Sets the z-coordinate of the third vertex used for pixel shading interpolation in screen coordinate space to the floating point argument.
    *
    * @param coordinateZ3 the value that the z-coordinate of the third vertex in screen coordinate space used for pixel shading interpolation is set to.
    */

   protected void setCoordinateZ3(float coordinateZ3) { this.coordinateZ3 = coordinateZ3; }

   /**
    * Sets the w-coordinate of the third vertex used for pixel shading interpolation in screen coordinate space to the floating point argument.
    *
    * @param coordinateW3 the value that the w-coordinate of the third vertex in screen coordinate spaceused for pixel shading interpolation is set to.
    */

   protected void setCoordinateW3(float coordinateW3) { this.coordinateW3 = coordinateW3; }

   /**
    * Sets the x-coordinate of the first vertex used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param objectX1 the value that the x-coordinate of the first vertex in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setObjectX1(float objectX1) { this.objectX1 = objectX1; }

   /**
    * Sets the y-coordinate of the first vertex used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param objectY1 the value that the y-coordinate of the first vertex in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setObjectY1(float objectY1) { this.objectY1 = objectY1; }

   /**
    * Sets the z-coordinate of the first vertex used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param objectZ1 the value that the z-coordinate of the first vertex in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setObjectZ1(float objectZ1) { this.objectZ1 = objectZ1; }

   /**
    * Sets the x-coordinate of the second vertex used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param objectX2 the value that the x-coordinate of the second vertex in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setObjectX2(float objectX2) { this.objectX2 = objectX2; }

   /**
    * Sets the y-coordinate of the second vertex used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param objectY2 the value that the y-coordinate of the second vertex in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setObjectY2(float objectY2) { this.objectY2 = objectY2; }

   /**
    * Sets the z-coordinate of the second vertex used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param objectZ2 the value that the z-coordinate of the second vertex in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setObjectZ2(float objectZ2) { this.objectZ2 = objectZ2; }

   /**
    * Sets the x-coordinate of the third vertex used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param objectX3 the value that the x-coordinate of the third vertex in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setObjectX3(float objectX3) { this.objectX3 = objectX3; }

   /**
    * Sets the y-coordinate of the third vertex used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param objectY3 the value that the y-coordinate of the third vertex in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setObjectY3(float objectY3) { this.objectY3 = objectY3; }

   /**
    * Sets the z-coordinate of the third vertex used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param objectZ3 the value that the z-coordinate of the third vertex in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setObjectZ3(float objectZ3) { this.objectZ3 = objectZ3; }

   /**
    * Sets the x-coordinate of the first vertex u1 tangent used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param tangentU1X1 the value that the x-coordinate of the first vertex u1 tangent in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTangentU1X1(float tangentU1X1) { this.tangentU1X1 = tangentU1X1; }

   /**
    * Sets the y-coordinate of the first vertex u1 tangent used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param tangentU1Y1 the value that the y-coordinate of the first vertex u1 tangent in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTangentU1Y1(float tangentU1Y1) { this.tangentU1Y1 = tangentU1Y1; }

   /**
    * Sets the z-coordinate of the first vertex u1 tangent used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param tangentU1Z1 the value that the z-coordinate of the first vertex u1 tangent in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTangentU1Z1(float tangentU1Z1) { this.tangentU1Z1 = tangentU1Z1; }

   /**
    * Sets the x-coordinate of the second vertex u1 tangent used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param tangentU1X2 the value that the x-coordinate of the second vertex u1 tangent in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTangentU1X2(float tangentU1X2) { this.tangentU1X2 = tangentU1X2; }

   /**
    * Sets the y-coordinate of the second vertex u1 tangent used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param tangentU1Y2 the value that the y-coordinate of the second vertex u1 tangent in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTangentU1Y2(float tangentU1Y2) { this.tangentU1Y2 = tangentU1Y2; }

   /**
    * Sets the z-coordinate of the second vertex u1 tangent used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param tangentU1Z2 the value that the z-coordinate of the second vertex u1 tangent in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTangentU1Z2(float tangentU1Z2) { this.tangentU1Z2 = tangentU1Z2; }

   /**
    * Sets the x-coordinate of the third vertex u1 tangent used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param tangentU1X3 the value that the x-coordinate of the third vertex u1 tangent in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTangentU1X3(float tangentU1X3) { this.tangentU1X3 = tangentU1X3; }

   /**
    * Sets the y-coordinate of the third vertex u1 tangent used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param tangentU1Y3 the value that the y-coordinate of the third vertex u1 tangent in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTangentU1Y3(float tangentU1Y3) { this.tangentU1Y3 = tangentU1Y3; }

   /**
    * Sets the z-coordinate of the third vertex u1 tangent used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param tangentU1Z3 the value that the z-coordinate of the third vertex u1 tangent in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTangentU1Z3(float tangentU1Z3) { this.tangentU1Z3 = tangentU1Z3; }

   /**
    * Sets the x-coordinate of the first vertex u2 tangent used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param tangentU2X1 the value that the x-coordinate of the first vertex u2 tangent in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTangentU2X1(float tangentU2X1) { this.tangentU2X1 = tangentU2X1; }

   /**
    * Sets the y-coordinate of the first vertex u2 tangent used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param tangentU2Y1 the value that the y-coordinate of the first vertex u2 tangent in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTangentU2Y1(float tangentU2Y1) { this.tangentU2Y1 = tangentU2Y1; }

   /**
    * Sets the z-coordinate of the first vertex u2 tangent used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param tangentU2Z1 the value that the z-coordinate of the first vertex u2 tangent in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTangentU2Z1(float tangentU2Z1) { this.tangentU2Z1 = tangentU2Z1; }

   /**
    * Sets the x-coordinate of the second vertex u2 tangent used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param tangentU2X2 the value that the x-coordinate of the second vertex u2 tangent in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTangentU2X2(float tangentU2X2) { this.tangentU2X2 = tangentU2X2; }

   /**
    * Sets the y-coordinate of the second vertex u2 tangent used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param tangentU2Y2 the value that the y-coordinate of the second vertex u2 tangent in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTangentU2Y2(float tangentU2Y2) { this.tangentU2Y2 = tangentU2Y2; }

   /**
    * Sets the z-coordinate of the second vertex u2 tangent used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param tangentU2Z2 the value that the z-coordinate of the second vertex u2 tangent in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTangentU2Z2(float tangentU2Z2) { this.tangentU2Z2 = tangentU2Z2; }

   /**
    * Sets the x-coordinate of the third vertex u2 tangent used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param tangentU2X3 the value that the x-coordinate of the third vertex u2 tangent in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTangentU2X3(float tangentU2X3) { this.tangentU2X3 = tangentU2X3; }

   /**
    * Sets the y-coordinate of the third vertex u2 tangent used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param tangentU2Y3 the value that the y-coordinate of the third vertex u2 tangent in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTangentU2Y3(float tangentU2Y3) { this.tangentU2Y3 = tangentU2Y3; }

   /**
    * Sets the z-coordinate of the third vertex u2 tangent used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param tangentU2Z3 the value that the z-coordinate of the third vertex u2 tangent in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTangentU2Z3(float tangentU2Z3) { this.tangentU2Z3 = tangentU2Z3; }

   /**
    * Sets the x-coordinate of the first vertex normal used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param normalX1 the value that the x-coordinate of the first vertex normal in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setNormalX1(float normalX1) { this.normalX1 = normalX1; }

   /**
    * Sets the y-coordinate of the first vertex normal used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param normalY1 the value that the y-coordinate of the first vertex normal in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setNormalY1(float normalY1) { this.normalY1 = normalY1; }

   /**
    * Sets the z-coordinate of the first vertex normal used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param normalZ1 the value that the z-coordinate of the first vertex normal in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setNormalZ1(float normalZ1) { this.normalZ1 = normalZ1; }

   /**
    * Sets the x-coordinate of the second vertex normal used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param normalX2 the value that the x-coordinate of the second vertex normal in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setNormalX2(float normalX2) { this.normalX2 = normalX2; }

   /**
    * Sets the y-coordinate of the second vertex normal used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param normalY2 the value that the y-coordinate of the second vertex normal in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setNormalY2(float normalY2) { this.normalY2 = normalY2; }

   /**
    * Sets the z-coordinate of the second vertex normal used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param normalZ2 the value that the z-coordinate of the second vertex normal in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setNormalZ2(float normalZ2) { this.normalZ2 = normalZ2; }

   /**
    * Sets the x-coordinate of the third vertex normal used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param normalX3 the value that the x-coordinate of the third vertex normal in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setNormalX3(float normalX3) { this.normalX3 = normalX3; }

   /**
    * Sets the y-coordinate of the third vertex normal used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param normalY3 the value that the y-coordinate of the third vertex normal in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setNormalY3(float normalY3) { this.normalY3 = normalY3; }

   /**
    * Sets the z-coordinate of the third vertex normal used for pixel shading interpolation in object coordinate space to the floating point argument.
    *
    * @param normalZ3 the value that the z-coordinate of the third vertex normal in object coordinate space used for pixel shading interpolation is set to.
    */

   protected void setNormalZ3(float normalZ3) { this.normalZ3 = normalZ3; }

   /**
    * Sets the u-coordinate of the first vertex texel used for pixel shading interpolation in texture coordinate space to the floating point argument.
    *
    * @param texelU1 the value that the u-coordinate of the first texel in texture coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTexelU1(float texelU1) { this.texelU1 = texelU1; }

   /**
    * Sets the v-coordinate of the first vertex texel used for pixel shading interpolation in texture coordinate space to the floating point argument.
    *
    * @param texelV1 the value that the v-coordinate of the first texel in texture coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTexelV1(float texelV1) { this.texelV1 = texelV1; }

   /**
    * Sets the u-coordinate of the second vertex texel used for pixel shading interpolation in texture coordinate space to the floating point argument.
    *
    * @param texelU2 the value that the u-coordinate of the second texel in texture coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTexelU2(float texelU2) { this.texelU2 = texelU2; }

   /**
    * Sets the v-coordinate of the second vertex texel used for pixel shading interpolation in texture coordinate space to the floating point argument.
    *
    * @param texelV2 the value that the v-coordinate of the second texel in texture coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTexelV2(float texelV2) { this.texelV2 = texelV2; }

   /**
    * Sets the u-coordinate of the third vertex texel used for pixel shading interpolation in texture coordinate space to the floating point argument.
    *
    * @param texelU3 the value that the u-coordinate of the third texel in texture coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTexelU3(float texelU3) { this.texelU3 = texelU3; }

   /**
    * Sets the v-coordinate of the third vertex texel used for pixel shading interpolation in texture coordinate space to the floating point argument.
    *
    * @param texelV3 the value that the v-coordinate of the third texel in texture coordinate space used for pixel shading interpolation is set to.
    */

   protected void setTexelV3(float texelV3) { this.texelV3 = texelV3; }

   /**
    * Sets the x-coordinate of the receptor point used for lighting calculations in object coordinate space to the floating point argument.
    *
    * @param eyeX the value that the x-coordinate of the receptor point of the receptor in object coordinate space used for lighting calculations is set to.
    */

   protected void setEyeX(float eyeX) { this.eyeX = eyeX; }

   /**
    * Sets the y-coordinate of the receptor point used for lighting calculations in object coordinate space to the floating point argument.
    *
    * @param eyeY the value that the y-coordinate of the receptor point in object coordinate space used for lighting calculations is set to.
    */

   protected void setEyeY(float eyeY) { this.eyeY = eyeY; }

   /**
    * Sets the z-coordinate of the receptor point used for lighting calculations in object coordinate space to the floating point argument.
    *
    * @param eyeZ the value that the z-coordinate of the receptor point in object coordinate space used for lighting calculations is set to.
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

   protected void setToolkit(ToolkitI toolkit) { this.toolkit = toolkit; }

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
    * Sets the receptor associated with this image shader to the receptor argument.
    *
    * @param receptor the receptor object that the receptor associated with this image shader is set to.
    */

   protected void setVertexReceptor(VertexReceptor receptor) { this.receptor = receptor; }

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