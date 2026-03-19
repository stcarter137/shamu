package shamu.media.render;

public class BeamInterceptor extends Object3D {

   public RefractionStrategy refractionStrategy = new InfintesimalRefractiveStrategy();

   public Matrix4D worldToObjectMatrix = new Matrix4D();

   public TracerGeometry geometry;

   public BeamInterceptor() { super();  }

   public BeamInterceptor(TracerGeometry tracerGeometry) { this.geometry = tracerGeometry; }

   public BeamInterceptor(TracerGeometry tracerGeometry, RefractionStrategy refractStrategy) { 

      this(tracerGeometry); 

      this.refractionStrategy = refractStrategy;

   }   

//   protected void initializeCurveHighlights() {

//        curveHighlights = new java.util.LinkedList<LineHighlight>();

//        for(int i = 1; i < 128; i++)
//           curveHighlights.add(new shamu.media.render.LineHighlight(i / 128.0f, 0.0f, i / 128.0f , 1.0f, (TracerGeometry)this.getGeometry()));

//       for(int i = 1; i < 128; i++)
//            curveHighlights.add(new shamu.media.render.LineHighlight(0.0f, i / 128.0f, 1.0f, i / 128.0f, (TracerGeometry)this.getGeometry()));

//   }

   public float intersection(float startX, float startY, float startZ, float endX, float endY, float endZ) throws NullIntersectionException{

      return geometry.intersection(startX, startY, startZ, endX, endY, endZ, worldToObjectMatrix);

   }
/*
   public void swap() {

      float index = outerIndexOfRefraction;

      outerIndexOfRefraction = indexOfRefraction;
      indexOfRefraction = index;



   }
*/
   protected void traverse(RenderNotifier notifier, Matrix4D currentMatrix) {

       //Matrix4D worldToObjectMatrix = new Matrix4D();
       currentMatrix.invert(worldToObjectMatrix);

       java.util.LinkedList<BeamInterceptor> visuals = notifier.getVisuals();

       visuals.add(this);

       //java.util.LinkedList<TracerContext> visuals  = notifier.getVisuals();

       //visuals.add(new TracerContext(this, worldToObjectMatrix));

      //visuals.add(this);
      //visuals.add(worldToObjectMatrix);

    //  if(renderingAttributes.getCurveHighlightsAttribute() == RenderingConstantsI.INTEGRAL_CURVES_ENABLED)
    //     initializeCurveHighlights();

   }

   public Matrix4D getMatrix() { return worldToObjectMatrix; }

   public RefractionStrategy getRefractionStrategy() { return refractionStrategy; }

   public TracerGeometry getGeometry() { return geometry; }

}
