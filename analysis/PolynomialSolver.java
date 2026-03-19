package shamu.analysis;

public class PolynomialSolver {

        public static void solve(Polynomial leftPolynomial, Polynomial rightPolynomial, java.util.Vector<Double> solutionSet) {

               int m = Math.max(leftPolynomial.coefficients.length, rightPolynomial.coefficients.length);

			   Polynomial p = Polynomial.build(new double[m]);

			   p.subtract(leftPolynomial, rightPolynomial);

               for(int j = 0; j < m; j++)
                    p.coefficients[j] /= p.coefficients[m - 1];

			   p.computeRoots(solutionSet);

		}

}

