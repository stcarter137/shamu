package shamu.analysis;

public class CubicPolynomial extends Polynomial {

	  public CubicPolynomial() { coefficients = new double[4];  }

	  public CubicPolynomial(double[] coefficients) {

            this();

		    this.coefficients[0] = coefficients[0];
		    this.coefficients[1] = coefficients[1];
		    this.coefficients[2] = coefficients[2];
            this.coefficients[3] = coefficients[3];

	   }

      public  void computeRoots(java.util.Vector<Double>  roots)  {

             try {

   		         double firstRoot = computeFirstRoot();
		         roots.addElement(Double.valueOf(firstRoot));

		         double[] reducedCoefficients = new double[coefficients.length - 1];
		         NumericalAnalysis.hornersAlgorithm(firstRoot, coefficients, reducedCoefficients);

                 QuadraticPolynomial reducedPolynomial = new QuadraticPolynomial(reducedCoefficients);
                 reducedPolynomial.computeRoots(roots);

             } catch(ComplexRootException e) {}

	   }

       public double computeFirstRoot() throws ComplexRootException {

              double xs = 2.0d * supremum();

              double xinflection = - coefficients[2] / 3.0d;

              double discr = map(xinflection);

              if(discr > -0.000001d)
                  xs = -xs;

		      return NumericalAnalysis.newtonsAlgorithm(this, xs);

	  }

	  public void setCoefficient(double coefficient, double degree) {

		     if(degree == 0)
		        this.coefficients[0] = coefficient;

		     else if(degree == 1)
		        this.coefficients[1] = coefficient;

		     else if(degree == 2)
		        this.coefficients[2] = coefficient;

	   }

	   public double getCoefficient(double degree) {

		     if(degree == 0)
		        return this.coefficients[0];

		     else if(degree == 1)
		        return this.coefficients[1];

		     else if(degree == 2)
		        return this.coefficients[2];

		     else if(degree == 3)
		        return this.coefficients[3];

		      return 0;

		 }

         public int degree() { return 3; }

	 }




