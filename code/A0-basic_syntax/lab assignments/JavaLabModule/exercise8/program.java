public class program
{
	public boolean test(int year)
	{
		/*
		Exercise 8: Leap year- Given a year, return true if it is a leap year otherwise
		return false. Please note that years that are multiples of 100 are not leap years,
		unless they are also multiples of 400.
		*/
		boolean is_divisible_by_4_and_not_by_100 = (year%4==0 && year%100!=0);
		boolean divisible_by_400 = (year%400==0);
		//return (is_divisible_by_4_and_not_by_100 || divisible_by_400);
	}
}
