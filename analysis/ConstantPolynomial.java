package shamu.analysis;

public class ConstantPolynomial extends Polynomial {

      public ConstantPolynomial() { this.coefficients = new double[1]; }

	  public ConstantPolynomial(double[] coefficients) {

		     this();

		     this.coefficients[0] = coefficients[0];

      }

      public  void computeRoots(java.util.Vector  roots)  {  }

     // public double distanceSquared(double x, double y)  { return 0.0d; }

	   public void setCoefficient(double coefficient, double degree){ }

	   public double getCoefficient(double degree) {

		     if(degree == 0)
		        return this.coefficients[0];

		      return 0;

		 }

         public int degree() { return 0; }

	 }