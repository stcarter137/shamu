package shamu.analysis;

public class QuinticPolynomial extends Polynomial {

	  public QuinticPolynomial() { coefficients = new double[6];  }

	  public QuinticPolynomial(double[] coefficients) {

            this();

		    this.coefficients[0] = coefficients[0];
		    this.coefficients[1] = coefficients[1];
		    this.coefficients[2] = coefficients[2];
            this.coefficients[3] = coefficients[3];
            this.coefficients[4] = coefficients[4];
            this.coefficients[5] = coefficients[5];

	   }

      public double computeFirstRoot() throws ComplexRootException {

		     QuarticPolynomial firstDifferential = (QuarticPolynomial)differentiate();
		      java.util.Vector<Double> extrema = new java.util.Vector<Double>();

		     firstDifferential.computeRoots(extrema);

		     double xs = -2.0d * supremum();

		     if(extrema.size() > 1) {

                 double[] extremaArray = new double[extrema.size()];

                 for(int k = 0; k < extrema.size(); k++)
                      extremaArray[k] = ((Double)extrema.elementAt(k)).doubleValue();

                 NumericalSort.sort(extremaArray, "insertion");

                 double mappedMin = map(extremaArray[0]);
                 double mappedMax = map(extremaArray[extremaArray.length - 1]);

                 if(mappedMax  < 0.0)
                      xs *= -1;

                 else if(mappedMin  < 0.0)
					  xs = (extremaArray[0] + extremaArray[extremaArray.length - 1]) / 2.0;

		    }

		    return NumericalAnalysis.newtonsAlgorithm(this, xs);

      }

      public  void computeRoots(java.util.Vector<Double>  roots)  {

            try {

   		         double firstRoot = computeFirstRoot();
		         roots.addElement(Double.valueOf(firstRoot));

		         double[] reducedCoefficients = new double[coefficients.length - 1];
		         NumericalAnalysis.hornersAlgorithm(firstRoot, coefficients, reducedCoefficients);

                 QuarticPolynomial reducedPolynomial = new QuarticPolynomial(reducedCoefficients);
                 reducedPolynomial.computeRoots(roots);

            } catch(ComplexRootException e) {System.out.println(e);}

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

		     else if(degree == 5)
		        this.coefficients[5] = coefficient;

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

		     else if(degree == 5)
		        return this.coefficients[5];

		      return 0;

		 }

         public int degree() { return 4; }

	 }




