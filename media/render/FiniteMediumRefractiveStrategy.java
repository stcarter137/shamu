package shamu.media.render;

public class FiniteMediumRefractiveStrategy implements RefractionStrategy  {

	public FiniteMediumRefractiveStrategy() {}

	public float adjustToPhysicalMedium(float dot, float[] refractionIndices, float[] normalIndices) {

		if(dot < 0f) {

			dot = -dot;

			normalIndices[0] *= -1;
			normalIndices[1] *= -1;
			normalIndices[2] *= -1;

			float temp = refractionIndices[0];

			refractionIndices[0] = refractionIndices[1];
			refractionIndices[1] = temp;

		}

		return dot;

	}

}