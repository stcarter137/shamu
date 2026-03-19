package shamu.analysis;

public class NumericalAnalysis {

     public static double binaryAlgorithm(Polynomial function, double x1, double x2) {

		 double midpoint = (x1 + x2) / 2.0f;

		 double y = function.map(midpoint);

		 if((Math.abs(y)) < .0001)
		    return midpoint;

		else if (y > 0)
		    return binaryAlgorithm(function, x1, midpoint);

	    else
	        return binaryAlgorithm(function, midpoint, x2);

     }

      public static double newtonsAlgorithm(Polynomial function, double xs) {

            AnalyticFunction  differential = function.differentiate();

		   double ys = function.map(xs);

		   for(int j = 0; j < 64; j++) {

		           xs = xs - ys / differential.map(xs);

		           ys = function.map(xs);

			}

			return xs;

		}

		public static double hornersAlgorithm(double z, double[] source, double[]  destination) {

               int n = destination.length;

               destination[n - 1] = source[n];

               for(int j = 1; j <  n; j++)
				    destination[n - 1 -  j] = source[n - j] + z * destination[n - j];

				return source[0] + z *  destination[0];

	    }

        public static double arclength(AnalyticFunction map, double t0, double t1, double nu, double nv) {

			 AnalyticFunction differential = map.differentiate();

			 double deltaT = (t1 - t0) / 1000.0;

			 double t = t0;
			 double integral = 0.0;

			 while(t < t1)  {

				 double integrand =  Math.sqrt(Math.pow(nu, 2) + Math.pow(nv, 2) + Math.pow(differential.map(t), 2));

				 integral += integrand * deltaT;

			     t += deltaT;

		    }

			return integral;

    }

      public static double minimum(double[] reals) {

		     double min = reals[0];

		     for(int j  = 1; j < reals.length; j++)
		          if(Math.abs(reals[j]) < Math.abs(min))
		              min = reals[j];

		     return min;

	   }

}