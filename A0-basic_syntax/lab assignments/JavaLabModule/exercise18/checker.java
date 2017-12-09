public class checker
{
	public static void main ( String args [])
	{
		int inputs[] = {1234, 11, 144, 97764 };
		int outputs[] = {1, 1, 4, 7};

		for(int i=0; i<inputs.length; i++) {
			int input=inputs[i];
			int oracle_output=outputs[i];

			program p = new program();

			String input_string = "" + input;
			int output = p.test(input_string);
			if(output==oracle_output) {
				System.out.println("test passed for " + input);
			} else {
				System.out.println("test failed for " + input);
			}
		}
	}
}
