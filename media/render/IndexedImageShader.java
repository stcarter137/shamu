package shamu.media.render;

import signalprocessing.computationalstatistics.SamplingManager;

/** Class representing an illumination image shader in the 3D rendering pipeline that performs depth testing and per pixel lighting calculations. Each instance
 *  contains four ouput registers that will store the output colors for an input pixel.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class IndexedImageShader extends ImageShader implements RenderingConstantsI {

   public static final int[][] PACIFIC_THEME = {{32, 254, 232}, {4, 110, 252}, {0, 74, 94}};

   //public static final int[][] CONSTANT_THEME = {{255, 255, 255}, {0, 0, 0}, {0, 0, 0}};

   public static final int[][] PACIFIC2_THEME = {{32, 32, 255}, {24, 255, 96}, {16, 8, 2}};

   //public static final int[][] STORM_THEME = {{32, 255, 137}, {4, 32, 13}, {0, 93, 255}};   
 public static final int[][] ARCTIC_THEME = {{32, 173, 255}, {4, 70, 190}, {0, 3, 110}};   

public static final int[][] STORM_2_THEME = {{94, 255, 137}, {4, 32, 13}, {0, 93, 255}};  

public static final int[][] STORM_3_THEME = {{255, 82, 32}, {75, 0, 255}, {32, 0, 22}};  

public static final int[][] STORM_4_THEME = {{255, 94, 32}, {32, 4, 255}, {93, 0, 5}};  

public static final int[][] STORM_5_THEME = {{255, 12, 132}, {96, 4, 0}, {11, 56, 255}};  

   //public static final int[][] PACIFIC_THEME = {{64, 255, 255}, {0, 227, 96}, {16, 0, 2}};

   public static final int[][] EMERALD_THEME = {{88, 127, 16}, {8, 145, 255}, {12, 16, 255}};

   public static final int[][] EMERALD2_THEME = {{44, 255, 212}, {0, 44, 22}, {0, 100, 255}};

   public static final int[][] EMERALD3_THEME = {{32, 255, 64}, {255, 96, 0}, {0, 2, 16}};

   public static final int[][] SOLAR_THEME = {{9, 0, 2}, {0, 227, 12}, {18, 255, 255}};

   public static int PALLETTE_SIZE = 256;

   protected int[] palletteR = new int[IndexedImageShader.PALLETTE_SIZE];
   
   protected int[] palletteG = new int[IndexedImageShader.PALLETTE_SIZE];
   
   protected int[] palletteB = new int[IndexedImageShader.PALLETTE_SIZE];

   protected int[] extremaB = PACIFIC2_THEME[0];

   protected int[] extremaG = PACIFIC2_THEME[1];

   protected int[] extremaR = PACIFIC2_THEME[2];

   protected double pLow;

   protected double pHigh;

   /**
    * Constructs an illumination image shader associated with the receptor object argument.
    *
    * @param receptor the receptor associated with this illumination image shader.
    */

   public IndexedImageShader(VertexReceptor receptor, java.util.stream.DoubleStream dataStream) { 

      super(receptor); 

      buildPallette(palletteR, extremaR[0], extremaR[1], 0, IndexedImageShader.PALLETTE_SIZE / 2 - 1);
      buildPallette(palletteR, extremaR[1], extremaR[2], IndexedImageShader.PALLETTE_SIZE / 2, IndexedImageShader.PALLETTE_SIZE - 1);

      buildPallette(palletteG, extremaG[0], extremaG[1], 0, IndexedImageShader.PALLETTE_SIZE / 2 - 1);
      buildPallette(palletteG, extremaG[1], extremaG[2], IndexedImageShader.PALLETTE_SIZE / 2, IndexedImageShader.PALLETTE_SIZE - 1);

      buildPallette(palletteB, extremaB[0], extremaB[1], 0, IndexedImageShader.PALLETTE_SIZE / 2 - 1);
      buildPallette(palletteB, extremaB[1], extremaB[2], IndexedImageShader.PALLETTE_SIZE / 2, IndexedImageShader.PALLETTE_SIZE - 1);

      this.pHigh = dataStream.max().getAsDouble();//SamplingManager.maximum(dataModel);
      this.pLow = 0;//SamplingManager.minimum(dataModel);

   }

   protected void buildPallette(int[] pallette, int lowColor, int highColor, int lowIndex, int highIndex) {

      int gradient = highColor - lowColor;
      int length = highIndex - lowIndex + 1;

      for(int k = 0; k < length; k++)  {
         pallette[lowIndex + k] = (int)(lowColor + k * ((double)(highColor - lowColor)) / (length - 1));            
      }

   }

   protected int getPalletteIndex(double pVal) {

      double gradient = pHigh - pLow;

      int index = (int)(IndexedImageShader.PALLETTE_SIZE * (pVal - pLow) / gradient); 

      if(index < 0)
         index = 0;

      else if(index > 255)
         index = 255;

      return index;
      //return (index > 255) ? 255 : index;

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

   protected boolean set(RenderingAttributes renderingAttributes, float barycentricU, float barycentricV, float barycentricW, float screenX, float screenY, float deviceX, float deviceY, int pixelX, int pixelY) {

      if(screenX > -1.0f && screenX < 1.0f && screenY > -1.0f && screenY < 1.0f) {

         float[] sample = new float[3];

         if(renderingAttributes.getDepthBufferStateAttribute() == DEPTH_BUFFER_ENABLED) {

            float ux = coordinateX1 - coordinateX2;
            float uy = coordinateY1 - coordinateY2;
            float uz = coordinateZ1 - coordinateZ2;

            float vx = coordinateX1 - coordinateX3;
            float vy = coordinateY1 - coordinateY3;
            float vz = coordinateZ1 - coordinateZ3;

            float wx = uy * vz - uz * vy;
            float wy = uz * vx - ux * vz;
            float wz = ux * vy - uy * vx;

            float screenZ = coordinateZ1 - (wx * (screenX - coordinateX1) + wy * (screenY - coordinateY1)) / wz;

            float[] depth = new float[1];

            receptor.depthBuffer.getPixel(pixelX, pixelY, depth);

            if(screenZ > 0.0f && screenZ < 1.0f && screenZ <= depth[0]) {

               receptor.depthBuffer.setPixel(pixelX, pixelY, new float[]{screenZ});

               float[] position = new float[3];
               computePosition(barycentricU, barycentricV, barycentricW, position);

               int palletteIndex = getPalletteIndex(-position[1]);

               sample[0] = palletteR[palletteIndex] / 255.0f;  
               sample[1] = palletteG[palletteIndex] / 255.0f;  
               sample[2] = palletteB[palletteIndex] / 255.0f;  

               if(renderingAttributes.getTexturingAttribute() == TEXTURING_ENABLED) {

                  float[] texel = new float[2];

                  computeTexel(barycentricU, barycentricV, barycentricW, texel);

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

                  textureMapArray[0].readTexel(TextureMap.BASE_TEXTURE_FLAG, transformU, transformV, textureColors);

                  sample[0] += .5f*textureColors[0];
                  sample[1] += .5f*textureColors[1];
                  sample[2] += textureColors[2];

                  textureMapArray[0].readTexel(TextureMap.BASE_TEXTURE_FLAG, transformV, transformU, textureColors);

                  sample[0] += .5f*textureColors[0];
                  sample[1] += .5f*textureColors[1];
                  sample[2] += textureColors[2];

               }

               outputR = (float)Math.min(sample[0], 1.0);
               outputG = (float)Math.min(sample[1], 1.0);
               outputB = (float)Math.min(sample[2], 1.0);

               return true;

            }

         }

      }

      return false;

   }

   protected void update(RenderNotifier notifier) {

      receptor.initializeImageShaderEye(notifier);
      receptor.initializeImageShaderTextureArray(notifier);

      receptor.initializeImageShaderObjectCoordinates(notifier);
      receptor.initializeImageShaderWorldCoordinates(notifier);

   }

}