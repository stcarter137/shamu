package shamu.analysis;

public class LinearPolynomial extends Polynomial {

	  public LinearPolynomial() {  this.coefficients = new double[2]; }

	  public LinearPolynomial(double[] coefficients) {

            this();

		    this.coefficients[0] = coefficients[0];
		    this.coefficients[1] = coefficients[1];


	   }

      public  void computeRoots(java.util.Vector<Double> roots) { roots.addElement(Double.valueOf(-coefficients[0])); }

   //   public double distanceSquared(double x, double y)  { return 0.0d; }

	   public void setCoefficient(double coefficient, double degree) {

		     if(degree == 0)
		        this.coefficients[0] = coefficient;

	   }

	   public double getCoefficient(double degree) {

		     if(degree == 0)
		        return this.coefficients[0];

		     else if(degree == 1)
		        return this.coefficients[1];

		      return 0;

		 }

         public int degree() { return 1; }

	 }




