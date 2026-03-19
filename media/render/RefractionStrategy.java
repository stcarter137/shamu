package shamu.media.render;

public interface RefractionStrategy  {

	public float adjustToPhysicalMedium(float dot, float[] refractionIndices, float[] normalIndices);

}