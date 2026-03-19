package shamu.media.render;

public class TracerPlane extends TracerMongePatch {

     public TracerPlane() { super(); }

     public TracerPlane(float xmin, float zmin, float xmax, float zmax)  {

		  super(xmin, zmin, xmax, zmax);

	 }

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

         float t = -startY_ / directionY_;

         float x = startX_ + directionX_ * t;
         float z = startZ_ + directionZ_ * t;

         if((x < umin) || (z < vmin) || (x > umax) || (z > vmax))
            throw new NullIntersectionException();

        return t;

	 }

     public void normal(float[] normalCoords, float[] posCoords) {

        normalCoords[0] =  0.0f;
        normalCoords[1] = -1.0f;
        normalCoords[2] =  0.0f;
        
     }

     public float mapY(float u, float v) { return 0.0f; }

     public float partialUMapY(float u, float v) { return 0.0f; }

     public float partialVMapY(float u, float v) { return 0.0f; }

     public int getSize() { return 0; } 

}