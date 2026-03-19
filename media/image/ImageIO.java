package shamu.media.image;

/** Class containing operations for reading and writing images in .jpg image file formats and creating rasters to write image data to, using
 *  the Java 2D API.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class ImageIO {

   /**
    * Constant defining the interleaved pixel format.
    */

   public static final int FORMAT_PIXEL_INTERLEAVED = 0x000000;

   /**
    * Constant defining the banded pixel format.
    */

   public static final int FORMAT_PIXEL_BANDED = 0x111111;

   /**
    * Constant defining the single packed pixel format.
    */

   public static final int FORMAT_PIXEL_SINGLEPACKED = 0x222222;

   /**
    * Constructor for this image io object, which may only be called by static methods belonging to this class.
    */

   private ImageIO() {}

   /**
    * Writes the buffered image argument to the path specified by the directory and filename arguments as a .jpg image file. The filename argument must
    * have a .jpg extension.
    *
    * @param image the buffered image to be written to a file.
    * @param directory the directory that the buffered image argument is written to.
    * @param filename the name of the file that the buffered image argument is written to.
    * @throws java.io.IOException if an I/O error occurs.
    */
/*
   public static void writeImageFile(java.awt.image.BufferedImage image, String directory, String filename) throws java.io.IOException {

      java.io.ByteArrayOutputStream imageOut = new java.io.ByteArrayOutputStream();

      com.sun.image.codec.jpeg.JPEGImageEncoder encoder = com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(imageOut);

      encoder.encode(image);

      java.io.FileOutputStream fileOut = new java.io.FileOutputStream(new java.io.File(directory + java.io.File.separator + filename));

      imageOut.writeTo(fileOut);

      fileOut.close();

   }
*/
   /**
    * Reads from a .jpg image file in the path specified by the directory and filename arguments and returns the image as a buffered image.
    * The filename argument must have a .jpg extension.
    *
    * @param directory the directory that the image file is read from.
    * @param filename the name of the image file that is read.
    * @return the bufferd image read from the image file specified by the directory and filename arguments.
    * @throws java.io.IOException if an I/O error occurs.
    */

 //  public static java.awt.image.BufferedImage readBufferedImage(String directory, String filename) throws java.io.IOException {

 //     java.io.FileInputStream fileIn = new java.io.FileInputStream(directory + java.io.File.separator + filename);

 //     com.sun.image.codec.jpeg.JPEGImageDecoder decoder =  com.sun.image.codec.jpeg.JPEGCodec.createJPEGDecoder(fileIn);

  //    return decoder.decodeAsBufferedImage();

  // }

   /**
    * Creates and returns a raster to write image data to having the type specified by the string argument and having a width and height
    * specified by the integer arguments.
    *
    * @param pixelFormat the pixel format of the raster to be returned: must be FORMAT_PIXEL_INTERLEAVED, FORMAT_PIXEL_BANDED, or FORMAT_PIXEL_SINGLEPACKED.
    * @param transferType the pixel transfer type of the raster to be returned: must be java.awt.image.DataBuffer.TYPE_BYTE, java.awt.image.DataBuffer.TYPE_DOUBLE, java.awt.image.DataBuffer.TYPE_FLOAT, java.awt.image.DataBuffer.TYPE_INT, java.awt.image.DataBuffer.TYPE_SHORT, or java.awt.image.DataBuffer.TYPE_USHORT.
    * @param rasterWidth the width of the raster to be returned.
    * @param raterHeight the height of the raster to be returned.
    * @return a raster that can have image data written to it.
    */

   public static java.awt.image.WritableRaster createWritableRaster(int pixelFormat, int transferType, int rasterWidth, int rasterHeight, int numComponents) {

      java.awt.image.SampleModel sampleModel = null;

      if(pixelFormat == FORMAT_PIXEL_INTERLEAVED) {

         int[] offsets = new int[numComponents];

         for(int i = 0; i < numComponents; i++)
            offsets[i] = i;

         sampleModel = new java.awt.image.PixelInterleavedSampleModel(transferType, rasterWidth, rasterHeight, numComponents, numComponents * rasterWidth, offsets);

      }

      else  if(pixelFormat == FORMAT_PIXEL_SINGLEPACKED) {

         int[] masks = new int[numComponents];

         for(int i = numComponents; i > 0; i--)
            masks[i - 1] = 0xFF << (8 * (numComponents - i));

         sampleModel = new java.awt.image.SinglePixelPackedSampleModel(transferType, rasterWidth, rasterHeight, masks);

      }

      else if(pixelFormat == FORMAT_PIXEL_BANDED)
         sampleModel = new java.awt.image.BandedSampleModel(transferType, rasterWidth, rasterHeight, numComponents);

      return java.awt.image.Raster.createWritableRaster(sampleModel, sampleModel.createDataBuffer(), null);

   }

}
