public class program
{
	public boolean test(int a, int b)
	{
		/*
		Exercise 9: Same last digit- Given two non-negative integers, return true if they
		have the same last digit, such as with 27 and 57. Note that the % "mod" operator
		computes remainder, so 17%10 is 7.
		*/
		if(a % 10 == b % 10){
			return true;
		}
		return false;
	}
}
