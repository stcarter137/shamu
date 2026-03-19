package shamu.analysis;

abstract public class AnalyticFunction {

    abstract public double map(double t);

    abstract public AnalyticFunction differentiate();

    public double inverse(double y, double xi, double xf) {

	       double xs = bisectionInverse(y, xi, xf);

	       return newtonianInverse(y, xs);

     }

    protected double bisectionInverse(double y, double xi, double xf) {

	     double yi = map(xi) - y;
	     double yf = map(xf) - y;

	     if(yi > yf) {

	        double temp_x = xi;

	        xi = xf;
	  	    xf = temp_x;

	     }

         double xm = (xi + xf) / 2.0f;
         double ym = map(xm) - y;

         if(Math.abs(xm - xi) < .001)
             return xm;

         else if(ym > 0)
	         return bisectionInverse(y, xi, xm);

         else
		     return bisectionInverse(y, xm, xf);

     }

    protected double newtonianInverse(double y, double xs) {

	     double xn = xs;

	     for(int j = 0; j < 10; j++) {

             double yfunc = map(xn) - y;
  		     double yprime = differentiate().map(xn);

             xn = xn - yfunc / yprime;

	      }

	   return xn;

   }

}