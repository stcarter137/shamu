package shamu.analysis;

public class Tuple {

    protected Polynomial xfunct;
    protected Polynomial yfunct;

    public Tuple() {

		 this.xfunct = null;
		 this.yfunct = null;

    }

    public Tuple(double x, double y) {

          this.xfunct = Polynomial.build(new double[] {x});
          this.yfunct = Polynomial.build(new double[] {y});

	  }

	  public Tuple(Polynomial funct) {

		     this.xfunct = Polynomial.build(new double[] {0.0d, 1.0d});
		     this.yfunct = funct;

	   }

       public Tuple differentiate() {

		     Tuple differentialTuple = new Tuple();

		     differentialTuple.xfunct = (Polynomial)xfunct.differentiate();
		     differentialTuple.yfunct = (Polynomial)yfunct.differentiate();

             return differentialTuple;

	   }

	   public Polynomial innerProduct(Tuple t2) {

		     int n = (this.xfunct.coefficients.length - 1) +  (t2.xfunct.coefficients.length - 1);
		     int m = (this.yfunct.coefficients.length - 1) +  (t2.yfunct.coefficients.length - 1);

		     Polynomial p1 = Polynomial.build(new double[n + 1]);
		     Polynomial p2 = Polynomial.build(new double[m + 1]);

		     p1.multiply(this.xfunct, t2.xfunct);
			 	 p2.multiply(this.yfunct, t2.yfunct);

				 Polynomial p3 = Polynomial.build(new double[Math.max(p1.coefficients.length, p2.coefficients.length)]);
		     p3.add(p1, p2);

            return p3;

	 }

}