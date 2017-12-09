public class checker
{
	public static void main ( String args [])
	{
		int inputs[][] = {{}, {2,-8}, {4,0}, {0}, {1,0,1}, {1,0,0,0,1,0}};
		int outputs[][] = {{}, {2,-8}, {4}, {}, {1,1}, {1,1}};

		for(int i=0; i<inputs.length; i++) {
			int input[]=inputs[i];
			int oracle_output[]=outputs[i];

			program p = new program();

			int output[] = p.test(input);
			if(areEqual(output, oracle_output)) {
				System.out.println("test passed for " + toString(input));
			} else {
				System.out.println("test failed for " + toString(input));
			}
		}
	}

	private static boolean areEqual(int a[], int b[])
	{
		if(a!=null && b!=null && a.length==b.length) {
			for(int i=0; i<a.length; i++) {
				int elementA = a[i];
				int elementB = b[i];

				if(elementA!=elementB) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	private static String toString(int array[])
	{
		String str = "{";
		for(int i=0; i<array.length; i++) {
			int element = array[i];
			if(i==array.length-1) {
				str+= element;
			} else {
				str+=element + ",";
			}
		}
		str+="}";
		return str;
	}
}
