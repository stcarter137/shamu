package shamu.media.render;

import shamu.analysis.*;

public class TracerCone extends TracerSurface {

     protected float slope;

     public TracerCone(float umin, float vmin, float umax, float vmax) {

		 this.umin = umin;
         this.vmin = vmin;

         this.umax = umax;
         this.vmax = vmax;

	 }

	 public TracerCone(float slope, float height) {

		 this.slope = slope;

         this.vmin = -height / slope;
         this.vmax = height / slope;

         this.umin = 0.0f;
         this.umax = 2.0f * (float)Math.PI;

	 }

       public float mapX(float u, float v) { return v * (float)Math.cos(u); }

       public float mapY(float u, float v) { return slope *  v; }

       public float mapZ(float u, float v) { return v * (float)Math.sin(u); }

       public float partialUMapX(float u, float v) { return -v * (float)Math.sin(u); }

       public float partialUMapY(float u, float v) { return 0.0f; }

       public float partialUMapZ(float u, float v) { return  v * (float)Math.cos(u); }

       public float partialVMapX(float u, float v) { return (float)Math.cos(u); }

       public float partialVMapY(float u, float v) { return slope; }

       public float partialVMapZ(float u, float v) { return  (float)Math.sin(u); }

       public void texturize(float[] textureCoords, float[] coords) {

   		     handleErrorsX(textureCoords, coords);
   		     handleErrorsY(textureCoords, coords);

   		     adjustUCoordinate(textureCoords, coords);
             adjustVCoordinate(textureCoords, coords);

             textureCoords[0] /= (2.0f * (float)Math.PI);
             textureCoords[1] /= (2.0f * slope * vmax);

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

   		     textureCoords[1] = (coords[1]  + slope * vmax);

	    }

        public void adjustUCoordinate(float[] textureCoords, float[] coords) {

             if(coords[2] > 0.0f && coords[0] < 0.0f)
                 textureCoords[0] = textureCoords[0] + (float)Math.PI;

             else if(coords[2] < 0.0f && coords[0] <0.0f)
                 textureCoords[0] = textureCoords[0] + (float)Math.PI;

             else if(coords[2] < 0.0f && coords[0] > 0.0f)
                 textureCoords[0] = 2.0f * (float)Math.PI + textureCoords[0];

        }

        public void adjustVCoordinate(float[] textureCoords, float[] coords) {}

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

              double[] coefficients = new double[3];

              float slopeSquared = slope * slope;

             coefficients[2] = directionX_ * directionX_ + directionZ_ * directionZ_ - directionY_ * directionY_ / slopeSquared;
             coefficients[1] = 2.0f * (startX_ * directionX_ + startZ_ * directionZ_ - (startY_ * directionY_) / slopeSquared);
             coefficients[0] = startX_ * startX_ + startZ_ * startZ_ - (startY_ * startY_) / slopeSquared;

             coefficients[0] /= coefficients[2];
             coefficients[1] /= coefficients[2];
             coefficients[2]  = 1.0;

             QuadraticPolynomial poly = (QuadraticPolynomial)Polynomial.build(coefficients);

             java.util.Vector<Double> roots = new java.util.Vector<Double>();
             poly.computeRoots(roots);

             float t =  nearest(roots);

             float x = startX_ + directionX_ * t;
             float y = startY_ + directionY_ * t;
             float z = startZ_ + directionZ_ * t;

             float theta = (float)Math.atan(z / x);
             float height = slope * vmax;

              if(y > height || y < -height)
                 throw new NullIntersectionException();

              return t;

	    }

        public void normal(float[] normalCoords, float[] posCoords) {

              float radius = (float)Math.sqrt(Math.pow(posCoords[0], 2) + Math.pow(posCoords[2], 2));

	   	     normalCoords[1] =  -1.0f;
		     normalCoords[0] =  posCoords[0] / (slope * radius) ;
		     normalCoords[2] =  posCoords[2] / (slope * radius);

             if(posCoords[1] < 0.0f)
                normalCoords[1] *=-1;

		    float magnitude = (float)Math.sqrt(normalCoords[0] * normalCoords[0] +normalCoords[1] * normalCoords[1] + normalCoords[2] * normalCoords[2]);

		    normalCoords[0] /= magnitude;
		    normalCoords[1] /= magnitude;
		    normalCoords[2] /= magnitude;

	    }

        public void getLocalCoordinates(float[] textureCoords, float[] localCoords) {

            localCoords[0] = (umax - umin) * textureCoords[0];
            localCoords[1] = 2 *  vmax * textureCoords[1]  -  vmax;

	    }

	 public int getSize() { return 0; }

}