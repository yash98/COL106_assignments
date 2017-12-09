public class checker
{
	public static void main ( String args [])
	{
		String inputs[][] = {{}, {"a.java","a.pdf"}, {"a.java","a.java.pdf","b.java"}};
		String outputs[][] = {{}, {"a.java"}, {"a.java", "b.java"}};

		for(int i=0; i<inputs.length; i++) {
			String input[]=inputs[i];
			String oracle_output[]=outputs[i];

			program p = new program();

			String output[] = p.test(input);
			if(areEqual(output,oracle_output)) {
				System.out.println("test passed for " + toString(input));
			} else {
				System.out.println("test failed for " + toString(input));
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
