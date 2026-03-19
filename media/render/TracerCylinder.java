package shamu.media.render;

public class TracerCylinder extends TracerGeometry {

	 protected float radius;

     protected float height;

     public TracerCylinder() {this.radius = 0;}

	 public TracerCylinder(float radius, float height) {

		 this.radius = radius;
		 this.height = height;

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

         float r = (float)Math.sqrt(directionX_ * directionX_ + directionY_* directionY_ + directionZ_ * directionZ_);

         directionX_ /= r ;
         directionY_ /= r;
         directionZ_ /= r;


//         float  directionX_ = matrix.getElement(0, 0) * directionX   + matrix.getElement(0, 1) *  directionY  + matrix.getElement(0, 2) * directionZ;
//	     float  directionY_ = matrix.getElement(1, 0) * directionX   + matrix.getElement(1, 1) * directionY  + matrix.getElement(1, 2) *  directionZ;
//         float  directionZ_  = matrix.getElement(2, 0) * directionX   + matrix.getElement(2, 1) * directionY  + matrix.getElement(2, 2) *  directionZ;

//         float  startX_  = matrix.getElement(0, 0) * startX   + matrix.getElement(0, 1) * startY  + matrix.getElement(0, 2) * startZ  + matrix.getElement(0, 3);
//	     float  startY_  = matrix.getElement(1, 0) * startX   + matrix.getElement(1, 1) * startY  + matrix.getElement(1, 2) * startZ  + matrix.getElement(1, 3);
//         float  startZ_  = matrix.getElement(2, 0) * startX   + matrix.getElement(2, 1) * startY  + matrix.getElement(2, 2) * startZ  + matrix.getElement(2, 3);

		  float a = (float)Math.pow(directionX_, 2) + (float)Math.pow(directionZ_, 2);
		  float b = 2 * (directionX_ * (startX_) + directionZ_ * (startZ_));
 		  float c = (float)Math.pow(startX_, 2) + (float)Math.pow(startZ_, 2) - (float)Math.pow(radius, 2);

 		  float discriminant = (float)Math.pow(b, 2) - 4 * a * c;

 		  if(discriminant < 0.0f)
 		     throw new NullIntersectionException();

           float t = (-b - (float)Math.sqrt(discriminant)) / (2 * a);

           float yt = startY_ + t  * directionY_;

           //if(yt  > height)
           //   throw new NullIntersectionException();

 	   	     return (-b - (float)Math.sqrt(discriminant)) / (2 * a);

	 }

     public void normal(float[] normalCoords, float[] posCoords) {

		 normalCoords[0] = posCoords[0];
		 normalCoords[1] = 0;
		 normalCoords[2] = posCoords[2];

		 float magnitude = (float)Math.sqrt(normalCoords[0] * normalCoords[0] +normalCoords[1] * normalCoords[1] + normalCoords[2] * normalCoords[2]);

		 normalCoords[0] /= magnitude;
		 normalCoords[1] /= magnitude;
		 normalCoords[2] /= magnitude;

	 }

	 public int getSize() { return 1; }

	 public void setRadius(float radius) { this.radius = radius; }

	 public float getRadius() { return radius; }

}