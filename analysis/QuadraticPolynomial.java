package shamu.analysis;

public class QuadraticPolynomial extends Polynomial {

	  public QuadraticPolynomial() {  this.coefficients = new double[3]; }

	  public QuadraticPolynomial(double[] coefficients) {

            this();

		    this.coefficients[0] = coefficients[0];
		    this.coefficients[1] = coefficients[1];
		    this.coefficients[2] = coefficients[2];

	   }

       public  void computeRoots(java.util.Vector<Double> roots)  {

              double discr = Math.pow(coefficients[1], 2) - 4 * coefficients[0];

             if(discr > -0.000001d) {

                 double xs = 2.0d * supremum();

double first =  NumericalAnalysis.newtonsAlgorithm(this,  xs);
double second = NumericalAnalysis.newtonsAlgorithm(this,  -xs);



                 roots.addElement(Double.valueOf(first));
                 roots.addElement(Double.valueOf(second));

		     }

	  }

  /*    public double distanceSquared(double x, double y) {

		     java.util.Vector roots = new java.util.Vector();

		     CubicPolynomial zeta = new CubicPolynomial(new double[] { coefficients[1] * (coefficients[0] - y)  - x, 1 + 2 * coefficients[2]  * (coefficients[0]  - y)  + coefficients[1] * coefficients[1], 3 * coefficients[2] * coefficients[1] , 2 * coefficients[2] * coefficients[2]});

 		     zeta.coefficients[0] /= zeta.coefficients[3];
 		     zeta.coefficients[1] /= zeta.coefficients[3];
  		     zeta.coefficients[2] /= zeta.coefficients[3];
 		     zeta.coefficients[3]   = 1.0d;

 		     zeta.computeRoots(roots);

             double[] realRoots = new double[roots.size()];

             for(int j = 0; j < realRoots.length; j++)
                  realRoots[j] = ((Double)roots.elementAt(j)).doubleValue();

		     double t = NumericalAnalysis.minimum(realRoots);

		     double intersectionX = t;
		     double intersectionY = map(t);

             return Math.pow(x - intersectionX, 2) + Math.pow(y - intersectionY, 2);

       }
*/
	   public void setCoefficient(double coefficient, double degree) {

		     if(degree == 0)
		        this.coefficients[0] = coefficient;

		     else if(degree == 1)
		        this.coefficients[1] = coefficient;

	   }

	   public double getCoefficient(double degree) {

		     if(degree == 0)
		        return this.coefficients[0];

		     else if(degree == 1)
		        return this.coefficients[1];

		     else if(degree == 2)
		        return this.coefficients[2];

		      return 0;

		 }

         public int degree() { return 2; }

	 }




