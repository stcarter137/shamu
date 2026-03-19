package shamu.media.render;

import shamu.media.image.*;
import signalprocessing.imaging.builders.RectangularImage;

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

abstract public class Receptor implements RenderingConstantsI, ModelingConstantsI {

   /**
    * Constant identifying the projection type of a camera to be a perspective type.
    */

   public static final int PERSPECTIVE_PROJECTION = 0xA0000;

   /**
    * Constant identifying the projection type of a camera to be an orthographic type.
    */

   public static final int ORTHOGRAPHIC_PROJECTION = 0x0B0000;

   /**
    * The projection type associated with this camera.
    */

   protected int projectionType = PERSPECTIVE_PROJECTION;

   /**
    * The position of the near plane in the camera frustum relative to the camera space coordinate system associated with this camera.
    */

   protected float nearPlane =  10.0f;

   /**
    * The position of the far plane in the camera frustum relative to the camera space coordinate system associated with this camera.
    */

   protected float farPlane  =  100.0f;

   /**
    * The angular field of camera of the viewport associated with this camera.
    */

   protected float fieldOfView = (float)Math.PI / 4;

   /**
    * The exposure time for rendering a scene from the viewpoint of this camera.
    */

   protected float shutterSpeed = 1.0f;

   /**
    * The focal length associated with this camera.
    */

   protected float focalLength = 1.0f;

   /**
    * The f-stop associated with this camera.
    */

   protected float fstop = 1000.0f;

   /**
    * The upper left x-coordinate of the viewport associated with this camera in relative coordinates.
    */

   protected float viewportX;

   /**
    * The upper left y-coordinate of the viewport associated with this camera in relative coordinates.
    */

   protected float viewportY;

   /**
    * The width of the viewport associated with this camera in relative coordinates.
    */

   protected float viewportWidth;

   /**
    * The height of the viewport associated with this camera in relative coordinates.
    */

   protected float viewportHeight;

   /**
    * The device context that this camera is rendered into.
    */

   protected java.awt.Graphics2D deviceContext;

 /**
    * Depth buffer used for depth testing while rendering into the device associated with this camera.
    */

   protected java.awt.image.WritableRaster depthBuffer;

   /**
    * The accumulation buffer that serves as a frame buffer for rendering into the device associated with this camera.
    */

   protected java.awt.image.WritableRaster accumulationBuffer;

   /**
    * Virtual color buffer used for antialiasing while rendering into the device associated with this camera.
    */

   protected java.awt.image.WritableRaster virtualBuffer;

   protected java.awt.image.RenderedImage offscreenImage;

   /**
    * The three dimensional transform used to transform world space coordinates into camera space coordinates.
    */

   protected Matrix4D worldToViewMatrix;

   /**
    * The three dimensional transform used to transform camera coordinates into homogeneous screen space coordinates.
    */

   protected Matrix4D projectionMatrix;

   /**
    * Flag identifying the display raster used by the device associated with this camera.
    */

   protected int displayMode = COLOR_BUFFER_DISPLAY;

   protected double[] dataModel;

   /**
    * Constructs a camera associated with the color and depth buffers defined by the integer arguments, with the default world-to-camera coordinate system transform, which is the
    * identity transform, and the default projection transform, which is a perspective transform.
    *
    * @param pixelFormat the pixel format for the color buffer associated with this camera. Must be shamu.image.io.ImageIO.FORMAT_PIXEL_INTERLEAVED, shamu.image.io.ImageIO.FORMAT_PIXEL_BANDED, or shamu.image.io.ImageIO.FORMAT_PIXEL_SINGLEPACKED.
    * @param bufferWidth the width of the depth and color buffers associated with this camera.
    * @param bufferHeight the height of the depth and color buffers associated with this camera.
    */

