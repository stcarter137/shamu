package shamu.analysis;

public class QuarticPolynomial extends Polynomial {

	  public QuarticPolynomial() { coefficients = new double[5];  }

	  public QuarticPolynomial(double[] coefficients) {

            this();

		    this.coefficients[0] = coefficients[0];
		    this.coefficients[1] = coefficients[1];
		    this.coefficients[2] = coefficients[2];
            this.coefficients[3] = coefficients[3];
            this.coefficients[4] = coefficients[4];

	  }

      public  void computeRoots(java.util.Vector<Double>  roots)  {

            try {

   		         double firstRoot = computeFirstRoot();
		         roots.addElement(Double.valueOf(firstRoot));

		         double[] reducedCoefficients = new double[coefficients.length - 1];
		         NumericalAnalysis.hornersAlgorithm(firstRoot, coefficients, reducedCoefficients);

                 CubicPolynomial reducedPolynomial = new CubicPolynomial(reducedCoefficients);
                 reducedPolynomial.computeRoots(roots);

            } catch(ComplexRootException e) {}

	  }

      public double computeFirstRoot() throws ComplexRootException {

	  	    double[]  extrema = checkForRealRoots();

            double mappedExtrema = this.map(extrema[0]);

            double xs = -2.0d * supremum();

            if(mappedExtrema > 0.0)
                xs *= -1;

		    return NumericalAnalysis.newtonsAlgorithm(this, xs);

	  }

	  public double[]  checkForRealRoots() throws ComplexRootException {

		    CubicPolynomial differential = (CubicPolynomial)differentiate();

            differential.coefficients[0] /= differential.coefficients[3];
            differential.coefficients[1] /= differential.coefficients[3];
            differential.coefficients[2] /= differential.coefficients[3];
            differential.coefficients[3] /= differential.coefficients[3];

		    java.util.Vector<Double> extrema = new java.util.Vector<Double>();
		   	differential.computeRoots(extrema);

		    if(!checkForNegativeValues(extrema))
		  	   throw new ComplexRootException();

            double[] extremaArray = new double[extrema.size()];

            for(int k = 0; k < extrema.size(); k++)
                 extremaArray[k] = ((Double)extrema.elementAt(k)).doubleValue();

           NumericalSort.sort(extremaArray, "insertion");

           return extremaArray;

	  }

	  public boolean checkForNegativeValues(java.util.Vector<Double> values) {

             for(int k = 0; k < values.size(); k++) {

                  double mapped = this.map((  (Double)values.elementAt(k)    ).doubleValue() );

                  if(mapped <= 0.0)
                      return true;

			  }

			  return false;

	  }

	  public void setCoefficient(double coefficient, double degree) {

		     if(degree == 0)
		         this.coefficients[0] = coefficient;

		     else if(degree == 1)
		         this.coefficients[1] = coefficient;

		     else if(degree == 2)
		         this.coefficients[2] = coefficient;

		     else if(degree == 3)
		         this.coefficients[3] = coefficient;

		     else if(degree == 4)
		         this.coefficients[4] = coefficient;

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

		    else if(degree == 4)
		        return this.coefficients[4];

		     return 0;

	   }

       public int degree() { return 4; }

}




