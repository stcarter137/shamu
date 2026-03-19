package shamu.utils.swing;

import shamu.media.render.*;
import shamu.media.image.*;
import signalprocessing.imaging.io.IIOImageAgent;

/** Class that subclasses javax.swing.JFrame representing a window that Shamu models are rendered into.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class RenderingFrame extends javax.swing.JFrame {

   /**
    * Buffered image that this rendering frame is associated with.
    */

   protected java.awt.image.BufferedImage offscreenImage = null;

   /**
    * Default constructor of this rendering frame.
    *
    * @param title the string that the title of this rendering frame is set to.
    */

   public RenderingFrame(String title) { super(title); }

   /**
    * Attaches the receptor argument to this rendering frame using a callback technique, in which the device context of the view is set to the graphics context of
    * the offscreen image associated with this rendering frame.
    *
    * @param receptor the receptor that is attached to this rendering frame.
    */

   public void attachViewObject(Receptor receptor) {

	  if(offscreenImage != null)
	     receptor.setDeviceContext(offscreenImage.createGraphics());

   }

   /**
    * Saves the information in this view window's offscreen image as an image file having the path specified by the directory, prefix, and format arguments.
    * Requires installation of Java Advanced Imaging Runtime Environment.
    *
    * @param directory the directory that the image is written to.
    * @param prefix the prefix of the image file name that the image is written to.
    * @param format the format of the image file that the image is written to: ".bmp", ".png", or ".tiff".
    * @throws java.io.IOException if an I/O error occurs during file writing.
    */

   public void createScreenShot(String directory, String prefix, int signature) throws java.io.IOException {

      try {

         javax.imageio.ImageWriter writer = (javax.imageio.ImageWriter)javax.imageio.ImageIO.getImageWritersByFormatName("png").next();

         javax.imageio.ImageWriteParam param = writer.getDefaultWriteParam();
         
         javax.imageio.metadata.IIOMetadata meta = writer.getDefaultImageMetadata(new javax.imageio.ImageTypeSpecifier(offscreenImage), param);
        
          new IIOImageAgent().writeRenderedImageToFile(offscreenImage, directory, prefix, "png");

          writer.dispose();


      } catch(Exception e) { System.out.println(e); }
         //try {

           // meta = writer.getDefaultImageMetadata(new javax.imageio.ImageTypeSpecifier(image), param); }
         
         //catch(Exception e) { System.out.println(meta); }



	   //new IIOImageAgent().writeRenderedImageToFile(offscreenImage, directory, prefix, signature);}
	   //AdvancedImageIO.writeImageFile(offscreenImage, directory, prefix + format); 

   }


   /**
    * Paints this rendering frame, including painting the underlying offscreen image.
    *
    * @param g the graphics context of this rendering frame.
    */

   public void paintComponent(java.awt.Graphics g) {

	  //super.paint(g);

	  java.awt.Graphics2D g2 = (java.awt.Graphics2D)g;

	  g2.drawRenderedImage(offscreenImage, new java.awt.geom.AffineTransform());

   }

   /**
    * Updates this rendering frame by painting the updated underlying offscreen image.
    *
    * @param g the graphics context of this rendering frame.
    */

   public void update(java.awt.Graphics g) {

	  java.awt.Graphics2D g2 = (java.awt.Graphics2D)g;

	  g2.drawRenderedImage(offscreenImage, new java.awt.geom.AffineTransform());

   }

   /**
    * Sets the size of this rendering frame and the underlying offscreen image to the size determined by the integer arguments.
    *
    * @param width the width determining the size of this rendering frame
    * @param height the height determining the size of this rendering frame
    */

   public void setSize(int width, int height) {

	  super.setSize(width, height);

      java.awt.GraphicsEnvironment graphicsEnvironment = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
      java.awt.GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
      java.awt.GraphicsConfiguration graphicsConfiguration = graphicsDevice.getDefaultConfiguration();

      offscreenImage = graphicsConfiguration.createCompatibleImage(width, height);

   }

   /**
    * Sets the size of this rendering frame and the underlying offscreen image to the size determined by the dimension argument.
    *
    * @param d the dimension determining the size of this rendering frame
    */

   public void setSize(java.awt.Dimension d) {

	  super.setSize(d);

      java.awt.GraphicsEnvironment graphicsEnvironment = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
      java.awt.GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
      java.awt.GraphicsConfiguration graphicsConfiguration = graphicsDevice.getDefaultConfiguration();

      offscreenImage = graphicsConfiguration.createCompatibleImage(d.width, d.height);

   }

   /**
    * Gets the offscreen image associated with this rendering frame.
    *
    * @return the offscreen image associated with this rendering frame.
    */

   public java.awt.image.BufferedImage getOffscreenImage() { return offscreenImage; }

   /**
    * Sets the offscreen image associated with this rendering frame to the buffered image argument.
    *
    * @param offscreenImage the buffered image that the offscreen image associated with this rendering frame is set to.
    */

   public void setOffscreenImage(java.awt.image.BufferedImage offscreenImage) { this.offscreenImage = offscreenImage; }

}