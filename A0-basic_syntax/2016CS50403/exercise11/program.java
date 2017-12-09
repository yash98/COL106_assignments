import java.lang.*;
public class program
{
	public float[] test(int b, int c)
	{
		/*
		Exercise 11: Roots of polynomial- Write a Java program that given b and c,
		computes the roots of the polynomial x*x+b*x+c. You can assume that the
		roots are real valued and need to be return in an array.
		Return the result in an array [p,q] where p<=q meaning the smaller 
		element should be the first element of the array
		*/
		float d;
		d = (float)Math.sqrt(Math.pow(b, 2) - (4*c));
		float m = (-1*b - d)/2;
		float n = (-1*b + d)/2;
		float ret[] = {m, n};
		return ret;
	}
}
