public class checker
{
	public static void main ( String args [])
	{
		char keys[][] = { {'a','b'}, {'a','b','c'}, {'a','a','b'} };
		char answers[][] = { {'a','b'}, {'a','b','?'}, {'?','a','v'} };
		int  scores[] = { 8, 8, 3};

		for(int i=0; i<keys.length; i++) {
			char key[] = keys[i];
			char answer[] = answers[i];
			int oracle_output=scores[i];

			program p = new program();

			int output = p.test(key,answer);
			if(oracle_output==(output)) {
				System.out.println("test passed for key:" + toString(key) + ", score:" + toString(answer));
			} else {
				System.out.println("test failed for key:" + toString(key) + ", score:" + toString(answer));
			}
		}
	}

	private static String toString(char array[])
	{
		String str = "{";
		for(int i=0; i<array.length; i++) {
			char element = array[i];
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
