public class checker
{
	public static void main ( String args [])
	{
		String inputsA[] = {"hello","a", "01234567"};
		String inputsB[] = {"world","b", "891011"};
		int ms[] =         {3,0,3};
		int ns[] =         {0,0,4};
		String outputs[] = {"low","ab", "3456789101"};

		for(int i=0; i<inputsA.length; i++) {
			String a = inputsA[i];
			String b = inputsB[i];
			int m = ms[i];
			int n = ns[i];
			String oracle_output=outputs[i];

			program p = new program();

			String output = p.test(a,b,m,n);
			if(oracle_output.equals(output)) {
				System.out.println("test passed for a:" + a + ", b:" + b + ", m:" + m + ", n:" + n);
			} else {
				System.out.println("test failed for a:" + a + ", b:" + b + ", m:" + m + ", n:" + n);
			}
		}
	}

	private static String toString(String array[])
	{
		String str = "{";
		for(int i=0; i<array.length; i++) {
			String element = array[i];
			if(i==array.length-1) {
				str+= element;
			} else {
				str+=element + ",";
			}
		}
		str+="}";
		return str;
	}

	private static boolean areEqual(String a[], String b[])
	{
		if(a!=null && b!=null && a.length==b.length) {
			for(int i=0; i<a.length; i++) {
				String elementA = a[i];
				String elementB = b[i];

				if(elementA.equals(elementB)==false) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
}
