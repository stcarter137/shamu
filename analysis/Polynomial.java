package shamu.analysis;

public abstract class Polynomial extends AnalyticFunction {

    public double[] coefficients;

    public double tmin = 0.0;

    public double tmax = 512.0;

	public Polynomial() {}

    public static Polynomial build(double[] coefficients) {

		   int n = coefficients.length;

           Polynomial poly = null;

		   switch(n) {

			     case 1:  return new ConstantPolynomial(coefficients);

			     case 2:  return new LinearPolynomial(coefficients);

			     case 3:  return new QuadraticPolynomial(coefficients);

			     case 4:  return new CubicPolynomial(coefficients);

			     case 5:  return new QuarticPolynomial(coefficients);

			     case 6:  return new QuinticPolynomial(coefficients);

                 default:  return null;

		   }

    }

    public AnalyticFunction differentiate() {

		  double[] derivativeCoefficients = new double[coefficients.length - 1];

		  for(int j = 0; j < derivativeCoefficients.length; j++)
			   derivativeCoefficients[j] = (j + 1)  * coefficients[j + 1];

          return Polynomial.build(derivativeCoefficients);

	 }

    public double map(double t) {

		 double[] reducedCoefficients = new double[coefficients.length - 1];

		 double s = NumericalAnalysis.hornersAlgorithm(t, coefficients, reducedCoefficients);

//double sum=0;

	//  for(int j = 0; j < coefficients.length; j++)
	//	  sum += coefficients[j] * Math.pow(t, j);

//System.out.println(s + ",    " + sum);

return s;

    }

    public void add(Polynomial addend1, Polynomial addend2) {

		  for(int j = 0; j < coefficients.length; j++) {

		      try {  this.coefficients[j] += addend1.coefficients[j]; }
		      catch(ArrayIndexOutOfBoundsException e) { }

              try {  this.coefficients[j] += addend2.coefficients[j]; }
		      catch(ArrayIndexOutOfBoundsException e) {}

		 }

    }

    public void subtract(Polynomial subtend1, Polynomial subtend2) {

		  for(int j = 0; j < coefficients.length; j++) {

		      try {  this.coefficients[j] += subtend1.coefficients[j]; }
		      catch(ArrayIndexOutOfBoundsException e) {}

              try {  this.coefficients[j] -= subtend2.coefficients[j]; }
		      catch(ArrayIndexOutOfBoundsException e) {}

		 }

    }

    public void multiply(Polynomial factor1, Polynomial factor2) {

		  int n = coefficients.length;

	      for(int j = 0; j < n; j++) {
	          for(int k =0; k <= j; k++) {

				   try {

 				      double product = 1.0d;

                      product *= factor1.coefficients[k];
				      product *= factor2.coefficients[j -k];

	                  this.coefficients[j] += product;

				   } catch(ArrayIndexOutOfBoundsException e) {}

			   }

		  }

    }

	public double supremum() {

		   double max = Math.max(Math.abs(coefficients[0]), 1.0d);

           for(int j = 1; j < coefficients.length; j++)
		       max = Math.max(Math.abs(coefficients[j]), max);

		   return max;

	}

    public double distanceSquared(double x, double y) throws CurveExteriorException {

           Tuple positionTuple= new Tuple(this);
           Tuple differentialTuple = positionTuple.differentiate();

           Polynomial leftPolynomial = differentialTuple.innerProduct(positionTuple);
           Polynomial rightPolynomial = differentialTuple.innerProduct(new Tuple(x, y));

           java.util.Vector<Double> solutionSet = new java.util.Vector<Double>();
           PolynomialSolver.solve(leftPolynomial, rightPolynomial, solutionSet);

           double[] realSolutions = new double[solutionSet.size()];

           for(int j = 0; j < realSolutions.length; j++) {

                double t  = ((Double)solutionSet.elementAt(j)).doubleValue();

               if(t < tmin || t > tmax)
                   throw new CurveExteriorException();

                realSolutions[j] = Math.pow(x - t, 2) + Math.pow(y - this.map(t), 2);

	       }

		   return NumericalAnalysis.minimum(realSolutions);

    }

    abstract public int degree();

    abstract public  void computeRoots(java.util.Vector<Double> roots);

	abstract public  double getCoefficient(double degree) ;

    abstract void setCoefficient(double coefficient, double degree);

}