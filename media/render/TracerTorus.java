package shamu.media.render;

import shamu.analysis.*;

public class TracerTorus extends TracerSurface {

      protected float innerRadius;
      protected float outerRadius;

      public TracerTorus(float umin, float vmin, float umax, float vmax) {

	         this.umin = umin;
	         this.vmin = vmin;

	         this.umax = umax;
	         this.vmax = vmax;

	   }

       public TracerTorus(float innerRadius, float outerRadius) {

             this(0.0f, 0.0f, 2.0f * (float)Math.PI, 2.0f * (float)Math.PI);

	  	     this.innerRadius = innerRadius;
		     this.outerRadius = outerRadius;

	   }

       public float mapX(float u, float v) { return (outerRadius + innerRadius * (float)Math.cos(v)) * (float)Math.cos(u); }

       public float mapY(float u, float v) { return innerRadius * (float)Math.sin(v); }

       public float mapZ(float u, float v) { return  (outerRadius + innerRadius * (float)Math.cos(v)) * (float)Math.sin(u); }

       public float partialUMapX(float u, float v) { return -(outerRadius + innerRadius * (float)Math.cos(v)) * (float)Math.sin(u); }

       public float partialUMapY(float u, float v) { return 0.0f; }

       public float partialUMapZ(float u, float v) { return  (outerRadius + innerRadius * (float)Math.cos(v)) * (float)Math.cos(u); }

       public float partialVMapX(float u, float v) { return -innerRadius * (float)Math.sin(v) * (float)Math.cos(u); }

       public float partialVMapY(float u, float v) { return innerRadius * (float)Math.cos(v); }

       public float partialVMapZ(float u, float v) { return  -innerRadius * (float)Math.sin(v) * (float)Math.sin(u); }

       public float intersection(float startX, float startY, float startZ, float endX, float endY, float endZ, Matrix4D matrix) throws NullIntersectionException {

             float  startX_  = matrix.getElement(0, 0) * startX   + matrix.getElement(0, 1) * startY  + matrix.getElement(0, 2) * startZ  + matrix.getElement(0, 3);
 	         float  startY_  = matrix.getElement(1, 0) * startX   + matrix.getElement(1, 1) * startY  + matrix.getElement(1, 2) * startZ  + matrix.getElement(1, 3);
             float  startZ_  = matrix.getElement(2, 0) * startX   + matrix.getElement(2, 1) * startY  + matrix.getElement(2, 2) * startZ  + matrix.getElement(2, 3);

             float  endX_ = matrix.getElement(0, 0) * endX   + matrix.getElement(0, 1) *  endY + matrix.getElement(0, 2) * endZ + matrix.getElement(0, 3);
	         float  endY_ = matrix.getElement(1, 0) * endX   + matrix.getElement(1, 1) * endY  + matrix.getElement(1, 2) * endZ + matrix.getElement(1, 3);
             float  endZ_  =matrix.getElement(2, 0) * endX   + matrix.getElement(2, 1) * endY  + matrix.getElement(2, 2) *  endZ + matrix.getElement(2, 3);

             float directionX_ = endX_  - startX_;
             float directionY_ = endY_ - startY_;
             float directionZ_ = endZ_  - startZ_;

             double alpha = Math.pow(outerRadius, 2);
             double beta  = Math.pow(innerRadius, 2);

             double dot1 = directionX_ * directionX_ + directionY_ * directionY_  + directionZ_ * directionZ_;
             double dot2 = startX_ * startX_ + startY_ * startY_  + startZ_ * startZ_;
             double dot3 = startX_ * directionX_ + startY_ * directionY_  + startZ_ * directionZ_;

             double[] coefficients = new double[5];

             coefficients[0] = Math.pow(dot2 - beta - alpha, 2) + 4.0 * alpha * (Math.pow(startY_, 2) - beta);
             coefficients[1] = 4.0 * dot3 * (dot2 - beta - alpha) + 8.0 * alpha * startY_ * directionY_;
             coefficients[2] = 4.0 * Math.pow(dot3, 2) + 2.0 * dot1 * (dot2 - beta - alpha) + 4.0 * alpha * Math.pow(directionY_, 2);
             coefficients[3] = 4.0 * dot1 * dot3;
             coefficients[4] = Math.pow(dot1, 2);

             coefficients[0] /= coefficients[4];
             coefficients[1] /= coefficients[4];
             coefficients[2] /= coefficients[4];
             coefficients[3] /= coefficients[4];
             coefficients[4]  = 1;

            QuarticPolynomial  poly = (QuarticPolynomial)Polynomial.build(coefficients);

            java.util.Vector<Double> roots = new java.util.Vector<Double>();
            poly.computeRoots(roots);

            return nearest(roots);

      }

