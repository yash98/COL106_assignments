import java.util.Scanner;
public class intro
{
	public static void main ( String args [])
	{
		Scanner s = new Scanner ( System.in );
		System.out.println ( " Enter a number " );
		int inp = s.nextInt ();
		System.out.println ( " Using if - else construct " );
		
		if (inp % 2 == 0)
			System.out.println ( inp + " is even " );
		else
			System.out.println ( inp + " is odd " );
		
		System.out.println ( " Using switch case construct " );
		switch(inp % 2){
			case 0:
				System.out.println ( inp + " is even " );
				break; // remove this break, and give an even number as input
			case 1:
				System.out.println ( inp + " is odd " );
				break;
		}
	}
}
