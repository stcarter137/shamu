package shamu.media.render;

public class BeamDetector implements TracerFunctional {

	public BeamDetector() {}

    public BeamInterceptor hit(java.util.Iterator<BeamInterceptor> objectIterator, float startX,float  startY, float startZ, float endX, float endY, float endZ, float[] intersectCoords) throws NullIntersectionException {

        float tmin = -1.0f;

        BeamInterceptor nearestInterceptor = null;

        while(objectIterator.hasNext()) {  

            BeamInterceptor interceptor =  objectIterator.next();

            try {

                float t = interceptor.intersection(startX, startY, startZ, endX, endY, endZ);

                if((t > 0)  && (tmin < 0.0f || t <= tmin)) {
                        
                	tmin = t;

                    nearestInterceptor = interceptor;

                    intersectCoords[0] = startX  + t * (endX - startX);
                    intersectCoords[1] = startY  + t * (endY - startY);
                    intersectCoords[2] = startZ  + t * (endZ - startZ);
                    
             	}


            } catch(Exception e) {}

		}

           if(tmin < 0.0f)
              throw new NullIntersectionException();

           return nearestInterceptor;

     }

}