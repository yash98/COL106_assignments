public class checker
{
	public static void main ( String args [])
	{
		int inputs[][] = {{}, {-3,3}, {1,2,3,4,5,6,7}, {1,3}};
		float outputs[] = {0.0f, 0.0f, 4.0f, 2.0f};

		for(int i=0; i<inputs.length; i++) {
			int input[]=inputs[i];
			float oracle_output=outputs[i];

			program p = new program();

			float output = p.test(input);
			if(output==oracle_output) {
				System.out.println("test passed for " + toString(input));
			} else {
				System.out.println("test failed for " + toString(input));
			}
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
