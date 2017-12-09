public class checker
{
	public static void main ( String args [])
	{
		int inputs[] = {1, 4, 100, 400};
		boolean outputs[] = {false, true, false, true};

		for(int i=0; i<inputs.length; i++) {
			int input=inputs[i];
			boolean oracle_output=outputs[i];

			program p = new program();

			boolean output = p.test(input);
			if(output==oracle_output) {
				System.out.println("test passed for " + input);
			} else {
				System.out.println("test failed for " + input);
			}
		}
	}
}
