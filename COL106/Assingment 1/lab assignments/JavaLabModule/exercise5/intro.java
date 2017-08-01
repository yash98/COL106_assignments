import java.util.Scanner;
public class intro
{
	public String name ;
	public int age ;

	public static void main ( String args [])
	{
		Scanner s = new Scanner ( System.in );
		intro obj = new intro();
		System.out.println ( " Enter your name " );
		obj.name = s.nextLine ();
		System.out.println ( " Enter your age " );
		obj.age = s.nextInt ();
		s.close ();
		System.out.println ( " Name : " + obj.name );
		System.out.println ( " Age : " + obj.age );
	}
}
