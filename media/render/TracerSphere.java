package shamu.media.render;

import shamu.analysis.*;

public class TracerSphere extends TracerSurface {

	public float radius;

    public TracerSphere(float umin, float vmin, float umax, float vmax) {

		this.umin = umin;
	    this.vmin = vmin;

	    this.umax = umax;
	    this.vmax = vmax;

	}

	public TracerSphere(float radius) {

		this(0.0f, 0.0f, 2.0f * (float)Math.PI, (float)Math.PI);

		this.radius = radius;

	}

    public float mapX(float u, float v) { return radius * (float)(Math.cos(u) * Math.sin(v)); }

    public float mapY(float u, float v) { return radius * (float)Math.cos(v); }

    public float mapZ(float u, float v) { return radius * (float)(Math.sin(u) * Math.sin(v)); }

    public float partialUMapX(float u, float v) { return -radius * (float)(Math.sin(u) * Math.sin(v));  }

   	public float partialUMapY(float u, float v) { return 0.0f; }

    public float partialUMapZ(float u, float v) { return radius * (float)(Math.cos(u) * Math.sin(v)); }

    public float partialVMapX(float u, float v) { return radius * (float)(Math.cos(u) * Math.cos(v));}

    public float partialVMapY(float u, float v) { return -radius * (float)Math.sin(v); }

    public float partialVMapZ(float u, float v) { return radius * (float)(Math.sin(u) * Math.cos(v));}

	public float intersection(float startX, float startY, float startZ, float endX, float endY, float endZ, Matrix4D matrix) throws NullIntersectionException {

    	float  startX_  = matrix.getElement(0, 0) * startX   + matrix.getElement(0, 1) * startY  + matrix.getElement(0, 2) * startZ  + matrix.getElement(0, 3);
	    float  startY_  = matrix.getElement(1, 0) * startX   + matrix.getElement(1, 1) * startY  + matrix.getElement(1, 2) * startZ   + matrix.getElement(1, 3);
        float  startZ_  = matrix.getElement(2, 0) * startX   + matrix.getElement(2, 1) * startY  + matrix.getElement(2, 2) * startZ   + matrix.getElement(2, 3);

        float  endX_ = matrix.getElement(0, 0) * endX   + matrix.getElement(0, 1) *  endY + matrix.getElement(0, 2) * endZ  + matrix.getElement(0, 3);
	    float  endY_ = matrix.getElement(1, 0) * endX   + matrix.getElement(1, 1) * endY  + matrix.getElement(1, 2) * endZ  + matrix.getElement(1, 3);
        float  endZ_  =matrix.getElement(2, 0) * endX   + matrix.getElement(2, 1) * endY  + matrix.getElement(2, 2) *  endZ + matrix.getElement(2, 3);

        float directionX_ = endX_  - startX_;
        float directionY_ = endY_ - startY_;
        float directionZ_ = endZ_  - startZ_;

        double[] coefficients = new double[3];

 	  	coefficients[0] = (float)Math.pow(startX_, 2) + (float)Math.pow(startY_, 2) + (float)Math.pow(startZ_, 2) - (float)Math.pow(radius, 2);
		coefficients[1] = 2 * (directionX_ * (startX_) + directionY_ * (startY_) + directionZ_ * (startZ_));
		coefficients[2] = (float)Math.pow(directionX_, 2) + (float)Math.pow(directionY_, 2) + (float)Math.pow(directionZ_, 2);

        coefficients[0] /= coefficients[2];
        coefficients[1] /= coefficients[2];
        coefficients[2] = 1.0;

        QuadraticPolynomial poly = (QuadraticPolynomial)Polynomial.build(coefficients);

        java.util.Vector<Double> roots = new java.util.Vector<Double>();
        poly.computeRoots(roots);

        float t = nearest(roots);

        return t;

	}

  	public void texturize(float[] textureCoords, float[] coords) {

    	handleErrorsX(textureCoords, coords);
        handleErrorsY(textureCoords, coords);

        adjustUCoordinate(textureCoords, coords);
        adjustVCoordinate(textureCoords, coords);

        textureCoords[0] /= (2.0f * (float)Math.PI);
        textureCoords[1] /= (float)Math.PI;

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

    	if(coords[1] > radius)
        	coords[1] = radius;

       	else if(coords[1] < -radius)
     		coords[1]= -radius;

 		textureCoords[1] = (float)Math.acos(coords[1] / radius);

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

	public void normal(float[] normalCoords, float[] posCoords) {

		normalCoords[0] = posCoords[0];
		normalCoords[1] = posCoords[1];
		normalCoords[2] = posCoords[2];

		float magnitude = (float)Math.sqrt(posCoords[0] * posCoords[0] + posCoords[1] * posCoords[1] + posCoords[2] * posCoords[2]);

		normalCoords[0] /= magnitude;
		normalCoords[1] /= magnitude;
		normalCoords[2] /= magnitude;

	}

	public int getSize() { return 1; }

	public void setRadius(float radius) { this.radius = radius; }

	public float getRadius() { return radius; }

}