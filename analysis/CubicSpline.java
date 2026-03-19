package shamu.analysis;

public class CubicSpline {

	  protected double[] x_knots;

      protected double[] y_knots;

      protected CubicPolynomial[] segments;

      public CubicSpline(int n) {

	       this.x_knots = new double[n];
	       this.y_knots = new double[n];

	       this.segments = new CubicPolynomial[n - 1];

	   }

	   public void createPolynomials() {

		    int n = this.x_knots.length;

		   double[] h_vector = new double[n - 1];
		   double[] b_vector = new double[n - 1];

		   for(int i = 0; i < h_vector.length; i++)
			      h_vector[i] = 	 x_knots[i + 1] - x_knots[i];

		   for(int i = 0; i < b_vector.length; i++)
			      b_vector[i] = 6.0 * (y_knots[i + 1] - y_knots[i]) / h_vector[i];

		   double[] z_vector = new double[n];

		   this.tridiagonalsolve(h_vector, b_vector, z_vector);

		   for(int i = 0; i < n - 1; i++) {

			    double alpha = 1.0 / h_vector[i];
			    double beta = h_vector[i] / 2.0;

			    double[] coefficients = new double[4];

		        coefficients[0] = (alpha / 6.0)  * (z_vector[i] * Math.pow(x_knots[i + 1], 3) - z_vector[i + 1] * Math.pow(x_knots[i], 3)) +
		                                        alpha * (x_knots[i+ 1] *  y_knots[i] - x_knots[i] * y_knots[i + 1]) +
		                                        (beta / 3.0) * (x_knots[i] * z_vector[i + 1] - x_knots[i + 1] * z_vector[i]);

		        coefficients[1] = (alpha / 2.0) * (z_vector[i + 1] * Math.pow(x_knots[i], 2) - z_vector[i] * Math.pow(x_knots[i + 1], 2)) +
		                                        alpha * (y_knots[i + 1] - y_knots[i]) + (beta / 3.0) * (z_vector[i] - z_vector[i + 1]);

		        coefficients[2] = (alpha / 2.0) * (z_vector[i] * x_knots[i + 1] - z_vector[i + 1] * x_knots[i]);

		        coefficients[3] = (alpha / 6.0) * (z_vector[i + 1] - z_vector[i]);

		        this.segments[i] = new CubicPolynomial(coefficients);

		   }

	   }

	   public void addKnot(int index, double x, double y) {

		    this.x_knots[index] = x;
		    this.y_knots[index] = y;

	   }

	   public CubicPolynomial getSegment(int i ) { return segments[i]; }

	   public int getLength() { return segments.length; }

       protected void tridiagonalsolve(double[] h_vector, double[] b_vector, double[] z_vector) {

            int n = h_vector.length;

		   double[] u_vector = new double[n];
		   double[] v_vector = new double[n];

		   u_vector[1] = 2.0 * (h_vector[0] + h_vector[1]);
		   v_vector[1] = b_vector[1] - b_vector[0];

		   for(int i = 2; i < n; i++) {

	             u_vector[i] = 2.0 * (h_vector[i] + h_vector[i-1]) - Math.pow(h_vector[i - 1], 2) / u_vector[i - 1];
	             v_vector[i] = b_vector[i] - b_vector[i - 1] - h_vector[i - 1] * v_vector[i - 1] /u_vector[i -1];

			}

            for(int i = n -1; i > 0; i--)
                  z_vector[i] = (v_vector[i] - h_vector[i] * z_vector[i+1] ) / u_vector[i];

        }

   }

