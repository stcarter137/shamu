package shamu.media.render;

/** Abstract toolkit class responsible for creating geometry data based on a parametric procedure.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

abstract public class ParametricToolkit implements ToolkitI {

   /**
    * The parametric procedure used for procedural modeling by this parametric toolkit.
    */

   protected ParametricProcedure parametricProcedure;

   /**
    * Constructs a parametric toolkit having the underlying parametric procedure specified by the parametric procedure argument.
    *
    * @param parametricProcedure the parametric procedure specifying this parametric toolkit.
    */

   protected ParametricToolkit(ParametricProcedure parametricProcedure) { this.parametricProcedure = parametricProcedure; }

   /**
    * Adds the vertex, vertex normal, vertex tangent, and vertex texel at the point on this parametric surface determined by the floating arguments; the x, y, z mappings;
	* the x, y, z partial derivative mappings with respect to the u1 local variable; and the x, y, z partial derivative mappings with respect to the u2 local variable
	* to the vertex array, vertex normal array, and vertex texel array associated with this parametric surface.
	*
	* @param u1 the local variable u1 argument that is mapped to a point on this parametric surface.
	* @param u2 the local variable u2 argument that is mapped to a point on this parametric surface.
	*/

   protected void generateData(Mesh mesh, float u1, float u2) {

	  try {

	     mesh.addVertex(parametricProcedure.mapX(u1, u2), parametricProcedure.mapY(u1, u2), parametricProcedure.mapZ(u1, u2));
	     mesh.addTexel((u1 - parametricProcedure.lowerBoundsU1) / (parametricProcedure.upperBoundsU1 - parametricProcedure.lowerBoundsU1), (u2 - parametricProcedure.lowerBoundsU2) / (parametricProcedure.upperBoundsU2 - parametricProcedure.lowerBoundsU2));

	     float tangentX1 = parametricProcedure.partialU1MapX(u1, u2);
	     float tangentY1 = parametricProcedure.partialU1MapY(u1, u2);
	     float tangentZ1 = parametricProcedure.partialU1MapZ(u1, u2);

	     float tangentX2 = parametricProcedure.partialU2MapX(u1, u2);
	     float tangentY2 = parametricProcedure.partialU2MapY(u1, u2);
	     float tangentZ2 = parametricProcedure.partialU2MapZ(u1, u2);

	     mesh.addTangentU1(tangentX1, tangentY1, tangentZ1);
	     mesh.addTangentU2(tangentX2, tangentY2, tangentZ2);

	     float normalX =  0.0f;
        float normalY = -1.0f;
	     float normalZ =  0.0f;

         if((tangentX1 != 0 || tangentY1 != 0 || tangentZ1 != 0) && (tangentX2 != 0 || tangentY2 != 0 || tangentZ2 != 0)) {

	        normalX = tangentY2 * tangentZ1 - tangentZ2 * tangentY1;
	        normalY = tangentZ2 * tangentX1 - tangentX2 * tangentZ1;
	        normalZ = tangentX2 * tangentY1 - tangentY2 * tangentX1;

	        float normalFactor   = (float)Math.sqrt(normalX * normalX + normalY * normalY + normalZ * normalZ);

	        normalX /= normalFactor;
	        normalY /= normalFactor;
	        normalZ /= normalFactor;

	     }

	     mesh.addNormal(normalX, normalY, normalZ);

	  } catch(Exception e) {}

   }

   public void computeNormal(float[] u_k, float[] normal) { 

   	float u1 = (parametricProcedure.upperBoundsU1 - parametricProcedure.lowerBoundsU1) * u_k[0] + parametricProcedure.lowerBoundsU1;
   	float u2 = (parametricProcedure.upperBoundsU2 - parametricProcedure.lowerBoundsU2) * u_k[1] + parametricProcedure.lowerBoundsU2;

   	parametricProcedure.computeNormal(u1, u2, normal);

   }

   /**
    * Returns the parametric procedure associated with this parametric toolkit.
    *
    * @return the parametric procedure associated with this parametric toolkit.
    */

   public ParametricProcedure getParametricProcedure() { return parametricProcedure; }

   /**
    * Sets the parametric procedure associated with this parametric toolkit to the parametric procedure argument.
    *
    * @param parametricProcedure the parametric procedure that the parametric procedure associated with this parametric toolkit is set to.
    */

   public void setParametricProcedure(ParametricProcedure parametricProcedure) { this.parametricProcedure = parametricProcedure; }

}