import java.util.Scanner;

class holder {
	public int old ;
}

public class intro
{
	public static void main ( String args [])
	{
		holder h ;
		h = new holder ();
		h.old = 20;
		System.out.println( " Value of old " + h . old );
		update(h ,30);
		System.out.println( " Value of old " + h . old );
	}

	static void update ( holder k , int newval )
	{
		k.old = newval ;
	}
}
