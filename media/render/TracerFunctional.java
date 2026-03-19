package shamu.media.render;

public interface TracerFunctional {

   public BeamInterceptor hit(java.util.Iterator<BeamInterceptor> objectIterator, float startX, float startY, float startZ, float endX, float endY, float endZ, float[] data) throws NullIntersectionException;

}