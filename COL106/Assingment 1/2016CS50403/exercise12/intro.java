import java . util . Scanner ;

public class intro
{
	public static void main ( String args [])
	{
		Scanner s = new Scanner ( System . in );
		System.out.println ( " Enter a number n " );
		int n = s . nextInt ();
		int [][] data = new int [ n ][ n +2];

		System.out.println( " number of rows = " + data.length + " and number of columns = " + data [0].length );
		
		for ( int i =0 ; i < data . length ; i ++){
			for ( int j =0; j < data [0]. length ; j ++)
				data [ i ][ j ] = 1;
		}

		int [][][] datam = new int [ n ][ n +2][ n +4];
		
		System.out.println(" Length along outermost indices = " + datam . length );
		System.out.println(" Length along middle indices = "+ datam [0]. length );
		System.out.println(" Length along innermost indices = " + datam [0][0]. length );
		
		for ( int i =0 ; i < datam . length ; i ++) {
			for ( int j =0; j < datam [0]. length ; j ++)
				for ( int k =0; k < datam [0][0]. length ; k ++)
					datam [i][j][k] = 1;
		}
	}
}
