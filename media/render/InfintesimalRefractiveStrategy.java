package shamu.media.render;

public class InfintesimalRefractiveStrategy implements RefractionStrategy  {

	public InfintesimalRefractiveStrategy() {}

	public float adjustToPhysicalMedium(float dot, float[] refractionIndices, float[] normalIndices) {

		if(dot < 0f) {

			normalIndices[0] *= -1;
			normalIndices[1] *= -1;
			normalIndices[2] *= -1;

			dot = -dot;

		}

		return dot;

	}

}