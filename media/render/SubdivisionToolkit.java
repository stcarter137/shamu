package shamu.media.render;

/** Abstract toolkit class responsible for creating modeling objects based on a concrete subdivision procedure.
 *
 *  @author  Scott T. Carter
 *  @version 1.4
 */

abstract public class SubdivisionToolkit implements ToolkitI {

   /**
    * The number of subdivisions used for procedural modeling by this subdivision toolkit.
    */

   protected int subdivisionLevels;

   /**
    * The subdivision procedure used for procedural modeling by this subdivision toolkit.
    */

   protected SubdivisionProcedure subdivisionProcedure;

   /**
    * Constructs a subdivision toolkit having the underlying subdivision procedure specified by the subdivision procedure argument, and the number
    * of subdivision levels specified by the integer argument.
    *
    * @param subdivisionProcedure the subdivision procedure specifying this subdivision toolkit.
    * @param subdivisionLevels the number of subdivision levels specifying this subdivision toolkit.
    */

   protected SubdivisionToolkit(SubdivisionProcedure subdivisionProcedure, int subdivisionLevels) {

	  this.subdivisionProcedure = subdivisionProcedure;

      this.subdivisionLevels = subdivisionLevels;

   }

   public void computeNormal(float[] u_k, float[] normal) {}

   /**
    * Returns the subdivision procedure associated with this subidivision toolkit.
    *
    * @return the subdivision procedure associated with this subdivision toolkit.
    */

   public SubdivisionProcedure getSubdivisionProcedure() { return subdivisionProcedure; }

   /**
    * Returns the number of subdivision levels associated with this subdivision toolkit.
    *
    * @return the number of subdivision levels associated with this subdivision toolkit.
    */

   public int getSubdivisionLevels() { return subdivisionLevels; }

   /**
    * Sets the subdivision procedure associated with this subdivision toolkit to the subdivision procedure argument.
    *
    * @param subdivisionProcedure the subdivision procedure that the subdivision procedure associated with this subdivision toolkit is set to.
    */

   public void setSubdivisionProcedure(SubdivisionProcedure subdivisionProcedure) { this.subdivisionProcedure = subdivisionProcedure; }

   /**
    * Sets the number of subdivision levels associated with this subdivision toolkit to the integer argument.
    *
    * @param subdivisionLevels the value that the number of subdivision levels associated with this subdivision toolkit is set to.
    */

   public void setSubdivisionLevels(int subdivisionLevels) { this.subdivisionLevels = subdivisionLevels; }

}

