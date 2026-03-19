package shamu.media.render;

public class TracerSphericalLens extends TracerGeometry {

	protected float radius;

	protected float phi;

	public TracerSphericalLens(float radius, float phi) {

		this.radius = radius;
		this.phi = phi;

	}

   public float intersection(float startX, float startY, float startZ, float endX, float endY, float endZ, Matrix4D matrix) throws NullIntersectionException {

   		TracerSphere testSphere = new TracerSphere(this.radius);

   		float t = testSphere.intersection(startX, startY, startZ, endX, endY, endZ, matrix);

    	float  startX_  = matrix.getElement(0, 0) * startX   + matrix.getElement(0, 1) * startY  + matrix.getElement(0, 2) * startZ  + matrix.getElement(0, 3);
	    float  startY_  = matrix.getElement(1, 0) * startX   + matrix.getElement(1, 1) * startY  + matrix.getElement(1, 2) * startZ   + matrix.getElement(1, 3);
        float  startZ_  = matrix.getElement(2, 0) * startX   + matrix.getElement(2, 1) * startY  + matrix.getElement(2, 2) * startZ   + matrix.getElement(2, 3);

        float  endX_ = matrix.getElement(0, 0) * endX   + matrix.getElement(0, 1) *  endY + matrix.getElement(0, 2) * endZ  + matrix.getElement(0, 3);
	    float  endY_ = matrix.getElement(1, 0) * endX   + matrix.getElement(1, 1) * endY  + matrix.getElement(1, 2) * endZ  + matrix.getElement(1, 3);
        float  endZ_  =matrix.getElement(2, 0) * endX   + matrix.getElement(2, 1) * endY  + matrix.getElement(2, 2) *  endZ + matrix.getElement(2, 3);

        float directionX_ = endX_  - startX_;
        float directionY_ = endY_ - startY_;
        float directionZ_ = endZ_  - startZ_;

		//float factor = (float)Math.sqrt((float)Math.pow(directionX_, 2) + (float)Math.pow(directionY_, 2) + (float)Math.pow(directionZ_, 2));

		//directionX_ /= factor;
		//directionY_ /= factor;
		//directionZ_ /= factor;

   		float z = t * directionZ_ + startZ_;
   		float testPhi = (float)Math.acos(-z / this.radius);

   		if(testPhi > phi || testPhi < 0) {
   			throw new NullIntersectionException();
   		}

   		return t; 

   }

   public void texturize(float[] textureCoords, float[] coords) {  new TracerSphere(this.radius).texturize(textureCoords, coords); }

   public void normal(float[] normalCoords, float[] posCoords) { new TracerSphere(this.radius).normal(normalCoords, posCoords); }

   public int getSize() { return new TracerSphere(this.radius).getSize(); }

   public float getRadius() { return radius; }

   public float getPhi() { return phi; }

   public void setRadius(float radius) { this.radius = radius; }

   public void setPhi(float phi) { this.phi = phi; }


}