   public Receptor(int pixelFormat, int bufferWidth, int bufferHeight) {

      this.accumulationBuffer = ImageIO.createWritableRaster(pixelFormat, java.awt.image.DataBuffer.TYPE_FLOAT, bufferWidth, bufferHeight, 3);
      this.virtualBuffer = ImageIO.createWritableRaster(pixelFormat, java.awt.image.DataBuffer.TYPE_FLOAT, bufferWidth, bufferHeight, 3);
      this.depthBuffer = ImageIO.createWritableRaster(pixelFormat, java.awt.image.DataBuffer.TYPE_FLOAT, bufferWidth, bufferHeight, 1);

      //for(int j = 0; j < bufferHeight; j++)
        // for(int k = 0; k < bufferWidth; k++)
          //  this.accumulationBuffer.setPixel(k, j, new float[] {255.0f, 255.0f, 255.0f});

      viewportX = 0;
      viewportY = 0;
      viewportWidth = bufferWidth;
      viewportHeight = bufferHeight;

      worldToViewMatrix = new Matrix4D();
      projectionMatrix = new Matrix4D();

      worldToViewMatrix.setElement(0, 0, 1.0f);
      worldToViewMatrix.setElement(0, 1, 0.0f);
      worldToViewMatrix.setElement(0, 2, 0.0f);
      worldToViewMatrix.setElement(0, 3, 0.0f);

      worldToViewMatrix.setElement(1, 0, 0.0f);
      worldToViewMatrix.setElement(1, 1, 1.0f);
      worldToViewMatrix.setElement(1, 2, 0.0f);
      worldToViewMatrix.setElement(1, 3, 0.0f);

      worldToViewMatrix.setElement(2, 0, 0.0f);
      worldToViewMatrix.setElement(2, 1, 0.0f);
      worldToViewMatrix.setElement(2, 2, 1.0f);
      worldToViewMatrix.setElement(2, 3, 0.0f);

      worldToViewMatrix.setElement(3, 0, 0.0f);
      worldToViewMatrix.setElement(3, 1, 0.0f);
      worldToViewMatrix.setElement(3, 2, 0.0f);
      worldToViewMatrix.setElement(3, 3, 1.0f);

      updateProjectionMatrix();

   }

   protected Receptor(int pixelFormat, int bufferWidth, int bufferHeight, double[] dataModel) {

      this(pixelFormat, bufferHeight, bufferHeight);

      this.dataModel = dataModel;

   }

   /**
    * Updates the projection matrix camera to the matrix defined by the near plane, far plane, field of camera, and projection type fields associated with
    * this camera.
    */

   //public void createScreenShot(String directory, String prefix, int signature) throws java.io.IOException { new ImageAgent().writeRenderedImageToFile(offscreenImage, directory, prefix, signature); }

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
    * Called whenever a call is made to the startObservers() method of the notifier argument, indicating the current mesh associated with the notifier argument
    * is to be rendered into the frame buffer of this camera. This camera must be registered to the notifier argument prior to calling this method.
    *
    * @param notifier the notifier that this camera is registered to.
    */

    abstract protected void notify(RenderNotifier notifier) ;

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
    * Clears the color buffer and depth buffer associated with this camera and starts rendering into the device associated with this camera.
    */

   public void start() {

      clearBuffer(VIRTUAL_BUFFER_CLEAR);
      clearBuffer(DEPTH_BUFFER_CLEAR);
      clearBuffer(ACCUMULATION_BUFFER_CLEAR);

   }

   /**
    * Forwards the current contents of the virtual buffer associated with this camera object to the accumulation buffer associated with this camera object.
    */

   public void forward() {

      for(int i = 0; i < accumulationBuffer.getWidth(); i++) {
	     for(int j = 0; j < accumulationBuffer.getHeight(); j++) {

            if(displayMode == COLOR_BUFFER_DISPLAY) {

               float[] sample = new float[3];

               virtualBuffer.getPixel(i, j, sample);

               float[] superSample = new float[3];

               accumulationBuffer.getPixel(i, j, superSample);

               superSample[0] += sample[0];
               superSample[1] += sample[1];
               superSample[2] += sample[2];

     		   accumulationBuffer.setPixel(i, j, superSample);

            }

            else if(displayMode == DEPTH_BUFFER_DISPLAY) {

               float[] sample = new float[1];

               depthBuffer.getPixel(i, j, sample);

               float[] superSample = new float[3];

               accumulationBuffer.getPixel(i, j, superSample);

               superSample[0] += sample[0];
               superSample[1] += sample[0];
               superSample[2] += sample[0];

     		   accumulationBuffer.setPixel(i, j, superSample);

            }


         }

      }

      clearBuffer(VIRTUAL_BUFFER_CLEAR);
      clearBuffer(DEPTH_BUFFER_CLEAR);

   }

   /**
    * Stops rendering into the virtual buffer associated with this camera and displays the accumulation buffer into the camera port of the device associated
    * with this camera.
    */

   public void stop() {

      java.awt.image.WritableRaster raster = accumulationBuffer;

	  java.awt.image.ColorModel colorModel = new java.awt.image.ComponentColorModel(java.awt.color.ColorSpace.getInstance(java.awt.color.ColorSpace.CS_sRGB), null, false, false, java.awt.Transparency.OPAQUE, java.awt.image.DataBuffer.TYPE_FLOAT);

//	  this.offscreenImage = new java.awt.image.BufferedImage(colorModel, raster, false, null);

      this.offscreenImage = RectangularImage.createRGBInstance(raster);

      if(deviceContext != null) {

   //      java.awt.image.WritableRaster raster = accumulationBuffer;

   //      java.awt.image.ColorModel colorModel = new java.awt.image.ComponentColorModel(java.awt.color.ColorSpace.getInstance(java.awt.color.ColorSpace.CS_sRGB), null, false, false, java.awt.Transparency.OPAQUE, java.awt.image.DataBuffer.TYPE_FLOAT);

	     deviceContext.drawRenderedImage(new java.awt.image.BufferedImage(colorModel, raster, false, null), new java.awt.geom.AffineTransform());

      }

   }

