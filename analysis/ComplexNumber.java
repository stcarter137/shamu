package shamu.analysis;

public class ComplexNumber {

	 protected float re = 0.0f;

     protected float im = 0.0f;

     public ComplexNumber(float re, float im) {

		   this.re = re;
		   this.im = im;

	  }

	 public ComplexNumber(float re) { this(re, 0.0f); }

     public ComplexNumber() { this(0.0f, 0.0f); }

	 public float argument() {

		 float theta = (float)Math.atan(im / re);

        if(im > 0.0f && re < 0.0f)
           theta = theta + (float)Math.PI;

        else if(im < 0.0f && re <0.0f)
            theta = theta + (float)Math.PI;

       else if(im < 0.0f && re > 0.0f)
            theta = theta + 2.0f * (float)Math.PI;

		 return theta;

	 }

     public float magnitude() { return (float)Math.sqrt(re * re + im * im); }

     public void conjugate(ComplexNumber z) {

		    this.re = z.re;
		    this.im = -z.im;

     }

	 public void complexAdd(ComplexNumber z) {

		    this.re += z.re;
		    this.im += z.im;

	 }

	 public void complexAdd(ComplexNumber z1, ComplexNumber z2) {

		    this.re = z1.re + z2.re;
		    this.im = z1.im + z2.im;

	 }

	 public void  complexSubtract(ComplexNumber z) {

		   this.re -= z.re;
		   this.im -= z.im;

	 }

	 public void complexSubtract(ComplexNumber z1, ComplexNumber z2) {

		    this.re = z1.re - z2.re;
		    this.im = z1.im - z2.im;

	 }

	 public void complexMultiply(ComplexNumber z) {

		   this.re =  this.re * z.re - this.im * z.im;
		   this.im = this.re * z.im + this.im * z.re;

	 }

	public void complexRoot(ComplexNumber z, int degree) {

         float theta = argument() / degree;
         float mag =  (float)Math.pow(re * re + im * im, 1.0f / (2.0f * degree));

         z.re= mag * (float)Math.cos(theta);
         z.im = mag * (float)Math.sin(theta);

	 }

     public void setRealProjection(float re) { this.re = re; }

     public void setImaginaryProjection(float im) { this.im = im; }

     public float getRealProjection() { return this.re; }

     public float getImaginaryProjection() { return this.im; }

 }






