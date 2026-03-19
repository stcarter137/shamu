package shamu.media.render;

/** 
 * 
 *  @author  Scott T. Carter
 *  @version 1.4
 */

public class PolarGrid extends ParametricProcedure {

   public PolarGrid(float maxRadius) { super(0.0f, 0.0f, maxRadius, (float)(2 * Math.PI)); }

   public float mapZ(float u1, float u2) { return u1 * (float)Math.cos(u2); }

   public float mapY(float u1, float u2) { return 0.0f; }

   public float mapX(float u1, float u2) { return u1 * (float)Math.sin(u2); } 

   public float partialU1MapZ(float u1, float u2) { return (float)Math.cos(u2); }

   public float partialU1MapY(float u1, float u2) { return 0.0f; }
   
   public float partialU1MapX(float u1, float u2) { return (float)Math.sin(u2); }   

    public float partialU2MapZ(float u1, float u2) { return -u1 * (float)Math.sin(u2); }

   public float partialU2MapY(float u1, float u2) { return 0.0f; }
   
   public float partialU2MapX(float u1, float u2) { return u1 * (float)Math.cos(u2); }

}     
