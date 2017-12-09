import java . util .*;
public class intro
{
	public static void main ( String args [])
	{
		HashMap < Integer , String > hm = new HashMap < Integer , String >();
		hm.put (3 , " three " );
		hm.put (5 , " five " );
		if ( hm.get(3)!= null )
			System.out.println ( " 3 is present with value = " + hm.get (3));
		else
			System.out.println ( " 3 is absent " );
	
		if ( hm . get (4)!= null )
			System.out.println ( " 4 is present with value = " + hm.get (4));
		else
			System.out.println ( " 4 is absent " );

		Iterator < Integer > it = hm . keySet (). iterator ();
		while ( it.hasNext ())
		{
			Integer tmp = it . next ();
			System.out.println ( " key = " + tmp + " value = " + hm.get ( tmp ));
		}
	}
}