      public void normal(float[] normalCoords, float[] posCoords) {

            float expr1  = (float)Math.sqrt(posCoords[0] * posCoords[0] + posCoords[2] * posCoords[2]);
            
            normalCoords[0] =  posCoords[0] * (expr1 - outerRadius) / expr1;
            normalCoords[1] =  posCoords[1];
            normalCoords[2] =  posCoords[2] * (expr1 - outerRadius) / expr1;

	        float normalFactor   = (float)Math.sqrt(normalCoords[0] * normalCoords[0] + normalCoords[1] * normalCoords[1] + normalCoords[2] * normalCoords[2]);

	        normalCoords[0] /= normalFactor;
	        normalCoords[1] /= normalFactor;
	        normalCoords[2] /= normalFactor;

	  }

      public void texturize(float[] textureCoords, float[] coords) {

            handleErrorsX(textureCoords, coords);
            handleErrorsY(textureCoords, coords);

            adjustUCoordinate(textureCoords, coords);
            adjustVCoordinate(textureCoords, coords);

             textureCoords[0] /= (2.0f * (float)Math.PI);
             textureCoords[1] /= (2.0f * (float)Math.PI);

       }

     public void handleErrorsX(float[] textureCoords, float[] coords) {

           if(coords[0] == 0 && coords[2] > 0)
              textureCoords[0] = (float)Math.PI / 2.0f;

           else if(coords[0] == 0 && coords[2] < 0)
              textureCoords[0] = 3.0f * (float)Math.PI / 2.0f;

           else
               textureCoords[0] = (float)Math.atan(coords[2]/ coords[0]);

     }

     public void handleErrorsY(float[] textureCoords, float[] coords) {

           if(coords[1] > innerRadius)
               coords[1] = innerRadius;

           else if(coords[1] < -innerRadius)
                coords[1]= -innerRadius;

            textureCoords[1] = (float)Math.asin(coords[1] / innerRadius);

     }

       public void adjustUCoordinate(float[] textureCoords, float[] coords) {

            if(coords[2] >= 0.0f && coords[0] < 0.0f)
                textureCoords[0] = textureCoords[0] + (float)Math.PI;

            else if(coords[2] <= 0.0f && coords[0] <0.0f)
                textureCoords[0] = textureCoords[0] + (float)Math.PI;

            else if(coords[2] <= 0.0f && coords[0] > 0.0f)
                textureCoords[0] = 2.0f * (float)Math.PI + textureCoords[0];

	   }

       public void adjustVCoordinate(float[] textureCoords, float[] coords) {

             float rho = (float)Math.sqrt(coords[0] * coords[0] + coords[2] * coords[2]);

             if(coords[1] >= 0.0f && rho > outerRadius)
                textureCoords[1] = (float)Math.PI - textureCoords[1];

             else if(coords[1] <= 0.0f && rho <= outerRadius)
                  textureCoords[1] = 2.0f * (float)Math.PI +  textureCoords[1];

             else if(coords[1] <= 0.0f && rho >= outerRadius)
                  textureCoords[1] = (float)Math.PI -  textureCoords[1];

     }

     public int getSize() { return 0; }

     public float getInnerRadius() { return this.innerRadius; }

     public float getOuterRadius() { return this.outerRadius; }

     public void setInnerRadius(float innerRadius) { this.innerRadius = innerRadius; }

    /**
      * Sets the value of the beta parameter associated with this monkey saddle to the floating point argument.
      *
      * @param beta the value that the beta parameter associated with this monkey saddle is set to.
      */

     public void setOuterRadius(float outerRadius) { this.outerRadius = outerRadius; }

}