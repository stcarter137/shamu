package shamu.media.render;

/** <p>Class whose instances contain complete information about a viewing system that renders 3D models into a frame buffer. In complying with the
 *  observer pattern, camera objects must be registered to a notifier before rendering into the frame buffer can occur.</p>
 *
 *  <p>The view plane of a camera instance is always positioned such that the viewing direction is orthogonal to the camera plane and centered relative
 *  to the view plane. The default projection transform is a perspective projection. The position and direction of the camera frustum relative to the
 *  world coordinate system can be changed by setting a new transformation that transforms world coordinates to camera coordinates. In general, the
 *  transformation that transforms the world coordinate system to the camera coordinate system is a translational transform that translates the origin
 *  of the camera coordinate systen to the origin of the world coordinate system followed by a rotational transform that aligns the "look-at" vector
 *  of the camera coordinate system to the (+)z-axis of the world coordinate system and aligns the "up" vector  of the camera coordinate system to the (-)y-axis
 *  of the world coordinate system.</p>
 *
 *  <p>The default origin of the camera coordinate systen is the origin of the world coordinate system, the default "look-at" vector of the camera coordinate
 *  system is the (+)z-axis, and the default "up" vector of the camera coordinates system is the (-)y-axis.</p>
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class VertexReceptor extends Receptor implements RenderingConstantsI, ModelingConstantsI {

   /**
    * Transformation shader used to transform vertices into homogeneous screen space coordinates for rendering into the color buffer associated with this camera.
    */

   protected TransformationShader transformationShader;

   /**
    * Image shader used to output colored pixels for rendering into the color buffer associated with this camera.
    */

   protected ImageShader imageShader;

   /**
    * Constructs a camera associated with the color and depth buffers defined by the integer arguments, with the default world-to-camera coordinate system transform, which is the
    * identity transform, and the default projection transform, which is a perspective transform.
    *
    * @param pixelFormat the pixel format for the color buffer associated with this camera. Must be shamu.image.io.ImageIO.FORMAT_PIXEL_INTERLEAVED, shamu.image.io.ImageIO.FORMAT_PIXEL_BANDED, or shamu.image.io.ImageIO.FORMAT_PIXEL_SINGLEPACKED.
    * @param bufferWidth the width of the depth and color buffers associated with this camera.
    * @param bufferHeight the height of the depth and color buffers associated with this camera.
    */

   public VertexReceptor(int pixelFormat, int bufferWidth, int bufferHeight) { super(pixelFormat, bufferWidth, bufferHeight);  }

   /**
    * Updates the projection matrix camera to the matrix defined by the near plane, far plane, field of camera, and projection type fields associated with
    * this camera.
    */

   public void updateProjectionMatrix() {

      Matrix4D updatedMatrix = new Matrix4D();

      if(projectionType == PERSPECTIVE_PROJECTION)
         updatedMatrix = Matrix4D.createPerspectiveProjection(nearPlane, farPlane, fieldOfView, (int)(viewportWidth / viewportHeight));

      else if(projectionType == ORTHOGRAPHIC_PROJECTION)
         updatedMatrix = Matrix4D.createOrthographicProjection(nearPlane, farPlane, fieldOfView, (int)(viewportWidth / viewportHeight));

      for(int i = 0; i < 4; i++)
         for(int j = 0; j < 4; j++)
            projectionMatrix.setElement(i, j, updatedMatrix.getElement(i, j));

   }

   /**
    * Initializes the vertex transformation field of the transformation shader associated with this camera for the vertex transformation stage of the 3D rendering pipeline.
    *
    * @param RenderNotifier the notifier that this camera is registered to.
    */

   protected void initializeTransformationShaderMatrix(RenderNotifier notifier) {

      Matrix4D currentMatrix = notifier.getCurrentMatrix();

      int focusBlurDimension = notifier.getFocusBlurDimension();
      int motionBlurDimension = notifier.getMotionBlurDimension();

      Matrix4D focusMatrix = new Matrix4D();

      for(int i = 0; i < 4; i++)
         for(int j = 0; j < 4; j++)
            focusMatrix.setElement(i, j, worldToViewMatrix.getElement(i, j));

      Matrix4D motionMatrix = null;

      if(focusBlurDimension > 1) {

         float aperatureLength = focalLength / fstop;
         float aperatureAngle = 2.0f * (float)Math.atan(1.0f / (2.0f * fstop));

         int focusIndexX = notifier.getFocusOffsetIndexX();
         int focusIndexY = notifier.getFocusOffsetIndexY();

         float jitterX = focusIndexX * aperatureLength / (focusBlurDimension - 1);
         float jitterY = focusIndexY * aperatureLength / (focusBlurDimension - 1);

         Quaternion quaternion = new Quaternion();
         quaternion.set(0.0f, 0.0f, focalLength, -jitterX, -jitterY, focalLength);

         focusMatrix.multiply(new Matrix4D(quaternion, jitterX, jitterY, 0));

      }

      if(motionBlurDimension > 1) {

         int motionIndex = notifier.getMotionOffsetIndex();

         MotionAttributes motionAttributes = notifier.getCurrentMotionAttributes();

         float motionDirectionX = motionAttributes.getMotionDirectionX();
         float motionDirectionY = motionAttributes.getMotionDirectionY();
         float motionDirectionZ = motionAttributes.getMotionDirectionZ();

         float motionAxisX = motionAttributes.getMotionAxisX();
         float motionAxisY = motionAttributes.getMotionAxisY();
         float motionAxisZ = motionAttributes.getMotionAxisZ();

         float linearSpeed = motionAttributes.getLinearSpeed();
         float angularSpeed = motionAttributes.getAngularSpeed();
         float time = motionIndex * shutterSpeed / motionBlurDimension;

         float linearDisplacementX = time * linearSpeed * motionDirectionX;
         float linearDisplacementY = time * linearSpeed * motionDirectionY;
         float linearDisplacementZ = time * linearSpeed * motionDirectionZ;

         float angularDisplacement = time * angularSpeed;

         Matrix4D linearMotionMatrix = new Matrix4D(new Matrix3D(), linearDisplacementX, linearDisplacementY, linearDisplacementZ);
         Matrix4D angularMotionMatrix = new Matrix4D(new Quaternion(motionAxisX, motionAxisY, motionAxisZ, angularDisplacement), 0.0f, 0.0f, 0.0f);

         motionMatrix = angularMotionMatrix;

         motionMatrix.multiply(currentMatrix);
         motionMatrix.multiply(linearMotionMatrix);

      }

      else
         motionMatrix = currentMatrix;

      Matrix4D objectToScreenMatrix = new Matrix4D();

      objectToScreenMatrix.multiply(focusMatrix, motionMatrix);
      objectToScreenMatrix.multiply(projectionMatrix);

      transformationShader.setObjectToScreenMatrix(objectToScreenMatrix);

   }

   /**
    * Initializes the light array of the image shader associated with this camera for the pixel setting stage of the 3D rendering pipeline.
    *
    * @param RenderNotifier the notifier that this camera is registered to.
    */

   protected void initializeImageShaderLightArray(RenderNotifier notifier) {

      Matrix4D worldToObjectMatrix = new Matrix4D();
      Matrix4D currentMatrix = notifier.getCurrentMatrix();

      currentMatrix.invert(worldToObjectMatrix);

      for(int i = 0; i < 16; i++) {

		 Light nextLight = notifier.getLight(i);

		 if(nextLight != null) {

		    Light localLight = (Light)nextLight.clone();
            localLight.set(worldToObjectMatrix);

            imageShader.setLight(localLight, i);

         }

      }

   }

   /**
    * Initializes the texture map array field of the image shader associated with this camera for the pixel setting stage of the 3D rendering pipeline.
    *
    * @param RenderNotifier the notifier that this camera is registered to.
    */

   protected void initializeImageShaderTextureArray(RenderNotifier notifier) {

	  TextureMap[] currentTextureMapArray = notifier.getCurrentTextureMapArray();
	  TextureAttributes[] currentTextureAttributesArray = notifier.getCurrentTextureAttributesArray();

	  for(int i = 0; i < 8; i++) {

         imageShader.setTextureMap(currentTextureMapArray[i], i);
         imageShader.setTextureAttributes(currentTextureAttributesArray[i], i);

      }

   }

   /**
    * Initializes the eye position coordinate fields of the image shader associated with this camera for the pixel setting stage of the 3D rendering pipeline.
    *
    * @param RenderNotifier the notifier that this camera is registered to.
    */

   protected void initializeImageShaderEye(RenderNotifier notifier) {

	  Matrix4D viewToObjectMatrix = new Matrix4D();
	  Matrix4D objectToViewMatrix = new Matrix4D();

	  objectToViewMatrix.multiply(worldToViewMatrix, notifier.getCurrentMatrix());
	  objectToViewMatrix.invert(viewToObjectMatrix);

	  imageShader.setEyeX(viewToObjectMatrix.getElement(0, 3));
	  imageShader.setEyeY(viewToObjectMatrix.getElement(1, 3));
	  imageShader.setEyeZ(viewToObjectMatrix.getElement(2, 3));

   }

   /**
    * Initializes the material fields of the image shader associated with this camera for the pixel setting stage of the 3D rendering pipeline.
    *
    * @param RenderNotifier the notifier that this camera is registered to.
    */

   protected void initializeImageShaderMaterial(RenderNotifier notifier) {

	  imageShader.setDiffuseCoefficientR(notifier.getCurrentDiffuseCoefficientR());
	  imageShader.setDiffuseCoefficientG(notifier.getCurrentDiffuseCoefficientG());
	  imageShader.setDiffuseCoefficientB(notifier.getCurrentDiffuseCoefficientB());

	  imageShader.setSpecularCoefficientR(notifier.getCurrentSpecularCoefficientR());
	  imageShader.setSpecularCoefficientG(notifier.getCurrentSpecularCoefficientG());
	  imageShader.setSpecularCoefficientB(notifier.getCurrentSpecularCoefficientB());

	  imageShader.setEmissiveCoefficientR(notifier.getCurrentEmissiveCoefficientR());
	  imageShader.setEmissiveCoefficientG(notifier.getCurrentEmissiveCoefficientG());
	  imageShader.setEmissiveCoefficientB(notifier.getCurrentEmissiveCoefficientB());

	  imageShader.setFresnelCoefficientR(notifier.getCurrentFresnelCoefficientR());
	  imageShader.setFresnelCoefficientG(notifier.getCurrentFresnelCoefficientG());
	  imageShader.setFresnelCoefficientB(notifier.getCurrentFresnelCoefficientB());

	  imageShader.setDiffuseReflectivityR(notifier.getCurrentDiffuseReflectivityR());
	  imageShader.setDiffuseReflectivityG(notifier.getCurrentDiffuseReflectivityG());
	  imageShader.setDiffuseReflectivityB(notifier.getCurrentDiffuseReflectivityB());

	  imageShader.setShininess(notifier.getCurrentShininess());
	  imageShader.setMicrofacetRoughness(notifier.getCurrentMicrofacetRoughness());

   }

   /**
    * Initializes the object coordinate fields of the image shader associated with this camera for the pixel setting stage of the 3D rendering pipeline.
    *
    * @param RenderNotifier the notifier that this camera is registered to.
    */

   protected void initializeImageShaderObjectCoordinates(RenderNotifier notifier) {

      Mesh currentMesh = (Mesh)notifier.getCurrentGeometry();

      int index = notifier.getCurrentPrimitiveIndex();

      float[][] verticesAddressArray = new float[1][];
      float[][] tangentsU1AddressArray = new float[1][];
      float[][] tangentsU2AddressArray = new float[1][];
      float[][] normalsAddressArray = new float[1][];
      float[][] texelsAddressArray = new float[1][];
      int[][] indicesAddressArray = new int[1][];

      currentMesh.getVerticesAddress(verticesAddressArray);
      currentMesh.getTangentsU1Address(tangentsU1AddressArray);
      currentMesh.getTangentsU2Address(tangentsU2AddressArray);
      currentMesh.getNormalsAddress(normalsAddressArray);
      currentMesh.getTexelsAddress(texelsAddressArray);
      currentMesh.getIndexedFaceVerticesAddress(indicesAddressArray);

      int vertexIndex1 = SPACE_POINT_STRIDE  * (indicesAddressArray[0])[index + 0];
      int vertexIndex2 = SPACE_POINT_STRIDE  * (indicesAddressArray[0])[index + 1];
      int vertexIndex3 = SPACE_POINT_STRIDE  * (indicesAddressArray[0])[index + 2];

      int tangentU1Index1 = Mesh.TANGENT_STRIDE * (indicesAddressArray[0])[index + 0];
      int tangentU1Index2 = Mesh.TANGENT_STRIDE * (indicesAddressArray[0])[index + 1];
      int tangentU1Index3 = Mesh.TANGENT_STRIDE * (indicesAddressArray[0])[index + 2];

      int tangentU2Index1 = Mesh.TANGENT_STRIDE * (indicesAddressArray[0])[index + 0];
      int tangentU2Index2 = Mesh.TANGENT_STRIDE * (indicesAddressArray[0])[index + 1];
      int tangentU2Index3 = Mesh.TANGENT_STRIDE * (indicesAddressArray[0])[index + 2];

      int normalIndex1 = Mesh.NORMAL_STRIDE * (indicesAddressArray[0])[index + 0];
      int normalIndex2 = Mesh.NORMAL_STRIDE * (indicesAddressArray[0])[index + 1];
      int normalIndex3 = Mesh.NORMAL_STRIDE * (indicesAddressArray[0])[index + 2];

      int texelIndex1 = Mesh.TEXEL_STRIDE * (indicesAddressArray[0])[index + 0];
      int texelIndex2 = Mesh.TEXEL_STRIDE * (indicesAddressArray[0])[index + 1];
      int texelIndex3 = Mesh.TEXEL_STRIDE * (indicesAddressArray[0])[index + 2];

	  imageShader.setObjectX1(verticesAddressArray[0][vertexIndex1++]);
	  imageShader.setObjectY1(verticesAddressArray[0][vertexIndex1++]);
	  imageShader.setObjectZ1(verticesAddressArray[0][vertexIndex1++]);

	  imageShader.setObjectX2(verticesAddressArray[0][vertexIndex2++]);
	  imageShader.setObjectY2(verticesAddressArray[0][vertexIndex2++]);
	  imageShader.setObjectZ2(verticesAddressArray[0][vertexIndex2++]);

	  imageShader.setObjectX3(verticesAddressArray[0][vertexIndex3++]);
	  imageShader.setObjectY3(verticesAddressArray[0][vertexIndex3++]);
	  imageShader.setObjectZ3(verticesAddressArray[0][vertexIndex3++]);

	  imageShader.setTangentU1X1(tangentsU1AddressArray[0][tangentU1Index1++]);
	  imageShader.setTangentU1Y1(tangentsU1AddressArray[0][tangentU1Index1++]);
	  imageShader.setTangentU1Z1(tangentsU1AddressArray[0][tangentU1Index1++]);

	  imageShader.setTangentU1X2(tangentsU1AddressArray[0][tangentU1Index2++]);
	  imageShader.setTangentU1Y2(tangentsU1AddressArray[0][tangentU1Index2++]);
	  imageShader.setTangentU1Z2(tangentsU1AddressArray[0][tangentU1Index2++]);

	  imageShader.setTangentU1X3(tangentsU1AddressArray[0][tangentU1Index3++]);
	  imageShader.setTangentU1Y3(tangentsU1AddressArray[0][tangentU1Index3++]);
	  imageShader.setTangentU1Z3(tangentsU1AddressArray[0][tangentU1Index3++]);

	  imageShader.setTangentU2X1(tangentsU2AddressArray[0][tangentU2Index1++]);
	  imageShader.setTangentU2Y1(tangentsU2AddressArray[0][tangentU2Index1++]);
	  imageShader.setTangentU2Z1(tangentsU2AddressArray[0][tangentU2Index1++]);

	  imageShader.setTangentU2X2(tangentsU2AddressArray[0][tangentU2Index2++]);
	  imageShader.setTangentU2Y2(tangentsU2AddressArray[0][tangentU2Index2++]);
	  imageShader.setTangentU2Z2(tangentsU2AddressArray[0][tangentU2Index2++]);

	  imageShader.setTangentU2X3(tangentsU2AddressArray[0][tangentU2Index3++]);
	  imageShader.setTangentU2Y3(tangentsU2AddressArray[0][tangentU2Index3++]);
	  imageShader.setTangentU2Z3(tangentsU2AddressArray[0][tangentU2Index3++]);

	  imageShader.setTexelU1(texelsAddressArray[0][texelIndex1++]);
	  imageShader.setTexelV1(texelsAddressArray[0][texelIndex1++]);

	  imageShader.setTexelU2(texelsAddressArray[0][texelIndex2++]);
	  imageShader.setTexelV2(texelsAddressArray[0][texelIndex2++]);

	  imageShader.setTexelU3(texelsAddressArray[0][texelIndex3++]);
	  imageShader.setTexelV3(texelsAddressArray[0][texelIndex3++]);

   //  if(notifier.getCurrentRenderingAttributes().getLocalShadingAttribute() == FLAT_SHADING_ENABLED) {

   //      imageShader.setNormalX1((normalsAddressArray[0][normalIndex1++] +
   //                               normalsAddressArray[0][normalIndex2++] +
   //                               normalsAddressArray[0][normalIndex3++]) / 3.0f);

   //      imageShader.setNormalY1((normalsAddressArray[0][normalIndex1++] +
   //                               normalsAddressArray[0][normalIndex2++] +
   //                               normalsAddressArray[0][normalIndex3++]) / 3.0f);

   //      imageShader.setNormalZ1((normalsAddressArray[0][normalIndex1++] +
   //                               normalsAddressArray[0][normalIndex2++] +
   //                               normalsAddressArray[0][normalIndex3++]) / 3.0f);

   //   }

   //   else {

        imageShader.setNormalX1(normalsAddressArray[0][normalIndex1++]);
   	  imageShader.setNormalY1(normalsAddressArray[0][normalIndex1++]);
	     imageShader.setNormalZ1(normalsAddressArray[0][normalIndex1++]);

	     imageShader.setNormalX2(normalsAddressArray[0][normalIndex2++]);
	     imageShader.setNormalY2(normalsAddressArray[0][normalIndex2++]);
	     imageShader.setNormalZ2(normalsAddressArray[0][normalIndex2++]);

	     imageShader.setNormalX3(normalsAddressArray[0][normalIndex3++]);
	     imageShader.setNormalY3(normalsAddressArray[0][normalIndex3++]);
	     imageShader.setNormalZ3(normalsAddressArray[0][normalIndex3++]);

   //   }

   }

   /**
    * Initializes the world coordinate fields of the image shader associated with this camera for the pixel setting stage of the 3D rendering pipeline.
    *
    * @param RenderNotifier the notifier that this camera is registered to.
    */

   protected void initializeImageShaderWorldCoordinates(RenderNotifier notifier) {

	  float[] currentCoordinates = notifier.getCurrentPrimitiveCoordinates();

	  imageShader.setCoordinateX1(currentCoordinates[0]);
	  imageShader.setCoordinateY1(currentCoordinates[1]);
	  imageShader.setCoordinateZ1(currentCoordinates[2]);
	  imageShader.setCoordinateW1(currentCoordinates[3]);

	  imageShader.setCoordinateX2(currentCoordinates[4]);
	  imageShader.setCoordinateY2(currentCoordinates[5]);
	  imageShader.setCoordinateZ2(currentCoordinates[6]);
	  imageShader.setCoordinateW2(currentCoordinates[7]);

	  imageShader.setCoordinateX3(currentCoordinates[8]);
	  imageShader.setCoordinateY3(currentCoordinates[9]);
	  imageShader.setCoordinateZ3(currentCoordinates[10]);
	  imageShader.setCoordinateW3(currentCoordinates[11]);

   }

   protected void initializeImageShaderToolkit(RenderNotifier notifier) {

      imageShader.setToolkit(notifier.getCurrentToolkit());

   }

   /**
    * Called whenever a call is made to the startObservers() method of the notifier argument, indicating the current mesh associated with the notifier argument
    * is to be rendered into the frame buffer of this camera. This camera must be registered to the notifier argument prior to calling this method.
    *
    * @param notifier the notifier that this camera is registered to.
    */

    protected void notify(RenderNotifier notifier) {

      transformationShader = new TransformationShader(this);

      if(notifier.getCurrentRenderingAttributes().getLocalShadingAttribute() == FLAT_SHADING_ENABLED)
         imageShader = new FlatImageShader(this);

      else if(notifier.getCurrentRenderingAttributes().getLocalShadingAttribute() == PHONG_SHADING_ENABLED && notifier.getCurrentRenderingAttributes().getBumpingAttribute() == BUMP_MAPPING_DISABLED)
         imageShader = new PhongImageShader(this);

      else if(notifier.getCurrentRenderingAttributes().getLocalShadingAttribute() == PHONG_SHADING_ENABLED && notifier.getCurrentRenderingAttributes().getBumpingAttribute() == TRUE_BUMP_MAPPING_ENABLED)
         imageShader = new Dot3BumpMapImageShader(this);

      else if(notifier.getCurrentRenderingAttributes().getLocalShadingAttribute() == COOK_TORRANCE_SHADING_ENABLED && notifier.getCurrentRenderingAttributes().getBumpingAttribute() == BUMP_MAPPING_DISABLED)
         imageShader = new CookTorranceImageShader(this);

      else if(notifier.getCurrentRenderingAttributes().getLocalShadingAttribute() == LIGHTING_DISABLED && notifier.getCurrentRenderingAttributes().getBumpingAttribute() == BUMP_MAPPING_DISABLED)
         imageShader = new BasicImageShader(this);

     // System.out.println(imageShader);

      transformationShader.update(notifier);

     // Geometry currentGeometry = notifier.getCurrentToolkit()
       //                                  .createGeometry();

      Geometry currentGeometry = notifier.getCurrentGeometry();

      if(currentGeometry instanceof Mesh) {

         Mesh currentMesh = (Mesh)currentGeometry;

         currentMesh.optimize();

         float[][] verticesAddressArray = new float[1][];
         int[][] indicesAddressArray = new int[1][];

         currentMesh.getVerticesAddress(verticesAddressArray);
         currentMesh.getIndexedFaceVerticesAddress(indicesAddressArray);

         float[] transformedVertices = new float[4 * currentMesh.getSize()];

         for(int i = 0; i < currentMesh.getIndexedFaceVerticesArrayStop(); i += currentMesh.getSize()) {

            for(int j = 0; j < currentMesh.getSize(); j++) {

               notifier.setCurrentPrimitiveIndex(i);

               int vertexIndex = SPACE_POINT_STRIDE  * (indicesAddressArray[0])[i + j];

               float x = verticesAddressArray[0][vertexIndex++];
               float y = verticesAddressArray[0][vertexIndex++];
               float z = verticesAddressArray[0][vertexIndex++];

               transformationShader.set(x, y, z);

               transformedVertices[4 * j + 0] = transformationShader.getOutputX();
               transformedVertices[4 * j + 1] = transformationShader.getOutputY();
               transformedVertices[4 * j + 2] = transformationShader.getOutputZ();
               transformedVertices[4 * j + 3] = transformationShader.getOutputW();

	        }

            notifier.setCurrentPrimitiveCoordinates(transformedVertices);

            drawPrimitive(notifier);

         }

      }

   }

   /**
    * Called whenever the notify() method of this camera is called by the notifier argument, indicating a primitive is to be rendered into the
    * frame buffer associated with this camera. This camera must be registered to the notifier argument prior to calling this method.
    *
    * @param notifier the notifier that this camera is registered to.
    */

   protected void drawPrimitive(RenderNotifier notifier) {

      RenderingAttributes renderingAttributes = notifier.getCurrentRenderingAttributes();

      int length = notifier.getCurrentGeometry().getSize();

      float[] verticesCoordinates = notifier.getCurrentPrimitiveCoordinates();

      int[] deviceCoordinatesX = new int[length];
      int[] deviceCoordinatesY = new int[length];

      for(int i = 0; i < verticesCoordinates.length; i += 4) {

		 verticesCoordinates[i + 0] /= verticesCoordinates[i + 3];
		 verticesCoordinates[i + 1] /= verticesCoordinates[i + 3];
		 verticesCoordinates[i + 2] /= verticesCoordinates[i + 3];

         float deviceX = viewportX + 0.5f * viewportWidth * (verticesCoordinates[i + 0] + 1);
         float deviceY = viewportY + 0.5f * viewportHeight * (verticesCoordinates[i + 1] + 1);

         deviceCoordinatesX[i / 4] = (int)deviceX;
         deviceCoordinatesY[i / 4] = (int)deviceY;

      }

      if(notifier.getCurrentRenderingAttributes().getLocalShadingAttribute() == FLAT_SHADING_ENABLED) {

         for(int i = 0; i < length; i++) {

            int startX = deviceCoordinatesX[(i + 0) % length];
            int startY = deviceCoordinatesY[(i + 0) % length];

            int stopX = deviceCoordinatesX[(i + 1) % length];
            int stopY = deviceCoordinatesY[(i + 1) % length];

            drawLine(startX, startY, stopX, stopY);

	     }

	  }

      else {

         imageShader.update(notifier);

         int[] boxCoordinatesX = new int[2];
         int[] boxCoordinatesY = new int[2];

         getBoundingBox(deviceCoordinatesX, deviceCoordinatesY, boxCoordinatesX, boxCoordinatesY);

         int width = boxCoordinatesX[1] - boxCoordinatesX[0];
         int height = boxCoordinatesY[1] - boxCoordinatesY[0];

         int offsetX = notifier.getAntiAliasingOffsetX();
         int offsetY = notifier.getAntiAliasingOffsetY();

         int dimension = notifier.getSuperSamplingDimension();

         for(int j = 0; j <= height; j ++) {
	        for(int i = 0; i <= width; i++) {           


               float deviceX = dimension * (boxCoordinatesX[0] + i) + dimension / 2 + offsetX + 0.5f;
               float deviceY = dimension * (boxCoordinatesY[0] + j) + dimension / 2 + offsetY + 0.5f;

               int pixelX = boxCoordinatesX[0] + i;
               int pixelY = boxCoordinatesY[0] + j;

               float screenX = 2.0f * (deviceX - viewportX) / (dimension * viewportWidth) - 1.0f;
               float screenY = 2.0f * (deviceY - viewportY) / (dimension * viewportHeight) - 1.0f;  

               float weight0 = (imageShader.coordinateX2 - imageShader.coordinateX1) * (imageShader.coordinateY3 - imageShader.coordinateY1) - (imageShader.coordinateX3 - imageShader.coordinateX1) * (imageShader.coordinateY2 - imageShader.coordinateY1);

               float weight1 = (imageShader.coordinateX2 - screenX) * (imageShader.coordinateY3 - screenY) - (imageShader.coordinateX3 - screenX) * (imageShader.coordinateY2 - screenY);
               float weight2 = (imageShader.coordinateX3 - screenX) * (imageShader.coordinateY1 - screenY) - (imageShader.coordinateX1 - screenX) * (imageShader.coordinateY3 - screenY);
               float weight3 = (imageShader.coordinateX1 - screenX) * (imageShader.coordinateY2 - screenY) - (imageShader.coordinateX2 - screenX) * (imageShader.coordinateY1 - screenY);

               float barycentricU = weight1 / weight0;
               float barycentricV = weight2 / weight0;
               float barycentricW = weight3 / weight0;

               if((barycentricU >= -.001f && barycentricU <= 1.001f) &&
                  (barycentricV >= -.001f && barycentricV <= 1.001f) &&
                  (barycentricW >= -.001f && barycentricW <= 1.001f)) {

                  if(imageShader.set(renderingAttributes, barycentricU, barycentricV, barycentricW, screenX, screenY, deviceX, deviceY, pixelX, pixelY)) {

                     float[] colors = new float[3];

                     colors[0] = 255.0f * imageShader.getOutputA() * imageShader.getOutputR();
                     colors[1] = 255.0f * imageShader.getOutputA() * imageShader.getOutputG();
                     colors[2] = 255.0f * imageShader.getOutputA() * imageShader.getOutputB();

                     virtualBuffer.setPixel(pixelX, pixelY, colors);

                  }

               }

            }

         }

      }

   }

   /**
    * Calculates the minimum 2D rectangle enclosing the primitive whose defining device space coordinate pairs are determined by the integer
    * array arguments and places the upper left corner and lower right corner device space coordinate pairs defining the bounding box into the
    * integer array arguments.
    *
    * @param deviceCoordinatesX the x-coordinates of the vertices defining the 2D primitive triangle.
    * @param deviceCoordinatesY the y-coordinates of the vertices defining the 2D primitive triangle.
    * @param boxCoordinatesX the array to store the calculated x-coordinates defining the 2D bounding rectangle.
    * @param boxCoordinatesY the array to store the calculated y-coordinates defining the 2D bounding rectangle.
    */

   public void getBoundingBox(int[] deviceCoordinatesX, int[] deviceCoordinatesY, int[] boxCoordinatesX, int[] boxCoordinatesY) {

      boxCoordinatesX[0] = deviceCoordinatesX[0];
      boxCoordinatesX[1] = deviceCoordinatesX[1];

      boxCoordinatesY[0] = deviceCoordinatesY[0];
      boxCoordinatesY[1] = deviceCoordinatesY[1];

      if(boxCoordinatesX[0] > boxCoordinatesX[1]) {

		 boxCoordinatesX[0] = deviceCoordinatesX[1];
         boxCoordinatesX[1] = deviceCoordinatesX[0];

      }

      if(boxCoordinatesY[0] > boxCoordinatesY[1]) {

		 boxCoordinatesY[0] = deviceCoordinatesY[1];
         boxCoordinatesY[1] = deviceCoordinatesY[0];

      }

      for(int i = 2; i < deviceCoordinatesX.length; i++) {

         if(boxCoordinatesX[0] > deviceCoordinatesX[i])
  		    boxCoordinatesX[0] = deviceCoordinatesX[i];

	     else if(boxCoordinatesX[1] < deviceCoordinatesX[i])
	        boxCoordinatesX[1] = deviceCoordinatesX[i];

         if(boxCoordinatesY[0] > deviceCoordinatesY[i])
		    boxCoordinatesY[0] = deviceCoordinatesY[i];

	     else if(boxCoordinatesY[1] < deviceCoordinatesY[i])
	        boxCoordinatesY[1] = deviceCoordinatesY[i];

      }

   }

   public void drawLine(float xi, float yi, float xf, float yf) {

	  float delta = (float)Math.abs((yf - yi)/(xf - xi));

	  if(delta < 1.0f)
	     drawLineX(xi, yi, xf, yf);

	  else
	     drawLineY(xi, yi, xf, yf);

   }

   public void drawLineY(float xi, float yi, float xf, float yf) {
System.out.println("yyyyyyyyyy");
	  if(yi > yf) {

		 float temp_x = xi;
		 float temp_y = yi;

		 xi = xf;
		 yi = yf;

	     xf = temp_x;
	     yf = temp_y;

	  }

	  for(int t = (int)yi; t < (int)yf; t++) {

         float meanT = xi + ((xf - xi) / (yf - yi)) * (t - yi);

         int minT = (int)Math.floor(meanT);
         int maxT = (int)Math.ceil(meanT);

         float weight1 = 0;//meanT - minT;
         float weight2 = 1;//maxT - meanT;

         try {

            accumulationBuffer.setPixel(minT, t, new float[] {1f * (1 - weight1) ,1f * (1 - weight1), 1 * (1 - weight1)});
            accumulationBuffer.setPixel(maxT, t, new float[] {1f * (1 - weight2) ,1f * (1 - weight2), 1 * (1 - weight2)});


         } catch(Exception e) {}

	  }

   }

   public void drawLineX(float xi, float yi, float xf, float yf) {
System.out.println("xxxxxxxxxx");
	  if(xi > xf) {

		 float temp_x = xi;
		 float temp_y = yi;

		 xi = xf;
		 yi = yf;

	     xf = temp_x;
	     yf = temp_y;

	  }

	  for(int t = (int)xi; t < (int)xf; t++) {

         float meanT = yi + ((yf - yi) / (xf - xi)) * (t - xi);

         int minT = (int)Math.floor(meanT);
         int maxT = (int)Math.ceil(meanT);

         float weight1 = 0;//meanT - minT;
         float weight2 = 1;//maxT - meanT;

         try {

            accumulationBuffer.setPixel(t, minT, new float[] {1f * (1 - weight1) ,1f * (1 - weight1), 1 * (1 - weight1)});
            accumulationBuffer.setPixel(t, maxT, new float[] {1f * (1 - weight2) ,1f * (1 - weight2), 1 * (1 - weight2)});


         } catch(Exception e) {}

	  }

   }

   /**
    * Clears the buffer identified by the integer argument, which must be equal to RenderingConstantsI.DEPTH_BUFFER_CLEAR or RenderingConstantsI.COLOR_BUFFER_CLEAR.
    *
    * @param arg the integer identifying whether the depth buffer or color buffer is to be cleared.
    */

   public void clearBuffer(int bufferType) {

      if(bufferType == VIRTUAL_BUFFER_CLEAR) {

	     for(int i = 0; i < virtualBuffer.getWidth(); i++)
	        for(int j = 0; j < virtualBuffer.getHeight(); j++)
	           virtualBuffer.setPixel(i, j, new float[]{0.0f, 0.0f, 0.0f});

      }

	  else if(bufferType == DEPTH_BUFFER_CLEAR) {

	     for(int i = 0; i < depthBuffer.getWidth(); i++)
	        for(int j = 0; j < depthBuffer.getHeight(); j++)
	           depthBuffer.setPixel(i, j, new float[] {1.0f});

      }

      else if(bufferType == ACCUMULATION_BUFFER_CLEAR) {

	     for(int i = 0; i < accumulationBuffer.getWidth(); i++)
	        for(int j = 0; j < accumulationBuffer.getHeight(); j++)
	           accumulationBuffer.setPixel(i, j, new float[] { 0.0f, 0.0f, 0.0f });

      }

   }

   /**
    * Returns the transformation shader associated with this camera.
    *
    * @return the transformation shader associated with this camera.
    */

   public TransformationShader getTransformationShader() { return transformationShader; }

   /**
    * Returns the image shader associated with this camera.
    *
    * @return the image shader associated with this camera.
    */

   public ImageShader getImageShader() { return imageShader; }

   /**
    * Sets the mesh shader associated with this camera to the mesh shader argument.
    *
    * @param meshShader the mesh shader that the mesh shader associated with this camera is set to.
    */

   public void setTransformationShader(TransformationShader transformationShader) { this.transformationShader = transformationShader; }

   /**
    * Sets the image shader associated with this camera to the image shader argument.
    *
    * @param imageShader the image shader that the image shader associated with this camera is set to.
    */

   public void setImageShader(ImageShader imageShader) { this.imageShader = imageShader; }

}