   /**
    * Returns the projection type associated with this camera.
    *
    * @return the projection type associated with this camera. Must be either PERSPECTIVE_PROJECTION or ORTHOGRAPHIC_PROJECTION.
    */

   public int getProjectionType(int projectionType) { return projectionType; }

   /**
    * Returns the position of the near plane in the camera frustum relative to the camera space coordinate system associated with this camera.
    *
    * @return the position of the near plane in the camera frustum relative to the camera space coordinate system associated with this camera.
    */

   public float getNearPlane() { return nearPlane; }

   /**
    * Returns the position of the far plane in the camera frustum relative to the camera space coordinate system associated with this camera.
    *
    * @return the position of the far plane in the camera frusturm relative to the camera space coordinate system associated with this camera.
    */

   public float getFarPlane() { return farPlane; }

   /**
    * Returns the angular field of camera in radians of the viewport associated with this camera.
    *
    * @return the angular field of camera in radians of the viewport associated with this camera.
    */

   public float getFieldOfView() { return fieldOfView; }

   /**
    * Returns the shutter speed associated with this camera.
    *
    * @return the shutter speed associated with this camera.
    */

   public float getShutterSpeed() { return shutterSpeed; }

   /**
    * Returns the focal length associated with this camera.
    *
    * @return the focal length associated with this camera.
    */

   public float getFocalLength() { return focalLength; }

   /**
    * Returns the f-stop associated with this camera.
    *
    * @return the f-stop associated with this camera.
    */

   public float getFStop() { return fstop; }

   /**
    * Returns the upper left x-coordinate of the viewport associated with this camera.
    *
    * @return the upper left x-coordinate of the viewport associated with this camera.
    */

   public float getViewportX() { return viewportX; }

   /**
    * Returns the upper left y-coordinate of the viewport associated with this camera.
    *
    * @return the upper left y-coordinate of the viewport associated with this camera.
    */

   public float getViewportY() { return viewportY; }

   /**
    * Returns the width of the viewport associated with this camera.
    *
    * @return the width of the viewport associated with this camera.
    */

   public float getViewportWidth() { return viewportWidth; }

   /**
    * Returns the height of the viewport associated with this camera.
    *
    * @return the height of the viewport associated with this camera.
    */

   public float getViewportHeight() { return viewportHeight; }

   /**
    * Returns the device context associated with this camera.
    *
    * @return the device context associated with this camera.
    */

   public java.awt.Graphics2D getDeviceContext() { return deviceContext; }

   /**
    * Returns the depth buffer used for depth testing while rendering into the device associated with this camera.
    *
    * @return the depth buffer used for depth testing while rendering into the device associated with this camera.
    */

   public java.awt.image.WritableRaster getDepthBuffer() { return depthBuffer; }

   /**
    * Returns the accumulation buffer that serves as a frame buffer for rendering into the device associated with this camera.
    *
    * @return the accumulation buffer that serves as a frame buffer for rendering into the device associated with this camera.
    */

   public java.awt.image.WritableRaster getAccumulationBuffer() { return accumulationBuffer; }


   /**
    * Returns the virtual buffer used for antialiasing while rendering into the device associated with this camera.
    *
    * @return the virtual buffer used for antialiasing while rendering into the device associated with this camera.
    */

   public java.awt.image.WritableRaster getVirtualBuffer() { return virtualBuffer; }

   public java.awt.image.RenderedImage getOffscreenImage() { return offscreenImage; }

   /**
    * Returns the matrix associated with this camera that transforms points from world space coordinates into camera space coordinates.
    *
    * @return the matrix associated with this camera that transforms points from world space coordinates into camera space coordinates.
    */

   public Matrix4D getWorldToViewMatrix() { return worldToViewMatrix; }

   /**
    * Returns the matrix associated with this camera that projects points from camera space coordinates associated into homogeneous screen space coordinates.
    *
    * @return the matrix associated with this camera that projects points from camera space coordinates associated into homogeneous screen space coordinates.
    */

   public Matrix4D getProjectionMatrix() { return projectionMatrix; }

   /**
    * Returns the flag identifing the display raster used by the device associated with this camera.
    *
    * @return the flag identifying the dispay raster used by the device associated with this camera.
    */

   public int getDisplayMode() { return displayMode; }

   /**
    * Sets the value of the projection type associated with this camera to the integer argument.
    *
    * @param projectionType the value that the projection type associated with this camera is set to. Must be either _PERSPECTIVE_PROJECTION or _ORTHOGRAPHIC_PROJECTION.
    */

