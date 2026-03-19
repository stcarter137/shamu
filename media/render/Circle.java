package shamu.media.render;

public class Circle extends TracerSphere {

	 public float radius;

     public Circle() {

          super(0.0f, 0.0f, 2.0f * (float)Math.PI, (float)Math.PI);

		 this.radius = 0;

	}

	 public Circle(float radius) {

         super(0.0f, 0.0f, 2.0f * (float)Math.PI, (float)Math.PI);

		 this.radius = radius;

	 }

	 public float intersection(float startX, float startY, float startZ, float directionX, float directionY, float directionZ, int flag) throws NullIntersectionException {

          float t1 = -startZ / directionZ;
          float t2 = radius / (float)Math.sqrt(Math.pow(directionX, 2) + Math.pow(directionY, 2));

          if(Math.abs(t1 - t2) < .01f)
             return t1;

           throw new NullIntersectionException();


	 }

     public void normal(float[] normalCoords, float[] posCoords) {

		 normalCoords[0] =  0.0f;
		 normalCoords[1] =  0.0f;
		 normalCoords[2] = -1.0f;

	 }


	 public int getSize() { return 1; }

	 public void setRadius(float radius) { this.radius = radius; }

	 public float getRadius() { return radius; }



}