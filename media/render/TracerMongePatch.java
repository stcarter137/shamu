package shamu.media.render;

abstract public class TracerMongePatch extends TracerSurface {

     public TracerMongePatch() { super() ;}

     public TracerMongePatch(float xmin, float zmin, float xmax, float zmax) {

		   this.umin = xmin;
		   this.vmin = zmin;

		   this.umax = xmax;
           this.vmax = zmax;

	 }

      public float mapX(float u, float v) { return u; }

      public float  mapZ(float u, float v) { return v; }

      public float partialUMapX(float u, float v) { return 1.0f; }

      public float partialUMapZ(float u, float v) { return 0.0f; }

      public float partialVMapX(float u, float v) { return 0.0f; }

      public float partialVMapZ(float u, float v) { return 1.0f; }

     public void normal(float[] normalCoords, float[] posCoords) {

		 normalCoords[0] =   partialUMapY(posCoords[0], posCoords[2]);
		 normalCoords[1] =  -1.0f;
		 normalCoords[2] =  partialVMapY(posCoords[0], posCoords[2]);

		 float magnitude = (float)Math.sqrt(normalCoords[0] * normalCoords[0] +normalCoords[1] * normalCoords[1] + normalCoords[2] * normalCoords[2]);

		 normalCoords[0] /= magnitude;
		 normalCoords[1] /= magnitude;
		 normalCoords[2] /= magnitude;

	 }

     public void filter(java.util.Vector<Double> reals, float startX, float startZ, float directionX, float directionZ) {

         java.util.ListIterator iterator = reals.listIterator(0);

         while(iterator.hasNext()) {

			  Double nextReal  = (Double)iterator.next();

			  double value = nextReal.doubleValue();

              float xt = startX + (float)value *  directionX;
              float zt = startZ + (float)value  *  directionZ;

              if(xt <  umin || xt >  umax || zt < vmin || zt > vmax)
                 iterator.remove();

	    }

	}

     public void texturize(float[] textureCoords, float[] coords) {

		 textureCoords[0] = (coords[0] - umin) / (umax - umin);
         textureCoords[1] = (coords[2] - vmin) / (vmax - vmin);

     }

     public float jacobian(LineHighlight highlight, float[] textCoords, float[] posCoords) {

		 float directionX = highlight.x2 - highlight.x1;
		 float directionZ = highlight.y2 - highlight.y1;

		 float squareX = (float)Math.pow(directionX, 2);
		 float squareZ = (float)Math.pow(directionZ, 2);

		 float alpha = squareX / (squareX + squareZ);

         float factor1 = (float)Math.pow(this.partialUMapY(posCoords[0], posCoords[2]), 2);
         float factor2 = (float)Math.pow(this.partialVMapY(posCoords[0], posCoords[2]), 2);

		 return 1.0f + (1.0f - alpha) * factor1 +  alpha * factor2;

     }

}