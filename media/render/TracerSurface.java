package shamu.media.render;

import shamu.analysis.*;

abstract public class TracerSurface extends TracerGeometry {

      float umin;
      float vmin;

      float umax;
      float vmax;

      protected TracerSurface() { super(); }

      abstract public float mapX(float u, float v);

      abstract public float mapY(float u, float v);

      abstract public float mapZ(float u, float v);

      abstract public float partialUMapX(float u, float v);

      abstract public float partialUMapY(float u, float v);

      abstract public float partialUMapZ(float u, float v);

      abstract public float partialVMapX(float u, float v);

      abstract public float partialVMapY(float u, float v);

      abstract public float partialVMapZ(float u, float v);

      public float jacobian(LineHighlight highlight, float[] textureCoords, float[] posCoords) {

            float[] localCoords = new float[2];

            getLocalCoordinates(textureCoords, localCoords);

		    float directionU = highlight.x2 - highlight.x1;
		    float directionV = highlight.y2 - highlight.y1;

		    float squareU= (float)Math.pow(directionU, 2);
		    float squareV = (float)Math.pow(directionV, 2);

		    float alpha = squareU / (squareU + squareV);

            float factor1 = (float)(Math.pow(this.partialUMapX(localCoords[0],  localCoords[1]), 2) +  Math.pow(this.partialUMapY(localCoords[0],  localCoords[1]), 2) +  Math.pow(this.partialUMapZ(localCoords[0], localCoords[1]), 2));
            float factor2 = (float)(Math.pow(this.partialVMapX(localCoords[0],  localCoords[1]), 2) +  Math.pow(this.partialVMapY(localCoords[0],  localCoords[1]), 2) +  Math.pow(this.partialVMapZ(localCoords[0],  localCoords[1]), 2));

		    return (1-alpha) * factor1 + alpha * factor2;

		}

        public void getLocalCoordinates(float[] textureCoords, float[] localCoords) {

            localCoords[0] = (umax - umin) * textureCoords[0];
            localCoords[1] = (vmax - vmin) * textureCoords[1];

	    }

	 public float getUMin() { return umin; }

	 public float getVMin() { return vmin; }

	 public float getUMax() { return umax; }

	 public float getVMax() { return vmax; }

     public void setUMin(float umin) { this.umin = umin; }

     public void setVMin(float vmin) { this.vmin = vmin; }

     public void setUMax(float umax) { this.umax = umax; }

     public void setVMax(float vmax) { this.vmax = vmax; }

}