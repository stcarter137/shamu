package shamu.media.render;

public class TracerContext {

	protected BeamInterceptor object3D;

	protected Matrix4D inverseMatrix;

	public TracerContext(BeamInterceptor object3D, Matrix4D inverseMatrix) {

		this.object3D = object3D;
		
		this.inverseMatrix = inverseMatrix;

	}

	public float intersection(float startX, float startY, float startZ, float endX, float endY, float endZ) throws NullIntersectionException {

	//	TracerGeometry geometry = (TracerGeometry)object3D.getGeometry();

		return this.object3D.getGeometry()
					   		.intersection(startX, startY, startZ, endX, endY, endZ, this.inverseMatrix);

	}

	public void update(float t, float startX, float startY, float startZ, float dx, float dy, float dz, Object[] data) {

		data[0]= this.object3D;
        data[1] = this.inverseMatrix;
        data[2] = new float[] {startX  + t * dx,
    						   startY  + t * dy,
    						   startZ  + t * dz};

	}

	public BeamInterceptor getInterceptor() { return object3D; }

	public Matrix4D getMatrix() { return inverseMatrix; }

}