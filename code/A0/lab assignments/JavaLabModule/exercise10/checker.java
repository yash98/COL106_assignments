public class checker
{
	public static void main ( String args [])
	{
		int inputs[][] = {{10,2}, {3,13}, {12,14}};
		int outputs[] = {10, 39, 84};

		for(int i=0; i<inputs.length; i++) {
			int input_a=inputs[i][0];
			int input_b=inputs[i][1];
			int oracle_output=outputs[i];

			program p = new program();

			int output = p.test(input_a, input_b);
			if(output==oracle_output) {
				System.out.println("test passed for " + input_a + "," + input_b);
			} else {
				System.out.println("test failed for " + input_a + "," + input_b);
			}
		}
	}
}
