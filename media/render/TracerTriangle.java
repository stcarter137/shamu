package shamu.media.render;

public class TracerTriangle extends TracerGeometry {

     protected float[] vertex1 = new float[3];

     protected float[] vertex2 = new float[3];

     protected float[] vertex3 = new float[3];

     public TracerTriangle(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3 ) {

           this.vertex1[0] = x1;
           this.vertex1[1] = y1;
           this.vertex1[2] = z1;

           this.vertex2[0] = x2;
           this.vertex2[1] = y2;
           this.vertex2[2] = z2;

           this.vertex3[0] = x3;
           this.vertex3[1] = y3;
           this.vertex3[2] = z3;

	 }

     public void texturize(float[] textureCoords, float[] coords) {}

     public float intersection(float startX, float startY, float startZ, float endX, float endY, float endZ, Matrix4D matrix) throws NullIntersectionException {

         float[] normalCoords = new float[3];

         float  startX_  = matrix.getElement(0, 0) * startX   + matrix.getElement(0, 1) * startY  + matrix.getElement(0, 2) * startZ  + matrix.getElement(0, 3);
	     float  startY_  = matrix.getElement(1, 0) * startX   + matrix.getElement(1, 1) * startY  + matrix.getElement(1, 2) * startZ  + matrix.getElement(1, 3);
         float  startZ_  = matrix.getElement(2, 0) * startX   + matrix.getElement(2, 1) * startY  + matrix.getElement(2, 2) * startZ  + matrix.getElement(2, 3);

         float  endX_ = matrix.getElement(0, 0) * endX + matrix.getElement(0, 1) *  endY + matrix.getElement(0, 2) * endZ + matrix.getElement(0, 3);
	     float  endY_ = matrix.getElement(1, 0) * endX + matrix.getElement(1, 1) * endY  + matrix.getElement(1, 2) * endZ + matrix.getElement(1, 3);
         float  endZ_  =matrix.getElement(2, 0) * endX  + matrix.getElement(2, 1) * endY  + matrix.getElement(2, 2) *  endZ + matrix.getElement(2, 3);

         float directionX_ = endX_  - startX_;
         float directionY_ = endY_ - startY_;
         float directionZ_ = endZ_  - startZ_;

         normal(normalCoords, null);

         float dot1 = normalCoords[0] * vertex1[0] + normalCoords[1] * vertex1[1] + normalCoords[2] * vertex1[2];
         float dot2 = normalCoords[0] * directionX_ + normalCoords[1] * directionY_ + normalCoords[2] * directionZ_;
         float dot3 = normalCoords[0] * startX_ + normalCoords[1] * startY_ + normalCoords[2] * startZ_;

         if(dot2 == 0.0f)
            throw new NullIntersectionException();

         float t = (dot1 - dot3) / dot2;

         float x = startX_ + t * directionX_;
         float y = startY_ + t * directionY_;
         float z = startZ_ + t  * directionZ_;

         float[] vertex0 = new float[] {x, y, z};

         float area1 = area(vertex1, vertex2, vertex0);
         float area2 = area(vertex1, vertex3, vertex0);
         float area3 = area(vertex2, vertex3, vertex0);

         float area0 = area(vertex1, vertex2, vertex3);

         if(area1 + area2 + area3 > (area0+ .000001f))
              throw new NullIntersectionException();

         return t;

	 }

     public float area(float[] vertex1, float[] vertex2, float[] vertex3) {

		  float x1 = vertex2[0] - vertex1[0];
		  float y1 = vertex2[1] - vertex1[1];
		  float z1 = vertex2[2] - vertex1[2];

		  float x2 = vertex3[0] - vertex1[0];
		  float y2 = vertex3[1] - vertex1[1];
		  float z2 = vertex3[2] - vertex1[2];

          float crossX = y1 * z2 - z1 * y2;
          float crossY = z1 * x2 - x1 * z2;
          float crossZ = x1 * y2 - y1 * x2;

          return (float)Math.sqrt(crossX * crossX + crossY * crossY + crossZ * crossZ) / 2.0f;

	  }

     public void normal(float[] normalCoords, float[] posCoords) {

            float u1 = vertex1[0] - vertex2[0];
            float u2 = vertex1[1] - vertex2[1];
            float u3 = vertex1[2] - vertex2[2];

            float w1 = vertex3[0] - vertex2[0];
            float w2 = vertex3[1] - vertex2[1];
            float w3 = vertex3[2] - vertex2[2];

           normalCoords[0] = u2 * w3 - u3 * w2;
           normalCoords[1] = u3 * w1 - u1 * w3;
           normalCoords[2] = u1 * w2 - u2 * w1;

           float magnitude = (float)Math.sqrt(normalCoords[0] * normalCoords[0] + normalCoords[1] * normalCoords[1] + normalCoords[2] * normalCoords[2] );

  		  normalCoords[0] /= -magnitude;
		  normalCoords[1] /= -magnitude;
		  normalCoords[2] /= -magnitude;



	 }

	 public int getSize() { return 3; }

}