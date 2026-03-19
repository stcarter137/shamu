package shamu.media.render;

import shamu.analysis.*;

public class TracerMonkeySaddle extends TracerMongePatch {

      protected float alpha;
      protected float beta;

      public TracerMonkeySaddle() {

		   this.alpha = 1.0f;
		   this.beta = 1.0f;

	   }

       public TracerMonkeySaddle(float alpha, float beta) {

	  	   this.alpha = alpha;
		   this.beta = beta;

     }

     public float  mapY(float u, float v) {

		   QuadraticPolynomial  poly1 = (QuadraticPolynomial)Polynomial.build(new double[] {0, 0, -3 * beta} );
		   CubicPolynomial poly2 = (CubicPolynomial)Polynomial.build(new double[] {0,poly1.map(v), 0, alpha});

		   return (float)poly2.map(u);

      }


      public float partialUMapY(float u, float v) {

		    QuadraticPolynomial poly1 = (QuadraticPolynomial)Polynomial.build(new double[] {0, 0, -3 * beta});
		    QuadraticPolynomial poly2 = (QuadraticPolynomial)Polynomial.build(new double [] {poly1.map(v), 0, 3 * alpha});

		    return (float)poly2.map(u);

       }

      public float  partialVMapY(float u, float v) { return -6 * beta * u * v; }

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

         double[] coefficients = new double[4];

         coefficients[0] = alpha * Math.pow(startX_, 3) - 3 * beta * startX_ * Math.pow(startZ_, 2)  - startY_;
         coefficients[1] = 3 * alpha * Math.pow(startX_, 2) * directionX_ - 3 * beta * (2.0 * startX_ * startZ_ * directionZ_ + directionX_ * Math.pow(startZ_, 2)) - directionY_;
         coefficients[2] = 3.0 * alpha * startX_ * Math.pow(directionX_, 2) - 3 * beta * (startX_ * Math.pow(directionZ_, 2) + 2.0 * startZ_ * directionX_ * directionZ_);
         coefficients[3] = alpha * Math.pow(directionX_, 3) - 3.0 * beta * directionX_ * Math.pow(directionZ_, 2);

         if(coefficients[3] != 0.0) {

            coefficients[0] /= coefficients[3];
            coefficients[1] /= coefficients[3];
            coefficients[2] /= coefficients[3];
            coefficients[3]  = 1.0f;

	     }

         else if(coefficients[2] != 0.0) {

            coefficients[0] /= coefficients[2];
            coefficients[1] /= coefficients[2];
            coefficients[2] = 1.0f;

            coefficients = new double[] { coefficients[0], coefficients[1], coefficients[2] };

	     }

         else if(coefficients[1] != 0.0) {

            coefficients[0] /= coefficients[1];
            coefficients[1] = 1.0f;

            coefficients = new double[] { coefficients[0], coefficients[1]  };

	    }

        else
            throw new NullIntersectionException();

        Polynomial poly = (Polynomial)Polynomial.build(coefficients);

         java.util.Vector<Double> roots = new java.util.Vector<Double>();
         poly.computeRoots(roots);

          filter(roots, startX_, startZ_, directionX_, directionZ_);

          return nearest(roots);

    }

   public int getSize() { return 0; }

   public void setAlpha(float alpha) { this.alpha = alpha; }

   /**
    * Sets the value of the beta parameter associated with this monkey saddle to the floating point argument.
    *
    * @param beta the value that the beta parameter associated with this monkey saddle is set to.
    */

   public void setBeta(float beta) { this.beta = beta; }

}