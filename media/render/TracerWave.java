package shamu.media.render;

public class TracerWave extends TracerMongePatch {

     protected float amplitude;

     protected float wavenumberX;

     protected float wavenumberZ;

     public TracerWave() { super(); }

     public TracerWave(float amplitude, float wavenumberX, float wavenumberZ, float xmin, float zmin, float xmax, float zmax)  {

		  super(xmin, zmin, xmax, zmax);

		  this.amplitude = amplitude;

		  this.wavenumberX = wavenumberX;
		  this.wavenumberZ = wavenumberZ;

	 }

     public void texturize(float[] textureCoords, float[] coords) {}

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

         float tmax = (amplitude - startY_) / directionY_;

         return tmax;

	 }

     public int getSize() { return 2; }

     public float mapY(float u, float v) { return amplitude * (float)(Math.sin(wavenumberX * u) * Math.sin(wavenumberZ * v)); }

     public float partialUMapY(float u, float v) { return amplitude * wavenumberX * (float)(Math.cos(wavenumberX *  u) * Math.sin(wavenumberZ * v)); }

     public float partialVMapY(float u, float v) { return amplitude * wavenumberZ * (float)(Math.sin(wavenumberX * u) * Math.cos(wavenumberZ * v)); }

     public float getAmplitude() { return amplitude; }

     public float getWavenumberX() { return wavenumberX; }

     public float getWavenumberZ() { return wavenumberZ; }

     public void setAmplitude(float amplitude) { this.amplitude = amplitude; }

     public void setWavenumberX(float wavenumberX) { this.wavenumberX = wavenumberX; }

     public void setWavenumberZ(float wavenumberZ) { this.wavenumberZ = wavenumberZ; }

}