   public void setProjectionType(int projectionType) { this.projectionType = projectionType; }

   /**
    * Sets the position of the near plane in the camera frustum associated with this camera to the floating point argument.
    *
    * @param nearPlane the value that the position of the near plane in the camera frustum associated with this camera is set to relative to the camera space coordinate system associated with this camera.
    */

   public void setNearPlane(float nearPlane) { this.nearPlane = nearPlane; }

   /**
    * Sets the position of the far plane in the camera frustum associated with this camera to the floating point argument.
    *
    * @param farPlane the value that the position of the far plane in the camera frustum associated with this camera is set to relative to the camera space coordinate system associated with this camera.
    */

   public void setFarPlane(float farPlane) { this.farPlane = farPlane; }

   /**
    * Sets the angular field of camera in radians of the viewport associated with this camera to the floating point argument.
    *
    * @param fieldOfView the value in radians that the angular field of camera of the viewport associated with this camera is set to.
    */

   public void setFieldOfView(float fieldOfView) { this.fieldOfView = fieldOfView; }

   /**
    * Sets the shutter speed associated with this camera to the floating point argument.
    *
    * @param fieldOfView the value that the shuttter speed associated with this camera is set to.
    */

   public void setShutterSpeed(float shutterSpeed) { this.shutterSpeed = shutterSpeed; }

   /**
    * Sets the value of the focal length associated with this camera to the floating point argument.
    *
    * @param focalLength the value that the focal length associated with this camera is set to.
    */

   public void setFocalLength(float focalLength) { this.focalLength = focalLength; }

   /**
    * Sets the value of the f-stop associated with this camera to the floating point argument.
    *
    * @param fstop the value that the f-stop associated with this camera is set to.
    */

   public void setFStop(float fstop) { this.fstop = fstop; }

   /**
    * Sets the upper left x-coordinate of the viewport associated with this camera to the floating point argument.
    *
    * @param viewportX the value that the upper left x-coordinate of the viewport associated with this camera is set to.
    */

   public void setViewportX(float viewportX) { this.viewportX = viewportX; }

   /**
    * Sets the upper left y-coordinate of the viewport associated with this camera to the floating point argument.
    *
    * @param viewportY the value that the upper left y-coordinate of the viewport associated with this camera is set to.
    */

   public void setViewportY(float viewportY) { this.viewportY = viewportY; }

   /**
    * Sets the width of the viewport associated with this camera to the floating point argument.
    *
    * @param viewportWidth the value that the width of the viewport associated with this camera is set to.
    */

   public void setViewportWidth(float viewportWidth) { this.viewportWidth = viewportWidth; }

   /**
    * Sets the height of the viewport associated with this camera to the floating point argument.
    *
    * @param viewportHeight the value that the height of the viewport associated with this camera is set to.
    */

   public void setViewportHeight(float viewportHeight) { this.viewportHeight = viewportHeight; }

   /**
    * Sets the device context associated with this camera to the Java graphics object argument.
    *
    * @param deviceContext the Java graphics object that is set as the device context associated this camera.
    */

   public void setDeviceContext(java.awt.Graphics2D deviceContext) { this.deviceContext = deviceContext; }

   /**
    * Sets the depth buffer associated with this camera to the raster argument.
    *
    * @param depthBuffer the raster that the depth buffer associated with this camera is set to.
    */

   public void setDepthBuffer(java.awt.image.WritableRaster depthBuffer) { this.depthBuffer = depthBuffer; }

   /**
    * Sets the accumulation buffer associated with this camera to the raster argument.
    *
    * @param accumulationBuffer the raster that the color buffer associated with this camera is set to.
    */

   public void setAccumulationBuffer(java.awt.image.WritableRaster accumulationBuffer) { this.accumulationBuffer = accumulationBuffer; }

   /**
    * Sets the virtual buffer associated with this camera to the raster argument.
    *
    * @param virtualBuffer the raster that the virtual buffer associated with this camera is set to.
    */

   public void setVirtualBuffer(java.awt.image.WritableRaster virtualBuffer) { this.virtualBuffer = virtualBuffer; }

   /**
    * Sets the world to camera transformation matrix associated with this camera to the matrix argument.
    *
    * @param worldToViewMatrix the matrix that the world to camera transformation matrix associated with this camera is set to.
    */

   public void setWorldToViewMatrix(Matrix4D worldToViewMatrix) { this.worldToViewMatrix = worldToViewMatrix; }

   /**
    * Sets the flag identifying the display raster used by the device associated with this camera to the integer argument. Must be equal to DEPTH_BUFFER_DISPLAY
    * or COLOR_BUFFER_DISPLAY.
    *
    * @param displayMode the value that the flag identifying the display raster used by the device associated with this camera is set to.
    */

   public void setDisplayMode(int displayMode) { this.displayMode = displayMode; }

}

