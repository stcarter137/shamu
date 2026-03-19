package shamu.media.render;

import shamu.analysis.*;

public class TracerParaboloid extends TracerMongePatch {

     protected float alpha = .007f;

     public TracerParaboloid() { }

     public float  mapY(float u, float v) {

		 QuadraticPolynomial quad1 = (QuadraticPolynomial)Polynomial.build(new double[] {0.0, 0.0, alpha});

         QuadraticPolynomial quad2 = (QuadraticPolynomial)Polynomial.build(new double[] {quad1.map(v), 0.0, alpha});

         return (float)quad2.map(u);

	  }

     public float partialUMapY(float u, float v) { return alpha * 2.0f * u;  }

     public float  partialVMapY(float u, float v) { return alpha * 2.0f * v; }

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

        coefficients[2] = alpha * (Math.pow(directionX_, 2) + Math.pow(directionZ_, 2));
        coefficients[1] = alpha * 2.0f * (startX_ * directionX_ + startZ_ * directionZ_) - directionY_;
        coefficients[0] = alpha * (Math.pow(startX_, 2) + Math.pow(startZ_, 2)) - startY_;

        coefficients[0] /= coefficients[2];
        coefficients[1] /= coefficients[2];
        coefficients[2]  = 1;

        double discr = Math.pow(coefficients[1], 2) - 4.0f * coefficients[0];

        if(discr < 0)
          throw new NullIntersectionException();

        QuadraticPolynomial poly = (QuadraticPolynomial)Polynomial.build(coefficients);

         java.util.Vector<Double> roots = new java.util.Vector<Double>();
         poly.computeRoots(roots);

         filter(roots, startX_, startZ_, directionX_, directionZ_);

         return nearest(roots);

    }

	 public int getSize() { return 0; }

}